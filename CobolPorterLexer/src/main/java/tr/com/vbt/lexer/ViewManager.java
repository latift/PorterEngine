package tr.com.vbt.lexer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;


import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.KelimeToken;
import tr.com.vbt.token.TokenTipi;
import tr.com.vbt.util.ConverterConfiguration;

public interface ViewManager {

	public void setTypeNameOfViews(List<AbstractToken> tokenListesi);

	
	public void setTypeNameOfViews(AbstractToken curToken);

}
