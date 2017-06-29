package tr.com.vbt.natural.parser.general.patern;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.natural.parser.general.ElementGlobalUsing;
import tr.com.vbt.patern.AbstractPattern;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.KelimeToken;
import tr.com.vbt.token.OzelKelimeToken;

/**
 * GLOBAL USING GDA01
 *
 */
public class PaternGlobalUsing extends AbstractPattern{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//AbstractPattern asp =new PaternAdd();

	}
	

	public PaternGlobalUsing() {
		super();
		
		//GLOBAL USING
		AbstractToken astKeyword=new OzelKelimeToken("GLOBAL_USING", 0, 0, 0);
		astKeyword.setSourceFieldName("FIRST_COMMAND");
		astKeyword.setTekrarlayabilir("+");
		patternTokenList.add(astKeyword);
		
		//GDA01
		AbstractToken astSource=new KelimeToken<String>();
		astSource.setTekrarlayabilir("+");
		astSource.setSourceFieldName("globalParameterName");
		patternTokenList.add(astSource);
		
	}
	
	@Override
	public AbstractCommand createElement(){
		ElementGlobalUsing createdElement = new ElementGlobalUsing(ReservedNaturalKeywords.GLOBAL_USING,"GLOBAL_USING");
		return createdElement;
	}


	@Override
	public void setTokenToElement(AbstractCommand matchedCommand,
			AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {
		
		ElementGlobalUsing matchedCommandAdd=(ElementGlobalUsing) matchedCommand;
		
		super.setSatirNumarasi(matchedCommand,currentTokenForMatch, abstractTokenInPattern);if(abstractTokenInPattern.getSourceFieldName()==null){
		
		}else if(abstractTokenInPattern.getSourceFieldName().equals("globalParameterName")){
			String globalParameterName=(String) currentTokenForMatch.getDeger();
			matchedCommandAdd.setGlobalParameterName(globalParameterName);
			matchedCommandAdd.getParameters().put("globalParameterName", matchedCommandAdd.getGlobalParameterName());
		}
		
	}


		





	
	
	

	
	
	
	

}
