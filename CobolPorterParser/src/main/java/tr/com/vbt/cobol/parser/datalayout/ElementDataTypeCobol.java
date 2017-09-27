package tr.com.vbt.cobol.parser.datalayout;

import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.Levelable;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.token.AbstractToken;

//01 WS-NUM1 PIC 9
// 01 WS-NUM3 PIC S9(3)V9(2) VALUE -123.45.
public class ElementDataTypeCobol extends AbstractCommand implements Levelable {

	protected long levelNumber; // mandatory

	protected String dataName; // mandatory

	private String sign; // + veya //optional
	
	private long occuringCount;

	@Override
	public String toString() {
		return this.dataName+" Len:"+this.length+" DecLen:"+this.decimalLength+"  Level:"+this.levelNumber;
	}

	private String dataType; // 9, X, A //mandatory

	private long length; // (5) Parantez içindeki ifade //optional

	private Long decimalLength; // (5) Parantez içindeki ifade //optional

	private String redefines;

	private String value;
	
	private Long valueAsInt;

	public ElementDataTypeCobol(AbstractToken baseToken,
			List<AbstractToken> tokenListesi, AbstractCommand parent) {
		super("DataType", "WORKING-STORAGE-SECTION.DATA_TYPE");
	}

	public ElementDataTypeCobol(String elementName, String detailedCobolName) {
		super(elementName, detailedCobolName);
	}

	@Override
	public String exportContents() {
		StringBuilder sb = new StringBuilder();
		sb.append(" " + ReservedCobolKeywords.DATA_TYPE + "=\""
				+ this.levelNumber + " " + this.dataName + " " + " Length:"
				+ this.length + " DecimalLength:" + this.decimalLength
				+ "  Redefines:"+this.redefines 
				+ "  Value:"+this.value 
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
		sb.append(" " + ReservedCobolKeywords.DATA_TYPE + "=\""
				+ this.levelNumber + " " + this.dataName + " " + " Length:"
				+ this.length + " DecimalLength:" + this.decimalLength);
		
		if(this.redefines!=null){
			sb.append("  Redefines:"+this.redefines);
		}
		
		if(this.value!=null){
			sb.append("  Value:"+this.value); 
		}
		if(this.occuringCount!=0){
			sb.append("  Occurs:"+this.occuringCount); 
		}
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

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public long getLength() {
		return length;
	}

	public void setLength(long length) {
		this.length = length;
	}

	public long getDecimalLength() {
		return decimalLength;
	}

	public void setDecimalLength(long decimalLength) {
		this.decimalLength = decimalLength;
	}

	public String getRedefines() {
		return redefines;
	}

	public void setRedefines(String redefines) {
		this.redefines = redefines;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public long getOccuringCount() {
		return occuringCount;
	}

	public void setOccuringCount(long occuringCount) {
		this.occuringCount = occuringCount;
	}

	public long getValueAsInt() {
		return valueAsInt;
	}

	public void setValueAsInt(long valueAsInt) {
		this.valueAsInt = valueAsInt;
	}

	
	
}
