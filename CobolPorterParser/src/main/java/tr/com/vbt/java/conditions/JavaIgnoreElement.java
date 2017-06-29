package tr.com.vbt.java.conditions;

import tr.com.vbt.java.AbstractJavaElement;



public class JavaIgnoreElement extends  AbstractJavaElement {
	
	public boolean writeJavaToStream() throws Exception{ super.writeJavaToStream();
		return true;
	}
}
