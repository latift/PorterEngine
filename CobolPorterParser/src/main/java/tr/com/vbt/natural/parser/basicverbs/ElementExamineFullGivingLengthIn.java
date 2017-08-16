package tr.com.vbt.natural.parser.basicverbs;

import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.token.AbstractToken;

// EXAMINE FULL MAP.PASNUM2 FOR ' ' GIVING LENGTH IN PASLEN1
public class ElementExamineFullGivingLengthIn extends AbstractCommand{
	

	private AbstractToken arrayToken;
	
	private AbstractToken searchVar;
	
	private String resultIndex;
	
	
	
	public ElementExamineFullGivingLengthIn(String elementName,String detailedCobolName) {
		super(elementName, detailedCobolName);
	}
	
	public ElementExamineFullGivingLengthIn(AbstractCommand parent, String elementName,
			String detailedCobolName, List<AbstractCommand> commandListesi) {
		super(parent, elementName, detailedCobolName, commandListesi);
	}
	
	public ElementExamineFullGivingLengthIn(String elementName,String detailedCobolName, List<AbstractCommand> commandListesi) {
		super(elementName, detailedCobolName, commandListesi);
	}


	


	@Override
	public String exportCommands() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedNaturalKeywords.EXAMINE_FULL_GIVING_LENGTH_IN +"=\"");
		sb.append("Array="+arrayToken.getDeger()+" SearchVar="+searchVar.getDeger()+" ResultIndex="+resultIndex);
		sb.append("\"\n");
		return sb.toString();
	}
	

	@Override
	public String exportContents() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedNaturalKeywords.EXAMINE_FULL_GIVING_LENGTH_IN +"=\"");
		sb.append("Array="+arrayToken.getDeger()+" SearchVar="+searchVar.getDeger()+" ResultIndex="+resultIndex);
		sb.append("\"\n");
		return sb.toString();
	}

	@Override
	public boolean generateDetailedCobolName() {
		// TODO Auto-generated method stub
				return false;
	}

	public AbstractToken getArrayToken() {
		return arrayToken;
	}

	public void setArrayToken(AbstractToken arrayToken) {
		this.arrayToken = arrayToken;
	}

	public AbstractToken getSearchVar() {
		return searchVar;
	}

	public void setSearchVar(AbstractToken searchVar) {
		this.searchVar = searchVar;
	}

	public String getResultIndex() {
		return resultIndex;
	}

	public void setResultIndex(String resultIndex) {
		this.resultIndex = resultIndex;
	}

	

	

	
}
