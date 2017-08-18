package tr.com.vbt.java.general;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tr.com.vbt.java.AbstractJavaElement;
import tr.com.vbt.java.utils.ConvertUtilities;
import tr.com.vbt.java.utils.JavaWriteUtilities;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.ArrayToken;

//*  5130   COMPRESS IDGIDBS-TOZLUK.ADI ' ' IDGIDBS-TOZLUK.SOYAD INTO                                                                    
//5132     MAP2.ADSOY1   --> MAP2.ADSOY1=TOZLUK.getAdi+' '+ TOZLUK.getSoyad;
public class JavaCompressElement2 extends AbstractJavaElement {

	final static Logger logger = LoggerFactory.getLogger(JavaCompressElement2.class);

	private List<AbstractToken> sourceList = new ArrayList<AbstractToken>();

	private AbstractToken dest, source;

	private ArrayToken arrayTypeSource;
	
	private boolean isLeavingNo;

	@Override
	public boolean writeJavaToStream() throws Exception{
		super.writeJavaToStream();

		try {
			AbstractToken parameter;
			ArrayToken arrayParameter;
			sourceList = (List<AbstractToken>) this.parameters.get("source");
			dest = (AbstractToken) this.parameters.get("dest"); // TODO Bi
			if( this.parameters.get("isLeavingNo")!=null){
				isLeavingNo = (boolean) this.parameters.get("isLeavingNo");
			}else{
				isLeavingNo = false;
			}

			if (dest.isPojoVariable()) {
			
				compressToPojo();
		
			} else if (dest instanceof ArrayToken) { // TODO Bu case analiz
														// edilmeli.
				arrayTypeSource = (ArrayToken) dest;
		
				compressToArray(isLeavingNo);
			
			} else if (dest.isRecordVariable()) {
				// 3990 COMPRESS SONVALORGUN '/' SONVALORAY '/' SONVALORYIL INTO
				// MAP_DIZISI.D_SONVALOR(PFSAY) LEAVE NO
				compressToRecord(isLeavingNo);
				
			} else {
				
				compressToSimple(isLeavingNo);
				
			}
			
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

	private boolean compressToRecord(boolean isLeavingNo) throws Exception {
		
		JavaClassElement.javaCodeBuffer.append(JavaWriteUtilities.toCustomString(dest));// MAP_DIZISI
		
		JavaClassElement.javaCodeBuffer.append(" = ");

		writeFromPartOfCompressCommand(isLeavingNo);

		return false;
	}

	private void writeFromPartOfCompressCommand(boolean isLeavingNo) throws Exception {

		for (int i = 0; i < sourceList.size(); i++) {

			source = sourceList.get(i);

			JavaClassElement.javaCodeBuffer.append(JavaWriteUtilities.toCustomString(source));

			if (i < sourceList.size() - 1) {
				JavaClassElement.javaCodeBuffer.append(" + ");
				if(!isLeavingNo){
					JavaClassElement.javaCodeBuffer.append("\" \" + ");					
				}
			}
		}
		if(!isLeavingNo){
			JavaClassElement.javaCodeBuffer.append(JavaConstants.DOT_WITH_COMMA+JavaConstants.NEW_LINE);
			JavaClassElement.javaCodeBuffer.append(JavaWriteUtilities.toCustomString(dest)+" = "+JavaWriteUtilities.toCustomString(dest)+".replaceAll(\"null \", \"\")");
		}else{
			JavaClassElement.javaCodeBuffer.append(JavaConstants.DOT_WITH_COMMA+JavaConstants.NEW_LINE);
			JavaClassElement.javaCodeBuffer.append(JavaWriteUtilities.toCustomString(dest)+" = "+JavaWriteUtilities.toCustomString(dest)+".replaceAll(\"null\", \"\")");
		}
	}

	private boolean compressToSimple(boolean isLeavingNo) throws Exception {
		
		if (dest.isRedefinedVariable()) {
			JavaClassElement.javaCodeBuffer.append(dest.getDeger().toString().replaceAll("-", "_") + ".setValue(");
		} else {
			JavaClassElement.javaCodeBuffer.append(dest.getDeger().toString().replaceAll("-", "_") + " = ");
		}

		writeFromPartOfCompressCommand(isLeavingNo);

		if (dest.isRedefinedVariable()) {
		
			JavaClassElement.javaCodeBuffer.append(")");
		
		}
		
	
		return true;
	}

	private boolean compressToArray(boolean isLeavingNo) throws Exception {
		
		JavaClassElement.javaCodeBuffer.append(JavaWriteUtilities.toCustomString(arrayTypeSource));
	
		JavaClassElement.javaCodeBuffer.append(" = ");
	

		writeFromPartOfCompressCommand(isLeavingNo);
		
		if (dest.isRedefinedVariable()) {
		
			JavaClassElement.javaCodeBuffer.append(")");
		
		}
			return true;

	}

	private boolean compressToPojo() {
		
		JavaClassElement.javaCodeBuffer.append("TODO:ImplementCompressToPojoInJavaCompressElement2: " + dest.getDeger());
		
		return false;
	}

}
