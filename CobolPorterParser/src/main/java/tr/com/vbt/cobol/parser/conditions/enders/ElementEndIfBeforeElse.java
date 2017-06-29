package tr.com.vbt.cobol.parser.conditions.enders;

import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.AbstractEndingCommand;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.token.AbstractToken;

//END-DATA-DIVISON
public class ElementEndIfBeforeElse extends AbstractEndingCommand{
	
	public ElementEndIfBeforeElse(AbstractToken baseToken,
			List<AbstractToken> tokenListesi, AbstractCommand parent) {
		super("ElementEndDataDivision","PROCEDURE_DIVISION.*.END_IF_BEFORE_ELSE");
	}
	
	public ElementEndIfBeforeElse(String elementName,String detailedCobolName) {
		super(elementName, detailedCobolName);
	}



	@Override
	public String exportContents() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedCobolKeywords.END_IF_BEFORE_ELSE +"\n");
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
		sb.append(" "+ReservedCobolKeywords.END_IF_BEFORE_ELSE +"\n");
		return sb.toString();
	}

	@Override
	public String toString() {
		return ReservedCobolKeywords.END_IF_BEFORE_ELSE;
	}
	
}
