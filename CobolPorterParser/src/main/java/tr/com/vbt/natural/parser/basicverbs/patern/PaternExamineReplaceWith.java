package tr.com.vbt.natural.parser.basicverbs.patern;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.natural.parser.basicverbs.ElementExamineForDelete;
import tr.com.vbt.natural.parser.basicverbs.ElementExamineReplaceWith;
import tr.com.vbt.patern.carriage_return.AbstractPatternFromXToYWithoutCarriageReturn;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.GenelTipToken;
import tr.com.vbt.token.OzelKelimeToken;

/**
 //  EXAMINE  BASLIK '-' REPLACE WITH ' '
  * 
  * EXAMINE  Uzunluk:0 Satir No:98 Tipi:OzelKelime
BASLIK  Uzunluk:0 Satir No:98 Tipi:Kelime LocalVariable
-  Uzunluk:0 Satir No:98 Tipi:Kelime
REPLACE  Uzunluk:0 Satir No:98 Tipi:Kelime LocalVariable
WITH  Uzunluk:0 Satir No:98 Tipi:OzelKelime
   Uzunluk:0 Satir No:98 Tipi:Kelime
 *
 */
public class PaternExamineReplaceWith extends AbstractPatternFromXToYWithoutCarriageReturn{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//AbstractPattern asp =new PaternAdd();

	}
	

	public PaternExamineReplaceWith() {
		super();
		
		//EXAMINE
		this.starterToken=new OzelKelimeToken("EXAMINE", 0, 0, 0);
		starterToken.setSourceFieldName("FIRST_COMMAND");
		starterToken.setTekrarlayabilir("+");
		patternTokenList.add(starterToken);
		
		
		//formatString
		this.midfieldToken=new GenelTipToken();
		midfieldToken.setTekrarlayabilir("+");
		midfieldToken.setSourceFieldName("examineParams");
		patternTokenList.add(midfieldToken);
		
		//Ender
		this.enderToken=new OzelKelimeToken(ReservedNaturalKeywords.END_EXAMINE,0,0,0);
		patternTokenList.add(enderToken);
		
	}
	
	@Override
	public AbstractCommand createElement(){
		ElementExamineReplaceWith createdElement = new ElementExamineReplaceWith(ReservedNaturalKeywords.EXAMINE_REPLACE_WITH,"GENERAL.*.EXAMINE_REPLACE_WITH");
		return createdElement;
	}


	@Override
	public void setTokenToElement(AbstractCommand matchedCommand,
			AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {
		
		ElementExamineReplaceWith matchedCommandAdd=(ElementExamineReplaceWith) matchedCommand;
		
		super.setSatirNumarasi(matchedCommand,currentTokenForMatch, abstractTokenInPattern);
		
		if(abstractTokenInPattern.getSourceFieldName()==null){
		
		}
	}


}
