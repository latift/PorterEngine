package tr.com.vbt.natural.parser.basicverbs.patern;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.natural.parser.basicverbs.ElementPerform;
import tr.com.vbt.patern.AbstractPattern;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.KelimeToken;
import tr.com.vbt.token.OzelKelimeToken;

/**
 * PERFORM B-PARA
 */
public class PaternPerform extends AbstractPattern{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//AbstractPattern asp =new PaternAdd();

	}
	

	public PaternPerform() {
		super();
		
		//PERFORM
		AbstractToken astKeyword=new OzelKelimeToken("PERFORM", 0, 0, 0);
		astKeyword.setTekrarlayabilir("+");
		astKeyword.setSourceFieldName("FIRST_COMMAND");
		patternTokenList.add(astKeyword);
		
		//A
		AbstractToken astSource=new KelimeToken<String>();
		astSource.setTekrarlayabilir("+");
		astSource.setSourceFieldName("paragraghName");
		patternTokenList.add(astSource);
		
	}
	
	@Override
	public AbstractCommand createElement(){
		ElementPerform createdElement = new ElementPerform(ReservedNaturalKeywords.PERFORM,"GENERAL.*.PERFORM");
		return createdElement;
	}


	@Override
	public void setTokenToElement(AbstractCommand matchedCommand,
			AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {
		
		ElementPerform matchedCommandAdd=(ElementPerform) matchedCommand;
		
		super.setSatirNumarasi(matchedCommand,currentTokenForMatch, abstractTokenInPattern);if(abstractTokenInPattern.getSourceFieldName()==null){
		
		}else if(abstractTokenInPattern.getSourceFieldName().equals("paragraghName")){
			String paragraphname=(String) currentTokenForMatch.getDeger();
			paragraphname=paragraphname.replaceAll("-", "_");
			matchedCommandAdd.setParagraghName(paragraphname);
			matchedCommandAdd.getParameters().put("paragraghName", matchedCommandAdd.getParagraghName());
		}
	}
		





	
	
	

	
	
	
	

}
