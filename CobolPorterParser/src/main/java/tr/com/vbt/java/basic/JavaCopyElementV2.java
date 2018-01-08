package tr.com.vbt.java.basic;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;


import tr.com.vbt.java.AbstractJavaElement;
import tr.com.vbt.java.general.JavaClassElement;
import tr.com.vbt.java.general.JavaConstants;
import tr.com.vbt.java.utils.ConvertUtilities;
import tr.com.vbt.java.utils.JavaWriteUtilities;
import tr.com.vbt.token.AbstractToken;

//MOVE 0 TO CAT-CT --> cat-ct=0;
public class JavaCopyElementV2 extends AbstractJavaElement {

	final static Logger logger = Logger.getLogger(JavaCopyElementV2.class);

	private AbstractToken dataToMove;

	private List<AbstractToken> destVariable = new ArrayList<AbstractToken>();
	
	boolean cast;

	public boolean writeJavaToStream() throws Exception{
		super.writeJavaToStream();
		destVariable = (List<AbstractToken>) this.parameters.get("destVariable");
		dataToMove = (AbstractToken) this.parameters.get("dataToMove");
		String setterString, getterString;
		
		if(controlAndWriteMoveToSubstringCommand()){
			return true;
		}
		
		try {
			try {
				for (AbstractToken destVar1 : destVariable) {

					if(destVar1.isPojoVariable() || (destVar1.getLinkedToken()!=null && destVar1.getLinkedToken().isRedefinedVariable()) || destVar1.isRedefinedVariable()|| destVar1.isRedefinedVariableDimensionToSimple()){
						JavaClassElement.javaCodeBuffer.append(JavaWriteUtilities.toCustomSetterString(destVar1, dataToMove));
					}else{
						JavaClassElement.javaCodeBuffer.append(JavaWriteUtilities.toCustomString(destVar1));
						JavaClassElement.javaCodeBuffer.append("=");
						cast=JavaWriteUtilities.addCast(destVar1,dataToMove);
						
						JavaClassElement.javaCodeBuffer.append(JavaWriteUtilities.toCustomString(dataToMove));
						
						JavaWriteUtilities.endCast(cast);
						
						JavaWriteUtilities.addTypeChangeFunctionToEnd(destVar1,dataToMove);
					}

					JavaClassElement.javaCodeBuffer.append(JavaConstants.DOT_WITH_COMMA + JavaConstants.NEW_LINE);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
//				e.printStackTrace();
			}
		} catch (Exception e) {
			logger.debug("//Conversion Error" + this.getClass() + this.getSourceCode().getSatirNumarasi()
					+ this.getSourceCode().getCommandName());
			JavaClassElement.javaCodeBuffer
					.append("/*Conversion Error" + this.getClass() + this.getSourceCode().getSatirNumarasi()
							+ this.getSourceCode().getCommandName() + "*/" + JavaConstants.NEW_LINE);
			logger.error("//Conversion Error:" + e.getMessage(), e);
			ConvertUtilities.writeconversionErrors(e, this);
		}
		JavaClassElement.javaCodeBuffer.append(JavaConstants.NEW_LINE);
		return true;
	}

	
	// NATURAL CODE:801 :.0 MOVE SUBSTR ( PBASVURUTARIHI , 1.0 , 4.0 ) TO SUBSTR ( BASVURUTARIHIGAY , 7.0 , 4.0 )
	//BASVURUTARIHIGAY = moveToSubstring(BASVURUTARIHIGAY, 6, 4, PBASVURUTARIHI.substring(0, 4));
	//BASVURUTARIHIGAY = moveToSubstring(BASVURUTARIHIGAY, 6, 4, PBASVURUTARIHI.substring(0, 4));
	
	// NATURAL CODE:1477 :.0 MOVE MAP .YAZADRES1 TO SUBSTR ( IDGIDBS-TKARTEX.ADRES , 1.0 , 60.0 )
	//TKARTEX.setAdres(moveToSubstring( getStringPojoValue("TKARTEX.getAdres()"), 0, 60, MAP_P.YAZADRES1));
	private boolean controlAndWriteMoveToSubstringCommand()  throws Exception{
	
		if(destVariable==null){
			return false;
		}
		
		AbstractToken destVariableFirst;
		try {
			destVariableFirst = destVariable.get(0);
		} catch (Exception e1) {
			JavaClassElement.javaCodeBuffer.append("Hata_olustu_Manuel_Kontrol_Edilmeli.");
			return false;
		}
		
		if(destVariableFirst==null || !destVariableFirst.isSubstringCommand()){
			return false;
		}
		
		try {
				
				destVariableFirst.setSubstringCommand(false);
				
				if(destVariableFirst.isPojoVariable() || destVariableFirst.isRedefinedVariable()|| destVariableFirst.isRedefinedVariableDimensionToSimple()){
					
					JavaClassElement.javaCodeBuffer.append(JavaWriteUtilities.toCustomSetterString(destVariableFirst));
							
					JavaClassElement.javaCodeBuffer.append("moveToSubstring("+JavaWriteUtilities.toCustomString(destVariableFirst)+","+destVariableFirst.getSubStringStartIndex()+","+destVariableFirst.getSubStringEndIndex()+",");
					
					JavaClassElement.javaCodeBuffer.append(JavaWriteUtilities.toCustomString(dataToMove));

					JavaClassElement.javaCodeBuffer.append("))"+JavaConstants.DOT_WITH_COMMA + JavaConstants.NEW_LINE);
				}else{
					JavaClassElement.javaCodeBuffer.append(JavaWriteUtilities.toCustomString(destVariableFirst));
					JavaClassElement.javaCodeBuffer.append("=");
				
					JavaClassElement.javaCodeBuffer.append("moveToSubstring("+JavaWriteUtilities.toCustomString(destVariableFirst)+","+destVariableFirst.getSubStringStartIndex()+","+destVariableFirst.getSubStringEndIndex()+",");
						
					JavaClassElement.javaCodeBuffer.append(JavaWriteUtilities.toCustomString(dataToMove));
	
					JavaClassElement.javaCodeBuffer.append(")"+JavaConstants.DOT_WITH_COMMA + JavaConstants.NEW_LINE);
				}
		} catch (Exception e) {
			logger.debug("//Conversion Error" + this.getClass() + this.getSourceCode().getSatirNumarasi()
					+ this.getSourceCode().getCommandName());
			JavaClassElement.javaCodeBuffer
					.append("/*Conversion Error" + this.getClass() + this.getSourceCode().getSatirNumarasi()
							+ this.getSourceCode().getCommandName() + "*/" + JavaConstants.NEW_LINE);
			logger.error("//Conversion Error:" + e.getMessage(), e);
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
