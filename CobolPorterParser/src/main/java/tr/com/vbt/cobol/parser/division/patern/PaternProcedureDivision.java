package tr.com.vbt.cobol.parser.division.patern;

import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.division.ElementIdentificationDivision;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.patern.AbstractPattern;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.OzelKelimeToken;

/**
 *     IDENTIFICATION DIVISION.
 *
 */
public class PaternProcedureDivision extends AbstractPattern{


	public PaternProcedureDivision() {
		super();
		
		//IDENTIFICATION_DIVISION
		AbstractToken astKeyword=new OzelKelimeToken("IDENTIFICATION_DIVISION", 0, 0, 0);
		astKeyword.setTekrarlayabilir("+");
		astKeyword.setSourceFieldName("FIRST_COMMAND");
		patternTokenList.add(astKeyword);
		
	}
	
	@Override
	public AbstractCommand createElement(){
		ElementIdentificationDivision elementDisplay = new ElementIdentificationDivision(ReservedCobolKeywords.IDENTIFICATION_DIVISION,"GENERAL.*.IDENTIFICATION_DIVISION");
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
