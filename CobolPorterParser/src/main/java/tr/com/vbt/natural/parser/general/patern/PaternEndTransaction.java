package tr.com.vbt.natural.parser.general.patern;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.natural.parser.general.ElementEndOfTransaction;
import tr.com.vbt.patern.AbstractPattern;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.OzelKelimeToken;

/**
 * //BACKOUT TRANSACTION 
 *
 */
public class PaternEndTransaction extends AbstractPattern{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//AbstractPattern asp =new PaternAdd();

	}
	

	public PaternEndTransaction() {
		super();
		
		//GLOBAL USING
		AbstractToken astKeyword=new OzelKelimeToken("END_TRANSACTION", 0, 0, 0);
		astKeyword.setSourceFieldName("FIRST_COMMAND");
		astKeyword.setTekrarlayabilir("+");
		patternTokenList.add(astKeyword);
		
	}
	
	@Override
	public AbstractCommand createElement(){
		ElementEndOfTransaction createdElement = new ElementEndOfTransaction(ReservedNaturalKeywords.END_TRANSACTION,"END_TRANSACTION");
		return createdElement;
	}


	@Override
	public void setTokenToElement(AbstractCommand matchedCommand,
			AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {
	
		super.setSatirNumarasi(matchedCommand,currentTokenForMatch, abstractTokenInPattern);
	}


		





	
	
	

	
	
	
	

}
