package tr.com.vbt.natural.parser.loops;

//ESCAPE BOTTOM (RP1.)
import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.token.AbstractToken;

//RP1. REPEAT
public class ElementEscapeBottom extends AbstractCommand{
	
	AbstractToken loopName;
	
	public ElementEscapeBottom(AbstractToken baseToken,
			List<AbstractToken> tokenListesi, AbstractCommand parent) {
		super("ElementEscapeBottom","GENERAL.*.ESCAPE_BOTTOM");
	}
	
	public ElementEscapeBottom(String elementName,String detailedCobolName) {
		super(elementName, detailedCobolName);
	}

	
	

	@Override
	public String exportContents() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedNaturalKeywords.ESCAPE_BOTTOM +"\n");
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
		sb.append(" "+ReservedNaturalKeywords.ESCAPE_BOTTOM +"\n");
		return sb.toString();
	}

	public AbstractToken getLoopName() {
		return loopName;
	}

	public void setLoopName(AbstractToken loopName) {
		this.loopName = loopName;
	}



	
	



	
}
