package tr.com.vbt.lexer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.KelimeToken;
import tr.com.vbt.token.TokenTipi;

public class ViewManagerTPS implements ViewManager{

	private static Map<String, String> viewSynonymMap = new HashMap<>();

	final static Logger logger = LoggerFactory.getLogger(ViewManagerTPS.class);
	
	private static ViewManagerTPS instance;
	
	public static ViewManagerTPS getInstance() {
		if (instance == null) {
			instance = new ViewManagerTPS();
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
		
		viewSynonymMap.put("KET_DOMESTIC_PDT","KET_DOMESTIC");
		viewSynonymMap.put("KET_DOMESTIC_PDT2","KET_DOMESTIC");
		viewSynonymMap.put("KET_FDB_OKU","KET_FDB");
		viewSynonymMap.put("KET_FDB_SPC","KET_FDB");
		viewSynonymMap.put("KET_NOTE_TAX","KET_MULTIFILE");
		viewSynonymMap.put("KET_NOTE_TAX_EXC","KET_MULTIFILE");		
		viewSynonymMap.put("KET_YK_NOTES","KET_MULTIFILE");
		viewSynonymMap.put("KET_KCKGRP","KET_MULTIFILE");
		viewSynonymMap.put("KET_KCKGRP_EXC","KET_MULTIFILE");
		viewSynonymMap.put("KET_MULTIFILE1","KET_MULTIFILE");
		viewSynonymMap.put("KET_MULTIFILE2","KET_MULTIFILE");
		viewSynonymMap.put("KET_NOTE_ALCS_TEXT","KET_NOTE_ALCS");
		viewSynonymMap.put("KET_NOTE","KET_NOTE_ATPCO");
		viewSynonymMap.put("KET_NOTE_LOOK","KET_NOTE_ATPCO");
		viewSynonymMap.put("KET_NOTE_TEXT","KET_NOTE_ATPCO");  
		viewSynonymMap.put("KET_NOTE_LOOK","KET_NOTE_CODE");
		viewSynonymMap.put("KET_NOTE_TEXT","KET_NOTE_CODE");
		viewSynonymMap.put("KET_NOTE","KET_NOTE_CODE");
		viewSynonymMap.put("KET_TABLES","KET_TABLES_NC");
		
		

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
