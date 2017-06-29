package tr.com.vbt.natural.parser.basicverbs;

import java.util.ArrayList;
import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.token.AbstractToken;


public class ElementFetchReturn extends AbstractCommand{
	
	private String programName;
	
	private List<AbstractToken> paragraghParameters=new ArrayList<AbstractToken>();
	
	public ElementFetchReturn(AbstractToken baseToken,
			List<AbstractToken> tokenListesi, AbstractCommand parent) {
		super("ElementFetchReturn","GENERAL.*.FETCH_RETURN");
	}
	
	public ElementFetchReturn(String elementName,String detailedCobolName) {
		super(elementName, detailedCobolName);
	}


	@Override
	public String exportContents() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedNaturalKeywords.FETCH_RETURN +"=\""+ this.programName+"\"\n");
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
		sb.append(" "+ReservedNaturalKeywords.FETCH_RETURN +"=\""+ this.programName+"\"\n");
		return sb.toString();
	}

	public String getProgramName() {
		return programName;
	}

	public void setProgramName(String programName) {
		this.programName = programName;
	}

	public List<AbstractToken> getParagraghParameters() {
		return paragraghParameters;
	}

	public void setParagraghParameters(List<AbstractToken> paragraghParameters) {
		this.paragraghParameters = paragraghParameters;
	}

	
	
	

	
}
