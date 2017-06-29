package tr.com.vbt.java.basic;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tr.com.vbt.java.AbstractJavaElement;
import tr.com.vbt.java.general.JavaClassElement;
import tr.com.vbt.java.general.JavaConstants;
import tr.com.vbt.java.utils.ConvertUtilities;

public class JavaDisplayElement extends  AbstractJavaElement{
	List<String> dataToDisplay;
	
	final static Logger logger = LoggerFactory.getLogger(JavaDisplayElement.class);

	@Override
	public boolean writeJavaToStream() throws Exception{ super.writeJavaToStream();
		dataToDisplay = (List<String> ) this.parameters.get("dataToDisplay");
		
		
		try{
			JavaClassElement.javaCodeBuffer.append(JavaConstants.SYSTEM_OUT_PRINTLN);
			JavaClassElement.javaCodeBuffer.append(JavaConstants.OPEN_NORMAL_BRACKET);
			JavaClassElement.javaCodeBuffer.append(JavaConstants.DOUBLE_QUOTA);
			for (String dataDisplayParam : dataToDisplay) {
				JavaClassElement.javaCodeBuffer.append(dataDisplayParam+" ");
			}
			JavaClassElement.javaCodeBuffer.append(JavaConstants.DOUBLE_QUOTA);
			JavaClassElement.javaCodeBuffer.append(JavaConstants.CLOSE_NORMAL_BRACKET);
			JavaClassElement.javaCodeBuffer.append(JavaConstants.DOT_WITH_COMMA);
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
