package tr.com.vbt.natural.parser.database.patern;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.natural.parser.database.ElementFindWith;
import tr.com.vbt.patern.carriage_return.AbstractPatternFromXToYWithoutCarriageReturn;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.GenelTipToken;
import tr.com.vbt.token.KarakterToken;
import tr.com.vbt.token.KelimeToken;
import tr.com.vbt.token.OzelKelimeToken;
import tr.com.vbt.token.TokenTipi;

/**
*  
	*S**      FIND PERF60 WITH KEYNO60=UNITE38 THRU SDES
*S**           MOVE EDITED ENDIK38(EM=9) TO ENDIK60
*S**           UPDATE(1650)
*S**      LOOP  
	
	private String viewName; //Liman
	
	private String searchKeyField;
	
	private String searchKeyValue;
 */
public class PaternFindWithThru extends AbstractPatternFromXToYWithoutCarriageReturn {

	final static Logger logger = LoggerFactory.getLogger(PaternFindWithThru.class);

	AbstractToken astViewName,astKeywordWith,astKeywordThru,conditionOuther;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// AbstractPattern asp =new PaternAdd();

	}

	public PaternFindWithThru() {
		super();

		// FIND
		starterToken = new OzelKelimeToken("FIND", 0, 0, 0);
		starterToken.setTekrarlayabilir("+");
		starterToken.setSourceFieldName("FIRST_COMMAND");
		patternTokenList.add(starterToken);

		// LIMAN
		astViewName = new KelimeToken<String>();
		astViewName.setSourceFieldName("viewName");
		patternTokenList.add(astViewName);

		// WITH
		astKeywordWith = new OzelKelimeToken("WITH", 0, 0, 0);
		patternTokenList.add(astKeywordWith);

		//conditionList
		midfieldToken=new KelimeToken<String>();
		midfieldToken.setTekrarlayabilir("+");
		midfieldToken.setSourceFieldName("conditionList");
		patternTokenList.add(midfieldToken);
		
		astKeywordThru = new OzelKelimeToken("THRU", 0, 0, 0);
		patternTokenList.add(astKeywordThru);

		
		conditionOuther = new KelimeToken<String>();
		conditionOuther.setSourceFieldName("conditionOther");
		patternTokenList.add(conditionOuther);
		
	
		
		//Ender
		enderToken=new OzelKelimeToken(ReservedCobolKeywords.THEN,0,0,0);
		patternTokenList.add(enderToken);
		

	}

	@Override
	public AbstractCommand createElement() {
		ElementFindWith createdElement = new ElementFindWith(
				ReservedNaturalKeywords.FIND_WITH, "DATABASE.*.FIND_WITH");
		return createdElement;
	}

	@Override
	public void setTokenToElement(AbstractCommand matchedCommand,
			AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {

		ElementFindWith matchedCommandAdd = (ElementFindWith) matchedCommand;

		super.setSatirNumarasi(matchedCommand,currentTokenForMatch, abstractTokenInPattern);if(abstractTokenInPattern.getSourceFieldName()==null){
			
		}else if (abstractTokenInPattern.getSourceFieldName().equals("viewName")) {
			matchedCommandAdd.setViewName(currentTokenForMatch);
			matchedCommandAdd.getParameters().put("viewName",matchedCommandAdd.getViewName());
			
		}else if(abstractTokenInPattern.getSourceFieldName().equals("conditionList")){
			
			matchedCommandAdd.getConditionList().add(currentTokenForMatch);
			
			List<AbstractToken> sourceList;
			
			if(matchedCommandAdd.getParameters().get("conditionList")!=null &&  matchedCommandAdd.getParameters().get("conditionOther")!=null){
				sourceList=(List<AbstractToken>) matchedCommandAdd.getParameters().get("conditionList");
			}else{
				sourceList=new ArrayList<AbstractToken>();
			}
			
			sourceList.add(currentTokenForMatch);
			matchedCommandAdd.getParameters().put("conditionList", sourceList);
			
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
					&&!currentTokenForMatch.getDeger().equals(ReservedNaturalKeywords.EQ)
					&&!currentTokenForMatch.getDeger().equals(ReservedNaturalKeywords.NE)
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
					logger.info(" MATCHED"+currentTokenForMatch.getDeger());
					matchedCommand.setSourceCodeSentence(matchedCommand.getSourceCodeSentence(),currentTokenForMatch);
					setTokenToElement(matchedCommand, currentTokenForMatch,midfieldToken);
				}
		}
		return null;
	}

}
