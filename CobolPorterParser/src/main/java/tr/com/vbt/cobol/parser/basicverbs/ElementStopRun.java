package tr.com.vbt.cobol.parser.basicverbs;

import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedCobolKeywords;


public class ElementStopRun extends AbstractCommand{
	
	
	public ElementStopRun(String elementName,String detailedCobolName) {
		super(elementName, detailedCobolName);
	}
	
	public ElementStopRun(AbstractCommand parent, String elementName,
			String detailedCobolName, List<AbstractCommand> commandListesi) {
		super(parent, elementName, detailedCobolName, commandListesi);
	}
	
	public ElementStopRun(String elementName,String detailedCobolName, List<AbstractCommand> commandListesi) {
		super(elementName, detailedCobolName, commandListesi);
	}


	@Override
	public String exportCommands() {
		StringBuilder sb=new StringBuilder();
		sb.append(ReservedCobolKeywords.STOP_RUN);
		sb.append("\"\n");
		return sb.toString();
	}
	

	@Override
	public String exportContents() {
		StringBuilder sb=new StringBuilder();
		sb.append(ReservedCobolKeywords.STOP_RUN);
		sb.append("\"\n");
		return sb.toString();
	}

	@Override
	public boolean generateDetailedCobolName() {
		// TODO Auto-generated method stub
				return false;
	}

	
}
