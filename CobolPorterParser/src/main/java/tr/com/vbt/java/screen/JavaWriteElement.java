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
import tr.com.vbt.lexer.ConversionLogModel;
import tr.com.vbt.natural.html.IOModeType;
import tr.com.vbt.natural.html.NaturalTagTypes;
import tr.com.vbt.natural.html.EngineIO;
import tr.com.vbt.natural.html.EngineIOIntegerInput;
import tr.com.vbt.natural.html.EngineIOLabel;
import tr.com.vbt.natural.html.EngineIOStringInput;
import tr.com.vbt.natural.html.EngineIOUndefined;
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
public class JavaWriteElement extends AbstractJavaElement {

	final static Logger logger = LoggerFactory.getLogger(JavaAtTopOfPageElement.class);

	// Paramaters: functionName;
	private List<AbstractToken> inputParameters;

	private List<EngineIO> screenInputOutputArray;

	EngineIO newScreenIO;

	int offset;

	long xCoord, yCoord;
	
	private List<AbstractToken> undefinedParameterList=new ArrayList<>();
	
	boolean isMap;
	
	boolean isMapTester;


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
			if(inputParameters==null || inputParameters.size()==0) {
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

			xCoord = (int)((long)currToken.getDeger()) - 1;

			yCoord = (int)((long)nextToken2.getDeger()) - 1;

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
			
			long maxLength=ConvertUtilities.getVariableMaxLength(currToken);
			
			value = JavaWriteUtilities.toCustomString(currToken).toString();
			
			String name;
			
			name ="\"" + value + "\"";
			
			if(currToken.isDiscardedWriteKeyword()){
				return true;
			}
			
			writeUndefinedTokens();
		
			if (currToken.isConstantVariableWithQuota() || currToken.isSystemVariable()) {

				newScreenIO = new EngineIOLabel((int)xCoord, (int)yCoord, IOModeType.AD_D, value, XCoordinationTypes.REFERANCE,
						XCoordinationTypes.EXACT,0,(int)maxLength, currToken.isConstantVariableWithQuota());
				
			} else if (currToken.getTip().equals(TokenTipi.Kelime)) { // #SECIM

				VariableTypes varType = ConvertUtilities.getVariableType(currToken);

				if (varType.equals(VariableTypes.INT_TYPE)|| varType.equals(VariableTypes.LONG_TYPE)) {

					newScreenIO = new EngineIOIntegerInput((int)xCoord, (int)yCoord, IOModeType.AD_D, name, value,
							XCoordinationTypes.REFERANCE, XCoordinationTypes.EXACT,0,(int)maxLength);

				}else{
					
					newScreenIO = new EngineIOStringInput((int)xCoord, (int)yCoord, IOModeType.AD_D, name, value,
							XCoordinationTypes.REFERANCE, XCoordinationTypes.EXACT,0,(int)maxLength);
				}
			} else if (currToken.getTip().equals(TokenTipi.Sayi)) { //

				newScreenIO = new EngineIOIntegerInput((int)xCoord, (int)yCoord, IOModeType.AD_D, name, value,
						XCoordinationTypes.REFERANCE, XCoordinationTypes.EXACT,0,(int)maxLength);
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
		newScreenIO = new EngineIOUndefined((int)xCoord, (int)yCoord, IOModeType.AD_D, sb.toString(), XCoordinationTypes.REFERANCE,  XCoordinationTypes.EXACT,0,ConverterConfiguration.DEFAULT_MAX_LENGTH_FOR_LABEL);
		
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
			
			long maxLength=ConvertUtilities.getVariableMaxLength(currToken);
			
			writeUndefinedTokens();
			
			if(arrayToken.getFirstDimension().getDeger().equals('*')){   //SCRLINES(*)
				value = JavaWriteUtilities.toCustomString(arrayToken).toString();
				
				long arrayLength;
				
				arrayLength=ConvertUtilities.getArrayLength(currToken);
				
				for(int i=0;i<arrayLength;i++){
				
					xCoord=xCoord+1;
						
					newScreenIO = new EngineIOLabel((int)xCoord, (int)yCoord, IOModeType.AD_D, value+"["+i+"]", XCoordinationTypes.EXACT,
						XCoordinationTypes.EXACT,0,(int)maxLength);
	
					screenInputOutputArray.add(newScreenIO);
	
				}
				
			}
			else{  //SCRLINES(i)
				value = JavaWriteUtilities.toCustomString(currToken).toString();
	
				if(currToken.getDeger().equals("_") || currToken.getDeger().equals("=")){
					newScreenIO = new EngineIOLabel((int)xCoord, (int)yCoord, IOModeType.AD_D, value, XCoordinationTypes.EXACT, XCoordinationTypes.EXACT,0,(int)maxLength);
					
				}else{
					newScreenIO = new EngineIOLabel((int)xCoord,(int) yCoord, IOModeType.AD_D, value+"["+ConvertUtilities.castToInt()+arrayToken.getFirstDimension().getDeger().toString()+"]", XCoordinationTypes.EXACT,
							XCoordinationTypes.EXACT,0,(int)maxLength);
					
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

			YCoordCarpan = (int) ((long)(currToken.getDeger()));
			if (nextToken.getDeger().equals("X")) {
				yCoord = YCoordCarpan * ConverterConfiguration.NATURAL_X_LENGTH+yCoord;
			} else if (nextToken.getDeger().equals("T")) {
				yCoord = YCoordCarpan * 8+yCoord;
			} else { // Hata durumda en azından boyle göstersin
				yCoord = YCoordCarpan * 1+yCoord;
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

		EngineIO sIO = null;
		
		/*if(isMap || isMapTester){
			JavaClassElement.javaCodeBuffer.append("natprog.");
		}*/
		JavaClassElement.javaCodeBuffer.append("write(");
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
			
		/*	if (sIO.getTagType().equals(NaturalTagTypes.LABEL)) {
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
			}*/
			
			if(sIO instanceof EngineIOUndefined)
			{
				writeUndefined(sIO);
			}else{
				writeLabel(sIO);
			}

			if (index < screenInputOutputArray.size() - 1) {
				JavaClassElement.javaCodeBuffer.append(",");
				JavaClassElement.javaCodeBuffer.append(JavaConstants.NEW_LINE);
			}
			
			if(xCoord>previousXCoord){
				
				JavaClassElement.javaCodeBuffer.append(JavaConstants.NEW_LINE);
			}
			

		}

		JavaClassElement.javaCodeBuffer.append(");");
		JavaClassElement.javaCodeBuffer.append(JavaConstants.NEW_LINE);

	}

	// new
	// ScreenIOIntegerInput(0,1,IOModeType.AD_MI_,"DIYEZ_SECIM",DIYEZ_SECIM,XCoordinationTypes.REFERANCE),
	private void writeNumberInputField(EngineIO sIO) {

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
	private void writeStringInputField(EngineIO sIO) {

		JavaClassElement.javaCodeBuffer.append("new ScreenIOStringInput(" + sIO.getXCoord() + "," + sIO.getYCoord());

		JavaClassElement.javaCodeBuffer.append(",");

		JavaClassElement.javaCodeBuffer.append("IOModeType.AD_MI_");

		JavaClassElement.javaCodeBuffer.append(",");

		
		JavaClassElement.javaCodeBuffer.append(sIO.getName());

		JavaClassElement.javaCodeBuffer.append(",");

		/*if(isMap || isMapTester){
			JavaClassElement.javaCodeBuffer.append("natprog.");
		}*/

		JavaClassElement.javaCodeBuffer.append(sIO.getValue());

		JavaClassElement.javaCodeBuffer.append(",");

		if (sIO.getxCoordinationType().equals(XCoordinationTypes.EXACT)) {
			JavaClassElement.javaCodeBuffer.append("XCoordinationTypes.EXACT , XCoordinationTypes.EXACT");
		} else {
			JavaClassElement.javaCodeBuffer.append("XCoordinationTypes.REFERANCE , XCoordinationTypes.EXACT");
		}
		
		JavaClassElement.javaCodeBuffer.append(",");
		
		JavaClassElement.javaCodeBuffer.append("0");
		
		JavaClassElement.javaCodeBuffer.append(",");
		
		JavaClassElement.javaCodeBuffer.append(sIO.getMaxLength());

		JavaClassElement.javaCodeBuffer.append(")");

	}
	private void writeLabel(EngineIO sIO) {

		JavaClassElement.javaCodeBuffer.append("new ScreenIOLabel(" + sIO.getXCoord() + "," + sIO.getYCoord());

		JavaClassElement.javaCodeBuffer.append(",");

		JavaClassElement.javaCodeBuffer.append("IOModeType.AD_MI_");

		JavaClassElement.javaCodeBuffer.append(",");


		JavaClassElement.javaCodeBuffer.append( sIO.getValue());
					
		JavaClassElement.javaCodeBuffer.append(",");

		if (sIO.getxCoordinationType().equals(XCoordinationTypes.EXACT)) {
			JavaClassElement.javaCodeBuffer.append("XCoordinationTypes.EXACT , XCoordinationTypes.EXACT");
		} else {
			JavaClassElement.javaCodeBuffer.append("XCoordinationTypes.REFERANCE , XCoordinationTypes.EXACT");
		}
		
		JavaClassElement.javaCodeBuffer.append(",");
		
		JavaClassElement.javaCodeBuffer.append("0");
		
		JavaClassElement.javaCodeBuffer.append(",");
		
		JavaClassElement.javaCodeBuffer.append(sIO.getMaxLength());

		JavaClassElement.javaCodeBuffer.append(")");

	}
	
	private void writeUndefined(EngineIO sIO) {

		JavaClassElement.javaCodeBuffer.append("new ScreenIOUndefined(" + sIO.getXCoord() + "," + sIO.getYCoord());
	
		JavaClassElement.javaCodeBuffer.append(",");

		JavaClassElement.javaCodeBuffer.append("IOModeType.AD_MI_");

		JavaClassElement.javaCodeBuffer.append(",\"");


		JavaClassElement.javaCodeBuffer.append( sIO.getValue());
					
		JavaClassElement.javaCodeBuffer.append("\",");

		if (sIO.getxCoordinationType().equals(XCoordinationTypes.EXACT)) {
			JavaClassElement.javaCodeBuffer.append("XCoordinationTypes.EXACT , XCoordinationTypes.EXACT");
		} else {
			JavaClassElement.javaCodeBuffer.append("XCoordinationTypes.REFERANCE , XCoordinationTypes.EXACT");
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
		if(inputParameters==null || inputParameters.size()<3) {
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
						&& adToken.getTip().equals(TokenTipi.Kelime) && adToken.isADParameters()) {

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
