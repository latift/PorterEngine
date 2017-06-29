package tr.com.vbt.natural.parser.basicverbs.patern;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.natural.parser.basicverbs.ElementClosePrinter;
import tr.com.vbt.patern.AbstractPattern;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.KarakterToken;
import tr.com.vbt.token.OzelKelimeToken;
import tr.com.vbt.token.SayiToken;

/**
 *  CLOSE PRINTER(3)
 *
 */
public class PaternClosePrinter extends AbstractPattern{

	
	public PaternClosePrinter() {
		super();
		
		//ADD
		AbstractToken astKeyword=new OzelKelimeToken("CLOSE_PRINTER", 0, 0, 0);
		astKeyword.setTekrarlayabilir("+");
		astKeyword.setSourceFieldName("FIRST_COMMAND");
		patternTokenList.add(astKeyword);
		
		///( Mandatory
		AbstractToken astSource1=new KarakterToken('(', 0,0,0);
		astSource1.setOptional(true);
		patternTokenList.add(astSource1);
		
		//	*	12 Mandatory Sayi
		AbstractToken astSource2=new SayiToken<Integer>();
		astSource2.setSourceFieldName("closeNum");
		patternTokenList.add(astSource2);
		
		///) Mandatory
		AbstractToken astSource3=new KarakterToken(')', 0,0,0);
		patternTokenList.add(astSource3);
		
		
		
	}
	
	@Override
	public AbstractCommand createElement(){
		ElementClosePrinter createdElement = new ElementClosePrinter(ReservedNaturalKeywords.CLOSE_PRINTER,"GENERAL.*.CLOSE_PRINTER");
		return createdElement;
	}


	@Override
	public void setTokenToElement(AbstractCommand matchedCommand,
			AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {
		
		super.setSatirNumarasi(matchedCommand,currentTokenForMatch, abstractTokenInPattern);
		
	}
}
