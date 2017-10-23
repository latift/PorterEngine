package tr.com.vbt.java.screen;

import java.util.List;

import tr.com.vbt.java.AbstractJavaElement;
import tr.com.vbt.java.general.JavaClassElement;
import tr.com.vbt.java.general.JavaConstants;

// NATURAL CODE:58 :0 DEFINE WINDOW WMUSSECIM -->  WMUSSECIM=new Window();
public class JavaDefineWindow extends  AbstractJavaElement{
	String windowName;

	@Override
	public boolean writeJavaToStream() throws Exception{ super.writeJavaToStream();
	windowName = (String) this.parameters.get("windowName");
		
		
		try{
			JavaClassElement.javaCodeBuffer.append("Window "+windowName+"=new Window()");
			JavaClassElement.javaCodeBuffer.append(JavaConstants.DOT_WITH_COMMA);
			JavaClassElement.javaCodeBuffer.append(JavaConstants.NEW_LINE);
			writeChildrenJavaToStream();
		} catch (Exception e) {
			JavaClassElement.javaCodeBuffer.append("//Conversion Error"+getClass().getName()); e.printStackTrace();
		}
		return true;
	}



	public String getWindowName() {
		return windowName;
	}

	public void setWindowName(String windowName) {
		this.windowName = windowName;
	}
	
	
	

}
