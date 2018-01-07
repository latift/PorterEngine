package tr.com.vbt.java.basic;

import java.util.List;

import org.apache.log4j.Logger;


import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.java.AbstractJavaElement;
import tr.com.vbt.java.general.JavaClassElement;
import tr.com.vbt.java.general.JavaConstants;
import tr.com.vbt.java.utils.ConvertUtilities;
import tr.com.vbt.java.utils.JavaWriteUtilities;
import tr.com.vbt.java.utils.VariableTypes;
import tr.com.vbt.natural.parser.datalayout.program.ElementProgramDataTypeNatural;
import tr.com.vbt.natural.parser.datalayout.program.ElementProgramGrupNatural;
import tr.com.vbt.natural.parser.datalayout.program.ElementProgramOneDimensionArrayNatural;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.ArrayToken;
import tr.com.vbt.token.KelimeToken;
import tr.com.vbt.token.SayiToken;
import tr.com.vbt.token.TokenTipi;


// RESET #T-BIT-TARIH #T-SEFNO  #CARSEF #U-REG #T-TARIH --> #T-BIT-TARIH=0;  T-SEFNO="";  CARSEF=0;
public class JavaResetElement extends  AbstractJavaElement {
	
	final static Logger logger = Logger.getLogger(JavaResetElement.class);
	
	private List<AbstractToken> resetVariableList;
	
	long scale;
	
	
	public boolean writeJavaToStream() throws Exception{ super.writeJavaToStream();
		
		resetVariableList=(List<AbstractToken>) this.getParameters().get("resetVariableList");
		
		VariableTypes variableType, childVariableType;
		
		AbstractCommand variableDefinitionCommand;
		
		ElementProgramDataTypeNatural variableDefinitionCommandDataType;
		
		AbstractToken linkedToken;
		
		ArrayToken arrayToken;
		
		if(resetVariableList==null){
			JavaClassElement.javaCodeBuffer.append("/*Conversion Error"+getClass().getName()+"*/"+JavaConstants.NEW_LINE);
			return true;
		}
		
		for (AbstractToken variable : resetVariableList) {
			
			if(variable.getTip().equals(TokenTipi.Kelime) || !variable.isAllArrayItems()){
				
				variableType=ConvertUtilities.getVariableType(variable);
				
				variableDefinitionCommand=ConvertUtilities.getVariableDefinitinCommand(variable);
				
				if(variable.isRecordVariable() && variable.getLinkedToken()!=null){
					variableType=ConvertUtilities.getVariableType(variable.getLinkedToken());
					
					variableDefinitionCommand=ConvertUtilities.getVariableDefinitinCommand(variable.getLinkedToken());
				}
				
				
				//RESET TAB ( *) --> FCU.resetArray(TAB);
			    if(variableDefinitionCommand instanceof ElementProgramOneDimensionArrayNatural && 
			    		(variable.isAllArrayItems() || (variable.getLinkedToken()!=null && variable.getLinkedToken().isAllArrayItems()))){
			    	JavaClassElement.javaCodeBuffer.append("FCU.resetArray("+JavaWriteUtilities.toCustomString(variable)+")"+JavaConstants.DOT_WITH_COMMA+JavaConstants.NEW_LINE);
			    }else if(variable.isRedefinedVariable()){
					AbstractToken token;
					try{
						if(variableType==VariableTypes.INT_TYPE||variableType==VariableTypes.LONG_TYPE){
							token=new SayiToken<Long>(0l,0, 0, 0);
							JavaClassElement.javaCodeBuffer.append(JavaWriteUtilities.toCustomSetterString(variable,token)+JavaConstants.DOT_WITH_COMMA+JavaConstants.NEW_LINE);
						}else{
							token=new KelimeToken<>("",0,0,0);
							JavaClassElement.javaCodeBuffer.append(JavaWriteUtilities.toCustomSetterString(variable,token)+JavaConstants.DOT_WITH_COMMA+JavaConstants.NEW_LINE);
						}
					} catch (Exception e) {
						logger.debug("//Conversion Error"+this.getClass()+this.getSourceCode().getSatirNumarasi()+this.getSourceCode().getCommandName());
						JavaClassElement.javaCodeBuffer.append("/*Conversion Error"+this.getClass()+this.getSourceCode().getSatirNumarasi()
								+this.getSourceCode().getCommandName()+"*/"+JavaConstants.NEW_LINE);
						logger.error("//Conversion Error:"+e.getMessage(), e); 
						ConvertUtilities.writeconversionErrors(e, this); 
					}
				}else if(variable.isPojoVariable()){
					try{
						if(variableType==VariableTypes.INT_TYPE||variableType==VariableTypes.LONG_TYPE||variableType==VariableTypes.BIG_DECIMAL_TYPE){
							JavaClassElement.javaCodeBuffer.append(JavaWriteUtilities.toCustomSetterString(variable));
						
							JavaClassElement.javaCodeBuffer.append(JavaWriteUtilities.toCustomString(new KelimeToken("0",0,0,0)));
							JavaClassElement.javaCodeBuffer.append(")"+JavaConstants.DOT_WITH_COMMA+JavaConstants.NEW_LINE);
						}else{
							JavaClassElement.javaCodeBuffer.append(JavaWriteUtilities.toCustomSetterString(variable));
						
							JavaClassElement.javaCodeBuffer.append(JavaWriteUtilities.toCustomString(new KelimeToken("new String()",0,0,0)));
							JavaClassElement.javaCodeBuffer.append(")"+JavaConstants.DOT_WITH_COMMA+JavaConstants.NEW_LINE);
						}
					} catch (Exception e) {
						logger.debug("//Conversion Error"+this.getClass()+this.getSourceCode().getSatirNumarasi()+this.getSourceCode().getCommandName());
						JavaClassElement.javaCodeBuffer.append("/*Conversion Error"+this.getClass()+this.getSourceCode().getSatirNumarasi()
								+this.getSourceCode().getCommandName()+"*/"+JavaConstants.NEW_LINE);
						logger.error("//Conversion Error:"+e.getMessage(), e); 
						ConvertUtilities.writeconversionErrors(e, this); 
					}
					//0412 1 MAP_DIZISI                                                                                                                   
					//0414   2 D_SECIM    (A1/1:100)                                                                                                      
					//0416   2 D_HESCINSI (A1/1:100)
					//0418   2 D_HESNO    (N8/1:100) 
					
					// 1076   RESET MAP_DIZISI 
					
					// --> 
					
				}else if(variableType==VariableTypes.INT_TYPE||variableType==VariableTypes.LONG_TYPE){
					
					try{
						JavaClassElement.javaCodeBuffer.append(JavaWriteUtilities.toCustomString(variable)+"="+"0"+JavaConstants.DOT_WITH_COMMA+JavaConstants.NEW_LINE);
					} catch (Exception e) {
						logger.debug("//Conversion Error"+this.getClass()+this.getSourceCode().getSatirNumarasi()+this.getSourceCode().getCommandName());
						JavaClassElement.javaCodeBuffer.append("/*Conversion Error"+this.getClass()+this.getSourceCode().getSatirNumarasi()
								+this.getSourceCode().getCommandName()+"*/"+JavaConstants.NEW_LINE);
						logger.error("//Conversion Error:"+e.getMessage(), e); 
						ConvertUtilities.writeconversionErrors(e, this); 
					}
				}else if(variableType==VariableTypes.BIG_DECIMAL_TYPE){
					
					if(variableDefinitionCommand instanceof ElementProgramDataTypeNatural){
						variableDefinitionCommandDataType=(ElementProgramDataTypeNatural) variableDefinitionCommand;
						scale=variableDefinitionCommandDataType.getLengthAfterDot();
					}
					//FCU.resetBigDecimal(2);
					try{
						JavaClassElement.javaCodeBuffer.append(JavaWriteUtilities.toCustomString(variable)+"="+"FCU.resetBigDecimal("+scale+")"+JavaConstants.DOT_WITH_COMMA+JavaConstants.NEW_LINE);
					} catch (Exception e) {
						logger.debug("//Conversion Error"+this.getClass()+this.getSourceCode().getSatirNumarasi()+this.getSourceCode().getCommandName());
						JavaClassElement.javaCodeBuffer.append("/*Conversion Error"+this.getClass()+this.getSourceCode().getSatirNumarasi()
								+this.getSourceCode().getCommandName()+"*/"+JavaConstants.NEW_LINE);
						logger.error("//Conversion Error:"+e.getMessage(), e); 
						ConvertUtilities.writeconversionErrors(e, this); 
					}
				}else if(variableType==VariableTypes.STRING_TYPE){
					try{
						JavaClassElement.javaCodeBuffer.append(JavaWriteUtilities.toCustomString(variable)+"="+"new String()"+JavaConstants.DOT_WITH_COMMA+JavaConstants.NEW_LINE);
					} catch (Exception e) {
						logger.debug("//Conversion Error"+this.getClass()+this.getSourceCode().getSatirNumarasi()+this.getSourceCode().getCommandName());
						JavaClassElement.javaCodeBuffer.append("/*Conversion Error"+this.getClass()+this.getSourceCode().getSatirNumarasi()
								+this.getSourceCode().getCommandName()+"*/"+JavaConstants.NEW_LINE);
						logger.error("//Conversion Error:"+e.getMessage(), e); 
						ConvertUtilities.writeconversionErrors(e, this); 
					}
					//0412 1 MAP_DIZISI                                                                                                                   
					//0414   2 D_SECIM    (A1/1:100)                                                                                                      
					//0416   2 D_HESCINSI (A1/1:100)
					//0418   2 D_HESNO    (N8/1:100) 
					
					// 1076   RESET MAP_DIZISI 
					
					// --> 
					
				}else if(variableType==VariableTypes.GRUP_TYPE){
					linkedToken=variable.getLinkedToken();
					if(variableDefinitionCommand!=null){
						if(variableDefinitionCommand instanceof ElementProgramGrupNatural && variable.getLinkedToken()==null &&((ElementProgramGrupNatural)variableDefinitionCommand).getArrayLength()!=0){  // Dizinin tamamı resetleniyorsa.
						
							/*
							 *  0550 1 MAP_DIZISI  (200)
								0560   2 D_ADSOYAD      (A58)
								0570   2 D_DOVIZ        (N2)
								0580   2 D_HESAP        (A1)
								0590   2 D_HESAPNO      (N8)
								0600   2 D_SICIL        (N5)
							 */
							//RESET MAP_DIZISI(*)  -->  MAP_DIZISI[]=new MAP_DIZISI[200];
							long arrayLength=((ElementProgramGrupNatural)variableDefinitionCommand).getArrayLength();
							JavaClassElement.javaCodeBuffer.append(JavaWriteUtilities.toCustomString(variable)+" = new "+ JavaWriteUtilities.toCustomString(variable)+"["+arrayLength+"];");
							JavaClassElement.javaCodeBuffer.append(JavaConstants.DOT_WITH_COMMA+JavaConstants.NEW_LINE);
						}else if(variable.getLinkedToken()==null){  // Dizinin tamamı resetleniyorsa. 
							// 1048 RESET MAP_DIZISI  --> MAP_DIZISI = new MAP_DIZISI();
							JavaClassElement.javaCodeBuffer.append(JavaWriteUtilities.toCustomString(variable)+" = new "+ variable.getDeger().toString().replaceAll("-", "_")+"()");
							JavaClassElement.javaCodeBuffer.append(JavaConstants.DOT_WITH_COMMA+JavaConstants.NEW_LINE);
						}
						else{ //Dizinin belli elemanları resetleniyorsa
							for (AbstractCommand definitionChild : variableDefinitionCommand.getChildElementList()) {
									try {
										writeChildReset(definitionChild,variable);
									} catch (Exception e) {
										logger.debug("//Conversion Error"+this.getClass()+this.getSourceCode().getSatirNumarasi()+this.getSourceCode().getCommandName());
										JavaClassElement.javaCodeBuffer.append("/*Conversion Error"+this.getClass()+this.getSourceCode().getSatirNumarasi()
												+this.getSourceCode().getCommandName()+"*/"+JavaConstants.NEW_LINE);
										logger.error("//Conversion Error:"+e.getMessage(), e); 
										ConvertUtilities.writeconversionErrors(e, this);
									}
							}
						}
					}
				}
				
			}else if(variable.getTip().equals(TokenTipi.Array)){
				if(variable.isPojoVariable()){
					JavaClassElement.javaCodeBuffer.append("//TODO POJO RESETLEME ARRAY VAR DİKKAT!");
				}else if(variable.isAllArrayItems()){
					
					JavaClassElement.javaCodeBuffer.append("FCU.resetArray("+JavaWriteUtilities.toCustomString(variable)+");");
				}else{
					JavaClassElement.javaCodeBuffer.append("UnimplementedCode");
				}

			}
		}
		
		return true;
	}

	private void writeChildReset(AbstractCommand definitionChild, AbstractToken variable) throws Exception {
		ElementProgramOneDimensionArrayNatural oneDimensionArrayChildDefinition;
		ElementProgramDataTypeNatural programDataTypeDefinition;
		String type;
		if(definitionChild  instanceof ElementProgramOneDimensionArrayNatural){
			oneDimensionArrayChildDefinition=(ElementProgramOneDimensionArrayNatural) definitionChild;
			if(!oneDimensionArrayChildDefinition.getDataName().equals(variable.getLinkedToken().getDeger())){
				return;
			}
			
			// 3814   FOR KAYSAY=J TO 10
			//				RESET MAP.HESCINSI(KAYSAY)
			
			//MAPP.HESCINSI[KAYSAY-1] = new String("");
			//MAPP.VADE[KAYSAY - 1] = 0;			
			type=ConvertUtilities.getJavaVariableType(oneDimensionArrayChildDefinition.getDataType(), oneDimensionArrayChildDefinition.getLength(), oneDimensionArrayChildDefinition.getLengthAfterDot());
			
			JavaClassElement.javaCodeBuffer.append(JavaWriteUtilities.toCustomString(variable));
		
			if(type.equals("String")){
				//= new String("");
				JavaClassElement.javaCodeBuffer.append("="+"new "+type+"()"+JavaConstants.DOT_WITH_COMMA+JavaConstants.NEW_LINE);
			}else{
				//= 0;	
				JavaClassElement.javaCodeBuffer.append("=0"+JavaConstants.DOT_WITH_COMMA+JavaConstants.NEW_LINE);
			}
		}else if(definitionChild  instanceof ElementProgramDataTypeNatural){
			
			programDataTypeDefinition=(ElementProgramDataTypeNatural) definitionChild;
			
			if(!programDataTypeDefinition.getDataName().equals(variable.getLinkedToken().getDeger())){
				return;
			}
			
			type=ConvertUtilities.getJavaVariableType(programDataTypeDefinition.getDataType(), programDataTypeDefinition.getLength(), programDataTypeDefinition.getLengthAfterDot());
			
			if(type.equals("String")){
				JavaClassElement.javaCodeBuffer.append(JavaWriteUtilities.toCustomString(variable)+"="+"new String(\"\")"+JavaConstants.DOT_WITH_COMMA+JavaConstants.NEW_LINE);
			}else
				JavaClassElement.javaCodeBuffer.append(JavaWriteUtilities.toCustomString(variable)+"="+"0"+JavaConstants.DOT_WITH_COMMA+JavaConstants.NEW_LINE);
			}
	}
		
	



	@Override
	public boolean writeChildrenJavaToStream() {
		// TODO Auto-generated method stub
		return false;
	}

	

}
