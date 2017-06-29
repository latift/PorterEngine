package tr.com.vbt.cobol.parser.loops;

import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.AbstractMultipleLinesCommand;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.token.AbstractToken;

//PERFORM paraX Y TIMES --> for (int i=0; i<Y;i++){paraX();}
//Parameters: ParagraghName, RunCount
public class ElementPerformTimes extends AbstractMultipleLinesCommand{
	
	private String paragraghName;
	
	private String runCount;
	
	public ElementPerformTimes(AbstractToken baseToken,
			List<AbstractToken> tokenListesi, AbstractCommand parent) {
		super("PerformTimes","PROCEDURE_DIVISION.*.PERFORM_TIMES");
	}
	
	public ElementPerformTimes(String elementName,String detailedCobolName) {
		super(elementName, detailedCobolName);
	}

	

	@Override
	public String exportContents() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedCobolKeywords.PERFORM_TIMES +"=\""+ this.paragraghName+" "+ runCount+ " Times \n");
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
		sb.append(" "+ReservedCobolKeywords.PERFORM_TIMES +"=\""+ this.paragraghName+" "+ runCount+ " Times \n");
		return sb.toString();
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
