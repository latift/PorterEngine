package tr.com.vbt.java.general;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tr.com.vbt.java.AbstractJavaElement;
import tr.com.vbt.java.general.JavaClassElement;
import tr.com.vbt.java.general.JavaConstants;
import tr.com.vbt.java.utils.ConvertUtilities;
import tr.com.vbt.java.utils.JavaWriteUtilities;
import tr.com.vbt.token.AbstractToken;

//SET KEY PF3 PF4 

//SET KEY PF11='%W>'   --> registerPFKey("PF3", "Yeni Hareket", false,true, "W");
public class JavaSetKeyElement extends  AbstractJavaElement {
	
	final static Logger logger = LoggerFactory.getLogger(JavaSetKeyElement.class);

	private List<AbstractToken> parametersOfSetKey;
	
	public boolean writeJavaToStream() throws Exception{ super.writeJavaToStream();
		
		try {
			parametersOfSetKey =  (List<AbstractToken>) this.parameters.get("parametersOfSetKey");
		
			parametersOfSetKey.get(0).setConstantVariableWithQuota(true);
			String pfKey = parametersOfSetKey.get(0).toString();
			
			String label="";
			
			boolean isVisible=true;
			
			boolean isActive=true;
			
			String shortCut="";
			
			String hotKey="";
			
			//	protected void registerPFKey(String PFKey, String label, boolean visible, boolean isActive, String shortCut, String hotKey) {
			JavaClassElement.javaCodeBuffer.append("registerPFKey("+JavaWriteUtilities.toCustomString(parametersOfSetKey.get(0))+",\""+label+"\","+isVisible+ ","+isActive+ ",\""+shortCut+"\",\""+hotKey+"\");"+JavaConstants.NEW_LINE);
			
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
