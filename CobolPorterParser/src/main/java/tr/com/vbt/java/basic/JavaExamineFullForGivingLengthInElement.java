package tr.com.vbt.java.basic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tr.com.vbt.java.AbstractJavaElement;
import tr.com.vbt.java.general.JavaClassElement;
import tr.com.vbt.java.general.JavaConstants;
import tr.com.vbt.java.utils.ConvertUtilities;
import tr.com.vbt.java.utils.JavaWriteUtilities;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.util.CustomStringUtils;


// EXAMINE #LIMANLARXV(*) FOR #R-LIMAN GIVING INDEX LIMANINDEX --> LIMANINDEX=searchArray(LIMANLARXV, R-LIMAN );
public class JavaExamineFullForGivingLengthInElement extends  AbstractJavaElement {
	
	final static Logger logger = LoggerFactory.getLogger(JavaExamineFullForGivingLengthInElement.class);
	
	private AbstractToken arrayToken;
	
	private AbstractToken searchVar;
	
	private String resultIndex;
	
	
	public boolean writeJavaToStream() throws Exception{ super.writeJavaToStream();
		arrayToken= (AbstractToken) this.getParameters().get("array");
		
		searchVar= (AbstractToken) this.getParameters().get("searchVar");
		
		resultIndex= (String) this.getParameters().get("resultIndex");
		
		
		
		try{
			//LIMANINDEX=searchArray(
			JavaClassElement.javaCodeBuffer.append(CustomStringUtils.replaceMiddleLineWithSubLine(resultIndex)+"=searchArray(");
			
			JavaClassElement.javaCodeBuffer.append(JavaWriteUtilities.toCustomString(arrayToken));
			JavaClassElement.javaCodeBuffer.append(",");
			JavaClassElement.javaCodeBuffer.append(JavaWriteUtilities.toCustomString(searchVar));
			JavaClassElement.javaCodeBuffer.append(")");
			JavaClassElement.javaCodeBuffer.append(JavaConstants.DOT_WITH_COMMA+JavaConstants.NEW_LINE);
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
