package tr.com.vbt.cobol.convert;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.ddm.DDMList;
import tr.com.vbt.java.MethodImplementation;
import tr.com.vbt.java.MethodSignature;
import tr.com.vbt.java.database.IteratorNameManager;
import tr.com.vbt.java.database.SQLManager;
import tr.com.vbt.java.general.JavaClassElement;
import tr.com.vbt.java.general.JavaClassGeneral;
import tr.com.vbt.java.general.JavaConstants;
import tr.com.vbt.java.util.RuleNotFoundException;
import tr.com.vbt.java.utils.ConvertUtilities;
import tr.com.vbt.java.utils.WriteFileUtility;
import tr.com.vbt.lexer.AbstractLexing;
import tr.com.vbt.lexer.ConversionFileType;
import tr.com.vbt.lexer.ConversionLogModel;
import tr.com.vbt.lexer.ConversionLogReport;
import tr.com.vbt.lexer.NaturalLexing;
import tr.com.vbt.lexer.Phase;
import tr.com.vbt.natural.parser.general.ElementMainStart;
import tr.com.vbt.natural.parser.general.ElementNaturalProgram;
import tr.com.vbt.natural.parser.general.ElementSubroutine;
import tr.com.vbt.patern.CommandList;
import tr.com.vbt.patern.NaturalCommandList;
import tr.com.vbt.patern.PaternManager;
import tr.com.vbt.patern.PaternManagerDataTypesNaturalImpl;
import tr.com.vbt.patern.PaternManagerMapNaturalImpl;
import tr.com.vbt.patern.PaternManagerNaturalImpl;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.TokenTipi;
import tr.com.vbt.util.ConversionMode;
import tr.com.vbt.util.ConverterConfiguration;
import tr.com.vbt.util.WriteToFile;

public class TransferFromNaturalToJavaMain {

	final static Logger logger = LoggerFactory.getLogger(TransferFromNaturalToJavaMain.class);

	ConversionOperationLogger conversionLogger = new ConversionOperationLogger();

	protected PaternManager paternManagerDataType = new PaternManagerDataTypesNaturalImpl();

	protected PaternManager paternManagerCode = new PaternManagerNaturalImpl();

	protected PaternManager paternManagerMap = new PaternManagerMapNaturalImpl();

	/**
	 * @param args
	 * @throws FileNotFoundException
	 */
	// Type1: java Latif File HanStart
	// Type2: java Latif Folder

	// Latif WINDOWS MB Map Files IDGM0004
	public static void main(String[] args) {
		ConversionLogReport.getInstance().reset();

		ConversionLogModel logModel = ConversionLogModel.getInstance();

		logModel.setUser(args[0]);
		logModel.setOPERATING_SYSTEM(args[1]);
		logModel.setCustomer(args[2]);
		logModel.setModule(args[3]);
		logModel.setSchemaName(args[4]);

		if (args[5].equals(ConversionFileType.PROGRAM.toString())) {
			logModel.setConversionFileType(ConversionFileType.PROGRAM);
		} else if (args[5].equals(ConversionFileType.SUBPROGRAM.toString())) {
			logModel.setConversionFileType(ConversionFileType.SUBPROGRAM);
		} else if (args[5].equals(ConversionFileType.MAP.toString())) {
			logModel.setConversionFileType(ConversionFileType.MAP);
		} else if (args[5].equals(ConversionFileType.MAP_TESTER.toString())) {
			logModel.setConversionFileType(ConversionFileType.MAP_TESTER);
		} else {
			throw new RuntimeException(
					"Conversion File Type Set Edilmeli: Degerler: 	PROGRAM 	SUBPROGRAM  	MAP");
		}

		logModel.setConvertOperationType(args[6]);
		if (logModel.getConvertOperationType().equals("Folder")) {

			if (args[7].equals(ConversionMode.ALL_SOURCE_CODES.toString())) {
				logModel.setConversionMode(ConversionMode.ALL_SOURCE_CODES);
			} else if (args[7].equals(ConversionMode.SOURCE_CODES_WITHOUT_GENERATED_JAVA.toString())) {
				logModel.setConversionMode(ConversionMode.SOURCE_CODES_WITHOUT_GENERATED_JAVA);
			} else if (args[7].equals(ConversionMode.SOURCE_CODES_WITHOUT_GENERATED_LEX.toString())) {
				logModel.setConversionMode(ConversionMode.SOURCE_CODES_WITHOUT_GENERATED_LEX);
			} else {
				throw new RuntimeException(
						"Conversion Mode Set Edilmeli: Degerler: 	ALL_SOURCE_CODES 	SOURCE_CODES_WITHOUT_GENERATED_JAVA  	SOURCE_CODES_WITHOUT_GENERATED_LEX");
			}
		}

		if (logModel.getConvertOperationType().equals("Files")) {

			List<String> fileList = new ArrayList<>();
			for (int i = 7; i < args.length; i++) {
				fileList.add(args[i]);
			}

			logModel.setFileList(fileList);
		}
		
		ConverterConfiguration.customer = logModel.getCustomer();
		ConverterConfiguration.OPERATING_SYSTEM = logModel.getOPERATING_SYSTEM();
		logModel.setFolderPath(ConverterConfiguration.getFolderPath());
		logModel.setFolderMainPath(ConverterConfiguration.getMainFolderPath());

		try {
			reCreateOutputFoldersForDAL();
			operateConversionForAModule();
		} catch (Exception e) {
			logger.debug(e.getMessage(),e);
			return;
		}
		ConversionLogReport.getInstance().writeReport();
	}

	
	public static void operateConversionForAModule() throws Exception {
		ConversionLogModel logModel = ConversionLogModel.getInstance();
		TransferFromNaturalToJavaMain transferDriver = null;
		
		logModel.setFolderPath(ConverterConfiguration.getFolderPath());

		if (logModel.getConvertOperationType().equals("Folder")) {

			File folder = null;
			if (ConversionLogModel.getInstance().isProgram()) {
				folder = new File(ConverterConfiguration.getFolderPath());
			} else if (ConversionLogModel.getInstance().isSubProgram()) {
				folder = new File(ConverterConfiguration.getSubFolderPath());
			} else if (ConversionLogModel.getInstance().isMapOrMapTester()) {
				folder = new File(ConverterConfiguration.getFolderPathMap());
			}
			File[] listOfFiles = folder.listFiles();

			if (listOfFiles == null || listOfFiles.length == 0) {
				logger.debug("Folder boş yada bulunamadi:" + folder.getAbsolutePath());
				logger.debug("Folder Adı:" + folder.getName());
				return;
			}

			for (int i = 0; i < listOfFiles.length; i++) {

				if (listOfFiles[i].getName().equals(".gitignore")) {
					continue;
				}

				ConverterConfiguration.className = listOfFiles[i].getName().replaceAll(".txt", "");
				logModel.setFileName(ConverterConfiguration.className);

				if (listOfFiles[i].isDirectory()) {
					continue;
				}

				if (listOfFiles[i].isFile() && !isInConversionSet(listOfFiles[i])) {
					logger.warn("Conversion Has Not Been Started For File " + logModel.getFileName()
							+ " is not in conversion set!!!");
					continue;
				}

				if (listOfFiles[i].isFile() && isInConversionSet(listOfFiles[i])) {
					try {
						logger.warn("**********************************************************************");
						logger.warn("*****************************START**************************************");
						logger.warn("**********************************************************************");
						logger.warn("Conversion Statu: Started For File " + logModel.getFileName());
						transferDriver = new TransferFromNaturalToJavaMain();
						transferDriver.setConversionConfigurationMode();
						MDC.put("customer", logModel.getCustomer());
						MDC.put("module", logModel.getModule());
						MDC.put("conversionFileType", logModel.getConversionFileType().toString());
						MDC.put("fileName", logModel.getFileName());
						logModel.setClientInteracting(logModel.isProgram());
						transferDriver.driveTransfer(logModel);
						//transferDriver.writeDalCodes(logModel);
						//transferDriver.writeDalHibernateCodes(logModel);
						logger.warn("Conversion Statu: Ended For File " + logModel.getFileName());
						logger.warn("**********************************************************************");
						logger.warn("******************************END***************************************");
						logger.warn("**********************************************************************");

						ConversionLogReport.getInstance().conversionSuccess();

					} catch (Exception e) {
						logger.warn("Conversion Statu: Aborted For File " + logModel.getFileName() + " WITH ERROR+");
						logger.warn(e.getMessage(), e);
						logger.warn("**********************************************************************");
						logger.warn("****************************ERROR END***********************************");
						logger.warn("**********************************************************************");
					}

				}
			}
		} else if (ConversionLogModel.getInstance().getConvertOperationType().equals("Files")) {
			for (int i = 0; i < logModel.getFileList().size(); i++) {
				try {

					ConverterConfiguration.className = ConversionLogModel.getInstance().getFileList().get(i).toString();
					logModel.setFileName(ConverterConfiguration.className);

					logger.warn("**********************************************************************");
					logger.warn("*****************************START**************************************");
					logger.warn("**********************************************************************");
					logger.warn("Conversion Statu: Started For File " + logModel.getFileName());

					logModel.setClientInteracting(true);
					transferDriver = new TransferFromNaturalToJavaMain();
					transferDriver.setConversionConfigurationMode();
					MDC.put("InputFileForConversion", logModel.getFileName());
					transferDriver.driveTransfer(logModel);
					//transferDriver.writeDalCodes(logModel);
					//transferDriver.writeDalHibernateCodes(logModel);
					logger.warn("Conversion Statu: Ended For File " + logModel.getFileName());
					logger.warn("**********************************************************************");
					logger.warn("******************************END***************************************");
					logger.warn("**********************************************************************");

					ConversionLogReport.getInstance().conversionSuccess();

				} catch (Exception e) {
					logger.warn(e.getMessage(), e);
					logger.warn("Conversion Statu: Aborted For File " + logModel.getFileName() + " WITH ERROR", e);
					logger.warn("**********************************************************************");
					logger.warn("****************************ERROR END***********************************");
					logger.warn("**********************************************************************");

				}
			}
		}
		
	
	}
	// Module/seperatedPrograms/output silinecek
	// Module/seperatedPrograms/subprogram/output silinecek
	// Module/seperatedPrograms/map/output silinecek

	// Module/seperatedPrograms/output dosyasını yaratacak
	// Module/seperatedPrograms/subprogram/output
	// Module/seperatedPrograms/map/output
	// Module/seperatedPrograms/output/java/web dosyasını yaratacak
	// Module/seperatedPrograms/output/java/web/map dosyasını yaratacak
	// Module/seperatedPrograms/output/java/web/subprogram dosyasını yaratacak
	
	private  void setConversionConfigurationMode() {
		// TODO Auto-generated method stub
		if(ConversionLogModel.getInstance().getCustomer().toUpperCase().equals("MB")){
			ConverterConfiguration.pojosAreDefinedInCode=false;
		}
		
	}

	public static void reCreateOutputFoldersForAModule() throws Exception {
		// TODO Auto-generated method stub
		String FILENAME = ConversionLogModel.getInstance().getFolderMainPath() + "/"
				+ ConversionLogModel.getInstance().getModule() + "/SeperatedPrograms" + "/output";
		String FILENAMEMAP = ConversionLogModel.getInstance().getFolderMainPath() + "/"
				+ ConversionLogModel.getInstance().getModule() + "/SeperatedPrograms" + "/Map" + "/output";
		String FILENAMESUBBPROGRAM = ConversionLogModel.getInstance().getFolderMainPath() + "/"
				+ ConversionLogModel.getInstance().getModule() + "/SeperatedPrograms" + "/Subprogram" + "/output";
		String FILENAME_JAVA = ConversionLogModel.getInstance().getFolderMainPath() + "/"
				+ ConversionLogModel.getInstance().getModule() + "/SeperatedPrograms" + "/output/web";
		String FILENAME_JAVA_MAP = ConversionLogModel.getInstance().getFolderMainPath() + "/"
				+ ConversionLogModel.getInstance().getModule() + "/SeperatedPrograms" + "/output/web/map";
		String FILENAME_JAVA_MAP_TESTER = ConversionLogModel.getInstance().getFolderMainPath() + "/"
				+ ConversionLogModel.getInstance().getModule() + "/SeperatedPrograms" + "/output/web/maptester";
		String FILENAME_JAVA_SUBPROGRAM = ConversionLogModel.getInstance().getFolderMainPath() + "/"
				+ ConversionLogModel.getInstance().getModule() + "/SeperatedPrograms" + "/output/web/subprogram";
		
		File index = new File(FILENAME);
		File indexSubProgram = new File(FILENAMESUBBPROGRAM);
		File indexMap = new File(FILENAMEMAP);
		File indexJava = new File(FILENAME_JAVA);
		File indexJavaMap = new File(FILENAME_JAVA_MAP);
		File indexJavaMapTest = new File(FILENAME_JAVA_MAP_TESTER);
		File indexJavaSubprogram = new File(FILENAME_JAVA_SUBPROGRAM);
		
		WriteFileUtility.deleteFolder(indexJavaSubprogram);
		WriteFileUtility.deleteFolder(indexJavaMapTest);
		WriteFileUtility.deleteFolder(indexJavaMap);
		WriteFileUtility.deleteFolder(indexJava);
		WriteFileUtility.deleteFolder(indexSubProgram);
		WriteFileUtility.deleteFolder(indexMap);
		WriteFileUtility.deleteFolder(index);
		
		
		WriteFileUtility.createFileInPath(FILENAME);
		try {
			WriteFileUtility.createFileInPath(FILENAMEMAP);
		} catch (Exception e) {
			logger.debug("Map dosyası create edilemedi. Modüle ait map olmayabilir.",e);
		}
		WriteFileUtility.createFileInPath(FILENAMESUBBPROGRAM);
		WriteFileUtility.createFileInPath(FILENAME_JAVA);
		WriteFileUtility.createFileInPath(FILENAME_JAVA_MAP);
		WriteFileUtility.createFileInPath(FILENAME_JAVA_MAP_TESTER);
		WriteFileUtility.createFileInPath(FILENAME_JAVA_SUBPROGRAM);

	}
	
	public static void reCreateOutputFoldersForDAL() throws Exception {
		
		WriteFileUtility.deleteFolder(new File(ConversionLogModel.getInstance().getCommonDALOutputsFolder() + "/output"+ "/generatedinterface/tr/com/"+ConversionLogModel.getInstance().getCustomer().toLowerCase()+"/dal/hibernate/generated"));
		WriteFileUtility.deleteFolder(new File(ConversionLogModel.getInstance().getCommonDALOutputsFolder() + "/output"+ "/generatedinterface/tr/com/"+ConversionLogModel.getInstance().getCustomer().toLowerCase()+"/dal/hibernate"));
		WriteFileUtility.deleteFolder(new File(ConversionLogModel.getInstance().getCommonDALOutputsFolder() + "/output"+ "/generatedinterface/tr/com/"+ConversionLogModel.getInstance().getCustomer().toLowerCase()+"/dal/generated"));
		WriteFileUtility.deleteFolder(new File(ConversionLogModel.getInstance().getCommonDALOutputsFolder() + "/output"+ "/generatedinterface/tr/com/"+ConversionLogModel.getInstance().getCustomer().toLowerCase()+"/dal/"));
		WriteFileUtility.deleteFolder(new File(ConversionLogModel.getInstance().getCommonDALOutputsFolder() + "/output"+ "/generatedinterface/tr/com/"+ConversionLogModel.getInstance().getCustomer().toLowerCase()+"/"));
		WriteFileUtility.deleteFolder(new File(ConversionLogModel.getInstance().getCommonDALOutputsFolder() + "/output"+ "/generatedinterface/tr/com/"));
		WriteFileUtility.deleteFolder(new File(ConversionLogModel.getInstance().getCommonDALOutputsFolder() + "/output"+ "/generatedinterface/tr/"));
		WriteFileUtility.deleteFolder(new File(ConversionLogModel.getInstance().getCommonDALOutputsFolder() + "/output"+ "/generatedinterface/"));
		WriteFileUtility.deleteFolder(new File(ConversionLogModel.getInstance().getCommonDALOutputsFolder() + "/output"));

		
		WriteFileUtility.createFileInPath(ConversionLogModel.getInstance().getCommonDALOutputsFolder() + "/output");
		
		WriteFileUtility.createFileInPath(ConversionLogModel.getInstance().getCommonDALOutputsFolder() + "/output"+ "/generatedinterface");
		WriteFileUtility.createFileInPath(ConversionLogModel.getInstance().getCommonDALOutputsFolder() + "/output"+ "/generatedinterface/tr");
		WriteFileUtility.createFileInPath(ConversionLogModel.getInstance().getCommonDALOutputsFolder() + "/output"+ "/generatedinterface/tr/com");
		WriteFileUtility.createFileInPath(ConversionLogModel.getInstance().getCommonDALOutputsFolder() + "/output"+ "/generatedinterface/tr/com/"+ConversionLogModel.getInstance().getCustomer().toLowerCase()+"/");
		WriteFileUtility.createFileInPath(ConversionLogModel.getInstance().getCommonDALOutputsFolder() + "/output"+ "/generatedinterface/tr/com/"+ConversionLogModel.getInstance().getCustomer().toLowerCase()+"/dal/");
		WriteFileUtility.createFileInPath(ConversionLogModel.getInstance().getCommonDALOutputsFolder() + "/output"+ "/generatedinterface/tr/com/"+ConversionLogModel.getInstance().getCustomer().toLowerCase()+"/dal/generated");
		WriteFileUtility.createFileInPath(ConversionLogModel.getInstance().getCommonDALOutputsFolder() + "/output"+ "/generatedinterface/tr/com/"+ConversionLogModel.getInstance().getCustomer().toLowerCase()+"/dal/hibernate");
		WriteFileUtility.createFileInPath(ConversionLogModel.getInstance().getCommonDALOutputsFolder() + "/output"+ "/generatedinterface/tr/com/"+ConversionLogModel.getInstance().getCustomer().toLowerCase()+"/dal/hibernate/generated");

		
	}



	private static boolean isInConversionSet(File file) {

		if (ConversionLogModel.getInstance().getConversionMode().equals(ConversionMode.ALL_SOURCE_CODES)) {
			return true;
		} else if (ConversionLogModel.getInstance().getConversionMode()
				.equals(ConversionMode.SOURCE_CODES_WITHOUT_GENERATED_JAVA) && isJavaAlreadyGenerated(file)) {
			logger.warn("Conversion Statu: " + ConversionLogModel.getInstance().getFileName()
					+ " has not been started cause of java is already there ");
			logger.debug(file.getName()
					+ " Java Kodu Daha once yaratıldığından tekrar çalıştırılmadı. Çalıştırmak isteniyorsa output folderdan java dosyası silinmeli.");
			return false;

		} else if (ConversionLogModel.getInstance().getConversionMode()
				.equals(ConversionMode.SOURCE_CODES_WITHOUT_GENERATED_LEX) && isLexAlreadyGenerated(file)) {
			logger.warn("Conversion Statu: " + ConversionLogModel.getInstance().getFileName()
					+ " has not been started cause of lex is already there ");
			logger.debug(file.getName()
					+ " Lex Aşamasına Daha once geldiğinden tekrar çalıştırılmadı. Çalıştırmak isteniyorsa output folderdan lex dosyası silinmeli.");
			return false;
		}
		return true;
	}

	private static boolean isLexAlreadyGenerated(File file) {
		if (WriteToFile.fileIsThere(ConversionLogModel.getInstance().getFullTokenizeFileName())) {
			return true;
		}
		return false;
	}

	private static boolean isJavaAlreadyGenerated(File file) {
		if (WriteToFile.fileIsThere(ConversionLogModel.getInstance().getFullJavaFileName())) {
			return true;
		}
		return false;
	}

	public void writeDalHibernateCodes() {

		Set<String> keys = JavaClassElement.javaHibernateCodeMap.keySet();

		String pojoName;
		
		MethodImplementation findByMethodImplemantation;

		Set<String> tables = new HashSet<String>();
		for (String key : keys) {
			System.out.println(key);
			
			findByMethodImplemantation = JavaClassElement.javaHibernateCodeMap.get(key);
			
			pojoName=findByMethodImplemantation.getMethodSignature().getReturnType();
			
			if (!tables.contains(pojoName)) { // Bu pojoyu İlk kez yazacaksa
				StringBuffer interfaceHeader = ConvertUtilities.writeDAOImplemantasyonClassHeader(pojoName);
				try {
					WriteToFile.writeHeaderToFile(interfaceHeader, ConversionLogModel.getInstance().getFullJavaHibernateFileName(pojoName));
				} catch (IOException e) {
					logger.warn(
							"DAL Interface  Write For File" + pojoName + " " + findByMethodImplemantation.toString() + " WITH ERROR+");
					logger.warn("", e);
					logger.warn("**********************************************************************");
					logger.warn("****************************ERROR END***********************************");
					logger.warn("**********************************************************************");
				}
				tables.add(pojoName);
			}

			try {
		
				WriteToFile.appendToFile(findByMethodImplemantation.toString(), ConversionLogModel.getInstance().getFullJavaHibernateFileName(pojoName));
			} catch (IOException e) {
				logger.warn("DAL Interface  Write For File" + pojoName + " " + findByMethodImplemantation.toString() + " WITH ERROR+");
				logger.warn("", e);
				logger.warn("**********************************************************************");
				logger.warn("****************************ERROR END***********************************");
				logger.warn("**********************************************************************");
			}
		}

	}

	public void writeDalCodes() throws FileNotFoundException {

		Set<String> keys = JavaClassElement.javaDAOInterfaceCodeMap.keySet();

		String pojoName = null;
		
		MethodSignature findByMethodSignature;

		Set<String> tables = new HashSet<String>();
		for (String key : keys) {
			System.out.println(key);
			//pojoName = key.substring(0, key.indexOf(" "));
			
			findByMethodSignature = JavaClassElement.javaDAOInterfaceCodeMap.get(key);

			pojoName=findByMethodSignature.getReturnType();
			
			if (!tables.contains(pojoName)) { // Bu pojoyu İlk kez yazacaksa
				StringBuffer interfaceHeader = ConvertUtilities.writeInterfaceHeader(pojoName);
				try {
					WriteToFile.writeHeaderToFile(interfaceHeader, ConversionLogModel.getInstance().getFullJavaDAOInterfaceFileName(pojoName));
				} catch (IOException e) {
					logger.warn(
							"DAL Interface  Write For File" + pojoName + " " + findByMethodSignature + " WITH ERROR+");
					logger.warn("", e);
					logger.warn("**********************************************************************");
					logger.warn("****************************ERROR END***********************************");
					logger.warn("**********************************************************************");
				}
				tables.add(pojoName);
			}

			try {
				
				WriteToFile.appendToFile("//"+findByMethodSignature.writeCallingPrograms()+JavaConstants.NEW_LINE, ConversionLogModel.getInstance().getFullJavaDAOInterfaceFileName(pojoName));
				
				WriteToFile.appendToFile(findByMethodSignature.toString(), ConversionLogModel.getInstance().getFullJavaDAOInterfaceFileName(pojoName));
			
			} catch (IOException e) {
				logger.warn("DAL Interface  Write For File" + pojoName + " " + findByMethodSignature + " WITH ERROR+");
				logger.warn("", e);
				logger.warn("**********************************************************************");
				logger.warn("****************************ERROR END***********************************");
				logger.warn("**********************************************************************");
			}
			
			registerPojo(pojoName);

		}

	}

	private void registerPojo(String pojoName) {

		ConversionLogModel.getInstance().registerPojo(pojoName);
		
	}


	public void endCloseTagGenDao() {
		
       Iterator iterator = ConversionLogModel.getInstance().getPojoMap().entrySet().iterator();
      
       String fileName,fileNameHibernate;
       
       while (iterator.hasNext()) {
            Map.Entry mapEntry = (Map.Entry) iterator.next();
            
            fileName=ConversionLogModel.getInstance().getFullJavaDAOInterfaceFileName(mapEntry.getKey().toString());
            
            fileNameHibernate=ConversionLogModel.getInstance().getFullJavaHibernateFileName(mapEntry.getKey().toString());
            
			try {
				WriteToFile.appendToFile("}", fileName);
				
				WriteToFile.appendToFile("}", fileNameHibernate);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
	}



	private void driveTransfer(ConversionLogModel logModel) throws Exception {

		StringBuilder sb = null;

		logModel.setPhase(Phase.START);
		MDC.put("phase", logModel.getPhase().toString());
		conversionLogger.logConversion(logModel);

		AbstractLexing lexer = new NaturalLexing();
		CommandList commandList = null;
		ElementNaturalProgram ep;
		JavaClassGeneral javaTreeElement = null;

		resetEveryThingForNewConversion();

		// ********************************************************************************************
		{

			logModel.setPhase(Phase.TOKENIZE);
			MDC.put("phase", logModel.getPhase().toString());
			logModel.setOutput(sb);
			conversionLogger.logConversion(logModel);

			// 1 Tokenize Source File
			lexer.tokenizeSourceFile(logModel.getFullNaturalInputFileName(), logModel.getModule(),
					logModel.getCustomer());

			// 1.1 Alter Midlines
			// TODO: open below code
			// alterMidLinesToSubLines(lexer);

			// 1.2 Export Tokenized Source File
			sb = lexer.exportLexedData(logModel.getFullTokenizeFileName()); // lex
																			// uzantılı
																			// dosyayı
																			// yaz.

			ConversionLogReport.getInstance().conversionStart();

		}

		// ********************************************************************************************
		{

			try {

				logModel.setPhase(Phase.PARSE_TO_COMMANDS);
				MDC.put("phase", logModel.getPhase().toString());
				logModel.setOutput(sb);
				conversionLogger.logConversion(logModel);

				// 2 Parse tokens To Command List
				// 2.1 Parse Data Type Definition tokens( END_DEFINE oncesi) To
				// Command List
				commandList = NaturalCommandList.getInstance(lexer.tokenListesi, lexer);

				//commandList.replaceGlobalVariables();

				try {
					commandList.setPaternManager(paternManagerDataType);
					commandList.parseDataTypeTokensToCommandsList();
				} catch (Exception e1) {
					logger.warn("", e1);
					throw e1;
				}

				commandList.setTypeNameOfView();

				commandList.changeProgramDataTypeToDBDataType(); // ElementProgramGrupNatural -->
																	// ElementDBDataTypeNatural

				commandList.findAndSetDataTypeDefinitions(); // Record DataType
																// ların sonuna
																// Ender ekler.

				commandList.setRedefineElementsParents(); // Redefine Grup
															// Elementların Redefine
															// Ettiği normal
															// dataType ı set eder.

				commandList.markSimpleRedefineElements(); // Parse düzeltmesidir.
															// Redefine olarak parse
															// edilemeyen ama
															// redefine olan
															// dataType tanım
															// commandları
															// redefineCommand ile
															// replace edilir.

				commandList.markOneDimensionRedefineElementOfSimpleDataType(); // Parse
				// düzeltmesidir.
				// Redefine olarak
				// parse edilemeyen
				// ama redefine olan
				// dataType tanım
				// commandları
				// redefineCommand
				// ile replace
				// edilir.

				commandList.markTwoDimensionRedefineElements(); // Parse
																// düzeltmesidir.
																// Redefine olarak
																// parse edilemeyen
																// ama redefine olan
																// dataType tanım
																// commandları
																// redefineCommand
																// ile replace
																// edilir.

				commandList.markRedefinedTokens();

				commandList.createAndFillRecordDataTypeMap();
				commandList.addGrupNamesToRecordItems();

				commandList.controlRecords();

				// 2.2 Parse Normal Commands( END_DEFINE sonrası) tokens To Command
				// List
				try {
					commandList.setPaternManager(paternManagerCode);
					commandList.parseCommandTokensToCommandsList();
				} catch (Exception e1) {
					e1.printStackTrace();
					return;
				}

				sb = commandList.writeCommands(logModel.getFullCommandFileName(),
						logModel.getFullUndefinedCommandFileName());

				commandList.addVirtualEndings();

				sb = commandList.writeCommands(logModel.getFullCommandFileName());

				// 2.1 Find And Set Endings
				if (ConverterConfiguration.STOP_ENGINE_ON_TREE_CREATE_ERROR) {

					commandList.findAndSetEndingCommands();

				} else {

					try {
						commandList.findAndSetEndingCommands();
					} catch (Exception e1) {
						logger.error("Her türlü program devam etmeli.", e1);
					}
				}

				// 2.2 Write Commands
				sb = commandList.writeCommands(logModel.getFullCommandFileNamewithEndings());

				// 2.3 Tüm komutlara command Listesini set Et
				for (AbstractCommand command : commandList.getCommandList()) {
					command.setCommandListesi(commandList.getCommandList());
				}

				try {
					commandList.generateDatatypeMap();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					logger.warn("", e);
				}
			} catch (Exception e) {
				logger.debug(e.getMessage(), e);
				throw e;
			} finally {
				sb = commandList.writeCommands(logModel.getFullCommandFileName(),
						logModel.getFullUndefinedCommandFileName());
			}

			controlIfThereIsUndefinedCommand(commandList);

		}

		// ********************************************************************************************

		// ********************************************************************************************
		{

			logger.warn("**********************************************************************");
			logger.warn("****************************Natural Ağaç Oluşturma Start**************");
			logger.warn("**********************************************************************");

			logModel.setPhase(Phase.PARSE_TO_SOURCE_TREE);
			MDC.put("phase", logModel.getPhase().toString());
			logModel.setOutput(sb);
			conversionLogger.logConversion(logModel);

			// 3 Command Dizisini Abstract Cobol Ağacına Çevir.
			ep = new ElementNaturalProgram(commandList.getCommandList());
			ep.parseBaslat();
			
			//moveOnErrorMethod(ep);

			// 3.1 Abstract Cobol Ağacını Yazdır.
			sb = ep.exportBaslat(logModel.getFullSourceTreeFileName());

			logger.warn("**********************************************************************");
			logger.warn("****************************Natural Ağaç Oluşturma End**************");
			logger.warn("**********************************************************************");

		}
		// ********************************************************************************************

		// ********************************************************************************************
		{
			// 4 Natural Ağacından Java ağacını oluştur.

			try {
				// Program çevrimi başlamadan önce önceki çevrimi
				// resetlemeliyiz.
				JavaClassGeneral.resetInstance();

				logModel.setPhase(Phase.CONVERT_NATURAL_TREE_TO_JAVA_TREE);
				MDC.put("phase", logModel.getPhase().toString());
				logModel.setOutput(sb);
				conversionLogger.logConversion(logModel);

				if (ep.startGenerateTree()) {
					
					javaTreeElement = (JavaClassGeneral) JavaClassGeneral.getInstance(); // Oluşan
																							// java
																							// sınıfını
																							// dön.
					
					javaTreeElement.moveAllLevelElementsIntoValidationBranch();
					
					// 4.1 Soyut Java Ağacını XML dosyaya yaz.
					sb = javaTreeElement.exportJavaTreeBaslat(logModel.getFullJavaTreeFileName());

				}
			} catch (RuleNotFoundException e) {
				logger.warn("Rule Not Found For:" + e.getCobolDetailedName());
				logger.warn("", e);
			}

		}
		// ********************************************************************************************

		// ********************************************************************************************
		{
			logModel.setPhase(Phase.WRITE_JAVA_CODE);
			MDC.put("phase", logModel.getPhase().toString());
			logModel.setOutput(sb);
			conversionLogger.logConversion(logModel);

			// 5 JAva Ağacından Java kodunu oluştur.
			javaTreeElement.resetSourceCode();
			IteratorNameManager.resetIteratorNameManager();
			sb = javaTreeElement.writeJavaBaslat(logModel.getFullJavaFileName());
			
			DDMList.getInstance().writeUndefinedDDMList();
			
			javaTreeElement.resetSourceCode();
			IteratorNameManager.resetIteratorNameManager();
			
			writeMapTester(javaTreeElement);
	
			javaTreeElement.resetSourceCode();
			IteratorNameManager.resetIteratorNameManager();
		}

	}

	private void moveOnErrorMethod(ElementNaturalProgram ep) {
		List<AbstractCommand> children=ep.getChildElementList();
		
		AbstractCommand child=null;
		ElementMainStart mainStart=null;
		ElementSubroutine subroutine=null, onErrorSubroutine=null;
		
		for(int i=0; i<children.size(); i++){
			child=children.get(i);
			if(child.getCommandName().equals("MAIN_START")){
				mainStart=(ElementMainStart) child;
				break;
			}
		}
		
		if(mainStart!=null){
			children=mainStart.getChildElementList();
			for(int i=0; i<children.size(); i++){
				child=children.get(i);
				if(child.getCommandName().equals("SUBROUTINE") && ((ElementSubroutine)child).getSubroutineName().equals("ON_ERROR")){
						onErrorSubroutine=subroutine;
						children.remove(i);
						break;
				}
			}
		}
		
		if(onErrorSubroutine!=null){
			ep.getChildElementList().add(onErrorSubroutine);
			
		}
		
		
	}


	private void writeMapTester(JavaClassGeneral javaTreeElement) {
	
		if(!ConversionLogModel.getInstance().getConversionFileType().equals(ConversionFileType.MAP)){
			return;
		}
		
		try {
			
			ConversionLogModel.getInstance().setConversionFileType(ConversionFileType.MAP_TESTER);
			javaTreeElement.writeJavaBaslat(ConversionLogModel.getInstance().getFullJavaFileName());
			ConversionLogModel.getInstance().setConversionFileType(ConversionFileType.MAP);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	private void controlIfThereIsUndefinedCommand(CommandList commandList) throws Exception {
		if (ConverterConfiguration.STOP_ENGINE_ON_PARSE_ERROR && !commandList.getUndefinedCommandSet().isEmpty()) {
			throw new Exception("Parse aşamasında Undefined Commandlar var.");
		}
	}

	private void resetEveryThingForNewConversion() {
		NaturalCommandList.reset();
		//JavaClassElement.javaHibernateCodeMap.clear();
		//JavaClassElement.javaDAOInterfaceCodeMap.clear();

	}

	private void alterMidLinesToSubLines(AbstractLexing lexer) {
		for (AbstractToken token : lexer.tokenListesi) {

			if (token.getTip().equals(TokenTipi.Kelime)) {
				token.setDeger(((String) token.getDeger()).replaceAll("-", "_"));
				logger.info(token.getDeger().toString());
			}
		}

	}


	public void operateConversionForCommonDAL() throws FileNotFoundException {
		
		writeDalCodes();
		
		writeDalHibernateCodes();
		
		endCloseTagGenDao();
		
	}

}
