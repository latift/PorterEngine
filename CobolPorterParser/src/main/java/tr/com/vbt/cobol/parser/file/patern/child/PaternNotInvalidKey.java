package tr.com.vbt.cobol.parser.file.patern.child;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.file.child.ElementInvalidKey;
import tr.com.vbt.patern.AbstractPattern;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.OzelKelimeToken;

/*
 * 
 *            READ VU2FILE RECORD KEY IS VU2-KEY                           08210000
                	INVALID KEY PERFORM VU2-OKUNAMADI.                      08220000

 */

public class PaternNotInvalidKey extends AbstractPattern{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//AbstractPattern asp =new PaternAdd();

	}
	

	public PaternNotInvalidKey() {
		super();
		
		//INVALID_KEY
		AbstractToken astKeyword=new OzelKelimeToken("NOT_AT_END", 0, 0, 0);
		patternTokenList.add(astKeyword);
		
	}
	
	@Override
	public AbstractCommand createElement(){
		ElementInvalidKey elementDisplay = new ElementInvalidKey();
		return elementDisplay;
	}


	@Override
	public void setTokenToElement(AbstractCommand matchedCommand,
			AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {
		
	}
		





	
	
	

	
	
	
	

}
