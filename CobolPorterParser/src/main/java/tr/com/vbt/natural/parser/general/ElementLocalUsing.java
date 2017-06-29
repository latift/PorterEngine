package tr.com.vbt.natural.parser.general;

import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.token.AbstractToken;

public class ElementLocalUsing extends AbstractCommand{

	
	private String localParameterName;
	
	public ElementLocalUsing(AbstractToken baseToken,
			List<AbstractToken> tokenListesi, AbstractCommand parent) {
		super("Local Using","LOCAL_USING");
	}
	
	public ElementLocalUsing(String elementName,String detailedCobolName) {
		super(elementName, detailedCobolName);
	}


	@Override
	public String exportContents() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedNaturalKeywords.LOCAL_USING+"\"\n");
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
		sb.append(" "+ReservedNaturalKeywords.LOCAL_USING+" ");
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
