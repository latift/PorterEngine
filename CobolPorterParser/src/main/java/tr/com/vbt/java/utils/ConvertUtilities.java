package tr.com.vbt.java.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
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
import tr.com.vbt.natural.parser.datalayout.program.ElementProgramTwoDimensionArrayNatural;
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
	public static long getMaxSize(String variableName) {
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

	// PERG001.SICIL --> SICIL
	public static String onlyFieldOfIncludedVariable(String inclVariable) {

		String result = inclVariable;

		if (inclVariable.contains(".")) {
			int indexOfDot = inclVariable.indexOf(".");
			result = inclVariable.substring(indexOfDot + 1);
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

	
	public static VariableTypes getVariableType(AbstractToken variable) {

		if (variable.isSayi()) {
			return VariableTypes.LONG_TYPE;
		}
		
		if(variable.isVal()){
			return VariableTypes.LONG_TYPE;
		}
		ElementProgramDataTypeNatural programData;
		ElementProgramGrupNatural programGrupData;
		ElementProgramOneDimensionArrayNatural elementProgramOneDimensionArrayNatural;
		ElementProgramTwoDimensionArrayNatural elementProgramTwoDimensionArrayNatural;
		
		if(variable.getLinkedToken()!=null){
			variable=variable.getLinkedToken();
		}
		
		
		if(variable.isSystemVariable()){
			return getVariableTypeOfSystem(variable);
		}else if(variable.isPojoVariable()){
			return getVariableTypeOfPojo(variable);
		}else if(variable.isGlobalVariable()){
			return getVariableTypeOfGlobalVariable(variable);
		}else if(variable.isRedefinedVariable()){
			return getVariableTypeOfRedefinedVariable(variable);
		}else if(variable.isConstantVariableWithQuota()){
			return VariableTypes.STRING_TYPE;
		}
		
		List<AbstractCommand> commandList = NaturalCommandList.getInstance().getCommandListWithIncludedVariables();
		for (AbstractCommand abstractCommand : commandList) {
			
			logger.debug(abstractCommand.toString());
			
			if (abstractCommand instanceof ElementProgramDataTypeNatural) {
				programData = (ElementProgramDataTypeNatural) abstractCommand;
				if (programData.getDataName().equals(variable.getDeger())) {
					if (programData.getDataType().substring(0, 1).equals("A")) {
						return VariableTypes.STRING_TYPE;
					} else if (programData.getDataType().substring(0, 1).equals("N") || programData.getDataType().substring(0, 1).equals("P")) {
						if (programData.getLength() > 18) {
							return VariableTypes.BIG_DECIMAL_TYPE;
						}else if (programData.getLengthAfterDot() == 0) {
							return VariableTypes.LONG_TYPE;
						} else {
							return VariableTypes.BIG_DECIMAL_TYPE;
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
			} else if (abstractCommand instanceof ElementProgramOneDimensionArrayNatural) {
				elementProgramOneDimensionArrayNatural = (ElementProgramOneDimensionArrayNatural) abstractCommand;
				if (elementProgramOneDimensionArrayNatural.getDataName().equals(variable.getDeger())) {
						if (elementProgramOneDimensionArrayNatural.getDataType().substring(0, 1).equals("A")) {
							return VariableTypes.STRING_TYPE;
						} else if (elementProgramOneDimensionArrayNatural.getDataType().substring(0, 1).equals("N") || elementProgramOneDimensionArrayNatural.getDataType().substring(0, 1).equals("P")) {
							if (elementProgramOneDimensionArrayNatural.getLengthAfterDot() == 0) {
								return VariableTypes.LONG_TYPE;
							} else {
								return VariableTypes.BIG_DECIMAL_TYPE;
							}
						} else if (elementProgramOneDimensionArrayNatural.getDataType().substring(0, 1).equals("D")) {
							return VariableTypes.DATE_TYPE;
						}
					}
				
			} else if (abstractCommand instanceof ElementProgramTwoDimensionArrayNatural) {
				
				elementProgramTwoDimensionArrayNatural = (ElementProgramTwoDimensionArrayNatural) abstractCommand;
				if (elementProgramTwoDimensionArrayNatural.getDataName().equals(variable.getDeger())) {
						if (elementProgramTwoDimensionArrayNatural.getDataType().substring(0, 1).equals("A")) {
							return VariableTypes.STRING_TYPE;
						} else if (elementProgramTwoDimensionArrayNatural.getDataType().substring(0, 1).equals("N") || elementProgramTwoDimensionArrayNatural.getDataType().substring(0, 1).equals("P")) {
							if (elementProgramTwoDimensionArrayNatural.getLengthAfterDot() == 0) {
								return VariableTypes.LONG_TYPE;
							} else {
								return VariableTypes.BIG_DECIMAL_TYPE;
							}
						} else if (elementProgramTwoDimensionArrayNatural.getDataType().substring(0, 1).equals("D")) {
							return VariableTypes.DATE_TYPE;
						}
					}
			}
		}
		return VariableTypes.UNDEFINED_TYPE;
	}
	
	private static VariableTypes getVariableTypeOfSystem(AbstractToken variable) {
			if (variable.getDeger().toString().startsWith("DAT")) {
				return VariableTypes.DATE_TYPE;
			}else if (variable.getDeger().toString().equalsIgnoreCase("PF_KEY") || variable.getDeger().toString().equalsIgnoreCase("USER")
					|| variable.getDeger().toString().equalsIgnoreCase("PROGRAM") || variable.getDeger().toString().equalsIgnoreCase("DEVICE")
					|| variable.getDeger().toString().equalsIgnoreCase("LANGUAGE")) {
				return VariableTypes.STRING_TYPE;
			} else {
				return VariableTypes.LONG_TYPE;
			}
		
	}

	private static VariableTypes getVariableTypeOfRedefinedVariable(AbstractToken variable) {
		
		return variable.getVarType();
	}

	private static VariableTypes getVariableTypeOfGlobalVariable(AbstractToken variable) {
	
		String variableName=variable.getDeger().toString();
		if(variableName.contains("HESCINSI")||
				variableName.contains("ISUSER")||
				variableName.contains("YAZICI")||
				variableName.contains("LAZERYAZICI")||
				variableName.contains("GMESAJ")||
				variableName.contains("PIKYAZICI")||
				variableName.contains("HESDOKYAZICI")
				){
			return VariableTypes.STRING_TYPE;
		}else{
			return VariableTypes.LONG_TYPE;
		}
	

	}
	
	public static String getVariableTypeOfPojoAsString(AbstractToken variable) {
		
		return Utility.findViewAndColumnNamesReturnType(variable).toLowerCase();
		
	}

	public static VariableTypes getVariableTypeOfPojo(AbstractToken variable) {
		
		String columnReturnType=Utility.findViewAndColumnNamesReturnType(variable).toLowerCase();
		
		if(columnReturnType.equals("string")){
			return VariableTypes.STRING_TYPE;
		}else if(columnReturnType.equals("bigdecimal")){
			return VariableTypes.BIG_DECIMAL_TYPE;
		}else if(columnReturnType.equals("long")){
			return VariableTypes.LONG_TYPE;
		}else if(columnReturnType.equals("date")){
			return VariableTypes.DATE_TYPE;
		}else if(columnReturnType.equals("grup")){
			return VariableTypes.GRUP_TYPE;
		}
		return VariableTypes.UNDEFINED_TYPE;
		
		
	}
	public static String getVariableTypeOfString(AbstractToken variable) {
		Double d = 0.0;
		if (variable.getTip().equals(TokenTipi.Sayi)) {
			if (variable.getDeger() instanceof Long || variable.getDeger() instanceof Integer) {
				return "long";
			} else {

				d = (Double) variable.getDeger();
				if (d % 1 != 0) {
					return "float";
				} else {
					return "long";
				}

			}

		}else if(variable.isConstantVariableWithQuota()){
			return "String";
		}
		
		String variableDeger = variable.getDeger().toString();

		if (variable.getDeger().toString().contains(".")) {
			int pointIndex = variable.getDeger().toString().indexOf(".");
			variableDeger = variable.getDeger().toString().substring(pointIndex + 1);
		}

		ElementProgramDataTypeNatural programData;
		ElementProgramGrupNatural grupData;
		ElementProgramOneDimensionArrayNatural arrayItem;
		ElementDBDataTypeNatural dbDataType;
		DDM ddm;
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
			} else if (abstractCommand instanceof ElementProgramOneDimensionArrayNatural) {
				arrayItem = (ElementProgramOneDimensionArrayNatural) abstractCommand;
				if (arrayItem.getDataName().equals(variableDeger)) {
					return ConvertUtilities.getJavaVariableType(arrayItem.getDataType(),
							arrayItem.getCommandMatchPoint(), arrayItem.getLengthAfterDot());
				}
			}

			else if (abstractCommand instanceof ElementDBDataTypeNatural) {
				dbDataType = (ElementDBDataTypeNatural) abstractCommand;
				if (dbDataType.getDataName().equals(variableDeger)) {
					ddm = DDMList.getInstance().getDDMByKey(dbDataType.getDataName(), abstractCommand);
					if (ddm == null || ddm.getF() == null) {
						return "String";
					} else {
						return ConvertUtilities.getJavaVariableType(ddm.getF(), 0, 0);
					}
				}
			}

		}
		return VariableTypes.UNDEFINED_TYPE.toString();
	}
	
	public static AbstractCommand getCommandsParentCommand(AbstractCommand command) {
		
		return command.getParent();
	}

	public static AbstractCommand getVariableDefinitinCommand(AbstractToken variable) {

		List<AbstractCommand> commandList = NaturalCommandList.getInstance().getCommandList();

		ElementProgramDataTypeNatural programData;

		ElementProgramGrupNatural grupNatural;
		
		ElementProgramOneDimensionArrayNatural elementProgramOneDimensionArrayNatural;

		for (AbstractCommand abstractCommand : commandList) {
			logger.debug(abstractCommand.toString());
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
			}else if (abstractCommand instanceof ElementProgramOneDimensionArrayNatural) {
				elementProgramOneDimensionArrayNatural = (ElementProgramOneDimensionArrayNatural) abstractCommand;
				if (elementProgramOneDimensionArrayNatural.getDataName().equals(variable.getDeger())) {
					return elementProgramOneDimensionArrayNatural;
				}
			}
		}
		return null;
	}

	public static String getJavaVariableType(String dataType, long length, long lengthAfterDot) {
		String type;
		if (dataType.equals("A") || dataType.equals("String")) {
			type = "String";
		} else if (	(dataType.equals("N") || dataType.equals("P") || dataType.equals("int") || dataType.equals("I"))		 && length > 18 ) {
			type = "BigDecimal";
		}  else if (((dataType.equals("N") || dataType.equals("P") || dataType.equals("int") || dataType.equals("I")) && lengthAfterDot == 0) ) {
			type = "long";
		} else if (((dataType.equals("N") || dataType.equals("P") || dataType.equals("float")) && lengthAfterDot != 0) ) {
			type = "BigDecimal";
		} else if (dataType.equals("D") || dataType.equals("Date") ) {
			type = "Date";
		} else if (dataType.equals("T") || dataType.equals("Date")) {
			type = "Time";
		} else if (dataType.equals("L") || dataType.equals("boolean")) {
			type = "boolean";
		} else if (dataType.equals("C") || dataType.equals("ControlEnum")) {
			type = "ControlEnum";
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

		if(variable.isSubstringCommand()){
			return "String";
		}else if(variable.isSystemVariable()) {
		
			if (variable.getDeger().toString().contains("DAT")) {
				return getTypeOfSystemVariableDAT(variable);
			}else if (variable.getDeger().toString().equalsIgnoreCase("PF_KEY") || variable.getDeger().toString().equalsIgnoreCase("USER")
					|| variable.getDeger().toString().equalsIgnoreCase("PROGRAM") || variable.getDeger().toString().equalsIgnoreCase("DEVICE")
					|| variable.getDeger().toString().equalsIgnoreCase("LANGUAGE") || variable.getDeger().toString().startsWith("DAT")) {
				return "String";
			} else {
				return "long";
			}
		}else if(variable.isSayi()){
			return "long";
		}else if(variable.isIncludedVariable()){
			try {
				String type= variable.getIncludedVariable().getType().getSimpleName().toLowerCase();
				return type;
			} catch (Exception e) {
				return null;
			}
		}else if(variable.isConstantVariableWithQuota()){
			return "String";
		}
		if(variable.getLinkedToken()!=null){
			variable=variable.getLinkedToken();
		}else if(variable.isPojoVariable()){
			return getVariableTypeOfPojoAsString(variable);
			
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
		return ""; 
	}

	private static String getTypeOfSystemVariableDAT(AbstractToken variable) {
		if (variable.isKelime("DATN")) {
			return "long";
		}else if(variable.isKelime("DATX")){
			return "date";
		}else{
			return "String";
		}
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

		c = Utility.findPojoClass(className);
		
		Field field;

		try {
			field = c.getDeclaredField(fieldName);
		
			field.setAccessible(true);
		
			return field.getType().getSimpleName();

		} catch (Exception e) {
			
			try {
				Class cPK = null;
				
				cPK = Utility.findPojoClass(className+"PK");
				
				field = cPK.getDeclaredField(fieldName);
				
				field.setAccessible(true);
				
				return field.getType().getSimpleName();
	
			} catch (Exception e1) {
				return "UndefinedPojoFieldType";
			}
			
		}
		
	}
	
	
	public static String getPojosFieldType(String pojoName,AbstractToken columnToken) {
		// THESAP -->Thesap
		String className = Utility.viewNameToPojoName(pojoName);
		
		if(columnToken.getColumnNameToken()!=null){
			columnToken=columnToken.getColumnNameToken();
		}

		String fieldName = Utility.columnNameToPojoFieldName(columnToken.getDeger().toString());

		Class c = null;

		c = Utility.findPojoClass(className);
		
		Field field;

		try {
			field = c.getDeclaredField(fieldName);
		
			field.setAccessible(true);
		
			return field.getType().getSimpleName();

		} catch (Exception e) {
			
			try {
				Class cPK = null;
				
				cPK = Utility.findPojoClass(className+"PK");
				
				field = cPK.getDeclaredField(fieldName);
				
				field.setAccessible(true);
				
				return field.getType().getSimpleName();
	
			} catch (Exception e1) {
				return "UndefinedPojoFieldType";
			}
			
		}
		
	}
	
	//Girtar --> girtar
	//Hescinsi --> id.hescinsi
	public static String getPojosFieldTypeForHibernate(String pojoName, String columnName) {
		
		//String className = Utility.viewNameToPojoName(pojoName);
		
		columnName=columnName.substring(0,1).toLowerCase()+columnName.substring(1);
		
		String fieldName = columnName;

		Class c = null;

		c = Utility.findPojoClass(pojoName);
		
		Field field;

		try {
			field = c.getDeclaredField(fieldName);
		
			return columnName;

		} catch (Exception e) {
			
			try {
				Class cPK = null;
				
				cPK = Utility.findPojoClass(pojoName+"PK");
				
				field = cPK.getDeclaredField(fieldName);
				
				field.setAccessible(true);
				
				return "id."+columnName;
	
			} catch (Exception e1) {
				logger.debug(e1.getMessage(),e1);
				return "";
			}
			
		}
		
	}
	public static boolean pojoHasField(AbstractToken pojoName,AbstractToken columnToken) {
		// THESAP -->Thesap
		String className = Utility.viewNameToPojoName(pojoName.getDeger().toString());
		
		if(columnToken.getColumnNameToken()!=null){
			columnToken=columnToken.getColumnNameToken();
		}

		String fieldName = Utility.columnNameToPojoFieldName(columnToken.getDeger().toString());

		Class c = null;

		c = Utility.findPojoClass(className);
		
		Field field;

		try {
			field = c.getDeclaredField(fieldName);
		
			return true; //field varsa return true;

		} catch (Exception e) {
			
			try {
				Class cPK = null;
				
				cPK = Utility.findPojoClass(className+"PK");
				
				field = cPK.getDeclaredField(fieldName);
				
				field.setAccessible(true);
				
				return true; //field varsa return true;
	
			} catch (Exception e1) {
				return false;
			}
			
		}
		
	}

	public static StringBuffer writeInterfaceHeader(String pojoName) {
		StringBuffer interfaceHeader = new StringBuffer();

		ConversionLogModel logModel = ConversionLogModel.getInstance();

		if (ConverterConfiguration.addGenerationInfoToJavaClass) {
			logModel = ConversionLogModel.getInstance();

			interfaceHeader.append("/**" + JavaConstants.NEW_LINE);
			interfaceHeader.append("* " + JavaConstants.CLASS_GENERATION_INFO + JavaConstants.NEW_LINE);
			interfaceHeader
					.append("* Engine Version: " + ConverterConfiguration.ENGINE_VERSION + JavaConstants.NEW_LINE);
			interfaceHeader.append("* Generation Date: " + ConvertUtilities.getCurrentDate() + JavaConstants.NEW_LINE);
			interfaceHeader.append("* Conversion User: " + logModel.getUser() + JavaConstants.NEW_LINE);
			interfaceHeader.append("* OS: " + logModel.getOPERATING_SYSTEM() + JavaConstants.NEW_LINE);
			interfaceHeader.append("* Customer: " + logModel.getCustomer() + JavaConstants.NEW_LINE);
			interfaceHeader.append("*/" + JavaConstants.NEW_LINE);
			interfaceHeader.append(JavaConstants.NEW_LINE);
		}

		interfaceHeader.append(
				JavaConstants.PACKAGE + " " + "tr.com." + ConversionLogModel.getInstance().getCustomer().toLowerCase()
						+ ".dal.generated" + " " + JavaConstants.DOT_WITH_COMMA + JavaConstants.NEW_LINE);

		if (logModel.getCustomer().equals("MB")) {

			interfaceHeader.append("import tr.com." + ConversionLogModel.getInstance().getCustomer().toLowerCase()
					+ ".dal.*;" + JavaConstants.NEW_LINE);

			/*String schemaName;
			for (int i = 0; i < ConverterConfiguration.getSchemaList().size(); i++) {
				schemaName = ConverterConfiguration.getSchemaList().get(i);
				interfaceHeader.append("import tr.com." + ConversionLogModel.getInstance().getCustomer().toLowerCase()
						+ ".dal.pojo." + schemaName.toLowerCase() + ".*;" + JavaConstants.NEW_LINE);
			}*/
			
			AbstractJavaElement.javaCodeBuffer.append("import tr.com."+ConversionLogModel.getInstance().getCustomer().toLowerCase()+".dal.pojo.idgidbs.*;"+JavaConstants.NEW_LINE);
			AbstractJavaElement.javaCodeBuffer.append("import tr.com."+ConversionLogModel.getInstance().getCustomer().toLowerCase()+".dal.pojo.common.*;"+JavaConstants.NEW_LINE);

		} else {

			interfaceHeader.append("import tr.com." + ConversionLogModel.getInstance().getCustomer().toLowerCase()
					+ ".dal.*;" + JavaConstants.NEW_LINE);
			interfaceHeader.append("import tr.com." + ConversionLogModel.getInstance().getCustomer().toLowerCase()
					+ ".dal.pojo.*;" + JavaConstants.NEW_LINE);
		}
		
		
		interfaceHeader.append("import java.math.BigDecimal;" + JavaConstants.NEW_LINE);
		interfaceHeader.append("import java.util.*;" + JavaConstants.NEW_LINE);

		interfaceHeader.append(JavaConstants.NEW_LINE);
		// fileName =fileName
		// public interface TafaizDAOGenerated extends TafaizDAO {
		// public interface TbesyilGenDAO extends GenericDAO<Tbesyil,TbesyilPK>{
		interfaceHeader.append("public interface " + pojoName + "GenDAO extends GenericDAO<" + pojoName + ", Long>"
				+ JavaConstants.OPEN_BRACKET + JavaConstants.NEW_LINE);
		return interfaceHeader;
	}

	public static StringBuffer writeDAOImplemantasyonClassHeader(String pojoName) {
		StringBuffer implClassHeader = new StringBuffer();

		ConversionLogModel logModel = ConversionLogModel.getInstance();

		if (ConverterConfiguration.addGenerationInfoToJavaClass) {
			logModel = ConversionLogModel.getInstance();

			implClassHeader.append("/**" + JavaConstants.NEW_LINE);
			implClassHeader.append("* " + JavaConstants.CLASS_GENERATION_INFO + JavaConstants.NEW_LINE);
			implClassHeader
					.append("* Engine Version: " + ConverterConfiguration.ENGINE_VERSION + JavaConstants.NEW_LINE);
			implClassHeader.append("* Generation Date: " + ConvertUtilities.getCurrentDate() + JavaConstants.NEW_LINE);
			implClassHeader.append("* Conversion User: " + logModel.getUser() + JavaConstants.NEW_LINE);
			implClassHeader.append("* OS: " + logModel.getOPERATING_SYSTEM() + JavaConstants.NEW_LINE);
			implClassHeader.append("* Customer: " + logModel.getCustomer() + JavaConstants.NEW_LINE);
			implClassHeader.append("*/" + JavaConstants.NEW_LINE);
			implClassHeader.append(JavaConstants.NEW_LINE);
		}

		// package tr.com.mb.dal.hibernate.generated;
		implClassHeader.append(
				JavaConstants.PACKAGE + " " + "tr.com." + ConversionLogModel.getInstance().getCustomer().toLowerCase()
						+ ".dal.hibernate.generated" + JavaConstants.DOT_WITH_COMMA + JavaConstants.NEW_LINE);

		implClassHeader.append("import java.util.*;" + JavaConstants.NEW_LINE);
		implClassHeader.append("import java.math.BigDecimal;" + JavaConstants.NEW_LINE);
	
		implClassHeader.append("import org.hibernate.Criteria;" + JavaConstants.NEW_LINE);
		implClassHeader.append("import org.hibernate.criterion.Restrictions;" + JavaConstants.NEW_LINE);
		implClassHeader.append("import org.springframework.stereotype.*;" + JavaConstants.NEW_LINE);

		if (logModel.getCustomer().equals("MB")) {

			implClassHeader.append("import tr.com." + ConversionLogModel.getInstance().getCustomer().toLowerCase()
					+ ".dal.generated.*;" + JavaConstants.NEW_LINE);
			implClassHeader.append("import tr.com." + ConversionLogModel.getInstance().getCustomer().toLowerCase()
					+ ".dal.hibernate.*;" + JavaConstants.NEW_LINE);

			String schemaName;
			/*for (int i = 0; i < ConverterConfiguration.getSchemaList().size(); i++) {
				schemaName = ConverterConfiguration.getSchemaList().get(i);
				implClassHeader.append("import tr.com." + ConversionLogModel.getInstance().getCustomer().toLowerCase()
						+ ".dal.pojo." + schemaName.toLowerCase() + ".*;" + JavaConstants.NEW_LINE);
			}*/
			
			AbstractJavaElement.javaCodeBuffer.append("import tr.com."+ConversionLogModel.getInstance().getCustomer().toLowerCase()+".dal.pojo.idgidbs.*;"+JavaConstants.NEW_LINE);
			AbstractJavaElement.javaCodeBuffer.append("import tr.com."+ConversionLogModel.getInstance().getCustomer().toLowerCase()+".dal.pojo.common.*;"+JavaConstants.NEW_LINE);
			
	
		} else {

			implClassHeader.append("import tr.com." + ConversionLogModel.getInstance().getCustomer().toLowerCase()
					+ ".dal.generated.*;" + JavaConstants.NEW_LINE);
			implClassHeader.append("import tr.com." + ConversionLogModel.getInstance().getCustomer().toLowerCase()
					+ ".dal.hibernate.*;" + JavaConstants.NEW_LINE);
			implClassHeader.append("import tr.com." + ConversionLogModel.getInstance().getCustomer().toLowerCase()
					+ ".dal.pojo.*;" + JavaConstants.NEW_LINE);
			implClassHeader.append("import tr.com." + ConversionLogModel.getInstance().getCustomer().toLowerCase()
					+ ".dal.*;" + JavaConstants.NEW_LINE);
		}

		implClassHeader.append(JavaConstants.NEW_LINE);
		implClassHeader.append("@Repository" + JavaConstants.NEW_LINE);
		implClassHeader.append("public class " + pojoName + "GenHibernateDAO extends AbstractHibernateDAO<" + pojoName
				+ ", Long> implements " + pojoName + "GenDAO" + JavaConstants.OPEN_BRACKET + JavaConstants.NEW_LINE);

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
	 * public static void setParameter(NaturalProgram program, Parameter
	 * parameterToSet, Parameter paramValue) { try { if
	 * (parameterToSet.getParamName().contains(".")) {// MAPP.UYARI gibi // {
	 * String[] parameterNames = parameterToSet.getParamName().split("\\.");
	 * Field f1 = program.getClass().getDeclaredField(parameterNames[0]); //
	 * MAPP // objesini // al. f1.setAccessible(true); // Field f2 =
	 * f1.getField(parameterNames[1]); //UYARI field ini // al. //
	 * f2.setAccessible(true); // f2.set(program, paramValue.getParameter()); }
	 * else { Field f1 =
	 * program.getClass().getDeclaredField(parameterToSet.getParamName());
	 * f1.setAccessible(true); f1.set(program, paramValue.getParameter()); } }
	 * catch (NoSuchFieldException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } catch (SecurityException e) { // TODO
	 * Auto-generated catch block e.printStackTrace(); } catch
	 * (IllegalArgumentException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } catch (IllegalAccessException e) { // TODO
	 * Auto-generated catch block e.printStackTrace(); }
	 * 
	 * }
	 * 
	 * public static void setReturnParameter(NaturalProgram program, Parameter
	 * returnParameterToSet, Object paramValueToSet) {
	 * 
	 * try { if (returnParameterToSet.getParamName().contains(".")) {//
	 * MAPP.UYARI // gibi { String[] parameterNames =
	 * returnParameterToSet.getParamName().split("\\."); Field f1 =
	 * program.getClass().getDeclaredField(parameterNames[0]); // MAPP //
	 * objesini // al. f1.setAccessible(true); // Field f2 =
	 * f1.getField(parameterNames[1]); //UYARI field ini // al. //
	 * f2.setAccessible(true); // f2.set(program, paramValue.getParameter()); }
	 * else { Field f1 =
	 * program.getClass().getDeclaredField(returnParameterToSet.getParamName());
	 * f1.setAccessible(true); f1.set(program, paramValueToSet); } } catch
	 * (NoSuchFieldException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } catch (SecurityException e) { // TODO
	 * Auto-generated catch block e.printStackTrace(); } catch
	 * (IllegalArgumentException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } catch (IllegalAccessException e) { // TODO
	 * Auto-generated catch block e.printStackTrace(); }
	 * 
	 * }
	 */

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
		if (value == null) {
			value = "";
		}
		if (array == null) {
			return false;
		}
		for (int i = 0; i < array.length; i++) {
			if (array[i] != null && array[i].equals(value)) {
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
		if (str == null) {
			str = "";
		}
		StringBuffer sb = null;
		try {
			sb = new StringBuffer(str);
			if (sb.toString().length() < karakterSayisi) {
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
				if (!paramName.equals("resetProgram") && !paramName.equals("Gonder")) {
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

		if (ConverterConfiguration.STOP_ENGINE_ON_CONVERSION_ERROR) {
			logger.warn(e.getMessage(), e);
			throw e;
		}

	}

	public static long getVariableMaxLength(AbstractToken currToken) {

		long maxLength = ConverterConfiguration.DEFAULT_MAX_LENGTH_FOR_INPUT;

		ElementProgramDataTypeNatural programData;

		try {

			logger.debug(currToken.toString());

			AbstractCommand dataType = ConvertUtilities.getVariableDefinitinCommand(currToken);

			programData = (ElementProgramDataTypeNatural) dataType;

			if(programData.getLengthAfterDot()==0){
				
				maxLength = programData.getLength();
				
			}else{
				
				maxLength = programData.getLength() + programData.getLengthAfterDot() + 1;
		
			}
		} catch (Exception e) {

		}

		return maxLength;
	}

	public static long getArrayLength(AbstractToken currToken) {

		ElementProgramOneDimensionArrayNatural programData;

		long arrayLength = ConverterConfiguration.DEFAULT_ARRAY_LENGTH;

		try {
			AbstractCommand dataType = ConvertUtilities.getVariableDefinitinCommand(currToken);

			programData = (ElementProgramOneDimensionArrayNatural) dataType;

			arrayLength = programData.getArrayLength();
		} catch (Exception e) {
			return getLengthOfGlobalArrays(currToken);
			// Globalde tanımlı ise
		}

		return arrayLength;
	}

	public static int getLengthOfGlobalArrays(AbstractToken currToken) {
		if (ConversionLogModel.getInstance().getModule().equals("TPS")) {
			if (currToken.getDeger().equals("SCRLINES")) {
				return 40;
			}
		}
		return ConverterConfiguration.DEFAULT_ARRAY_LENGTH;
	}

	public static void assingFromArrayToArrayAllItems(String[] arrayFrom, String[] arrayTo) {

		arrayTo = arrayFrom;
	}
	
	public static String castToInt(){
		return "(int)";
		
	}

	public static void main(String[] args) {
		String a = "19981212";
		String b = "199";
		// System.out.println(srtTarihToSlash(a));
		// System.out.println(basaAltCizgiEkle(b,5));
		// System.out.println(sonaAltCizgiEkle(b,5));
	}

	public static boolean isBigDecimal(AbstractToken token) {
		
		VariableTypes varType=ConvertUtilities.getVariableType(token);
		
		return varType.equals(VariableTypes.BIG_DECIMAL_TYPE);
	}

	public static boolean isPrimitiveType(AbstractToken token) {
		
		VariableTypes varType=ConvertUtilities.getVariableType(token);
		
		return varType.equals(VariableTypes.LONG_TYPE)|| varType.equals(VariableTypes.INT_TYPE);
	}



}
