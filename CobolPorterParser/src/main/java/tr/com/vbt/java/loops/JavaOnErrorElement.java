package tr.com.vbt.java.loops;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tr.com.vbt.java.AbstractJavaElement;
import tr.com.vbt.java.general.JavaClassElement;
import tr.com.vbt.java.general.JavaConstants;
import tr.com.vbt.java.utils.ConvertUtilities;


//RP1. REPEAT -->	while(true){   };
public class JavaOnErrorElement extends  AbstractJavaElement {
	
	final static Logger logger = LoggerFactory.getLogger(JavaPerformThru.class);
	
	
	public boolean writeJavaToStream() throws Exception{ super.writeJavaToStream();
		try {
			JavaClassElement.javaCodeBuffer.append("//TODO ON ERROR"+JavaConstants.NEW_LINE);
			JavaClassElement.javaCodeBuffer.append("if(1==2)"+JavaConstants.OPEN_BRACKET+JavaConstants.NEW_LINE);
			this.writeChildrenJavaToStream();
			JavaClassElement.javaCodeBuffer.append(JavaConstants.CLOSE_BRACKET+"// on error"+JavaConstants.NEW_LINE);
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
