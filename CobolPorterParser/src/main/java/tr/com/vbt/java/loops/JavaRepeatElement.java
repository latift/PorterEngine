package tr.com.vbt.java.loops;

import org.apache.log4j.Logger;


import tr.com.vbt.java.AbstractJavaElement;
import tr.com.vbt.java.general.JavaClassElement;
import tr.com.vbt.java.general.JavaConstants;
import tr.com.vbt.java.utils.ConvertUtilities;


//RP1. REPEAT -->	while(true){   };
public class JavaRepeatElement extends  AbstractJavaElement {
	
	final static Logger logger = Logger.getLogger(JavaPerformThru.class);
	
	private String loopName;
	
	public boolean writeJavaToStream() throws Exception{ super.writeJavaToStream();
		try {
			loopName = (String) this.parameters.get("loopName");
			JavaClassElement.javaCodeBuffer.append("while (true)"+JavaConstants.OPEN_BRACKET+JavaConstants.NEW_LINE);
			if(loopName!=null){
				JavaClassElement.javaCodeBuffer.append("//"+loopName+JavaConstants.NEW_LINE);
			}
			
			addTryBlock();
			this.writeChildrenJavaToStream();
			
			addCatchBlock();
			
			JavaClassElement.javaCodeBuffer.append(JavaConstants.CLOSE_BRACKET+JavaConstants.NEW_LINE);
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
