package tr.com.vbt.cobol.parser.file.enders;

import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.AbstractEndingCommand;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.token.AbstractToken;

//END-DATA-DIVISON
public class ElementEndStartFile extends AbstractEndingCommand{
	
	public ElementEndStartFile(AbstractToken baseToken,
			List<AbstractToken> tokenListesi, AbstractCommand parent) {
		super("ElementEndStartFile","PROCEDURE_DIVISION.*.END_START");
	}
	
	public ElementEndStartFile(String elementName,String detailedCobolName) {
		super(elementName, detailedCobolName);
	}



	public ElementEndStartFile() {
		super("ElementEndStartFile","PROCEDURE_DIVISION.*.END_START");
	}

	@Override
	public String exportContents() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedCobolKeywords.END_START +"\n");
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
		sb.append(" "+ReservedCobolKeywords.END_START +"\n");
		return sb.toString();
	}

	@Override
	public String toString() {
	return ReservedCobolKeywords.END_START;
	}

	
	
}
