package tr.com.vbt.java.database;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tr.com.vbt.java.AbstractJava;
import tr.com.vbt.java.general.JavaClassElement;
import tr.com.vbt.java.general.JavaConstants;
import tr.com.vbt.java.util.Utility;
import tr.com.vbt.java.utils.ConvertUtilities;
import tr.com.vbt.lexer.ConversionLogModel;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.token.AbstractToken;
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
	
	private List<AbstractToken> sortList;
	
	String calculatedResultListName = "";// LIMAN_RESULT_LIST
	String calculatedDAOName = "";
	
	String findByString,itName; //method call from natural
	//MethodSignature findByMethodSignature;  //DAOInterface
	//MethodImplementation findByMethodImplemantation; //HibernateDAO
	
	private AbstractJava javaIfNoRecords;
	
	public boolean writeJavaToStream() throws Exception{
	
		super.writeJavaToStream();
		
		try{

		viewName = (AbstractToken) this.getParameters().get("viewName");
		
		pojoType=Utility.viewNameToPojoName(viewName.getTypeNameOfView());
		
		parseSortList();
		convertConditions(); // Tek token olmayan filtre operatorlerini tek tokena düşürür.
		
		defineConditionTokenTypes();
	
		convertConditionsToFilters();
	
		
		calculatedResultListName = "";// LIMAN_RESULT_LIST
		calculatedDAOName = "";
		
		findByString=createFindByString("readBy");
		findByMethodSignature=createFindByMethodString("readBy", pojoType);
		
		logger.debug("findByMethodSignature :"+findByMethodSignature);
		//itName="it"+pojoType;
		itName=itNameManager.createIteratorName(pojoType);
		

		calculatedResultListName = viewName.getTypeNameOfView().replaceAll("-", "_") + "_RESULT_LIST";
		calculatedDAOName = viewName.getTypeNameOfView().replaceAll("-", "_") + "_DAO";
		
		javaIfNoRecords=this.getChildWithName("JavaIfNoRecords");
		
		
		
		//LIMAN_RESULT_LIST=LIMAN_DAO.findByMusno2AndReferansSmallerAndOpenParBsicilOrAsicilCloseParAndIslemTar(GecMusno2, Map.refno, 0,0,Guntar);
		JavaClassElement.javaCodeBuffer.append("List<"+pojoType+"> ");
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
		
					addTryBlock();
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
							JavaClassElement.javaCodeBuffer.append("Iterator<"+pojoType+"> "+itName+"="+calculatedResultListName+".iterator()"+JavaConstants.DOT_WITH_COMMA+ JavaConstants.NEW_LINE);
							
							//		while(it.hasnext()){
							JavaClassElement.javaCodeBuffer.append("while("+itName+".hasNext()){"+ JavaConstants.NEW_LINE);
		
										
										//			LIMAN=it.next();
										JavaClassElement.javaCodeBuffer.append(viewName.toCustomString()+"="+itName+".next()"+JavaConstants.DOT_WITH_COMMA+ JavaConstants.NEW_LINE);
										
										if(ConversionLogModel.getInstance().getCustomer().equals("THY")){
										JavaClassElement.javaCodeBuffer.append("ISN=(int) "+viewName.toCustomString()+".getIsn()"+JavaConstants.DOT_WITH_COMMA+ JavaConstants.NEW_LINE);
										}
										//			ULKE_KODU=LIMAN.getLUlkeKodu();
										this.writeChildrenJavaToStream();
										
										
		
							//		}
							JavaClassElement.javaCodeBuffer.append("}"+"//While Iterator End"+ JavaConstants.NEW_LINE);
		
					//	}
					JavaClassElement.javaCodeBuffer.append("}"+"//Else End"+ JavaConstants.NEW_LINE);
		
					//finderIndex=false;
					JavaClassElement.javaCodeBuffer.append("finderIndex=false"+JavaConstants.DOT_WITH_COMMA+ JavaConstants.NEW_LINE);
					
				addCatchBlock();
					
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

	

	
	private void defineConditionTokenTypes() {
		AbstractToken currentToken = null;
		
		/**
		 * Filtrename ve FiltreValue birbirini takip eder.
		 */
		boolean isFiltrename=true;
		
		if(conditionList==null){
			return;
		}
		
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
		if(conditionList==null){
			return;
		}
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
