package tr.com.vbt.cobol.parser.sql;

import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.token.AbstractToken;

public class ElementIncludeSQLCA extends AbstractCommand{
	
	public ElementIncludeSQLCA(AbstractToken baseToken,
			List<AbstractToken> tokenListesi, AbstractCommand parent) {
		super("ElementIncludeSQLCA","PROCEDURE_DIVISION.*.INCLUDE_SQLCA");
	}

	public ElementIncludeSQLCA(String elementName,String detailedCobolName) {
		super(elementName, detailedCobolName);
	}
	
	
	@Override
	public String exportContents() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedCobolKeywords.INCLUDE_SQLCA +"\"\n");
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
		sb.append(" "+ReservedCobolKeywords.INCLUDE_SQLCA +"\"\n");
		return sb.toString();
	}
}
