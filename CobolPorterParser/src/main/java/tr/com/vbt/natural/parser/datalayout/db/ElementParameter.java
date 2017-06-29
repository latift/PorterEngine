package tr.com.vbt.natural.parser.datalayout.db;

import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.AbstractMultipleLinesCommand;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.token.AbstractToken;

public class ElementParameter extends AbstractMultipleLinesCommand{
	
	public ElementParameter(AbstractToken baseToken, List<AbstractToken> tokenListesi, AbstractCommand parent) {
		super("Parameter","PARAMETER");
	}
	
	public ElementParameter(String elementName,String detailedCobolName) {
		super(elementName, detailedCobolName);
	}

	@Override
	public String exportContents() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedNaturalKeywords.PARAMETER);
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
		sb.append(" "+ReservedNaturalKeywords.PARAMETER);
		sb.append("Ender:"+this.endingCommand);
		sb.append("\"\n");
		return sb.toString();
	}


	

	
}
