package tr.com.vbt.cobol.parser.division.patern;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.division.ElementParagraph;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.patern.AbstractPattern;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.KelimeToken;
import tr.com.vbt.token.OzelKelimeToken;

/**
 * PARAGRAPH B-PARA
 *
 */
public class PaternParagraph extends AbstractPattern{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//AbstractPattern asp =new PaternAdd();

	}
	

	public PaternParagraph() {
		super();
		
		//PARAGRAPH
		AbstractToken astKeyword=new OzelKelimeToken("PARAGRAPH", 0, 0, 0);
		astKeyword.setTekrarlayabilir("+");
		patternTokenList.add(astKeyword);
		
		
		//A
		AbstractToken astSource=new KelimeToken<String>();
		astSource.setTekrarlayabilir("+");
		astSource.setSourceFieldName("paragraphName");
		patternTokenList.add(astSource);
		
	}
	
	@Override
	public AbstractCommand createElement(){
		AbstractCommand elementParagraph = new ElementParagraph(ReservedCobolKeywords.PARAGRAPH,"PROCEDURE_DIVISION.PARAGRAPH" );
		return elementParagraph;
	}


	@Override
	public void setTokenToElement(AbstractCommand matchedCommand,
			AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {
		
		ElementParagraph elementParagraph=(ElementParagraph) matchedCommand;
		
		super.setSatirNumarasi(matchedCommand,currentTokenForMatch, abstractTokenInPattern);if(abstractTokenInPattern.getSourceFieldName()==null){
		
		}else if(abstractTokenInPattern.getSourceFieldName().equals("paragraphName")){
			String paragraphname=(String) currentTokenForMatch.getDeger();
			paragraphname=paragraphname.replaceAll("-", "_");
			elementParagraph.setParagraphName(paragraphname);
			elementParagraph.getParameters().put("paragraphName", elementParagraph.getParagraphName());
		}
	}
		





	
	
	

	
	
	
	

}
