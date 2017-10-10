package tr.com.vbt.natural.parser.basicverbs.patern;

import java.util.ArrayList;
import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.natural.parser.basicverbs.ElementMultiply;
import tr.com.vbt.patern.AbstractPattern;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.KelimeToken;
import tr.com.vbt.token.OzelKelimeToken;

/**
 * MULTIPLY A BY B C
 *
 */
/*
 * In syntax-1, A and B are multipled and the result is stored in B (B=A*B). A and C are multipled and the result is stored in C (C=A*C).
 * 
 * private String sourceNum=new String();
	
	private List<String> destNum=new ArrayList<String>();
	
 */
//4868 MULTIPLY ROUNDED ALDOVIZ BY CAPRAZ_KUR GIVING SATDOVIZ

public class PaternMultiply extends AbstractPattern{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//AbstractPattern asp =new PaternAdd();

	}
	

	public PaternMultiply() {
		super();
		
		
		
		//MULTIPLY
		AbstractToken astKeyword=new OzelKelimeToken("MULTIPLY_ROUNDED", 0, 0, 0);
		patternTokenList.add(astKeyword);
		
		
		//ALDOVIZ
		AbstractToken astSource=new KelimeToken<String>();
		astSource.setSourceFieldName("multiplyNum");
		patternTokenList.add(astSource);
		
		//BY
		AbstractToken astKeyword2=new OzelKelimeToken("BY", 0, 0, 0);
		patternTokenList.add(astKeyword2);
		
		// CAPRAZ_KUR
		AbstractToken astDest=new KelimeToken<String>();
		astDest.setSourceFieldName("multiplierNum");
		patternTokenList.add(astDest);
		
		
		//GIVING
		AbstractToken astKeyword3=new OzelKelimeToken("GIVING", 0, 0, 0);
		astKeyword3.setOptional(true);
		patternTokenList.add(astKeyword3);
		
		// SATDOVIZ
		AbstractToken astKeyword4=new KelimeToken<String>();
		astKeyword4.setTekrarlayabilir("+");
		astKeyword4.setSourceFieldName("resultNum");
		astKeyword4.setOptional(true);
		patternTokenList.add(astKeyword4);
		
	}
	
	@Override
	public AbstractCommand createElement(){
		ElementMultiply createdElement = new ElementMultiply(ReservedCobolKeywords.MULTIPLY,"PROCEDURE_DIVISION.*.MULTIPLY");
		return createdElement;
	}


	@Override
	public void setTokenToElement(AbstractCommand matchedCommand,
			AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {
		
		ElementMultiply matchedCommandAdd=(ElementMultiply) matchedCommand;
		
		super.setSatirNumarasi(matchedCommand,currentTokenForMatch, abstractTokenInPattern);if(abstractTokenInPattern.getSourceFieldName()==null){
		
		}else if(abstractTokenInPattern.getSourceFieldName().equals("multiplyNum")){
			
			matchedCommandAdd.setMultiplyNum(currentTokenForMatch);
			
			matchedCommandAdd.getParameters().put("multiplyNum", matchedCommandAdd.getMultiplyNum());
			
		}else if(abstractTokenInPattern.getSourceFieldName().equals("multiplierNum")){
			
			matchedCommandAdd.setMultiplierNum(currentTokenForMatch);
			
			matchedCommandAdd.getParameters().put("multiplierNum", matchedCommandAdd.getMultiplierNum());
			
		}else if(abstractTokenInPattern.getSourceFieldName().equals("resultNum")){
			
			matchedCommandAdd.setResultNum(currentTokenForMatch);
			
			matchedCommandAdd.getParameters().put("resultNum", matchedCommandAdd.getResultNum());
			
		}
	}


	@Override
	public String toString() {
		return "MULTIPLY PATERN";
	}


		





	
	
	

	
	
	
	

}
