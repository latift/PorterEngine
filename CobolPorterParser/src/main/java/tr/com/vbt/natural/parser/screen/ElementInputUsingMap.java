package tr.com.vbt.natural.parser.screen;

import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.token.AbstractToken;


public class ElementInputUsingMap extends AbstractCommand{
	
	private String mapName;
	
	private AbstractToken markToken;
	
	public ElementInputUsingMap(AbstractToken baseToken,
			List<AbstractToken> tokenListesi, AbstractCommand parent) {
		super(ReservedNaturalKeywords.INPUT_USING_MAP,"SCREEN.*.INPUT_USING_MAP");
	}
	
	public ElementInputUsingMap(String elementName,String detailedCobolName) {
		super(elementName, detailedCobolName);
	}

	@Override
	public String exportContents() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedNaturalKeywords.INPUT_USING_MAP +" "+mapName+"\n");
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
		sb.append(" "+ReservedNaturalKeywords.INPUT_USING_MAP +" "+mapName+"\n");
		return sb.toString();
	}

	public String getMapName() {
		return mapName;
	}

	public void setMapName(String mapName) {
		this.mapName = mapName;
	}

	public AbstractToken getMarkToken() {
		return markToken;
	}

	public void setMarkToken(AbstractToken markToken) {
		this.markToken = markToken;
	}

	
	
}
