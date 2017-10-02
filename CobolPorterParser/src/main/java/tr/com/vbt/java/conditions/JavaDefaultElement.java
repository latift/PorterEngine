package tr.com.vbt.java.conditions;

import tr.com.vbt.java.AbstractJavaElement;
import tr.com.vbt.java.general.JavaClassElement;
import tr.com.vbt.java.general.JavaConstants;


//
public class JavaDefaultElement extends  AbstractJavaElement {
	
	
	boolean parentIsSwitchDecideFirstCondition=false;
	
	public boolean writeJavaToStream() throws Exception{ 
		super.writeJavaToStream();
		
		if(this.parent.getJavaElementName().equalsIgnoreCase("JavaWhen") &&
				this.parent.getParent().getJavaElementName().equalsIgnoreCase("JavaSwitchDecideFirstCondition")	){
			parentIsSwitchDecideFirstCondition=true;
		}
		
		if(!parentIsSwitchDecideFirstCondition){
			JavaClassElement.javaCodeBuffer.append("default :"+ JavaConstants.NEW_LINE);
				
		}
		this.writeChildrenJavaToStream();
		return true;
	}

}

