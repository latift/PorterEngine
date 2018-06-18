package tr.com.vbt.java.loops;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import tr.com.vbt.java.AbstractJavaElement;
import tr.com.vbt.java.database.JavaFindNumberWithElement;
import tr.com.vbt.java.database.JavaFindWithElement;
import tr.com.vbt.java.database.JavaFindWithElementV2;
import tr.com.vbt.java.general.JavaClassElement;
import tr.com.vbt.java.general.JavaClassGeneral;
import tr.com.vbt.java.general.JavaConstants;
import tr.com.vbt.java.utils.ConvertUtilities;
import tr.com.vbt.java.utils.JavaWriteUtilities;
import tr.com.vbt.lexer.ConversionLogModel;
import tr.com.vbt.token.AbstractToken;


//ESCAPE TOP -->Continue;
public class JavaEscapeTopElement extends  AbstractJavaElement {
	
	final static Logger logger = Logger.getLogger(JavaEscapeTopElement.class);

	AbstractToken firstCommand;
	
	AbstractToken loopName;
	
	boolean hasVBTWhileStatement=false;
	
	boolean isContinueNotInLoop=false; //Subroutine icinde ise ve loop icinde değilse 
	
	public boolean writeJavaToStream() throws Exception{ super.writeJavaToStream();
		
		
		try {
		
			//hasVBTWhileStatement=hasStatementByName("JavaValidationElement");
			
			//isContinueNotInLoop=isStatementInLoop();
			
			if(this.parameters.get("loopName") !=null){
				loopName = (AbstractToken) this.parameters.get("loopName");
				JavaClassElement.javaCodeBuffer.append("//TODO:"+JavaWriteUtilities.toCustomString(loopName)+JavaConstants.NEW_LINE);
			}else if(this.parameters.get("FIRST_COMMAND") !=null){
				firstCommand = (AbstractToken) this.parameters.get("FIRST_COMMAND");
			}
			List errorTokenList=new ArrayList<>();
			errorTokenList.add(firstCommand);
			
			if(controlIfInFindStatement()){
				
				ConversionLogModel.getInstance().writeError(7, errorTokenList,"continue olması gereken throw new VBTContinue yazılmış");
				
			}else{
			//	ConversionLogModel.getInstance().writeError(8, errorTokenList,parentLoopElement.getJavaElementName()+" escape top controlu");
			}
			parentLoopElement=null;
			//if(hasVBTWhileStatement || isContinueNotInLoop){
				JavaClassElement.javaCodeBuffer.append("	throw new VBTContinue()"+JavaConstants.DOT_WITH_COMMA+JavaConstants.NEW_LINE);
			//}else{
			//	JavaClassElement.javaCodeBuffer.append("	continue"+JavaConstants.DOT_WITH_COMMA+JavaConstants.NEW_LINE);
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
