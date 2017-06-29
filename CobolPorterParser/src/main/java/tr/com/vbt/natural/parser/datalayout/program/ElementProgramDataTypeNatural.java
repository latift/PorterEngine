package tr.com.vbt.natural.parser.datalayout.program;

import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.DataTypeMapConverter;
import tr.com.vbt.cobol.parser.Levelable;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.token.AbstractToken;

/* 		1 #T-USER     (A8)  	 Local Normal Değişken
 * 
 * (A15)
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
**/
public class ElementProgramDataTypeNatural extends AbstractCommand implements Levelable, DataTypeMapConverter {

	protected String dataName; // mandatory
	
	private String dataType; // 9, X, A //mandatory

	private int length; // (5) Parantez içindeki ifade //optional
	
	private int lengthAfterDot; // Noktanin Solundali
	
	private String initialValue; //int yada String farketmez. Burada String olarak tutulur. JAvaCreate esnasında tipine göre doğrusu set edilir.


	@Override
	public String toString() {
		return this.dataName+" Len:"+this.length+"  Level:"+this.levelNumber+" Initial:"+initialValue+" .";
		
	}

		
	public ElementProgramDataTypeNatural(AbstractToken baseToken,
			List<AbstractToken> tokenListesi, AbstractCommand parent) {
		super(ReservedNaturalKeywords.PROGRAM_DATA_TYPE, "GENERAL.PROGRAM_DATA_TYPE");
	}

	public ElementProgramDataTypeNatural(String elementName, String detailedCobolName) {
		super(elementName, detailedCobolName);
	}

	@Override
	public String exportContents() {
		StringBuilder sb = new StringBuilder();
		sb.append(" " + ReservedNaturalKeywords.PROGRAM_DATA_TYPE + "=\""
				+ this.levelNumber + " " 
				+ this.dataName + " "  
				+" Length:"+ this.length 
				+ " DecimalLength:" +this.lengthAfterDot
				+" Initial Value:"+initialValue+" ."
				+"\"\n");
		return sb.toString();
	}

	@Override
	public boolean generateDetailedCobolName() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String exportCommands() {
		StringBuilder sb = new StringBuilder();
		sb.append(" " + ReservedNaturalKeywords.PROGRAM_DATA_TYPE + "=\""
				+ this.levelNumber + " " 
				+ this.dataName + " "  
				+" Length:"+ this.length 
				+ " DecimalLength:" +this.lengthAfterDot
				+" Initial Value:"+initialValue+" ."
				+"\"\n");
		return sb.toString();
	}

	public String getDataName() {
		return dataName;
	}

	public void setDataName(String dataName) {
		this.dataName = dataName;
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


	public int getLengthAfterDot() {
		return lengthAfterDot;
	}


	public void setLengthAfterDot(int lengthAfterDot) {
		this.lengthAfterDot = lengthAfterDot;
	}


	@Override
	public void generateDataTypeConversionString(
			List<AbstractCommand> commandList, int commandIndex) {
		//*S**01 TRAFIK VIEW OF TFK-TRAFIK --> TRAFIK
		/*
				String pojoName;
				MapManager manager=MapManagerNaturalImpl.getInstance();
						
				int offset=0;
				int endPoint;
			  
						if(this.levelNumber==1){ //TFK-ADTRH --> TFK-ADTRH Hic bir şey yapma
						
						}else if(this.levelNumber==2){ //Atası redefinedir. 
							
							ElementProgramGrupNatural parentGroupCommand;
							ElementProgramDataTypeNatural parentRedefinedCommand;
							
							parentGroupCommand=(ElementProgramGrupNatural) Utility.findParentCommand(this, commandList,commandIndex);
							parentRedefinedCommand=(ElementProgramDataTypeNatural) parentGroupCommand.getRedefinedCommand();
							String parentDataName=parentRedefinedCommand.getDataName();
							
							offset=Utility.calculateOffset(this, commandList,commandIndex);
							pojoName=Utility.viewNameToPojoName(this.dataName);
							endPoint=offset+length;
							
							if(parentRedefinedCommand.getDataType().equals("N")){
								if(this.getDataType().equals("N")){
									this.dataTypeGetterMapString="Integer.parseInt(((String)"+parentDataName+").substring[1,4])";
									
									
									//Setter Sample: MOVE TAR1 TO NEXT-KEY_LIMAN
									// NEXT-KEY= ((String)NEXT-KEY).subString(0,1)+ TAR1+"+ ((String)NEXT-KEY).subString(4);
									this.dataTypeSetterMapString=parentDataName+"=((String)"+parentDataName+").subString(0,"+offset+")+ TAR1+ ((String)"+parentDataName+").subString("+endPoint+")";
									
								}else { // this.getDataType() N değilse
								
									
									//Getter  Sample: MOVE NEXT-KEY_LIMAN TO TAR1
									// --> TAR1= ((String)NEXT-KEY).substring[1,4];
									this.dataTypeGetterMapString="((String)"+parentDataName+").substring["+offset+","+endPoint+"];";
									
									//Setter Sample: MOVE TAR1 TO NEXT-KEY_LIMAN
									// NEXT-KEY= Integer.parseInt(((String)NEXT-KEY).subString(0,1)+ TAR1+"+ ((String)NEXT-KEY).subString(4));
									
									this.dataTypeSetterMapString= parentDataName+"=Integer.parseInt(((String)"+parentDataName+").subString(0,"+offset+")+ TAR1((String)"+parentDataName+").subString("+endPoint+"))";
									
								}
							}else { // parentRedefinedCommand. data tipi N değilse
								
									if(this.getDataType().equals("N")){
										
										//Getter  Sample: MOVE NEXT-KEY_LIMAN TO TAR1
										// --> TAR1= Integer.parseInt((NEXT-KEY).substring[1,4]));
										this.dataTypeGetterMapString="Integer.parseInt(("+parentDataName+").substring["+offset+","+length+"]))";
										
										//Setter Sample: MOVE TAR1 TO NEXT-KEY_LIMAN
										// NEXT-KEY= ((String)NEXT-KEY).subString(0,1)+ TAR1+"+ ((String)NEXT-KEY).subString(4);
										this.dataTypeSetterMapString=parentDataName+"= ((String)"+parentDataName+").subString(0,"+offset+") + TAR1 + ((String)"+ parentDataName+").subString(4)";
										
									}else { // this.getDataType() N değilse
										
										
										//Getter  Sample: MOVE NEXT-KEY_LIMAN TO TAR1
										// --> TAR1= ((String)NEXT-KEY).substring[1,4];
										this.dataTypeGetterMapString="((String)"+parentDataName+").substring["+offset+","+length+"]";
										
										//Setter Sample: MOVE TAR1 TO NEXT-KEY_LIMAN
										// NEXT-KEY= NEXT-KEY.subString(0,1)+ TAR1+"+ NEXT-KEY.subString(4);
										this.dataTypeSetterMapString="((String)"+parentDataName+").substring["+offset+","+length+"]"+"%XXX%"+"((String)"+parentDataName+").substring["+endPoint+"]";
										
									}
							}
							manager.registerCommandToLocalVariablesMap(this.dataName, this);
						}
						return;
						*/
		
	}


	public String getInitialValue() {
		return initialValue;
	}


	public void setInitialValue(String initialValue) {
		this.initialValue = initialValue;
	}
	
	
	
}
