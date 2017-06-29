package tr.com.vbt.natural.parser.datalayout.db;

import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.AbstractMultipleLinesCommand;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.token.AbstractToken;

public class ElementLocal extends AbstractMultipleLinesCommand{
	
	public ElementLocal(AbstractToken baseToken, List<AbstractToken> tokenListesi, AbstractCommand parent) {
		super("Local","GENERAL.*.LOCAL");
	}
	
	public ElementLocal(String elementName,String detailedCobolName) {
		super(elementName, detailedCobolName);
	}

	@Override
	public String exportContents() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedNaturalKeywords.LOCAL);
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
		sb.append(" "+ReservedNaturalKeywords.LOCAL);
		sb.append("Ender:"+this.endingCommand);
		sb.append("\"\n");
		return sb.toString();
	}


	

	
}
