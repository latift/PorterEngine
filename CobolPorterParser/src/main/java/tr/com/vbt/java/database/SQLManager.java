package tr.com.vbt.java.database;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import tr.com.vbt.java.general.JavaConstants;
import tr.com.vbt.lexer.ConversionLogModel;
import tr.com.vbt.util.WriteToFile;

public class SQLManager {
	
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
		try {
			WriteToFile.appendToFile("//"+ConversionLogModel.getInstance().getModule(), ConversionLogModel.getInstance().getFullModuleSQLFile());
			WriteToFile.appendToFile(JavaConstants.NEW_LINE, ConversionLogModel.getInstance().getFullModuleSQLFile());
			WriteToFile.appendToFile(allSqlStringsOfMenu().toString(), ConversionLogModel.getInstance().getFullModuleSQLFile());
	
		} catch (IOException e) {
			
		}
	}

}
