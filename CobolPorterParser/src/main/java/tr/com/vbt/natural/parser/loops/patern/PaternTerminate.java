package tr.com.vbt.natural.parser.loops.patern;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.natural.parser.general.ElementTerminate;
import tr.com.vbt.patern.AbstractPattern;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.OzelKelimeToken;

/**
 *     //NEWPAGE
 *
 */
public class PaternTerminate extends AbstractPattern{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//AbstractPattern asp =new PaternAdd();

	}
	

	public PaternTerminate() {
		super();
		
		//RETURN
		AbstractToken astKeyword=new OzelKelimeToken("TERMINATE", 0, 0, 0);
		astKeyword.setSourceFieldName("FIRST_COMMAND");
		patternTokenList.add(astKeyword);
				
		
	}
	
	@Override
	public AbstractCommand createElement(){
		ElementTerminate elementEscapeTop = new ElementTerminate(ReservedNaturalKeywords.TERMINATE,"GENERAL.*.TERMINATE");
		return elementEscapeTop;
	}


	@Override
	public void setTokenToElement(AbstractCommand matchedCommand,
			AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {
		
		ElementTerminate matchedCommandAdd=(ElementTerminate) matchedCommand;
		
		super.setSatirNumarasi(matchedCommand,currentTokenForMatch, abstractTokenInPattern);
		
		if(abstractTokenInPattern.getSourceFieldName()==null){
		
		}
	}
		





	
	
	

	
	
	
	

}
