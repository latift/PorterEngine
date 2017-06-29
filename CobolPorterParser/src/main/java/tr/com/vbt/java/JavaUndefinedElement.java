package tr.com.vbt.java;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tr.com.vbt.cobol.convert.TransferFromNaturalToJavaMain;
import tr.com.vbt.java.general.JavaClassElement;
import tr.com.vbt.java.general.JavaConstants;


//
public class JavaUndefinedElement extends  AbstractJavaElement {
	
	final static Logger logger = LoggerFactory.getLogger(TransferFromNaturalToJavaMain.class);
	
	private String dataToDisplay;
	
	private List dataToDisplayList;
	
	public boolean writeJavaToStream() throws Exception{ super.writeJavaToStream();
		
		JavaClassElement.javaCodeBuffer.append(JavaConstants.COMMENT_ONE_LINE);
		JavaClassElement.javaCodeBuffer.append("UNDEFINED ELEMENT");
		
		try {
			if(this.parameters.get("dataToDisplay")!=null){
				if(this.parameters.get("dataToDisplay") instanceof String){
					dataToDisplay = (String) this.parameters.get("dataToDisplay");
				}else if(this.parameters.get("dataToDisplay") instanceof ArrayList){
					dataToDisplayList= (ArrayList) this.parameters.get("dataToDisplay");
				}
				
			}
			
			//JavaClassElement.javaCodeBuffer.append(JavaConstants.SYSTEM_OUT_PRINTLN);
			
			if(dataToDisplay!=null){
				JavaClassElement.javaCodeBuffer.append(dataToDisplay);
			}else if(dataToDisplayList!=null && !dataToDisplayList.isEmpty()){
				for (Object obj : dataToDisplayList) {
					JavaClassElement.javaCodeBuffer.append(obj);
				}
				JavaClassElement.javaCodeBuffer.append(dataToDisplay);
			}
			
			JavaClassElement.javaCodeBuffer.append(JavaConstants.NEW_LINE);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.trace("", e);
		}
		
		return true;
	}

	@Override
	public boolean writeChildrenJavaToStream() {
		// TODO Auto-generated method stub
		return false;
	}

	public String getDataToDisplay() {
		return dataToDisplay;
	}

	public void setDataToDisplay(String dataToDisplay) {
		this.dataToDisplay = dataToDisplay;
	}
	
	

}
