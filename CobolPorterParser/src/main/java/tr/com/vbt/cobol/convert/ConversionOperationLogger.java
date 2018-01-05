package tr.com.vbt.cobol.convert;

import java.util.Calendar;

import org.apache.log4j.Logger;


import tr.com.vbt.lexer.ConversionLogModel;

public class ConversionOperationLogger {
	
	final static Logger logger = Logger.getLogger(ConversionOperationLogger.class);
	
	public void logConversion(ConversionLogModel logModel){
		logModel.setDate(Calendar.getInstance().getTime());
		logger.info("***************************************************************\n");
		System.out.println(logModel.toString());
		logger.info("");
	}

}
