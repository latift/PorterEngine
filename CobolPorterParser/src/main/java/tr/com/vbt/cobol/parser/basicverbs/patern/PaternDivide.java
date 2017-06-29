package tr.com.vbt.cobol.parser.basicverbs.patern;

import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.basicverbs.ElementDivide;
import tr.com.vbt.lexer.ReservedCobolKeywords;
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
		patternTokenList.add(astKeyword);
		
		//A
		AbstractToken astSource=new GenelTipToken<>();
		astSource.setSourceFieldName("sourceNum");
		patternTokenList.add(astSource);
		
		//INTTO
		AbstractToken astKeyword2=new OzelKelimeToken("INTO", 0, 0, 0);
		patternTokenList.add(astKeyword2);
		
		// B
		AbstractToken astDest=new GenelTipToken();
		astDest.setTekrarlayabilir("+");
		astDest.setSourceFieldName("destNum");
		patternTokenList.add(astDest);
		
		//GIVING
		AbstractToken astKeyword3=new OzelKelimeToken("GIVING", 0, 0, 0);
		astKeyword3.setOptional(true);
		patternTokenList.add(astKeyword3);
		
		// TOPLAM_SAYFA
		AbstractToken astKeyword4=new KelimeToken<String>();
		astKeyword4.setTekrarlayabilir("+");
		astKeyword4.setSourceFieldName("giving");
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
		astKeyword6.setSourceFieldName("remainder");
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
		
		}else if(abstractTokenInPattern.getSourceFieldName().equals("sourceNum")){
			
			Object source=currentTokenForMatch.getDeger();
			if(source instanceof String){
				matchedCommandAdd.setSourceNum((String) source);
			}else if(source instanceof Double){
				double sourceAsDbl=(double) source;
				matchedCommandAdd.setSourceNum(Double.toString(sourceAsDbl));
			}
			matchedCommandAdd.getParameters().put("sourceNum", matchedCommandAdd.getSourceNum());
			
		}else if(abstractTokenInPattern.getSourceFieldName().equals("destNum")){
			
			Object destNum=currentTokenForMatch.getDeger();
			if(destNum instanceof String){
				matchedCommandAdd.setSourceNum((String) destNum);
			}else if(destNum instanceof Double){
				double sourceAsDbl=(double) destNum;
				matchedCommandAdd.setSourceNum(Double.toString(sourceAsDbl));
			}
			matchedCommandAdd.getParameters().put("destNum", matchedCommandAdd.getSourceNum());
			
		}
		else if(abstractTokenInPattern.getSourceFieldName().equals("giving")){
			matchedCommandAdd.setGiving((String) currentTokenForMatch.getDeger());
			matchedCommandAdd.getParameters().put("giving", matchedCommandAdd.getGiving());
		}
		else if(abstractTokenInPattern.getSourceFieldName().equals("remainder")){
			matchedCommandAdd.setDestNum((String) currentTokenForMatch.getDeger());
			matchedCommandAdd.getParameters().put("remainder", matchedCommandAdd.getDestNum());
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
