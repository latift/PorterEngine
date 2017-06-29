package tr.com.vbt.natural.parser.loops;

//STACK_COMMAND 'PERP101'
import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.token.AbstractToken;

//STACK_COMMAND 'PERP101'
public class ElementStackCommand extends AbstractCommand{
	
	AbstractToken command;
	
	public ElementStackCommand(AbstractToken baseToken,
			List<AbstractToken> tokenListesi, AbstractCommand parent) {
		super("ElementStackTopCommand","GENERAL.*.STACK_COMMAND");
	}
	
	public ElementStackCommand(String elementName,String detailedCobolName) {
		super(elementName, detailedCobolName);
	}

	
	

	@Override
	public String exportContents() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedNaturalKeywords.STACK_COMMAND +"\n");
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
		sb.append(" "+ReservedNaturalKeywords.STACK_COMMAND +"\n");
		return sb.toString();
	}

	public AbstractToken getCommand() {
		return command;
	}

	public void setCommand(AbstractToken command) {
		this.command = command;
	}



	
	



	
}
