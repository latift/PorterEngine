package tr.com.vbt.java.database;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tr.com.vbt.java.AbstractJavaElement;
import tr.com.vbt.token.AbstractToken;

public abstract class  AbsctractConditionalJavaElement extends AbstractJavaElement {
	
	final static Logger logger = LoggerFactory.getLogger(AbsctractConditionalJavaElement.class);
	
	protected  List conditionListWithFiltersAndParantesiz;
	
	protected List<AbstractToken> conditionList;
	


	

	protected void convertConditionsToFilters() {
		conditionListWithFiltersAndParantesiz=new ArrayList();
		Filter fl;
		AbstractToken filterName = null,filterOperator,filterValue, curToken;
		for(int index=0; index<conditionList.size();index++){
			curToken=conditionList.get(index);
			logger.debug("curToken:"+curToken.getDeger().toString());
			if(curToken.isFiltreParantez()||curToken.isFiltrejoiner()){
				conditionListWithFiltersAndParantesiz.add(curToken);
				System.out.println("Token Added:"+curToken);
			}else if(curToken.isFilterName()){
				filterName=curToken;
				index++;
				filterOperator=conditionList.get(index);
				index++;
				filterValue=conditionList.get(index);
				
				fl=new Filter(filterName,filterOperator,filterValue);
				conditionListWithFiltersAndParantesiz.add(fl);
				System.out.println("Filter Added:"+fl.toString());
			}
		}
	}
	
	//^= --> ^= Tek token
	//>= --> >= Tek token
	//<= --> <= Tek token
	
	protected List<AbstractToken> convertConditions() {
		
		List<AbstractToken> resultList=new ArrayList<AbstractToken>();
		
		AbstractToken currentToken = null, nextToken=null, previousToken=null;
		
		System.out.println(conditionList);
		
		for (int index=0; index<conditionList.size();index++ ) {
		
				currentToken=conditionList.get(index);
				
				if(index>0){
					previousToken=conditionList.get(index-1);
				}
				//
				if(currentToken.getDeger().equals('^')){
					nextToken=conditionList.get(index+1);
					if(nextToken.getDeger().equals('=')){
						currentToken.setDeger("^=");
						conditionList.remove(index+1); 
					}
				}else if(currentToken.getDeger().equals('>')){
					nextToken=conditionList.get(index+1);
					if(nextToken.getDeger().equals('=')){
						currentToken.setDeger(">=");
						conditionList.remove(index+1); 
					}
				}else if(currentToken.getDeger().equals('<')){
					nextToken=conditionList.get(index+1);
					if(nextToken.getDeger().equals('=')){
						currentToken.setDeger("<=");
						conditionList.remove(index+1); 
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
			return "Filter [filterName=" + filterName.getDeger() + ", filterOperator=" + filterOperator.getDeger() + ", filterValue="
					+ filterValue.getDeger() + "]";
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


}
