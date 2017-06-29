package tr.com.vbt.cobol.parser.file;

import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.AbstractMultipleLinesCommand;
import tr.com.vbt.token.AbstractToken;


public class ElementMerge extends AbstractMultipleLinesCommand{
	
	public ElementMerge(AbstractToken baseToken,
			List<AbstractToken> tokenListesi, AbstractCommand parent) {
		super("PerformTimes","PROCEDURE_DIVISION.*.PERFORM_TIMES");
	}
	
	public ElementMerge(String elementName,String detailedCobolName) {
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
