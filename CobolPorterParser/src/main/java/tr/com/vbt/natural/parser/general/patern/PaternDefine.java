package tr.com.vbt.natural.parser.general.patern;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.natural.parser.general.ElementSubroutine;
import tr.com.vbt.patern.AbstractPattern;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.KelimeToken;
import tr.com.vbt.token.OzelKelimeToken;

/**
 *DEFINE SUBROUTINE MARK-SPECIAL-EMPLOYEES
 *
 */
public class PaternDefine extends AbstractPattern{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//AbstractPattern asp =new PaternAdd();

	}
	

	public PaternDefine() {
		super();
		
		//PARAGRAPH
		AbstractToken astKeyword=new OzelKelimeToken("DEFINE", 0, 0, 0);
		astKeyword.setSourceFieldName("FIRST_COMMAND");
		astKeyword.setTekrarlayabilir("+");
		patternTokenList.add(astKeyword);
		
		
		//A
		AbstractToken astSource=new KelimeToken<String>();
		astSource.setTekrarlayabilir("+");
		astSource.setSourceFieldName("subroutineName");
		patternTokenList.add(astSource);
		
	}
	
	@Override
	public AbstractCommand createElement(){
		AbstractCommand elementParagraph = new ElementSubroutine(ReservedNaturalKeywords.SUBROUTINE,"GENERAL.SUBROUTINE" );
		return elementParagraph;
	}


	@Override
	public void setTokenToElement(AbstractCommand matchedCommand,
			AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {
		
		ElementSubroutine elementParagraph=(		ElementSubroutine) matchedCommand;
		
		super.setSatirNumarasi(matchedCommand,currentTokenForMatch, abstractTokenInPattern);if(abstractTokenInPattern.getSourceFieldName()==null){
		
		}else if(abstractTokenInPattern.getSourceFieldName().equals("subroutineName")){
			String subroutineName=(String) currentTokenForMatch.getDeger();
			subroutineName=subroutineName.replaceAll("-", "_");
			elementParagraph.setSubroutineName(subroutineName);
			elementParagraph.getParameters().put("subroutineName", elementParagraph.getSubroutineName());
		}
	}
		





	
	
	

	
	
	
	

}
