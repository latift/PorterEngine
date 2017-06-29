package tr.com.vbt.natural.parser.basicverbs.patern;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.natural.parser.basicverbs.ElementSetControl;
import tr.com.vbt.patern.AbstractPattern;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.KelimeToken;
import tr.com.vbt.token.OzelKelimeToken;

/**
 * 0988 SET CONTROL 'MB'
 */
public class PaternSetControl extends AbstractPattern{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//AbstractPattern asp =new PaternAdd();

	}
	

	public PaternSetControl() {
		super();
		
		//SET_CONTROL
		AbstractToken astKeyword=new OzelKelimeToken("SET_CONTROL", 0, 0, 0);
		astKeyword.setTekrarlayabilir("+");
		astKeyword.setSourceFieldName("FIRST_COMMAND");
		patternTokenList.add(astKeyword);
		
		//A
		AbstractToken astSource=new KelimeToken<String>();
		astSource.setTekrarlayabilir("+");
		astSource.setSourceFieldName("controlName");
		patternTokenList.add(astSource);
		
	}
	
	@Override
	public AbstractCommand createElement(){
		ElementSetControl createdElement = new ElementSetControl(ReservedNaturalKeywords.SET_CONTROL,"GENERAL.*.SET_CONTROL");
		return createdElement;
	}


	@Override
	public void setTokenToElement(AbstractCommand matchedCommand,
			AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {
		
		ElementSetControl matchedCommandAdd=(ElementSetControl) matchedCommand;
		
		super.setSatirNumarasi(matchedCommand,currentTokenForMatch, abstractTokenInPattern);if(abstractTokenInPattern.getSourceFieldName()==null){
		
		}else if(abstractTokenInPattern.getSourceFieldName().equals("controlName")){
			String controlName=(String) currentTokenForMatch.getDeger();
			controlName=controlName.replaceAll("-", "_");
			matchedCommandAdd.setControlName(controlName);
			matchedCommandAdd.getParameters().put("controlName", matchedCommandAdd.getControlName());
		}
	}
		





	
	
	

	
	
	
	

}
