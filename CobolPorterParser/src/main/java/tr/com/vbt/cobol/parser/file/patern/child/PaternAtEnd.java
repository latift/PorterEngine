package tr.com.vbt.cobol.parser.file.patern.child;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.file.child.ElementAtEnd;
import tr.com.vbt.patern.AbstractPattern;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.OzelKelimeToken;

/*
 * 003500     READ AMBLIST INTO IN-REC AT END                              00349500
   003510         MOVE 1 TO EOF-FLAG.                                      00349600
*/
public class PaternAtEnd extends AbstractPattern{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//AbstractPattern asp =new PaternAdd();

	}
	

	public PaternAtEnd() {
		super();
		
		//READ
		AbstractToken astKeyword=new OzelKelimeToken("AT_END", 0, 0, 0);
		patternTokenList.add(astKeyword);
		
	}
	
	@Override
	public AbstractCommand createElement(){
		ElementAtEnd elementDisplay = new ElementAtEnd();
		return elementDisplay;
	}


	@Override
	public void setTokenToElement(AbstractCommand matchedCommand,
			AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {
		
		
	}
		





	
	
	

	
	
	
	

}
