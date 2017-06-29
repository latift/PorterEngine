package tr.com.vbt.lexer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.KelimeToken;
import tr.com.vbt.token.TokenTipi;

public class ViewManagerGDR implements ViewManager{

	private static Map<String, String> viewSynonymMap = new HashMap<>();

	final static Logger logger = LoggerFactory.getLogger(ViewManagerGDR.class);
	
	private static ViewManagerGDR instance;
	
	public static ViewManagerGDR getInstance() {
		if (instance == null) {
			instance = new ViewManagerGDR();
		}
		return instance;
	}

	public void setTypeNameOfViews(List<AbstractToken> tokenListesi) {
		
		String viewName, synonymName, realTablName;
		 viewSynonymMap = new HashMap<>();
		loadViewSynonymMap();

		AbstractToken curToken;

		for (int i = 0; i < tokenListesi.size() - 1; i++) {

			curToken = tokenListesi.get(i);
			
			if(curToken.getDeger() !=null){
				logger.debug("CurToken:"+curToken.getDeger().toString());
			}
			
			if (curToken.getTip().equals(TokenTipi.Kelime) && curToken.getDeger() != null) {
				viewName=curToken.getDeger().toString();
				//logger.debug(synonymName);
				synonymName=viewSynonymMap.get(curToken.getDeger().toString());
				
				if(synonymName==null){
					synonymName=curToken.getDeger().toString();
				}
		
				realTablName=SysnonymManagerFactory.getInstance().getRealTableName(synonymName);
				
				curToken.setTypeNameOfView(realTablName);
			}

		}
		logger.debug("SynonymManager: Operation Completed");
 
	}

	private static void loadViewSynonymMap() {
		
		//Include Fileda olanlar

		

	}

	public void setTypeNameOfViews(KelimeToken curToken) {
		
		String viewName, synonymName, realTablName;
		

		if(curToken.getDeger() !=null){
				logger.debug("CurToken:"+curToken.getDeger().toString());
			}
			
			if (curToken.getTip().equals(TokenTipi.Kelime) && curToken.getDeger() != null) {
				
				synonymName=viewSynonymMap.get(curToken.getDeger().toString());
				
				if(synonymName==null){
					synonymName=curToken.getDeger().toString();
				}
		
				realTablName=SysnonymManagerFactory.getInstance().getRealTableName(synonymName);
				
				curToken.setTypeNameOfView(realTablName);
			}

	}

}
