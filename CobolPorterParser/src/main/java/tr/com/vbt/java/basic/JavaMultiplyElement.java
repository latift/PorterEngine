package tr.com.vbt.java.basic;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;


import tr.com.vbt.java.AbstractJavaElement;
import tr.com.vbt.java.general.JavaClassElement;
import tr.com.vbt.java.general.JavaConstants;
import tr.com.vbt.java.utils.ConvertUtilities;
import tr.com.vbt.java.utils.JavaWriteUtilities;
import tr.com.vbt.token.AbstractToken;


//MULTIPLY A BY B C --> 	B=A*B, C=A*C
public class JavaMultiplyElement extends  AbstractJavaElement {
	
	final static Logger logger = Logger.getLogger(JavaMultiplyElement.class);

	private AbstractToken multiplyNum;
	
	private AbstractToken multiplierNum;
	
	private AbstractToken resultNum;
	
	public boolean writeJavaToStream() throws Exception{ super.writeJavaToStream();
		multiplyNum=(AbstractToken) this.getParameters().get("multiplyNum");
		
		multiplierNum=(AbstractToken) this.getParameters().get("multiplierNum");
		
		resultNum=(AbstractToken) this.getParameters().get("resultNum");
		 
		try {
			
			String typeOfCopyTo=ConvertUtilities.getTypeOfVariable(resultNum);
			
			String typeOfCopFrom=ConvertUtilities.getTypeOfVariable(multiplyNum);
			
			if(typeOfCopyTo!=null && typeOfCopyTo.equalsIgnoreCase("bigdecimal") && typeOfCopFrom!=null && typeOfCopFrom.equalsIgnoreCase("bigdecimal")){
				multiplyBigDecimal();
				return true;
			}
			
			// resultNum= multiplyNum* multiplierNum;
			if (typeOfCopyTo != null) {
				JavaClassElement.javaCodeBuffer.append(JavaWriteUtilities.toCustomString(resultNum));

				JavaClassElement.javaCodeBuffer.append("=");
				
				JavaClassElement.javaCodeBuffer.append(JavaWriteUtilities.toCustomString(multiplyNum));

				JavaClassElement.javaCodeBuffer.append("*");

				JavaClassElement.javaCodeBuffer.append(JavaWriteUtilities.toCustomString(multiplierNum));
		
				JavaClassElement.javaCodeBuffer.append(JavaConstants.DOT_WITH_COMMA + JavaConstants.NEW_LINE);
			}

		} catch (Exception e) {
			logger.debug("//Conversion Error" + this.getClass() + this.getSourceCode().getSatirNumarasi()
					+ this.getSourceCode().getCommandName());
			JavaClassElement.javaCodeBuffer
					.append("/*Conversion Error" + this.getClass() + this.getSourceCode().getSatirNumarasi()
							+ this.getSourceCode().getCommandName() + "*/" + JavaConstants.NEW_LINE);
			logger.error("//Conversion Error:" + e.getMessage(), e);
			ConvertUtilities.writeconversionErrors(e, this);
		}

		return true;
	}

	
	private void multiplyBigDecimal() throws Exception {
		// sonuc= bolunen/bolen;
		// bg3 = bg1.multiply(bg2, mc);
		//SATDOVIZ=ALDOVIZ.multiply(CAPRAZ_KUR).setScale(2,RoundingMode.DOWN);
		if (resultNum != null) {
			JavaClassElement.javaCodeBuffer.append(JavaWriteUtilities.toCustomString(resultNum));

			JavaClassElement.javaCodeBuffer.append("=");
			
			JavaClassElement.javaCodeBuffer.append(JavaWriteUtilities.toCustomString(multiplyNum));

			JavaClassElement.javaCodeBuffer.append(".multiply(");

			JavaClassElement.javaCodeBuffer.append(JavaWriteUtilities.toCustomString(multiplierNum));
			
			JavaClassElement.javaCodeBuffer.append(").setScale(2,RoundingMode.DOWN)"); //scale
	
			JavaClassElement.javaCodeBuffer.append(JavaConstants.DOT_WITH_COMMA + JavaConstants.NEW_LINE);
		}

		

}

	
	
	@Override
	public boolean writeChildrenJavaToStream() {
		return false;
	}

}
