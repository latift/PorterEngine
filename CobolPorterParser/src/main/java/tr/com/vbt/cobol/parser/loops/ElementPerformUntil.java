package tr.com.vbt.cobol.parser.loops;

import java.util.ArrayList;
import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.AbstractMultipleLinesCommand;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.token.AbstractToken;

//PERFORM UNTIL S-BITTI
public class ElementPerformUntil extends AbstractMultipleLinesCommand{
	
	private List<String> conditionList=new ArrayList<String>();
	
	public ElementPerformUntil(AbstractToken baseToken,
			List<AbstractToken> tokenListesi, AbstractCommand parent) {
		super("PerformUntil","PROCEDURE_DIVISION.*.PERFORM_UNTIL");
	}
	
	public ElementPerformUntil(String elementName,String detailedCobolName) {
		super(elementName, detailedCobolName);
	}

	
	

	@Override
	public String exportContents() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedCobolKeywords.PERFORM_UNTIL +"\n");
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
		sb.append(" "+ReservedCobolKeywords.PERFORM_UNTIL +"\n");
		return sb.toString();
	}

	public List<String> getConditionList() {
		return conditionList;
	}

	public void setConditionList(List<String> conditionList) {
		this.conditionList = conditionList;
	}

	



	
}
