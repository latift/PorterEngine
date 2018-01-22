package tr.com.vbt.java.general;

import org.apache.log4j.Logger;


import tr.com.vbt.java.AbstractJava;
import tr.com.vbt.java.AbstractJavaElement;
import tr.com.vbt.java.utils.ConvertUtilities;
import tr.com.vbt.java.utils.CustomAnnotations;
import tr.com.vbt.lexer.ConversionLogModel;


public class JavaParameter extends  AbstractJavaElement{
	
	final static Logger logger = Logger.getLogger(JavaParameter.class);

	public boolean writeJavaToStream() throws Exception{ super.writeJavaToStream();
		
		try {
			
			/*if(ConversionLogModel.getInstance().isMapOrMapTester()){
				return true;
			}*/
			
			if(children!=null){
				for (AbstractJava aje : children) {
					if(aje!=null){
						try {
							aje.setAnnotationStr(CustomAnnotations.CallNatParameter);
						} catch (Exception e) {
							logger.error(e.getLocalizedMessage(),e);
						}
					}
				}
			}
			
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
