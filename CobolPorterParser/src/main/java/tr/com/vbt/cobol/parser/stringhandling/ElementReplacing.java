package tr.com.vbt.cobol.parser.stringhandling;

import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.AbstractMultipleLinesCommand;
import tr.com.vbt.token.AbstractToken;

public class ElementReplacing extends AbstractMultipleLinesCommand{

	public ElementReplacing(AbstractCommand parent, String commandName,
			String detailedCobolName, List<AbstractCommand> commandListesi) {
		super(parent, commandName, detailedCobolName, commandListesi);
		// TODO Auto-generated constructor stub
	}

	public ElementReplacing(AbstractToken baseToken,
			List<AbstractToken> tokenListesi, AbstractCommand parent) {
		super("ElementReplacing","PROCEDURE_DIVISION.*.REPLACING");
	}
	
	@Override
	public String exportContents() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String exportCommands() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean generateDetailedCobolName() {
		// TODO Auto-generated method stub
		return false;
	}
	

	
}
