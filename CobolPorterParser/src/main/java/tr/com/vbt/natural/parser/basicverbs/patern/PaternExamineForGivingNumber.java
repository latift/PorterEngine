package tr.com.vbt.natural.parser.basicverbs.patern;

import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.natural.parser.basicverbs.ElementExamineGivingIndex;
import tr.com.vbt.natural.parser.basicverbs.ElementExamineGivingNumber;
import tr.com.vbt.patern.AbstractPattern;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.ArrayToken;
import tr.com.vbt.token.GenelTipToken;
import tr.com.vbt.token.KelimeToken;
import tr.com.vbt.token.OzelKelimeToken;

/**
 * *S**EXAMINE #SECIM(*) FOR'X' GIVING NUMBER #XDATA
 *
 */
public class PaternExamineForGivingNumber extends AbstractPattern{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//AbstractPattern asp =new PaternAdd();

	}
	

	public PaternExamineForGivingNumber() {
		super();
		
		
		
		//EXAMINE
		AbstractToken astKeyword=new OzelKelimeToken("EXAMINE", 0, 0, 0);
		astKeyword.setSourceFieldName("FIRST_COMMAND");
		astKeyword.setTekrarlayabilir("+");
		patternTokenList.add(astKeyword);
		
		// #LIMANLARXV(*)
		AbstractToken astSource=new GenelTipToken();
		astSource.setSourceFieldName("array");
		patternTokenList.add(astSource);
		
		//FOR
		AbstractToken astFor=new OzelKelimeToken("EXAMINEFOR", 0, 0, 0);
		patternTokenList.add(astFor);
		
		// #R-LIMAN
		AbstractToken astSearchVar=new GenelTipToken();
		astSearchVar.setSourceFieldName("searchVar");
		patternTokenList.add(astSearchVar);
				
		//GIVING_INDEX
		AbstractToken astKeyword2=new OzelKelimeToken("GIVING_NUMBER", 0, 0, 0);
		patternTokenList.add(astKeyword2);
		
		//LIMANINDEX
		AbstractToken astIndex=new KelimeToken();
		astIndex.setSourceFieldName("resultIndex");
		patternTokenList.add(astIndex);
		
		//Ender
		AbstractToken enderToken=new OzelKelimeToken(ReservedNaturalKeywords.END_EXAMINE,0,0,0);
		patternTokenList.add(enderToken);
		
		
	}
	
	@Override
	public AbstractCommand createElement(){
		ElementExamineGivingNumber createdElement = new ElementExamineGivingNumber(ReservedNaturalKeywords.EXAMINE_GIVING_NUMBER,"GENERAL.EXAMINE_GIVING_NUMBER");
		return createdElement;
	}


	@Override
	public void setTokenToElement(AbstractCommand matchedCommand,
			AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {
		
		ElementExamineGivingNumber matchedCommandIndex=(ElementExamineGivingNumber) matchedCommand;
		
		super.setSatirNumarasi(matchedCommand,currentTokenForMatch, abstractTokenInPattern);if(abstractTokenInPattern.getSourceFieldName()==null){
		
		}else if(abstractTokenInPattern.getSourceFieldName().equals("array")){
			matchedCommandIndex.setArrayToken(abstractTokenInPattern);
			matchedCommandIndex.getParameters().put("array", currentTokenForMatch);
			
		}else if(abstractTokenInPattern.getSourceFieldName().equals("searchVar")){
			matchedCommandIndex.setSearchVar(abstractTokenInPattern);
			matchedCommandIndex.getParameters().put("searchVar", currentTokenForMatch);
			
		}else if(abstractTokenInPattern.getSourceFieldName().equals("resultIndex")){
			matchedCommandIndex.setResultIndex((String) abstractTokenInPattern.getDeger());
			matchedCommandIndex.getParameters().put("resultIndex", currentTokenForMatch.getDeger());
		}
	}


	@Override
	public AbstractCommand getmatchedCommand(List<AbstractToken> tokenListesi, int offset) {
		// TODO Auto-generated method stub
		return super.getmatchedCommand(tokenListesi, offset);
	}
	
	

}
