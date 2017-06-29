package tr.com.vbt.cobol.parser.tableprocessing.patern;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.tableprocessing.ElementSetTo;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.patern.AbstractPattern;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.KelimeToken;
import tr.com.vbt.token.OzelKelimeToken;
import tr.com.vbt.token.SayiToken;

//SET ZZ TO 1  --> ZZ=1;
//PArams: source value
public class PaternSetTo extends AbstractPattern{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//AbstractPattern asp =new PaternAdd();

	}
	

	public PaternSetTo() {
		super();
		
		//PERFORM
		AbstractToken astKeyword=new OzelKelimeToken("SET", 0, 0, 0);
		astKeyword.setTekrarlayabilir("+");
		patternTokenList.add(astKeyword);
		
		
		//A
		AbstractToken astSource=new KelimeToken<String>();
		astSource.setTekrarlayabilir("+");
		astSource.setSourceFieldName("source");
		patternTokenList.add(astSource);
		
		//TO
		AbstractToken astKeyword2=new OzelKelimeToken("TO", 0, 0, 0);
		astKeyword2.setTekrarlayabilir("+");
		patternTokenList.add(astKeyword2);
		
		// C
		AbstractToken astDest=new SayiToken<Integer>();
		astDest.setTekrarlayabilir("+");
		astDest.setSourceFieldName("value");
		patternTokenList.add(astDest);
				
				
		
		
		
	}
	
	@Override
	public AbstractCommand createElement(){
		ElementSetTo createdElement = new ElementSetTo(ReservedCobolKeywords.SET,"PROCEDURE_DIVISION.*.SET");
		return createdElement;
	}


	@Override
	public void setTokenToElement(AbstractCommand matchedCommand,
			AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {
		
		ElementSetTo matchedCommandAdd=(ElementSetTo) matchedCommand;
		
		super.setSatirNumarasi(matchedCommand,currentTokenForMatch, abstractTokenInPattern);if(abstractTokenInPattern.getSourceFieldName()==null){
		
		}else if(abstractTokenInPattern.getSourceFieldName().equals("source")){
			matchedCommandAdd.setSource((String) currentTokenForMatch.getDeger());
			matchedCommandAdd.getParameters().put("paragraghName", matchedCommandAdd.getSource());
			
			
		}else if(abstractTokenInPattern.getSourceFieldName().equals("value")){
			matchedCommandAdd.setValue(Double.toString((double) currentTokenForMatch.getDeger()));
			matchedCommandAdd.getParameters().put("value", matchedCommandAdd.getValue());
		}
	}
		





	
	
	

	
	
	
	

}
