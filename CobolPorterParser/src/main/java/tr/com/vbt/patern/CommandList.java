package tr.com.vbt.patern;

import java.util.List;
import java.util.Set;

import tr.com.vbt.cobol.parser.AbstractCommand;

public interface CommandList {

	//public void parseTokensToCommandsList();
	
	public StringBuilder writeCommands(String fullFilePath,String undefinedCommandfullFilePath) ;
	
	public StringBuilder writeCommands(String fullFilePath);
	
	public void findAndSetEndingCommands() throws Exception;
	
	public void findAndSetDataTypeDefinitions();
	
	public void addVirtualEndings() ;
	
	public List<AbstractCommand> getCommandList();

	public void setCommandList(List<AbstractCommand> commandList);

	public void generateDatatypeMap();

	public void setRedefineElementsParents();

	public void markSimpleRedefineElements();
	
	public void markOneDimensionRedefineElementOfSimpleDataType();

	public void markTwoDimensionRedefineElements();

	public void parseDataTypeTokensToCommandsList() throws Exception;

	public void parseCommandTokensToCommandsList();

	public void createAndFillRecordDataTypeMap();

	public void addGrupNamesToRecordItems();

	public void controlRecords();

	public void markRedefinedTokens();

	public PaternManager getPaternManager();

	void setPaternManager(PaternManager paternManager);

	public void setTypeNameOfView();

	public void changeProgramDataTypeToDBDataType();
	
	public Set<String> getUndefinedCommandSet();
	
	public void addDefineData() ;

	
}
