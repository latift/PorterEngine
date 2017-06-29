package tr.com.vbt.natural.parser.screen.patern;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.natural.parser.screen.ElementDefineWindow;
import tr.com.vbt.patern.AbstractPattern;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.KelimeToken;
import tr.com.vbt.token.OzelKelimeToken;

/**
 0990 DEFINE WINDOW WODEME                                                                                                           
 0992   SIZE 22 * 65                                                                                                                 
 0994   BASE BOTTOM RIGHT                                                                                                            
 0996   CONTROL WINDOW  
 */
public class PaternDefineWindow extends AbstractPattern{

	
	public PaternDefineWindow() {
		super();
		
		//WRITE
		AbstractToken astKeyword=new OzelKelimeToken("DEFINE_WINDOW", 0, 0, 0);
		astKeyword.setSourceFieldName("FIRST_COMMAND");
		astKeyword.setTekrarlayabilir("+");
		patternTokenList.add(astKeyword);
		
		//WODEME
		AbstractToken astSource=new KelimeToken<String>();
		astSource.setTekrarlayabilir("+");
		astSource.setSourceFieldName("windowName");
		patternTokenList.add(astSource);
		
		
	}
	
	@Override
	public AbstractCommand createElement(){
		ElementDefineWindow elementDisplay = new ElementDefineWindow(ReservedNaturalKeywords.DEFINE_WINDOW,"SCREEN.*.DEFINE_WINDOW");
		return elementDisplay;
	}


	@Override
	public void setTokenToElement(AbstractCommand matchedCommand,
			AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {
		
		ElementDefineWindow matchedCommandAdd=(ElementDefineWindow) matchedCommand;
		
		super.setSatirNumarasi(matchedCommand,currentTokenForMatch, abstractTokenInPattern);if(abstractTokenInPattern.getSourceFieldName()==null){
		
		}else if(abstractTokenInPattern.getSourceFieldName().equals("windowName")){
			
			matchedCommandAdd.setWindowName((String) currentTokenForMatch.getDeger());
			matchedCommandAdd.getParameters().put("windowName", matchedCommandAdd.getWindowName());
		}
	}
		

}
