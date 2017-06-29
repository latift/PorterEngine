package tr.com.vbt.cobol.parser.conditions.patern;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.conditions.ElementEvaluate;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.patern.AbstractPattern;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.GenelTipToken;
import tr.com.vbt.token.OzelKelimeToken;

/**
 *     EVALUATE SEVIYE 

 *
 */
public class PaternEvaluate extends AbstractPattern{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//AbstractPattern asp =new PaternAdd();

	}
	

	public PaternEvaluate() {
		super();
		
		//MOVE
		AbstractToken<String> astKeyword=new OzelKelimeToken<String>("EVALUATE", 0, 0, 0);
		astKeyword.setTekrarlayabilir("+");
		patternTokenList.add(astKeyword);
		
		
		//condition
		AbstractToken<String> astSource=new GenelTipToken<String>();
		astSource.setTekrarlayabilir("+");
		astSource.setSourceFieldName("condition");
		patternTokenList.add(astSource);
		
	}
	
	@Override
	public AbstractCommand createElement(){
		ElementEvaluate elementDisplay = new ElementEvaluate(ReservedCobolKeywords.EVALUATE,"PROCEDURE_DIVISION.*.EVALUATE");
		return elementDisplay;
	}


	@Override
	public void setTokenToElement(AbstractCommand matchedCommand,
			AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {
		
		ElementEvaluate matchedCommandAdd=(ElementEvaluate) matchedCommand;
		
		super.setSatirNumarasi(matchedCommand,currentTokenForMatch, abstractTokenInPattern);if(abstractTokenInPattern.getSourceFieldName()==null){
		
		}else if(abstractTokenInPattern.getSourceFieldName().equals("condition")){
			matchedCommandAdd.setCondition((String) currentTokenForMatch.getDeger());
			matchedCommandAdd.getParameters().put("condition", matchedCommandAdd.getCondition());
		}
	}
		

	


	
	
	

	
	
	
	

}
