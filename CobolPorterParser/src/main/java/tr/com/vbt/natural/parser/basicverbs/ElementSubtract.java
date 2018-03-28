package tr.com.vbt.natural.parser.basicverbs;

import java.util.ArrayList;
import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.token.AbstractToken;

//SUBTRACT A B FROM C D  -->
/*In syntax-1, A and B are added and subtracted from C. The Result is stored in C (C=C-(A+B)). 
 * A and B are added and subtracted from D. The result is stored in D (D=D-(A+B)).
 * 
 */
public class ElementSubtract extends AbstractCommand{
	
	private List<AbstractToken> sourceNum=new ArrayList<AbstractToken>();
	
	private List<AbstractToken> destNum=new ArrayList<AbstractToken>();
	
	
	
	public ElementSubtract(String elementName,String detailedCobolName) {
		super(elementName, detailedCobolName);
	}
	
	public ElementSubtract(AbstractCommand parent, String elementName,
			String detailedCobolName, List<AbstractCommand> commandListesi) {
		super(parent, elementName, detailedCobolName, commandListesi);
	}
	
	public ElementSubtract(String elementName,String detailedCobolName, List<AbstractCommand> commandListesi) {
		super(elementName, detailedCobolName, commandListesi);
	}

	public ElementSubtract(AbstractToken baseToken,
			List<AbstractToken> tokenListesi, AbstractCommand parent) {
		super("ElementSubctract","PROCEDURE_DIVISION.*.SUBTRACT");
	}
	

	@Override
	public String exportCommands() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedCobolKeywords.SUBTRACT +"=\"");
		sb.append("SOURCE: ");
		for (AbstractToken src : sourceNum) {
			sb.append(src.getDeger()+" ");
		}
		sb.append("DESTINATION: ");
		for (AbstractToken src : destNum) {
			sb.append(src.getDeger()+" ");
		}
		sb.append("\"\n");
		return sb.toString();
	}
	

	@Override
	public String exportContents() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedCobolKeywords.SUBTRACT +"=\"");
		sb.append("SOURCE: ");
		for (AbstractToken src : sourceNum) {
			sb.append(src.getDeger()+" ");
		}
		sb.append("DESTINATION: ");
		for (AbstractToken src : destNum) {
			sb.append(src.getDeger()+" ");
		}
		sb.append("\"\n");
		return sb.toString();
	}

	@Override
	public boolean generateDetailedCobolName() {
		// TODO Auto-generated method stub
				return false;
	}

	public List<AbstractToken> getSourceNum() {
		return sourceNum;
	}

	public void setSourceNum(List<AbstractToken> sourceNum) {
		this.sourceNum = sourceNum;
	}

	public List<AbstractToken> getDestNum() {
		return destNum;
	}

	public void setDestNum(List<AbstractToken> destNum) {
		this.destNum = destNum;
	}

	
}
