package tr.com.vbt.java.basic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tr.com.vbt.java.AbstractJavaElement;
import tr.com.vbt.java.general.JavaClassElement;
import tr.com.vbt.java.general.JavaConstants;
import tr.com.vbt.java.utils.ConvertUtilities;

//	TPSCNT01 tpsCnt01 = new TPSCNT01();
// NATURAL CODE:2 :GLOBAL USING TOPSCRG1 
public class JavaGlobalUsing extends  AbstractJavaElement{
	
	final static Logger logger = LoggerFactory.getLogger(JavaGlobalUsing.class);
	
	String globalParameterName;

	@Override
	public boolean writeJavaToStream() throws Exception{ 
		super.writeJavaToStream();
		globalParameterName = (String) this.parameters.get("globalParameterName");
		
		
		try{
			/*JavaClassElement.javaCodeBuffer.append(globalParameterName);
			JavaClassElement.javaCodeBuffer.append(" ");
			JavaClassElement.javaCodeBuffer.append(globalParameterName);
			JavaClassElement.javaCodeBuffer.append("= new ");
			JavaClassElement.javaCodeBuffer.append(globalParameterName);
			JavaClassElement.javaCodeBuffer.append(JavaConstants.OPEN_NORMAL_BRACKET);
			JavaClassElement.javaCodeBuffer.append(JavaConstants.CLOSE_NORMAL_BRACKET);
			JavaClassElement.javaCodeBuffer.append(JavaConstants.DOT_WITH_COMMA);
			JavaClassElement.javaCodeBuffer.append(JavaConstants.NEW_LINE);*/
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
