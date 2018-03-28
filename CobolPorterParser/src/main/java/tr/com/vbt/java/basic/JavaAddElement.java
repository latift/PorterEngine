package tr.com.vbt.java.basic;

import java.util.List;

import org.apache.log4j.Logger;


import tr.com.vbt.java.AbstractJavaElement;
import tr.com.vbt.java.general.JavaClassElement;
import tr.com.vbt.java.general.JavaConstants;
import tr.com.vbt.java.utils.ConvertUtilities;
import tr.com.vbt.java.utils.JavaWriteUtilities;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.TokenTipi;


// ADD 1 TO JJ --> JJ=JJ+1;
public class JavaAddElement extends  AbstractJavaElement {
	
	final static Logger logger = Logger.getLogger(JavaAddElement.class);
	
	private List<AbstractToken> sourceList;
	
	private List<AbstractToken> destList;
	
	public boolean writeJavaToStream() throws Exception{ super.writeJavaToStream();
		sourceList=(List<AbstractToken>) this.getParameters().get("SOURCE");
		
		destList=(List<AbstractToken>) this.getParameters().get("DESTINATION");
		try{
			String typeOfCopyTo=ConvertUtilities.getTypeOfVariable(destList.get(0));
			
			 if(typeOfCopyTo.equalsIgnoreCase("bigdecimal")){
					
					//cast=JavaWriteUtilities.addCast(copyTo,copyFrom.get(0));
					
					fromBigDecimalToBigDecimal();
					
					//JavaWriteUtilities.endCast(cast);
					
					JavaWriteUtilities.addTypeChangeFunctionToEnd(destList.get(0),sourceList.get(0));
					
					JavaClassElement.javaCodeBuffer.append(")"+JavaConstants.DOT_WITH_COMMA);
					
			}else if(destList.get(0).isPojoVariable()){
				
				JavaClassElement.javaCodeBuffer.append(JavaWriteUtilities.toCustomSetterString(destList.get(0)));
				
				JavaClassElement.javaCodeBuffer.append(JavaWriteUtilities.toCustomString(destList.get(0)));
				
				JavaClassElement.javaCodeBuffer.append("+");
				
				for (int i = 0; i < sourceList.size(); i++) {
					JavaClassElement.javaCodeBuffer.append(JavaWriteUtilities.toCustomString(sourceList.get(i)));
				}
				
				JavaClassElement.javaCodeBuffer.append(")"+JavaConstants.DOT_WITH_COMMA);
				
				//Pojodan Arraya
			}else{
				JavaClassElement.javaCodeBuffer.append(JavaWriteUtilities.toCustomString(destList.get(0))+"="+JavaWriteUtilities.toCustomString(destList.get(0))+JavaConstants.PLUS+JavaWriteUtilities.toCustomString(sourceList.get(0))+JavaConstants.DOT_WITH_COMMA+JavaConstants.NEW_LINE);
			}
		} catch (Exception e) {
			logger.debug("//Conversion Error"+this.getClass()+this.getSourceCode().getSatirNumarasi()+this.getSourceCode().getCommandName());
			JavaClassElement.javaCodeBuffer.append("/*Conversion Error"+this.getClass()+this.getSourceCode().getSatirNumarasi()
					+this.getSourceCode().getCommandName()+"*/"+JavaConstants.NEW_LINE);
			logger.error("//Conversion Error:"+e.getMessage(), e); 
			ConvertUtilities.writeconversionErrors(e, this); 
		}
		return true;
	}
	
	private void fromBigDecimalToBigDecimal() throws Exception {
		
		JavaClassElement.javaCodeBuffer.append(JavaWriteUtilities.toCustomString(destList.get(0)));
		JavaClassElement.javaCodeBuffer.append("=");
			
		JavaClassElement.javaCodeBuffer.append(JavaWriteUtilities.toCustomString(destList.get(0)));
		JavaClassElement.javaCodeBuffer.append(".add(");
		JavaClassElement.javaCodeBuffer.append(JavaWriteUtilities.toCustomString(sourceList.get(0)));
			
	}

	@Override
	public boolean writeChildrenJavaToStream() {
		// TODO Auto-generated method stub
		return false;
	}

	

}
