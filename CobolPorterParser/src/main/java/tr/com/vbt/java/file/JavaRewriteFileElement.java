package tr.com.vbt.java.file;

import tr.com.vbt.java.AbstractJavaElement;
import tr.com.vbt.java.general.JavaClassElement;
import tr.com.vbt.java.general.JavaConstants;



// OPEN INPUT   RMFFILE -->  FileReader fileReader = new FileReader(fileName);  BufferedReader bufferedReader =new BufferedReader(fileReader);
public class JavaRewriteFileElement extends  AbstractJavaElement {
	
	private String fileName;
	
	private String mode;
	
	public boolean writeJavaToStream() throws Exception{ super.writeJavaToStream();
		fileName=(String) this.getParameters().get("fileName");
		JavaClassElement.javaCodeBuffer.append("FileReader fileReader = new FileReader(");
		JavaClassElement.javaCodeBuffer.append(fileName);
		JavaClassElement.javaCodeBuffer.append(");"+JavaConstants.NEW_LINE);
		JavaClassElement.javaCodeBuffer.append("BufferedReader bufferedReader =new BufferedReader(fileReader)");
		JavaClassElement.javaCodeBuffer.append(JavaConstants.DOT_WITH_COMMA+JavaConstants.NEW_LINE);
		return true;
	}

	@Override
	public boolean writeChildrenJavaToStream() {
		// TODO Auto-generated method stub
		return false;
	}

	

}
