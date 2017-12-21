package tr.com.vbt.natural.html;

import tr.com.vbt.token.AbstractToken;

public abstract class AbstractEngineIO implements EngineIO {

	private HtmlColor backgroundColor;
	
	private AbstractToken token;
	
	protected String controlVariableName;
	
	public HtmlColor getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(HtmlColor backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public String getControlVariableName() {
		return controlVariableName;
	}

	public void setControlVariableName(String controlVariableName) {
		this.controlVariableName = controlVariableName;
	}

	public AbstractToken getToken() {
		return token;
	}

	public void setToken(AbstractToken token) {
		this.token = token;
	}




}
