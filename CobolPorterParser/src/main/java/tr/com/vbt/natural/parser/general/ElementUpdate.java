package tr.com.vbt.natural.parser.general;

import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.java.general.JavaConstants;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.token.AbstractToken;

//UPDATE 
public class ElementUpdate extends AbstractCommand{

	private int recNumber;
	
	public ElementUpdate(AbstractToken baseToken,
			List<AbstractToken> tokenListesi, AbstractCommand parent) {
		super("ElementDelete","GENERAL.*.UPDATE");
	}
	
	public ElementUpdate(String elementName,String detailedCobolName) {
		super(elementName, detailedCobolName);
	}


	@Override
	public String exportContents() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedNaturalKeywords.UPDATE + " rec:"+recNumber);
		sb.append(JavaConstants.NEW_LINE);
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
		sb.append(" "+ReservedNaturalKeywords.UPDATE + " rec:"+recNumber);
		sb.append(JavaConstants.NEW_LINE);
		return sb.toString();
	}

	public int getRecNumber() {
		return recNumber;
	}

	public void setRecNumber(int recNumber) {
		this.recNumber = recNumber;
	}
	
	
}
