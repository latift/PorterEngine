package tr.com.vbt.cobol.parser.basicverbs.patern;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.basicverbs.ElementStopRun;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.patern.AbstractPattern;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.OzelKelimeToken;

/**
 *   STOP RUN
 *
 */
public class PaternStopRun extends AbstractPattern{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//AbstractPattern asp =new PaternAdd();

	}
	

	public PaternStopRun() {
		super();
		//ADD
		AbstractToken astKeyword=new OzelKelimeToken("STOP_RUN", 0, 0, 0);
		astKeyword.setTekrarlayabilir("+");
		patternTokenList.add(astKeyword);
		
	
	}
	
	@Override
	public AbstractCommand createElement(){
		ElementStopRun createdElement = new ElementStopRun(ReservedCobolKeywords.STOP_RUN,"PROCEDURE_DIVISION.*.STOP_RUN");
		return createdElement;
	}


	@Override
	public void setTokenToElement(AbstractCommand matchedCommand,
			AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {
		
	
	}


		





	
	
	

	
	
	
	

}
