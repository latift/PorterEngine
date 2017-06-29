package tr.com.vbt.java.screen;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tr.com.vbt.java.AbstractJavaElement;
import tr.com.vbt.java.general.JavaClassElement;
import tr.com.vbt.java.general.JavaConstants;
import tr.com.vbt.java.utils.ConvertUtilities;


//NEW PAGE -->Continue;
public class JavaTerminateElement extends  AbstractJavaElement {
	
	final static Logger logger = LoggerFactory.getLogger(JavaTerminateElement.class);

	public boolean writeJavaToStream() throws Exception{ super.writeJavaToStream();
		
		try {

			JavaClassElement.javaCodeBuffer.append("terminate();"+JavaConstants.NEW_LINE);

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
