package tr.com.vbt.natural.parser.datalayout.program;

import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.DataTypeMapConverter;
import tr.com.vbt.cobol.parser.Levelable;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.token.AbstractToken;

/* 		1 #DIZI-KLIM(A3/1:500)	  Local Normal Dizi Tanımı
 * 
 *  0630   2 FAIZ_MEB             (N06.2/10)
 * 
**/
public class ElementProgramOneDimensionArrayNatural extends AbstractCommand implements Levelable, DataTypeMapConverter {

	protected long levelNumber; // 1

	protected String dataName; // DIZI-KLIM
	
	private String dataType; // A3

	private long arrayLength; // 500 Parantez içindeki ifade
	
	private long length;
	
	private long lengthAfterDot;
	
	
	@Override
	public String toString() {
		return "Dataname:"+this.dataName+"   DataType:"+dataType.substring(0,1)+"   Len:"+this.dataType.substring(1)+"   lengthAfterDot:"+lengthAfterDot +"   Level:"+this.levelNumber+"  ArraySize:"+arrayLength;
	}

		
	public ElementProgramOneDimensionArrayNatural(AbstractToken baseToken,
			List<AbstractToken> tokenListesi, AbstractCommand parent) {
		super(ReservedNaturalKeywords.PROGRAM_ARRAY_DATA_TYPE, "GENERAL.PROGRAM_ARRAY_DATA_TYPE");
	}

	public ElementProgramOneDimensionArrayNatural(String elementName, String detailedCobolName) {
		super(elementName, detailedCobolName);
	}

	@Override
	public String exportContents() {
		StringBuilder sb = new StringBuilder();
		sb.append(" " + ReservedNaturalKeywords.PROGRAM_ARRAY_DATA_TYPE + "=\""
				+ this.levelNumber + " " + this.dataName + " " + " DataType:"
				+ this.dataType + " ArrayLength:"+this.arrayLength 
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
		sb.append(" " + ReservedNaturalKeywords.PROGRAM_ARRAY_DATA_TYPE + "=\""
				+ this.levelNumber + " " + this.dataName + " " + " Length:"
				+ this.dataType + " ArrayLength:"+this.arrayLength  );
		sb.append("\"\n");
		return sb.toString();
	}

	public String getDataName() {
		return dataName;
	}

	public void setDataName(String dataName) {
		this.dataName = dataName;
	}

	public long getLevelNumber() {
		return levelNumber;
	}

	public void setLevelNumber(long levelNumber) {
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


	public long getArrayLength() {
		return arrayLength;
	}


	public void setArrayLength(long arrayLength) {
		this.arrayLength = arrayLength;
	}


	public long getLength() {
		return length;
	}


	public void setLength(long length) {
		this.length = length;
	}


	public long getLengthAfterDot() {
		return lengthAfterDot;
	}


	public void setLengthAfterDot(long lengthAfterDot) {
		this.lengthAfterDot = lengthAfterDot;
	}



	
	
}
