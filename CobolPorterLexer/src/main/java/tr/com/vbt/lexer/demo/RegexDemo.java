package tr.com.vbt.lexer.demo;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RegexDemo {

	final static Logger logger = LoggerFactory.getLogger(RegexDemo.class);
	/**
	 * @param args
	 */
	public static void main(String[] args) {
			
			String ssn4="01 WSNUM4 PIC 9";
			
			if (ssn4.matches("^(\\d{2}+\\s?\\w+\\s?\\w+\\s+\\d)$")) {
				logger.info("Found good SSN: " + ssn4);
			}
		
	}

}
