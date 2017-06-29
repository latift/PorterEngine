package tr.com.vbt.cobol.parser.division;

import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.AbstractMultipleLinesCommand;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.token.AbstractToken;

public class ElementConfSection extends AbstractMultipleLinesCommand{

	private String SOURCE_COMPUTER; 
	
	private String OBJECT_COMPUTER;
	
	public ElementConfSection(AbstractToken baseToken, List<AbstractToken> tokenListesi, AbstractCommand parent) {
		super("ConfigurationSection","ENVIRONMENT_DIVISION.CONFIGURATION_SECTION");
	}
	
	public ElementConfSection(String elementName,String detailedCobolName) {
		super(elementName, detailedCobolName);
	}


	@Override
	public String exportContents() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedCobolKeywords.CONFIGURATION_SECTION);
		sb.append(" "+ReservedCobolKeywords.SOURCE_COMPUTER +"=\""+ this.SOURCE_COMPUTER);
		sb.append(" "+ReservedCobolKeywords.OBJECT_COMPUTER +"=\""+ this.OBJECT_COMPUTER+"\"\n");
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
		sb.append(" "+ReservedCobolKeywords.CONFIGURATION_SECTION);
		sb.append(" "+ReservedCobolKeywords.SOURCE_COMPUTER +"=\""+ this.SOURCE_COMPUTER);
		sb.append(" "+ReservedCobolKeywords.OBJECT_COMPUTER +"=\""+ this.OBJECT_COMPUTER+"\"\n");
		return sb.toString();
	}

	public String getSOURCE_COMPUTER() {
		return SOURCE_COMPUTER;
	}

	public void setSOURCE_COMPUTER(String sOURCE_COMPUTER) {
		SOURCE_COMPUTER = sOURCE_COMPUTER;
	}

	public String getOBJECT_COMPUTER() {
		return OBJECT_COMPUTER;
	}

	public void setOBJECT_COMPUTER(String oBJECT_COMPUTER) {
		OBJECT_COMPUTER = oBJECT_COMPUTER;
	}









}
