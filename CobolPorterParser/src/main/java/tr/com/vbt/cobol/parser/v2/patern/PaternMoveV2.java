package tr.com.vbt.cobol.parser.v2.patern;

import java.util.ArrayList;
import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.natural.parser.basicverbs.ElementMove;
import tr.com.vbt.patern.PatternV2;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.CommandKeyToken;
import tr.com.vbt.token.OzelKelimeToken;

/**
 *  MOVE A TO B
 *
 */
public class PaternMoveV2 extends PatternV2{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//AbstractPattern asp =new PaternAdd();

	}
	

	public PaternMoveV2() {
		super();
		
		//MOVE
		AbstractToken starterToken=new CommandKeyToken("MOVE", 0, 0, 0);
		starterToken.setSourceFieldName("FIRST_COMMAND");
		patternTokenList.add(starterToken);
		
		//A
		AbstractToken aToken=new PatternV2();
		patternTokenList.add(aToken);
		
		//TO
		AbstractToken toToken=new OzelKelimeToken("TO", 0, 0, 0);
		patternTokenList.add(toToken);
		
		//B
		AbstractToken bToken=new PatternV2();
		patternTokenList.add(bToken);
		

		
	}
	
	@Override
	public AbstractCommand createElement(){
		ElementMove elementDisplay = new ElementMove(ReservedCobolKeywords.MOVE,"GENERAL.*.MOVE");
		return elementDisplay;
	}


	@Override
	public void setTokenToElement(AbstractCommand matchedCommand,
			AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {
		
		ElementMove matchedCommandAdd=(ElementMove) matchedCommand;
		
		super.setSatirNumarasi(matchedCommand,currentTokenForMatch, abstractTokenInPattern);
		if(abstractTokenInPattern.getSourceFieldName()==null){
		
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
