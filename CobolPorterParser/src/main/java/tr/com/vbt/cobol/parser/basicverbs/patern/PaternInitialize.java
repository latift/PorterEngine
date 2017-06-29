package tr.com.vbt.cobol.parser.basicverbs.patern;

import java.util.ArrayList;
import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.basicverbs.ElementInitialize;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.patern.AbstractPattern;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.KelimeToken;
import tr.com.vbt.token.OzelKelimeToken;

/**
 *  INITIALIZE T-GRP (JJ) T-PGN (JJ) T-IOC (JJ) T-CPU (JJ)
 *
 *   INITIALIZE T-GRP  T-PGN  T-IOC  T-CPU 
 */
public class PaternInitialize extends AbstractPattern{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//AbstractPattern asp =new PaternAdd();

	}
	

	public PaternInitialize() {
		super();
		//INITIALIZE
		AbstractToken astKeyword=new OzelKelimeToken("INITIALIZE", 0, 0, 0);
		astKeyword.setTekrarlayabilir("+");
		patternTokenList.add(astKeyword);
		
		
		//T-GRP
		AbstractToken astSource=new KelimeToken<String>();
		astSource.setTekrarlayabilir("+");
		astSource.setSourceFieldName("params");
		patternTokenList.add(astSource);
	}
	
	@Override
	public AbstractCommand createElement(){
		ElementInitialize createdElement = new ElementInitialize(ReservedCobolKeywords.INITIALIZE,"PROCEDURE_DIVISION.*.INITIALIZE");
		return createdElement;
	}


	@Override
	public void setTokenToElement(AbstractCommand matchedCommand,
			AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {
		
		ElementInitialize matchedCommandAdd=(ElementInitialize) matchedCommand;
		
		super.setSatirNumarasi(matchedCommand,currentTokenForMatch, abstractTokenInPattern);if(abstractTokenInPattern.getSourceFieldName()==null){
		
		}else if(abstractTokenInPattern.getSourceFieldName().equals("params")){
			
			matchedCommandAdd.getParams().add((String) currentTokenForMatch.getDeger());
			
			List<String> sourceList;
			if(matchedCommandAdd.getParameters().get("params")!=null){
				sourceList=(List<String>) matchedCommandAdd.getParameters().get("params");
			}else{
				sourceList=new ArrayList<String>();
			}
			sourceList.add((String) currentTokenForMatch.getDeger());
			matchedCommandAdd.getParameters().put("params", sourceList);
			
		}
	}


	


		





	
	
	

	
	
	
	

}
