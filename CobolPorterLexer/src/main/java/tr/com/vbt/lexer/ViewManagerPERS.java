package tr.com.vbt.lexer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.KelimeToken;
import tr.com.vbt.token.TokenTipi;

public class ViewManagerPERS implements ViewManager{

	private static Map<String, String> viewSynonymMap = new HashMap<>();

	final static Logger logger = LoggerFactory.getLogger(ViewManagerPERS.class);
	
	private static ViewManagerPERS instance;
	
	public static ViewManagerPERS getInstance() {
		if (instance == null) {
			instance = new ViewManagerPERS();
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
		viewSynonymMap.put("PERF34V","PERF34");
		viewSynonymMap.put("PERF30A","PERF30");
		viewSynonymMap.put("PERF27","PERF27");
		viewSynonymMap.put("PERF34","PERF34");
		viewSynonymMap.put("KREF03","KREF03");
		viewSynonymMap.put("PERF35","PERF35");
		viewSynonymMap.put("PERF38","PERF38");
		viewSynonymMap.put("PERF04","PERF04");
		viewSynonymMap.put("PERF07","PERF07");
		viewSynonymMap.put("PERF02","PERF02");
		viewSynonymMap.put("PERF14","PERF14");
		viewSynonymMap.put("PERF01","PERF01");
		viewSynonymMap.put("MUVF05","MUVF0");
		viewSynonymMap.put("PERF04","PERF04");
		viewSynonymMap.put("PERF15","PERF15");
		viewSynonymMap.put("PERF17","PERF17");
		viewSynonymMap.put("PERF38","PERF38");
		viewSynonymMap.put("PERF30","PERF30");
		viewSynonymMap.put("PERF34","PERF34");
		viewSynonymMap.put("PERF50","PERF50");
		viewSynonymMap.put("PERF40","PERF40");
		viewSynonymMap.put("PERF41","PERF41");
		viewSynonymMap.put("PERF06","PERF06");
		viewSynonymMap.put("PERF30","PERF30");
		
		//Kodda olanlar 
		//TODO: Bu logic yeniden tasarlanmali.
		viewSynonymMap.put("PERF04A","PERF04");
		

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
