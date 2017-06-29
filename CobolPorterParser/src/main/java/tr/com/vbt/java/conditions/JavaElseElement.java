package tr.com.vbt.java.conditions;

import tr.com.vbt.java.AbstractJavaElement;
import tr.com.vbt.java.general.JavaClassElement;
import tr.com.vbt.java.general.JavaConstants;
import tr.com.vbt.lexer.ConversionLogModel;
import tr.com.vbt.lexer.NaturalMode;


//
public class JavaElseElement extends  AbstractJavaElement {
	
	public boolean writeJavaToStream() throws Exception{ super.writeJavaToStream();

		JavaClassElement.javaCodeBuffer.append("else");
		
		if(ConversionLogModel.getInstance().getMode().equals(NaturalMode.STRUCTRURED)){
			JavaClassElement.javaCodeBuffer.append(JavaConstants.OPEN_BRACKET);
		}
		JavaClassElement.javaCodeBuffer.append(JavaConstants.NEW_LINE);
		
		this.writeChildrenJavaToStream();
		
		if(ConversionLogModel.getInstance().getMode().equals(NaturalMode.STRUCTRURED)){
			JavaClassElement.javaCodeBuffer.append(JavaConstants.CLOSE_BRACKET+"// else");
		}
		
		JavaClassElement.javaCodeBuffer.append(JavaConstants.NEW_LINE);
		
		return true;
	}

}
