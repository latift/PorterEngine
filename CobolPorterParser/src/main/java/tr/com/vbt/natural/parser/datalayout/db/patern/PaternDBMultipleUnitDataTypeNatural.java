package tr.com.vbt.natural.parser.datalayout.db.patern;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.natural.parser.datalayout.db.ElementDBMultipleUnitDataTypeNatural;
import tr.com.vbt.patern.AbstractDataTypePattern;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.KarakterToken;
import tr.com.vbt.token.KelimeToken;
import tr.com.vbt.token.SayiToken;

 /**S**  
  		02 T-LOG-USER (1:50)
 **/
public class PaternDBMultipleUnitDataTypeNatural extends AbstractDataTypePattern{


	public PaternDBMultipleUnitDataTypeNatural() {
		super();
		
		//01
		AbstractToken astSource=new SayiToken();
		astSource.setTekrarlayabilir("+");
		astSource.setSourceFieldName("levelNumber");
		patternTokenList.add(astSource);
		
		//CLIENT-ID
		AbstractToken astSource2=new KelimeToken<String>();
		astSource2.setTekrarlayabilir("+");
		astSource2.setSourceFieldName("dataName");
		astSource2.setPojoVariable(true);
		patternTokenList.add(astSource2);
		
		///( Mandatory
		AbstractToken astSource3=new KarakterToken('(', 0,0,0);
		patternTokenList.add(astSource3);
		
		//	*	1
		AbstractToken astNumStart=new SayiToken();
		patternTokenList.add(astNumStart);
		
		AbstractToken astSource4=new KarakterToken(':', 0,0,0);
		patternTokenList.add(astSource4);
		
		//*	500
		AbstractToken astNumEnd=new SayiToken();
		astNumEnd.setSourceFieldName("multipleUnitCount");
		patternTokenList.add(astNumEnd);
		
		///) Mandatory
		AbstractToken astSource5=new KarakterToken(')', 0,0,0);
		patternTokenList.add(astSource5);
		
	}

	@Override
	public void setTokenToElement(AbstractCommand matchedCommand,
			AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {
		
		ElementDBMultipleUnitDataTypeNatural matchedCommandAdd=(ElementDBMultipleUnitDataTypeNatural) matchedCommand;
		
		super.setSatirNumarasi(matchedCommand,currentTokenForMatch, abstractTokenInPattern);if(abstractTokenInPattern.getSourceFieldName()==null){
		
		}else if(abstractTokenInPattern.getSourceFieldName().equals("levelNumber")){
			matchedCommandAdd.setLevelNumber(((Long)currentTokenForMatch.getDeger()));
			matchedCommandAdd.getParameters().put("levelNumber", matchedCommandAdd.getLevelNumber());
		}
		
		else if(abstractTokenInPattern.getSourceFieldName().equals("dataName")){
			matchedCommandAdd.setDataName((String) currentTokenForMatch.getDeger());
			matchedCommandAdd.getParameters().put("dataName", matchedCommandAdd.getDataName());
		}
		
		else if(abstractTokenInPattern.getSourceFieldName().equals("multipleUnitCount")){
			matchedCommandAdd.setMultipleUnitCount((long) currentTokenForMatch.getDeger());
			matchedCommandAdd.getParameters().put("multipleUnitCount", matchedCommandAdd.getMultipleUnitCount());
		}
	}

	@Override
	public AbstractCommand createElement() {
		ElementDBMultipleUnitDataTypeNatural createdElement = new ElementDBMultipleUnitDataTypeNatural(ReservedNaturalKeywords.MU_DATA_TYPE, "DATABASE.MU_DATA_TYPE");
		return createdElement;
		
	}

	
	


	

}
