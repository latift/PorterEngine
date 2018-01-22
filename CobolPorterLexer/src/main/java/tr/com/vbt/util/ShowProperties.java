package tr.com.vbt.util;

import org.apache.log4j.Logger;


public class ShowProperties {

	final static Logger logger = Logger.getLogger(ShowProperties.class);
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		
		 System.getProperties().list(System.out);
		 logger.info(System.getProperty("os.name"));

	}

}
