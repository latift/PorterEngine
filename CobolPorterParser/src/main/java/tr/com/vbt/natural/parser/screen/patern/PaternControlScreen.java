package tr.com.vbt.natural.parser.screen.patern;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.natural.parser.screen.ElementControlWindowScreen;
import tr.com.vbt.patern.AbstractPattern;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.OzelKelimeToken;

/**
 0990 DEFINE WINDOW WODEME                                                                                                           
 0992   SIZE 22 * 65                                                                                                                 
 0994   BASE BOTTOM RIGHT                                                                                                            
 0996   CONTROL WINDOW  
 */
public class PaternControlScreen extends AbstractPattern{

	
	public PaternControlScreen() {
		super();
		
		//SIZE
		AbstractToken astKeyword=new OzelKelimeToken("CONTROL_SCREEN", 0, 0, 0);
		astKeyword.setSourceFieldName("FIRST_COMMAND");
		astKeyword.setTekrarlayabilir("+");
		astKeyword.setSourceFieldName("controlScreen");
		patternTokenList.add(astKeyword);

		
	}
	
	@Override
	public AbstractCommand createElement(){
		ElementControlWindowScreen elementDisplay = new ElementControlWindowScreen(ReservedNaturalKeywords.CONTROL_WINDOW_SCREEN,"SCREEN.*.CONTROL_WINDOW_SCREEN");
		return elementDisplay;
	}


	@Override
	public void setTokenToElement(AbstractCommand matchedCommand,
			AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {
		
		ElementControlWindowScreen matchedCommandAdd=(ElementControlWindowScreen) matchedCommand;
		
		super.setSatirNumarasi(matchedCommand,currentTokenForMatch, abstractTokenInPattern);if(abstractTokenInPattern.getSourceFieldName()==null){
		
		}else if(abstractTokenInPattern.getSourceFieldName().equals("controlScreen")){
			
			matchedCommandAdd.setWindowOrScreen("screen");
			matchedCommandAdd.getParameters().put("windowOrScreen", matchedCommandAdd.getWindowOrScreen());
		}
	}
		

}
