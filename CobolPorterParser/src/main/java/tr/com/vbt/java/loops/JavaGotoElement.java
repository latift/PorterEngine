package tr.com.vbt.java.loops;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tr.com.vbt.java.AbstractJavaElement;
import tr.com.vbt.java.general.JavaClassElement;
import tr.com.vbt.java.general.JavaConstants;


//PERFORM B-PARA VARYING WS-A FROM 1 BY 1 UNTIL WS-A=5-->for (int ws-a=1; i<100;i=i+1)
//{b_para();}
public class JavaGotoElement extends  AbstractJavaElement {
	
	final static Logger logger = LoggerFactory.getLogger(JavaGotoElement.class);

	
	private String variable;
	
	private int  from;
	 
	private int by;
	
	private int until=100;
	
	public boolean writeJavaToStream() throws Exception{ super.writeJavaToStream();
		JavaClassElement.javaCodeBuffer.append("//Unimplemented Code:"+getClass().getName()+ JavaConstants.NEW_LINE);
		
		return false;
	}

}
