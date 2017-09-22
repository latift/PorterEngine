package tr.com.vbt.cobol.parser.datalayout;

import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.AbstractMultipleLinesCommand;
import tr.com.vbt.cobol.parser.Levelable;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.token.AbstractToken;

//01 WS-NUM1
public class ElementGroupDataType extends AbstractMultipleLinesCommand implements Levelable{

	protected String dataName;

	protected long levelNumber;
	
	private long occuringCount;
	
	private String redefines;
	
	public ElementGroupDataType(AbstractToken baseToken,
			List<AbstractToken> tokenListesi, AbstractCommand parent) {
		super("DataType", "WORKING-STORAGE-SECTION.GROUP_DATA_TYPE");
	}

	public ElementGroupDataType(String elementName, String detailedCobolName) {
		super(elementName, detailedCobolName);
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
		StringBuilder sb = new StringBuilder();
		sb.append(" " + ReservedCobolKeywords.GROUP_DATA_TYPE + "=\""
				+ this.levelNumber + " " + this.dataName + "\"\n");
		return sb.toString();
	}

	@Override
	public boolean generateDetailedCobolName() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String exportCommands() {
		StringBuilder sb = new StringBuilder();
		sb.append(" " + ReservedCobolKeywords.GROUP_DATA_TYPE + "=\""
				+ this.levelNumber + " " + this.dataName +" ");
		
		if(this.endingCommand != null){
			sb.append(this.endingCommand.getCommandName());
		}
	    sb.append("\"\n");
		return sb.toString();
	}

	public long getLevelNumber() {
		return levelNumber;
	}

	public void setLevelNumber(long levelNumber) {
		this.levelNumber = levelNumber;
	}

	public String getDataName() {
		return dataName;
	}

	public void setDataName(String dataName) {
		this.dataName = dataName;
	}

	@Override
	public String toString() {
		return this.commandName+ "  "+this.dataName+"  "+ this.levelNumber;
	}

	public long getOccuringCount() {
		return occuringCount;
	}

	public void setOccuringCount(long occuringCount) {
		this.occuringCount = occuringCount;
	}

	public String getRedefines() {
		return redefines;
	}

	public void setRedefines(String redefines) {
		this.redefines = redefines;
	}

	

}
