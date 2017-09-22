package tr.com.vbt.natural.parser.datalayout.program.patern;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.natural.parser.datalayout.program.ElementProgramTwoDimensionArrayNatural;
import tr.com.vbt.patern.AbstractDataTypePattern;
import tr.com.vbt.patern.AbstractPattern;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.KarakterToken;
import tr.com.vbt.token.KelimeToken;
import tr.com.vbt.token.SayiToken;

 /**S**  
  		1 #DIZI-KLIM(A3/1:500,1:60)	  Local Normal Two Dimension Dizi Tanımı
 **/
public class PaternProgramTwoDimensionArrayNatural extends AbstractDataTypePattern{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		AbstractPattern asp =new PaternProgramTwoDimensionArrayNatural();

	}

	public PaternProgramTwoDimensionArrayNatural() {
		super();
		
		//01
		AbstractToken astSource=new SayiToken<Integer>();
		astSource.setTekrarlayabilir("+");
		astSource.setSourceFieldName("levelNumber");
		patternTokenList.add(astSource);
		
		//#
		/*AbstractToken astSource1=new KarakterToken('#', 0,0,0);
		patternTokenList.add(astSource1);*/
				
		//CLIENT-ID
		AbstractToken astSource2=new KelimeToken<String>();
		astSource2.setTekrarlayabilir("+");
		astSource2.setSourceFieldName("dataName");
		astSource2.setLocalVariable(true);
		patternTokenList.add(astSource2);
		
		///( Mandatory
		AbstractToken astSource3=new KarakterToken('(', 0,0,0);
		patternTokenList.add(astSource3);
		
		//	*	A3
		AbstractToken astSource4=new KelimeToken<Integer>();
		astSource4.setSourceFieldName("dataType");
		patternTokenList.add(astSource4);
		
		//// Mandatory
		AbstractToken astSource5=new KarakterToken('/', 0,0,0);
		patternTokenList.add(astSource5);
				
		//1
		AbstractToken astSource6=new SayiToken<Integer>();
		patternTokenList.add(astSource6);
		
		// :
		AbstractToken astSource7=new KarakterToken(':', 0,0,0);
		patternTokenList.add(astSource7);
		
		//500
		AbstractToken astSource8=new SayiToken<Integer>();
		astSource8.setSourceFieldName("arrayLength");
		patternTokenList.add(astSource8);
		
		//,
		AbstractToken astSource13=new KarakterToken(',', 0,0,0);
		patternTokenList.add(astSource13);
		
		//1
		AbstractToken astSource9=new SayiToken<Integer>();
		patternTokenList.add(astSource9);
		
		// :
		AbstractToken astSource10=new KarakterToken(':', 0,0,0);
		patternTokenList.add(astSource10);
		
		//60
		AbstractToken astSource11=new SayiToken<Integer>();
		astSource11.setSourceFieldName("arrayLength2");
		patternTokenList.add(astSource11);
		
		///) Mandatory
		AbstractToken astSource12=new KarakterToken(')', 0,0,0);
		patternTokenList.add(astSource12);
		
	}

	@Override
	public void setTokenToElement(AbstractCommand matchedCommand,
			AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {
		
		ElementProgramTwoDimensionArrayNatural matchedCommandAdd=(ElementProgramTwoDimensionArrayNatural) matchedCommand;
		
		super.setSatirNumarasi(matchedCommand,currentTokenForMatch, abstractTokenInPattern);if(abstractTokenInPattern.getSourceFieldName()==null){
		
		}else if(abstractTokenInPattern.getSourceFieldName().equals("levelNumber")){
			matchedCommandAdd.setLevelNumber(((Long)currentTokenForMatch.getDeger()));
			matchedCommandAdd.getParameters().put("levelNumber", matchedCommandAdd.getLevelNumber());
			matchedCommandAdd.setDataType("A");
			matchedCommandAdd.getParameters().put("type","String");
		}
		
		else if(abstractTokenInPattern.getSourceFieldName().equals("dataName")){
			matchedCommandAdd.setDataName((String) currentTokenForMatch.getDeger());
			matchedCommandAdd.getParameters().put("dataName", matchedCommandAdd.getDataName());
		}
		
		else if(abstractTokenInPattern.getSourceFieldName().equals("dataType")){
			matchedCommandAdd.setDataType((String) currentTokenForMatch.getDeger().toString().substring(0, 1));
			matchedCommandAdd.getParameters().put("dataType", matchedCommandAdd.getDataType());
		}
		
		else if(abstractTokenInPattern.getSourceFieldName().equals("arrayLength")){
			matchedCommandAdd.setArrayLength((long) currentTokenForMatch.getDeger());
			matchedCommandAdd.getParameters().put("arrayLength", matchedCommandAdd.getArrayLength());
		}
		
		else if(abstractTokenInPattern.getSourceFieldName().equals("arrayLength2")){
			matchedCommandAdd.setArrayLength2((long) currentTokenForMatch.getDeger());
			matchedCommandAdd.getParameters().put("arrayLength2", matchedCommandAdd.getArrayLength2());
		}
	}

	@Override
	public AbstractCommand createElement() {
		ElementProgramTwoDimensionArrayNatural createdElement = new ElementProgramTwoDimensionArrayNatural(ReservedNaturalKeywords.PROGRAM_TWO_DIMENSION_ARRAY_DATA_TYPE, "GENERAL.PROGRAM_TWO_DIMENSION_ARRAY_DATA_TYPE");
		return createdElement;
		
	}

	
	


	

}
