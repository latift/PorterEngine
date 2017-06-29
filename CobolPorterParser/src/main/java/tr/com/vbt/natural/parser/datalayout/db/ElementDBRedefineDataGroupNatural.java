package tr.com.vbt.natural.parser.datalayout.db;

import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.AbstractMultipleLinesCommand;
import tr.com.vbt.cobol.parser.DataTypeMapConverter;
import tr.com.vbt.cobol.parser.Levelable;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.token.AbstractToken;

//03 REDEFINE T-USER-DATE-TIME  
public class ElementDBRedefineDataGroupNatural extends AbstractMultipleLinesCommand implements Levelable , DataTypeMapConverter{

	protected String redefineName;

	protected int levelNumber;
	
	public ElementDBRedefineDataGroupNatural(AbstractToken baseToken,
			List<AbstractToken> tokenListesi, AbstractCommand parent) {
		super("DataType", "DATABASE.DB_REDEFINE_GROUP_DATA");
	}

	public ElementDBRedefineDataGroupNatural(String elementName, String detailedCobolName) {
		super(elementName, detailedCobolName);
	}

	
	@Override
	public String exportContents() {
		StringBuilder sb = new StringBuilder();
		sb.append(" " + ReservedNaturalKeywords.DB_REDEFINE_GROUP_DATA + "=\""
				+redefineName+ this.levelNumber + " " + this.redefineName);
		sb.append("Ender:"+this.endingCommand);
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
		StringBuilder sb = new StringBuilder();
		sb.append(" " + ReservedNaturalKeywords.DB_REDEFINE_GROUP_DATA + "=\""
				+redefineName+ this.levelNumber + " " + this.redefineName +" ");
		
		sb.append("Ender:"+this.endingCommand);
		sb.append("\"\n");
		return sb.toString();
	}

	public int getLevelNumber() {
		return levelNumber;
	}

	public void setLevelNumber(int levelNumber) {
		this.levelNumber = levelNumber;
	}

	

	public String getRedefineName() {
		return redefineName;
	}

	public void setRedefineName(String redefineName) {
		this.redefineName = redefineName;
	}

	@Override
	public String toString() {
		return this.commandName+ "  "+this.redefineName+"  "+ this.levelNumber;
	}

	@Override
	public void generateDataTypeConversionString(
			List<AbstractCommand> commandList, int commandIndex) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getDataName() {
	return this.redefineName;
	}

	@Override
	public void setDataName(String dataName) {
		redefineName=dataName;
		
	}

	



	

}
