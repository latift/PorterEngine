package tr.com.vbt.cobol.parser.sql.patern;

import java.util.ArrayList;
import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.sql.ElementSQLInto;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.patern.AbstractPattern;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.KelimeToken;
import tr.com.vbt.token.OzelKelimeToken;

/**
 *     INTO :WS-STUDENT-ID, :WS-STUDENT-NAME, WS-STUDENT-ADDRESS
 *
 */
public class PaternSQLInto extends AbstractPattern{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//AbstractPattern asp =new PaternAdd();

	}
	

	public PaternSQLInto() {
		super();
		
		//INTO
		AbstractToken astKeyword=new OzelKelimeToken("INTO", 0, 0, 0);
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
		ElementSQLInto elementDisplay = new ElementSQLInto(ReservedCobolKeywords.INTO,"PROCEDURE_DIVISION.*.INTO");
		return elementDisplay;
	}


	@Override
	public void setTokenToElement(AbstractCommand matchedCommand,
			AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {
		
		ElementSQLInto matchedCommandAdd=(ElementSQLInto) matchedCommand;
		
		super.setSatirNumarasi(matchedCommand,currentTokenForMatch, abstractTokenInPattern);if(abstractTokenInPattern.getSourceFieldName()==null){
			
		}else if(abstractTokenInPattern.getSourceFieldName().equals("columName")){
			
			matchedCommandAdd.getIntoList().add((String) currentTokenForMatch.getDeger());
			
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
