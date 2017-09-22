package tr.com.vbt.java.loops;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tr.com.vbt.java.AbstractJavaElement;
import tr.com.vbt.java.general.JavaClassElement;
import tr.com.vbt.java.general.JavaConstants;
import tr.com.vbt.java.utils.ConvertUtilities;


//PERFORM B-PARA VARYING WS-A FROM 1 BY 1 UNTIL WS-A=5-->for (int ws-a=1; i<100;i=i+1)
//{b_para();}
public class JavaForElementPerformVarying extends  AbstractJavaElement {
	
	final static Logger logger = LoggerFactory.getLogger(JavaForElementPerformVarying.class);

	
	private String variable;
	
	private int  from;
	 
	private int by;
	
	private int until=100;
	
	public boolean writeJavaToStream() throws Exception{ super.writeJavaToStream();
		try {
			variable = (String) this.parameters.get("variable");
			from = (int)((long) this.parameters.get("from"));
			by = (int) ((long)this.parameters.get("by"));
			//until = (int) this.parameters.get("until");
			
			JavaClassElement.javaCodeBuffer.append("for (int "+variable+"=0; "+variable+"<100;"+ variable+"="+variable+"+"+by+")"+JavaConstants.NEW_LINE);
			JavaClassElement.javaCodeBuffer.append(JavaConstants.OPEN_BRACKET+JavaConstants.NEW_LINE);
			this.writeChildrenJavaToStream();
			JavaClassElement.javaCodeBuffer.append(JavaConstants.CLOSE_BRACKET+JavaConstants.NEW_LINE);
		} catch (Exception e) {
			logger.debug("//Conversion Error"+this.getClass()+this.getSourceCode().getSatirNumarasi()+this.getSourceCode().getCommandName());
			JavaClassElement.javaCodeBuffer.append("/*Conversion Error"+this.getClass()+this.getSourceCode().getSatirNumarasi()
					+this.getSourceCode().getCommandName()+"*/"+JavaConstants.NEW_LINE);
			logger.error("//Conversion Error:"+e.getMessage(), e); 
			ConvertUtilities.writeconversionErrors(e, this);
		}
		return true;
	}

}
