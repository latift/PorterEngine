package tr.com.vbt.java.general;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tr.com.vbt.java.AbstractJavaElement;
import tr.com.vbt.java.utils.ConvertUtilities;

//01 WS-NUM1 PIC 9 --> int ws-num1;
//01 WS-NUM1 PIC A --> String ws-num1;
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
public class JavaGeneralVariableElement extends AbstractJavaElement {
	
	final static Logger logger = LoggerFactory.getLogger(JavaGeneralVariableElement.class);
	

	private String type;

	private String dataType;
	
	private long length;
	
	private long lengthAfterDot;
	
	private String dataName;

	private String visibility;
	
	private String initialValue;

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

	public long getLength() {
		return length;
	}

	public void setLength(long length) {
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
	public boolean writeJavaToStream() throws Exception{ 
		
		super.writeJavaToStream();
		
		try {
			writeFieldAnnotation();
			
			dataType = (String) this.parameters.get("dataType");
			length=(long) this.parameters.get("length");
			dataName = (String) this.parameters.get("dataName");
			if(parameters.get("lengthAfterDot")!=null){
				lengthAfterDot=(long)parameters.get("lengthAfterDot");
			}
			if(parameters.get("initialValue")!=null){
				initialValue= (String) parameters.get("initialValue");
			}
			

			// 9 --> int

			/**
			 * *   A 253 length Alpha (letters, numbers, certain symbols)
N 29 length Numeric
I Integer
P 29 (length /2)  Packed
B 	Binary
D 4 Date (internal day number)
T 7 Time (internal day number and time)
L 1 Logical (TRUE or FALSE)
C 1 Control (whether modified)
			 */
			type=ConvertUtilities.getJavaVariableType(dataType, length, lengthAfterDot);

			JavaClassElement.javaCodeBuffer.append("public "+type + " ");
			if (dataName != null) {
				JavaClassElement.javaCodeBuffer.append(dataName.replace('-', '_'));
			} else {
				JavaClassElement.javaCodeBuffer
						.append("Untransmitted_Constant_Name");
			}
			if(type.equals("String")){ //Add new String("  ");
				JavaClassElement.javaCodeBuffer.append(" = new String(\"");
				/*for(int i=0; i<length;i++){
					JavaClassElement.javaCodeBuffer.append(" ");
				}*/
				JavaClassElement.javaCodeBuffer.append("\")");
			}
			
			else if(type.toLowerCase().equals("bigdecimal")){
				
				initBigDecimal();
			
			}
			
			else if(initialValue!=null && !initialValue.trim().isEmpty()){
				
				JavaClassElement.javaCodeBuffer.append(" =");
				JavaClassElement.javaCodeBuffer.append(initialValue);
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


	private void initBigDecimal() {
	
		JavaClassElement.javaCodeBuffer.append("=FCU.resetBigDecimal("+lengthAfterDot+")");
		
	}

	public long getLengthAfterDot() {
		return lengthAfterDot;
	}

	public void setLengthAfterDot(long lengthAfterDot) {
		this.lengthAfterDot = lengthAfterDot;
	}
	

}
