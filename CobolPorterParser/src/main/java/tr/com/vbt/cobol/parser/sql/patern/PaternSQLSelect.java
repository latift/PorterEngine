package tr.com.vbt.cobol.parser.sql.patern;

import java.util.ArrayList;
import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.sql.ElementSQLSelect;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.patern.AbstractPattern;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.KelimeToken;
import tr.com.vbt.token.OzelKelimeToken;

/**
 *     SELECT STUDENT-ID, STUDENT-NAME, STUDENT-ADDRESS 
 *
 */
public class PaternSQLSelect extends AbstractPattern{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//AbstractPattern asp =new PaternAdd();

	}
	

	public PaternSQLSelect() {
		super();
		
		//SELECT
		AbstractToken astKeyword=new OzelKelimeToken("SELECT", 0, 0, 0);
		astKeyword.setTekrarlayabilir("+");
		patternTokenList.add(astKeyword);
		
		
		//columname
		AbstractToken astSource=new KelimeToken<String>();
		astSource.setTekrarlayabilir("+");
		astSource.setSourceFieldName("columName");
		patternTokenList.add(astSource);
		
		
		
	}
	
	@Override
	public AbstractCommand createElement(){
		ElementSQLSelect elementDisplay = new ElementSQLSelect(ReservedCobolKeywords.SELECT,"PROCEDURE_DIVISION.*.SELECT");
		return elementDisplay;
	}


	@Override
	public void setTokenToElement(AbstractCommand matchedCommand,
			AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {
		
		ElementSQLSelect matchedCommandAdd=(ElementSQLSelect) matchedCommand;
		
		super.setSatirNumarasi(matchedCommand,currentTokenForMatch, abstractTokenInPattern);if(abstractTokenInPattern.getSourceFieldName()==null){
			
		}else if(abstractTokenInPattern.getSourceFieldName().equals("columName")){
			
			matchedCommandAdd.getColumnList().add((String) currentTokenForMatch.getDeger());
			
			List<String> columnList;
			if(matchedCommandAdd.getParameters().get("columName")!=null){
				columnList=(List<String>) matchedCommandAdd.getParameters().get("columName");
			}else{
				columnList=new ArrayList<String>();
			}
			columnList.add((String) currentTokenForMatch.getDeger());
			matchedCommandAdd.getParameters().put("columName", columnList);
			
		}
	}
		





	
	
	

	
	
	
	

}
