package tr.com.vbt.cobol.convert;

import java.io.File;
import java.io.FileNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.java.general.JavaClassElement;
import tr.com.vbt.java.general.JavaClassGeneral;
import tr.com.vbt.java.util.RuleNotFoundException;
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

public class TransferFromNaturalToJavaMap {

	final static Logger logger = LoggerFactory.getLogger(TransferFromNaturalToJavaMap.class);

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

		ConversionLogModel logModel = ConversionLogModel.getInstance();
		String convertOperationType;
		TransferFromNaturalToJavaMap transferDriver = null;

		logModel.setUser(args[0]);
		logModel.setOPERATING_SYSTEM(args[1]);
		logModel.setCustomer(args[2]);
		logModel.setModule(args[3]);
		logModel.setSchemaName(args[4]);
		logModel.setIsProgramOrMap(args[5]);
		convertOperationType = args[6];
		ConverterConfiguration.customer = logModel.getCustomer();
		ConverterConfiguration.OPERATING_SYSTEM = logModel.getOPERATING_SYSTEM();
		logModel.setFolderPathMap(ConverterConfiguration.getFolderPathMap());
		logModel.setFolderPath(ConverterConfiguration.getFolderPath());

		if (convertOperationType.equals("File")) {
			logModel.setFileName(args[7]);

			try {

				logger.info("**********************************************************************");
				logger.info("*****************************START**************************************");
				logger.info("**********************************************************************");
				logger.info("Conversion Started For File" + args[2]);
				ConverterConfiguration.className = args[2];
				transferDriver = new TransferFromNaturalToJavaMap();
				MDC.put("InputFileForConversion", logModel.getFileName());
				transferDriver.driveTransfer(logModel); 
				logger.info("Conversion Ended For File" + args[2]);
				logger.info("**********************************************************************");
				logger.info("******************************END***************************************");
				logger.info("**********************************************************************");
			} catch (Exception e) {
				logger.info("Conversion Aborted For File" + args[2] + " WITH ERROR+");
				logger.debug("", e);
				logger.info("**********************************************************************");
				logger.info("****************************ERROR END***********************************");
				logger.info("**********************************************************************");

			}
		} else if (convertOperationType.equals("Folder")) {

			File folder = new File(ConverterConfiguration.getFolderPathMap());
			File[] listOfFiles = folder.listFiles();

			for (int i = 0; i < listOfFiles.length; i++) {
				if (listOfFiles[i].isFile()) {
					
					if(listOfFiles[i].getName().equals(".gitignore")){
						continue;
					}
					ConverterConfiguration.className = listOfFiles[i].getName().replaceAll(".txt", "");
					logModel.setFileName(ConverterConfiguration.className);
					
					if (listOfFiles[i].isDirectory()){
						continue;
					}
					
					try {
						logger.info("**********************************************************************");
						logger.info("*****************************START**************************************");
						logger.info("**********************************************************************");
						ConverterConfiguration.className = listOfFiles[i].getName();
						logger.info("Conversion Started For File" + listOfFiles[i].getName());
						transferDriver = new TransferFromNaturalToJavaMap();
						MDC.put("InputFileForConversion", logModel.getFileName());
						transferDriver.driveTransfer(logModel);
						logger.info("Conversion Ended For File" + listOfFiles[i].getName());
						logger.info("**********************************************************************");
						logger.info("******************************END***************************************");
						logger.info("**********************************************************************");
					} catch (Exception e) {
						logger.info("Conversion Aborted For File" + listOfFiles[i].getName() + " WITH ERROR+");
						logger.debug("", e);
						logger.info("**********************************************************************");
						logger.info("****************************ERROR END***********************************");
						logger.info("**********************************************************************");
					}

				}
			}
		} else if (convertOperationType.equals("Files")) {
			for (int i = 7; i < args.length; i++) {
				try {
					logger.info("**********************************************************************");
					logger.info("*****************************START**************************************");
					logger.info("**********************************************************************");
					logger.info("Conversion Started For File " + args[i]);
					ConverterConfiguration.className = args[i];
					logModel.setFileName(args[i]);
					transferDriver = new TransferFromNaturalToJavaMap();
					MDC.put("InputFileForConversion", logModel.getFileName());
					transferDriver.driveTransfer(logModel);
					logger.info("Conversion Ended For File" + args[i]);
					logger.info("**********************************************************************");
					logger.info("******************************END***************************************");
					logger.info("**********************************************************************");
				} catch (Exception e) {
					logger.debug("Conversion Aborted For File" + args[i] + " WITH ERROR", e);
					logger.info("**********************************************************************");
					logger.info("****************************ERROR END***********************************");
					logger.info("**********************************************************************");

				}
			}
		}
	

	}

	private void driveTransfer(ConversionLogModel logModel) throws Exception {

		StringBuilder sb = null;

		logModel.setPhase(Phase.START);
		MDC.put("phase", logModel.getPhase().toString());
		conversionLogger.logConversion(logModel);

		AbstractLexing lexer = new NaturalLexing();
		CommandList commandList;
		ElementNaturalProgram ep;
		JavaClassGeneral javaTreeElement = null;

		resetEveryThingForNewConversion();

		// ********************************************************************************************
		{

			// 1 Tokenize Source File
			lexer.tokenizeSourceFile(logModel.getFullNaturalInputFileName(), logModel.getModule());

			// 1.1 Alter Midlines
			// TODO: open below code
			// alterMidLinesToSubLines(lexer);

			// 1.2 Export Tokenized Source File
			sb = lexer.exportLexedData(logModel.getFullTokenizeFileName()); // lex
																			// uzantılı
																			// dosyayı
																			// yaz.

			logModel.setPhase(Phase.TOKENIZE);
			MDC.put("phase", logModel.getPhase().toString());
			logModel.setOutput(sb);
			conversionLogger.logConversion(logModel);

		}

		// ********************************************************************************************
		{

			// 2 Parse tokens To Command List
			// 2.1 Parse Data Type Definition tokens( END_DEFINE oncesi) To
			// Command List
			commandList = NaturalCommandList.getInstance(lexer.tokenListesi,lexer);

			commandList.replaceGlobalVariables();

			try {
				commandList.setPaternManager(paternManagerDataType);
				commandList.parseDataTypeTokensToCommandsList();
			} catch (Exception e1) {
				e1.printStackTrace();
				return;
			}

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

			logModel.setPhase(Phase.PARSE_TO_COMMANDS);
			MDC.put("phase", logModel.getPhase().toString());
			logModel.setOutput(sb);
			conversionLogger.logConversion(logModel);

			try {
				commandList.generateDatatypeMap();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.debug("", e);
			}

			sb = commandList.writeCommands(logModel.getFullCommandFileName(),
					logModel.getFullUndefinedCommandFileName());
		}

		// ********************************************************************************************

		// ********************************************************************************************
		{
			// 3 Command Dizisini Abstract Cobol Ağacına Çevir.
			ep = new ElementNaturalProgram(commandList.getCommandList());
			ep.parseBaslat();

			// 3.1 Abstract Cobol Ağacını Yazdır.
			sb = ep.exportBaslat(logModel.getFullSourceTreeFileName());

			logModel.setPhase(Phase.PARSE_TO_SOURCE_TREE);
			MDC.put("phase", logModel.getPhase().toString());
			logModel.setOutput(sb);
			conversionLogger.logConversion(logModel);

		}
		// ********************************************************************************************

		// ********************************************************************************************
		{
			// 4 Natural Ağacından Java ağacını oluştur.

			try {
				// Program çevrimi başlamadan önce önceki çevrimi
				// resetlemeliyiz.
				JavaClassGeneral.resetInstance();
				if (ep.startGenerateTree()) {
					javaTreeElement = (JavaClassGeneral) JavaClassGeneral.getInstance(); // Oluşan
																							// java
																							// sınıfını
																							// dön.
					// 4.1 Soyut Java Ağacını XML dosyaya yaz.
					sb = javaTreeElement.exportJavaTreeBaslat(logModel.getFullJavaTreeFileName());

					logModel.setPhase(Phase.CONVERT_COBOL_TREE_TO_JAVA_TREE);
					MDC.put("phase", logModel.getPhase().toString());
					logModel.setOutput(sb);
					conversionLogger.logConversion(logModel);
				}
			} catch (RuleNotFoundException e) {
				logger.info("Rule Not Found For:" + e.getCobolDetailedName());
				logger.debug("", e);
			}

		}
		// ********************************************************************************************

		// ********************************************************************************************
		{
			// 5 JAva Ağacından Java kodunu oluştur.
			javaTreeElement.resetSourceCode();
			sb = javaTreeElement.writeJavaBaslat(logModel.getFullJavaFileName());

			logModel.setPhase(Phase.WRITE_JAVA_CODE);
			MDC.put("phase", logModel.getPhase().toString());
			logModel.setOutput(sb);
			conversionLogger.logConversion(logModel);

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
