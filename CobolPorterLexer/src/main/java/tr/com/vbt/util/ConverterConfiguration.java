package tr.com.vbt.util;

import java.util.HashSet;
import java.util.Set;

import tr.com.vbt.lexer.ConversionLogModel;

public class ConverterConfiguration {

	public static final boolean RESERVERDNUMBERS=true;
	
	private static String folderPath;
	
	public static String customer="MB";
	
	public static ConversionLogModel logModel=ConversionLogModel.getInstance();
	
	public static boolean pojosAreDefinedInCode=true; //THY için true olmalı.
	
	public static String cobolWindowsFolderPath="C:/CobolPorterWorkspace2/CobolPorter/CobolPorter/CobolFiles/Samples/";
	
	public static String cobolLinuxFolderPath="/root/chopper/CobolFiles/";
	
	private static String naturalWindowsFolderPath="C:/CobolPorterWorkspace2/CobolPorter/CobolPorter/THY/"+logModel.getModule()+"/SeperatedPrograms";
	
	private static String naturalWindowsFolderPathMB="C:/CobolPorterWorkspace2/CobolPorter/CobolPorter/MB/"+logModel.getModule()+"/SeperatedPrograms";
	
	public static String naturalLinuxFolderPathUnix="/root/chopper/THY/"+logModel.getModule()+"/SeperatedPrograms";
	
	public static String naturalLinuxFolderPathUnixMB="/root/chopper/MB/"+logModel.getModule()+"/SeperatedPrograms";
	

	
	private static String naturalWindowsMainFolderPathTHY="C:/CobolPorterWorkspace2/CobolPorter/CobolPorter/THY";
	
	private static String naturalWindowsMainFolderPathMB="C:/CobolPorterWorkspace2/CobolPorter/CobolPorter/MB";
	
	public static String naturalLinuxMainFolderPathUnixTHY="/root/chopper/THY";
	
	public static String naturalLinuxMainFolderPathUnixMB="/root/chopper/MB";
	
	
	
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

	public static final String DEFAULT_SCHEMA = ConversionLogModel.getInstance().getSchemaName();
	
	public static final boolean STOP_ENGINE_ON_PARSE_ERROR = true;
	
	public static final boolean STOP_ENGINE_ON_TREE_CREATE_ERROR=true;

	public static final boolean STOP_ENGINE_ON_CONVERSION_ERROR = true;

	public static final boolean SHOW_RULER = false;
	
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

	public static String getSubFolderPath() {
		return getFolderPath()+"/Subprogram";
	}
	public static String getFolderPath() {
		if(customer.equals("MB")){
			if(OPERATING_SYSTEM.equalsIgnoreCase("LINUX")&&sourceLan.equalsIgnoreCase("NATURAL")){
				return naturalLinuxFolderPathUnixMB;
			}else if(OPERATING_SYSTEM.equalsIgnoreCase("WINDOWS")&&sourceLan.equalsIgnoreCase("NATURAL")){
				return naturalWindowsFolderPathMB;	
			}else if(OPERATING_SYSTEM.equalsIgnoreCase("LINUX")&&sourceLan.equalsIgnoreCase("COBOL")){
				return cobolLinuxFolderPath;
			}else if(OPERATING_SYSTEM.equalsIgnoreCase("WINDOWS")&&sourceLan.equalsIgnoreCase("COBOL")){
				return cobolWindowsFolderPath;
			}
		}else if(customer.equals("THY")){
			if(OPERATING_SYSTEM.equalsIgnoreCase("LINUX")&&sourceLan.equalsIgnoreCase("NATURAL")){
				return naturalLinuxFolderPathUnix;
			}else if(OPERATING_SYSTEM.equalsIgnoreCase("WINDOWS")&&sourceLan.equalsIgnoreCase("NATURAL")){
				return naturalWindowsFolderPath;	
			}else if(OPERATING_SYSTEM.equalsIgnoreCase("LINUX")&&sourceLan.equalsIgnoreCase("COBOL")){
				return cobolLinuxFolderPath;
			}else if(OPERATING_SYSTEM.equalsIgnoreCase("WINDOWS")&&sourceLan.equalsIgnoreCase("COBOL")){
				return cobolWindowsFolderPath;
			}
		}
		return "";
	}
	
	public static String getFolderPathMap() {
		return getFolderPath()+"/Map";
	}
	public static String getMainFolderPath() {
		if(customer.equals("MB")){
			if(OPERATING_SYSTEM.equalsIgnoreCase("LINUX")&&sourceLan.equalsIgnoreCase("NATURAL")){
				return naturalLinuxMainFolderPathUnixMB;
			}else if(OPERATING_SYSTEM.equalsIgnoreCase("WINDOWS")&&sourceLan.equalsIgnoreCase("NATURAL")){
				return naturalWindowsMainFolderPathMB;	
			}
		}else if(customer.equals("THY")){
			if(OPERATING_SYSTEM.equalsIgnoreCase("LINUX")&&sourceLan.equalsIgnoreCase("NATURAL")){
				return naturalLinuxMainFolderPathUnixTHY;
			}else if(OPERATING_SYSTEM.equalsIgnoreCase("WINDOWS")&&sourceLan.equalsIgnoreCase("NATURAL")){
				return naturalWindowsMainFolderPathTHY;	
			}
		}
		return "";
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
