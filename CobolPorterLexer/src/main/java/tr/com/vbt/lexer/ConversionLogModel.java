package tr.com.vbt.lexer;

import java.util.Date;

import tr.com.vbt.util.ConverterConfiguration;
import tr.com.vbt.util.ConversionMode;

public class ConversionLogModel {

	private String user;
	
	private Date date;
	
	private String fileName;
	
	private String folderPath;
	
	private String folderPathMap;
	
	private String folderMainPath;
	
	private Phase phase;
	
	private NaturalMode Mode;
	
	private StringBuilder output;
	
	public String customer;
	
	public String module;

	public String schemaName;
	
	public String OPERATING_SYSTEM;
	
	private static ConversionLogModel instance;
	
	private static ConversionMode conversionMode;
	
	private String isProgramOrMap;
	
	private boolean subProgram;
	
	private boolean isClientInteracting;
	
	public static ConversionLogModel getInstance() {
		if (instance==null){
			instance=new ConversionLogModel();
		}
		return  instance;
	}

	private ConversionLogModel() {
		super();
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	

	public Phase getPhase() {
		return phase;
	}

	public void setPhase(Phase phase) {
		this.phase = phase;
	}

	public StringBuilder getOutput() {
		return output;
	}

	public void setOutput(StringBuilder output) {
		this.output = output;
	}

	public String getFolderPath() {
		return folderPath;
	}

	public void setFolderPath(String folderPath) {
		this.folderPath = folderPath;
	}

	public String getFullInputFileName() {
		if(isProgramOrMap!=null && isProgramOrMap.equalsIgnoreCase("MAP")){
			return folderPath+"/Map/"+fileName+".txt";
		}
		return folderPath+"/"+fileName+".txt";
	}
	
	public String getFullTokenizeFileName() {
		if(isProgramOrMap!=null && isProgramOrMap.equalsIgnoreCase("MAP")){
			return folderPath+"/Map/"+"output"+"/"+fileName+"_2_"+"Tokenize.lex";
		}
		return folderPath+"/output"+"/"+fileName+"_2_"+"Tokenize.lex";
	}
	
	public String getFullCommandFileName() {
		if(isProgramOrMap!=null && isProgramOrMap.equalsIgnoreCase("MAP")){
			return folderPath+"/Map/"+"output"+"/"+ fileName+"_3_"+"Command.txt";
		}
		return folderPath+"/output"+"/"+ fileName+"_3_"+"Command.txt";
	}
	
	public String getFullCommandFileNamewithEndings() {
		if(isProgramOrMap!=null && isProgramOrMap.equalsIgnoreCase("MAP")){
			return folderPath+"/Map/"+"output"+"/"+fileName+"_3_1_"+"CommandWithEndings.txt";
		}
		return folderPath+"/output"+"/"+fileName+"_3_1_"+"CommandWithEndings.txt";
	}

	public String getFullSourceTreeFileName() {
		if(isProgramOrMap!=null && isProgramOrMap.equalsIgnoreCase("MAP")){
			return folderPath+"/Map/"+"output"+"/"+fileName+"_4_"+"SourceLanguageTree.xml";
		}
		return folderPath+"/output"+"/"+fileName+"_4_"+"SourceLanguageTree.xml";
	}

	public String getFullJavaTreeFileName() {
		if(isProgramOrMap!=null && isProgramOrMap.equalsIgnoreCase("MAP")){
			return folderPath+"/Map/"+"output"+"/"+fileName+"_5_"+"JavaTree.xml";
		}
		return folderPath+"/output"+"/"+fileName+"_5_"+"JavaTree.xml";
	}
	
	public String getFullJSPTreeFileName() {
		if(isProgramOrMap!=null && isProgramOrMap.equalsIgnoreCase("MAP")){
			return folderPath+"/Map/"+"output"+"/"+fileName+"_5_"+"JSPTree.xml";
		}
		return folderPath+"/output"+"/"+fileName+"_5_"+"JSPTree.xml";
	}


	public String getFullJavaFileName() {
		if(isProgramOrMap!=null && isProgramOrMap.equalsIgnoreCase("MAP")){
			return folderPath+"/Map/"+"output"+"/"+fileName+".java";
		}
		return folderPath+"/output"+"/"+fileName+"Impl.java";
	}
	
	public String getFullJavaHibernateFileName(String viewName) {
		return folderPath+"/output"+"/"+"generatedhibernate"+"/"+viewName+"GenHibernateDAO.java";
	}
	
	public String getFullJavaDAOInterfaceFileName(String viewName) {
		return folderPath+"/output"+"/"+"generatedinterface"+"/"+viewName+"GenDAO.java";
	}
	
	public String getFullConversionErrorFileName() {
		if(isProgramOrMap!=null && isProgramOrMap.equalsIgnoreCase("MAP")){
			return folderPath+"/Map/"+"output"+"/"+"ConversionError"+".txt";
		}
		return folderPath+"/output"+"/"+"ConversionError"+".txt";
	}

	public String getFullLoadedDDMListFile() {
		if(isProgramOrMap!=null && isProgramOrMap.equalsIgnoreCase("MAP")){
			return folderPath+"/Map/"+"output"+"/"+"DDMList"+".txt";
		}
		return folderPath+"/output"+"/"+"DDMList"+".txt";
	}
	
	public String getFullDDMFolder() {
		
		//return folderPath+"/DDM";
		return folderMainPath+"/DDM";
		
	}
	

	
	public String getFullJSPFileName() {
		if(isProgramOrMap!=null && isProgramOrMap.equalsIgnoreCase("MAP")){
			return folderPath+"/Map/"+"output"+"/"+fileName+"_6_"+".jsp";
		}
		return folderPath+"/output"+"/"+fileName+"_6_"+".jsp";
	}
	
	public String getFullUndefinedCommandFileName() {
		if(isProgramOrMap!=null && isProgramOrMap.equalsIgnoreCase("MAP")){
			return folderPath+"/Map/"+"output"+"/"+ fileName+"_3_"+"Undefined_Command.txt";
		}
		return folderPath+"/output"+"/"+ fileName+"_3_"+"Undefined_Command.txt";
	}
	
	
	public String getFullNaturalInputFileName() {
		
		String fullNaturalFileName="";
		
		if(isProgramOrMap!=null && isProgramOrMap.equalsIgnoreCase("MAP")){
			fullNaturalFileName= folderPath+"/Map/"+fileName;
		}else{
			fullNaturalFileName= folderPath+"/"+fileName;
		}
		
		if(!fullNaturalFileName.contains("txt")){
			fullNaturalFileName=fullNaturalFileName+".txt";
		}
		
		return fullNaturalFileName;
	}
	
	
	public String getFullPathRuleTable() {
		return ConverterConfiguration.ruleTable();
	}

	

	@Override
	public String toString() {
		String result="CONVERSION;  PHASE:"+ this.phase+"   User:"+ this.user+"  FileName:";
		
		if(this.phase.equals(Phase.START)){
			result+=this.getFullInputFileName();
		}else if(this.phase.equals(Phase.TOKENIZE)){
			result+=this.getFullTokenizeFileName();
		}else if(this.phase.equals(Phase.PARSE_TO_COMMANDS)){
			result+=this.getFullCommandFileName();
		}else if(this.phase.equals(Phase.PARSE_TO_SOURCE_TREE)){
			result+=this.getFullSourceTreeFileName();
		}else if(this.phase.equals(Phase.CONVERT_COBOL_TREE_TO_JAVA_TREE)){
			result+=this.getFullJavaTreeFileName();
		}else if(this.phase.equals(Phase.WRITE_JAVA_CODE)){
			result+=this.getFullJavaFileName();
		}
		return result;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}



	public String getIsProgramOrMap() {
		return isProgramOrMap;
	}

	public void setIsProgramOrMap(String isProgramOrMap) {
		this.isProgramOrMap = isProgramOrMap;
	}

	public String getModuleOnly() {
		return module.replaceAll("/SeperatedPrograms", "");
	}

	public boolean isMap() {
		if (isProgramOrMap.equals("MAP")){
			return true;
		}
		return false;
	}
	
	public boolean isProgram() {
		if (isProgramOrMap.equals("PROGRAM")){
			return true;
		}
		return false;
	}

	public NaturalMode getMode() {
		return Mode;
	}

	public void setMode(NaturalMode mode) {
		Mode = mode;
	}

	public boolean isClientInteracting() {
		return isClientInteracting;
	}

	public void setClientInteracting(boolean isClientInteracting) {
		this.isClientInteracting = isClientInteracting;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getSchemaName() {
		return schemaName;
	}

	public void setSchemaName(String schemaName) {
		this.schemaName = schemaName;
	}

	public String getOPERATING_SYSTEM() {
		return OPERATING_SYSTEM;
	}

	public void setOPERATING_SYSTEM(String oPERATING_SYSTEM) {
		OPERATING_SYSTEM = oPERATING_SYSTEM;
	}

	public String getFolderMainPath() {
		return folderMainPath;
	}

	public void setFolderMainPath(String folderMainPath) {
		this.folderMainPath = folderMainPath;
	}

	public boolean isSubProgram() {
		return subProgram;
	}

	public void setSubProgram(boolean subProgram) {
		this.subProgram = subProgram;
	}

	public static ConversionMode getConversionMode() {
		return conversionMode;
	}

	public static void setConversionMode(ConversionMode conversionMode) {
		ConversionLogModel.conversionMode = conversionMode;
	}

	public String getFolderPathMap() {
		return folderPathMap;
	}

	public void setFolderPathMap(String folderPathMap) {
		this.folderPathMap = folderPathMap;
	}


	
}
