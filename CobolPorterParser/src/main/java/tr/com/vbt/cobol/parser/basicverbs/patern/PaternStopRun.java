package tr.com.vbt.cobol.parser.basicverbs.patern;

import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.basicverbs.ElementStopRun;
import tr.com.vbt.cobol.parser.division.ElementIdentificationDivision;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.patern.AbstractPattern;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.OzelKelimeToken;

/**
 *     STOP RUN.
 *
 */
public class PaternStopRun extends AbstractPattern{


	public PaternStopRun() {
		super();
		
		//STOP_RUN
		AbstractToken astKeyword=new OzelKelimeToken("STOP_RUN", 0, 0, 0);
		astKeyword.setTekrarlayabilir("+");
		astKeyword.setSourceFieldName("FIRST_COMMAND");
		patternTokenList.add(astKeyword);
		
	}
	
	@Override
	public AbstractCommand createElement(){
		ElementStopRun elementDisplay = new ElementStopRun(ReservedCobolKeywords.STOP_RUN,"GENERAL.*.STOP_RUN");
		return elementDisplay;
	}


	@Override
	public void setTokenToElement(AbstractCommand matchedCommand,
			AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {
	}
		


	public AbstractCommand getmatchedCommand(List<AbstractToken> tokenListesi, int offset) {
		return super.getmatchedCommand(tokenListesi, offset);
	}
	
	

}
