package tr.com.vbt.cobol.parser.basicverbs.patern;

import java.util.ArrayList;
import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.basicverbs.ElementDisplay;
import tr.com.vbt.cobol.parser.division.ElementIdentificationDivision;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.patern.AbstractPattern;
import tr.com.vbt.patern.carriage_return.AbstractPattern_XY_KKKK_Z_WithoutCarriageReturn;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.CommandKeyToken;
import tr.com.vbt.token.GenelTipToken;
import tr.com.vbt.token.KelimeToken;
import tr.com.vbt.token.OzelKelimeToken;
import tr.com.vbt.token.TokenTipi;

/**
 *     DISPLAY "Hello World".
 *
 */
public class PaternDisplay extends AbstractPattern{


	public PaternDisplay() {
		super();
		
		//PROGRAM-ID
		AbstractToken astKeyword=new CommandKeyToken("DISPLAY", 0, 0, 0);
		astKeyword.setTekrarlayabilir("+");
		astKeyword.setSourceFieldName("FIRST_COMMAND");
		patternTokenList.add(astKeyword);
		
		
		//HELLO_WORD
		AbstractToken<String> astSource=new KelimeToken<String>();
		astSource.setTekrarlayabilir("+");
		astSource.setSourceFieldName("dataToDisplay");
		patternTokenList.add(astSource);
		
		//Ender
//		AbstractToken<String> enderToken =new OzelKelimeToken(ReservedCobolKeywords.END_DISPLAY,0,0,0);
//		patternTokenList.add(enderToken);
		
		
	}
	
	@Override
	public AbstractCommand createElement(){
		ElementDisplay elementDisplay = new ElementDisplay(ReservedCobolKeywords.DISPLAY,"GENERAL.*.DISPLAY");
		return elementDisplay;
	}


	@Override
	public void setTokenToElement(AbstractCommand matchedCommand,
			AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {
		
		ElementDisplay matchedCommandAdd=(ElementDisplay) matchedCommand;
		
		super.setSatirNumarasi(matchedCommand,currentTokenForMatch, abstractTokenInPattern);if(abstractTokenInPattern.getSourceFieldName()==null){
		
		}else if(abstractTokenInPattern.getSourceFieldName().equals("dataToDisplay")){
			if(currentTokenForMatch.getTip().equals(TokenTipi.Karakter)&&currentTokenForMatch.getDeger().equals('#')){
				return;
			}
			matchedCommandAdd.getDataToDisplay().add((AbstractToken) currentTokenForMatch);
			
			List<AbstractToken> sourceList;
			if(matchedCommandAdd.getParameters().get("dataToDisplay")!=null){
				sourceList=(List<AbstractToken>) matchedCommandAdd.getParameters().get("dataToDisplay");
			}else{
				sourceList=new ArrayList<AbstractToken>();
			}
			sourceList.add((AbstractToken) currentTokenForMatch);
			matchedCommandAdd.getParameters().put("dataToDisplay", sourceList);
		}
	}
		


	public AbstractCommand getmatchedCommand(List<AbstractToken> tokenListesi, int offset) {
		return super.getmatchedCommand(tokenListesi, offset);
	}
	
	

}
