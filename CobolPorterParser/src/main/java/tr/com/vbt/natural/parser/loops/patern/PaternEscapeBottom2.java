package tr.com.vbt.natural.parser.loops.patern;

import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.natural.parser.loops.ElementEscapeBottom;
import tr.com.vbt.patern.AbstractPattern;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.GenelTipToken;
import tr.com.vbt.token.KarakterToken;
import tr.com.vbt.token.OzelKelimeToken;

/**
 *     //ESCAPE BOTTOM (RP1.)
 *     //ESCAPE BOTTOM(4440)
 *
 */
public class PaternEscapeBottom2 extends AbstractPattern{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//AbstractPattern asp =new PaternAdd();

	}
	

	public PaternEscapeBottom2() {
		super();
		
		//ESCAPE_BOTTOM
		AbstractToken astKeyword=new OzelKelimeToken("ESCAPE_BOTTOM", 0, 0, 0);
		astKeyword.setSourceFieldName("FIRST_COMMAND");
		patternTokenList.add(astKeyword);
				
		//( 
		AbstractToken astSource5=new KarakterToken('(', 0,0,0);
		patternTokenList.add(astSource5);
				
		//RP1 yada Sayi 4440
		AbstractToken astSource=new GenelTipToken();
		astSource.setSourceFieldName("LOOP_NAME");
		patternTokenList.add(astSource);
		
		//)
		AbstractToken astSource6=new KarakterToken(')', 0,0,0);
		patternTokenList.add(astSource6);
		
	}
	
	@Override
	public AbstractCommand createElement(){
		ElementEscapeBottom elementEscapeBottom = new ElementEscapeBottom(ReservedNaturalKeywords.ESCAPE_BOTTOM,"GENERAL.*.ESCAPE_BOTTOM");
		return elementEscapeBottom;
	}


	@Override
	public void setTokenToElement(AbstractCommand matchedCommand,
			AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {
		
		ElementEscapeBottom matchedCommandAdd=(ElementEscapeBottom) matchedCommand;
		
		super.setSatirNumarasi(matchedCommand,currentTokenForMatch, abstractTokenInPattern);if(abstractTokenInPattern.getSourceFieldName()==null){
		
		}else if(abstractTokenInPattern.getSourceFieldName().equals("LOOP_NAME")){
				
			matchedCommandAdd.setLoopName(currentTokenForMatch);
			
			matchedCommandAdd.getParameters().put("LOOP_NAME", matchedCommandAdd.getLoopName());
			
		}
	}
		
	public AbstractCommand getmatchedCommand(List<AbstractToken> tokenListesi, int offset){
		return super.getmatchedCommand(tokenListesi, offset);
	}





	
	
	

	
	
	
	

}
