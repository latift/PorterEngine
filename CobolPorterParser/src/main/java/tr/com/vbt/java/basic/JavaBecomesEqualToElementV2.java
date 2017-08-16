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
			
			if(copyTo.getLinkedToken()!=null && copyFrom.get(0).getLinkedToken()!=null&& copyFrom.get(0).getLinkedToken().isAllArrayItems()){
				copyFrom.get(0).setAllArrayItems(true);
			}
			if(copyTo.getLinkedToken()!=null&& copyTo.getLinkedToken().isAllArrayItems()){
				copyTo.setAllArrayItems(true);
			}
			
			
			//*S**ASSIGN SCR-PAX-DSCR(*)  = TAX-PAX-DSCR(*)
			if(copyTo.isAllArrayItems() &&copyFrom.get(0).isPojoVariable()&& copyFrom.get(0).getColumnNameToken().isAllArrayItems()){  
					
					fromPojoToArrayAllItems();
				
			//*S**ASSIGN TAX-EXC-DEST(*) = SCR-EXC-DEST(*) ArrayAllItemdan PojoArraye
			}else if(copyFrom.get(0).isAllArrayItems() &&copyTo.isPojoVariable()&& copyTo.getColumnNameToken().isAllArrayItems()){ 				
				
				fromArrayToPojoAllItems();
				
			}else if(copyTo.isPojoVariable() && copyFrom.get(0).getTip().equals(TokenTipi.Array)){
				
				fromArrayToPojoSelectedItems();
				
				//Pojodan Arraya
			}else if(copyFrom.get(0).isPojoVariable() && (copyTo.getTip().equals(TokenTipi.Array)|| (copyTo.getLinkedToken()!=null &&copyTo.getLinkedToken().getTip().equals(TokenTipi.Array)))){
				
				fromPojoToArraySelectedItems();
				
			}else if(copyTo.isPojoVariable() && ConversionLogModel.getInstance().isMB()){
					//7220 IDGIDBS-TGECICI.HSONVALOR:=*DAT4I --> TGECICI.setHSonvalor();
					
					JavaClassElement.javaCodeBuffer.append(JavaWriteUtilities.toCustomSetterString(copyTo, copyFrom.get(0)));
					
				/*	for (int i = 0; i < copyFrom.size(); i++) {
						JavaClassElement.javaCodeBuffer.append(JavaWriteUtilities.toCustomString(copyFrom.get(i)));
					}*/
					
					//JavaClassElement.javaCodeBuffer.append(")");
					
			}else if(copyTo.isPojoVariable() || copyTo.isRedefinedVariable()){
				//*S**ASSIGN TAX-INOUT = SCR-IN-OUT -->KET_TAX.setTaxInout(SCR_IN_OUT);
				
				JavaClassElement.javaCodeBuffer.append(JavaWriteUtilities.toCustomSetterString(copyTo));
				
				JavaClassElement.javaCodeBuffer.append("(");
				for (int i = 0; i < copyFrom.size(); i++) {
					JavaClassElement.javaCodeBuffer.append(JavaWriteUtilities.toCustomString(copyFrom.get(i)));
				}
				
				JavaClassElement.javaCodeBuffer.append(")");
				
			}else{
					JavaClassElement.javaCodeBuffer.append(JavaWriteUtilities.toCustomString(copyTo));
					JavaClassElement.javaCodeBuffer.append("=");
					for (int i = 0; i < copyFrom.size(); i++) {
						JavaClassElement.javaCodeBuffer.append(JavaWriteUtilities.toCustomString(copyFrom.get(i)));
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





	/*
	* NATURAL CODE:605   :ASSIGN TAX-EXC-DEST ( *) = SCR-EXC-DEST ( *) 
	*	-->ConvertUtilities.copyArrayToPojoSubTable(SCREEN.SCR_EXC_DEST,KET_TAX.getKetTaxAls(),"TAX_EXC_DEST" );
	 */
	private void fromArrayToPojoAllItems() throws Exception {
		
		DDM ddm= DDMList.getInstance().getDDM(copyTo);
		if(ddm==null){
			JavaClassElement.javaCodeBuffer.append("FrameworkConvertUtilities.copyArrayToPojoSubTable("
				+ JavaWriteUtilities.toCustomString(copyFrom.get(0)) + "," + JavaWriteUtilities.pojosSubTablesArray(copyTo)+",\" \""+")");
		}else{
			JavaClassElement.javaCodeBuffer.append("FrameworkConvertUtilities.copyArrayToPojoSubTable("
					+ JavaWriteUtilities.toCustomString(copyFrom.get(0)) + "," + JavaWriteUtilities.pojosSubTablesArray(copyTo)+",\""+ddm.getName()+"\""+")");
		}
		
	}



	private void fromPojoToArrayAllItems() throws Exception {
		
		DDM ddm= DDMList.getInstance().getDDM(copyFrom.get(0));
		
		if(ddm==null){
		JavaClassElement.javaCodeBuffer.append("FrameworkConvertUtilities.copyPojoSubTableToArray("
				+ JavaWriteUtilities.pojosSubTablesArray(copyFrom.get(0))
				+",\""+copyFrom.get(0) + "\","
				+ JavaWriteUtilities.toCustomString(copyTo)+")");
		}else{
			JavaClassElement.javaCodeBuffer.append("FrameworkConvertUtilities.copyPojoSubTableToArray("
					+ JavaWriteUtilities.pojosSubTablesArray(copyFrom.get(0))
					+",\""+ddm.getName() + "\","
					+ JavaWriteUtilities.toCustomString(copyTo)+")");
		}
		
	}
	
	
	
	private void fromPojoToArraySelectedItems() throws Exception {
		
		DDM ddm= DDMList.getInstance().getDDM(copyFrom.get(0));
		
		//dimensions=copyTo.get(0).getPojosDimension().getColumnNameToken().getDeger().toString().split(":");
	
		if(ddm==null){
			JavaClassElement.javaCodeBuffer.append("FrameworkConvertUtilities.copyPojoSubTableToArray("
					+ JavaWriteUtilities.pojosSubTablesArray(copyFrom.get(0))
					+",\""+copyFrom.get(0) + "\","
					+copyTo.getDeger().toString()+")");
			//4470 MAP_DIZISI.MEBLAG(I):= IDGIDBS-THESAP.ACMEBLAG
		}else if(copyFrom.get(0).isPojoVariable() && !copyFrom.get(0).isAllArrayItems() && copyTo.isRecordVariable() && copyTo.getLinkedToken().isArray() && !copyTo.getLinkedToken().isAllArrayItems()){
			JavaClassElement.javaCodeBuffer.append( JavaWriteUtilities.toCustomString(copyTo)+"="+JavaWriteUtilities.toCustomString(copyFrom.get(0)) );
		}else{
			JavaClassElement.javaCodeBuffer.append("FrameworkConvertUtilities.copyPojoSubTableToArray("
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
			JavaClassElement.javaCodeBuffer.append("FrameworkConvertUtilities.copyArrayToPojoSubTable("
					+ JavaWriteUtilities.toCustomString(copyFrom.get(0)) 
					+ "," 
					+ JavaWriteUtilities.pojosSubTablesArray(copyTo)
					+",\""
					+copyFrom.get(0)
					+"\")");
		}else{
		JavaClassElement.javaCodeBuffer.append("FrameworkConvertUtilities.copyArrayToPojoSubTable("
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
