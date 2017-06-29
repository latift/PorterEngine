package tr.com.vbt.java.file;

import tr.com.vbt.java.AbstractJavaElement;
import tr.com.vbt.java.general.JavaClassElement;
import tr.com.vbt.java.general.JavaConstants;



// READ file-name NEXT RECORD INTO ws-file-structure  -->
//while ((c = file-name.read()) != -1) {

public class JavaReadFileElement extends  AbstractJavaElement {
	
	private String fileName;
	
	private String fileStructure; //optional
	
	private String recordKeyIs;
	
	private String organizationMode;
	
	private String accessMode;
	
	
	public boolean writeJavaToStream() throws Exception{ super.writeJavaToStream();
		
		fileName=(String) this.getParameters().get("fileName");
		
		fileStructure=(String) this.getParameters().get(fileStructure);
		
		recordKeyIs=(String) this.getParameters().get(recordKeyIs);
		 
		/*JavaClassElement.javaCodeBuffer.append("while((c="+ fileName+".read())  !=-1  ){"+JavaConstants.NEW_LINE);
		
		this.writeChildrenJavaToStream();
		
		JavaClassElement.javaCodeBuffer.append("};"+JavaConstants.NEW_LINE);
		*/
		JavaClassElement.javaCodeBuffer.append("//Unimplemented Code:"+getClass().getName()+ JavaConstants.NEW_LINE);
		
		return true;
	}

}
