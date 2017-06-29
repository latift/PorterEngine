package tr.com.vbt.cobol.parser.division.patern;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.division.ElementProcedureDivision;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.patern.AbstractPattern;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.OzelKelimeToken;

/**
 *  PROCEDURE DIVISION
 *
 */
public class PaternProcedureDivision extends AbstractPattern{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//AbstractPattern asp =new PaternAdd();

	}
	

	public PaternProcedureDivision() {
		super();
		
		//ADD
		AbstractToken astKeyword=new OzelKelimeToken("PROCEDURE_DIVISION", 0, 0, 0);
		astKeyword.setTekrarlayabilir("+");
		patternTokenList.add(astKeyword);
		
		
	}
	
	@Override
	public AbstractCommand createElement(){
		ElementProcedureDivision createdElement = new ElementProcedureDivision(ReservedCobolKeywords.PROCEDURE_DIVISION,"PROCEDURE_DIVISION");
		return createdElement;
	}
	
	


	@Override
	public void setTokenToElement(AbstractCommand matchedCommand,
			AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {
		
	}
		





	
	
	

	
	
	
	

}
