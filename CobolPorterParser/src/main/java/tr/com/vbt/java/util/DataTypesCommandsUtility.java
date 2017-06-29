package tr.com.vbt.java.util;

import java.util.LinkedList;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.Levelable;
import tr.com.vbt.cobol.parser.datalayout.ElementDataTypeCobol;
import tr.com.vbt.cobol.parser.datalayout.ElementGroupDataType;
import tr.com.vbt.natural.parser.datalayout.db.ElementDBDataTypeNatural;
import tr.com.vbt.natural.parser.datalayout.db.ElementDBMultipleUnitDataTypeNatural;
import tr.com.vbt.natural.parser.datalayout.db.ElementDBRedefineDataGroupNatural;
import tr.com.vbt.natural.parser.datalayout.db.ElementDBViewOfNatural;
import tr.com.vbt.natural.parser.datalayout.program.ElementProgramDataTypeNatural;
import tr.com.vbt.natural.parser.datalayout.program.ElementProgramGrupNatural;
import tr.com.vbt.natural.parser.datalayout.program.ElementProgramOneDimensionArrayNatural;
import tr.com.vbt.natural.parser.datalayout.program.ElementProgramTwoDimensionArrayNatural;
import tr.com.vbt.natural.parser.datalayout.program.redefiners.ElementOneDimenRedefineArrayOfSimpleDataType;
import tr.com.vbt.natural.parser.datalayout.program.redefiners.ElementProgramRedefineGrupNatural;
import tr.com.vbt.natural.parser.datalayout.program.redefiners.ElementRedefineDataTypeOfSimpleDataType;

public class DataTypesCommandsUtility {
	
	private LinkedList<Levelable> groupDataTypeBuffer = new LinkedList<Levelable>();
	
	public static boolean isGroupDataType(AbstractCommand command){
    	if(command instanceof ElementGroupDataType ||
    			command instanceof ElementDBViewOfNatural ||
    			command instanceof ElementDBMultipleUnitDataTypeNatural ||
    			command instanceof ElementDBRedefineDataGroupNatural ||
    			command instanceof ElementProgramRedefineGrupNatural||
    			command instanceof ElementProgramGrupNatural){
    		return true;
    	} 
    	return false;
    }
	
	public static boolean isRecordGroupDataType(AbstractCommand command){
    	if(	command instanceof ElementProgramGrupNatural){
    		return true;
    	} 
    	return false;
    }
     
	 public static boolean isDataType(AbstractCommand command){
		 if(command instanceof ElementDataTypeCobol||
				 command instanceof ElementDBDataTypeNatural||
				 command instanceof ElementProgramDataTypeNatural||
				 command instanceof ElementProgramOneDimensionArrayNatural||
				 command instanceof ElementProgramTwoDimensionArrayNatural||
				 command instanceof ElementOneDimenRedefineArrayOfSimpleDataType||
				 command instanceof ElementRedefineDataTypeOfSimpleDataType
				 ){
	    		return true;
	    	}
	    	return false;
	    }
	   
	 public int bufferSize() {
		 return groupDataTypeBuffer.size();
	 }
	
	public boolean bufferHasElement() {
		if(groupDataTypeBuffer.isEmpty()){
			return false;
		}
		return true;
	}

	public Levelable getEndGroupDataTypeToBuffer() {
		return groupDataTypeBuffer.removeLast();
	}
	
	


	public void putEndingCommandToBuffer(Levelable endingCommand) {
			//logger.info("PUT TO BUFFER:"+((AbstractCommand)endingCommand).getCommandName());
			groupDataTypeBuffer.add(endingCommand);
	}
   
    
    
    

}
