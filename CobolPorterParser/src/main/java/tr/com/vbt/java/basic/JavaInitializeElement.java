package tr.com.vbt.java.basic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tr.com.vbt.java.AbstractJavaElement;
import tr.com.vbt.java.general.JavaClassElement;
import tr.com.vbt.java.general.JavaConstants;
import tr.com.vbt.java.utils.ConvertUtilities;


//MOVE 0 TO CAT-CT --> cat-ct=0;
public class JavaInitializeElement extends  AbstractJavaElement {
	
	final static Logger logger = LoggerFactory.getLogger(JavaInitializeElement.class);

	private String destVariable;
	
	private String dataToMove;
	
	public boolean writeJavaToStream() throws Exception{ super.writeJavaToStream();
		destVariable = (String) this.parameters.get("destVariable");
		dataToMove = (String) this.parameters.get("dataToMove");
		
		try{
			JavaClassElement.javaCodeBuffer.append(destVariable+"="+dataToMove+JavaConstants.DOT_WITH_COMMA);
			JavaClassElement.javaCodeBuffer.append(JavaConstants.NEW_LINE);
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
