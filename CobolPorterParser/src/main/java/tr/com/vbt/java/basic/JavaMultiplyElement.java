package tr.com.vbt.java.basic;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tr.com.vbt.java.AbstractJavaElement;
import tr.com.vbt.java.general.JavaClassElement;
import tr.com.vbt.java.general.JavaConstants;
import tr.com.vbt.java.utils.ConvertUtilities;


//MULTIPLY A BY B C --> 	B=A*B, C=A*C
public class JavaMultiplyElement extends  AbstractJavaElement {
	
	final static Logger logger = LoggerFactory.getLogger(JavaMultiplyElement.class);

	private String sourceNum=new String();  //A
	
	private List<String> destNum=new ArrayList<String>(); //B, C
	
	public boolean writeJavaToStream() throws Exception{ super.writeJavaToStream();
		sourceNum=(String) this.getParameters().get("sourceNum");
		
		destNum=(List<String>) this.getParameters().get("destNum");
		 
		try{
			for (String destNum1 : destNum) {
				
				JavaClassElement.javaCodeBuffer.append(destNum1+"="+sourceNum+" * "+destNum1+JavaConstants.DOT_WITH_COMMA+JavaConstants.NEW_LINE);
				
			}
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
		return false;
	}

}
