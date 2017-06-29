package tr.com.vbt.natural.parser.conditions.patern;

import java.util.ArrayList;
import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.natural.parser.conditions.ElementWhen;
import tr.com.vbt.patern.carriage_return.AbstractPatternFromXToYWithoutCarriageReturn;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.KelimeToken;
import tr.com.vbt.token.OzelKelimeToken;

/**
 *    EVALUATE TRUE
	      WHEN WS-A > 2
	         DISPLAY 'WS-A GREATER THAN 2'
	
	      WHEN WS-A < 0
	         DISPLAY 'WS-A LESS THAN 0'
	
	      WHEN OTHER
	         DISPLAY 'INVALID VALUE OF WS-A'
	   END-EVALUATE.
 *
 */
public class PaternWhen extends AbstractPatternFromXToYWithoutCarriageReturn{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//AbstractPattern asp =new PaternAdd();

	}
	

	public PaternWhen() {
		super();
		
		//WHEN
		starterToken=new OzelKelimeToken<String>("WHEN", 0, 0, 0);
		starterToken.setSourceFieldName("FIRST_COMMAND");
		patternTokenList.add(starterToken);
		
		//conditionList
		midfieldToken=new KelimeToken<String>();
		midfieldToken.setTekrarlayabilir("+");
		midfieldToken.setSourceFieldName("conditionList");
		patternTokenList.add(midfieldToken);
		
		//Ender
		enderToken=new OzelKelimeToken(ReservedCobolKeywords.THEN,0,0,0);
		patternTokenList.add(enderToken);
		
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
			matchedCommandAdd.getConditionList().add((String) currentTokenForMatch.getDeger());
			
			List<String> sourceList;
			if(matchedCommandAdd.getParameters().get("conditionList")!=null){
				sourceList=(List<String>) matchedCommandAdd.getParameters().get("conditionList");
			}else{
				sourceList=new ArrayList<String>();
			}
			sourceList.add((String) currentTokenForMatch.getDeger());
			matchedCommandAdd.getParameters().put("conditionList", sourceList);
		}
	}
		

	


	
	
	

	
	
	
	

}
