package tr.com.vbt.natural.parser.basicverbs.patern;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.natural.parser.basicverbs.ElementAtTopOfPage;
import tr.com.vbt.patern.AbstractPattern;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.KarakterToken;
import tr.com.vbt.token.OzelKelimeToken;
import tr.com.vbt.token.SayiToken;

/**
 *  AT TOP OF PAGE (3)   //(3)  optional
 *
 */
public class PaternAtTopOfPage extends AbstractPattern{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//AbstractPattern asp =new PaternAdd();

	}
	

	public PaternAtTopOfPage() {
		super();
		
		//AT TOP OF PAGE
		AbstractToken astKeyword=new OzelKelimeToken("AT_TOP_OF_PAGE", 0, 0, 0);
		astKeyword.setSourceFieldName("FIRST_COMMAND");
		astKeyword.setTekrarlayabilir("+");
		patternTokenList.add(astKeyword);
		
		///( Mandatory
		AbstractToken astSource5=new KarakterToken('(', 0,0,0);
		astSource5.setOptional(true);
		patternTokenList.add(astSource5);
		
		//	*	12 Mandatory Sayi
		AbstractToken astSource6=new SayiToken<Integer>();
		astSource6.setSourceFieldName("printNumber");
		astSource6.setOptional(true);
		patternTokenList.add(astSource6);
		
		///) Mandatory
		AbstractToken astSource7=new KarakterToken(')', 0,0,0);
		astSource7.setOptional(true);
		patternTokenList.add(astSource7);
	}
	
	@Override
	public AbstractCommand createElement(){
		ElementAtTopOfPage createdElement = new ElementAtTopOfPage(ReservedNaturalKeywords.AT_TOP_OF_PAGE,"GENERAL.*.AT_TOP_OF_PAGE");
		return createdElement;
	}


	@Override
	public void setTokenToElement(AbstractCommand matchedCommand,
			AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {
		
		ElementAtTopOfPage matchedCommandAdd=(ElementAtTopOfPage) matchedCommand;
		
		super.setSatirNumarasi(matchedCommand,currentTokenForMatch, abstractTokenInPattern);if(abstractTokenInPattern.getSourceFieldName()==null){
		
		}else if(abstractTokenInPattern.getSourceFieldName().equals("printNumber")){ 
				matchedCommandAdd.setPrintNumber((int) currentTokenForMatch.getDeger());
				matchedCommandAdd.getParameters().put("printNumber", matchedCommandAdd.getPrintNumber());
		}
	}		
		


	@Override
	public String toString() {
	return "AT_TOP_OF_PAGE";
	}


		





	
	
	

	
	
	
	

}
