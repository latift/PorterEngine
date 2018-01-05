package tr.com.vbt.java.loops;

import java.util.List;

import org.apache.log4j.Logger;


import tr.com.vbt.java.AbstractJavaElement;
import tr.com.vbt.java.general.JavaClassElement;
import tr.com.vbt.java.general.JavaConstants;
import tr.com.vbt.java.utils.ConvertUtilities;

public class JavaIfNoRecords extends  AbstractJavaElement{
	
	final static Logger logger = Logger.getLogger(JavaIfNoRecords.class);
	
	List<String> dataToDisplay;

	@Override
	public boolean writeJavaToStream() throws Exception{ super.writeJavaToStream();
		
		
		try{
			writeChildrenJavaToStream();
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
