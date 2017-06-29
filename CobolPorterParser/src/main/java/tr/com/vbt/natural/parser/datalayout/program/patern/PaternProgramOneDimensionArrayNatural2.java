package tr.com.vbt.natural.parser.datalayout.program.patern;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.natural.parser.datalayout.program.ElementProgramOneDimensionArrayNatural;
import tr.com.vbt.patern.AbstractDataTypePattern;
import tr.com.vbt.patern.AbstractPattern;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.KarakterToken;
import tr.com.vbt.token.KelimeToken;
import tr.com.vbt.token.SayiToken;

 /**S**  
  		1 #DIZI-KLIM(A3/500)	Local Normal Dizi Tanımı
 **/
public class PaternProgramOneDimensionArrayNatural2 extends AbstractDataTypePattern{

	final static Logger logger = LoggerFactory.getLogger(PaternProgramOneDimensionArrayNatural2.class);
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		AbstractPattern asp =new PaternProgramOneDimensionArrayNatural2();

	}

	public PaternProgramOneDimensionArrayNatural2() {
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
				
		//500
		AbstractToken astSource8=new SayiToken<Integer>();
		astSource8.setSourceFieldName("arrayLength");
		patternTokenList.add(astSource8);
		
		///) Mandatory
		AbstractToken astSource9=new KarakterToken(')', 0,0,0);
		patternTokenList.add(astSource9);
		
	}

	@Override
	public void setTokenToElement(AbstractCommand matchedCommand,
			AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {
		
		ElementProgramOneDimensionArrayNatural matchedCommandAdd=(ElementProgramOneDimensionArrayNatural) matchedCommand;
		
		super.setSatirNumarasi(matchedCommand,currentTokenForMatch, abstractTokenInPattern);if(abstractTokenInPattern.getSourceFieldName()==null){
		
		}else if(abstractTokenInPattern.getSourceFieldName().equals("levelNumber")){
			matchedCommandAdd.setLevelNumber(((Integer)currentTokenForMatch.getDeger()));
			matchedCommandAdd.getParameters().put("levelNumber", matchedCommandAdd.getLevelNumber());
			matchedCommandAdd.setDataType("A");
			matchedCommandAdd.getParameters().put("type","String");
		}
		
		else if(abstractTokenInPattern.getSourceFieldName().equals("dataName")){
			if(currentTokenForMatch.getColumnNameToken()!=null){
				matchedCommandAdd.setDataName((String) currentTokenForMatch.getColumnNameToken().getDeger());
				matchedCommandAdd.getParameters().put("dataName", matchedCommandAdd.getDataName());
			}else{
			matchedCommandAdd.setDataName((String) currentTokenForMatch.getDeger());
			matchedCommandAdd.getParameters().put("dataName", matchedCommandAdd.getDataName());
			}
		}
		
		else if(abstractTokenInPattern.getSourceFieldName().equals("dataType")){
			String deger;
			if(currentTokenForMatch.getColumnNameToken()!=null){
				deger=currentTokenForMatch.getColumnNameToken().getDeger().toString();
			}else{
				deger=currentTokenForMatch.getDeger().toString();
			}
			matchedCommandAdd.setDataType((String) deger.substring(0, 1));
			matchedCommandAdd.getParameters().put("dataType", matchedCommandAdd.getDataType());
			if(deger.length()>1){
				try {
					matchedCommandAdd.setLength(Integer.parseInt(deger.substring(1)));
					matchedCommandAdd.getParameters().put("length", matchedCommandAdd.getLength());
				} catch (NumberFormatException e) {
					logger.error(e.getMessage(),e);
				}
			}
		}
		
		else if(abstractTokenInPattern.getSourceFieldName().equals("arrayLength")){
			if(currentTokenForMatch.getDeger() instanceof Double){
			matchedCommandAdd.setArrayLength(((Double)currentTokenForMatch.getDeger()).intValue());
			}else {
				matchedCommandAdd.setArrayLength((int) currentTokenForMatch.getDeger());
			}
			matchedCommandAdd.getParameters().put("arrayLength", matchedCommandAdd.getArrayLength());
		}
	}

	@Override
	public AbstractCommand createElement() {
		ElementProgramOneDimensionArrayNatural createdElement = new ElementProgramOneDimensionArrayNatural(ReservedNaturalKeywords.PROGRAM_ARRAY_DATA_TYPE, "GENERAL.PROGRAM_ARRAY_DATA_TYPE");
		return createdElement;
		
	}

	public AbstractCommand getmatchedCommand(List<AbstractToken> tokenListesi, int offset){
		return super.getmatchedCommand(tokenListesi, offset);
	}

	

}
