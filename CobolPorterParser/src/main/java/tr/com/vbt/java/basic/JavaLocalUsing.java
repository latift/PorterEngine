package tr.com.vbt.java.basic;

import org.apache.log4j.Logger;


import tr.com.vbt.java.AbstractJavaElement;
import tr.com.vbt.java.general.JavaClassElement;
import tr.com.vbt.java.general.JavaConstants;
import tr.com.vbt.java.utils.ConvertUtilities;

public class JavaLocalUsing extends AbstractJavaElement {

	final static Logger logger = Logger.getLogger(JavaLocalUsing.class);
	
	String localParameterName;

	@Override
	public boolean writeJavaToStream() throws Exception{
		super.writeJavaToStream();
		localParameterName = (String) this.parameters.get("localParameterName");

		try {
		/*	JavaClassElement.javaCodeBuffer.append(localParameterName);
			JavaClassElement.javaCodeBuffer.append(" ");
			JavaClassElement.javaCodeBuffer.append(localParameterName);
			JavaClassElement.javaCodeBuffer.append("= new ");
			JavaClassElement.javaCodeBuffer.append(localParameterName);
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
