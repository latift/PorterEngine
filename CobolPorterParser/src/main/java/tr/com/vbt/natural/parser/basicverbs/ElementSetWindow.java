package tr.com.vbt.natural.parser.basicverbs;

import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.token.AbstractToken;

//1970 *   SET WINDOW OFF
//1914 SET WINDOW 'WSWIFT' 
public class ElementSetWindow extends AbstractCommand{
	
	private String windowName;
	
	private boolean windowOffState;
	
	
	public ElementSetWindow(AbstractToken baseToken,
			List<AbstractToken> tokenListesi, AbstractCommand parent) {
		super("SetWindow","GENERAL.*.SET_WINDOW");
	}
	
	public ElementSetWindow(String elementName,String detailedCobolName) {
		super(elementName, detailedCobolName);
	}


	@Override
	public String exportContents() {
		StringBuilder sb=new StringBuilder();
		if(windowName!=null){
			sb.append(" "+ReservedNaturalKeywords.SET_WINDOW +"=\""+ this.windowName+"\"\n");
		}else{
			sb.append(" "+ReservedNaturalKeywords.SET_WINDOW +"=\""+ this.windowOffState+"\"\n");
		}
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
		if(windowName!=null){
			sb.append(" "+ReservedNaturalKeywords.SET_WINDOW +"=\""+ this.windowName+"\"\n");
		}else{
			sb.append(" "+ReservedNaturalKeywords.SET_WINDOW +"=\""+ this.windowOffState+"\"\n");
		}
		return sb.toString();
	}

	public String getWindowName() {
		return windowName;
	}

	public void setWindowName(String windowName) {
		this.windowName = windowName;
	}

	public boolean isWindowOffState() {
		return windowOffState;
	}

	public void setWindowOffState(boolean windowOffState) {
		this.windowOffState = windowOffState;
	}

	


	
}
