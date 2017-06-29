package tr.com.vbt.cobol.parser.sql;

import java.util.ArrayList;
import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.token.AbstractToken;

public class ElementSQLInto extends AbstractCommand{
	
	private List<String> intoList=new ArrayList<String>();
	
	public ElementSQLInto(AbstractToken baseToken,
			List<AbstractToken> tokenListesi, AbstractCommand parent) {
		super("ElementSQLInto","PROCEDURE_DIVISION.*.INTO");
	}

	public ElementSQLInto(String elementName,String detailedCobolName) {
		super(elementName, detailedCobolName);
	}
	
	@Override
	public String exportContents() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedCobolKeywords.INTO +"=\"");
		for (String src : intoList) {
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
		sb.append(" "+ReservedCobolKeywords.INTO +"=\"");
		for (String src : intoList) {
			sb.append(src+" ");
		}
		sb.append("\"\n");
		return sb.toString();
	}

	public List<String> getIntoList() {
		return intoList;
	}

	public void setIntoList(List<String> intoList) {
		this.intoList = intoList;
	}

	
}
