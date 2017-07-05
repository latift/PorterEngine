package tr.com.vbt.lexer;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ViewManagerFactory {

	final static Logger logger = LoggerFactory.getLogger(ViewManagerFactory.class);

	private static ViewManager instance;

	public static ViewManager getInstance() {
		instance = ViewManagerImpl.getInstance();
		return instance;
	}


	
}
