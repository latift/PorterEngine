package tr.com.vbt.cobol.parser.sql.patern;

import java.util.ArrayList;
import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.sql.ElementSQLFrom;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.patern.AbstractPattern;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.KelimeToken;
import tr.com.vbt.token.OzelKelimeToken;

/**
 *     FROM STUDENT
 *
 */
public class PaternSQLFrom extends AbstractPattern{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//AbstractPattern asp =new PaternAdd();

	}
	
	public PaternSQLFrom() {
		super();
		
		//DISPLAY
		AbstractToken astKeyword=new OzelKelimeToken("FROM", 0, 0, 0);
		astKeyword.setTekrarlayabilir("+");
		patternTokenList.add(astKeyword);
		
		
		//dataToDisplay
		AbstractToken astSource=new KelimeToken<String>();
		astSource.setTekrarlayabilir("+");
		astSource.setSourceFieldName("fromTableList");
		patternTokenList.add(astSource);
		
		
	}
	
	@Override
	public AbstractCommand createElement(){
		ElementSQLFrom elementDisplay = new ElementSQLFrom(ReservedCobolKeywords.FROM,"PROCEDURE_DIVISION.*.FROM");
		return elementDisplay;
	}


	@Override
	public void setTokenToElement(AbstractCommand matchedCommand,
			AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {
		
		ElementSQLFrom matchedCommandAdd=(ElementSQLFrom) matchedCommand;
		
		super.setSatirNumarasi(matchedCommand,currentTokenForMatch, abstractTokenInPattern);if(abstractTokenInPattern.getSourceFieldName()==null){
			
		}else if(abstractTokenInPattern.getSourceFieldName().equals("fromTableList")){
			
			matchedCommandAdd.getFromTableList().add((String) currentTokenForMatch.getDeger());
			
			List<String> columnList;
			if(matchedCommandAdd.getParameters().get("fromTableList")!=null){
				columnList=(List<String>) matchedCommandAdd.getParameters().get("fromTableList");
			}else{
				columnList=new ArrayList<String>();
			}
			columnList.add((String) currentTokenForMatch.getDeger());
			matchedCommandAdd.getParameters().put("fromTableList", columnList);
			
		}
	}
		





	
	
	

	
	
	
	

}
