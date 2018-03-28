package tr.com.vbt.natural.parser.conditions;

import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;

public abstract class AbstractConditionCommand extends AbstractCommand{

	
	public AbstractConditionCommand(AbstractCommand parent, String commandName,String detailedCobolName, List<AbstractCommand> commandListesi) {
		super(parent, commandName, detailedCobolName, commandListesi);
		
	}
	

	
	public AbstractConditionCommand(String commandName, String detailedCobolName2,
			List<AbstractCommand> commandListesi2) {
		super(commandName, detailedCobolName2, commandListesi2);
	}



	public AbstractConditionCommand(String commandName,
			String detailedCobolName2, int satirNumarasi) {
		super(commandName,detailedCobolName2,satirNumarasi);
	}

}
