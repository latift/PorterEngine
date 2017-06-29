package tr.com.vbt.java.general;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.java.AbstractJavaElement;
import tr.com.vbt.java.utils.ConvertUtilities;
import tr.com.vbt.natural.parser.datalayout.program.ElementProgramDataTypeNatural;
import tr.com.vbt.util.CustomStringUtils;

/*
 * *S**1 TAX-EXC-TEXT(A50)
   *S**1 REDEFINE TAX-EXC-TEXT
   *S**   2 TAX-EXC-ITEM(A5/10)
   *
   *	--> public RedefineSimpleToOneDimension TAX_EXC_ITEM = new RedefineSimpleToOneDimension(this,TAX-EXC-TEXT,10);
 */


public class JavaOneDimensionRedefineArrayOfSimpleElement extends AbstractJavaElement {
	
	final static Logger logger = LoggerFactory.getLogger(JavaOneDimensionRedefineArrayOfSimpleElement.class);


	private String dataName;

	private int arrayLength;
	
	
	private AbstractCommand redefinedCommand;
	
	private ElementProgramDataTypeNatural redCommandDataType;
	
	
	
	//*S**1 #SECIM     (N2)  
	@Override
	public boolean writeJavaToStream() throws Exception{ 
		
		super.writeJavaToStream();
		
		try {
			//length=(int) this.parameters.get("length");
			dataName = (String) this.parameters.get("dataName");
			arrayLength=(int) this.parameters.get("arrayLength");
			redefinedCommand=(AbstractCommand) this.parameters.get("redefinedCommand");
			
			redCommandDataType=(ElementProgramDataTypeNatural) redefinedCommand;
			
			
			/*
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
			}*/
			
			
			//public RedefineSimpleToOneDimension TAX_EXC_ITEM = new RedefineSimpleToOneDimension(this,TAX-EXC-TEXT,10);
			JavaClassElement.javaCodeBuffer.append("public RedefineSimpleToOneDimension "+(CustomStringUtils.replaceMiddleLineWithSubLine(dataName))+"=new RedefineSimpleToOneDimension(this,"+redCommandDataType.getDataName()+","+arrayLength+")");
			
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



	public String getDataName() {
		return dataName;
	}



	public void setDataName(String dataName) {
		this.dataName = dataName;
	}



	public int getArrayLength() {
		return arrayLength;
	}



	public void setArrayLength(int arrayLength) {
		this.arrayLength = arrayLength;
	}



	public AbstractCommand getRedefinedCommand() {
		return redefinedCommand;
	}



	public void setRedefinedCommand(AbstractCommand redefinedCommand) {
		this.redefinedCommand = redefinedCommand;
	}


	

}
