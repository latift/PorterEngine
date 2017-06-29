package tr.com.vbt.cobol.parser.conditions.patern;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.conditions.ElementElse;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.patern.AbstractPattern;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.OzelKelimeToken;

/**
 *     ELSE

 *
 */
public class PaternElse extends AbstractPattern{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//AbstractPattern asp =new PaternAdd();

	}
	

	public PaternElse() {
		super();
		
		//MOVE
		AbstractToken<String> astKeyword=new OzelKelimeToken<String>("ELSE", 0, 0, 0);
		astKeyword.setTekrarlayabilir("+");
		patternTokenList.add(astKeyword);
		
	}
	
	@Override
	public AbstractCommand createElement(){
		ElementElse elementDisplay = new ElementElse(ReservedCobolKeywords.ELSE,"PROCEDURE_DIVISION.*.ELSE");
		return elementDisplay;
	}


	@Override
	public void setTokenToElement(AbstractCommand matchedCommand,
			AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {
		
	
	}
		





	
	
	

	
	
	
	

}
