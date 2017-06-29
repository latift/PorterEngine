package tr.com.vbt.natural.parser.basicverbs.patern;

import java.util.ArrayList;
import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.natural.parser.basicverbs.ElementStop;
import tr.com.vbt.patern.AbstractPattern;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.OzelKelimeToken;
import tr.com.vbt.token.TokenTipi;

/**
 *    FETCH RETURN 'AYKPFMM2'
 *
 */
public class PaternStop extends AbstractPattern{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//AbstractPattern asp =new PaternAdd();

	}


	private OzelKelimeToken starterToken;
	

	public PaternStop() {
		super();
		
		//FETCH_RETURN
		this.starterToken=new OzelKelimeToken("STOP", 0, 0, 0);
		starterToken.setTekrarlayabilir("+");
		starterToken.setSourceFieldName("FIRST_COMMAND");
		patternTokenList.add(starterToken);
		
	}
	
	@Override
	public AbstractCommand createElement(){
		ElementStop elementDisplay = new ElementStop(ReservedNaturalKeywords.STOP,"GENERAL.*.STOP");
		return elementDisplay;
	}


	@Override
	public void setTokenToElement(AbstractCommand matchedCommand,
			AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {
		
		ElementStop matchedCommandAdd=(ElementStop) matchedCommand;
		
		super.setSatirNumarasi(matchedCommand,currentTokenForMatch, abstractTokenInPattern);

	}
		





	
	
	

	
	
	
	

}
