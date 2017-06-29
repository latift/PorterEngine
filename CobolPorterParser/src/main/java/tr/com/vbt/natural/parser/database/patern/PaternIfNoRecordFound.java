package tr.com.vbt.natural.parser.database.patern;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.natural.parser.database.ElementIfNoRecords;
import tr.com.vbt.patern.AbstractPattern;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.OzelKelimeToken;

/**
*  IF NO RECORDS 
	
 *IF  Uzunluk:0 Satir No:58 Tipi:OzelKelime
NO  Uzunluk:0 Satir No:58 Tipi:OzelKelime
RECORDS  Uzunluk:0 Satir No:58 Tipi:OzelKelime
	
	

 */
public class PaternIfNoRecordFound extends AbstractPattern {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// AbstractPattern asp =new PaternAdd();

	}

	public PaternIfNoRecordFound() {
		super();

		// FIND
		
		AbstractToken astKeyword = new OzelKelimeToken("IF_NO_RECORD_FOUND", 0, 0, 0);
		astKeyword.setSourceFieldName("FIRST_COMMAND");
		astKeyword.setTekrarlayabilir("+");
		patternTokenList.add(astKeyword);
		

		
		
	}

	@Override
	public AbstractCommand createElement() {
		ElementIfNoRecords createdElement = new ElementIfNoRecords(
				ReservedNaturalKeywords.IF_NO_RECORDS, "DATABASE.*.IF_NO_RECORDS");
		return createdElement;
	}

	@Override
	public void setTokenToElement(AbstractCommand matchedCommand,
			AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {
		super.setSatirNumarasi(matchedCommand,currentTokenForMatch, abstractTokenInPattern);
	
	}

}
