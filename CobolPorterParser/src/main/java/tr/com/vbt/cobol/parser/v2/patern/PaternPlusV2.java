package tr.com.vbt.cobol.parser.v2.patern;

import java.util.ArrayList;
import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.natural.parser.conditions.ElementWhen;
import tr.com.vbt.patern.PatternV2;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.KarakterToken;

/**
 *  A + B
 *
 */
public class PaternPlusV2 extends PatternV2{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//AbstractPattern asp =new PaternAdd();

	}
	

	public PaternPlusV2() {
		super();
		
		//A
		AbstractToken starterToken=new PatternV2();
		patternTokenList.add(starterToken);
		
		//+
		AbstractToken aToken=new KarakterToken<>("+",0,0,0);
		patternTokenList.add(aToken);
		
		//B
		AbstractToken toToken=new PatternV2();
		patternTokenList.add(toToken);
		
		

		
	}
	
	@Override
	public AbstractCommand createElement(){
		ElementWhen elementDisplay = new ElementWhen(ReservedCobolKeywords.WHEN,"GENERAL.*.WHEN");
		return elementDisplay;
	}


	@Override
	public void setTokenToElement(AbstractCommand matchedCommand,
			AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {
		
		ElementWhen matchedCommandAdd=(ElementWhen) matchedCommand;
		
		super.setSatirNumarasi(matchedCommand,currentTokenForMatch, abstractTokenInPattern);if(abstractTokenInPattern.getSourceFieldName()==null){
		
		}else if(abstractTokenInPattern.getSourceFieldName().equals("conditionList")){
			
			matchedCommandAdd.getConditionList().add(currentTokenForMatch);
			
			List<AbstractToken> conditionList;
			if(matchedCommandAdd.getParameters().get("conditionList")!=null){
				conditionList=(List<AbstractToken>) matchedCommandAdd.getParameters().get("conditionList");
			}else{
				conditionList=new ArrayList<AbstractToken>();
			}
			
			conditionList.add(currentTokenForMatch);
			
			matchedCommandAdd.getParameters().put("conditionList",conditionList);			
			
		}
	}
		

	


	
	
	

	
	
	
	

}
