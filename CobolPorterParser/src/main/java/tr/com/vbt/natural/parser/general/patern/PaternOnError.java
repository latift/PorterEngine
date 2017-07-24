package tr.com.vbt.natural.parser.general.patern;

import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ConversionLogModel;
import tr.com.vbt.lexer.NaturalMode;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.natural.parser.general.ElementEndError;
import tr.com.vbt.natural.parser.general.ElementOnError;
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
		
		enderToken=new OzelKelimeToken(ReservedNaturalKeywords.END_ERROR, 0, 0, 0);
		enderToken.setSourceFieldName("END_COMMAND");
		enderToken.setOptional(true);
		patternTokenList.add(enderToken);	

		
	
	}
	
	@Override
	public AbstractCommand createElement(){
		ElementOnError elementDisplay = new ElementOnError(ReservedNaturalKeywords.ON_ERROR,"GENERAL.*.ON_ERROR");
		return elementDisplay;
	}


	@Override
	public void setTokenToElement(AbstractCommand matchedCommand,
			AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {
		
		ElementOnError matchedCommandAdd=(ElementOnError) matchedCommand;
		ElementEndError matchedCommandAddEND=(ElementEndError) matchedCommand;
		
		super.setSatirNumarasi(matchedCommand,currentTokenForMatch, abstractTokenInPattern);
		if(abstractTokenInPattern.getSourceFieldName()==null){
		
		}
		
	}
		

	public AbstractCommand getmatchedCommand(List<AbstractToken> tokenListesi, int offset) {
		return super.getmatchedCommand(tokenListesi, offset);
	}
}
