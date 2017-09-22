package tr.com.vbt.natural.parser.datalayout.db;

import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.AbstractMultipleLinesCommand;
import tr.com.vbt.cobol.parser.DataTypeMapConverter;
import tr.com.vbt.cobol.parser.Levelable;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.token.AbstractToken;

/* *S**  02 T-LOG-USER (1:50)
**/
public class ElementDBMultipleUnitDataTypeNatural extends AbstractMultipleLinesCommand implements Levelable, DataTypeMapConverter {

	protected long levelNumber; // mandatory

	protected String dataName; // mandatory
	
	protected long multipleUnitCount; // mandatory

	@Override
	public String toString() {
		return this.levelNumber+" "+ this.dataName+" MU Count:"+multipleUnitCount;
	}

	
	
	public ElementDBMultipleUnitDataTypeNatural(AbstractToken baseToken,
			List<AbstractToken> tokenListesi, AbstractCommand parent) {
		super(ReservedNaturalKeywords.MU_DATA_TYPE, "DATABASE.MU_DATA_TYPE");
	}

	public ElementDBMultipleUnitDataTypeNatural(String elementName, String detailedCobolName) {
		super(elementName, detailedCobolName);
	}

	@Override
	public String exportContents() {
		
		StringBuilder sb = new StringBuilder();
		sb.append(" " + ReservedNaturalKeywords.DB_MULTIPLE_UNIT_DATA_TYPE + "=\""+
				this.dataName+" " + this.levelNumber + " " + this.dataName +" ");
		
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
		sb.append(" " + ReservedNaturalKeywords.DB_MULTIPLE_UNIT_DATA_TYPE + "=\""+
				this.dataName+" " + this.levelNumber + " " + this.dataName +" ");
		
		sb.append("Ender:"+this.endingCommand);
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



	public long getMultipleUnitCount() {
		return multipleUnitCount;
	}



	public void setMultipleUnitCount(long multipleUnitCount) {
		this.multipleUnitCount = multipleUnitCount;
	}



	@Override
	public void generateDataTypeConversionString(
			List<AbstractCommand> commandList, int commandIndex) {
		// TODO Auto-generated method stub
		
	}


	

	
	
	
}
