package tr.com.vbt.natural.parser.datalayout.program.patern;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.natural.parser.datalayout.ElementDataTypeCobol;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.natural.parser.datalayout.program.ElementProgramDataTypeNatural;
import tr.com.vbt.patern.AbstractPattern;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.KarakterToken;
import tr.com.vbt.token.KelimeToken;
import tr.com.vbt.token.KeyValueOzelKelimeToken;
import tr.com.vbt.token.OzelKelimeToken;
import tr.com.vbt.token.SayiToken;

//	2 FILLER 1X

/**
 * 
 * @author 47159500
 *
 */
public class PaternDataTypeFiller_Count_X extends AbstractPattern{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		AbstractPattern asp =new PaternDataTypeFiller_Count_X();

	}

	public PaternDataTypeFiller_Count_X() {
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
		
		//	*	12 Mandatory Sayi
		AbstractToken astSource6=new SayiToken<Integer>();
		astSource6.setSourceFieldName("length");
		patternTokenList.add(astSource6);

		// X Mandatory Kelime(değer verilecek.)
		AbstractToken astSource4=new KelimeToken<String>("X",0,0,0);
		astSource4.setSourceFieldName("dataType");
		patternTokenList.add(astSource4);
		
		
						
	}

	@Override
	public void setTokenToElement(AbstractCommand matchedCommand,
			AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {
		
		ElementProgramDataTypeNatural matchedCommandAdd=(ElementProgramDataTypeNatural) matchedCommand;
		
		super.setSatirNumarasi(matchedCommand,currentTokenForMatch, abstractTokenInPattern);
		
		if(currentTokenForMatch.getSatirNumarasi()==46){
			System.out.println("");
		}
		if(abstractTokenInPattern.getSourceFieldName()==null){
		
		}else if(abstractTokenInPattern.getSourceFieldName().equals("levelNumber")){
			matchedCommandAdd.setLevelNumber(((Long)currentTokenForMatch.getDeger()).longValue());
			matchedCommandAdd.getParameters().put("levelNumber", matchedCommandAdd.getLevelNumber());
			matchedCommandAdd.setDataType("X");
			matchedCommandAdd.getParameters().put("type","String");
		}else if(abstractTokenInPattern.getSourceFieldName().equals("dataName")){
			matchedCommandAdd.setDataName((String) currentTokenForMatch.getDeger());
			matchedCommandAdd.getParameters().put("dataName", matchedCommandAdd.getDataName()+matchedCommand.getSatirNumarasi()%10);
		}else if(abstractTokenInPattern.getSourceFieldName().equals("length")){
			matchedCommandAdd.setLength( ((Long)currentTokenForMatch.getDeger()).longValue());
			matchedCommandAdd.getParameters().put("length", matchedCommandAdd.getLength());
		}
		
		
	}

	@Override
	public AbstractCommand createElement() {
		ElementProgramDataTypeNatural createdElement = new ElementProgramDataTypeNatural(ReservedNaturalKeywords.PROGRAM_DATA_TYPE, "GENERAL.PROGRAM_DATA_TYPE");
		return createdElement;
		
	}

	
	


	

}
