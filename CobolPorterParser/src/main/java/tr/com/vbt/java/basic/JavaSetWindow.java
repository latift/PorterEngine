package tr.com.vbt.java.basic;

import tr.com.vbt.java.AbstractJavaElement;
import tr.com.vbt.java.general.JavaClassElement;
import tr.com.vbt.java.general.JavaConstants;

//1970 *   SET WINDOW OFF  --> 		unregisterWindow();
//1914 SET WINDOW 'WSWIFT' -->	registerWindow(WSWIFT);
public class JavaSetWindow extends  AbstractJavaElement{
	
	private String windowName;
	
	private String windowOffState;

	@Override
	public boolean writeJavaToStream() throws Exception{ super.writeJavaToStream();
		
		if(this.parameters.get("windowName")!=null){
			windowName =(String) this.parameters.get("windowName");
		}
		if(this.parameters.get("windowOffState")!=null){
			windowOffState =(String) this.parameters.get("windowOffState");
		}
		
		try{
			////1970 *   SET WINDOW OFF  --> 		unregisterWindow();
			if(windowOffState!=null && windowOffState.equalsIgnoreCase("off")){
			
				JavaClassElement.javaCodeBuffer.append("unregisterWindow()");
				JavaClassElement.javaCodeBuffer.append(JavaConstants.DOT_WITH_COMMA);
				JavaClassElement.javaCodeBuffer.append(JavaConstants.NEW_LINE);
			//1970 *  SET WINDOW 'WSWIFT' -->	registerWindow(WSWIFT);
			}else{
				JavaClassElement.javaCodeBuffer.append("registerWindow("+windowName+")");
				JavaClassElement.javaCodeBuffer.append(JavaConstants.DOT_WITH_COMMA);
				JavaClassElement.javaCodeBuffer.append(JavaConstants.NEW_LINE);
			}
			
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
