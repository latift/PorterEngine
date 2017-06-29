package tr.com.vbt.java.basic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tr.com.vbt.java.AbstractJavaElement;
import tr.com.vbt.java.general.JavaClassElement;
import tr.com.vbt.java.general.JavaConstants;
import tr.com.vbt.java.utils.ConvertUtilities;


// ACCEPT WS-STUDENT-NAME. -> String input = System.console().readLine();
public class JavaAcceptElement extends  AbstractJavaElement {
	
	final static Logger logger = LoggerFactory.getLogger(JavaAcceptElement.class);
	
	private String inputName;
	
	public boolean writeJavaToStream() throws Exception 
	{ super.writeJavaToStream();
		inputName=(String) this.getParameters().get("inputName");
		
		try {
			JavaClassElement.javaCodeBuffer.append(inputName+"=System.console().readLine()"+JavaConstants.DOT_WITH_COMMA+JavaConstants.NEW_LINE);
		} catch (Exception e) {
			logger.debug("//Conversion Error"+this.getClass()+this.getSourceCode().getSatirNumarasi()+this.getSourceCode().getCommandName());
			JavaClassElement.javaCodeBuffer.append("/*Conversion Error"+this.getClass()+this.getSourceCode().getSatirNumarasi()
					+this.getSourceCode().getCommandName()+"*/"+JavaConstants.NEW_LINE);
			logger.error("//Conversion Error:"+e.getMessage(), e); 
			ConvertUtilities.writeconversionErrors(e, this); 
		}
		
		return true;
	}

	@Override
	public boolean writeChildrenJavaToStream() {
		// TODO Auto-generated method stub
		return false;
	}

	

}
