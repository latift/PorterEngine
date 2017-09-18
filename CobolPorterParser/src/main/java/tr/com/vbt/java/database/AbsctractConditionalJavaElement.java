package tr.com.vbt.java.database;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.java.AbstractJavaElement;
import tr.com.vbt.java.MethodImplementation;
import tr.com.vbt.java.MethodParameter;
import tr.com.vbt.java.MethodSignature;
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
import tr.com.vbt.token.KarakterToken;
import tr.com.vbt.token.KelimeToken;
import tr.com.vbt.token.OzelKelimeToken;
import tr.com.vbt.token.TokenTipi;

public abstract class AbsctractConditionalJavaElement extends AbstractJavaElement {

	final static Logger logger = LoggerFactory.getLogger(AbsctractConditionalJavaElement.class);

	protected List conditionListWithFiltersAndParantesiz;

	protected List<AbstractToken> conditionList;

	protected IteratorNameManager itNameManager = new IteratorNameManager();
	
	protected MethodSignature findByMethodSignature;  //DAOInterface
	
	protected MethodImplementation findByMethodImplemantation; //HibernateDAO
	
	protected String pojoType; // Liman 

	protected void convertConditionsToFilters() {
		conditionListWithFiltersAndParantesiz = new ArrayList();
		Filter fl;
		AbstractToken filterName = null, filterOperator, filterValue, curToken;
		
		if(conditionList==null){
			return;
		}
		for (int index = 0; index < conditionList.size(); index++) {
			curToken = conditionList.get(index);
			logger.debug("curToken:" + curToken.getDeger().toString());
			if (curToken.isFiltreParantez() || curToken.isFiltrejoiner()) {
				conditionListWithFiltersAndParantesiz.add(curToken);
				System.out.println("Token Added:" + curToken);
			} else if (curToken.isFilterName()) {
				filterName = curToken;
				index++;
				if(conditionList.size()==index){
					return;
				}
				filterOperator = conditionList.get(index);
				index++;
				if(conditionList.size()==index){
					return;
				}
				filterValue = conditionList.get(index);

				fl = new Filter(filterName, filterOperator, filterValue);
				conditionListWithFiltersAndParantesiz.add(fl);
				System.out.println("Filter Added:" + fl.toString());
			}
		}
	}

	// ^= --> ^= Tek token
	// >= --> >= Tek token
	// <= --> <= Tek token

	protected List<AbstractToken> convertConditions() {

		List<AbstractToken> resultList = new ArrayList<AbstractToken>();

		AbstractToken currentToken = null, nextToken = null, previousToken = null;

		System.out.println(conditionList);
		
		if(conditionList==null){
			return null;
		}

		for (int index = 0; index < conditionList.size(); index++) {

			currentToken = conditionList.get(index);

			if (index > 0) {
				previousToken = conditionList.get(index - 1);
			}
			//
			if (currentToken.getDeger().equals('^')) {
				nextToken = conditionList.get(index + 1);
				if (nextToken.getDeger().equals('=')) {
					currentToken.setDeger("^=");
					conditionList.remove(index + 1);
				}
			} else if (currentToken.getDeger().equals('>')) {
				nextToken = conditionList.get(index + 1);
				if (nextToken.getDeger().equals('=')) {
					currentToken.setDeger(">=");
					conditionList.remove(index + 1);
				}
			} else if (currentToken.getDeger().equals('<')) {
				nextToken = conditionList.get(index + 1);
				if (nextToken.getDeger().equals('=')) {
					currentToken.setDeger("<=");
					conditionList.remove(index + 1);
				}
			}
			resultList.add(currentToken);
		}
		return resultList;
	}

	protected class Filter {

		AbstractToken filterName;

		AbstractToken filterOperator;

		AbstractToken filterValue;

		public Filter(AbstractToken filterName, AbstractToken filterOperator, AbstractToken filterValue) {
			super();
			this.filterName = filterName;
			this.filterOperator = filterOperator;
			this.filterValue = filterValue;
		}

		@Override
		public String toString() {
			return "Filter [filterName=" + filterName.getDeger() + ", filterOperator=" + filterOperator.getDeger()
					+ ", filterValue=" + filterValue.getDeger() + "]";
		}

		public AbstractToken getFilterName() {
			return filterName;
		}

		public void setFilterName(AbstractToken filterName) {
			this.filterName = filterName;
		}

		public AbstractToken getFilterOperator() {
			return filterOperator;
		}

		public void setFilterOperator(AbstractToken filterOperator) {
			this.filterOperator = filterOperator;
		}

		public AbstractToken getFilterValue() {
			return filterValue;
		}

		public void setFilterValue(AbstractToken filterValue) {
			this.filterValue = filterValue;
		}

	}

	

	private String operatorInfoToMethodName(Filter curFilter) {
		if (curFilter.getFilterOperator().getDeger().equals(">=")) {
			return "GE";
		} else if (curFilter.getFilterOperator().getDeger().equals("<=")) {
			return "LE";
		} else if (curFilter.getFilterOperator().getDeger().equals(">")) {
			return "GT";
		} else if (curFilter.getFilterOperator().getDeger().equals(">")) {
			return "LT";
		} else if (curFilter.getFilterOperator().getDeger().equals("^=")) {
			return "NE";
		}
		return "";
	}
	
	
	/*
	 * DAO ve HibernateDAO daki method imzasını oluşturur
	 */
	protected MethodSignature createFindByMethodString(String isFindByOrReadBy, String returnPojoType) {
		//StringBuffer findByString = new StringBuffer(isFindByOrReadBy);
		
		MethodSignature createdFindByMethod=new MethodSignature(isFindByOrReadBy,returnPojoType);
		
		AbstractToken curToken;
		Filter curFilter;
		AbstractCommand variableDefinition;
		ElementProgramDataTypeNatural variableDefinitionProgramDataTypeNatural = null;
		ElementProgramOneDimensionArrayNatural variableDefinitionOneDimensionArray = null;

		String methodParameterType, methodParameterName;
		
		
		
		if(conditionListWithFiltersAndParantesiz==null || conditionListWithFiltersAndParantesiz.size()==0){
			if(isFindByOrReadBy.equals("readBy")){
				
				return new MethodSignature("readBy",returnPojoType);
			
			}else{
				
				return new MethodSignature("findAll",returnPojoType);
		
			}
			
		}

		for (int index = 0; index < conditionListWithFiltersAndParantesiz.size(); index++) {
			curFilter = null;
			curToken = null;
			if (conditionListWithFiltersAndParantesiz.get(index) instanceof Filter) {
				curFilter = (Filter) conditionListWithFiltersAndParantesiz.get(index);
			} else {
				curToken = (AbstractToken) conditionListWithFiltersAndParantesiz.get(index);
			}

			// FIND IDGIDBS-TBESYIL WITH MUSNO=PMUSNO AND HESHARYIL>=10
			// -->findByMusno_HesharyilGE_
			if (curFilter != null) {
				logger.debug(curFilter.getFilterName().getDeger().toString());
				if (curFilter.getFilterName().isPojoVariable()) {
					if (curFilter.getFilterName().getColumnNameToken() == null) {
						createdFindByMethod.getMethodName().append(Utility.columnNameToPojoFieldNameWithFirstLetterUpper(
								curFilter.getFilterName().getDeger().toString()));
					} else {
						createdFindByMethod.getMethodName().append(Utility.columnNameToPojoFieldNameWithFirstLetterUpper(
								curFilter.getFilterName().getColumnNameToken().getDeger().toString()));
					}
				} else if (curFilter.getFilterName().isRecordVariable()) {
					if (curFilter.getFilterName().getColumnNameToken() == null) {
						createdFindByMethod.getMethodName().append(Utility.recordNameToRecordDotRecordFieldName(curFilter.getFilterName()));
					}else{
						createdFindByMethod.getMethodName().append(Utility.recordNameToRecordDotRecordFieldName(curFilter.getFilterName().getColumnNameToken()));
					}
				} else {
					if (curFilter.getFilterName().getColumnNameToken() == null) {
						createdFindByMethod.getMethodName().append(Utility.columnNameToPojoFieldNameWithFirstLetterUpper(
								curFilter.getFilterName().getDeger().toString()));
					}else{
						createdFindByMethod.getMethodName().append(Utility.columnNameToPojoFieldNameWithFirstLetterUpper(
								curFilter.getFilterName().getColumnNameToken().getDeger().toString()));
					}
				}
				createdFindByMethod.getMethodName().append(operatorInfoToMethodName(curFilter));

				if (index < conditionListWithFiltersAndParantesiz.size() - 1) {
					createdFindByMethod.getMethodName().append("_");
				}
			} else {
				if (curToken.getDeger().equals('(')) {
					// createdFindByMethod.getMethodName().append("OpenPar");
				} else if (curToken.getDeger().equals(ReservedNaturalKeywords.OR)) {
					createdFindByMethod.getMethodName().append("Or");
					// createdFindByMethod.getMethodName().append("ClosePar");
				}
			}

		}
		//createdFindByMethod.getMethodName().append(JavaConstants.OPEN_NORMAL_BRACKET);
		for (int index = 0; index < conditionListWithFiltersAndParantesiz.size(); index++) {
			curFilter = null;
			if (conditionListWithFiltersAndParantesiz.get(index) instanceof Filter) {
				curFilter = (Filter) conditionListWithFiltersAndParantesiz.get(index);
			} else {
				continue;
			}

			logger.debug(curFilter.getFilterValue().getDeger().toString());
			if (curFilter.getFilterValue().getTip().equals(TokenTipi.Sayi)) {
				methodParameterType = ConvertUtilities.getVariableTypeOfString(curFilter.getFilterValue()).toString();
				methodParameterName = curFilter.getFilterName().getDeger().toString().replaceAll("-", "_");
			} else if (curFilter.getFilterValue().isRecordVariable()) {
				methodParameterType = ConvertUtilities.getVariableTypeOfString(curFilter.getFilterValue().getLinkedToken()).toString();
				methodParameterName = curFilter.getFilterValue().getLinkedToken().getDeger().toString().replaceAll("-",
						"_");
			} else if (curFilter.getFilterValue().isPojoVariable()) {
				
				methodParameterType = ConvertUtilities.getPojosFieldType(curFilter.getFilterValue());
				
				methodParameterName = curFilter.getFilterValue().getColumnNameToken().getDeger().toString()
						.replaceAll("-", "_");
			} else {
				methodParameterType = ConvertUtilities.getVariableTypeOfString(curFilter.getFilterValue()).toString();
				methodParameterName = curFilter.getFilterValue().getDeger().toString().replaceAll("-", "_");
			}
			//createdFindByMethod.getMethodName().append( methodParameterType + " " + ConvertUtilities.onlyFieldOfIncludedVariable(methodParameterName));

			MethodParameter parameter=new MethodParameter();
			parameter.setName(ConvertUtilities.onlyFieldOfIncludedVariable(methodParameterName));
			parameter.setType(methodParameterType);
			
			createdFindByMethod.getParams().add(parameter);
			
		}

		return createdFindByMethod;
	}
	
	/*
	 * (MUSNO1=SECMUSNO1 OR MUSNO2=SECMUSNO1)                                                             
 		3644     AND DOVIZ=WDOVIZ AND                                                                                                       
 		3646     KALAN_MEBLAG> 0 AND AKOD1^='S' AND AKOD2^='S'
 
	-->findByMusno2AndReferansSmallerAndOpenParBsicilOrAsicilCloseParAndIslemTar(GecMusno2, Map.refno, 0,0,Guntar);
	
	*/
	/*
	 *    FilterNameve JoinOperatorleriOnce Ekle 
	 *    Sonra Parantez Açıp FiltreValue ları ekle.
	 */
	/*
	 * Java kodundan DAO methodunu çağıran kodu oluşturur
	 */
	protected String createFindByString(String isFindByOrReadBy) throws Exception {
		StringBuffer findByString=new StringBuffer(isFindByOrReadBy);
		AbstractToken curToken;
		Filter curFilter;
		
		if(conditionListWithFiltersAndParantesiz==null || conditionListWithFiltersAndParantesiz.size()==0){
			if(isFindByOrReadBy.equals("readBy")){
				return "readAll()";
			}else{
				return "findAll()";
			}
			
		}
		for(int index=0; index<conditionListWithFiltersAndParantesiz.size();index++){
			curFilter=null;
			curToken=null;
			if (conditionListWithFiltersAndParantesiz.get(index) instanceof Filter){ 
				curFilter=(Filter) conditionListWithFiltersAndParantesiz.get(index);
			}else{
				curToken=(AbstractToken) conditionListWithFiltersAndParantesiz.get(index);
			}
			
			if(curFilter!=null){
				
				if(curFilter.getFilterName().isRecordVariable()) {
					findByString.append( Utility.recordNameToRecordDotRecordFieldName(curFilter.getFilterName()));
				}else {
					if(curFilter.getFilterName().getColumnNameToken()!=null){
					
						findByString.append(Utility.columnNameToPojoFieldNameWithFirstLetterUpper(curFilter.getFilterName().getColumnNameToken().getDeger().toString()));
								
					}else{
						
						findByString.append(Utility.columnNameToPojoFieldNameWithFirstLetterUpper(curFilter.getFilterName().getDeger().toString()));
				
					}
				}
				
				if(index<conditionListWithFiltersAndParantesiz.size()-1){
					findByString.append("_");
				}
			}else{
				if(curToken.getDeger().equals('(')){
					//findByString.append("OpenPar");
				}else if(curToken.getDeger().equals(ReservedNaturalKeywords.OR)){
					findByString.append("Or");
					//findByString.append("ClosePar");
				}
			}
		
		}
		findByString.append(JavaConstants.OPEN_NORMAL_BRACKET);
		Object lastItem;
		lastItem=conditionListWithFiltersAndParantesiz.get(conditionListWithFiltersAndParantesiz.size()-1);
		for(int index=0; index<conditionListWithFiltersAndParantesiz.size();index++){
			curFilter=null;
			if (conditionListWithFiltersAndParantesiz.get(index) instanceof Filter){ 
				curFilter=(Filter) conditionListWithFiltersAndParantesiz.get(index);
			}else{
				continue;
			}
			
			/*if(curFilter.getFilterValue().isPojoVariable() ||curFilter.getFilterValue().isRecordVariable() || curFilter.getFilterValue().isConstantVariableWithQuota() || curFilter.getFilterValue().isArray()) {
				//findByString.append(Utility.recordNameToRecordDotRecordFieldName(curFilter.getFilterValue()));
				findByString.append(JavaWriteUtilities.toCustomString(curFilter.getFilterValue()));
			}else {
				findByString.append(ConvertUtilities.onlyFieldOfIncludedVariable(curFilter.getFilterValue().getDeger().toString()));
			}*/
			findByString.append(JavaWriteUtilities.toCustomString(curFilter.getFilterValue()));
			
			
			
			if(index<conditionListWithFiltersAndParantesiz.size()-1  && lastItem instanceof Filter){
				findByString.append(",");
			}
			if(index<conditionListWithFiltersAndParantesiz.size()-2  && lastItem instanceof KarakterToken){ // ) varsa sonunda
				findByString.append(",");
			}
		}
		
		findByString.append(JavaConstants.CLOSE_NORMAL_BRACKET);
	
		return findByString.toString();
	}

	protected void writeDAOInterfaceCode() {
		
		setCallerOfMethod();
		
		JavaClassElement.javaDAOInterfaceCodeMap.put(findByMethodSignature.toUniqueString(), findByMethodSignature);
		writeMethodSignatures();
		
	}
	
	private void writeMethodSignatures() {
		
		logger.debug("Methods:");
		for ( Map.Entry<String, MethodSignature> entry : JavaClassElement.javaDAOInterfaceCodeMap.entrySet()) {
			String key = entry.getKey();
			MethodSignature value = entry.getValue();
			
			logger.debug(value.toString()+ value.writeCallingPrograms());
		}
		logger.debug("Methods End");
		
	}

	private void setCallerOfMethod() {
		MethodSignature storedMethod= JavaClassElement.javaDAOInterfaceCodeMap.get(findByMethodSignature.toString());
		
		if(storedMethod!=null){
			findByMethodSignature.getCallerPrograms().addAll(storedMethod.getCallerPrograms());
		}
		
		findByMethodSignature.getCallerPrograms().add(ConversionLogModel.getInstance().getFileName());
		
	}

	protected void writeHibernateCode() {
		Filter filter;
		AbstractToken curToken;
		
		List conditionListInParantesiz;
		
		String criteriaInParantez;
		
		boolean orCondition = false;
		
		findByMethodImplemantation=new MethodImplementation(findByMethodSignature);
		
		findByMethodImplemantation.getMethodImplementation().append("Criteria main_crit = currentSession().createCriteria(getPersistentClass());"+JavaConstants.NEW_LINE);
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
					findByMethodImplemantation.getMethodImplementation().append("main_crit.add("+criteriaInParantez+")"+JavaConstants.DOT_WITH_COMMA+JavaConstants.NEW_LINE);
					
				}
			}else if(conditionListWithFiltersAndParantesiz.get(index) instanceof Filter){
				
					if(index<conditionListWithFiltersAndParantesiz.size()-1){
						Object nextElement=conditionListWithFiltersAndParantesiz.get(index+1);
						if(nextElement instanceof AbstractToken){
							
							OzelKelimeToken nextToken=null;
							
							KelimeToken nextKelimeToken = null;
							
							if(nextElement instanceof OzelKelimeToken){
								nextToken=(OzelKelimeToken) nextElement;
								if(nextToken.getDeger().equals("OR")){
									orCondition=true;
									break;
								}
							}else if(nextElement instanceof KelimeToken){
								
								nextKelimeToken=(KelimeToken) nextElement;
								
								if(nextKelimeToken!=null && nextKelimeToken.getDeger()!=null&&nextKelimeToken.getDeger().equals("OR")){
									orCondition=true;
									break;
								}else{
									logger.debug(nextKelimeToken.toString());
								}
							}
						}
							
					}
					
					createFilterWithAndConditions(index);
					
				
					findByMethodImplemantation.getMethodImplementation().append(JavaConstants.DOT_WITH_COMMA+JavaConstants.NEW_LINE);
				
			}
		}
		if(orCondition){
			createFilterWithOrConditions();
		}
		findByMethodImplemantation.getMethodImplementation().append("return main_crit.list();");
		
		JavaClassElement.javaHibernateCodeMap.put(findByMethodSignature.toUniqueString(), findByMethodImplemantation);

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
		
		private void createFilterWithAndConditions(int index) {
			Filter filter;
			filter=(Filter) conditionListWithFiltersAndParantesiz.get(index);
			if(filter.filterOperator.getDeger().equals('=')){
				findByMethodImplemantation.getMethodImplementation().append("main_crit.add(Restrictions.eq(\"");
			}else if(filter.filterOperator.getDeger().equals('>')){
				findByMethodImplemantation.getMethodImplementation().append("main_crit.add(Restrictions.gt(\"");
				
			}else if(filter.filterOperator.getDeger().equals('<')){
				findByMethodImplemantation.getMethodImplementation().append("main_crit.add(Restrictions.lt(\"");
				
			}else if(filter.filterOperator.getDeger().equals("^=")){
				
				findByMethodImplemantation.getMethodImplementation().append("main_crit.add(Restrictions.not(Restrictions.eq(\"");
			}else if(filter.filterOperator.getDeger().equals(">=")){
				
				findByMethodImplemantation.getMethodImplementation().append("main_crit.add(Restrictions.ge(\"");
			}else if(filter.filterOperator.getDeger().equals("<=")){
				
				findByMethodImplemantation.getMethodImplementation().append("main_crit.add(Restrictions.le(\"");
			}else{
				findByMethodImplemantation.getMethodImplementation().append("main_crit.add(Restrictions.unknown(");
			}

			if(filter.getFilterValue().getTip().equals(TokenTipi.Sayi)){
				findByMethodImplemantation.getMethodImplementation().append(filter.getFilterName().getDeger().toString().replaceAll("-","_").toLowerCase()+"\", "+filter.getFilterName().getDeger().toString().replaceAll("-","_")+")");
			}else if(filter.getFilterValue().isRecordVariable()){
				findByMethodImplemantation.getMethodImplementation().append(Utility.columnNameToPojoFieldName(filter.getFilterName().getDeger().toString())+"\", "+filter.getFilterValue().getLinkedToken().getDeger().toString().replaceAll("-","_")+")");
			}else if(filter.getFilterValue().isPojoVariable()){
				findByMethodImplemantation.getMethodImplementation().append(Utility.columnNameToPojoFieldName(filter.getFilterName().getDeger().toString())+"\", "+filter.getFilterValue().getColumnNameToken().getDeger().toString().replaceAll("-","_")+")");
			}else{
				findByMethodImplemantation.getMethodImplementation().append(Utility.columnNameToPojoFieldName(filter.getFilterName().getDeger().toString())+"\", "+filter.getFilterValue().getDeger().toString().replaceAll("-","_")+")");
				
			}
			
			findByMethodImplemantation.getMethodImplementation().append(")");
			if(filter.filterOperator.getDeger().equals("^=")){
				findByMethodImplemantation.getMethodImplementation().append(")");
			}
			
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
						
						
						if(filter.getFilterValue().getTip().equals(TokenTipi.Sayi)){
							result.append(Utility.columnNameToPojoFieldName(filter.getFilterName().getDeger().toString())+"\", "+filter.getFilterName().getDeger().toString());
						}else if(filter.getFilterValue().isRecordVariable()){
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
			findByMethodImplemantation.getMethodImplementation().append(result.toString());
			findByMethodImplemantation.getMethodImplementation().append(JavaConstants.DOT_WITH_COMMA+ JavaConstants.NEW_LINE);
			
		}

		

}
