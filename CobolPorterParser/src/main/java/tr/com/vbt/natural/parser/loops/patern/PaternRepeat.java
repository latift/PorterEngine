package tr.com.vbt.natural.parser.loops.patern;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.natural.parser.loops.ElementRepeat;
import tr.com.vbt.patern.AbstractPattern;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.KelimeToken;
import tr.com.vbt.token.OzelKelimeToken;

/**
 *     //RP1. REPEAT
 *
 */
public class PaternRepeat extends AbstractPattern{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//AbstractPattern asp =new PaternAdd();

	}
	

	public PaternRepeat() {
		super();
		
		//RP1
		AbstractToken astSource=new KelimeToken<String>();
		astSource.setOptional(true);
		astSource.setSourceFieldName("loopName");
		patternTokenList.add(astSource);
				
		//REPEAT
		AbstractToken astKeyword=new OzelKelimeToken("REPEAT", 0, 0, 0);
		astKeyword.setSourceFieldName("FIRST_COMMAND");
		patternTokenList.add(astKeyword);
		
	}
	
	@Override
	public AbstractCommand createElement(){
		ElementRepeat elementRepeat = new ElementRepeat(ReservedNaturalKeywords.REPEAT,"GENERAL.*.REPEAT");
		return elementRepeat;
	}


	@Override
	public void setTokenToElement(AbstractCommand matchedCommand,
			AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {
		
		ElementRepeat matchedCommandAdd=(ElementRepeat) matchedCommand;
		
		super.setSatirNumarasi(matchedCommand,currentTokenForMatch, abstractTokenInPattern);
		
		if(abstractTokenInPattern.getSourceFieldName()==null){
		
		}else if(abstractTokenInPattern.getSourceFieldName().equals("loopName")){
				
			matchedCommandAdd.setLoopName((String) currentTokenForMatch.getDeger());
			
			matchedCommandAdd.getParameters().put("loopName", matchedCommandAdd.getLoopName());
			
		}
	}
		





	
	
	

	
	
	
	

}
