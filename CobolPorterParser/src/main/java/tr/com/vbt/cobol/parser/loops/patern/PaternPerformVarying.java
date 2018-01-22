package tr.com.vbt.cobol.parser.loops.patern;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.apache.log4j.Logger;


import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.loops.ElementPerformVarying;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.patern.carriage_return.AbstractPatternFromXToYWithoutCarriageReturn;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.KelimeToken;
import tr.com.vbt.token.OzelKelimeToken;
import tr.com.vbt.token.SayiToken;
import tr.com.vbt.token.TokenTipi;

//PERFORM VARYING JJ FROM 1 BY 1 UNTIL JJ > 10000
//PArams:  variable, from,  by , until
/**
 * PERFORM  Uzunluk:0 Satir No:26 Tipi:OzelKelime
VARYING  Uzunluk:0 Satir No:26 Tipi:OzelKelime
JJ  Uzunluk:0 Satir No:26 Tipi:Kelime
FROM  Uzunluk:0 Satir No:26 Tipi:OzelKelime
1.0  Uzunluk:0 Satir No:26 Tipi:Sayi
BY  Uzunluk:0 Satir No:26 Tipi:OzelKelime
1.0  Uzunluk:0 Satir No:26 Tipi:Sayi
UNTIL  Uzunluk:0 Satir No:26 Tipi:OzelKelime
JJ  Uzunluk:0 Satir No:26 Tipi:Kelime
62  Uzunluk:1 Satir No:26 Tipi:Standart
 * @author 47159500
 *
 */
public class PaternPerformVarying extends AbstractPatternFromXToYWithoutCarriageReturn{

	final static Logger logger = Logger.getLogger(PaternPerformVarying.class);
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//AbstractPattern asp =new PaternAdd();

	}
	

	public PaternPerformVarying() {
		super();
		
		//PERFORM
		starterToken=new OzelKelimeToken("PERFORM_VARYING", 0, 0, 0);
		starterToken.setTekrarlayabilir("+");
		patternTokenList.add(starterToken);
		
		//variable  //JJ
		AbstractToken astSource=new KelimeToken<String>();
		astSource.setTekrarlayabilir("+");
		astSource.setSourceFieldName("variable");
		patternTokenList.add(astSource);
	
		
		//FROM
		AbstractToken astKeyword2=new OzelKelimeToken("FROM", 0, 0, 0);
		astKeyword2.setTekrarlayabilir("+");
		patternTokenList.add(astKeyword2);
		//1 
		AbstractToken astSource2=new SayiToken<Integer>();
		astSource2.setTekrarlayabilir("+");
		astSource2.setSourceFieldName("from");
		patternTokenList.add(astSource2);
		
		
		//BY
		AbstractToken astKeyword3=new OzelKelimeToken("BY", 0, 0, 0);
		astKeyword3.setTekrarlayabilir("+");
		patternTokenList.add(astKeyword3);
		//1 
		AbstractToken astSource3=new SayiToken<Integer>();
		astSource3.setTekrarlayabilir("+");
		astSource3.setSourceFieldName("by");
		patternTokenList.add(astSource3);
		
		//UNTIL JJ > 10000
		//UNTIL
		AbstractToken astKeyword4=new OzelKelimeToken("UNTIL", 0, 0, 0);
		astKeyword4.setTekrarlayabilir("+");
		patternTokenList.add(astKeyword4);
		
		//conditionList
		AbstractToken conList=new KelimeToken<String>();
		conList.setSourceFieldName("conditionList");
		patternTokenList.add(conList);
		
		//Ender
		enderToken=new OzelKelimeToken(ReservedCobolKeywords.THEN,0,0,0);
		patternTokenList.add(enderToken);
		
	}
	
	@Override
	public AbstractCommand createElement(){
		ElementPerformVarying elementDisplay = new ElementPerformVarying(ReservedCobolKeywords.PERFORM_VARYING,"PROCEDURE_DIVISION.PERFORM.*.VARYING");
		return elementDisplay;
	}


	@Override
	public void setTokenToElement(AbstractCommand matchedCommand,
			AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {
		
		ElementPerformVarying matchedCommandAdd=(ElementPerformVarying) matchedCommand;
		
		super.setSatirNumarasi(matchedCommand,currentTokenForMatch, abstractTokenInPattern);if(abstractTokenInPattern.getSourceFieldName()==null){
		
		}else if(abstractTokenInPattern.getSourceFieldName().equals("variable")){
			matchedCommandAdd.setVariable((String) currentTokenForMatch.getDeger());
			matchedCommandAdd.getParameters().put("variable", matchedCommandAdd.getVariable());
			
		}else if(abstractTokenInPattern.getSourceFieldName().equals("from")){
			matchedCommandAdd.setFrom((long) currentTokenForMatch.getDeger());
			matchedCommandAdd.getParameters().put("from", matchedCommandAdd.getFrom());
			
		}else if(abstractTokenInPattern.getSourceFieldName().equals("by")){
			matchedCommandAdd.setBy((long) currentTokenForMatch.getDeger());
			matchedCommandAdd.getParameters().put("by", matchedCommandAdd.getBy());
			
		}else if(abstractTokenInPattern.getSourceFieldName().equals("until")){
			matchedCommandAdd.setUntil( (long) currentTokenForMatch.getDeger());
			matchedCommandAdd.getParameters().put("until", matchedCommandAdd.getUntil());
		}else if(abstractTokenInPattern.getSourceFieldName().equals("conditionList")){
			
			String degerAsStr="";
			
			if(currentTokenForMatch.getDeger() instanceof Character){
				 degerAsStr=String.valueOf(currentTokenForMatch.getDeger());
			}else{
				 degerAsStr=(String) currentTokenForMatch.getDeger();
			}
				 
			matchedCommandAdd.getConditionList().add(degerAsStr);
			
			List<String> sourceList;
			
			if(matchedCommandAdd.getParameters().get("conditionList")!=null){
				sourceList=(List<String>) matchedCommandAdd.getParameters().get("conditionList");
			}else{
				sourceList=new ArrayList<String>();
			}
			
			sourceList.add(degerAsStr);
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
		
		int tokenInPatternIndex=1; //0 nolu olan starter;
		
		AbstractToken midfieldTokenX=patternTokenList.get(tokenInPatternIndex);
		
		while(tokenListIterator.hasNext()){
			
			currentTokenForMatch=tokenListIterator.next();
			matchedCommand.increaseCommandsMatchPoint();
			
			if((currentTokenForMatch.getTip().equals(TokenTipi.OzelKelime)
					&&!currentTokenForMatch.getDeger().equals(ReservedCobolKeywords.NOT) //IFElement için eklendi
					&&!currentTokenForMatch.getDeger().equals(ReservedCobolKeywords.OR) //IFElement için eklendi
					&&!currentTokenForMatch.getDeger().equals(ReservedCobolKeywords.AND) //IFElement için eklendi
					&&!currentTokenForMatch.getDeger().equals(ReservedCobolKeywords.EQUAL)
					&&!currentTokenForMatch.getDeger().equals(ReservedCobolKeywords.BY)
					&&!currentTokenForMatch.getDeger().equals(ReservedCobolKeywords.UNTIL)
					&&!currentTokenForMatch.getDeger().equals(ReservedCobolKeywords.FROM))){ //IFElement için eklendi
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
				    midfieldTokenX=patternTokenList.get(tokenInPatternIndex);
					logger.info(" MATCHED"+currentTokenForMatch.getDeger());
					matchedCommand.setSourceCodeSentence(matchedCommand.getSourceCodeSentence(),currentTokenForMatch);
					setTokenToElement(matchedCommand, currentTokenForMatch,midfieldTokenX);
					if(tokenInPatternIndex<7){ //conditionList PAterndeki 7 token. PaternToken Listesi Size=9 
												// 7. Elemandan sonra hep 7. elemanı kontrol et.
						tokenInPatternIndex++;
					}
				}
		}
		return null;
	}




	
	
	

	
	
	
	

}
