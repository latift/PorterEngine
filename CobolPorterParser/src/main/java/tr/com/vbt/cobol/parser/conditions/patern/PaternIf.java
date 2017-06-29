package tr.com.vbt.cobol.parser.conditions.patern;

import java.util.ArrayList;
import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.conditions.ElementIf;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.patern.carriage_return.AbstractPatternFromXToYWithoutCarriageReturn;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.KelimeToken;
import tr.com.vbt.token.OzelKelimeToken;

/**
 *  	If:ÖzelKelime
		Condition:
		Then: ÖzelKelime
 *
 */
public class PaternIf extends AbstractPatternFromXToYWithoutCarriageReturn{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//AbstractPattern asp =new PaternAdd();

	}
	

	public PaternIf() {
		super();
		
		//IF
		starterToken=new OzelKelimeToken("IF", 0, 0, 0);
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
		ElementIf elementDisplay = new ElementIf(ReservedCobolKeywords.IF,"PROCEDURE_DIVISION.*.IF");
		return elementDisplay;
	}


	@Override
	public void setTokenToElement(AbstractCommand matchedCommand,
			AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {
		
		ElementIf matchedCommandAdd=(ElementIf) matchedCommand;
		
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
