package tr.com.vbt.natural.parser.conditions;

import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.AbstractMultipleLinesCommand;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.token.AbstractToken;

/**
 DECIDE ON EVERY #SECIM2                                                                 
*S**      VALUE 1                                                                               
*S**        FETCH RETURN 'AYKPFMM2' /* #T-I                                                     
*S**      VALUE 2                                                                               
*S**        FETCH RETURN 'AYKPUCRT' /* #T-I                                                     
*S**      NONE                                                                                  
*S**        REINPUT 'HATALI SECIM.!'   MARK *#SECIM2                                            
*S**    END-DECIDE         
 * @author 47159500
 *
 */
public class ElementDecideOn extends AbstractMultipleLinesCommand{
	
	private AbstractToken condition;
	
	public ElementDecideOn(AbstractToken baseToken,
			List<AbstractToken> tokenListesi, AbstractCommand parent) {
		super("ElementDecideOn","GENERAL.*.DECIDE_ON");
	}
	
	
	public ElementDecideOn(String elementName,String detailedCobolName) {
		super(elementName, detailedCobolName);
	}

	

	@Override
	public String exportContents() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedNaturalKeywords.DECIDE_ON +"=\"");
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
		sb.append(" "+ReservedNaturalKeywords.DECIDE_ON +"=\"");
		if(this.endingCommand!=null){
			sb.append(" 	Ender:"+ this.endingCommand.toString() +" ");
		}
		sb.append("\"\n");
		return sb.toString();
	}


	public AbstractToken getCondition() {
		return condition;
	}


	public void setCondition(AbstractToken condition) {
		this.condition = condition;
	}





}
