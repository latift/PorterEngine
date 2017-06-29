package tr.com.vbt.cobol.parser.basicverbs.patern;

import java.util.ArrayList;
import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.basicverbs.ElementCompute;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.patern.carriage_return.AbstractPatternFromXToYWithCarriageReturn;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.GenelTipToken;
import tr.com.vbt.token.OzelKelimeToken;

/**
 * COMPUTE BORCT  = 0.
 * 
TODO: If icinde ise Nokta ile bitmiyor. NE yapmalı? 
Örnek: 
027091        IF SENE NOT = 'F1' AND                                    02709100
027093           VLM-DEGER ( ACOCC-XX2 , I ) IS NUMERIC THEN            02709300
027095          COMPUTE ALACT = ALACT + VLM-DEGER ( ACOCC-XX2 , I )     02709500
027096        END-IF                                                    02709600    
 */
public class PaternCompute extends AbstractPatternFromXToYWithCarriageReturn{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//AbstractPattern asp =new PaternAdd();

	}
	

	public PaternCompute() {
		super();
		
		//PERFORM
		AbstractToken astKeyword=new OzelKelimeToken("COMPUTE", 0, 0, 0);
		astKeyword.setTekrarlayabilir("+");
		patternTokenList.add(astKeyword);
		
		
		//dataToDisplay
		AbstractToken astSource=new GenelTipToken();
		astSource.setTekrarlayabilir("+");
		astSource.setSourceFieldName("aritmethicOperators");
		patternTokenList.add(astSource);
		
		//Ender
		AbstractToken ender=new OzelKelimeToken(ReservedCobolKeywords.END_COMPUTE,0,0,0);
		patternTokenList.add(ender);
						
		
		
	}
	
	@Override
	public AbstractCommand createElement(){
		ElementCompute createdElement = new ElementCompute(ReservedCobolKeywords.COMPUTE,"PROCEDURE_DIVISION.*.COMPUTE");
		return createdElement;
	}


	@Override
	public void setTokenToElement(AbstractCommand matchedCommand,
			AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {
		
		ElementCompute matchedCommandAdd=(ElementCompute) matchedCommand;
		
		super.setSatirNumarasi(matchedCommand,currentTokenForMatch, abstractTokenInPattern);if(abstractTokenInPattern.getSourceFieldName()==null){
		
		}else if(abstractTokenInPattern.getSourceFieldName().equals("aritmethicOperators")){
			
			matchedCommandAdd.getAritmethicOperators().add(currentTokenForMatch);
			
			List<AbstractToken> sourceList;
			
			if(matchedCommandAdd.getParameters().get("aritmethicOperators")!=null){
				
				sourceList=(List<AbstractToken>) matchedCommandAdd.getParameters().get("aritmethicOperators");
				
			}else{
				
				sourceList=new ArrayList<AbstractToken>();
			}
			
			sourceList.add((AbstractToken) currentTokenForMatch);
			matchedCommandAdd.getParameters().put("aritmethicOperators", sourceList);
		}
		
	}
			
	

}
