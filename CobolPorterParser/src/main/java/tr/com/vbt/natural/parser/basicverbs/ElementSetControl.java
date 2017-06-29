package tr.com.vbt.natural.parser.basicverbs;

import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.token.AbstractToken;

// 0988 SET CONTROL 'MB'
public class ElementSetControl extends AbstractCommand{
	
	private String controlName;
	
	public ElementSetControl(AbstractToken baseToken,
			List<AbstractToken> tokenListesi, AbstractCommand parent) {
		super("SetControl","GENERAL.*.SET_CONTROL");
	}
	
	public ElementSetControl(String elementName,String detailedCobolName) {
		super(elementName, detailedCobolName);
	}


	@Override
	public String exportContents() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedNaturalKeywords.SET_CONTROL +"=\""+ this.controlName+"\"\n");
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
		sb.append(" "+ReservedNaturalKeywords.SET_CONTROL +"=\""+ this.controlName+"\"\n");
		return sb.toString();
	}

	public String getControlName() {
		return controlName;
	}

	public void setControlName(String controlName) {
		this.controlName = controlName;
	}



	
}
