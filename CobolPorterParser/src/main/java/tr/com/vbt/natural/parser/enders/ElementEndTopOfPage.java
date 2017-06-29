package tr.com.vbt.natural.parser.enders;

import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.AbstractEndingCommand;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.token.AbstractToken;

//END_DISPLAY
public class ElementEndTopOfPage extends AbstractEndingCommand{
	
	public ElementEndTopOfPage(AbstractToken baseToken,
			List<AbstractToken> tokenListesi, AbstractCommand parent) {
		super("ElementEndTopOfPage","GENERAL.*.END_TOP_OF_PAGE");
	}
	
	public ElementEndTopOfPage(String elementName,String detailedCobolName) {
		super(elementName, detailedCobolName);
	}



	@Override
	public String exportContents() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedNaturalKeywords.END_TOP_OF_PAGE +"\n");
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
		sb.append(" "+ReservedNaturalKeywords.END_TOP_OF_PAGE +"\n");
		return sb.toString();
	}

	
}
