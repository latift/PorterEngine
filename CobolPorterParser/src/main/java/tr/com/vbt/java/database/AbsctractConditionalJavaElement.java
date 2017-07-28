package tr.com.vbt.java.database;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.java.AbstractJavaElement;
import tr.com.vbt.java.general.JavaConstants;
import tr.com.vbt.java.util.Utility;
import tr.com.vbt.java.utils.ConvertUtilities;
import tr.com.vbt.java.utils.JavaWriteUtilities;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.natural.parser.datalayout.program.ElementProgramDataTypeNatural;
import tr.com.vbt.natural.parser.datalayout.program.ElementProgramOneDimensionArrayNatural;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.TokenTipi;

public abstract class AbsctractConditionalJavaElement extends AbstractJavaElement {

	final static Logger logger = LoggerFactory.getLogger(AbsctractConditionalJavaElement.class);

	protected List conditionListWithFiltersAndParantesiz;

	protected List<AbstractToken> conditionList;

	protected IteratorNameManager itNameManager = new IteratorNameManager();

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

	protected String createFindByMethodString(String isFindByOrReadBy) {
		StringBuffer findByString = new StringBuffer(isFindByOrReadBy);
		AbstractToken curToken;
		Filter curFilter;
		AbstractCommand variableDefinition;
		ElementProgramDataTypeNatural variableDefinitionProgramDataTypeNatural = null;
		ElementProgramOneDimensionArrayNatural variableDefinitionOneDimensionArray = null;

		String methodParameterType, methodParameterName;
		
		if(conditionListWithFiltersAndParantesiz==null || conditionListWithFiltersAndParantesiz.size()==0){
			if(isFindByOrReadBy.equals("readBy")){
				return "readAll()";
			}else{
				return "findAll()";
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
						findByString.append(Utility.columnNameToPojoFieldNameWithFirstLetterUpper(
								curFilter.getFilterName().getDeger().toString()));
					} else {
						findByString.append(Utility.columnNameToPojoFieldNameWithFirstLetterUpper(
								curFilter.getFilterName().getColumnNameToken().getDeger().toString()));
					}
				} else if (curFilter.getFilterName().isRecordVariable()) {
					if (curFilter.getFilterName().getColumnNameToken() == null) {
						findByString.append(Utility.recordNameToRecordDotRecordFieldName(curFilter.getFilterName()));
					}else{
						findByString.append(Utility.recordNameToRecordDotRecordFieldName(curFilter.getFilterName().getColumnNameToken()));
					}
				} else {
					if (curFilter.getFilterName().getColumnNameToken() == null) {
						findByString.append(Utility.columnNameToPojoFieldNameWithFirstLetterUpper(
								curFilter.getFilterName().getDeger().toString()));
					}else{
						findByString.append(Utility.columnNameToPojoFieldNameWithFirstLetterUpper(
								curFilter.getFilterName().getColumnNameToken().getDeger().toString()));
					}
				}
				findByString.append(operatorInfoToMethodName(curFilter));

				if (index < conditionListWithFiltersAndParantesiz.size() - 1) {
					findByString.append("_");
				}
			} else {
				if (curToken.getDeger().equals('(')) {
					// findByString.append("OpenPar");
				} else if (curToken.getDeger().equals(ReservedNaturalKeywords.OR)) {
					findByString.append("Or");
					// findByString.append("ClosePar");
				}
			}

		}
		findByString.append(JavaConstants.OPEN_NORMAL_BRACKET);
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
				methodParameterType = ConvertUtilities.getVariableTypeOfString(curFilter.getFilterValue()).toString();
				methodParameterName = curFilter.getFilterValue().getLinkedToken().getDeger().toString().replaceAll("-",
						"_");
			} else if (curFilter.getFilterValue().isPojoVariable()) {
				methodParameterType = ConvertUtilities
						.getVariableTypeOfString(curFilter.getFilterValue().getColumnNameToken()).toString();
				methodParameterName = curFilter.getFilterValue().getColumnNameToken().getDeger().toString()
						.replaceAll("-", "_");
			} else {
				methodParameterType = ConvertUtilities.getVariableTypeOfString(curFilter.getFilterValue()).toString();
				methodParameterName = curFilter.getFilterValue().getDeger().toString().replaceAll("-", "_");
			}
			findByString.append(
					methodParameterType + " " + ConvertUtilities.onlyFieldOfIncludedVariable(methodParameterName));

			if (index != conditionListWithFiltersAndParantesiz.size() - 1) {
				findByString.append(",");
			}
		}

		findByString.append(JavaConstants.CLOSE_NORMAL_BRACKET);

		return findByString.toString();
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
	 * (MUSNO1=SECMUSNO1 OR MUSNO2=SECMUSNO1)                                                             
 		3644     AND DOVIZ=WDOVIZ AND                                                                                                       
 		3646     KALAN_MEBLAG> 0 AND AKOD1^='S' AND AKOD2^='S'
 
	-->findByMusno2AndReferansSmallerAndOpenParBsicilOrAsicilCloseParAndIslemTar(GecMusno2, Map.refno, 0,0,Guntar);
	
	*/
	/*
	 *    FilterNameve JoinOperatorleriOnce Ekle 
	 *    Sonra Parantez Açıp FiltreValue ları ekle.
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
		for(int index=0; index<conditionListWithFiltersAndParantesiz.size();index++){
			curFilter=null;
			if (conditionListWithFiltersAndParantesiz.get(index) instanceof Filter){ 
				curFilter=(Filter) conditionListWithFiltersAndParantesiz.get(index);
			}else{
				continue;
			}
			
			if(curFilter.getFilterValue().isPojoVariable()){
				//findByString.append(Utility.pojoNameToPojoDotColumnName(curFilter.getFilterValue()));
				findByString.append(JavaWriteUtilities.toCustomString(curFilter.getFilterName()));
			}else if(curFilter.getFilterValue().isRecordVariable()) {
				findByString.append(Utility.recordNameToRecordDotRecordFieldName(curFilter.getFilterValue()));
			}else if(curFilter.getFilterValue().isConstantVariableWithQuota()){
				findByString.append("\""+curFilter.getFilterValue().getDeger()+"\"");
			}else {
				findByString.append(ConvertUtilities.onlyFieldOfIncludedVariable(curFilter.getFilterValue().getDeger().toString()));
			}
			if(index!=conditionListWithFiltersAndParantesiz.size()-1){
				findByString.append(",");
			}
		}
		
		findByString.append(JavaConstants.CLOSE_NORMAL_BRACKET);
	
		return findByString.toString();
	}


}
