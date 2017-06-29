package tr.com.vbt.java.file;

import tr.com.vbt.java.AbstractJavaElement;
import tr.com.vbt.java.general.JavaClassElement;
import tr.com.vbt.java.general.JavaConstants;



// OPEN INPUT   RMFFILE -->  FileReader fileReader = new FileReader(fileName);  BufferedReader bufferedReader =new BufferedReader(fileReader);
public class JavaSelectFileElement extends  AbstractJavaElement {
	
	private String fileName;
	private String assignedTo;
	private String organizationType;
	private String accessMode;
	private String fileStatus; 
	private String recordKey;
	
	public boolean writeJavaToStream() throws Exception{ super.writeJavaToStream();
		
		// ORGANIZATION IS RELATIVE
	    // ACCESS MODE IS DYNAMIC
	    //Direct Read
		/*READ SupplierFile
          INVALID KEY DISPLAY "SUPP STATUS :-", SupplierStatus
        END-READ*/
		
		
		
		// ORGANIZATION IS RELATIVE
	    // ACCESS MODE IS DYNAMIC
		/* Sequeantial Read
		 * 
		 *  READ SupplierFile NEXT RECORD
            	AT END SET EndOfFile TO TRUE
        	END-READ
	        	 PERFORM UNTIL EndOfFile
	            PERFORM DisplayRecord
	            READ SupplierFile NEXT RECORD
	                AT END SET EndOfFile TO TRUE
	            END-READ
        	END-PERFORM
		 */
		
		 
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
