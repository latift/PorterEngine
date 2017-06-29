package tr.com.vbt.natural.html;

public class HtmlParameter {

	String paramKey;
	
	String paramValue;
	
	public HtmlParameter(String paramKey, String paramValue) {
		super();
		this.paramKey = paramKey;
		this.paramValue = paramValue;
	}
	
	
	public String getParamKey() {
		return paramKey;
	}

	public void setParamKey(String paramKey) {
		this.paramKey = paramKey;
	}

	public String getParamValue() {
		return paramValue;
	}

	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}

	
	
}
