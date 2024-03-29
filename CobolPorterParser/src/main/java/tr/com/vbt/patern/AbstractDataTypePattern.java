package tr.com.vbt.patern;

import java.util.List;
import java.util.ListIterator;

import org.apache.log4j.Logger;


import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.java.util.Utility;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.TokenTipi;

public abstract class AbstractDataTypePattern extends AbstractPattern {

	final static Logger logger = Logger.getLogger(AbstractDataTypePattern.class);
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	
	public AbstractDataTypePattern() {
		super();
	}



	public AbstractCommand getmatchedCommand(List<AbstractToken> tokenListesi, int offset){
		
		ListIterator<AbstractToken> tokenListIterator=tokenListesi.listIterator(offset);
		
		ListIterator<AbstractToken> abstractTokenInPatternIterator=patternTokenList.listIterator();
		
		AbstractToken currentTokenForMatch = null;
		
		AbstractCommand matchedCommand;
		
		matchedCommand=createElement();
		
		AbstractToken abstractTokenInPattern = null;
		
		boolean iterateCurrentToken=true;
		
		boolean lastElementMatched=false;
		
		while(abstractTokenInPatternIterator.hasNext()) {
		
			abstractTokenInPattern = abstractTokenInPatternIterator.next();
			
			
			if(!tokenListIterator.hasNext()){
				
				while(true){
					if(abstractTokenInPattern.isOptional()&&abstractTokenInPatternIterator.hasNext()){ //Kontrole Devam Et
						abstractTokenInPattern = abstractTokenInPatternIterator.next();
					}else if(abstractTokenInPattern.isOptional()&&!abstractTokenInPatternIterator.hasNext()){ //Patern Sonuna Gelinmisse  ve Hala optionalsa match etmiştir
						return matchedCommand;
					}else if(!abstractTokenInPattern.isOptional()&&abstractTokenInPatternIterator.hasNext()){ //Sona gelmeden mandatory buldu ise match etmez.
						return null;
					}
				}
			}
			
			if(iterateCurrentToken){
				while(tokenListIterator.hasNext())	{
					currentTokenForMatch=tokenListIterator.next();
					if(	!currentTokenForMatch.getTip().equals(TokenTipi.SatirBasi) && !currentTokenForMatch.getTip().equals(TokenTipi.Nokta)	){
						break;
					}
					matchedCommand.increaseCommandsMatchPoint();
					logger.info(" MATCHED  SatirBAsi Or Nokta POINT:"+matchedCommand.getCommandMatchPoint());
				}
			}
			
			if(!tokenListIterator.hasNext()){  //Sona Gelinmisse
				
				while(true){
					if(abstractTokenInPattern.isOptional()&&abstractTokenInPatternIterator.hasNext()){ //Kontrole Devam Et
						abstractTokenInPattern = abstractTokenInPatternIterator.next();
					}else if(abstractTokenInPattern.isOptional()&&!abstractTokenInPatternIterator.hasNext()){ //Patern Sonuna Gelinmisse  ve Hala optionalsa match etmiştir
						return matchedCommand;
					}else if(!abstractTokenInPattern.isOptional()&&abstractTokenInPatternIterator.hasNext()){ //Sona gelmeden mandatory buldu ise match etmez.
						return null;
					}else if(!abstractTokenInPattern.isOptional()&&!abstractTokenInPatternIterator.hasNext()){ //PAtern de ve tokne Array de sona geldi ise ve  optional değilse aşağıya geç ve kontrol et.
						break;
					}
				}
			}
			
			if(!Utility.DEBUG){
				Utility.controlDebug(patternTokenList,currentTokenForMatch);
			}
			if(Utility.DEBUG){
				logger.info("PATERN TOKEN KONTROL: Paterndeki Token:"+abstractTokenInPattern.getDeger()+" "+ abstractTokenInPattern.getTip()+"  Current Token: "+currentTokenForMatch.getDeger() + " "+abstractTokenInPattern.getTip());
			}
			
			if(!currentTokenForMatch.tokenMatchs(abstractTokenInPattern)||
					(abstractTokenInPattern.isPojoVariable()==true &&!currentTokenForMatch.isPojoVariable())||//&&Configuration.pojosAreDefinedInCode)||
					(abstractTokenInPattern.isLocalVariable()==true &&!currentTokenForMatch.isLocalVariable())){//&&Configuration.pojosAreDefinedInCode)){
					logger.info(" NOT MATCHED"+currentTokenForMatch.getDeger());
					Utility.DEBUG=false;
					lastElementMatched=false;
					if(abstractTokenInPattern.isOptional()){
						iterateCurrentToken=false;
						continue;
					}
					return null;
			}
			else{
					logger.info(" MATCHED"+currentTokenForMatch.getDeger());
					matchedCommand.setSourceCodeSentence(matchedCommand.getSourceCodeSentence(),currentTokenForMatch);
					setTokenToElement(matchedCommand, currentTokenForMatch,abstractTokenInPattern);
					matchedCommand.increaseCommandsMatchPoint();
					logger.info("  POINT:"+matchedCommand.getCommandMatchPoint());
					iterateCurrentToken=true;
					lastElementMatched=true;
			}
		}
		/*if(!lastElementMatched){ //Son Token optional ise son token match olmadan da patern match edebilir. Bu durumda currentToken daki son iterasyondaki puan artisi geriye alinir. 
			matchedCommand.decreaseCommandsMatchPoint();
			logger.info("DECREASE POINT:"+ matchedCommand.toString());
		}*/
		logger.info("PATERN TOKEN KONTROL MATCH:"+ matchedCommand.toString());
		Utility.DEBUG=false;
		return matchedCommand;
	}

	
	
}
