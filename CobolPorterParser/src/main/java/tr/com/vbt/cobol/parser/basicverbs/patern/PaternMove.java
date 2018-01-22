package tr.com.vbt.cobol.parser.basicverbs.patern;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.apache.log4j.Logger;


import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.basicverbs.ElementMove;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.patern.AbstractPattern;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.GenelTipToken;
import tr.com.vbt.token.KelimeToken;
import tr.com.vbt.token.OzelKelimeToken;
import tr.com.vbt.token.TokenTipi;

/**
 *     MOVE CCX TO CC1 --> CC1=CCX;

 *
 */
public class PaternMove extends AbstractPattern{

	final static Logger logger = Logger.getLogger(PaternMove.class);
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//AbstractPattern asp =new PaternAdd();

	}
	

	public PaternMove() {
		super();
		
		//MOVE
		AbstractToken<String> astKeyword=new OzelKelimeToken<String>("MOVE", 0, 0, 0);
		astKeyword.setTekrarlayabilir("+");
		patternTokenList.add(astKeyword);
		
		
		//dataToDisplay
		AbstractToken<String> astSource=new GenelTipToken<String>();
		astSource.setTekrarlayabilir("+");
		astSource.setSourceFieldName("dataToMove");
		patternTokenList.add(astSource);
		
		//MOVE
		AbstractToken<String> astKeyword2=new OzelKelimeToken<String>("TO", 0, 0, 0);
		astKeyword2.setTekrarlayabilir("+");
		patternTokenList.add(astKeyword2);
		
		
		//destVariable
		AbstractToken<String> astSource2=new KelimeToken<String>();
		astSource2.setTekrarlayabilir("+");
		astSource2.setSourceFieldName("destVariable");
		patternTokenList.add(astSource2);
		
		
	}
	
	@Override
	public AbstractCommand createElement(){
		ElementMove elementDisplay = new ElementMove(ReservedCobolKeywords.MOVE,"PROCEDURE_DIVISION.*.MOVE");
		return elementDisplay;
	}


	@Override
	public void setTokenToElement(AbstractCommand matchedCommand,
			AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {
		
		ElementMove matchedCommandAdd=(ElementMove) matchedCommand;
		
		super.setSatirNumarasi(matchedCommand,currentTokenForMatch, abstractTokenInPattern);if(abstractTokenInPattern.getSourceFieldName()==null){
		
		}else if(abstractTokenInPattern.getSourceFieldName().equals("dataToMove")){
			if(currentTokenForMatch.getDeger() instanceof String){
				matchedCommandAdd.setDataToMove((String) currentTokenForMatch.getDeger());
			}else if(currentTokenForMatch.getDeger() instanceof Double){
				matchedCommandAdd.setDataToMove( ((Double)currentTokenForMatch.getDeger()).toString());
			}
			matchedCommandAdd.getParameters().put("dataToMove", matchedCommandAdd.getDataToMove());
		}else if(abstractTokenInPattern.getSourceFieldName().equals("destVariable")){
			
			matchedCommandAdd.getDestVariable().add((String) currentTokenForMatch.getDeger());
			List<String> sourceList;
			if(matchedCommandAdd.getParameters().get("destVariable")!=null){
				sourceList=(List<String>) matchedCommandAdd.getParameters().get("destVariable");
			}else{
				sourceList=new ArrayList<String>();
			}
			sourceList.add((String) currentTokenForMatch.getDeger());
			matchedCommandAdd.getParameters().put("destVariable", sourceList);
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
		
		AbstractToken starterToken=patternTokenList.get(0);
		AbstractToken moveToken=patternTokenList.get(1);
		AbstractToken toToken=patternTokenList.get(2);
		AbstractToken destListToken=patternTokenList.get(3);
		
		
		if(!currentTokenForMatch.tokenMatchs(starterToken)){
			return null;
		}
		logger.info(" MATCHED "+currentTokenForMatch.getDeger());
		setTokenToElement(matchedCommand, currentTokenForMatch,starterToken);
		
		
		
		currentTokenForMatch=tokenListIterator.next();
		matchedCommand.increaseCommandsMatchPoint();
		if(!currentTokenForMatch.tokenMatchs(moveToken)){
			return null;
		}
		logger.info(" MATCHED "+currentTokenForMatch.getDeger());
		matchedCommand.setSourceCodeSentence(matchedCommand.getSourceCodeSentence(),currentTokenForMatch);
		setTokenToElement(matchedCommand, currentTokenForMatch,moveToken);
		
		
		
		currentTokenForMatch=tokenListIterator.next();
		matchedCommand.increaseCommandsMatchPoint();
		if(!currentTokenForMatch.tokenMatchs(toToken)){
			return null;
		}
		logger.info(" MATCHED "+currentTokenForMatch.getDeger());
		
		
		
		while(tokenListIterator.hasNext()){
			
			currentTokenForMatch=tokenListIterator.next();
			matchedCommand.increaseCommandsMatchPoint();
			
			if(currentTokenForMatch.getTip().equals(TokenTipi.OzelKelime)){ //IFElement için eklendi
					matchedCommand.decreaseCommandsMatchPoint();
					return matchedCommand;
			}
			else if(currentTokenForMatch.getTip().equals(TokenTipi.Nokta)||currentTokenForMatch.getTip().equals(TokenTipi.SatirBasi)){
				return matchedCommand;
			}
			else{
					if(currentTokenForMatch.tokenMatchs(destListToken)){
						logger.info(" MATCHED"+currentTokenForMatch.getDeger());
						matchedCommand.setSourceCodeSentence(matchedCommand.getSourceCodeSentence(),currentTokenForMatch);
						setTokenToElement(matchedCommand, currentTokenForMatch,destListToken);
					}
				}
		}
		return null;
	}



	
	
	

	
	
	
	

}
