package tr.com.vbt.natural.parser.basicverbs.patern;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.natural.parser.basicverbs.ElementMoveByName;
import tr.com.vbt.patern.AbstractPattern;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.GenelTipToken;
import tr.com.vbt.token.OzelKelimeToken;
import tr.com.vbt.token.TokenTipi;

/**
*S**  MOVE BY NAME FRMMLZ TO FRMMLZ2
 *    
 *
 */
public class PaternMoveByName extends AbstractPattern{

	final static Logger logger = LoggerFactory.getLogger(PaternMoveByName.class);


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//AbstractPattern asp =new PaternAdd();

	}
	

	public PaternMoveByName() {
		super();
		
		//MOVE
		AbstractToken<String> astKeyword=new OzelKelimeToken<String>("MOVE_BY_NAME", 0, 0, 0);
		astKeyword.setSourceFieldName("FIRST_COMMAND");
		astKeyword.setTekrarlayabilir("+");
		patternTokenList.add(astKeyword);
		
		//#
		/*AbstractToken astSource5=new KarakterToken('#', 0,0,0);
		patternTokenList.add(astSource5);*/
				
		//dataToDisplay
		AbstractToken<String> astSource=new GenelTipToken<String>();
		astSource.setTekrarlayabilir("+");
		astSource.setSourceFieldName("dataToMove");
		patternTokenList.add(astSource);
		
		//TO
		AbstractToken<String> astKeyword2=new OzelKelimeToken<String>("TO", 0, 0, 0);
		astKeyword2.setTekrarlayabilir("+");
		patternTokenList.add(astKeyword2);
		
		
		//#
		/*AbstractToken astSource6=new KarakterToken('#', 0,0,0);
		patternTokenList.add(astSource6);*/
		
		//destVariable
		AbstractToken<String> astSource2=new GenelTipToken<String>();
		astSource2.setTekrarlayabilir("+");
		astSource2.setSourceFieldName("destVariable");
		patternTokenList.add(astSource2);
		
		
	}
	
	@Override
	public AbstractCommand createElement(){
		ElementMoveByName elementDisplay = new ElementMoveByName(ReservedNaturalKeywords.MOVE_BY_NAME,"GENERAL.*.MOVE_BY_NAME");
		return elementDisplay;
	}


	@Override
	public void setTokenToElement(AbstractCommand matchedCommand,
			AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {
		
		ElementMoveByName matchedCommandAdd=(ElementMoveByName) matchedCommand;
		
		super.setSatirNumarasi(matchedCommand,currentTokenForMatch, abstractTokenInPattern);if(abstractTokenInPattern.getSourceFieldName()==null){
			
		}else if(abstractTokenInPattern.getSourceFieldName().equals("dataToMove")){
			matchedCommandAdd.setDataToMove(currentTokenForMatch);
			matchedCommandAdd.getParameters().put("dataToMove", matchedCommandAdd.getDataToMove());
		}else if(abstractTokenInPattern.getSourceFieldName().equals("destVariable")){
			
			matchedCommandAdd.getDestVariable().add(currentTokenForMatch);
			List<AbstractToken> sourceList;
			if(matchedCommandAdd.getParameters().get("destVariable")!=null){
				sourceList=(List<AbstractToken>) matchedCommandAdd.getParameters().get("destVariable");
			}else{
				sourceList=new ArrayList<AbstractToken>();
			}
			sourceList.add(currentTokenForMatch);
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
		
		
		while(tokenListIterator.hasNext())	{ //Satir Başı bu paternlerde önemli ancak bitişte önemli başlangıçta bunu atlıyoruz.
			currentTokenForMatch=tokenListIterator.next();
			matchedCommand.increaseCommandsMatchPoint();
			if(!currentTokenForMatch.getTip().equals(TokenTipi.SatirBasi)){
				break;
			}
		}
		
		if(!currentTokenForMatch.tokenMatchs(toToken)){
			return null;
		}
		logger.info(" MATCHED "+currentTokenForMatch.getDeger());
		
		while(tokenListIterator.hasNext()){
			
			currentTokenForMatch=tokenListIterator.next();  //Satir Başı bu paternlerde önemli ancak bitişte önemli başlangıçta bunu atlıyoruz.
			matchedCommand.increaseCommandsMatchPoint();
			if(currentTokenForMatch.getTip().equals(TokenTipi.SatirBasi)){
				continue;
			}
			
			if(currentTokenForMatch.getTip().equals(TokenTipi.OzelKelime)){ //IFElement için eklendi
					matchedCommand.decreaseCommandsMatchPoint();
					return matchedCommand;
			}
			if(currentTokenForMatch.getTip().equals(TokenTipi.Nokta)||currentTokenForMatch.getTip().equals(TokenTipi.KeyValueOzelKelime)||currentTokenForMatch.getTip().equals(TokenTipi.SatirBasi)){
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
