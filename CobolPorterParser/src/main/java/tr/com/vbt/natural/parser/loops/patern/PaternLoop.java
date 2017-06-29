package tr.com.vbt.natural.parser.loops.patern;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.natural.parser.loops.ElementLoop;
import tr.com.vbt.patern.AbstractPattern;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.OzelKelimeToken;

/*
 *     
 *     //*S**LOOP
 *
 */
public class PaternLoop extends AbstractPattern{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//AbstractPattern asp =new PaternAdd();

	}
	

	public PaternLoop() {
		super();
		
		//LOOP
		AbstractToken astKeyword=new OzelKelimeToken("LOOP", 0, 0, 0);
		astKeyword.setSourceFieldName("FIRST_COMMAND");
		patternTokenList.add(astKeyword);
		
		
	}
	
	@Override
	public AbstractCommand createElement(){
		ElementLoop elementLoop = new ElementLoop(ReservedNaturalKeywords.LOOP,"GENERAL.*.LOOP");
		return elementLoop;
	}


	@Override
	public void setTokenToElement(AbstractCommand matchedCommand,
			AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {
		
		ElementLoop matchedCommandAdd=(ElementLoop) matchedCommand;
		
		super.setSatirNumarasi(matchedCommand,currentTokenForMatch, abstractTokenInPattern);
		
		if(abstractTokenInPattern.getSourceFieldName()==null){
		
		}else if(abstractTokenInPattern.getSourceFieldName().equals("loop")){
				
			matchedCommandAdd.setLoop(currentTokenForMatch);
			
			matchedCommandAdd.getParameters().put("loop", matchedCommandAdd.getLoop());
			
		}
	}
		





	
	
	

	
	
	
	

}
