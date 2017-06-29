package tr.com.vbt.cobol.parser.sql;

import java.util.ArrayList;
import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.token.AbstractToken;

public class ElementSQLSelect extends AbstractCommand{
	
	private List<String> columnList=new ArrayList<String>();
	
	public ElementSQLSelect(AbstractToken baseToken,
			List<AbstractToken> tokenListesi, AbstractCommand parent) {
		super("ElementSQLSelect","PROCEDURE_DIVISION.*.SELECT");
	}

	public ElementSQLSelect(String elementName,String detailedCobolName) {
		super(elementName, detailedCobolName);
	}
	

	@Override
	public String exportContents() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedCobolKeywords.SELECT +"=\"");
		for (String src : columnList) {
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
		sb.append(" "+ReservedCobolKeywords.SELECT +"=\"");
		for (String src : columnList) {
			sb.append(src+" ");
		}
		sb.append("\"\n");
		return sb.toString();
	}

	public List<String> getColumnList() {
		return columnList;
	}

	public void setColumnList(List<String> columnList) {
		this.columnList = columnList;
	}

	
	
	

}
