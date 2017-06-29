package tr.com.vbt.patern.carriage_return;

import java.util.List;
import java.util.ListIterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.patern.AbstractPattern;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.TokenTipi;

public abstract class AbstractPatternFromXOptionalYToNextKeyword extends AbstractPattern {

	final static Logger logger = LoggerFactory.getLogger(AbstractPatternFromXOptionalYToNextKeyword.class);


	/** Sadece X TO Y yi destekler.
	 * Önek: * Comment1, Comment2, Comment3 *
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
		
		AbstractToken starterToken=patternTokenList.get(0);
		
		AbstractToken optionalStarterToken1=patternTokenList.get(1);
		
		AbstractToken optionalStarterToken2=patternTokenList.get(2);
		
		AbstractToken midfieldToken=patternTokenList.get(3);
		
		if(!currentTokenForMatch.tokenMatchs(starterToken)){
			return null;
		}
		setTokenToElement(matchedCommand, currentTokenForMatch,starterToken);
		logger.info(" MATCHED"+currentTokenForMatch.getDeger());
		
		
		currentTokenForMatch=tokenListIterator.next();
		matchedCommand.increaseCommandsMatchPoint();
		/*if(!(currentTokenForMatch.tokenMatchs(optionalStarterToken1)||currentTokenForMatch.tokenMatchs(optionalStarterToken2))){
			return null;
		}*/
		if(currentTokenForMatch.tokenMatchs(optionalStarterToken1)){
			matchedCommand.setSourceCodeSentence(matchedCommand.getSourceCodeSentence(),currentTokenForMatch);
			setTokenToElement(matchedCommand, currentTokenForMatch,optionalStarterToken1);
		}else  if(currentTokenForMatch.tokenMatchs(optionalStarterToken2)){
			matchedCommand.setSourceCodeSentence(matchedCommand.getSourceCodeSentence(),currentTokenForMatch);
			setTokenToElement(matchedCommand, currentTokenForMatch,optionalStarterToken2);
		}else{
			return null;
		}
		logger.info(" MATCHED"+currentTokenForMatch.getDeger());
		
		
		while(tokenListIterator.hasNext()){
			
			currentTokenForMatch=tokenListIterator.next();
			matchedCommand.increaseCommandsMatchPoint();
			
			if(currentTokenForMatch.getTip().equals(TokenTipi.OzelKelime)){
					logger.info(" NEW KEYWORD REACHED"+currentTokenForMatch.getDeger());
					matchedCommand.decreaseCommandsMatchPoint(); //NEw KEyword puan artırmamalı.
					return matchedCommand;
							
			}
			else if(currentTokenForMatch.getTip().equals(TokenTipi.Nokta)){
				continue;
			}
			else{
					if(currentTokenForMatch.tokenMatchs(midfieldToken)){
						logger.info(" MATCHED"+currentTokenForMatch.getDeger());
						matchedCommand.setSourceCodeSentence(matchedCommand.getSourceCodeSentence(),currentTokenForMatch);
						setTokenToElement(matchedCommand, currentTokenForMatch,midfieldToken);
					}
				}
		}
		return null;
	}
	
}
