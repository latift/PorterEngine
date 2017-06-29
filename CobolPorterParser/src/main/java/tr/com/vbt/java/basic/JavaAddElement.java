package tr.com.vbt.java.basic;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tr.com.vbt.java.AbstractJavaElement;
import tr.com.vbt.java.general.JavaClassElement;
import tr.com.vbt.java.general.JavaConstants;
import tr.com.vbt.java.utils.ConvertUtilities;
import tr.com.vbt.java.utils.JavaWriteUtilities;
import tr.com.vbt.token.AbstractToken;


// ADD 1 TO JJ --> JJ=JJ+1;
public class JavaAddElement extends  AbstractJavaElement {
	
	final static Logger logger = LoggerFactory.getLogger(JavaAddElement.class);
	
	private List<AbstractToken> sourceList;
	
	private List<AbstractToken> destList;
	
	public boolean writeJavaToStream() throws Exception{ super.writeJavaToStream();
		sourceList=(List<AbstractToken>) this.getParameters().get("SOURCE");
		
		destList=(List<AbstractToken>) this.getParameters().get("DESTINATION");
		try{
			JavaClassElement.javaCodeBuffer.append(JavaWriteUtilities.toCustomString(destList.get(0))+"="+JavaWriteUtilities.toCustomString(destList.get(0))+JavaConstants.PLUS+JavaWriteUtilities.toCustomString(sourceList.get(0))+JavaConstants.DOT_WITH_COMMA+JavaConstants.NEW_LINE);
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
