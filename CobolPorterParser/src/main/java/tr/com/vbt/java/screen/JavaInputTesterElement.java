package tr.com.vbt.java.screen;

import java.util.ArrayList;

import org.apache.log4j.Logger;


import tr.com.vbt.java.general.JavaClassElement;
import tr.com.vbt.java.general.JavaConstants;
import tr.com.vbt.java.utils.ConvertUtilities;
import tr.com.vbt.natural.html.EngineIO;
import tr.com.vbt.natural.html.XCoordinationTypes;
import tr.com.vbt.token.AbstractToken;

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

	
	JavaInputElement javaInputElement;
	
	final static Logger logger = Logger.getLogger(JavaInputTesterElement.class);

	
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

	
	
	public JavaInputTesterElement(JavaInputElement parent) {
		super();
		this.javaInputElement = parent;
		this.inputParameters=parent.inputParameters;
	}


	@Override
	public boolean writeJavaToStream() throws Exception{
		
		screenInputOutputArray = new ArrayList<>();
		
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
			logger.debug("//Conversion Error" + this.getClass() );
			JavaClassElement.javaCodeBuffer.append("//Conversion Error" + this.getClass());
			logger.error("//Conversion Error " + e.getMessage(), e);
			JavaClassElement.javaCodeBuffer.append(JavaConstants.NEW_LINE);
			ConvertUtilities.writeconversionErrors(e, this); 
		}

		return true;
	}

	
	// new
	// ScreenIOIntegerInput(0,1,IOModeType.AD_MI_,"DIYEZ_SECIM",DIYEZ_SECIM,XCoordinationTypes.REFERANCE),
	protected void writeNumberInputField(EngineIO sIO) {

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
	protected void writeStringInputField(EngineIO sIO) {

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
	protected void writeLabel(EngineIO sIO) {

		
		JavaClassElement.javaCodeBuffer.append("new ScreenIOLabel(" + sIO.getXCoord() + "," + sIO.getYCoord());

		JavaClassElement.javaCodeBuffer.append(",");

		JavaClassElement.javaCodeBuffer.append("IOModeType.AD_MI_");

		JavaClassElement.javaCodeBuffer.append(",");


		JavaClassElement.javaCodeBuffer.append( getLabelActor(sIO));
					
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
	
	private String getLabelActor(EngineIO sIO) {
		if(sIO.isDoubleQouta()){
			return sIO.getValue();
		}else if(sIO.getValue().equals("PROGRAM")){
			return "getMapName()";
		}else{
			return getLabelActor(sIO.getValue());
		}
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

	
	

}
