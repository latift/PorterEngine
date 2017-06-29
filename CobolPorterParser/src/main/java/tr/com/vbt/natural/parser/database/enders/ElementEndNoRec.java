package tr.com.vbt.natural.parser.database.enders;

import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.AbstractEndingCommand;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.token.AbstractToken;

//END-NOREC
public class ElementEndNoRec extends AbstractEndingCommand{
	
	public ElementEndNoRec(AbstractToken baseToken,
			List<AbstractToken> tokenListesi, AbstractCommand parent) {
		super("ElementEndNoRec","DATABASE.*.END_NOREC");
	}
	
	public ElementEndNoRec(String elementName,String detailedCobolName) {
		super(elementName, detailedCobolName);
	}



	@Override
	public String exportContents() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedNaturalKeywords.END_NOREC +"\n");
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
		sb.append(" "+ReservedNaturalKeywords.END_NOREC +"\n");
		return sb.toString();
	}

	@Override
	public String toString() {
	return ReservedNaturalKeywords.END_NOREC;
	}

	
	
}
