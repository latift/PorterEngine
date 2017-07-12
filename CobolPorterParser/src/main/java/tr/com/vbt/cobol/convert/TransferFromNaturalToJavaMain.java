package tr.com.vbt.cobol.convert;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.ddm.DDMList;
import tr.com.vbt.java.database.IteratorNameManager;
import tr.com.vbt.java.general.JavaClassElement;
import tr.com.vbt.java.general.JavaClassGeneral;
import tr.com.vbt.java.util.RuleNotFoundException;
import tr.com.vbt.java.utils.ConvertUtilities;
import tr.com.vbt.lexer.AbstractLexing;
import tr.com.vbt.lexer.ConversionFileType;
import tr.com.vbt.lexer.ConversionLogModel;
import tr.com.vbt.lexer.ConversionLogReport;
import tr.com.vbt.lexer.NaturalLexing;
import tr.com.vbt.lexer.Phase;
import tr.com.vbt.natural.parser.general.ElementNaturalProgram;
import tr.com.vbt.patern.CommandList;
import tr.com.vbt.patern.NaturalCommandList;
import tr.com.vbt.patern.PaternManager;
import tr.com.vbt.patern.PaternManagerDataTypesNaturalImpl;
import tr.com.vbt.patern.PaternManagerMapNaturalImpl;
import tr.com.vbt.patern.PaternManagerNaturalImpl;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.TokenTipi;
import tr.com.vbt.util.ConverterConfiguration;
import tr.com.vbt.util.ConversionMode;
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
	public static void main(String[] args) throws FileNotFoundException {
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

		operateConversion();
		ConversionLogReport.getInstance().writeReport();
	}

	public static void operateConversion() throws FileNotFoundException {
		ConversionLogModel logModel = ConversionLogModel.getInstance();
		TransferFromNaturalToJavaMain transferDriver = null;

		ConverterConfiguration.customer = logModel.getCustomer();
		ConverterConfiguration.OPERATING_SYSTEM = logModel.getOPERATING_SYSTEM();
		logModel.setFolderPath(ConverterConfiguration.getFolderPath());
		logModel.setFolderMainPath(ConverterConfiguration.getMainFolderPath());
		createOutputFolders();

		if (logModel.getConvertOperationType().equals("Folder")) {

			File folder = null;
			if (ConversionLogModel.getInstance().isProgram()) {
				folder = new File(ConverterConfiguration.getFolderPath());
			} else if (ConversionLogModel.getInstance().isSubProgram()) {
				folder = new File(ConverterConfiguration.getSubFolderPath());
			} else if (ConversionLogModel.getInstance().isMap()) {
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
						MDC.put("customer", logModel.getCustomer());
						MDC.put("module", logModel.getModule());
						MDC.put("conversionFileType", logModel.getConversionFileType().toString());
						MDC.put("fileName", logModel.getFileName());
						logModel.setClientInteracting(true);
						transferDriver.driveTransfer(logModel);
						transferDriver.writeDalCodes(logModel);
						transferDriver.writeDalHibernateCodes(logModel);
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
					MDC.put("InputFileForConversion", logModel.getFileName());
					transferDriver.driveTransfer(logModel);
					transferDriver.writeDalCodes(logModel);
					transferDriver.writeDalHibernateCodes(logModel);
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

	// Module/seperatedPrograms/output/generatedhibernate/tr/com/thy/dal/$module/dal/hibernate/generated/
	// Module/seperatedPrograms/output/generatedinterface/tr/com/thy/dal/$module/dal/generated/
	private static void createOutputFolders() {
		// TODO Auto-generated method stub
		String FILENAME = ConversionLogModel.getInstance().getFolderMainPath() + "/"
				+ ConversionLogModel.getInstance().getModule() + "/SeperatedPrograms" + "/output";
		String FILENAMEMAP = ConversionLogModel.getInstance().getFolderMainPath() + "/"
				+ ConversionLogModel.getInstance().getModule() + "/SeperatedPrograms" + "/map" + "/output";
		String FILENAMESUBBPROGRAM = ConversionLogModel.getInstance().getFolderMainPath() + "/"
				+ ConversionLogModel.getInstance().getModule() + "/SeperatedPrograms" + "/subprogram" + "/output";
		File index = new File(FILENAME);
		File indexSubProgram = new File(FILENAMESUBBPROGRAM);
		File indexMap = new File(FILENAMEMAP);
		try {
			deleteFolder(index);
			deleteFolder(indexSubProgram);
			deleteFolder(indexMap);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		createOuputFolder(FILENAME, FILENAMEMAP, FILENAMESUBBPROGRAM);

	}

	private static void createOuputFolder(String fILENAME, String fILENAMEMAP, String fILENAMESUBBPROGRAM) {
		createFileInPath(fILENAME);
		createFileInPath(fILENAMEMAP);
		createFileInPath(fILENAMESUBBPROGRAM);
		createFileInPath(ConversionLogModel.getInstance().getFolderMainPath() + "/"	+ ConversionLogModel.getInstance().getModule() + "/SeperatedPrograms" + "/output"+ "/generatedhibernate");
		createFileInPath(ConversionLogModel.getInstance().getFolderMainPath() + "/"	+ ConversionLogModel.getInstance().getModule() + "/SeperatedPrograms" + "/output"+ "/generatedhibernate/tr");
		createFileInPath(ConversionLogModel.getInstance().getFolderMainPath() + "/"	+ ConversionLogModel.getInstance().getModule() + "/SeperatedPrograms" + "/output"+ "/generatedhibernate/tr/com");
		createFileInPath(ConversionLogModel.getInstance().getFolderMainPath() + "/"	+ ConversionLogModel.getInstance().getModule() + "/SeperatedPrograms" + "/output"+ "/generatedhibernate/tr/com/thy/");
		createFileInPath(ConversionLogModel.getInstance().getFolderMainPath() + "/"	+ ConversionLogModel.getInstance().getModule() + "/SeperatedPrograms" + "/output"+ "/generatedhibernate/tr/com/thy/dal/");
		createFileInPath(ConversionLogModel.getInstance().getFolderMainPath() + "/"	+ ConversionLogModel.getInstance().getModule() + "/SeperatedPrograms" + "/output"+ "/generatedhibernate/tr/com/thy/dal/"+ConversionLogModel.getInstance().getModule());
		createFileInPath(ConversionLogModel.getInstance().getFolderMainPath() + "/"	+ ConversionLogModel.getInstance().getModule() + "/SeperatedPrograms" + "/output"+ "/generatedhibernate/tr/com/thy/dal/"+ConversionLogModel.getInstance().getModule()+"/dal");
		createFileInPath(ConversionLogModel.getInstance().getFolderMainPath() + "/"	+ ConversionLogModel.getInstance().getModule() + "/SeperatedPrograms" + "/output"+ "/generatedhibernate/tr/com/thy/dal/"+ConversionLogModel.getInstance().getModule()+"/dal/hibernate");
		createFileInPath(ConversionLogModel.getInstance().getFolderMainPath() + "/"	+ ConversionLogModel.getInstance().getModule() + "/SeperatedPrograms" + "/output"+ "/generatedhibernate/tr/com/thy/dal/"+ConversionLogModel.getInstance().getModule()+"/dal/hibernate/generated");

		createFileInPath(ConversionLogModel.getInstance().getFolderMainPath() + "/"	+ ConversionLogModel.getInstance().getModule() + "/SeperatedPrograms" + "/output"+ "/generatedinterface");
		createFileInPath(ConversionLogModel.getInstance().getFolderMainPath() + "/"	+ ConversionLogModel.getInstance().getModule() + "/SeperatedPrograms" + "/output"+ "/generatedinterface/tr/");
		createFileInPath(ConversionLogModel.getInstance().getFolderMainPath() + "/"	+ ConversionLogModel.getInstance().getModule() + "/SeperatedPrograms" + "/output"+ "/generatedinterface/tr/com/");
		createFileInPath(ConversionLogModel.getInstance().getFolderMainPath() + "/"	+ ConversionLogModel.getInstance().getModule() + "/SeperatedPrograms" + "/output"+ "/generatedinterface/tr/com/thy/");
		createFileInPath(ConversionLogModel.getInstance().getFolderMainPath() + "/"	+ ConversionLogModel.getInstance().getModule() + "/SeperatedPrograms" + "/output"+ "/generatedinterface/tr/com/thy/dal/");
		createFileInPath(ConversionLogModel.getInstance().getFolderMainPath() + "/"	+ ConversionLogModel.getInstance().getModule() + "/SeperatedPrograms" + "/output"+ "/generatedinterface/tr/com/thy/dal/"+ConversionLogModel.getInstance().getModule());
		createFileInPath(ConversionLogModel.getInstance().getFolderMainPath() + "/"	+ ConversionLogModel.getInstance().getModule() + "/SeperatedPrograms" + "/output"+ "/generatedinterface/tr/com/thy/dal/"+ConversionLogModel.getInstance().getModule()+"/dal");
		createFileInPath(ConversionLogModel.getInstance().getFolderMainPath() + "/"	+ ConversionLogModel.getInstance().getModule() + "/SeperatedPrograms" + "/output"+ "/generatedinterface/tr/com/thy/dal/"+ConversionLogModel.getInstance().getModule()+"/dal/generated");

		String FILENAMESUBBPROGRAM = ConversionLogModel.getInstance().getFolderMainPath() + "/"
				+ ConversionLogModel.getInstance().getModule() + "/SeperatedPrograms" + "/output"
						+ "/generatedhibernate/tr/com/thy/dal/";


	}

	private static void createFileInPath(String str) {
		File file= new File(str);

		boolean result = false;
		try {
			file.mkdir();
			result = true;
		} catch (SecurityException se) {
			System.out.println("dosya yaratma izni yok!!!");
		}
	}

	public static void deleteFolder(File file) throws IOException {

		if (file.isDirectory()) {

			// directory is empty, then delete it
			if (file.list().length == 0) {

				file.delete();
				System.out.println("Directory is deleted : " + file.getAbsolutePath());

			} else {

				// list all the directory contents
				String files[] = file.list();

				for (String temp : files) {
					// construct the file structure
					File fileDelete = new File(file, temp);

					// recursive delete
					deleteFolder(fileDelete);
				}

				// check the directory again, if empty then delete it
				if (file.list().length == 0) {
					file.delete();
					System.out.println("Directory is deleted : " + file.getAbsolutePath());
				}
			}

		} else {
			// if file, then delete it
			file.delete();
			System.out.println("File is deleted : " + file.getAbsolutePath());
		}
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

	private void writeDalHibernateCodes(ConversionLogModel logModel) {

		Set<String> keys = JavaClassElement.javaHibernateCodeMap.keySet();

		String pojoName, findByMethodSignature;

		Set<String> tables = new HashSet<String>();
		for (String key : keys) {
			System.out.println(key);
			pojoName = key.substring(0, key.indexOf(" "));
			findByMethodSignature = JavaClassElement.javaHibernateCodeMap.get(key);

			if (!tables.contains(pojoName)) { // Bu pojoyu İlk kez yazacaksa
				StringBuffer interfaceHeader = ConvertUtilities.writeDAOImplemantasyonClassHeader(pojoName);
				try {
					WriteToFile.writeHeaderToFile(interfaceHeader, logModel.getFullJavaHibernateFileName(pojoName));
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
				WriteToFile.appendToFile(findByMethodSignature, logModel.getFullJavaHibernateFileName(pojoName));
			} catch (IOException e) {
				logger.warn("DAL Interface  Write For File" + pojoName + " " + findByMethodSignature + " WITH ERROR+");
				logger.warn("", e);
				logger.warn("**********************************************************************");
				logger.warn("****************************ERROR END***********************************");
				logger.warn("**********************************************************************");
			}
			endCloseTagGenHibernate(pojoName);

		}

	}

	private void writeDalCodes(ConversionLogModel logModel) throws FileNotFoundException {

		Set<String> keys = JavaClassElement.javaDAOInterfaceCodeMap.keySet();

		String pojoName = null, findByMethodSignature;

		Set<String> tables = new HashSet<String>();
		for (String key : keys) {
			System.out.println(key);
			pojoName = key.substring(0, key.indexOf(" "));
			findByMethodSignature = JavaClassElement.javaDAOInterfaceCodeMap.get(key);

			if (!tables.contains(pojoName)) { // Bu pojoyu İlk kez yazacaksa
				StringBuffer interfaceHeader = ConvertUtilities.writeInterfaceHeader(pojoName);
				try {
					WriteToFile.writeHeaderToFile(interfaceHeader, logModel.getFullJavaDAOInterfaceFileName(pojoName));
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
				WriteToFile.appendToFile(findByMethodSignature, logModel.getFullJavaDAOInterfaceFileName(pojoName));
			} catch (IOException e) {
				logger.warn("DAL Interface  Write For File" + pojoName + " " + findByMethodSignature + " WITH ERROR+");
				logger.warn("", e);
				logger.warn("**********************************************************************");
				logger.warn("****************************ERROR END***********************************");
				logger.warn("**********************************************************************");
			}

			endCloseTagGenDao(pojoName);
		}

	}

	private void endCloseTagGenDao(String pojoName) {
		// TODO Auto-generated method stub
		try {
			WriteToFile.appendToFile("}", ConversionLogModel.getInstance().getFullJavaDAOInterfaceFileName(pojoName));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void endCloseTagGenHibernate(String pojoName) {
		// TODO Auto-generated method stub
		try {
			WriteToFile.appendToFile("}", ConversionLogModel.getInstance().getFullJavaHibernateFileName(pojoName));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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

				commandList.replaceGlobalVariables();

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

		}

	}

	private void controlIfThereIsUndefinedCommand(CommandList commandList) throws Exception {
		if (ConverterConfiguration.STOP_ENGINE_ON_PARSE_ERROR && !commandList.getUndefinedCommandSet().isEmpty()) {
			throw new Exception("Parse aşamasında Undefined Commandlar var.");
		}
	}

	private void resetEveryThingForNewConversion() {
		NaturalCommandList.reset();
		JavaClassElement.javaHibernateCodeMap.clear();
		JavaClassElement.javaDAOInterfaceCodeMap.clear();

	}

	private void alterMidLinesToSubLines(AbstractLexing lexer) {
		for (AbstractToken token : lexer.tokenListesi) {

			if (token.getTip().equals(TokenTipi.Kelime)) {
				token.setDeger(((String) token.getDeger()).replaceAll("-", "_"));
				logger.info(token.getDeger().toString());
			}
		}

	}

}
