package tr.com.vbt.java.basic;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tr.com.vbt.java.AbstractJavaElement;
import tr.com.vbt.java.general.JavaClassElement;
import tr.com.vbt.java.general.JavaConstants;
import tr.com.vbt.java.utils.ConvertUtilities;

public class JavaIncludeElement extends  AbstractJavaElement{
	
	final static Logger logger = LoggerFactory.getLogger(JavaIncludeElement.class);
	
	private String source;
	
	@Override
	public boolean writeJavaToStream() throws Exception{ super.writeJavaToStream();
	
	
	try{
		source= (String) this.getParameters().get("localParameterName");
		JavaClassElement.javaCodeBuffer.append("//INCLUDE "+source);
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
