package tr.com.vbt.natural.parser.screen;

import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.token.AbstractToken;

/**
 *  0990 DEFINE WINDOW WODEME                                                                                                           
 0992   SIZE 22 * 65                                                                                                                 
 0994   BASE BOTTOM RIGHT                                                                                                            
 0996   CONTROL WINDOW   
 
  0998 DEFINE WINDOW WREFAKAT                                                                                                         
 1000   SIZE 15 * 70                                                                                                                 
 1002   BASE 05 / 05                                                                                                                 
 1004   TITLE '   REFAKAT TALEBÝ'                                                                                                    
 1006   CONTROL SCREEN  
 *
 */
public class ElementControlWindowScreen extends AbstractCommand{
	
	private String windowOrScreen;
	
	public ElementControlWindowScreen(AbstractToken baseToken,
			List<AbstractToken> tokenListesi, AbstractCommand parent) {
		super(ReservedNaturalKeywords.CONTROL_WINDOW_SCREEN,"SCREEN.*.CONTROL_WINDOW_SCREEN");
	}
	
	public ElementControlWindowScreen(String elementName,String detailedCobolName) {
		super(elementName, detailedCobolName);
	}

	@Override
	public String exportContents() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedNaturalKeywords.CONTROL_WINDOW_SCREEN);
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
		sb.append(" "+ReservedNaturalKeywords.CONTROL_WINDOW_SCREEN);
		sb.append("\"\n");
		return sb.toString();
	}

	public String getWindowOrScreen() {
		return windowOrScreen;
	}

	public void setWindowOrScreen(String windowOrScreen) {
		this.windowOrScreen = windowOrScreen;
	}

	
	
}
