package tr.com.vbt.natural.parser.conditions;

import java.util.ArrayList;
import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.AbstractMultipleLinesCommand;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.token.AbstractToken;

//PERFORM paraX Y TIMES --> for (int i=0; i<Y;i++){paraX();}
//Parameters: ParagraghName, RunCount
public class ElementAcceptIf extends AbstractCommand{
	
	private List<AbstractToken> conditionList=new ArrayList<AbstractToken>();
	
	public ElementAcceptIf(AbstractToken baseToken,
			List<AbstractToken> tokenListesi, AbstractCommand parent) {
		super("ElementAcceptIf","GENERAL.*.ACCEPT_IF");
	}
	
	
	public ElementAcceptIf(String elementName,String detailedCobolName) {
		super(elementName, detailedCobolName);
	}

	

	@Override
	public String exportContents() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedNaturalKeywords.ACCEPT_IF +"=\"");
		for (AbstractToken data : conditionList) {
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
		sb.append(" "+ReservedNaturalKeywords.ACCEPT_IF +"=\"");
		for (AbstractToken data : conditionList) {
			sb.append(" "+ data.getDeger());	
		}
		sb.append("\"\n");
		return sb.toString();
	}


	public List<AbstractToken> getConditionList() {
		return conditionList;
	}


	public void setConditionList(List<AbstractToken> conditionList) {
		this.conditionList = conditionList;
	}

	

	

	

	
}
