package tr.com.vbt.java.basic;

import tr.com.vbt.java.AbstractJavaElement;
import tr.com.vbt.java.general.JavaClassElement;
import tr.com.vbt.java.general.JavaConstants;



//Stop Run--> Return();
public class JavaReturnElement extends  AbstractJavaElement {
	
	
	public boolean writeJavaToStream() throws Exception{ super.writeJavaToStream();
		JavaClassElement.javaCodeBuffer.append(JavaConstants.RETURN+JavaConstants.DOT_WITH_COMMA);
		JavaClassElement.javaCodeBuffer.append(JavaConstants.NEW_LINE);
		return true;
	}

	@Override
	public boolean writeChildrenJavaToStream() {
		// TODO Auto-generated method stub
		return false;
	}

}
