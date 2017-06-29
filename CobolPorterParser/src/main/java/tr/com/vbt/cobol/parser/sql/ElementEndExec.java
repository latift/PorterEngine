package tr.com.vbt.cobol.parser.sql;

import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.AbstractEndingCommand;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.token.AbstractToken;


public class ElementEndExec extends AbstractEndingCommand{
	
	public ElementEndExec(AbstractToken baseToken,
			List<AbstractToken> tokenListesi, AbstractCommand parent) {
		super(ReservedCobolKeywords.END_EXEC,"PROCEDURE_DIVISION.*.END_EXEC");
	}
	
	public ElementEndExec(String elementName,String detailedCobolName) {
		super(elementName, detailedCobolName);
	}

	@Override
	public void parse() {
		
	}
	

	@Override
	public String exportContents() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedCobolKeywords.END_EXEC +"\"\n");
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
		sb.append(" "+ReservedCobolKeywords.END_EXEC +"\"\n");
		return sb.toString();
	}
	
}
