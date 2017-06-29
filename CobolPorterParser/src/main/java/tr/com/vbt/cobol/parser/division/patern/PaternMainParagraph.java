package tr.com.vbt.cobol.parser.division.patern;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.division.ElementParagraphMain;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.patern.AbstractPattern;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.OzelKelimeToken;

/**
 * MAIN_PARAGRAPH
 *
 */
public class PaternMainParagraph extends AbstractPattern{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//AbstractPattern asp =new PaternAdd();

	}
	

	public PaternMainParagraph() {
		super();
		
		//MAIN_PARAGRAPH
		AbstractToken astKeyword=new OzelKelimeToken(ReservedCobolKeywords.MAIN_PARAGRAPH, 0, 0, 0);
		astKeyword.setTekrarlayabilir("+");
		patternTokenList.add(astKeyword);
		
	}
	
	@Override
	public AbstractCommand createElement(){
		ElementParagraphMain elementParagraph = new ElementParagraphMain(ReservedCobolKeywords.MAIN_PARAGRAPH,"PROCEDURE_DIVISION."+ReservedCobolKeywords.MAIN_PARAGRAPH);
		return elementParagraph;
	}


	@Override
	public void setTokenToElement(AbstractCommand matchedCommand,
			AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {
		
	}
		





	
	
	

	
	
	
	

}
