package tr.com.vbt.cobol.parser.loops.patern;

import java.util.ArrayList;
import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.loops.ElementPerformUntil;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.patern.carriage_return.AbstractPatternFromXToYWithoutCarriageReturn;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.KelimeToken;
import tr.com.vbt.token.OzelKelimeToken;

/**
 *     //PERFORM UNTIL S-BITTI
 *
 */
public class PaternPerformUntil extends AbstractPatternFromXToYWithoutCarriageReturn{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//AbstractPattern asp =new PaternAdd();

	}
	

	public PaternPerformUntil() {
		super();
		
		//DISPLAY
		starterToken=new OzelKelimeToken("PERFORM_UNTIL", 0, 0, 0);
		starterToken.setTekrarlayabilir("+");
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
		ElementPerformUntil elementDisplay = new ElementPerformUntil(ReservedCobolKeywords.DISPLAY,"PROCEDURE_DIVISION.*.PERFORM_UNTIL");
		return elementDisplay;
	}


	@Override
	public void setTokenToElement(AbstractCommand matchedCommand,
			AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {
		
		ElementPerformUntil matchedCommandAdd=(ElementPerformUntil) matchedCommand;
		
		super.setSatirNumarasi(matchedCommand,currentTokenForMatch, abstractTokenInPattern);if(abstractTokenInPattern.getSourceFieldName()==null){
		
		}else if(abstractTokenInPattern.getSourceFieldName().equals("conditionList")){
				
			String degerAsStr="";
			
			if(currentTokenForMatch.getDeger() instanceof Character){
				 degerAsStr=String.valueOf(currentTokenForMatch.getDeger());
			}else if(currentTokenForMatch.getDeger() instanceof Double){
				 degerAsStr= ((Double) currentTokenForMatch.getDeger()).toString();
			}else{
				 degerAsStr= currentTokenForMatch.getDeger().toString();
			}
				 
			matchedCommandAdd.getConditionList().add(degerAsStr);
			
			List<String> sourceList;
			
			if(matchedCommandAdd.getParameters().get("conditionList")!=null){
				sourceList=(List<String>) matchedCommandAdd.getParameters().get("conditionList");
			}else{
				sourceList=new ArrayList<String>();
			}
			
			sourceList.add(degerAsStr);
			matchedCommandAdd.getParameters().put("conditionList", sourceList);
			
		}
	}
		





	
	
	

	
	
	
	

}
