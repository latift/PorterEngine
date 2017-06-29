package tr.com.vbt.lexer;

import java.lang.reflect.Field;

public class FieldWrapper {

	private Field field;
	
	private String fieldOwnerFile;
	
	

	public FieldWrapper(Field field, String fieldOwnerFile) {
		super();
		this.field = field;
		this.fieldOwnerFile = fieldOwnerFile;
	}

	public Field getField() {
		return field;
	}

	public void setField(Field field) {
		this.field = field;
	}

	public String getFieldOwnerFile() {
		return fieldOwnerFile;
	}

	public void setFieldOwnerFile(String fieldOwnerFile) {
		this.fieldOwnerFile = fieldOwnerFile;
	}
	
	
}
