package tr.com.vbt.natural.parser.general;

import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.token.AbstractToken;

public class ElementGlobalUsing extends AbstractCommand{

	
	private String globalParameterName;
	
	public ElementGlobalUsing(AbstractToken baseToken,
			List<AbstractToken> tokenListesi, AbstractCommand parent) {
		super("Global Using","GLOBAL_USING");
	}
	
	public ElementGlobalUsing(String elementName,String detailedCobolName) {
		super(elementName, detailedCobolName);
	}


	@Override
	public String exportContents() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedNaturalKeywords.GLOBAL_USING+"\"\n");
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
		sb.append(" "+ReservedNaturalKeywords.GLOBAL_USING+" ");
		sb.append("\"\n");
		return sb.toString();
	}

	public String getGlobalParameterName() {
		return globalParameterName;
	}

	public void setGlobalParameterName(String globalParameterName) {
		this.globalParameterName = globalParameterName;
	}

	
	
}
