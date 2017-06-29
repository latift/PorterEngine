package tr.com.vbt.cobol.parser.file.patern;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.division.ElementFileSection;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.patern.AbstractPattern;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.OzelKelimeToken;


public class PaternFileSection extends AbstractPattern{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//AbstractPattern asp =new PaternAdd();

	}
	

	public PaternFileSection() {
		
		//DISPLAY
		AbstractToken astKeyword=new OzelKelimeToken("FILE_SECTION", 0, 0, 0);
		astKeyword.setTekrarlayabilir("+");
		patternTokenList.add(astKeyword);
		
	}
	
	@Override
	public AbstractCommand createElement(){
		ElementFileSection elementDisplay = new ElementFileSection(ReservedCobolKeywords.FILE_SECTION,"DATA_DIVISION.*.FILE_SECTION");
		return elementDisplay;
	}


	@Override
	public void setTokenToElement(AbstractCommand matchedCommand,
			AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {
	}
		





	
	
	

	
	
	
	

}
