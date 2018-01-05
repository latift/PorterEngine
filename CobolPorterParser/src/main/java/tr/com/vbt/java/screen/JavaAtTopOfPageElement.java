package tr.com.vbt.java.screen;

import org.apache.log4j.Logger;


import tr.com.vbt.java.AbstractJavaElement;
import tr.com.vbt.java.general.JavaClassElement;
import tr.com.vbt.java.general.JavaConstants;
import tr.com.vbt.java.utils.ConvertUtilities;


/*
 * 
*S**AT TOP OF PAGE                                                                              
*S**    WRITE NOTITLE                                                                           
*S**      15X  #T-TARIH ' - ' #T-BIT-TARIH 'TARIHLERI ARASINDA' 12X *DAT4E                      
*S**END-TOPPAGE  
*
*--> 
*  onload
					
 * 
 * **/
public class JavaAtTopOfPageElement extends  AbstractJavaElement {
	
	final static Logger logger = Logger.getLogger(JavaAtTopOfPageElement.class);

	private int printNumber;
	
	public boolean writeJavaToStream() throws Exception{ super.writeJavaToStream();
		if(this.getParameters().get("printNumber")!=null){
			printNumber= (int) ((long)this.getParameters().get("printNumber"));
		}
		
		try{
			JavaClassElement.javaCodeBuffer.append("@Override"+JavaConstants.NEW_LINE);
			JavaClassElement.javaCodeBuffer.append("public void atTopOfPage(){"+JavaConstants.NEW_LINE);
			
			this.writeChildrenJavaToStream();
			
			JavaClassElement.javaCodeBuffer.append("}"+JavaConstants.DOT_WITH_COMMA+JavaConstants.NEW_LINE);
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
