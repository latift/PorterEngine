package tr.com.vbt.natural.parser.datalayout.program.redefiners;

import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.DataTypeMapConverter;
import tr.com.vbt.cobol.parser.Levelable;
import tr.com.vbt.java.util.Utility;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.patern.MapManager;
import tr.com.vbt.patern.MapManagerNaturalImpl;
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
public class ElementRedefineDataTypeOfSimpleDataType extends AbstractCommand implements Levelable, DataTypeMapConverter {

	protected long levelNumber; // mandatory

	protected String dataName; // mandatory
	
	private String dataType; // 9, X, A //mandatory

	private long length; // (5) Parantez içindeki ifade //optional
	
	private long lengthAfterDot; // Noktanin Solundali
	
	private String redefinedDataType;
	
	private String redefinedDataName;
	
	private int redefineStartIndex;
	
	private int redefineEndIndex;
	
	private int offset;
	
	
	@Override
	public String toString() {
		return this.dataName+" Len:"+this.length+"  Level:"+this.levelNumber;
	}

		
	public ElementRedefineDataTypeOfSimpleDataType(AbstractToken baseToken,
			List<AbstractToken> tokenListesi, AbstractCommand parent) {
		super(ReservedNaturalKeywords.PROGRAM_REDEFINE_DATA_TYPE, "GENERAL.PROGRAM_REDEFINE_DATA_TYPE");
	}

	public ElementRedefineDataTypeOfSimpleDataType(String elementName, String detailedCobolName) {
		super(elementName, detailedCobolName);
	}

	@Override
	public String exportContents() {
		StringBuilder sb = new StringBuilder();
		sb.append(" " + ReservedNaturalKeywords.PROGRAM_REDEFINE_DATA_TYPE + "=\""
				+ this.levelNumber + " " + this.dataName + " " + 
				" Length:"+ this.length + 
				" redefinedDataName:" +this.redefinedDataName+
				" redefinedDataType:" +this.redefinedDataType+
				" redefineStartIndex:" +this.redefineStartIndex+
				" redefineEndIndex:" +this.redefineEndIndex
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
		sb.append(" " + ReservedNaturalKeywords.PROGRAM_REDEFINE_DATA_TYPE + "=\""
				+ this.levelNumber + " " + this.dataName + " " + 
				" Length:"+ this.length + 
				" redefinedDataName:" +this.redefinedDataName+
				" redefinedDataType:" +this.redefinedDataType+
				" redefineStartIndex:" +this.redefineStartIndex+
				" redefineEndIndex:" +this.redefineEndIndex
				+"\"\n");
		return sb.toString();
	}

	public String getDataName() {
		return dataName;
	}

	public void setDataName(String dataName) {
		this.dataName = dataName;
	}

	public long getLevelNumber() {
		return levelNumber;
	}

	public void setLevelNumber(long levelNumber) {
		this.levelNumber = levelNumber;
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


	public long getLengthAfterDot() {
		return lengthAfterDot;
	}


	public void setLengthAfterDot(long lengthAfterDot) {
		this.lengthAfterDot = lengthAfterDot;
	}


	@Override
	public void generateDataTypeConversionString(
			List<AbstractCommand> commandList, int commandIndex) {
		//*S**01 TRAFIK VIEW OF TFK-TRAFIK --> TRAFIK
				String pojoName;
				MapManager manager=MapManagerNaturalImpl.getInstance();
						
		
			  /***S**1 #NEXT-KEY (A20)                                                                           
					*S**1 REDEFINE #NEXT-KEY                                                                        
					*S**  2 #NEXT-KEY-ID   (A1)                                                                     
					*S**  2 #NEXT-KEY-LIMAN(A3)                                                                     
					*S**  2 #NEXT-KEY-TARIH(A8)                                                                     
					*S**  2 #NEXT-KEY-SAAT (A4)                                                                     
					*S**  2 #NEXT-KEY-SEFNO(A4)*/ 
						if(this.levelNumber==1){ //TFK-ADTRH --> TFK-ADTRH Hic bir şey yapma
						
						}else if(this.levelNumber==2){ //Atası redefinedir. 
							
							ElementProgramRedefineGrupNatural parentRedefineCommand;
							ElementRedefineDataTypeOfSimpleDataType parentRedefinedCommand;
							
							parentRedefineCommand=(ElementProgramRedefineGrupNatural) Utility.findParentCommand(this, commandList,commandIndex);
							parentRedefinedCommand=(ElementRedefineDataTypeOfSimpleDataType) parentRedefineCommand.getRedefinedCommand();
							String parentDataName=parentRedefinedCommand.getDataName();
							
							offset=Utility.calculateOffset(this, commandList,commandIndex);
							pojoName=Utility.viewNameToPojoName(this.dataName);
							redefineEndIndex=offset+(int)length;
							
							if(parentRedefinedCommand.getDataType().equals("N")){
								if(this.getDataType().equals("N")){
									/**
									 *  *S**1 #NEXT-KEY (N20)                                                                           
										*S**1 REDEFINE #NEXT-KEY                                                                        
										*S**  2 #NEXT-KEY-ID   (N1)                                                                     
										*S**  2 #NEXT-KEY-LIMAN(N3)
									 */
									//Getter  Sample: MOVE NEXT-KEY_LIMAN TO TAR1
									// --> TAR1= Integer.parseInt(((String)NEXT-KEY).substring[1,4]);
									// NEXT-KEY_LIMAN yerine  this.dataTypeGetterMapString basilacak.
									this.dataTypeGetterMapString="Integer.parseInt(((String)"+parentDataName+").substring[1,4])";
									
									
									//Setter Sample: MOVE TAR1 TO NEXT-KEY_LIMAN
									// NEXT-KEY= ((String)NEXT-KEY).subString(0,1)+ TAR1+"+ ((String)NEXT-KEY).subString(4);
									this.dataTypeSetterMapString=parentDataName+"=((String)"+parentDataName+").subString(0,"+offset+")+ TAR1+ ((String)"+parentDataName+").subString("+redefineEndIndex+")";
									
								}else { // this.getDataType() N değilse
									/**
									 *  *S**1 #NEXT-KEY (N20)                                                                           
										*S**1 REDEFINE #NEXT-KEY                                                                        
										*S**  2 #NEXT-KEY-ID   (N1)                                                                     
										*S**  2 #NEXT-KEY-LIMAN(A3)
									 */
									
									//Getter  Sample: MOVE NEXT-KEY_LIMAN TO TAR1
									// --> TAR1= ((String)NEXT-KEY).substring[1,4];
									this.dataTypeGetterMapString="((String)"+parentDataName+").substring["+offset+","+redefineEndIndex+"];";
									
									//Setter Sample: MOVE TAR1 TO NEXT-KEY_LIMAN
									// NEXT-KEY= Integer.parseInt(((String)NEXT-KEY).subString(0,1)+ TAR1+"+ ((String)NEXT-KEY).subString(4));
									
									this.dataTypeSetterMapString= parentDataName+"=Integer.parseInt(((String)"+parentDataName+").subString(0,"+offset+")+ TAR1((String)"+parentDataName+").subString("+redefineEndIndex+"))";
									
								}
							}else { // parentRedefinedCommand. data tipi N değilse
								
									if(this.getDataType().equals("N")){
										/**
										 *  *S**1 #NEXT-KEY (A20)                                                                           
											*S**1 REDEFINE #NEXT-KEY                                                                        
											*S**  2 #NEXT-KEY-ID   (N1)                                                                     
											*S**  2 #NEXT-KEY-LIMAN(N3)
										 */
										//Getter  Sample: MOVE NEXT-KEY_LIMAN TO TAR1
										// --> TAR1= Integer.parseInt((NEXT-KEY).substring[1,4]));
										this.dataTypeGetterMapString="Integer.parseInt(("+parentDataName+").substring["+offset+","+length+"]))";
										
										//Setter Sample: MOVE TAR1 TO NEXT-KEY_LIMAN
										// NEXT-KEY= ((String)NEXT-KEY).subString(0,1)+ TAR1+"+ ((String)NEXT-KEY).subString(4);
										this.dataTypeSetterMapString=parentDataName+"= ((String)"+parentDataName+").subString(0,"+offset+") + TAR1 + ((String)"+ parentDataName+").subString(4)";
										
									}else { // this.getDataType() N değilse
										/**
										 *  *S**1 #NEXT-KEY (A20)                                                                           
											*S**1 REDEFINE #NEXT-KEY                                                                        
											*S**  2 #NEXT-KEY-ID   (A1)                                                                     
											*S**  2 #NEXT-KEY-LIMAN(A3)
										 */
										
										//Getter  Sample: MOVE NEXT-KEY_LIMAN TO TAR1
										// --> TAR1= ((String)NEXT-KEY).substring[1,4];
										this.dataTypeGetterMapString="((String)"+parentDataName+").substring["+offset+","+length+"]";
										
										//Setter Sample: MOVE TAR1 TO NEXT-KEY_LIMAN
										// NEXT-KEY= NEXT-KEY.subString(0,1)+ TAR1+"+ NEXT-KEY.subString(4);
										this.dataTypeSetterMapString="((String)"+parentDataName+").substring["+offset+","+length+"]"+"%XXX%"+"((String)"+parentDataName+").substring["+redefineEndIndex+"]";
										
									}
							}
							manager.registerCommandToLocalVariablesMap(this.dataName, this);
						}
						return;
		
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
	
	
	
	
}
