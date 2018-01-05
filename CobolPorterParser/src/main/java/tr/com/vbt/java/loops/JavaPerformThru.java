package tr.com.vbt.java.loops;

import org.apache.log4j.Logger;


import tr.com.vbt.java.AbstractJavaElement;
import tr.com.vbt.java.general.JavaClassElement;
import tr.com.vbt.java.general.JavaConstants;
import tr.com.vbt.java.utils.ConvertUtilities;


//PERFORM PARAGRAPH1 THRU PARAGRAPH4  --> PARAGRAPH1(); PARAGRAPH2(); PARAGRAPH3(); PARAGRAPH4();
public class JavaPerformThru extends  AbstractJavaElement {
	
	final static Logger logger = Logger.getLogger(JavaPerformThru.class);
	
	private String paragraghName;
	
	private String runCount;
	
	public boolean writeJavaToStream() throws Exception{ super.writeJavaToStream();
		try {
			paragraghName = (String) this.parameters.get("paragraghName");
			runCount = (String) this.parameters.get("runCount");
			JavaClassElement.javaCodeBuffer.append("for (int i=0; i<"+runCount+"; i++)"+JavaConstants.OPEN_BRACKET+JavaConstants.NEW_LINE);
			JavaClassElement.javaCodeBuffer.append(JavaConstants.TAB+ paragraghName+"();"+ JavaConstants.NEW_LINE);
			JavaClassElement.javaCodeBuffer.append(JavaConstants.CLOSE_BRACKET+JavaConstants.NEW_LINE);
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
