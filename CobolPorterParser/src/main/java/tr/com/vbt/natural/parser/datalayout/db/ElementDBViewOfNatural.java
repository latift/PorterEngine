package tr.com.vbt.natural.parser.datalayout.db;

import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.AbstractMultipleLinesCommand;
import tr.com.vbt.cobol.parser.DataTypeMapConverter;
import tr.com.vbt.cobol.parser.Levelable;
import tr.com.vbt.java.general.JavaConstants;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.patern.MapManager;
import tr.com.vbt.patern.MapManagerNaturalImpl;
import tr.com.vbt.token.AbstractToken;

//*S**1 TESL2 VIEW OF AYK-TESL 
public class ElementDBViewOfNatural extends AbstractMultipleLinesCommand implements Levelable, DataTypeMapConverter{

	protected int levelNumber;
	
	//TESL2
	protected String variableName;

	//AYK-TESL
	protected String typeName;
	
	
	public ElementDBViewOfNatural(AbstractToken baseToken,
			List<AbstractToken> tokenListesi, AbstractCommand parent) {
		super("DataType", "DATABASE.VIEW_OF");
	}

	public ElementDBViewOfNatural(String elementName, String detailedCobolName) {
		super(elementName, detailedCobolName);
	}

	
	
	public ElementDBViewOfNatural(int levelNumber, String variableName, String typeName) {
		super(ReservedNaturalKeywords.VIEW_OF, "DATABASE.VIEW_OF");
		this.levelNumber = levelNumber;
		this.variableName = variableName;
		this.typeName = typeName;
	}

	@Override
	public String exportContents() {
		StringBuilder sb = new StringBuilder();
		sb.append(" " + ReservedNaturalKeywords.VIEW_OF + "=\""
				+ this.levelNumber + " " + this.variableName +" viewOf " + this.typeName);
		sb.append(JavaConstants.NEW_LINE);
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
		sb.append(" " + ReservedNaturalKeywords.VIEW_OF + "=\""
				+ this.levelNumber + " " + this.variableName +" viewOf " + this.typeName);
		sb.append("Ender:"+this.endingCommand);
		sb.append(JavaConstants.NEW_LINE);
		return sb.toString();
	}

	public int getLevelNumber() {
		return levelNumber;
	}

	public void setLevelNumber(int levelNumber) {
		this.levelNumber = levelNumber;
	}

	


	@Override
	public String toString() {
		return this.commandName+ "  "+this.levelNumber+"  "+ this.variableName+"  "+ this.typeName;
	}

	public String getVariableName() {
		return variableName;
	}

	public void setVariableName(String variableName) {
		this.variableName = variableName;
	}



	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	@Override
	public void generateDataTypeConversionString(
			List<AbstractCommand> commandList, int commandIndex) {
		//*S**01 TRAFIK VIEW OF TFK-TRAFIK --> TRAFIK
		MapManager manager=MapManagerNaturalImpl.getInstance();
		this.dataTypeGetterMapString=this.variableName.replaceAll("-", "_");
		this.dataTypeSetterMapString=this.variableName.replaceAll("-", "_");
		manager.registerCommandToPojoMap(variableName, this);
		return;
		
	}

	

	@Override
	public String getDataName() {
	return this.variableName;
	}

	@Override
	public void setDataName(String dataName) {
		variableName=dataName;
		
	}


	

}
