package tr.com.vbt.natural.parser.basicverbs.patern;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.apache.log4j.Logger;


import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.natural.parser.basicverbs.ElementFormat;
import tr.com.vbt.patern.AbstractPattern;
import tr.com.vbt.patern.carriage_return.AbstractPatternFromXToYWithoutCarriageReturn;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.GenelTipToken;
import tr.com.vbt.token.KarakterToken;
import tr.com.vbt.token.OzelKelimeToken;
import tr.com.vbt.token.SayiToken;
import tr.com.vbt.token.TokenTipi;

/**
 *     FORMAT (4)   LS=132 PS=21 // (4) optional
 *
 **S**FORMAT(2)  PS=66 LS=132
 */
public class PaternFormatWithNumber extends AbstractPatternFromXToYWithoutCarriageReturn{


	final static Logger logger = Logger.getLogger(PaternFormatWithNumber.class);
	
	protected AbstractToken printNumber;
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//AbstractPattern asp =new PaternAdd();

	}
	

	public PaternFormatWithNumber() {
		super();
		
		//FORMAT
		this.starterToken=new OzelKelimeToken("FORMAT", 0, 0, 0);
		starterToken.setSourceFieldName("FIRST_COMMAND");
		starterToken.setTekrarlayabilir("+");
		patternTokenList.add(starterToken);
		
		AbstractToken astSource5=new KarakterToken('(', 0,0,0);
		patternTokenList.add(astSource5);
		
		//	
		printNumber=new SayiToken<Integer>();
		printNumber.setSourceFieldName("printNumber");
		patternTokenList.add(printNumber);
		
		//) 
		AbstractToken astSource7=new KarakterToken(')', 0,0,0);
		patternTokenList.add(astSource7);
		
		//formatString
		this.midfieldToken=new GenelTipToken();
		midfieldToken.setTekrarlayabilir("+");
		midfieldToken.setSourceFieldName("parametersOfFormat");
		patternTokenList.add(midfieldToken);
		
		//Ender
		this.enderToken=new OzelKelimeToken(ReservedNaturalKeywords.END_FORMAT,0,0,0);
		patternTokenList.add(enderToken);
						
		
	}
	
	@Override
	public AbstractCommand createElement(){
		ElementFormat elementDisplay = new ElementFormat(ReservedNaturalKeywords.FORMAT,"GENERAL.*.FORMAT");
		return elementDisplay;
	}


	@Override
	public void setTokenToElement(AbstractCommand matchedCommand,
			AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {
		
		ElementFormat matchedCommandAdd=(ElementFormat) matchedCommand;
		
		super.setSatirNumarasi(matchedCommand,currentTokenForMatch, abstractTokenInPattern);if(abstractTokenInPattern.getSourceFieldName()==null){
		
		}else if(abstractTokenInPattern.getSourceFieldName().equals("parametersOfFormat")){
			if(currentTokenForMatch.getTip().equals(TokenTipi.Karakter)&&currentTokenForMatch.getDeger().equals('#')){
				return;
			}
			
			matchedCommandAdd.getParametersOfFormat().add(currentTokenForMatch);
			
			List<AbstractToken> sourceList;
			if(matchedCommandAdd.getParameters().get("parametersOfFormat")!=null){
				sourceList=(List<AbstractToken>) matchedCommandAdd.getParameters().get("parametersOfFormat");
			}else{
				sourceList=new ArrayList<AbstractToken>();
			}
			sourceList.add(currentTokenForMatch);
			matchedCommandAdd.getParameters().put("parametersOfFormat", sourceList);
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
		
		/*AbstractToken starterToken=patternTokenList.get(0);
		
		AbstractToken midfieldToken=patternTokenList.get(1);
		
		AbstractToken enderToken=patternTokenList.get(2);*/
		
		if(!currentTokenForMatch.tokenMatchs(starterToken)){
			return null;
		}
		setTokenToElement(matchedCommand, currentTokenForMatch,starterToken);
		logger.info(" MATCHED: "+currentTokenForMatch.getDeger());
		logger.info("...");
		
		//(
		if(!tokenListIterator.hasNext()){
			return null;
		}
		currentTokenForMatch=tokenListIterator.next(); //( atlamak için
		logger.info(" MATCHED: "+currentTokenForMatch.getDeger());
		matchedCommand.increaseCommandsMatchPoint();
		
		
		//8 /printnumber
		if(!tokenListIterator.hasNext()){
			return null;
		}
		currentTokenForMatch=tokenListIterator.next();
		if(!currentTokenForMatch.tokenMatchs(printNumber)){
			return null;
		}
		logger.info(" MATCHED: "+currentTokenForMatch.getDeger());
		matchedCommand.increaseCommandsMatchPoint();
			
		
		//)
		if(!tokenListIterator.hasNext()){
			return null;
		}
		currentTokenForMatch=tokenListIterator.next();
		logger.info(" MATCHED: "+currentTokenForMatch.getDeger());
		matchedCommand.increaseCommandsMatchPoint();
		
		
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
					&&!currentTokenForMatch.getDeger().equals(ReservedNaturalKeywords.GE)
					&&!currentTokenForMatch.getDeger().equals(ReservedNaturalKeywords.GT)
					&&!currentTokenForMatch.getDeger().equals(ReservedNaturalKeywords.LE)
					&&!currentTokenForMatch.getDeger().equals(ReservedNaturalKeywords.LT)
					&&!currentTokenForMatch.getDeger().equals(ReservedNaturalKeywords.THRU)
					&&!currentTokenForMatch.getDeger().equals(ReservedNaturalKeywords.INTO)
					&&!currentTokenForMatch.getDeger().equals(ReservedNaturalKeywords.MAX)
					&&!currentTokenForMatch.getDeger().equals(ReservedNaturalKeywords.COUNT)
					&&!currentTokenForMatch.getDeger().equals(ReservedNaturalKeywords.MIN)
					&&!currentTokenForMatch.getDeger().equals(ReservedNaturalKeywords.DISTINCT)
					&&!currentTokenForMatch.getDeger().equals(ReservedNaturalKeywords.FROM)
					&&!currentTokenForMatch.getDeger().equals(ReservedNaturalKeywords.SORTED_BY)
					&&!currentTokenForMatch.getDeger().equals(ReservedNaturalKeywords.OFF)
					&&!currentTokenForMatch.getDeger().equals(ReservedNaturalKeywords.ON)
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
