package tr.com.vbt.java.screen;

import java.util.List;

import tr.com.vbt.java.AbstractJavaElement;
import tr.com.vbt.java.general.JavaClassElement;
import tr.com.vbt.java.general.JavaConstants;

//NATURAL CODE:59   SIZE 06 * 27
//WMUSSECIM.setHeight(6);
//WMUSSECIM.setWidth(27);
public class JavaSize extends  AbstractJavaElement{
	
	long lineCount;
	
	long lineLength;

	String windowName;
	@Override
	public boolean writeJavaToStream() throws Exception{ 
		
		super.writeJavaToStream();
		
		windowName=(String) getParent().getParameters().get("windowName");
		
		
		lineCount = (long) this.parameters.get("lineCount");
		
		lineLength = (long) this.parameters.get("lineLength");
	
	
		try{
			JavaClassElement.javaCodeBuffer.append(windowName+".setHeight("+lineCount+")");
			JavaClassElement.javaCodeBuffer.append(JavaConstants.DOT_WITH_COMMA);
			JavaClassElement.javaCodeBuffer.append(JavaConstants.NEW_LINE);
			JavaClassElement.javaCodeBuffer.append(windowName+".setWidth("+lineLength+")");
			JavaClassElement.javaCodeBuffer.append(JavaConstants.DOT_WITH_COMMA);
			JavaClassElement.javaCodeBuffer.append(JavaConstants.NEW_LINE);
		} catch (Exception e) {
			JavaClassElement.javaCodeBuffer.append("//Conversion Error"+getClass().getName()); e.printStackTrace();
		}
		return true;
	}

	@Override
	public boolean writeChildrenJavaToStream() {
		// TODO Auto-generated method stub
		return false;
	}
	

}
