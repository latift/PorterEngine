package tr.com.vbt.java.database;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;


import javassist.compiler.Javac;
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
import tr.com.vbt.java.utils.VariableTypes;
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

	final static Logger logger = Logger.getLogger(AbsctractConditionalJavaElement.class);

	protected List conditionListWithFiltersAndParantesiz;

	protected List<AbstractToken> conditionList;

	protected IteratorNameManager itNameManager = new IteratorNameManager();
	
	protected MethodSignature findByMethodSignature;  //DAOInterface
	
	protected MethodImplementation findByMethodImplemantation; //HibernateDAO
	
	protected String pojoType; // Liman 

	private String endCastStr="";
	
	protected List<AbstractToken> sortList;
	
	protected AbstractToken maxResultCount; //Liman

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

		AbstractToken currentToken = null, nextToken = null, previousToken = null, newOperatorToken=null;

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
			}else if (currentToken.isOzelKelime("EQ")) {
					newOperatorToken=new KarakterToken("=", 0, 0, 0);
					conditionList.set(index, newOperatorToken);
			}else if (currentToken.isOzelKelime("GE")) {
					newOperatorToken=new KarakterToken(">=", 0, 0, 0);
					conditionList.set(index, newOperatorToken);
			}else if (currentToken.isOzelKelime("GT")) {
					newOperatorToken=new KarakterToken(">", 0, 0, 0);
					conditionList.set(index, newOperatorToken);
			}else if (currentToken.isOzelKelime("LE")) {
				newOperatorToken=new KarakterToken("<=", 0, 0, 0);
				conditionList.set(index, newOperatorToken);
			}else if (currentToken.isOzelKelime("LT")) {
				newOperatorToken=new KarakterToken("<", 0, 0, 0);
				conditionList.set(index, newOperatorToken);
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
		} else if (curFilter.getFilterOperator().getDeger().equals(">") || curFilter.getFilterOperator().isKarakter('>')) {
			return "GT";
		} else if (curFilter.getFilterOperator().getDeger().equals("<") || curFilter.getFilterOperator().isKarakter('<')) {
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
		
		String methodName=createMethodName(isFindByOrReadBy);
		
		MethodSignature createdFindByMethod=new MethodSignature(methodName,returnPojoType);
		
		Filter curFilter;

		String methodParameterType, methodParameterName;
		
		if(conditionListWithFiltersAndParantesiz==null || conditionListWithFiltersAndParantesiz.size()==0){
			if(isFindByOrReadBy.equals("readBy")){
				
				return new MethodSignature(methodName,returnPojoType);
			
			}else{
				
				return new MethodSignature(methodName,returnPojoType);
		
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

			logger.debug(curFilter.getFilterName().getDeger().toString());
			
			if (curFilter.getFilterName().isRecordVariable()) {
				methodParameterType = ConvertUtilities.getVariableTypeOfString(curFilter.getFilterName().getLinkedToken()).toString();
				methodParameterName = curFilter.getFilterName().getLinkedToken().getDeger().toString();
			} else if (curFilter.getFilterName().isPojoVariable()) {
				
				methodParameterType = ConvertUtilities.getPojosFieldType(curFilter.getFilterName());
				
				methodParameterName = curFilter.getFilterName().getColumnNameToken().getDeger().toString()
						.replaceAll("-", "_");
			}else{
				methodParameterType = ConvertUtilities.getVariableTypeOfString(curFilter.getFilterName()).toString();
				if(curFilter.getFilterName()!=null && curFilter.getFilterName().getColumnNameToken()!=null){
					methodParameterName = curFilter.getFilterName().getColumnNameToken().getDeger().toString().replaceAll("-", "_");
				}else{
					methodParameterName = curFilter.getFilterName().getDeger().toString().replaceAll("-", "_");
				}
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
		
		StringBuffer findByString=new StringBuffer();
		
		Filter curFilter;
		
		findByString.append(createMethodName(isFindByOrReadBy));
		
		findByString.append(JavaConstants.OPEN_NORMAL_BRACKET);
		
		if(conditionListWithFiltersAndParantesiz.size()>0){
			Object lastItem=null;
			try {
				lastItem=conditionListWithFiltersAndParantesiz.get(conditionListWithFiltersAndParantesiz.size()-1);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for(int index=0; index<conditionListWithFiltersAndParantesiz.size();index++){
				curFilter=null;
				if (conditionListWithFiltersAndParantesiz.get(index) instanceof Filter){ 
					curFilter=(Filter) conditionListWithFiltersAndParantesiz.get(index);
				}else{
					continue;
				}
	
				findByString.append(addCastForConstantNumber(curFilter));
			
				
				findByString.append(JavaWriteUtilities.toCustomString(curFilter.getFilterValue()));
				
				findByString.append(endCastStr);
				
				endCastStr="";
				
				
				
				if(index<conditionListWithFiltersAndParantesiz.size()-1  && lastItem instanceof Filter){
					findByString.append(",");
				}
				if(index<conditionListWithFiltersAndParantesiz.size()-2  && lastItem instanceof KarakterToken){ // ) varsa sonunda
					findByString.append(",");
				}
			}
		}
		findByString.append(JavaConstants.CLOSE_NORMAL_BRACKET);
	
		return findByString.toString();
	}


	private String addCastForConstantNumber(Filter curFilter) {
		
		if(!curFilter.getFilterValue().isSayi()){
			return "";
		}
		
		StringBuffer castString=new StringBuffer();
		
		String filterType=ConvertUtilities.getPojosFieldType(pojoType, curFilter.getFilterName());
		
		if(filterType.equalsIgnoreCase("bigdecimal")){
			castString.append("BigDecimal.valueOf(");
			endCastStr=")";
		}
		else if(curFilter.getFilterValue().isSayi()){
			castString.append("(long)");
		}
		
		return castString.toString();
		
	}

	private String createMethodName(String isFindByOrReadBy) {
	
		StringBuilder createdFindByMethodName=new StringBuilder(isFindByOrReadBy);
		
		AbstractToken curToken;
		Filter curFilter;
		AbstractCommand variableDefinition;
		ElementProgramDataTypeNatural variableDefinitionProgramDataTypeNatural = null;
		ElementProgramOneDimensionArrayNatural variableDefinitionOneDimensionArray = null;

		
		
		createdFindByMethodName.append(createRecMaxNumber());
		
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
					if (curFilter.getFilterName().getLinkedToken() != null) {
						createdFindByMethodName.append(Utility.columnNameToPojoFieldNameWithFirstLetterUpper(
								curFilter.getFilterName().getLinkedToken().getDeger().toString()));
					}else if (curFilter.getFilterName().getColumnNameToken() != null) {
						createdFindByMethodName.append(Utility.columnNameToPojoFieldNameWithFirstLetterUpper(
								curFilter.getFilterName().getColumnNameToken().getDeger().toString()));
					}else{
						createdFindByMethodName.append(Utility.columnNameToPojoFieldNameWithFirstLetterUpper(
								curFilter.getFilterName().getDeger().toString()));
					}
				createdFindByMethodName.append(operatorInfoToMethodName(curFilter));

				if (index < conditionListWithFiltersAndParantesiz.size() - 1) {
					createdFindByMethodName.append("_");
				}
			} else {
				if (curToken.getDeger().equals('(')) {
					// createdFindByMethodName.append("OpenPar");
				} else if (curToken.getDeger().equals(ReservedNaturalKeywords.OR)) {
					createdFindByMethodName.append("Or");
					// createdFindByMethod.getMethodName().append("ClosePar");
				}
			}

		}
		createdFindByMethodName=addSortPartToMethodName(createdFindByMethodName);
		return createdFindByMethodName.toString();
		
	}

	private String createRecMaxNumber() {
		
		try {
			if(maxResultCount==null){
				return "";
			}
			
			return maxResultCount.getDeger().toString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
					
	}

	private StringBuilder addSortPartToMethodName(StringBuilder createdFindByMethodName) {
		
		if(sortList==null || sortList.size()==0){
			return createdFindByMethodName;
		}
		
		try {
			AbstractToken curSortToken;
			createdFindByMethodName.append("_SortedBy");
			for(int i=1; i<sortList.size(); i++ ){
				curSortToken=sortList.get(i);
				curSortToken.setPojoVariable(false);
				String sortKey;
				if(curSortToken.getLinkedToken()!=null){
					curSortToken.getLinkedToken().setPojoVariable(false);
					 sortKey=Utility.viewNameToPojoName(JavaWriteUtilities.toCustomString(curSortToken.getLinkedToken()).toString());
					
				}else if(curSortToken.getColumnNameToken()!=null){
					curSortToken.getColumnNameToken().setPojoVariable(false);
					 sortKey=Utility.viewNameToPojoName(JavaWriteUtilities.toCustomString(curSortToken.getColumnNameToken()).toString());
				}else{
					 sortKey=Utility.viewNameToPojoName(JavaWriteUtilities.toCustomString(curSortToken).toString());				
				}
				createdFindByMethodName.append("_"+sortKey);
			}
		} catch (Exception e) {
			logger.debug(e.getMessage(), e);
			return createdFindByMethodName;
		}
		return createdFindByMethodName;
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
		findByMethodImplemantation.getMethodImplementation().append(createSortRestrictionsForHibernate());
		
		findByMethodImplemantation.getMethodImplementation().append(createMaxResult());
	
		findByMethodImplemantation.getMethodImplementation().append("return main_crit.list();");
		
		JavaClassElement.javaHibernateCodeMap.put(findByMethodSignature.toUniqueString(), findByMethodImplemantation);

	}
	
	private String createMaxResult() {
		StringBuilder sb;
		try {
			if(maxResultCount==null){
				return "";
			}
			
			sb=new StringBuilder();
				
			//main_crit.setMaxResults(1);
			sb.append("main_crit.setMaxResults("+JavaWriteUtilities.toCustomString(maxResultCount)+")");
				
			sb.append(JavaConstants.DOT_WITH_COMMA+JavaConstants.NEW_LINE);
		} catch (Exception e) {
			logger.debug(e.getMessage(),e);
			return "";
		}
		return sb.toString();
	}

	//main_crit.addOrder(Order.asc("Girtar"));
	//main_crit.addOrder(Order.asc("Girsaat"))
	//main_crit.addOrder(Order.asc("Desc"))
	
	private String createSortRestrictionsForHibernate() {
		
		StringBuilder sb;
		try {
			if(sortList==null || sortList.size()==0){
				return "";
			}
			
			sb=new StringBuilder();
			String sortKey;
			AbstractToken curSortToken;
			for(int i=1; i< sortList.size(); i++){
				curSortToken = sortList.get(i);
				if(curSortToken.getLinkedToken()!=null){
					curSortToken.getLinkedToken().setPojoVariable(false);
					 sortKey=Utility.viewNameToPojoName(JavaWriteUtilities.toCustomString(curSortToken.getLinkedToken()).toString());
					
				}else if(curSortToken.getColumnNameToken()!=null){
					curSortToken.getColumnNameToken().setPojoVariable(false);
					 sortKey=Utility.viewNameToPojoName(JavaWriteUtilities.toCustomString(curSortToken.getColumnNameToken()).toString());
				}else{
					 sortKey=Utility.viewNameToPojoName(JavaWriteUtilities.toCustomString(curSortToken).toString());				
				}
				
				//Hescinsi --> id.hescinsi
				sortKey=ConvertUtilities.getPojosFieldTypeForHibernate(pojoType,sortKey);
				
				if(curSortToken.isDescending()){
					sb.append("main_crit.addOrder(Order.desc(\""+sortKey+"\"))");
				}else{
					sb.append("main_crit.addOrder(Order.asc(\""+sortKey+"\"))");
				}
				sb.append(JavaConstants.DOT_WITH_COMMA+JavaConstants.NEW_LINE);
			}
		} catch (Exception e) {
			logger.debug(e.getMessage(),e);
			return "";
		}
		return sb.toString();
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
						
						if(filter.filterOperator.getDeger().equals('=') || filter.filterOperator.getDeger().equals("=")){
								result.append("Restrictions.eq(\"");
						}else if(filter.filterOperator.getDeger().equals('>') || filter.filterOperator.getDeger().equals(">")){
								result.append("Restrictions.gt(\"");
						}else if(filter.filterOperator.getDeger().equals('<') || filter.filterOperator.getDeger().equals("<")){
								result.append("Restrictions.lt(\"");
						}else if(filter.filterOperator.getDeger().equals("^=") ){
								result.append("Restrictions.not(Restrictions.eq(\"");
						}else if(filter.filterOperator.getDeger().equals(">=")){
								result.append("Restrictions.ge(\"");
						}else if(filter.filterOperator.getDeger().equals("<=")){
								result.append("Restrictions.le(\"");
						}else{
							result.append("Restrictions.unknown(\"");
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
			if(filter.filterOperator.getDeger().equals('=')|| filter.filterOperator.getDeger().equals("=")){
				findByMethodImplemantation.getMethodImplementation().append("main_crit.add(Restrictions.eq(\"");
			}else if(filter.filterOperator.getDeger().equals('>') || filter.filterOperator.getDeger().equals(">")){
				findByMethodImplemantation.getMethodImplementation().append("main_crit.add(Restrictions.gt(\"");
				
			}else if(filter.filterOperator.getDeger().equals('<') || filter.filterOperator.getDeger().equals("<")){
				findByMethodImplemantation.getMethodImplementation().append("main_crit.add(Restrictions.lt(\"");
				
			}else if(filter.filterOperator.getDeger().equals("^=")){
				
				findByMethodImplemantation.getMethodImplementation().append("main_crit.add(Restrictions.not(Restrictions.eq(\"");
			}else if(filter.filterOperator.getDeger().equals(">=")){
				
				findByMethodImplemantation.getMethodImplementation().append("main_crit.add(Restrictions.ge(\"");
			}else if(filter.filterOperator.getDeger().equals("<=")){
				
				findByMethodImplemantation.getMethodImplementation().append("main_crit.add(Restrictions.le(\"");
			}else{
				findByMethodImplemantation.getMethodImplementation().append("main_crit.add(Restrictions.unknown(\"");
			}

			String criteriaName;
			String filterType, criteriaValue="", columnType;
			if(filter.getFilterName()!=null &&filter.getFilterName().getColumnNameToken()!=null){
				criteriaName=createFilterName(filter.getFilterName().getColumnNameToken().getDeger().toString());
			}else{
				criteriaName=createFilterName(filter.getFilterName().getDeger().toString());
			}
			
			if(filter.getFilterName().isRecordVariable()){
				findByMethodImplemantation.getMethodImplementation().append(criteriaName+"\", "+filter.getFilterName().getLinkedToken().getDeger().toString().replaceAll("-","_")+")");
			}else if(filter.getFilterName().isPojoVariable()){
				findByMethodImplemantation.getMethodImplementation().append(criteriaName+"\", "+filter.getFilterName().getColumnNameToken().getDeger().toString().replaceAll("-","_")+")");
			}else if(filter.getFilterName().getLinkedToken()!=null){
				findByMethodImplemantation.getMethodImplementation().append(criteriaName+"\", "+filter.getFilterName().getLinkedToken().getDeger().toString().replaceAll("-","_")+")");
			}else {
				
				filterType=getFilterType(filter.getFilterName());
				
				//filter.getFilterName().setPojoVariable(true);
				
				columnType=getFilterType(filter.getFilterValue());
				if(filterType.equalsIgnoreCase("string_type") && columnType.equalsIgnoreCase("date_type")){
					criteriaValue=filter.getFilterValue().getDeger().toString().replaceAll("-","_");
					criteriaValue="FrameworkConvertUtilities.stringToSqlDate("+criteriaValue+",\"yyyy-MM-dd\")";
				}else if(filterType.equalsIgnoreCase("string_type") && columnType.equalsIgnoreCase("time_type")){
					criteriaValue=filter.getFilterValue().getDeger().toString().replaceAll("-","_");
					criteriaValue="FrameworkConvertUtilities.stringToSqlTime("+criteriaValue+")";
				}else{
					
					if(filter.getFilterName()!=null && filter.getFilterName().getColumnNameToken()!=null){
						criteriaValue=filter.getFilterName().getColumnNameToken().getDeger().toString();
					}else{
						criteriaValue=filter.getFilterName().getDeger().toString();
					}
				}
				findByMethodImplemantation.getMethodImplementation().append(criteriaName+"\", "+criteriaValue+")");
			}
			
			findByMethodImplemantation.getMethodImplementation().append(")");
			if(filter.filterOperator.getDeger().equals("^=")){
				findByMethodImplemantation.getMethodImplementation().append(")");
			}
			
		}
		

		private String getFilterType(AbstractToken type) {
			VariableTypes varType=ConvertUtilities.getVariableType(type);
			return varType.toString();
		}

		private String createFilterName(String filterOrjName) {
			String filterName=ConvertUtilities.getPojosFieldTypeForHibernate(pojoType,Utility.columnNameToPojoFieldName(filterOrjName));
			return filterName;
		}

		private void createFilterWithOrConditions() {
			AbstractToken curToken;
			Filter filter;
			String firstChar;
			boolean paramIsNumber=false;
			StringBuffer result=new StringBuffer();
			result.append("Restrictions.or(");
			for(int index=0; index<conditionListWithFiltersAndParantesiz.size();index++){
				if(conditionListWithFiltersAndParantesiz.get(index) instanceof AbstractToken){
					
					curToken=(AbstractToken) conditionListWithFiltersAndParantesiz.get(index);
					
					result.append(",");
				
				}else if(conditionListWithFiltersAndParantesiz.get(index) instanceof Filter){
			
						filter=(Filter) conditionListWithFiltersAndParantesiz.get(index);
						
						if(filter.filterOperator.getDeger().equals('=') || filter.filterOperator.getDeger().equals("=")){
								result.append("Restrictions.eq(\"");
						}else if(filter.filterOperator.getDeger().equals('>') || filter.filterOperator.getDeger().equals(">")){
								result.append("Restrictions.gt(\"");
						}else if(filter.filterOperator.getDeger().equals('<') || filter.filterOperator.getDeger().equals("<")){
								result.append("Restrictions.lt(\"");
						}else if(filter.filterOperator.getDeger().equals("^=")){
								result.append("Restrictions.not(Restrictions.eq(\"");
						}else if(filter.filterOperator.getDeger().equals(">=")){
								result.append("Restrictions.ge(\"");
						}else if(filter.filterOperator.getDeger().equals("<=")){
								result.append("Restrictions.le(\"");
						}else{
							result.append("Restrictions.unknown(\"");
						}
						
						firstChar=filter.getFilterValue().getDeger().toString().substring(0,1);
						
						try {
							Integer.parseInt(firstChar);
							paramIsNumber=true;
						} catch (NumberFormatException e) {
							paramIsNumber=false;
						}
						
						if(filter.getFilterName().isRecordVariable()){
							result.append(Utility.columnNameToPojoFieldName(filter.getFilterName().getDeger().toString())+"\", "+filter.getFilterName().getLinkedToken().getDeger().toString());
						}else if(filter.getFilterName().isPojoVariable()){
							result.append(Utility.columnNameToPojoFieldName(filter.getFilterName().getDeger().toString())+"\", "+filter.getFilterName().getColumnNameToken().getDeger().toString());
						}else{
							result.append(Utility.columnNameToPojoFieldName(filter.getFilterName().getDeger().toString())+"\", "+filter.getFilterName().getDeger().toString());
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
		protected void parseSortList() { 
			 List<AbstractToken> newConditionList=new ArrayList<AbstractToken>();
			 List<AbstractToken> newSortList=new ArrayList<AbstractToken>();
			 boolean sortReached=false;
			 boolean descanding=false;
			 int sortByIndex=0;
			 if(conditionList==null ||conditionList.size()==0){
				 return;
			 }
			 if(conditionList.size()==1){ //*S**READ TKS-AIRLINE BY AIR-CODE-NUM
				 newSortList.add(new OzelKelimeToken(ReservedNaturalKeywords.SORTED_BY,0, 0, 0));
				 newSortList.add(conditionList.get(0));
				 this.sortList=newSortList;
				 
				 newConditionList=new ArrayList<AbstractToken>();
				 this.conditionList=newConditionList;
				 return;
		
			 }
			for(int index=0; index<conditionList.size();index++){
				if(conditionList.get(index).isKelime(ReservedNaturalKeywords.SORTED_BY)){
					sortByIndex=index;
					break;
				}else{
					newConditionList.add(conditionList.get(index));
				}
		
			}
			
			AbstractToken curToken=null, nextToken=null;
			
			if(sortByIndex!=0){
				for(int index=sortByIndex; index<conditionList.size();index++){
					
					curToken=conditionList.get(index);
					if(index<conditionList.size()-1){
						nextToken=conditionList.get(index+1);
					}
				
					if(nextToken.isKelime("DESC")){
							curToken.setDescending(true);
							conditionList.remove(index+1);
					}else if(nextToken.isKelime("ASC")){
							curToken.setDescending(false);
							conditionList.remove(index+1);
					}
					
					newSortList.add(curToken);
					
				
				}
				this.sortList=newSortList;
			}
			this.conditionList=newConditionList;
			
			
		}

		

}
