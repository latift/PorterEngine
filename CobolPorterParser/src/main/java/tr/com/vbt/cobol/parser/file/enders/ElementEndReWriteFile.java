package tr.com.vbt.cobol.parser.file.enders;

import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.AbstractEndingCommand;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.token.AbstractToken;

//END-DATA-DIVISON
public class ElementEndReWriteFile extends AbstractEndingCommand{
	
	public ElementEndReWriteFile(AbstractToken baseToken,
			List<AbstractToken> tokenListesi, AbstractCommand parent) {
		super("ElementEndReWriteFile","PROCEDURE_DIVISION.*.END_REWRITE");
	}
	
	public ElementEndReWriteFile(String elementName,String detailedCobolName) {
		super(elementName, detailedCobolName);
	}



	public ElementEndReWriteFile() {
		super("ElementEndReWriteFile","PROCEDURE_DIVISION.*.END_REWRITE");
	}

	@Override
	public String exportContents() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedCobolKeywords.END_REWRITE +"\n");
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
		sb.append(" "+ReservedCobolKeywords.END_REWRITE +"\n");
		return sb.toString();
	}

	@Override
	public String toString() {
	return ReservedCobolKeywords.END_REWRITE;
	}

	
	
}
