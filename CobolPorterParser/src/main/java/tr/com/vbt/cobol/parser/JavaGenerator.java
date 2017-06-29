package tr.com.vbt.cobol.parser;

import tr.com.vbt.java.util.RuleNotFoundException;


public interface JavaGenerator {
	public boolean generateJava() throws RuleNotFoundException;
	
	public boolean generateJavaForChildren() throws RuleNotFoundException;
	
	
	
}
