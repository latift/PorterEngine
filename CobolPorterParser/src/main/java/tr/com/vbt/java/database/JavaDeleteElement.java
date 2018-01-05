package tr.com.vbt.java.database;

import org.apache.log4j.Logger;


import tr.com.vbt.java.AbstractJavaElement;
import tr.com.vbt.java.general.JavaClassElement;
import tr.com.vbt.java.general.JavaConstants;
import tr.com.vbt.java.utils.ConvertUtilities;

//// 6214 STORE IDGIDBS-TGECICI  --> delete(TGECICI_DAO,TGECICI);
public class JavaDeleteElement extends  AbstractJavaElement{
	
	final static Logger logger = Logger.getLogger(JavaDeleteElement.class);

	private int recNumber;
	
	@Override
	public boolean writeJavaToStream() throws Exception{ super.writeJavaToStream();

		try {
			
			//recNumber=(int) this.getParameters().get("RECNUMBER");
			//JavaClassElement.javaCodeBuffer.append(JavaConstants.NEW_LINE);
			JavaClassElement.javaCodeBuffer.append("delete("+recNumber+")"+JavaConstants.DOT_WITH_COMMA+JavaConstants.NEW_LINE);
			//JavaClassElement.javaCodeBuffer.append("//"+recNumber+ JavaConstants.DOT_WITH_COMMA+JavaConstants.NEW_LINE);
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
