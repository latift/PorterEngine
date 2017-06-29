package tr.com.vbt.natural.parser.datalayout.db.enders;

import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.AbstractEndingCommand;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.token.AbstractToken;

//END-LOCAL
public class ElementEndLocal extends AbstractEndingCommand{
	
	public ElementEndLocal(AbstractToken baseToken,
			List<AbstractToken> tokenListesi, AbstractCommand parent) {
		super("ElementEndLocal","GENERAL.*.END-LOCAL");
	}
	
	public ElementEndLocal(String elementName,String detailedCobolName) {
		super(elementName, detailedCobolName);
	}



	public ElementEndLocal(String elementName, String detailedCobolName, boolean isVirtualToken) {
		super(elementName, detailedCobolName,isVirtualToken);
	}

	@Override
	public String exportContents() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedNaturalKeywords.END_LOCAL +"\n");
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
		sb.append(" "+ReservedNaturalKeywords.END_LOCAL +"\n");
		return sb.toString();
	}

	
}
