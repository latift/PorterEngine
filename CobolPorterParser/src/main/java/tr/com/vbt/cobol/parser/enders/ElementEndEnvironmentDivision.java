package tr.com.vbt.cobol.parser.enders;

import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.AbstractEndingCommand;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.token.AbstractToken;

//END-ENVIRONMENT-DIVISION
public class ElementEndEnvironmentDivision extends AbstractEndingCommand{
	
	public ElementEndEnvironmentDivision(AbstractToken baseToken,
			List<AbstractToken> tokenListesi, AbstractCommand parent) {
		super("ElementEndEnvironmentDivision","PROCEDURE_DIVISION.*.END-ENVIRONMENT-DIVISION");
	}
	
	public ElementEndEnvironmentDivision(String elementName,String detailedCobolName) {
		super(elementName, detailedCobolName);
	}


	

	@Override
	public String exportContents() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedCobolKeywords.END_ENVIRONMENT_DIVISION +"\n");
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
		sb.append(" "+ReservedCobolKeywords.END_ENVIRONMENT_DIVISION +"\n");
		return sb.toString();
	}


}
