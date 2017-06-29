package tr.com.vbt.natural.parser.basicverbs;

import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.token.AbstractToken;

public class ElementInclude extends AbstractCommand{

	
	private String localParameterName;
	
	public ElementInclude(AbstractToken baseToken,
			List<AbstractToken> tokenListesi, AbstractCommand parent) {
		super("Include","INCLUDE");
	}
	
	public ElementInclude(String elementName,String detailedCobolName) {
		super(elementName, detailedCobolName);
	}


	@Override
	public String exportContents() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedNaturalKeywords.INCLUDE+"\"\n");
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
		sb.append(" "+ReservedNaturalKeywords.INCLUDE+" ");
		sb.append("\"\n");
		return sb.toString();
	}

	public String getLocalParameterName() {
		return localParameterName;
	}

	public void setLocalParameterName(String localParameterName) {
		this.localParameterName = localParameterName;
	}

	
}
