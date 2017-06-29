package tr.com.vbt.natural.parser.loops;

//ESCAPE BOTTOM (RP1.)
import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.token.AbstractToken;

//RP1. REPEAT
public class ElementStackTopCommand extends AbstractCommand{
	
	AbstractToken command;
	
	public ElementStackTopCommand(AbstractToken baseToken,
			List<AbstractToken> tokenListesi, AbstractCommand parent) {
		super("ElementStackTopCommand","GENERAL.*.STACK_TOP_COMMAND");
	}
	
	public ElementStackTopCommand(String elementName,String detailedCobolName) {
		super(elementName, detailedCobolName);
	}

	
	

	@Override
	public String exportContents() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedNaturalKeywords.STACK_TOP_COMMAND +"\n");
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
		sb.append(" "+ReservedNaturalKeywords.STACK_TOP_COMMAND +"\n");
		return sb.toString();
	}

	public AbstractToken getCommand() {
		return command;
	}

	public void setCommand(AbstractToken command) {
		this.command = command;
	}



	
	



	
}
