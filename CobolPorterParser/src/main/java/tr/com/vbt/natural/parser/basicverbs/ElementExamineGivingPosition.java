package tr.com.vbt.natural.parser.basicverbs;

import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.token.AbstractToken;

//EXAMINE SCR-TXT(II) FOR 'YETISKIN' GIVING POSITION LOC
public class ElementExamineGivingPosition extends AbstractCommand{
	

	private AbstractToken sourceToken;
	
	private AbstractToken searchVar;
	
	private String resultPosition;
	
	
	
	public ElementExamineGivingPosition(String elementName,String detailedCobolName) {
		super(elementName, detailedCobolName);
	}
	
	public ElementExamineGivingPosition(AbstractCommand parent, String elementName,
			String detailedCobolName, List<AbstractCommand> commandListesi) {
		super(parent, elementName, detailedCobolName, commandListesi);
	}
	
	public ElementExamineGivingPosition(String elementName,String detailedCobolName, List<AbstractCommand> commandListesi) {
		super(elementName, detailedCobolName, commandListesi);
	}


	


	@Override
	public String exportCommands() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedNaturalKeywords.EXAMINE_GIVING_POSITION +"=\"");
		sb.append("Source="+sourceToken.getDeger()+" SearchVar="+searchVar.getDeger()+" ResultIndex="+resultPosition);
		sb.append("\"\n");
		return sb.toString();
	}
	

	@Override
	public String exportContents() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedNaturalKeywords.EXAMINE_GIVING_POSITION +"=\"");
		sb.append("Source="+sourceToken.getDeger()+" SearchVar="+searchVar.getDeger()+" ResultIndex="+resultPosition);
		sb.append("\"\n");
		return sb.toString();
	}

	@Override
	public boolean generateDetailedCobolName() {
		// TODO Auto-generated method stub
				return false;
	}

	public AbstractToken getSourceToken() {
		return sourceToken;
	}

	public void setSourceToken(AbstractToken sourceToken) {
		this.sourceToken = sourceToken;
	}

	public AbstractToken getSearchVar() {
		return searchVar;
	}

	public void setSearchVar(AbstractToken searchVar) {
		this.searchVar = searchVar;
	}

	public String getResultPosition() {
		return resultPosition;
	}

	public void setResultPosition(String resultPosition) {
		this.resultPosition = resultPosition;
	}

	

	

	
}
