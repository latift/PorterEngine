package tr.com.vbt.java.loops;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tr.com.vbt.java.AbstractJavaElement;
import tr.com.vbt.java.general.JavaClassElement;
import tr.com.vbt.java.general.JavaConstants;
import tr.com.vbt.java.utils.ConvertUtilities;


//ESCAPE TOP -->Continue;
public class JavaEscapeRoutineElement extends  AbstractJavaElement {
	
	final static Logger logger = LoggerFactory.getLogger(JavaEscapeRoutineElement.class);

	String loopName;
	
	public boolean writeJavaToStream() throws Exception{ super.writeJavaToStream();
		
		try {
			if(this.parameters.get("loopName") !=null){
				loopName = (String) this.parameters.get("loopName");
			}

			JavaClassElement.javaCodeBuffer.append("return;"+JavaConstants.NEW_LINE);
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
