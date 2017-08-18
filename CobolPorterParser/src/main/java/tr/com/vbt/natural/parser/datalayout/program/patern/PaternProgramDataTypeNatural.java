package tr.com.vbt.natural.parser.datalayout.program.patern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.natural.parser.datalayout.program.ElementProgramDataTypeNatural;
import tr.com.vbt.patern.AbstractDataTypePattern;
import tr.com.vbt.patern.AbstractPattern;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.KarakterToken;
import tr.com.vbt.token.KelimeToken;
import tr.com.vbt.token.SayiToken;

 /**S**  
  		1 #T-USER     (A8)  	 Local Normal Değişken
  		#T-RFN-UCRET(P7.7)
  		
  		1.0  Uzunluk:0 Satir No:50 Tipi:Sayi
		T-RFN-UCRET  Uzunluk:0 Satir No:50 Tipi:Kelime
		(  Uzunluk:1 Satir No:50 Tipi:Karakter
		P7  Uzunluk:0 Satir No:50 Tipi:Kelime
		7.0  Uzunluk:0 Satir No:50 Tipi:Sayi
		)  Uzunluk:1 Satir No:50 Tipi:Karakter

	*/
public class PaternProgramDataTypeNatural extends AbstractDataTypePattern{

	final static Logger logger = LoggerFactory.getLogger(PaternProgramDataTypeNatural.class);
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		AbstractPattern asp =new PaternProgramDataTypeNatural();

	}

	public PaternProgramDataTypeNatural() {
		super();
		
		//01
		AbstractToken astSource=new SayiToken<Integer>();
		astSource.setTekrarlayabilir("+");
		astSource.setSourceFieldName("levelNumber");
		patternTokenList.add(astSource);
		
		//#
		/*AbstractToken astSource6=new KarakterToken('#', 0,0,0);
		patternTokenList.add(astSource6);*/
				
		//CLIENT-ID
		AbstractToken astSource2=new KelimeToken<String>();
		astSource2.setTekrarlayabilir("+");
		astSource2.setSourceFieldName("dataName");
		//astSource2.setLocalVariable(true); //MB si için kaldirildi
		patternTokenList.add(astSource2);
		
		///( Mandatory
		AbstractToken astSource3=new KarakterToken('(', 0,0,0);
		patternTokenList.add(astSource3);
		
		//	*	N8
		AbstractToken astSource4=new KelimeToken();
		astSource4.setSourceFieldName("dataType");
		patternTokenList.add(astSource4);
		
//		*	N8.5
		AbstractToken astSource6=new SayiToken();
		astSource6.setSourceFieldName("lengthAfterDot");
		astSource6.setOptional(true);
		patternTokenList.add(astSource6);
		
		///) Mandatory
		AbstractToken astSource5=new KarakterToken(')', 0,0,0);
		patternTokenList.add(astSource5);
		
	}

	@Override
	public void setTokenToElement(AbstractCommand matchedCommand,
			AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {
		
		ElementProgramDataTypeNatural matchedCommandAdd=(ElementProgramDataTypeNatural) matchedCommand;
		
		super.setSatirNumarasi(matchedCommand,currentTokenForMatch, abstractTokenInPattern);if(abstractTokenInPattern.getSourceFieldName()==null){
		
		}else if(abstractTokenInPattern.getSourceFieldName().equals("levelNumber")){
			matchedCommandAdd.setLevelNumber(((Integer)currentTokenForMatch.getDeger()));
			matchedCommandAdd.getParameters().put("levelNumber", matchedCommandAdd.getLevelNumber());
		}
		
		else if(abstractTokenInPattern.getSourceFieldName().equals("dataName")){
			
			if(currentTokenForMatch.getColumnNameToken()!=null){
				matchedCommandAdd.setDataName((String) currentTokenForMatch.getColumnNameToken().getDeger());
			}else{
				matchedCommandAdd.setDataName((String) currentTokenForMatch.getDeger());
			}
			matchedCommandAdd.getParameters().put("dataName", matchedCommandAdd.getDataName());
		}
		//N8
		else if(abstractTokenInPattern.getSourceFieldName().equals("dataType")){
			
			String deger;
			
			if(currentTokenForMatch.getColumnNameToken()!=null){
				deger=((String) currentTokenForMatch.getColumnNameToken().getDeger());
			}else{
				deger=((String) currentTokenForMatch.getDeger());
			}
			matchedCommandAdd.setDataType(deger.substring(0,1));
			matchedCommandAdd.getParameters().put("dataType", matchedCommandAdd.getDataType());
			
			int len=0;
			try {
				if(deger.length()>1){
					len=Integer.parseInt(deger.substring(1));
				}else{
					len=0;
				}
			} catch (NumberFormatException e) {
				logger.error(e.getMessage(),e);
			}
			matchedCommandAdd.setLength(len);
			matchedCommandAdd.getParameters().put("length", matchedCommandAdd.getLength());
			
			
		}else if(abstractTokenInPattern.getSourceFieldName().equals("lengthAfterDot")){
			Double lenghtD;
			Integer lengthInt;
			if(currentTokenForMatch.getDeger()  instanceof Double){
				lenghtD=(Double) currentTokenForMatch.getDeger();
				matchedCommandAdd.setLengthAfterDot(lenghtD.intValue());
				matchedCommandAdd.getParameters().put("lengthAfterDot", matchedCommandAdd.getLengthAfterDot());
			}else if(currentTokenForMatch.getDeger()  instanceof Integer){
				lengthInt=(Integer) currentTokenForMatch.getDeger();
				matchedCommandAdd.setLengthAfterDot(lengthInt);
				matchedCommandAdd.getParameters().put("lengthAfterDot", matchedCommandAdd.getLengthAfterDot());
			}
		}
	}

	@Override
	public AbstractCommand createElement() {
		ElementProgramDataTypeNatural createdElement = new ElementProgramDataTypeNatural(ReservedNaturalKeywords.PROGRAM_DATA_TYPE, "GENERAL.PROGRAM_DATA_TYPE");
		return createdElement;
		
	}

	
	


	

}
