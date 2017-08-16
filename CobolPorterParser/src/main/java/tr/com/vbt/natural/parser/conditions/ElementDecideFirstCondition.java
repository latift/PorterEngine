package tr.com.vbt.natural.parser.conditions;

import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.AbstractMultipleLinesCommand;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.token.AbstractToken;

/**
0420    DECIDE FOR FIRST CONDITION
0430    WHEN *PF-KEY='PF3'
0440       ESCAPE ROUTINE
0450    WHEN *PF-KEY='PF2'
0460       FETCH 'IDGPANAM'
0470    WHEN *PF-KEY='PF7'
0480       IF D_BAS > 15 THEN
0490           D_BAS:=D_BAS - 15
0500       ELSE
0510       D_BAS:=1
0520       END-IF
0530    WHEN *PF-KEY='PF8'
0540       Z:=KAYIT_SAY - 14
0550       IF D_BAS < Z THEN
0560          D_BAS :=D_BAS + 15
0570       END-IF
0580    WHEN NONE
0590          IGNORE
0600        END-DECIDE      
 * @author 47159500
 *
 */
public class ElementDecideFirstCondition extends AbstractMultipleLinesCommand{
	
	public ElementDecideFirstCondition(AbstractToken baseToken,
			List<AbstractToken> tokenListesi, AbstractCommand parent) {
		super("ElementDecideFirstCondition","GENERAL.*.DECIDE_FIRST_CONDITION");
	}
	
	
	public ElementDecideFirstCondition(String elementName,String detailedCobolName) {
		super(elementName, detailedCobolName);
	}

	

	@Override
	public String exportContents() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedNaturalKeywords.DECIDE_FIRST_CONDITION +"=\"");
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
		sb.append(" "+ReservedNaturalKeywords.DECIDE_FIRST_CONDITION +"=\"");
		if(this.endingCommand!=null){
			sb.append(" 	Ender:"+ this.endingCommand.toString() +" ");
		}
		sb.append("\"\n");
		return sb.toString();
	}


}
