package tr.com.vbt.java.subroutines;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tr.com.vbt.java.AbstractJavaElement;
import tr.com.vbt.java.general.JavaClassElement;
import tr.com.vbt.java.general.JavaConstants;
import tr.com.vbt.java.utils.ConvertUtilities;


//FETCH RETURN 'AYKPFMM2' -->

// AYKPFMM2 a
/**
 * JavaClassElement.javaCodeBuffer.append("showInput(new HtmlLink(NaturalTagTypes.LABEL,null,\"\","+token.getDeger()+"));");
	avaClassElement.javaCodeBuffer.append(JavaConstants.NEW_LINE);
 * 
 *
 */
public class JavaFetchReturnElement extends  AbstractJavaElement{
	
	final static Logger logger = LoggerFactory.getLogger(JavaFetchReturnElement.class);

	private String programName;
	
	@Override
	public boolean writeJavaToStream() throws Exception{ super.writeJavaToStream();

		try {
			programName=(String) this.parameters.get("programName");
			
			JavaClassElement.javaCodeBuffer.append("fetchReturnV2(\""+programName+"\",null)"+JavaConstants.DOT_WITH_COMMA);
			JavaClassElement.javaCodeBuffer.append(JavaConstants.NEW_LINE);
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
