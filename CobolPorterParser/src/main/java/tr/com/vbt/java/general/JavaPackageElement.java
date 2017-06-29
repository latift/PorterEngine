package tr.com.vbt.java.general;

import tr.com.vbt.java.AbstractJavaElement;


public class JavaPackageElement extends AbstractJavaElement{

	String packageFolder;
	
	
	public boolean writeJavaToStream() throws Exception{ super.writeJavaToStream();
		JavaPackageElement.javaCodeBuffer.append(JavaConstants.PACKAGE+ " "+ packageFolder+ JavaConstants.DOT_WITH_COMMA+"\n");
		System.out.println(JavaPackageElement.javaCodeBuffer);
		return true;
	}
}
