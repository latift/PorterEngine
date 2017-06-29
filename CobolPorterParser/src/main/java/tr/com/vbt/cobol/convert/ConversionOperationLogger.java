package tr.com.vbt.cobol.convert;

import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tr.com.vbt.lexer.ConversionLogModel;

public class ConversionOperationLogger {
	
	final static Logger logger = LoggerFactory.getLogger(ConversionOperationLogger.class);
	
	public void logConversion(ConversionLogModel logModel){
		logModel.setDate(Calendar.getInstance().getTime());
		logger.info("***************************************************************\n");
		System.out.println(logModel.toString());
		logger.info("");
	}

}
