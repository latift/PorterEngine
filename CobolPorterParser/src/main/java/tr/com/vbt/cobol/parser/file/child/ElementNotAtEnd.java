package tr.com.vbt.cobol.parser.file.child;

import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.AbstractMultipleLinesCommand;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.token.AbstractToken;


public class ElementNotAtEnd extends AbstractMultipleLinesCommand{
	
	public ElementNotAtEnd(AbstractToken baseToken,
			List<AbstractToken> tokenListesi, AbstractCommand parent) {
		super("ElementNotAtEnd","PROCEDURE_DIVISION.*.NOT_AT_END");
	}
	
	
	public ElementNotAtEnd(String elementName,String detailedCobolName) {
		super(elementName, detailedCobolName);
	}

	
	public ElementNotAtEnd() {
		super("ElementNotAtEnd","PROCEDURE_DIVISION.*.NOT_AT_END");
	}
	

	@Override
	public String exportContents() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedCobolKeywords.NOT_AT_END +"=\"");
		if(this.endingCommand!=null){
			sb.append(" 	Ender:"+this.endingCommand.toString() +" ");
		}
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
		sb.append(" "+ReservedCobolKeywords.NOT_AT_END +"=\"");
		if(this.endingCommand!=null){
			sb.append(" 	Ender:"+ this.endingCommand.toString() +" ");
		}
		sb.append("\n");
		return sb.toString();
	}

	
}
