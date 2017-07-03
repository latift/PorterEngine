package tr.com.vbt.java.util;

import java.lang.reflect.Method;
import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.Levelable;
import tr.com.vbt.java.AbstractJavaElement;
import tr.com.vbt.java.general.JavaConstants;
import tr.com.vbt.lexer.THYModules;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.ArrayToken;
import tr.com.vbt.token.TokenTipi;

public class Utility {
	
	public static boolean DEBUG;

		public AbstractJavaElement getChildWithName(AbstractJavaElement parent, String childName){
			/*
			for (AbstractJavaElement child : parent.getChildren()) {
				if(childName.equals("DataDivision") && child instanceof DataDivision)
				{
					return child;
				
				}
			}*/
			return null;
		}

		public static boolean controlDebug(List<AbstractToken> pattern, AbstractToken currentTokenForMatch) {
			try {
				//if(pattern.get(0).getDeger()!=null &&pattern.get(0).getDeger().toString().equals("PERFORM")&&pattern.get(1).getDeger()!=null &&pattern.get(1).getDeger().toString().equals("VARYING")){
				if(		pattern.get(0).getDeger()!=null 
						//&&pattern.get(0).getDeger().toString().equals("PERFORM")
						&&pattern.get(1).getDeger()!=null 
						&&pattern.get(1).getDeger().toString().equals("EXIT")
					){
					if(currentTokenForMatch.getDeger().equals("EXIT")){
						DEBUG=true;
						return DEBUG;
					}
				}
				DEBUG=false;
				return DEBUG;
			} catch (Exception e) {
				DEBUG=false;
				return DEBUG;
			}
		}
		
		//UHM_ACILIS1 --> UhmAcilis1
		public static String viewNameToPojoName(String viewName){
			StringBuffer pojoName=new StringBuffer();
			String convertedPart;
			String[] viewNameParts;
			
			if(viewName.contains("-")){
					viewNameParts=viewName.split("-");
			}else{
					viewNameParts=viewName.split("_");
			}
			
			for (String part : viewNameParts) {
				convertedPart=part.charAt(0)+part.substring(1).toLowerCase();
				pojoName.append(convertedPart);
			}
			
			return pojoName.toString();
		}
		
		
		//UHM_ACILIS1 --> UHMACILIS1
		public static String viewNameToBiggerPojoName(String viewName){
			StringBuffer pojoName=new StringBuffer();
			return viewName.replaceAll("_", "").toUpperCase();
			
		}
		
		//UHM_ACILIS1 --> setUhmAcilis1
		public static String viewNameToPojoSetterName(String viewName){
			StringBuffer pojoName=new StringBuffer();
			String convertedPart=new String();
			String[] viewNameParts;
			if(viewName.contains("-")){
			  viewNameParts=viewName.split("-");
			}else{
			  viewNameParts=viewName.split("_");
			}
			pojoName.append("set");
			for (String part : viewNameParts) {
				convertedPart=part.charAt(0)+part.substring(1).toLowerCase();
				pojoName.append(convertedPart);
			}
			
			return pojoName.toString();
		}
		
		//UHM_ACILIS1 --> getUhmAcilis1
		public static String viewNameToPojoGetterName(String viewName){
			StringBuffer pojoName=new StringBuffer();
			String convertedPart=new String();
			String[] viewNameParts;
			if(viewName.contains("-")){
			  viewNameParts=viewName.split("-");
			}else{
			  viewNameParts=viewName.split("_");
			}
			pojoName.append("get");
			for (String part : viewNameParts) {
				convertedPart=part.charAt(0)+part.substring(1).toLowerCase();
				pojoName.append(convertedPart);
			}
			
			return pojoName.toString();
		}

	
		//KRX_KOD --> krxKod
		public static String columnNameToPojoFieldName(String columnName){
			StringBuffer pojoFieldName=new StringBuffer();
			String convertedPart;
			String[] viewNameParts=columnName.split("-");
			if(viewNameParts.length==1){
				viewNameParts=columnName.split("_");
			}
			pojoFieldName.append(viewNameParts[0].toLowerCase());
			for (int i=1;i<viewNameParts.length;i++) {
				convertedPart=viewNameParts[i].substring(0, 1).toUpperCase()  +viewNameParts[i].substring(1).toLowerCase();
				pojoFieldName.append(convertedPart);
			}
			
			return pojoFieldName.toString();
		}

		// TPARAM.ANAHTAR -- TParam
		public static String columnNameToPojoFieldNameWithFirstLetterUpper(String columnName){
			StringBuffer pojoFieldName=new StringBuffer();
			String convertedPart;
			String[] viewNameParts=columnName.split("_");
			for (int i=0;i<viewNameParts.length;i++) {
				convertedPart=viewNameParts[i].substring(0, 1).toUpperCase()  +viewNameParts[i].substring(1).toLowerCase();
				pojoFieldName.append(convertedPart);
			}
			
			return pojoFieldName.toString();
		}
		public static AbstractCommand findParentCommand(Levelable child, List<AbstractCommand> commandList, int commandIndex) {
			int index;
			AbstractCommand curCommand;
			Levelable curCommandLevelable;
			while(true){
				curCommand=commandList.get(commandIndex);
				if(curCommand instanceof Levelable){
					curCommandLevelable=(Levelable) curCommand;
				
					if(curCommandLevelable.getLevelNumber()<child.getLevelNumber()){
						return curCommand;
					}
				}
				commandIndex--;
				if(commandIndex==0){
					return null;
				}
			}
		}

		public static int calculateOffset(AbstractCommand elementProgramDataTypeNatural, List<AbstractCommand> commandList, int commandIndex) {
			// TODO Auto-generated method stub
			return 0;
		}

		public static String replaceSpaceWithHtmlSpace(String lIMAN_ADI) {
			return lIMAN_ADI.replaceAll(" ", "-");
		}

		//THESAP --> THESAP.getHesNo()
		public static String pojoNameToPojoDotColumnName(AbstractToken pojoToken) {
			StringBuffer result=new StringBuffer();
			result.append(pojoToken.getDeger().toString().replace('-', '_')+".");
			result.append( Utility.viewNameToPojoGetterName(pojoToken.getColumnNameToken().getDeger().toString())+JavaConstants.OPEN_NORMAL_BRACKET +JavaConstants.CLOSE_NORMAL_BRACKET );
			return result.toString();
		}
		
		
		public static String recordNameToRecordDotRecordFieldName(AbstractToken recordToken) {
			StringBuffer result=new StringBuffer();
			
			ArrayToken arrayToken = null;
			
			AbstractToken firstDimension;
			
			int firstDimensionSize;
			
			AbstractToken secDimension;
			
			int secDimensionSize;
		
			result.append(recordToken.getDeger().toString().replace('-', '_'));  //MAP_DIZISI
			result.append(".");
			try {
				result.append(recordToken.getLinkedToken().getDeger().toString().replace('-', '_')); //D_SIRA
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(recordToken.getLinkedToken().getTip().equals(TokenTipi.Array)){
				arrayToken=(ArrayToken) recordToken.getLinkedToken();
				firstDimension=arrayToken.getFirstDimension();
				secDimension=arrayToken.getSecondDimension();
				if(firstDimension.getDeger() instanceof Integer){
					firstDimensionSize=((int)firstDimension.getDeger());
					result.append("["+firstDimensionSize+"-1]");
				}else {
					result.append("["+firstDimension.getDeger()+"-1]");
				}
				if(secDimension!=null){
					if(firstDimension.getDeger() instanceof Integer){
						secDimensionSize=((int)secDimension.getDeger());
						result.append("["+secDimensionSize+"-1]");
					}else {
						result.append("["+secDimension.getDeger()+"-1]");
					}
				}
			}else if(recordToken.getLinkedToken().getTip().equals(TokenTipi.Kelime)){
					//Do nothing
			}else{
				result.append(recordToken.getLinkedToken());
			}
			return result.toString();
		}

		//IDGIDBS-TESKI.MUSNO1 -->TESKI.getTeskiPK().getMusno1() 
		//IDGIDBS-TESKI.MUSNO1 -->TESKI.getTeskiPK().getMusno1()
		public static String viewAndColumnNameToPojoAndGetterMethodName(AbstractToken condition) throws NoSuchMethodException, SecurityException {
			
			String getterString, tableName, className, biggerPojoName, columnName, getterMethod;
			
			Method primaryKeyGetterMethod,method = null;
			
			tableName= condition.getDeger().toString(); //TESKI;
			biggerPojoName=Utility.viewNameToBiggerPojoName(tableName);
			className=Utility.viewNameToPojoName(tableName);
				
			columnName=condition.getColumnNameToken().getDeger().toString();
			getterMethod =Utility.viewNameToPojoGetterName(columnName);
			
		
			
			
			Class c = null;

			c=findPojoClass(className);
			
		    try {
				method= c.getDeclaredMethod(getterMethod, null);
				return biggerPojoName+"."+method.getName()+"()";
			} catch (NoSuchMethodException e) {
				//MEthod yoksa Primary Key dir. O zaman burası koşar.
				//IDGIDBS-TESKI.MUSNO1 -->TESKI.getTeskiPK().getMusno1()
				//TESKI --> getTeskiPK
				primaryKeyGetterMethod=Utility.primaryKeyGetterMethod(tableName);
				
				return biggerPojoName+"."+primaryKeyGetterMethod.getName()+"()." +getterMethod+"()";
				
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
			
		   return null;
		}

		public static Class findPojoClass(String className) {
			Class c = null;


			THYModules[] modules = THYModules.class.getEnumConstants();

			for(int i=0; i<modules.length;i++){
				
				try {
					  c = Class.forName("tr.com.thy.dal."+modules[i].toString()+".dal"+className);
				} catch (ClassNotFoundException e) {
				
				}
				if(c!=null){
					return c;
				}
			}
			return c;
		}

		//TESKI --> getTeskiPK
		private static Method primaryKeyGetterMethod(String tableName) throws NoSuchMethodException, SecurityException {
			StringBuffer sb=new StringBuffer();
			
			String result;
			
			String pojoName=Utility.viewNameToPojoName(tableName);
			sb.append("get");
			sb.append(pojoName);
			sb.append("PK");
			
			result=sb.toString();
			
			//Bu kısım kodun doğru uretildiğini valide etmek için.
			Class c = null;
			/*try {
				  c = Class.forName("tr.com.thy.tps.dal.pojo."+pojoName);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			c=findPojoClass(pojoName);
			
			Method method= c.getDeclaredMethod(result, null);
			//Bu kısım kodun doğru uretildiğini valide etmek için.
			
			return method;
		}

		public static String typeOfVariableByVariableValue(AbstractToken filterValue) {
			String result;
			if(filterValue.isPojoVariable()){
				
				result= (((Object)filterValue.getColumnNameToken().getDeger()).getClass().getSimpleName());
				
			}else if(filterValue.isRecordVariable()){
				
				result= (((Object)filterValue.getLinkedToken().getDeger()).getClass().getSimpleName());
				
			}else if(filterValue.isConstantVariableWithQuota()){
				
				result= "String";
				
			}else{
			
				result= (((Object)filterValue.getDeger()).getClass().getSimpleName());
			}
			if(!result.equals("String")){
				return result.substring(0, 1).toLowerCase()+result.substring(1);
			}
			return result;
		}


		
		
	
}
