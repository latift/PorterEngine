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

/*10  VCC-DBID                PIC X(3)

 	000001		05 VAC-AMTFMT                   PIC 99V9(12)					000001
 * 
 * X Mandatory Kelime(değer verilmeyecek.)
 * 
 * ( Optional
*	12 Optional Sayi
 * ) Optional
 *
 */
public class PaternDataTypeLevel_A_Size extends AbstractPattern{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		AbstractPattern asp =new PaternDataTypeLevel_A_Size();

	}

	public PaternDataTypeLevel_A_Size() {
		super();
		
		//01
		AbstractToken astSource=new SayiToken();
		astSource.setTekrarlayabilir("+");
		astSource.setSourceFieldName("levelNumber");
		patternTokenList.add(astSource);
		
		//WS-NUM1
		AbstractToken astSource2=new KelimeToken<String>();
		astSource2.setTekrarlayabilir("+");
		astSource2.setSourceFieldName("dataName");
		patternTokenList.add(astSource2);
		
		//REDEFINES
		AbstractToken astRedefines=new KeyValueOzelKelimeToken("REDEFINES","", 0, 0, 0);
		astRedefines.setSourceFieldName("REDEFINES");
		astRedefines.setOptional(true);
		patternTokenList.add(astRedefines);
		
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
		
		
		
		// A Mandatory Kelime(değer verilmeyecek.)
		AbstractToken astSource4=new KelimeToken<String>("A",0,0,0);
		astSource4.setSourceFieldName("dataType");
		patternTokenList.add(astSource4);
		
		///( Optional
		AbstractToken astSource5=new KarakterToken('(', 0,0,0);
		astSource5.setOptional(true);
		patternTokenList.add(astSource5);
		
		//	*	12 Optional Sayi
		AbstractToken astSource6=new SayiToken<Integer>();
		astSource6.setSourceFieldName("length");
		astSource6.setOptional(true);
		patternTokenList.add(astSource6);
		
		// ) Optional
		AbstractToken astSource7=new KarakterToken(')', 0,0,0);
		astSource7.setOptional(true);
		patternTokenList.add(astSource7);
		
		//COMP-3
		AbstractToken astComp=new KelimeToken<String>();
		astComp.setSourceFieldName("COMP");
		astComp.setOptional(true);
		patternTokenList.add(astComp);
		
		//VALUE
		AbstractToken astValue=new KeyValueOzelKelimeToken("VALUE","", 0, 0, 0);
		astValue.setSourceFieldName("VALUE");
		astValue.setOptional(true);
		patternTokenList.add(astValue);

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
			matchedCommandAdd.setDataType("A");
			matchedCommandAdd.getParameters().put("type","String");
		}
		
		else if(abstractTokenInPattern.getSourceFieldName().equals("dataName")){
			matchedCommandAdd.setDataName((String) currentTokenForMatch.getDeger());
			matchedCommandAdd.getParameters().put("dataName", matchedCommandAdd.getDataName());
		}
		
		else if(abstractTokenInPattern.getSourceFieldName().equals("length")){
			matchedCommandAdd.setLength( ((Double)currentTokenForMatch.getDeger()).intValue());
			matchedCommandAdd.getParameters().put("length", matchedCommandAdd.getLength());
		}
		else if(abstractTokenInPattern.getSourceFieldName().equals("REDEFINES")){
			matchedCommandAdd.setRedefines(((String) ((KeyValueOzelKelimeToken)currentTokenForMatch).getValue()));
			matchedCommandAdd.getParameters().put("PROGRAM_ID", matchedCommandAdd.getRedefines());
		}
		else if(abstractTokenInPattern.getSourceFieldName().equals("VALUE")){
			System.out.println(currentTokenForMatch.toString());
			if(((KeyValueOzelKelimeToken)currentTokenForMatch).getValue() instanceof Integer) {
				Integer valueAsInt= (Integer) ((KeyValueOzelKelimeToken)currentTokenForMatch).getValue();
				matchedCommandAdd.setValueAsInt(valueAsInt);
				
			}else if(((KeyValueOzelKelimeToken)currentTokenForMatch).getValue() instanceof String) {
				String value= (String) ((KeyValueOzelKelimeToken)currentTokenForMatch).getValue();
				matchedCommandAdd.setValue(value);
				
			} 
			
			matchedCommandAdd.getParameters().put("VALUE", matchedCommandAdd.getValue());
		}
		else if(abstractTokenInPattern.getSourceFieldName().equals("OCCURS")){
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
