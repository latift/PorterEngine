package tr.com.vbt.cobol.parser.general.patern;

import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.general.ElementProgramId;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.patern.AbstractPattern;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.OzelKelimeToken;

/**
 *     PROGRAM-ID. HELLO_WORD.
 *
 */
public class PaternProgramId extends AbstractPattern{

	
	public PaternProgramId() {
		super();
		
		//IDENTIFICATION_DIVISION
		AbstractToken astKeyword=new OzelKelimeToken("PROGRAM-ID", 0, 0, 0);
		astKeyword.setTekrarlayabilir("+");
		astKeyword.setSourceFieldName("FIRST_COMMAND");
		patternTokenList.add(astKeyword);
		
	}
	
	@Override
	public AbstractCommand createElement(){
		ElementProgramId elementDisplay = new ElementProgramId(ReservedCobolKeywords.PROGRAM_ID,"GENERAL.*.PROGRAM_ID");
		return elementDisplay;
	}


	@Override
	public void setTokenToElement(AbstractCommand matchedCommand,
			AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {
		
		ElementProgramId matchedCommandAdd=(ElementProgramId) matchedCommand;
		
		super.setSatirNumarasi(matchedCommand,currentTokenForMatch, abstractTokenInPattern);
		if(abstractTokenInPattern.getSourceFieldName()==null){
		
		}
		else if(abstractTokenInPattern.getSourceFieldName().equals("FIRST_COMMAND")){
				matchedCommandAdd.setParam(currentTokenForMatch);
				matchedCommandAdd.getParameters().put("param", matchedCommandAdd.getParam());
		
		}
	}
		


	public AbstractCommand getmatchedCommand(List<AbstractToken> tokenListesi, int offset) {
		return super.getmatchedCommand(tokenListesi, offset);
	}
	
	

}
