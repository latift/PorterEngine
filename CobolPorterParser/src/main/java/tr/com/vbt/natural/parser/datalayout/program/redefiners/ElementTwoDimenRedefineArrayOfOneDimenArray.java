package tr.com.vbt.natural.parser.datalayout.program.redefiners;

import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.DataTypeMapConverter;
import tr.com.vbt.cobol.parser.Levelable;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.token.AbstractToken;

/* 		1 #DIZI-KLIM(A3/1:500,1:60)	  Local Normal Two Dimension Dizi Tanımı
 *   	1 #DIZI-KLIM(A3/500,60)	  Local Normal Two Dimension Dizi Tanımı
 * 
**/
public class ElementTwoDimenRedefineArrayOfOneDimenArray extends AbstractCommand implements Levelable, DataTypeMapConverter {

	protected int levelNumber; // 1

	protected String dataName; // DIZI-KLIM
	
	private String dataType; // A3
	
	private int lengthAfterDot; // Noktanin Solundali

	private int arrayLength; // 500 Parantez içindeki ifade
	
	private int arrayLength2;  //60 
	
	private AbstractCommand redefinedCommand;


	@Override
	public String toString() {
		return this.dataName+" Len:"+this.dataType+"  Level:"+this.levelNumber+" ArraySize:"+arrayLength;
	}

		
	public ElementTwoDimenRedefineArrayOfOneDimenArray(AbstractToken baseToken,
			List<AbstractToken> tokenListesi, AbstractCommand parent) {
		super(ReservedNaturalKeywords.PROGRAM_TWO_DIMENSION_ARRAY_DATA_TYPE, "GENERAL.PROGRAM_TWO_DIMENSION_ARRAY_DATA_TYPE");
	}

	public ElementTwoDimenRedefineArrayOfOneDimenArray(String elementName, String detailedCobolName) {
		super(elementName, detailedCobolName);
	}

	@Override
	public String exportContents() {
		StringBuilder sb = new StringBuilder();
		sb.append(" " + ReservedNaturalKeywords.PROGRAM_TWO_DIMENSION_ARRAY_DATA_TYPE + "=\""
				+ this.levelNumber + " " + this.dataName + " " + " Length:"
				+ this.arrayLength + " DecimalLength:" 
				+"\"\n");
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
		sb.append(" " + ReservedNaturalKeywords.PROGRAM_TWO_DIMENSION_ARRAY_DATA_TYPE + "=\""
				+ this.levelNumber + " " + this.dataName + " " + " Length:"
				+ this.arrayLength  );
		sb.append("\"\n");
		return sb.toString();
	}

	public String getDataName() {
		return dataName;
	}

	public void setDataName(String dataName) {
		this.dataName = dataName;
	}

	public int getLevelNumber() {
		return levelNumber;
	}

	public void setLevelNumber(int levelNumber) {
		this.levelNumber = levelNumber;
	}

	

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	@Override
	public void generateDataTypeConversionString(
			List<AbstractCommand> commandList, int commandIndex) {
		// TODO Auto-generated method stub
		
	}


	public int getLengthAfterDot() {
		return lengthAfterDot;
	}


	public void setLengthAfterDot(int lengthAfterDot) {
		this.lengthAfterDot = lengthAfterDot;
	}


	public int getArrayLength() {
		return arrayLength;
	}


	public void setArrayLength(int arrayLength) {
		this.arrayLength = arrayLength;
	}


	public int getArrayLength2() {
		return arrayLength2;
	}


	public void setArrayLength2(int arrayLength2) {
		this.arrayLength2 = arrayLength2;
	}


	public AbstractCommand getRedefinedCommand() {
		return redefinedCommand;
	}


	public void setRedefinedCommand(AbstractCommand redefinedCommand) {
		this.redefinedCommand = redefinedCommand;
	}
	
	
	
	
}
