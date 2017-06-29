package tr.com.vbt.natural.parser.screen.patern;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.natural.parser.screen.ElementBase;
import tr.com.vbt.patern.AbstractPattern;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.KarakterToken;
import tr.com.vbt.token.OzelKelimeToken;
import tr.com.vbt.token.SayiToken;

/**
 0990 DEFINE WINDOW WODEME                                                                                                           
 0992   SIZE 22 * 65                                                                                                                 
 0994   BASE BOTTOM RIGHT                                                                                                            
 0996   CONTROL WINDOW   
 
  0998 DEFINE WINDOW WREFAKAT                                                                                                         
 1000   SIZE 15 * 70                                                                                                                 
 1002   BASE 05 / 05                                                                                                                 
 1004   TITLE '   REFAKAT TALEBÝ'                                                                                                    
 1006   CONTROL SCREEN  
 */
public class PaternBase2 extends AbstractPattern{

	
	public PaternBase2() {
		super();
		
		//SIZE
		AbstractToken astKeyword=new OzelKelimeToken("BASE", 0, 0, 0);
		astKeyword.setSourceFieldName("FIRST_COMMAND");
		astKeyword.setTekrarlayabilir("+");
		patternTokenList.add(astKeyword);
		
		//22
		AbstractToken astSourceInt=new SayiToken<Integer>();
		astSourceInt.setSourceFieldName("baseX");
		patternTokenList.add(astSourceInt);
		
		AbstractToken astSourceDiyez2=new KarakterToken<>('*', 0,0,0);
		patternTokenList.add(astSourceDiyez2);
		
		
		//65
		AbstractToken astSourceInt2=new SayiToken<Integer>();
		astSourceInt2.setSourceFieldName("baseY");
		patternTokenList.add(astSourceInt2);
				
				
		
	}
	
	@Override
	public AbstractCommand createElement(){
		ElementBase elementDisplay = new ElementBase(ReservedNaturalKeywords.BASE,"SCREEN.*.BASE");
		return elementDisplay;
	}


	@Override
	public void setTokenToElement(AbstractCommand matchedCommand,
			AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {
		
		ElementBase matchedCommandAdd=(ElementBase) matchedCommand;
		
		super.setSatirNumarasi(matchedCommand,currentTokenForMatch, abstractTokenInPattern);if(abstractTokenInPattern.getSourceFieldName()==null){
		
		}else if(abstractTokenInPattern.getSourceFieldName().equals("baseX")){
			double deger=(double) currentTokenForMatch.getDeger();
			
			matchedCommandAdd.setBaseX((int) deger);
			matchedCommandAdd.getParameters().put("baseX", matchedCommandAdd.getBaseX());
		}else if(abstractTokenInPattern.getSourceFieldName().equals("baseY")){
			double deger=(double) currentTokenForMatch.getDeger();
			matchedCommandAdd.setBaseY((int) deger);
			matchedCommandAdd.getParameters().put("baseY", matchedCommandAdd.getBaseY());
		}
	}
		

}
