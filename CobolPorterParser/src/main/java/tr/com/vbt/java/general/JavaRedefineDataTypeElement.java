package tr.com.vbt.java.general;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tr.com.vbt.java.AbstractJavaElement;
import tr.com.vbt.java.utils.ConvertUtilities;
import tr.com.vbt.lexer.ConversionLogModel;

//*S**01 #SECIM-YURT-ICI-AKARYAKIT(A1/4)  --> String[4] SECIM_YURT_ICI_AKARYAKIT=new String[4];
//*S**01 #ROL1(N1/15) --> int[15] ROL1=new int[15];  

/**
 *  * (A15)
 * (N8)	--> int
 * (N10)	--> double
 * 	(D)  -->Date
 * (P7) --> 
 * (C) -->         
 * 
 *   A 253 length Alpha (letters, numbers, certain symbols)
	N 29 length Numeric
	I Integer
	P 29 (length /2)  Packed
	B 	Binary
	D 4 Date (internal day number)
	T 7 Time (internal day number and time)
	L 1 Logical (TRUE or FALSE)
	C 1 Control (whether modified)
 * 
 * @author 47159500
 * 
 */
public class JavaRedefineDataTypeElement extends AbstractJavaElement {
	
	final static Logger logger = LoggerFactory.getLogger(JavaRedefineDataTypeElement.class);
	

	private String type;

	private String dataType;
	
	private int length;
	
	private int lengthAfterDot;
	
	private String dataName;

	private String visibility;
	
	private String redefinedDataType;
	
	private String redefinedDataName;
	
	private int redefineStartIndex;
	
	private int redefineEndIndex;


	private ConversionLogModel logModel;
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getVisibility() {
		return visibility;
	}

	public void setVisibility(String visibility) {
		this.visibility = visibility;
	}

	
	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public String getDataName() {
		return dataName;
	}

	public void setDataName(String dataName) {
		this.dataName = dataName;
	}

	//*S**1 #SECIM     (N2)  
	@Override
	public boolean writeJavaToStream() throws Exception{ super.writeJavaToStream();
		try {
			
			dataType = (String) this.parameters.get("dataType");
			//length=(int) this.parameters.get("length");
			dataName = (String) this.parameters.get("dataName");
			
			redefinedDataType= (String) this.parameters.get("redefinedDataType");
			
			redefinedDataName= (String) this.parameters.get("redefinedDataName");
			
			try {
				redefineStartIndex= (int) this.parameters.get("redefineStartIndex");
					
				redefineEndIndex= (int) this.parameters.get("redefineEndIndex");
			} catch (Exception e) {
				dataType="A";
				redefinedDataType="A";
				dataName="TEST";
				redefinedDataName="TEST";
				logger.debug(e.getMessage(),e);
				
				redefineStartIndex=0;
				
				redefineEndIndex=0;
			}		
			
			logModel=ConversionLogModel.getInstance();
			
			//Eğer dataType Float ise redefinedDataType da Float ise
			if(dataType.equalsIgnoreCase("N") && redefinedDataType.equalsIgnoreCase("N")){
			//RedefinedFloatForFloat dataName=new RedefinedFloatForFloat(ShortImpl.this,redefinedDataName,redefineStartIndex,redefineEndIndex);
				JavaClassElement.javaCodeBuffer.append("RedefinedFloatForFloat " +dataName.replaceAll("-", "_")+"=new RedefinedFloatForFloat(this,\""+redefinedDataName+"\","+redefineStartIndex+","+redefineEndIndex+")");
			}
			//Eğer dataType Alphabet ise redefinedDataType da Number ise
			else if(dataType.equalsIgnoreCase("A") && redefinedDataType.equalsIgnoreCase("N")){
			//RedefinedAlphabetForNumber dataName=new RedefinedAlphabetForNumber(ShortImpl.this,redefinedDataName,redefineStartIndex,redefineEndIndex);
				JavaClassElement.javaCodeBuffer.append("RedefinedAlphabetForNumber " +dataName.replaceAll("-", "_")+"=new RedefinedAlphabetForNumber(this,\""+redefinedDataName+"\","+redefineStartIndex+","+redefineEndIndex+")");
			}
			//Eğer dataType Number ise redefinedDataType da Alphabet ise
			else if(dataType.equalsIgnoreCase("N") && redefinedDataType.equalsIgnoreCase("A")){
				JavaClassElement.javaCodeBuffer.append("RedefinedNumberForAlphabet " +dataName.replaceAll("-", "_")+"=new RedefinedNumberForAlphabet(this,\""+redefinedDataName+"\","+redefineStartIndex+","+redefineEndIndex+")");
			}else if(dataType.equalsIgnoreCase("A") && redefinedDataType.equalsIgnoreCase("A")){
				JavaClassElement.javaCodeBuffer.append("RedefinedAlphabetForAlphabet " +dataName.replaceAll("-", "_")+"=new RedefinedAlphabetForAlphabet(this,\""+redefinedDataName+"\","+redefineStartIndex+","+redefineEndIndex+")");
			}else{
				JavaClassElement.javaCodeBuffer.append("RedefinedNumberForAlphabet " +dataName.replaceAll("-", "_")+"=new RedefinedNumberForAlphabet(this,\""+redefinedDataName+"\","+redefineStartIndex+","+redefineEndIndex+")");
			}
			
			JavaClassElement.javaCodeBuffer.append(JavaConstants.DOT_WITH_COMMA);
			JavaClassElement.javaCodeBuffer.append(JavaConstants.NEW_LINE);
		} catch (Exception e) {
			logger.debug("//Conversion Error"+this.getClass()+this.getSourceCode().getSatirNumarasi()+this.getSourceCode().getCommandName());
			JavaClassElement.javaCodeBuffer.append("/*Conversion Error"+this.getClass()+this.getSourceCode().getSatirNumarasi()
					+this.getSourceCode().getCommandName()+"*/"+JavaConstants.NEW_LINE);
			logger.error("//Conversion Error:"+e.getMessage(), e); 
			ConvertUtilities.writeconversionErrors(e, this);
		}
		return true;
	}

	public int getLengthAfterDot() {
		return lengthAfterDot;
	}

	public void setLengthAfterDot(int lengthAfterDot) {
		this.lengthAfterDot = lengthAfterDot;
	}

	public String getRedefinedDataType() {
		return redefinedDataType;
	}

	public void setRedefinedDataType(String redefinedDataType) {
		this.redefinedDataType = redefinedDataType;
	}

	public String getRedefinedDataName() {
		return redefinedDataName;
	}

	public void setRedefinedDataName(String redefinedDataName) {
		this.redefinedDataName = redefinedDataName;
	}

	public int getRedefineStartIndex() {
		return redefineStartIndex;
	}

	public void setRedefineStartIndex(int redefineStartIndex) {
		this.redefineStartIndex = redefineStartIndex;
	}

	public int getRedefineEndIndex() {
		return redefineEndIndex;
	}

	public void setRedefineEndIndex(int redefineEndIndex) {
		this.redefineEndIndex = redefineEndIndex;
	}
	
	

}
