package tr.com.vbt.java.utils;

import java.lang.reflect.Field;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.ddm.DDM;
import tr.com.vbt.ddm.DDMList;
import tr.com.vbt.exceptions.ConversionException;
import tr.com.vbt.java.AbstractJavaElement;
import tr.com.vbt.java.general.JavaClassGeneral;
import tr.com.vbt.java.general.JavaConstants;
import tr.com.vbt.java.general.JavaGeneralVariableElement;
import tr.com.vbt.java.general.JavaNaturalClassElement;
import tr.com.vbt.java.util.Utility;
import tr.com.vbt.lexer.ConversionLogModel;
import tr.com.vbt.lexer.ConversionLogReport;
import tr.com.vbt.natural.parser.datalayout.db.ElementDBDataTypeNatural;
import tr.com.vbt.natural.parser.datalayout.program.ElementProgramDataTypeNatural;
import tr.com.vbt.natural.parser.datalayout.program.ElementProgramGrupNatural;
import tr.com.vbt.natural.parser.datalayout.program.ElementProgramOneDimensionArrayNatural;
import tr.com.vbt.patern.NaturalCommandList;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.TokenTipi;
import tr.com.vbt.util.ConverterConfiguration;
import tr.com.vbt.util.WriteToFile;

public class ConvertUtilities {

	final static Logger logger = LoggerFactory.getLogger(ConvertUtilities.class);
	public static String yyyy_MM_dd = "yyyy_MM_dd";

	public static long MILISECOND_TO_DAY = 1000 * 60 * 60 * 24;

	// #DATX (D) bu duruma dikkat.
	public static int getMaxSize(String variableName) {
		JavaClassGeneral javaTreeElement = (JavaClassGeneral) JavaClassGeneral.getInstance(); // Oluşan
																								// java
																								// sınıfını
																								// dön.
		JavaNaturalClassElement classElement = (JavaNaturalClassElement) javaTreeElement
				.getChildWithName("JavaNaturalClassElement");
		List<JavaGeneralVariableElement> elementsWithSameName = classElement.getGeneralVariables();

		for (JavaGeneralVariableElement generalVariable : elementsWithSameName) {
			if (generalVariable != null) {
				if (generalVariable.getDataName().equals(variableName)) {
					return generalVariable.getLength() + generalVariable.getLengthAfterDot();
				}
			}
		}
		// #DATX (D)
		return 20; // Kayıt bulamazsan sinirlamamak için 20 giriyoruz.
	}
	
	//PERG001.SICIL --> SICIL
	public static String onlyFieldOfIncludedVariable(String inclVariable){
		
		String result=inclVariable;
		
		if(inclVariable.contains(".")){
			int indexOfDot=inclVariable.indexOf(".");
			result=inclVariable.substring(indexOfDot+1);
		}
		return result;
	}

	public static int getMinSize(String label) {
		return 0;
	}

	// #ROL1(*):=#ROL1-SAYFA(#SAYFA,*)
	// copyArrayToArray(ROL1-SAYFA,SAYFA,*, ROL1, *);
	public static void copyArrayToArray(Object anp, String sourceArrayName, String sourceArrayParameter1,
			String sourceArrayParameter2, String destArrayName, String destArrayParameter)
			throws NoSuchFieldException, SecurityException {

		Field field = anp.getClass().getDeclaredField("sourceArrayName");
		field.setAccessible(true);
		// List<> field=

	}
	/*
	 * public static void main(String[] args){ JavaBecomesEqualToArrayElement
	 * anp=new JavaBecomesEqualToArrayElement();
	 * ConvertUtilities.copyArrayToArray(anp, sourceArrayName,
	 * sourceArrayParameter1, sourceArrayParameter2, destArrayName,
	 * destArrayParameter);
	 * 
	 * }
	 */

	// TODO: İmplement below code
	public static VariableTypes getVariableType(AbstractToken variable) {

		if(variable.isSayi()){
			return VariableTypes.LONG_TYPE;
		}
		ElementProgramDataTypeNatural programData;
		ElementProgramGrupNatural programGrupData;
		List<AbstractCommand> commandList = NaturalCommandList.getInstance().getCommandListWithIncludedVariables();
		for (AbstractCommand abstractCommand : commandList) {
			if (abstractCommand instanceof ElementProgramDataTypeNatural) {
				programData = (ElementProgramDataTypeNatural) abstractCommand;
				if (programData.getDataName().equals(variable.getDeger())) {
					if (programData.getDataType().substring(0, 1).equals("A")) {
						return VariableTypes.STRING_TYPE;
					} else if (programData.getDataType().substring(0, 1).equals("N")) {
						if (programData.getLengthAfterDot() == 0) {
							return VariableTypes.LONG_TYPE;
						} else {
							return VariableTypes.FLOAT_TYPE;
						}
					} else if (programData.getDataType().substring(0, 1).equals("D")) {
						return VariableTypes.DATE_TYPE;
					}
				}
			} else if (abstractCommand instanceof ElementProgramGrupNatural) {
				programGrupData = (ElementProgramGrupNatural) abstractCommand;
				if (programGrupData.getDataName().equals(variable.getDeger())) {
					return VariableTypes.GRUP_TYPE;
				}
			}
		}
		return VariableTypes.UNDEFINED_TYPE;
	}

	// TODO: İmplement below code
	public static String getVariableTypeOfString(AbstractToken variable) {

		if (variable.getTip().equals(TokenTipi.Sayi)) {
			Double d = (Double) variable.getDeger();
			if (d % 1 != 0) {
				return "float";
			} else {
				return "int";
			}

		}
		
		String variableDeger=variable.getDeger().toString();
		
		if(variable.getDeger().toString().contains(".")){
			int pointIndex=variable.getDeger().toString().indexOf(".");
			variableDeger=variable.getDeger().toString().substring(pointIndex+1);
		}
		
		ElementProgramDataTypeNatural programData;
		ElementProgramGrupNatural grupData;
		ElementProgramOneDimensionArrayNatural arrayItem;
		ElementDBDataTypeNatural dbDataType;
		DDM ddm ;
		List<AbstractCommand> commandList = NaturalCommandList.getInstance().getCommandListWithIncludedVariables();
		for (AbstractCommand abstractCommand : commandList) {
			logger.debug(abstractCommand.toString());
			if (abstractCommand instanceof ElementProgramDataTypeNatural) {
				programData = (ElementProgramDataTypeNatural) abstractCommand;
				if (programData.getDataName().equals(variableDeger)) {

					return ConvertUtilities.getJavaVariableType(programData.getDataType(),
							programData.getCommandMatchPoint(), programData.getLengthAfterDot());
				}
			} else if (abstractCommand instanceof ElementProgramGrupNatural) {
				grupData = (ElementProgramGrupNatural) abstractCommand;
				if (grupData.getDataName().equals(variableDeger)) {
					return VariableTypes.GRUP_TYPE.toString();
				}
			}
			else if (abstractCommand instanceof ElementProgramOneDimensionArrayNatural) {
				arrayItem = (ElementProgramOneDimensionArrayNatural) abstractCommand;
				if (arrayItem.getDataName().equals(variableDeger)) {
					return ConvertUtilities.getJavaVariableType(arrayItem.getDataType(),
							arrayItem.getCommandMatchPoint(), arrayItem.getLengthAfterDot());
				}
			}
			
			 else if (abstractCommand instanceof ElementDBDataTypeNatural) {
					dbDataType = (ElementDBDataTypeNatural) abstractCommand;
					if (dbDataType.getDataName().equals(variableDeger)) {
						ddm = DDMList.getInstance().getDDMByKey(dbDataType.getDataName(),abstractCommand);
						if(ddm==null|| ddm.getF()==null){
							return "String";
						}else{
							return ConvertUtilities.getJavaVariableType(ddm.getF(),0,0);
						}
					}
				} 
			
		}
		return VariableTypes.UNDEFINED_TYPE.toString();
	}

	public static AbstractCommand getVariableDefinitinCommand(AbstractToken variable) {

		List<AbstractCommand> commandList = NaturalCommandList.getInstance().getCommandList();

		ElementProgramDataTypeNatural programData;

		ElementProgramGrupNatural grupNatural;

		for (AbstractCommand abstractCommand : commandList) {
			if (abstractCommand instanceof ElementProgramDataTypeNatural) {
				programData = (ElementProgramDataTypeNatural) abstractCommand;
				if (programData.getDataName().equals(variable.getDeger())) {
					return programData;
				}
			} else if (abstractCommand instanceof ElementProgramGrupNatural) {
				grupNatural = (ElementProgramGrupNatural) abstractCommand;
				if (grupNatural.getGrupName().equals(variable.getDeger())) {
					return grupNatural;
				}
			}
		}
		return null;
	}

	public static String getJavaVariableType(String dataType, int length, int lengthAfterDot) {
		String type;
		if (dataType.equals("A") || dataType.equals("String")) {
			type = "String";
		} else if ((dataType.equals("N") && lengthAfterDot == 0) || dataType.equals("int")) {
			type = "int";
		} else if ((dataType.equals("N") && lengthAfterDot != 0) || dataType.equals("float")){
			type = "float";
		} else if (dataType.equals("I")) {
			type = "int";
		} else if (dataType.equals("D")|| dataType.equals("Date")) {
			type = "Date";
		} else if (dataType.equals("T")|| dataType.equals("Date")) {
			type = "Date";
		} else if (dataType.equals("L")|| dataType.equals("boolean")) {
			type = "boolean";
		} else if (dataType.equals("C")|| dataType.equals("ControlEnum")) {
			type = "ControlEnum";
		} else if (dataType.equals("P")) {
			type = "int";
		} else {
			type = "UndefinedDataType";
		}
		return type;
	}

	/*
	 * public static String getDataTypeFor(String dataType) { if
	 * (dataType.equalsIgnoreCase("N")){ return "float"; }else if
	 * (dataType.equalsIgnoreCase("I")){ return "int"; }else if
	 * (dataType.equalsIgnoreCase("A")){ return "String"; }else if
	 * (dataType.equalsIgnoreCase("D")){ return "Date"; }else if
	 * (dataType.equalsIgnoreCase("T")){ return "Date"; }else if
	 * (dataType.equalsIgnoreCase("L")){ return "boolean"; }else if
	 * (dataType.equalsIgnoreCase("C")){ return "boolean"; }else if
	 * (dataType.equalsIgnoreCase("P")){ return "boolean"; } return
	 * "undefinedType"+dataType; }
	 */

	public static String getTypeOfVariable(AbstractToken variable) {

		/**
		 * if(systemVariable.getDeger().equals("PF-KEY")) return true; else
		 * if(systemVariable.getDeger().toString().startsWith("DAT")) return
		 * true; else if(systemVariable.getDeger().equals("DAT4E")) return true;
		 * else if(systemVariable.getDeger().equals("TIMX")) return true; else
		 * if(systemVariable.getDeger().equals("TIME")) return true; else
		 * if(systemVariable.getDeger().equals("USER")) return true; else
		 * if(systemVariable.getDeger().equals("PROGRAM")) return true; else
		 * if(systemVariable.getDeger().equals("DEVICE")) return true; else
		 * if(systemVariable.getDeger().equals("LANGUAGE")) return true;
		 * 
		 * else if(systemVariable.getDeger().equals("PAGE_NUMBER")) return true;
		 * else if(systemVariable.getDeger().equals("PAGE-NUMBER")) return true;
		 * else if(systemVariable.getDeger().equals("ISN")) return true; else
		 * if(systemVariable.getDeger().equals("COUNTER"))
		 */

		if (variable.isSystemVariable()) {
			if (variable.getDeger().equals("PF-KEY") || variable.getDeger().equals("USER")
					|| variable.getDeger().equals("PROGRAM") || variable.getDeger().equals("DEVICE")
					|| variable.getDeger().equals("LANGUAGE") || variable.getDeger().toString().startsWith("DAT")
					|| variable.getDeger().equals("PF-KEY")) {
				return "String";
			} else {
				return "int";
			}
		}
		AbstractCommand commmand;
		ElementProgramOneDimensionArrayNatural oneDimensionArrayChildDefinition;
		ElementProgramDataTypeNatural programDataTypeDefinition;
		commmand = ConvertUtilities.getVariableDefinitinCommand(variable);

		if (commmand instanceof ElementProgramOneDimensionArrayNatural) {
			oneDimensionArrayChildDefinition = (ElementProgramOneDimensionArrayNatural) commmand;
			return ConvertUtilities.getJavaVariableType(oneDimensionArrayChildDefinition.getDataType(),
					oneDimensionArrayChildDefinition.getLength(), oneDimensionArrayChildDefinition.getLengthAfterDot());
		} else if (commmand instanceof ElementProgramDataTypeNatural) {
			programDataTypeDefinition = (ElementProgramDataTypeNatural) commmand;
			return ConvertUtilities.getJavaVariableType(programDataTypeDefinition.getDataType(),
					programDataTypeDefinition.getLength(), programDataTypeDefinition.getLengthAfterDot());
		}
		return null;

	}

	// STORE IDGIDBS-TGECICI --> TGECICIDAO.save(TGECICI);
	// tablName=TGECICI --> DAO=TGECICIDAO
	public static String getDAOOfTable(AbstractToken tableName) {
		return tableName.getDeger() + "DAO";
	}

	// STORE IDGIDBS-TGECICI --> TGECICI_DAO.save(TGECICI);
	// tablName=TGECICI --> DAO=TGECICI_DAO
	public static String getDAOOfTableWithUnderscore(AbstractToken tableName) {
		return tableName.getDeger().toString().replaceAll("-", "_") + "_DAO";
	}

	

	public static java.sql.Time stringToSqlTime(String param) {

		try {
			return new java.sql.Time(new SimpleDateFormat("HH:mm:ss").parse(param).getTime());
		} catch (ParseException e) {
		}

		try {
			return new java.sql.Time(new SimpleDateFormat("HH:mm").parse(param).getTime());
		} catch (ParseException e) {
		}
		return null;
	}

	public static String getCurrentDay() {
		LocalDate localDate = new LocalDate();
		// localDate : 2016-06-28
		return localDate.toString().substring(8);
	}

	public static String getCurrentMonth() {
		LocalDate localDate = new LocalDate();
		// localDate : 2016-06-28
		return localDate.toString().substring(5, 7);
	}

	public static String getCurrentYear() {
		LocalDate localDate = new LocalDate();
		// localDate : 2016-06-28
		return localDate.toString().substring(0, 4);
	}

	public static String getCurrentHourAndMinute() {
		LocalTime localTime = new LocalTime();
		// localDate : 2016-06-28
		return localTime.toString();
	}

	public static String getCurrentCountry() {
		return "TR";
	}

	public static String getCurrentDate() {
		// (GG.AA.YYYY)
		return getCurrentDay() + "." + getCurrentMonth() + "." + getCurrentYear();
	}

	public static String getCurrentDateAndTime() {
		// (GG.AA.YYYY)
		return getCurrentDay() + "." + getCurrentMonth() + "." + getCurrentYear() + " " + getCurrentHourAndMinute();
	}

	public static String getPojosFieldType(AbstractToken currentToken) {
		// THESAP -->Thesap
		String className = Utility.viewNameToPojoName(currentToken.getDeger().toString());
	
		String fieldName = Utility.columnNameToPojoFieldName(currentToken.getColumnNameToken().getDeger().toString());
		
		Class c = null;
		
		c=Utility.findPojoClass(className);

		try {
			Field field = c.getDeclaredField(fieldName);
			field.setAccessible(true);
			/*
			 * if (field.getType().isAssignableFrom(String.class)) { return
			 * "String"; }else if
			 * (field.getType().isAssignableFrom(Timestamp.class)) { return
			 * "Timestamp"; }else if
			 * (field.getType().isAssignableFrom(Integer.class)) { return
			 * "Integer"; }else if
			 * (field.getType().isAssignableFrom(Float.class)) { return "Float";
			 * }else if (field.getType().isAssignableFrom(Double.class)) {
			 * return "Double"; }else if
			 * (field.getType().isAssignableFrom(Long.class)) { return "Long"; }
			 */
			return field.getType().getSimpleName();

		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
		return "";

	}

	public static StringBuffer writeInterfaceHeader(String pojoName) {
		StringBuffer interfaceHeader = new StringBuffer();

		ConversionLogModel logModel = ConversionLogModel.getInstance();

		if (ConverterConfiguration.addGenerationInfoToJavaClass) {
			logModel = ConversionLogModel.getInstance();

			interfaceHeader.append("/**" + JavaConstants.NEW_LINE);
			interfaceHeader.append("* " + JavaConstants.CLASS_GENERATION_INFO + JavaConstants.NEW_LINE);
			interfaceHeader.append("* Engine Version: " + ConverterConfiguration.ENGINE_VERSION + JavaConstants.NEW_LINE);
			interfaceHeader.append("* Generation Date: " + ConvertUtilities.getCurrentDate() + JavaConstants.NEW_LINE);
			interfaceHeader.append("* Conversion User: " + logModel.getUser() + JavaConstants.NEW_LINE);
			interfaceHeader.append("* OS: " + logModel.getOPERATING_SYSTEM() + JavaConstants.NEW_LINE);
			interfaceHeader.append("* Customer: " + logModel.getCustomer() + JavaConstants.NEW_LINE);
			interfaceHeader.append("*/" + JavaConstants.NEW_LINE);
			interfaceHeader.append(JavaConstants.NEW_LINE);
		}

		interfaceHeader.append(JavaConstants.PACKAGE + " " + "tr.com.thy.dal.generated" + " "
				+ JavaConstants.DOT_WITH_COMMA + JavaConstants.NEW_LINE);

		interfaceHeader.append("import tr.com.thy.dal.*;" + JavaConstants.NEW_LINE);
		interfaceHeader.append("import tr.com.thy.dal.pojo.*;" + JavaConstants.NEW_LINE);
		interfaceHeader.append("import java.util.*;" + JavaConstants.NEW_LINE);

		interfaceHeader.append(JavaConstants.NEW_LINE);
		// fileName =fileName
		// public interface TafaizDAOGenerated extends TafaizDAO {
		// public interface TbesyilGenDAO extends GenericDAO<Tbesyil,TbesyilPK>{
		interfaceHeader.append("public interface " + pojoName + "GenDAO extends GenericDAO<" + pojoName + ", Long>" + JavaConstants.OPEN_BRACKET + JavaConstants.NEW_LINE);
		return interfaceHeader;
	}

	public static StringBuffer writeDAOImplemantasyonClassHeader(String pojoName) {
		StringBuffer implClassHeader = new StringBuffer();

		ConversionLogModel logModel = ConversionLogModel.getInstance();

		if (ConverterConfiguration.addGenerationInfoToJavaClass) {
			logModel = ConversionLogModel.getInstance();

			implClassHeader.append("/**" + JavaConstants.NEW_LINE);
			implClassHeader.append("* " + JavaConstants.CLASS_GENERATION_INFO + JavaConstants.NEW_LINE);
			implClassHeader.append("* Engine Version: " + ConverterConfiguration.ENGINE_VERSION + JavaConstants.NEW_LINE);
			implClassHeader.append("* Generation Date: " + ConvertUtilities.getCurrentDate() + JavaConstants.NEW_LINE);
			implClassHeader.append("* Conversion User: " + logModel.getUser() + JavaConstants.NEW_LINE);
			implClassHeader.append("* OS: " + logModel.getOPERATING_SYSTEM() + JavaConstants.NEW_LINE);
			implClassHeader.append("* Customer: " + logModel.getCustomer() + JavaConstants.NEW_LINE);
			implClassHeader.append("*/" + JavaConstants.NEW_LINE);
			implClassHeader.append(JavaConstants.NEW_LINE);
		}

		// package tr.com.mb.dal.hibernate.generated;
		implClassHeader.append(JavaConstants.PACKAGE + " " + "tr.com.thy.dal.hibernate.generated"
				+ JavaConstants.DOT_WITH_COMMA + JavaConstants.NEW_LINE);

		implClassHeader.append("import org.hibernate.Criteria;" + JavaConstants.NEW_LINE);
		implClassHeader.append("import org.hibernate.criterion.Restrictions;" + JavaConstants.NEW_LINE);
		implClassHeader.append("import org.springframework.stereotype.*;" + JavaConstants.NEW_LINE);
		implClassHeader.append("import tr.com.thy.dal.generated.*;" + JavaConstants.NEW_LINE);
		implClassHeader.append("import tr.com.thy.dal.hibernate.*;" + JavaConstants.NEW_LINE);
		implClassHeader.append("import tr.com.thy.dal.pojo.*;" + JavaConstants.NEW_LINE);
		implClassHeader.append("import java.util.*;" + JavaConstants.NEW_LINE);

		// public class TAFAIZHibernateDAOGenerated extends TafaizHibernateDAO
		// implements TafaizDAOGenerated{
		// public class TbesyilGenHibernateDAO extends
		// AbstractHibernateDAO<Tbesyil, TbesyilPK> implements TbesyilGenDAO {

		implClassHeader.append(JavaConstants.NEW_LINE);
		implClassHeader.append("@Repository" + JavaConstants.NEW_LINE);
		implClassHeader.append("public class " + pojoName + "GenHibernateDAO extends AbstractHibernateDAO<" + pojoName +", Long> implements " + pojoName + "GenDAO" + JavaConstants.OPEN_BRACKET+JavaConstants.NEW_LINE);
		
				return implClassHeader;
	}

	public static int examineFullGivingLength(String sBIC, String searchString) {
		if (searchString.equals("")) {
			return sBIC.length();
		}
		return 0;
	}

	/**
	 * 
	 * program objesinin parameterToSet parametresini reflectionla paramValue
	 * ile set eder.
	 * 
	 * UYARI olabilir. Tipi primitive dir. MAPP.UYARI olabilir. SECILEN_DIZISI
	 * olabilir. Tipi class dır.
	 */
	/*
	public static void setParameter(NaturalProgram program, Parameter parameterToSet, Parameter paramValue) {
		try {
			if (parameterToSet.getParamName().contains(".")) {// MAPP.UYARI gibi
																// {
				String[] parameterNames = parameterToSet.getParamName().split("\\.");
				Field f1 = program.getClass().getDeclaredField(parameterNames[0]); // MAPP
																					// objesini
																					// al.
				f1.setAccessible(true);
				// Field f2 = f1.getField(parameterNames[1]); //UYARI field ini
				// al.
				// f2.setAccessible(true);
				// f2.set(program, paramValue.getParameter());
			} else {
				Field f1 = program.getClass().getDeclaredField(parameterToSet.getParamName());
				f1.setAccessible(true);
				f1.set(program, paramValue.getParameter());
			}
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void setReturnParameter(NaturalProgram program, Parameter returnParameterToSet,
			Object paramValueToSet) {

		try {
			if (returnParameterToSet.getParamName().contains(".")) {// MAPP.UYARI
																	// gibi {
				String[] parameterNames = returnParameterToSet.getParamName().split("\\.");
				Field f1 = program.getClass().getDeclaredField(parameterNames[0]); // MAPP
																					// objesini
																					// al.
				f1.setAccessible(true);
				// Field f2 = f1.getField(parameterNames[1]); //UYARI field ini
				// al.
				// f2.setAccessible(true);
				// f2.set(program, paramValue.getParameter());
			} else {
				Field f1 = program.getClass().getDeclaredField(returnParameterToSet.getParamName());
				f1.setAccessible(true);
				f1.set(program, paramValueToSet);
			}
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}*/

	public static int convertMiliSecondToDay(long timeInMiliseconds) {
		return (int) (timeInMiliseconds / MILISECOND_TO_DAY);
	}

	public static int convertDateToDaySince1970(Date date) {
		return (int) (date.getTime() / MILISECOND_TO_DAY);
	}

	

	

	public static boolean hasArrayNullOrEmtyValue(String[] array) {
		for (int i = 0; i < array.length; i++) {
			if (array[i] == null || array[i].trim().isEmpty()) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean isArrayContains(String[] array, String value) {
		if(value == null){
			value= "";
		}
		if(array==null){
			return false;
		}
		for (int i = 0; i < array.length; i++) {
			if (array[i]!=null && array[i].equals(value)) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean isArrayContains(String[] array, String value, int startInd, int endInd) {
		for (int i = startInd; i < endInd; i++) {
			if (array[i].equals(value)) {
				return true;
			}
		}
		return false;
	}

	public static boolean isEmpty(String param) {
		if (param == null || param.isEmpty()) {
			return true;
		}
		return false;
	}




	public static String strSonaAltTireAt(String str, int karakterSayisi) {
		if(str==null){
			str="";
		}
		StringBuffer sb = null;
		try {
			sb = new StringBuffer(str);
			if(sb.toString().length()<karakterSayisi){
				for (int i = sb.toString().length(); i < karakterSayisi; i++) {
					sb.insert(sb.toString().length(), "_");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	public static Field getField(Object sourceObj, String paramName) {

		Field parameterField = null;

		try {
			
		
			parameterField = sourceObj.getClass().getField(paramName);
			
			// this
			
		} catch (NoSuchFieldException e) {
			try {
				parameterField = sourceObj.getClass().getDeclaredField(paramName);
				logger.debug("Parametre Init Yapılamadı Normal olabilir:" + e.getMessage());
			} catch (NoSuchFieldException e1) {
				if(!paramName.equals("resetProgram") && !paramName.equals("Gonder")){
					e1.printStackTrace();
				}
			} catch (SecurityException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}
		return parameterField;
	}

	public static void writeconversionErrors(Exception e, AbstractJavaElement javaElement) throws Exception {

		ConversionLogModel logModel = ConversionLogModel.getInstance();

		StringBuilder sb = new StringBuilder();

		try {
			WriteToFile.appendToFile(sb.toString(), logModel.getFullConversionErrorFileName());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		ConversionLogReport.getInstance().addException(new ConversionException(e));
		
		if(ConverterConfiguration.STOP_ENGINE_ON_CONVERSION_ERROR){
			logger.warn(e.getMessage(),e);
			throw e;
		}

	}
	


	public static int getVariableMaxLength(AbstractToken currToken) {

		int maxLength = ConverterConfiguration.DEFAULT_MAX_LENGTH_FOR_INPUT;

		ElementProgramDataTypeNatural programData;

		try {
			
			logger.debug(currToken.toString());
			
			AbstractCommand dataType = ConvertUtilities.getVariableDefinitinCommand(currToken);

			programData = (ElementProgramDataTypeNatural) dataType;

			maxLength = programData.getLength() + programData.getLengthAfterDot() + 1;
		} catch (Exception e) {

		}

		return maxLength;
	}
	
	public static int getArrayLength(AbstractToken currToken) {

		ElementProgramOneDimensionArrayNatural programData;
		
		int arrayLength=ConverterConfiguration.DEFAULT_ARRAY_LENGTH;

		try {
			AbstractCommand dataType = ConvertUtilities.getVariableDefinitinCommand(currToken);

			programData = (ElementProgramOneDimensionArrayNatural) dataType;

			arrayLength = programData.getArrayLength();
		} catch (Exception e) {
			 return getLengthOfGlobalArrays(currToken);
			//Globalde tanımlı ise 
		}

		return arrayLength;
	}
	
	public static int getLengthOfGlobalArrays(AbstractToken currToken){
		if(ConversionLogModel.getInstance().getModule().equals("TPS")){
			if(currToken.getDeger().equals("SCRLINES")){
				return 40;
			}
		}
		return ConverterConfiguration.DEFAULT_ARRAY_LENGTH;
	}

	

	public static void assingFromArrayToArrayAllItems(String[] arrayFrom, String[] arrayTo) {
		
		arrayTo=arrayFrom;
	}

	


		

	
 
public static void main(String[] args) {
	String a="19981212";
	String b="199";
	//System.out.println(srtTarihToSlash(a));
	//System.out.println(basaAltCizgiEkle(b,5));
	//System.out.println(sonaAltCizgiEkle(b,5));
}




}
