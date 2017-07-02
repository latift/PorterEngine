package tr.com.vbt.natural.parser.screen.patern;

import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.natural.parser.screen.ElementSize;
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
 */
public class PaternSize extends AbstractPattern{

	
	public PaternSize() {
		super();
		
		//SIZE
		AbstractToken astKeyword=new OzelKelimeToken("SIZE", 0, 0, 0);
		astKeyword.setSourceFieldName("FIRST_COMMAND");
		astKeyword.setTekrarlayabilir("+");
		patternTokenList.add(astKeyword);
		
		//22
		AbstractToken astSourceInt=new SayiToken<Integer>();
		astSourceInt.setSourceFieldName("lineCount");
		patternTokenList.add(astSourceInt);
		
		AbstractToken astSourceDiyez2=new KarakterToken<>('*', 0,0,0);
		patternTokenList.add(astSourceDiyez2);
		
		
		//65
		AbstractToken astSourceInt2=new SayiToken<Integer>();
		astSourceInt2.setSourceFieldName("lineLength");
		patternTokenList.add(astSourceInt2);
				
				
		
	}
	
	@Override
	public AbstractCommand createElement(){
		ElementSize elementDisplay = new ElementSize(ReservedNaturalKeywords.SIZE,"SCREEN.*.SIZE");
		return elementDisplay;
	}


	@Override
	public void setTokenToElement(AbstractCommand matchedCommand,
			AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {
		
		ElementSize matchedCommandAdd=(ElementSize) matchedCommand;
		
		super.setSatirNumarasi(matchedCommand,currentTokenForMatch, abstractTokenInPattern);if(abstractTokenInPattern.getSourceFieldName()==null){
		
		}else if(abstractTokenInPattern.getSourceFieldName().equals("lineCount")){
			int deger=(int) currentTokenForMatch.getDeger();
			matchedCommandAdd.setLineCount(deger);
			matchedCommandAdd.getParameters().put("lineCount", matchedCommandAdd.getLineCount());
		}else if(abstractTokenInPattern.getSourceFieldName().equals("lineLength")){
			int deger=(int) currentTokenForMatch.getDeger();
			matchedCommandAdd.setLineCount((int) deger);
			matchedCommandAdd.getParameters().put("lineLength", matchedCommandAdd.getLineCount());
		}
	}
	
	public AbstractCommand getmatchedCommand(List<AbstractToken> tokenListesi, int offset){
		return super.getmatchedCommand(tokenListesi,offset);
	}
		

}
