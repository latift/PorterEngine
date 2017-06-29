package tr.com.vbt.cobol.parser.file.child.enders;

import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.AbstractEndingCommand;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.token.AbstractToken;

//END-DATA-DIVISON
public class ElementEndInvalidKey extends AbstractEndingCommand{
	
	public ElementEndInvalidKey(AbstractToken baseToken,
			List<AbstractToken> tokenListesi, AbstractCommand parent) {
		super("ElementEndInvalidKey","PROCEDURE_DIVISION.*.END_INVALID_KEY");
	}
	
	public ElementEndInvalidKey(String elementName,String detailedCobolName) {
		super(elementName, detailedCobolName);
	}
	
	public ElementEndInvalidKey() {
		super("ElementEndInvalidKey","PROCEDURE_DIVISION.*.END_INVALID_KEY");
	}




	@Override
	public String exportContents() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedCobolKeywords.END_INVALID_KEY +"\n");
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
		sb.append(" "+ReservedCobolKeywords.END_INVALID_KEY +"\n");
		return sb.toString();
	}

	@Override
	public String toString() {
	return ReservedCobolKeywords.END_INVALID_KEY;
	}

	
	
}
