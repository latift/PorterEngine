package tr.com.vbt.java.utils;

import tr.com.vbt.ddm.DDM;
import tr.com.vbt.java.util.Utility;
import tr.com.vbt.lexer.RedefinedColumn;
import tr.com.vbt.token.AbstractToken;

public abstract class AbstractJavaWriteUtility {

	protected static boolean addPojoNullControlMethod=false;

	/*1)	Boş ve  1  se bir normal
	 * 					1  A8  TAX-DOM-INT --> KetTax.getTaxDomInt()*/
	protected static String ruleEmtpy_1(DDM ddm, AbstractToken token) {
		
		StringBuilder getterString=new StringBuilder();
		
		getterString.append(token.getDeger().toString());
		
		addPojosControlMethod(token, getterString);

		getterString.append(".");
		if(token.getColumnNameToken()==null){
			getterString.append(Utility.viewNameToPojoGetterName(token.getDeger().toString()));
		}else{
			getterString.append(Utility.viewNameToPojoGetterName(token.getColumnNameToken().getDeger().toString()));
		}
		getterString.append("()");
		addPojosControlMethodCloser(getterString);
	
		
		return getterString.toString();

	}
	
	protected static void addPojosControlMethodCloser(StringBuilder getterString) {
		if(!addPojoNullControlMethod){
			return;
		}
		getterString.append("\"");
		getterString.append(")");
	
	}


	protected static void addPojosControlMethod(AbstractToken token, StringBuilder getterString){
		if(!addPojoNullControlMethod){
			return;
		}
		
		String columnReturnType=Utility.findViewAndColumnNamesReturnTypeAdabas(token);
		
		if(columnReturnType==null){
			columnReturnType=Utility.findViewAndColumnNamesReturnTypeAdabas(token);
		}
		
		
		
		if(addPojoNullControlMethod){
			if(columnReturnType==null)
			{
				getterString.append("getStringPojoValue(\"");
				
			}else if(columnReturnType.toLowerCase().equals("long")){
			
				getterString.append("getLongPojoValue(\"");
				
			}else if(columnReturnType.toLowerCase().equals("int")){  //Pojo da int olmamali ama varsada Long diye cekmeli
				
				getterString.append("getLongPojoValue(\"");
				
			}else if(columnReturnType.toLowerCase().equals("bigdecimal")){
				
				getterString.append("getBigDecimalPojoValue(\"");
				
			}else if(columnReturnType.toLowerCase().equals("date")){
				
				getterString.append("getDatePojoValue(\"");
				
			}else if(columnReturnType.toLowerCase().equals("time")){
				
				getterString.append("getTimePojoValue(\"");
				
			}else{
				getterString.append("getStringPojoValue(\"");
			}
		}
	}
	
	/*1)	Boş ve  1  se bir normal
	 * 
	 *	tableColumnReferans.put("MUL_PREFIX","KET_MULTIFILE");
		RedefinedColumn mulFrefix=new RedefinedColumn("KET_KCKGRP_EXC","KET_MULTFILE","MUL_TIMESTAMP","MUL_PREFIX",0,13,"A");
		tableColumnRedefiners.put("MUL_PREFIX", mulFrefix);
	
	 * 					1  A8  MUL-PREFIX --> KET_MULTIFILE.getMultimestamp().substring(0,13);*/
	protected static String ruleEmtpy_1_RedefinedColumn(DDM ddm, AbstractToken token) {
		
		RedefinedColumn rdfColumn=token.getColumnNameToken().getRedefinedColumn();
		
		String getterString;//TESKI;
			getterString= "getPojoValue("+"\""+token.getDeger().toString();
		
		getterString +=".";
		if(rdfColumn.getColumn()==null){
	
			getterString +=Utility.viewNameToPojoGetterName(rdfColumn.toString());
			
		}else{
			
			getterString +=Utility.viewNameToPojoGetterName(rdfColumn.getColumn().toString());
			
		}
		getterString +="()";
		if(rdfColumn.getStartIndex()!=-1){
			getterString +=".substring(";
			getterString +=rdfColumn.getStartIndex()+","+rdfColumn.getEndIndex();
			getterString +=")"+"\""+")";
		}
		
		return getterString;

	}
	
	/*1)	Boş ve  1  se bir normal
	 * 					1  A8  TAX-DOM-INT --> KetTax.setTaxDomInt()*/
	protected static String ruleEmtpy_1_setter(DDM ddm, AbstractToken token) {
		
		StringBuilder setterString=new StringBuilder();//TESKI;
		setterString.append(token.getDeger().toString());

		setterString.append(".");
		if(token.getColumnNameToken()==null){
			setterString.append(Utility.viewNameToPojoSetterName(token.getDeger().toString()));
		}else{
			setterString.append(Utility.viewNameToPojoSetterName(token.getColumnNameToken().getDeger().toString()));
		}
		setterString.append("(");
		
		return setterString.toString();

	}
	
	/*1)	Boş ve  1  se bir normal
	 * 					1  A8  TAX-DOM-INT --> KetTax.setTaxDomInt()*/
	protected static String ruleEmtpy_1_RedefinedColumnSetter(DDM ddm, AbstractToken token) {
		
		String setterString;
			setterString= "getPojoValue("+"\""+token.getDeger().toString();
		setterString +=".";
		if(token.getColumnNameToken()==null){
			setterString +=Utility.viewNameToPojoSetterName(token.getDeger().toString())+"\""+")";
		}else{
			setterString +=Utility.viewNameToPojoSetterName(token.getColumnNameToken().getDeger().toString())+"\""+")";
		}
		return setterString;

	}

	/*
	 * 	 * 		2)	M ve  1 se 
	 * 					 M 1  A9  TAX-DETAIL  -->  KetTax.getKetTaxA9s().get(i).getTaxDetail()
	 * 
	 * 
	 *  M 1  BI  AGT-TIMESTAMP                    A   19  N
	 *  	-->getPojoValue("KET_AGENCY.getBIs().get(pojosDimension-1).getAgtTimestamp");
	 */							 
	protected static String ruleM_1(DDM ddm, AbstractToken token) throws Exception {
		
		// token.getDeger() = KETTAX
		//columnt.getDeger() ==TAX_DETAIL
		
		StringBuilder getterString=new StringBuilder();
		
		addPojosControlMethod(token, getterString);
		
		getterString.append(token.getDeger().toString());
		getterString.append(".");
		
		if(token.getTypeNameOfView()!=null){
			getterString.append(Utility.viewNameToPojoGetterName(token.getTypeNameOfView()+"_"+ddm.getFirstLevelDDM().getDB()+"s()"));
		}else{
			getterString.append(Utility.viewNameToPojoGetterName(token.getDeger().toString()+"_"+ddm.getFirstLevelDDM().getDB()+"s()"));
		}
		
		getterString.append(".");
		if(token.getPojosDimension()==null){
			getterString.append("get(-1)");
		}else{
			getterString.append("get("+JavaWriteUtilities.toCustomString(token.getPojosDimension())+"-1)");
		}
		getterString.append(".");
		getterString.append(Utility.viewNameToPojoGetterName(token.getColumnNameToken().getDeger().toString()));
		
		getterString.append("()");
		addPojosControlMethodCloser(getterString);
		
		return getterString.toString();
	}
	
	/*
	 * 	 * 		2)	M ve  1 se 
	 * 					 M 1  A9  TAX-DETAIL  -->  KetTax.getKetTaxA9s().get(i).getTaxDetail()
	 */							 
	protected static String ruleM_1_RedefinedColumn(DDM ddm, AbstractToken token) throws Exception {
		
		// token.getDeger() = KETTAX
		//columnt.getDeger() ==TAX_DETAIL
		String getterString;//TESKI;
			getterString= "getPojoValue("+"\""+token.getDeger().toString();
		getterString +=".";
		getterString +=Utility.viewNameToPojoGetterName(token.getTypeNameOfView().toString())+"_"+ddm.getFirstLevelDDM().getDB()+"s()";		//getKetTaxA9s()
		getterString +=".";
		getterString +="get("+JavaWriteUtilities.toCustomString(token.getPojosDimension())+"-1)";
		getterString +=".";
		getterString +=Utility.viewNameToPojoGetterName(token.getColumnNameToken().getDeger().toString());
		getterString +="()"+"\""+")";
		return getterString;
	}

	protected static String ruleM_1_setter(DDM ddm, AbstractToken token) throws Exception {
		
		String getterString;//TESKI;
			getterString= "getPojoValue("+"\""+token.getDeger().toString();
		getterString +=".";
		getterString +=Utility.viewNameToPojoGetterName(token.getTypeNameOfView().toString())+"_"+ddm.getFirstLevelDDM().getDB()+"s()";		//getKetTaxA9s()
		getterString +=".";
		if(token.getPojosDimension()==null){
			getterString +="get("+"-1)";
		}else{
			getterString +="get("+JavaWriteUtilities.toCustomString(token.getPojosDimension())+"-1)";
		}
		getterString +=".";
		getterString +=Utility.viewNameToPojoSetterName(token.getColumnNameToken().getDeger().toString())+"\""+")";
		return getterString;
	}
	
	
	protected static String ruleM_1_RedefinedColumnSetter(DDM ddm, AbstractToken token) throws Exception {
		
		String getterString;//TESKI;
			getterString= "getPojoValue("+"\""+token.getDeger().toString();
		getterString +=".";
		getterString +=Utility.viewNameToPojoGetterName(token.getTypeNameOfView().toString())+"_"+ddm.getFirstLevelDDM().getDB()+"s()";		//getKetTaxA9s()
		getterString +=".";
		getterString +="get("+JavaWriteUtilities.toCustomString(token.getPojosDimension())+"-1)";
		getterString +=".";
		getterString +=Utility.viewNameToPojoSetterName(token.getColumnNameToken().getDeger().toString())+"\""+")";
		return getterString;
	}

	/*
	 * 	 * 		3)	P VE  1 se count içindir.
	 * 					 P 1  AC  TAX-EXEMPT-PER  -->KetTax.getKetTaxAc().size();
	 */
	protected static String ruleP_1(DDM ddm, AbstractToken token) {
		String getterString;//TESKI;
		getterString= token.getDeger().toString();
		getterString +=".";
		getterString +=Utility.viewNameToPojoGetterName(token.getTypeNameOfView().toString())+"_"+ddm.getFirstLevelDDM().getDB()+"s()";		//getKetTaxA9s()
		getterString +=".";
		getterString +="size()";
		return getterString;
	}
	
	/*
	 * 	 * 		3)	P VE  1 se count içindir.
	 * 					 P 1  AC  TAX-EXEMPT-PER  -->KetTax.getKetTaxAc().size();
	 */
	protected static String ruleP_1_RedefinedColumn(DDM ddm, AbstractToken token) {
		String getterString;//TESKI;
			getterString= "getPojoValue("+"\""+token.getDeger().toString();
		getterString +=".";
		getterString +=  "get"+token.getDeger().toString()+ddm.getFirstLevelDDM().getDB()+"s()";		//getKetTaxA9s()
		getterString +=".";
		getterString +="size()"+"\""+")";
		return getterString;
	}
	
	/**
	 *   P 1  AS  DUY-LOG
		    2  AT  DUY-USER                         A    6  N
		    2  AU  DUY-TIMESTAMP                    N 10.0  N
		    2  AV  DUY-REMARKS                      A   10  N
	 	
	 	1  AS  DUY-LOG   --> TKS_DUY.setTksDuyuruAs(new ArrayList<TksDuyuruA>());
	 */
	protected static String ruleP_1_setter(DDM ddm, AbstractToken token) throws Exception {
		
		String getterString;//TKS_DUY;
		getterString= token.getDeger().toString();
		getterString +=".";
		getterString +="set"+Utility.viewNameToPojoName(token.getDeger().toString())+ddm.getDB()+"(";		//setTksDuyuruAs(
		getterString +="new ArrayList<";
		getterString +=Utility.viewNameToPojoName(token.getDeger().toString())+ddm.getDB()+">";
		getterString +="())";
		return getterString;
	}
	
	protected static String ruleP_1_RedefinedColumnSetter(DDM ddm, AbstractToken token) throws Exception {
		throw new Exception("Unimplemented Code");
	}

	/*4)Boş ve 2 ise periodic altindadir.
	 * 					DDM KET-DOMESTIC-TAX
	 * 				  	P 1  AH  ITX-APPLIED
    					  2  AI  ITX-CITIES  
	 * 										--> KetDomesticTax.getKetTaxAh().get(i).getItxCities()*/	 
	protected static String ruleEmpty_2(DDM ddm, AbstractToken token) throws Exception {
		
		
		StringBuilder getterString=new StringBuilder();
		
		addPojosControlMethod(token, getterString);
		
		getterString.append(token.getDeger().toString());
		getterString.append(".");
		//getterString +="get"+token.getDeger().toString()+Utility.viewNameToPojoGetterName(ddm.getFirstLevelDDM().getDB())+"s()";		//getKetTaxA9s()
		getterString.append(Utility.viewNameToPojoGetterName(token.getTypeNameOfView()+"_"+ddm.getFirstLevelDDM().getDB()+"s"));
		getterString.append("()");
		getterString.append(".");
		if(token.getPojosDimension()!=null){
			getterString.append("get("+JavaWriteUtilities.toCustomString(token.getPojosDimension())+"-1)");
		}else{
			getterString.append("get()");
		}
		getterString.append(".");
		getterString.append(Utility.viewNameToPojoGetterName(token.getColumnNameToken().getDeger().toString()));

		getterString.append("()");
		addPojosControlMethodCloser(getterString);
		
		return getterString.toString();
	}
	
	protected static String ruleEmpty_2_RedefinedColumn(DDM ddm, AbstractToken token) throws Exception {
		String getterString;//TESKI;
			getterString= "getPojoValue("+"\""+token.getDeger().toString();
		getterString +=".";
		//getterString +="get"+token.getDeger().toString()+Utility.viewNameToPojoGetterName(ddm.getFirstLevelDDM().getDB())+"s()";		//getKetTaxA9s()
		getterString +=Utility.viewNameToPojoGetterName(token.getTypeNameOfView()+"_"+ddm.getFirstLevelDDM().getDB()+"s");
		getterString +="()";
		getterString +=".";
		getterString +="get("+JavaWriteUtilities.toCustomString(token.getPojosDimension())+"-1)";
		getterString +=".";
		getterString +=Utility.viewNameToPojoGetterName(token.getColumnNameToken().getDeger().toString());
		getterString +="()"+"\""+")";
		return getterString;
	}
	
	//     2  AD  TAX-EXEMPT-CODE   --> KET_TAX.getKetTaxAcs().get(i-1).setTaxExemptCode(
	protected static String ruleEmpty_2_setter(DDM ddm, AbstractToken token) throws Exception {
		StringBuilder getterString=new StringBuilder();
		getterString.append(token.getDeger().toString());
		getterString .append(".");
		//getterString .append("get"+token.getDeger().toString()+Utility.viewNameToPojoGetterName(ddm.getFirstLevelDDM().getDB())+"s()";		//getKetTaxA9s()
		getterString .append(Utility.viewNameToPojoGetterName(token.getTypeNameOfView()+"_"+ddm.getFirstLevelDDM().getDB()+"s"));
		getterString .append("()");
		getterString .append(".");
		
		if(token.getPojosDimension()==null){
			getterString .append("get("+"-1)");
		}else{
			getterString .append("get("+JavaWriteUtilities.toCustomString(token.getPojosDimension())+"-1)");
		}
		getterString .append(".");
		if(token.getColumnNameToken()==null){
			getterString .append("(");
		}else{
			getterString .append(Utility.viewNameToPojoSetterName(token.getColumnNameToken().getDeger().toString())+"(");
		}
		
		return getterString.toString();
	}
	
	//     2  AD  TAX-EXEMPT-CODE   --> KET_TAX.getKetTaxAcs().get(i-1).getTaxExemptCode()
	protected static String ruleEmpty_2_RedefinedColumnSetter(DDM ddm, AbstractToken token) throws Exception {
		String getterString;//TESKI;
			getterString= "getPojoValue("+"\""+token.getDeger().toString();
		getterString +=".";
		//getterString +="get"+token.getDeger().toString()+Utility.viewNameToPojoGetterName(ddm.getFirstLevelDDM().getDB())+"s()";		//getKetTaxA9s()
		getterString +=Utility.viewNameToPojoGetterName(token.getTypeNameOfView()+"_"+ddm.getFirstLevelDDM().getDB()+"s");
		getterString +="()";
		getterString +=".";
		getterString +="get("+JavaWriteUtilities.toCustomString(token.getPojosDimension())+"-1)";
		getterString +=".";
		getterString +=Utility.viewNameToPojoSetterName(token.getColumnNameToken().getDeger().toString())+"\""+")";
		return getterString;
	}


	/**
	 * 5) 	M  ve  2 ise 
	 * 					KET-NOTE-ATPCO
	 * 				
					  P 1  A8  NOT-CODE-PERIODIC
					  M 2  A9  NOT-CODE                         A    7  N
	 * 						
	 * 						-->KetNoteATPCO.getKetNoteAtpcoA8().get(i).getKetNoteAtpcoA9().get(i).getNotCode();
	 * @throws Exception 

	 */
	protected static String ruleM_2(DDM ddm, AbstractToken token) throws Exception {
		
		StringBuilder getterString=new StringBuilder();
		
		addPojosControlMethod(token, getterString);
		
		getterString.append(token.getDeger().toString());
		
		String dimensionFull;
		dimensionFull=token.getPojosDimension().getDeger().toString();
		String[] dimensions=dimensionFull.split(",");
		if(dimensions.length==2){
			
			getterString.append(".");
			if(token.getTypeNameOfView()==null){
				getterString.append(Utility.viewNameToPojoGetterName(token.getDeger().toString()+"_"+ddm.getFirstLevelDDM().getDB()+"s()"));		//getKetTaxA9s()
			}else{
				getterString.append(Utility.viewNameToPojoGetterName(token.getTypeNameOfView()+"_"+ddm.getFirstLevelDDM().getDB()+"s()"));		//getKetTaxA9s()
			}
			getterString.append(".");
			getterString.append("get("+dimensions[0]+"-1)");
			getterString.append(".");
			if(token.getTypeNameOfView()==null){
				getterString.append(Utility.viewNameToPojoGetterName(token.getDeger().toString()+"_"+ddm.getDB()+"s()"));		//getKetTaxA9s()
			}else{
				getterString.append(Utility.viewNameToPojoGetterName(token.getTypeNameOfView()+"_"+ddm.getDB()+"s()"));		//getKetTaxA9s()
			}
			getterString.append(".");
			getterString.append("get("+dimensions[1]+"-1)");
			getterString.append(".");
			getterString.append(Utility.viewNameToPojoGetterName(token.getColumnNameToken().getDeger().toString()));
		}else{
			//TODO: Burası hatali incelenmeli
			getterString.append(".");
			if(token.getTypeNameOfView()==null){
				getterString.append(Utility.viewNameToPojoGetterName(token.getDeger().toString()+"_"+ddm.getFirstLevelDDM().getDB()+"s()"));		//getKetTaxA9s()
			}else{
				getterString.append(Utility.viewNameToPojoGetterName(token.getTypeNameOfView()+"_"+ddm.getFirstLevelDDM().getDB()+"s()"));		//getKetTaxA9s()
			}
			getterString.append(".");
			getterString.append("get("+dimensions[0]+")");
			getterString.append(".");
			if(token.getTypeNameOfView()==null){
				getterString.append(Utility.viewNameToPojoGetterName(token.getDeger().toString()+"_"+ddm.getDB()+"s()"));		//getKetTaxA9s()
			}else{
				getterString.append(Utility.viewNameToPojoGetterName(token.getTypeNameOfView()+"_"+ddm.getDB()+"s()"));		//getKetTaxA9s()
			}
			getterString.append(".");
			getterString.append("get("+dimensions[0]+")"); //TODO: Burası hatali incelenmeli
			getterString.append(".");
			getterString.append(Utility.viewNameToPojoGetterName(token.getColumnNameToken().getDeger().toString()));
		}
		
		getterString.append("()");
		addPojosControlMethodCloser(getterString);
		
		return getterString.toString();
	}
	
	protected static String ruleM_2_setter(DDM ddm, AbstractToken token) throws Exception {
		
		StringBuilder getterString=new StringBuilder();
		getterString.append(token.getDeger().toString());
		getterString .append(".");
		getterString .append("get"+token.getTypeNameOfView().toString()+"_"+ddm.getFirstLevelDDM().getDB()+"s()");		//getKetTaxA9s()
		getterString .append(".");
		if(token.getPojosDimension()==null){
			getterString.append("get(-1)");
		}else{
			getterString .append("get("+JavaWriteUtilities.toCustomString(token.getPojosDimension())+"-1)");
		}
		getterString .append(".");
		getterString .append("get"+token.getTypeNameOfView().toString()+"_"+ddm.getDB()+"s()");		//getKetTaxA9s()
		getterString .append(".");
		getterString .append("get(i)");
		getterString .append(".");
		getterString.append(Utility.viewNameToPojoSetterName(token.getColumnNameToken().getDeger().toString()));
		
		getterString.append("(");
		
		return getterString.toString();
	}

	protected static String ruleM_2_RedefinedColumnSetter(DDM ddm, AbstractToken token) throws Exception {
		
		String getterString;//TESKI;
			getterString= token.getDeger().toString();
		getterString +=".";
		getterString +="get"+token.getDeger().toString()+ddm.getFirstLevelDDM().getDB()+"s()";		//getKetTaxA9s()
		getterString +=".";
		getterString +="get("+JavaWriteUtilities.toCustomString(token.getPojosDimension())+"-1)";
		getterString +=".";
		getterString +="get"+token.getDeger().toString()+ddm.getDB()+"s()";		//getKetTaxA9s()
		getterString +=".";
		getterString +="get(i)";
		getterString +=".";
		getterString +=Utility.viewNameToPojoSetterName(token.getColumnNameToken().getDeger().toString())+")";
		return getterString;
	}

}
