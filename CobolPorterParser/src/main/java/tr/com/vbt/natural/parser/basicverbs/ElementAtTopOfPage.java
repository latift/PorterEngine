package tr.com.vbt.natural.parser.basicverbs;

import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.AbstractMultipleLinesCommand;
import tr.com.vbt.lexer.ReservedNaturalKeywords;

//AT TOP OF PAGE.
public class ElementAtTopOfPage extends AbstractMultipleLinesCommand{
	
	private long printNumber;
	
	public ElementAtTopOfPage(String elementName,String detailedCobolName) {
		super(elementName, detailedCobolName);
	}
	
	public ElementAtTopOfPage(AbstractCommand parent, String elementName,
			String detailedCobolName, List<AbstractCommand> commandListesi) {
		super(parent, elementName, detailedCobolName, commandListesi);
	}
	
	public ElementAtTopOfPage(String elementName,String detailedCobolName, List<AbstractCommand> commandListesi) {
		super(elementName, detailedCobolName, commandListesi);
	}


	


	@Override
	public String exportCommands() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedNaturalKeywords.AT_TOP_OF_PAGE);
		sb.append(" Ender:"+ this.endingCommand +"\"");
		sb.append("\"\n");
		return sb.toString();
	}
	

	@Override
	public String exportContents() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedNaturalKeywords.AT_TOP_OF_PAGE +"=\"");
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
