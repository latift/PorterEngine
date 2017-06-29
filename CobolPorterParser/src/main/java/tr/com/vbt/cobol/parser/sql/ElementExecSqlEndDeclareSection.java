package tr.com.vbt.cobol.parser.sql;

import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.AbstractEndingCommand;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.token.AbstractToken;


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
public class ElementExecSqlEndDeclareSection extends AbstractEndingCommand{
	
	public ElementExecSqlEndDeclareSection(AbstractToken baseToken,
			List<AbstractToken> tokenListesi, AbstractCommand parent) {
		super(ReservedCobolKeywords.EXEC_SQL_END_DECLARE_SECTION,"PROCEDURE_DIVISION.*.EXEC_SQL_END_DECLARE_SECTION");
	}
	
	public ElementExecSqlEndDeclareSection(String elementName,String detailedCobolName) {
		super(elementName, detailedCobolName);
	}

	@Override
	public String exportContents() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedCobolKeywords.EXEC_SQL_END_DECLARE_SECTION +"\"\n");
		return sb.toString();
	}

	@Override
	public boolean generateDetailedCobolName() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String exportCommands() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedCobolKeywords.EXEC_SQL_END_DECLARE_SECTION +"\"\n");
		return sb.toString();
	}

	
}
