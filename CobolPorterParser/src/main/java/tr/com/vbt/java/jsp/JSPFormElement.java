package tr.com.vbt.java.jsp;

import java.util.Iterator;
import java.util.List;

import tr.com.vbt.java.AbstractJavaElement;
import tr.com.vbt.java.general.JavaConstants;
import tr.com.vbt.java.jsp.general.JSPConstants;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.KarakterToken;


/**
 *  INPUT (AD=MT) 	-> 		 <form method="get" name="input1">
 * 
 *
 *INPUT="[
 *  Token:( 	TokenTip :Karakter, 
 	Token:AD  	TokenTip :Kelime, 
 	Token:=  	TokenTip :Karakter, 
 	Token:MT  	TokenTip :Kelime, 
 	Token:)  	TokenTip :Karakter, 
 	Token:Start:  TokenTip :Kelime, 
 	Token:#  	TokenTip :Karakter, 
 	Token:NAME-START  TokenTip :Kelime, 
 	Token:/  	TokenTip :Karakter, 
 	Token:End:  TokenTip :Kelime, 
 	Token:#  	TokenTip :Karakter, 
 	Token:NAME-END  TokenTip :Kelime, 
 	Token:WRITE  TokenTip :Kelime, 
 	Token:#  	TokenTip :Karakter, 
 	Token:NAME-START  TokenTip :Kelime, 
 	Token:#  	TokenTip :Karakter, 
 	Token:NAME-END  TokenTip :Kelime]
 	
	Kural: # Görünce sonrakinin textfield ini yarat.
	# görmedi isen label yarat.
	/ görürsen <br> yarat
 */


public class JSPFormElement extends  AbstractJavaElement{

	private String formName="form1";
	
	private List<AbstractToken> inputParameters;
	
	private List<AbstractToken> inputParametersImportant;
	
	
	
	public String getFormName() {
		return formName;
	}



	public void setFormName(String formName) {
		this.formName = formName;
	}



	@Override
	public boolean writeJavaToStream() throws Exception{ super.writeJavaToStream();
		inputParameters = (List<AbstractToken> ) this.parameters.get("inputParameters");
		removeUnimportantedTokens();
		
		
		AbstractJavaElement.javaCodeBuffer.append(JSPConstants.FORM_OPEN+" "+JSPConstants.METHOD+"=\"get\" "+JSPConstants.NAME+"=\"" +formName+"\">" + JavaConstants.NEW_LINE );
		AbstractToken currentToken, nextToken;
		Iterator<AbstractToken> iter1= inputParametersImportant.iterator();
		while(iter1.hasNext()){
			currentToken=iter1.next();
			/**
			 *  <!-- #NAME-START -->  <input type="text" name="NAME-START">
	    
			 * Kural: # Görünce sonrakinin textfield ini yarat.
				# görmedi isen label yarat.
				/ görürsen <br> yarat
			 */
			if(currentToken.getDeger().equals('/')){
				AbstractJavaElement.javaCodeBuffer.append("<br>");
			}else if(currentToken.getDeger().equals('#')){
				nextToken=iter1.next();
				AbstractJavaElement.javaCodeBuffer.append("<input type=\"text\" name=\""+nextToken.getDeger().toString().replace("-", "_") +"\">");
			}else{
				AbstractJavaElement.javaCodeBuffer.append(currentToken.getDeger());
			}
		}
		AbstractJavaElement.javaCodeBuffer.append(JSPConstants.SUBMIT_INPUT_TYPE);// <input type="submit" value="Ok">
		AbstractJavaElement.javaCodeBuffer.append(JSPConstants.FORM_CLOSE_TAG+ JavaConstants.NEW_LINE );
		return true;
	}



	private void removeUnimportantedTokens() {
		
		int startIndex=0, index=0;
		
		AbstractToken parantezKapatToken=new KarakterToken((char)')',0,0,0);
		
		for (AbstractToken abstractToken : inputParameters) {
			if(abstractToken.tokenMatchs(parantezKapatToken)){
				startIndex=index;
				break;
			}
			index++;
		}
		
		inputParametersImportant=inputParameters.subList(startIndex+1, inputParameters.size());
	}
	
	
	

}
