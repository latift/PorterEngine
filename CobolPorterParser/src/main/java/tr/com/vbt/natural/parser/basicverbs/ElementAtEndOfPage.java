package tr.com.vbt.natural.parser.basicverbs;

import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.AbstractMultipleLinesCommand;
import tr.com.vbt.lexer.ReservedNaturalKeywords;

//AT END OF PAGE.
public class ElementAtEndOfPage extends AbstractMultipleLinesCommand{
	
	private long printNumber;
	
	public ElementAtEndOfPage(String elementName,String detailedCobolName) {
		super(elementName, detailedCobolName);
	}
	
	public ElementAtEndOfPage(AbstractCommand parent, String elementName,
			String detailedCobolName, List<AbstractCommand> commandListesi) {
		super(parent, elementName, detailedCobolName, commandListesi);
	}
	
	public ElementAtEndOfPage(String elementName,String detailedCobolName, List<AbstractCommand> commandListesi) {
		super(elementName, detailedCobolName, commandListesi);
	}


	


	@Override
	public String exportCommands() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedNaturalKeywords.AT_END_OF_PAGE);
		sb.append(" Ender:"+ this.endingCommand +"\"");
		sb.append("\"\n");
		return sb.toString();
	}
	

	@Override
	public String exportContents() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedNaturalKeywords.AT_END_OF_PAGE +"=\"");
		sb.append("\"\n");
		return sb.toString();
	}

	@Override
	public boolean generateDetailedCobolName() {
		// TODO Auto-generated method stub
				return false;
	}

	public long getPrintNumber() {
		return printNumber;
	}

	public void setPrintNumber(long printNumber) {
		this.printNumber = printNumber;
	}



	

	
}
