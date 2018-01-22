package tr.com.vbt.java.general;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;


import tr.com.vbt.java.AbstractJavaElement;
import tr.com.vbt.java.utils.ConvertUtilities;
import tr.com.vbt.java.utils.JavaFullWriteUtilities;
import tr.com.vbt.java.utils.JavaTrimWriteUtilities;
import tr.com.vbt.java.utils.JavaWriteUtilities;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.ArrayToken;
import tr.com.vbt.token.KelimeToken;

//*  5130   COMPRESS IDGIDBS-TOZLUK.ADI ' ' IDGIDBS-TOZLUK.SOYAD INTO                                                                    
//5132     MAP2.ADSOY1   --> MAP2.ADSOY1=TOZLUK.getAdi+' '+ TOZLUK.getSoyad;


//3740    COMPRESS FULL SUBSTR(IDGIDBS-TFORAN.TARIH,7,4) '-'                                                                          
//3750             SUBSTR(IDGIDBS-TFORAN.TARIH,4,2) '-'                                                                               
//3760             SUBSTR(IDGIDBS-TFORAN.TARIH,1,2) INTO TEMP_DATE LEAVE NO 

//			TEMP_DATE= stringTamamla(getPojoValue("TFORAn.getTarih").subString(6,10)) +
//					stringTamamla(getPojoValue("TFORAn.getTarih").subString(3,5)) +
//					"-"
//					stringTamamla(getPojoValue("TFORAn.getTarih").subString(0,2)) +
					

//0880      COMPRESS FULL SUBSTR(IDGIDBS-THESAP.MUHTARIH,7,4) INTO                                                                    
//0890                   SUBSTR(SACMUHTARYAG,1,4) LEAVE NO  
//0900      COMPRESS '-' INTO SUBSTR(SACMUHTARYAG,5,1) LEAVE NO 
//0900	  COMPRESS MAP.SBIC '%%%%%%%%' INTO BICA12 LEAVE NO
public class JavaCompressElementV3 extends AbstractJavaElement {

	final static Logger logger = Logger.getLogger(JavaCompressElementV3.class);

	private List<AbstractToken> sourceList = new ArrayList<AbstractToken>();

	private AbstractToken dest, source;

	private ArrayToken arrayTypeSource;
	
	private boolean isLeavingNo, isFull;
	
	boolean cast;

	@Override
	public boolean writeJavaToStream() throws Exception{
		super.writeJavaToStream();
		
	

		try {
			AbstractToken parameter;
			ArrayToken arrayParameter;
			sourceList = (List<AbstractToken>) this.parameters.get("source");
			dest = (AbstractToken) this.parameters.get("dest"); 
		
			if(controlAndWriteMoveToSubstringCommand()){
				return true;
			}
			
			if(this.parameters.get("isFull")!=null && this.parameters.get("isFull").equals("true")){
				isFull=true;
			}
			
			if( this.parameters.get("isLeavingNo")!=null){
				isLeavingNo = (boolean) this.parameters.get("isLeavingNo");
			}else{
				isLeavingNo = false;
			}

			compressToSimple();
			
			JavaClassElement.javaCodeBuffer.append(JavaConstants.DOT_WITH_COMMA + JavaConstants.NEW_LINE);

			
		} catch (Exception e) {
			logger.debug("//Conversion Error"+this.getClass()+this.getSourceCode().getSatirNumarasi()+this.getSourceCode().getCommandName());
			JavaClassElement.javaCodeBuffer.append("/*Conversion Error"+this.getClass()+this.getSourceCode().getSatirNumarasi()
					+this.getSourceCode().getCommandName()+"*/"+JavaConstants.NEW_LINE);
			logger.error("//Conversion Error:"+e.getMessage(), e); 
			ConvertUtilities.writeconversionErrors(e, this);
		}
		return true;

	}

	private boolean compressToSimple() throws Exception {
		
		boolean objectTypeSet=false;
		
		String typeOfCopyTo=ConvertUtilities.getTypeOfVariable(dest);
		
		//NATURAL CODE:2230   :.0 COMPRESS FULL AVEKDOGYIL - AVEKDOGAY 
		//setPojoValue("TVEKALET"DOGTARAVEKDOGYIL.getValue() + "-" + AVEKDOGAY.getValue() + "-" + AVEKDOGGUN.getValue());
		//Dogrusu:  	TVEKALET.setDogtar(FrameworkConvertUtilities.stringToSqlDate(MAP_P.AVEKDOGYIL + "-" + MAP_P.AVEKDOGAY + "-" + MAP_P.AVEKDOGGUN));
		JavaClassElement.javaCodeBuffer.append(JavaWriteUtilities.toCustomSetterString(dest));
		
		if(dest.isPojoVariable() || dest.isRedefinedVariable() || (dest.getLinkedToken()!=null && dest.getLinkedToken().isRedefinedVariable())){
			objectTypeSet=true;
		}
	
		logger.debug(JavaClassElement.javaCodeBuffer.substring(JavaClassElement.javaCodeBuffer.length()-20).toString());
		
		if (objectTypeSet) {
			//JavaClassElement.javaCodeBuffer.append("(");
		}
		else{
			JavaClassElement.javaCodeBuffer.append("=");
		}

		if(typeOfCopyTo!=null &&typeOfCopyTo.equalsIgnoreCase("date")){
			writeSourcePartToDate();
		}else{
			writeSourcePart();
		}
		if (objectTypeSet) {
		
			JavaClassElement.javaCodeBuffer.append(")");
		
		}
		
	
		return true;
	}

	
	private void writeSourcePartToDate() throws Exception {
		String castStr;
		
		StringBuffer sb=new StringBuffer();

		for (int i = 0; i < sourceList.size(); i++) {

			source = sourceList.get(i);
			
			//Compress de - işlemi olmaz. Compress string join yapar.
			if(source.isKarakter('-')){
				source=new KelimeToken<>("-",0,0,0);
				source.setConstantVariableWithQuota(true); 
			}

			if(isFull){  // Trim yapma
				
				String typeOfCopyTo="String"; //Date olmasına ragmen String yazıyor ve string operasyonu yapıyoruz. Sonra en son oluşan Stringe cast yapacağız.
				
				String typeOfCopyFrom=ConvertUtilities.getTypeOfVariable(source);
				
				if(typeOfCopyTo==null){
					typeOfCopyTo="";
				}
				
				if(typeOfCopyFrom==null){
					typeOfCopyFrom="";
				}
				
				sb.append(JavaFullWriteUtilities.toCustomString(source));
				
				if(i<sourceList.size()-1){
					sb.append("+");
				}
				
				
				
			}

		}
		
		cast=JavaWriteUtilities.addCast(dest,source);	
		
		JavaClassElement.javaCodeBuffer.append(sb);
	
		JavaWriteUtilities.endCast(cast);
	
		JavaWriteUtilities.addTypeChangeFunctionToEnd(dest,source);
	
		
		
		
	}

	private void writeSourcePart() throws Exception {
		
		String castStr;

		for (int i = 0; i < sourceList.size(); i++) {

			source = sourceList.get(i);
			
			//Compress de - işlemi olmaz. Compress string join yapar.
			if(source.isKarakter('-')){
				source=new KelimeToken<>("-",0,0,0);
				source.setConstantVariableWithQuota(true); 
			}

			if(isFull){  // Trim yapma
				
				String typeOfCopyTo=ConvertUtilities.getTypeOfVariable(dest);
				
				String typeOfCopyFrom=ConvertUtilities.getTypeOfVariable(source);
				
				if(typeOfCopyTo==null){
					typeOfCopyTo="";
				}
				
				if(typeOfCopyFrom==null){
					typeOfCopyFrom="";
				}
				
				if(!(typeOfCopyTo.equalsIgnoreCase("string") && typeOfCopyFrom.equalsIgnoreCase("long"))){
					
					cast=JavaWriteUtilities.addCast(dest,source);	
				
				}
				
				JavaClassElement.javaCodeBuffer.append(JavaFullWriteUtilities.toCustomString(source));
				
				if(!(typeOfCopyTo.equalsIgnoreCase("string") && typeOfCopyFrom.equalsIgnoreCase("long"))){
		
					JavaWriteUtilities.endCast(cast);
				
					JavaWriteUtilities.addTypeChangeFunctionToEnd(dest,source);
				
				}
				
			}else{ //Trim yap
				
				cast=JavaWriteUtilities.addCast(dest,source);
				
				JavaClassElement.javaCodeBuffer.append(JavaTrimWriteUtilities.toCustomString(source));
				
				JavaWriteUtilities.endCast(cast);
				
				JavaWriteUtilities.addTypeChangeFunctionToEnd(dest,source);
			}

			
			
			
			
			
			if (i < sourceList.size() - 1) {
				JavaClassElement.javaCodeBuffer.append(" + ");
				if(!isLeavingNo){
					JavaClassElement.javaCodeBuffer.append("\" \"");
					JavaClassElement.javaCodeBuffer.append(" + ");
				}
			}
			
		}
		
	}
	
	//0880      COMPRESS FULL SUBSTR(IDGIDBS-THESAP.MUHTARIH,7,4) INTO                                                                    
	//0890                   SUBSTR(SACMUHTARYAG,1,4) LEAVE NO  
	// moveToSubstring(SACMUHTARYAG, 1, 5, FCU.basasifirEkle(PBASVURUTARIHI.substring(0, 4),4));
		private boolean controlAndWriteMoveToSubstringCommand()  throws Exception{
		
			if(dest==null || !dest.isSubstringCommand()){
				return false;
			}
			
			try {
				
				int startIndex=dest.getSubStringStartIndex();
				int endIndex=dest.getSubStringEndIndex()+startIndex;
					
					dest.setSubstringCommand(false);
					
					JavaClassElement.javaCodeBuffer.append("moveToSubstring(this,\""+ JavaWriteUtilities.toCustomString(dest)+"\","+startIndex+","+endIndex+",");
						
					writeSourcePart();

					JavaClassElement.javaCodeBuffer.append(")"+JavaConstants.DOT_WITH_COMMA + JavaConstants.NEW_LINE);
			} catch (Exception e) {
				logger.debug("//Conversion Error" + this.getClass() + this.getSourceCode().getSatirNumarasi()
						+ this.getSourceCode().getCommandName());
				JavaClassElement.javaCodeBuffer
						.append("/*Conversion Error" + this.getClass() + this.getSourceCode().getSatirNumarasi()
								+ this.getSourceCode().getCommandName() + "*/" + JavaConstants.NEW_LINE);
				logger.error("//Conversion Error:" + e.getMessage(), e);
				ConvertUtilities.writeconversionErrors(e, this);
			}
			JavaClassElement.javaCodeBuffer.append(JavaConstants.NEW_LINE);
			
			return true;
		}
		
		

}
