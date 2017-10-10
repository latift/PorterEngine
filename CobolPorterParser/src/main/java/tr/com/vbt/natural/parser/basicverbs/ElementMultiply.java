package tr.com.vbt.natural.parser.basicverbs;

import java.util.ArrayList;
import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.token.AbstractToken;

//MULTIPLY A BY B C
/*
 * In syntax-1, A and B are multipled and the result is stored in B (B=A*B). A and C are multipled and the result is stored in C (C=A*C).
 *
 *
 */
//4868 MULTIPLY ROUNDED ALDOVIZ BY CAPRAZ_KUR GIVING SATDOVIZ
public class ElementMultiply extends AbstractCommand{
	
	
	private AbstractToken multiplyNum;
	
	private AbstractToken multiplierNum;
	
	private AbstractToken resultNum;
	
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
		sb.append(multiplyNum+" ");
		sb.append("Multiply By:");
		sb.append(multiplierNum+", ");
		sb.append("Result:");
		sb.append(resultNum+", ");
	
		sb.append("\"\n");
		return sb.toString();
	}
	

	@Override
	public String exportContents() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedCobolKeywords.MULTIPLY +"=\"");
		sb.append(multiplyNum+" ");
		sb.append("Multiply By:");
		sb.append(multiplierNum+", ");
		sb.append("Result:");
		sb.append(resultNum+", ");
	
		sb.append("\"\n");
		return sb.toString();
	}

	@Override
	public boolean generateDetailedCobolName() {
		// TODO Auto-generated method stub
				return false;
	}

	public AbstractToken getMultiplyNum() {
		return multiplyNum;
	}

	public void setMultiplyNum(AbstractToken multiplyNum) {
		this.multiplyNum = multiplyNum;
	}

	public AbstractToken getMultiplierNum() {
		return multiplierNum;
	}

	public void setMultiplierNum(AbstractToken multiplierNum) {
		this.multiplierNum = multiplierNum;
	}

	public AbstractToken getResultNum() {
		return resultNum;
	}

	public void setResultNum(AbstractToken resultNum) {
		this.resultNum = resultNum;
	}

	

	

	

	
}
