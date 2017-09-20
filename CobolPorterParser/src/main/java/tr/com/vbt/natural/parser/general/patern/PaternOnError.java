package tr.com.vbt.natural.parser.general.patern;

import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.natural.parser.general.ElementSubroutine;
import tr.com.vbt.patern.AbstractPattern;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.OzelKelimeToken;

/**
 *  	If:ÖzelKelime
		Condition:
		Then: ÖzelKelimes
		
		  DO
*S**           PROGRAM  = *PROGRAM
*S**           CALLNAT 'ERROR' PROGRAM
*S**         DOEND
 *
 */
public class PaternOnError extends AbstractPattern{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//AbstractPattern asp =new PaternAdd();

	}
	
	protected AbstractToken starterToken;
	protected AbstractToken enderToken;
	

	public PaternOnError() {
		super();
		
		//IF
		starterToken=new OzelKelimeToken(ReservedNaturalKeywords.ON_ERROR, 0, 0, 0);
		starterToken.setSourceFieldName("FIRST_COMMAND");
		starterToken.setTekrarlayabilir("+");
		patternTokenList.add(starterToken);	
		
	}
	
	@Override
	public AbstractCommand createElement(){
		AbstractCommand elementParagraph = new ElementSubroutine(ReservedNaturalKeywords.SUBROUTINE,"GENERAL.SUBROUTINE" );
		return elementParagraph;
	}


	@Override
	public void setTokenToElement(AbstractCommand matchedCommand,
			AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {
		
		ElementSubroutine elementParagraph=(ElementSubroutine) matchedCommand;
		
		super.setSatirNumarasi(matchedCommand,currentTokenForMatch, abstractTokenInPattern);
		
		elementParagraph.setSubroutineName("ON_ERROR");
		elementParagraph.getParameters().put("subroutineName", elementParagraph.getSubroutineName());
		
	}
		

	public AbstractCommand getmatchedCommand(List<AbstractToken> tokenListesi, int offset) {
		return super.getmatchedCommand(tokenListesi, offset);
	}
}
