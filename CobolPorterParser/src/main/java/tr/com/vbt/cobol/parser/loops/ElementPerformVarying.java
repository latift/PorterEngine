package tr.com.vbt.cobol.parser.loops;

import java.util.ArrayList;
import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.AbstractMultipleLinesCommand;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.token.AbstractToken;

//PERFORM VARYING JJ FROM 1 BY 1 UNTIL JJ > 10000
//PArams:  variable, from,  by , until
public class ElementPerformVarying extends AbstractMultipleLinesCommand{
	
	private String variable;
	
	private long from;
	 
	private long by;
	
	private long until;
	
	private List<String> conditionList=new ArrayList<String>();
	
	public ElementPerformVarying(AbstractToken baseToken,
			List<AbstractToken> tokenListesi, AbstractCommand parent) {
		super("PerformTimes","PROCEDURE_DIVISION.PERFORM.*.VARYING");
	}
	
	public ElementPerformVarying(String elementName,String detailedCobolName) {
		super(elementName, detailedCobolName);
		
		
	}

	/*
	@Override
	public void parse() {
		
		MultipleLinesCommandsUtility multipleLinesUtil;
		
		ListIterator<AbstractCommand> commandListIterator = commandListesi.listIterator();
		do{
			currentCommand=commandListIterator.next();
		}while(!currentCommand.equals(this)&&commandListIterator.hasNext());
		
		logger.info("*******************************************************************************");
		logger.info("ELEMENTPERFROMVARYING PARSE: Start Command"+currentCommand.getCommandName());
	
		do
		{
			currentCommand=commandListIterator.next();
			System.out.println(currentCommand.toString());
		    if (currentCommand.matches(this.endingCommand)&&levelOfCode==0) { // Kullanılmamış Yeni Bir Paragraf nesnesi görürsen bitir.
		    	logger.info("ELEMENTPERFORMVARYING: End Command"+currentCommand.getCommandName());
		    	logger.info("*******************************************************************************");
				break;
			}
		    manageLevelOfCode();
		   this.addChild(currentCommand);
		}while(commandListIterator.hasNext());
		this.generateDetailedCobolName();
		parseChild();
	}*/
	

	@Override
	public String exportContents() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedCobolKeywords.PERFORM_VARYING +"= "+ this.variable+" "+this.from+" "+this.by+" "+this.until+ "\n");
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
		sb.append(" "+ReservedCobolKeywords.PERFORM_VARYING +"= "+ this.variable+" "+this.from+this.by+" "+this.until+" ");
		if(this.endingCommand!=null){
			sb.append(" "+this.endingCommand.getCommandName());
		}
		sb.append(" \n");
		return sb.toString();
	}

	public String getVariable() {
		return variable;
	}

	public void setVariable(String variable) {
		this.variable = variable;
	}

	public long getFrom() {
		return from;
	}

	public void setFrom(long from) {
		this.from = from;
	}

	public long getBy() {
		return by;
	}

	public void setBy(long by) {
		this.by = by;
	}

	public long getUntil() {
		return until;
	}

	public void setUntil(long until) {
		this.until = until;
	}

	public List<String> getConditionList() {
		return conditionList;
	}

	public void setConditionList(List<String> conditionList) {
		this.conditionList = conditionList;
	}



	

	
}
