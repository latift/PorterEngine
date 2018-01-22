package tr.com.vbt.java.basic;

import org.apache.log4j.Logger;


import tr.com.vbt.java.AbstractJavaElement;
import tr.com.vbt.java.general.JavaClassElement;
import tr.com.vbt.java.general.JavaConstants;
import tr.com.vbt.java.utils.ConvertUtilities;
import tr.com.vbt.java.utils.JavaWriteUtilities;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.util.CustomStringUtils;


// EXAMINE FULL PADI1 FOR ' '  REPLACE WITH '*' --> PAD1=PAD1.replaceAll(' ','*');
public class JavaExamineFullForReplaceWithElement extends  AbstractJavaElement {
	
	final static Logger logger = Logger.getLogger(JavaExamineFullForReplaceWithElement.class);
	
	private AbstractToken sourceToken;
	
	private AbstractToken searchVar;
	
	private AbstractToken replaceVar;
	
	
	public boolean writeJavaToStream() throws Exception{ super.writeJavaToStream();
		
		sourceToken= (AbstractToken) this.getParameters().get("sourceToken");
		
		searchVar= (AbstractToken) this.getParameters().get("searchVar");
		
		replaceVar= (AbstractToken) this.getParameters().get("replaceVar");
		
		
		
		try{
			//LIMANINDEX=searchArray(
			JavaClassElement.javaCodeBuffer.append(JavaWriteUtilities.toCustomString(sourceToken)+"=");
			
			JavaClassElement.javaCodeBuffer.append(JavaWriteUtilities.toCustomString(sourceToken)+".replaceAll(");
			JavaClassElement.javaCodeBuffer.append(JavaWriteUtilities.toCustomString(searchVar));
			JavaClassElement.javaCodeBuffer.append(",");
			JavaClassElement.javaCodeBuffer.append(JavaWriteUtilities.toCustomString(replaceVar));
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
