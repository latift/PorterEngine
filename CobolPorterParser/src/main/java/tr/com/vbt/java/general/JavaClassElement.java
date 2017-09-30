package tr.com.vbt.java.general;

import java.util.List;

import tr.com.vbt.java.AbstractJava;
import tr.com.vbt.java.AbstractJavaElement;
import tr.com.vbt.lexer.ConversionLogModel;

public class JavaClassElement extends  AbstractJavaElement{
	
	
	String classSecurity="public";
	String className;
	String date_written;
	String author;
	
	private List<AbstractJava> jFunctions;
	
	public boolean writeJavaToStream() throws Exception{ super.writeJavaToStream();
		className=(String) this.parameters.get("PROGRAM_ID");
		date_written=(String) this.parameters.get("DATE_WRITTEN");
		author=(String) this.parameters.get("AUTHOR");
		

		
		if(className!=null){
			
			if(ConversionLogModel.getInstance().isProgram()){
				className=className+"Impl";
			}
			
			AbstractJava.javaCodeBuffer.append(classSecurity+ " " +JavaConstants.CLASS+ " "+ className);
			
			AbstractJava.javaCodeBuffer.append(" extends AbstractNaturalTPSProgram implements "+JavaConstants.CLASS);
			AbstractJava.javaCodeBuffer.append(JavaConstants.OPEN_BRACKET+JavaConstants.NEW_LINE );
		}
		if(date_written!=null){
			AbstractJava.javaCodeBuffer.append(JavaConstants.COMMENT_ONE_LINE+"Date Written:"+date_written+JavaConstants.NEW_LINE);
		}
		if(author!=null){
			AbstractJava.javaCodeBuffer.append(JavaConstants.COMMENT_ONE_LINE+"Author:"+author+JavaConstants.NEW_LINE);
		}
		
		writeConstructor();
		this.writeChildrenJavaToStream();
		AbstractJava.javaCodeBuffer.append(JavaConstants.NEW_LINE+JavaConstants.CLOSE_BRACKET);
		return true;
	}


	private void writeConstructor() {
		
//		public GZTBAS21Impl() {
		AbstractJava.javaCodeBuffer.append(classSecurity+ " "+ className+JavaConstants.OPEN_NORMAL_BRACKET+JavaConstants.CLOSE_NORMAL_BRACKET+JavaConstants.OPEN_BRACKET+JavaConstants.NEW_LINE);
//			super();0
		AbstractJava.javaCodeBuffer.append("super();");
//			this.isClientInteracting=true;
		AbstractJava.javaCodeBuffer.append("this.isClientInteracting="+ConversionLogModel.getInstance().isClientInteracting()+";"+JavaConstants.NEW_LINE);
//		}
		AbstractJava.javaCodeBuffer.append(JavaConstants.CLOSE_BRACKET+JavaConstants.NEW_LINE);
		
	}


	@Override
	public boolean hasInputChild() {
		// TODO Auto-generated method stub
		return false;
	}



	
	
}
