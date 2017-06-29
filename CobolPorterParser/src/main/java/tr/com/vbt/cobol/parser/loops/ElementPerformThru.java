package tr.com.vbt.cobol.parser.loops;

import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.AbstractMultipleLinesCommand;
import tr.com.vbt.token.AbstractToken;

//PERFORM paraX Y TIMES --> for (int i=0; i<Y;i++){paraX();}
//Parameters: ParagraghName, RunCount
public class ElementPerformThru extends AbstractMultipleLinesCommand{
	
	private String paragraghName;
	
	private String runCount;
	
	public ElementPerformThru(AbstractToken baseToken,
			List<AbstractToken> tokenListesi, AbstractCommand parent) {
		super("ElementPerformThru","PROCEDURE_DIVISION.*.PERFORM_THRU");
	}
	
	public ElementPerformThru(String elementName,String detailedCobolName) {
		super(elementName, detailedCobolName);
	}

	

	@Override
	public String exportContents() {
		return null;
	}

	@Override
	public boolean generateDetailedCobolName() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String exportCommands() {
		return null;
	}

	public String getParagraghName() {
		return paragraghName;
	}

	public void setParagraghName(String paragraghName) {
		this.paragraghName = paragraghName;
	}

	public String getRunCount() {
		return runCount;
	}

	public void setRunCount(String runCount) {
		this.runCount = runCount;
	}

	

	
}
