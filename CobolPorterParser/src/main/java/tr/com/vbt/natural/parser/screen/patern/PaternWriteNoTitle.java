package tr.com.vbt.natural.parser.screen.patern;

import java.util.ArrayList;
import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.natural.parser.screen.ElementWrite;
import tr.com.vbt.patern.carriage_return.AbstractPatternFromXToYWithoutCarriageReturn;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.KelimeToken;
import tr.com.vbt.token.OzelKelimeToken;

/**
 *      WRITE NOTITLE                                                                        
*S**                #TARIH1 '-' #TARIH2 #T-RFN-UCRET (EM=Z,ZZZ,ZZ9.999999)                      
*S**  #P-SIRKET-MARJI(EM=Z,ZZZ,ZZ9.999999) #BIRIM-FIYAT(EM=Z,ZZZ,ZZ9.999999)                    
*S**                #ODM-BIRIM #P-FORMUL (IS=ON) #P-FKOD #P-RKOD              
 *     
 *
 */
public class PaternWriteNoTitle extends AbstractPatternFromXToYWithoutCarriageReturn{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//AbstractPattern asp =new PaternAdd();

	}
	

	public PaternWriteNoTitle() {
		super();
		
		//INPUT
		this.starterToken=new OzelKelimeToken(ReservedNaturalKeywords.WRITE_NOTITLE, 0, 0, 0);
		starterToken.setSourceFieldName("FIRST_COMMAND");
		starterToken.setTekrarlayabilir("+");
		patternTokenList.add(starterToken);
		
		
		//dataToDisplay
		this.midfieldToken=new KelimeToken<String>();
		midfieldToken.setTekrarlayabilir("+");
		midfieldToken.setSourceFieldName("inputParameters");
		patternTokenList.add(midfieldToken);
		
		//END_INPUT
		this.enderToken=new OzelKelimeToken(ReservedNaturalKeywords.END_WRITE,0,0,0);
		patternTokenList.add(enderToken);
		
		
	}
	
	@Override
	public AbstractCommand createElement(){
		ElementWrite elementDisplay = new ElementWrite(ReservedNaturalKeywords.WRITE_NOTITLE,"SCREEN.*.WRITE");
		return elementDisplay;
	}


	@Override
	public void setTokenToElement(AbstractCommand matchedCommand,
			AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {
		
		ElementWrite matchedCommandAdd=(ElementWrite) matchedCommand;
		
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
