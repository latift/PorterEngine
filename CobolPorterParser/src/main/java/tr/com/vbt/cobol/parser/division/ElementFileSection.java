package tr.com.vbt.cobol.parser.division;

import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.AbstractMultipleLinesCommand;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.token.AbstractToken;

public class ElementFileSection extends AbstractMultipleLinesCommand{
	
	
	public ElementFileSection(AbstractToken baseToken,
			List<AbstractToken> tokenListesi, AbstractCommand parent) {
		super("ElementFileSection","DATA_DIVISION.FILE_SECTION");
	}
	
	public ElementFileSection(String paragraphName,String detailedCobolName) {
		super(paragraphName,detailedCobolName);
	}

	
	
	

	@Override
	public String exportContents() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedCobolKeywords.FILE_SECTION +"\"\n");
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
		sb.append(" "+ReservedCobolKeywords.FILE_SECTION +"\"\n");
		return sb.toString();
	}

}
