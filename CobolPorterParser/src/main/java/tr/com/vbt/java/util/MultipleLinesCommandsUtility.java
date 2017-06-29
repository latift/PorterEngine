package tr.com.vbt.java.util;

import java.util.LinkedList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.AbstractEndingCommand;
import tr.com.vbt.cobol.parser.AbstractMultipleLinesCommand;
import tr.com.vbt.lexer.ReservedNaturalKeywords;

public class MultipleLinesCommandsUtility {
	
	final static Logger logger = LoggerFactory.getLogger(MultipleLinesCommandsUtility.class);
	
	private LinkedList<AbstractMultipleLinesCommand> tokenListBuffer = new LinkedList<AbstractMultipleLinesCommand>();
	
    public static boolean isStarter(AbstractCommand starterCommandToTest){
    	if(starterCommandToTest instanceof AbstractMultipleLinesCommand ){
    		return true;
    	}
    	return false;
    }
    
    public static boolean isEnder(AbstractCommand enderCommandToTest){
    	if(enderCommandToTest instanceof AbstractEndingCommand ){
    		return true;
    	}
    	return false;
    }
   
    public void putStarterToBuffer(AbstractMultipleLinesCommand command){
    	Object[] bufferAsList;
    	AbstractCommand abstractCommandInList;
    	String currentBufferAsString=new String("");
    	logger.info("---------------------------------------------------------------------");
    	logger.info("Starter:"+command);
    	logger.info("PUT TO BUFFER: "+command.getCommandName()+ " SatirNo:"+command.getSatirNumarasi());
    	tokenListBuffer.add(command);
    	
    	bufferAsList= tokenListBuffer.toArray();
    	for (Object abstractCommand : bufferAsList) {
    		abstractCommandInList=(AbstractCommand) abstractCommand;
    		currentBufferAsString=currentBufferAsString+abstractCommandInList.getCommandName()+" ";
    	}
    	logger.info("CURRENT BUFFER AFTER PUT : "+currentBufferAsString);
        
    	logger.info(" Buffer Size After Put:"+this.bufferSize());
    	logger.info("---------------------------------------------------------------------");
    }
   
   
    public void registerEnderWithBufferLastStarter(AbstractEndingCommand enderCommandToRegister) throws Exception{
    	Object[] bufferAsList;
    	AbstractCommand abstractCommandInList;
    	String currentBufferAsString=new String("");
    	logger.info("---------------------------------------------------------------------");
    	logger.info("REMOVE FROM BUFFER AND REGISTER:"+enderCommandToRegister.getCommandName() + " SatirNo:"+enderCommandToRegister.getSatirNumarasi());
    	AbstractMultipleLinesCommand lastStarterCommand=tokenListBuffer.removeLast();
    	lastStarterCommand.registerEnderCommand(enderCommandToRegister);
    	
    	logger.info("Register...:"+lastStarterCommand.getSatirNumarasi()+" nolu satirdaki " +lastStarterCommand.getCommandName()+ " starter ile "+enderCommandToRegister.getSatirNumarasi()+" nolu satirdaki "+enderCommandToRegister.getCommandName() +" ender register ediliyor");
    	
    	if(!verifyEnderStarterRelation(lastStarterCommand,enderCommandToRegister)){
    		logger.warn("Register...:"+lastStarterCommand.getSatirNumarasi()+" nolu satirdaki " +lastStarterCommand.getCommandName()+ " starter ile "+enderCommandToRegister.getSatirNumarasi()+" nolu satirdaki "+enderCommandToRegister.getCommandName() +" ender register ediliyor");
        	throw new Exception("Starter Ender Uyumsuz: "+lastStarterCommand.toString()+" "+enderCommandToRegister.toString());
    	}
    	enderCommandToRegister.registerStarterCommand(lastStarterCommand);
    	
    	
    	bufferAsList= tokenListBuffer.toArray();
    	for (Object abstractCommand : bufferAsList) {
    		abstractCommandInList=(AbstractCommand) abstractCommand;
    		currentBufferAsString=currentBufferAsString+abstractCommandInList.getCommandName()+" ";
    	}
    	logger.info("CURRENT BUFFER AFTER REMOVE : "+currentBufferAsString);
    	
    	
    	logger.info(" Buffer Size After Remove:"+this.bufferSize()+" ");
    	logger.info("---------------------------------------------------------------------");
       }
    
    private boolean verifyEnderStarterRelation(AbstractMultipleLinesCommand lastStarterCommand,
			AbstractEndingCommand enderComandToRegister) {
    	
    	if(enderComandToRegister.getCommandName().equals("LOOP")&&
    		(lastStarterCommand.getCommandName().equals("FIND_WITH") ||
    				lastStarterCommand.getCommandName().equals("READ")||
    				lastStarterCommand.getCommandName().equals("FOR")||
    				lastStarterCommand.getCommandName().equals("END_REPEAT")||
    				lastStarterCommand.getCommandName().equals("READ_BY")||
    				lastStarterCommand.getCommandName().equals("REPEAT_UNTIL")||
    				lastStarterCommand.getCommandName().equals("REPEAT")||
    				lastStarterCommand.getCommandName().equals("UNTIL")||
    				lastStarterCommand.getCommandName().equals("FIND_NUMBER_WITH"))){
    			return true;
    	}
    	
    	if((enderComandToRegister.getCommandName().equals(ReservedNaturalKeywords.LOOP)|| enderComandToRegister.getCommandName().equals("END_FIND"))&&
        		lastStarterCommand.getCommandName().equals(ReservedNaturalKeywords.FIND_ONE_WITH)){
        			return true;
        	}
    	

    	if(enderComandToRegister.getCommandName().equals("END_ENDPAGE") &&
    			lastStarterCommand.getCommandName().equals("AT_TOP_OF_PAGE")){
    		return true;
    	}
    	
    	if(enderComandToRegister.getCommandName().equals("END_IF") &&
    			lastStarterCommand.getCommandName().equals("ELSE_IF")){
    		return true;
    	}
    	
    	if(enderComandToRegister.getCommandName().equals("END_IF") &&
    			lastStarterCommand.getCommandName().equals("IF_NO_RECORDS")){
    		return true;
    	}
    	
    	//ElementEndIf  ElementIf
    	if(enderComandToRegister.getCommandName().equals("END_DEFINE") &&
    			lastStarterCommand.getCommandName().equals("DEFINE_DATA")){
    		return true;
    	}
    	if(enderComandToRegister.getCommandName().equals("END_GROUP_DATA_TYPE") &&
    			lastStarterCommand.getCommandName().equals("DB_REDEFINE_GROUP_DATA")){
    		return true;
    	}
    	if(enderComandToRegister.getCommandName().equals("END_GROUP_DATA_TYPE") &&
    			lastStarterCommand.getCommandName().equals("PROGRAM_REDEFINE_GROUP")){
    		return true;
    	}
    	
    	if(enderComandToRegister.getCommandName().equals("END_GROUP_DATA_TYPE") &&
    			lastStarterCommand.getCommandName().equals("PROGRAM_GROUP")){
    		return true;
    	}
    	
    	if(enderComandToRegister.getCommandName().equals("END_GROUP_DATA_TYPE") &&
    			lastStarterCommand.getCommandName().equals("MU_DATA_TYPE")){
    		return true;
    	}
    	
    	if(enderComandToRegister.getCommandName().equals("END") &&
    			lastStarterCommand.getCommandName().equals("MAIN_START")){
    		return true;
    	}
    	if(enderComandToRegister.getCommandName().equals("END_GROUP_DATA_TYPE") &&
    			lastStarterCommand.getCommandName().equals("VIEW_OF")){
    		return true;
    	}
   
    	if(enderComandToRegister.getCommandName().equals("END_IF") &&
    			lastStarterCommand.getCommandName().equals("ELSE")){
    		return true;
    	}
    	if(enderComandToRegister.getCommandName().equals("END_NOREC") &&
    			lastStarterCommand.getCommandName().equals("IF_NO_RECORDS")){
    		return true;
    	}
    	if(enderComandToRegister.getCommandName().equals("END_FIND") &&
    			lastStarterCommand.getCommandName().equals("FIND_WITH")){
    		return true;
    	}
    	if(enderComandToRegister.getCommandName().equals("END_FIND") &&
    			lastStarterCommand.getCommandName().equals("FIND_NUMBER_WITH")){
    		return true;
    	}
    	if(enderComandToRegister.getCommandName().equals("END_REPEAT") &&
    			lastStarterCommand.getCommandName().equals("REPEAT_UNTIL")){
    		return true;
    	}
    	if(enderComandToRegister.getCommandName().equals("END_READ") &&
    			lastStarterCommand.getCommandName().equals("READ_BY")){
    		return true;
    	}
    	if(enderComandToRegister.getCommandName().equals("END-DECIDE") &&
    			lastStarterCommand.getCommandName().equals("DECIDE_ON")){
    		return true;
    	}
    	if(enderComandToRegister.getCommandName().equals("END_ENDPAGE") &&
    			lastStarterCommand.getCommandName().equals("AT_END_OF_PAGE")){
    		return true;
    	}
    	if(enderComandToRegister.getCommandName().equals("END_TOP_OF_PAGE") &&
    			lastStarterCommand.getCommandName().equals("AT_TOP_OF_PAGE")){
    		return true;
    	}
    	if(enderComandToRegister.getCommandName().equals("END_ERROR") &&
    			lastStarterCommand.getCommandName().equals("ON_ERROR")){
    		return true;
    	}
    	if(enderComandToRegister.getCommandName().toLowerCase().contains(lastStarterCommand.getCommandName().toLowerCase())){
    		return true;
    	}
    	if(enderComandToRegister.getCommandName().replaceAll("-", "").replaceAll("_", "").toLowerCase().contains(lastStarterCommand.getCommandName().replaceAll("-", "").replaceAll("_", "").toLowerCase())){
    		return true;
    	}
    	logger.debug("lastStarterCommand.getCommandName()  :"+(lastStarterCommand.getCommandName())+" enderComandToRegister.getCommandName()  :"+enderComandToRegister.getCommandName());
		return false;
	}

	public int bufferSize(){
    	return tokenListBuffer.size();
    }
    
   
    
    
    

}
