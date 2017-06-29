package tr.com.vbt.cobol.parser.division;

import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.AbstractMultipleLinesCommand;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.token.AbstractToken;

public class ElementParagraphMain extends AbstractMultipleLinesCommand{
	
	
	public ElementParagraphMain(AbstractToken baseToken,
			List<AbstractToken> tokenListesi, AbstractCommand parent) {
		super("ElementParagraphMain","PROCEDURE_DIVISION.MAIN_PARAGRAPH");
	}
	
	public ElementParagraphMain() {
		super("ElementParagraphMain","PROCEDURE_DIVISION.MAIN_PARAGRAPH");
	}

	public ElementParagraphMain(String paragraphName,String detailedCobolName) {
		super(paragraphName,detailedCobolName);
	}
	@Override
	public String exportContents() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedCobolKeywords.MAIN_PARAGRAPH +"\n");
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
		sb.append(" "+ReservedCobolKeywords.MAIN_PARAGRAPH +"\n");
		return sb.toString();
	}
	
}
