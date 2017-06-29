package tr.com.vbt.natural.parser.general.patern;

import java.util.ArrayList;
import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.natural.parser.general.ElementAmpersand;
import tr.com.vbt.patern.carriage_return.AbstractPatternFromXToYWithoutCarriageReturn;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.GenelTipToken;
import tr.com.vbt.token.OzelKelimeToken;
import tr.com.vbt.token.TokenTipi;

/**
 *     FORMAT (4)   LS=132 PS=21 // (4) optional
 *
 */
public class PaternAmpersand extends AbstractPatternFromXToYWithoutCarriageReturn{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//AbstractPattern asp =new PaternAdd();

	}
	

	public PaternAmpersand() {
		super();
		
		//FORMAT
		this.starterToken=new OzelKelimeToken("AMPERSAND", 0, 0, 0);
		starterToken.setSourceFieldName("FIRST_COMMAND");
		starterToken.setTekrarlayabilir("+");
		patternTokenList.add(starterToken);
		
		
		//formatString
		this.midfieldToken=new GenelTipToken();
		midfieldToken.setTekrarlayabilir("+");
		midfieldToken.setSourceFieldName("parametersOfAmpersand");
		patternTokenList.add(midfieldToken);
		
		//Ender
		this.enderToken=new OzelKelimeToken(ReservedNaturalKeywords.END_AMPERSAND,0,0,0);
		patternTokenList.add(enderToken);
						
		
	}
	
	@Override
	public AbstractCommand createElement(){
		ElementAmpersand elementDisplay = new ElementAmpersand(ReservedNaturalKeywords.AMPERSAND,"GENERAL.*.AMPERSAND");
		return elementDisplay;
	}


	@Override
	public void setTokenToElement(AbstractCommand matchedCommand,
			AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {
		
		ElementAmpersand matchedCommandAdd=(ElementAmpersand) matchedCommand;
		
		super.setSatirNumarasi(matchedCommand,currentTokenForMatch, abstractTokenInPattern);
		
		if(abstractTokenInPattern.getSourceFieldName()==null){
		
		}else if(abstractTokenInPattern.getSourceFieldName().equals("parametersOfAmpersand")){
			if(currentTokenForMatch.getTip().equals(TokenTipi.Karakter)&&currentTokenForMatch.getDeger().equals('#')){
				return;
			}
			
			matchedCommandAdd.getDynamicCode().add(currentTokenForMatch);
			
			List<AbstractToken> sourceList;
			if(matchedCommandAdd.getParameters().get("parametersOfAmpersand")!=null){
				sourceList=(List<AbstractToken>) matchedCommandAdd.getParameters().get("parametersOfAmpersand");
			}else{
				sourceList=new ArrayList<AbstractToken>();
			}
			sourceList.add(currentTokenForMatch);
			matchedCommandAdd.getParameters().put("parametersOfAmpersand", sourceList);
		}
	}
		





	
	
	

	
	
	
	

}
