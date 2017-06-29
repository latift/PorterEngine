package tr.com.vbt.natural.parser.conditions.enders;

import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.AbstractEndingCommand;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.token.AbstractToken;

//END_DECIDE
public class ElementEndDecide extends AbstractEndingCommand{
	
	public ElementEndDecide(AbstractToken baseToken,
			List<AbstractToken> tokenListesi, AbstractCommand parent) {
		super("ElementEndDecide","GENERAL.*.END_DECIDE");
	}
	
	public ElementEndDecide(String elementName,String detailedCobolName) {
		super(elementName, detailedCobolName);
	}



	@Override
	public String exportContents() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedNaturalKeywords.END_DECIDE +"\n");
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
		sb.append(" "+ReservedNaturalKeywords.END_DECIDE +"\n");
		return sb.toString();
	}

	@Override
	public String toString() {
		return ReservedNaturalKeywords.END_DECIDE;
	}

	
	
}
