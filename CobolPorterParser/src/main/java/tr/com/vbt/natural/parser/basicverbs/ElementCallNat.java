package tr.com.vbt.natural.parser.basicverbs;

import java.util.ArrayList;
import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.token.AbstractToken;

//CALLNAT 'HANMSGN2' #ERR-NUM #PROGRAM #PREFIX #SUFFIX #STRING
public class ElementCallNat extends AbstractCommand{
	
	private String paragraghName;
	
	private List<AbstractToken> paragraghParameters=new ArrayList<AbstractToken>();
	
	public ElementCallNat(AbstractToken baseToken,
			List<AbstractToken> tokenListesi, AbstractCommand parent) {
		super("CallNat","GENERAL.*.CALLNAT");
	}
	
	public ElementCallNat(String elementName,String detailedCobolName) {
		super(elementName, detailedCobolName);
	}


	@Override
	public String exportContents() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedNaturalKeywords.CALLNAT +"=\""+ this.paragraghName+" ");
		for (AbstractToken parameter : paragraghParameters) {
			sb.append(parameter.getDeger()+" ");	
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
		sb.append(" "+ReservedNaturalKeywords.CALLNAT +"=\""+ this.paragraghName+" ");
		for (AbstractToken parameter : paragraghParameters) {
			sb.append(parameter.getDeger()+" ");	
		}
		sb.append("\"\n");
		return sb.toString();
	}

	public String getParagraghName() {
		return paragraghName;
	}

	public void setParagraghName(String paragraghName) {
		this.paragraghName = paragraghName;
	}

	public List<AbstractToken> getParagraghParameters() {
		return paragraghParameters;
	}

	public void setParagraghParameters(List<AbstractToken> paragraghParameters) {
		this.paragraghParameters = paragraghParameters;
	}


	
}
