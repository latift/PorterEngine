package tr.com.vbt.java.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SystemVariables {

	/*
	 * LANGUAGE --> S
	 * 
	 */
	private static String LANGUAGE="LANGUAGE";
	
	private static String DATE="DATE";
	
	private static String PF_KEY="PF_KEY";
	
	private static String TIMX="TIMX";
	
	
	public static String getSystemVariable(String label) {
		
		if(label.equals(LANGUAGE)){
			return getLanguage();
		}else if(label.equals(DATE)){
			return getDate();
		}else if(label.equals(TIMX)){
			return getDate();
		}
		return "";
				
	}


	private static String getLanguage() {
		return "TR";
	}


	private static String getDate() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = new Date();
		return dateFormat.format(date); //2013/10/15 16:16:39
	}
	

}
