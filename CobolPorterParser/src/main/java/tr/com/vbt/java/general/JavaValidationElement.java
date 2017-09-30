package tr.com.vbt.java.general;

import tr.com.vbt.java.AbstractJava;
import tr.com.vbt.java.AbstractJavaElement;

public class JavaValidationElement extends AbstractJavaElement {

	public boolean writeJavaToStream() throws Exception{
		super.writeJavaToStream();
		
		if(!logModel.isMapOrMapTester()){
			addValidationLoopStarter();
		}
		this.writeChildrenJavaToStream();
		
		if(!logModel.isMapOrMapTester()){
			addValidationLoopEnder();
		}
		return true;
	
	}
	

	private void addValidationLoopStarter() {
			JavaClassElement.javaCodeBuffer.append("validationError = true"+JavaConstants.DOT_WITH_COMMA+JavaConstants.NEW_LINE);	
			JavaClassElement.javaCodeBuffer.append("while (this.validationError) {"+JavaConstants.DOT_WITH_COMMA+JavaConstants.NEW_LINE);
			JavaClassElement.javaCodeBuffer.append("    breakControl = false"+JavaConstants.DOT_WITH_COMMA+JavaConstants.NEW_LINE);	
			JavaClassElement.javaCodeBuffer.append("	validationError = false"+JavaConstants.DOT_WITH_COMMA+JavaConstants.NEW_LINE);
			JavaClassElement.javaCodeBuffer.append("	try {"+JavaConstants.DOT_WITH_COMMA+JavaConstants.NEW_LINE);
			
			JavaClassElement.javaCodeBuffer.append("if(continueControl){"+JavaConstants.NEW_LINE);
			JavaClassElement.javaCodeBuffer.append("	break"+JavaConstants.DOT_WITH_COMMA+JavaConstants.NEW_LINE);
			JavaClassElement.javaCodeBuffer.append("}//Continue Kontrol"+JavaConstants.NEW_LINE);
	}

	
	private void addValidationLoopEnder() {
		JavaClassElement.javaCodeBuffer.append("	} catch (VBTValidationException e) { // TODO:Bu satır ve altindaki 3 satir. Bu ekranla ilgili son showDialogV2 den sonraya taşınmalı"+JavaConstants.DOT_WITH_COMMA+JavaConstants.NEW_LINE);
		JavaClassElement.javaCodeBuffer.append(""+JavaConstants.DOT_WITH_COMMA+JavaConstants.NEW_LINE);
		JavaClassElement.javaCodeBuffer.append("	}//Validation Catch"+JavaConstants.DOT_WITH_COMMA+JavaConstants.NEW_LINE);
		JavaClassElement.javaCodeBuffer.append("}//Validation While"+JavaConstants.DOT_WITH_COMMA+JavaConstants.NEW_LINE);
		
		JavaClassElement.javaCodeBuffer.append("if(breakControl){"+JavaConstants.NEW_LINE);
		JavaClassElement.javaCodeBuffer.append("	breakControl=false"+JavaConstants.DOT_WITH_COMMA+JavaConstants.NEW_LINE);
		JavaClassElement.javaCodeBuffer.append("	break"+JavaConstants.DOT_WITH_COMMA+JavaConstants.NEW_LINE);
		JavaClassElement.javaCodeBuffer.append("}//Break Kontrol"+JavaConstants.NEW_LINE);
		
		JavaClassElement.javaCodeBuffer.append("if(continueControl){"+JavaConstants.NEW_LINE);
		JavaClassElement.javaCodeBuffer.append("	continueControl=false"+JavaConstants.DOT_WITH_COMMA+JavaConstants.NEW_LINE);
		JavaClassElement.javaCodeBuffer.append("	continue"+JavaConstants.DOT_WITH_COMMA+JavaConstants.NEW_LINE);
		JavaClassElement.javaCodeBuffer.append("}//Continue Kontrol"+JavaConstants.NEW_LINE);
		
		
	}
}
