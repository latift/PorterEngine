package tr.com.vbt.cobol.parser.file.enders;

import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.AbstractEndingCommand;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.token.AbstractToken;

//END-DATA-DIVISON
public class ElementEndWriteFile extends AbstractEndingCommand{
	
	public ElementEndWriteFile(AbstractToken baseToken,
			List<AbstractToken> tokenListesi, AbstractCommand parent) {
		super("ElementEndWriteFile","PROCEDURE_DIVISION.*.END_WRITE");
	}
	
	public ElementEndWriteFile(String elementName,String detailedCobolName) {
		super(elementName, detailedCobolName);
	}



	public ElementEndWriteFile() {
		super("ElementEndWriteFile","PROCEDURE_DIVISION.*.END_WRITE");
	}

	@Override
	public String exportContents() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedCobolKeywords.END_WRITE +"\n");
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
		sb.append(" "+ReservedCobolKeywords.END_WRITE +"\n");
		return sb.toString();
	}

	@Override
	public String toString() {
	return ReservedCobolKeywords.END_WRITE;
	}

	
	
}
