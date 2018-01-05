package tr.com.vbt.java.database;

import org.apache.log4j.Logger;


import tr.com.vbt.java.AbstractJavaElement;
import tr.com.vbt.java.general.JavaClassElement;
import tr.com.vbt.java.general.JavaConstants;
import tr.com.vbt.java.utils.ConvertUtilities;


// RD1. READ EMPLOYEES-VIEW BY NAME--> select * from employees-view;
public class JavaReadFromDBElement extends  AbstractJavaElement {
	
	final static Logger logger = Logger.getLogger(JavaReadFromDBElement.class);
	
	public boolean writeJavaToStream() throws Exception{ super.writeJavaToStream();
		
		try {
			JavaClassElement.javaCodeBuffer.append("select c.adi from customer c"+JavaConstants.DOT_WITH_COMMA+JavaConstants.NEW_LINE);
		} catch (Exception e) {
			logger.debug("//Conversion Error"+this.getClass()+this.getSourceCode().getSatirNumarasi()+this.getSourceCode().getCommandName());
			JavaClassElement.javaCodeBuffer.append("/*Conversion Error"+this.getClass()+this.getSourceCode().getSatirNumarasi()
					+this.getSourceCode().getCommandName()+"*/"+JavaConstants.NEW_LINE);
			logger.error("//Conversion Error:"+e.getMessage(), e); 
			ConvertUtilities.writeconversionErrors(e, this);
		}
		
		return true;
	}

	@Override
	public boolean writeChildrenJavaToStream() {
		// TODO Auto-generated method stub
		return false;
	}

	

}
