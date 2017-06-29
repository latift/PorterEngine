package tr.com.vbt.java.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tr.com.vbt.java.AbstractJavaElement;
import tr.com.vbt.java.general.JavaClassElement;
import tr.com.vbt.java.general.JavaConstants;



public class JavaAtEndElement extends  AbstractJavaElement {
	
	final static Logger logger = LoggerFactory.getLogger(JavaAtEndElement.class);
	
	public boolean writeJavaToStream() throws Exception{ super.writeJavaToStream();
		JavaClassElement.javaCodeBuffer.append("//Unimplemented Code:"+getClass().getName()+ JavaConstants.NEW_LINE);
		
		return true;
	}

	@Override
	public boolean writeChildrenJavaToStream() {
		return false;
	}

	

}
