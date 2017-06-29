package tr.com.vbt.natural.parser.loops.patern;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.natural.parser.loops.ElementEscapeRoutine;
import tr.com.vbt.patern.AbstractPattern;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.OzelKelimeToken;

/**
 *     //ESCAPE 
 *
 */
public class PaternEscape extends AbstractPattern{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//AbstractPattern asp =new PaternAdd();

	}
	

	public PaternEscape() {
		super();
		
		//ESCAPE_TOP
		AbstractToken astKeyword=new OzelKelimeToken("ESCAPE", 0, 0, 0);
		astKeyword.setSourceFieldName("FIRST_COMMAND");
		patternTokenList.add(astKeyword);
				
		
	}
	
	@Override
	public AbstractCommand createElement(){
		ElementEscapeRoutine elementEscapeTop = new ElementEscapeRoutine(ReservedNaturalKeywords.ESCAPE_ROUTINE,"GENERAL.*.ESCAPE_ROUTINE");
		return elementEscapeTop;
	}


	@Override
	public void setTokenToElement(AbstractCommand matchedCommand,
			AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {
		
		ElementEscapeRoutine matchedCommandAdd=(ElementEscapeRoutine) matchedCommand;
		
		super.setSatirNumarasi(matchedCommand,currentTokenForMatch, abstractTokenInPattern);if(abstractTokenInPattern.getSourceFieldName()==null){
		
		}
	}
		





	
	
	

	
	
	
	

}
