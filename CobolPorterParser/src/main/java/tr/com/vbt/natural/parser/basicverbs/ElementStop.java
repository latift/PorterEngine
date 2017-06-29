package tr.com.vbt.natural.parser.basicverbs;

import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.token.AbstractToken;


public class ElementStop extends AbstractCommand{
	
	
	public ElementStop(AbstractToken baseToken,
			List<AbstractToken> tokenListesi, AbstractCommand parent) {
		super("ElementStop","GENERAL.*.STOP");
	}
	
	public ElementStop(String elementName,String detailedCobolName) {
		super(elementName, detailedCobolName);
	}


	@Override
	public String exportContents() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedNaturalKeywords.STOP+"\"\n");
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
		sb.append(" "+ReservedNaturalKeywords.STOP +"\"\n");
		return sb.toString();
	}


	
	
	

	
}
