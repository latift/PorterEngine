package tr.com.vbt.natural.parser.loops;

import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.AbstractMultipleLinesCommand;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.token.AbstractToken;

//RP1. REPEAT
public class ElementRepeat extends AbstractMultipleLinesCommand{
	
	String loopName;
	
	public ElementRepeat(AbstractToken baseToken,
			List<AbstractToken> tokenListesi, AbstractCommand parent) {
		super("ElementRepeat","LOOPS.*.REPEAT");
	}
	
	public ElementRepeat(String elementName,String detailedCobolName) {
		super(elementName, detailedCobolName);
	}

	
	

	@Override
	public String exportContents() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedNaturalKeywords.REPEAT +"\n");
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
		sb.append(" "+ReservedNaturalKeywords.REPEAT);
		sb.append(" Ender:"+this.endingCommand);
		sb.append("\"\n");
		return sb.toString();
	}

	public String getLoopName() {
		return loopName;
	}

	public void setLoopName(String loopName) {
		this.loopName = loopName;
	}

	
	



	
}
