package tr.com.vbt.natural.parser.screen;

import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.AbstractMultipleLinesCommand;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.token.AbstractToken;


public class ElementDefineWindow extends AbstractMultipleLinesCommand{
	
	String windowName;
	
	public ElementDefineWindow(AbstractToken baseToken,
			List<AbstractToken> tokenListesi, AbstractCommand parent) {
		super(ReservedNaturalKeywords.DEFINE_WINDOW,"SCREEN.*.DEFINE_WINDOW");
	}
	
	public ElementDefineWindow(String elementName,String detailedCobolName) {
		super(elementName, detailedCobolName);
	}

	@Override
	public String exportContents() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedNaturalKeywords.DEFINE_WINDOW +"=\""+ this.windowName+"\n");
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
		sb.append(" "+ReservedNaturalKeywords.DEFINE_WINDOW +"=\""+ this.windowName+"\n");
		if(this.endingCommand!=null){
			sb.append(" 	Ender:"+this.endingCommand.toString() +" ");
		}
		sb.append("\"\n");
		return sb.toString();
	}

	public String getWindowName() {
		return windowName;
	}

	public void setWindowName(String windowName) {
		this.windowName = windowName;
	}

	

	
	
}
