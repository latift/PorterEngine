package tr.com.vbt.cobol.parser.basicverbs;

import java.util.ArrayList;
import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.token.AbstractToken;

//MULTIPLY A BY B C
/*
 * In syntax-1, A and B are multipled and the result is stored in B (B=A*B). A and C are multipled and the result is stored in C (C=A*C).
 */
public class ElementMultiply extends AbstractCommand{
	
	
	private String sourceNum=new String();
	
	private List<String> destNum=new ArrayList<String>();
	
	public ElementMultiply(String elementName,String detailedCobolName) {
		super(elementName, detailedCobolName);
	}
	
	public ElementMultiply(AbstractCommand parent, String elementName,
			String detailedCobolName, List<AbstractCommand> commandListesi) {
		super(parent, elementName, detailedCobolName, commandListesi);
	}
	
	public ElementMultiply(String elementName,String detailedCobolName, List<AbstractCommand> commandListesi) {
		super(elementName, detailedCobolName, commandListesi);
	}

	public ElementMultiply(AbstractToken baseToken,
			List<AbstractToken> tokenListesi, AbstractCommand parent) {
		super("ElementMultiply","PROCEDURE_DIVISION.*.MULTIPLY");
	}
	

	@Override
	public String exportCommands() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedCobolKeywords.MULTIPLY +"=\"");
		sb.append(sourceNum+" ");
		sb.append("Multiply By:");
		for (String dest : destNum) {
			sb.append(dest+", ");
		}
		sb.append("\"\n");
		return sb.toString();
	}
	

	@Override
	public String exportContents() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedCobolKeywords.MULTIPLY +"=\"");
		sb.append(sourceNum+" ");
		sb.append("Multiply By:");
		for (String dest : destNum) {
			sb.append(dest+", ");
		}
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

	public List<String> getDestNum() {
		return destNum;
	}

	public void setDestNum(List<String> destNum) {
		this.destNum = destNum;
	}

	

	

	

	
}
