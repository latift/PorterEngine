package tr.com.vbt.java.general;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tr.com.vbt.java.AbstractJavaElement;
import tr.com.vbt.java.utils.ConvertUtilities;
import tr.com.vbt.lexer.ConversionLogModel;


public class JavaLocal extends  AbstractJavaElement{
	
	final static Logger logger = LoggerFactory.getLogger(JavaLocal.class);

	public boolean writeJavaToStream() throws Exception{ super.writeJavaToStream();
		
		try {
			
			/*if(ConversionLogModel.getInstance().isMapOrMapTester()){
				return true;
			}*/
			
			this.writeChildrenJavaToStream();
			
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
