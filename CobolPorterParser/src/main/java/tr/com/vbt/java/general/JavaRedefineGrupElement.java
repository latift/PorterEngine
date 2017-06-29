package tr.com.vbt.java.general;

import tr.com.vbt.java.AbstractJavaElement;

public class JavaRedefineGrupElement extends AbstractJavaElement {


	@Override
	public boolean writeJavaToStream() throws Exception{ super.writeJavaToStream();

		this.writeChildrenJavaToStream();
		
		return true;
	}

}
