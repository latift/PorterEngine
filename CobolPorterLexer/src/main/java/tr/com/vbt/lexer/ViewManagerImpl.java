package tr.com.vbt.lexer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;


import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.KelimeToken;
import tr.com.vbt.token.TokenTipi;

public class ViewManagerImpl extends AbtractViewManager implements ViewManager{

	final static Logger logger = Logger.getLogger(ViewManagerImpl.class);
	
	private static ViewManagerImpl instance;
	
	public static ViewManagerImpl getInstance() {
		if (instance == null) {
			instance = new ViewManagerImpl();
		}
		return instance;
	}

}
