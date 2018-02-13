package tr.com.vbt.natural.parser.screen;

import java.util.ArrayList;
import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.token.AbstractToken;

public class ElementReInput extends AbstractCommand{
	
	private List<AbstractToken> dataToDisplay=new ArrayList<AbstractToken>();
	
	private AbstractToken markValue;
	
	private boolean isFull;
	
	
	public ElementReInput(AbstractToken baseToken,
			List<AbstractToken> tokenListesi, AbstractCommand parent) {
		super("ReInput","DATABASE.*.REINPUT");
	}

	public ElementReInput(String elementName,String detailedCobolName) {
		super(elementName, detailedCobolName);
	}
	
	
	

	@Override
	public String exportContents() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedNaturalKeywords.REINPUT +"=\"");
		for (AbstractToken data : dataToDisplay) {
			sb.append(" "+ data.getDeger());	
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
		sb.append(" "+ReservedNaturalKeywords.REINPUT +"=\"");
		for (AbstractToken data : dataToDisplay) {
			sb.append(" "+ data.getDeger());	
		}
		sb.append("\n");
		return sb.toString();
	}



	public AbstractToken getMarkValue() {
		return markValue;
	}

	public void setMarkValue(AbstractToken markValue) {
		this.markValue = markValue;
	}

	public List<AbstractToken> getDataToDisplay() {
		return dataToDisplay;
	}

	public void setDataToDisplay(List<AbstractToken> dataToDisplay) {
		this.dataToDisplay = dataToDisplay;
	}

	public boolean isFull() {
		return isFull;
	}

	public void setFull(boolean isFull) {
		this.isFull = isFull;
	}


	
}
