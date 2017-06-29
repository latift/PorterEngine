package tr.com.vbt.cobol.parser.file.enders;

import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.AbstractEndingCommand;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.token.AbstractToken;

//END-DATA-DIVISON
public class ElementEndReadFile extends AbstractEndingCommand{
	
	public ElementEndReadFile(AbstractToken baseToken,
			List<AbstractToken> tokenListesi, AbstractCommand parent) {
		super("ElementEndReadFile","PROCEDURE_DIVISION.*.END-READ");
	}
	
	public ElementEndReadFile(String elementName,String detailedCobolName) {
		super(elementName, detailedCobolName);
	}
	
	public ElementEndReadFile() {
		super("ElementEndReadFile","PROCEDURE_DIVISION.*.END-READ");
	}



	@Override
	public String exportContents() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedCobolKeywords.END_READ +"\n");
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
		sb.append(" "+ReservedCobolKeywords.END_READ +"\n");
		return sb.toString();
	}

	@Override
	public String toString() {
	return ReservedCobolKeywords.END_READ;
	}

	
	
}
