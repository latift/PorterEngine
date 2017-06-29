package tr.com.vbt.natural.parser.basicverbs;

import java.util.ArrayList;
import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.token.AbstractToken;

//SET KEY PF3 PF4 

//SET KEY PF11='%W>' 
public class ElementSetKey extends AbstractCommand{
	
	private List<AbstractToken> parametersOfSetKey=new ArrayList<AbstractToken>();
	
	
	public ElementSetKey(String elementName,String detailedCobolName) {
		super(elementName, detailedCobolName);
	}
	
	public ElementSetKey(AbstractCommand parent, String elementName,
			String detailedCobolName, List<AbstractCommand> commandListesi) {
		super(parent, elementName, detailedCobolName, commandListesi);
	}
	
	public ElementSetKey(String elementName,String detailedCobolName, List<AbstractCommand> commandListesi) {
		super(elementName, detailedCobolName, commandListesi);
	}


	


	@Override
	public String exportCommands() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedNaturalKeywords.SET_KEY +" ");
		for (AbstractToken src : parametersOfSetKey) {
			sb.append(src.getDeger()+" ");
		}
		sb.append("\n");
		return sb.toString();
	}
	

	@Override
	public String exportContents() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedNaturalKeywords.SET_KEY +" ");
		for (AbstractToken src : parametersOfSetKey) {
			sb.append(src.getDeger()+" ");
		}
		sb.append("\n");
		return sb.toString();
	}

	@Override
	public boolean generateDetailedCobolName() {
		// TODO Auto-generated method stub
				return false;
	}

	public List<AbstractToken> getParametersOfSetKey() {
		return parametersOfSetKey;
	}

	public void setParametersOfSetKey(List<AbstractToken> parametersOfSetKey) {
		this.parametersOfSetKey = parametersOfSetKey;
	}



	

	

	

	
}
