package tr.com.vbt.natural.parser.database.enders;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.patern.AbstractPattern;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.OzelKelimeToken;

/**
//FOR I = 1 TO SECILENSAY	
// FOR J=1 TO IEKSI1
//FOR I:=1 TO SECILENSAY 
 *
 */
public class PaternEndSelect extends AbstractPattern{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//AbstractPattern asp =new PaternAdd();

	}
	

	public PaternEndSelect() {
		super();
		
		//FOR
		AbstractToken astKeyword=new OzelKelimeToken("END-SELECT", 0, 0, 0);
		astKeyword.setSourceFieldName("FIRST_COMMAND");
		patternTokenList.add(astKeyword);
		
		
	}
	
	@Override
	public AbstractCommand createElement(){
		ElementEndSelect elementRepeat = new ElementEndSelect(ReservedNaturalKeywords.END_SELECT,"DATABASE.*.END_SELECT");
		return elementRepeat;
	}


	@Override
	public void setTokenToElement(AbstractCommand matchedCommand,
			AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {
		
		ElementEndSelect matchedCommandAdd=(ElementEndSelect) matchedCommand;
		
		super.setSatirNumarasi(matchedCommand,currentTokenForMatch, abstractTokenInPattern);
	}
		





	
	
	

	
	
	
	

}
