package tr.com.vbt.java.file;

import java.util.ArrayList;
import java.util.List;

import tr.com.vbt.java.AbstractJavaElement;
import tr.com.vbt.java.general.JavaClassElement;
import tr.com.vbt.java.general.JavaConstants;



// OPEN INPUT   RMFFILE -->  FileReader fileReader = new FileReader(fileName);  BufferedReader bufferedReader =new BufferedReader(fileReader);
public class JavaCloseFile extends  AbstractJavaElement {
	
	private List<String> fileNameList=new ArrayList<String>();
	
	public boolean writeJavaToStream() throws Exception{ super.writeJavaToStream();
		fileNameList = (List<String> ) this.parameters.get("fileNameList");
		
		for (String fileName : fileNameList) {
			JavaClassElement.javaCodeBuffer.append(fileName+".close()");
			JavaClassElement.javaCodeBuffer.append(JavaConstants.DOT_WITH_COMMA);
			JavaClassElement.javaCodeBuffer.append(JavaConstants.NEW_LINE);
		}
		return true;
	}

	@Override
	public boolean writeChildrenJavaToStream() {
		// TODO Auto-generated method stub
		return false;
	}

	

}
