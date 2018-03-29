package tr.com.vbt.java.general;

import java.io.IOException;

import org.apache.log4j.Logger;


import tr.com.vbt.java.AbstractJava;
import tr.com.vbt.java.AbstractJavaElement;
import tr.com.vbt.java.utils.FCU;
import tr.com.vbt.lexer.ConversionLogModel;
import tr.com.vbt.util.ConverterConfiguration;
import tr.com.vbt.util.WriteToFile;


public class JavaIdentificationDivision extends AbstractJavaElement{
	
	final static Logger logger = Logger.getLogger(JavaIdentificationDivision.class);
	
	ConversionLogModel logModel;

	public JavaIdentificationDivision() {
		super();
		
	}


	@Override
	public boolean writeJavaToStream() throws Exception{ 
		super.writeJavaToStream();

		return this.writeChildrenJavaToStream();
	}

	
	
	public StringBuilder writeJavaBaslat(String fullJavaFilePath) throws Exception {
		logger.info("Start of Java Export");
		try {
			JavaIdentificationDivision.javaCodeBuffer= new StringBuilder(); //Her çağrıldığında önce buffer bir boşaltılmalı.
			this.writeJavaToStream();
			WriteToFile.writeToFile(JavaIdentificationDivision.javaCodeBuffer,fullJavaFilePath);
		} catch (IOException e) {
			logger.info(e.getMessage(),e);
		}
		logger.info("End of Java Export");
		return JavaIdentificationDivision.javaCodeBuffer;
		
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
