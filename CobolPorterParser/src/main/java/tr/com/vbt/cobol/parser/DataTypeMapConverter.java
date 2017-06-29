package tr.com.vbt.cobol.parser;

import java.util.List;

public interface DataTypeMapConverter {
	
	public void generateDataTypeConversionString(
			List<AbstractCommand> commandList, int commandIndex);
	
}
