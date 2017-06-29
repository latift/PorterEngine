package tr.com.vbt.natural.parser.general;

import java.util.ArrayList;
import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.AbstractEndingCommand;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.token.AbstractToken;

//RP1. REPEAT
public class ElementAmpersand extends AbstractEndingCommand{
	
	private List<AbstractToken> dynamicCode=new ArrayList<AbstractToken>();
	
	public ElementAmpersand(AbstractToken baseToken,
			List<AbstractToken> tokenListesi, AbstractCommand parent) {
		super("ElementAmpersand","GENERAL.*.AMPERSAND");
	}
	
	public ElementAmpersand(String elementName,String detailedCobolName) {
		super(elementName, detailedCobolName);
	}

	
	

	@Override
	public String exportContents() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedNaturalKeywords.AMPERSAND +"\n");
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
		sb.append(" "+ReservedNaturalKeywords.AMPERSAND);
		sb.append("\"\n");
		return sb.toString();
	}

	public List<AbstractToken> getDynamicCode() {
		return dynamicCode;
	}

	public void setDynamicCode(List<AbstractToken> dynamicCode) {
		this.dynamicCode = dynamicCode;
	}

	
}
