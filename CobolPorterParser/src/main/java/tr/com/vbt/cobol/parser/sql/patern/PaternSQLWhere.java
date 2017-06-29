package tr.com.vbt.cobol.parser.sql.patern;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.sql.ElementSQLWhere;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.patern.AbstractPattern;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.OzelKelimeToken;

/**
 * WHERE
 * 
 */
public class PaternSQLWhere extends AbstractPattern {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// AbstractPattern asp =new PaternAdd();

	}

	public PaternSQLWhere() {
		super();

		// DISPLAY
		AbstractToken astKeyword = new OzelKelimeToken("WHERE", 0, 0, 0);
		astKeyword.setTekrarlayabilir("+");
		patternTokenList.add(astKeyword);

	}

	@Override
	public AbstractCommand createElement() {
		ElementSQLWhere elementDisplay = new ElementSQLWhere(
				ReservedCobolKeywords.WHERE, "PROCEDURE_DIVISION.*.WHERE");
		return elementDisplay;
	}

	@Override
	public void setTokenToElement(AbstractCommand matchedCommand,
			AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {

	}

}
