package tr.com.vbt.lexer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.TokenTipi;

public abstract class AbstractSysnoymManager implements SynonymManager{
	
	protected static Map<String, String> synonymToRealTableNameMap = new HashMap<>();
	
	final static Logger logger = LoggerFactory.getLogger(AbstractSysnoymManager.class);
	
	protected abstract void loadSynoymMap();

	public  void changeSynonyms(List<AbstractToken> tokenListesi) {
		
		String realTableName, synonymName;
		

		AbstractToken curToken;

		for (int i = 0; i < tokenListesi.size() - 1; i++) {

			curToken = tokenListesi.get(i);
			
			if(curToken.getDeger() !=null){
				logger.debug("CurToken:"+curToken.getDeger().toString());
			}
			
			if (curToken.getTip().equals(TokenTipi.Kelime) && curToken.getDeger() != null) {
				synonymName=curToken.getDeger().toString();
				//logger.debug(synonymName);
				realTableName=synonymToRealTableNameMap.get(curToken.getDeger().toString());
				
				if(realTableName!=null){
					curToken.setSynoynmsRealTableName(synonymName);
					curToken.setDeger(realTableName);
					logger.debug("SynonymManager:"+synonymName+"  is changed with "+realTableName);
					logger.debug("");
				}
			}

		}
		logger.debug("SynonymManager: Operation Completed");
 
	}
	
	@Override
	public String getRealTableName(String synonymName) {
		String realTableName=synonymToRealTableNameMap.get(synonymName);
		
		if(realTableName==null){
			return synonymName;
		}
	
		return realTableName;
	}

	@Override
	public void setSynonymsRealTableName(List<AbstractToken> tokenListesi) {
		String realTableName, synonymName;

		AbstractToken curToken;

		for (int i = 0; i < tokenListesi.size() - 1; i++) {

			curToken = tokenListesi.get(i);

			if (curToken.getDeger() != null) {
				logger.debug("CurToken:" + curToken.getDeger().toString());
			}

			if (curToken.getTip().equals(TokenTipi.Kelime) && curToken.getDeger() != null) {
				
				realTableName = synonymToRealTableNameMap.get(curToken.getDeger().toString());

				if (realTableName != null) {
					
					curToken.setSynoynmsRealTableName(realTableName);
					
				}
			}

		}
		logger.debug("SynonymManager: Operation Completed");
		
	}
}
