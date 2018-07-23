package tr.com.vbt.cobol.parser.general.patern;

import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.general.ElementAuthor;
import tr.com.vbt.cobol.parser.general.ElementAuthor;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.patern.AbstractPattern;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.CommandKeyToken;
import tr.com.vbt.token.GenelTipToken;
import tr.com.vbt.token.KelimeToken;
import tr.com.vbt.token.OzelKelimeToken;

/**
 *     PROGRAM-ID. HELLO_WORD.
 *
 */
public class PaternAuthor extends AbstractPattern{

	
	public PaternAuthor() {
		super();
		
		//PROGRAM-ID
		AbstractToken astKeyword=new CommandKeyToken("AUTHOR", 0, 0, 0);
		astKeyword.setTekrarlayabilir("+");
		astKeyword.setSourceFieldName("FIRST_COMMAND");
		patternTokenList.add(astKeyword);
		
		
		//HELLO_WORD
		AbstractToken<String> astSource=new KelimeToken<String>();
		astSource.setTekrarlayabilir("+");
		astSource.setSourceFieldName("param");
		patternTokenList.add(astSource);
		
		
	}
	
	@Override
	public AbstractCommand createElement(){
		ElementAuthor elementDisplay = new ElementAuthor(ReservedCobolKeywords.AUTHOR,"GENERAL.*.AUTHOR");
		return elementDisplay;
	}


	@Override
	public void setTokenToElement(AbstractCommand matchedCommand,
			AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {
		
		ElementAuthor matchedCommandAdd=(ElementAuthor) matchedCommand;
		
		super.setSatirNumarasi(matchedCommand,currentTokenForMatch, abstractTokenInPattern);
		if(abstractTokenInPattern.getSourceFieldName()==null){
		
		}
		else if(abstractTokenInPattern.getSourceFieldName().equals("FIRST_COMMAND")){
			
		}else if(abstractTokenInPattern.getSourceFieldName().equals("param")){
			matchedCommandAdd.setParam(currentTokenForMatch);
			matchedCommandAdd.getParameters().put("param", matchedCommandAdd.getParam());
	
		}
	}
		


	public AbstractCommand getmatchedCommand(List<AbstractToken> tokenListesi, int offset) {
		return super.getmatchedCommand(tokenListesi, offset);
	}
	
	

}
