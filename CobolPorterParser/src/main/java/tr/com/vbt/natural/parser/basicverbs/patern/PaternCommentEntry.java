package tr.com.vbt.natural.parser.basicverbs.patern;

import java.util.ArrayList;
import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.basicverbs.ElementComment;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.patern.carriage_return.AbstractPatternFromXToYWithCarriageReturn;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.GenelTipToken;
import tr.com.vbt.token.KarakterToken;
import tr.com.vbt.token.SatirBasiToken;

/**
 *  ****Comment
 * @author 47159500
 *
 */
public class PaternCommentEntry extends AbstractPatternFromXToYWithCarriageReturn{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//AbstractPattern asp =new PaternAdd();

	}
	

	public PaternCommentEntry() {
		super();
		
		
		//*
		AbstractToken starter=new KarakterToken('*', 0, 1, 0, true,false);
		starter.setTekrarlayabilir("+");
		starter.setSourceFieldName("FIRST_COMMAND");
		patternTokenList.add(starter);
		
		AbstractToken comment=new GenelTipToken();
		comment.setTekrarlayabilir("+");
		comment.setSourceFieldName("commentList");
		comment.setOptional(true);
		patternTokenList.add(comment);
		
		//SatirSonu
		AbstractToken ender=new SatirBasiToken(0,false,true,"");
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


		





	
	
	

	
	
	
	

}
