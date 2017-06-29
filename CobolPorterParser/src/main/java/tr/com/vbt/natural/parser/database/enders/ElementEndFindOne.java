package tr.com.vbt.natural.parser.database.enders;

import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.AbstractEndingCommand;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.token.AbstractToken;

//FIND(1) i kapatir
public class ElementEndFindOne extends AbstractEndingCommand{
	
	public ElementEndFindOne(AbstractToken baseToken,
			List<AbstractToken> tokenListesi, AbstractCommand parent) {
		super("ElementEndFindOne","DATABASE.*.END_FIND");
	}
	
	public ElementEndFindOne(String elementName,String detailedCobolName) {
		super(elementName, detailedCobolName);
	}



	@Override
	public String exportContents() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedNaturalKeywords.END_FIND_ONE +"\n");
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
		sb.append(" "+ReservedNaturalKeywords.END_FIND_ONE +"\n");
		return sb.toString();
	}

	@Override
	public String toString() {
	return ReservedNaturalKeywords.END_FIND_ONE;
	}

	
	
}
