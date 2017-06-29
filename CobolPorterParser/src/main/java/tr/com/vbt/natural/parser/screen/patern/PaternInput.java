package tr.com.vbt.natural.parser.screen.patern;

import java.util.ArrayList;
import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.natural.parser.screen.ElementInput;
import tr.com.vbt.patern.carriage_return.AbstractPatternFromXToYWithoutCarriageReturn;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.KelimeToken;
import tr.com.vbt.token.OzelKelimeToken;

/**
 *     INPUT (AD=MI'.' ZP=OFF IP=OFF SG=OFF) 
 *     //20X 'KALKIS LIMANI....:' #T-KLIM /* (CV=#CV)                                        
*S**      /20X 'VARIS LIMANI.....:' #T-VLIM            
 *     
 *
 */
public class PaternInput extends AbstractPatternFromXToYWithoutCarriageReturn{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//AbstractPattern asp =new PaternAdd();

	}
	

	public PaternInput() {
		super();
		
		//INPUT
		this.starterToken=new OzelKelimeToken(ReservedNaturalKeywords.INPUT, 0, 0, 0);
		starterToken.setSourceFieldName("FIRST_COMMAND");
		starterToken.setTekrarlayabilir("+");
		patternTokenList.add(starterToken);
		
		
		
		//dataToDisplay
		this.midfieldToken=new KelimeToken<String>();
		midfieldToken.setTekrarlayabilir("+");
		midfieldToken.setSourceFieldName("inputParameters");
		patternTokenList.add(midfieldToken);
		
		//END_INPUT
		this.enderToken=new OzelKelimeToken(ReservedNaturalKeywords.END_INPUT,0,0,0);
		patternTokenList.add(enderToken);
		
		
	}
	
	@Override
	public AbstractCommand createElement(){
		ElementInput elementDisplay = new ElementInput(ReservedNaturalKeywords.INPUT,"SCREEN.*.INPUT");
		return elementDisplay;
	}


	@Override
	public void setTokenToElement(AbstractCommand matchedCommand,
			AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {
		
		ElementInput matchedCommandAdd=(ElementInput) matchedCommand;
		
		super.setSatirNumarasi(matchedCommand,currentTokenForMatch, abstractTokenInPattern);if(abstractTokenInPattern.getSourceFieldName()==null){
		
		}else if(abstractTokenInPattern.getSourceFieldName().equals("inputParameters")){
			
			matchedCommandAdd.getInputParameters().add(currentTokenForMatch);
			
			List<AbstractToken> sourceList;
			
			if(matchedCommandAdd.getParameters().get("inputParameters")!=null){
				
				sourceList=(List<AbstractToken>) matchedCommandAdd.getParameters().get("inputParameters");
				
			}else{
				
				sourceList=new ArrayList<AbstractToken>();
			}
			
			sourceList.add((AbstractToken) currentTokenForMatch);
			matchedCommandAdd.getParameters().put("inputParameters", sourceList);
		}
	}
		





	
	
	

	
	
	
	

}
