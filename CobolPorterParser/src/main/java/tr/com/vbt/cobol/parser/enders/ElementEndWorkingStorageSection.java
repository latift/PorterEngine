package tr.com.vbt.cobol.parser.enders;

import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.AbstractEndingCommand;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.token.AbstractToken;

//END_WORKING_STORAGE_SECTION
public class ElementEndWorkingStorageSection extends AbstractEndingCommand{
	
	public ElementEndWorkingStorageSection(AbstractToken baseToken,
			List<AbstractToken> tokenListesi, AbstractCommand parent) {
		super("EndWorkingStorageSection","PROCEDURE_DIVISION.*.END_WORKING_STORAGE_SECTION");
	}
	
	public ElementEndWorkingStorageSection(String elementName,String detailedCobolName) {
		super(elementName, detailedCobolName);
	}

	@Override
	public String exportContents() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedCobolKeywords.END_WORKING_STORAGE_SECTION +"\n");
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
		sb.append(" "+ReservedCobolKeywords.END_WORKING_STORAGE_SECTION +"\n");
		return sb.toString();
	}

	
}
