package tr.com.vbt.natural.parser.basicverbs.patern;

import java.util.ArrayList;
import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.natural.parser.basicverbs.ElementFormat;
import tr.com.vbt.patern.carriage_return.AbstractPatternFromXToYWithoutCarriageReturn;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.GenelTipToken;
import tr.com.vbt.token.OzelKelimeToken;
import tr.com.vbt.token.TokenTipi;

/**
 *     FORMAT (4)   LS=132 PS=21 // (4) optional
 *
 */
public class PaternFormat extends AbstractPatternFromXToYWithoutCarriageReturn{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//AbstractPattern asp =new PaternAdd();

	}
	

	public PaternFormat() {
		super();
		
		//FORMAT
		this.starterToken=new OzelKelimeToken("FORMAT", 0, 0, 0);
		starterToken.setSourceFieldName("FIRST_COMMAND");
		starterToken.setTekrarlayabilir("+");
		patternTokenList.add(starterToken);
		
		
		//formatString
		this.midfieldToken=new GenelTipToken();
		midfieldToken.setTekrarlayabilir("+");
		midfieldToken.setSourceFieldName("parametersOfFormat");
		patternTokenList.add(midfieldToken);
		
		//Ender
		this.enderToken=new OzelKelimeToken(ReservedNaturalKeywords.END_FORMAT,0,0,0);
		patternTokenList.add(enderToken);
						
		
	}
	
	@Override
	public AbstractCommand createElement(){
		ElementFormat elementDisplay = new ElementFormat(ReservedNaturalKeywords.FORMAT,"GENERAL.*.FORMAT");
		return elementDisplay;
	}


	@Override
	public void setTokenToElement(AbstractCommand matchedCommand,
			AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {
		
		ElementFormat matchedCommandAdd=(ElementFormat) matchedCommand;
		
		super.setSatirNumarasi(matchedCommand,currentTokenForMatch, abstractTokenInPattern);if(abstractTokenInPattern.getSourceFieldName()==null){
		
		}else if(abstractTokenInPattern.getSourceFieldName().equals("parametersOfFormat")){
			if(currentTokenForMatch.getTip().equals(TokenTipi.Karakter)&&currentTokenForMatch.getDeger().equals('#')){
				return;
			}
			
			matchedCommandAdd.getParametersOfFormat().add(currentTokenForMatch);
			
			List<AbstractToken> sourceList;
			if(matchedCommandAdd.getParameters().get("parametersOfFormat")!=null){
				sourceList=(List<AbstractToken>) matchedCommandAdd.getParameters().get("parametersOfFormat");
			}else{
				sourceList=new ArrayList<AbstractToken>();
			}
			sourceList.add(currentTokenForMatch);
			matchedCommandAdd.getParameters().put("parametersOfFormat", sourceList);
		}
	}
		





	
	
	

	
	
	
	

}
