package tr.com.vbt.cobol.parser.general;

import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.AbstractMultipleLinesCommand;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.token.AbstractToken;

public class ElementDefineData extends AbstractMultipleLinesCommand{

	
	
	public ElementDefineData(AbstractToken baseToken,
			List<AbstractToken> tokenListesi, AbstractCommand parent) {
		super("DefineData","DEFINE_DATA");
	}
	
	public ElementDefineData(String elementName,String detailedCobolName) {
		super(elementName, detailedCobolName);
	}


	@Override
	public String exportContents() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedNaturalKeywords.DEFINE_DATA);
		sb.append(" Ender:"+this.endingCommand);
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
		sb.append(" "+ReservedNaturalKeywords.DEFINE_DATA+" ");
		if(this.endingCommand != null){
			sb.append(this.endingCommand.getCommandName());
		}
		sb.append("Ender:"+this.endingCommand);
	    sb.append("\"\n");
		return sb.toString();
	}

	
}
