package tr.com.vbt.util;

public class CustomStringUtils {

		public static String removeDot(String input){
			return input.replace(".", "");
		}
		
		public static String replaceMiddleLineWithSubLine(String input){
			return input.replace("-", "_");
		}
}
