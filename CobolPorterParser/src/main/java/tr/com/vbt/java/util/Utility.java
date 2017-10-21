package tr.com.vbt.java.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.Levelable;
import tr.com.vbt.java.AbstractJavaElement;
import tr.com.vbt.java.general.JavaConstants;
import tr.com.vbt.java.utils.ConvertUtilities;
import tr.com.vbt.lexer.ConversionLogModel;
import tr.com.vbt.lexer.THYModules;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.ArrayToken;
import tr.com.vbt.token.TokenTipi;
import tr.com.vbt.util.ConverterConfiguration;

public class Utility {
	
	public static boolean DEBUG;

	final static Logger logger = LoggerFactory.getLogger(Utility.class);
	
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
					result.append("["+ConvertUtilities.castToInt()+firstDimensionSize+"-1]");
				}else {
					result.append("["+ConvertUtilities.castToInt()+firstDimension.getDeger()+"-1]");
				}
				if(secDimension!=null){
					if(firstDimension.getDeger() instanceof Integer){
						secDimensionSize=((int)secDimension.getDeger());
						result.append("["+ConvertUtilities.castToInt()+secDimensionSize+"-1]");
					}else {
						result.append("["+ConvertUtilities.castToInt()+secDimension.getDeger()+"-1]");
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
				
			try {
				columnName=condition.getColumnNameToken().getDeger().toString();
			} catch (Exception e2) {
				columnName=condition.getDeger().toString();
			}
			getterMethod =Utility.viewNameToPojoGetterName(columnName);
			
			Class c = null;

			c=findPojoClass(className);
			
		    try {
				method= c.getDeclaredMethod(getterMethod, null);
				return biggerPojoName+"."+method.getName()+"()";
			} catch (Exception e) {
				//MEthod yoksa Primary Key dir. O zaman burası koşar.
				//IDGIDBS-TESKI.MUSNO1 -->TESKI.getTeskiPK().getMusno1()
				//TESKI --> getTeskiPK
				try {
					primaryKeyGetterMethod=Utility.primaryKeyGetterMethod(tableName);
	
					return biggerPojoName+"."+primaryKeyGetterMethod.getName()+"()." +getterMethod+"()";
					
				} catch (Exception e1) {
				
					return "";
				}
				
	
			} 
		}

		
	public static String findViewAndColumnNamesReturnType(AbstractToken condition){
			
			String getterString, tableName, className, biggerPojoName, columnName = null, getterMethod;
			
			Method primaryKeyGetterMethod,method, pkClassGetterMethod = null;
			
			tableName= condition.getDeger().toString(); //TESKI;
			biggerPojoName=Utility.viewNameToBiggerPojoName(tableName);
			className=Utility.viewNameToPojoName(tableName);
				
			try {
				columnName=condition.getColumnNameToken().getDeger().toString();
			} catch (Exception e2) {
				columnName=condition.getDeger().toString();
			}
			getterMethod =Utility.viewNameToPojoGetterName(columnName);
			
		
			Class c = null, pkClass=null, returnClass;

			c=findPojoClass(className);
			
		    try {
				method= c.getDeclaredMethod(getterMethod, null);
				returnClass=method.getReturnType();
				return returnClass.getSimpleName().toString();
			} catch (Exception e) {
				//MEthod yoksa Primary Key dir. O zaman burası koşar.
				//IDGIDBS-TESKI.MUSNO1 -->TESKI.getId().getMusno1()
				//TESKI --> getTeskiPK
				try {
					primaryKeyGetterMethod=Utility.primaryKeyGetterMethod(tableName);
					
					pkClass=findPojosPkClass(className);
					
					pkClassGetterMethod= pkClass.getDeclaredMethod(getterMethod, null);
					
					returnClass=pkClassGetterMethod.getReturnType();
					
					return returnClass.getSimpleName().toString();
				
				} catch (Exception e1) {
					logger.debug("pkClass:"+className.toString()+" getterMethod:"+getterMethod);
					logger.debug(e.getMessage(),e);
					
					return "String";
				}
				
			}
				
		}
	
			//THesap --> class(ThesapPK)
		private static Class findPojosPkClass(String pojoClassName) {
			
			if(ConversionLogModel.getInstance().isMB()){
				
				String fullPKClassName,schemaName;
				
				Class pojoClass = null;
				
				fullPKClassName="tr.com."+ConversionLogModel.getInstance().getCustomer().toLowerCase()+".dal.pojo.idgidbs."+pojoClassName+"PK";
						
				try {
					pojoClass = Class.forName(fullPKClassName);
							
					return pojoClass;
				} catch (ClassNotFoundException e) {
					
						fullPKClassName="tr.com."+ConversionLogModel.getInstance().getCustomer().toLowerCase()+".dal.pojo.common."+pojoClassName+"PK";
							
						try {
							pojoClass = Class.forName(fullPKClassName);
							
							return pojoClass;
							
						} catch (ClassNotFoundException e1) {
							
							logger.debug(e1.getMessage(), e1);
							return null;
						}
				}
					
			}else{
			
				
				return null;
			}
	
	}

		public static Class findPojoClass(String className) {
			
			if(ConversionLogModel.getInstance().isMB()){
				
				String fullClassName,schemaName;
				
				Class pojoClass = null;
				
					for(int i=0; i<ConverterConfiguration.getSchemaList().size();i++){
						schemaName=ConverterConfiguration.getSchemaList().get(i);
						fullClassName="tr.com."+ConversionLogModel.getInstance().getCustomer().toLowerCase()+".dal.pojo."+schemaName.toLowerCase()+"."+className;
						
						try {
							pojoClass = Class.forName(fullClassName);
							
							return pojoClass;
						} catch (ClassNotFoundException e) {
							
							continue;
						}
					}
					
				return null;
				
			}else{
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
		}

		//TESKI --> getTeskiPK
		private static Method primaryKeyGetterMethod(String tableName) throws NoSuchMethodException, SecurityException {
			StringBuffer sb=new StringBuffer();
			
			String result;
			
			String pojoName=Utility.viewNameToPojoName(tableName);
			sb.append("getId");
			
			result=sb.toString();
			
			//Bu kısım kodun doğru uretildiğini valide etmek için.
			Class c = null;
	
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

		//IDGIDBS-TGECICI .KBIS --> TGECICI.getId().setKBis(
		public static String pojoSetterName(AbstractToken token) {
			
			StringBuilder setterString=new StringBuilder();
			
			setterString.append(token.getDeger().toString()+".");
			
			String columnName=token.getColumnNameToken().getDeger().toString();
			
			//partLevel1=FrameworkConvertUtilities.getFieldsObjectByGetterMethod(partLevel1, "id");
			
			setterString.append(Utility.viewNameToPojoSetterName(token.getColumnNameToken().getDeger().toString()));
			
			return null;
		}

		//IDGIDBS-TGECICI .KBIS --> TGECICI.getId().setKbis(
		
		//IDGIDBS-TGECICI .Xxx --> TGECICI..setXxx(
		public static String viewNameToPojoFullSetterName(AbstractToken token) {
			
			StringBuilder sb=new StringBuilder();
		
			String biggerclassName = Utility.viewNameToBiggerPojoName(token.getDeger().toString());
			
			String className = Utility.viewNameToPojoName(token.getDeger().toString());

			String fieldName = Utility.columnNameToPojoFieldName(token.getColumnNameToken().getDeger().toString());
			
			String setterName= Utility.viewNameToPojoSetterName(token.getColumnNameToken().getDeger().toString());

			Class c = null;

			c = Utility.findPojoClass(className);
			
			Field field;

			try {
				field = c.getDeclaredField(fieldName);
				field.setAccessible(true);
				
				sb.append(biggerclassName+"."+setterName);
			
				return sb.toString();

			} catch (Exception e) {
				
				try {
					Class cPK = null;
					
					cPK = Utility.findPojoClass(className+"PK");
					
					field = cPK.getDeclaredField(fieldName);
					
					field.setAccessible(true);
					
					sb.append(biggerclassName+".getId()."+setterName);
					
					return sb.toString();
		
				} catch (Exception e1) {
					return "UndefinedPojoFieldType";
				}
				
			}
		}


		
		
	
}
