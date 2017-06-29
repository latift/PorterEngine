package tr.com.vbt.cobol.parser.conditions.enders;

import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.AbstractEndingCommand;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.token.AbstractToken;

//END-DATA-DIVISON
public class ElementEndWhen extends AbstractEndingCommand{
	
	public ElementEndWhen(AbstractToken baseToken,
			List<AbstractToken> tokenListesi, AbstractCommand parent) {
		super("ElementEndWhen","PROCEDURE_DIVISION.*.END_WHEN");
	}
	
	public ElementEndWhen(String elementName,String detailedCobolName) {
		super(elementName, detailedCobolName);
	}
	
	public ElementEndWhen() {
		super(ReservedCobolKeywords.END_WHEN,"PROCEDURE_DIVISION.*.END_WHEN");
	}



	@Override
	public String exportContents() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedCobolKeywords.END_WHEN +"\n");
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
		sb.append(" "+ReservedCobolKeywords.END_WHEN +"\n");
		return sb.toString();
	}

	@Override
	public String toString() {
	return ReservedCobolKeywords.END_WHEN;
	}

	
	
}
