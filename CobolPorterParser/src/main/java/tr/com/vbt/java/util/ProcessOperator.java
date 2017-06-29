package tr.com.vbt.java.util;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.java.AbstractJavaElement;
import tr.com.vbt.java.JavaElementsFactory;

public abstract class ProcessOperator {

	Rule rule;

	AbstractJavaElement parentJava;
	AbstractJavaElement elementForCreate;
	
	JavaElementsFactory factory=new JavaElementsFactory();
	
	public abstract AbstractJavaElement operateRule(Rule rule, AbstractCommand sourceElement);

	public ProcessOperator(Rule rule) {
		super();
		this.rule = rule;
	}

	

}
