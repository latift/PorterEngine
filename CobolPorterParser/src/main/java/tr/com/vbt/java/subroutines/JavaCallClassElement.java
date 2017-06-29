package tr.com.vbt.java.subroutines;

import java.util.List;

import tr.com.vbt.java.AbstractJavaElement;
import tr.com.vbt.token.AbstractToken;


public class JavaCallClassElement extends  AbstractJavaElement{

	//Paramaters: functionName;
	private List<AbstractToken> inputParameters; 
	
	
	@Override
	public boolean writeJavaToStream() throws Exception{ super.writeJavaToStream();

		inputParameters=(List<AbstractToken>) this.parameters.get("inputParameters");
		
		/*if(paragraphName==null||paragraphName==""){     //Coboldaki PAragrafda Sectionda Java daki functiona denk gelir. 
			paragraphName=(String) this.parameters.get("sectionName");
		}
		
		JavaClassElement.javaCodeBuffer.append(paragraphName+JavaConstants.OPEN_NORMAL_BRACKET+JavaConstants.CLOSE_NORMAL_BRACKET+JavaConstants.DOT_WITH_COMMA+JavaConstants.NEW_LINE);
		*/
		return true;
	}

}
