package tr.com.vbt.cobol.parser.sql.patern;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.sql.ElementEndExec;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.patern.AbstractPattern;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.OzelKelimeToken;

/**
 *     END-EXEC
 *
 */
public class PaternEndExec extends AbstractPattern{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//AbstractPattern asp =new PaternAdd();

	}
	

	public PaternEndExec() {
		super();
		//END-EXEC
		AbstractToken astKeyword=new OzelKelimeToken("END-EXEC", 0, 0, 0);
		astKeyword.setTekrarlayabilir("+");
		patternTokenList.add(astKeyword);
	}
	
	@Override
	public AbstractCommand createElement(){
		ElementEndExec elementDisplay = new ElementEndExec(ReservedCobolKeywords.END_EXEC,"PROCEDURE_DIVISION.*.END_EXEC");
		return elementDisplay;
	}


	@Override
	public void setTokenToElement(AbstractCommand matchedCommand,
			AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {
		
	}
		





	
	
	

	
	
	
	

}
