package tr.com.vbt.natural.html;

import tr.com.vbt.lexer.ControlEnum;

public abstract class AbstractEngineIO implements EngineIO {

	private HtmlColor backgroundColor;
	
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




}
