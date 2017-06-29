package tr.com.vbt.java.file;

import java.util.ArrayList;
import java.util.List;

import tr.com.vbt.java.AbstractJavaElement;
import tr.com.vbt.java.general.JavaClassElement;
import tr.com.vbt.java.general.JavaConstants;



// OPEN INPUT   RMFFILE -->  FileReader fileReader = new FileReader(fileName);  BufferedReader bufferedReader =new BufferedReader(fileReader);
public class JavaFileOpenElement extends  AbstractJavaElement {
	
private List<String> fileNameList=new ArrayList<String>();
	
	private String mode;
	
	public boolean writeJavaToStream() throws Exception{ super.writeJavaToStream();
		
		fileNameList=(List<String>) this.getParameters().get("fileNameList");
		mode=(String) this.getParameters().get("mode");
		
		if(mode.equals("INPUT")){
				for (String fileName : fileNameList) {
					
					JavaClassElement.javaCodeBuffer.append("FileReader fileReader"+fileName+" = new FileReader(");
					JavaClassElement.javaCodeBuffer.append(fileName);
					JavaClassElement.javaCodeBuffer.append(");"+JavaConstants.NEW_LINE);
					JavaClassElement.javaCodeBuffer.append("BufferedReader bufferedReader =new BufferedReader(fileReader"+fileName+")");
					JavaClassElement.javaCodeBuffer.append(JavaConstants.DOT_WITH_COMMA+JavaConstants.NEW_LINE);
				}
		}else if(mode.equals("OUTPUT")){
			for (String fileName : fileNameList) {
				
				JavaClassElement.javaCodeBuffer.append("FileWriter fileReader"+fileName+" = new FileWriter(");
				JavaClassElement.javaCodeBuffer.append(fileName);
				JavaClassElement.javaCodeBuffer.append(");"+JavaConstants.NEW_LINE);
				JavaClassElement.javaCodeBuffer.append("BufferedReader bufferedReader =new BufferedReader(fileReader"+fileName+")");
				JavaClassElement.javaCodeBuffer.append(JavaConstants.DOT_WITH_COMMA+JavaConstants.NEW_LINE);
			}
		}
		return true;
	}

	@Override
	public boolean writeChildrenJavaToStream() {
		// TODO Auto-generated method stub
		return false;
	}

	

}
