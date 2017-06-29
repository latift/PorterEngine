package tr.com.vbt.cobol.parser.division;

import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.AbstractMultipleLinesCommand;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.token.AbstractToken;

public class ElementParagraph extends AbstractMultipleLinesCommand{
	
	private String paragraphName;
	
	
	
	public ElementParagraph(AbstractToken baseToken,
			List<AbstractToken> tokenListesi, AbstractCommand parent) {
		super("ElementParagraph","PROCEDURE_DIVISION.PARAGRAPH");
	}
	
	public ElementParagraph(String paragraphName,String detailedCobolName) {
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
		logger.info("ELEMENT PARAGHRAF PARSE: Start Command"+currentCommand.getCommandName());
	
		do
		{
			currentCommand=commandListIterator.next();
			
		    System.out.println(currentCommand.toString());
		    if (currentCommand instanceof ElementParagraph
		    		||currentCommand instanceof ElementParagraphMain
		    		||currentCommand instanceof ElementStopRun) { // Yeni Bir Paragraf nesnesi görürsen bitir.
		    	logger.info("ELEMENT PARAGHRAF PARSE: End Command"+currentCommand.getCommandName());
		    	logger.info("*******************************************************************************");
				break;
			}
			if (currentCommand.getCommandName().equals(ReservedCobolKeywords.DISPLAY)) 
			{
				this.addChild(currentCommand);
			}else if(currentCommand.getCommandName().equals(ReservedCobolKeywords.MOVE)){
				this.addChild(currentCommand);
			}else if(currentCommand.getCommandName().equals(ReservedCobolKeywords.ADD)){
				this.addChild(currentCommand);
			}else if(currentCommand.getCommandName().equals(ReservedCobolKeywords.PERFORM)){
				this.addChild(currentCommand);
			}else if(currentCommand.getCommandName().equals(ReservedCobolKeywords.PERFORM_TIMES)){
				this.addChild(currentCommand);
			}
			
		}while(commandListIterator.hasNext());
		this.generateDetailedCobolName();
		parseChild();
	}
	*/

	@Override
	public String exportContents() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedCobolKeywords.PARAGRAPH +"=\""+ this.paragraphName);
		if(this.endingCommand!=null){
			sb.append(" 	Ender:"+ this.endingCommand.toString());
		}
		sb.append("\"\n");
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
		sb.append(" "+ReservedCobolKeywords.PARAGRAPH +"=\""+ this.paragraphName);
		if(this.endingCommand!=null){
			sb.append(" 	Ender:"+ this.endingCommand.toString());
		}
		sb.append("\"\n");
		return sb.toString();
	}

	public String getParagraphName() {
		return paragraphName;
	}

	public void setParagraphName(String paragraphName) {
		this.paragraphName = paragraphName;
	}

	

	
}
