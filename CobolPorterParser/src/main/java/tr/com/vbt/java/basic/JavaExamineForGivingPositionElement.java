package tr.com.vbt.java.basic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tr.com.vbt.java.AbstractJavaElement;
import tr.com.vbt.java.general.JavaClassElement;
import tr.com.vbt.java.general.JavaConstants;
import tr.com.vbt.java.utils.ConvertUtilities;
import tr.com.vbt.java.utils.JavaWriteUtilities;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.util.CustomStringUtils;


// //EXAMINE SCRTXT FOR 'YETISKIN' GIVING POSITION LOC  --> LOC=SCRTXT.indexOf(YETISKIN);
public class JavaExamineForGivingPositionElement extends  AbstractJavaElement {
	
	final static Logger logger = LoggerFactory.getLogger(JavaExamineForGivingPositionElement.class);
	
	private AbstractToken sourceToken;
	
	private AbstractToken searchVar;
	
	private String resultPosition;
	
	
	public boolean writeJavaToStream() throws Exception{ 
		
		super.writeJavaToStream();
		
		try{
			sourceToken= (AbstractToken) this.getParameters().get("sourceToken");
			
			searchVar= (AbstractToken) this.getParameters().get("searchVar");
			
			resultPosition= (String) this.getParameters().get("resultPosition");
			
			
			//LIMANINDEX=searchArray(
			JavaClassElement.javaCodeBuffer.append(CustomStringUtils.replaceMiddleLineWithSubLine(resultPosition)+"=");
			
			JavaClassElement.javaCodeBuffer.append(JavaWriteUtilities.toCustomString(sourceToken));
			JavaClassElement.javaCodeBuffer.append(".indexOf(");
			JavaClassElement.javaCodeBuffer.append(JavaWriteUtilities.toCustomString(searchVar));
			JavaClassElement.javaCodeBuffer.append(")");
			JavaClassElement.javaCodeBuffer.append(JavaConstants.DOT_WITH_COMMA+JavaConstants.NEW_LINE);
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
