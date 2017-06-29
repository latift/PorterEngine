package tr.com.vbt.java;

import tr.com.vbt.java.basic.JavaDisplayElement;
import tr.com.vbt.java.general.JavaClassElement;
import tr.com.vbt.java.general.JavaClassGeneral;
import tr.com.vbt.java.general.JavaFunctionElement;
import tr.com.vbt.java.general.JavaGeneralVariableElement;
import tr.com.vbt.java.general.JavaImport;
import tr.com.vbt.java.general.JavaPackageElement;

public class JavaElementsFactory {
	
	public AbstractJavaElement createJavaClassGeneral(){
		return JavaClassGeneral.getInstance();
	}
	
	public AbstractJavaElement createJavaClassElement(){
		return new JavaClassElement();
	}
	
	public AbstractJavaElement createJavaFunctionElement(){
		return new JavaFunctionElement();
	}
	
	public AbstractJavaElement createJavaGeneralVariableElement(){
		return new JavaGeneralVariableElement();
	}
	
	public AbstractJavaElement createJavaImport(){
		return new JavaImport();
	}
	
	public AbstractJavaElement createJavaPackageElement(){
		return new JavaPackageElement();
	}
	
	public AbstractJavaElement createJavaDisplayElement(){
		return new JavaDisplayElement();
	}
	
	

}
