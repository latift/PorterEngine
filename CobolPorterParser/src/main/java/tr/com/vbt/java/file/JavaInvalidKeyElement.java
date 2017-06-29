package tr.com.vbt.java.file;

import tr.com.vbt.java.AbstractJavaElement;
import tr.com.vbt.java.general.JavaClassElement;
import tr.com.vbt.java.general.JavaConstants;



public class JavaInvalidKeyElement extends  AbstractJavaElement {
	
	
	public boolean writeJavaToStream() throws Exception{ super.writeJavaToStream();
		JavaClassElement.javaCodeBuffer.append("//Unimplemented Code:"+getClass().getName()+ JavaConstants.NEW_LINE);
		
		return true;
	}

	@Override
	public boolean writeChildrenJavaToStream() {
		return false;
	}

	

}
