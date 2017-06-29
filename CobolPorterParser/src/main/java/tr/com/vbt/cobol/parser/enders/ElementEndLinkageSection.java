package tr.com.vbt.cobol.parser.enders;

import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.AbstractEndingCommand;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.token.AbstractToken;

//END_LINKAGE_SECTION
public class ElementEndLinkageSection extends AbstractEndingCommand{
	
	public ElementEndLinkageSection(AbstractToken baseToken,
			List<AbstractToken> tokenListesi, AbstractCommand parent) {
		super("EndLinkageSection","PROCEDURE_DIVISION.*.END_LINKAGE_SECTION");
	}
	
	public ElementEndLinkageSection(String elementName,String detailedCobolName) {
		super(elementName, detailedCobolName);
	}

	@Override
	public String exportContents() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedCobolKeywords.END_LINKAGE_SECTION +"\n");
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
		sb.append(" "+ReservedCobolKeywords.END_LINKAGE_SECTION +"\n");
		return sb.toString();
	}

}
