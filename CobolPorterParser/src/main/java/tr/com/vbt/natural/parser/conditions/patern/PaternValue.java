package tr.com.vbt.natural.parser.conditions.patern;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.natural.parser.conditions.ElementValue;
import tr.com.vbt.patern.AbstractPattern;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.KelimeToken;
import tr.com.vbt.token.OzelKelimeToken;

/**
 *     VALUE 1 FETCH RETURN 'TOPADEP5'

 *
 */
public class PaternValue extends AbstractPattern{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//AbstractPattern asp =new PaternAdd();

	}
	

	public PaternValue() {
		super();
		
		//VALUE
		AbstractToken<String> astKey=new OzelKelimeToken("VALUE", 0, 0, 0);
		astKey.setSourceFieldName("FIRST_COMMAND");
		patternTokenList.add(astKey);
		
		//VALUE
		AbstractToken<String> astValue=new KelimeToken<>();
		astValue.setSourceFieldName("VALUE");
		patternTokenList.add(astValue);
	
	}
	
	@Override
	public AbstractCommand createElement(){
		ElementValue elementDisplay = new ElementValue(ReservedNaturalKeywords.VALUE,"GENERAL.*.VALUE");
		return elementDisplay;
	}


	@Override
	public void setTokenToElement(AbstractCommand matchedCommand,
			AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {
		
		ElementValue matchedCommandAdd=(ElementValue) matchedCommand;
		
		super.setSatirNumarasi(matchedCommand,currentTokenForMatch, abstractTokenInPattern);
		
		if(abstractTokenInPattern.getSourceFieldName()==null){
		
		}else if(abstractTokenInPattern.getSourceFieldName().equals("VALUE")){
			matchedCommandAdd.setValue(currentTokenForMatch);
			matchedCommandAdd.getParameters().put("VALUE", matchedCommandAdd.getValue());
	
		}
	}
		
}
