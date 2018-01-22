package tr.com.vbt.java.database;

import org.apache.log4j.Logger;


import tr.com.vbt.java.AbstractJavaElement;


//ElementIfNoRecords
public class JavaIfNoRecordsElement extends  AbstractJavaElement {
	
	final static Logger logger = Logger.getLogger(JavaIfNoRecordsElement.class);

	public boolean writeJavaToStream() throws Exception{ super.writeJavaToStream();
		
		this.writeChildrenJavaToStream();
		return true;
	}
	

}
