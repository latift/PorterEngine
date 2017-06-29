package tr.com.vbt.natural.parser.basicverbs.patern;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.natural.parser.basicverbs.ElementBecomesEqualTo;
import tr.com.vbt.patern.carriage_return.AbstractPatternFromXToYWithCarriageReturn;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.GenelTipToken;
import tr.com.vbt.token.KarakterToken;
import tr.com.vbt.token.OzelKelimeToken;
import tr.com.vbt.token.TokenTipi;

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

	final static Logger logger = LoggerFactory.getLogger(PaternCompute.class);
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//AbstractPattern asp =new PaternAdd();

	}
	
	AbstractToken becomesEqualTo,copyToToken,midfieldToken2, copyFromToken, becomesEqualToEnd;

	public PaternCompute() {
		super();
		
		
		becomesEqualTo=new OzelKelimeToken("COMPUTE", 0, 0, 0);
		becomesEqualTo.setTekrarlayabilir("+");
		patternTokenList.add(becomesEqualTo);
		
		//FARK1
		copyToToken=new GenelTipToken();
		copyToToken.setSourceFieldName("FIRST_COMMAND");
		patternTokenList.add(copyToToken);
		
		
		//=
		midfieldToken2=new KarakterToken('=', 0,0,0);
		patternTokenList.add(midfieldToken2);
		
		//=
		copyFromToken=new GenelTipToken();
		copyFromToken.setSourceFieldName("copyFrom");
		patternTokenList.add(copyFromToken);
		
		
		AbstractToken ender=new OzelKelimeToken(ReservedNaturalKeywords.END_COMPUTE,0,0,0);
		patternTokenList.add(ender);

		
	}
	
	@Override
	public AbstractCommand createElement(){
		ElementBecomesEqualTo createdElement = new ElementBecomesEqualTo(ReservedNaturalKeywords.BECOMES_EQUAL_TO,"GENERAL.*.BECOMES_EQUAL_TO");
		return createdElement;
	}


	@Override
	public void setTokenToElement(AbstractCommand matchedCommand,
			AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {
		
		ElementBecomesEqualTo matchedCommandAdd=(ElementBecomesEqualTo) matchedCommand;
		
		super.setSatirNumarasi(matchedCommand,currentTokenForMatch, abstractTokenInPattern);if(abstractTokenInPattern.getSourceFieldName()==null){
		
		}else if(abstractTokenInPattern.getSourceFieldName().equals("FIRST_COMMAND")){
			matchedCommandAdd.setCopyTo( currentTokenForMatch);
			matchedCommandAdd.getParameters().put("FIRST_COMMAND", matchedCommandAdd.getCopyTo());
			
		}else if(abstractTokenInPattern.getSourceFieldName().equals("copyFrom")){
			matchedCommandAdd.getCopyFrom().add(currentTokenForMatch);
			
			List<AbstractToken> sourceList;
			if(matchedCommandAdd.getParameters().get("copyFrom")!=null){
				sourceList=(List<AbstractToken>) matchedCommandAdd.getParameters().get("copyFrom");
			}else{
				sourceList=new ArrayList<AbstractToken>();
			}
			String deger = null;
			sourceList.add(currentTokenForMatch);
			matchedCommandAdd.getParameters().put("copyFrom", sourceList);
		}
		
	}
	
	/** FARK1 := T-MIKTAR - T-UPLIFT
	 */
	@Override
	public AbstractCommand getmatchedCommand(List<AbstractToken> tokenListesi, int offset) {
		
		ListIterator<AbstractToken> tokenListIterator=tokenListesi.listIterator(offset);
		
		AbstractToken currentTokenForMatch = null;
		
		AbstractCommand matchedCommand;
		
		matchedCommand=createElement();
		
		if(!tokenListIterator.hasNext()){
			return null;
		}
		
		while(tokenListIterator.hasNext())	{ //Satir Başı bu paternlerde önemli ancak bitişte önemli başlangıçta bunu atlıyoruz.
			currentTokenForMatch=tokenListIterator.next();
			matchedCommand.increaseCommandsMatchPoint();
			if(!currentTokenForMatch.getTip().equals(TokenTipi.SatirBasi)){
				break;
			}
		}
		
		if(!tokenListIterator.hasNext()){
			return null;
		}
		
		
		
		if(!currentTokenForMatch.tokenMatchs(becomesEqualTo)){
			return null;
		}
		currentTokenForMatch=tokenListIterator.next();
		matchedCommand.increaseCommandsMatchPoint();
		
		//*********************************************************
		if(!currentTokenForMatch.tokenMatchs(copyToToken)){
			return null;
		}
		matchedCommand.setSourceCodeSentence(matchedCommand.getSourceCodeSentence(),currentTokenForMatch);
		setTokenToElement(matchedCommand, currentTokenForMatch,copyToToken);
		logger.info(" MATCHED"+currentTokenForMatch.getDeger());
		
		
		//*********************************************************
		currentTokenForMatch=tokenListIterator.next();
		matchedCommand.increaseCommandsMatchPoint();
		if(!currentTokenForMatch.tokenMatchs(midfieldToken2)){
			return null;
		}
		logger.info(" MATCHED"+currentTokenForMatch.getDeger());
		//*********************************************************
		
		while(tokenListIterator.hasNext()){
			
			currentTokenForMatch=tokenListIterator.next();
			matchedCommand.increaseCommandsMatchPoint();
			
			if(currentTokenForMatch.getTip().equals(TokenTipi.OzelKelime)&&currentTokenForMatch.getDeger().equals("END_COMPUTE")){
						logger.info(" MATCHED"+currentTokenForMatch.getDeger());
						return matchedCommand;
			}
			else{
						logger.info(" MATCHED"+currentTokenForMatch.getDeger());
						//matchedCommand.setSourceCodeSentence(matchedCommand.getSourceCodeSentence(),currentTokenForMatch);
						
						setTokenToElement(matchedCommand, currentTokenForMatch,copyFromToken);
				}
		}
		return null;
	}
			
	

}
