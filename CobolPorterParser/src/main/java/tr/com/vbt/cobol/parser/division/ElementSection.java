package tr.com.vbt.cobol.parser.division;

import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.AbstractMultipleLinesCommand;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.token.AbstractToken;

public class ElementSection extends AbstractMultipleLinesCommand{
	
	private String sectionName;
	
	
	
	public ElementSection(AbstractToken baseToken,
			List<AbstractToken> tokenListesi, AbstractCommand parent) {
		super("ElementSection","PROCEDURE_DIVISION.SECTION");
	}
	
	public ElementSection(String paragraphName,String detailedCobolName) {
		super(paragraphName,detailedCobolName);
	}

	
	
/*
	@Override
	public void parse() {
		
		ListIterator<AbstractCommand> commandListIterator = commandListesi.listIterator();
		
		do{
			currentCommand=commandListIterator.next();
		}while(!currentCommand.equals(this)&&commandListIterator.hasNext());
		
		logger.info("*******************************************************************************");
		logger.info("ELEMENT SECTION PARSE: Start Command"+currentCommand.getCommandName());
	
		do
		{
			currentCommand=commandListIterator.next();
			
			System.out.println(currentCommand.toString());
		    if (currentCommand.matches(this.endingCommand)) { // Yeni Bir Paragraf nesnesi görürsen bitir.
		    	logger.info("ELEMENT SECTION PARSE: End Command"+currentCommand.getCommandName());
		    	logger.info("*******************************************************************************");
				break;
			}
		    this.addChild(currentCommand);
		    manageLevelOfCode();
			
		}while(commandListIterator.hasNext());
		this.generateDetailedCobolName();
		parseChild();
	}*/
	

	

	@Override
	public String exportContents() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedCobolKeywords.SECTION +"=\""+ this.sectionName+ " "+"\"\n");
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
		sb.append(" "+ReservedCobolKeywords.SECTION +"=\""+ this.sectionName+" ");
		if(this.endingCommand != null){
			sb.append(this.endingCommand.getCommandName());
		}
	    sb.append("\"\n");
		return sb.toString();
	}

	public String getSectionName() {
		return sectionName;
	}

	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}


	

	
}
