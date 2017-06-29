package tr.com.vbt.natural.parser.basicverbs;

import java.util.ArrayList;
import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.token.AbstractToken;

public class ElementDisplay extends AbstractCommand{
	
	private List<String> dataToDisplay=new ArrayList<String>();
	
	public ElementDisplay(AbstractToken baseToken,
			List<AbstractToken> tokenListesi, AbstractCommand parent) {
		super("Display","GENERAL.*.DISPLAY");
	}

	public ElementDisplay(String elementName,String detailedCobolName) {
		super(elementName, detailedCobolName);
	}
	
	
	

	@Override
	public String exportContents() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedNaturalKeywords.DISPLAY +"=\"");
		for (String data : dataToDisplay) {
			sb.append(" "+ data);	
		}
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
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedNaturalKeywords.DISPLAY +"=\"");
		for (String data : dataToDisplay) {
			sb.append(" "+ data);	
		}
		sb.append("\n");
		return sb.toString();
	}

	public List<String> getDataToDisplay() {
		return dataToDisplay;
	}

	public void setDataToDisplay(List<String> dataToDisplay) {
		this.dataToDisplay = dataToDisplay;
	}

	

	

	
}
