package tr.com.vbt.natural.parser.database.patern;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.apache.log4j.Logger;


import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.natural.parser.database.ElementReadBy;
import tr.com.vbt.patern.carriage_return.AbstractPatternFromXToYWithoutCarriageReturn;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.KarakterToken;
import tr.com.vbt.token.KelimeToken;
import tr.com.vbt.token.OzelKelimeToken;
import tr.com.vbt.token.SayiToken;
import tr.com.vbt.token.TokenTipi;

/**
 * 
 *  *S**READ UHM-ACILIS1 BY UHT-SCTARIH EQ #UHT-SCTRH-N
 *  
 *  
 * READ  Uzunluk:0 Satir No:2 Tipi:OzelKelime
UHM-ACILIS1  Uzunluk:0 Satir No:2 Tipi:Kelime
BY  Uzunluk:0 Satir No:2 Tipi:OzelKelime
UHT-SCTARIH  Uzunluk:0 Satir No:2 Tipi:Kelime
EQ  Uzunluk:0 Satir No:2 Tipi:OzelKelime
UHT-SCTRH-N  Uzunluk:0 Satir No:2 Tipi:Kelime

private String viewName;//UHM-ACILIS1
	
	private String columnName; //UHT-SCTARIH
	
	private String columnValue; //UHT-SCTRH-N
 */
public class PaternReadWith extends AbstractPatternFromXToYWithoutCarriageReturn {

	final static Logger logger = Logger.getLogger(PaternReadWith.class);
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// AbstractPattern asp =new PaternAdd();

	}
	
	AbstractToken astViewName,astKeywordWith, astParOpen, astRecordNum, astParClose;

	public PaternReadWith() {
		super();

		// FIND
		starterToken = new OzelKelimeToken("READ", 0, 0, 0);
		starterToken.setTekrarlayabilir("+");
		starterToken.setSourceFieldName("FIRST_COMMAND");
		patternTokenList.add(starterToken);

		astParOpen=new KarakterToken('(', 0,0,0);
		astParOpen.setOptional(true);
		patternTokenList.add(astParOpen);
		
		astRecordNum=new SayiToken<>();
		astRecordNum.setOptional(true);
		patternTokenList.add(astRecordNum);
		
		astParClose=new KarakterToken(')', 0,0,0);
		astParClose.setOptional(true);
		patternTokenList.add(astParClose);
		
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
		
		
		
		// (
		currentTokenForMatch=tokenListIterator.next();
		matchedCommand.increaseCommandsMatchPoint();
		if(currentTokenForMatch.tokenMatchs(astParOpen)){
			
			// 1
			currentTokenForMatch=tokenListIterator.next();
			matchedCommand.increaseCommandsMatchPoint();
			if(!currentTokenForMatch.tokenMatchs(astRecordNum)){
				return null;
			}
			
			// )
			currentTokenForMatch=tokenListIterator.next();
			matchedCommand.increaseCommandsMatchPoint();
			if(!currentTokenForMatch.tokenMatchs(astParClose)){
				return null;
			}
			
			currentTokenForMatch=tokenListIterator.next();
			matchedCommand.increaseCommandsMatchPoint();
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
					&&!currentTokenForMatch.getDeger().equals(ReservedNaturalKeywords.EQ)
					&&!currentTokenForMatch.getDeger().equals(ReservedNaturalKeywords.NE)
					&&!currentTokenForMatch.getDeger().equals(ReservedNaturalKeywords.GT)
					&&!currentTokenForMatch.getDeger().equals(ReservedNaturalKeywords.LT)
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
