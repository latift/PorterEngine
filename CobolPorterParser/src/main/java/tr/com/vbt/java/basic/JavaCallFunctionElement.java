package tr.com.vbt.java.basic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tr.com.vbt.java.AbstractJavaElement;
import tr.com.vbt.java.general.JavaClassElement;
import tr.com.vbt.java.general.JavaConstants;
import tr.com.vbt.java.utils.ConvertUtilities;


public class JavaCallFunctionElement extends  AbstractJavaElement{
	
	final static Logger logger = LoggerFactory.getLogger(JavaCallFunctionElement.class);
	//Paramaters: functionName;
	public String paragraphName;
	
	@Override
	public boolean writeJavaToStream() throws Exception{ super.writeJavaToStream();

		paragraphName=(String) this.parameters.get("paragraghName");
		
		if(paragraphName==null||paragraphName==""){     //Coboldaki PAragrafda Sectionda Java daki functiona denk gelir. 
			paragraphName=(String) this.parameters.get("sectionName");
		}
		
		try{
			JavaClassElement.javaCodeBuffer.append(paragraphName+JavaConstants.OPEN_NORMAL_BRACKET+JavaConstants.CLOSE_NORMAL_BRACKET+JavaConstants.DOT_WITH_COMMA+JavaConstants.NEW_LINE);
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
