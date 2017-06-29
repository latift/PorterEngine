package tr.com.vbt.natural.parser.conditions.patern;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.natural.parser.conditions.ElementDecideOn;
import tr.com.vbt.patern.AbstractPattern;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.GenelTipToken;
import tr.com.vbt.token.OzelKelimeToken;

/**
 *     DECIDE ON FIRST VALUE OF PAR-NUM
 *	   DECIDE ON EVERY #SECIM
 */
public class PaternDecideOn extends AbstractPattern{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//AbstractPattern asp =new PaternAdd();

	}
	

	public PaternDecideOn() {
		super();
		
		//DECIDE
		AbstractToken<String> astKeyword=new OzelKelimeToken<String>("DECIDE_ON", 0, 0, 0);
		astKeyword.setSourceFieldName("FIRST_COMMAND");
		patternTokenList.add(astKeyword);
		
		AbstractToken<String> firstValueOf=new OzelKelimeToken<String>("FIRST_VALUE_OF", 0, 0, 0);
		firstValueOf.setOptional(true);
		firstValueOf.setSourceFieldName("FIRST_VALUE_OF");
		patternTokenList.add(firstValueOf);
		
		AbstractToken<String> every=new OzelKelimeToken<String>("EVERY", 0, 0, 0);
		every.setOptional(true);
		every.setSourceFieldName("EVERY");
		patternTokenList.add(every);
		
		//parnum
		AbstractToken<String> astSource=new GenelTipToken<String>();
		astSource.setTekrarlayabilir("+");
		astSource.setSourceFieldName("parnum");
		patternTokenList.add(astSource);
		
		AbstractToken<String> ender=new OzelKelimeToken<String>("END_DECIDE_ON", 0, 0, 0);
		ender.setSourceFieldName("END_DECIDE_ON");
		patternTokenList.add(ender);
		
	}
	
	@Override
	public AbstractCommand createElement(){
		ElementDecideOn elementDisplay = new ElementDecideOn(ReservedNaturalKeywords.DECIDE_ON,"GENERAL.*.DECIDE_ON");
		return elementDisplay;
	}


	@Override
	public void setTokenToElement(AbstractCommand matchedCommand,
			AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {
		
		ElementDecideOn matchedCommandAdd=(ElementDecideOn) matchedCommand;
		
		super.setSatirNumarasi(matchedCommand,currentTokenForMatch, abstractTokenInPattern);
		
		if(abstractTokenInPattern.getSourceFieldName()==null){
		
		}else if(abstractTokenInPattern.getSourceFieldName().equals("parnum")){
			matchedCommandAdd.setCondition(currentTokenForMatch);
			matchedCommandAdd.getParameters().put("condition", matchedCommandAdd.getCondition());
		}
	}
		

}
