package tr.com.vbt.cobol.parser.basicverbs.patern;

import org.apache.log4j.Logger;


import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.basicverbs.ElementExitSection;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.patern.AbstractPattern;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.KelimeToken;
import tr.com.vbt.token.OzelKelimeToken;

/**
 *     S00-ANA-PROGRAM-EX. EXIT
 *
 */
public class PaternExitSection extends AbstractPattern{

	final static Logger logger = Logger.getLogger(PaternExitSection.class);
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//AbstractPattern asp =new PaternAdd();

	}
	

	public PaternExitSection() {
		super();
		//paragrahpName
		AbstractToken astSource=new KelimeToken<String>();
		astSource.setTekrarlayabilir("+");
		astSource.setSourceFieldName("sectionName");
		patternTokenList.add(astSource);
		
		//DISPLAY
		AbstractToken astKeyword=new OzelKelimeToken(ReservedCobolKeywords.EXIT, 0, 0, 0);
		astKeyword.setTekrarlayabilir("+");
		patternTokenList.add(astKeyword);
		
	}
	
	@Override
	public AbstractCommand createElement(){
		ElementExitSection elementDisplay = new ElementExitSection(ReservedCobolKeywords.EXIT_SECTION,"PROCEDURE_DIVISION.*.EXIT_SECTION");
		return elementDisplay;
	}


	@Override
	public void setTokenToElement(AbstractCommand matchedCommand,
			AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {
		
		ElementExitSection matchedCommandAdd=(ElementExitSection) matchedCommand;
		
		super.setSatirNumarasi(matchedCommand,currentTokenForMatch, abstractTokenInPattern);if(abstractTokenInPattern.getSourceFieldName()==null){
		
		}else if(abstractTokenInPattern.getSourceFieldName().equals("sectionName")){
			logger.info("currentTokenForMatch.getDeger():"+ currentTokenForMatch.getDeger());
			try {
				matchedCommandAdd.setSectionName((String) currentTokenForMatch.getDeger());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			matchedCommandAdd.getParameters().put("sectionName", matchedCommandAdd.getSectionName());
		}
	}
		





	
	
	

	
	
	
	

}
