package tr.com.vbt.cobol.convert;

import java.io.FileNotFoundException;
import java.util.Date;

import org.apache.log4j.Logger;


import tr.com.vbt.java.database.SQLManager;
import tr.com.vbt.lexer.ConversionFileType;
import tr.com.vbt.lexer.ConversionLogModel;
import tr.com.vbt.lexer.ConversionLogReport;
import tr.com.vbt.lexer.ModuleAndSchema;
import tr.com.vbt.util.ConversionMode;
import tr.com.vbt.util.ConverterConfiguration;

public class TransferFromCobolToJavaMainAllElements {

	final static Logger logger = Logger.getLogger(TransferFromCobolToJavaMainAllElements.class);

	/**
	 * @param args
	 * @throws FileNotFoundException
	 */
	// Type1: java Latif File HanStart
	// Type2: java Latif Folder

	// Latif WINDOWS MB Map Files IDGM0004
	public static void main(String[] args) throws FileNotFoundException {
		
		ConversionLogModel logModel = ConversionLogModel.getInstance();
		
		ConverterConfiguration.sourceLan="COBOL";
		
		logModel.setUser(args[0]);
		logModel.setOPERATING_SYSTEM(args[1]);
		logModel.setCustomer(args[2]);
		logModel.setConversionFileType(ConversionFileType.PROGRAM);
		logModel.setConvertOperationType("Folder");
		logModel.setConversionMode(ConversionMode.ALL_SOURCE_CODES);
		
		ModuleAndSchema moduleAndSchema;
		
		Date conversionGeneralStartDate=new Date();
		for(int i=3; i<args.length;i++){
			
			logModel.getModuleList().add(new ModuleAndSchema(args[i],args[i+1]));
		
			i++;
		}
		
		
		ConverterConfiguration.customer = logModel.getCustomer();
		ConverterConfiguration.OPERATING_SYSTEM = logModel.getOPERATING_SYSTEM();
		logModel.setFolderMainPath(ConverterConfiguration.getMainFolderPath());
		
		TransferFromCobolToJavaMain  fromCobolToJavaMain = null;
		try {
			TransferFromCobolToJavaMain.reCreateOutputFoldersForDAL();
		} catch (Exception e1) {
			logger.debug(e1.getMessage(),e1);
			return;
		}
		for(int i=0; i<logModel.getModuleList().size();i++){
		
			logModel.setModule(logModel.getModuleList().get(i).getModule());
			logModel.setSchemaName(logModel.getModuleList().get(i).getSchema());
			
			ConversionLogReport.getInstance().reset();
			ConversionLogReport.getInstance().setAllConversionStartTime(conversionGeneralStartDate);
			
			fromCobolToJavaMain=new  TransferFromCobolToJavaMain();
			try {
				
				fromCobolToJavaMain.reCreateOutputFoldersForAModule();
				
				logModel.setConversionFileType(ConversionFileType.PROGRAM);
				fromCobolToJavaMain.operateConversionForAModule();
				
				
				logModel.setConversionFileType(ConversionFileType.SUBPROGRAM);
				fromCobolToJavaMain.operateConversionForAModule();
		
				//SQLManager.writeSQLStringToFile();
				
				logModel.setConversionFileType(ConversionFileType.MAP);
				fromCobolToJavaMain.operateConversionForAModule();
				
			} catch (Exception e) {
				logger.debug(e.getMessage(),e);
				return;
			}
			

	
			ConversionLogReport.getInstance().setModulConversionEndTime(new Date());
			if(i==logModel.getModuleList().size()-1){
				ConversionLogReport.getInstance().setAllConversionEndTime(new Date());
			}
			ConversionLogReport.getInstance().writeReport();
		}
		
		try {
			fromCobolToJavaMain.operateConversionForCommonDAL();
		} catch (Exception e) {
			logger.debug(e.getMessage(),e);
			return;
		}
		
		ConversionLogReport.getInstance().writeExceptionReport();
		
	
	}
	
	
	

}
