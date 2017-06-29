package tr.com.vbt.cobol.parser.enders;

import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.AbstractEndingCommand;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.token.AbstractToken;

//END_IDENTIFICATION_DIVISION
public class ElementEndIdentificationDivision extends AbstractEndingCommand{
	
	public ElementEndIdentificationDivision(AbstractToken baseToken,
			List<AbstractToken> tokenListesi, AbstractCommand parent) {
		super("ElementEndDataDivision","PROCEDURE_DIVISION.*.END-IDENTIFICATION-DIVISON");
	}
	
	public ElementEndIdentificationDivision(String elementName,String detailedCobolName) {
		super(elementName, detailedCobolName);
	}

	@Override
	public String exportContents() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedCobolKeywords.END_IDENTIFICATION_DIVISION +"\n");
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
		sb.append(" "+ReservedCobolKeywords.END_IDENTIFICATION_DIVISION +"\n");
		return sb.toString();
	}


}
