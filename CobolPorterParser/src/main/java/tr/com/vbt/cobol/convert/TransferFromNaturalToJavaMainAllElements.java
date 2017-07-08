package tr.com.vbt.cobol.convert;

import java.io.FileNotFoundException;

import tr.com.vbt.lexer.ConversionFileType;
import tr.com.vbt.lexer.ConversionLogModel;
import tr.com.vbt.lexer.ConversionLogReport;
import tr.com.vbt.lexer.ModuleAndSchema;
import tr.com.vbt.util.ConversionMode;

public class TransferFromNaturalToJavaMainAllElements {

	

	/**
	 * @param args
	 * @throws FileNotFoundException
	 */
	// Type1: java Latif File HanStart
	// Type2: java Latif Folder

	// Latif WINDOWS MB Map Files IDGM0004
	public static void main(String[] args) throws FileNotFoundException {
		
		ConversionLogModel logModel = ConversionLogModel.getInstance();
		
		logModel.setUser(args[0]);
		logModel.setOPERATING_SYSTEM(args[1]);
		logModel.setCustomer(args[2]);
		logModel.setConversionFileType(ConversionFileType.PROGRAM);
		logModel.setConvertOperationType("Folder");
		logModel.setConversionMode(ConversionMode.ALL_SOURCE_CODES);
		
		ModuleAndSchema moduleAndSchema;
		
		for(int i=3; i<args.length;i++){
			
			logModel.getModuleList().add(new ModuleAndSchema(args[i],args[i+1]));
		
			i++;
		}
		TransferFromNaturalToJavaMain  fromNaturalToJavaMain;
		for(int i=0; i<logModel.getModuleList().size();i++){
		
			ConversionLogReport.getInstance().reset();
			
			logModel.setModule(logModel.getModuleList().get(i).getModule());
			logModel.setSchemaName(logModel.getModuleList().get(i).getSchema());
			
			fromNaturalToJavaMain=new  TransferFromNaturalToJavaMain();
			logModel.setConversionFileType(ConversionFileType.PROGRAM);
			fromNaturalToJavaMain.operateConversion();
			
			logModel.setConversionFileType(ConversionFileType.SUBPROGRAM);
			fromNaturalToJavaMain.operateConversion();
			
			logModel.setConversionFileType(ConversionFileType.MAP);
			fromNaturalToJavaMain.operateConversion();
	
			ConversionLogReport.getInstance().writeReport();
		}
		
	
	}
	
	
	

}
