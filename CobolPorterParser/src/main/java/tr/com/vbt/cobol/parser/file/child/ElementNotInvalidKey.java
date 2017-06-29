package tr.com.vbt.cobol.parser.file.child;

import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.AbstractMultipleLinesCommand;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.token.AbstractToken;



public class ElementNotInvalidKey extends AbstractMultipleLinesCommand{
	
	
	public ElementNotInvalidKey(AbstractToken baseToken,
			List<AbstractToken> tokenListesi, AbstractCommand parent) {
		super("ElementNotInvalidKey","PROCEDURE_DIVISION.*.NOT_INVALID_KEY");
	}
	
	public ElementNotInvalidKey(String elementName,String detailedCobolName) {
		super(elementName, detailedCobolName);
	}
	
	public ElementNotInvalidKey() {
		super("ElementNotInvalidKey","PROCEDURE_DIVISION.*.NOT_INVALID_KEY");
	}

	@Override
	public String exportContents() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedCobolKeywords.NOT_INVALID_KEY +"\n");
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
		sb.append(" "+ReservedCobolKeywords.NOT_INVALID_KEY +"\n");
		return sb.toString();
	}

	

	
	


	
}
