package tr.com.vbt.java.database;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import tr.com.vbt.java.general.JavaConstants;
import tr.com.vbt.java.utils.FCU;
import tr.com.vbt.lexer.ConversionLogModel;
import tr.com.vbt.util.WriteToFile;

public class SQLManager {
	
	public static boolean  isDateWritten=false;
	private static Map<String, SQLStringObject> sqlStringMap=new HashMap<String, SQLStringObject>();

	public static void registerSQL(String sqlStringKey, SQLStringObject sqlString) {
		sqlStringMap.put(sqlStringKey, sqlString);
		
	}
	
	public static StringBuffer allSqlStringsOfMenu(){
		
		StringBuffer sb=new StringBuffer();
		SQLStringObject currentObject;
		for (Map.Entry<String, SQLStringObject> entry : sqlStringMap.entrySet())
		{
			currentObject=entry.getValue();
			//public String IDGB0101_172="Select * from abc where";
			sb.append("public static final String "+currentObject.getProgram()+"_"+currentObject.getSatirNo()+" = "+currentObject.getSqlString()+JavaConstants.DOT_WITH_COMMA+JavaConstants.NEW_LINE);
		}
		return sb;
	}
	
	public static void writeSQLStringToFile(){
		writeDate();
		try {
			WriteToFile.appendToFile(JavaConstants.NEW_LINE, ConversionLogModel.getInstance().getFullModuleSQLFile());
			WriteToFile.appendToFile(JavaConstants.NEW_LINE, ConversionLogModel.getInstance().getFullModuleSQLFile());
			WriteToFile.appendToFile(JavaConstants.NEW_LINE, ConversionLogModel.getInstance().getFullModuleSQLFile());
			WriteToFile.appendToFile("//"+ConversionLogModel.getInstance().getModule(), ConversionLogModel.getInstance().getFullModuleSQLFile());
			WriteToFile.appendToFile(JavaConstants.NEW_LINE, ConversionLogModel.getInstance().getFullModuleSQLFile());
			WriteToFile.appendToFile(allSqlStringsOfMenu().toString(), ConversionLogModel.getInstance().getFullModuleSQLFile());
			
			sqlStringMap=new HashMap<String, SQLStringObject>();
	
		} catch (IOException e) {
			
		}
	}

	private static void writeDate() {
		try {
			if(!isDateWritten){
				isDateWritten=true;
				WriteToFile.appendToFile("* Generation Date: "+FCU.getCurrentDateAndTime()+JavaConstants.NEW_LINE,ConversionLogModel.getInstance().getFullModuleSQLFile());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
