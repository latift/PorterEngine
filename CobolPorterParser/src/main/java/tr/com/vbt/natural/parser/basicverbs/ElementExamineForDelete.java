package tr.com.vbt.natural.parser.basicverbs;

import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.token.AbstractToken;

//EXAMINE #FIRST-NAME FOR ‘SIR’ DELETE
public class ElementExamineForDelete extends AbstractCommand{
	
	private String mainString;
	
	private String searchString;
	
	public ElementExamineForDelete(AbstractToken baseToken, List<AbstractToken> tokenListesi, AbstractCommand parent) {
		super("ElementExamine","EXAMINE");
	}
	
	public ElementExamineForDelete(String elementName,String detailedCobolName) {
		super(elementName, detailedCobolName);
	}

	@Override
	public String exportContents() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedNaturalKeywords.EXAMINE+" "+mainString+" "+" "+searchString);
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
		sb.append(" "+ReservedNaturalKeywords.EXAMINE);
		return sb.toString();
	}

	public String getMainString() {
		return mainString;
	}

	public void setMainString(String mainString) {
		this.mainString = mainString;
	}

	public String getSearchString() {
		return searchString;
	}

	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}


	

	
}
