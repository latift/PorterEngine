package tr.com.vbt.natural.parser.general;

import java.io.IOException;
import java.util.List;
import java.util.ListIterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.AbstractMultipleLinesCommand;
import tr.com.vbt.java.util.RuleNotFoundException;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.util.WriteToFile;

public class ElementNaturalProgram extends AbstractMultipleLinesCommand {

	final static Logger logger = LoggerFactory.getLogger(ElementNaturalProgram.class);
	
	public ElementNaturalProgram(List<AbstractCommand> commandListesi) {
		super("PROGRAM","ELEMENT_PROGRAM",commandListesi);
		
	}
	
	
	public ElementNaturalProgram(String elementName,String detailedCobolName) {
		super(elementName, detailedCobolName);
		
	}
	

	@Override
	public void parse() {
		ListIterator<AbstractCommand> commandListIterator = commandListesi.listIterator();
		do
		{
			currentCommand=commandListIterator.next();
		    System.out.println(currentCommand.toString());
			if (currentCommand.getCommandName().equals(ReservedNaturalKeywords.DEFINE_DATA)) 
			{
				this.addChild(currentCommand);
			}else if(currentCommand.getCommandName().equals(ReservedNaturalKeywords.MAIN_START)){
				this.addChild(currentCommand);
			}
		}while(commandListIterator.hasNext());
		this.generateDetailedCobolName();
		parseChild();
	}

	


	
	


	public void parseBaslat(){
		logger.info("Start of Program Parse");
		this.parse();
		logger.info("End Program Parse");
	}

	public StringBuilder exportBaslat(String fullPath) {
		logger.info("Start of Program Export");
		StringBuilder sb=new StringBuilder();
		this.export(sb);
		try {
			WriteToFile.writeToFile(sb,fullPath);
		} catch (IOException e) {
			logger.debug("",e);
		}
		logger.info("End Program Export");
		return sb;
	}


	
	
	public boolean startGenerateTree() throws RuleNotFoundException{
		this.generateJava();
		return true;  
		
	}



	@Override
	public String exportContents() {
		return "";
	}



	@Override
	public boolean generateDetailedCobolName() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String exportCommands() {
		return "Natural Program";
	}


}


