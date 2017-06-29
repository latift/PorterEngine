package tr.com.vbt.patern;

import tr.com.vbt.cobol.parser.AbstractCommand;


public interface MapManager {

	public void registerCommandToPojoMap(String commandName, AbstractCommand command);
	
	public void registerCommandToLocalVariablesMap(String commandName, AbstractCommand command);
	
	public void printMaps();

	public String getJavaStyleGetterCodeOfNatDataTypeForPojo(String key);
	
	public String getJavaStyleSetterCodeOfNatDataTypeForPojo(String key);
	
	public String getJavaStyleGetterCodeOfNatDataTypeForLocalVar(String key);
	
	public String getJavaStyleSetterCodeOfNatDataTypeForLocalVar(String key);
}
