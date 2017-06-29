package tr.com.vbt.cobol.parser.division;

import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.AbstractMultipleLinesCommand;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.token.AbstractToken;

public class ElementFileControl extends AbstractMultipleLinesCommand{

	public ElementFileControl(AbstractToken baseToken, List<AbstractToken> tokenListesi, AbstractCommand parent) {
		super("ConfigurationSection","INPUT_OUTPUT_SECTION.FILE_CONTROL");
	}
	
	public ElementFileControl(String elementName,String detailedCobolName) {
		super(elementName, detailedCobolName);
	}

	@Override
	public String exportContents() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedCobolKeywords.FILE_CONTROL);
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
		sb.append(" "+ReservedCobolKeywords.FILE_CONTROL);
		return sb.toString();
	}



}
