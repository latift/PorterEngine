package tr.com.vbt.cobol.parser.sql.patern;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.sql.ElementInclude;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.patern.AbstractPattern;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.KelimeToken;
import tr.com.vbt.token.OzelKelimeToken;

/**
 *    INCLUDE STUDENT
 *
 */
public class PaternInclude extends AbstractPattern{

	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}
	

	public PaternInclude() {
		super();
		
		//INCLUDE
		AbstractToken astKeyword=new OzelKelimeToken("INCLUDE", 0, 0, 0);
		astKeyword.setTekrarlayabilir("+");
		patternTokenList.add(astKeyword);
		
		AbstractToken astSource=new KelimeToken<String>();
		astSource.setTekrarlayabilir("+");
		astSource.setSourceFieldName("tableName");
		patternTokenList.add(astSource);
	}
	
	@Override
	public AbstractCommand createElement(){
		ElementInclude elementDisplay = new ElementInclude(ReservedCobolKeywords.INCLUDE,"PROCEDURE_DIVISION.*.INCLUDE");
		return elementDisplay;
	}


	@Override
	public void setTokenToElement(AbstractCommand matchedCommand,
			AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {
		
		ElementInclude matchedCommandAdd=(ElementInclude) matchedCommand;
		
		super.setSatirNumarasi(matchedCommand,currentTokenForMatch, abstractTokenInPattern);if(abstractTokenInPattern.getSourceFieldName()==null){
		
		}else if(abstractTokenInPattern.getSourceFieldName().equals("tableName")){
			matchedCommandAdd.setTableName((String) currentTokenForMatch.getDeger());
			matchedCommandAdd.getParameters().put("tableName", matchedCommandAdd.getTableName());
		}
		
	}
		





	
	
	

	
	
	
	

}
