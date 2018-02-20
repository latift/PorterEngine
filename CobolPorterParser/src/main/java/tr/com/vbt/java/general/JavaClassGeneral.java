package tr.com.vbt.java.general;

import java.io.IOException;

import org.apache.log4j.Logger;


import tr.com.vbt.java.AbstractJava;
import tr.com.vbt.java.AbstractJavaElement;
import tr.com.vbt.java.utils.FCU;
import tr.com.vbt.lexer.ConversionLogModel;
import tr.com.vbt.util.ConverterConfiguration;
import tr.com.vbt.util.WriteToFile;


public class JavaClassGeneral extends AbstractJavaElement{
	
	final static Logger logger = Logger.getLogger(JavaClassGeneral.class);
	
	private static JavaClassGeneral instance = null;
	
	ConversionLogModel logModel;

	private JavaClassGeneral() {
		super();
		
	}

	public static AbstractJavaElement getInstance(){
		if(instance == null) {
	         instance = new JavaClassGeneral();
	      }
	      return instance;
	}
	
	public static void resetInstance(){
		instance=null;
	}

	@Override
	public boolean writeJavaToStream() throws Exception{ 
		super.writeJavaToStream();
		logModel=ConversionLogModel.getInstance();
		
		String module=ConversionLogModel.getInstance().getModule().replaceAll("/SeperatedPrograms", "").toLowerCase();;
	if(ConverterConfiguration.addGenerationInfoToJavaClass){
		/**
		 *  Bu kod kod generator tarafından çevrilmiştir.
		 *  Engine Version:
		 *  Generation Date:
		 *  User: latif
		 *  OS: Windows
		 *  Customer: MB
		 *  
		 *  		logModel.setUser(args[0]);
	logModel.setOPERATING_SYSTEM(args[1]);
	logModel.setCustomer(args[2]);
	logModel.setIsProgramOrMap(args[3]);
		 */
		
		
		
		AbstractJava.javaCodeBuffer.append("/**"+JavaConstants.NEW_LINE);
		AbstractJava.javaCodeBuffer.append("* "+JavaConstants.CLASS_GENERATION_INFO+JavaConstants.NEW_LINE);
		AbstractJava.javaCodeBuffer.append("* Engine Version: "+ConverterConfiguration.ENGINE_VERSION+JavaConstants.NEW_LINE);
		AbstractJava.javaCodeBuffer.append("* Generation Date: "+FCU.getCurrentDateAndTime()+JavaConstants.NEW_LINE);
		AbstractJava.javaCodeBuffer.append("* Conversion User: "+logModel.getUser()+JavaConstants.NEW_LINE);
		AbstractJava.javaCodeBuffer.append("* OS: "+logModel.getOPERATING_SYSTEM()+JavaConstants.NEW_LINE);
		AbstractJava.javaCodeBuffer.append("* Customer: "+logModel.getCustomer()+JavaConstants.NEW_LINE);
		AbstractJava.javaCodeBuffer.append("*/"+JavaConstants.NEW_LINE);
		AbstractJava.javaCodeBuffer.append(JavaConstants.NEW_LINE );
	}
	
		String customer=ConversionLogModel.getInstance().getCustomer().toLowerCase();
		if(logModel.isMap()){
			AbstractJava.javaCodeBuffer.append(JavaConstants.PACKAGE+ " "+ "tr.com."+customer+"."+module+".web.map"+ " "+JavaConstants.DOT_WITH_COMMA+JavaConstants.NEW_LINE );
		}else if(logModel.isMapTester()){
			AbstractJava.javaCodeBuffer.append(JavaConstants.PACKAGE+ " "+ "tr.com."+customer+"."+module+".web.maptester"+ " "+JavaConstants.DOT_WITH_COMMA+JavaConstants.NEW_LINE );
		}else if(logModel.isSubProgram()){
			AbstractJava.javaCodeBuffer.append(JavaConstants.PACKAGE+ " "+ "tr.com."+customer+"."+module+".web.subprogram"+ " "+JavaConstants.DOT_WITH_COMMA+JavaConstants.NEW_LINE );
		}else{
			AbstractJava.javaCodeBuffer.append(JavaConstants.PACKAGE+ " "+ "tr.com."+customer+"."+module+".web"+ " "+JavaConstants.DOT_WITH_COMMA+JavaConstants.NEW_LINE );
		}
		return this.writeChildrenJavaToStream();
	}

	
	
	public StringBuilder writeJavaBaslat(String fullJavaFilePath) throws Exception {
		logger.info("Start of Java Export");
		try {
			JavaClassGeneral.javaCodeBuffer= new StringBuilder(); //Her çağrıldığında önce buffer bir boşaltılmalı.
			this.writeJavaToStream();
			WriteToFile.writeToFile(JavaClassGeneral.javaCodeBuffer,fullJavaFilePath);
		} catch (IOException e) {
			logger.info(e.getMessage(),e);
		}
		logger.info("End of Java Export");
		return JavaClassGeneral.javaCodeBuffer;
		
	}
	

	
	public StringBuilder exportJavaTreeBaslat(String fullJavaTreePath) {
		StringBuilder sb=this.exportJavaTree();
		logger.info("Start of Java Tree Export");
		try {
			WriteToFile.writeToFile(sb,fullJavaTreePath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("End of Java Tree Export");
		return sb;
	}
	
	
	
	

}
