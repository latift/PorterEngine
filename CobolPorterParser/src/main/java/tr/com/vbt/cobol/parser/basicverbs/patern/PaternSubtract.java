package tr.com.vbt.cobol.parser.basicverbs.patern;

import java.util.ArrayList;
import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.basicverbs.ElementSubtract;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.patern.AbstractPattern;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.KelimeToken;
import tr.com.vbt.token.OzelKelimeToken;

/**
 *  Subtract Verb
 *  SUBTRACT A B FROM C D
 *  
 *  In syntax-1, A and B are added and subtracted from C. The Result is stored in C (C=C-(A+B)). 
 *  A and B are added and subtracted from D. The result is stored in D (D=D-(A+B)).
 *
 */
public class PaternSubtract extends AbstractPattern{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//AbstractPattern asp =new PaternAdd();

	}
	

	public PaternSubtract() {
		super();
		
		
		
		//SUBTRACT
		AbstractToken astKeyword=new OzelKelimeToken("SUBTRACT", 0, 0, 0);
		patternTokenList.add(astKeyword);
		
		//A
		AbstractToken astSource=new KelimeToken<String>();
		astSource.setSourceFieldName("sourceNum");
		patternTokenList.add(astSource);
		
		//FROM
		AbstractToken astKeyword2=new OzelKelimeToken("FROM", 0, 0, 0);
		patternTokenList.add(astKeyword2);
		
		// B
		AbstractToken astDest=new KelimeToken<String>();
		astDest.setTekrarlayabilir("+");
		astDest.setSourceFieldName("destNum");
		patternTokenList.add(astDest);
		
		
	}
	
	@Override
	public AbstractCommand createElement(){
		ElementSubtract createdElement = new ElementSubtract(ReservedCobolKeywords.SUBTRACT,"PROCEDURE_DIVISION.*.SUBTRACT");
		return createdElement;
	}


	@Override
	public void setTokenToElement(AbstractCommand matchedCommand,
			AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {
		
		ElementSubtract matchedCommandAdd=(ElementSubtract) matchedCommand;
		
		super.setSatirNumarasi(matchedCommand,currentTokenForMatch, abstractTokenInPattern);if(abstractTokenInPattern.getSourceFieldName()==null){
		
		}else if(abstractTokenInPattern.getSourceFieldName().equals("sourceNum")){
			
			matchedCommandAdd.getSourceNum().add((String) currentTokenForMatch.getDeger());
			
			List<String> sourceList;
			if(matchedCommandAdd.getParameters().get("sourceNum")!=null){
				sourceList=(List<String>) matchedCommandAdd.getParameters().get("sourceNum");
			}else{
				sourceList=new ArrayList<String>();
			}
			sourceList.add((String) currentTokenForMatch.getDeger());
			matchedCommandAdd.getParameters().put("sourceNum", sourceList);
			
		}else if(abstractTokenInPattern.getSourceFieldName().equals("destNum")){
			
			matchedCommandAdd.getSourceNum().add((String) currentTokenForMatch.getDeger());
			
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
		return "SUBCTRACT PATERN";
	}


		





	
	
	

	
	
	
	

}
