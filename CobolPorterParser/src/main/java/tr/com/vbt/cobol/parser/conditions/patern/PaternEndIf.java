package tr.com.vbt.cobol.parser.conditions.patern;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.conditions.enders.ElementEndIf;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.patern.AbstractPattern;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.OzelKelimeToken;

/**
 *     ELSE

 *
 */
public class PaternEndIf extends AbstractPattern{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//AbstractPattern asp =new PaternAdd();

	}
	

	public PaternEndIf() {
		super();
		
		//MOVE
		AbstractToken<String> astKeyword=new OzelKelimeToken<String>("END-IF", 0, 0, 0);
		astKeyword.setTekrarlayabilir("+");
		patternTokenList.add(astKeyword);
		
	}
	
	@Override
	public AbstractCommand createElement(){
		ElementEndIf elementDisplay = new ElementEndIf(ReservedCobolKeywords.END_IF,"PROCEDURE_DIVISION.*.END_IF");
		return elementDisplay;
	}


	@Override
	public void setTokenToElement(AbstractCommand matchedCommand,
			AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {
		
	
	}
		





	
	
	

	
	
	
	

}
