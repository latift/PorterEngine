package tr.com.vbt.cobol.parser.subroutines;

import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.AbstractMultipleLinesCommand;
import tr.com.vbt.token.AbstractToken;


public class ElementCall extends AbstractMultipleLinesCommand{
	
	public ElementCall(AbstractToken baseToken,
			List<AbstractToken> tokenListesi, AbstractCommand parent) {
		super("ElementCall","PROCEDURE_DIVISION.*.CALL");
	}
	
	public ElementCall(String elementName,String detailedCobolName) {
		super(elementName, detailedCobolName);
	}

	

	@Override
	public String exportContents() {
		return null;
	}
	

	@Override
	public boolean generateDetailedCobolName() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String exportCommands() {
		return null;
	}

	
	

	
}
