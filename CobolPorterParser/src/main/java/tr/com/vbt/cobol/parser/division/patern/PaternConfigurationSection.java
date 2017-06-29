package tr.com.vbt.cobol.parser.division.patern;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.division.ElementConfSection;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.patern.AbstractPattern;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.KeyValueOzelKelimeToken;
import tr.com.vbt.token.OzelKelimeToken;

/**
 *   CONFIGURATION SECTION	   SOURCE-COMPUTER X	   OBJECT-COMPUTER Y
 *
 */
public class PaternConfigurationSection extends AbstractPattern{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//AbstractPattern asp =new PaternAdd();

	}
	

	public PaternConfigurationSection() {
		super();
		
		//CONFIGURATION SECTION
		AbstractToken astKeyword=new OzelKelimeToken("CONFIGURATION_SECTION", 0, 0, 0);
		astKeyword.setTekrarlayabilir("+");
		patternTokenList.add(astKeyword);
	
		
		/*
		//SOURCE-COMPUTER
		AbstractToken astKeyword2=new OzelKelimeToken("SOURCE-COMPUTER", 0, 0, 0);
		astKeyword2.setTekrarlayabilir("+");
		patternTokenList.add(astKeyword2);
		//X
		AbstractToken astSource=new KelimeToken<String>();
		astSource.setTekrarlayabilir("+");
		astSource.setSourceFieldName("SOURCE_COMPUTER");
		patternTokenList.add(astSource);
		
		//OBJECT-COMPUTER
		AbstractToken astKeyword3=new OzelKelimeToken("OBJECT-COMPUTER", 0, 0, 0);
		astKeyword3.setTekrarlayabilir("+");
		patternTokenList.add(astKeyword3);
		
		//y
		AbstractToken astSource3=new KelimeToken<String>();
		astSource3.setTekrarlayabilir("+");
		astSource3.setSourceFieldName("OBJECT-COMPUTER");
		patternTokenList.add(astSource3);
		*/
		
		AbstractToken astKeyword2=new KeyValueOzelKelimeToken("SOURCE-COMPUTER","", 0, 0, 0);
		astKeyword2.setTekrarlayabilir("+");
		astKeyword2.setSourceFieldName("SOURCE-COMPUTER");
		patternTokenList.add(astKeyword2);
		
		
		AbstractToken astKeyword3=new KeyValueOzelKelimeToken("OBJECT-COMPUTER","", 0, 0, 0);
		astKeyword3.setTekrarlayabilir("+");
		astKeyword3.setSourceFieldName("OBJECT-COMPUTER");
		patternTokenList.add(astKeyword3);
		
		
		
				
	}
	
	@Override
	public AbstractCommand createElement(){
		ElementConfSection createdElement = new ElementConfSection(ReservedCobolKeywords.CONFIGURATION_SECTION,"ENVIRONMENT_DIVISION.CONFIGURATION_SECTION");
		return createdElement;
	}


	@Override
	public void setTokenToElement(AbstractCommand matchedCommand,
			AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {

		ElementConfSection matchedCommandAdd=(ElementConfSection) matchedCommand;
		
		super.setSatirNumarasi(matchedCommand,currentTokenForMatch, abstractTokenInPattern);if(abstractTokenInPattern.getSourceFieldName()==null){
		
		}else if(abstractTokenInPattern.getSourceFieldName().equals("SOURCE-COMPUTER")){
			matchedCommandAdd.setSOURCE_COMPUTER(((String) ((KeyValueOzelKelimeToken)currentTokenForMatch).getValue()));
			matchedCommandAdd.getParameters().put("PROGRAM_ID", matchedCommandAdd.getSOURCE_COMPUTER());
		}else if(abstractTokenInPattern.getSourceFieldName().equals("OBJECT-COMPUTER")){
			matchedCommandAdd.setOBJECT_COMPUTER(((String) ((KeyValueOzelKelimeToken)currentTokenForMatch).getValue()));
			matchedCommandAdd.getParameters().put("PROGRAM_ID", matchedCommandAdd.getOBJECT_COMPUTER());
		}
	}
		
		
	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}




	
	
	

	
	
	
	

}
