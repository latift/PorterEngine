package tr.com.vbt.cobol.parser.division.patern;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.division.ElementInputOutputSection;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.patern.AbstractPattern;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.OzelKelimeToken;

/**
 *  INPUT-OUTPUT SECTION
 *
 */
public class PaternInputOutputSection extends AbstractPattern{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//AbstractPattern asp =new PaternAdd();

	}
	

	public PaternInputOutputSection() {
		super();
		//ADD
		AbstractToken astKeyword=new OzelKelimeToken("INPUT-OUTPUT_SECTION", 0, 0, 0);
		astKeyword.setTekrarlayabilir("+");
		patternTokenList.add(astKeyword);
	}
	
	@Override
	public AbstractCommand createElement(){
		ElementInputOutputSection createdElement = new ElementInputOutputSection(ReservedCobolKeywords.INPUT_OUTPUT_SECTION,"ENVIRONMENT_DIVISION.INPUT-OUTPUT_SECTION");
		return createdElement;
	}


	@Override
	public void setTokenToElement(AbstractCommand matchedCommand,
			AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {
		
	}


	




	
	
	

	
	
	
	

}
