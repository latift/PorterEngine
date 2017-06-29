package tr.com.vbt.natural.parser.loops.patern;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.natural.parser.general.ElementNewPage;
import tr.com.vbt.patern.AbstractPattern;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.OzelKelimeToken;

/**
 *     //NEWPAGE
 *
 */
public class PaternNewPage extends AbstractPattern{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//AbstractPattern asp =new PaternAdd();

	}
	

	public PaternNewPage() {
		super();
		
		//RETURN
		AbstractToken astKeyword=new OzelKelimeToken("NEWPAGE", 0, 0, 0);
		astKeyword.setSourceFieldName("FIRST_COMMAND");
		patternTokenList.add(astKeyword);
				
		
	}
	
	@Override
	public AbstractCommand createElement(){
		ElementNewPage elementEscapeTop = new ElementNewPage(ReservedNaturalKeywords.NEWPAGE,"GENERAL.*.NEWPAGE");
		return elementEscapeTop;
	}


	@Override
	public void setTokenToElement(AbstractCommand matchedCommand,
			AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {
		
		ElementNewPage matchedCommandAdd=(ElementNewPage) matchedCommand;
		
		super.setSatirNumarasi(matchedCommand,currentTokenForMatch, abstractTokenInPattern);
		
		if(abstractTokenInPattern.getSourceFieldName()==null){
		
		}
	}
		





	
	
	

	
	
	
	

}
