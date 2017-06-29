package tr.com.vbt.natural.parser.conditions.patern;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.natural.parser.conditions.ElementNone;
import tr.com.vbt.patern.AbstractPattern;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.OzelKelimeToken;

/**
 *     NONE

 *
 */
public class PaternNone extends AbstractPattern{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		

	}
	

	public PaternNone() {
		super();
		
		//NONE
		AbstractToken<String> astValue=new OzelKelimeToken<String>("NONE", 0, 0, 0);
		astValue.setSourceFieldName("FIRST_COMMAND");
		astValue.setTekrarlayabilir("+");
		patternTokenList.add(astValue);

		
	}
	
	@Override
	public AbstractCommand createElement(){
		ElementNone elementDisplay = new ElementNone(ReservedNaturalKeywords.NONE,"GENERAL.*.NONE");
		return elementDisplay;
	}


	@Override
	public void setTokenToElement(AbstractCommand matchedCommand,
			AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {
		
		super.setSatirNumarasi(matchedCommand,currentTokenForMatch, abstractTokenInPattern);
	}
		

	


	
	
	

	
	
	
	

}
