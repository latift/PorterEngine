package tr.com.vbt.cobol.parser.division.patern;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.division.ElementWorkingStorageSection;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.patern.AbstractPattern;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.OzelKelimeToken;

/**
 *WORKING-STORAGE SECTION
 *
 */
public class PaternWorkingStorageSection extends AbstractPattern{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//AbstractPattern asp =new PaternAdd();

	}
	

	public PaternWorkingStorageSection() {
		super();
		
		//WORKING-STORAGE SECTION
		AbstractToken astKeyword=new OzelKelimeToken("WORKING-STORAGE_SECTION", 0, 0, 0);
		astKeyword.setTekrarlayabilir("+");
		patternTokenList.add(astKeyword);
		
	}
	
	@Override
	public AbstractCommand createElement(){
		ElementWorkingStorageSection createdElement = new ElementWorkingStorageSection(ReservedCobolKeywords.WORKING_STORAGE_SECTION,"DATA_DIVISION.WORKING-STORAGE_SECTION");
		return createdElement;
	}


	@Override
	public void setTokenToElement(AbstractCommand matchedCommand,
			AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {
		
	}


		





	
	
	

	
	
	
	

}
