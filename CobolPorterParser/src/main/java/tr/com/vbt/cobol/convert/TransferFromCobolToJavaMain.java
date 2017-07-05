package tr.com.vbt.cobol.convert;

import java.io.FileNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.division.ElementProgram;
import tr.com.vbt.java.general.JavaClassGeneral;
import tr.com.vbt.java.util.RuleNotFoundException;
import tr.com.vbt.lexer.AbstractLexing;
import tr.com.vbt.lexer.CobolLexing;
import tr.com.vbt.lexer.ConversionLogModel;
import tr.com.vbt.lexer.Phase;
import tr.com.vbt.patern.CobolCommandList;
import tr.com.vbt.patern.CommandList;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.TokenTipi;
import tr.com.vbt.util.ConverterConfiguration;

public class TransferFromCobolToJavaMain {
	
	final static Logger logger = LoggerFactory.getLogger(TransferFromCobolToJavaMain.class);
	
	ConversionOperationLogger conversionLogger=new ConversionOperationLogger();
	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args){
		
		ConversionLogModel logModel=ConversionLogModel.getInstance();
		
		logModel.setUser(args[0]);
		logModel.setFileName(args[1]);
		logModel.setFolderPath(ConverterConfiguration.getFolderPath());
		
		TransferFromCobolToJavaMain transferDriver=new TransferFromCobolToJavaMain(); 
		
		try {
			transferDriver.driveTransfer(logModel);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	

	private void driveTransfer(ConversionLogModel logModel) throws Exception {
		
		StringBuilder sb = null;
		
		logModel.setPhase(Phase.START);
		conversionLogger.logConversion(logModel);
		MDC.put("phase", logModel.getPhase().toString());
		
		AbstractLexing lexer =new CobolLexing();
		CommandList commandList;
		ElementProgram ep;
		JavaClassGeneral javaTreeElement = null;
		
		//********************************************************************************************
		{
			
			//1 Tokenize Source File
			lexer.tokenizeSourceFile(logModel.getFullInputFileName(), logModel.getModule(),logModel.getCustomer());
			
			//1.1 Alter Midlines
			//TODO: open below code
			//alterMidLinesToSubLines(lexer);

			//1.2 Export Tokenized Source File
			sb=lexer.exportLexedData(logModel.getFullTokenizeFileName()); // lex uzantılı dosyayı yaz.
			
			logModel.setPhase(Phase.TOKENIZE);
			MDC.put("phase", logModel.getPhase().toString());
			logModel.setOutput(sb);
			conversionLogger.logConversion(logModel);
			
		}
		
		//********************************************************************************************
		{
			//2 Parse tokens To Command List
			 commandList=new CobolCommandList(lexer.tokenListesi);
			try {
				commandList.parseDataTypeTokensToCommandsList();
			} catch (Exception e1) {
				e1.printStackTrace();
				return ;
			}
			
			sb=commandList.writeCommands(logModel.getFullCommandFileName(), logModel.getFullUndefinedCommandFileName());
			
			commandList.findAndSetDataTypeDefinitions();
			
			commandList.addVirtualEndings();
			
			sb=commandList.writeCommands(logModel.getFullCommandFileName());
			
			//2.1 Find And Set Endings
			commandList.findAndSetEndingCommands();
			
			//2.2 Write Commands
			sb=commandList.writeCommands(logModel.getFullCommandFileNamewithEndings());
			
			//2.3 Tüm komutlara command Listesini set Et
			for (AbstractCommand command: commandList.getCommandList()) {
				command.setCommandListesi(commandList.getCommandList());
			}
			
			logModel.setPhase(Phase.PARSE_TO_COMMANDS);
			MDC.put("phase", logModel.getPhase().toString());
			logModel.setOutput(sb);
			conversionLogger.logConversion(logModel);
			
		}
		//********************************************************************************************
				
		
		
		//********************************************************************************************
		{
			//3 Token Dizisini Abstract Cobol Ağacına Çevir.
			ep=new ElementProgram(commandList.getCommandList());
			ep.parseBaslat();
			
			//3.1 Abstract Cobol Ağacını Yazdır.
			sb=ep.exportBaslat(logModel.getFullSourceTreeFileName());
			
			logModel.setPhase(Phase.PARSE_TO_SOURCE_TREE);
			MDC.put("phase", logModel.getPhase().toString());
			logModel.setOutput(sb);
			conversionLogger.logConversion(logModel);
		
		}
		//********************************************************************************************
		
		
		//********************************************************************************************
		{
			//4 Cobol Ağacından Java ağacını oluştur.
			
			try {
				if( ep.startGenerateTree()){
					javaTreeElement=(JavaClassGeneral)JavaClassGeneral.getInstance(); //Oluşan java sınıfını dön.
					//4.1 Soyut Java Ağacını XML dosyaya yaz.
					sb=javaTreeElement.exportJavaTreeBaslat(logModel.getFullJavaTreeFileName());
					
					logModel.setPhase(Phase.CONVERT_COBOL_TREE_TO_JAVA_TREE);
					MDC.put("phase", logModel.getPhase().toString());
					logModel.setOutput(sb);
					conversionLogger.logConversion(logModel);
				}
			} catch (RuleNotFoundException e) {
				logger.info("Rule Not Found For:"+e.getCobolDetailedName());
				 logger.debug("",e);
				
			}
			
			
		}
		//********************************************************************************************
		
		
		//********************************************************************************************
				{
					//5 JAva Ağacından Java kodunu oluştur.
					sb=javaTreeElement.writeJavaBaslat(logModel.getFullJavaFileName());
							
					logModel.setPhase(Phase.WRITE_JAVA_CODE);
					MDC.put("phase", logModel.getPhase().toString());
					logModel.setOutput(sb);
					conversionLogger.logConversion(logModel);
					
				}
				//********************************************************************************************
		
		
	}

	private void alterMidLinesToSubLines(AbstractLexing lexer) {
		for (AbstractToken token : lexer.tokenListesi) {
			
			if(token.getTip().equals(TokenTipi.Kelime)){
				token.setDeger(((String)token.getDeger()).replaceAll("-", "_"));
				System.out.println(token.getDeger());
				System.out.println();
			}
		}
		
	}

	

	

}
