package tr.com.vbt.patern;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;


import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.AbstractEndingCommand;
import tr.com.vbt.cobol.parser.AbstractMultipleLinesCommand;
import tr.com.vbt.cobol.parser.Levelable;
import tr.com.vbt.cobol.parser.basicverbs.ElementUndefinedCobol;
import tr.com.vbt.cobol.parser.conditions.enders.ElementEndWhen;
import tr.com.vbt.cobol.parser.enders.ElementEndConfigurationSection;
import tr.com.vbt.cobol.parser.enders.ElementEndDataDivision;
import tr.com.vbt.cobol.parser.enders.ElementEndEnvironmentDivision;
import tr.com.vbt.cobol.parser.enders.ElementEndFileControl;
import tr.com.vbt.cobol.parser.enders.ElementEndFileDescription;
import tr.com.vbt.cobol.parser.enders.ElementEndFileSection;
import tr.com.vbt.cobol.parser.enders.ElementEndGroupDataType;
import tr.com.vbt.cobol.parser.enders.ElementEndIOControl;
import tr.com.vbt.cobol.parser.enders.ElementEndIdentificationDivision;
import tr.com.vbt.cobol.parser.enders.ElementEndInputOutputSection;
import tr.com.vbt.cobol.parser.enders.ElementEndLinkageSection;
import tr.com.vbt.cobol.parser.enders.ElementEndLocalStorageSection;
import tr.com.vbt.cobol.parser.enders.ElementEndParagraph;
import tr.com.vbt.cobol.parser.enders.ElementEndProcedureDivision;
import tr.com.vbt.cobol.parser.enders.ElementEndWorkingStorageSection;
import tr.com.vbt.cobol.parser.file.child.enders.ElementEndAtEnd;
import tr.com.vbt.cobol.parser.file.child.enders.ElementEndInvalidKey;
import tr.com.vbt.cobol.parser.file.child.enders.ElementEndNotAtEnd;
import tr.com.vbt.cobol.parser.file.child.enders.ElementEndNotInvalidKey;
import tr.com.vbt.cobol.parser.file.enders.ElementEndDeleteFile;
import tr.com.vbt.cobol.parser.file.enders.ElementEndReWriteFile;
import tr.com.vbt.cobol.parser.file.enders.ElementEndReadFile;
import tr.com.vbt.cobol.parser.file.enders.ElementEndStartFile;
import tr.com.vbt.cobol.parser.file.enders.ElementEndWriteFile;
import tr.com.vbt.java.util.DataTypesCommandsUtility;
import tr.com.vbt.java.util.MultipleLinesCommandsUtility;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.TokenTipi;
import tr.com.vbt.util.WriteToFile;

public class CobolCommandList  extends AbstractCommandList{

	final static Logger logger = Logger.getLogger(CobolCommandList.class);
	
	public CobolCommandList(List<AbstractToken> tokenListesi) {
		paternManager=new PaternManagerCobolImpl();
		this.tokenListesi=tokenListesi;
	}

	
	
	public void parseTokensToCommandsList(){
		logger.info("*************************************************************************************");
		logger.info("*************************************************************************************");
		logger.info("*************************************************************************************");
		logger.info("Start of Program Parse  Tokens To Command List");
		AbstractCommand command;
		
		int offset=0;
		
		int tokenListesiRealSize=getTokenListesiRealSize();
		
		do
		{
			
			
			command=paternManager.findBestMatchedCommand(tokenListesi, offset);
			
			commandList.add(command);
			
			if(command instanceof ElementUndefinedCobol){
				ElementUndefinedCobol undefCommand=(ElementUndefinedCobol) command;
				undefinedCommandSet.add(undefCommand.getDataToDisplay());
			}
			
			logger.info("COMMAND MATCHED: "+command);
			
			offset+=command.getCommandMatchPoint();
			
		}while(offset<tokenListesiRealSize);
		logger.info("End of Program Parse  Tokens To Command List");
		logger.info("*************************************************************************************");
		logger.info("*************************************************************************************");
		logger.info("*************************************************************************************");
		
	}
	
	private int getTokenListesiRealSize() {
		int tokenListesiRealSize=tokenListesi.size();
		
		for(int index=tokenListesi.size()-1;index>0;index--){
			if(tokenListesi.get(index).getTip().equals(TokenTipi.SatirBasi)||tokenListesi.get(index).getTip().equals(TokenTipi.Nokta)){
				tokenListesiRealSize--;
			}else{
				break;
			}
		}
		return tokenListesiRealSize;
	}

	public StringBuilder writeCommands(String fullFilePath,String undefinedCommandfullFilePath) {
		
		StringBuilder sb = new StringBuilder();
		for (AbstractCommand command : commandList) {
			sb.append(command.exportCommands());
		}
		try {
			WriteToFile.writeToFile(sb,fullFilePath);
		} catch (IOException e) {
		   logger.debug("",e);
		}
		
		StringBuilder undefinedBuffer = new StringBuilder();
		ElementUndefinedCobol undef;
		for (String undefStr : undefinedCommandSet) {
			undefinedBuffer.append(undefStr+"\n");
		}
		try {
			WriteToFile.writeToFile(undefinedBuffer,undefinedCommandfullFilePath);
		} catch (IOException e) {
			 logger.debug("",e);
		}
		
		return sb;
		
	}

	public void findAndSetEndingCommands() throws Exception {
		MultipleLinesCommandsUtility utility=new MultipleLinesCommandsUtility();
		for (AbstractCommand command : commandList) {
			//System.out.println(command);
			if(utility.isStarter(command)){
				utility.putStarterToBuffer((AbstractMultipleLinesCommand) command);
			}else if(utility.isEnder(command)){
				utility.registerEnderWithBufferLastStarter((AbstractEndingCommand) command);
			}
			//System.out.println();
		}
		
	}
	
	/**
	 * /** Divisions:
		Identification Division
		Environment Division
			CONFIGURATION SECTION.
			INPUT-OUTPUT SECTION.
		Data Division
			File section
			Working-Storage section
			Local-Storage section
			Linkage section
		Procedure Division
		*/
	public void addVirtualEndings() {
		
		addVirtualEndingCommandsForFileDefinition();
		
		addEnderForIdentificationDivision(commandList);
		addEnderForEnvironmentDivision(commandList);
		addEnderForDataDivision(commandList);
		addEnderForProcedureDivision(commandList);
		
		addVirtualEndingCommandsForConfigurationSection();
		addVirtualEndingCommandsForInputOutputSection();
		
		addVirtualEndingCommandsForFileSection();
		addVirtualEndingCommandsForWorkingStorageSection();
		addVirtualEndingCommandsForLocalStorageSection();
		addVirtualEndingCommandsForLinkageSection();
		
		addVirtualEndingCommandsForFileControl();
		addVirtualEndingCommandsForIOControl();
		
		addVirtualEndingCommandsForParagraphs();
		
		
		
		//File Operation Enders:
		addVirtualEndingCommandsForFileStatementsChildren();
		
		addVirtualEndingCommandsForFileStatements();
		
		addVirtualEndingCommandsForWhen();
	}
	
	
	private void addVirtualEndingCommandsForWhen() {
		
		boolean addEnderForWhen=false;
		ElementEndWhen elementEndWhen = new ElementEndWhen();
		
		AbstractCommand curCommand;
		AbstractCommand nextCommand;
		
		for (int index=0; index<commandList.size()-1;index++) {
			
			curCommand=commandList.get(index);
			nextCommand=commandList.get(index+1);
			
			System.out.println(curCommand.getCommandName());
			
			if(curCommand.getCommandName().equals(ReservedCobolKeywords.WHEN)){
				addEnderForWhen=true;
			}else if(addEnderForWhen&&
						(nextCommand.getCommandName().equals(ReservedCobolKeywords.WHEN)  || (nextCommand.getCommandName().equals(ReservedCobolKeywords.END_EVALUATE) ) ) ){
				commandList.add(index+1, elementEndWhen);
				addEnderForWhen=false;
			}
		}
		
	}

	/**
	 *  READ,
	 *  AT END,
	 *  DISPLAY
	 *  NOT AT END
	 *  DISPLAY
	 *  END-READ
	 *  
	 *  
	 *  WRITE
		INVALID KEY DISPLAY
		DISPLAY
		NOT INVALID KEY 
		DISPLAY
		END-WRITE
		
		REWRITE
		INVALID KEY DISPLAY
		DISPLAY
		NOT INVALID KEY 
		DISPLAY
		END-REWRITE
		
	    DELETE
		INVALID KEY DISPLAY
		DISPLAY
		NOT INVALID KEY 
		DISPLAY
		END-DELETE
		
		START
		INVALID KEY DISPLAY
		DISPLAY
		NOT INVALID KEY 
		DISPLAY
		END-START
	 */
private void addVirtualEndingCommandsForFileStatements() {
	
	addVirtualEndingCommandsForWriteFile(ReservedCobolKeywords.READ_FILE, ReservedCobolKeywords.END_READ);
	addVirtualEndingCommandsForWriteFile(ReservedCobolKeywords.WRITE_FILE, ReservedCobolKeywords.END_WRITE);
	addVirtualEndingCommandsForWriteFile(ReservedCobolKeywords.REWRITE_FILE, ReservedCobolKeywords.END_REWRITE);
	addVirtualEndingCommandsForWriteFile(ReservedCobolKeywords.DELETE_FILE , ReservedCobolKeywords.END_DELETE);
	addVirtualEndingCommandsForWriteFile(ReservedCobolKeywords.START_FILE, ReservedCobolKeywords.END_START);
		
}


/**
 * WRITE
		INVALID KEY DISPLAY
		DISPLAY
		END INVALID KEY DISPLAY
		
		NOT INVALID KEY 
		DISPLAY
		END NOT INVALID KEY
		
	END-WRITE
 * @param ender 
 */
		
	private void addVirtualEndingCommandsForWriteFile(String mode, String ender) {
		
		AbstractCommand curCommand;
		
		for (int index=0; index < commandList.size();index++) {
		
			curCommand=commandList.get(index);
			
			if(curCommand.getCommandName().equals(mode)){  //READ, WRITE,DELETE , REWRITE, START GELMISSE
				
				index++; 
				
				curCommand=commandList.get(index);
				
				while(true){
					//Ender gelmisse bitir.
					if(curCommand.getCommandName().equals(ender)){
						break;
					//Ozel Kelime Gelmisse
					}else if(curCommand.getCommandName().equals("ElementInvalidKey")||  
							curCommand.getCommandName().equals("ElementNotInvalidKey")||
							curCommand.getCommandName().equals("ElementNotAtEnd")||
							curCommand.getCommandName().equals("ElementAtEnd")){
						//+3 yap.
						index=index+3;
						curCommand=commandList.get(index);
				    //Ender Gelmezse ve Özel Kelime Gelmezse	
					}else {
						//Ender ekle ve bitir.
						commandList.add(index, createFileStatementEnder(mode));
						break;
					}
						
				}
				
			}
		}
		
	}

	private AbstractCommand createFileStatementEnder(String mode) {
		AbstractCommand retCom;
		switch(mode){
	    case ReservedCobolKeywords.WRITE_FILE :
	    	retCom=new ElementEndWriteFile();
	    	return retCom;
	    case ReservedCobolKeywords.REWRITE_FILE :
	    	retCom=new ElementEndReWriteFile();
	    	return retCom;
	    case ReservedCobolKeywords.DELETE_FILE :
	    	retCom=new ElementEndDeleteFile();
	    	return retCom;
	    case ReservedCobolKeywords.START_FILE :
	    	retCom=new ElementEndStartFile();
	    	return retCom;	
	    case ReservedCobolKeywords.READ_FILE :
	    	retCom=new ElementEndReadFile();
	    	return retCom;		
		}
	    return null;	
	}

	

	
	private void addVirtualEndingCommandsForFileStatementsChildren() {
		 AbstractCommand curCommand;
			
			for (int index=0; index < commandList.size();index++) {
			
				curCommand=commandList.get(index);
				if(curCommand.getCommandName().equals("ElementInvalidKey")){
					
					index++; //MOVE 1  TO VCV-OKEY
					
					index++; //END_INVALID_KEY
					
					commandList.add(index, new ElementEndInvalidKey());
					
				}else if(curCommand.getCommandName().equals("ElementNotInvalidKey")){
						
						index++; //MOVE 1  TO VCV-OKEY
						
						index++; //END_INVALID_KEY
						
						commandList.add(index, new ElementEndNotInvalidKey());
						
				}else if(curCommand.getCommandName().equals("ElementAtEnd")){
					
					index++; //MOVE 1  TO VCV-OKEY
					
					index++; //END_INVALID_KEY
					
					commandList.add(index, new ElementEndAtEnd());
				}else if(curCommand.getCommandName().equals("ElementNotAtEnd")){
					
					index++; //MOVE 1  TO VCV-OKEY
					
					index++; //END_INVALID_KEY
					
					commandList.add(index, new ElementEndNotAtEnd());
				}
				
			}
			
		}

	
	private void addVirtualEndingCommandsForParagraphs() {
		int tokenIndex=0;
		
		AbstractCommand curCommand;
		
		boolean firstParagraphReached=false;
		
		for (int index=0; index < commandList.size();index++) {
			
			curCommand=commandList.get(index);
			
			if(curCommand.getCommandName().equals(ReservedCobolKeywords.PARAGRAPH)||  
					curCommand.getCommandName().equals(ReservedCobolKeywords.MAIN_PARAGRAPH)||
					curCommand.getCommandName().equals(ReservedCobolKeywords.END_PROCEDURE_DIVISION)){ //En sona geldi ise de ekle
				if(firstParagraphReached){
					addEnderForParagraph();
					commandList.add(index, new ElementEndParagraph());
					index++; //Ekleyince bir kaydırki tekrar tekrar eklemesin.
				}
				firstParagraphReached=true;
				continue;
			}
		}
		
	}

	private void addEnderForParagraph() {
		// TODO Auto-generated method stub
		
	}
	
	private void addVirtualEndingCommandsForFileDefinition() {
		boolean addEnderForFileDef=false;
		
		ElementEndFileDescription elementEndFileDescription = new ElementEndFileDescription(ReservedCobolKeywords.END_FILE_DESCRIPTION,"");
		
		AbstractCommand curCommand;
		
		AbstractCommand nextCommand;
		
		
		
		for (int index=0; index<commandList.size()-1;index++) {
			if(index==45){
				logger.info("debug");
			}
			curCommand=commandList.get(index);
			nextCommand=commandList.get(index+1);
			
			if(curCommand.getCommandName().equals(ReservedCobolKeywords.FILE_DESCRIPTION)){
				addEnderForFileDef=true;
			}else if(addEnderForFileDef&&
						(nextCommand.getCommandName().equals(ReservedCobolKeywords.FILE_DESCRIPTION)||
						(nextCommand.getCommandName().equals(ReservedCobolKeywords.WORKING_STORAGE_SECTION)	)	)	){

				commandList.add(index+1, elementEndFileDescription);
				addEnderForFileDef=false;
			}
		}
	}

	private void addVirtualEndingCommandsForFileControl() {
		boolean addEnderForFileControl=false;
		
		int tokenIndex=0;
		ElementEndFileControl elementEndFileControl = new ElementEndFileControl(ReservedCobolKeywords.END_FILE_CONTROL,"");
		
		for (AbstractCommand command : commandList) {
			if(command.getCommandName().equals(ReservedCobolKeywords.FILE_CONTROL)){
				addEnderForFileControl=true;
			}else if(addEnderForFileControl&&
					(command.getCommandName().equals(ReservedCobolKeywords.I_O_CONTROL)||
					(command.getCommandName().equals(ReservedCobolKeywords.END_INPUTOUTPUT_SECTION)))){
				break;
			}
			tokenIndex++;
		}
		if(addEnderForFileControl){
			commandList.add(tokenIndex, elementEndFileControl);
		}
	}

	private void addVirtualEndingCommandsForIOControl() {
		boolean addEnderForIOControl=false;
		
		int tokenIndex=0;
		ElementEndIOControl elementEndIOControl = new ElementEndIOControl(ReservedCobolKeywords.END_IO_CONTROL,"");
		
		for (AbstractCommand command : commandList) {
			if(command.getCommandName().equals(ReservedCobolKeywords.I_O_CONTROL)){
				addEnderForIOControl=true;
			}else if(addEnderForIOControl&&
					command.getCommandName().equals(ReservedCobolKeywords.END_INPUTOUTPUT_SECTION)){
				break;
			}
			tokenIndex++;
		}
		if(addEnderForIOControl){
			commandList.add(tokenIndex, elementEndIOControl);
		}
		
	}
	
	/**
	 * Environment Division
			CONFIGURATION SECTION.
			INPUT-OUTPUT SECTION.
	 */
	private void addVirtualEndingCommandsForConfigurationSection() {
		boolean addEnderForConfigurationSection=false;
		
		int tokenIndex=0;
		ElementEndConfigurationSection elementEndConfigurationSection = new ElementEndConfigurationSection(ReservedCobolKeywords.END_CONFIGURATION_SECTION,"");
		
		for (AbstractCommand command : commandList) {
			if(command.getCommandName().equals(ReservedCobolKeywords.CONFIGURATION_SECTION)){
				addEnderForConfigurationSection=true;
			}else if(addEnderForConfigurationSection&&(command.getCommandName().equals(ReservedCobolKeywords.INPUT_OUTPUT_SECTION)
					||command.getCommandName().equals(ReservedCobolKeywords.END_DATA_DIVISION))){
				break;
			}
			tokenIndex++;
		}
		if(addEnderForConfigurationSection){
			commandList.add(tokenIndex, elementEndConfigurationSection);
		}
	}
	
	
	/**
	 * Environment Division
			CONFIGURATION SECTION.
			INPUT-OUTPUT SECTION.
	 */
	private void addVirtualEndingCommandsForInputOutputSection() {
		boolean addEnderForInputOutputSection=false;
		
		int tokenIndex=0;
		ElementEndInputOutputSection elementInputOutputSection = new ElementEndInputOutputSection(ReservedCobolKeywords.END_INPUTOUTPUT_SECTION,"");
		
		for (AbstractCommand command : commandList) {
			if(command.getCommandName().equals(ReservedCobolKeywords.INPUT_OUTPUT_SECTION)){
				addEnderForInputOutputSection=true;
			}else if(addEnderForInputOutputSection&&command.getCommandName().equals(ReservedCobolKeywords.END_ENVIRONMENT_DIVISION)){
				break;
			}
			tokenIndex++;
		}
		if(addEnderForInputOutputSection){
			commandList.add(tokenIndex, elementInputOutputSection);
		}
	}

	/**
	 * Data Division
			File section
			Working-Storage section
			Local-Storage section
			Linkage section
	 * @param commandList2
	 */
	private void addVirtualEndingCommandsForFileSection() {
		boolean addEnderForelementEndFileSection=false;
		
		int tokenIndex=0;
		ElementEndFileSection elementEndFileSection = new ElementEndFileSection(ReservedCobolKeywords.END_FILE_SECTION,"");
		
		for (AbstractCommand command : commandList) {
			if(command.getCommandName().equals(ReservedCobolKeywords.FILE_SECTION)){
				addEnderForelementEndFileSection=true;
			}else if(addEnderForelementEndFileSection&&
					(command.getCommandName().equals(ReservedCobolKeywords.END_DATA_DIVISION)||
					command.getCommandName().equals(ReservedCobolKeywords.WORKING_STORAGE_SECTION)||
					command.getCommandName().equals(ReservedCobolKeywords.LOCAL_STORAGE_SECTION)||
					command.getCommandName().equals(ReservedCobolKeywords.LINKAGE_SECTION))){
				break;
			}
			tokenIndex++;
		}
		if(addEnderForelementEndFileSection){
			commandList.add(tokenIndex, elementEndFileSection);
		}
	}
	
	/**
	 * Data Division
			File section
			Working-Storage section
			Local-Storage section
			Linkage section
	 * @param commandList2
	 */
	private void addVirtualEndingCommandsForWorkingStorageSection() {
		boolean addEnderForelementEndWorkingStorageSection=false;
		
		int tokenIndex=0;
		ElementEndWorkingStorageSection elementEndWorkingStorageSection = new ElementEndWorkingStorageSection(ReservedCobolKeywords.END_WORKING_STORAGE_SECTION,"");
		
		for (AbstractCommand command : commandList) {
			if(command.getCommandName().equals(ReservedCobolKeywords.WORKING_STORAGE_SECTION)){
				addEnderForelementEndWorkingStorageSection=true;
			}else if(addEnderForelementEndWorkingStorageSection&&
					(command.getCommandName().equals(ReservedCobolKeywords.END_DATA_DIVISION)||
					command.getCommandName().equals(ReservedCobolKeywords.LOCAL_STORAGE_SECTION)||
					command.getCommandName().equals(ReservedCobolKeywords.LINKAGE_SECTION))||
					command.getCommandName().equals(ReservedCobolKeywords.PROCEDURE_DIVISION)){
				break;
			}
			tokenIndex++;
		}
		if(addEnderForelementEndWorkingStorageSection){
			commandList.add(tokenIndex, elementEndWorkingStorageSection);
		}
	}
	
	/**
	 * Data Division
			File section
			Working-Storage section
			Local-Storage section
			Linkage section
	 * @param commandList2
	 */
	private void addVirtualEndingCommandsForLocalStorageSection() {
		
		boolean addEnderForelementEndLocalStorageSection=false;
		
		int tokenIndex=0;
		ElementEndLocalStorageSection elementEndLocalStorageSection = new ElementEndLocalStorageSection(ReservedCobolKeywords.END_LOCAL_STORAGE_SECTION,"");
		
		for (AbstractCommand command : commandList) {
			if(command.getCommandName().equals(ReservedCobolKeywords.LOCAL_STORAGE_SECTION)){
				addEnderForelementEndLocalStorageSection=true;
			}else if(addEnderForelementEndLocalStorageSection&&
					(command.getCommandName().equals(ReservedCobolKeywords.END_DATA_DIVISION)||
					command.getCommandName().equals(ReservedCobolKeywords.LINKAGE_SECTION))){
				break;
			}
			tokenIndex++;
		}
		if(addEnderForelementEndLocalStorageSection){
			commandList.add(tokenIndex, elementEndLocalStorageSection);
		}
	}
	
	/**
	 * Data Division
			File section
			Working-Storage section
			Local-Storage section
			Linkage section
	 * @param commandList2
	 */
	private void addVirtualEndingCommandsForLinkageSection() {
		
		boolean addEnderForelementEndLinkageSection=false;
		
		int tokenIndex=0;
		ElementEndLinkageSection elementEndLinkageSection = new ElementEndLinkageSection(ReservedCobolKeywords.END_LINKAGE_SECTION,"");
		
		for (AbstractCommand command : commandList) {
			if(command.getCommandName().equals(ReservedCobolKeywords.LINKAGE_SECTION)){
				addEnderForelementEndLinkageSection=true;
			}else if(addEnderForelementEndLinkageSection&&
					command.getCommandName().equals(ReservedCobolKeywords.END_DATA_DIVISION)){
				break;
			}
			tokenIndex++;
		}
		if(addEnderForelementEndLinkageSection){
			commandList.add(tokenIndex, elementEndLinkageSection);
		}
	}
	
	private void addEnderForIdentificationDivision(
			List<AbstractCommand> commandList2) {
		
		boolean addEndIdentficationDivision=false;
		ElementEndIdentificationDivision elementEndIdentificationDivision= new ElementEndIdentificationDivision(ReservedCobolKeywords.END_IDENTIFICATION_DIVISION,"");
		
		int tokenIndex=0;
		for (AbstractCommand command : commandList) {
			if(command.getCommandName().equals(ReservedCobolKeywords.IDENTIFICATION_DIVISION)){
				addEndIdentficationDivision=true;
			}else if(addEndIdentficationDivision&&(command.getCommandName().equals(ReservedCobolKeywords.ENVIRONMENT_DIVISION)
					||command.getCommandName().equals(ReservedCobolKeywords.DATA_DIVISION)
					||command.getCommandName().equals(ReservedCobolKeywords.PROCEDURE_DIVISION))
					){
				break;
			}
			tokenIndex++;
		}
		if(addEndIdentficationDivision){
			commandList.add(tokenIndex, elementEndIdentificationDivision);
		}
		
	}
	
	private void addEnderForEnvironmentDivision(
			List<AbstractCommand> commandList2) {
		
		boolean addEndEnvironmentDivision=false;
		ElementEndEnvironmentDivision elementEndEnvironmentDivision= new ElementEndEnvironmentDivision(ReservedCobolKeywords.END_ENVIRONMENT_DIVISION,"");
		
		int tokenIndex=0;
		for (AbstractCommand command : commandList) {
			if(command.getCommandName().equals(ReservedCobolKeywords.ENVIRONMENT_DIVISION)){
				addEndEnvironmentDivision=true;
			}else if(command.getCommandName().equals(ReservedCobolKeywords.DATA_DIVISION)
					||command.getCommandName().equals(ReservedCobolKeywords.PROCEDURE_DIVISION)){
				break;
			}
			tokenIndex++;
		}
		if(addEndEnvironmentDivision){
			commandList.add(tokenIndex, elementEndEnvironmentDivision);
		}
		
	}
	
	
	private void addEnderForDataDivision(
			List<AbstractCommand> commandList2) {
		
		boolean addDataDivisionEnd=false;
		ElementEndDataDivision elementEndDataDivision= new ElementEndDataDivision(ReservedCobolKeywords.END_DATA_DIVISION,"");
			
		int tokenIndex=0;
		for (AbstractCommand command : commandList) {
			if(command.getCommandName().equals(ReservedCobolKeywords.DATA_DIVISION)){
				addDataDivisionEnd=true;
			}else if(command.getCommandName().equals(ReservedCobolKeywords.PROCEDURE_DIVISION)){
				break;
			}
			tokenIndex++;
		}
		if(addDataDivisionEnd){
			commandList.add(tokenIndex, elementEndDataDivision);
		}
		
	}
	
	private void addEnderForProcedureDivision(
			List<AbstractCommand> commandList2) {
		
		boolean addProcedureDivisionEnd=false;
		ElementEndProcedureDivision elementEndProcedureDivision= new ElementEndProcedureDivision(ReservedCobolKeywords.END_PROCEDURE_DIVISION,"");
			
		for (AbstractCommand command : commandList) {
			if(command.getCommandName().equals(ReservedCobolKeywords.PROCEDURE_DIVISION)){
				addProcedureDivisionEnd=true;
				break;
			}
		}
		if(addProcedureDivisionEnd){
			commandList.add(elementEndProcedureDivision);
			
		}
		
	}
	
	

	

	
	/**
	 * 
000100 01  WS-CONTROL-INFO.                                             00010000    1 current.GroupDataType=true, --> putToBuffer
000101     05  FILLER                  PIC X(5).                        00010100	2 current.dataType=true, next.dataType=true, currentLevel<=nextLevel--> doNothing
000102     05  WS-CARD-JOB-ID          PIC X(8).                        00010200    2 current.dataType=true, next.dataType=true, currentLevel<=nextLevel --> doNothing
000103     05  WS-CARD-JOB-ACTION      PIC X(8).                        00010300	3 current.dataType=true, next.GroupDataType=true, next.level<=current.level --> doNothing
000104     05  WS-CARD-SOURCE-OR-DD.                                    00010400    1 current.GroupDataType=true, --> putToBuffer
000105         10  WS-CARD-SOURCE-ONLY PIC X(8).                        00010500    2 current.dataType=true, next.dataType=true, currentLevel<=nextLevel --> doNothing
000106         10  WS-CARD-DDNAME      PIC X(8).                        00010700    3 current.dataType=true, next.GroupDataType=true, currentLevel<=nextLevel --> doNothing
000108         10  WS-CARD-DSID-AREA   							        00010800	1 current.GroupDataType=true, --> putToBuffer
000109             15  WS-CARD-DSID-PREFIX.                             00010900	1 current.GroupDataType=true, --> putToBuffer
000110                 20  WS-CARD-DSID-PREFIX-1 PIC X(01).                       	2 current.dataType=true, next.dataType=true, currentLevel<=nextLevel --> doNothing
000112                 20  WS-CARD-DSID-PREFIX-2 PIC X(01).                         2 current.dataType=true, next.dataType=true, currentLevel<=nextLevel --> doNothing
000114                 20  WS-CARD-DSID-PREFIX-3 PIC X(01).                         4 current.dataType=true, next.dataType=true, currentLevel>nextLevel --> addEnderFromBuffer
000116             15  WS-CARD-DSID-FILLER PIC X(05).                       		4 current.dataType=true, next.dataType=true, currentLevel>nextLevel--> addEnderFromBuffer
000118         10  WS-CARD-NUMB-TERM   PIC 9(4).                        00011800	3 current.dataType=true, next.GroupDataType=true, currentLevel<=nextLevel --> doNothing
000119         10  WS-CARD-VERSION-AREA   REDEFINES  WS-CARD-NUMB-TERM. 00011900	1 current.GroupDataType=true, --> putToBuffer
000121             15  WS-CARD-VERSION   PIC X(01).						00012100	2 current.dataType=true, next.dataType=true, currentLevel<=nextLevel--> doNothing
000123             15  WS-CARD-VERSION-FILLER PIC X(03).   				00012300	5 current.dataType=true, next.GroupDataType=true, currentLevel>nextLevel --> addEnderFromBuffer
000125     05  WS-CARD-APPLICATION     REDEFINES WS-CARD-SOURCE-OR-DD.  00012500 	1 current.GroupDataType=true, --> putToBuffer
000126         10  WS-CARD-APP-ID      PIC X                            00012600	2  current.dataType=true, next.dataType=true, currentLevel<=nextLevel --> doNothing
000129         10  FILLER              PIC XX.                          00012900	4 current.dataType=true, next.dataType=true, currentLevel>nextLevel --> addEnderFromBuffer
000130     05  FILLER                  PIC X(47).                       00013000    6 current.dataType=true, next.dataType=false,  next.GroupDataType=false --> addEnderFromBuffer
	 * 
	 * 
	 * 1) current.GroupDataType=true, --> putToBuffer
	 * 2) current.DataType=true, next.dataType=false,  next.GroupDataType=false--> addEnderFromBuffer--> addEnderFromBuffer since  currentLevel<=01
	 * 3) current.DataType=true,currentLevel>nextLevel --> addEnderFromBuffer--> addEnderFromBuffer since  currentLevel<=nextLevel
	 * 
	 */
	public void findAndSetDataTypeDefinitions() {
		
		DataTypesCommandsUtility utility=new DataTypesCommandsUtility();
		
		AbstractCommand currentCommand,nextCommand;
		
		Levelable currentLevelable = null, nextLevelable = null;
		
		for(int index=0; index<commandList.size()-1;index++){
			currentCommand=commandList.get(index);
			nextCommand=commandList.get(index+1);
			logger.info("Current Command:"+ currentCommand.toString());
			logger.info("Next Command:"+ nextCommand.toString());
			//1) current.GroupDataType=true, --> putToBuffer
			if(DataTypesCommandsUtility.isGroupDataType(currentCommand)){
				currentLevelable=(Levelable) currentCommand;
				currentLevelable = new ElementEndGroupDataType(ReservedCobolKeywords.END_GROUP_DATA_TYPE,"",currentLevelable.getLevelNumber());
				logger.info("PUT TO BUFFER:"+currentCommand.toString());
				utility.putEndingCommandToBuffer(currentLevelable);
			
			
			//2) current.DataType=true, next.dataType=false,  next.GroupDataType=false--> addEnderFromBuffer--> addEnderFromBuffer since  currentLevel<=01
			}else if(DataTypesCommandsUtility.isDataType(currentCommand)		&&	!DataTypesCommandsUtility.isDataType(nextCommand)	&&	!DataTypesCommandsUtility.isGroupDataType(nextCommand)){
				//addEnderFromBuffer since  currentLevel<=01
				while(utility.bufferHasElement()){
					currentLevelable=utility.getEndGroupDataTypeToBuffer();
					logger.info("**************************START***************************************");
					logger.info("ADD1 FROM BUFFER:"+currentCommand.toString()+" Ender Name:"+ ((AbstractCommand)currentLevelable).toString());
					logger.info("Buffer Size:"+utility.bufferSize());
					logger.info("**************************END***************************************");
					System.out.println();
					System.out.println();
					System.out.println();
					index++;
					commandList.add(index, (AbstractCommand) currentLevelable);
				};
				
			//3) current.DataType=true,currentLevel>nextLevel --> addEnderFromBuffer--> addEnderFromBuffer since  currentLevel>nextLevel	
			}else if(DataTypesCommandsUtility.isDataType(currentCommand)&& (DataTypesCommandsUtility.isDataType(nextCommand)	|| DataTypesCommandsUtility.isGroupDataType(nextCommand))){
				
				currentLevelable=(Levelable) currentCommand;
				nextLevelable=(Levelable) nextCommand;
				
				if(nextLevelable.getLevelNumber()<currentLevelable.getLevelNumber()){
					
					do{
						if(!utility.bufferHasElement()){
							break;
						}
						currentLevelable=utility.getEndGroupDataTypeToBuffer();
						if(nextLevelable.getLevelNumber()>currentLevelable.getLevelNumber()){
							utility.putEndingCommandToBuffer(currentLevelable);
							break;
						}
						logger.info("**************************START***************************************");
						logger.info("ADD2 FROM BUFFER:"+currentCommand.toString()+" Ender Name:"+ ((AbstractCommand)currentLevelable).toString());
						logger.info("Buffer2 Size:"+utility.bufferSize());
						logger.info("**************************END***************************************");
						System.out.println();
						System.out.println();
						System.out.println();
						index++;
						commandList.add(index, (AbstractCommand) currentLevelable);
					}while(true);
					
				}
				
			}
			
		};
		
	}

	public StringBuilder writeCommands(String fullFilePath) {
		StringBuilder sb = new StringBuilder();
		for (AbstractCommand command : commandList) {
			sb.append(command.exportCommands());
		}
		try {
			WriteToFile.writeToFile(sb,fullFilePath);
		} catch (IOException e) {
			 logger.debug("",e);
		}
		return sb;
		
	}
	
	@Override
	public void setRedefineElementsParents() {
		
	}



	@Override
	public void generateDatatypeMap() {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void markSimpleRedefineElements() {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void markTwoDimensionRedefineElements() {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void parseDataTypeTokensToCommandsList() {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void parseCommandTokensToCommandsList() {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void createAndFillRecordDataTypeMap() {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void addGrupNamesToRecordItems() {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void controlRecords() {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void markRedefinedTokens() {
		// TODO Auto-generated method stub
		
	}




	@Override
	public void markOneDimensionRedefineElementOfSimpleDataType() {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void setTypeNameOfView() {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void changeProgramDataTypeToDBDataType() {
		// TODO Auto-generated method stub
		
	}


	
	
}
