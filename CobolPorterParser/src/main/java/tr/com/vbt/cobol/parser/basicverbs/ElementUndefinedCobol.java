package tr.com.vbt.cobol.parser.basicverbs;

import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.token.AbstractToken;

public class ElementUndefinedCobol extends AbstractCommand{
	
	private String dataToDisplay;
	
	public ElementUndefinedCobol(AbstractToken baseToken,
			List<AbstractToken> tokenListesi, AbstractCommand parent) {
		super("Undefined","UNDEFINED_COBOL_KEYWORD");
		this.commandMatchPoint=1;
	}
	
	public ElementUndefinedCobol(String elementName,String detailedCobolName) {
		super(elementName, detailedCobolName);
		this.commandMatchPoint=1;
	}
	
	public ElementUndefinedCobol() {
		super("Undefined","UNDEFINED_COBOL_KEYWORD");
		this.commandMatchPoint=1;
	}


	@Override
	public String exportContents() {
		StringBuilder sb=new StringBuilder(); 
		sb.append(this.satirNumarasi);
		sb.append(" "+ReservedCobolKeywords.UNDEFINED +"=\""+ this.dataToDisplay+"\"\n");
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
		sb.append(this.satirNumarasi);
		sb.append(" "+ReservedCobolKeywords.UNDEFINED +"=\""+ this.dataToDisplay+"\"\n");
		return sb.toString();
	}

	@Override
	public String toString() {
		return ReservedCobolKeywords.UNDEFINED +":"+ this.dataToDisplay;
	}

	public String getDataToDisplay() {
		return dataToDisplay;
	}

	public void setDataToDisplay(String dataToDisplay) {
		this.dataToDisplay = dataToDisplay;
	}

	
	
}
