package tr.com.vbt.cobol.parser.division;

import tr.com.vbt.cobol.parser.AbstractMultipleLinesCommand;
import tr.com.vbt.lexer.ReservedCobolKeywords;

public class ElementIdentificationDivision extends AbstractMultipleLinesCommand{

	String PROGRAM_ID;
	String AUTHOR;
	String DATE_WRITTEN;
	
	
	public ElementIdentificationDivision(String elementName,String detailedCobolName) {
		super(elementName, detailedCobolName);
	}
	

	@Override
	public String exportContents() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedCobolKeywords.IDENTIFICATION_DIVISION);
		sb.append(" "+ReservedCobolKeywords.PROGRAM_ID +"=\""+ this.PROGRAM_ID+"\"\n");
		sb.append(ReservedCobolKeywords.AUTHOR +"=\""+ this.AUTHOR+"\"\n");
		sb.append(ReservedCobolKeywords.DATE_WRITTEN +"=\""+ this.DATE_WRITTEN+"\"\n");
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
		sb.append(" "+ReservedCobolKeywords.IDENTIFICATION_DIVISION);
		sb.append(" "+ReservedCobolKeywords.PROGRAM_ID +"=\""+ this.PROGRAM_ID);
		sb.append(" "+ReservedCobolKeywords.AUTHOR +"=\""+ this.AUTHOR);
		sb.append(" "+ReservedCobolKeywords.DATE_WRITTEN +"=\""+ this.DATE_WRITTEN+"\"\n");
		return sb.toString();
	}

	public String getPROGRAM_ID() {
		return PROGRAM_ID;
	}

	public void setPROGRAM_ID(String pROGRAM_ID) {
		PROGRAM_ID = pROGRAM_ID;
	}

	public String getAUTHOR() {
		return AUTHOR;
	}

	public void setAUTHOR(String aUTHOR) {
		AUTHOR = aUTHOR;
	}

	public String getDATE_WRITTEN() {
		return DATE_WRITTEN;
	}

	public void setDATE_WRITTEN(String dATE_WRITTEN) {
		DATE_WRITTEN = dATE_WRITTEN;
	}

	
}
