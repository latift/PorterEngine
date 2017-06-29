package tr.com.vbt.natural.parser.basicverbs.patern;

import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.natural.parser.basicverbs.ElementDivide;
import tr.com.vbt.patern.AbstractPattern;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.GenelTipToken;
import tr.com.vbt.token.KelimeToken;
import tr.com.vbt.token.OzelKelimeToken;

/**
 *  DIVIDE A INTO B
 *  DIVIDE A INTO B GIVING TOPLAM_SAYFA REMAINDER KALAN
 */
public class PaternDivide extends AbstractPattern{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//AbstractPattern asp =new PaternAdd();

	}
	

	public PaternDivide() {
		super();
		
		
		
		//DIVIDE
		AbstractToken astKeyword=new OzelKelimeToken("DIVIDE", 0, 0, 0);
		astKeyword.setSourceFieldName("FIRST_COMMAND");
		patternTokenList.add(astKeyword);
		
		//A
		AbstractToken astSource=new GenelTipToken<>();
		astSource.setSourceFieldName("bolen");
		patternTokenList.add(astSource);
		
		//INTTO
		AbstractToken astKeyword2=new OzelKelimeToken("INTO", 0, 0, 0);
		patternTokenList.add(astKeyword2);
		
		// B
		AbstractToken astDest=new GenelTipToken();
		astDest.setTekrarlayabilir("+");
		astDest.setSourceFieldName("bolunen");
		patternTokenList.add(astDest);
		
		//GIVING
		AbstractToken astKeyword3=new OzelKelimeToken("GIVING", 0, 0, 0);
		astKeyword3.setOptional(true);
		patternTokenList.add(astKeyword3);
		
		// TOPLAM_SAYFA
		AbstractToken astKeyword4=new KelimeToken<String>();
		astKeyword4.setTekrarlayabilir("+");
		astKeyword4.setSourceFieldName("sonuc");
		astKeyword4.setOptional(true);
		patternTokenList.add(astKeyword4);
	
		//REMAINDER
		AbstractToken astKeyword5=new OzelKelimeToken("REMAINDER", 0, 0, 0);
		astKeyword5.setOptional(true);
		patternTokenList.add(astKeyword5);
		
		// KALAN
		AbstractToken astKeyword6=new KelimeToken<String>();
		astKeyword6.setTekrarlayabilir("+");
		astKeyword6.setOptional(true);
		astKeyword6.setSourceFieldName("kalan");
		patternTokenList.add(astKeyword6);
		
	}
	
	@Override
	public AbstractCommand createElement(){
		ElementDivide createdElement = new ElementDivide(ReservedCobolKeywords.DIVIDE,"PROCEDURE_DIVISION.*.DIVIDE");
		return createdElement;
	}


	@Override
	public void setTokenToElement(AbstractCommand matchedCommand,
			AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {
		
		ElementDivide matchedCommandAdd=(ElementDivide) matchedCommand;
		
		super.setSatirNumarasi(matchedCommand,currentTokenForMatch, abstractTokenInPattern);if(abstractTokenInPattern.getSourceFieldName()==null){
		
		}else if(abstractTokenInPattern.getSourceFieldName().equals("bolunen")){
			
			matchedCommandAdd.setBolunen(currentTokenForMatch);
			matchedCommandAdd.getParameters().put("bolunen", matchedCommandAdd.getBolunen());
			
		}else if(abstractTokenInPattern.getSourceFieldName().equals("bolen")){
			
			matchedCommandAdd.setBolen(currentTokenForMatch);
			matchedCommandAdd.getParameters().put("bolen", matchedCommandAdd.getBolen());
			
		}
		else if(abstractTokenInPattern.getSourceFieldName().equals("sonuc")){
			matchedCommandAdd.setSonuc(currentTokenForMatch);
			matchedCommandAdd.getParameters().put("sonuc", matchedCommandAdd.getSonuc());
		}
		else if(abstractTokenInPattern.getSourceFieldName().equals("kalan")){
			matchedCommandAdd.setKalan(currentTokenForMatch);
			matchedCommandAdd.getParameters().put("kalan", matchedCommandAdd.getKalan());
		}
	}


	@Override
	public String toString() {
		return "DIVIDE PATERN";
	}

	public AbstractCommand getmatchedCommand(List<AbstractToken> tokenListesi, int offset){
		return super.getmatchedCommand(tokenListesi, offset);
	}
		





	
	
	

	
	
	
	

}
