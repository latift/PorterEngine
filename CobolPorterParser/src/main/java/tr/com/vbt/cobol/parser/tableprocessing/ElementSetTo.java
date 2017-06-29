package tr.com.vbt.cobol.parser.tableprocessing;

import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.token.AbstractToken;

//SET ZZ TO 1
//PArams: source value
public class ElementSetTo extends AbstractCommand{
	
	private String source;
	
	private String value;
	
	public ElementSetTo(AbstractToken baseToken,
			List<AbstractToken> tokenListesi, AbstractCommand parent) {
		super("ElementSetTo","PROCEDURE_DIVISION.*.SET");
	}
	
	public ElementSetTo(String elementName,String detailedCobolName) {
		super(elementName, detailedCobolName);
	}

	@Override
	public String exportContents() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedCobolKeywords.SET +"=\""+ this.source+" "+value+"\n");
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
		sb.append(" "+ReservedCobolKeywords.SET +"=\""+ this.source+" "+value+"\n");
		return sb.toString();
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	


	
}
