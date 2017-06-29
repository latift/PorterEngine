package tr.com.vbt.natural.parser.basicverbs.patern;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.natural.parser.basicverbs.ElementStore;
import tr.com.vbt.patern.AbstractPattern;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.KelimeToken;
import tr.com.vbt.token.OzelKelimeToken;

/**
 *    // 6214 STORE IDGIDBS-TGECICI
 *
 */
public class PaternStore extends AbstractPattern{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//AbstractPattern asp =new PaternAdd();

	}
	

	public PaternStore() {
		super();
		
		//STORE
		AbstractToken astSource=new OzelKelimeToken("STORE", 0, 0, 0);
		astSource.setTekrarlayabilir("+");
		astSource.setSourceFieldName("FIRST_COMMAND");
		patternTokenList.add(astSource);
		
		//tableName
		AbstractToken astSource2=new KelimeToken<String>();
		astSource2.setTekrarlayabilir("+");
		astSource2.setSourceFieldName("tableName");
		patternTokenList.add(astSource2);
		
		

		
	}
	
	@Override
	public AbstractCommand createElement(){
		ElementStore elementDisplay = new ElementStore(ReservedNaturalKeywords.STORE,"GENERAL.*.STORE");
		return elementDisplay;
	}


	@Override
	public void setTokenToElement(AbstractCommand matchedCommand,
			AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {
		
		ElementStore matchedCommandAdd=(ElementStore) matchedCommand;
		
		super.setSatirNumarasi(matchedCommand,currentTokenForMatch, abstractTokenInPattern);
		if(abstractTokenInPattern.getSourceFieldName()==null){
		
		}
		else if(abstractTokenInPattern.getSourceFieldName().equals("tableName")){
			matchedCommandAdd.setTableName(currentTokenForMatch);
			matchedCommandAdd.getParameters().put("tableName", matchedCommandAdd.getTableName());
		}
	}
		





	
	
	

	
	
	
	

}
