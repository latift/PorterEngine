package tr.com.vbt.java.basic;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;


import tr.com.vbt.java.AbstractJavaElement;
import tr.com.vbt.java.general.JavaClassElement;
import tr.com.vbt.java.general.JavaConstants;
import tr.com.vbt.java.util.Utility;
import tr.com.vbt.java.utils.ConvertUtilities;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.util.CustomStringUtils;


//MOVE 0 TO CAT-CT --> cat-ct=0;
public class JavaCopyElement extends  AbstractJavaElement {
	
	final static Logger logger = Logger.getLogger(JavaCopyElement.class);
	
	private AbstractToken dataToMove;
	
	private List<AbstractToken> destVariable=new ArrayList<AbstractToken>();
	
	String dataTypeGetterMapString;
	
	String dataTypeSetterMapString;
	
	public boolean writeJavaToStream() throws Exception{ super.writeJavaToStream();
		destVariable = (List<AbstractToken>) this.parameters.get("destVariable");
		dataToMove = (AbstractToken) this.parameters.get("dataToMove");
		String setterString, getterString;
		try{
			for (AbstractToken destVar1 : destVariable) {
				//JavaClassElement.javaCodeBuffer.append(CustomStringUtils.replaceMiddleLineWithSubLine(destVar1.getDeger().toString())+"="+CustomStringUtils.replaceMiddleLineWithSubLine(dataToMove.getDeger().toString())+JavaConstants.DOT_WITH_COMMA);
				
				
				/**
				 *  *S**1 #NEXT-KEY (A20)                                                                           
					*S**1 REDEFINE #NEXT-KEY                                                                        
					*S**  2 #NEXT-KEY-ID   (A1)                                                                     
					*S**  2 #NEXT-KEY-LIMAN(A3)
				 */
				//Getter  Sample: MOVE NEXT-KEY_LIMAN TO TAR1
				// --> TAR1= ((String)NEXT-KEY).substring[1,4];
				
				//*S**01 TRAFIK VIEW OF TFK-TRAFIK --> TRAFIK
				//DBDatatypeNAtural ın en küçük leveli 2 olabilir. 1 olan viewOf dur.
				//*S**  02 TFK-ADTRH  	--> TRAFIK.getTfkAdtrh();

				//Pojo.setter(localVariable.getter());
				if(destVar1.isPojoVariable()&& dataToMove.isLocalVariable()){   //Setter   //Local To Pojo
					
					setterString= sourceToJavaStyleManager.getJavaStyleSetterCodeOfNatDataTypeForPojo(destVar1.getDeger().toString());
					
					getterString=sourceToJavaStyleManager.getJavaStyleGetterCodeOfNatDataTypeForLocalVar(dataToMove.getDeger().toString());
					
					if(getterString==null|| setterString==null){
					
						JavaClassElement.javaCodeBuffer.append(destVar1.getDeger().toString().replaceAll("-", "_")+"="+"\""+dataToMove.getDeger().toString().replaceAll("-", "_")+"\""+JavaConstants.DOT_WITH_COMMA+JavaConstants.NEW_LINE);
					}else{
						JavaClassElement.javaCodeBuffer.append(setterString+"("+getterString+")"+JavaConstants.DOT_WITH_COMMA+JavaConstants.NEW_LINE);
					}
				}
				//LocalVariable=Pojo.getter()
				else if(dataToMove.isPojoVariable()&&destVar1.isLocalVariable()){ //Getter  //Pojo To Local
					
					if(destVar1.isSubstringCommand()){
						JavaClassElement.javaCodeBuffer.append(destVar1.getDeger().toString().replaceAll("-", "_"));
						if(destVar1.getSubStringStartIndexString()!=null) {
							JavaClassElement.javaCodeBuffer.append(".substring("+destVar1.getSubStringStartIndexString());
						}else{
							JavaClassElement.javaCodeBuffer.append(".substring("+destVar1.getSubStringStartIndex());
						}
						if(destVar1.getSubStringEndIndexString()!=null) {
							JavaClassElement.javaCodeBuffer.append(","+destVar1.getSubStringEndIndexString()+")");
						}else{
							JavaClassElement.javaCodeBuffer.append(","+destVar1.getSubStringEndIndex()+")");
						}
					}else{
						JavaClassElement.javaCodeBuffer.append(destVar1.getDeger().toString().replaceAll("-", "_"));
						
					}
					JavaClassElement.javaCodeBuffer.append("=");
					
					if(dataToMove.isSubstringCommand()){
						JavaClassElement.javaCodeBuffer.append(dataToMove.getDeger().toString().replaceAll("-", "_"));
						JavaClassElement.javaCodeBuffer.append(".");
						String columnNameGetter=Utility.viewNameToPojoGetterName(dataToMove.getColumnNameToken().getDeger().toString())+"()";
						JavaClassElement.javaCodeBuffer.append(columnNameGetter);
						if(dataToMove.getSubStringStartIndexString()!=null) {
							JavaClassElement.javaCodeBuffer.append(".substring("+dataToMove.getSubStringStartIndexString());
						}else{
							JavaClassElement.javaCodeBuffer.append(".substring("+dataToMove.getSubStringStartIndex());
						}
						if(dataToMove.getSubStringEndIndexString()!=null) {
							JavaClassElement.javaCodeBuffer.append(","+dataToMove.getSubStringEndIndexString()+")");
						}else{
							JavaClassElement.javaCodeBuffer.append(","+dataToMove.getSubStringEndIndex()+")");
						}
					}else{
						JavaClassElement.javaCodeBuffer.append(dataToMove.getDeger().toString().replaceAll("-", "_"));
					}
					JavaClassElement.javaCodeBuffer.append(JavaConstants.DOT_WITH_COMMA+JavaConstants.NEW_LINE);
					
					
					//LocalVariable=LocalVariable;
				}else if(dataToMove.isConstantVariableWithQuota()){
					
					if(destVar1.isSubstringCommand()){
						JavaClassElement.javaCodeBuffer.append("subStringMove(this,");
						JavaClassElement.javaCodeBuffer.append("\""+destVar1.getDeger().toString().replaceAll("-", "_")+"\"");
						JavaClassElement.javaCodeBuffer.append(",");
						JavaClassElement.javaCodeBuffer.append(destVar1.getSubStringStartIndex());
						JavaClassElement.javaCodeBuffer.append(",");
						JavaClassElement.javaCodeBuffer.append(destVar1.getSubStringEndIndex());
						JavaClassElement.javaCodeBuffer.append(",");
						JavaClassElement.javaCodeBuffer.append("\"");
						JavaClassElement.javaCodeBuffer.append(dataToMove.getDeger().toString().replaceAll("-", "_"));
						JavaClassElement.javaCodeBuffer.append("\"");
						JavaClassElement.javaCodeBuffer.append(")");
					}else{
						JavaClassElement.javaCodeBuffer.append(destVar1.getDeger().toString().replaceAll("-", "_")+"="+"\""+dataToMove.getDeger().toString()+"\""+JavaConstants.DOT_WITH_COMMA+JavaConstants.NEW_LINE);
					}
					JavaClassElement.javaCodeBuffer.append(JavaConstants.DOT_WITH_COMMA+JavaConstants.NEW_LINE);
					
				}else if(dataToMove.isLocalVariable()&&destVar1.isLocalVariable()){  
				
					/*
					 0770    MOVE SUBSTR(MAXTARGAY,1,2) TO SUBSTR(MAXTARYAG,9,2)  --> subStringMove(this, "MAXTARYAG",8,10, MAXTARGAY.substring(0,2));
					 0780    MOVE SUBSTR(MAXTARGAY,4,2) TO SUBSTR(MAXTARYAG,6,2) --> subStringMove(this, "MAXTARYAG",5,7, MAXTARGAY.substring(3,5));
					 0790    MOVE SUBSTR(MAXTARGAY,7,4) TO SUBSTR(MAXTARYAG,1,4) --> subStringMove(this, "MAXTARYAG",0,3, MAXTARGAY.substring(0,2));
					 0800    MOVE '-' TO SUBSTR(MAXTARYAG,5,1) --> subStringMove(this, "MAXTARYAG",4,5, "-");
					 0810    MOVE '-' TO SUBSTR(MAXTARYAG,8,1) --> subStringMove(this, "MAXTARYAG",7,8, "-");
					 
					 Method Sign subStringMove(Nap, String fieldName, int startIndex,int endIndex, String newValue);
					 */
					if(destVar1.isSubstringCommand()){
						JavaClassElement.javaCodeBuffer.append("subStringMove(this,");
						JavaClassElement.javaCodeBuffer.append("\""+destVar1.getDeger().toString().replaceAll("-", "_")+"\"");
						JavaClassElement.javaCodeBuffer.append(",");
						JavaClassElement.javaCodeBuffer.append(destVar1.getSubStringStartIndex());
						JavaClassElement.javaCodeBuffer.append(",");
						JavaClassElement.javaCodeBuffer.append(destVar1.getSubStringEndIndex());
						JavaClassElement.javaCodeBuffer.append(",");
						if(dataToMove.isSubstringCommand()){
							JavaClassElement.javaCodeBuffer.append(dataToMove.getDeger().toString().replaceAll("-", "_"));
							if(dataToMove.getSubStringStartIndexString()!=null) {
								JavaClassElement.javaCodeBuffer.append(".substring("+dataToMove.getSubStringStartIndexString());
							}else{
								JavaClassElement.javaCodeBuffer.append(".substring("+dataToMove.getSubStringStartIndex());
							}
							if(dataToMove.getSubStringEndIndexString()!=null) {
								JavaClassElement.javaCodeBuffer.append(","+dataToMove.getSubStringEndIndexString()+")");
							}else{
								JavaClassElement.javaCodeBuffer.append(","+dataToMove.getSubStringEndIndex()+")");
							}
						}else{
							JavaClassElement.javaCodeBuffer.append(dataToMove.getDeger().toString().replaceAll("-", "_"));
						}
						
						JavaClassElement.javaCodeBuffer.append(")");
						JavaClassElement.javaCodeBuffer.append(JavaConstants.DOT_WITH_COMMA+JavaConstants.NEW_LINE);
					}
					// 1686 MOVE SUBSTR(SB_ACIKLAMA,1,79) TO SB_ACIKLAMA79 -->SB_ACIKLAMA79 =SB_ACIKLAMA.substring(0,78);
					else if(dataToMove.isSubstringCommand()){
						JavaClassElement.javaCodeBuffer.append(destVar1.getDeger().toString().replaceAll("-", "_")+"=");
						JavaClassElement.javaCodeBuffer.append(dataToMove.getDeger().toString().replaceAll("-", "_"));
						if(dataToMove.getSubStringStartIndexString()!=null) {
							JavaClassElement.javaCodeBuffer.append(".substring("+dataToMove.getSubStringStartIndexString());
						}else{
							JavaClassElement.javaCodeBuffer.append(".substring("+dataToMove.getSubStringStartIndex());
						}
						if(dataToMove.getSubStringEndIndexString()!=null) {
							JavaClassElement.javaCodeBuffer.append(","+dataToMove.getSubStringEndIndexString()+")");
						}else{
							JavaClassElement.javaCodeBuffer.append(","+dataToMove.getSubStringEndIndex()+")");
						}
						JavaClassElement.javaCodeBuffer.append(JavaConstants.DOT_WITH_COMMA+JavaConstants.NEW_LINE);
						
					}else{
						if(dataToMove.isEdited()){
							AbstractToken maskToken;
							JavaClassElement.javaCodeBuffer.append(destVar1.getDeger().toString().replaceAll("-", "_")+"=formatWithMask("+dataToMove.getDeger().toString().replaceAll("-", "_")+",");
							JavaClassElement.javaCodeBuffer.append("\"");
							int i;
							for(i=0; i<dataToMove.getEditMaskTokenList().size();i++){
								maskToken=(AbstractToken) dataToMove.getEditMaskTokenList().get(i);
								JavaClassElement.javaCodeBuffer.append(maskToken.getDeger());
							}
							JavaClassElement.javaCodeBuffer.append("\"");
							JavaClassElement.javaCodeBuffer.append(")");
						}else{
							JavaClassElement.javaCodeBuffer.append(destVar1.getDeger().toString().replaceAll("-", "_")+"="+dataToMove.getDeger().toString().replaceAll("-", "_"));
						}
						JavaClassElement.javaCodeBuffer.append(JavaConstants.DOT_WITH_COMMA+JavaConstants.NEW_LINE);
					}
					
				}else if(dataToMove.isSystemVariable()&&destVar1.isLocalVariable()){  
				
						if(destVar1.isSubstringCommand()){
							//TODO: Implement This Case
						}
						else if(dataToMove.isEdited()){
								
								JavaClassElement.javaCodeBuffer.append(destVar1.getDeger().toString().replaceAll("-", "_")+"=");
								JavaClassElement.javaCodeBuffer.append(dataToMove.toCustomString());
						}else{
								JavaClassElement.javaCodeBuffer.append(destVar1.getDeger().toString().replaceAll("-", "_")+"="+dataToMove.getDeger().toString().replaceAll("-", "_"));
						}
						JavaClassElement.javaCodeBuffer.append(JavaConstants.DOT_WITH_COMMA+JavaConstants.NEW_LINE);
					}
					else{
					JavaClassElement.javaCodeBuffer.append(CustomStringUtils.replaceMiddleLineWithSubLine(destVar1.getDeger().toString())+"="+CustomStringUtils.replaceMiddleLineWithSubLine(dataToMove.getDeger().toString())+JavaConstants.DOT_WITH_COMMA);
				}
			}
		} catch (Exception e) {
			logger.debug("//Conversion Error"+this.getClass()+this.getSourceCode().getSatirNumarasi()+this.getSourceCode().getCommandName());
			JavaClassElement.javaCodeBuffer.append("/*Conversion Error"+this.getClass()+this.getSourceCode().getSatirNumarasi()
					+this.getSourceCode().getCommandName()+"*/"+JavaConstants.NEW_LINE);
			logger.error("//Conversion Error:"+e.getMessage(), e); 
			ConvertUtilities.writeconversionErrors(e, this);
		}
		JavaClassElement.javaCodeBuffer.append(JavaConstants.NEW_LINE);
		return true;
	}

	@Override
	public boolean writeChildrenJavaToStream() {
		// TODO Auto-generated method stub
		return false;
	}

}
 