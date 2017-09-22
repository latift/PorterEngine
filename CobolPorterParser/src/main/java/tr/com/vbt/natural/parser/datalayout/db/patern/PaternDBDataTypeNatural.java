package tr.com.vbt.natural.parser.datalayout.db.patern;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.natural.parser.datalayout.db.ElementDBDataTypeNatural;
import tr.com.vbt.patern.AbstractDataTypePattern;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.KarakterToken;
import tr.com.vbt.token.KelimeToken;
import tr.com.vbt.token.SayiToken;

 /**S**  
  		02 T-BLOCKFUEL (N8) //N8 optional
 **/
public class PaternDBDataTypeNatural extends AbstractDataTypePattern{

	

	public PaternDBDataTypeNatural() {
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
		
		AbstractToken astSource3=new KarakterToken('(', 0,0,0);
		//astSource3.setOptional(true);
		patternTokenList.add(astSource3);
		
		//	*	N8
		AbstractToken astSource4=new KelimeToken();
		astSource4.setSourceFieldName("dataType");
		//astSource4.setOptional(true);
		patternTokenList.add(astSource4);
		
//		*	N8.5
		AbstractToken astSource6=new SayiToken();
		astSource6.setSourceFieldName("lengthAfterDot");
		astSource6.setOptional(true);
		patternTokenList.add(astSource6);
		
		///) Mandatory
		AbstractToken astSource5=new KarakterToken(')', 0,0,0);
		//astSource5.setOptional(true);
		patternTokenList.add(astSource5);
		
	}

	@Override
	public void setTokenToElement(AbstractCommand matchedCommand,
			AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {
		
		ElementDBDataTypeNatural matchedCommandAdd=(ElementDBDataTypeNatural) matchedCommand;
		
		super.setSatirNumarasi(matchedCommand,currentTokenForMatch, abstractTokenInPattern);if(abstractTokenInPattern.getSourceFieldName()==null){
		
		}else if(abstractTokenInPattern.getSourceFieldName().equals("levelNumber")){
			matchedCommandAdd.setLevelNumber(((Long)currentTokenForMatch.getDeger()));
			matchedCommandAdd.getParameters().put("levelNumber", matchedCommandAdd.getLevelNumber());
			matchedCommandAdd.setDataType("A");
			matchedCommandAdd.getParameters().put("type","String");
		}
		
		else if(abstractTokenInPattern.getSourceFieldName().equals("dataName")){
			if(currentTokenForMatch.getColumnNameToken()!=null){
				matchedCommandAdd.setDataName((String) currentTokenForMatch.getColumnNameToken().getDeger());
			}else{
				matchedCommandAdd.setDataName((String) currentTokenForMatch.getDeger());
			}
			matchedCommandAdd.getParameters().put("dataName", matchedCommandAdd.getDataName());
		}
		
		else if(abstractTokenInPattern.getSourceFieldName().equals("dataType")){
			matchedCommandAdd.setDataType((String) currentTokenForMatch.getDeger());
			matchedCommandAdd.getParameters().put("dataType", matchedCommandAdd.getDataType());
		}
		else if(abstractTokenInPattern.getSourceFieldName().equals("lengthAfterDot")){
			Double lenghtD;
			Long lengthInt;
			if(currentTokenForMatch.getDeger()  instanceof Double){
				lenghtD=(Double) currentTokenForMatch.getDeger();
				matchedCommandAdd.setLengthAfterDot(lenghtD.intValue());
				matchedCommandAdd.getParameters().put("lengthAfterDot", matchedCommandAdd.getLengthAfterDot());
			}else if(currentTokenForMatch.getDeger()  instanceof Integer){
				lengthInt=(Long) currentTokenForMatch.getDeger();
				matchedCommandAdd.setLengthAfterDot(lengthInt);
				matchedCommandAdd.getParameters().put("lengthAfterDot", matchedCommandAdd.getLengthAfterDot());
			}
		}
	}

	@Override
	public AbstractCommand createElement() {
		ElementDBDataTypeNatural createdElement = new ElementDBDataTypeNatural(ReservedNaturalKeywords.DB_DATA_TYPE, "DATABASE.DB_DATA_TYPE");
		return createdElement;
		
	}

	
	


	

}
