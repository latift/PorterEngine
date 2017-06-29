package tr.com.vbt.natural.parser.general;

import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.token.AbstractToken;

//BACKOUT TRANSACTION 
public class ElementBackoutTransaction extends AbstractCommand{

	
	public ElementBackoutTransaction(AbstractToken baseToken,
			List<AbstractToken> tokenListesi, AbstractCommand parent) {
		super("BACKOUT_TRANSACTION","BACKOUT_TRANSACTION");
	}
	
	public ElementBackoutTransaction(String elementName,String detailedCobolName) {
		super(elementName, detailedCobolName);
	}


	@Override
	public String exportContents() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedNaturalKeywords.BACKOUT_TRANSACTION+"\"\n");
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
		sb.append(" "+ReservedNaturalKeywords.BACKOUT_TRANSACTION+" ");
		sb.append("\"\n");
		return sb.toString();
	}



	
	
}
