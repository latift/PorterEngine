package tr.com.vbt.natural.parser.loops.patern;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.natural.parser.loops.ElementAtEndOfData;
import tr.com.vbt.patern.AbstractPattern;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.OzelKelimeToken;

/**
 *    AT END OF DATA
 *
 */
public class PaternAtEndOfData extends AbstractPattern{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//AbstractPattern asp =new PaternAdd();

	}
	

	public PaternAtEndOfData() {
		super();
		
		//AT
		AbstractToken astKeyword=new OzelKelimeToken("AT", 0, 0, 0);
		astKeyword.setSourceFieldName("FIRST_COMMAND");
		patternTokenList.add(astKeyword);
				
		//END OF DATA
		AbstractToken astKeyword2=new OzelKelimeToken("END_OF_DATA", 0, 0, 0);
		astKeyword2.setSourceFieldName("END_OF_DATA");
		patternTokenList.add(astKeyword2);
		
	}
	
	@Override
	public AbstractCommand createElement(){
		ElementAtEndOfData elementRepeat = new ElementAtEndOfData("END_OF_DATA","GENERAL.*.END_OF_DATA");
		return elementRepeat;
	}


	@Override
	public void setTokenToElement(AbstractCommand matchedCommand,
			AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {
		
		ElementAtEndOfData matchedCommandAdd=(ElementAtEndOfData) matchedCommand;
		
		super.setSatirNumarasi(matchedCommand,currentTokenForMatch, abstractTokenInPattern);
		
	}
		





	
	
	

	
	
	
	

}
