package tr.com.vbt.java.screen;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tr.com.vbt.java.general.JavaClassElement;
import tr.com.vbt.java.general.JavaConstants;
import tr.com.vbt.java.utils.ConvertUtilities;
import tr.com.vbt.java.utils.JavaWriteUtilities;
import tr.com.vbt.java.utils.VariableTypes;
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
public class JavaInputTesterElement extends JavaInputElement {

	JavaInputElement  javaInputElement;
	
	final static Logger logger = LoggerFactory.getLogger(JavaAtTopOfPageElement.class);

	// Paramaters: functionName;
	private List<AbstractToken> inputParameters;

	private List<ScreenIO> screenInputOutputArray;

	ScreenIO newScreenIO;

	int offset;

	int xCoord, yCoord;
	
	private List<AbstractToken> undefinedParameterList=new ArrayList<>();
	
	boolean isMap;
	
	boolean isMapTester;
	
	
	public String INPUT_SIZE_1 = new String("A");
	public String INPUT_SIZE_2 = new String("AB");
	public String INPUT_SIZE_3 = new String("ABC");
	public String INPUT_SIZE_4 = new String("ABCD");
	public String INPUT_SIZE_5 = new String("ABCDE");
	public String INPUT_SIZE_6 = new String("ABCDEF");
	public String INPUT_SIZE_7 = new String("ABCDEFG");
	public String INPUT_SIZE_8 = new String("ABCDEFGH");
	public String INPUT_SIZE_9 = new String("ABCDEFGHI");
	public String INPUT_SIZE_10 = new String("ABCDEFGHIJ");




	public JavaInputTesterElement(JavaInputElement javaInputElement) {
		javaInputElement=javaInputElement;
		
		this.parameters=javaInputElement.getParameters();
	}



	@Override
	public boolean writeJavaToStream() throws Exception{
		
		screenInputOutputArray = new ArrayList<>();

		inputParameters = (List<AbstractToken>) this.parameters.get("inputParameters");
		
		isMap=ConversionLogModel.getInstance().isMap();
		
		isMapTester=ConversionLogModel.getInstance().isMapTester();

		int XCoord;

		XCoordinationTypes xCoordinationType;

		int YCoord;

		NaturalTagTypes tagType;

		IOModeType modeType;

		String value;

		AbstractToken currToken = null;

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

					} else if (matchsPaternWord(index)) {

					} else if (matchsPaternArray(index)) {

					} else {
						
						undefinedParameterList.add(inputParameters.get(index));
					}

					index = index + offset;
		
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



	// 2/01
	private boolean matchsPaternExact(int index) {

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

			xCoord = (int)currToken.getDeger() - 1;

			yCoord = (int)nextToken2.getDeger() - 1;

			offset = 2;

			logger.debug("Input Match matchsPaternExact:" + currToken.getDeger().toString()+" "+nextToken.getDeger().toString()+"/"+nextToken2.getTip().equals(TokenTipi.Sayi));

			return true;
		}

		return false;
	}

	// *S** /
	private boolean matchsPaternSlash(int index) {

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
	private boolean matchsPaternWord(int index) throws Exception {

		AbstractToken currToken, nextToken;

		currToken = inputParameters.get(index);

		String value = null;

		if (currToken.getTip().equals(TokenTipi.Kelime)) {
			
			int maxLength=ConvertUtilities.getVariableMaxLength(currToken);
			
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
		
			if (currToken.isConstantVariableWithQuota() || currToken.isSystemVariable()) {

				newScreenIO = new ScreenIOLabel(xCoord, yCoord, IOModeType.AD_D, value, XCoordinationTypes.EXACT,
						XCoordinationTypes.EXACT,0,maxLength, currToken.isConstantVariableWithQuota());
				
			} else if (currToken.getTip().equals(TokenTipi.Kelime)) { // #SECIM

				VariableTypes varType = ConvertUtilities.getVariableType(currToken);

				if (varType.equals(VariableTypes.INT_TYPE)|| varType.equals(VariableTypes.LONG_TYPE)) {

					newScreenIO = new ScreenIOIntegerInput(xCoord, yCoord, IOModeType.AD_D, name, value,
							XCoordinationTypes.EXACT, XCoordinationTypes.EXACT,0,maxLength);

				}else{
					
					newScreenIO = new ScreenIOStringInput(xCoord, yCoord, IOModeType.AD_D, name, value,
							XCoordinationTypes.EXACT, XCoordinationTypes.EXACT,0,maxLength);
				}
			} else if (currToken.getTip().equals(TokenTipi.Sayi)) { //

				newScreenIO = new ScreenIOIntegerInput(xCoord, yCoord, IOModeType.AD_D, name, value,
						XCoordinationTypes.EXACT, XCoordinationTypes.EXACT,0,maxLength);
			}

			if(value.length()>maxLength){
				yCoord = yCoord + value.length();
			}else{
				yCoord = yCoord + maxLength;
			}
			
			screenInputOutputArray.add(newScreenIO);

			offset = 0;

			logger.debug("Input Match matchsPaternWord:" + newScreenIO.toString());
			

			return true;
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
		
		xCoord=xCoord+1;
		
		yCoord=0;
		
		undefinedParameterList=new  ArrayList<AbstractToken>();
	}

	// *S** SCRLINES(*)
	private boolean matchsPaternArray(int index) throws Exception {


		AbstractToken currToken;
		
		ArrayToken arrayToken;

		currToken = inputParameters.get(index);

		String value = null;
		
		if (currToken.getTip().equals(TokenTipi.Array)) {
			
			arrayToken=(ArrayToken) currToken;
			
			int maxLength=ConvertUtilities.getVariableMaxLength(currToken);
			
			writeUndefinedTokens();
			
			if(arrayToken.getFirstDimension().getDeger().equals('*')){   //SCRLINES(*)
				value = JavaWriteUtilities.toCustomString(arrayToken).toString();
				
				int arrayLength;
				
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
	private boolean matchsPaternXIndentation(int index) {

		if (index + 1 >= inputParameters.size()) {
			return false;
		}

		AbstractToken currToken, nextToken, nextToken2;

		currToken = inputParameters.get(index);

		nextToken = inputParameters.get(index + 1);

		if (currToken.getTip().equals(TokenTipi.Sayi) && nextToken.getTip().equals(TokenTipi.Kelime)
				&& (nextToken.getDeger().equals("X") || nextToken.getDeger().equals("T"))) {

			int YCoordCarpan;

			YCoordCarpan = (int) (currToken.getDeger());
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

	private void writeOutput() {

		AbstractToken curToken, nextToken, previousToken = null;

		String label = "", ADParameters;

		ScreenIO sIO = null;
		
		if(isMap || isMapTester){
			JavaClassElement.javaCodeBuffer.append("natprog.");
		}
		
		if(!isMap && !isMapTester){
			addValidationLoopStarter();
		}
		JavaClassElement.javaCodeBuffer.append("input(");
		JavaClassElement.javaCodeBuffer.append(JavaConstants.NEW_LINE);

		int xCoord=0, previousXCoord = 0;
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
		
		if(!isMap&& !isMapTester){
			addValidationLoopEnder();
		}

	}

	private void addValidationLoopEnder() {
		JavaClassElement.javaCodeBuffer.append("	} catch (VBTValidationException e) { // TODO:Bu satır ve altindaki 3 satir. Bu ekranla ilgili son showDialogV2 den sonraya taşınmalı"+JavaConstants.DOT_WITH_COMMA+JavaConstants.NEW_LINE);
		JavaClassElement.javaCodeBuffer.append(""+JavaConstants.DOT_WITH_COMMA+JavaConstants.NEW_LINE);
		JavaClassElement.javaCodeBuffer.append("	}//Validation Catch"+JavaConstants.DOT_WITH_COMMA+JavaConstants.NEW_LINE);
		JavaClassElement.javaCodeBuffer.append("}//Validation While"+JavaConstants.DOT_WITH_COMMA+JavaConstants.NEW_LINE);
		
	}



	private void addValidationLoopStarter() {
		JavaClassElement.javaCodeBuffer.append("validationError = true"+JavaConstants.DOT_WITH_COMMA+JavaConstants.NEW_LINE);	
		JavaClassElement.javaCodeBuffer.append("while (this.validationError) {"+JavaConstants.DOT_WITH_COMMA+JavaConstants.NEW_LINE);
		JavaClassElement.javaCodeBuffer.append("	validationError = false"+JavaConstants.DOT_WITH_COMMA+JavaConstants.NEW_LINE);
		JavaClassElement.javaCodeBuffer.append("	try {"+JavaConstants.DOT_WITH_COMMA+JavaConstants.NEW_LINE);
		
	}



	// new
	// ScreenIOIntegerInput(0,1,IOModeType.AD_MI_,"DIYEZ_SECIM",DIYEZ_SECIM,XCoordinationTypes.REFERANCE),
	private void writeNumberInputField(ScreenIO sIO) {

		JavaClassElement.javaCodeBuffer.append("new ScreenIOIntegerInput(" + sIO.getXCoord() + "," + sIO.getYCoord());

		JavaClassElement.javaCodeBuffer.append(",");

		JavaClassElement.javaCodeBuffer.append("IOModeType.AD_MI_");

		JavaClassElement.javaCodeBuffer.append(",");

		JavaClassElement.javaCodeBuffer.append(sIO.getName());

		JavaClassElement.javaCodeBuffer.append(",");

		JavaClassElement.javaCodeBuffer.append(getLabelActor(sIO.getValueForEngine()));

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
	private void writeStringInputField(ScreenIO sIO) {

		JavaClassElement.javaCodeBuffer.append("new ScreenIOStringInput(" + sIO.getXCoord() + "," + sIO.getYCoord());

		JavaClassElement.javaCodeBuffer.append(",");

		JavaClassElement.javaCodeBuffer.append("IOModeType.AD_MI_");

		JavaClassElement.javaCodeBuffer.append(",");

		
		JavaClassElement.javaCodeBuffer.append(sIO.getName());

		JavaClassElement.javaCodeBuffer.append(",");


		JavaClassElement.javaCodeBuffer.append(getLabelActor(sIO.getValue()));

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
	
	//new ScreenIOLabel(0,2,IOModeType.AD_MI_,"T.C.M.B.",XCoordinationTypes.EXACT , XCoordinationTypes.EXACT,0,5),
	private void writeLabel(ScreenIO sIO) {

		
		JavaClassElement.javaCodeBuffer.append("new ScreenIOLabel(" + sIO.getXCoord() + "," + sIO.getYCoord());

		JavaClassElement.javaCodeBuffer.append(",");

		JavaClassElement.javaCodeBuffer.append("IOModeType.AD_MI_");

		JavaClassElement.javaCodeBuffer.append(",");


		JavaClassElement.javaCodeBuffer.append( getLabelActor(sIO.getValue()));
					
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
	
	private String getLabelActor(String value) {
		int labelLen=value.length();
		
		String label;
		switch (labelLen) {
		case 1:
			
			label= this.INPUT_SIZE_1;
			break;
		case 2:
			
			label= this.INPUT_SIZE_2;
			break;
		case 3:
			
			label= this.INPUT_SIZE_3;
			break;
		case 4:
			
			label= this.INPUT_SIZE_4;
			break;
		case 5:
			
			label= this.INPUT_SIZE_5;
			break;
		case 6:
			
			label= this.INPUT_SIZE_6;
			break;
			
		case 7:
			
			label= this.INPUT_SIZE_7;
			break;
			
		case 8:
			
			label= this.INPUT_SIZE_8;
			break;
			
		case 9:
			
			label= this.INPUT_SIZE_9;
			break;
			
		case 10:
			
			label= this.INPUT_SIZE_10;
			break;

		default:
			label= this.INPUT_SIZE_1;
			break;
		}
		
		return "\""+label+"\"";
	}



	private void writeUndefined(ScreenIO sIO) {

		JavaClassElement.javaCodeBuffer.append("//new ScreenIOUndefined(" + sIO.getXCoord() + "," + sIO.getYCoord());
	
		JavaClassElement.javaCodeBuffer.append(",");

		JavaClassElement.javaCodeBuffer.append("IOModeType.AD_MI_");

		JavaClassElement.javaCodeBuffer.append(",\"");

		JavaClassElement.javaCodeBuffer.append(getLabelActor(sIO.getValue()));
					
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
	private void removeParantezI() {
		
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
	private void removeAdParameters() {

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

				if (parantezOpenToken.getTip().equals(TokenTipi.Karakter)&& parantezOpenToken.getDeger().equals('(') 
						&& equalsToken.getTip().equals(TokenTipi.Karakter) && equalsToken.getDeger().equals('=')
						&& adToken.getTip().equals(TokenTipi.Kelime) && adToken.getDeger().equals("AD")) {

					do {
						parantezCloseParameters = inputParameters.get(index+1);

						ADParameter.append(parantezCloseParameters.getDeger().toString());

						inputParameters.remove(index+1);

					} while (!(parantezCloseParameters.getTip().equals(TokenTipi.Karakter)
							&& parantezCloseParameters.getDeger().equals(')')));

					parantezOpenToken.setInputADParameters(new KelimeToken(("("+ADParameter.toString()), 0, 0, 0));
					inputParameters.remove(index);
				}
				
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}

	}
	
	

}