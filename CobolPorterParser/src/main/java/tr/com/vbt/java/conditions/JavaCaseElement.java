package tr.com.vbt.java.conditions;

import tr.com.vbt.java.AbstractJavaElement;
import tr.com.vbt.java.general.JavaClassElement;
import tr.com.vbt.java.general.JavaConstants;
import tr.com.vbt.java.utils.JavaWriteUtilities;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.KelimeToken;
import tr.com.vbt.token.KeyValueOzelKelimeToken;


//
public class JavaCaseElement extends  AbstractJavaElement {
	
	private AbstractToken valueToken;
	
	public boolean writeJavaToStream() throws Exception{ 
		super.writeJavaToStream();
		valueToken = (AbstractToken) this.parameters.get("VALUE");
		
		JavaClassElement.javaCodeBuffer.append("case "+JavaWriteUtilities.toCustomString(valueToken)+":" + JavaConstants.NEW_LINE);	
		this.writeChildrenJavaToStream();
		JavaClassElement.javaCodeBuffer.append("break"+JavaConstants.DOT_WITH_COMMA+ JavaConstants.NEW_LINE);	
		
		return true;
	}

}
