package tr.com.vbt.natural.parser.basicverbs.patern;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.natural.parser.basicverbs.ElementExamineReplaceWith;
import tr.com.vbt.patern.AbstractPattern;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.GenelTipToken;
import tr.com.vbt.token.KelimeToken;
import tr.com.vbt.token.OzelKelimeToken;

/**
 //  EXAMINE FULL PADI1 FOR ' '  REPLACE WITH '*'
  * 
EXAMINE_FULL  Uzunluk:0 Satir No:6 Tipi:OzelKelime
PADI1  Uzunluk:0 Satir No:6 Tipi:Kelime LocalVariable
FOR  Uzunluk:0 Satir No:6 Tipi:OzelKelime
   Uzunluk:0 Satir No:6 Tipi:Kelime
REPLACE  Uzunluk:0 Satir No:6 Tipi:Kelime LocalVariable
WITH  Uzunluk:0 Satir No:6 Tipi:OzelKelime
*  Uzunluk:0 Satir No:6 Tipi:Kelime
END_EXAMINE  Uzunluk:0 Satir No:7 Tipi:OzelKelime
 *
 */
public class PaternExamineFullReplaceWith extends AbstractPattern{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//AbstractPattern asp =new PaternAdd();

	}
	

	public PaternExamineFullReplaceWith() {
		super();
		
		//EXAMINE
		AbstractToken starterToken=new OzelKelimeToken("EXAMINE_FULL", 0, 0, 0);
		starterToken.setSourceFieldName("FIRST_COMMAND");
		starterToken.setTekrarlayabilir("+");
		patternTokenList.add(starterToken);
		
		
		//formatString
		AbstractToken midfieldToken=new GenelTipToken();
		midfieldToken.setTekrarlayabilir("+");
		midfieldToken.setSourceFieldName("sourceToken");
		patternTokenList.add(midfieldToken);
		
		//FOR
		AbstractToken astFor=new OzelKelimeToken("EXAMINEFOR", 0, 0, 0);
		patternTokenList.add(astFor);
		
		
		// #R-LIMAN
		AbstractToken astSearchVar=new GenelTipToken();
		astSearchVar.setSourceFieldName("searchVar");
		patternTokenList.add(astSearchVar);
				
		//REPLACE_WITH
		AbstractToken astKeyword2=new OzelKelimeToken("REPLACE_WITH", 0, 0, 0);
		patternTokenList.add(astKeyword2);
		
		//*
		AbstractToken astIndex=new KelimeToken();
		astIndex.setSourceFieldName("replaceVar");
		patternTokenList.add(astIndex);
		
		//Ender
		AbstractToken enderToken=new OzelKelimeToken(ReservedNaturalKeywords.END_EXAMINE,0,0,0);
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
		
		ElementExamineReplaceWith matchedCommandIndex=(ElementExamineReplaceWith) matchedCommand;
		
		super.setSatirNumarasi(matchedCommand,currentTokenForMatch, abstractTokenInPattern);
		
		if(abstractTokenInPattern.getSourceFieldName()==null){
		
		}else if(abstractTokenInPattern.getSourceFieldName().equals("sourceToken")){
			matchedCommandIndex.setSourceToken(currentTokenForMatch);
			matchedCommandIndex.getParameters().put("sourceToken", currentTokenForMatch);
			
		}else if(abstractTokenInPattern.getSourceFieldName().equals("searchVar")){
			matchedCommandIndex.setSearchVar(currentTokenForMatch);
			matchedCommandIndex.getParameters().put("searchVar", currentTokenForMatch);
			
		}else if(abstractTokenInPattern.getSourceFieldName().equals("replaceVar")){
			matchedCommandIndex.setReplaceVar(currentTokenForMatch);
			matchedCommandIndex.getParameters().put("replaceVar", currentTokenForMatch);
		}
	}


}
