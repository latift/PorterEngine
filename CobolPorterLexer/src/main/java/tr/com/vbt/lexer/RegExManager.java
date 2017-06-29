package tr.com.vbt.lexer;

import java.util.HashMap;

public class RegExManager {

	public static HashMap<String, String> hm;
	
	public static final String REGEX_GLOBAL_VARIABLE="^(\\w+)(\\s?)(\\w+)(\\s?)(\\w+)(\\s+)(\\w+)$";  //01 WS-NUM1 PIC 9
	public static final String REGEX_ADD_TO="^(\\d{2}+\\s?\\w+\\s?\\w+\\s+\\d)$";
	public static final  String REGEX_DISPLAY="^(\\d{2}+\\s?\\w+\\s?\\w+\\s+\\d)$";
	
	
	
	public RegExManager() {
		super();
		hm=new HashMap<String, String>();
		hm.put("REGEX_GLOBAL_VARIABLE", REGEX_GLOBAL_VARIABLE);
		hm.put("REGEX_ADD_TO", REGEX_ADD_TO);
		hm.put("REGEX_DISPLAY", REGEX_DISPLAY);
		
	}


	
	

}
