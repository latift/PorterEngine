package tr.com.vbt.cobol.parser.division.patern;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.division.ElementEnvironmentDivision;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.patern.AbstractPattern;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.OzelKelimeToken;

/**
 *  ENVIRONMENT DIVISION
 *
 */
public class PaternEnvironmentDivision extends AbstractPattern{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//AbstractPattern asp =new PaternAdd();

	}
	

	public PaternEnvironmentDivision() {
		super();
		
		//ADD
		AbstractToken astKeyword=new OzelKelimeToken("ENVIRONMENT_DIVISION", 0, 0, 0);
		astKeyword.setTekrarlayabilir("+");
		patternTokenList.add(astKeyword);
		
		
	}
	
	@Override
	public AbstractCommand createElement(){
		ElementEnvironmentDivision createdElement = new ElementEnvironmentDivision(ReservedCobolKeywords.ENVIRONMENT_DIVISION,"ENVIRONMENT_DIVISION");
		return createdElement;
	}
	
	


	@Override
	public void setTokenToElement(AbstractCommand matchedCommand,
			AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {
		
	}
		





	
	
	

	
	
	
	

}
