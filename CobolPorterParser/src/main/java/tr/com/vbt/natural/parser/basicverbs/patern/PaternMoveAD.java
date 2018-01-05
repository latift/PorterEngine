package tr.com.vbt.natural.parser.basicverbs.patern;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.apache.log4j.Logger;


import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.natural.parser.basicverbs.ElementMove;
import tr.com.vbt.patern.AbstractPattern;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.GenelTipToken;
import tr.com.vbt.token.KarakterToken;
import tr.com.vbt.token.KelimeToken;
import tr.com.vbt.token.OzelKelimeToken;
import tr.com.vbt.token.TokenTipi;

/**
 * MOVE (AD=P) TO #CV 

	MOVE  Uzunluk:0 Satir No:79 Tipi:OzelKelime
	(  Uzunluk:1 Satir No:79 Tipi:Karakter
	AD  Uzunluk:0 Satir No:79 Tipi:Kelime
	=  Uzunluk:1 Satir No:79 Tipi:Karakter
	P  Uzunluk:0 Satir No:79 Tipi:Kelime
	)  Uzunluk:1 Satir No:79 Tipi:Karakter
	TO  Uzunluk:0 Satir No:79 Tipi:OzelKelime
	CV  Uzunluk:0 Satir No:79 Tipi:Kelime
 *
 */
public class PaternMoveAD extends AbstractPattern{

	final static Logger logger = Logger.getLogger(PaternMoveAD.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//AbstractPattern asp =new PaternAdd();

	}
	
	AbstractToken<String> astMove,astParantez,astSourceAD,
	astSourceEqualSign,astSourceP,astKapaParantez,astTo,astDest;

	public PaternMoveAD() {
		super();
		
		//MOVE
		astMove=new OzelKelimeToken<String>("MOVE", 0, 0, 0);
		astMove.setTekrarlayabilir("+");
		astMove.setSourceFieldName("FIRST_COMMAND");
		patternTokenList.add(astMove);
		
		//(
		astParantez=new KarakterToken('(', 0,0,0);
		patternTokenList.add(astParantez);
		
		astSourceAD=new KelimeToken<String>();
		astSourceAD.setSourceFieldName("astSourceAD");
		patternTokenList.add(astSourceAD);
		
		//=
		astSourceEqualSign=new KarakterToken('=', 0,0,0);
		patternTokenList.add(astSourceEqualSign);
		
		astSourceP=new KelimeToken<String>();
		astSourceP.setSourceFieldName("astSourceP");
		patternTokenList.add(astSourceP);
		
		//)
		astKapaParantez=new KarakterToken(')', 0,0,0);
		patternTokenList.add(astKapaParantez);
				
		//TO
		astTo=new OzelKelimeToken<String>("TO", 0, 0, 0);
		patternTokenList.add(astTo);
		
		//destVariable
		astDest=new GenelTipToken<String>();
		astDest.setTekrarlayabilir("+");
		astDest.setSourceFieldName("destVariable");
		patternTokenList.add(astDest);
		
		
	}
	
	@Override
	public AbstractCommand createElement(){
		ElementMove elementDisplay = new ElementMove(ReservedNaturalKeywords.MOVE,"GENERAL.*.MOVE");
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
				matchedCommandAdd.setDataToMove(currentTokenForMatch);
			}else if(currentTokenForMatch.getDeger() instanceof Double){
				matchedCommandAdd.setDataToMove(currentTokenForMatch);
			}
			matchedCommandAdd.getParameters().put("dataToMove", matchedCommandAdd.getDataToMove());
		}else if(abstractTokenInPattern.getSourceFieldName().equals("destVariable")){
			
			matchedCommandAdd.getDestVariable().add(currentTokenForMatch);
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
		
		/**
		 * astMove,astParantez,astSourceAD,
	astSourceEqualSign,astSourceP,astKapaParantez,astTo,astDest;
		 */
		
		if(!currentTokenForMatch.tokenMatchs(astMove)){
			return null;
		}
		logger.info(" MATCHED "+currentTokenForMatch.getDeger());
		setTokenToElement(matchedCommand, currentTokenForMatch,astMove);
		//****************************************************************
		
		
		currentTokenForMatch=tokenListIterator.next();
		matchedCommand.increaseCommandsMatchPoint();
		if(!currentTokenForMatch.tokenMatchs(astParantez)){
			return null;
		}
		logger.info(" MATCHED "+currentTokenForMatch.getDeger());
		//****************************************************************
		
		
		
		currentTokenForMatch=tokenListIterator.next();
		matchedCommand.increaseCommandsMatchPoint();
		if(!currentTokenForMatch.tokenMatchs(astSourceAD)){
			return null;
		}
		logger.info(" MATCHED "+currentTokenForMatch.getDeger());
		//****************************************************************
		
		currentTokenForMatch=tokenListIterator.next();
		matchedCommand.increaseCommandsMatchPoint();
		if(!currentTokenForMatch.tokenMatchs(astSourceEqualSign)){
			return null;
		}
		logger.info(" MATCHED "+currentTokenForMatch.getDeger());
		//****************************************************************
		
		
		currentTokenForMatch=tokenListIterator.next();
		matchedCommand.increaseCommandsMatchPoint();
		if(!currentTokenForMatch.tokenMatchs(astSourceP)){
			return null;
		}
		logger.info(" MATCHED "+currentTokenForMatch.getDeger());
		//****************************************************************
		
		currentTokenForMatch=tokenListIterator.next();
		matchedCommand.increaseCommandsMatchPoint();
		if(!currentTokenForMatch.tokenMatchs(astKapaParantez)){
			return null;
		}
		logger.info(" MATCHED "+currentTokenForMatch.getDeger());
		//****************************************************************
		
		currentTokenForMatch=tokenListIterator.next();
		matchedCommand.increaseCommandsMatchPoint();
		if(!currentTokenForMatch.tokenMatchs(astTo)){
			return null;
		}
		logger.info(" MATCHED "+currentTokenForMatch.getDeger());
		//****************************************************************
		
		
		
		while(tokenListIterator.hasNext()){
			
			currentTokenForMatch=tokenListIterator.next();
			matchedCommand.increaseCommandsMatchPoint();
			
			if(currentTokenForMatch.getTip().equals(TokenTipi.OzelKelime)){ 
					matchedCommand.decreaseCommandsMatchPoint();
					return matchedCommand;
			}
			if(currentTokenForMatch.getTip().equals(TokenTipi.Nokta)||currentTokenForMatch.getTip().equals(TokenTipi.SatirBasi)){
				return matchedCommand;
			}
			else{
					if(currentTokenForMatch.tokenMatchs(astDest)){
						logger.info(" MATCHED"+currentTokenForMatch.getDeger());
						matchedCommand.setSourceCodeSentence(matchedCommand.getSourceCodeSentence(),currentTokenForMatch);
						setTokenToElement(matchedCommand, currentTokenForMatch,astDest);
					}
				}
		}
		return null;
	}



	
	
	

	
	
	
	

}
