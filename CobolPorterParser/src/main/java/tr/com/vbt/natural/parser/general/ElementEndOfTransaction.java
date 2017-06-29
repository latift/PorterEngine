package tr.com.vbt.natural.parser.general;

import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.token.AbstractToken;

//BACKOUT TRANSACTION 
public class ElementEndOfTransaction extends AbstractCommand{

	
	public ElementEndOfTransaction(AbstractToken baseToken,
			List<AbstractToken> tokenListesi, AbstractCommand parent) {
		super("END_OF_TRANSACTION","END_OF_TRANSACTION");
	}
	
	public ElementEndOfTransaction(String elementName,String detailedCobolName) {
		super(elementName, detailedCobolName);
	}


	@Override
	public String exportContents() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedNaturalKeywords.END_OF_TRANSACTION+"\"\n");
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
		sb.append(" "+ReservedNaturalKeywords.END_OF_TRANSACTION+" ");
		sb.append("\"\n");
		return sb.toString();
	}



	
	
}
