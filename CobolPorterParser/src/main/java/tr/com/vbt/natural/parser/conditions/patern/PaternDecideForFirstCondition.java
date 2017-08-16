package tr.com.vbt.natural.parser.conditions.patern;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.natural.parser.conditions.ElementDecideFirstCondition;
import tr.com.vbt.natural.parser.conditions.ElementDecideOn;
import tr.com.vbt.patern.AbstractPattern;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.GenelTipToken;
import tr.com.vbt.token.OzelKelimeToken;

/**
0420    DECIDE FOR FIRST CONDITION
 */
public class PaternDecideForFirstCondition extends AbstractPattern{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//AbstractPattern asp =new PaternAdd();

	}
	

	public PaternDecideForFirstCondition() {
		super();
		
		//DECIDE
		AbstractToken<String> astKeyword=new OzelKelimeToken<String>("DECIDE", 0, 0, 0);
		astKeyword.setSourceFieldName("FIRST_COMMAND");
		patternTokenList.add(astKeyword);
		
		AbstractToken<String> astKeyword2=new OzelKelimeToken<String>("FOR_FIRST_CONDITION", 0, 0, 0);
		astKeyword2.setSourceFieldName("FOR_FIRST_CONDTION");
		patternTokenList.add(astKeyword2);
		
	}
	
	@Override
	public AbstractCommand createElement(){
		ElementDecideFirstCondition elementDisplay = new ElementDecideFirstCondition(ReservedNaturalKeywords.DECIDE_FIRST_CONDITION,"GENERAL.*.DECIDE_FIRST_CONDITION");
		return elementDisplay;
	}


	@Override
	public void setTokenToElement(AbstractCommand matchedCommand,
			AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {
		
		ElementDecideFirstCondition matchedCommandAdd=(ElementDecideFirstCondition) matchedCommand;
		
		super.setSatirNumarasi(matchedCommand,currentTokenForMatch, abstractTokenInPattern);
		
		if(abstractTokenInPattern.getSourceFieldName()==null){
		
		}
	}
		

}
