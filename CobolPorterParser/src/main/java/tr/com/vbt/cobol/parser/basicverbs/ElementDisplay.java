package tr.com.vbt.cobol.parser.basicverbs;

import java.util.ArrayList;
import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.token.AbstractToken;

//PROCEDURE DIVISION.
public class ElementDisplay extends AbstractCommand{
	
	private List<AbstractToken> dataToDisplay=new ArrayList<AbstractToken>();
	
	public ElementDisplay(AbstractToken baseToken,
			List<AbstractToken> tokenListesi, AbstractCommand parent) {
		super("ElementDisplay","GENERAL.*.DISPLAY");
	}
	
	public ElementDisplay(String elementName,String detailedCobolName) {
		super(elementName, detailedCobolName);
	}


	@Override
	public String exportContents() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedNaturalKeywords.DISPLAY +"=\"");
		for (AbstractToken data : dataToDisplay) {
			sb.append(" "+ data.getDeger().toString());	
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
		for (AbstractToken data : dataToDisplay) {
			sb.append(" "+ data);	
		}
		sb.append("\n");
		return sb.toString();
	}

	public List<AbstractToken> getDataToDisplay() {
		return dataToDisplay;
	}

	public void setDataToDisplay(List<AbstractToken> dataToDisplay) {
		this.dataToDisplay = dataToDisplay;
	}


	
	
}
