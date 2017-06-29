package tr.com.vbt.natural.parser.general;

//ESCAPE BOTTOM (RP1.)
import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.token.AbstractToken;

//RP1. REPEAT
public class ElementNewPage extends AbstractCommand{
	
	public ElementNewPage(AbstractToken baseToken,
			List<AbstractToken> tokenListesi, AbstractCommand parent) {
		super("ElementNewPage","GENERAL.*.NEWPAGE");
	}
	
	public ElementNewPage(String elementName,String detailedCobolName) {
		super(elementName, detailedCobolName);
	}

	
	

	@Override
	public String exportContents() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedNaturalKeywords.NEWPAGE +"\n");
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
		sb.append(" "+ReservedNaturalKeywords.NEWPAGE +"\n");
		return sb.toString();
	}



	
}
