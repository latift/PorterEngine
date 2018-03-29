package tr.com.vbt.cobol.parser.division.enders;

import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.AbstractEndingCommand;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.token.AbstractToken;

//END_PROCEDURE_DIVISION
public class ElementEndProcedureDivision extends AbstractEndingCommand{
	
	public ElementEndProcedureDivision(AbstractToken baseToken,
			List<AbstractToken> tokenListesi, AbstractCommand parent) {
		super("ElementEndProcedureDivision","GENERAL.*.END_PROCEDURE_DIVISION");
	}
	
	public ElementEndProcedureDivision(String elementName,String detailedCobolName) {
		super(elementName, detailedCobolName);
	}
	
	public ElementEndProcedureDivision() {
		super(ReservedCobolKeywords.END_PROCEDURE_DIVISION,"GENERAL.*.END_PROCEDURE_DIVISION");
	}



	@Override
	public String exportContents() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedCobolKeywords.END_PROCEDURE_DIVISION +"\n");
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
		sb.append(" "+ReservedCobolKeywords.END_PROCEDURE_DIVISION +"\n");
		return sb.toString();
	}

	@Override
	public String toString() {
	return ReservedCobolKeywords.END_PROCEDURE_DIVISION;
	}

	
	
}
