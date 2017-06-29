package tr.com.vbt.cobol.parser.file.child;

import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.AbstractMultipleLinesCommand;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.token.AbstractToken;

public class ElementInvalidKey extends AbstractMultipleLinesCommand{
	
	
	public ElementInvalidKey(AbstractToken baseToken,
			List<AbstractToken> tokenListesi, AbstractCommand parent) {
		super("ElementInvalidKey","PROCEDURE_DIVISION.*.INVALID_KEY");
	}
	
	public ElementInvalidKey(String elementName,String detailedCobolName) {
		super(elementName, detailedCobolName);
	}
	
	public ElementInvalidKey() {
		super("ElementInvalidKey","PROCEDURE_DIVISION.*.INVALID_KEY");
	}

	@Override
	public String exportContents() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedCobolKeywords.INVALID_KEY +"\n");
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
		sb.append(" "+ReservedCobolKeywords.INVALID_KEY +"\n");
		return sb.toString();
	}

	

	
	


	
}
