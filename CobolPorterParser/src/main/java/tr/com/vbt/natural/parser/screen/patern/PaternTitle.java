package tr.com.vbt.natural.parser.screen.patern;

import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.natural.parser.screen.ElementTitle;
import tr.com.vbt.patern.AbstractPattern;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.KelimeToken;
import tr.com.vbt.token.OzelKelimeToken;

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
 */
public class PaternTitle extends AbstractPattern{

	
	public PaternTitle() {
		super();
		
		//SIZE
		AbstractToken astKeyword=new OzelKelimeToken("TITLE", 0, 0, 0);
		astKeyword.setSourceFieldName("FIRST_COMMAND");
		astKeyword.setTekrarlayabilir("+");
		patternTokenList.add(astKeyword);
		
		// REFAKAT TALEBÝ
		AbstractToken astSourceInt2=new KelimeToken<>();
		astSourceInt2.setSourceFieldName("title");
		patternTokenList.add(astSourceInt2);
				
				
		
	}
	
	@Override
	public AbstractCommand createElement(){
		ElementTitle elementDisplay = new ElementTitle(ReservedNaturalKeywords.TITLE,"SCREEN.*.TITLE");
		return elementDisplay;
	}


	@Override
	public void setTokenToElement(AbstractCommand matchedCommand,
			AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {
		
		ElementTitle matchedCommandAdd=(ElementTitle) matchedCommand;
		
		super.setSatirNumarasi(matchedCommand,currentTokenForMatch, abstractTokenInPattern);if(abstractTokenInPattern.getSourceFieldName()==null){
		
		}else if(abstractTokenInPattern.getSourceFieldName().equals("title")){
			
			matchedCommandAdd.setTitle((String) currentTokenForMatch.getDeger());
			matchedCommandAdd.getParameters().put("title", matchedCommandAdd.getTitle());
		}
	}
		
	public AbstractCommand getmatchedCommand(List<AbstractToken> tokenListesi, int offset){
		return super.getmatchedCommand(tokenListesi, offset);
	}

}
