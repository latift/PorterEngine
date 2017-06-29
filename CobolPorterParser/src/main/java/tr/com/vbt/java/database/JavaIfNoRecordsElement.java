package tr.com.vbt.java.database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tr.com.vbt.java.AbstractJavaElement;


//ElementIfNoRecords
public class JavaIfNoRecordsElement extends  AbstractJavaElement {
	
	final static Logger logger = LoggerFactory.getLogger(JavaIfNoRecordsElement.class);

	public boolean writeJavaToStream() throws Exception{ super.writeJavaToStream();
		
		this.writeChildrenJavaToStream();
		return true;
	}
	

}
