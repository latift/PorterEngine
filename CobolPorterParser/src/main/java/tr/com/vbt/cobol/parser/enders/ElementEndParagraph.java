package tr.com.vbt.cobol.parser.enders;

import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.AbstractEndingCommand;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.token.AbstractToken;

//END_PARAGRAPH
public class ElementEndParagraph extends AbstractEndingCommand{
	
	public ElementEndParagraph(AbstractToken baseToken,
			List<AbstractToken> tokenListesi, AbstractCommand parent) {
		super("ElementEndFileControl","INPUT_OUTPUT_SECTION.*.END_PARAGRAPH");
	}
	
	public ElementEndParagraph(String elementName,String detailedCobolName) {
		super(elementName, detailedCobolName);
	}
	
	public ElementEndParagraph() {
		super("ElementEndParagraph", "INPUT_OUTPUT_SECTION.*.END_PARAGRAPH");
	}



	@Override
	public String exportContents() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedCobolKeywords.END_PARAGRAPH +"\n");
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
		sb.append(" "+ReservedCobolKeywords.END_PARAGRAPH +"\n");
		return sb.toString();
	}

	@Override
	public String toString() {
		return "ElementEndParagraph";
	}

	
}
