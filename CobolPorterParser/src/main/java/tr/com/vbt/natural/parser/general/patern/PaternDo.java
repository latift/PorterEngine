package tr.com.vbt.natural.parser.general.patern;

import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.natural.parser.general.ElementDo;
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
public class PaternDo extends AbstractPattern{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//AbstractPattern asp =new PaternAdd();

	}
	
	protected AbstractToken starterToken;
	
	protected AbstractToken enderToken;

	public PaternDo() {
		super();
		
		//IF
		starterToken=new OzelKelimeToken(ReservedNaturalKeywords.DO, 0, 0, 0);
		starterToken.setSourceFieldName("FIRST_COMMAND");
		starterToken.setTekrarlayabilir("+");
		patternTokenList.add(starterToken);						
		
	}
	
	@Override
	public AbstractCommand createElement(){
		ElementDo elementDisplay = new ElementDo(ReservedNaturalKeywords.DO,"GENERAL.*.DO");
		return elementDisplay;
	}


	@Override
	public void setTokenToElement(AbstractCommand matchedCommand,
			AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {
		
		ElementDo matchedCommandAdd=(ElementDo) matchedCommand;
		
		super.setSatirNumarasi(matchedCommand,currentTokenForMatch, abstractTokenInPattern);
		if(abstractTokenInPattern.getSourceFieldName()==null){
		
		}
		
	}
		

	public AbstractCommand getmatchedCommand(List<AbstractToken> tokenListesi, int offset) {
		return super.getmatchedCommand(tokenListesi, offset);
	}
}
