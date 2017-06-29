package tr.com.vbt.cobol.parser.basicverbs.patern;

import java.util.ArrayList;
import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.basicverbs.ElementMultiply;
import tr.com.vbt.lexer.ReservedCobolKeywords;
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
		AbstractToken astKeyword=new OzelKelimeToken("MULTIPLY", 0, 0, 0);
		patternTokenList.add(astKeyword);
		
		
		//A
		AbstractToken astSource=new KelimeToken<String>();
		astSource.setSourceFieldName("sourceNum");
		patternTokenList.add(astSource);
		
		//TO
		AbstractToken astKeyword2=new OzelKelimeToken("BY", 0, 0, 0);
		astKeyword2.setTekrarlayabilir("+");
		patternTokenList.add(astKeyword2);
		
		// B
		AbstractToken astDest=new KelimeToken<String>();
		astDest.setSourceFieldName("destNum");
		patternTokenList.add(astDest);
		
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
		
		}else if(abstractTokenInPattern.getSourceFieldName().equals("sourceNum")){
			
			matchedCommandAdd.setSourceNum((String) currentTokenForMatch.getDeger());
			
			matchedCommandAdd.getParameters().put("sourceNum", matchedCommandAdd.getSourceNum());
			
		}else if(abstractTokenInPattern.getSourceFieldName().equals("destNum")){
			matchedCommandAdd.getDestNum().add((String) currentTokenForMatch.getDeger());
			
			List<String> sourceList;
			if(matchedCommandAdd.getParameters().get("destNum")!=null){
				sourceList=(List<String>) matchedCommandAdd.getParameters().get("destNum");
			}else{
				sourceList=new ArrayList<String>();
			}
			sourceList.add((String) currentTokenForMatch.getDeger());
			matchedCommandAdd.getParameters().put("destNum", sourceList);
		}
	}


	@Override
	public String toString() {
	return "MULTIPLY PATERN";
	}


		





	
	
	

	
	
	
	

}
