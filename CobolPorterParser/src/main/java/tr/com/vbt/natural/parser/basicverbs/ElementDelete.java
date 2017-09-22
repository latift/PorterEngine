package tr.com.vbt.natural.parser.basicverbs;

import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.token.AbstractToken;

// 6214 DELETE
public class ElementDelete extends AbstractCommand{
	
	private long recNumber;
	
	public ElementDelete(AbstractToken baseToken,
			List<AbstractToken> tokenListesi, AbstractCommand parent) {
		super("ElementDelete","GENERAL.*.DELETE");
	}
	
	public ElementDelete(String elementName,String detailedCobolName) {
		super(elementName, detailedCobolName);
	}


	@Override
	public String exportContents() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedNaturalKeywords.DELETE + " rec:"+recNumber+"\"\n");
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
		sb.append(" "+ReservedNaturalKeywords.DELETE + " rec:"+recNumber+"\"\n");
		return sb.toString();
	}

	public long getRecNumber() {
		return recNumber;
	}

	public void setRecNumber(long recNumber) {
		this.recNumber = recNumber;
	}
	
	

}
