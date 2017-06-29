package tr.com.vbt.java.general;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tr.com.vbt.java.AbstractJavaElement;

public class JavaFunctionVariablesElement extends  AbstractJavaElement{

	final static Logger logger = LoggerFactory.getLogger(JavaFunctionVariablesElement.class);
	
	private String type;
	
	private String name;
	
	private String visibility;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVisibility() {
		return visibility;
	}

	public void setVisibility(String visibility) {
		this.visibility = visibility;
	}

	@Override
	public boolean writeJavaToStream() throws Exception{ super.writeJavaToStream();
		// TODO Auto-generated method stub
		return false;
	}
	
	
	

}
