package tr.com.vbt.natural.parser.basicverbs.patern;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.natural.parser.basicverbs.ElementDelete;
import tr.com.vbt.patern.AbstractPattern;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.OzelKelimeToken;

/**
 *    5794   DELETE 
 *
 */
public class PaternDelete extends AbstractPattern{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//AbstractPattern asp =new PaternAdd();

	}
	

	public PaternDelete() {
		super();
		
		//STORE
		AbstractToken astSource=new OzelKelimeToken("DELETE", 0, 0, 0);
		astSource.setTekrarlayabilir("+");
		astSource.setSourceFieldName("FIRST_COMMAND");
		patternTokenList.add(astSource);
		
	/*	//(
		AbstractToken ast2=new KarakterToken('(', 0, 1, 0, false,false);
		ast2.setOptional(true);
		ast2.setSourceFieldName("PARANTEZ_OPEN");
		patternTokenList.add(ast2);
		
		AbstractToken recNumber=new SayiToken<>("", 0, 0, 0);
		recNumber.setOptional(true);
		recNumber.setSourceFieldName("RECNUMBER");
		patternTokenList.add(recNumber);
		
		//)
		AbstractToken ast3=new KarakterToken(')', 0, 1, 0, false,false);
		ast3.setOptional(true);
		ast3.setSourceFieldName("PARANTEZ_CLOSE");
		patternTokenList.add(ast3);*/
		
	}
	
	@Override
	public AbstractCommand createElement(){
		ElementDelete elementDisplay = new ElementDelete(ReservedNaturalKeywords.DELETE,"GENERAL.*.DELETE");
		return elementDisplay;
	}


	@Override
	public void setTokenToElement(AbstractCommand matchedCommand,
			AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {
		
		ElementDelete matchedCommandAdd=(ElementDelete) matchedCommand;
		super.setSatirNumarasi(matchedCommand,currentTokenForMatch, abstractTokenInPattern);

		if(abstractTokenInPattern.getSourceFieldName()==null){
			
		}else if(abstractTokenInPattern.getSourceFieldName().equals("RECNUMBER")){
			int recNumber=(int) currentTokenForMatch.getDeger();
			matchedCommandAdd.setRecNumber(recNumber);
			matchedCommandAdd.getParameters().put("RECNUMBER", matchedCommandAdd.getRecNumber());
		}
	}
		





	
	
	

	
	
	
	

}
