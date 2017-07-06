package tr.com.vbt.cobol.convert;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.java.database.IteratorNameManager;
import tr.com.vbt.java.general.JavaClassElement;
import tr.com.vbt.java.general.JavaClassGeneral;
import tr.com.vbt.java.util.RuleNotFoundException;
import tr.com.vbt.java.utils.ConvertUtilities;
import tr.com.vbt.lexer.AbstractLexing;
import tr.com.vbt.lexer.ConversionLogModel;
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
import tr.com.vbt.util.WriteToFile;

public class TransferFromNaturalSubProgramsToJava {

	final static Logger logger = LoggerFactory.getLogger(TransferFromNaturalSubProgramsToJava.class);

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
		operateConversionSubPrograms(args);
	}
	
	
	public static void operateConversionSubPrograms(String[] args) throws FileNotFoundException {

		ConversionLogModel logModel = ConversionLogModel.getInstance();
		String convertOperationType;
		TransferFromNaturalSubProgramsToJava transferDriver = null;

		logModel.setUser(args[0]);
		logModel.setOPERATING_SYSTEM(args[1]);
		logModel.setCustomer(args[2]);
		logModel.setModule(args[3]);
		logModel.setSchemaName(args[4]);
		logModel.setIsProgramOrMap(args[5]);
		logModel.setSubProgram(true);
		convertOperationType = args[6];
		ConverterConfiguration.customer = logModel.getCustomer();
		ConverterConfiguration.OPERATING_SYSTEM = logModel.getOPERATING_SYSTEM();
		logModel.setFolderPath(ConverterConfiguration.getSubFolderPath());
		logModel.setFolderMainPath(ConverterConfiguration.getMainFolderPath());

		if (convertOperationType.equals("Folder")) {

			File folder = new File(logModel.getFolderPath());
			File[] listOfFiles = folder.listFiles();

			for (int i = 0; i < listOfFiles.length; i++) {
				
				if(listOfFiles[i].getName().equals(".gitignore")){
					continue;
				}
				ConverterConfiguration.className = listOfFiles[i].getName().replaceAll(".txt", "");
				logModel.setFileName(ConverterConfiguration.className);
				
				if (listOfFiles[i].isFile()&& isJavaAlreadyGenerated(listOfFiles[i])) {
					logger.warn("Conversion Has Not Been Started For File " + logModel.getFileName()+" Java File is already exist!!!");
					continue;
				}
				
				if (listOfFiles[i].isFile()&& !isJavaAlreadyGenerated(listOfFiles[i])) {
					try {
						logger.warn("**********************************************************************");
						logger.warn("*****************************START**************************************");
						logger.warn("**********************************************************************");
						logger.warn("Conversion Statu: Started For File " + logModel.getFileName());
						transferDriver = new TransferFromNaturalSubProgramsToJava();
						MDC.put("InputFileForConversion", logModel.getFileName());
						logModel.setClientInteracting(false);
						transferDriver.driveTransfer(logModel);
						transferDriver.writeDalCodes(logModel);
						transferDriver.writeDalHibernateCodes(logModel);
						logger.warn("Conversion Statu: Ended For File " + logModel.getFileName());
						logger.warn("**********************************************************************");
						logger.warn("******************************END***************************************");
						logger.warn("**********************************************************************");
					} catch (Exception e) {
						logger.warn("Conversion Statu: Aborted For File " + logModel.getFileName() + " WITH ERROR+");
						logger.warn("", e);
						logger.warn("**********************************************************************");
						logger.warn("****************************ERROR END***********************************");
						logger.warn("**********************************************************************");
					}

				}
			}
		} else if (convertOperationType.equals("Files")) {
			for (int i = 7; i < args.length; i++) {
				try {
					ConverterConfiguration.className = args[i];
					logModel.setFileName(args[i]);
					
					logger.warn("**********************************************************************");
					logger.warn("*****************************START**************************************");
					logger.warn("**********************************************************************");
					logger.warn("Conversion Statu: Started For File " + logModel.getFileName());
					
					i++;
					if(args[i].equals("ClientInteracting")){
						logModel.setClientInteracting(true);
					}else if(args[i].equals("ClientNotInteracting")){
						logModel.setClientInteracting(false);
					}else{
						throw new RuntimeException("ClientInteracting parametresi set edilmelidir.");
					}
					
					transferDriver = new TransferFromNaturalSubProgramsToJava();
					MDC.put("InputFileForConversion", logModel.getFileName());
					transferDriver.driveTransfer(logModel);
					transferDriver.writeDalCodes(logModel);
					transferDriver.writeDalHibernateCodes(logModel);
					logger.warn("Conversion Statu: Ended For File " +logModel.getFileName());
					logger.warn("**********************************************************************");
					logger.warn("******************************END***************************************");
					logger.warn("**********************************************************************");
				} catch (Exception e) {
					logger.warn("Conversion Statu: Aborted For File " + logModel.getFileName() + " WITH ERROR", e);
					logger.warn("**********************************************************************");
					logger.warn("****************************ERROR END***********************************");
					logger.warn("**********************************************************************");

				}
			}
		}
	

	}

	private static boolean isJavaAlreadyGenerated(File file) {
		if(WriteToFile.fileIsThere(ConversionLogModel.getInstance().getFullJavaFileName())){
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

		}

	}

	private void writeDalCodes(ConversionLogModel logModel) throws FileNotFoundException {

		Set<String> keys = JavaClassElement.javaDAOInterfaceCodeMap.keySet();

		String pojoName, findByMethodSignature;

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
			lexer.tokenizeSourceFile(logModel.getFullNaturalInputFileName(), logModel.getModule(),logModel.getCustomer());

			// 1.1 Alter Midlines
			// TODO: open below code
			// alterMidLinesToSubLines(lexer);

			// 1.2 Export Tokenized Source File
			sb = lexer.exportLexedData(logModel.getFullTokenizeFileName()); // lex
																			// uzantılı
																			// dosyayı
																			// yaz.

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
				
				commandList.changeProgramDataTypeToDBDataType(); //ElementProgramGrupNatural --> ElementDBDataTypeNatural

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
				// TODO Auto-generated catch block
				e.printStackTrace();
				
				sb = commandList.writeCommands(logModel.getFullCommandFileName(),
						logModel.getFullUndefinedCommandFileName());
				throw e;
			}
		
			sb = commandList.writeCommands(logModel.getFullCommandFileName(),
						logModel.getFullUndefinedCommandFileName());
			
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

				
				logModel.setPhase(Phase.PARSE_TO_SOURCE_TREE);
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



		}

	}

	private void controlIfThereIsUndefinedCommand(CommandList commandList) throws Exception {
		if(ConverterConfiguration.STOP_ENGINE_ON_PARSE_ERROR &&!commandList.getUndefinedCommandSet().isEmpty()){
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
