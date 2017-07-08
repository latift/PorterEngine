package tr.com.vbt.lexer;

public class ModuleAndSchema{
	
	private String module;
	
	private String schema;

	
	public ModuleAndSchema(String module, String schema) {
		super();
		this.module = module;
		this.schema = schema;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getSchema() {
		return schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}
	
	
}
