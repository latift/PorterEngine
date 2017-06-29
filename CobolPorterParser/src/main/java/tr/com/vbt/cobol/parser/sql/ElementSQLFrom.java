package tr.com.vbt.cobol.parser.sql;

import java.util.ArrayList;
import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.token.AbstractToken;

public class ElementSQLFrom extends AbstractCommand{
	
	private List<String> fromTableList=new ArrayList<String>();
	
	public ElementSQLFrom(AbstractToken baseToken,
			List<AbstractToken> tokenListesi, AbstractCommand parent) {
		super("ElementSQLFrom","PROCEDURE_DIVISION.*.FROM");
	}

	public ElementSQLFrom(String elementName,String detailedCobolName) {
		super(elementName, detailedCobolName);
	}
	
	@Override
	public String exportContents() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedCobolKeywords.FROM +"=\"");
		for (String src : fromTableList) {
			sb.append(src+" ");
		}
		sb.append("\"\n");
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
		sb.append(" "+ReservedCobolKeywords.FROM +"=\"");
		for (String src : fromTableList) {
			sb.append(src+" ");
		}
		sb.append("\"\n");
		return sb.toString();
	}

	public List<String> getFromTableList() {
		return fromTableList;
	}

	public void setFromTableList(List<String> fromTableList) {
		this.fromTableList = fromTableList;
	}
	
	

}
