package tr.com.vbt.java.screen;

import tr.com.vbt.java.AbstractJavaElement;
import tr.com.vbt.java.general.JavaClassElement;
import tr.com.vbt.java.general.JavaConstants;


// NATURAL CODE:60 :0 BASE 11.0 /54.0
//WMUSSECIM.setTopCoord(11);
//WMUSSECIM.setLeftCoord(54);
public class JavaBaseElement extends  AbstractJavaElement{
	
	long baseX;
	
	long baseY;
	
	String windowName;

	@Override
	public boolean writeJavaToStream() throws Exception{ super.writeJavaToStream();
		super.writeJavaToStream();
		
		windowName=(String) getParent().getParameters().get("windowName");
		
		
		baseX = (long) this.parameters.get("baseX");
		
		baseY = (long) this.parameters.get("baseY");
	
	
		try{
			JavaClassElement.javaCodeBuffer.append(windowName+".setTopCoord("+baseX+")");
			JavaClassElement.javaCodeBuffer.append(JavaConstants.DOT_WITH_COMMA);
			JavaClassElement.javaCodeBuffer.append(JavaConstants.NEW_LINE);
			JavaClassElement.javaCodeBuffer.append(windowName+".setLeftCoord("+baseY+")");
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
