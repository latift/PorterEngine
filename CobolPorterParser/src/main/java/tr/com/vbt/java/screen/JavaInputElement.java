package tr.com.vbt.java.screen;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tr.com.vbt.java.AbstractJavaElement;
import tr.com.vbt.java.general.JavaClassElement;
import tr.com.vbt.java.general.JavaConstants;
import tr.com.vbt.java.utils.ConvertUtilities;
import tr.com.vbt.java.utils.JavaWriteUtilities;
import tr.com.vbt.java.utils.VariableTypes;
import tr.com.vbt.lexer.ConversionFileType;
import tr.com.vbt.lexer.ConversionLogModel;
import tr.com.vbt.natural.html.IOModeType;
import tr.com.vbt.natural.html.NaturalTagTypes;
import tr.com.vbt.natural.html.ScreenIO;
import tr.com.vbt.natural.html.ScreenIOIntegerInput;
import tr.com.vbt.natural.html.ScreenIOLabel;
import tr.com.vbt.natural.html.ScreenIOStringInput;
import tr.com.vbt.natural.html.ScreenIOUndefined;
import tr.com.vbt.natural.html.XCoordinationTypes;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.ArrayToken;
import tr.com.vbt.token.KelimeToken;
import tr.com.vbt.token.TokenTipi;
import tr.com.vbt.util.ConverterConfiguration;

/**
 * *S** INPUT S** (AD=MI'.' ZP=OFF IP=OFF SG=OFF) 01/65 *DAT4E S** 03/20 ' FMM
 * MESAJ LISTELEME ' S** /20X '-------------------------' S** //20X 'KALKIS
 * LIMANI....:' #T-KLIM S** /20X 'VARIS LIMANI.....:' #T-VLIM S** /20X
 * 'BASLANGIC TARIH..:' #T-TARIH 2X '(YYYYMMDD)' S** /20X 'DOWNLOAD.........:'
 *
 ** S**INPUT (AD=MI'.') S** 2/01 ' +----------------------------------+' S** / '
 * | T P S S I S T E M I |' S** / ' +----------------------------------+' S** /
 * ' | |'
 * 
 * Patern1: *S** 2/01 ' +----------------------------------+'
 * 
 */
public class JavaInputElement extends AbstractJavaElement {

	final static Logger logger = LoggerFactory.getLogger(JavaInputElement.class);

	// Paramaters: functionName;
	protected List<AbstractToken> inputParameters;

	protected List<ScreenIO> screenInputOutputArray;
	
	protected List<AbstractToken> buttonArray=new ArrayList<AbstractToken>();

	protected ScreenIO newScreenIO;

	protected long offset;

	protected long xCoord, yCoord;
	
	protected long carpan;
	
	protected List<AbstractToken> undefinedParameterList=new ArrayList<>();
	
	JavaInputTesterElement tester;
	
	protected boolean modifiable =false;

	@Override
	public boolean writeJavaToStream() throws Exception{
		
		screenInputOutputArray = new ArrayList<>();
		
		AbstractToken currToken = null;
	
		inputParameters = (List<AbstractToken>) this.parameters.get("inputParameters");
		
		
		if(ConversionLogModel.getInstance().getConversionFileType().equals(ConversionFileType.MAP_TESTER) && !(this instanceof JavaInputTesterElement)){
			
			
			tester=new JavaInputTesterElement(this);
			
			tester.inputParameters=this.inputParameters;
			
			
			return tester.writeJavaToStream();
		}
		
	

		try {

			removeAdParameters();
			
			removeParantezI();
			if(inputParameters==null || inputParameters.size()<3) {
				return true;
			}
			for (int index = 0; index < inputParameters.size(); index++) {

					currToken = inputParameters.get(index);
					if (currToken.getLinkedToken() != null) {
						logger.debug(currToken.getTip() + " " + currToken.getDeger().toString() + " "
								+ currToken.getLinkedToken().getDeger());
					} else {
						logger.debug(currToken.getTip() + " " + currToken.getDeger().toString());
					}

					if (matchsPaternExact(index)) { // 2/01

					} else if (matchsPaternSlash(index)) { // *S** /

					} else if (matchsPaternXIndentation(index)) { // 13X

					} else if (matchsPaternKarakterCarpan(index)) { // 0046  013T '-' (041)(I)

						processCarpan();
						
					}else if (matchsPaternWord(index)) {

					} else if (matchsPaternArray(index)) {

					} else {
						
						undefinedParameterList.add(inputParameters.get(index));
					}

					index = index + (int)offset;
		
			}

			writeOutput();

		} catch (Exception e) {
			logger.debug("//Conversion Error" + this.getClass() + this.getSourceCode().getSatirNumarasi()
					+ this.getSourceCode().getCommandName());
			JavaClassElement.javaCodeBuffer.append("//Conversion Error" + this.getClass()
					+ this.getSourceCode().getSatirNumarasi() + this.getSourceCode().getCommandName());
			logger.error("//Conversion Error " + e.getMessage(), e);
			JavaClassElement.javaCodeBuffer.append(JavaConstants.NEW_LINE);
			ConvertUtilities.writeconversionErrors(e, this); 
		}

		return true;
	}



	// (041) -- Carpan=41
	protected boolean matchsPaternKarakterCarpan(int index) {
		
		if (index + 2 >= inputParameters.size()) {
			return false;
		}

		AbstractToken openParToken, closeParToken, carpanToken;

		openParToken = inputParameters.get(index);

		carpanToken = inputParameters.get(index + 1);
		
		closeParToken=inputParameters.get(index + 2);
		
		logger.debug(openParToken.getDeger().toString()+" "+carpanToken.getDeger().toString());

		if (openParToken.isKarakter('(') && closeParToken.isKarakter(')') && carpanToken.isSayi()) {

			offset = 2;

			carpan=Integer.parseInt(carpanToken.getDeger().toString());
			
			logger.debug("Input Match Patern Carpan:" + carpanToken.getDeger().toString());

			return true;
		
		}
		
		return false;
	}



	// 2/01
	protected boolean matchsPaternExact(int index) {

		if (index + 2 >= inputParameters.size()) {
			return false;
		}

		AbstractToken currToken, nextToken, nextToken2, nextToken3;

		currToken = inputParameters.get(index);

		nextToken = inputParameters.get(index + 1);

		nextToken2 = inputParameters.get(index + 2);
		;

		if (currToken.getTip().equals(TokenTipi.Sayi) && nextToken.getTip().equals(TokenTipi.Karakter)
				&& nextToken.getDeger().equals('/') && nextToken2.getTip().equals(TokenTipi.Sayi)) {

			xCoord = (int)((long)currToken.getDeger()) - 1;

			yCoord = (int)((long)nextToken2.getDeger()) - 1;

			offset = 2;

			logger.debug("Input Match matchsPaternExact:" + currToken.getDeger().toString()+" "+nextToken.getDeger().toString()+"/"+nextToken2.getTip().equals(TokenTipi.Sayi));

			return true;
		}

		return false;
	}

	// *S** /
	protected boolean matchsPaternSlash(int index) {

		AbstractToken currToken;

		currToken = inputParameters.get(index);

		if (currToken.getTip().equals(TokenTipi.Karakter) && currToken.getDeger().equals('/')) {

			xCoord = xCoord + 1;

			yCoord = 0;

			logger.debug("Input Match matchsPaternSlash: Slash" );

			offset = 0;
			
			return true;
		}

		return false;
	}

	// *S** #SECIM
	protected boolean matchsPaternWord(int index) throws Exception {

		AbstractToken currToken, nextToken;

		currToken = inputParameters.get(index);

		String value = null;

		
		
		if (currToken.getTip().equals(TokenTipi.Kelime)) {
			
			long maxLength=ConvertUtilities.getVariableMaxLength(currToken);
			
			value = JavaWriteUtilities.toCustomString(currToken).toString();
			
			String name;
			
			if(value.contains("getInstance")){
				String firstPart= value.substring(0,value.indexOf('.'));
				String remaingPart= value.substring(value.indexOf('.')+1);
				String lastPart=remaingPart.substring(remaingPart.indexOf('.'));
				name ="\"" + firstPart+ lastPart + "\"";
					
			}else{
				name ="\"" + value + "\"";
			}
			
			writeUndefinedTokens();
			
			
			logger.debug(currToken.getDeger().toString());
			
			modifiable=controlModifiable(currToken);
			//AD=MIT  --> M modifiable
		
			if(isButton(currToken)){
				
				buttonArray.add(currToken);
			}
			else if (currToken.isConstantVariableWithQuota() || currToken.isSystemVariable()) {

					newScreenIO = new ScreenIOLabel(xCoord, yCoord, IOModeType.AD_D, value, XCoordinationTypes.EXACT,
							XCoordinationTypes.EXACT,0,maxLength, currToken.isConstantVariableWithQuota());
				
			} else if (currToken.getTip().equals(TokenTipi.Kelime) && modifiable) { // #SECIM

				VariableTypes varType = ConvertUtilities.getVariableType(currToken);

				if (varType.equals(VariableTypes.INT_TYPE)|| varType.equals(VariableTypes.LONG_TYPE)) {

					newScreenIO = new ScreenIOIntegerInput(xCoord, yCoord, IOModeType.AD_D, name, value,
							XCoordinationTypes.EXACT, XCoordinationTypes.EXACT,(long)0,maxLength);

				}else{
					
					newScreenIO = new ScreenIOStringInput(xCoord, yCoord, IOModeType.AD_D, name, value,
							XCoordinationTypes.EXACT, XCoordinationTypes.EXACT,0,maxLength);
				}
			} else if (currToken.getTip().equals(TokenTipi.Sayi)) { //

				newScreenIO = new ScreenIOIntegerInput(xCoord, yCoord, IOModeType.AD_D, name, value,
						XCoordinationTypes.EXACT, XCoordinationTypes.EXACT,0,maxLength);
			} else { //

				newScreenIO = new ScreenIOLabel(xCoord, yCoord, IOModeType.AD_D, value, XCoordinationTypes.EXACT,
						XCoordinationTypes.EXACT,0,maxLength, currToken.isConstantVariableWithQuota());
			}


			if(value.length()>maxLength){
				yCoord = yCoord + value.length();
			}else{
				yCoord = yCoord + maxLength;
			}
			
			if(!isButton(currToken)){
				
				screenInputOutputArray.add(newScreenIO);
				
			}
			offset = 0;
			
			logger.debug("Input Match matchsPaternWord:" + newScreenIO.toString());
	
			return true;
		}

		return false;
	}





	protected void processCarpan() {
		
		ScreenIO lastScreenIOForModify = screenInputOutputArray.get(screenInputOutputArray.size()-1);
		
		if(!(lastScreenIOForModify instanceof ScreenIOLabel)){
			return;
		}
		
		ScreenIOLabel lastlabelForModify;
		
		String oldValue=lastScreenIOForModify.getValue().replaceAll("\"", "");
		
		StringBuffer newValue=new StringBuffer("\"");
		
		for(int i=0; i<carpan;i++){
			newValue.append(oldValue);
		}
		
		newValue.append("\"");
		
		lastlabelForModify=(ScreenIOLabel) lastScreenIOForModify;
			
		lastlabelForModify.setValue(newValue.toString());
		
		carpan=1;
	
		return;
		
	}



	protected boolean controlModifiable(AbstractToken currToken) {
		
		this.modifiable=false;
		
		String inputADParameters;
		try {
			if(currToken.getInputADParameters()!=null){
				inputADParameters=currToken.getInputADParameters().getDeger().toString();
				
				if(inputADParameters.contains("M")){
					return true;
				}
			}
			
			if(currToken.getLinkedToken()!=null && currToken.getLinkedToken().getInputADParameters()!=null){
				inputADParameters=currToken.getLinkedToken().getInputADParameters().getDeger().toString();
				
				if(inputADParameters.contains("M")){
					return true;
				}
			}
		
		} catch (Exception e) {
			logger.debug(e.getMessage(), e);
			return false;
		}
		return false;
	}



	private void writeUndefinedTokens() {
		
		StringBuffer sb=new StringBuffer();
		
		if(undefinedParameterList==null || undefinedParameterList.isEmpty()){
			return;
		}
		
		for(AbstractToken undefined: undefinedParameterList){
			
			sb.append(undefined.toCustomString());
			
		}
		newScreenIO = new ScreenIOUndefined(xCoord, yCoord, IOModeType.AD_D, sb.toString(), XCoordinationTypes.EXACT,  XCoordinationTypes.EXACT,0,ConverterConfiguration.DEFAULT_MAX_LENGTH_FOR_LABEL);
		
		screenInputOutputArray.add(newScreenIO);
		
		undefinedParameterList=new  ArrayList<AbstractToken>();
	}

	// *S** SCRLINES(*)
	protected boolean matchsPaternArray(int index) throws Exception {


		AbstractToken currToken;
		
		ArrayToken arrayToken;

		currToken = inputParameters.get(index);

		String value = null;
		
		if (currToken.getTip().equals(TokenTipi.Array)) {
			
			arrayToken=(ArrayToken) currToken;
			
			long maxLength=ConvertUtilities.getVariableMaxLength(currToken);
			
			writeUndefinedTokens();
			
			if(arrayToken.getFirstDimension().getDeger().equals('*')){   //SCRLINES(*)
				value = JavaWriteUtilities.toCustomString(arrayToken).toString();
				
				long arrayLength;
				
				arrayLength=ConvertUtilities.getArrayLength(currToken);
				
				for(int i=0;i<arrayLength;i++){
				
					xCoord=xCoord+1;
						
					newScreenIO = new ScreenIOLabel(xCoord, yCoord, IOModeType.AD_D, value+"["+i+"]", XCoordinationTypes.EXACT,
						XCoordinationTypes.EXACT,0,maxLength);
	
					screenInputOutputArray.add(newScreenIO);
	
				}
				
			}
			else{  //SCRLINES(i)
				value = JavaWriteUtilities.toCustomString(currToken).toString();
	
				if(currToken.getDeger().equals("_") || currToken.getDeger().equals("=")){
					newScreenIO = new ScreenIOLabel(xCoord, yCoord, IOModeType.AD_D, value, XCoordinationTypes.EXACT, XCoordinationTypes.EXACT,0,maxLength);
					
				}else{
					newScreenIO = new ScreenIOLabel(xCoord, yCoord, IOModeType.AD_D, value, XCoordinationTypes.EXACT,
							XCoordinationTypes.EXACT,0,maxLength);
					
				}
	
				yCoord = yCoord + value.length();
	
				screenInputOutputArray.add(newScreenIO);
	
			}
			offset = 0;
			
			logger.debug("Input Match matchsPaternArray:" + newScreenIO.toString());

			return true;
		}

		return false;
	}

	// 13X '*** TAX , COUNTRY , AIRPORT MANAGEMENT ***'
	protected boolean matchsPaternXIndentation(int index) {

		if (index + 1 >= inputParameters.size()) {
			return false;
		}

		AbstractToken currToken, nextToken, nextToken2;

		currToken = inputParameters.get(index);

		nextToken = inputParameters.get(index + 1);

		if (currToken.getTip().equals(TokenTipi.Sayi) && nextToken.getTip().equals(TokenTipi.Kelime)
				&& (nextToken.getDeger().equals("X") || nextToken.getDeger().equals("T"))) {

			int YCoordCarpan;

			YCoordCarpan = (int) ((long)currToken.getDeger());
			if (nextToken.getDeger().equals("X")) {
				yCoord = YCoordCarpan * ConverterConfiguration.NATURAL_X_LENGTH;
			} else if (nextToken.getDeger().equals("T")) {
				yCoord = YCoordCarpan * ConverterConfiguration.NATURAL_T_LENGTH;
			} else { // Hata durumda en azından boyle göstersin
				yCoord = YCoordCarpan * ConverterConfiguration.NATURAL_T_LENGTH;
			}

			offset = 1;

			logger.debug("Input Match Patern1:" + currToken.getDeger().toString()+" "+nextToken.getDeger().toString());

			return true;
		}

		return false;
	}

	protected void writeOutput() {

		AbstractToken curToken, nextToken, previousToken = null;

		String label = "", ADParameters;

		ScreenIO sIO = null;
		
		writeButtons();

		if(!logModel.isMapOrMapTester()){
			addValidationLoopStarter();
		}
		
		if(logModel.isMapOrMapTester()){
			JavaClassElement.javaCodeBuffer.append("natprog.");
		}
	
		JavaClassElement.javaCodeBuffer.append("input(");
		JavaClassElement.javaCodeBuffer.append(JavaConstants.NEW_LINE);

		long xCoord=0, previousXCoord = 0;
		// new
		// ScreenInputOutput(2,"1X",NaturalTagTypes.LABEL,IOModeType.AD_MI_,"+----------------------------------+",XCoordinationTypes.EXACT)
		for (int index = 0; index < screenInputOutputArray.size(); index++) {
			
			if(sIO!=null){
				previousXCoord=sIO.getXCoord();
			}
			
			sIO = screenInputOutputArray.get(index);

			xCoord=sIO.getXCoord();

			logger.debug("Current:"+sIO.getName()+sIO.getValue());
	
			
			if(xCoord>previousXCoord){
				
				JavaClassElement.javaCodeBuffer.append(JavaConstants.NEW_LINE);
			}
			
			//EM parameterdir. Yazdırma
			if(sIO.getValue().equals("EM")){
				continue;
			}
			
			if (sIO.getTagType().equals(NaturalTagTypes.LABEL)) {
				if(sIO instanceof ScreenIOUndefined)
				{
					writeUndefined(sIO);
				}else{
					writeLabel(sIO);
				}
			} else if (sIO.getTagType().equals(NaturalTagTypes.INPUTFIELD)) {
				if(sIO instanceof ScreenIOIntegerInput){
					writeNumberInputField(sIO);
				}else{
					writeStringInputField(sIO);
				}
			}

			if (index < screenInputOutputArray.size() - 1) {
				JavaClassElement.javaCodeBuffer.append(",");
				JavaClassElement.javaCodeBuffer.append(JavaConstants.NEW_LINE);
			}
			

		}

		JavaClassElement.javaCodeBuffer.append(");");
		JavaClassElement.javaCodeBuffer.append(JavaConstants.NEW_LINE);
		
		if(!logModel.isMapOrMapTester()){
			addValidationLoopEnder();
		}
	}

	/*
	 * natprog.unRegisterPFKeyAll();
		natprog.unRegisterEnterKey();
		natprog.registerPFKey("PF3", ":Çıkış", true, true, "", "");
		natprog.registerPFKey("PF2", ":Ana Menu", true, true, "", "");
		natprog.registerPFKey("PF1", ":Ulke Kodları", true, true, "", "");
		natprog.registerPFKey("PF5", ":Şehir Adları", true, true, "", "");
		natprog.registerPFKey("PF12", ":Devam", true, true, "", "");
	 */
	private void writeButtons() {
		
		boolean enterButtonVisible=false;
		enterButtonVisible=isEnterButtonVisible();
		String buttonPFKey="", tokenDeger, buttonName="";
		if(ConversionLogModel.getInstance().isMapOrMapTester()){
			JavaClassElement.javaCodeBuffer.append("natprog.");
		}
		
		JavaClassElement.javaCodeBuffer.append("unRegisterPFKeyAll()");
		JavaClassElement.javaCodeBuffer.append(JavaConstants.DOT_WITH_COMMA+JavaConstants.NEW_LINE);
		
		if(!enterButtonVisible){
			if(ConversionLogModel.getInstance().isMapOrMapTester()){
				JavaClassElement.javaCodeBuffer.append("natprog.");
			}
			JavaClassElement.javaCodeBuffer.append("unRegisterEnterKey()");
			JavaClassElement.javaCodeBuffer.append(JavaConstants.DOT_WITH_COMMA+JavaConstants.NEW_LINE);
		}
		
		for(AbstractToken button:buttonArray){
			
			tokenDeger=button.getDeger().toString();
			if(tokenDeger.startsWith("F20") 
					||tokenDeger.startsWith("F19")
					||tokenDeger.startsWith("F18")
					||tokenDeger.startsWith("F17")
					||tokenDeger.startsWith("F16")
					||tokenDeger.startsWith("F15")
					||tokenDeger.startsWith("F14")
					||tokenDeger.startsWith("F13")
					||tokenDeger.startsWith("F12")
					||tokenDeger.startsWith("F11")
					||tokenDeger.startsWith("F10")
					){
				buttonPFKey=tokenDeger.substring(0,3);
				buttonName=tokenDeger.substring(3);
				buttonPFKey="P"+buttonPFKey;
			}else if(tokenDeger.startsWith("F9")
					||tokenDeger.startsWith("F8")
					||tokenDeger.startsWith("F7")
					||tokenDeger.startsWith("F6")
					||tokenDeger.startsWith("F5")
					||tokenDeger.startsWith("F4")
					||tokenDeger.startsWith("F3")
					||tokenDeger.startsWith("F2")
					||tokenDeger.startsWith("F1")
					){
				buttonPFKey=tokenDeger.substring(0,2);
				buttonName=tokenDeger.substring(2);
				buttonPFKey="P"+buttonPFKey;
			}else if(tokenDeger.contains("ENTER")){
				buttonPFKey="ENTER";
				buttonName=tokenDeger.replaceAll("ENTER","").trim();
			}
			
			logger.debug("Register Button: "+buttonPFKey +" "+buttonName);
			if(ConversionLogModel.getInstance().isMapOrMapTester()){
				JavaClassElement.javaCodeBuffer.append("natprog.");
			}
			JavaClassElement.javaCodeBuffer.append("registerPFKey(\""+buttonPFKey+"\", \""+buttonName+"\", true, true, \"\", \"\")");
			JavaClassElement.javaCodeBuffer.append(JavaConstants.DOT_WITH_COMMA+JavaConstants.NEW_LINE);
		}
		
	}


	
	private boolean isButton(AbstractToken currToken) {
		
		String tokenDeger;
		
		tokenDeger=currToken.getDeger().toString();
		
		if(tokenDeger.toUpperCase().trim().startsWith("ENTER")
				|| tokenDeger.toUpperCase().trim().startsWith("ENTR")
				|| tokenDeger.toUpperCase().trim().startsWith("F1")
				|| tokenDeger.toUpperCase().trim().startsWith("F2")
				|| tokenDeger.toUpperCase().trim().startsWith("F3")
				|| tokenDeger.toUpperCase().trim().startsWith("F4")
				|| tokenDeger.toUpperCase().trim().startsWith("F5")
				|| tokenDeger.toUpperCase().trim().startsWith("F6")
				|| tokenDeger.toUpperCase().trim().startsWith("F7")
				|| tokenDeger.toUpperCase().trim().startsWith("F8")
				|| tokenDeger.toUpperCase().trim().startsWith("F9")){
			return true;
		}
		return false;	
	}


	private boolean isEnterButtonVisible() {
		String buttonPFKey, tokenDeger;
		
		for(AbstractToken button:buttonArray){
			tokenDeger=button.getDeger().toString();
			if(tokenDeger.indexOf(" ")==-1){
				continue;
			}
			buttonPFKey=tokenDeger.substring(0, tokenDeger.indexOf(" "));
			if(buttonPFKey.toUpperCase().equals("ENTER")|| buttonPFKey.toUpperCase().equals("ENTR")){
				return true;
			}
		}
		return false;
	}





	private void addValidationLoopStarter() {
			JavaClassElement.javaCodeBuffer.append("validationError = true"+JavaConstants.DOT_WITH_COMMA+JavaConstants.NEW_LINE);	
			JavaClassElement.javaCodeBuffer.append("while (this.validationError) {"+JavaConstants.DOT_WITH_COMMA+JavaConstants.NEW_LINE);
			JavaClassElement.javaCodeBuffer.append("    breakControl = false"+JavaConstants.DOT_WITH_COMMA+JavaConstants.NEW_LINE);	
			JavaClassElement.javaCodeBuffer.append("	validationError = false"+JavaConstants.DOT_WITH_COMMA+JavaConstants.NEW_LINE);
			JavaClassElement.javaCodeBuffer.append("	try {"+JavaConstants.DOT_WITH_COMMA+JavaConstants.NEW_LINE);
			
			JavaClassElement.javaCodeBuffer.append("if(continueControl){"+JavaConstants.NEW_LINE);
			JavaClassElement.javaCodeBuffer.append("	break"+JavaConstants.DOT_WITH_COMMA+JavaConstants.NEW_LINE);
			JavaClassElement.javaCodeBuffer.append("}//Continue Kontrol"+JavaConstants.NEW_LINE);
	}

	
	private void addValidationLoopEnder() {
		JavaClassElement.javaCodeBuffer.append("	} catch (VBTValidationException e) { // TODO:Bu satır ve altindaki 3 satir. Bu ekranla ilgili son showDialogV2 den sonraya taşınmalı"+JavaConstants.DOT_WITH_COMMA+JavaConstants.NEW_LINE);
		JavaClassElement.javaCodeBuffer.append(""+JavaConstants.DOT_WITH_COMMA+JavaConstants.NEW_LINE);
		JavaClassElement.javaCodeBuffer.append("	}//Validation Catch"+JavaConstants.DOT_WITH_COMMA+JavaConstants.NEW_LINE);
		JavaClassElement.javaCodeBuffer.append("}//Validation While"+JavaConstants.DOT_WITH_COMMA+JavaConstants.NEW_LINE);
		
		JavaClassElement.javaCodeBuffer.append("if(breakControl){"+JavaConstants.NEW_LINE);
		JavaClassElement.javaCodeBuffer.append("	breakControl=false"+JavaConstants.DOT_WITH_COMMA+JavaConstants.NEW_LINE);
		JavaClassElement.javaCodeBuffer.append("	break"+JavaConstants.DOT_WITH_COMMA+JavaConstants.NEW_LINE);
		JavaClassElement.javaCodeBuffer.append("}//Break Kontrol"+JavaConstants.NEW_LINE);
		
		JavaClassElement.javaCodeBuffer.append("if(continueControl){"+JavaConstants.NEW_LINE);
		JavaClassElement.javaCodeBuffer.append("	continueControl=false"+JavaConstants.DOT_WITH_COMMA+JavaConstants.NEW_LINE);
		JavaClassElement.javaCodeBuffer.append("	continue"+JavaConstants.DOT_WITH_COMMA+JavaConstants.NEW_LINE);
		JavaClassElement.javaCodeBuffer.append("}//Continue Kontrol"+JavaConstants.NEW_LINE);
		
		
	}




	// new
	// ScreenIOIntegerInput(0,1,IOModeType.AD_MI_,"DIYEZ_SECIM",DIYEZ_SECIM,XCoordinationTypes.REFERANCE),
	protected void writeNumberInputField(ScreenIO sIO) {

		JavaClassElement.javaCodeBuffer.append("new ScreenIOIntegerInput(" + sIO.getXCoord() + "," + sIO.getYCoord());

		JavaClassElement.javaCodeBuffer.append(",");

		JavaClassElement.javaCodeBuffer.append("IOModeType.AD_MI_");

		JavaClassElement.javaCodeBuffer.append(",");

		JavaClassElement.javaCodeBuffer.append(sIO.getName());

		JavaClassElement.javaCodeBuffer.append(",");

		JavaClassElement.javaCodeBuffer.append(sIO.getValueForEngine());

		JavaClassElement.javaCodeBuffer.append(",");

		if (sIO.getxCoordinationType().equals(XCoordinationTypes.EXACT)) {
			JavaClassElement.javaCodeBuffer.append("XCoordinationTypes.EXACT , XCoordinationTypes.EXACT");
		} else {
			JavaClassElement.javaCodeBuffer.append("XCoordinationTypes.REFERANCE , XCoordinationTypes.REFERANCE");
		}
		
		JavaClassElement.javaCodeBuffer.append(",");
		
		JavaClassElement.javaCodeBuffer.append("0");
		
		JavaClassElement.javaCodeBuffer.append(",");
		
		JavaClassElement.javaCodeBuffer.append(sIO.getMaxLength());
		
		
		JavaClassElement.javaCodeBuffer.append(")");

	}

	
	// new
	// ScreenIOIntegerInput(0,1,IOModeType.AD_MI_,"DIYEZ_SECIM",DIYEZ_SECIM,XCoordinationTypes.REFERANCE),
	protected void writeStringInputField(ScreenIO sIO) {

		JavaClassElement.javaCodeBuffer.append("new ScreenIOStringInput(" + sIO.getXCoord() + "," + sIO.getYCoord());

		JavaClassElement.javaCodeBuffer.append(",");

		JavaClassElement.javaCodeBuffer.append("IOModeType.AD_MI_");

		JavaClassElement.javaCodeBuffer.append(",");

		
		JavaClassElement.javaCodeBuffer.append(sIO.getName());

		JavaClassElement.javaCodeBuffer.append(",");

		if(logModel.isMapOrMapTester()){
			JavaClassElement.javaCodeBuffer.append("natprog.");
		}

		JavaClassElement.javaCodeBuffer.append(sIO.getValue());

		JavaClassElement.javaCodeBuffer.append(",");

		if (sIO.getxCoordinationType().equals(XCoordinationTypes.EXACT)) {
			JavaClassElement.javaCodeBuffer.append("XCoordinationTypes.EXACT , XCoordinationTypes.EXACT");
		} else {
			JavaClassElement.javaCodeBuffer.append("XCoordinationTypes.REFERANCE , XCoordinationTypes.REFERANCE");
		}
		
		JavaClassElement.javaCodeBuffer.append(",");
		
		JavaClassElement.javaCodeBuffer.append("0");
		
		JavaClassElement.javaCodeBuffer.append(",");
		
		JavaClassElement.javaCodeBuffer.append(sIO.getMaxLength());

		JavaClassElement.javaCodeBuffer.append(")");

	}
	protected void writeLabel(ScreenIO sIO) {

		JavaClassElement.javaCodeBuffer.append("new ScreenIOLabel(" + sIO.getXCoord() + "," + sIO.getYCoord());

		JavaClassElement.javaCodeBuffer.append(",");

		JavaClassElement.javaCodeBuffer.append("IOModeType.AD_MI_");

		JavaClassElement.javaCodeBuffer.append(",");

		logger.debug("Value:"+sIO.getValue());
		if(logModel.isMapOrMapTester() && !sIO.isDoubleQouta()){
			JavaClassElement.javaCodeBuffer.append("natprog.");
		}
		
		JavaClassElement.javaCodeBuffer.append( sIO.getValue());
					
		JavaClassElement.javaCodeBuffer.append(",");

		if (sIO.getxCoordinationType().equals(XCoordinationTypes.EXACT)) {
			JavaClassElement.javaCodeBuffer.append("XCoordinationTypes.EXACT , XCoordinationTypes.EXACT");
		} else {
			JavaClassElement.javaCodeBuffer.append("XCoordinationTypes.REFERANCE , XCoordinationTypes.REFERANCE");
		}
		
		JavaClassElement.javaCodeBuffer.append(",");
		
		JavaClassElement.javaCodeBuffer.append("0");
		
		JavaClassElement.javaCodeBuffer.append(",");
		
		JavaClassElement.javaCodeBuffer.append(sIO.getMaxLength());

		JavaClassElement.javaCodeBuffer.append(")");

	}
	
	private void writeUndefined(ScreenIO sIO) {

		JavaClassElement.javaCodeBuffer.append("//new ScreenIOUndefined(" + sIO.getXCoord() + "," + sIO.getYCoord());
	
		JavaClassElement.javaCodeBuffer.append(",");

		JavaClassElement.javaCodeBuffer.append("IOModeType.AD_MI_");

		JavaClassElement.javaCodeBuffer.append(",\"");


		JavaClassElement.javaCodeBuffer.append( sIO.getValue());
					
		JavaClassElement.javaCodeBuffer.append("\",");

		if (sIO.getxCoordinationType().equals(XCoordinationTypes.EXACT)) {
			JavaClassElement.javaCodeBuffer.append("XCoordinationTypes.EXACT , XCoordinationTypes.EXACT");
		} else {
			JavaClassElement.javaCodeBuffer.append("XCoordinationTypes.REFERANCE , XCoordinationTypes.REFERANCE");
		}
		
		JavaClassElement.javaCodeBuffer.append(",");
		
		JavaClassElement.javaCodeBuffer.append("0");
		
		JavaClassElement.javaCodeBuffer.append(",");
		
		JavaClassElement.javaCodeBuffer.append(sIO.getMaxLength());

		JavaClassElement.javaCodeBuffer.append(")");

	}

	// (I) karakter dizisini inputParameters listten siler 
	protected void removeParantezI() {
		
		AbstractToken IKelimesi, parantezOpenToken,parantezCloseParameters;
		if(inputParameters==null || inputParameters.size()<3) {
			return ;
		}
		for (int index = 0; index < inputParameters.size() - 2; index++) {
			
			parantezOpenToken=inputParameters.get(index);
			IKelimesi=inputParameters.get(index+1);
			parantezCloseParameters=inputParameters.get(index+2);
			
			
			if(parantezOpenToken.isKarakter('(') && parantezCloseParameters.isKarakter(')') && IKelimesi.isKelime("I")){
				inputParameters.remove(index);
				inputParameters.remove(index);
				inputParameters.remove(index);
			}
		}
		
	}
	
	
	// MAP2.MUSNO1 (AD=ODL ) --> MAP2.MUSNO1
	// MUSNO1 (AD=ODL ) --> MUSNO1
	// *S**INPUT (AD=MI'.') -->
	protected void removeAdParameters() {

		AbstractToken curToken, parantezOpenToken, adToken, equalsToken, inputADParameters, parantezCloseParameters,
				noktaOrTireToken;

		StringBuffer ADParameter;
		if(inputParameters==null || inputParameters.size()<4) {
			return ;
		}
		for (int index = 0; index < inputParameters.size() - 3; index++) {
			try {
				ADParameter = new StringBuffer();
				// /38T
				parantezOpenToken = inputParameters.get(index);
				adToken = inputParameters.get(index + 1);
				equalsToken = inputParameters.get(index + 2);

				logger.debug(adToken.getDeger().toString());

				//004T MAP.MUSNO1  (AD=ODL )
				if (parantezOpenToken.isKarakter('(')
						&& equalsToken.isKarakter('=')
						&& adToken.isADParameters()) {

					do {
						parantezCloseParameters = inputParameters.get(index+1);

						ADParameter.append(parantezCloseParameters.getDeger().toString());

						inputParameters.remove(index+1);

					} while (!(parantezCloseParameters.getTip().equals(TokenTipi.Karakter)
							&& parantezCloseParameters.getDeger().equals(')')));

					adToken.setInputADParameters(new KelimeToken(("("+ADParameter.toString()), 0, 0, 0));
					inputParameters.remove(index);
				}
				
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}

	}

}
