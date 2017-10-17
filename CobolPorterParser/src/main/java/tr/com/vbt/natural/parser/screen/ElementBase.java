package tr.com.vbt.natural.parser.screen;

import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.java.general.JavaConstants;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.token.AbstractToken;

/*
 *  0990 DEFINE WINDOW WODEME                                                                                                           
 0992   SIZE 22 * 65                                                                                                                 
 0994   BASE BOTTOM RIGHT                                                                                                            
 0996   CONTROL WINDOW   
 
  0998 DEFINE WINDOW WREFAKAT                                                                                                         
 1000   SIZE 15 * 70                                                                                                                 
 1002   BASE 05 / 05                                                                                                                 
 1004   TITLE '   REFAKAT TALEBÝ'                                                                                                    
 1006   CONTROL SCREEN  
 */

public class ElementBase extends AbstractCommand{
	
	long baseX;
	
	long baseY;
	
	public ElementBase(AbstractToken baseToken,
			List<AbstractToken> tokenListesi, AbstractCommand parent) {
		super(ReservedNaturalKeywords.SIZE,"SCREEN.*.SIZE");
	}
	
	public ElementBase(String elementName,String detailedCobolName) {
		super(elementName, detailedCobolName);
	}

	@Override
	public String exportContents() {
		StringBuilder sb=new StringBuilder();
		sb.append(ReservedNaturalKeywords.BASE +" baseX"+ this.baseX+" baseY"+ this.baseY+JavaConstants.NEW_LINE);
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
		sb.append(ReservedNaturalKeywords.BASE +" baseX"+ this.baseX+" baseY"+ this.baseY+JavaConstants.NEW_LINE);
		return sb.toString();
	}

	public long getBaseX() {
		return baseX;
	}

	public void setBaseX(long baseX) {
		this.baseX = baseX;
	}

	public long getBaseY() {
		return baseY;
	}

	public void setBaseY(long baseY) {
		this.baseY = baseY;
	}

	
	
}
