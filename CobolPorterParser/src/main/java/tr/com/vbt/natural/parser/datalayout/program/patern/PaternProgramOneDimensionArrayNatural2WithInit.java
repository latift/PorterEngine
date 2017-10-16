package tr.com.vbt.natural.parser.datalayout.program.patern;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.java.util.Utility;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.natural.parser.datalayout.program.ElementProgramOneDimensionArrayNatural;
import tr.com.vbt.patern.AbstractDataTypePattern;
import tr.com.vbt.patern.AbstractPattern;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.GenelTipToken;
import tr.com.vbt.token.KarakterToken;
import tr.com.vbt.token.KelimeToken;
import tr.com.vbt.token.OzelKelimeToken;
import tr.com.vbt.token.SayiToken;
import tr.com.vbt.token.TokenTipi;

 /**S**  
  		1 #DIZI-KLIM(A3/500)	Local Normal Dizi Tanımı
 **/
public class PaternProgramOneDimensionArrayNatural2WithInit extends AbstractDataTypePattern{

	final static Logger logger = LoggerFactory.getLogger(PaternProgramOneDimensionArrayNatural2WithInit.class);
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		AbstractPattern asp =new PaternProgramOneDimensionArrayNatural2WithInit();

	}

	AbstractToken astLevelNumber,astDataName,astParantezAc,astDataType,astSlash,astArrayStart,astDoubleDot,astArrayLength,astCloseParantez,astInitKeyword,astConstKeyword,astKoseliParantezAc,astInitValue,astKoseliParantezKapa;

	public PaternProgramOneDimensionArrayNatural2WithInit() {
		super();
		
		//01
		astLevelNumber=new SayiToken<Integer>();
		astLevelNumber.setTekrarlayabilir("+");
		astLevelNumber.setSourceFieldName("levelNumber");
		patternTokenList.add(astLevelNumber);
		
		//#
		/*AbstractToken astSource1=new KarakterToken('#', 0,0,0);
		patternTokenList.add(astSource1);*/
				
		//CLIENT-ID
		astDataName=new KelimeToken<String>();
		astDataName.setSourceFieldName("dataName");
		astDataName.setLocalVariable(true);
		patternTokenList.add(astDataName);
		
		///( Mandatory
		astParantezAc=new KarakterToken('(', 0,0,0);
		patternTokenList.add(astParantezAc);
		
		//	*	A3
	    astDataType=new KelimeToken<Integer>();
		astDataType.setSourceFieldName("dataType");
		patternTokenList.add(astDataType);
		
		//// Mandatory
		astSlash=new KarakterToken('/', 0,0,0);
		patternTokenList.add(astSlash);
		
		//500
		astArrayLength=new SayiToken<Integer>();
		astArrayLength.setSourceFieldName("arrayLength");
		patternTokenList.add(astArrayLength);
		
		///) Mandatory
		astCloseParantez=new KarakterToken(')', 0,0,0);
		patternTokenList.add(astCloseParantez);
		
		//INIT
		astInitKeyword=new OzelKelimeToken("INIT", 0, 0, 0);
		astInitKeyword.setOptional(true);
		patternTokenList.add(astInitKeyword);
		
		//CONST
		astConstKeyword=new OzelKelimeToken("CONST", 0, 0, 0);
		astConstKeyword.setOptional(true);
		patternTokenList.add(astConstKeyword);
		
		//<
		astKoseliParantezAc=new KarakterToken('<', 0,0,0);
		patternTokenList.add(astKoseliParantezAc);
		
		//Sayi
		astInitValue=new KelimeToken();
		astInitValue.setSourceFieldName("initialValue");
		astInitValue.setTekrarlayabilir("*");
		astInitValue.setOptional(true);
		patternTokenList.add(astInitValue);
		
		//>
		astKoseliParantezKapa=new KarakterToken('>', 0,0,0);
		patternTokenList.add(astKoseliParantezKapa);
		
		
	}

	@Override
	public void setTokenToElement(AbstractCommand matchedCommand,
			AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {
		
		ElementProgramOneDimensionArrayNatural matchedCommandAdd=(ElementProgramOneDimensionArrayNatural) matchedCommand;
		
		super.setSatirNumarasi(matchedCommand,currentTokenForMatch, abstractTokenInPattern);if(abstractTokenInPattern.getSourceFieldName()==null){
		
		}else if(abstractTokenInPattern.getSourceFieldName().equals("levelNumber")){
			matchedCommandAdd.setLevelNumber(((Long)currentTokenForMatch.getDeger()));
			matchedCommandAdd.getParameters().put("levelNumber", matchedCommandAdd.getLevelNumber());
			matchedCommandAdd.setDataType("A");
			matchedCommandAdd.getParameters().put("type","String");
		}
		
		else if(abstractTokenInPattern.getSourceFieldName().equals("dataName")){
			matchedCommandAdd.setDataName((String) currentTokenForMatch.getDeger());
			matchedCommandAdd.getParameters().put("dataName", matchedCommandAdd.getDataName());
		}
		
		else if(abstractTokenInPattern.getSourceFieldName().equals("dataType")){
			String deger;
			if(currentTokenForMatch.getColumnNameToken()!=null){
				deger=currentTokenForMatch.getColumnNameToken().getDeger().toString();
			}else{
				deger=currentTokenForMatch.getDeger().toString();
			}
			matchedCommandAdd.setDataType((String) deger.substring(0, 1));
			matchedCommandAdd.getParameters().put("dataType", matchedCommandAdd.getDataType());
			if(deger.length()>1){
				try {
					matchedCommandAdd.setLength(Long.parseLong(deger.substring(1)));
					matchedCommandAdd.getParameters().put("length", matchedCommandAdd.getLength());
				} catch (NumberFormatException e) {
					logger.error(e.getMessage(),e);
				}
			}
		}
		
		else if(abstractTokenInPattern.getSourceFieldName().equals("arrayLength")){
			if(currentTokenForMatch.getDeger() instanceof Double){
			matchedCommandAdd.setArrayLength(((Double)currentTokenForMatch.getDeger()).longValue());
			}else {
				matchedCommandAdd.setArrayLength((long) currentTokenForMatch.getDeger());
			}
			matchedCommandAdd.getParameters().put("arrayLength", matchedCommandAdd.getArrayLength());
		}else if(abstractTokenInPattern.getSourceFieldName().equals("initialValue")){
			
			matchedCommandAdd.getInitValues().add(currentTokenForMatch);
			
			List<AbstractToken> sourceList;
			if(matchedCommandAdd.getParameters().get("initialValue")!=null){
				sourceList=(List<AbstractToken>) matchedCommandAdd.getParameters().get("initialValue");
			}else{
				sourceList=new ArrayList<AbstractToken>();
			}
			String deger = null;
			sourceList.add(currentTokenForMatch);
			matchedCommandAdd.getParameters().put("initialValue", sourceList);	
		}
	}

	@Override
	public AbstractCommand createElement() {
		ElementProgramOneDimensionArrayNatural createdElement = new ElementProgramOneDimensionArrayNatural(ReservedNaturalKeywords.PROGRAM_ARRAY_DATA_TYPE, "GENERAL.PROGRAM_ARRAY_DATA_TYPE");
		return createdElement;
		
	}

	/** Sadece X TO Y yi destekler.
	 * Önek: * Comment1, Comment2, Comment3 *
	 */
public AbstractCommand getmatchedCommand(List<AbstractToken> tokenListesi, int offset){
		
		ListIterator<AbstractToken> tokenListIterator=tokenListesi.listIterator(offset);
		
		ListIterator<AbstractToken> abstractTokenInPatternIterator=patternTokenList.listIterator();
		
		AbstractToken currentTokenForMatch = null;
		
		AbstractCommand matchedCommand;
		
		matchedCommand=createElement();
		
		AbstractToken abstractTokenInPattern = null;
		
		boolean iterateCurrentToken=true;
		
		boolean lastElementMatched=false;
		
		boolean tekrarliObject=false;
		
		while(abstractTokenInPatternIterator.hasNext()) {
		
			if(!tekrarliObject){
				abstractTokenInPattern = abstractTokenInPatternIterator.next();
			}
			
			if(!tokenListIterator.hasNext()){
				
				while(true){
					if(abstractTokenInPattern.isOptional()&&abstractTokenInPatternIterator.hasNext()){ //Kontrole Devam Et
						abstractTokenInPattern = abstractTokenInPatternIterator.next();
					}else if(abstractTokenInPattern.isOptional()&&!abstractTokenInPatternIterator.hasNext()){ //Patern Sonuna Gelinmisse  ve Hala optionalsa match etmiştir
						return matchedCommand;
					}else if(!abstractTokenInPattern.isOptional()&&abstractTokenInPatternIterator.hasNext()){ //Sona gelmeden mandatory buldu ise match etmez.
						return null;
					}
				}
			}
			
			if(iterateCurrentToken){
				while(tokenListIterator.hasNext())	{
					currentTokenForMatch=tokenListIterator.next();
					if(	!currentTokenForMatch.getTip().equals(TokenTipi.SatirBasi) && !currentTokenForMatch.getTip().equals(TokenTipi.Nokta)	){
						break;
					}
					matchedCommand.increaseCommandsMatchPoint();
					logger.info(" MATCHED  SatirBAsi Or Nokta POINT:"+matchedCommand.getCommandMatchPoint());
				}
			}
			
			if(!tokenListIterator.hasNext()){  //Sona Gelinmisse
				
				while(true){
					if(abstractTokenInPattern.isOptional()&&abstractTokenInPatternIterator.hasNext()){ //Kontrole Devam Et
						abstractTokenInPattern = abstractTokenInPatternIterator.next();
					}else if(abstractTokenInPattern.isOptional()&&!abstractTokenInPatternIterator.hasNext()){ //Patern Sonuna Gelinmisse  ve Hala optionalsa match etmiştir
						return matchedCommand;
					}else if(!abstractTokenInPattern.isOptional()&&abstractTokenInPatternIterator.hasNext()){ //Sona gelmeden mandatory buldu ise match etmez.
						return null;
					}else if(!abstractTokenInPattern.isOptional()&&!abstractTokenInPatternIterator.hasNext()){ //PAtern de ve tokne Array de sona geldi ise ve  optional değilse aşağıya geç ve kontrol et.
						break;
					}
				}
			}
			
			if(!Utility.DEBUG){
				Utility.controlDebug(patternTokenList,currentTokenForMatch);
			}
			if(Utility.DEBUG){
				logger.info("PATERN TOKEN KONTROL: Paterndeki Token:"+abstractTokenInPattern.getDeger()+" "+ abstractTokenInPattern.getTip()+"  Current Token: "+currentTokenForMatch.getDeger() + " "+abstractTokenInPattern.getTip());
			}
			if(currentTokenForMatch.isKarakter(',')){
				matchedCommand.increaseCommandsMatchPoint();
				continue;
			}
			if(!currentTokenForMatch.tokenMatchs(abstractTokenInPattern)){//&&Configuration.pojosAreDefinedInCode)){
					logger.info(" NOT MATCHED"+currentTokenForMatch.getDeger());
					Utility.DEBUG=false;
					lastElementMatched=false;
					tekrarliObject=false;
					if(abstractTokenInPattern.isOptional()){
						iterateCurrentToken=false;
						continue;
					}
					return null;
			}
			else{
					logger.info(" MATCHED"+currentTokenForMatch.getDeger());
					matchedCommand.setSourceCodeSentence(matchedCommand.getSourceCodeSentence(),currentTokenForMatch);
					setTokenToElement(matchedCommand, currentTokenForMatch,abstractTokenInPattern);
					matchedCommand.increaseCommandsMatchPoint();
					logger.info("  POINT:"+matchedCommand.getCommandMatchPoint());
					if(abstractTokenInPattern.getTekrarlayabilir()!=null &&abstractTokenInPattern.getTekrarlayabilir().equals("*")){
						tekrarliObject=true;
					}else{
						tekrarliObject=false;
					}
					iterateCurrentToken=true;
					lastElementMatched=true;
			}
		}
		/*if(!lastElementMatched){ //Son Token optional ise son token match olmadan da patern match edebilir. Bu durumda currentToken daki son iterasyondaki puan artisi geriye alinir. 
			matchedCommand.decreaseCommandsMatchPoint();
			logger.info("DECREASE POINT:"+ matchedCommand.toString());
		}*/
		logger.info("PATERN TOKEN KONTROL MATCH:"+ matchedCommand.toString());
		Utility.DEBUG=false;
		return matchedCommand;
	}
	

}
