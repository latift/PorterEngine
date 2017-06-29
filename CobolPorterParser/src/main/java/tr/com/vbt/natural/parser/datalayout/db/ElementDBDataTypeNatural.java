package tr.com.vbt.natural.parser.datalayout.db;

import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.DataTypeMapConverter;
import tr.com.vbt.cobol.parser.Levelable;
import tr.com.vbt.java.util.Utility;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.patern.MapManager;
import tr.com.vbt.patern.MapManagerNaturalImpl;
import tr.com.vbt.token.AbstractToken;

/* 1 CLIENT-ID (N8)
	1 LAST-NAME (A20)
	1 FIRST-NAME (A20)
	1 MIDDLE-INITIAL (A1)
**/
public class ElementDBDataTypeNatural extends AbstractCommand implements Levelable, DataTypeMapConverter {

	protected int levelNumber; // mandatory

	protected String dataName; // mandatory

	@Override
	public String toString() {
		return this.dataName+" Len:"+this.length+"  Level:"+this.levelNumber;
	}

	private String dataType; // 9, X, A //mandatory

	private int length; // (5) Parantez içindeki ifade //optional
	
	private int lengthAfterDot; // Noktanin Solundali

	
	public ElementDBDataTypeNatural(AbstractToken baseToken,
			List<AbstractToken> tokenListesi, AbstractCommand parent) {
		super(ReservedNaturalKeywords.DB_DATA_TYPE, "DATABASE.DB_DATA_TYPE");
	}

	public ElementDBDataTypeNatural(String elementName, String detailedCobolName) {
		super(elementName, detailedCobolName);
	}

	@Override
	public String exportContents() {
		StringBuilder sb = new StringBuilder();
		sb.append(" " + ReservedNaturalKeywords.DB_DATA_TYPE + "=\""
				+ this.levelNumber + " " + this.dataName + " " + " Length:"
				+ this.length
				+ " DecimalLength:" +this.lengthAfterDot
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
		sb.append(" " + ReservedNaturalKeywords.DB_DATA_TYPE + "=\""
				+ this.levelNumber + " " + this.dataName + " " + " Length:"
				+ this.length 
				+ " DecimalLength:" +this.lengthAfterDot);
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

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}



	@Override
	public void generateDataTypeConversionString( List<AbstractCommand> commandList, int commandIndex) {
		//*S**01 TRAFIK VIEW OF TFK-TRAFIK --> TRAFIK
		String pojoName;
		AbstractCommand parentCommand;
		MapManager manager=MapManagerNaturalImpl.getInstance();
				
				//DBDatatypeNAtural ın en küçük leveli 2 olabilir. 1 olan viewOf dur.
				//*S**  02 TFK-ADTRH  	--> TRAFIK.getTfkAdtrh();
				if(this.levelNumber==2){
					parentCommand=Utility.findParentCommand(this, commandList,commandIndex);
					pojoName=Utility.viewNameToPojoName(this.dataName);
					
					//*S**  02 TFK-ADTRH  	--> TRAFIK.getTfkAdtrh();
					this.dataTypeGetterMapString=parentCommand.getDataTypeGetterMapString()+".get"+pojoName+"()";
					
					//*S**  02 TFK-ADTRH  	--> TRAFIK.setTfkAdtrh(
					this.dataTypeSetterMapString=parentCommand.getDataTypeGetterMapString()+".set"+pojoName+"(";
					manager.registerCommandToPojoMap(this.dataName, this);
				}else if(this.levelNumber==3){
					parentCommand=Utility.findParentCommand(this, commandList,commandIndex);
					pojoName=Utility.viewNameToPojoName(this.dataName);
					this.dataTypeGetterMapString=parentCommand.getDataTypeGetterMapString()+".get"+pojoName+"()";
					this.dataTypeSetterMapString=parentCommand.getDataTypeGetterMapString()+".set"+pojoName+"(";
					manager.registerCommandToPojoMap(this.dataName, this);
				}
				return;
	}

	public int getLengthAfterDot() {
		return lengthAfterDot;
	}

	public void setLengthAfterDot(int lengthAfterDot) {
		this.lengthAfterDot = lengthAfterDot;
	}
	
	
	
	
}
