package tr.com.vbt.natural.parser.general.patern;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.natural.parser.general.ElementUpdate;
import tr.com.vbt.patern.AbstractPattern;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.OzelKelimeToken;

/**
 *   UPDATE (2000)
 *
 */
public class PaternUpdate extends AbstractPattern{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//AbstractPattern asp =new PaternAdd();

	}
	

	public PaternUpdate() {
		super();
		
		//GLOBAL USING
		AbstractToken astKeyword=new OzelKelimeToken("UPDATE", 0, 0, 0);
		astKeyword.setSourceFieldName("FIRST_COMMAND");
		astKeyword.setTekrarlayabilir("+");
		patternTokenList.add(astKeyword);
		
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
		ElementUpdate createdElement = new ElementUpdate(ReservedNaturalKeywords.UPDATE,"GENERAL.*.UPDATE");
		return createdElement;
	}


	@Override
	public void setTokenToElement(AbstractCommand matchedCommand,
			AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {
		
		ElementUpdate matchedCommandAdd=(ElementUpdate) matchedCommand;
		
		super.setSatirNumarasi(matchedCommand,currentTokenForMatch, abstractTokenInPattern);
		
		if(abstractTokenInPattern.getSourceFieldName()==null){
		
		}else if(abstractTokenInPattern.getSourceFieldName().equals("RECNUMBER")){
			int recNumber=(int) currentTokenForMatch.getDeger();
			matchedCommandAdd.setRecNumber(recNumber);
			matchedCommandAdd.getParameters().put("RECNUMBER", matchedCommandAdd.getRecNumber());
		}
		
	}


		





	
	
	

	
	
	
	

}
