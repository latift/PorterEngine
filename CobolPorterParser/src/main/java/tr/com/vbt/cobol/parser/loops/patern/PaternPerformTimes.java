package tr.com.vbt.cobol.parser.loops.patern;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.loops.ElementPerformTimes;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.patern.AbstractPattern;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.KelimeToken;
import tr.com.vbt.token.OzelKelimeToken;
import tr.com.vbt.token.SayiToken;

/**
 * PERFORM B-PARA 3 TIMES.
 */
public class PaternPerformTimes extends AbstractPattern{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//AbstractPattern asp =new PaternAdd();

	}
	

	public PaternPerformTimes() {
		super();
		
		//PERFORM
		AbstractToken astKeyword=new OzelKelimeToken("PERFORM", 0, 0, 0);
		astKeyword.setTekrarlayabilir("+");
		patternTokenList.add(astKeyword);
		
		
		//A
		AbstractToken astSource=new KelimeToken<String>();
		astSource.setTekrarlayabilir("+");
		astSource.setSourceFieldName("paragraghName");
		patternTokenList.add(astSource);
		
		// C
		AbstractToken astDest=new SayiToken<Integer>();
		astDest.setTekrarlayabilir("+");
		astDest.setSourceFieldName("runCount");
		patternTokenList.add(astDest);
				
				
		//TIMES
		AbstractToken astKeyword2=new OzelKelimeToken("TIMES", 0, 0, 0);
		astKeyword2.setTekrarlayabilir("+");
		patternTokenList.add(astKeyword2);
		
		
		
	}
	
	@Override
	public AbstractCommand createElement(){
		ElementPerformTimes createdElement = new ElementPerformTimes(ReservedCobolKeywords.PERFORM_TIMES,"PROCEDURE_DIVISION.*.PERFORM_TIMES");
		return createdElement;
	}


	@Override
	public void setTokenToElement(AbstractCommand matchedCommand,
			AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {
		
		ElementPerformTimes matchedCommandAdd=(ElementPerformTimes) matchedCommand;
		
		super.setSatirNumarasi(matchedCommand,currentTokenForMatch, abstractTokenInPattern);if(abstractTokenInPattern.getSourceFieldName()==null){
		
		}else if(abstractTokenInPattern.getSourceFieldName().equals("paragraghName")){
			String paragraphname=(String) currentTokenForMatch.getDeger();
			paragraphname=paragraphname.replaceAll("-", "_");
			matchedCommandAdd.setParagraghName(paragraphname);
			matchedCommandAdd.getParameters().put("paragraghName", matchedCommandAdd.getParagraghName());
			
			
		}else if(abstractTokenInPattern.getSourceFieldName().equals("runCount")){
			matchedCommandAdd.setRunCount(Double.toString((double) currentTokenForMatch.getDeger()));
			matchedCommandAdd.getParameters().put("runCount", matchedCommandAdd.getRunCount());
		}
	}
		





	
	
	

	
	
	
	

}
