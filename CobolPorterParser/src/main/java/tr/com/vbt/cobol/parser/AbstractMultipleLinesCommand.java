package tr.com.vbt.cobol.parser;

import java.util.List;
import java.util.ListIterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tr.com.vbt.java.util.MultipleLinesCommandsUtility;

public abstract class AbstractMultipleLinesCommand extends AbstractCommand implements MultipleLines{
	
	final static Logger logger = LoggerFactory.getLogger(AbstractMultipleLinesCommand.class);
	
	protected AbstractEndingCommand endingCommand; 
	
	public AbstractMultipleLinesCommand(AbstractCommand parent,
			String commandName, String detailedCobolName,
			List<AbstractCommand> commandListesi) {
		super(parent, commandName, detailedCobolName, commandListesi);
		// TODO Auto-generated constructor stub
	}

	
	public AbstractMultipleLinesCommand(String commandName,
			String detailedCobolName2, List<AbstractCommand> commandListesi2) {
		super(commandName, detailedCobolName2, commandListesi2);
		// TODO Auto-generated constructor stub
	}

	public AbstractMultipleLinesCommand(String commandName,
			String detailedCobolName2,int satirNumarasi) {
		super(commandName, detailedCobolName2,satirNumarasi);
		// TODO Auto-generated constructor stub
	}
	
	public AbstractMultipleLinesCommand(String commandName,
			String detailedCobolName2) {
		super(commandName, detailedCobolName2);
	}
	

	@Override
	public void registerEnderCommand(AbstractEndingCommand enderCommandToRegister) {
		this.endingCommand=enderCommandToRegister;
	}


	
	protected void manageLevelOfCode() {
		if(MultipleLinesCommandsUtility.isStarter(currentCommand)){
			this.levelOfCode++;
		}else if(MultipleLinesCommandsUtility.isEnder(currentCommand)){
			this.levelOfCode--;
		}
	}
	
	
	@Override
	public void parse() {
		
		
		ListIterator<AbstractCommand> commandListIterator = commandListesi.listIterator();
		
		do{
			currentCommand=commandListIterator.next();
		}while(!currentCommand.equals(this)&&commandListIterator.hasNext());
		
		System.out.println(currentCommand);
		logger.info("*******************************************************************************");
		
		do
		{
			currentCommand=commandListIterator.next();
			
			System.out.println(currentCommand.toString());
		    if (currentCommand.equals(this.endingCommand)) { // Yeni Bir Paragraf nesnesi görürsen bitir.
		    	logger.info("ELEMENT PARSE: End Command"+currentCommand.getCommandName());
		    	logger.info("Orjinal Kod Parse Edildi:"+this.getSourceCodeSentence());
		    	logger.info("*******************************************************************************");
				break;
			}
		    this.addChild(currentCommand);
		    manageLevelOfCode();
			
		}while(commandListIterator.hasNext());
		this.generateDetailedCobolName();
		parseChild();

	}

	public void calculateLevel(Levelable previousLevelable){
		this.levelNumber=previousLevelable.getLevelNumber()+1;
	}
	

	
}
