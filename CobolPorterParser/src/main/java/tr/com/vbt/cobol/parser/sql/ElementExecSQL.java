package tr.com.vbt.cobol.parser.sql;

import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.AbstractMultipleLinesCommand;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.token.AbstractToken;

public class ElementExecSQL extends AbstractMultipleLinesCommand{
	
	public ElementExecSQL(AbstractToken baseToken,
			List<AbstractToken> tokenListesi, AbstractCommand parent) {
		super("Exec Sql","PROCEDURE_DIVISION.*.EXEC_SQL");
	}

	public ElementExecSQL(String elementName,String detailedCobolName) {
		super(elementName, detailedCobolName);
	}
	

	@Override
	public String exportContents() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedCobolKeywords.EXEC_SQL +" "+ "\n");
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
		sb.append(" "+ReservedCobolKeywords.EXEC_SQL+" ");
		if(this.endingCommand != null){
			sb.append(this.endingCommand.getCommandName());
		}
	    sb.append("\"\n");
		return sb.toString();
	}

}
