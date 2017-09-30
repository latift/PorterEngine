package tr.com.vbt.patern.carriage_return;

import java.util.List;
import java.util.ListIterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.patern.AbstractPattern;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.TokenTipi;

public abstract class AbstractPatternFromXToYWithoutCarriageReturn extends AbstractPattern {

	final static Logger logger = LoggerFactory.getLogger(AbstractPattern.class);
	protected AbstractToken starterToken;
	
	protected AbstractToken midfieldToken;
	
	protected AbstractToken enderToken;
	

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
		
		/*AbstractToken starterToken=patternTokenList.get(0);
		
		AbstractToken midfieldToken=patternTokenList.get(1);
		
		AbstractToken enderToken=patternTokenList.get(2);*/
		
		if(!currentTokenForMatch.tokenMatchs(starterToken)){
			return null;
		}
		setTokenToElement(matchedCommand, currentTokenForMatch,starterToken);
		logger.info(" MATCHED: "+currentTokenForMatch.getDeger());
		logger.info("...");
		
		while(tokenListIterator.hasNext()){
			
			currentTokenForMatch=tokenListIterator.next();
			matchedCommand.increaseCommandsMatchPoint();
			
			if(	currentTokenForMatch.getTip().equals(TokenTipi.OzelKelime)
					&&!currentTokenForMatch.getDeger().equals(ReservedCobolKeywords.NOT) //IFElement için eklendi
					&&!currentTokenForMatch.getDeger().equals(ReservedCobolKeywords.OR) //IFElement için eklendi
					&&!currentTokenForMatch.getDeger().equals(ReservedCobolKeywords.AND) //IFElement için eklendi
					&&!currentTokenForMatch.getDeger().equals(ReservedCobolKeywords.EQUAL)
					&&!currentTokenForMatch.getDeger().equals(ReservedCobolKeywords.IS)
					&&!currentTokenForMatch.getDeger().equals(ReservedCobolKeywords.IS_NUMERIC)
					&&!currentTokenForMatch.getDeger().equals(ReservedNaturalKeywords.MASK)
					&&!currentTokenForMatch.getDeger().equals(ReservedNaturalKeywords.WITH_TEXT) //Input için eklendi
					&&!currentTokenForMatch.getDeger().equals(ReservedNaturalKeywords.SUBSTR)
					&&!currentTokenForMatch.getDeger().equals(ReservedNaturalKeywords.EQ)
					&&!currentTokenForMatch.getDeger().equals(ReservedNaturalKeywords.NE)
					&&!currentTokenForMatch.getDeger().equals(ReservedNaturalKeywords.VIEW)
					&&!currentTokenForMatch.getDeger().equals(ReservedNaturalKeywords.GE)
					&&!currentTokenForMatch.getDeger().equals(ReservedNaturalKeywords.GT)
					&&!currentTokenForMatch.getDeger().equals(ReservedNaturalKeywords.LE)
					&&!currentTokenForMatch.getDeger().equals(ReservedNaturalKeywords.LT)
					&&!currentTokenForMatch.getDeger().equals(ReservedNaturalKeywords.THRU)
					&&!currentTokenForMatch.getDeger().equals(ReservedNaturalKeywords.INTO)
					&&!currentTokenForMatch.getDeger().equals(ReservedNaturalKeywords.MAX)
					&&!currentTokenForMatch.getDeger().equals(ReservedNaturalKeywords.COUNT)
					&&!currentTokenForMatch.getDeger().equals(ReservedNaturalKeywords.SUM)
					&&!currentTokenForMatch.getDeger().equals(ReservedNaturalKeywords.MIN)
					&&!currentTokenForMatch.getDeger().equals(ReservedNaturalKeywords.DISTINCT)
					&&!currentTokenForMatch.getDeger().equals(ReservedNaturalKeywords.FROM)
					&&!currentTokenForMatch.getDeger().equals(ReservedNaturalKeywords.SORTED_BY)
					&&!currentTokenForMatch.getDeger().equals(ReservedNaturalKeywords.OFF)
					&&!currentTokenForMatch.getDeger().equals(ReservedNaturalKeywords.ON)
					&&!currentTokenForMatch.getDeger().equals(ReservedNaturalKeywords.REPLACE)
					&&!currentTokenForMatch.getDeger().equals(ReservedNaturalKeywords.WITH)
					&&!currentTokenForMatch.getDeger().equals(ReservedCobolKeywords.NUMERIC)){ //IFElement için eklendi
				if(currentTokenForMatch.tokenMatchs(enderToken)){
					logger.info(" MATCHED: "+currentTokenForMatch.getDeger());
					return matchedCommand;
				}else{
					return null;
				}
			}
			else if(currentTokenForMatch.getTip().equals(TokenTipi.Nokta)||currentTokenForMatch.getTip().equals(TokenTipi.SatirBasi)){
				continue;
			}
			else{
					/*if(currentTokenForMatch.tokenMatchs(midfieldToken)){
						logger.info(" MATCHED"+currentTokenForMatch.getDeger());
						setTokenToElement(matchedCommand, currentTokenForMatch,midfieldToken);
					}else if(currentTokenForMatch.getDeger().equals(ReservedCobolKeywords.NOT) //IFElement için eklendi
							||currentTokenForMatch.getDeger().equals(ReservedCobolKeywords.OR) //IFElement için eklendi
							||currentTokenForMatch.getDeger().equals(ReservedCobolKeywords.AND) //IFElement için eklendi
							||currentTokenForMatch.getDeger().equals(ReservedCobolKeywords.EQUAL)){
						logger.info(" MATCHED"+currentTokenForMatch.getDeger());
						setTokenToElement(matchedCommand, currentTokenForMatch,midfieldToken);
					}*/
					logger.info(" MATCHED :"+currentTokenForMatch.getDeger());
					matchedCommand.setSourceCodeSentence(matchedCommand.getSourceCodeSentence(),currentTokenForMatch);
					setTokenToElement(matchedCommand, currentTokenForMatch,midfieldToken);
				}
		}
		return null;
	}



}
