package tr.com.vbt.natural.parser.database.patern;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.apache.log4j.Logger;


import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.natural.parser.database.ElementReadBy;
import tr.com.vbt.patern.AbstractPattern;
import tr.com.vbt.patern.carriage_return.AbstractPatternFromXToYWithoutCarriageReturn;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.GenelTipToken;
import tr.com.vbt.token.KarakterToken;
import tr.com.vbt.token.KelimeToken;
import tr.com.vbt.token.OzelKelimeToken;
import tr.com.vbt.token.TokenTipi;

/**
 * 
*S**  READ ISTASYON BY S-ISTASYON-KODU=#ISTASYON-KODU-BAS
*S**      THRU #ISTASYON-KODU-BIT THEN
 *  

 */
public class PaternReadByThru extends AbstractPatternFromXToYWithoutCarriageReturn {

	final static Logger logger = Logger.getLogger(PaternReadByThru.class);
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// AbstractPattern asp =new PaternAdd();

	}
	
	AbstractToken astViewName,astKeywordWith,astKeywordThru,astThru;

	public PaternReadByThru() {
		super();

		// FIND
		starterToken = new OzelKelimeToken("READ", 0, 0, 0);
		starterToken.setTekrarlayabilir("+");
		starterToken.setSourceFieldName("FIRST_COMMAND");
		patternTokenList.add(starterToken);

		// LIMAN
		astViewName = new KelimeToken<String>();
		astViewName.setSourceFieldName("viewName");
		patternTokenList.add(astViewName);

		// WITH
		astKeywordWith = new OzelKelimeToken("BY", 0, 0, 0);
		patternTokenList.add(astKeywordWith);

		//conditionList
		midfieldToken=new GenelTipToken();
		midfieldToken.setTekrarlayabilir("+");
		midfieldToken.setSourceFieldName("conditionList");
		patternTokenList.add(midfieldToken);
		
		// THRU
		astKeywordThru = new OzelKelimeToken("THRU", 0, 0, 0);
		patternTokenList.add(astKeywordThru);
		
		// ISTASYON-KODU-BIT
		astThru = new KelimeToken<String>();
		astThru.setSourceFieldName("astThru");
		patternTokenList.add(astThru);
		
		//Ender
		enderToken=new OzelKelimeToken(ReservedCobolKeywords.THEN,0,0,0);
		patternTokenList.add(enderToken);


	}

	@Override
	public AbstractCommand createElement() {
		ElementReadBy createdElement = new ElementReadBy(
				ReservedNaturalKeywords.READ_BY, "DATABASE.*.READ_BY");
		return createdElement;
	}

	@Override
	public void setTokenToElement(AbstractCommand matchedCommand,
			AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {

		ElementReadBy matchedCommandAdd = (ElementReadBy) matchedCommand;

		super.setSatirNumarasi(matchedCommand,currentTokenForMatch, abstractTokenInPattern);
		if(abstractTokenInPattern.getSourceFieldName()==null){
			
		}else if (abstractTokenInPattern.getSourceFieldName().equals("viewName")) {
			matchedCommandAdd.setViewName(currentTokenForMatch);
			matchedCommandAdd.getParameters().put("viewName",matchedCommandAdd.getViewName());
			
		}else if(abstractTokenInPattern.getSourceFieldName().equals("conditionList")){
			
			matchedCommandAdd.getConditionList().add(currentTokenForMatch);
			
			List<AbstractToken> sourceList;
			
			if(matchedCommandAdd.getParameters().get("conditionList")!=null){
				sourceList=(List<AbstractToken>) matchedCommandAdd.getParameters().get("conditionList");
			}else{
				sourceList=new ArrayList<AbstractToken>();
			}
			
			sourceList.add(currentTokenForMatch);
			matchedCommandAdd.getParameters().put("conditionList", sourceList);
			
		}else if (abstractTokenInPattern.getSourceFieldName().equals("astThru")) {
			matchedCommandAdd.setThru(currentTokenForMatch);
			matchedCommandAdd.getParameters().put("astThru",matchedCommandAdd.getThru());
			
		}
	}
	
	
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
		
		if(!currentTokenForMatch.tokenMatchs(starterToken)){
			return null;
		}
		setTokenToElement(matchedCommand, currentTokenForMatch,starterToken);
		logger.info(" MATCHED: "+currentTokenForMatch.getDeger());
		
		while(tokenListIterator.hasNext())	{ //Satir Başı bu paternlerde önemli ancak bitişte önemli başlangıçta bunu atlıyoruz.
			currentTokenForMatch=tokenListIterator.next();
			matchedCommand.increaseCommandsMatchPoint();
			if(!currentTokenForMatch.getTip().equals(TokenTipi.SatirBasi)){
				break;
			}
		}
		
		
		//VIEW Name
		if(!currentTokenForMatch.tokenMatchs(astViewName)){
			return null;
		}
		setTokenToElement(matchedCommand, currentTokenForMatch,astViewName);
		
		while(tokenListIterator.hasNext())	{ //Satir Başı bu paternlerde önemli ancak bitişte önemli başlangıçta bunu atlıyoruz.
			currentTokenForMatch=tokenListIterator.next();
			matchedCommand.increaseCommandsMatchPoint();
			if(!currentTokenForMatch.getTip().equals(TokenTipi.SatirBasi)){
				break;
			}
		}
		
		//WITH
		if(!currentTokenForMatch.tokenMatchs(astKeywordWith)){
			return null;
		}
		
		
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
					&&!currentTokenForMatch.getDeger().equals(ReservedNaturalKeywords.STARTING_FROM)
					&&!currentTokenForMatch.getDeger().equals(ReservedNaturalKeywords.ENDING_AT)
					&&!currentTokenForMatch.getDeger().equals(ReservedNaturalKeywords.EQ)
					&&!currentTokenForMatch.getDeger().equals(ReservedNaturalKeywords.NE)
					&&!currentTokenForMatch.getDeger().equals(ReservedCobolKeywords.NUMERIC)){ //IFElement için eklendi
				if(currentTokenForMatch.tokenMatchs(astKeywordThru)){
					logger.info(" MATCHED: "+currentTokenForMatch.getDeger());
					break;
				}else{
					return null;
				}
			}
			else if(currentTokenForMatch.getTip().equals(TokenTipi.Nokta)||currentTokenForMatch.getTip().equals(TokenTipi.SatirBasi)){
				continue;
			}
			else if(currentTokenForMatch.isOzelKelime(ReservedNaturalKeywords.THRU)){
				break;
			}
			else{
					logger.info(" MATCHED"+currentTokenForMatch.getDeger());
					matchedCommand.setSourceCodeSentence(matchedCommand.getSourceCodeSentence(),currentTokenForMatch);
					setTokenToElement(matchedCommand, currentTokenForMatch,midfieldToken);
				}
		}
		
		//ISTASYON-KODU-BIT
		currentTokenForMatch=tokenListIterator.next();
		matchedCommand.increaseCommandsMatchPoint();
		if(!currentTokenForMatch.tokenMatchs(astThru)){
			return null;
		}
		logger.info(" MATCHED"+currentTokenForMatch.getDeger());
		
		//THEN
		currentTokenForMatch=tokenListIterator.next();
		matchedCommand.increaseCommandsMatchPoint();
		if(!currentTokenForMatch.tokenMatchs(enderToken)){
			return null;
		}
		logger.info(" MATCHED"+currentTokenForMatch.getDeger());
		
		return matchedCommand;
	}

}
