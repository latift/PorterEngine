package tr.com.vbt.java.basic;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tr.com.vbt.ddm.DDM;
import tr.com.vbt.ddm.DDMList;
import tr.com.vbt.java.AbstractJavaElement;
import tr.com.vbt.java.general.JavaClassElement;
import tr.com.vbt.java.general.JavaConstants;
import tr.com.vbt.java.utils.ConvertUtilities;
import tr.com.vbt.java.utils.JavaWriteUtilities;
import tr.com.vbt.lexer.ConversionLogModel;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.TokenTipi;

//1) StringToString
//#FARK1 := T-MIKTAR - T-UPLIFT
//#T-TERM :=T-INIT-USER
//#TOPLAM-UPLIFT2  := #TOPLAM-UPLIFT2  + ( T-GYOG * T-UPLIFT )

// 2) StringToArray
//#SECIM-YURT-ICI-AKARYAKIT(*):=' '  

//3) ComplexStringToArray
//#SECIM-YURT-ICI-AKARYAKIT(*):=' ' + #SECIM

//4) ComplexArrayToArray
//#ROL1(*):=#ROL1-SAYFA(#SAYFA,*)

//5) ComplexArrayToArray
//#SECIM-YURT-ICI-AKARYAKIT(*):=' ' + #SECIM + #SECIM-YK(*)

public class JavaBecomesEqualToElementV2 extends AbstractJavaElement {

	final static Logger logger = LoggerFactory.getLogger(JavaBecomesEqualToElementV2.class);

	private List<AbstractToken> copyFrom = new ArrayList<AbstractToken>(); // #TOPLAM-UPLIFT2 +(T-GYOG*T-UPLIFT)
	
	private AbstractToken copyTo;
	
	private List<AbstractToken> aritmethicOperators = new ArrayList<AbstractToken>();
	
	boolean cast;
	
	public boolean writeJavaToStream() throws Exception{
		
		super.writeJavaToStream(); 
		try {
			copyFrom = (List<AbstractToken>) this.parameters.get("copyFrom");
			copyTo = (AbstractToken) this.parameters.get("FIRST_COMMAND");
			if(copyFrom==null ) {
				return true;
			}
			if(copyFrom.get(0)==null) {
				return true;
			}
			
			if(copyTo==null) {
				return true;
			}
			
			copyFrom.set(0, createTableNameTokenForColumnsWithoutTable(copyFrom.get(0)));
			
			String typeOfCopyTo=ConvertUtilities.getTypeOfVariable(copyTo);
			
			if(typeOfCopyTo==null){
				typeOfCopyTo="";
			}
			typeOfCopyTo=typeOfCopyTo.toLowerCase();
			
			if(copyTo.getLinkedToken()!=null && copyFrom.get(0).getLinkedToken()!=null&& copyFrom.get(0).getLinkedToken().isAllArrayItems()){
				copyFrom.get(0).setAllArrayItems(true);
			}
			if(copyTo.getLinkedToken()!=null&& copyTo.getLinkedToken().isAllArrayItems()){
				copyTo.setAllArrayItems(true);
			}
			
			if(typeOfCopyTo.equalsIgnoreCase("bigdecimal") && copyFrom!=null && copyFrom.size()>1 && copyFrom.get(1)!=null && copyFrom.get(1).isKarakter('-')){
				 minusOperationBigDecimal();
			}else if(copyFrom!=null && copyFrom.size()>1 && copyFrom.get(1)!=null && copyFrom.get(1).isKarakter('-')){
				 minusOperation();
			}
			//*S**ASSIGN SCR-PAX-DSCR(*)  = TAX-PAX-DSCR(*)
			else if(copyTo.isAllArrayItems() &&copyFrom.get(0).isPojoVariable()&& copyFrom.get(0).getColumnNameToken().isAllArrayItems()){  
					
					cast=JavaWriteUtilities.addCast(copyTo,copyFrom.get(0));
				
					fromPojoToArrayAllItems();
					
					JavaWriteUtilities.endCast(cast);
					
					JavaWriteUtilities.addTypeChangeFunctionToEnd(copyTo,copyFrom.get(0));
				
			//*S**ASSIGN TAX-EXC-DEST(*) = SCR-EXC-DEST(*) ArrayAllItemdan PojoArraye
			}else if(copyFrom.get(0).isAllArrayItems() &&copyTo.isPojoVariable()&& copyTo.getColumnNameToken().isAllArrayItems()){ 		
				
				cast=JavaWriteUtilities.addCast(copyTo,copyFrom.get(0));
				
				fromArrayToPojoAllItems();
				
				JavaWriteUtilities.endCast(cast);
				
				JavaWriteUtilities.addTypeChangeFunctionToEnd(copyTo,copyFrom.get(0));
				
			}else if(copyTo.isPojoVariable() && copyFrom.get(0).getTip().equals(TokenTipi.Array)){
				
				cast=JavaWriteUtilities.addCast(copyTo,copyFrom.get(0));
				
				fromArrayToPojoSelectedItems();
				
				JavaWriteUtilities.endCast(cast);
				
				JavaWriteUtilities.addTypeChangeFunctionToEnd(copyTo,copyFrom.get(0));
				
				//Pojodan Arraya
			}else if(copyFrom.get(0).isPojoVariable() && (copyTo.getTip().equals(TokenTipi.Array) && copyTo.isAllArrayItems())|| (copyTo.getLinkedToken()!=null &&copyTo.getLinkedToken().getTip().equals(TokenTipi.Array) && copyTo.getLinkedToken().isAllArrayItems())){
				
				fromPojoToArraySelectedItems();
				
				JavaWriteUtilities.endCast(cast);
				
				JavaWriteUtilities.addTypeChangeFunctionToEnd(copyTo,copyFrom.get(0));
				
			}else if(copyTo.isPojoVariable() && ConversionLogModel.getInstance().isMB()){
					
				JavaClassElement.javaCodeBuffer.append(JavaWriteUtilities.toCustomSetterString(copyTo, copyFrom.get(0)));
					
			}else if(copyTo.isRedefinedVariable() || (copyTo.getLinkedToken()!=null && copyTo.getLinkedToken().isRedefinedVariable())){
				//*S**ASSIGN TAX-INOUT = SCR-IN-OUT -->KET_TAX.setTaxInout(SCR_IN_OUT);
				
				JavaClassElement.javaCodeBuffer.append(JavaWriteUtilities.toCustomSetterString(copyTo));
				
				//JavaClassElement.javaCodeBuffer.append("(");
				for (int i = 0; i < copyFrom.size(); i++) {
					JavaClassElement.javaCodeBuffer.append(JavaWriteUtilities.toCustomString(copyFrom.get(i)));
				}
				
				JavaClassElement.javaCodeBuffer.append(")");
				
			}else if(typeOfCopyTo.equals("bigdecimal")){
				
				//cast=JavaWriteUtilities.addCast(copyTo,copyFrom.get(0));
				
				fromBigDecimalToBigDecimal();
				
				//JavaWriteUtilities.endCast(cast);
				
				JavaWriteUtilities.addTypeChangeFunctionToEnd(copyTo,copyFrom.get(0));
				
			}else if((copyTo.isAllArrayItems()|| copyTo.getTip().equals(TokenTipi.Array)) && copyFrom.get(0).isSayi()){
				
				cast=JavaWriteUtilities.addCast(copyTo,copyFrom.get(0));
				
				fromSingleToArray();
				
				JavaWriteUtilities.endCast(cast);
				
				JavaWriteUtilities.addTypeChangeFunctionToEnd(copyTo,copyFrom.get(0));
				
			}else{
					JavaClassElement.javaCodeBuffer.append(JavaWriteUtilities.toCustomString(copyTo));
					JavaClassElement.javaCodeBuffer.append("=");
					for (int i = 0; i < copyFrom.size(); i++) {
					
						cast=JavaWriteUtilities.addCast(copyTo,copyFrom.get(i));
					
						JavaClassElement.javaCodeBuffer.append(JavaWriteUtilities.toCustomString(copyFrom.get(i)));
						
						JavaWriteUtilities.endCast(cast);
						
						JavaWriteUtilities.addTypeChangeFunctionToEnd(copyTo,copyFrom.get(i));
						
					}
			}

			JavaClassElement.javaCodeBuffer.append(JavaConstants.DOT_WITH_COMMA + JavaConstants.NEW_LINE);

		} catch (Exception e) {
			logger.debug("//Conversion Error" + this.getClass() + this.getSourceCode().getSatirNumarasi()
					+ this.getSourceCode().getCommandName());
			JavaClassElement.javaCodeBuffer
					.append("/*Conversion Error" + this.getClass() + this.getSourceCode().getSatirNumarasi()
							+ this.getSourceCode().getCommandName() + "*/" + JavaConstants.NEW_LINE);
			logger.error("//Conversion Error:" + e.getMessage(), e);
			ConvertUtilities.writeconversionErrors(e, this);
		}
		return true;
	}








	private void minusOperationBigDecimal() throws Exception {
		
		
		if(copyTo.isPojoVariable()){
			
			StringBuilder tempCodeBuffer=new StringBuilder();
			
		
			
			boolean closeParantez=false;
			for (int i = 0; i < copyFrom.size(); i++) {
				
				if(copyFrom.get(i).isKarakter('-')){
					tempCodeBuffer.append(".subtract(");
					closeParantez=true;
				}
				
				tempCodeBuffer.append(")");
				
			}
				
			JavaClassElement.javaCodeBuffer.append(JavaWriteUtilities.toCustomSetterString(copyTo, tempCodeBuffer.toString()));
			
			
			List errorTokenList=new ArrayList<>();
			errorTokenList.add(copyTo);
			errorTokenList.add(copyFrom.get(0));
			errorTokenList.add(copyFrom.get(1));
			errorTokenList.add(copyFrom.get(2));
			
			ConversionLogModel.getInstance().writeError(3, errorTokenList,"Eksi Matematik operasyonu olması gereken üretilmemiş. Bigdecimal Pojo");
				
			
		}else{
		
			JavaClassElement.javaCodeBuffer.append(JavaWriteUtilities.toCustomString(copyTo));
			JavaClassElement.javaCodeBuffer.append("=");
			boolean closeParantez=false;
			for (int i = 0; i < copyFrom.size(); i++) {
				
				cast=JavaWriteUtilities.addCast(copyTo,copyFrom.get(i));
				
				//KONTMEB:=D_SCEKMEB(I)+D_SVFMEB(I)-D_SME(I) --> KONTMEB=D_SCEKMEB(I).add(D_SVFMEB(I)).mınus(D_SME(I));
				if(copyFrom.get(i).isKarakter('+')){
					JavaClassElement.javaCodeBuffer.append(".add(");
					closeParantez=true;
				}else if(copyFrom.get(i).isKarakter('-')){
					JavaClassElement.javaCodeBuffer.append(".subtract(");
					closeParantez=true;
				}else if(copyFrom.get(i).isKarakter('/')){
					JavaClassElement.javaCodeBuffer.append(".divide(");
					closeParantez=true;
				}else if(copyFrom.get(i).isKarakter('*')){
					JavaClassElement.javaCodeBuffer.append(".multiply(");
					closeParantez=true;
				}else{
					JavaClassElement.javaCodeBuffer.append(JavaWriteUtilities.toCustomString(copyFrom.get(i)));
					if(closeParantez){
						JavaClassElement.javaCodeBuffer.append(")");
						closeParantez=false;
					}
				}
				
				JavaWriteUtilities.endCast(cast);
				
				JavaWriteUtilities.addTypeChangeFunctionToEnd(copyTo,copyFrom.get(i));
							
			}
			
			List errorTokenList=new ArrayList<>();
			errorTokenList.add(copyTo);
			if(copyFrom.size()>0){
				errorTokenList.add(copyFrom.get(0));
			}
			if(copyFrom.size()>1){
				errorTokenList.add(copyFrom.get(1));
			}
			if(copyFrom.size()>2){
				errorTokenList.add(copyFrom.get(2));
			}
			
			ConversionLogModel.getInstance().writeError(4, errorTokenList,"Eksi Matematik operasyonu olması gereken üretilmemiş. Bigdecimal");
		}
		
	}
	
	private void minusOperation() throws Exception {
		
		JavaClassElement.javaCodeBuffer.append(JavaWriteUtilities.toCustomString(copyTo));
		JavaClassElement.javaCodeBuffer.append("=");
		JavaClassElement.javaCodeBuffer.append(JavaWriteUtilities.toCustomString(copyFrom.get(0)));
		JavaClassElement.javaCodeBuffer.append(JavaWriteUtilities.toCustomString(copyFrom.get(1)));
		JavaClassElement.javaCodeBuffer.append(JavaWriteUtilities.toCustomString(copyFrom.get(2)));
		
		List errorTokenList=new ArrayList<>();
		errorTokenList.add(copyTo);
		errorTokenList.add(copyFrom.get(0));
		errorTokenList.add(copyFrom.get(1));
		errorTokenList.add(copyFrom.get(2));
		
		ConversionLogModel.getInstance().writeError(2, errorTokenList,"Eksi Matematik operasyonu olması gereken üretilmemiş.");
		
			
	}








	//0700 MUSNOSUBE(*):=0  --> FCU.setArray(MUSNOSUBE, 0);
	private void fromSingleToArray() {
		try {
			JavaClassElement.javaCodeBuffer.append("FCU.setArray("
					+ JavaWriteUtilities.toCustomString(copyTo).toString()
					+ " , "
					+ JavaWriteUtilities.toCustomString(copyFrom.get(0)).toString()
					+")");
		} catch (Exception e) {
			JavaClassElement.javaCodeBuffer.append("FCU.setArray("+e.getMessage()+")");
		}
		
	}








	/*
	* NATURAL CODE:605   :ASSIGN TAX-EXC-DEST ( *) = SCR-EXC-DEST ( *) 
	*	-->ConvertUtilities.copyArrayToPojoSubTable(SCREEN.SCR_EXC_DEST,KET_TAX.getKetTaxAls(),"TAX_EXC_DEST" );
	 */
	private void fromArrayToPojoAllItems() throws Exception {
		
		DDM ddm= DDMList.getInstance().getDDM(copyTo);
		if(ddm==null){
			JavaClassElement.javaCodeBuffer.append("FCU.copyArrayToPojoSubTable("
				+ JavaWriteUtilities.toCustomString(copyFrom.get(0)) + "," + JavaWriteUtilities.pojosSubTablesArray(copyTo)+",\" \""+")");
		}else{
			JavaClassElement.javaCodeBuffer.append("FCU.copyArrayToPojoSubTable("
					+ JavaWriteUtilities.toCustomString(copyFrom.get(0)) + "," + JavaWriteUtilities.pojosSubTablesArray(copyTo)+",\""+ddm.getName()+"\""+")");
		}
		
	}



	private void fromPojoToArrayAllItems() throws Exception {
		
		DDM ddm= DDMList.getInstance().getDDM(copyFrom.get(0));
		
		if(ddm==null){
		JavaClassElement.javaCodeBuffer.append("FCU.copyPojoSubTableToArray("
				+ JavaWriteUtilities.pojosSubTablesArray(copyFrom.get(0))
				+",\""+copyFrom.get(0) + "\","
				+ JavaWriteUtilities.toCustomString(copyTo)+")");
		}else{
			JavaClassElement.javaCodeBuffer.append("FCU.copyPojoSubTableToArray("
					+ JavaWriteUtilities.pojosSubTablesArray(copyFrom.get(0))
					+",\""+ddm.getName() + "\","
					+ JavaWriteUtilities.toCustomString(copyTo)+")");
		}
		
	}
	
	private void fromBigDecimalToBigDecimal() throws Exception {
		
		JavaClassElement.javaCodeBuffer.append(JavaWriteUtilities.toCustomString(copyTo));
		JavaClassElement.javaCodeBuffer.append("=");
		boolean closeParantez=false;
		for (int i = 0; i < copyFrom.size(); i++) {
			
			cast=JavaWriteUtilities.addCast(copyTo,copyFrom.get(i));
			
			//KONTMEB:=D_SCEKMEB(I)+D_SVFMEB(I)-D_SME(I) --> KONTMEB=D_SCEKMEB(I).add(D_SVFMEB(I)).mınus(D_SME(I));
			if(copyFrom.get(i).isKarakter('+')){
				JavaClassElement.javaCodeBuffer.append(".add(");
				closeParantez=true;
			}else if(copyFrom.get(i).isKarakter('-')){
				JavaClassElement.javaCodeBuffer.append(".subtract(");
				closeParantez=true;
			}else if(copyFrom.get(i).isKarakter('/')){
				JavaClassElement.javaCodeBuffer.append(".divide(");
				closeParantez=true;
			}else if(copyFrom.get(i).isKarakter('*')){
				JavaClassElement.javaCodeBuffer.append(".multiply(");
				closeParantez=true;
			}else{
				JavaClassElement.javaCodeBuffer.append(JavaWriteUtilities.toCustomString(copyFrom.get(i)));
				if(closeParantez){
					JavaClassElement.javaCodeBuffer.append(")");
					closeParantez=false;
				}
			}
			
			JavaWriteUtilities.endCast(cast);
			
			JavaWriteUtilities.addTypeChangeFunctionToEnd(copyTo,copyFrom.get(i));
						
		}
	}
	
	private void fromPojoToArraySelectedItems() throws Exception {
		
		DDM ddm= DDMList.getInstance().getDDM(copyFrom.get(0));
		
		//dimensions=copyTo.get(0).getPojosDimension().getColumnNameToken().getDeger().toString().split(":");
	
		if(ddm==null){
			JavaClassElement.javaCodeBuffer.append("FCU.copyPojoSubTableToArray("
					+ JavaWriteUtilities.pojosSubTablesArray(copyFrom.get(0))
					+",\""+copyFrom.get(0) + "\","
					+copyTo.getDeger().toString()+")");
			//4470 MAP_DIZISI.MEBLAG(I):= IDGIDBS-THESAP.ACMEBLAG
		}else if(copyFrom.get(0).isPojoVariable() && !copyFrom.get(0).isAllArrayItems() && copyTo.isRecordVariable() && copyTo.getLinkedToken().isArray() && !copyTo.getLinkedToken().isAllArrayItems()){
			JavaClassElement.javaCodeBuffer.append( JavaWriteUtilities.toCustomString(copyTo)+"=");
		
			cast=JavaWriteUtilities.addCast(copyTo,copyFrom.get(0));
			
			JavaClassElement.javaCodeBuffer.append(JavaWriteUtilities.toCustomString(copyFrom.get(0)) );
			
		}else{
			JavaClassElement.javaCodeBuffer.append("FCU.copyPojoSubTableToArray("
					+ JavaWriteUtilities.pojosSubTablesArray(copyFrom.get(0))
					+",\""+ddm.getName() + "\","
					+copyTo.getDeger().toString()+")");
		}
		
	}


	/*
	 * S**ASSIGN SCR-DETAIL(1:4) = TAX-DETAIL(1:4)
	 * 
	 */
	private void fromArrayToPojoSelectedItems() throws Exception {
		
		DDM ddm= DDMList.getInstance().getDDM(copyTo);
		
		String[] dimensions;
		
//		dimensions=copyTo.getPojosDimension().getDeger().toString().split(":");
		if(ddm==null){
			JavaClassElement.javaCodeBuffer.append("FCU.copyArrayToPojoSubTable("
					+ JavaWriteUtilities.toCustomString(copyFrom.get(0)) 
					+ "," 
					+ JavaWriteUtilities.pojosSubTablesArray(copyTo)
					+",\""
					+copyFrom.get(0)
					+"\")");
		}else{
		JavaClassElement.javaCodeBuffer.append("FCU.copyArrayToPojoSubTable("
				+ JavaWriteUtilities.toCustomString(copyFrom.get(0)) 
				+ "," 
				+ JavaWriteUtilities.pojosSubTablesArray(copyTo)
				+",\""
				+ddm.getName()
				+"\")");
		}
		
	}
	

	@Override
	public boolean writeChildrenJavaToStream() {
		// TODO Auto-generated method stub
		return false;
	}

}
