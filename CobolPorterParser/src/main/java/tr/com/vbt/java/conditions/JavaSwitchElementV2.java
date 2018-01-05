package tr.com.vbt.java.conditions;

import org.apache.log4j.Logger;


import tr.com.vbt.java.AbstractJavaElement;
import tr.com.vbt.java.general.JavaClassElement;
import tr.com.vbt.java.general.JavaConstants;
import tr.com.vbt.java.utils.ConvertUtilities;
import tr.com.vbt.java.utils.JavaWriteUtilities;
import tr.com.vbt.token.AbstractToken;


/*
	DECIDE ON EVERY #SECIM
	*S**  VALUE 1 FETCH RETURN 'TOPADEP5'
	*S**  VALUE 7 FETCH RETURN 'TPSEXBP1' 'O'
	*S**  VALUE 11 FETCH RETURN 'TPSITXP1'
	*S**  VALUE 12 FETCH RETURN 'TPSNAEP1' '*'
	*S**  NONE
	*S**    REINPUT 'HATALI SECIM.!'   MARK *#SECIM
	*S**END-DECIDE
	
	
	int SECIM = 0;
	switch(SECIM) {
	case 1:
		fetch("TOPADEP5");
		break;
	default:
		System.out.println("HATA");	
		break;
}
*/


public class JavaSwitchElementV2 extends  AbstractJavaElement {
	
	final static Logger logger = Logger.getLogger(JavaSwitchElementV2.class);
	
	private AbstractToken condition;
	
	public boolean writeJavaToStream() throws Exception{ super.writeJavaToStream();
		try {
			condition = (AbstractToken) this.parameters.get("condition");
			
			//conditionList=processOrCondition();  // OR= arasına değisken ekler.
			
//			conditionList=processTypeControls();   //KArakterse ve = değilse tek Tirnak ekle. A --> 'A'
			
			//conditionList=convertConditions(); //And OR EQ GT gibleri  && != == >= gibi çevirir.
			
			//convertMaskCondition(); //And OR EQ GT gibleri  && != == >= gibi çevirir.
			
			//conditionList=addQuotas();  // Çift Tırnak ekler
			
			// VALORKONT < SAK_TCYEGIRIS2  --> VALORKONT.compareTo(SAK_TCYEGIRIS2)
			//conditionList=addCompareToStarterAndEnder();
			
			// ISLEMVALOR > BASVURUTARIHI  --> !ISLEMVALOR.compareTo(BASVURUTARIHI)
			//conditionList=addReverseCompareToStarterAndEnder();
			
			// SAK_TCYEGIRIS2=="1900_01_01"  --> SAK_TCYEGIRIS2.equals("1900_01_01")
			//conditionList=addEqualToStarterAndEnder();
						
			
			// SAK_TCYEGIRIS2!="1900_01_01"  --> !SAK_TCYEGIRIS2.equals("1900_01_01")
			// SAK_TCYEGIRIS2 ^= '1900-01-01'  --> !SAK_TCYEGIRIS2.equals("1900_01_01")
			//conditionList=addNotEqualToStarterAndEnder();
			
			String getterString;
			
			String type;
			
			JavaClassElement.javaCodeBuffer.append("switch "+JavaConstants.OPEN_NORMAL_BRACKET);
				try {
					addIntCastForLong(condition);
					JavaClassElement.javaCodeBuffer.append(JavaWriteUtilities.toCustomString(condition));
					
					
				} catch (Exception e) {
					logger.debug("//Conversion Error"+this.getClass()+this.getSourceCode().getSatirNumarasi()+this.getSourceCode().getCommandName());
					JavaClassElement.javaCodeBuffer.append("/*Conversion Error"+this.getClass()+this.getSourceCode().getSatirNumarasi()
							+this.getSourceCode().getCommandName()+"*/"+JavaConstants.NEW_LINE);
					logger.error("//Conversion Error:"+e.getMessage(), e); 
					ConvertUtilities.writeconversionErrors(e, this);
				}
			
			JavaClassElement.javaCodeBuffer.append(JavaConstants.CLOSE_NORMAL_BRACKET+ JavaConstants.OPEN_BRACKET+JavaConstants.NEW_LINE);
			this.writeChildrenJavaToStream();
			JavaClassElement.javaCodeBuffer.append(JavaConstants.CLOSE_BRACKET+"// switch"+JavaConstants.NEW_LINE);
		} catch (Exception e) {
			logger.debug("//Conversion Error"+this.getClass()+this.getSourceCode().getSatirNumarasi()+this.getSourceCode().getCommandName());
			JavaClassElement.javaCodeBuffer.append("/*Conversion Error"+this.getClass()+this.getSourceCode().getSatirNumarasi()
					+this.getSourceCode().getCommandName()+"*/"+JavaConstants.NEW_LINE);
			logger.error("//Conversion Error:"+e.getMessage(), e); 
			ConvertUtilities.writeconversionErrors(e, this);
		}
		return true;
	}


	private static void addIntCastForLong(AbstractToken controlToken) {
	
		String typeOfToken=ConvertUtilities.getTypeOfVariable(controlToken);
		
		if(typeOfToken==null){
			return;
		}
		typeOfToken=typeOfToken.toLowerCase();
		
		if(typeOfToken.equals("long")){
			JavaClassElement.javaCodeBuffer.append("(int)");
		}
		return;
	}

}
