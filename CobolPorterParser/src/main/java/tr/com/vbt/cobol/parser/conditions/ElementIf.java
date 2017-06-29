package tr.com.vbt.cobol.parser.conditions;

import java.util.ArrayList;
import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.AbstractMultipleLinesCommand;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.token.AbstractToken;

//PERFORM paraX Y TIMES --> for (int i=0; i<Y;i++){paraX();}
//Parameters: ParagraghName, RunCount
public class ElementIf extends AbstractMultipleLinesCommand{
	
	private List<String> conditionList=new ArrayList<String>();
	
	public ElementIf(AbstractToken baseToken,
			List<AbstractToken> tokenListesi, AbstractCommand parent) {
		super("If","PROCEDURE_DIVISION.*.IF");
	}
	
	
	public ElementIf(String elementName,String detailedCobolName) {
		super(elementName, detailedCobolName);
	}

	

	@Override
	public String exportContents() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedCobolKeywords.IF +"=\"");
		for (String data : conditionList) {
			sb.append(" "+ data);	
		}
		if(this.endingCommand!=null){
			sb.append(" 	Ender:"+this.endingCommand.toString() +" ");
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
		sb.append(" "+ReservedCobolKeywords.IF +"=\"");
		for (String data : conditionList) {
			sb.append(" "+ data);	
		}
		if(this.endingCommand!=null){
			sb.append(" 	Ender:"+ this.endingCommand.toString() +" ");
		}
		sb.append("\"\n");
		return sb.toString();
	}

	public List<String> getConditionList() {
		return conditionList;
	}

	public void setConditionList(List<String> conditionList) {
		this.conditionList = conditionList;
	}

	

	

	
}
