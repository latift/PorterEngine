package tr.com.vbt.java.conditions;

import tr.com.vbt.java.AbstractJavaElement;
import tr.com.vbt.java.general.JavaClassElement;
import tr.com.vbt.java.general.JavaConstants;


//
public class JavaDefaultElement extends  AbstractJavaElement {
	
	
	public boolean writeJavaToStream() throws Exception{ 
		super.writeJavaToStream();
		JavaClassElement.javaCodeBuffer.append("default :"+ JavaConstants.NEW_LINE);
		this.writeChildrenJavaToStream();
		return true;
	}

}

