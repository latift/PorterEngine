package tr.com.vbt.cobol.parser.loops.patern.enders;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.enders.ElementEndPerform;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.patern.AbstractPattern;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.OzelKelimeToken;

/**
 *     ELSE

 *
 */
public class PaternEndPerform extends AbstractPattern{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//AbstractPattern asp =new PaternAdd();

	}
	

	public PaternEndPerform() {
		super();
		
		//MOVE
		AbstractToken<String> astKeyword=new OzelKelimeToken<String>("END-PERFORM", 0, 0, 0);
		astKeyword.setTekrarlayabilir("+");
		patternTokenList.add(astKeyword);
		
	}
	
	@Override
	public AbstractCommand createElement(){
		ElementEndPerform elementDisplay = new ElementEndPerform(ReservedCobolKeywords.END_PERFORM,"PROCEDURE_DIVISION.*.END-PERFORM");
		return elementDisplay;
	}


	@Override
	public void setTokenToElement(AbstractCommand matchedCommand,
			AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {
		
	
	}
		





	
	
	

	
	
	
	

}
