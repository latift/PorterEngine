package tr.com.vbt.natural.parser.basicverbs.patern;

import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.natural.parser.basicverbs.ElementExamineGivingIndex;
import tr.com.vbt.natural.parser.basicverbs.ElementExamineGivingPosition;
import tr.com.vbt.patern.AbstractPattern;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.GenelTipToken;
import tr.com.vbt.token.KelimeToken;
import tr.com.vbt.token.OzelKelimeToken;

/**
 * //EXAMINE SCR-TXT(II) FOR 'YETISKIN' GIVING POSITION LOC
 *
 */
public class PaternExamineForGivingPosition extends AbstractPattern{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//AbstractPattern asp =new PaternAdd();

	}
	

	public PaternExamineForGivingPosition() {
		super();
		
		
		
		//EXAMINE
		AbstractToken astKeyword=new OzelKelimeToken("EXAMINE", 0, 0, 0);
		astKeyword.setSourceFieldName("FIRST_COMMAND");
		astKeyword.setTekrarlayabilir("+");
		patternTokenList.add(astKeyword);
		
		// #LIMANLARXV(*)
		AbstractToken astSource=new GenelTipToken();
		astSource.setSourceFieldName("sourceToken");
		patternTokenList.add(astSource);
		
		//FOR
		AbstractToken astFor=new OzelKelimeToken("FOR", 0, 0, 0);
		patternTokenList.add(astFor);
		
		// #R-LIMAN
		AbstractToken astSearchVar=new GenelTipToken();
		astSearchVar.setSourceFieldName("searchVar");
		patternTokenList.add(astSearchVar);
				
		//GIVING_POSITION
		AbstractToken astKeyword2=new OzelKelimeToken("GIVING_POSITION", 0, 0, 0);
		patternTokenList.add(astKeyword2);
		
		//LIMANINDEX
		AbstractToken astIndex=new KelimeToken();
		astIndex.setSourceFieldName("resultPosition");
		patternTokenList.add(astIndex);
		
		//Ender
		AbstractToken enderToken=new OzelKelimeToken(ReservedNaturalKeywords.END_EXAMINE,0,0,0);
		patternTokenList.add(enderToken);
		
		
	}
	
	@Override
	public AbstractCommand createElement(){
		ElementExamineGivingPosition createdElement = new ElementExamineGivingPosition(ReservedNaturalKeywords.EXAMINE_GIVING_POSITION,"GENERAL.EXAMINE_GIVING_POSITION");
		return createdElement;
	}


	@Override
	public void setTokenToElement(AbstractCommand matchedCommand,
			AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {
		
		ElementExamineGivingPosition matchedCommandIndex=(ElementExamineGivingPosition) matchedCommand;
		
		super.setSatirNumarasi(matchedCommand,currentTokenForMatch, abstractTokenInPattern);if(abstractTokenInPattern.getSourceFieldName()==null){
		
		}else if(abstractTokenInPattern.getSourceFieldName().equals("sourceToken")){
			matchedCommandIndex.setSourceToken(currentTokenForMatch);
			matchedCommandIndex.getParameters().put("sourceToken", currentTokenForMatch);
			
		}else if(abstractTokenInPattern.getSourceFieldName().equals("searchVar")){
			matchedCommandIndex.setSearchVar(currentTokenForMatch);
			matchedCommandIndex.getParameters().put("searchVar", currentTokenForMatch);
			
		}else if(abstractTokenInPattern.getSourceFieldName().equals("resultPosition")){
			matchedCommandIndex.setResultPosition(currentTokenForMatch.getDeger().toString());
			matchedCommandIndex.getParameters().put("resultPosition", currentTokenForMatch.getDeger());
		}
	}


	@Override
	public AbstractCommand getmatchedCommand(List<AbstractToken> tokenListesi, int offset) {
		// TODO Auto-generated method stub
		return super.getmatchedCommand(tokenListesi, offset);
	}
	
	

}
