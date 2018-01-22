package tr.com.vbt.lexer.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;


	public class ExtractDemo {
		
		final static Logger logger = Logger.getLogger(ExtractDemo.class);
		public static void main(String[] args) {
			//String input = "I have a cat, but I like my dog better.";
			String input = "A 1 A";

			Pattern p = Pattern.compile("^(\\w{1})\\s{1}(\\d{1})\\s(\\w{1})$");
			Matcher m = p.matcher(input);

			List<String> animals = new ArrayList<String>();
			/*while (m.find()) {
				logger.info("Found a " + m.group() + ".");
				animals.add(m.group(1));
			}*/
			
			if (m.matches()) {
				  String day   = m.group(1);
				  int month = Integer.parseInt(m.group(2));
				  String year  = m.group(3);
				  logger.info(day);
				  logger.info("month");
				  logger.info(year);
				}
		}
	}
