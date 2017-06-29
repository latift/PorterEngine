package tr.com.vbt.natural.parser.basicverbs.patern;

import java.util.ArrayList;
import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.natural.parser.basicverbs.ElementSetKey;
import tr.com.vbt.patern.carriage_return.AbstractPatternFromXToYWithoutCarriageReturn;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.GenelTipToken;
import tr.com.vbt.token.OzelKelimeToken;
import tr.com.vbt.token.TokenTipi;

/**
 *     FORMAT (4)   LS=132 PS=21 // (4) optional
 *
 */
public class PaternSetKey extends AbstractPatternFromXToYWithoutCarriageReturn{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//AbstractPattern asp =new PaternAdd();

	}
	

	public PaternSetKey() {
		super();
		
		//FORMAT
		this.starterToken=new OzelKelimeToken("SET_KEY", 0, 0, 0);
		starterToken.setTekrarlayabilir("+");
		starterToken.setSourceFieldName("FIRST_COMMAND");
		patternTokenList.add(starterToken);
		
		
		//formatString
		this.midfieldToken=new GenelTipToken<>();
		midfieldToken.setTekrarlayabilir("+");
		midfieldToken.setSourceFieldName("parametersOfSetKey");
		patternTokenList.add(midfieldToken);
		
		//Ender
		this.enderToken=new OzelKelimeToken(ReservedNaturalKeywords.END_SET_KEY,0,0,0);
		patternTokenList.add(enderToken);
						
		
	}
	
	@Override
	public AbstractCommand createElement(){
		ElementSetKey elementDisplay = new ElementSetKey(ReservedNaturalKeywords.SET_KEY,"GENERAL.*.SET_KEY");
		return elementDisplay;
	}


	@Override
	public void setTokenToElement(AbstractCommand matchedCommand,
			AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {
		
		ElementSetKey matchedCommandAdd=(ElementSetKey) matchedCommand;
		
		super.setSatirNumarasi(matchedCommand,currentTokenForMatch, abstractTokenInPattern);if(abstractTokenInPattern.getSourceFieldName()==null){
		
		}else if(abstractTokenInPattern.getSourceFieldName().equals("parametersOfSetKey")){
			if(currentTokenForMatch.getTip().equals(TokenTipi.Karakter)&&currentTokenForMatch.getDeger().equals('#')){
				return;
			}
			
			matchedCommandAdd.getParametersOfSetKey().add(currentTokenForMatch);
			
			List<AbstractToken> sourceList;
			if(matchedCommandAdd.getParameters().get("parametersOfSetKey")!=null){
				sourceList=(List<AbstractToken>) matchedCommandAdd.getParameters().get("parametersOfSetKey");
			}else{
				sourceList=new ArrayList<AbstractToken>();
			}
			sourceList.add(currentTokenForMatch);
			matchedCommandAdd.getParameters().put("parametersOfSetKey", sourceList);
		}
	}
		
	
	public AbstractCommand getmatchedCommand(List<AbstractToken> tokenListesi, int offset) {
		return super.getmatchedCommand(tokenListesi, offset);
	}





	
	
	

	
	
	
	

}
