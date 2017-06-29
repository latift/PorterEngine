package tr.com.vbt.cobol.parser.division;

import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.token.AbstractToken;

public class ElementProgramId extends AbstractCommand{

	private String programName="";
	
	public ElementProgramId(AbstractToken baseToken,
			List<AbstractToken> tokenListesi, AbstractCommand parent) {
		super("ProgramId","IDENTIFICATION_DIVISION.PROGRAM-ID");
		//super(baseToken, tokenListesi, parent,"ProgramId","IDENTIFICATION_DIVISION.PROGRAM-ID");
		// TODO Auto-generated constructor stub
	}
	
	public ElementProgramId(String elementName,String detailedCobolName) {
		super(elementName, detailedCobolName);
		//super(baseToken, tokenListesi, parent,"ProgramId","IDENTIFICATION_DIVISION.PROGRAM-ID");
		// TODO Auto-generated constructor stub
	}
	

	
	public StringBuilder export() {
		return null;
		// TODO Auto-generated method stub
		
	}

	@Override
	public String exportContents() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean generateDetailedCobolName() {
		// TODO Auto-generated method stub
		return false;
	}

	public String getProgramName() {
		return programName;
	}

	public void setProgramName(String programName) {
		this.programName = programName;
	}

	@Override
	public String exportCommands() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedCobolKeywords.PROGRAM_ID+ " "+programName);
		return sb.toString();
	}
	
	
	

}
