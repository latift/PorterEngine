package tr.com.vbt.cobol.parser.sql.patern;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.sql.ElementExecSqlEndDeclareSection;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.patern.AbstractPattern;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.OzelKelimeToken;

//EXEC SQL END DECLARE SECTION
/**
* 
* 
*  EXEC SQL 
*  	BEGIN DECLARE SECTION END-EXEC.
	      01 WS-STUDENT-REC.
	         05 WS-STUDENT-ID PIC 9(4).
	         05 WS-STUDENT-NAME PIC X(25).
	         05 WS-STUDENT-ADDRESS X(50).
	    EXEC SQL END DECLARE SECTION
 END-EXEC.

* @author 47159500
*
*/
public class PaternExecSqlEndDeclareSection extends AbstractPattern{

	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}
	

	public PaternExecSqlEndDeclareSection() {
		super();
		
		AbstractToken astKeyword=new OzelKelimeToken("EXEC SQL", 0, 0, 0);
		astKeyword.setTekrarlayabilir("+");
		patternTokenList.add(astKeyword);
		
		AbstractToken astKeyword3=new OzelKelimeToken("END", 0, 0, 0);
		astKeyword3.setTekrarlayabilir("+");
		patternTokenList.add(astKeyword3);
		
		AbstractToken astKeyword4=new OzelKelimeToken("DECLARE", 0, 0, 0);
		astKeyword4.setTekrarlayabilir("+");
		patternTokenList.add(astKeyword4);
		
		AbstractToken astKeyword5=new OzelKelimeToken("SECTION", 0, 0, 0);
		astKeyword5.setTekrarlayabilir("+");
		patternTokenList.add(astKeyword5);
		
	}
	
	@Override
	public AbstractCommand createElement(){
		ElementExecSqlEndDeclareSection elementDisplay = new ElementExecSqlEndDeclareSection(ReservedCobolKeywords.EXEC_SQL_END_DECLARE_SECTION,"PROCEDURE_DIVISION.*.EXEC_SQL_END_DECLARE_SECTION");
		return elementDisplay;
	}


	@Override
	public void setTokenToElement(AbstractCommand matchedCommand,
			AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {
		
	}
		





	
	
	

	
	
	
	

}
