package tr.com.vbt.cobol.parser.datalayout.patern;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.datalayout.ElementDataTypeCobol;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.patern.AbstractPattern;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.KarakterToken;
import tr.com.vbt.token.KelimeToken;
import tr.com.vbt.token.KeyValueOzelKelimeToken;
import tr.com.vbt.token.OzelKelimeToken;
import tr.com.vbt.token.SayiToken;

//	05  FILLER OCCURS  PIC X(99)

/**
 * 
 *  PIC CLAUSE:
 *  Data type: 
 *  	9	Numeric
		A	Alphabetic
		X	Alphanumeric
		V	Implicit Decimal
		S	Sign
		P	Assumed Decimal
		
 * @author 47159500
 *
 */
public class PaternDataTypeFiller_X_Size extends AbstractPattern{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		AbstractPattern asp =new PaternDataTypeFiller_X_Size();

	}

	public PaternDataTypeFiller_X_Size() {
		super();
		
		//01
		AbstractToken astSource=new SayiToken();
		astSource.setTekrarlayabilir("+");
		astSource.setSourceFieldName("levelNumber");
		patternTokenList.add(astSource);
		
		//FILLER
		AbstractToken astSource2=new OzelKelimeToken("FILLER", 0, 0, 0);
		astSource2.setTekrarlayabilir("+");
		astSource2.setSourceFieldName("dataName");
		patternTokenList.add(astSource2);
		
		//OCCURS
		AbstractToken astOccurs=new KeyValueOzelKelimeToken("OCCURS","", 0, 0, 0);
		astOccurs.setSourceFieldName("OCCURS");
		astOccurs.setOptional(true);
		patternTokenList.add(astOccurs);
		//TIMES
		AbstractToken astTimes=new OzelKelimeToken("TIMES", 0, 0, 0);
		astTimes.setSourceFieldName("TIMES");
		astTimes.setOptional(true);
		patternTokenList.add(astTimes);
				
		//PIC
		AbstractToken astKeyword=new OzelKelimeToken("PIC", 0, 0, 0);
		patternTokenList.add(astKeyword);
		
		// X Mandatory Kelime(değer verilecek.)
		AbstractToken astSource4=new KelimeToken<String>("X",0,0,0);
		astSource4.setSourceFieldName("dataType");
		patternTokenList.add(astSource4);
		
		///( Mandatory
		AbstractToken astSource5=new KarakterToken('(', 0,0,0);
		patternTokenList.add(astSource5);
		
		//	*	12 Mandatory Sayi
		AbstractToken astSource6=new SayiToken<Integer>();
		astSource6.setSourceFieldName("length");
		patternTokenList.add(astSource6);
		
		// ) Mandatory
		AbstractToken astSource7=new KarakterToken(')', 0,0,0);
		patternTokenList.add(astSource7);
		
		//COMP-3
		AbstractToken astComp=new KelimeToken<String>();
		astComp.setSourceFieldName("COMP");
		astComp.setOptional(true);
		patternTokenList.add(astComp);
						
	}

	@Override
	public void setTokenToElement(AbstractCommand matchedCommand,
			AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {
		
		ElementDataTypeCobol matchedCommandAdd=(ElementDataTypeCobol) matchedCommand;
		
		super.setSatirNumarasi(matchedCommand,currentTokenForMatch, abstractTokenInPattern);if(abstractTokenInPattern.getSourceFieldName()==null){
		
		}else if(abstractTokenInPattern.getSourceFieldName().equals("levelNumber")){
			matchedCommandAdd.setLevelNumber(((Double)currentTokenForMatch.getDeger()).intValue());
			matchedCommandAdd.getParameters().put("levelNumber", matchedCommandAdd.getLevelNumber());
			matchedCommandAdd.setDataType("X");
			matchedCommandAdd.getParameters().put("type","String");
		}else if(abstractTokenInPattern.getSourceFieldName().equals("dataName")){
			matchedCommandAdd.setDataName((String) currentTokenForMatch.getDeger());
			matchedCommandAdd.getParameters().put("dataName", matchedCommandAdd.getDataName());
		}else if(abstractTokenInPattern.getSourceFieldName().equals("length")){
			matchedCommandAdd.setLength( ((Double)currentTokenForMatch.getDeger()).intValue());
			matchedCommandAdd.getParameters().put("length", matchedCommandAdd.getLength());
		}else if(abstractTokenInPattern.getSourceFieldName().equals("OCCURS")){
			matchedCommandAdd.setOccuringCount((int) ((KeyValueOzelKelimeToken)currentTokenForMatch).getValue());
			matchedCommandAdd.getParameters().put("OCCURS", matchedCommandAdd.getOccuringCount());
		}
		
		
	}

	@Override
	public AbstractCommand createElement() {
		ElementDataTypeCobol createdElement = new ElementDataTypeCobol(ReservedCobolKeywords.DATA_TYPE, "WORKING-STORAGE-SECTION.DATA_TYPE");
		return createdElement;
		
	}

	
	


	

}
