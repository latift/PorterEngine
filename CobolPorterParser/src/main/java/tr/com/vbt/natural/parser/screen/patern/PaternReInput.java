package tr.com.vbt.natural.parser.screen.patern;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.natural.parser.screen.ElementReInput;
import tr.com.vbt.patern.AbstractPattern;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.GenelTipToken;
import tr.com.vbt.token.OzelKelimeToken;
import tr.com.vbt.token.TokenTipi;

/**
 *    REINPUT 'No employees meet your criteria.' MARK *WULKEKOD 
 *
 */
public class PaternReInput extends AbstractPattern{

	final static Logger logger = LoggerFactory.getLogger(PaternReInput.class);
	
	protected AbstractToken starterToken;
	
	protected AbstractToken midfieldToken;
	
	protected AbstractToken markToken;
	
	protected AbstractToken markValueToken;
	
	
	protected AbstractToken enderToken;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//AbstractPattern asp =new PaternAdd();

	}
	

	public PaternReInput() {
		super();
		
		//REINPUT
		this.starterToken=new OzelKelimeToken("REINPUT", 0, 0, 0);
		starterToken.setSourceFieldName("FIRST_COMMAND");
		starterToken.setTekrarlayabilir("+");
		patternTokenList.add(starterToken);
		
		//dataToDisplay
		this.midfieldToken=new GenelTipToken<>();
		midfieldToken.setTekrarlayabilir("+");
		midfieldToken.setSourceFieldName("dataToDisplay");
		patternTokenList.add(midfieldToken);
		
		//MARK
		this.markToken=new OzelKelimeToken("MARK", 0, 0, 0);
		markToken.setTekrarlayabilir("+");
		markToken.setOptional(true);
		patternTokenList.add(markToken);
		
		//dataToDisplay
		this.markValueToken=new GenelTipToken<String>();
		markValueToken.setTekrarlayabilir("+");
		markValueToken.setSourceFieldName("markValue");
		markValueToken.setOptional(true);
		patternTokenList.add(markValueToken);
		
		
		//Ender
		this.enderToken=new OzelKelimeToken(ReservedNaturalKeywords.END_REINPUT,0,0,0);
		patternTokenList.add(enderToken);
						
		
	}
	
	@Override
	public AbstractCommand createElement(){
		ElementReInput elementDisplay = new ElementReInput(ReservedNaturalKeywords.REINPUT,"DATABASE.*.REINPUT");
		return elementDisplay;
	}


	@Override
	public void setTokenToElement(AbstractCommand matchedCommand,
			AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {
		
		ElementReInput matchedCommandAdd=(ElementReInput) matchedCommand;
		
		super.setSatirNumarasi(matchedCommand,currentTokenForMatch, abstractTokenInPattern);if(abstractTokenInPattern.getSourceFieldName()==null){
		
		}else if(abstractTokenInPattern.getSourceFieldName().equals("dataToDisplay")){
		
			List<AbstractToken> sourceList;
			if(matchedCommandAdd.getParameters().get("dataToDisplay")!=null){
				sourceList=(List<AbstractToken>) matchedCommandAdd.getParameters().get("dataToDisplay");
			}else{
				sourceList=new ArrayList<AbstractToken>();
			}
			sourceList.add(currentTokenForMatch);
			matchedCommandAdd.getParameters().put("dataToDisplay", sourceList);
			
		}else if(abstractTokenInPattern.getSourceFieldName().equals("markValue")){
			matchedCommandAdd.setMarkValue(currentTokenForMatch);
			matchedCommandAdd.getParameters().put("markValue", matchedCommandAdd.getMarkValue());
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
				if(currentTokenForMatch.tokenMatchs(markToken)){
					logger.info(" MATCHED: "+currentTokenForMatch.getDeger());
					setTokenToElement(matchedCommand, currentTokenForMatch,markToken);
					currentTokenForMatch=tokenListIterator.next();
					matchedCommand.increaseCommandsMatchPoint();
					break;
				}else if(currentTokenForMatch.tokenMatchs(enderToken)){
		
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
		
		
		if(currentTokenForMatch.tokenMatchs(markValueToken)){
			logger.info(" MATCHED: "+currentTokenForMatch.getDeger());
			setTokenToElement(matchedCommand, currentTokenForMatch,markValueToken);
		}
		matchedCommand.increaseCommandsMatchPoint();
		currentTokenForMatch=tokenListIterator.next();
		return matchedCommand;
	}


	
	
	

	
	
	
	

}
