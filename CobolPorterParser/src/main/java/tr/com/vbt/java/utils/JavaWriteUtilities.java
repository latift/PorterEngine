package tr.com.vbt.java.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

public class JavaWriteUtilities {
	
	final static Logger logger = LoggerFactory.getLogger(JavaWriteUtilities.class);
	
	public static String endCastStr=null;
	
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
			
			
		}else{
			
			tempCodeBuffer.append(toCustomDefaultVariableString(token));
			
		}
		
		return tempCodeBuffer;
	}

	public static StringBuilder toCustomSetterString(AbstractToken token, AbstractToken newValueToken) throws Exception {
		
		StringBuilder tempCodeBuffer=new StringBuilder();
		
		if(token.getTip().equals(TokenTipi.SatirBasi)){ 
			//Do nothing;
		}else if(token.isRedefinedVariable()){ 
			
			tempCodeBuffer.append(toCustomRedefinedVariableSetterString(token, newValueToken));
			
		}else if(token.getTip().equals(TokenTipi.Karakter)){
		
		}else if(token.isPojoVariable() && ConversionLogModel.getInstance().isMB()){ //MB
				
				// IDGIDBS-TGECICI .HSONVALOR : = *DAT4I  --> TGECICI.setHSONVALOR(getSystemVAriable(DAT4I));
				tempCodeBuffer.append(toCustomPojoDB2VariableSetterString(token, newValueToken));
			
		}else if(token.isPojoVariable()){ //MB
			
			// IDGIDBS-TGECICI .HSONVALOR : = *DAT4I  --> TGECICI.setHSONVALOR(getSystemVAriable(DAT4I));
			tempCodeBuffer.append(toCustomPojoVariableSetterString(token));
			
		}else if(token.isSystemVariable()){
	
			tempCodeBuffer.append(toCustomSystemVariableString(token));
			
		}else if(token.isConstantVariableWithQuota()){		
		
			
		}else if(token.isRecordVariable()){
				
		
		}else if(token.isSubstringCommand()){
			
		
		}else if(token.isEdited()){ //DVANATOPMEB(DOVIZGEC) -->DVANATOPMEB[DOVIZGEC]
			
			
		}else if(token.getTip().equals(TokenTipi.Sayi)){
			
			
		}else if(token.getTip().equals(TokenTipi.Array)){ //DVANATOPMEB(DOVIZGEC) -->DVANATOPMEB[DOVIZGEC]
			
			
		}else{
			
			tempCodeBuffer.append(toCustomDefaultVariableString(token));
			
		}
		
		return tempCodeBuffer;
	}

	private static Object toCustomRedefinedVariableSetterString(AbstractToken token, AbstractToken newValueToken) {
		StringBuffer sb=new StringBuffer();
		sb.append(token.getDeger().toString()+".setValue(");
		
		sb.append(newValueToken.toCustomString());
		
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
			
			if(ConversionLogModel.getInstance().isMB()){
			
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
		
		return token.getDeger().toString()+".setValue";
	
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
			
				firstDimensionSize=((int)firstDimension.getDeger());
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
				
					secDimensionSize=((int)secDimension.getDeger());
			
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
				tempCodeBuffer.append(token.getDeger());
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
		return "\""+token.getDeger().toString().trim().toString()+"\"";
		
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
			
			setterString.append("(");
			
			//cast=JavaWriteUtilities.addCast(token,newValueToken);
			castStr=returnCastString(token, newValueToken);
			
			if(castStr!=null){
				setterString.append(castStr);
			}
			
			setterString.append(JavaWriteUtilities.toCustomString(newValueToken));
			
			
			if(castStr!=null){
				setterString.append(")");
				castStr=null;
			}
			
			setterString.append(JavaWriteUtilities.returnTypeChangeFunctionToEnd(token,newValueToken));
			
			setterString.append(")");
			
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
		
		String columnReturnType=Utility.findViewAndColumnNamesReturnType(token);
		
	if(columnReturnType.toLowerCase().equals("long")){
			
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
	

/*1)	Boş ve  1  se bir normal
	 * 					1  A8  TAX-DOM-INT --> KetTax.getTaxDomInt()*/
	private static String ruleEmtpy_1(DDM ddm, AbstractToken token) {
		
		String getterString;//TESKI;
			getterString= "getPojoValue("+"\""+token.getDeger().toString();

		getterString +=".";
		if(token.getColumnNameToken()==null){
			getterString +=Utility.viewNameToPojoGetterName(token.getDeger().toString());
		}else{
			getterString +=Utility.viewNameToPojoGetterName(token.getColumnNameToken().getDeger().toString());
		}
		getterString +="()"+"\""+")";
		return getterString;

	}
	
	/*1)	Boş ve  1  se bir normal
	 * 
	 *	tableColumnReferans.put("MUL_PREFIX","KET_MULTIFILE");
		RedefinedColumn mulFrefix=new RedefinedColumn("KET_KCKGRP_EXC","KET_MULTFILE","MUL_TIMESTAMP","MUL_PREFIX",0,13,"A");
		tableColumnRedefiners.put("MUL_PREFIX", mulFrefix);
	
	 * 					1  A8  MUL-PREFIX --> KET_MULTIFILE.getMultimestamp().substring(0,13);*/
	private static String ruleEmtpy_1_RedefinedColumn(DDM ddm, AbstractToken token) {
		
		RedefinedColumn rdfColumn=token.getColumnNameToken().getRedefinedColumn();
		
		String getterString;//TESKI;
			getterString= "getPojoValue("+"\""+token.getDeger().toString();
		
		getterString +=".";
		if(rdfColumn.getColumn()==null){
	
			getterString +=Utility.viewNameToPojoGetterName(rdfColumn.toString());
			
		}else{
			
			getterString +=Utility.viewNameToPojoGetterName(rdfColumn.getColumn().toString());
			
		}
		getterString +="()";
		if(rdfColumn.getStartIndex()!=-1){
			getterString +=".substring(";
			getterString +=rdfColumn.getStartIndex()+","+rdfColumn.getEndIndex();
			getterString +=")"+"\""+")";
		}
		
		return getterString;

	}
	
	/*1)	Boş ve  1  se bir normal
	 * 					1  A8  TAX-DOM-INT --> KetTax.setTaxDomInt()*/
	private static String ruleEmtpy_1_setter(DDM ddm, AbstractToken token) {
		
		StringBuilder setterString=new StringBuilder();//TESKI;
		setterString.append(token.getDeger().toString());

		setterString.append(".");
		if(token.getColumnNameToken()==null){
			setterString.append(Utility.viewNameToPojoSetterName(token.getDeger().toString()));
		}else{
			setterString.append(Utility.viewNameToPojoSetterName(token.getColumnNameToken().getDeger().toString()));
		}
		setterString.append("(");
		return setterString.toString();

	}
	
	/*1)	Boş ve  1  se bir normal
	 * 					1  A8  TAX-DOM-INT --> KetTax.setTaxDomInt()*/
	private static String ruleEmtpy_1_RedefinedColumnSetter(DDM ddm, AbstractToken token) {
		
		String setterString;
			setterString= "getPojoValue("+"\""+token.getDeger().toString();
		setterString +=".";
		if(token.getColumnNameToken()==null){
			setterString +=Utility.viewNameToPojoSetterName(token.getDeger().toString())+"\""+")";
		}else{
			setterString +=Utility.viewNameToPojoSetterName(token.getColumnNameToken().getDeger().toString())+"\""+")";
		}
		return setterString;

	}

	/*
	 * 	 * 		2)	M ve  1 se 
	 * 					 M 1  A9  TAX-DETAIL  -->  KetTax.getKetTaxA9s().get(i).getTaxDetail()
	 */							 
	private static String ruleM_1(DDM ddm, AbstractToken token) throws Exception {
		
		// token.getDeger() = KETTAX
		//columnt.getDeger() ==TAX_DETAIL
		String getterString;//TESKI;
			getterString= "getPojoValue("+"\""+token.getDeger().toString();
		getterString +=".";
		if(token.getColumnNameToken()==null){
			getterString +=Utility.viewNameToPojoGetterName(token.getDeger().toString());
		}else{
			getterString +=Utility.viewNameToPojoGetterName(token.getColumnNameToken().getDeger().toString());
		};
		getterString +="s()";		//getKetTaxA9s()
		getterString +=".";
		if(token.getPojosDimension()==null){
			getterString +="get("+JavaWriteUtilities.toCustomString(token)+"-1)";
		}else{
			getterString +="get("+JavaWriteUtilities.toCustomString(token.getPojosDimension())+"-1)";
		}
		getterString +=".";
		getterString +=Utility.viewNameToPojoGetterName(token.getColumnNameToken().getDeger().toString());
		getterString +="()"+"\""+")";
		return getterString;
	}
	
	/*
	 * 	 * 		2)	M ve  1 se 
	 * 					 M 1  A9  TAX-DETAIL  -->  KetTax.getKetTaxA9s().get(i).getTaxDetail()
	 */							 
	private static String ruleM_1_RedefinedColumn(DDM ddm, AbstractToken token) throws Exception {
		
		// token.getDeger() = KETTAX
		//columnt.getDeger() ==TAX_DETAIL
		String getterString;//TESKI;
			getterString= "getPojoValue("+"\""+token.getDeger().toString();
		getterString +=".";
		getterString +="get"+token.getDeger().toString()+ddm.getDB()+"s()";		//getKetTaxA9s()
		getterString +=".";
		getterString +="get("+JavaWriteUtilities.toCustomString(token.getPojosDimension())+"-1)";
		getterString +=".";
		getterString +=Utility.viewNameToPojoGetterName(token.getColumnNameToken().getDeger().toString());
		getterString +="()"+"\""+")";
		return getterString;
	}

	private static String ruleM_1_setter(DDM ddm, AbstractToken token) throws Exception {
		
		String getterString;//TESKI;
			getterString= "getPojoValue("+"\""+token.getDeger().toString();
		getterString +=".";
		getterString +="get"+token.getDeger().toString()+ddm.getDB()+"s()";		//getKetTaxA9s()
		getterString +=".";
		getterString +="get("+JavaWriteUtilities.toCustomString(token.getPojosDimension())+"-1)";
		getterString +=".";
		getterString +=Utility.viewNameToPojoSetterName(token.getColumnNameToken().getDeger().toString())+"\""+")";
		return getterString;
	}
	
	
	private static String ruleM_1_RedefinedColumnSetter(DDM ddm, AbstractToken token) throws Exception {
		
		String getterString;//TESKI;
			getterString= "getPojoValue("+"\""+token.getDeger().toString();
		getterString +=".";
		getterString +="get"+token.getDeger().toString()+ddm.getDB()+"s()";		//getKetTaxA9s()
		getterString +=".";
		getterString +="get("+JavaWriteUtilities.toCustomString(token.getPojosDimension())+"-1)";
		getterString +=".";
		getterString +=Utility.viewNameToPojoSetterName(token.getColumnNameToken().getDeger().toString())+"\""+")";
		return getterString;
	}

	/*
	 * 	 * 		3)	P VE  1 se count içindir.
	 * 					 P 1  AC  TAX-EXEMPT-PER  -->KetTax.getKetTaxAc().size();
	 */
	private static String ruleP_1(DDM ddm, AbstractToken token) {
		String getterString;//TESKI;
			getterString= "getPojoValue("+"\""+token.getDeger().toString();
		getterString +=".";
		getterString +="get"+token.getDeger().toString()+ddm.getFirstLevelDDM().getDB()+"s()";		//getKetTaxA9s()
		getterString +=".";
		getterString +="size()"+"\""+")";
		return getterString;
	}
	
	/*
	 * 	 * 		3)	P VE  1 se count içindir.
	 * 					 P 1  AC  TAX-EXEMPT-PER  -->KetTax.getKetTaxAc().size();
	 */
	private static String ruleP_1_RedefinedColumn(DDM ddm, AbstractToken token) {
		String getterString;//TESKI;
			getterString= "getPojoValue("+"\""+token.getDeger().toString();
		getterString +=".";
		getterString +="get"+token.getDeger().toString()+ddm.getFirstLevelDDM().getDB()+"s()";		//getKetTaxA9s()
		getterString +=".";
		getterString +="size()"+"\""+")";
		return getterString;
	}
	
	private static String ruleP_1_setter(DDM ddm, AbstractToken token) throws Exception {
		throw new Exception("Unimplemented Code");
	}
	
	private static String ruleP_1_RedefinedColumnSetter(DDM ddm, AbstractToken token) throws Exception {
		throw new Exception("Unimplemented Code");
	}

	/*4)Boş ve 2 ise periodic altindadir.
	 * 					DDM KET-DOMESTIC-TAX
	 * 				  	P 1  AH  ITX-APPLIED
    					  2  AI  ITX-CITIES  
	 * 										--> KetDomesticTax.getKetTaxAh().get(i).getItxCities()*/	 
	private static String ruleEmpty_2(DDM ddm, AbstractToken token) throws Exception {
		String getterString;//TESKI;
			getterString="getPojoValue("+"\""+ token.getDeger().toString();
		getterString +=".";
		//getterString +="get"+token.getDeger().toString()+Utility.viewNameToPojoGetterName(ddm.getFirstLevelDDM().getDB())+"s()";		//getKetTaxA9s()
		getterString +=Utility.viewNameToPojoGetterName(token.getDeger()+"_"+ddm.getFirstLevelDDM().getDB()+"s");
		getterString +="()";
		getterString +=".";
		if(token.getPojosDimension()!=null){
			getterString +="get("+JavaWriteUtilities.toCustomString(token.getPojosDimension())+"-1)";
		}else{
			getterString +="get()";
		}
		getterString +=".";
		getterString +=Utility.viewNameToPojoGetterName(token.getColumnNameToken().getDeger().toString());
		getterString +="()"+"\""+")";
		return getterString;
	}
	
	private static String ruleEmpty_2_RedefinedColumn(DDM ddm, AbstractToken token) throws Exception {
		String getterString;//TESKI;
			getterString= "getPojoValue("+"\""+token.getDeger().toString();
		getterString +=".";
		//getterString +="get"+token.getDeger().toString()+Utility.viewNameToPojoGetterName(ddm.getFirstLevelDDM().getDB())+"s()";		//getKetTaxA9s()
		getterString +=Utility.viewNameToPojoGetterName(token.getDeger()+"_"+ddm.getFirstLevelDDM().getDB()+"s");
		getterString +="()";
		getterString +=".";
		getterString +="get("+JavaWriteUtilities.toCustomString(token.getPojosDimension())+"-1)";
		getterString +=".";
		getterString +=Utility.viewNameToPojoGetterName(token.getColumnNameToken().getDeger().toString());
		getterString +="()"+"\""+")";
		return getterString;
	}
	
	//     2  AD  TAX-EXEMPT-CODE   --> KET_TAX.getKetTaxAcs().get(i-1).getTaxExemptCode()
	private static String ruleEmpty_2_setter(DDM ddm, AbstractToken token) throws Exception {
		String getterString;//TESKI;
			getterString= "\""+token.getDeger().toString();
		getterString +=".";
		//getterString +="get"+token.getDeger().toString()+Utility.viewNameToPojoGetterName(ddm.getFirstLevelDDM().getDB())+"s()";		//getKetTaxA9s()
		getterString +=Utility.viewNameToPojoGetterName(token.getDeger()+"_"+ddm.getFirstLevelDDM().getDB()+"s");
		getterString +="()";
		getterString +=".";
		getterString +="get("+JavaWriteUtilities.toCustomString(token.getPojosDimension())+"-1)";
		getterString +=".";
		getterString +=Utility.viewNameToPojoSetterName(token.getColumnNameToken().getDeger().toString())+"\"";
		return getterString;
	}
	
	//     2  AD  TAX-EXEMPT-CODE   --> KET_TAX.getKetTaxAcs().get(i-1).getTaxExemptCode()
	private static String ruleEmpty_2_RedefinedColumnSetter(DDM ddm, AbstractToken token) throws Exception {
		String getterString;//TESKI;
			getterString= "getPojoValue("+"\""+token.getDeger().toString();
		getterString +=".";
		//getterString +="get"+token.getDeger().toString()+Utility.viewNameToPojoGetterName(ddm.getFirstLevelDDM().getDB())+"s()";		//getKetTaxA9s()
		getterString +=Utility.viewNameToPojoGetterName(token.getDeger()+"_"+ddm.getFirstLevelDDM().getDB()+"s");
		getterString +="()";
		getterString +=".";
		getterString +="get("+JavaWriteUtilities.toCustomString(token.getPojosDimension())+"-1)";
		getterString +=".";
		getterString +=Utility.viewNameToPojoSetterName(token.getColumnNameToken().getDeger().toString())+"\""+")";
		return getterString;
	}


	/**
	 * 5) 	M  ve  2 ise 
	 * 					KET-NOTE-ATPCO
	 * 				
					  P 1  A8  NOT-CODE-PERIODIC
					  M 2  A9  NOT-CODE                         A    7  N
	 * 						
	 * 						-->KetNoteATPCO.getKetNoteAtpcoA8().get(i).getKetNoteAtpcoA9().get(i).getNotCode();
	 * @throws Exception 

	 */
	private static String ruleM_2(DDM ddm, AbstractToken token) throws Exception {
		
		String getterString;//TESKI;
			getterString= "getPojoValue("+"\""+token.getDeger().toString();
		getterString +=".";
		getterString +="get"+token.getDeger().toString()+ddm.getFirstLevelDDM().getDB()+"s()";		//getKetTaxA9s()
		getterString +=".";
		getterString +="get("+JavaWriteUtilities.toCustomString(token.getPojosDimension())+"-1)";
		getterString +=".";
		getterString +="get"+token.getDeger().toString()+ddm.getDB()+"s()";		//getKetTaxA9s()
		getterString +=".";
		getterString +="get(i)";
		getterString +=".";
		getterString +=Utility.viewNameToPojoGetterName(token.getColumnNameToken().getDeger().toString());
		getterString +="()"+"\""+")";
		return getterString;
	}
	
	private static String ruleM_2_setter(DDM ddm, AbstractToken token) throws Exception {
		
		String getterString;//TESKI;
			getterString="getPojoValue("+ "\""+token.getDeger().toString();
		getterString +=".";
		getterString +="get"+token.getDeger().toString()+ddm.getFirstLevelDDM().getDB()+"s()";		//getKetTaxA9s()
		getterString +=".";
		getterString +="get("+JavaWriteUtilities.toCustomString(token.getPojosDimension())+"-1)";
		getterString +=".";
		getterString +="get"+token.getDeger().toString()+ddm.getDB()+"s()";		//getKetTaxA9s()
		getterString +=".";
		getterString +="get(i)";
		getterString +=".";
		getterString +=Utility.viewNameToPojoSetterName(token.getColumnNameToken().getDeger().toString())+"\""+")";
		return getterString;
	}

	private static String ruleM_2_RedefinedColumnSetter(DDM ddm, AbstractToken token) throws Exception {
		
		String getterString;//TESKI;
			getterString= "getPojoValue("+"\""+token.getDeger().toString();
		getterString +=".";
		getterString +="get"+token.getDeger().toString()+ddm.getFirstLevelDDM().getDB()+"s()";		//getKetTaxA9s()
		getterString +=".";
		getterString +="get("+JavaWriteUtilities.toCustomString(token.getPojosDimension())+"-1)";
		getterString +=".";
		getterString +="get"+token.getDeger().toString()+ddm.getDB()+"s()";		//getKetTaxA9s()
		getterString +=".";
		getterString +="get(i)";
		getterString +=".";
		getterString +=Utility.viewNameToPojoSetterName(token.getColumnNameToken().getDeger().toString())+"\""+")";
		return getterString;
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
					
					tempCodeBuffer.append("["+(int)arrayToken.getFirstDimension().getDeger()+"-1]");
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
					
					tempCodeBuffer.append("["+(int)arrayToken.getFirstDimension().getDeger()+"-1]");
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
				firstDimensionSize=((int)firstDimension.getDeger());
				tempCodeBuffer.append("["+addIntCastForArrays()+firstDimensionSize+"-1]");
			}else {
				tempCodeBuffer.append("["+addIntCastForArrays()+firstDimension.getDeger()+"-1]");
			}
			if(secDimension!=null){
				if(secDimension.getDeger() instanceof Integer){
					secDimensionSize=((int)secDimension.getDeger());
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
				firstDimensionSize=((int)firstDimension.getDeger());
				tempCodeBuffer.append("["+addIntCastForArrays()+firstDimensionSize+"-1]");
			}else {
				tempCodeBuffer.append("["+addIntCastForArrays()+firstDimension.getDeger()+"-1]");
			}
			if(secDimension!=null){
				if(secDimension.getDeger() instanceof Integer){
					secDimensionSize=((int)secDimension.getDeger());
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

	
	
	
	
	
	private static String toCustomSystemVariableString(AbstractToken token) {

			StringBuilder tempCodeBuffer = new StringBuilder();
			
			String type=ConvertUtilities.getTypeOfVariable(token);
			if(token.getDeger().toString().equalsIgnoreCase("DAT4I")){
				type="Date";
			}else if(token.getDeger().toString().equalsIgnoreCase("TIME")){
				type="Time";
			}
			else if(token.getDeger().toString().equals("TIME")
					||token.getDeger().toString().equals("TIMX")
					||token.getDeger().toString().equals("LANGUAGE")
					||token.getDeger().toString().equals("PROGRAM")
					||token.getDeger().toString().contains("DATEKRAN")
					||token.getDeger().toString().contains("DAT")
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
			}else{
				tempCodeBuffer.append("getSystemVariable(\""+token.getDeger().toString()+"\")");
			}
		
			return tempCodeBuffer.toString();
	}



	////JavaWriteUtilities.pojosSubTablesArray(copyTo, ddm)  =KET_TAX.getKetTaxAls()
	public static String pojosSubTablesArray(AbstractToken copyTo) {
		
		StringBuilder tempCodeBuffer = new StringBuilder();
		
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
		
		tempCodeBuffer.append(Utility.viewNameToPojoGetterName(copyTo.getDeger()+"_"+ddm.getDB()+"s"));
		
		tempCodeBuffer.append("()");
		
		return tempCodeBuffer.toString();
		
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
					
				
			// Bigdecimal -- Long
			}else if(typeOfCopyTo.equalsIgnoreCase("bigdecimal") && typeOfCopyFrom.equalsIgnoreCase("long")){
				result=" BigDecimal.valueOf(";
			}
			
			// Bigdecimal -- String
			else if(typeOfCopyTo.equalsIgnoreCase("bigdecimal") && typeOfCopyFrom.equalsIgnoreCase("string")){
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
				result=" FCU.stringToDate(";
				endCastStr=",\"dd.MM.yyyy\"";
			}else if(typeOfCopyTo.equalsIgnoreCase("string") && typeOfCopyFrom.equalsIgnoreCase("date")){
				result=" FCU.dateToStringwithFormat(";
				endCastStr=",\"dd.MM.yyyy\"";
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
		public static String returnTypeChangeFunctionToEnd(AbstractToken copyTo, AbstractToken copyFrom) {
			
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




}
