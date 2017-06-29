package tr.com.vbt.natural.parser.conditions.patern;

import java.util.ArrayList;
import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.natural.parser.conditions.ElementIf;
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
		starterToken.setSourceFieldName("FIRST_COMMAND");
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
		ElementIf elementDisplay = new ElementIf(ReservedCobolKeywords.IF,"GENERAL.*.IF");
		return elementDisplay;
	}


	@Override
	public void setTokenToElement(AbstractCommand matchedCommand,
			AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {
		
		ElementIf matchedCommandAdd=(ElementIf) matchedCommand;
		
		super.setSatirNumarasi(matchedCommand,currentTokenForMatch, abstractTokenInPattern);if(abstractTokenInPattern.getSourceFieldName()==null){
		
		}else if(abstractTokenInPattern.getSourceFieldName().equals("conditionList")){
			
			matchedCommandAdd.getConditionList().add(currentTokenForMatch);
			
			List<AbstractToken> sourceList;
			
			if(matchedCommandAdd.getParameters().get("conditionList")!=null){
				sourceList=(List<AbstractToken>) matchedCommandAdd.getParameters().get("conditionList");
			}else{
				sourceList=new ArrayList<AbstractToken>();
			}
			
			if(currentTokenForMatch.getLinkedToken()!=null && currentTokenForMatch.getLinkedToken().isPojoVariable()){
				//POJO Icin Eklendi - YIGITER 18.05.2017 | 02:11
				//Uretilen Ornek Kod PERF17.setSicil17(PERG001.SICIL);
				sourceList.add( currentTokenForMatch.getLinkedToken());
			}else{
				sourceList.add( currentTokenForMatch);
			}
			
			matchedCommandAdd.getParameters().put("conditionList", sourceList);
			
		}
		
		
	}
		

	public AbstractCommand getmatchedCommand(List<AbstractToken> tokenListesi, int offset) {
		return super.getmatchedCommand(tokenListesi, offset);
	}




	
	
	

	
	
	
	

}
