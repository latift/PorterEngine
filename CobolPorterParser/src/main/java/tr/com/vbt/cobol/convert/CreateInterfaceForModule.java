package tr.com.vbt.cobol.convert;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tr.com.vbt.java.general.JavaClassGeneral;
import tr.com.vbt.lexer.ConversionLogModel;
import tr.com.vbt.util.ConverterConfiguration;
import tr.com.vbt.util.WriteToFile;

public class CreateInterfaceForModule {

	final static Logger logger = LoggerFactory.getLogger(CreateInterfaceForModule.class);
	
	final static String NEW_LINE="\n";
	
	private static StringBuilder interfaceString;
	
	ConversionLogModel logModel;
	

	public static void main(String[] args){
		ConversionLogModel logModel = ConversionLogModel.getInstance();
		String convertOperationType;
		TransferFromNaturalToJavaMain transferDriver = null;

		logModel.setUser(args[0]);
		logModel.setOPERATING_SYSTEM(args[1]);
		logModel.setCustomer(args[2]);
		logModel.setModule(args[3]);
		logModel.setSchemaName(args[4]);
		logModel.setIsProgramOrMap(args[5]);
		convertOperationType = args[6];
		ConverterConfiguration.customer = logModel.getCustomer();
		ConverterConfiguration.OPERATING_SYSTEM = logModel.getOPERATING_SYSTEM();
		logModel.setFolderPath(ConverterConfiguration.getFolderPath());
		
		CreateInterfaceForModule createInterfaceForModule=new CreateInterfaceForModule();
		
		createInterfaceForModule.setLogModel(logModel);

		if (convertOperationType.equals("Folder")) {

			File folder = new File(ConverterConfiguration.getFolderPath());
			File[] listOfFiles = folder.listFiles();
			
			for (int i = 0; i < listOfFiles.length; i++) {
				
				try {
					if(listOfFiles[i].getName().equals(".gitignore")){
						continue;
					}
					
					ConverterConfiguration.className = listOfFiles[i].getName().replaceAll(".txt", "");
					logModel.setFileName(ConverterConfiguration.className);
					
					if (listOfFiles[i].isFile()) {
					
						createInterfaceForModule.setInterfaceString();
						
						createInterfaceForModule.writeInterfaceToFile();
						
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
	}
	

	public StringBuilder writeInterfaceToFile() throws Exception {
		logger.info("Start of Java Export");
		try {
			WriteToFile.writeToFile(interfaceString,getFullInterfaceFileName());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("End of Java Export");
		return JavaClassGeneral.javaCodeBuffer;
		
	}



	private void setInterfaceString() {
		
		 interfaceString=new StringBuilder();
		//package tr.com.thy.meucretl.web.interfaces;
		 interfaceString.append("package tr.com.thy."+logModel.getModule().toLowerCase()+".web.interfaces;"+NEW_LINE);

		//import tr.com.vbt.natural.core.NaturalProgram;
		 interfaceString.append("import tr.com.vbt.natural.core.NaturalProgram;"+NEW_LINE);

		//public interface UCRET extends NaturalProgram{
		 interfaceString.append("public interface "+logModel.getFileName()+" extends NaturalProgram{"+NEW_LINE);

		//}
		 interfaceString.append("}"+NEW_LINE);
		
	}



	public String getFullInterfaceFileName() {
		String folderPath=ConverterConfiguration.getFolderPath();
		return folderPath+"/output/interface/"+this.logModel.getFileName()+".java";
	}



	public ConversionLogModel getLogModel() {
		return logModel;
	}



	public void setLogModel(ConversionLogModel logModel) {
		this.logModel = logModel;
	}
	
	
}
