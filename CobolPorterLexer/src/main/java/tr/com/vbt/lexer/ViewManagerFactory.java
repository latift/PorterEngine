package tr.com.vbt.lexer;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;


public class ViewManagerFactory {

	final static Logger logger = Logger.getLogger(ViewManagerFactory.class);

	private static ViewManager instance;

	public static ViewManager getInstance() {
		instance = ViewManagerImpl.getInstance();
		return instance;
	}


	
}
