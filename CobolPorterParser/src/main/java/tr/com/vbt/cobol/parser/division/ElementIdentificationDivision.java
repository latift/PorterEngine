package tr.com.vbt.cobol.parser.division;

import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.token.AbstractToken;

//IDENTIFICATION DIVISION.
public class ElementIdentificationDivision extends AbstractCommand{
	
	public ElementIdentificationDivision(AbstractToken baseToken,
			List<AbstractToken> tokenListesi, AbstractCommand parent) {
		super("ElementIdentificationDivision","GENERAL.*.IDENTIFICATION_DIVISION");
	}
	
	public ElementIdentificationDivision(String elementName,String detailedCobolName) {
		super(elementName, detailedCobolName);
	}


	@Override
	public String exportContents() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedCobolKeywords.IDENTIFICATION_DIVISION );
		sb.append("\n");
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
		sb.append(" "+ReservedCobolKeywords.IDENTIFICATION_DIVISION );
		sb.append("\n");
		return sb.toString();
	}

	
	
}
