package tr.com.vbt.patern;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.apache.log4j.Logger;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.natural.parser.conditions.AbstractConditionCommand;
import tr.com.vbt.java.util.Utility;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.TokenTipi;

public class PatternV2 extends AbstractPattern {

	final static Logger logger = Logger.getLogger(PatternV2.class);

	protected List<AbstractToken> patternTokenList = new ArrayList<AbstractToken>();

	protected List<AbstractConditionCommand> patternConditionList = new ArrayList<AbstractConditionCommand>();

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public PatternV2() {
		super();
	}

	public AbstractCommand getmatchedCommand(List<AbstractToken> tokenListesi, int offset,List<AbstractToken> subpaterList) {

		ListIterator<AbstractToken> tokenListIterator = tokenListesi.listIterator(offset);
		ListIterator<AbstractToken> abstractTokenInPatternIterator = patternTokenList.listIterator();
		AbstractToken currentTokenForMatch = null;
		AbstractCommand matchedCommand;
		matchedCommand = createElement();

		boolean iterateCurrentToken = true;

		boolean lastElementMatched = false;
		

		
		AbstractToken abstractTokenInPattern = null;
		ListIterator<AbstractToken> tokenListSubIterator = tokenListesi.listIterator(offset);
		ListIterator<AbstractToken> abstractTokenInPatternSubIterator = subpaterList.listIterator();
		AbstractToken currentTokenForSubMatch = null;
		AbstractToken abstractTokenInSubPattern = null;
		
		logger.info(" Submatch ");
		//CEPNİ
		// Anacommand ile birlikte search yapılacak subcommand gelir. İçteki döngüde
		// subpattern aranırken her eşleşmede iterator sıfırlanarak(?) sona gelene kadar
		// aratılır.
		// Böylelikle Move-Plus patternleri geldiğinde Move komutu içindeki bütün plus
		// paternleri kontrol edilmiş olur.
		// Bu işlem sıra ile her bir patern için uygulanır.
		while (tokenListIterator.hasNext()) {
			currentTokenForMatch = tokenListIterator.next();
			if (abstractTokenInPatternIterator.hasNext()) {
				abstractTokenInPattern = abstractTokenInPatternIterator.next();

				logger.info("AnaPaternSearch-->" + abstractTokenInPattern.getDeger().toString());
				
				if (abstractTokenInPattern.getDeger().equals("Pattern")) {
					abstractTokenInPatternSubIterator = subpaterList.listIterator();
					abstractTokenInSubPattern = abstractTokenInPatternSubIterator.next();

					logger.info("SubPaternSearch-->" + abstractTokenInPattern.getDeger().toString());

					while (tokenListSubIterator.hasNext()) {
						currentTokenForSubMatch = tokenListSubIterator.next();

						logger.info(" TOKEN  : " + currentTokenForSubMatch.getDeger());
						logger.info(" PATTERN: " + abstractTokenInSubPattern.getDeger());
						// PatternV2 özelkelime ile de başlayabilir kelime ile de
						if (currentTokenForSubMatch.getTip().equals(TokenTipi.Kelime) && abstractTokenInSubPattern.getDeger().equals("Pattern")) {
							logger.info(" eşleşme: " + currentTokenForSubMatch.getDeger() + "-" + abstractTokenInSubPattern.getDeger());
							if (abstractTokenInPatternSubIterator.hasNext())
								abstractTokenInSubPattern = abstractTokenInPatternSubIterator.next();

							continue;
						} else if (currentTokenForSubMatch.getDeger().toString().compareTo(abstractTokenInSubPattern.getDeger().toString()) == 0) {
							logger.info(" eşleşme patternV2 dışı: " + currentTokenForSubMatch.getDeger() + "-" + abstractTokenInSubPattern.getDeger());
							if (abstractTokenInPatternSubIterator.hasNext())
								abstractTokenInSubPattern = abstractTokenInPatternSubIterator.next();

							continue;
						} else {
							logger.info(" eşleşme sağlanamadı: " + currentTokenForSubMatch.getDeger() + "-" + abstractTokenInSubPattern.getDeger());
						}

					}
				}
			}
		}
			
		logger.info(" Submatch-End ");
		//CEPNİ

		while (abstractTokenInPatternIterator.hasNext()) {

			abstractTokenInPattern = abstractTokenInPatternIterator.next();

			if (!tokenListIterator.hasNext()) {

				while (true) {
					if (abstractTokenInPattern.isOptional() && abstractTokenInPatternIterator.hasNext()) { // Kontrole
																											// Devam Et
						abstractTokenInPattern = abstractTokenInPatternIterator.next();
					} else if (abstractTokenInPattern.isOptional() && !abstractTokenInPatternIterator.hasNext()) { // Patern
																													// Sonuna
																													// Gelinmisse
																													// ve
																													// Hala
																													// optionalsa
																													// match
																													// etmiştir
						return matchedCommand;
					} else if (!abstractTokenInPattern.isOptional() && abstractTokenInPatternIterator.hasNext()) { // Sona
																													// gelmeden
																													// mandatory
																													// buldu
																													// ise
																													// match
																													// etmez.
						return null;
					}
				}
			}

			if (iterateCurrentToken) {
				while (tokenListIterator.hasNext()) {
					currentTokenForMatch = tokenListIterator.next();
					if (!currentTokenForMatch.getTip().equals(TokenTipi.SatirBasi)
							&& !currentTokenForMatch.getTip().equals(TokenTipi.Nokta)) {
						break;
					}
					matchedCommand.increaseCommandsMatchPoint();
					logger.info(" MATCHED  SatirBasi Or Nokta POINT:" + matchedCommand.getCommandMatchPoint());
				}
			}

			if (!tokenListIterator.hasNext()) { // Sona Gelinmisse

				while (true) {
					if (abstractTokenInPattern.isOptional() && abstractTokenInPatternIterator.hasNext()) { 
						// Kontrole Devam Et
						abstractTokenInPattern = abstractTokenInPatternIterator.next();
					} else if (abstractTokenInPattern.isOptional() && !abstractTokenInPatternIterator.hasNext()) { 
						// Patern Sonuna Gelinmisse ve Hala optionalsa match etmiştir
						return matchedCommand;
					} else if (!abstractTokenInPattern.isOptional() && abstractTokenInPatternIterator.hasNext()) { 
						// Sona gelmeden mandatory buldu ise match etmez.
						return null;
					} else if (!abstractTokenInPattern.isOptional() && !abstractTokenInPatternIterator.hasNext()) { 
						// PAternde ve tokne Array de sona geldi ise ve optional değilse aşağıya geç ve kontrol et.
						break;
					}
				}
			}

			if (!Utility.DEBUG) {
				Utility.controlDebug(patternTokenList, currentTokenForMatch);
			}
			if (Utility.DEBUG) {
				logger.info("PATERN TOKEN KONTROL: Paterndeki Token:" + abstractTokenInPattern.getDeger() + " "
						+ abstractTokenInPattern.getTip() + "  Current Token: " + currentTokenForMatch.getDeger() + " "
						+ abstractTokenInPattern.getTip());
			}

			if (!currentTokenForMatch.tokenMatchs(abstractTokenInPattern)) {
				logger.info(" NOT MATCHED " + currentTokenForMatch.getDeger());
				Utility.DEBUG = false;
				lastElementMatched = false;
				if (abstractTokenInPattern.isOptional()) {
					iterateCurrentToken = false;
					continue;
				}
				return null;
			} else {
				logger.info(" MATCHED " + currentTokenForMatch.getDeger());
				matchedCommand.setSourceCodeSentence(matchedCommand.getSourceCodeSentence(), currentTokenForMatch);
				setTokenToElement(matchedCommand, currentTokenForMatch, abstractTokenInPattern);
				matchedCommand.increaseCommandsMatchPoint();
				logger.info("  POINT:" + matchedCommand.getCommandMatchPoint());
				iterateCurrentToken = true;
				lastElementMatched = true;
			}
		}
		/*
		 * if(!lastElementMatched){ //Son Token optional ise son token match olmadan da
		 * patern match edebilir. Bu durumda currentToken daki son iterasyondaki puan
		 * artisi geriye alinir. matchedCommand.decreaseCommandsMatchPoint();
		 * logger.info("DECREASE POINT:"+ matchedCommand.toString()); }
		 */
		logger.info("PATERN TOKEN KONTROL MATCH:" + matchedCommand.toString());
		Utility.DEBUG = false;
		return matchedCommand;
	}

	public void setTokenToElement(AbstractCommand matchedCommand, AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {

	}

	public AbstractCommand createElement() {
		return null;

	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}

	public void setSatirNumarasi(AbstractCommand matchedCommand, AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {
		if (abstractTokenInPattern.getSourceFieldName() != null
				&& (abstractTokenInPattern.getSourceFieldName().equalsIgnoreCase("FIRST_COMMAND")
						|| abstractTokenInPattern.getSourceFieldName().equalsIgnoreCase("levelNumber"))) {
			matchedCommand.setSatirNumarasi(currentTokenForMatch.getSatirNumarasi());
		}

	}

}
