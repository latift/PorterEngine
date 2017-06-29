package tr.com.vbt.natural.parser.basicverbs;

import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.token.AbstractToken;

/**CLOSE PRINTER(3)
 * 
 * 
 * 
 * @author 47159500
 *
 */
public class ElementClosePrinter extends AbstractCommand{
	
	private int closeNum;
	
	public ElementClosePrinter(AbstractToken baseToken,
			List<AbstractToken> tokenListesi, AbstractCommand parent) {
		super("ClosePrinter","GENERAL.*.CLOSE_PRINTER");
	}

	public ElementClosePrinter(String elementName,String detailedCobolName) {
		super(elementName, detailedCobolName);
	}
	
	@Override
	public String exportContents() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedNaturalKeywords.CLOSE_PRINTER +"=\"");
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
		sb.append(" "+ReservedNaturalKeywords.CLOSE_PRINTER +"=\"");
		sb.append("\"\n");
		return sb.toString();
	}

	public int getCloseNum() {
		return closeNum;
	}

	public void setCloseNum(int closeNum) {
		this.closeNum = closeNum;
	}




	
}
