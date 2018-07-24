package tr.com.vbt.cobol.parser.v2.patern;

import java.util.ArrayList;
import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.natural.parser.conditions.ElementWhen;
import tr.com.vbt.patern.PatternV2;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.KelimeToken;

/**
 *  A(1:2)
 *
 */
public class PaternSubstringV2 extends PatternV2{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//AbstractPattern asp =new PaternAdd();

	}
	

	public PaternSubstringV2() {
		super();
		
		//A
		AbstractToken aToken=new PatternV2();
		patternTokenList.add(aToken);
		
		//(

		
		//1
		
		//:
		
		//2
		
		//)
		AbstractToken bToken=new KelimeToken<>();
		patternTokenList.add(bToken);
		

		
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
