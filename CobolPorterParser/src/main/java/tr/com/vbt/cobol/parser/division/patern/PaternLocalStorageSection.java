package tr.com.vbt.cobol.parser.division.patern;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.division.ElementLocalStorageSection;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.patern.AbstractPattern;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.OzelKelimeToken;

/**
 *LOCAL-STORAGE SECTION
 *
 */
public class PaternLocalStorageSection extends AbstractPattern{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//AbstractPattern asp =new PaternAdd();

	}
	

	public PaternLocalStorageSection() {
		super();
		
		//LOCAL-STORAGE SECTION
		AbstractToken astKeyword=new OzelKelimeToken("LOCAL-STORAGE_SECTION", 0, 0, 0);
		astKeyword.setTekrarlayabilir("+");
		patternTokenList.add(astKeyword);
		
	}
	
	@Override
	public AbstractCommand createElement(){
		ElementLocalStorageSection createdElement = new ElementLocalStorageSection(ReservedCobolKeywords.LOCAL_STORAGE_SECTION,"DATA_DIVISION.LOCAL-STORAGE_SECTION");
		return createdElement;
	}


	@Override
	public void setTokenToElement(AbstractCommand matchedCommand,
			AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {
		
	}


		





	
	
	

	
	
	
	

}
