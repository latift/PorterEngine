package tr.com.vbt.natural.parser.basicverbs.patern;

import java.util.ArrayList;
import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.natural.parser.basicverbs.ElementReset;
import tr.com.vbt.patern.carriage_return.AbstractPatternFromXToYWithoutCarriageReturn;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.GenelTipToken;
import tr.com.vbt.token.OzelKelimeToken;

/**
 * MOVE #MARK #MARK2
 *     
RESET  Uzunluk:0 Satir No:19 Tipi:OzelKelime
#  Uzunluk:1 Satir No:19 Tipi:Karakter
MARK  Uzunluk:0 Satir No:19 Tipi:OzelKelime
#  Uzunluk:1 Satir No:19 Tipi:Karakter
MARK2  Uzunluk:0 Satir No:19 Tipi:OzelKelime
 *
 */
public class PaternReset extends AbstractPatternFromXToYWithoutCarriageReturn{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//AbstractPattern asp =new PaternAdd();

	}
	

	public PaternReset() {
		super();
		
		//MOVE
		this.starterToken=new OzelKelimeToken<String>("RESET", 0, 0, 0);
		starterToken.setTekrarlayabilir("+");
		starterToken.setSourceFieldName("FIRST_COMMAND");
		patternTokenList.add(starterToken);
		
			
		//dataToDisplay
		this.midfieldToken=new GenelTipToken<String>();
		midfieldToken.setSourceFieldName("resetVariableList");
		patternTokenList.add(midfieldToken);
		
		//Ender_Reset
		this.enderToken=new OzelKelimeToken(ReservedNaturalKeywords.END_RESET,0,0,0);
		patternTokenList.add(enderToken);
								
	}
	
	@Override
	public AbstractCommand createElement(){
		ElementReset elementReset= new ElementReset(ReservedNaturalKeywords.RESET,"GENERAL.*.RESET");
		return elementReset;
	}


	@Override
	public void setTokenToElement(AbstractCommand matchedCommand,
			AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {
		
		ElementReset matchedCommandAdd=(ElementReset) matchedCommand;
		
		super.setSatirNumarasi(matchedCommand,currentTokenForMatch, abstractTokenInPattern);if(abstractTokenInPattern.getSourceFieldName()==null){
		
		}else if(abstractTokenInPattern.getSourceFieldName().equals("resetVariableList")){
			
			if(!(currentTokenForMatch.getDeger() instanceof String)){
				return;
			}
				
			matchedCommandAdd.getResetVariableList().add((AbstractToken) currentTokenForMatch);
			List<AbstractToken> sourceList;
			if(matchedCommandAdd.getParameters().get("resetVariableList")!=null){
				sourceList=(List<AbstractToken>) matchedCommandAdd.getParameters().get("resetVariableList");
			}else{
				sourceList=new ArrayList<AbstractToken>();
			}
			sourceList.add(currentTokenForMatch);
			matchedCommandAdd.getParameters().put("resetVariableList", sourceList);
		}
	}
		
	public AbstractCommand getmatchedCommand(List<AbstractToken> tokenListesi, int offset) {
		return super.getmatchedCommand(tokenListesi, offset);
	}
}
