package tr.com.vbt.cobol.parser.division;

import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.AbstractMultipleLinesCommand;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.token.AbstractToken;

public class ElementLinkageSection extends AbstractMultipleLinesCommand{

	public ElementLinkageSection(AbstractToken baseToken,
			List<AbstractToken> tokenListesi, AbstractCommand parent) {
		super("LocalStorageSection","DATA_DIVISION.LINKAGE_SECTION");
		
	}
	
	public ElementLinkageSection(String elementName,String detailedCobolName) {
		super(elementName, detailedCobolName);
	}

	@Override
	public String exportContents() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedCobolKeywords.LINKAGE_SECTION +"\n");
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
		sb.append(" "+ReservedCobolKeywords.LINKAGE_SECTION +" ");
		if(this.endingCommand != null){
			sb.append(this.endingCommand.getCommandName());
		}
	    sb.append("\"\n");
		return sb.toString();
	}

}
