package tr.com.vbt.natural.parser.enders;

import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.AbstractEndingCommand;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.token.AbstractToken;

//END_SUBROUTINE
public class ElementEndSubroutine extends AbstractEndingCommand{
	
	public ElementEndSubroutine(AbstractToken baseToken,
			List<AbstractToken> tokenListesi, AbstractCommand parent) {
		super("ElementEndSubroutine","GENERAL.*.END-SUBROUTINE");
	}
	
	public ElementEndSubroutine(String elementName,String detailedCobolName) {
		super(elementName, detailedCobolName);
	}
	
	public ElementEndSubroutine() {
		super("ElementEndSubroutine", "GENERAL.*.END-SUBROUTINE");
	}



	@Override
	public String exportContents() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedNaturalKeywords.END_SUBROUTINE+"\n");
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
		sb.append(" "+ReservedNaturalKeywords.END_SUBROUTINE +"\n");
		return sb.toString();
	}

	@Override
	public String toString() {
		return "ElementEndSubroutine";
	}

	
}
