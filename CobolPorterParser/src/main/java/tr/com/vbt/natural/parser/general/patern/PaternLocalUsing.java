package tr.com.vbt.natural.parser.general.patern;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.natural.parser.general.ElementLocalUsing;
import tr.com.vbt.patern.AbstractPattern;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.KelimeToken;
import tr.com.vbt.token.OzelKelimeToken;

/**
 *  LOCAL USING LDA01
 *
 */
public class PaternLocalUsing extends AbstractPattern{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//AbstractPattern asp =new PaternAdd();

	}
	

	public PaternLocalUsing() {
		super();
		
		//LOCAL USING
		AbstractToken astKeyword=new OzelKelimeToken("LOCAL_USING", 0, 0, 0);
		astKeyword.setSourceFieldName("FIRST_COMMAND");
		astKeyword.setTekrarlayabilir("+");
		patternTokenList.add(astKeyword);
		
		
		//LDA01
		AbstractToken astSource=new KelimeToken<String>();
		astSource.setTekrarlayabilir("+");
		astSource.setSourceFieldName("localParameterName");
		patternTokenList.add(astSource);
	}
	
	@Override
	public AbstractCommand createElement(){
		ElementLocalUsing createdElement = new ElementLocalUsing(ReservedNaturalKeywords.LOCAL_USING,"LOCAL_USING");
		return createdElement;
	}


	@Override
	public void setTokenToElement(AbstractCommand matchedCommand,
			AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {
		
		ElementLocalUsing matchedCommandAdd=(ElementLocalUsing) matchedCommand;
		
		super.setSatirNumarasi(matchedCommand,currentTokenForMatch, abstractTokenInPattern);if(abstractTokenInPattern.getSourceFieldName()==null){
		
		}else if(abstractTokenInPattern.getSourceFieldName().equals("localParameterName")){
			String localParameterName=(String) currentTokenForMatch.getDeger();
			matchedCommandAdd.setLocalParameterName(localParameterName);
			matchedCommandAdd.getParameters().put("localParameterName", matchedCommandAdd.getLocalParameterName());
		}
		
	}


		





	
	
	

	
	
	
	

}
