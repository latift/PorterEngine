package tr.com.vbt.cobol.parser.sql.patern;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.sql.ElementExecSQL;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.patern.AbstractPattern;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.OzelKelimeToken;

/**
 *     EXEC_SQL
 *
 */
public class PaternExecSQL extends AbstractPattern{

	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}
	

	public PaternExecSQL() {
		super();
		
		//EXEC SQL
		AbstractToken astKeyword=new OzelKelimeToken("EXEC SQL", 0, 0, 0);
		astKeyword.setTekrarlayabilir("+");
		patternTokenList.add(astKeyword);
		
	}
	
	@Override
	public AbstractCommand createElement(){
		ElementExecSQL elementDisplay = new ElementExecSQL(ReservedCobolKeywords.EXEC_SQL,"PROCEDURE_DIVISION.*.EXEC_SQL");
		return elementDisplay;
	}


	@Override
	public void setTokenToElement(AbstractCommand matchedCommand,
			AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {
		
	}
		





	
	
	

	
	
	
	

}
