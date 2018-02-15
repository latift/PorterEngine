package tr.com.vbt.java.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;


import tr.com.vbt.java.conditions.Condition;
import tr.com.vbt.java.conditions.ConditionJoiner;
import tr.com.vbt.java.conditions.OneItemSimpleCondition;
import tr.com.vbt.java.conditions.SimpleCondition;
import tr.com.vbt.java.general.JavaClassElement;
import tr.com.vbt.lexer.ConversionLogModel;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.KelimeToken;
import tr.com.vbt.token.TokenTipi;

public class ConditionUtilities {

	final static Logger logger = Logger.getLogger(ConditionUtilities.class);
	
	private Condition rootCondition;
	
	public void processConditions(List<AbstractToken> conditionList) {
		
		conditionList=processOrCondition(conditionList);  // OR= arasına değisken ekler.
		
		conditionList=convertConditionOperatorsToJavaStyleOperators(conditionList); //And OR EQ GT gibleri  && != == >= gibi çevirir.
	
		convertTokensToCondition(conditionList);
		
		return;
	}
	
	

	public void writeConditions() throws Exception {
		rootCondition.writeCondition();
		
	}
	

	//*S**  IF ERR-FOUND AND NOT(CMD(1:2) NE ' ')

	//*S**  IF TPS-DOF NE MASK(YYMMDD) OR TPS-DOF < TPS-DOS

	//*S**  IF USER-WEB = C_WEB AND NOT(*USER = 'HU01' OR = 'KETPAH')
	private void convertTokensToCondition(List<AbstractToken> conditionList) {
		
		rootCondition=new Condition(null,false);
		
		Condition curCondition = null;
		
		Condition childCon;
		
		AbstractToken curToken = null,nextToken=null,nexterToken=null;
		
		boolean notCondition = false;
		
		List<AbstractToken> errorTokenList;
		
		for(int i=0; i<conditionList.size();i++){
			
			curToken=conditionList.get(i);
			
			if(i<conditionList.size()-1){
				nextToken=conditionList.get(i+1);
			}else{
				nextToken=null;
			}
			
			if(i<conditionList.size()-2){
				nexterToken=conditionList.get(i+2);
			}else{
				nexterToken=null;
			}
		
			if(curCondition==null){
				curCondition=rootCondition;
			}
			logger.debug("CurToken:"+curToken.toString());
			if(curToken.isNotOperator()){
				notCondition=true;
			}else if(curToken.controlOpenParantez()){
				childCon=new Condition(curCondition,false);
				childCon.setNotCondition(notCondition);
				notCondition=false;
				curCondition.addChildCondition(childCon);
				curCondition=childCon;
			}else if(curToken.controlCloseParantez()){
				
				if(nextToken!=null && controlConditionJoiner(nextToken)){
					i++;
						curCondition.setConditionJoiner(new ConditionJoiner(nextToken));
				}
			
				curCondition=curCondition.getParentCondition();
				
			}else if(controlOneItemCondition(curToken,nextToken)){
				
				OneItemSimpleCondition oneItemCon=new OneItemSimpleCondition(curToken);
				oneItemCon.setNotCondition(notCondition);
				notCondition=false;
				if(i+1<conditionList.size()){
					nextToken=conditionList.get(i+1);
					if(nextToken!=null && controlConditionJoiner(nextToken)){
						i++;
						oneItemCon.setConditionJoiner(new ConditionJoiner(nextToken));
					}
				}
				curCondition.addChildCondition(oneItemCon);
			}else if(controlSimpleCondition(curToken,nextToken, nexterToken)){
				
				SimpleCondition sCondition=new SimpleCondition(curToken,nextToken, nexterToken);
				sCondition.setNotCondition(notCondition);
				notCondition=false;
				i=i+2;
				if(i+1<conditionList.size()){
					nextToken=conditionList.get(i+1);
					if(nextToken!=null && controlConditionJoiner(nextToken)){
						i++;
						sCondition.setConditionJoiner(new ConditionJoiner(nextToken));
					}
				}
				curCondition.addChildCondition(sCondition);
			}else if(controlThruCondition(curToken,nextToken, nexterToken)){
				
				SimpleCondition sCondition=new SimpleCondition(curToken,nextToken, nexterToken);
				sCondition.setNotCondition(notCondition);
				notCondition=false;
				i=i+2;
				if(i+1<conditionList.size()){
					nextToken=conditionList.get(i+1);
					if(nextToken!=null && controlConditionJoiner(nextToken)){
						i++;
						sCondition.setConditionJoiner(new ConditionJoiner(nextToken));
					}
				}
				curCondition.addChildCondition(sCondition);
			}else {
				errorTokenList=new ArrayList<>();
				errorTokenList.add(curToken);
				ConversionLogModel.getInstance().writeError(6, errorTokenList,"if Condition İçinde Matematik Operasyonuna rastlandı.");
			}
		}
		
		
		return;
	}




	private boolean controlThruCondition(AbstractToken curToken, AbstractToken nextToken, AbstractToken nexterToken) {
		if(nextToken ==null|| nexterToken==null){
			return false;
		}
		if(nextToken.isConditionOperator()&&nexterToken.isOzelKelime("THRU") ){
			return true;
		}
		return false;
	}



	private boolean controlOneItemCondition(AbstractToken curToken, AbstractToken nextToken) {
		if((curToken.isKelime() ||curToken.isArray()) && (nextToken==null|| nextToken.isConditionJoiner())){
			return true;
		}
		return false;
	}





	private boolean controlConditionJoiner(AbstractToken curToken) {
		if(curToken.isConditionJoiner()){
			return true;
		}
		return false;
	}



	private boolean controlSimpleCondition(AbstractToken curToken, AbstractToken nextToken,AbstractToken nexterToken) {
		if(nextToken ==null|| nexterToken==null){
			return false;
		}
		if((curToken.isKelime() ||curToken.isArray()||curToken.isSayi()) && nextToken.isConditionOperator()&&(nexterToken.isKelime() ||nexterToken.isArray()|| nexterToken.isMasked()||nexterToken.isSayi()) ){
			return true;
		}
		return false;
	}








	/**
	 *IF USER-WEB = C_WEB AND NOT(*USER = 'HU01' OR = 'KETPAH')  
	 *  
	 *  OR ve conditionOperator varsa öncesine parantezden sonraki ifadeyi koy(*USER)
	 * 
	 * @return
	 */
	private List<AbstractToken> processOrCondition(List<AbstractToken> conditionList) {
		
		AbstractToken tokenForAdd = null, curToken, nextToken, firstConditionItem = null;
		
		List<AbstractToken> resultList=new ArrayList<>();
		
		try {
			firstConditionItem=conditionList.get(0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
		}
		
		if(conditionList==null){
			 
		}else{
			
	
		for(int i=0;i<conditionList.size();i++){
			
			curToken=conditionList.get(i);
			
			logger.debug("CurToken:"+curToken.toString());
			
			resultList.add(curToken);
			
			if(i<conditionList.size()-1){
				nextToken=conditionList.get(i+1);
			}else{
				break; 
			}
			
			if(curToken.controlOpenParantez()){
				tokenForAdd=conditionList.get(i+1);
			}else if(curToken.isOzelKelime(ReservedNaturalKeywords.OR) && nextToken.isConditionOperator() ){
				if(tokenForAdd==null){
					resultList.add(firstConditionItem);
				}else{
					resultList.add(tokenForAdd);
				}
			}
			
		}
		}
		return resultList;
	}

	public List<AbstractToken> parseThruKeyword(List<AbstractToken> conditionList) {
		
		AbstractToken curToken=null, nextToken=null, thruToken=null;
		
		 List<AbstractToken> newConditionList=new ArrayList<AbstractToken>();
		 
		 AbstractToken newThruToken;
		
		for(int index=0; index<conditionList.size();index++){
				
				curToken=conditionList.get(index);
				if(index+2<conditionList.size()){
					thruToken=conditionList.get(index+1);
					nextToken=conditionList.get(index+2);
				}else{
					thruToken=null;
				}
				if(thruToken!=null && thruToken.isOzelKelime("THRU")){
					newThruToken=thruToken;
					newThruToken.setThruFirstToken(curToken);
					newThruToken.setThruSecondToken(nextToken);
					newConditionList.add(newThruToken);
					index=index+2;
						
				}else{
					newConditionList.add(curToken);
				}
				
		}
		return newConditionList;
		
		
	}



	//And OR EQ GT gibleri  && != == >= gibi çevirir.
	private List<AbstractToken> convertConditionOperatorsToJavaStyleOperators(List<AbstractToken> conditionList) {
		
		List<AbstractToken> resultList=new ArrayList<AbstractToken>();
		
		AbstractToken currentToken = null, nextToken=null, previousToken=null;
		
		System.out.println(conditionList);
		
		for (int index=0; index<conditionList.size();index++ ) {
		
				currentToken=conditionList.get(index);
				
				if(index>0){
					previousToken=conditionList.get(index-1);
				}
				
				if(currentToken==null){
					logger.debug("currentToken");
				}
				
				if(currentToken.getDeger()==null){
					continue;
				}else if(currentToken.getDeger().equals("AND")){
					currentToken.setDeger("&&");
					currentToken.setConditionJoiner(true);
					currentToken.setTip(TokenTipi.Karakter);
				}else if(currentToken.getDeger().equals("OR")){
					currentToken.setDeger("||");
					currentToken.setTip(TokenTipi.Karakter);
					currentToken.setConditionJoiner(true);
				}else if(currentToken.getDeger().equals('=')&& (!previousToken.getDeger().equals('>') && !previousToken.getDeger().equals('<'))){ //>= yada <= için işlem yapma
					currentToken.setDeger("==");
					currentToken.setTip(TokenTipi.Karakter);
				}else if(currentToken.getDeger().equals("EQ")){
					currentToken.setDeger("==");
					currentToken.setTip(TokenTipi.Karakter);
				}else if(currentToken.getDeger().equals("NE")){
					currentToken.setDeger("!=");
					currentToken.setTip(TokenTipi.Karakter);
				}else if(currentToken.getDeger().equals("GT")){
					currentToken.setDeger(">");
					currentToken.setTip(TokenTipi.Karakter);
				}else if(currentToken.getDeger().equals("GE")){
					currentToken.setDeger(">=");
					currentToken.setTip(TokenTipi.Karakter);
				}else if(currentToken.getDeger().equals("LT")){
					currentToken.setDeger("<");
					currentToken.setTip(TokenTipi.Karakter);
				}else if(currentToken.getDeger().equals("LE")){
					currentToken.setDeger("<=");
					currentToken.setTip(TokenTipi.Karakter);
				}else if(currentToken.getDeger().equals("NOT")){
					currentToken.setDeger("!");
					currentToken.setTip(TokenTipi.Karakter);
				}else if(currentToken.getDeger().equals('^')){
					nextToken=conditionList.get(index+1);
					if(nextToken.getDeger().equals('=')){
						currentToken.setDeger("!=");
						currentToken.setTip(TokenTipi.Karakter);
						conditionList.remove(index+1); 
					}
				}else if(currentToken.getDeger().equals('!')){
					nextToken=conditionList.get(index+1);
					if(nextToken.getDeger().equals('=')){
						currentToken.setDeger("!=");
						currentToken.setTip(TokenTipi.Karakter);
						conditionList.remove(index+1); 
					}
					
				}else if(currentToken.getDeger().equals('>')){ // >= Tek tokena cevir
					nextToken=conditionList.get(index+1);
					if( nextToken.getDeger().equals('=')){
						currentToken.setDeger(">=");
						currentToken.setTip(TokenTipi.Karakter);
						conditionList.remove(index+1);
					}
				}else if(currentToken.getDeger().equals('<')){ // <= Tek tokena cevir
					nextToken=conditionList.get(index+1);
					if( nextToken.getDeger().equals('=')){
						currentToken.setDeger("<=");
						currentToken.setTip(TokenTipi.Karakter);
						conditionList.remove(index+1);
					}
				}
		 		resultList.add(currentToken);
		}
		return resultList;
	}



}
