package tr.com.vbt.natural.parser.basicverbs;

import java.util.ArrayList;
import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.token.AbstractToken;

/**RESET #I #S-NO #FT-SAP-BILGILERI #PARA-BIRIMI
 * 
 * 
 * 
 * @author 47159500
 *
 */
public class ElementReset extends AbstractCommand{
	
	private List<AbstractToken> resetVariableList=new ArrayList<AbstractToken>();
	
	public ElementReset(AbstractToken baseToken,
			List<AbstractToken> tokenListesi, AbstractCommand parent) {
		super("Reset","GENERAL.*.RESET");
	}

	public ElementReset(String elementName,String detailedCobolName) {
		super(elementName, detailedCobolName);
	}
	
	@Override
	public String exportContents() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedNaturalKeywords.RESET +"=\"");
		for (AbstractToken data : resetVariableList) {
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
		sb.append(" "+ReservedNaturalKeywords.RESET +"=\"");
		for (AbstractToken data : resetVariableList) {
			sb.append(" "+ data.getDeger());	
		}
		sb.append("\"\n");
		return sb.toString();
	}

	public List<AbstractToken> getResetVariableList() {
		return resetVariableList;
	}

	public void setResetVariableList(List<AbstractToken> resetVariableList) {
		this.resetVariableList = resetVariableList;
	}

	



	
}
