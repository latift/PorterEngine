package tr.com.vbt.java.general;

import java.util.List;

import tr.com.vbt.java.AbstractJavaElement;
import tr.com.vbt.lexer.ConversionLogModel;

public class JavaClassElement extends  AbstractJavaElement{
	
	
	String classSecurity="public";
	String className;
	String date_written;
	String author;
	
	private List<JavaFunctionElement> jFunctions;
	
	public boolean writeJavaToStream() throws Exception{ super.writeJavaToStream();
		className=(String) this.parameters.get("PROGRAM_ID");
		date_written=(String) this.parameters.get("DATE_WRITTEN");
		author=(String) this.parameters.get("AUTHOR");
		

		
		if(className!=null){
			
			if(ConversionLogModel.getInstance().isProgram()){
				className=className+"Impl";
			}
			
			AbstractJavaElement.javaCodeBuffer.append(classSecurity+ " " +JavaConstants.CLASS+ " "+ className);
			
			AbstractJavaElement.javaCodeBuffer.append(" extends AbstractNaturalTPSProgram implements "+JavaConstants.CLASS);
			AbstractJavaElement.javaCodeBuffer.append(JavaConstants.OPEN_BRACKET+JavaConstants.NEW_LINE );
		}
		if(date_written!=null){
			AbstractJavaElement.javaCodeBuffer.append(JavaConstants.COMMENT_ONE_LINE+"Date Written:"+date_written+JavaConstants.NEW_LINE);
		}
		if(author!=null){
			AbstractJavaElement.javaCodeBuffer.append(JavaConstants.COMMENT_ONE_LINE+"Author:"+author+JavaConstants.NEW_LINE);
		}
		
		writeConstructor();
		this.writeChildrenJavaToStream();
		AbstractJavaElement.javaCodeBuffer.append(JavaConstants.NEW_LINE+JavaConstants.CLOSE_BRACKET);
		return true;
	}


	private void writeConstructor() {
		
//		public GZTBAS21Impl() {
		AbstractJavaElement.javaCodeBuffer.append(classSecurity+ " "+ className+JavaConstants.OPEN_NORMAL_BRACKET+JavaConstants.CLOSE_NORMAL_BRACKET+JavaConstants.OPEN_BRACKET+JavaConstants.NEW_LINE);
//			super();0
		AbstractJavaElement.javaCodeBuffer.append("super();");
//			this.isClientInteracting=true;
		AbstractJavaElement.javaCodeBuffer.append("this.isClientInteracting="+ConversionLogModel.getInstance().isClientInteracting()+";"+JavaConstants.NEW_LINE);
//		}
		AbstractJavaElement.javaCodeBuffer.append(JavaConstants.CLOSE_BRACKET+JavaConstants.NEW_LINE);
		
	}



	
	
}
