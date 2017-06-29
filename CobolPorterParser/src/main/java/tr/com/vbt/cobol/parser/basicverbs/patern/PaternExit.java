package tr.com.vbt.cobol.parser.basicverbs.patern;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.basicverbs.ElementDisplay;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.patern.AbstractPattern;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.OzelKelimeToken;

/**
 *     DISPLAY 'IN B-PARA
 *
 */
public class PaternExit extends AbstractPattern{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//AbstractPattern asp =new PaternAdd();

	}
	

	public PaternExit() {
		super();
		//DISPLAY
		AbstractToken astKeyword=new OzelKelimeToken("EXIT", 0, 0, 0);
		astKeyword.setTekrarlayabilir("+");
		patternTokenList.add(astKeyword);
	}
	
	@Override
	public AbstractCommand createElement(){
		ElementDisplay elementDisplay = new ElementDisplay(ReservedCobolKeywords.EXIT,"PROCEDURE_DIVISION.*.EXIT");
		return elementDisplay;
	}


	@Override
	public void setTokenToElement(AbstractCommand matchedCommand,
			AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {
			
	}
		





	
	
	

	
	
	
	

}
