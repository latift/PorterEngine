package tr.com.vbt.lexer;

import java.util.List;

import tr.com.vbt.token.AbstractToken;

public interface SynonymManager {
	
	public  String getRealTableName(String synonymName);
	
	public void setSynonymsRealTableName(List<AbstractToken> tokenListesi);
	


}
