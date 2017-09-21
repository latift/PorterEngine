package tr.com.vbt.java.screen;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tr.com.vbt.java.AbstractJavaElement;
import tr.com.vbt.java.general.JavaClassElement;
import tr.com.vbt.java.general.JavaConstants;
import tr.com.vbt.java.utils.ConvertUtilities;
import tr.com.vbt.lexer.ConversionLogModel;
import tr.com.vbt.token.AbstractToken;


// // 1078   INPUT USING MAP 'IDGMMUSS' -->

/**
 *  	shortProgram.inputUsingMap("IDGMMUSS", mapFactory);
 * 
 *
 */
public class JavaInputUsingMapElement extends  AbstractJavaElement {
	
	final static Logger logger = LoggerFactory.getLogger(JavaAtTopOfPageElement.class);
	
	private String mapName;
	
	private AbstractToken markToken;
	
	public boolean writeJavaToStream() throws Exception{ super.writeJavaToStream();
		mapName=  (String) this.getParameters().get("mapName");
		
		if(this.getParameters().get("markToken")!=null){
			markToken= (AbstractToken) this.getParameters().get("markToken");
		}
		
		try {
			
//			validationError = true;
//			while (this.validationError) {
//				validationError = false;
//				try {
					
			addValidationLoopStarter();
			
				
			//JavaClassElement.javaCodeBuffer.append("=openScreen("+mapName+")"+JavaConstants.DOT_WITH_COMMA+JavaConstants.NEW_LINE);
			JavaClassElement.javaCodeBuffer.append("	inputUsingMap(\""+mapName+"\")"+JavaConstants.DOT_WITH_COMMA+JavaConstants.NEW_LINE);
	
			addValidationLoopEnder();
			
//		} catch (VBTValidationException e) {
//
//		}
//	}
			registerMapToProgramMapList();
		} catch (Exception e) {
			logger.debug("//Conversion Error"+this.getClass()+this.getSourceCode().getSatirNumarasi()+this.getSourceCode().getCommandName());
			JavaClassElement.javaCodeBuffer.append("/*Conversion Error"+this.getClass()+this.getSourceCode().getSatirNumarasi()
					+this.getSourceCode().getCommandName()+"*/"+JavaConstants.NEW_LINE);
			logger.error("//Conversion Error:"+e.getMessage(), e); 
			ConvertUtilities.writeconversionErrors(e, this);
		}
		
		return true;
	}
	

	private void addValidationLoopEnder() {
		JavaClassElement.javaCodeBuffer.append("	} catch (VBTValidationException e) { // TODO:Bu satır ve altindaki 3 satir. Bu ekranla ilgili son showDialogV2 den sonraya taşınmalı"+JavaConstants.DOT_WITH_COMMA+JavaConstants.NEW_LINE);
		JavaClassElement.javaCodeBuffer.append(""+JavaConstants.NEW_LINE);
		JavaClassElement.javaCodeBuffer.append("	}//Validation Catch"+JavaConstants.DOT_WITH_COMMA+JavaConstants.NEW_LINE);
		JavaClassElement.javaCodeBuffer.append("}//Validation While"+JavaConstants.DOT_WITH_COMMA+JavaConstants.NEW_LINE);
		
	}



	private void addValidationLoopStarter() {
		JavaClassElement.javaCodeBuffer.append("validationError = true"+JavaConstants.DOT_WITH_COMMA+JavaConstants.NEW_LINE);	
		JavaClassElement.javaCodeBuffer.append("while (this.validationError) {"+JavaConstants.DOT_WITH_COMMA+JavaConstants.NEW_LINE);
		JavaClassElement.javaCodeBuffer.append("	validationError = false"+JavaConstants.DOT_WITH_COMMA+JavaConstants.NEW_LINE);
		JavaClassElement.javaCodeBuffer.append("	try {"+JavaConstants.DOT_WITH_COMMA+JavaConstants.NEW_LINE);
		
	}

	private void registerMapToProgramMapList() {
		String programName=ConversionLogModel.getInstance().getFileName();
		logger.debug("At to Program Map Relation: "+programName+" "+mapName); 
		ConversionLogModel.getInstance().registerInputUsingMap(programName, mapName);
		
	}

	@Override
	public boolean writeChildrenJavaToStream() {
		// TODO Auto-generated method stub
		return false;
	}

	

}
