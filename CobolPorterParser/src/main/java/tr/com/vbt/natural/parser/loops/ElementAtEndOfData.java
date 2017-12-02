package tr.com.vbt.natural.parser.loops;

import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.AbstractMultipleLinesCommand;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.token.AbstractToken;

//RP1. REPEAT
public class ElementAtEndOfData extends AbstractMultipleLinesCommand{
	
	public ElementAtEndOfData(AbstractToken baseToken,
			List<AbstractToken> tokenListesi, AbstractCommand parent) {
		super("ElementAtEndOfData","LOOPS.*.AT_END_OF_DATA");
	}
	
	public ElementAtEndOfData(String elementName,String detailedCobolName) {
		super(elementName, detailedCobolName);
	}

	
	

	@Override
	public String exportContents() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+"AT_END_OF_DATA" +"\n");
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
		sb.append(" "+"AT_END_OF_DATA");
		sb.append(" Ender:"+this.endingCommand);
		sb.append("\"\n");
		return sb.toString();
	}

	
	



	
}
