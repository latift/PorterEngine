package tr.com.vbt.natural.parser.basicverbs.patern;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.natural.parser.basicverbs.ElementRedefine;
import tr.com.vbt.patern.AbstractPattern;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.KelimeToken;
import tr.com.vbt.token.OzelKelimeToken;

/**
 * REDEFINE B-PARA
 */
public class PaternRedefine extends AbstractPattern{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//AbstractPattern asp =new PaternAdd();

	}
	

	public PaternRedefine() {
		super();
		
		//REDEFINE
		AbstractToken astKeyword=new OzelKelimeToken("REDEFINE", 0, 0, 0);
		astKeyword.setTekrarlayabilir("+");
		astKeyword.setSourceFieldName("FIRST_COMMAND");
		patternTokenList.add(astKeyword);
		
		//A
		AbstractToken astSource=new KelimeToken<String>();
		astSource.setTekrarlayabilir("+");
		astSource.setSourceFieldName("redefineParameter");
		patternTokenList.add(astSource);
		
	}
	
	@Override
	public AbstractCommand createElement(){
		ElementRedefine createdElement = new ElementRedefine(ReservedNaturalKeywords.REDEFINE,"GENERAL.*.REDEFINE");
		return createdElement;
	}


	@Override
	public void setTokenToElement(AbstractCommand matchedCommand,
			AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {
		
		ElementRedefine matchedCommandAdd=(ElementRedefine) matchedCommand;
		
		super.setSatirNumarasi(matchedCommand,currentTokenForMatch, abstractTokenInPattern);if(abstractTokenInPattern.getSourceFieldName()==null){
		
		}else if(abstractTokenInPattern.getSourceFieldName().equals("redefineParameter")){
			String redefineParameter=(String) currentTokenForMatch.getDeger();
			redefineParameter=redefineParameter.replaceAll("-", "_");
			matchedCommandAdd.setRedefineParameter(redefineParameter);
			matchedCommandAdd.getParameters().put("redefineParameter", matchedCommandAdd.getRedefineParameter());
		}
	}
		





	
	
	

	
	
	
	

}
