package tr.com.vbt.lexer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.KelimeToken;
import tr.com.vbt.token.TokenTipi;

public class ViewManagerImpl extends AbtractViewManager implements ViewManager{

	final static Logger logger = LoggerFactory.getLogger(ViewManagerImpl.class);
	
	private static ViewManagerImpl instance;
	
	public static ViewManagerImpl getInstance() {
		if (instance == null) {
			instance = new ViewManagerImpl();
		}
		return instance;
	}

}
