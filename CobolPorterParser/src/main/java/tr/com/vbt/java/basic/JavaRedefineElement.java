package tr.com.vbt.java.basic;

import tr.com.vbt.java.AbstractJavaElement;


public class JavaRedefineElement extends  AbstractJavaElement{

	@Override
	public boolean writeJavaToStream() throws Exception{ 
		
		super.writeJavaToStream();

		return true;
	}

}
