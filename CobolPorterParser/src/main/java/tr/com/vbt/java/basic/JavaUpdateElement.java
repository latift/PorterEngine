package tr.com.vbt.java.basic;

import org.apache.log4j.Logger;


import tr.com.vbt.java.AbstractJavaElement;
import tr.com.vbt.java.general.JavaClassElement;
import tr.com.vbt.java.general.JavaConstants;
import tr.com.vbt.java.utils.ConvertUtilities;


//MOVE 0 TO CAT-CT --> cat-ct=0;
public class JavaUpdateElement extends  AbstractJavaElement {
	
	private int recNumber;
	
	final static Logger logger = Logger.getLogger(JavaUpdateElement.class);
	
	public boolean writeJavaToStream() throws Exception{ super.writeJavaToStream();
		
		try{
			
			//recNumber=(int) this.getParameters().get("RECNUMBER");
				
			JavaClassElement.javaCodeBuffer.append("update("+recNumber+")"+JavaConstants.DOT_WITH_COMMA+JavaConstants.NEW_LINE);
			//JavaClassElement.javaCodeBuffer.append("//"+recNumber+ JavaConstants.DOT_WITH_COMMA+JavaConstants.NEW_LINE);
			
		} catch (Exception e) {
			logger.debug("//Conversion Error"+this.getClass()+this.getSourceCode().getSatirNumarasi()+this.getSourceCode().getCommandName());
			JavaClassElement.javaCodeBuffer.append("/*Conversion Error"+this.getClass()+this.getSourceCode().getSatirNumarasi()
					+this.getSourceCode().getCommandName()+"*/"+JavaConstants.NEW_LINE);
			logger.error("//Conversion Error:"+e.getMessage(), e); 
			ConvertUtilities.writeconversionErrors(e, this);
		}
		JavaClassElement.javaCodeBuffer.append(JavaConstants.NEW_LINE);
		return true;
	}

	@Override
	public boolean writeChildrenJavaToStream() {
		// TODO Auto-generated method stub
		return false;
	}

}
 