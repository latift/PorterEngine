package tr.com.vbt.cobol.parser.sql.patern;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.sql.ElementBeginDeclareSectionEndExec;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.patern.AbstractPattern;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.OzelKelimeToken;

/**
 *     BEGIN DECLARE SECTION END-EXEC
 */
public class PaternBeginDeclareSectionEndExec extends AbstractPattern{

	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}
	

	public PaternBeginDeclareSectionEndExec() {
		super();
		
		//EXEC SQL
		AbstractToken astKeyword=new OzelKelimeToken("BEGIN", 0, 0, 0);
		astKeyword.setTekrarlayabilir("+");
		patternTokenList.add(astKeyword);
		
		AbstractToken astKeyword2=new OzelKelimeToken("DECLARE", 0, 0, 0);
		astKeyword2.setTekrarlayabilir("+");
		patternTokenList.add(astKeyword2);
		
		AbstractToken astKeyword3=new OzelKelimeToken("SECTION", 0, 0, 0);
		astKeyword3.setTekrarlayabilir("+");
		patternTokenList.add(astKeyword3);
		
		AbstractToken astKeyword4=new OzelKelimeToken("END-EXEC", 0, 0, 0);
		astKeyword4.setTekrarlayabilir("+");
		patternTokenList.add(astKeyword4);
		
	}
	
	@Override
	public AbstractCommand createElement(){
		ElementBeginDeclareSectionEndExec elementDisplay = new ElementBeginDeclareSectionEndExec(ReservedCobolKeywords.BEGIN_DECLARE_SECTION_END_EXEC,"PROCEDURE_DIVISION.*.BEGIN_DECLARE_SECTION_END_EXEC");
		return elementDisplay;
	}


	@Override
	public void setTokenToElement(AbstractCommand matchedCommand,
			AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {
		
	}
		





	
	
	

	
	
	
	

}
