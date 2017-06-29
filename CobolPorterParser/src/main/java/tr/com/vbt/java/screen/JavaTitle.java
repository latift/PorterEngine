package tr.com.vbt.java.screen;

import java.util.List;

import tr.com.vbt.java.AbstractJavaElement;

public class JavaTitle extends  AbstractJavaElement{
	List<String> dataToDisplay;

	@Override
	public boolean writeJavaToStream() throws Exception{ super.writeJavaToStream();
		/*dataToDisplay = (List<String> ) this.parameters.get("dataToDisplay");
		
		
		try{
			JavaClassElement.javaCodeBuffer.append(JavaConstants.SYSTEM_OUT_PRINTLN);
			JavaClassElement.javaCodeBuffer.append(JavaConstants.OPEN_NORMAL_BRACKET);
			JavaClassElement.javaCodeBuffer.append(JavaConstants.DOUBLE_QUOTA);
			for (String dataDisplayParam : dataToDisplay) {
				JavaClassElement.javaCodeBuffer.append(dataDisplayParam+" ");
			}
			JavaClassElement.javaCodeBuffer.append(JavaConstants.DOUBLE_QUOTA);
			JavaClassElement.javaCodeBuffer.append(JavaConstants.CLOSE_NORMAL_BRACKET);
			JavaClassElement.javaCodeBuffer.append(JavaConstants.DOT_WITH_COMMA);
			JavaClassElement.javaCodeBuffer.append(JavaConstants.NEW_LINE);
		} catch (Exception e) {
			JavaClassElement.javaCodeBuffer.append("//Conversion Error"+getClass().getName()); e.printStackTrace();
		}*/
		return true;
	}

	@Override
	public boolean writeChildrenJavaToStream() {
		// TODO Auto-generated method stub
		return false;
	}
	

}
