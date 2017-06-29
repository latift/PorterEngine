package tr.com.vbt.cobol.parser.tableprocessing.patern;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.tableprocessing.ElementSearch;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.patern.AbstractPattern;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.KelimeToken;
import tr.com.vbt.token.OzelKelimeToken;
import tr.com.vbt.token.SayiToken;

/**
 * SEARCH B-PARA 3 TIMES.
 */
public class PaternSearch extends AbstractPattern{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//AbstractPattern asp =new PaternAdd();

	}
	

	public PaternSearch() {
		super();
		
		//SEARCH
		AbstractToken astKeyword=new OzelKelimeToken("SEARCH", 0, 0, 0);
		astKeyword.setTekrarlayabilir("+");
		patternTokenList.add(astKeyword);
		
		
		//A
		AbstractToken astSource=new KelimeToken<String>();
		astSource.setTekrarlayabilir("+");
		astSource.setSourceFieldName("paragraghName");
		patternTokenList.add(astSource);
		
		// C
		AbstractToken astDest=new SayiToken<Integer>();
		astDest.setTekrarlayabilir("+");
		astDest.setSourceFieldName("runCount");
		patternTokenList.add(astDest);
				
				
		//TIMES
		AbstractToken astKeyword2=new OzelKelimeToken("TIMES", 0, 0, 0);
		astKeyword2.setTekrarlayabilir("+");
		patternTokenList.add(astKeyword2);
		
		
		
	}
	
	@Override
	public AbstractCommand createElement(){
		ElementSearch createdElement = new ElementSearch(ReservedCobolKeywords.SEARCH,"PROCEDURE_DIVISION.*.SEARCH");
		return createdElement;
	}


	@Override
	public void setTokenToElement(AbstractCommand matchedCommand,
			AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {
		
		ElementSearch matchedCommandAdd=(ElementSearch) matchedCommand;
		
		super.setSatirNumarasi(matchedCommand,currentTokenForMatch, abstractTokenInPattern);if(abstractTokenInPattern.getSourceFieldName()==null){
		
		}
	}
		





	
	
	

	
	
	
	

}
