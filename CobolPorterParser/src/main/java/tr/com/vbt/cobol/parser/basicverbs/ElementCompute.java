package tr.com.vbt.cobol.parser.basicverbs;

import java.util.ArrayList;
import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.token.AbstractToken;

//COMPUTE WS-NUMC= (WS-NUM1 * WS-NUM2) - (WS-NUMA / WS-NUMB) + WS-NUM3.
public class ElementCompute extends AbstractCommand{
	
	private List<AbstractToken> aritmethicOperators=new ArrayList<AbstractToken>();
	
	public ElementCompute(AbstractToken baseToken,
			List<AbstractToken> tokenListesi, AbstractCommand parent) {
		super("ElementCompute","PROCEDURE_DIVISION.*.COMPUTE");
	}
	
	public ElementCompute(String elementName,String detailedCobolName) {
		super(elementName, detailedCobolName);
	}

	

	@Override
	public String exportContents() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedCobolKeywords.COMPUTE +"=\""+ this.aritmethicOperators+"\n");
		
		for (AbstractToken token : aritmethicOperators) {
			sb.append(" "+ token.toString());	
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
		sb.append(" "+ReservedCobolKeywords.COMPUTE +"=\""+ this.aritmethicOperators+"\n");
		
		for (AbstractToken token : aritmethicOperators) {
			sb.append(" "+ token.toString());	
		}
		sb.append("\"\n");
		
		return sb.toString();
	}

	public List<AbstractToken> getAritmethicOperators() {
		return aritmethicOperators;
	}

	public void setAritmethicOperators(List<AbstractToken> aritmethicOperators) {
		this.aritmethicOperators = aritmethicOperators;
	}

	

	
}
