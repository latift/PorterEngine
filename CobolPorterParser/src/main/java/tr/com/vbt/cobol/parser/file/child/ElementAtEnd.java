package tr.com.vbt.cobol.parser.file.child;

import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.AbstractMultipleLinesCommand;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.token.AbstractToken;

/**
 * 003500     READ AMBLIST INTO IN-REC AT END                              00349500
   003510         MOVE 1 TO EOF-FLAG.                                      00349600
 * @author 47159500
 *
 */
public class ElementAtEnd extends AbstractMultipleLinesCommand{
	
	public ElementAtEnd(AbstractToken baseToken,
			List<AbstractToken> tokenListesi, AbstractCommand parent, int satirNumarasi) {
		super("ElementAtEnd","PROCEDURE_DIVISION.*.AT_END",satirNumarasi);
	}
	
	
	public ElementAtEnd(String elementName,String detailedCobolName) {
		super(elementName, detailedCobolName);
	}
	
	public ElementAtEnd() {
		super("ElementAtEnd","PROCEDURE_DIVISION.*.AT_END");
	}

	

	@Override
	public String exportContents() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedCobolKeywords.AT_END +"=\"");
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
		sb.append(" "+ReservedCobolKeywords.AT_END +"=\"");
		if(this.endingCommand!=null){
			sb.append(" 	Ender:"+ this.endingCommand.toString() +" ");
		}
		sb.append("\n");
		return sb.toString();
	}

	
}
