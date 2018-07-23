package tr.com.vbt.cobol.parser.division.patern;

import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.division.ElementIdentificationDivision;
import tr.com.vbt.cobol.parser.division.ElementProcedureDivision;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.patern.AbstractPattern;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.CommandKeyToken;
import tr.com.vbt.token.OzelKelimeToken;

/**
 *     PROCEDURE_DIVISION.
 *
 */
public class PaternProcedureDivision extends AbstractPattern{


	public PaternProcedureDivision() {
		super();
		
		//PROCEDURE_DIVISION
		AbstractToken astKeyword=new CommandKeyToken("PROCEDURE_DIVISION", 0, 0, 0);
		astKeyword.setTekrarlayabilir("+");
		astKeyword.setSourceFieldName("FIRST_COMMAND");
		patternTokenList.add(astKeyword);
		
	}
	
	@Override
	public AbstractCommand createElement(){
		ElementProcedureDivision elementDisplay = new ElementProcedureDivision(ReservedCobolKeywords.PROCEDURE_DIVISION,"GENERAL.*.PROCEDURE_DIVISION");
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
