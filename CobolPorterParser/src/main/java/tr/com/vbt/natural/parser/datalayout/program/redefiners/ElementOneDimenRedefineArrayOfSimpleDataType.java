package tr.com.vbt.natural.parser.datalayout.program.redefiners;

import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.DataTypeMapConverter;
import tr.com.vbt.cobol.parser.Levelable;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.natural.parser.datalayout.program.ElementProgramDataTypeNatural;
import tr.com.vbt.token.AbstractToken;

/* 		*S**1 TAX-EXC-TEXT(A50)
*S**		1 REDEFINE TAX-EXC-TEXT
*S**  			2 TAX-EXC-ITEM(A5/10)
 * 
**/
public class ElementOneDimenRedefineArrayOfSimpleDataType extends AbstractCommand implements Levelable, DataTypeMapConverter {

	protected int levelNumber; // 2

	protected String dataName; // TAX-EXC-ITEM
	
	private String dataType; // A5

	private int arrayLength; // 10 Parantez içindeki ifade
	
	private ElementProgramDataTypeNatural redefinedDataType;


	@Override
	public String toString() {
		return this.dataName+" ["+this.arrayLength+"]  Level:"+this.levelNumber;
	}

		
	public ElementOneDimenRedefineArrayOfSimpleDataType(AbstractToken baseToken,
			List<AbstractToken> tokenListesi, AbstractCommand parent) {
		super(ReservedNaturalKeywords.PROGRAM_ONE_DIMENSION_REDEFINE_ARRAY_OF_SIMPLE, "GENERAL.PROGRAM_ONE_DIMENSION_REDEFINE_ARRAY_OF_SIMPLE");
	}

	public ElementOneDimenRedefineArrayOfSimpleDataType(String elementName, String detailedCobolName) {
		super(elementName, detailedCobolName);
	}

	@Override
	public String exportContents() {
		StringBuilder sb = new StringBuilder();
		sb.append(" " + ReservedNaturalKeywords.PROGRAM_ONE_DIMENSION_REDEFINE_ARRAY_OF_SIMPLE + "=\""
				+ this.levelNumber + " " + this.dataName + " " + " ["
				+ this.arrayLength+"]"  );
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
		sb.append(" " + ReservedNaturalKeywords.PROGRAM_ONE_DIMENSION_REDEFINE_ARRAY_OF_SIMPLE + "=\""
				+ this.levelNumber + " " + this.dataName + " " + " ["
				+ this.arrayLength+"]"  );
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

	public ElementProgramDataTypeNatural getRedefinedDataType() {
		return redefinedDataType;
	}


	public void setRedefinedDataType(ElementProgramDataTypeNatural redefinedDataType) {
		this.redefinedDataType = redefinedDataType;
	}


	public int getArrayLength() {
		return arrayLength;
	}

	public void setArrayLength(int arrayLength) {
		this.arrayLength = arrayLength;
	}

	
	
}
