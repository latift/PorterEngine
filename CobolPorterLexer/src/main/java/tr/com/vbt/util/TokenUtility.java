package tr.com.vbt.util;

import java.util.List;
import java.util.ListIterator;

import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.TokenTipi;

public class TokenUtility {
	
	
	public static ListIterator<AbstractToken> goToEndOfLine(ListIterator<AbstractToken> tokenListIterator){
		AbstractToken token;
		do{
			token=tokenListIterator.next();
		}while(token.getTip().equals(TokenTipi.SatirBasi));
		return tokenListIterator;
			
	}

	/*public static List<AbstractToken> findPattern(
			List<AbstractToken> tokenListesi, AbstractPattern dataLevelPattern,
			boolean ignoreSatirBasi) {
		
		List<AbstractToken> resultList=new ArrayList<AbstractToken>();
		
		ListIterator<AbstractToken> tokenListIterator=tokenListesi.listIterator();
		
		AbstractToken currentToken;
		do
		{
			currentToken=tokenListIterator.next();
			
			if(currentToken.get)
			
			resultList.add(currentToken);
			
		}while(tokenListIterator.hasNext()&&currentToken !=null&&currentToken.getDeger()!=null);
		
		return resultList;
	}*/


	/**
	 * Update Token Arrays Type ve dataLevelType
	 * @param tokenListesi
	 * @param dataleveltype
	 */
	public static void updateTokensType(List<AbstractToken> tokenListesi,
			TokenTipi dataleveltype) {
		for (AbstractToken abstractToken : tokenListesi) {
			abstractToken.setTip(dataleveltype);
		}
	}

}
