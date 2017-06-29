package tr.com.vbt.cobol.parser.division.patern;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.division.ElementIdentificationDivision;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.patern.AbstractPattern;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.KeyValueOzelKelimeToken;
import tr.com.vbt.token.OzelKelimeToken;

/**
 IDENTIFICATION DIVISION.
       PROGRAM-ID.   BATCH1.
       AUTHOR.       XXXXXXXXX.
       DATE-WRITTEN. 2008.
 */
public class PaternIdentificationDivision extends AbstractPattern{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//AbstractPattern asp =new PaternAdd();

	}
	

	public PaternIdentificationDivision() {
		super();
		
		//IDENTIFICATION_DIVISION
		AbstractToken astKeyword=new OzelKelimeToken("IDENTIFICATION_DIVISION", 0, 0, 0);
		astKeyword.setTekrarlayabilir("+");
		patternTokenList.add(astKeyword);
		
		//PROGRAM_ID
		AbstractToken astKeyword2=new KeyValueOzelKelimeToken("PROGRAM-ID","", 0, 0, 0);
		astKeyword2.setTekrarlayabilir("+");
		astKeyword2.setSourceFieldName("PROGRAM_ID");
		astKeyword2.setOptional(true);
		patternTokenList.add(astKeyword2);
		
		//AUTHOR
		AbstractToken astKeyword3=new KeyValueOzelKelimeToken("AUTHOR","", 0, 0, 0);
		astKeyword3.setTekrarlayabilir("+");
		astKeyword3.setSourceFieldName("AUTHOR");
		astKeyword3.setOptional(true);
		patternTokenList.add(astKeyword3);
		
		//DATE_WRITTEN
		AbstractToken astKeyword4=new KeyValueOzelKelimeToken("DATE_WRITTEN","", 0, 0, 0);
		astKeyword4.setTekrarlayabilir("+");
		astKeyword4.setSourceFieldName("DATE_WRITTEN");
		astKeyword4.setOptional(true);
		patternTokenList.add(astKeyword4);
		
	}
	
	@Override
	public AbstractCommand createElement(){
		ElementIdentificationDivision createdElement = new ElementIdentificationDivision(ReservedCobolKeywords.IDENTIFICATION_DIVISION,"IDENTIFICATION_DIVISION");
		return createdElement;
	}


	@Override
	public void setTokenToElement(AbstractCommand matchedCommand,
			AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {
		
		ElementIdentificationDivision matchedCommandAdd=(ElementIdentificationDivision) matchedCommand;
		
		super.setSatirNumarasi(matchedCommand,currentTokenForMatch, abstractTokenInPattern);if(abstractTokenInPattern.getSourceFieldName()==null){
		
		}else if(abstractTokenInPattern.getSourceFieldName().equals("PROGRAM_ID")){
			matchedCommandAdd.setPROGRAM_ID(((String) ((KeyValueOzelKelimeToken)currentTokenForMatch).getValue()));
			matchedCommandAdd.getParameters().put("PROGRAM_ID", matchedCommandAdd.getPROGRAM_ID());
		}else if(abstractTokenInPattern.getSourceFieldName().equals("AUTHOR")){
			matchedCommandAdd.setAUTHOR(((String) ((KeyValueOzelKelimeToken)currentTokenForMatch).getValue()));
			matchedCommandAdd.getParameters().put("AUTHOR", matchedCommandAdd.getAUTHOR());
		}else if(abstractTokenInPattern.getSourceFieldName().equals("DATE_WRITTEN")){
			matchedCommandAdd.setDATE_WRITTEN(((String) ((KeyValueOzelKelimeToken)currentTokenForMatch).getValue()));
			matchedCommandAdd.getParameters().put("DATE_WRITTEN", matchedCommandAdd.getDATE_WRITTEN());
		}
	}
		
	





	
	
	

	
	
	
	

}
