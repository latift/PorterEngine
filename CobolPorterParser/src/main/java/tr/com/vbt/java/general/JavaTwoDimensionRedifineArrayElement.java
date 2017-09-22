package tr.com.vbt.java.general;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.java.AbstractJavaElement;
import tr.com.vbt.java.utils.ConvertUtilities;
import tr.com.vbt.util.CustomStringUtils;

//*S**01 #SP-STATUS-DIZI(A2/900)                                                                  
//*S**01 REDEFINE #SP-STATUS-DIZI                                                                 
//*S**  02 #SP-STATUS-SAYFA(A2/60,15)  -->  


public class JavaTwoDimensionRedifineArrayElement extends AbstractJavaElement {
	
	final static Logger logger = LoggerFactory.getLogger(JavaTwoDimensionRedifineArrayElement.class);


	private String type;

	private String dataType;
	
	private int length;
	
	private int lengthAfterDot;
	
	private String dataName;

	private String visibility;
	
	private int arrayLength;
	
	private int arrayLength2;
	
	private AbstractCommand redefinedCommand;
	
	
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
	public boolean writeJavaToStream() throws Exception{ 
		
		super.writeJavaToStream();
		
		try {
			dataType = (String) this.parameters.get("dataType");
			//length=(int) this.parameters.get("length");
			dataName = (String) this.parameters.get("dataName");
			arrayLength=(int)((long) this.parameters.get("arrayLength"));
			arrayLength2=(int) ((long)this.parameters.get("arrayLength2"));
			redefinedCommand=(AbstractCommand) this.parameters.get("redefinedCommand");
			
			
			if(parameters.get("lengthAfterDot")!=null){
				lengthAfterDot=(int)((long) parameters.get("lengthAfterDot"));
			}

			
			if (dataType.startsWith("A")) {
				type=ConvertUtilities.getJavaVariableType("A",0,0);
			}else if (dataType.startsWith("N")) {
				type=ConvertUtilities.getJavaVariableType("N",0,0);
			}else if (dataType.startsWith("I")) {
				type=ConvertUtilities.getJavaVariableType("I",0,0);
			}else if (dataType.startsWith("D")) {
				type=ConvertUtilities.getJavaVariableType("D",0,0);
			}else if (dataType.startsWith("T")) {
				type=ConvertUtilities.getJavaVariableType("D",0,0);
			}else if (dataType.startsWith("L")) {
				type=ConvertUtilities.getJavaVariableType("L",0,0);
			}else if (dataType.startsWith("C")) {
				type=ConvertUtilities.getJavaVariableType("C",0,0);
			}else if (dataType.startsWith("P")) {
				type=ConvertUtilities.getJavaVariableType("P",0,0);
			} else {
				type = "Undefined Data Type:";
			}
			
			
			//RedefineOneDimensionToTwoDimension ref1=new RedefineOneDimensionToTwoDimension(SIRALIMAN_DIZI1,60,15,900);
			JavaClassElement.javaCodeBuffer.append("public RedefineOneDimensionToTwoDimension "+(CustomStringUtils.replaceMiddleLineWithSubLine(dataName))+"=new RedefineOneDimensionToTwoDimension("+redefinedCommand.getCommandName()+",60,15,900)");
			
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
