package tr.com.vbt.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShowProperties {

	final static Logger logger = LoggerFactory.getLogger(ShowProperties.class);
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		
		 System.getProperties().list(System.out);
		 logger.info(System.getProperty("os.name"));

	}

}
