package tr.com.vbt.cobol.parser.division;

import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.AbstractMultipleLinesCommand;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.token.AbstractToken;

public class ElementInputOutputSection extends AbstractMultipleLinesCommand{


	public ElementInputOutputSection(AbstractToken baseToken, List<AbstractToken> tokenListesi, AbstractCommand parent) {
		super("InputOutputSection","ENVIRONMENT_DIVISION.INPUT-OUTPUT_SECTION");
	}
	
	public ElementInputOutputSection(String elementName,String detailedCobolName) {
		super(elementName, detailedCobolName);
	}
	

	@Override
	public String exportContents() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedCobolKeywords.INPUT_OUTPUT_SECTION +"\n");
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
		sb.append(" "+ReservedCobolKeywords.INPUT_OUTPUT_SECTION +"\n");
		return sb.toString();
	}

}
