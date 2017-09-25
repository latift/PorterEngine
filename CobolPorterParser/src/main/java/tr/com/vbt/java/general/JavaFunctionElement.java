package tr.com.vbt.java.general;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tr.com.vbt.java.AbstractJavaElement;
import tr.com.vbt.java.utils.ConvertUtilities;


public class JavaFunctionElement extends  AbstractJavaElement{

	final static Logger logger = LoggerFactory.getLogger(JavaFunctionElement.class);
	
	//Paramaters: functionName;
	String subroutineName;
	
	//Fonksiyona ait değişken tanımları (JavaFunctionVariablesElement) children da tutulur.
	
	//Fonksiyona ait komutlar  children da tutulur.
	
	@Override
	public boolean writeJavaToStream() throws Exception{ super.writeJavaToStream();

		try {
			subroutineName=(String) this.parameters.get("subroutineName");
			
			JavaClassElement.javaCodeBuffer.append(JavaConstants.NEW_LINE);
			JavaClassElement.javaCodeBuffer.append("public void "+subroutineName+JavaConstants.OPEN_NORMAL_BRACKET+JavaConstants.CLOSE_NORMAL_BRACKET+JavaConstants.OPEN_BRACKET+JavaConstants.NEW_LINE);
			
			this.writeChildrenJavaToStream();
			
			JavaClassElement.javaCodeBuffer.append(JavaConstants.CLOSE_BRACKET+"// End Of Subroutine "+subroutineName+JavaConstants.NEW_LINE);
		} catch (Exception e) {
			logger.debug("//Conversion Error"+this.getClass()+this.getSourceCode().getSatirNumarasi()+this.getSourceCode().getCommandName());
			JavaClassElement.javaCodeBuffer.append("/*Conversion Error"+this.getClass()+this.getSourceCode().getSatirNumarasi()
					+this.getSourceCode().getCommandName()+"*/"+JavaConstants.NEW_LINE);
			logger.error("//Conversion Error:"+e.getMessage(), e); 
			ConvertUtilities.writeconversionErrors(e, this);
		}
		return true;
	}

	
	
}
