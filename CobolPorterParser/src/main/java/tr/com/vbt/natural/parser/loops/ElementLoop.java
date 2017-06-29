package tr.com.vbt.natural.parser.loops;

import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.AbstractEndingCommand;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.token.AbstractToken;

//RP1. REPEAT
public class ElementLoop extends AbstractEndingCommand{
	
	AbstractToken loop;
	
	public ElementLoop(AbstractToken baseToken,
			List<AbstractToken> tokenListesi, AbstractCommand parent) {
		super("ElementLoop","LOOPS.*.LOOP");
	}
	
	public ElementLoop(String elementName,String detailedCobolName) {
		super(elementName, detailedCobolName);
	}

	
	

	@Override
	public String exportContents() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedNaturalKeywords.LOOP +"\n");
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
		sb.append(" "+ReservedNaturalKeywords.LOOP);
		sb.append("\"\n");
		return sb.toString();
	}

	public AbstractToken getLoop() {
		return loop;
	}

	public void setLoop(AbstractToken loop) {
		this.loop = loop;
	}



	
	



	
}
