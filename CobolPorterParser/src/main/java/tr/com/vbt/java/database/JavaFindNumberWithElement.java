package tr.com.vbt.java.database;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;


import tr.com.vbt.java.AbstractJava;
import tr.com.vbt.java.general.JavaClassElement;
import tr.com.vbt.java.general.JavaConstants;
import tr.com.vbt.java.util.Utility;
import tr.com.vbt.java.utils.ConvertUtilities;
import tr.com.vbt.lexer.ConversionLogModel;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.KelimeToken;
import tr.com.vbt.token.TokenTipi;

// 4218   FIND IDGIDBS-TAZIL WITH MUSNO=+MUSNO2 SORTED BY GIRTAR GIRZAM   

// FIND LIMAN WITH L-ICAO EQ FZ-DEPARTURE-ICAO 
/*
 * 
		LIMAN_RESULT_LIST=LIMAN_DAO.findByMusno2AndReferansSmallerAndOpenParBsicilOrAsicilCloseParAndIslemTar(GecMusno2, Map.refno, 0,0,Guntar);
		{
		finderIndex=true;
		while(finderIndex){
			if(LIMAN_RESULT_LIST==null || LIMAN_RESULT_LIST.size==0){
				  	if No.writeJava{
						break;
					}
			}else{
				Iterotor it=LIMAN_RESULT_LIST.iterator(); 
				while(it.hasnext()){
					LIMAN=it.next();
					ULKE_KODU=LIMAN.getLUlkeKodu();
				}
			}
			finderIndex=false;
		}
 */

/**

NUMBER = runPreparedStatementInt("select count(*)  from TCT.TKS_REZ this_ where this_.REZ_CNR=" + DIYEZ_CNR, "Integer");

  */

public class JavaFindNumberWithElement extends AbsctractConditionalJavaElement implements FinderJavaElement{
	
	ConversionLogModel logModel=ConversionLogModel.getInstance();

	final static Logger logger = Logger.getLogger(JavaFindNumberWithElement.class);

	private AbstractToken viewName; // LIMAN
	
	private AbstractToken viewNameToken; //LIMAN;
	
	private String selectCountString; //method call from natural
	
	private AbstractJava javaIfNoRecords;
	
	public boolean writeJavaToStream() throws Exception{
		super.writeJavaToStream();

		String viewNameWithoutUnderscore;
		
		viewName = (AbstractToken) this.getParameters().get("viewName");
		
		viewNameToken=new KelimeToken(viewName, 0, 0, 0);  //Tablo ismi.
		
		pojoType=Utility.viewNameToPojoName(viewName.getTypeNameOfView());
		
		conditionList = (List<AbstractToken>) this.parameters.get("conditionList");
		parseThruKeyword();
		parseSortList();
		convertConditions(); // Tek token olmayan filtre operatorlerini tek tokena düşürür.
		defineConditionTokenTypes();
		try {
			convertConditionsToFilters();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {


			//NUMBER = runPreparedStatementInt("select count(*)  from TCT.TKS_REZ this_ where this_.REZ_CNR=" + DIYEZ_CNR, "Integer");

			JavaClassElement.javaCodeBuffer.append("NUMBER");
			JavaClassElement.javaCodeBuffer.append("=");
			JavaClassElement.javaCodeBuffer.append("runPreparedStatementInt(\"");
			selectCountString="select count(*)  from TCT."+pojoType+" this_ where this_";
			JavaClassElement.javaCodeBuffer.append(selectCountString);
			
			JavaClassElement.javaCodeBuffer.append(", \"Integer\")"+ JavaConstants.DOT_WITH_COMMA + JavaConstants.NEW_LINE);
					
		} catch (Exception e) {
			logger.debug("//Conversion Error"+this.getClass()+this.getSourceCode().getSatirNumarasi()+this.getSourceCode().getCommandName());
			JavaClassElement.javaCodeBuffer.append("/*Conversion Error"+this.getClass()+this.getSourceCode().getSatirNumarasi()
					+this.getSourceCode().getCommandName()+"*/"+JavaConstants.NEW_LINE);
			logger.error("//Conversion Error:"+e.getMessage(), e); 
			ConvertUtilities.writeconversionErrors(e, this);
		}

		return true;
	}




	private void defineConditionTokenTypes() {
		AbstractToken currentToken = null;
		
		/**
		 * Filtrename ve FiltreValue birbirini takip eder.
		 */
		boolean isFiltrename=true;
		
		for (int index=0; index<conditionList.size();index++ ) {
			
			currentToken=conditionList.get(index);
			
			if(currentToken.getTip().equals(TokenTipi.OzelKelime)||
					(currentToken.getTip().equals(TokenTipi.Kelime)&& 
							(currentToken.getDeger().equals(ReservedNaturalKeywords.AND)|| currentToken.getDeger().equals(ReservedNaturalKeywords.OR)))){
				currentToken.setFiltrejoiner(true);
			}else if(currentToken.getTip().equals(TokenTipi.Karakter)){
				if(currentToken.getDeger().equals('(')|| currentToken.getDeger().equals(')')){
					currentToken.setFiltreParantez(true);
				}else{
					currentToken.setFilterOPerator(true);
				}
			}else {
				if(isFiltrename){
					currentToken.setFilterName(true);
					isFiltrename=false;
				}else{
					currentToken.setFiltreValue(true);	
					isFiltrename=true;
				}
			}
		}
		
	}

	public AbstractToken getViewName() {
		return viewName;
	}


	public void setViewName(AbstractToken viewName) {
		this.viewName = viewName;
	}

	@Override
	public AbstractToken getPojoToken() {
		return viewNameToken;
	}



	
	
}
