package tr.com.vbt.cobol.parser.sql.patern;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.sql.ElementIncludeSQLCA;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.patern.AbstractPattern;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.OzelKelimeToken;

/**
 *     INCLUDE SQLCA
 *
 */
public class PaternIncludeSQLCA extends AbstractPattern{

	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}
	

	public PaternIncludeSQLCA() {
		super();
		
		//INCLUDE SQLCA
		AbstractToken astKeyword=new OzelKelimeToken("INCLUDE", 0, 0, 0);
		astKeyword.setTekrarlayabilir("+");
		patternTokenList.add(astKeyword);
		
		AbstractToken astKeyword2=new OzelKelimeToken("SQLCA", 0, 0, 0);
		astKeyword2.setTekrarlayabilir("+");
		patternTokenList.add(astKeyword2);
		
	}
	
	@Override
	public AbstractCommand createElement(){
		ElementIncludeSQLCA elementDisplay = new ElementIncludeSQLCA(ReservedCobolKeywords.INCLUDE_SQLCA,"PROCEDURE_DIVISION.*.INCLUDE_SQLCA");
		return elementDisplay;
	}


	@Override
	public void setTokenToElement(AbstractCommand matchedCommand,
			AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {
		
	}
		





	
	
	

	
	
	
	

}
