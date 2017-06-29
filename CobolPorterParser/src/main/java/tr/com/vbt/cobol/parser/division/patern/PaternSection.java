package tr.com.vbt.cobol.parser.division.patern;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.division.ElementSection;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.patern.AbstractPattern;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.KelimeToken;
import tr.com.vbt.token.OzelKelimeToken;

/**
 * S00-ANA-PROGRAM SECTION. 
 *
 */
public class PaternSection extends AbstractPattern{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//AbstractPattern asp =new PaternAdd();

	}
	

	public PaternSection() {
		super();
		
		//PARAGRAPH
		AbstractToken astSource=new KelimeToken<String>();
		astSource.setTekrarlayabilir("+");
		astSource.setSourceFieldName("sectionName");
		patternTokenList.add(astSource);
		
		AbstractToken astKeyword=new OzelKelimeToken("SECTION", 0, 0, 0);
		astKeyword.setTekrarlayabilir("+");
		patternTokenList.add(astKeyword);
		
		
		
		
	}
	
	@Override
	public AbstractCommand createElement(){
		AbstractCommand elementParagraph = new ElementSection(ReservedCobolKeywords.SECTION,"PROCEDURE_DIVISION.SECTION" );
		return elementParagraph;
	}


	@Override
	public void setTokenToElement(AbstractCommand matchedCommand,
			AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {
		
		ElementSection elementParagraph=(ElementSection) matchedCommand;
		
		super.setSatirNumarasi(matchedCommand,currentTokenForMatch, abstractTokenInPattern);if(abstractTokenInPattern.getSourceFieldName()==null){
		
		}else if(abstractTokenInPattern.getSourceFieldName().equals("sectionName")){
			String paragraphname=(String) currentTokenForMatch.getDeger();
			paragraphname=paragraphname.replaceAll("-", "_");
			elementParagraph.setSectionName(paragraphname);
			elementParagraph.getParameters().put("sectionName", elementParagraph.getSectionName());
		}
	}
		





	
	
	

	
	
	
	

}
