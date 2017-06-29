package tr.com.vbt.java.screen;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tr.com.vbt.java.AbstractJavaElement;
import tr.com.vbt.java.general.JavaClassElement;
import tr.com.vbt.java.general.JavaConstants;
import tr.com.vbt.java.utils.ConvertUtilities;
import tr.com.vbt.token.AbstractToken;


// // 1078   INPUT USING MAP 'IDGMMUSS' -->

/**
 *  	shortProgram.inputUsingMap("IDGMMUSS", mapFactory);
 * 
 *
 */
public class JavaInputUsingMapElement extends  AbstractJavaElement {
	
	final static Logger logger = LoggerFactory.getLogger(JavaAtTopOfPageElement.class);
	
	private String mapName;
	
	private AbstractToken markToken;
	
	public boolean writeJavaToStream() throws Exception{ super.writeJavaToStream();
		mapName=  (String) this.getParameters().get("mapName");
		
		if(this.getParameters().get("markToken")!=null){
			markToken= (AbstractToken) this.getParameters().get("markToken");
		}
		
		try {
			//JavaClassElement.javaCodeBuffer.append("=openScreen("+mapName+")"+JavaConstants.DOT_WITH_COMMA+JavaConstants.NEW_LINE);
			JavaClassElement.javaCodeBuffer.append("inputUsingMap(\""+mapName+"\")"+JavaConstants.DOT_WITH_COMMA+JavaConstants.NEW_LINE);
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
