package tr.com.vbt.natural.parser.loops;

import java.util.ArrayList;
import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.AbstractMultipleLinesCommand;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.token.AbstractToken;

//PERFORM paraX Y TIMES --> for (int i=0; i<Y;i++){paraX();}
//Parameters: ParagraghName, RunCount
public class ElementRepeatWhile extends AbstractMultipleLinesCommand{
	
	private List<AbstractToken> conditionList=new ArrayList<AbstractToken>();
	
	public ElementRepeatWhile(AbstractToken baseToken,
			List<AbstractToken> tokenListesi, AbstractCommand parent) {
		super("ElementRepeatWhile","GENERAL.*.REPEAT_WHILE");
	}
	
	
	public ElementRepeatWhile(String elementName,String detailedCobolName) {
		super(elementName, detailedCobolName);
	}

	

	@Override
	public String exportContents() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedNaturalKeywords.REPEAT_WHILE +"=\"");
		for (AbstractToken data : conditionList) {
			sb.append(" "+ data.getDeger());	
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
		sb.append(" "+ReservedNaturalKeywords.REPEAT_UNTIL +"=\"");
		for (AbstractToken data : conditionList) {
			sb.append(" "+ data.getDeger());	
		}
		sb.append(" 	Ender:"+ this.endingCommand);
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
