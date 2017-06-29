package tr.com.vbt.cobol.parser;

import java.util.List;

public abstract class AbstractEndingCommand extends AbstractCommand implements EndingLines{
	
	protected AbstractMultipleLinesCommand starterCommand; 
	
	public AbstractEndingCommand(AbstractCommand parent,
			String commandName, String detailedCobolName,
			List<AbstractCommand> commandListesi) {
		super(parent, commandName, detailedCobolName, commandListesi);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void export(StringBuilder sb) { //Ending Command Export yapmamalı.
		return;
	};
	
	public AbstractEndingCommand(String commandName,
			String detailedCobolName2, List<AbstractCommand> commandListesi2) {
		super(commandName, detailedCobolName2, commandListesi2);
	}

	public AbstractEndingCommand(String commandName,
			String detailedCobolName2, int satirNumarasi) {
		super(commandName, detailedCobolName2,satirNumarasi);
	}

	public AbstractEndingCommand(String commandName,
			String detailedCobolName2) {
		super(commandName, detailedCobolName2);
	}
	public AbstractEndingCommand(String commandName, String detailedCobolName2, boolean isVisualCommand) {
		super(commandName, detailedCobolName2,isVisualCommand);
	}

	@Override
	public void registerStarterCommand(AbstractMultipleLinesCommand lastStarterCommand) {
		this.starterCommand=lastStarterCommand;
	}

	public void calculateLevel(Levelable previousLevelable){
		this.levelNumber=previousLevelable.getLevelNumber()-1;
	}
	
}
