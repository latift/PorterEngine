package tr.com.vbt.natural.parser.basicverbs.patern;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.basicverbs.ElementComment;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.patern.carriage_return.AbstractPatternFromXToYWithCarriageReturn;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.GenelTipToken;
import tr.com.vbt.token.KarakterToken;
import tr.com.vbt.token.SatirBasiToken;
import tr.com.vbt.token.TokenTipi;

/**
 *  *S**  ELSE /* TK-AJ 
 *
 */
public class PaternSlashStarComment extends AbstractPatternFromXToYWithCarriageReturn{

	final static Logger logger = LoggerFactory.getLogger(PaternSlashStarComment.class);
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//AbstractPattern asp =new PaternAdd();

	}

	AbstractToken starter,starter2,comment,ender;
	

	public PaternSlashStarComment() {
		super();
		
		
		//*
	    starter=new KarakterToken('/', 0, 1, 0, true,false);
	    starter.setSourceFieldName("FIRST_COMMAND");
		patternTokenList.add(starter);
		
		 starter2=new KarakterToken('*', 0, 1, 0, true,false);
		patternTokenList.add(starter2);
		
		 comment=new GenelTipToken();
		comment.setTekrarlayabilir("+");
		comment.setSourceFieldName("commentList");
		comment.setOptional(true);
		patternTokenList.add(comment);
		
		//SatirSonu
		 ender=new SatirBasiToken(0,false,true,"");
		patternTokenList.add(ender);
		
	}
	
	@Override
	public AbstractCommand createElement(){
		ElementComment createdElement = new ElementComment(ReservedCobolKeywords.COMMENT_ENTRY,"PROCEDURE_DIVISION.*.COMMENT");
		return createdElement;
	}


	@Override
	public void setTokenToElement(AbstractCommand matchedCommand,
			AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {
		
		ElementComment matchedCommanComment=(ElementComment) matchedCommand;
		
		super.setSatirNumarasi(matchedCommand,currentTokenForMatch, abstractTokenInPattern);if(abstractTokenInPattern.getSourceFieldName()==null){
		
		}else if(abstractTokenInPattern.getSourceFieldName().equals("commentList")){
			
			matchedCommanComment.getCommentList().add(((Object) currentTokenForMatch.getDeger()).toString());
			
			List<String> sourceList;
			if(matchedCommanComment.getParameters().get("commentList")!=null){
				sourceList=(List<String>) matchedCommanComment.getParameters().get("commentList");
			}else{
				sourceList=new ArrayList<String>();
			}
			if(currentTokenForMatch.getDeger() instanceof String){
				sourceList.add((String) currentTokenForMatch.getDeger());
			}else if(currentTokenForMatch.getDeger() instanceof Character){
				sourceList.add(String.valueOf(currentTokenForMatch.getDeger()));
			}
			matchedCommanComment.getParameters().put("commentList", sourceList);
			
		}
	}


	@Override
	public String toString() {
	return "COMMENT PATERN";
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
		
		
		if(!currentTokenForMatch.tokenMatchs(starter)){
			return null;
		}
		setTokenToElement(matchedCommand, currentTokenForMatch,starter);
		logger.info(" MATCHED"+currentTokenForMatch.getDeger());
		//***************************************************************
		
		if(!tokenListIterator.hasNext()){
			return null;
		}
		currentTokenForMatch=tokenListIterator.next();
		matchedCommand.increaseCommandsMatchPoint();
		
		if(!currentTokenForMatch.tokenMatchs(starter2)){
			return null;
		}
		logger.info(" MATCHED"+currentTokenForMatch.getDeger());
		//***************************************************************
		
		while(tokenListIterator.hasNext()){
			
			currentTokenForMatch=tokenListIterator.next();
			matchedCommand.increaseCommandsMatchPoint();
			
			if(currentTokenForMatch.getTip().equals(TokenTipi.SatirBasi)){
				if(currentTokenForMatch.tokenMatchs(ender)){
					logger.info(" MATCHED"+currentTokenForMatch.getDeger());
					return matchedCommand;
				}else{
					return null;
				}
			}
			else{
					if(currentTokenForMatch.tokenMatchs(comment)){
						logger.info(" MATCHED"+currentTokenForMatch.getDeger());
						matchedCommand.setSourceCodeSentence(matchedCommand.getSourceCodeSentence(),currentTokenForMatch);
						setTokenToElement(matchedCommand, currentTokenForMatch,comment);
					}
				}
		}
		return null;
	}





	
	
	

	
	
	
	

}
