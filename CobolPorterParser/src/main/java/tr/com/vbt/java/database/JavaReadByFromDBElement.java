package tr.com.vbt.java.database;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.java.AbstractJavaElement;
import tr.com.vbt.java.general.JavaClassElement;
import tr.com.vbt.java.general.JavaConstants;
import tr.com.vbt.java.util.Utility;
import tr.com.vbt.java.utils.ConvertUtilities;
import tr.com.vbt.java.utils.JavaWriteUtilities;
import tr.com.vbt.lexer.ConversionLogModel;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.natural.parser.datalayout.program.ElementProgramDataTypeNatural;
import tr.com.vbt.natural.parser.datalayout.program.ElementProgramOneDimensionArrayNatural;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.OzelKelimeToken;
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
 3642 FIND IDGIDBS-TIPTALCEK WITH (MUSNO1=SECMUSNO1 OR MUSNO2=SECMUSNO1)                                                             
 3644     AND DOVIZ=WDOVIZ AND                                                                                                       
 3646     KALAN_MEBLAG> 0 AND AKOD1^='S' AND AKOD2^='S'                                                                              
 3648   IF NO                                                                                                                        
 3650     ESCAPE ROUTINE                                                                                                             
 3652   END-NOREC                                                                                                                     
 3328 END-FIND 
 
 
			   Criteria crit = getSession().createCriteria(getPersistentClass());
		        Criterion c =Restrictions.eq("id.musno1", sECMUSNO1);
		       Criterion c2 =Restrictions.eq("id.musno2", sECMUSNO1);
		       
		       Criterion c3 =Restrictions.eq("id.doviz", wDOVIZ);
		      // Criterion c6 =Restrictions.gt("kalanMeblag",(float) 0);
		       Criterion c7 =Restrictions.not(Restrictions.eq("akod1", akod));
		       Criterion c8 =Restrictions.not(Restrictions.eq("akod2", akod));
		       
		       
		       crit.add(Restrictions.or(c, c2));
		       crit.add(c3);
		      // crit.add(c6);
		       crit.add(c7);
		       crit.add(c8);
			    
		       return crit.list();
 
 FindByOpParantezMusnoEqualsOrMusno2EqualsCloseParantes
 */
/**
 * @author 47159500
 *
 */
/**
 * Algoritma:
 * 
 * 1) ^= --> Tek bir tokena çevir.
 * 2) Filtreleri tek bir filtre token olarak set et. Filtreden sonra gelen = i ve diğer token i token içinde fitreoperator ve filtrevaluetoken olarak set et.
 * 3) AND, OR ve Parantezleri filtrejoiner olarak set et.
 * 4) Bu aşamalardan sonra sonuç: (Musno1, or, musno2,), and, doviz, and, kalanmeblag, and, akod1, and, akod2  Yani: (Filtre,Joiner,Filtre,), Joiner Filtre vs...)
   5)   a) Criteria crit = getSession().createCriteria(getPersistentClass());
        b) Parantez gördü isen subfonksiyonu çağır. Parantez içindeki ifadenin criterion objesini yarat. 
        c) Filtre gördü isen filtreyi yarat. 
        			Filtreden sonraki joiner ifadeye göre and ise  crit.add(c3); yap.
        												or ise  crit.or(c3); yap.
        
 */ 
public class JavaReadByFromDBElement extends AbsctractConditionalJavaElement {
	
	ConversionLogModel logModel=ConversionLogModel.getInstance();

	final static Logger logger = LoggerFactory.getLogger(JavaReadByFromDBElement.class);

	private AbstractToken viewName; // LIMAN
	
	private String pojoName; // Liman 

	private List<AbstractToken> sortList;
	
	String calculatedResultListName = "";// LIMAN_RESULT_LIST
	String calculatedDAOName = "";
	String findByString, findByMethodSignature,itName;
	
	private AbstractJavaElement javaIfNoRecords;
	
	public boolean writeJavaToStream() throws Exception{
		super.writeJavaToStream();

		viewName = (AbstractToken) this.getParameters().get("viewName");
		
		pojoName=Utility.viewNameToPojoName(viewName.getTypeNameOfView());
		
		conditionList = (List<AbstractToken>) this.parameters.get("conditionList");
		parseSortList();
		convertConditions(); // Tek token olmayan filtre operatorlerini tek tokena düşürür.
		defineConditionTokenTypes();
		try {
			convertConditionsToFilters();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		

		calculatedResultListName = "";// LIMAN_RESULT_LIST
		calculatedDAOName = "";
		findByString=createFindByString();
		findByMethodSignature=createFindByMethodString();
		logger.debug("findByString :"+findByString);
		logger.debug("findByMethodSignature :"+findByMethodSignature);
		//itName="it"+pojoName;
		itName=itNameManager.createIteratorName(pojoName);
		

		calculatedResultListName = viewName.toCustomString() + "_RESULT_LIST";
		calculatedDAOName = viewName.getTypeNameOfView() + "_DAO";
		
		javaIfNoRecords=this.getChildWithName("JavaIfNoRecords");
		
		
		try {


			//LIMAN_RESULT_LIST=LIMAN_DAO.findByMusno2AndReferansSmallerAndOpenParBsicilOrAsicilCloseParAndIslemTar(GecMusno2, Map.refno, 0,0,Guntar);
			JavaClassElement.javaCodeBuffer.append(calculatedResultListName);
			JavaClassElement.javaCodeBuffer.append("=");
			JavaClassElement.javaCodeBuffer.append(calculatedDAOName);
			JavaClassElement.javaCodeBuffer.append(".");
			JavaClassElement.javaCodeBuffer.append(findByString.replaceAll("-", "_")+ JavaConstants.DOT_WITH_COMMA+ JavaConstants.NEW_LINE);
			
			//{
			JavaClassElement.javaCodeBuffer.append("{"+ JavaConstants.NEW_LINE);
			
				//finderIndex=true;
				JavaClassElement.javaCodeBuffer.append("finderIndex=true"+ JavaConstants.DOT_WITH_COMMA+ JavaConstants.NEW_LINE);
			
				//while(finderIndex){
				JavaClassElement.javaCodeBuffer.append("while(finderIndex){"+ JavaConstants.NEW_LINE);
			
			
						//	if(LIMAN_RESULT_LIST==null || LIMAN_RESULT_LIST.size==0){
						JavaClassElement.javaCodeBuffer.append("if("+calculatedResultListName+"==null || "+calculatedResultListName+".size()==0){"+ JavaConstants.NEW_LINE);
			
						//
						if(javaIfNoRecords!=null){
									this.getChildren().remove(0); //aşağıda tekrar yazılmasın diye listeden çıkarılır.
									javaIfNoRecords.writeJavaToStream();
							}
			
						//	}else{
						JavaClassElement.javaCodeBuffer.append("}else{"+ JavaConstants.NEW_LINE);
				
								//		Iterotor it=LIMAN_RESULT_LIST.iterator(); 
								JavaClassElement.javaCodeBuffer.append("Iterator<"+pojoName+"> "+itName+"="+calculatedResultListName+".iterator()"+JavaConstants.DOT_WITH_COMMA+ JavaConstants.NEW_LINE);
								
								//		while(it.hasnext()){
								JavaClassElement.javaCodeBuffer.append("while("+itName+".hasNext()){"+ JavaConstants.NEW_LINE);
			
											//			LIMAN=it.next();
											JavaClassElement.javaCodeBuffer.append(viewName.toCustomString()+"="+itName+".next()"+JavaConstants.DOT_WITH_COMMA+ JavaConstants.NEW_LINE);
											
											JavaClassElement.javaCodeBuffer.append("ISN=(int) "+viewName.toCustomString()+".getIsn()"+JavaConstants.DOT_WITH_COMMA+ JavaConstants.NEW_LINE);
											
											//			ULKE_KODU=LIMAN.getLUlkeKodu();
											this.writeChildrenJavaToStream();
			
								//		}
								JavaClassElement.javaCodeBuffer.append("}"+"//While Iterator End"+ JavaConstants.NEW_LINE);
			
						//	}
						JavaClassElement.javaCodeBuffer.append("}"+"//Else End"+ JavaConstants.NEW_LINE);
			
						//finderIndex=false;
						JavaClassElement.javaCodeBuffer.append("finderIndex=false"+JavaConstants.DOT_WITH_COMMA+ JavaConstants.NEW_LINE);
			
				//}
				JavaClassElement.javaCodeBuffer.append("}"+ "//While End"+ JavaConstants.NEW_LINE);
			//}
			JavaClassElement.javaCodeBuffer.append("}"+ "//Find End"+ JavaConstants.NEW_LINE);
			
		} catch (Exception e) {
			logger.debug("//Conversion Error"+this.getClass()+this.getSourceCode().getSatirNumarasi()+this.getSourceCode().getCommandName());
			JavaClassElement.javaCodeBuffer.append("/*Conversion Error"+this.getClass()+this.getSourceCode().getSatirNumarasi()
					+this.getSourceCode().getCommandName()+"*/"+JavaConstants.NEW_LINE);
			logger.error("//Conversion Error:"+e.getMessage(), e); 
			ConvertUtilities.writeconversionErrors(e, this);
		}

		writeHibernateCode();
		writeDAOInterfaceCode();
		return true;
	}



	private void writeDAOInterfaceCode() {
		
		JavaClassElement.javaDAOInterfaceCodeBuffer=new StringBuilder();
		JavaClassElement.javaDAOInterfaceCodeBuffer.append(JavaConstants.NEW_LINE);
		JavaClassElement.javaDAOInterfaceCodeBuffer.append(JavaConstants.NEW_LINE);
		JavaClassElement.javaDAOInterfaceCodeBuffer.append("public List<"+pojoName+">");
		JavaClassElement.javaDAOInterfaceCodeBuffer.append(" ");
		JavaClassElement.javaDAOInterfaceCodeBuffer.append(findByMethodSignature+JavaConstants.DOT_WITH_COMMA+JavaConstants.NEW_LINE);
		JavaClassElement.javaDAOInterfaceCodeBuffer.append(JavaConstants.NEW_LINE);
		
		JavaClassElement.javaDAOInterfaceCodeMap.put(pojoName+" "+findByMethodSignature, JavaClassElement.javaDAOInterfaceCodeBuffer.toString());
		//folderPath+"output"+"/"+"generatedinterface"+"/"+viewName+"GenDAO.java
		//StringBuffer interfaceHeader=ConvertUtilities.writeInterfaceHeader(pojoName);
		//WriteToFile.appendToFileWithHeader(interfaceHeader, JavaClassElement.javaDAOInterfaceCodeBuffer,logModel.getFullJavaDAOInterfaceFileName(pojoName));
			
	}

	/*
 * LIMAN_DAO.findByMusno2AndReferansSmallerAndOpenParBsicilOrAsicilCloseParAndIslemTar(GecMusno2, Map.refno, 0,0,Guntar){
 * 			   Criteria main_crit = getSession().createCriteria(getPersistentClass());
			       Criterion c =Restrictions.eq("id.musno1", sECMUSNO1);
			       Criterion c2 =Restrictions.eq("id.musno2", sECMUSNO1);
			       return Restrictions.or(c, c2);
		       main_crit.add(cParantez);
		       
		       main_crit.add(Restrictions.eq("id.doviz", wDOVIZ));
		       main_crit.add(Restrictions.not(Restrictions.eq("akod1", akod)));
		       
		      return crit.list();
		       
  5)   a) Criteria crit = getSession().createCriteria(getPersistentClass());
        b) Parantez gördü isen subfonksiyonu çağır. Parantez içindeki ifadenin criterion objesini yarat. 
        c) Filtre gördü isen filtreyi yarat. 
        		fitre Op = ise
        d)Filtreden sonraki joiner ifadeye göre and ise  crit.add(c3); yap.
        												or ise  crit.or(c3); yap.
 * }
 */
	private void writeHibernateCode() {
		Filter filter;
		AbstractToken curToken;
		
		List conditionListInParantesiz;
		
		String criteriaInParantez;
		
		boolean orCondition = false;
		
		JavaClassElement.javaHibernateCodeBuffer=new StringBuilder();
		JavaClassElement.javaHibernateCodeBuffer.append(JavaConstants.NEW_LINE);
		JavaClassElement.javaHibernateCodeBuffer.append("// Generated For Program: "+logModel.getFileName());
		JavaClassElement.javaHibernateCodeBuffer.append(JavaConstants.NEW_LINE);
		JavaClassElement.javaHibernateCodeBuffer.append("@Override"+JavaConstants.NEW_LINE);
		JavaClassElement.javaHibernateCodeBuffer.append("public List<"+pojoName+">");
		JavaClassElement.javaHibernateCodeBuffer.append(" ");
		JavaClassElement.javaHibernateCodeBuffer.append(findByMethodSignature+ JavaConstants.OPEN_BRACKET +JavaConstants.NEW_LINE);
		JavaClassElement.javaHibernateCodeBuffer.append("Criteria main_crit = currentSession().createCriteria(getPersistentClass());"+JavaConstants.NEW_LINE);
		for(int index=0; index<conditionListWithFiltersAndParantesiz.size();index++){
			if(conditionListWithFiltersAndParantesiz.get(index) instanceof AbstractToken){// b) Parantez gördü isen subfonksiyonu çağır. Parantez içindeki ifadenin criterion objesini yarat. 
				curToken=(AbstractToken) conditionListWithFiltersAndParantesiz.get(index);
				if(curToken.getDeger().equals('(')){
					conditionListInParantesiz=new ArrayList<>();
					index++;
					curToken=null;
					filter=null;
					if(conditionListWithFiltersAndParantesiz.get(index) instanceof AbstractToken){
						curToken=(AbstractToken) conditionListWithFiltersAndParantesiz.get(index);
					}else if(conditionListWithFiltersAndParantesiz.get(index) instanceof Filter){
						filter=(Filter) conditionListWithFiltersAndParantesiz.get(index);
					}
				
					do{
						if(curToken!=null){
							conditionListInParantesiz.add(curToken);
						}else{
							conditionListInParantesiz.add(filter);
						}
						curToken=null;
						filter=null;
						index++;
						
						if(conditionListWithFiltersAndParantesiz.get(index) instanceof AbstractToken){
							curToken=(AbstractToken) conditionListWithFiltersAndParantesiz.get(index);
						}else if(conditionListWithFiltersAndParantesiz.get(index) instanceof Filter){
							filter=(Filter) conditionListWithFiltersAndParantesiz.get(index);
						}
					
					}while(curToken==null||!curToken.getDeger().equals(')'));
					criteriaInParantez=writeHibernateCodeInParantez(conditionListInParantesiz);
					JavaClassElement.javaHibernateCodeBuffer.append("main_crit.add("+criteriaInParantez+")"+JavaConstants.DOT_WITH_COMMA+JavaConstants.NEW_LINE);
					
				}
			}else if(conditionListWithFiltersAndParantesiz.get(index) instanceof Filter){
				
					if(index<conditionListWithFiltersAndParantesiz.size()-1){
						Object nextElement=conditionListWithFiltersAndParantesiz.get(index+1);
						if(nextElement instanceof AbstractToken){
							OzelKelimeToken nextToken=(OzelKelimeToken) nextElement;
							if(nextToken.getDeger().equals("OR")){
								orCondition=true;
								break;
							}
						}
					}
					
					createFilterWithAndConditions(index);
					
				
					JavaClassElement.javaHibernateCodeBuffer.append(JavaConstants.DOT_WITH_COMMA+JavaConstants.NEW_LINE);
				
			}
		}
		if(orCondition){
			createFilterWithOrConditions();
		}
		JavaClassElement.javaHibernateCodeBuffer.append("return main_crit.list();");
		JavaClassElement.javaHibernateCodeBuffer.append(JavaConstants.NEW_LINE+JavaConstants.CLOSE_BRACKET +JavaConstants.NEW_LINE);
		JavaClassElement.javaHibernateCodeBuffer.append(JavaConstants.NEW_LINE);
		
		JavaClassElement.javaHibernateCodeMap.put(pojoName+" "+findByMethodSignature, JavaClassElement.javaHibernateCodeBuffer.toString());
		//folderPath+"output"+"/"+"generatedinterface"+"/"+viewName+"GenDAO.java
		//StringBuffer hibernateHeader=ConvertUtilities.writeDAOImplemantasyonClassHeader(pojoName);
		//WriteToFile.appendToFileWithHeader(hibernateHeader, JavaClassElement.javaHibernateCodeBuffer,logModel.getFullJavaHibernateFileName(pojoName));
			


	}
	
	private void createFilterWithOrConditions() {
		AbstractToken curToken;
		Filter filter;
		StringBuffer result=new StringBuffer();
		result.append("Restrictions.or(");
		for(int index=0; index<conditionListWithFiltersAndParantesiz.size();index++){
			if(conditionListWithFiltersAndParantesiz.get(index) instanceof AbstractToken){
				
				curToken=(AbstractToken) conditionListWithFiltersAndParantesiz.get(index);
				
				result.append(",");
			
			}else if(conditionListWithFiltersAndParantesiz.get(index) instanceof Filter){
		
					filter=(Filter) conditionListWithFiltersAndParantesiz.get(index);
					
					if(filter.filterOperator.getDeger().equals('=')){
							result.append("Restrictions.eq(\"");
					}else if(filter.filterOperator.getDeger().equals('>')){
							result.append("Restrictions.gt(\"");
					}else if(filter.filterOperator.getDeger().equals('<')){
							result.append("Restrictions.lt(\"");
					}else if(filter.filterOperator.getDeger().equals("^=")){
							result.append("Restrictions.not(Restrictions.eq(\"");
					}else if(filter.filterOperator.getDeger().equals(">=")){
							result.append("Restrictions.ge(\"");
					}else if(filter.filterOperator.getDeger().equals("<=")){
							result.append("Restrictions.le(\"");
					}else{
						result.append("Restrictions.unknown(");
					}
					
					
					if(filter.getFilterValue().isRecordVariable()){
						result.append(Utility.columnNameToPojoFieldName(filter.getFilterName().getDeger().toString())+"\", "+filter.getFilterValue().getLinkedToken().getDeger().toString().replaceAll("-","_"));
					}else if(filter.getFilterValue().isPojoVariable()){
						result.append(Utility.columnNameToPojoFieldName(filter.getFilterName().getDeger().toString())+"\", "+filter.getFilterValue().getColumnNameToken().getDeger().toString().replaceAll("-","_"));
					}else{
						result.append(Utility.columnNameToPojoFieldName(filter.getFilterName().getDeger().toString())+"\", "+filter.getFilterValue().getDeger().toString().replaceAll("-","_"));
						
					}
					
					result.append(")");
					if(filter.filterOperator.getDeger().equals("^=")){
						result.append(")");
					}
			}
		}
		result.append(")");
		JavaClassElement.javaHibernateCodeBuffer.append(result.toString());
		JavaClassElement.javaHibernateCodeBuffer.append(JavaConstants.DOT_WITH_COMMA+ JavaConstants.NEW_LINE);
		
	}

	private void createFilterWithAndConditions(int index) {
		Filter filter;
		filter=(Filter) conditionListWithFiltersAndParantesiz.get(index);
		if(filter.filterOperator.getDeger().equals('=')){
			JavaClassElement.javaHibernateCodeBuffer.append("main_crit.add(Restrictions.eq(\"");
		}else if(filter.filterOperator.getDeger().equals('>')){
			JavaClassElement.javaHibernateCodeBuffer.append("main_crit.add(Restrictions.gt(\"");
			
		}else if(filter.filterOperator.getDeger().equals('<')){
			JavaClassElement.javaHibernateCodeBuffer.append("main_crit.add(Restrictions.lt(\"");
			
		}else if(filter.filterOperator.getDeger().equals("^=")){
			
			JavaClassElement.javaHibernateCodeBuffer.append("main_crit.add(Restrictions.not(Restrictions.eq(\"");
		}else if(filter.filterOperator.getDeger().equals(">=")){
			
			JavaClassElement.javaHibernateCodeBuffer.append("main_crit.add(Restrictions.ge(\"");
		}else if(filter.filterOperator.getDeger().equals("<=")){
			
			JavaClassElement.javaHibernateCodeBuffer.append("main_crit.add(Restrictions.le(\"");
		}else{
			JavaClassElement.javaHibernateCodeBuffer.append("main_crit.add(Restrictions.unknown(");
		}

		if(filter.getFilterValue().getTip().equals(TokenTipi.Sayi)){
			JavaClassElement.javaHibernateCodeBuffer.append(filter.getFilterName().getDeger().toString().replaceAll("-","_").toLowerCase()+"\", "+filter.getFilterName().getDeger().toString().replaceAll("-","_")+")");
		}else if(filter.getFilterValue().isRecordVariable()){
			JavaClassElement.javaHibernateCodeBuffer.append(Utility.columnNameToPojoFieldName(filter.getFilterName().getDeger().toString())+"\", "+filter.getFilterValue().getLinkedToken().getDeger().toString().replaceAll("-","_")+")");
		}else if(filter.getFilterValue().isPojoVariable()){
			JavaClassElement.javaHibernateCodeBuffer.append(Utility.columnNameToPojoFieldName(filter.getFilterName().getDeger().toString())+"\", "+filter.getFilterValue().getColumnNameToken().getDeger().toString().replaceAll("-","_")+")");
		}else{
			JavaClassElement.javaHibernateCodeBuffer.append(Utility.columnNameToPojoFieldName(filter.getFilterName().getDeger().toString())+"\", "+filter.getFilterValue().getDeger().toString().replaceAll("-","_")+")");
			
		}
		
		JavaClassElement.javaHibernateCodeBuffer.append(")");
		if(filter.filterOperator.getDeger().equals("^=")){
			JavaClassElement.javaHibernateCodeBuffer.append(")");
		}
		
	}


	//main_crit.add(Restrictions.or(Restrictions.eq("musno1", MUSNO1),Restrictions.eq("musno2", MUSNO2)));
	// Aşağıdaki method Restrictions.or(Restrictions.eq("musno1", MUSNO1),Restrictions.eq("musno2", MUSNO2)) donmeli.
	private String writeHibernateCodeInParantez(List conditionListInParantesiz) {
		
		AbstractToken curToken;
		Filter filter;
		StringBuffer result=new StringBuffer();
		result.append("Restrictions.or(");
		for(int index=0; index<conditionListInParantesiz.size();index++){
			if(conditionListInParantesiz.get(index) instanceof AbstractToken){
				
				curToken=(AbstractToken) conditionListInParantesiz.get(index);
				
				result.append(",");
			
			}else if(conditionListInParantesiz.get(index) instanceof Filter){
		
					filter=(Filter) conditionListInParantesiz.get(index);
					
					if(filter.filterOperator.getDeger().equals('=')){
							result.append("Restrictions.eq(\"");
					}else if(filter.filterOperator.getDeger().equals('>')){
							result.append("Restrictions.gt(\"");
					}else if(filter.filterOperator.getDeger().equals('<')){
							result.append("Restrictions.lt(\"");
					}else if(filter.filterOperator.getDeger().equals("^=")){
							result.append("Restrictions.not(Restrictions.eq(\"");
					}else if(filter.filterOperator.getDeger().equals(">=")){
							result.append("Restrictions.ge(\"");
					}else if(filter.filterOperator.getDeger().equals("<=")){
							result.append("Restrictions.le(\"");
					}else{
						result.append("Restrictions.unknown(");
					}
					
					
					if(filter.getFilterValue().isRecordVariable()){
						result.append(Utility.columnNameToPojoFieldName(filter.getFilterName().getDeger().toString())+"\", "+filter.getFilterValue().getLinkedToken().getDeger().toString().replaceAll("-","_"));
					}else if(filter.getFilterValue().isPojoVariable()){
						result.append(Utility.columnNameToPojoFieldName(filter.getFilterName().getDeger().toString())+"\", "+filter.getFilterValue().getColumnNameToken().getDeger().toString().replaceAll("-","_"));
					}else{
						result.append(Utility.columnNameToPojoFieldName(filter.getFilterName().getDeger().toString())+"\", "+filter.getFilterValue().getDeger().toString().replaceAll("-","_"));
						
					}
					
					result.append(")");
					if(filter.filterOperator.getDeger().equals("^=")){
						result.append(")");
					}
			}
		}
		result.append(")");
		return result.toString();
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


	/*
	private String createFindByString() {
		StringBuffer findBy=new StringBuffer("findBy");
		for(int index=0; index<filterList.size();index++){
			findBy.append(filterList.get(index).getFilterName());
			findBy.append(filterList.get(index).getFilterOperator());
			findBy.append(filterList.get(index).getFilterValue());
		}
		return findBy.toString();
	}*/

	// 4218   FIND IDGIDBS-TAZIL WITH MUSNO=+MUSNO2 SORTED BY GIRTAR GIRZAM    --> FIND IDGIDBS-TAZIL WITH MUSNO=+MUSNO2  ve  SORTED BY GIRTAR GIRZAM
	private void parseSortList() { 
		 List<AbstractToken> newConditionList=new ArrayList<AbstractToken>();
		 List<AbstractToken> newSortList=new ArrayList<AbstractToken>();
		 boolean sortReached=false;
		for(int index=0; index<conditionList.size();index++){
			if(conditionList.get(index).getTip().equals(TokenTipi.Kelime)&& conditionList.get(index).getDeger().equals(ReservedNaturalKeywords.SORTED_BY)){
				sortReached=true;
			}
			
			if(sortReached){
				newSortList.add(conditionList.get(index));
			}else{
				newConditionList.add(conditionList.get(index));
			}
		}
		this.conditionList=newConditionList;
		this.sortList=newSortList;
	}

	

	private void convertStartingFromToFilter() {
	
		
	}

	private void convertStartingFromEndingAtToFilter() {
		// TODO Auto-generated method stub
		
	}
	
	private boolean conditionListContainsStartingFrom() {
		
		AbstractToken curToken;
		for(int index=0;index<conditionList.size();index++){
			
			curToken=conditionList.get(index);
			if(curToken.isOzelKelime(ReservedNaturalKeywords.STARTING_FROM)){
				return true;
			}
		}
		return false;
	}

	private boolean conditionListContainsStartingFromAndEndingAt() {
		AbstractToken curToken;
		
		boolean hasStartingFrom = false, hasEndingAt = false;
		for(int index=0;index<conditionList.size();index++){
			
			curToken=conditionList.get(index);
			if(curToken.isOzelKelime(ReservedNaturalKeywords.STARTING_FROM)){
				hasStartingFrom=true;
			}else if(curToken.isOzelKelime(ReservedNaturalKeywords.ENDING_AT)){
				hasEndingAt=true;
			}
			
			if(hasStartingFrom&&hasEndingAt){
				return true;
			}
			
		}
		return false;
	}

	public AbstractToken getViewName() {
		return viewName;
	}

	public void setViewName(AbstractToken viewName) {
		this.viewName = viewName;
	}

	
	
}
