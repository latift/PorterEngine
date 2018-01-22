package tr.com.vbt.lexer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import tr.com.vbt.token.AbstractToken;


public class ViewManagerFactory {

	final static Logger logger = Logger.getLogger(ViewManagerFactory.class);

	private static ViewManager instance;

	public static ViewManager getInstance( HashMap<String, String> tableColumnReferans) {
		instance = ViewManagerImpl.getInstance(tableColumnReferans);
		return instance;
	}

	public static void resetInstance() {
		 ViewManagerImpl.resetInstance();
		
	}


	
}
