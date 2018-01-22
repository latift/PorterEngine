package tr.com.vbt.natural.parser.screen.patern;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.apache.log4j.Logger;


import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.natural.parser.screen.ElementWrite;
import tr.com.vbt.patern.carriage_return.AbstractPatternFromXToYWithoutCarriageReturn;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.KarakterToken;
import tr.com.vbt.token.KelimeToken;
import tr.com.vbt.token.OzelKelimeToken;
import tr.com.vbt.token.SayiToken;
import tr.com.vbt.token.TokenTipi;

/**
 *      WRITE  (3) NOTITLE 
*S**                #TARIH1 '-' #TARIH2 #T-RFN-UCRET (EM=Z,ZZZ,ZZ9.999999)                      
*S**  #P-SIRKET-MARJI(EM=Z,ZZZ,ZZ9.999999) #BIRIM-FIYAT(EM=Z,ZZZ,ZZ9.999999)                    
*S**                #ODM-BIRIM #P-FORMUL (IS=ON) #P-FKOD #P-RKOD              
 *     
 *
 */
public class PaternWriteWithPrintNumber extends AbstractPatternFromXToYWithoutCarriageReturn{

	final static Logger logger = Logger.getLogger(PaternWriteWithPrintNumber.class);

	protected AbstractToken printNumber;
	protected AbstractToken noTitle;
	
	

	public PaternWriteWithPrintNumber() {
		super();
		
		//WRITE
		this.starterToken=new OzelKelimeToken(ReservedNaturalKeywords.WRITE, 0, 0, 0);
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
		
		//dataToDisplay
		this.midfieldToken=new KelimeToken<String>();
		midfieldToken.setTekrarlayabilir("+");
		midfieldToken.setSourceFieldName("inputParameters");
		patternTokenList.add(midfieldToken);
		
		//END_INPUT
		this.enderToken=new OzelKelimeToken(ReservedNaturalKeywords.END_WRITE,0,0,0);
		patternTokenList.add(enderToken);
		
		
	}
	
	@Override
	public AbstractCommand createElement(){
		ElementWrite elementDisplay = new ElementWrite(ReservedNaturalKeywords.WRITE,"SCREEN.*.WRITE");
		return elementDisplay;
	}


	@Override
	public void setTokenToElement(AbstractCommand matchedCommand,
			AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {
		
		ElementWrite matchedCommandAdd=(ElementWrite) matchedCommand;
		super.setSatirNumarasi(matchedCommand,currentTokenForMatch, abstractTokenInPattern);
	   if(abstractTokenInPattern.getSourceFieldName()==null){
		
		}else if(abstractTokenInPattern.getSourceFieldName().equals("inputParameters")){
			
			matchedCommandAdd.getInputParameters().add(currentTokenForMatch);
			
			List<AbstractToken> sourceList;
			
			if(matchedCommandAdd.getParameters().get("inputParameters")!=null){
				
				sourceList=(List<AbstractToken>) matchedCommandAdd.getParameters().get("inputParameters");
				
			}else{
				
				sourceList=new ArrayList<AbstractToken>();
			}
			
			sourceList.add((AbstractToken) currentTokenForMatch);
			matchedCommandAdd.getParameters().put("inputParameters", sourceList);
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
		
		//WRITE
		if(!tokenListIterator.hasNext()){
			return null;
		}
		if(!currentTokenForMatch.tokenMatchs(starterToken)){
			return null;
		}
		setTokenToElement(matchedCommand, currentTokenForMatch,starterToken);
		logger.info(" MATCHED: "+currentTokenForMatch.getDeger());
		
		
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
			
			if((currentTokenForMatch.getTip().equals(TokenTipi.OzelKelime)
					&&!currentTokenForMatch.getDeger().equals(ReservedCobolKeywords.NOT) //IFElement için eklendi
					&&!currentTokenForMatch.getDeger().equals(ReservedCobolKeywords.OR) //IFElement için eklendi
					&&!currentTokenForMatch.getDeger().equals(ReservedCobolKeywords.AND) //IFElement için eklendi
					&&!currentTokenForMatch.getDeger().equals(ReservedCobolKeywords.EQUAL)
					&&!currentTokenForMatch.getDeger().equals(ReservedCobolKeywords.IS)
					&&!currentTokenForMatch.getDeger().equals(ReservedCobolKeywords.IS_NUMERIC)
					&&!currentTokenForMatch.getDeger().equals(ReservedCobolKeywords.NUMERIC))){ //IFElement için eklendi
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
					logger.info(" MATCHED"+currentTokenForMatch.getDeger());
					matchedCommand.setSourceCodeSentence(matchedCommand.getSourceCodeSentence(),currentTokenForMatch);
					setTokenToElement(matchedCommand, currentTokenForMatch,midfieldToken);
				}
		}
		return null;
	}




	
	
	

	
	
	
	

}
