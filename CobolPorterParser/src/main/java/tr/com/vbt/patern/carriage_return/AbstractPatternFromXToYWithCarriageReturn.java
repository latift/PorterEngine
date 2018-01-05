package tr.com.vbt.patern.carriage_return;

import java.util.List;
import java.util.ListIterator;

import org.apache.log4j.Logger;


import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.patern.AbstractPattern;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.TokenTipi;

public abstract class AbstractPatternFromXToYWithCarriageReturn extends AbstractPattern {

	final static Logger logger = Logger.getLogger(AbstractPatternFromXToYWithCarriageReturn.class);


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
		
		AbstractToken midfieldToken=patternTokenList.get(1);
		
		AbstractToken enderToken=patternTokenList.get(2);
		
		if(!currentTokenForMatch.tokenMatchs(starterToken)){
			return null;
		}
		setTokenToElement(matchedCommand, currentTokenForMatch,starterToken);
		logger.info(" MATCHED"+currentTokenForMatch.getDeger());
		
		while(tokenListIterator.hasNext()){
			
			currentTokenForMatch=tokenListIterator.next();
			matchedCommand.increaseCommandsMatchPoint();
			if(currentTokenForMatch.getTip().equals(TokenTipi.SatirBasi)){
				continue;				
			}
			else if(currentTokenForMatch.getTip().equals(TokenTipi.SatirBasi)||(currentTokenForMatch.getTip().equals(TokenTipi.OzelKelime)
					&&!currentTokenForMatch.getDeger().equals(ReservedCobolKeywords.NOT) //IFElement için eklendi
					&&!currentTokenForMatch.getDeger().equals(ReservedCobolKeywords.OR) //IFElement için eklendi
					&&!currentTokenForMatch.getDeger().equals(ReservedCobolKeywords.AND))){ //IFElement için eklendi
				if(currentTokenForMatch.tokenMatchs(enderToken)){
					logger.info(" MATCHED"+currentTokenForMatch.getDeger());
					return matchedCommand;
				}else{
					return null;
				}
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
