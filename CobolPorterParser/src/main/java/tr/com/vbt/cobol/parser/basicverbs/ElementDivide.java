package tr.com.vbt.cobol.parser.basicverbs;

import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.token.AbstractToken;

//DIVIDE A INTO B  --> B (B=B/A)
public class ElementDivide extends AbstractCommand{
	
	//DIVIDE A INTO B  --> B (B=B/A)
	
	private String sourceNum;
	
	private String destNum;
	
	private String giving;
	
	private String remainder;
	
	
	public ElementDivide(String elementName,String detailedCobolName) {
		super(elementName, detailedCobolName);
	}
	
	public ElementDivide(AbstractCommand parent, String elementName,
			String detailedCobolName, List<AbstractCommand> commandListesi) {
		super(parent, elementName, detailedCobolName, commandListesi);
	}
	
	public ElementDivide(String elementName,String detailedCobolName, List<AbstractCommand> commandListesi) {
		super(elementName, detailedCobolName, commandListesi);
	}

	public ElementDivide(AbstractToken baseToken,
			List<AbstractToken> tokenListesi, AbstractCommand parent) {
		super("ElementDivide","PROCEDURE_DIVISION.*.DIVIDE");
	}
	


	@Override
	public String exportCommands() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedCobolKeywords.DIVIDE +"=\"");
		sb.append(sourceNum+" ");
		sb.append("Divide By: ");
		sb.append(destNum);
		sb.append("\"\n");
		return sb.toString();
	}
	

	@Override
	public String exportContents() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedCobolKeywords.DIVIDE +"=\"");
		sb.append(sourceNum+" ");
		sb.append("Divide By: ");
		sb.append(destNum);
		sb.append("\"\n");
		return sb.toString();
	}

	@Override
	public boolean generateDetailedCobolName() {
		// TODO Auto-generated method stub
				return false;
	}

	public String getSourceNum() {
		return sourceNum;
	}

	public void setSourceNum(String sourceNum) {
		this.sourceNum = sourceNum;
	}

	public String getDestNum() {
		return destNum;
	}

	public void setDestNum(String destNum) {
		this.destNum = destNum;
	}

	public String getGiving() {
		return giving;
	}

	public void setGiving(String giving) {
		this.giving = giving;
	}

	public String getRemainder() {
		return remainder;
	}

	public void setRemainder(String remainder) {
		this.remainder = remainder;
	}

	
	
	
}
