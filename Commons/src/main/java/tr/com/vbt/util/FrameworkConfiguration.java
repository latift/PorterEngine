package tr.com.vbt.util;

import java.util.HashSet;
import java.util.Set;

public class FrameworkConfiguration {

	public static final boolean RESERVERDNUMBERS=true;
	
	private static String folderPath;
	
	
	
	
	
	
	public static String naturalLinuxRuleTableFolderPath="/root/chopper/NaturalFiles/NaturalToJavaRule.xlsx";
	
	public static String naturalWindowsRuleTableFolderPath="C:/CobolPorterWorkspace2/CobolPorter/CobolPorter/NaturalFiles/NaturalToJavaRule.xlsx";
	
	public static boolean convertComments=false;
	
	public static String className="TemporaryClassName";
	
	public static boolean YAZICI_TEST=false;
	
	
	//public static String OPERATING_SYSTEM="LINUX"; //"COBOL"
	public static String OPERATING_SYSTEM="LINUX";
	
	public static final String sourceLan="NATURAL"; //"COBOL"
	
	public static final String destLan="JAVA";
	
	public static final int NATURAL_X_LENGTH = 1;
	public static final int NATURAL_T_LENGTH = 1;
	public static final int NATURAL_LINE_HEIGHT = 1;
	public static final int NATURAL_T_LENGTH_FOR_PRINT = 1;
	public static final int NATURAL_LINE_HEIGHT_FOR_PRINT = 1;

	public static final String ENGINE_VERSION = "0.15";

	public static final int NAT_SCREEN_COLUMN_NUMBER = 90*3;

	public static final int NAT_SCREEN_ROW_NUMBER = 23;
	
	public static final int NAT_SCREEN_ROW_NUMBER_WITH_FOOTER = 26;

	public static final int DEFAULT_MAX_LENGTH_FOR_INPUT = 5;

	public static final int DEFAULT_ARRAY_LENGTH = 40;

	public static final int DEFAULT_MAX_LENGTH_FOR_LABEL = 20;

	public static final boolean STOP_ENGINE_ON_PARSE_ERROR = true;
	
	public static final boolean STOP_ENGINE_ON_TREE_CREATE_ERROR=true;

	public static final boolean STOP_ENGINE_ON_CONVERSION_ERROR = true;

	public static final boolean SHOW_RULER = false;

	public static final String DEFAULT_SCHEMA = "PERS";
	
	private static Set<String> schemas=new HashSet<String>();

	public static boolean addGenerationInfoToJavaClass=true;
	
	public static void loadConfiguration() {
		schemas.add("DEPDEMO");
		schemas.add("IDGIDBS");
		schemas.add("IKGPBS2");
		schemas.add("MUHMBST");
		schemas.add("MUHSAOS");
		schemas.add("MUHSWFT");
		schemas.add("MFEXIT");
		schemas.add("TPS");
		schemas.add("PER");
		schemas.add("IKR");
		schemas.add("UCR");
	}


	public static String ruleTable() {
		if(OPERATING_SYSTEM.equalsIgnoreCase("LINUX")&&sourceLan.equalsIgnoreCase("NATURAL")){
			return naturalLinuxRuleTableFolderPath;
		}else if(OPERATING_SYSTEM.equalsIgnoreCase("WINDOWS")&&sourceLan.equalsIgnoreCase("NATURAL")){
			return naturalWindowsRuleTableFolderPath;	
		}
		return "";
	}
	
	public static boolean isSchemaName(String schemaName){
		if(schemas.isEmpty()){
			loadConfiguration();
		}
		if(schemas.contains(schemaName)){
			return true;
		}
		return false;
	}
	
}
