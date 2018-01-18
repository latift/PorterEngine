package tr.com.vbt.java.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.log4j.Logger;


import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.ddm.DDM;
import tr.com.vbt.ddm.DDMList;
import tr.com.vbt.java.general.JavaClassElement;
import tr.com.vbt.java.util.Utility;
import tr.com.vbt.lexer.ConversionLogModel;
import tr.com.vbt.lexer.RedefinedColumn;
import tr.com.vbt.natural.parser.datalayout.program.ElementProgramDataTypeNatural;
import tr.com.vbt.natural.parser.datalayout.program.ElementProgramGrupNatural;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.ArrayToken;
import tr.com.vbt.token.KelimeToken;
import tr.com.vbt.token.TokenTipi;
import tr.com.vbt.util.WriteToFile;

public class JavaWriteUtilities extends AbstractJavaWriteUtility{
	
	final static Logger logger = Logger.getLogger(JavaWriteUtilities.class);
	
	private static String endCastStr=null;
	
	public static StringBuilder toCustomSetterString(AbstractToken token) throws Exception {
		
		StringBuilder tempCodeBuffer=new StringBuilder();

		
		if(token.getTip().equals(TokenTipi.SatirBasi)){ 
			//Do nothing;
		}else if(token.isRedefinedVariable()){ 
			
			tempCodeBuffer.append(toCustomRedefinedVariableSetterString(token));
			
		}else if(token.getTip().equals(TokenTipi.Karakter)){
		
		}else if(token.isPojoVariable()){ //MB
				
			tempCodeBuffer.append(toCustomPojoVariableSetterString(token));
			
		}else if(token.isSystemVariable()){
	
			tempCodeBuffer.append(toCustomSystemVariableString(token));
			
		}else if(token.isConstantVariableWithQuota()){		
		
			
		}else if(token.isRecordVariable()){
				
			tempCodeBuffer.append(toCustomRecordVariableSetterString(token));
			
		}else if(token.isSubstringCommand()){
			
		
		}else if(token.isEdited()){ //DVANATOPMEB(DOVIZGEC) -->DVANATOPMEB[DOVIZGEC]
			
			
		}else if(token.getTip().equals(TokenTipi.Sayi)){
			
			
		}else if(token.getTip().equals(TokenTipi.Array)){ //DVANATOPMEB(DOVIZGEC) -->DVANATOPMEB[DOVIZGEC]
			tempCodeBuffer.append(toCustomArrayVariableString(token));
			
		}else{
			
			tempCodeBuffer.append(toCustomDefaultVariableString(token));
			
		}
		
		return tempCodeBuffer;
	}
	

	public static StringBuilder toCustomSetterString(AbstractToken token, String newValueStr) throws Exception {
		
		StringBuilder tempCodeBuffer=new StringBuilder();

		 if(token.isPojoVariable()){ //MB
			
			// IDGIDBS-TGECICI .HSONVALOR : = *DAT4I  --> TGECICI.setHSONVALOR(getSystemVAriable(DAT4I));
			tempCodeBuffer.append(toCustomPojoVariableSetterString(token, newValueStr));
			
		}
		 
			return tempCodeBuffer;
	}


	private static StringBuilder toCustomPojoVariableSetterString(AbstractToken token, String newValueStr) {
		
		StringBuilder setterString=new StringBuilder();
		
		setterString.append(Utility.viewNameToPojoFullSetterName(token));
		
		setterString.append("(");  //Pojo Starter
		
		setterString.append(newValueStr);
		
		setterString.append(")"); //Pojo Ender
		
		return setterString;
	}
	
	private static StringBuilder toCustomPojoVariableSetterString(AbstractToken token, AbstractToken newValueToken) {
		
		StringBuilder setterString=new StringBuilder();
		
		setterString.append(Utility.viewNameToPojoFullSetterName(token));
		
		setterString.append("(");  //Pojo Starter
		
		try {
			setterString.append(toCustomString(newValueToken));
		} catch (Exception e) {
			logger.debug(e.getMessage(),e);
			setterString.append(newValueToken);
		}
		
		setterString.append(")"); //Pojo Ender
		
		return setterString;
	}

	

	public static StringBuilder toCustomSetterString(AbstractToken token, AbstractToken newValueToken) throws Exception {
		
		StringBuilder tempCodeBuffer=new StringBuilder();
		
		if(token.getTip().equals(TokenTipi.SatirBasi)){ 
			//Do nothing;
		}else if(token.isRedefinedVariable()){ 
			
			tempCodeBuffer.append(toCustomRedefinedVariableSetterString(token, newValueToken));
			
		}else if(token.getTip().equals(TokenTipi.Karakter)){
		
		}else if(token.isPojoVariable() && ConversionLogModel.getInstance().isRelationalDatabase()){ //MB
				
				// IDGIDBS-TGECICI .HSONVALOR : = *DAT4I  --> TGECICI.setHSONVALOR(getSystemVAriable(DAT4I));
				tempCodeBuffer.append(toCustomPojoDB2VariableSetterString(token, newValueToken));
			
		}else if(token.isPojoVariable()){ //MB
			
			// IDGIDBS-TGECICI .HSONVALOR : = *DAT4I  --> TGECICI.setHSONVALOR(getSystemVAriable(DAT4I));
			tempCodeBuffer.append(toCustomPojoVariableSetterString(token, newValueToken));
			
		}else if(token.isSystemVariable()){
	
			tempCodeBuffer.append(toCustomSystemVariableString(token));
			
		}else if(token.isConstantVariableWithQuota()){		
		
			
		}else if(token.isRecordVariable()){
			tempCodeBuffer.append(toCustomRecordVariableSetterString(newValueToken));
		
		}else if(token.isSubstringCommand()){
			tempCodeBuffer.append(toCustomDefaultVariableString(token));
		
		}else if(token.isEdited()){ //DVANATOPMEB(DOVIZGEC) -->DVANATOPMEB[DOVIZGEC]
			tempCodeBuffer.append(toCustomDefaultVariableString(token));
			
		}else if(token.getTip().equals(TokenTipi.Sayi)){
			
			tempCodeBuffer.append(toCustomDefaultVariableString(token));
		}else if(token.getTip().equals(TokenTipi.Array)){ //DVANATOPMEB(DOVIZGEC) -->DVANATOPMEB[DOVIZGEC]
			tempCodeBuffer.append(toCustomDefaultVariableString(token));
			
		}else{
			
			tempCodeBuffer.append(toCustomDefaultVariableString(token));
			
		}
		
		return tempCodeBuffer;
	}

	private static Object toCustomRedefinedVariableSetterString(AbstractToken token, AbstractToken newValueToken) {
		StringBuffer sb=new StringBuffer();
		sb.append(token.getDeger().toString()+".setValue(");
		
		//sb.append(newValueToken.toCustomString());
		try {
			sb.append(JavaWriteUtilities.toCustomString(newValueToken));
		} catch (Exception e) {
			sb.append(newValueToken.toCustomString());
		}
		
		sb.append(")");
		return sb.toString();
	}

	public static StringBuilder toCustomString(AbstractToken token, String castType) throws Exception  {
		
		StringBuilder tempCodeBuffer=new StringBuilder();
		
		if(castType.toUpperCase().equals("BIGDECIMAL")){
			
		}
		return null;
		
	}
	public static StringBuilder toCustomString(AbstractToken token) throws Exception  {

		StringBuilder tempCodeBuffer=new StringBuilder();
		
		if(token==null){
			logger.debug("");
		}
		
		if(token.isSubstringCommand() || (token.getLinkedToken()!=null && token.getLinkedToken().isSubstringCommand())){
			
			tempCodeBuffer.append(toCustomSubstringVariableString(token));
		
		}else if(token.isPojoVariable()){
			
			if(ConversionLogModel.getInstance().isRelationalDatabase()){
			
				tempCodeBuffer.append(toCustomPojoDB2VariableString(token));
				
			}else{
				
				tempCodeBuffer.append(toCustomPojoVariableString(token));
		
			}
			
		}else if(token.getTip().equals(TokenTipi.Kelime) && token.isInputParameters() ){ 
		
			tempCodeBuffer.append(toCustomInputParametersVariableString(token));
		}else if(token.getTip().equals(TokenTipi.SatirBasi)){ 
			//Do nothing;
		}else if(token.isMasked()){ 
			
			tempCodeBuffer.append(toCustomMaskedVariableString(token));
			
		}else if(token.isRedefinedVariable()){ 
			
			tempCodeBuffer.append(toCustomRedefinedVariableString(token));
			
		}else if(token.getTip().equals(TokenTipi.Karakter)
				&&(token.getDeger().equals('+')
						||token.getDeger().equals('-')
						||token.getDeger().equals('/')
						||token.getDeger().equals('*')
						||token.getDeger().equals('%'))){
		
			tempCodeBuffer.append(toCustomKarakterVariableString(token));
		
		}else if(token.isSystemVariable()){
	
			tempCodeBuffer.append(toCustomSystemVariableString(token));
			
		}else if(token.isGlobalVariable()){
			
			tempCodeBuffer.append(toCustomGlobalVariableString(token));
			
		}else if(token.isConstantVariableWithQuota()){		
		
			tempCodeBuffer.append(toCustomConstantVariableString(token));
			
		}else if(token.isRecordVariable()){
				
			tempCodeBuffer.append(toCustomRecordVariableString(token));
		
		}else if(token.isEdited()){ //DVANATOPMEB(DOVIZGEC) -->DVANATOPMEB[DOVIZGEC]
			
			tempCodeBuffer.append(toCustomEditedVariableString(token));
			
		}else if(token.getTip().equals(TokenTipi.Sayi)){
			
			tempCodeBuffer.append(toCustomNumberVariableString(token));
			
		}else if(token.getTip().equals(TokenTipi.Array)){ //DVANATOPMEB(DOVIZGEC) -->DVANATOPMEB[DOVIZGEC]
			
			tempCodeBuffer.append(toCustomArrayVariableString(token));
			
		}else{
			
			tempCodeBuffer.append(toCustomDefaultVariableString(token));
			
		}
		
		return tempCodeBuffer;
		
	}
	

	private static Object toCustomGlobalVariableString(AbstractToken token) {
		StringBuffer resultList=new StringBuffer("");
		if(token instanceof ArrayToken){
			token.setGlobalVariable(false);
			resultList.append(token.getIncludedFile()+".getInstance(sessionId, programName)." );
			try {
				resultList.append(toCustomArrayVariableString(token));
			} catch (Exception e) {
				resultList.append("Hata_GlobalArray");
			}
			token.setGlobalVariable(true);
			return resultList.toString();
		}
		
		return token.getIncludedFile()+".getInstance(sessionId, programName)." + token.getDeger();
		
	}


	private static Object toCustomInputParametersVariableString(AbstractToken token) {
	
		StringBuffer resultList=new StringBuffer("");
		
		String inputParameters;
		
		if(token.getInputADParameters()!=null){
			
			inputParameters=token.getInputADParameters().getDeger().toString();
			
			resultList.append("ControlEnum."+inputParameters);
		}else{
			resultList.append("ControlEnum.AD");	
		}
		return resultList.toString();
	}



	private static Object toCustomMaskedVariableString(AbstractToken token) {
		StringBuffer resultList=new StringBuffer("\"");
		AbstractToken maskedToken;
		for(int i=0; i<token.getMaskTokenList().size();i++){
			
			maskedToken=(AbstractToken) token.getMaskTokenList().get(i);
			
			resultList.append(maskedToken.getDeger().toString());
		}
		resultList.append("\"");
		return resultList.toString();
	}



	private static Object toCustomRedefinedVariableString(AbstractToken token) {
	
		if(token.isRedefinedVariableDimensionToSimple()){
			if(token instanceof ArrayToken){
				return token.getDeger().toString()+".getValue("+ConvertUtilities.castToInt()+((ArrayToken)token).getFirstDimension().getDeger().toString()+"-1)";
			}else{
				return token.getDeger().toString()+".getValue("+ConvertUtilities.castToInt()+token.getDeger().toString()+"-1)";
			}
				
		}
		return token.getDeger().toString()+".getValue()";
	
	}
	
	private static Object toCustomRedefinedVariableSetterString(AbstractToken token) {
		
		return token.getDeger().toString()+".setValue(";
	
	}

	private static String toCustomKarakterVariableString(AbstractToken token) {
	
		return token.getDeger().toString();
	}

	private static String toCustomEditedVariableString(AbstractToken token) {
		
		StringBuilder tempCodeBuffer=new StringBuilder();
		
		AbstractToken maskToken;
		
		tempCodeBuffer.append("formatWithMask(");
		tempCodeBuffer.append(token.getDeger());
		tempCodeBuffer.append(",");
		
		tempCodeBuffer.append("\"");
		
		for(int i=0; i<token.getEditMaskTokenList().size();i++){
			maskToken=(AbstractToken) token.getEditMaskTokenList().get(i);
			tempCodeBuffer.append(maskToken.getDeger());
		}
		tempCodeBuffer.append("\"");
		tempCodeBuffer.append(")");
		
		return tempCodeBuffer.toString();
	}

	private static String toCustomDefaultVariableString(AbstractToken token) {
		if(token.getDeger()!=null &&( token.isKelime("FALSE")||token.isKelime("TRUE"))){
			return token.getDeger().toString().toLowerCase();
		}
		if(token.getDeger()!=null){
			return token.getDeger().toString();
		}else{
			return token.toString();
		}
	}

	private static String toCustomArrayVariableString(AbstractToken token) throws Exception {
	
		StringBuilder tempCodeBuffer=new StringBuilder();
		
		AbstractToken firstDimension;
		
		int firstDimensionSize;
		
		AbstractToken secDimension;
		
		int secDimensionSize;
		
		ArrayToken arrayToken=(ArrayToken) token;
		
		firstDimension=arrayToken.getFirstDimension();
		
		secDimension=arrayToken.getSecondDimension();
		
		int sayacForLine = 0;
		
		if(firstDimension.getDeger().toString().equals("*")){
		
			tempCodeBuffer.append(arrayToken.getDeger().toString());
		
		}else{
		
			if(firstDimension.getDeger() instanceof Integer){
			
				firstDimensionSize=((int)((long)firstDimension.getDeger()));
				if(arrayToken.getDeger().equals("_") || arrayToken.getDeger().equals("=") || arrayToken.getDeger().equals("-")){ //Cizgi Bastirmak Icin Eklendi
					tempCodeBuffer.append("\"");
					while(sayacForLine != firstDimensionSize){
						sayacForLine++;
						tempCodeBuffer.append(arrayToken.getDeger());
					}
					tempCodeBuffer.append("\"");
				}else{
					tempCodeBuffer.append(arrayToken.getDeger().toString()+"["+addIntCastForArrays()+firstDimensionSize+"-1]");
				}
			
			}else {
			
				firstDimension=controlFirstDimensionIsRecord(firstDimension);
				tempCodeBuffer.append(arrayToken.getDeger().toString()+"["+addIntCastForArrays()+JavaWriteUtilities.toCustomString(firstDimension)+"-1]");
			
			}
			if(secDimension!=null){
			
				if(secDimension.getDeger() instanceof Integer){
				
					secDimensionSize=((int)((long)secDimension.getDeger()));
			
					tempCodeBuffer.append("["+addIntCastForArrays()+secDimensionSize+"-1]");
				
				}else {
				
					tempCodeBuffer.append("["+addIntCastForArrays()+secDimension.getDeger().toString()+"-1]");
			
				}
			}
		}
		
		return tempCodeBuffer.toString();

	}

	private static AbstractToken controlFirstDimensionIsRecord(AbstractToken token) {
		
		try {
			AbstractToken resultToken;
			
			AbstractCommand command=ConvertUtilities.getVariableDefinitinCommand(token);
			
			ElementProgramDataTypeNatural commandElement=null;
			ElementProgramGrupNatural parentCommandElement = null;
			
			AbstractCommand parentCommand=command.getParent();
			
			if(parentCommand instanceof ElementProgramGrupNatural && command instanceof ElementProgramDataTypeNatural){
				commandElement=(ElementProgramDataTypeNatural) command;
				parentCommandElement=(ElementProgramGrupNatural) parentCommand;
			}
			
			resultToken=new KelimeToken(parentCommandElement.getDataName(), parentCommandElement.getSatirNumarasi(), 0, 0);
			
			resultToken.setRecordVariable(true);
			resultToken.setLinkedToken(token);
			
			return resultToken;
		} catch (Exception e) {
			return token;
		}
	}

	private static String addIntCastForArrays() {
		return "(int)";
	}

	private static String toCustomNumberVariableString(AbstractToken token) {
		
		StringBuilder tempCodeBuffer=new StringBuilder();
		
		Double tokenDegerDouble = null;

		Long tokenDegerLong = null;
		
		if(token.getDeger() instanceof Double){
			tokenDegerDouble=(Double) token.getDeger();
			tempCodeBuffer.append((double)token.getDeger());
		}else if(token.getDeger() instanceof Long){
			tokenDegerLong=(Long) token.getDeger();
			if(tokenDegerLong.equals(0)){
				tempCodeBuffer.append("0"); //token.getDeger kullansam 0.0 üretirki bu da long tiplerde compile hatası verir.
			}else{
				tempCodeBuffer.append(token.getDeger()+"l");
			}
		}else{
			tempCodeBuffer.append(token.getDeger());
		}
		
		return tempCodeBuffer.toString();
		 
	}

	// SUBSTR(MAP.YAZPK,1,2) --> FrameworkConvertUtilities.SubstringAlma(TOZLUK.getTcyegiris().toString(),6,10)
	private static String toCustomSubstringVariableString(AbstractToken token) throws Exception {
		
		token.setSubstringCommand(false);
		
		if(token.getLinkedToken()!=null && token.getLinkedToken().isSubstringCommand()){
			
			token.getLinkedToken().setSubstringCommand(false);
			int startIndex=token.getLinkedToken().getSubStringStartIndex();
			int endIndex=startIndex+token.getLinkedToken().getSubStringEndIndex();
			return 	"FCU.substring("+toCustomString(token)+","+startIndex+","+endIndex+")";
			
		}else{
	
			int startIndex=token.getSubStringStartIndex();
			int endIndex=startIndex+token.getSubStringEndIndex();
			return 	"FCU.substring("+toCustomString(token)+","+startIndex+","+endIndex+")";
		}
		
	}
	

	private static String toCustomRecordVariableString(AbstractToken token) throws Exception {
		AbstractCommand variableDefinition;
		
		ElementProgramGrupNatural variableDefinitionGrupNatural = null;
		
		variableDefinition=ConvertUtilities.getVariableDefinitinCommand(token);
		
		System.out.println("test");
		if(variableDefinition instanceof ElementProgramGrupNatural){
			variableDefinitionGrupNatural=(ElementProgramGrupNatural) variableDefinition;
		}
		
		if(variableDefinitionGrupNatural!=null&&variableDefinitionGrupNatural.getArrayLength()!=0){
				return writeArrayOfRecord(variableDefinitionGrupNatural, token);
		}
		else{
			 return writeSimpleRecord(token);
		}
		
	}
	
	
	private static String toCustomRecordVariableSetterString(AbstractToken token) {
		
		AbstractCommand variableDefinition;
		
		ElementProgramGrupNatural variableDefinitionGrupNatural = null;
		
		variableDefinition=ConvertUtilities.getVariableDefinitinCommand(token);
		
		System.out.println("test");
		if(variableDefinition instanceof ElementProgramGrupNatural){
			variableDefinitionGrupNatural=(ElementProgramGrupNatural) variableDefinition;
		}
		
		if(variableDefinitionGrupNatural!=null&&variableDefinitionGrupNatural.getArrayLength()!=0){
				return writeSetterArrayOfRecord(variableDefinitionGrupNatural, token);
		}
		else{
			 return writeSetterSimpleRecord(token);
		}
	}



	private static String toCustomConstantVariableString(AbstractToken token) {
		if(token.getDeger().toString().trim().length()==0){
			return "\""+token.getDeger().toString()+"\"";
		}else{
			return "\""+token.getDeger().toString().trim().toString()+"\"";
		}
		
	}
	
	// IDGIDBS-TGECICI .HSONVALOR : = *DAT4I --> TGECICI.setHSonValor(getSystemVariable(DAT4I));
	//TNAZIM.setUptar(FrameworkConvertUtilities.stringToSqlDate("11111111", "yyyy.MM.dd"));
		private static String toCustomPojoDB2VariableSetterString(AbstractToken token, AbstractToken newValueToken) throws Exception{
			
			String pojosFieldType = "";
			
			String castStr;
			
			if(token.isPojoVariable()){
				
				pojosFieldType=ConvertUtilities.getPojosFieldType(token);
			
			}
			
			StringBuilder setterString=new StringBuilder();
			
			setterString.append(Utility.viewNameToPojoFullSetterName(token));
			
			//setterString.append(Utility.pojoSetterName(token));
			
			setterString.append("(");  //Pojo Starter
			
			
			
			
			
			//cast=JavaWriteUtilities.addCast(token,newValueToken);
			castStr=returnCastString(token, newValueToken);
			logger.debug("1"+setterString.toString());
			
			if(castStr!=null){
				setterString.append(castStr);
			}
			logger.debug("2"+setterString.toString());
			
			setterString.append(JavaWriteUtilities.toCustomString(newValueToken));
			logger.debug("3"+setterString.toString());
			
			if(endCastStr!=null){
				setterString.append(endCastStr);
				endCastStr=null;
			}
			
			if(castStr!=null){
				setterString.append(")");
			}
			
			logger.debug("4"+setterString.toString());
			
			setterString.append(JavaWriteUtilities.returnTypeChangeFunctionToEnd(token,newValueToken));
			logger.debug("5"+setterString.toString());
			
			////NATURAL CODE:1487   :.0 IDGIDBS-TKARTEX .GIRTAR : = *DATN  
			setterString.append(")"); //Pojo Ender
			logger.debug("6"+setterString.toString());
			
			return setterString.toString();
			
			
		}
		
		// IDGIDBS-TGECICI .HSONVALOR : = *DAT4I --> TGECICI.setHSonValor(getSystemVariable(DAT4I));
		//TNAZIM.setUptar(FrameworkConvertUtilities.stringToSqlDate("11111111", "yyyy.MM.dd"));
			private static String toCustomPojoDB2VariableSetterString(AbstractToken token, String newValueStr) throws Exception{
				
				StringBuilder setterString=new StringBuilder();
				
				setterString.append(Utility.viewNameToPojoFullSetterName(token));
				
				setterString.append("(");  //Pojo Starter
				
				setterString.append(newValueStr);
				
				setterString.append(")"); //Pojo Ender
				logger.debug("6"+setterString.toString());
				
				return setterString.toString();
				
				
			}
	
		//NATURAL CODE:2969   :.0 IDGIDBS-TGECICI .HSDGGIRZAM : = YENIZAMAN 
		//TGECICI.setHsdggirzam(FrameworkConvertUtilities.stringToSqlTime(YENIZAMAN));
	private static String toCustomSqlTimeString(AbstractToken newValueToken) throws Exception {
		
			StringBuilder sqlTimeString=new StringBuilder();
		
			sqlTimeString.append("FCU.stringToSqlTime(");
			
			sqlTimeString.append(JavaWriteUtilities.toCustomString(newValueToken)); //"11111111"
			
			sqlTimeString.append(")");
			return sqlTimeString.toString();
		}

	//FrameworkConvertUtilities.stringToSqlDate("11111111", "yyyy.MM.dd")
	private static String toCustomSqlDateString(AbstractToken newValueToken) throws Exception {
			
			StringBuilder sqlTimeString=new StringBuilder();
			
			sqlTimeString.append("FCU.stringToSqlDate(");
			
			sqlTimeString.append(JavaWriteUtilities.toCustomString(newValueToken)); //"11111111"
			
			sqlTimeString.append(",");
			sqlTimeString.append("\"yyyy-MM-dd\")");
			
			return sqlTimeString.toString();
		}


	private static String writeArrayOfRecord(ElementProgramGrupNatural variableDefinitionGrupNatural, AbstractToken token) {
		
		StringBuilder tempCodeBuffer=new StringBuilder();
		
		ArrayToken arrayToken = null;
		AbstractToken firstDimension;
		tempCodeBuffer.append(token.getDeger().toString());  //MAP_DIZISI
		
		logger.debug(token.getLinkedToken().toString());
		if(token.getLinkedToken() instanceof ArrayToken){
			arrayToken=(ArrayToken) token.getLinkedToken();
			firstDimension=arrayToken.getFirstDimension();
			if(firstDimension.getDeger() instanceof String){
				tempCodeBuffer.append("["+ConvertUtilities.castToInt()+arrayToken.getFirstDimension().getDeger()+"-1]");
			}
			else{
				if(arrayToken.getFirstDimension().isKarakter('*')) {
					tempCodeBuffer.append("["+ConvertUtilities.castToInt()+arrayToken.getFirstDimension().getDeger()+"-1]");
				}else {
					
					tempCodeBuffer.append("["+(int)((long)arrayToken.getFirstDimension().getDeger())+"-1]");
				}
			}
			tempCodeBuffer.append(".");
			tempCodeBuffer.append(token.getLinkedToken().getDeger().toString()); //D_SIRA
		}else if(token.getLinkedToken() instanceof KelimeToken){
			tempCodeBuffer.append(".");
			tempCodeBuffer.append(token.getLinkedToken().getDeger().toString()); //D_SIRA
		}else{
			tempCodeBuffer.append(".");
			tempCodeBuffer.append(token.getLinkedToken().getDeger().toString()); //D_SIRA
		}
		
		return tempCodeBuffer.toString();
	}

	private static String writeSetterArrayOfRecord(ElementProgramGrupNatural variableDefinitionGrupNatural,
			AbstractToken token) {
	
		StringBuilder tempCodeBuffer=new StringBuilder();
		
		ArrayToken arrayToken = null;
		AbstractToken firstDimension;
		tempCodeBuffer.append(token.getDeger().toString());  //MAP_DIZISI
		
		logger.debug(token.getLinkedToken().toString());
		if(token.getLinkedToken() instanceof ArrayToken){
			arrayToken=(ArrayToken) token.getLinkedToken();
			firstDimension=arrayToken.getFirstDimension();
			if(firstDimension.getDeger() instanceof String){
				tempCodeBuffer.append("["+ConvertUtilities.castToInt()+arrayToken.getFirstDimension().getDeger()+"-1]");
			}
			else{
				if(arrayToken.getFirstDimension().isKarakter('*')) {
					tempCodeBuffer.append("["+ConvertUtilities.castToInt()+arrayToken.getFirstDimension().getDeger()+"-1]");
				}else {
					
					tempCodeBuffer.append("["+(int)((long)arrayToken.getFirstDimension().getDeger())+"-1]");
				}
			}
			tempCodeBuffer.append(".");
			tempCodeBuffer.append(token.getLinkedToken().getDeger().toString()); //D_SIRA
		}else if(token.getLinkedToken() instanceof KelimeToken){
			tempCodeBuffer.append(".");
			tempCodeBuffer.append(token.getLinkedToken().getDeger().toString()); //D_SIRA
		}else{
			tempCodeBuffer.append(".");
			tempCodeBuffer.append(token.getLinkedToken().getDeger().toString()); //D_SIRA
		}
		
		return tempCodeBuffer.toString();
	}

	
	private static String writeSimpleRecord(AbstractToken token) throws Exception {
		
		StringBuilder tempCodeBuffer=new StringBuilder();
		
		ArrayToken arrayToken = null;
		
		AbstractToken firstDimension;
		
		int firstDimensionSize;
		
		AbstractToken secDimension;
		
		int secDimensionSize;
		
		if(token.isVal() || token.getLinkedToken().isVal()){
			
			String typeOfCopyFrom=ConvertUtilities.getTypeOfVariable(token.getLinkedToken());
			
			if(typeOfCopyFrom.equalsIgnoreCase("string")){
				tempCodeBuffer.append("Long.valueOf(");
				tempCodeBuffer.append(token.getDeger().toString());  //MAP_DIZISI
				tempCodeBuffer.append(".");
				tempCodeBuffer.append(token.getLinkedToken().getDeger().toString()); //D_SIRA
				tempCodeBuffer.append(")");
				return tempCodeBuffer.toString();
			}else{
				
			}
		}
		
		tempCodeBuffer.append(token.getDeger().toString());  //MAP_DIZISI
		tempCodeBuffer.append(".");
		if(token.getLinkedToken().isRedefinedVariable()){
			tempCodeBuffer.append(JavaWriteUtilities.toCustomString(token.getLinkedToken()).toString());
			return tempCodeBuffer.toString();
		}
		tempCodeBuffer.append(token.getLinkedToken().getDeger().toString()); //D_SIRA
		if(token.getLinkedToken().getTip().equals(TokenTipi.Array)){
			arrayToken=(ArrayToken) token.getLinkedToken();
			firstDimension=arrayToken.getFirstDimension();
			secDimension=arrayToken.getSecondDimension();
			if(firstDimension.getDeger() instanceof Integer){
				firstDimensionSize=((int)((long)firstDimension.getDeger()));
				tempCodeBuffer.append("["+addIntCastForArrays()+firstDimensionSize+"-1]");
			}else {
				tempCodeBuffer.append("["+addIntCastForArrays()+firstDimension.getDeger()+"-1]");
			}
			if(secDimension!=null){
				if(secDimension.getDeger() instanceof Integer){
					secDimensionSize=((int)((long)secDimension.getDeger()));
					tempCodeBuffer.append("["+addIntCastForArrays()+secDimensionSize+"-1]");
				}else {
					tempCodeBuffer.append("["+addIntCastForArrays()+secDimension.getDeger()+"-1]");
				}
			}
		}else if(token.getLinkedToken().getTip().equals(TokenTipi.Kelime)){
				//Do nothing
		}else{
			tempCodeBuffer.append(token.getLinkedToken());
		}
		
		return tempCodeBuffer.toString();
	}
	

	private static String writeSetterSimpleRecord(AbstractToken token) {
		
		StringBuilder tempCodeBuffer=new StringBuilder();
		
		ArrayToken arrayToken = null;
		
		AbstractToken firstDimension;
		
		int firstDimensionSize;
		
		AbstractToken secDimension;
		
		int secDimensionSize;
		
		tempCodeBuffer.append(token.getDeger().toString());  //MAP_DIZISI
		tempCodeBuffer.append(".");
		tempCodeBuffer.append(token.getLinkedToken().getDeger().toString()); //D_SIRA
		if(token.getLinkedToken().getTip().equals(TokenTipi.Array)){
			arrayToken=(ArrayToken) token.getLinkedToken();
			firstDimension=arrayToken.getFirstDimension();
			secDimension=arrayToken.getSecondDimension();
			if(firstDimension.getDeger() instanceof Integer){
				firstDimensionSize=((int)((long)firstDimension.getDeger()));
				tempCodeBuffer.append("["+addIntCastForArrays()+firstDimensionSize+"-1]");
			}else {
				tempCodeBuffer.append("["+addIntCastForArrays()+firstDimension.getDeger()+"-1]");
			}
			if(secDimension!=null){
				if(secDimension.getDeger() instanceof Integer){
					secDimensionSize=((int)((long)secDimension.getDeger()));
					tempCodeBuffer.append("["+addIntCastForArrays()+secDimensionSize+"-1]");
				}else {
					tempCodeBuffer.append("["+addIntCastForArrays()+secDimension.getDeger()+"-1]");
				}
			}
		}else if(token.getLinkedToken()!=null && token.getLinkedToken().isRedefinedVariable()){
			tempCodeBuffer.append(".setValue(");
		}else if(token.getLinkedToken().getTip().equals(TokenTipi.Kelime)){
				//Do nothing
		}else{
			tempCodeBuffer.append(token.getLinkedToken());
		}
		
		return tempCodeBuffer.toString();
	}

	
	
	
	
	
	private static String toCustomSystemVariableString(AbstractToken token) {

			StringBuilder tempCodeBuffer = new StringBuilder();
			
			String type=ConvertUtilities.getTypeOfVariable(token);
			if(token.isOneOfKelime("DATD","DATE","DATI","DATJ","DATU","DAT4D","DAT4E","DAT4I","DAT4J","DAT4U")){
				type="String";
			}if(token.getDeger().toString().contains("DATEKRAN")){
				type="String";
			}else if(token.getDeger().toString().equalsIgnoreCase("DATN")){
				type="Long";
			}else if(token.getDeger().toString().equalsIgnoreCase("DATX")){
				type="Date";
			}else if(token.getDeger().toString().equalsIgnoreCase("TIME")
					||token.getDeger().toString().contains("TIMX")){
				type="Time";
			}
			else if(token.getDeger().toString().equals("TIME")
					||token.getDeger().toString().equals("LANGUAGE")
					||token.getDeger().toString().equals("PROGRAM")
					||token.getDeger().toString().contains("COUNTER")
					||token.getDeger().toString().contains("LIBRARY_ID")
					||token.getDeger().toString().contains("GROUP")){
				type="String";
			}
				
			if(token.getDeger().toString().equalsIgnoreCase("PF_KEY")){
				tempCodeBuffer.append("getPF_KEY()");
			}else if(type.equalsIgnoreCase("String")){
				tempCodeBuffer.append("getSystemVariableStr(\""+token.getDeger().toString()+"\")");
			}else if(type.equalsIgnoreCase("Date")){
				tempCodeBuffer.append("getSystemVariableDate(\""+token.getDeger().toString()+"\")");
			}else if(type.equalsIgnoreCase("Time")){
				tempCodeBuffer.append("getSystemVariableTime(\""+token.getDeger().toString()+"\")");
			}else if(type.equalsIgnoreCase("Long")){
				tempCodeBuffer.append("getSystemVariableLong(\""+token.getDeger().toString()+"\")");
			}else{
				tempCodeBuffer.append("getSystemVariable(\""+token.getDeger().toString()+"\")");
			}
		
			return tempCodeBuffer.toString();
	}



	////JavaWriteUtilities.pojosSubTablesArray(copyTo, ddm)  =KET_TAX.getKetTaxAls()
	public static String pojosSubTablesArray(AbstractToken copyTo) throws Exception {
		
		StringBuilder tempCodeBuffer = new StringBuilder();
		
		if(ConversionLogModel.getInstance().isRelationalDatabase()){
			
			tempCodeBuffer.append(Utility.viewAndColumnNameToPojoAndGetterMethodName(copyTo));
			
			return tempCodeBuffer.toString();
		}else{
		
			
			
			DDM ddm= DDMList.getInstance().getFirstLevelDDM(copyTo);
			
			if(ddm==null){
				
				tempCodeBuffer.append(copyTo.getDeger().toString()); //KETTAX;
				
				tempCodeBuffer.append(".");
				
				tempCodeBuffer.append(Utility.viewNameToPojoGetterName(copyTo.getDeger().toString()));
				
				tempCodeBuffer.append("()");
				
				return tempCodeBuffer.toString();
			}
			
			tempCodeBuffer.append(copyTo.getDeger().toString()); //KETTAX;
		
			tempCodeBuffer.append(".");
			
			tempCodeBuffer.append(Utility.viewNameToPojoGetterName(copyTo.getColumnNameToken().getDeger()+"_"+ddm.getDB()+"s"));
			
			tempCodeBuffer.append("()");
			
			return tempCodeBuffer.toString();
		}
		
	}

	public static void endCast(boolean cast) {
		if(endCastStr!=null){
			JavaClassElement.javaCodeBuffer.append(endCastStr);
			endCastStr=null;
		}
		if(cast){
			JavaClassElement.javaCodeBuffer.append(")");
		}
	}

	public  static boolean addCast(AbstractToken copyTo, AbstractToken copyFrom) {
		
		String result=null;
		String typeOfCopyTo=ConvertUtilities.getTypeOfVariable(copyTo);
		
		if(copyFrom.isPojoSubTableCount() ||( copyFrom.getColumnNameToken()!=null && copyFrom.getColumnNameToken().isPojoSubTableCount())){
			return false;
		}
		
		String typeOfCopyFrom=ConvertUtilities.getTypeOfVariable(copyFrom);
		
		if(typeOfCopyTo==null || typeOfCopyFrom==null){
			return false;
		}
		typeOfCopyTo=typeOfCopyTo.toLowerCase();
		typeOfCopyFrom=typeOfCopyFrom.toLowerCase();
		
		result=cast(typeOfCopyTo,typeOfCopyFrom);
		
		if(result!=null  && result.trim().length()>0){
			JavaClassElement.javaCodeBuffer.append(result);
			return true;
		}
		return false;
		
	}

	
	public  static String returnCastString(AbstractToken copyTo, AbstractToken copyFrom) {
		
		String result=null;
		String typeOfCopyTo=ConvertUtilities.getTypeOfVariable(copyTo);
		
		String typeOfCopyFrom=ConvertUtilities.getTypeOfVariable(copyFrom);
		
		if(typeOfCopyTo==null || typeOfCopyFrom==null){
			return null;
		}
		typeOfCopyTo=typeOfCopyTo.toLowerCase();
		typeOfCopyFrom=typeOfCopyFrom.toLowerCase();
		
		
		result=cast(typeOfCopyTo,typeOfCopyFrom);
		
		return result;
		
	}

	private static String cast(String typeOfCopyTo, String typeOfCopyFrom) {
			String result=null;
			//Long-String
			if(typeOfCopyTo.equalsIgnoreCase("long") && typeOfCopyFrom.equalsIgnoreCase("string") ){
				result="Long.valueOf(";
			}else if(typeOfCopyTo.equalsIgnoreCase("string") && typeOfCopyFrom.equalsIgnoreCase("long") ){
					result=" String.valueOf(";
					
				
			// Long --> Bigdecimal 
			}else if(typeOfCopyTo.equalsIgnoreCase("bigdecimal") && typeOfCopyFrom.equalsIgnoreCase("long")){
				result=" BigDecimal.valueOf(";
			}
			
			// Bigdecimal -- String
			else if(typeOfCopyTo.equalsIgnoreCase("bigdecimal") && typeOfCopyFrom.equalsIgnoreCase("string")){
				result=" BigDecimal.valueOf(";
			}
			
			// Date --> Bigdecimal 
			else if(typeOfCopyTo.equalsIgnoreCase("bigdecimal") && typeOfCopyFrom.equalsIgnoreCase("date")){
				result=" FCU.bigDecimalToDate(";
			}
			
			// Bigdecimal -- Date
			else if(typeOfCopyTo.equalsIgnoreCase("date") && typeOfCopyFrom.equalsIgnoreCase("bigdecimal")){
				result=" BigDecimal.valueOf(";
			}
		
			
			//String-Time
			else if(typeOfCopyTo.equalsIgnoreCase("string") && typeOfCopyFrom.equalsIgnoreCase("time")){
				result=" FCU.timeToStringwithFormat(";
			}else if(typeOfCopyTo.equalsIgnoreCase("time") && typeOfCopyFrom.equalsIgnoreCase("string")){
				result=" FCU.stringToSqlTime(";
			}
			
			//String-Date
			else if(typeOfCopyTo.equalsIgnoreCase("date") && typeOfCopyFrom.equalsIgnoreCase("string")){
				result=" FCU.stringToSqlDate(";
				endCastStr=",\"yyyy-MM-dd\"";
			}else if(typeOfCopyTo.equalsIgnoreCase("string") && typeOfCopyFrom.equalsIgnoreCase("date")){
				result=" FCU.dateToStringwithFormat(";
				//endCastStr=",\"yyyy-MM-dd\""; // 16-10-2017 Mevlütün isteği ile. GetDatePojoValue icin yapildi. Veritabanından gelen deger becomesEqualTo ile Stringe atanma durum için yapıldı.
				endCastStr=",\"dd.MM.yyyy\"";
				
			}
			
			//Date-Long
			else if(typeOfCopyTo.equalsIgnoreCase("date") && typeOfCopyFrom.equalsIgnoreCase("long")){
				result=" FCU.stringToSqlDate(";
				endCastStr=",\"yyyy-MM-dd\"";
			}else if(typeOfCopyTo.equalsIgnoreCase("long") && typeOfCopyFrom.equalsIgnoreCase("date")){
				result=" Long.valueOf(FCU.dateToStringwithFormat(";
				endCastStr=",\"yyyyMMdd\")";
			}
			return result;
	}
	
	private static String typeChangeMethod(String typeOfCopyTo, String typeOfCopyFrom) {
		String result=null;
		//Long-String
		
		// Bigdecimal -- Long
		if(typeOfCopyTo.equalsIgnoreCase("long") && typeOfCopyFrom.equalsIgnoreCase("bigdecimal")){
			result=".longValue()";
		}
		
		// Bigdecimal -- String
		else if(typeOfCopyTo.equalsIgnoreCase("string") && typeOfCopyFrom.equalsIgnoreCase("bigdecimal")){
			result=".toPlainString()";
		}
	
		return result;
}

	//Sonuna ... şeklinde method ekler. Örnek .longValue()
	public static void addTypeChangeFunctionToEnd(AbstractToken copyTo, AbstractToken copyFrom) {
		
		String result=null;
		String typeOfCopyTo=ConvertUtilities.getTypeOfVariable(copyTo);
		
		String typeOfCopyFrom=ConvertUtilities.getTypeOfVariable(copyFrom);
		
		if(typeOfCopyTo==null || typeOfCopyFrom==null){
			return;
		}
		typeOfCopyTo=typeOfCopyTo.toLowerCase();
		typeOfCopyFrom=typeOfCopyFrom.toLowerCase();
		
		result=typeChangeMethod(typeOfCopyTo,typeOfCopyFrom);
		
		if(result!=null  && result.trim().length()>0){
			JavaClassElement.javaCodeBuffer.append(result);
			return;
		}
		return;
		
	}
	
	//Sonuna ... şeklinde method ekler. Örnek .longValue()
		private static String returnTypeChangeFunctionToEnd(AbstractToken copyTo, AbstractToken copyFrom) {
			
			String result=null;
			String typeOfCopyTo=ConvertUtilities.getTypeOfVariable(copyTo);
			
			String typeOfCopyFrom=ConvertUtilities.getTypeOfVariable(copyFrom);
			
			if(typeOfCopyTo==null || typeOfCopyFrom==null){
				return "";
			}
			typeOfCopyTo=typeOfCopyTo.toLowerCase();
			typeOfCopyFrom=typeOfCopyFrom.toLowerCase();
			
			result=typeChangeMethod(typeOfCopyTo,typeOfCopyFrom);
			
			if(result!=null  && result.trim().length()>0){
				return result;
			}
			return "";
			
		}

		// IDGIDBS-TOZLUK.MESLEKID --> TOZLUK.getMeslekId()
		private static String toCustomPojoDB2VariableString(AbstractToken token) throws NoSuchMethodException, SecurityException {
			
			/*String getterString;//TESKI;
			getterString= "getPojoValue("+"\""+token.getDeger().toString();
		
			if(token.getColumnNameToken()!=null){
				getterString +=".";
				
				getterString +=Utility.viewNameToPojoGetterName(token.getColumnNameToken().getDeger().toString());
			}
			getterString +="()"+"\""+")";
			return getterString;*/
			
			
			StringBuilder getterString=new StringBuilder();
			
			String columnReturnType=Utility.findViewAndColumnNamesReturnTypeRelationalDB(token);
			
			if(columnReturnType.toLowerCase().equals("long")){
				
				getterString.append("getLongPojoValue(\"");
				
			}else if(columnReturnType.toLowerCase().equals("int")){  //Pojo da int olmamali ama varsada Long diye cekmeli
				
				getterString.append("getLongPojoValue(\"");
				
			}else if(columnReturnType.toLowerCase().equals("bigdecimal")){
				
				getterString.append("getBigDecimalPojoValue(\"");
				
			}else if(columnReturnType.toLowerCase().equals("date")){
				
				getterString.append("getDatePojoValue(\"");
				
			}else if(columnReturnType.toLowerCase().equals("time")){
				
				getterString.append("getTimePojoValue(\"");
				
			}else{
				getterString.append("getStringPojoValue(\"");
			}
			getterString.append(Utility.viewAndColumnNameToPojoAndGetterMethodName(token));
			
			getterString.append("\")");
			return getterString.toString();
			
			
		}

		/**
		 *      1  A8  TAX-DOM-INT                      A    1  N
			  M 1  A9  TAX-DETAIL                       A   40  N
			    1  AA  TAX-EFF-DATE                     N  6.0  N
			    1  AB  TAX-EXP-DATE                     N  6.0  N
			  P 1  AC  TAX-EXEMPT-PER
			    2  AD  TAX-EXEMPT-CODE                  A    6  N
			  M 2  AE  TAX-EXEMPT-DEFINITION            A   60  N
			    2  AF  TAX-EXEMPT-EFF                   N  6.0  N
			    2  AG  TAX-EXEMPT-EXP                   N  6.0  N
			    1  AH  TAX-RATE                         P  1.4  N
			  M 1  AI  TAX-PAX-DSCR                     A    6  N D
			  M 1  AJ  TAX-SECURITY                     A   19  N
		 * @param token
		 * @return
		 * 
		 * Rules:   
		 * 		1)	Boş ve  1  se bir normal
		 * 					1  A8  TAX-DOM-INT --> KetTax.getTaxDomInt()
		 * 		2)	M ve  1 se 
		 * 					 M 1  A9  TAX-DETAIL  -->  KetTax.getKetTaxA9s().get(i).getTaxDetail()
		 * 		3)	P VE  1 se count içindir.
		 * 					 P 1  AC  TAX-EXEMPT-PER  -->KetTax.getKetTaxAc().size();
		 * 		4)	Boş ve 2 ise periodic altindadir.
		 * 					DDM KET-DOMESTIC-TAX
		 * 				  	P 1  AH  ITX-APPLIED
	    					  2  AI  ITX-CITIES  
		 * 										--> KetDomesticTax.getKetTaxAh().get(i).getItxCities()
		 * 		5) 	M  ve  2 ise 
		 * 					KET-NOTE-ATPCO
		 * 				
						  P 1  A8  NOT-CODE-PERIODIC
						  M 2  A9  NOT-CODE                         A    7  N
		 * 						
		 * 						-->KetNoteATPCO.getKetNoteAtpcoA8().get(i).getKetNoteAtpcoA9().get(i).getNotCode();
		 * @throws Exception 
		 */
		private static String toCustomPojoVariableString(AbstractToken token) throws Exception {
			
			try {
				if(token.isColumnRedefiner()){
					return toCustomRedefinedColumnVariableString(token);
				}
				
				DDM ddm=DDMList.getInstance().getDDM(token);
				if(ddm==null){
					return ruleEmtpy_1(ddm, token);
				}
				if(token.getDeger().equals(token.getColumnNameToken().getDeger())){
					token.setColumnNameToken(token.getLinkedToken());
				}
				if(((ddm.getT().isEmpty()) && ddm.getL().equals("1")) || (ddm.getT().equals("G")&& ddm.getL().equals("1"))){
					return ruleEmtpy_1(ddm, token);
				}else if(ddm.getT().equals("M")&& ddm.getL().equals("1")){
					return ruleM_1(ddm, token);
				}else if(ddm.getT().equals("P")&& ddm.getL().equals("1")){
					return ruleP_1(ddm , token);
				}else if(ddm.getT().isEmpty()&& ddm.getL().equals("2")){
					return ruleEmpty_2(ddm ,token);
				}else if(ddm.getT().equals("M")&& ddm.getL().equals("2")){
					return ruleM_2(ddm,token);
				}
			} catch (Exception e) {
				logger.debug(e.getMessage(),e);
				return "getPojoValue()";
			}
		
			throw new Exception(token.getDeger().toString()+" DDM bulunamadi.");
		}

		//*S**    MUL-PREFIX := 'TTOP:KCKGRP'
		private static String toCustomRedefinedColumnVariableString(AbstractToken token) throws Exception {
			
			String key=token.getDeger().toString()+"."+token.getColumnNameToken().getRedefinedColumn().getColumn().toString();
			
			DDM ddm=DDMList.getInstance().getDDMByKey(key,token);
			
			if(ddm.getT().isEmpty()&& ddm.getL().equals("1")){
				return ruleEmtpy_1_RedefinedColumn(ddm, token);
			}else if(ddm.getT().equals("M")&& ddm.getL().equals("1")){
				return ruleM_1(ddm, token);
			}else if(ddm.getT().equals("P")&& ddm.getL().equals("1")){
				return ruleP_1(ddm , token);
			}else if(ddm.getT().isEmpty()&& ddm.getL().equals("2")){
				return ruleEmpty_2(ddm ,token);
			}else if(ddm.getT().equals("M")&& ddm.getL().equals("2")){
				return ruleM_2(ddm,token);
			}
			
			throw new Exception(token.getDeger().toString()+"DDM bulunamadi.");
			
		}
		
		//*S**    MUL-PREFIX := 'TTOP:KCKGRP'
		private static String toCustomRedefinedColumnVariableSetterString(AbstractToken token) throws Exception {
			
			String key=token.getDeger().toString()+"."+token.getColumnNameToken().getRedefinedColumn().getColumn().toString();
			
			DDM ddm=DDMList.getInstance().getDDMByKey(key,token);
			
			if(ddm.getT().isEmpty()&& ddm.getL().equals("1")){
				return ruleEmtpy_1_RedefinedColumnSetter(ddm, token);
			}else if(ddm.getT().equals("M")&& ddm.getL().equals("1")){
				return ruleM_1_RedefinedColumnSetter(ddm, token);
			}else if(ddm.getT().equals("P")&& ddm.getL().equals("1")){
				return ruleP_1_RedefinedColumnSetter(ddm , token);
			}else if(ddm.getT().isEmpty()&& ddm.getL().equals("2")){
				return ruleEmpty_2_RedefinedColumnSetter(ddm ,token);
			}else if(ddm.getT().equals("M")&& ddm.getL().equals("2")){
				return ruleM_2_RedefinedColumnSetter(ddm,token);
			}
			
			throw new Exception(token.getDeger().toString()+"DDM bulunamadi.");
		}



		private static String toCustomPojoVariableSetterString(AbstractToken token) throws Exception {
			
			if(token.isColumnRedefiner()){
				return toCustomRedefinedColumnVariableSetterString(token);
			}
			
			DDM ddm=DDMList.getInstance().getDDM(token);
			if(ddm==null){
				return ruleEmtpy_1_setter(ddm, token);
			}
			
			if(ddm.getT().isEmpty()&& ddm.getL().equals("1")){
				return ruleEmtpy_1_setter(ddm, token);
			}else if(ddm.getT().equals("M")&& ddm.getL().equals("1")){
				return ruleM_1_setter(ddm, token);
			}else if(ddm.getT().equals("P")&& ddm.getL().equals("1")){
				return ruleP_1_setter(ddm , token);
			}else if(ddm.getT().isEmpty()&& ddm.getL().equals("2")){
				return ruleEmpty_2_setter(ddm ,token);
			}else if(ddm.getT().equals("M")&& ddm.getL().equals("2")){
				return ruleM_2_setter(ddm,token);
			}
			
			throw new Exception(token.getDeger().toString()+"DDM bulunamadi.");
		}



}
