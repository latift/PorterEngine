package tr.com.vbt.natural.parser.general.patern;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.natural.parser.general.ElementMainStart;
import tr.com.vbt.patern.AbstractPattern;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.OzelKelimeToken;

/**
 * MAIN_START
 *
 */
public class PaternMainStart extends AbstractPattern{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//AbstractPattern asp =new PaternAdd();

	}
	

	public PaternMainStart() {
		super();
		
		//MAIN_PARAGRAPH
		AbstractToken astKeyword=new OzelKelimeToken(ReservedNaturalKeywords.MAIN_START, 0, 0, 0);
		astKeyword.setSourceFieldName("FIRST_COMMAND");
		astKeyword.setTekrarlayabilir("+");
		patternTokenList.add(astKeyword);
		
	}
	
	@Override
	public AbstractCommand createElement(){
		ElementMainStart elementParagraph = new ElementMainStart(ReservedNaturalKeywords.MAIN_START,"GENERAL.*.MAIN_START");
		return elementParagraph;
	}


	@Override
	public void setTokenToElement(AbstractCommand matchedCommand,
			AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {
		super.setSatirNumarasi(matchedCommand,currentTokenForMatch, abstractTokenInPattern);
	}
		





	
	
	

	
	
	
	

}
