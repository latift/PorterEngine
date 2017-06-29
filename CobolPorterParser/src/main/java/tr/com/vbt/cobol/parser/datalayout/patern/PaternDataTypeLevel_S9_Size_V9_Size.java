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

/**
 * 000001		05  VLM-TODR                        PIC S9(13)V9(12)	000001
 * 
 *
 *S9 Mandatory Kelime
 *
 *( Mandatory
 *12 Mandatory Sayi
 *) Mandatory
 *
 *V9 Mandatory Kelime
 *
 *( Mandatory
 *12 Mandatory Sayi
 *) Mandatory
 */
public class PaternDataTypeLevel_S9_Size_V9_Size extends AbstractPattern{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		AbstractPattern asp =new PaternDataTypeLevel_S9_Size_V9_Size();

	}

	public PaternDataTypeLevel_S9_Size_V9_Size() {
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
		astKeyword.setTekrarlayabilir("+");
		astKeyword.setSourceFieldName("PIC");
		patternTokenList.add(astKeyword);
		
		//S9 Mandatory Kelime
		AbstractToken astSource3=new KelimeToken<String>("S9",0,0,0);
		astSource3.setTekrarlayabilir("+");
		astSource3.setSourceFieldName("pictureClause"); 
		patternTokenList.add(astSource3);
		
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
		
		
		//V9 Mandatory Kelime 
		AbstractToken astSource8=new KelimeToken<String>("V9",0,0,0);
		patternTokenList.add(astSource8);
		
		///( Mandatory
		AbstractToken astSource11=new KarakterToken('(', 0,0,0);
		patternTokenList.add(astSource11);
		
		//	*	12 Mandatory Sayi
		AbstractToken astSource9=new SayiToken<Integer>();
		astSource9.setSourceFieldName("decimalLength");
		patternTokenList.add(astSource9);
		
		// ) Mandatory
		AbstractToken astSource10=new KarakterToken(')', 0,0,0);
		patternTokenList.add(astSource10);
		
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
			matchedCommandAdd.setDataType("9");
			matchedCommandAdd.getParameters().put("type","number");
		}
		
		else if(abstractTokenInPattern.getSourceFieldName().equals("dataName")){
			matchedCommandAdd.setDataName((String) currentTokenForMatch.getDeger());
			matchedCommandAdd.getParameters().put("dataName", matchedCommandAdd.getDataName());
		}
		//length
		else if(abstractTokenInPattern.getSourceFieldName().equals("length")){ 
			Double db=(Double) currentTokenForMatch.getDeger();
			matchedCommandAdd.setLength( db.intValue());
			matchedCommandAdd.getParameters().put("length", matchedCommandAdd.getLength());
		}
		//V999 Mandatory Kelime (V ile basla)
		else if(abstractTokenInPattern.getSourceFieldName().equals("decimalLength")){  
			Double db=(Double) currentTokenForMatch.getDeger();
			matchedCommandAdd.setDecimalLength( db.intValue());
			matchedCommandAdd.getParameters().put("decimalLength", matchedCommandAdd.getDecimalLength());
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
