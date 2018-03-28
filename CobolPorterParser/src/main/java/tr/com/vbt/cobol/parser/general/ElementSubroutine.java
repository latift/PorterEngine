package tr.com.vbt.cobol.parser.general;

import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.AbstractMultipleLinesCommand;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.token.AbstractToken;

public class ElementSubroutine extends AbstractMultipleLinesCommand{
	
	private String subroutineName;
	
	
	
	public ElementSubroutine(AbstractToken baseToken,
			List<AbstractToken> tokenListesi, AbstractCommand parent) {
		super("ElementSubroutine","GENERAL.SUBROUTINE");
	}
	
	public ElementSubroutine(String paragraphName,String detailedCobolName) {
		super(paragraphName,detailedCobolName);
	}

	
	


	@Override
	public String exportContents() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedNaturalKeywords.SUBROUTINE +"=\""+ this.subroutineName);
		sb.append(" 	Ender:"+ this.endingCommand);
		sb.append("\"\n");
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
		sb.append(" "+ReservedNaturalKeywords.SUBROUTINE +"=\""+ this.subroutineName);
		sb.append(" 	Ender:"+ this.endingCommand);
		sb.append("\"\n");
		return sb.toString();
	}

	public String getSubroutineName() {
		return subroutineName;
	}

	public void setSubroutineName(String subroutineName) {
		this.subroutineName = subroutineName;
	}


	

	
}
