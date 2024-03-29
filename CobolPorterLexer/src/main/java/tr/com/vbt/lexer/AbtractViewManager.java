package tr.com.vbt.lexer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;


import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.KelimeToken;
import tr.com.vbt.token.TokenTipi;

public abstract class AbtractViewManager {
	
	final static Logger logger = Logger.getLogger(AbtractViewManager.class);

	//protected static Map<String, String> viewSynonymMap = new HashMap<>();
	
	protected static String operatingSourceFileName;
	
	
	protected HashMap<String, String> tableColumnReferans;
	

	public void setTypeNameOfViews(List<AbstractToken> tokenListesi) {
		
		String viewName, synonymName, realTablName;
		

		AbstractToken curToken;

		for (int i = 0; i < tokenListesi.size() - 1; i++) {

			curToken = tokenListesi.get(i);
			
			if(curToken.getDeger() !=null){
				logger.debug("CurToken:"+curToken.getDeger().toString());
			}
			if(curToken.getSatirNumarasi()==21){
				logger.debug(curToken.getDeger().toString());
			}
			
			if (curToken.getTip().equals(TokenTipi.Kelime) && curToken.getDeger() != null) {
				
				synonymName=tableColumnReferans.get(curToken.getDeger().toString());
				
				if(synonymName==null){
					synonymName=curToken.getDeger().toString();
				}
		
				realTablName=SysnonymManagerFactory.getInstance().getRealTableName(synonymName);
				
				curToken.setTypeNameOfView(realTablName);
			}

		}
		logger.debug("SynonymManager: Operation Completed");
 
	}
	

	/*
	 * Her natural program çevriminde bir kere calisir
	 *//*
	protected static void loadViewSynonymMap(List<AbstractToken> tokenListesi) {
		
		if(operatingSourceFileName==ConversionLogModel.getInstance().getFileName()){
			return;
		}
		
		operatingSourceFileName=ConversionLogModel.getInstance().getFileName();
		
		//*S**1 ANLASMA VIEW OF IKR-ANLASMA 
		AbstractToken viewName = null, tableName = null;
		
		for(int i=1; i<tokenListesi.size()-1;i++){
			if(tokenListesi.get(i).isOzelKelime(ReservedNaturalKeywords.END_DEFINE)){
				break;
			}
			
			if(tokenListesi.get(i).isOzelKelime(ReservedNaturalKeywords.VIEW) && tokenListesi.get(i+1).isOzelKelime("OF")){
				viewName=tokenListesi.get(i-1);
				tableName=tokenListesi.get(i+2);
				viewSynonymMap.put(viewName.getDeger().toString(),tableName.getDeger().toString());
			}else if(tokenListesi.get(i).isOzelKelime(ReservedNaturalKeywords.VIEW)){
				viewName=tokenListesi.get(i-1);
				tableName=tokenListesi.get(i+1);
				viewSynonymMap.put(viewName.getDeger().toString(),tableName.getDeger().toString());
			}else if(tokenListesi.get(i).isOzelKelime(ReservedNaturalKeywords.VIEW_OF)){
				viewName=tokenListesi.get(i-1);
				tableName=tokenListesi.get(i+1);
				viewSynonymMap.put(viewName.getDeger().toString(),tableName.getDeger().toString());
			}
		}
	}*/
	
	public void setTypeNameOfViews(AbstractToken curToken) {
		
		String viewName, synonymName, realTablName;
		

		if(curToken.getDeger() !=null){
				logger.debug("CurToken:"+curToken.getDeger().toString());
			}
			
			if (curToken.getTip().equals(TokenTipi.Kelime) && curToken.getDeger() != null) {
				
				synonymName=tableColumnReferans.get(curToken.getDeger().toString());
				
				if(synonymName==null){
					synonymName=curToken.getDeger().toString();
				}
		
				realTablName=SysnonymManagerFactory.getInstance().getRealTableName(synonymName);
				
				curToken.setTypeNameOfView(realTablName);
			}

	}
	
}
