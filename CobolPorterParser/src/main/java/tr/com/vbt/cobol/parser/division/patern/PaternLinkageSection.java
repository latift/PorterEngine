package tr.com.vbt.cobol.parser.division.patern;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.division.ElementLinkageSection;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.patern.AbstractPattern;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.OzelKelimeToken;

/**
 *LINKAGE SECTION
 *
 */
public class PaternLinkageSection extends AbstractPattern{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//AbstractPattern asp =new PaternAdd();

	}
	

	public PaternLinkageSection() {
		super();
		
		//LINKAGE_SECTION
		AbstractToken astKeyword=new OzelKelimeToken("LINKAGE_SECTION", 0, 0, 0);
		astKeyword.setTekrarlayabilir("+");
		patternTokenList.add(astKeyword);
		
	}
	
	@Override
	public AbstractCommand createElement(){
		ElementLinkageSection createdElement = new ElementLinkageSection(ReservedCobolKeywords.LINKAGE_SECTION,"DATA_DIVISION.LINKAGE_SECTION");
		return createdElement;
	}


	@Override
	public void setTokenToElement(AbstractCommand matchedCommand,
			AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {
		
	}


		





	
	
	

	
	
	
	

}
