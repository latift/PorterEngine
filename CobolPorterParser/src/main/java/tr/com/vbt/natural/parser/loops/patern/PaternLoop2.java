package tr.com.vbt.natural.parser.loops.patern;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.natural.parser.loops.ElementLoop;
import tr.com.vbt.patern.AbstractPattern;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.GenelTipToken;
import tr.com.vbt.token.KarakterToken;
import tr.com.vbt.token.OzelKelimeToken;

/**
 *     //*S**LOOP(0790)
 *
 */
public class PaternLoop2 extends AbstractPattern{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//AbstractPattern asp =new PaternAdd();

	}
	

	public PaternLoop2() {
		super();
		
		//LOOP
		AbstractToken astKeyword=new OzelKelimeToken("LOOP", 0, 0, 0);
		astKeyword.setSourceFieldName("FIRST_COMMAND");
		patternTokenList.add(astKeyword);
		
		///( Mandatory
		AbstractToken astSource5=new KarakterToken('(', 0,0,0);
		patternTokenList.add(astSource5);
		
		//	*	12 Mandatory Sayi
		AbstractToken astSource6=new GenelTipToken<>();
		astSource6.setSourceFieldName("loop");
		patternTokenList.add(astSource6);
		
		///) Mandatory
		AbstractToken astSource7=new KarakterToken(')', 0,0,0);
		patternTokenList.add(astSource7);
		
				
	
		
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
