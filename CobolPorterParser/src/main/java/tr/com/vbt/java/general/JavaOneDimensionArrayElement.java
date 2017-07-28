package tr.com.vbt.java.general;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tr.com.vbt.java.AbstractJavaElement;
import tr.com.vbt.java.utils.ConvertUtilities;

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
 * 
 *  0414   2 D_SECIM    (A1/1:100)
 * @author 47159500
 * 
 */
public class JavaOneDimensionArrayElement extends AbstractJavaElement {

	final static Logger logger = LoggerFactory.getLogger(JavaOneDimensionArrayElement.class);
	
	private String type;

	private String dataType;
	
	private int length;
	
	private int lengthAfterDot;
	
	private String dataName;

	private String visibility;
	
	private int arrayLength;
	
	private int levelNumber;
	
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
			if(this.parameters.get("length")!=null){
				length=(int) this.parameters.get("length");
				
			}
			dataName = (String) this.parameters.get("dataName");
			levelNumber = (int) this.parameters.get("levelNumber");
			if(this.parameters.get("arrayLength")!=null){
				arrayLength=(int) this.parameters.get("arrayLength");
			}
			if(parameters.get("lengthAfterDot")!=null){
				lengthAfterDot=(int) parameters.get("lengthAfterDot");
			}

			type=ConvertUtilities.getJavaVariableType(dataType, arrayLength, lengthAfterDot);
	
			//String[4] SECIM_YURT_ICI_AKARYAKIT=new String[4];
			//int[15] ROL1=new int[15];  
			JavaClassElement.javaCodeBuffer.append("public "+type + "[] ");
			if (dataName != null) {
				JavaClassElement.javaCodeBuffer.append(dataName.replace('-', '_'));
			} else {
				JavaClassElement.javaCodeBuffer
						.append("Untransmitted_Constant_Name");
			}
			JavaClassElement.javaCodeBuffer.append("=new ");
			JavaClassElement.javaCodeBuffer.append(type + "["+arrayLength+"]");
			
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
	

}
