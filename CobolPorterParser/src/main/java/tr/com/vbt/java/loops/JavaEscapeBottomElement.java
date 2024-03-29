package tr.com.vbt.java.loops;

import org.apache.log4j.Logger;


import tr.com.vbt.java.AbstractJavaElement;
import tr.com.vbt.java.database.JavaIfNoRecordsElement;
import tr.com.vbt.java.general.JavaClassElement;
import tr.com.vbt.java.general.JavaConstants;
import tr.com.vbt.java.utils.ConvertUtilities;
import tr.com.vbt.java.utils.JavaWriteUtilities;
import tr.com.vbt.token.AbstractToken;


//ESCAPE TOP -->break;
public class JavaEscapeBottomElement extends  AbstractJavaElement {
	
	final static Logger logger = Logger.getLogger(JavaEscapeBottomElement.class);

	AbstractToken loopName;
	
	//boolean hasVBTWhileStatement=false;
	
	//boolean isContinueNotInLoop=false; //Subroutine icinde ise ve loop icinde değilse 
	
	public boolean writeJavaToStream() throws Exception{ super.writeJavaToStream();
		
		try {
			
			//hasVBTWhileStatement=hasStatementByName("JavaValidationElement");
			
			//isContinueNotInLoop=isStatementInLoop();
			
			if(this.parent instanceof JavaIfNoRecords || this.parent instanceof JavaIfNoRecordsElement){
				return true;
			}
			
			if(this.parameters.get("loopName") !=null){
				loopName = (AbstractToken) this.parameters.get("loopName");
				JavaClassElement.javaCodeBuffer.append("//TODO:"+JavaWriteUtilities.toCustomString(loopName)+JavaConstants.NEW_LINE);
			}
			
			//if(hasVBTWhileStatement || isContinueNotInLoop){
				JavaClassElement.javaCodeBuffer.append("	throw new VBTBreak()"+JavaConstants.DOT_WITH_COMMA+JavaConstants.NEW_LINE);
			//}else{
			//	JavaClassElement.javaCodeBuffer.append("	break"+JavaConstants.DOT_WITH_COMMA+JavaConstants.NEW_LINE);
			//}
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
