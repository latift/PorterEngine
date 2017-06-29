package tr.com.vbt.natural.parser.conditions;

import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.AbstractMultipleLinesCommand;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.token.AbstractToken;

//PERFORM paraX Y TIMES --> for (int i=0; i<Y;i++){paraX();}
//Parameters: ParagraghName, RunCount
public class ElementElse extends AbstractMultipleLinesCommand{
	
	public ElementElse(AbstractToken baseToken,
			List<AbstractToken> tokenListesi, AbstractCommand parent) {
		super("PerformTimes","GENERAL.*.ELSE");
	}
	
	public ElementElse(String elementName,String detailedCobolName) {
		super(elementName, detailedCobolName);
	}

	

	@Override
	public String exportContents() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedCobolKeywords.ELSE +" \"");
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
		sb.append(ReservedCobolKeywords.ELSE +" 	Ender:"+ this.endingCommand +"\"");
		sb.append("\"\n");
		return sb.toString();
	}


	
}
