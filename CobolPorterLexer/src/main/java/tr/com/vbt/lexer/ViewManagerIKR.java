package tr.com.vbt.lexer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.KelimeToken;
import tr.com.vbt.token.TokenTipi;

public class ViewManagerIKR extends AbtractViewManager implements ViewManager{

	final static Logger logger = LoggerFactory.getLogger(ViewManagerIKR.class);
	
	private static ViewManagerIKR instance;
	
	public static ViewManagerIKR getInstance() {
		if (instance == null) {
			instance = new ViewManagerIKR();
		}
		return instance;
	}

}
