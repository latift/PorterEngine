package tr.com.vbt.java.general;

import java.util.ArrayList;
import java.util.List;

import tr.com.vbt.java.AbstractJavaElement;


public class JavaImport extends  AbstractJavaElement{

	public List<String> importClasses=new ArrayList<String>();
	
	public boolean writeJavaToStream() throws Exception{ super.writeJavaToStream();
		this.javaCodeBuffer.append("\n");
		for (String importClass : importClasses) {
			this.javaCodeBuffer.append(JavaConstants.IMPORT_STRING+ " "+ importClass+ JavaConstants.DOT_WITH_COMMA+"\n");
		}
		System.out.println(JavaImport.javaCodeBuffer);
		return true;
	}
}
