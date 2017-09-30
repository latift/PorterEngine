package tr.com.vbt.java.general;

import tr.com.vbt.java.AbstractJava;
import tr.com.vbt.java.AbstractJavaElement;
import tr.com.vbt.util.CustomStringUtils;


//01 WS-NUM1 PIC 9 --> int ws-num1;
//01 WS-NUM1 PIC A --> String ws-num1;
/**
 *GROUP_DATA_TYPE-->
	class WS_STUDENT_REC{
		int WS_STUDENT_ID;
		int WS_STUDENT_NAME;
		int WS_STUDENT_ADDRESS;
		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return super.toString();
		}
	}
 *
 */
public class JavaInnerClassElement extends  AbstractJavaElement{

	private String className;
	

	@Override
	public boolean writeJavaToStream() throws Exception{ super.writeJavaToStream();
		
		className=(String) this.parameters.get("dataName");
		
		AbstractJavaElement.javaCodeBuffer.append("public "+JavaConstants.CLASS+ " "+  CustomStringUtils.replaceMiddleLineWithSubLine(className)+ " "+JavaConstants.OPEN_BRACKET+JavaConstants.NEW_LINE );
			if(this.children!=null){
			for (AbstractJava jge : children) {
				jge.writeJavaToStream();
			}
		}
		AbstractJavaElement.javaCodeBuffer.append(JavaConstants.NEW_LINE+JavaConstants.CLOSE_BRACKET);
		return true;
	}


	public String getClassName() {
		return className;
	}


	public void setClassName(String className) {
		this.className = className;
	}


	
	
	
	
	

}
