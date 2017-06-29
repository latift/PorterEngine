package tr.com.vbt.lexer;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.CobolOzelKelimeler;
import tr.com.vbt.token.IkiliOzelKelimelerCobol;
import tr.com.vbt.token.KarakterToken;
import tr.com.vbt.token.KelimeToken;
import tr.com.vbt.token.KeyValueOzelKelimeToken;
import tr.com.vbt.token.KeyValueOzelKelimeler;
import tr.com.vbt.token.NoktaToken;
import tr.com.vbt.token.OzelKelimeToken;
import tr.com.vbt.token.SatirBasiToken;
import tr.com.vbt.token.SayiToken;
import tr.com.vbt.token.TokenTipi;
import tr.com.vbt.token.UcluOzelKelimeler;
import tr.com.vbt.util.ConverterConfiguration;
import tr.com.vbt.util.IfEndIfCommandsUtility;
import tr.com.vbt.util.WriteToFile;

public class CobolLexing extends AbstractLexing{
	
	final static Logger logger = LoggerFactory.getLogger(CobolLexing.class);

	protected CobolOzelKelimeler ozelKelimeler;
	
	
	
	
	public CobolLexing() {
		super();
		ozelKelimeler=new CobolOzelKelimeler();
	}



	/**
	 * 
	 * OPEN INPUT   VACFILE  VCCFILE VLMFILE VCVFILE                02360000
023700          OUTPUT  PCFILE.                                         02370000
		-->
		OPEN INPUT   VACFILE  VCCFILE VLMFILE VCVFILE                02360000
	023700    OPEN OUTPUT  PCFILE.                                         02370000
	 */
	private void addOpenForOutput() {
		
		AbstractToken astCurrent,astNext;
		
		boolean ifFound=false;
		for (int i=0; i<tokenListesi.size()-2; i++) {
			
			astCurrent=tokenListesi.get(i);
			astNext=tokenListesi.get(i+1);
			
			logger.info("addOperForOutput: tokenListesi.get("+i+") : "+ astCurrent.toString()+" tokenListesi.size()="+tokenListesi.size());
			
			if(astNext.getTip().equals(TokenTipi.OzelKelime)  //Soraki Kelime OUTPUT ise döngüye devam et.
					&&astNext.getDeger()!=null 
					&&(astNext.getDeger().equals(ReservedCobolKeywords.OUTPUT))){  
				if(	astCurrent.getTip().equals(TokenTipi.OzelKelime) &&  astCurrent.getDeger()!=null  && astCurrent.getDeger().equals(ReservedCobolKeywords.OPEN)	){
					// do nothing
				}else{
					tokenListesi.add(i+1, new OzelKelimeToken<String>("OPEN", 0,0,0));
					i++;
				}
				
			}
			
		}
		
	}



	private void addIfVisualTokens() {
		  
		  addThenForModeStatement(ReservedCobolKeywords.IF);
		    
		  addEndIf();
		    
		  addEndIfBeforeElse();
		
	}


	/**
	 * If den sonra THENN yazılmayabiliyor.
	 * Bu durumlarda visual ekliyoruz. Düzeltme Eklemesidir.
	 * 
	 */
	private void addThenForModeStatement(String mode) {
		
		AbstractToken astCurrent;
		
		boolean ifFound=false;
		for (int i=0; i<tokenListesi.size()-1; i++) {
			
			astCurrent=tokenListesi.get(i);
			
			logger.info(astCurrent.toString());
			
			if(astCurrent.getTip().equals(TokenTipi.OzelKelime)  //Soraki Kelime AND yada OR ise döngüye devam et.
					&&astCurrent.getDeger()!=null 
					&&(astCurrent.getDeger().equals(ReservedCobolKeywords.AND)
					  ||astCurrent.getDeger().equals(ReservedCobolKeywords.OR)
					  ||astCurrent.getDeger().equals(ReservedCobolKeywords.NOT)
					  ||astCurrent.getDeger().equals(ReservedCobolKeywords.IS)	
					  ||astCurrent.getDeger().equals(ReservedCobolKeywords.NUMERIC)
					  ||astCurrent.getDeger().equals(ReservedCobolKeywords.IS_NUMERIC)	
					  ||astCurrent.getDeger().equals(ReservedCobolKeywords.EQUAL))){  //VLM-DEGER ( ACOCC-XX2 , I ) 
				continue;
			}else if(astCurrent.getTip().equals(TokenTipi.Karakter)){ //Karakterse Devam Et
				continue;
			}
			else if(astCurrent.getTip().equals(TokenTipi.OzelKelime)&&astCurrent.getDeger()!=null&&astCurrent.getDeger().equals(mode)){ // IF varsa
				ifFound=true;
			}
			else if(ifFound&&astCurrent.getTip().equals(TokenTipi.OzelKelime) &&astCurrent.getDeger().equals(ReservedCobolKeywords.THEN)){ //If den sonraki OzelKelime THEN se bir şey yapma
				ifFound=false;
			}
			else if(ifFound&&astCurrent.getTip().equals(TokenTipi.OzelKelime) &&!astCurrent.getDeger().equals(ReservedCobolKeywords.THEN)){ //IF den sonraki kelime THEN değilse ekle.
				ifFound=false;
				tokenListesi.add(i-1, new OzelKelimeToken<String>("THEN", 0,0,0));
			}
			
		}
		
	}
	
	




	/**
	 * END-IF yazmadan yazılanlar icin düzeltme kodudur.
	 * 
	 * 	1) If görürsen Buffer a Enf-İf koy.
		2) End-İf görürsen bufferdan bir tane sil. (End-if i programcı koydu ise)
		3) Nokta görürsen(Complex Inner If serisinin sonuna gelinmiştir.) buffer daki tüm end-ifleri ekle
		4)  Else den sonra Else görürsen Else den önce End-İf ekle.
	 */
	private void addEndIf() {
		AbstractToken astCurrent;
		
		AbstractToken endIfInBuffer;
		
		IfEndIfCommandsUtility endIfutil=new IfEndIfCommandsUtility();
		
		boolean previousIsElse=false; //Ard arda else gelirseyi kontrol etmek için.
		
		for (int i=0; i<tokenListesi.size()-1; i++) {
			
			astCurrent=tokenListesi.get(i);
			
			logger.info(astCurrent.toString());
			
			if(astCurrent.getTip().equals(TokenTipi.OzelKelime)  //If görürsen Buffer a Enf-İf koy.
					&&astCurrent.getDeger()!=null 
					&&astCurrent.getDeger().equals(ReservedCobolKeywords.IF)){
				endIfutil.putEndIfTokenToBuffer();
				previousIsElse=false;
			}else if(astCurrent.getTip().equals(TokenTipi.OzelKelime)  //End-İf görürsen bufferdan bir tane sil. (End-if i programcı koydu ise)
					&&astCurrent.getDeger()!=null 
					&&astCurrent.getDeger().equals("END-IF")){
				endIfutil.getEndIfFromBuffer();
				previousIsElse=false;
			}else if(astCurrent.getTip().equals(TokenTipi.Nokta)  // Nokta görürsen(Complex Inner If serisinin sonuna gelinmiştir.) buffer daki tüm end-ifleri ekle
					&&astCurrent.getDeger()!=null 
					&&astCurrent.getDeger().equals('.')){
				while(endIfutil.bufferSize()>0){
					endIfInBuffer=endIfutil.getEndIfFromBuffer();
					tokenListesi.add(i, endIfInBuffer); 
					i++;
				}
				previousIsElse=false;
			}else if(previousIsElse	
					&&astCurrent.getTip().equals(TokenTipi.OzelKelime)  //Else den sonra Else görürsen Else den önce End-İf ekle. (İki Else arasında )
					&&astCurrent.getDeger()!=null 
					&&astCurrent.getDeger().equals(ReservedCobolKeywords.ELSE)){
					
					endIfInBuffer=endIfutil.getEndIfFromBuffer();
					tokenListesi.add(i, endIfInBuffer); 
					i++;
				previousIsElse=true;
			}
		}
		
	}



	
	
	/**
	 * Sorun: If-EndIf de ifin enderi EndIf,
		   	  If-Else-EndIf  de Else ender ı EndIf ama If in Enderi yok.
		Çözümü: addEndIfBeforeElse:
					Else gördü isen öncesinde EndIfBeforeElse ekle.
	 */
		private void addEndIfBeforeElse() {
			
			AbstractToken astCurrent;
				
			for (int i=0; i<tokenListesi.size()-1; i++) {
				
				astCurrent=tokenListesi.get(i);
				
				if(astCurrent.getTip().equals(TokenTipi.OzelKelime)  //Else gördü isen öncesinde EndIfBeforeElse ekle.
						&&astCurrent.getDeger()!=null 
						&&astCurrent.getDeger().equals(ReservedCobolKeywords.ELSE)){
					tokenListesi.add(i, new OzelKelimeToken<String>("END_IF_BEFORE_ELSE", 0,0,0)); 
					i++;
				}
			}		
			
		}



	private void addEnderForDisplay() {
		AbstractToken astDisplay;
		AbstractToken displayParam;
		AbstractToken displayEnder;
		List<Integer> addDisplayEnderList=new ArrayList<Integer>(); 
		boolean endOfTokenListReached = false;
		for (int i=0; i<tokenListesi.size()-1; i++) {
			
				astDisplay=tokenListesi.get(i);
				
				
				if(astDisplay.getTip().equals(TokenTipi.OzelKelime)&&astDisplay.getDeger()!=null&&astDisplay.getDeger().equals(ReservedCobolKeywords.DISPLAY)){ // DIPSLAY varsa
				
					if(i==tokenListesi.size()-1){
						endOfTokenListReached=true;
						break;
					}
					i++;
					displayParam=tokenListesi.get(i);
					
					while(displayParam.getTip().equals(TokenTipi.Kelime)||displayParam.getTip().equals(TokenTipi.SatirBasi)||displayParam.getTip().equals(TokenTipi.Nokta)){
						
						if(i==tokenListesi.size()-1){
							endOfTokenListReached=true;
							break;
						}
						i++;
						displayParam=tokenListesi.get(i);
					}
					addDisplayEnderList.add(i-1);
					if(endOfTokenListReached){
						break;
					}
					i--; // Display dan hemen sonra bir daha display gelirse yakalamak icin.
				}
		}
		
		while(!addDisplayEnderList.isEmpty()){
			displayEnder=new OzelKelimeToken<String>(ReservedCobolKeywords.END_DISPLAY, 0,0,0);
			tokenListesi.add(addDisplayEnderList.get(addDisplayEnderList.size()-1), displayEnder);
			addDisplayEnderList.remove (addDisplayEnderList.size()-1); //RemoveLast
			
		}
	}
	
	private void addEnderForCompute() {
		AbstractToken astCompute;
		AbstractToken computeParam;
		AbstractToken computeEnder;
		List<Integer> addComputeEnderList=new ArrayList<Integer>(); 
		boolean endOfTokenListReached = false;
		for (int i=0; i<tokenListesi.size()-1; i++) {
			
			astCompute=tokenListesi.get(i);
				
				
				if(astCompute.getTip().equals(TokenTipi.OzelKelime)&&astCompute.getDeger()!=null&&astCompute.getDeger().equals(ReservedCobolKeywords.COMPUTE)){ // DIPSLAY varsa
				
					if(i==tokenListesi.size()-1){
						endOfTokenListReached=true;
						break;
					}
					
					do{
						i++;
						computeParam=tokenListesi.get(i);
						
						if(i==tokenListesi.size()-1){
							endOfTokenListReached=true;
							break;
						}
						
					}while(!computeParam.getTip().equals(TokenTipi.OzelKelime));
					
					addComputeEnderList.add(i-1);
					if(endOfTokenListReached){
						break;
					}
					
					i--; // Compute dan hemen sonra bir daha compute gelirse yakalamak icin.
				}
				
		}
		
		while(!addComputeEnderList.isEmpty()){
			computeEnder=new OzelKelimeToken<String>(ReservedCobolKeywords.END_COMPUTE, 0,0,0);
			tokenListesi.add(addComputeEnderList.get(addComputeEnderList.size()-1), computeEnder);
			addComputeEnderList.remove (addComputeEnderList.size()-1); //RemoveLast
		}
	}
	
	private void addEnderForAssign() {
		AbstractToken astCompute;
		AbstractToken computeParam;
		AbstractToken computeEnder;
		List<Integer> addComputeEnderList=new ArrayList<Integer>(); 
		boolean endOfTokenListReached = false;
		for (int i=0; i<tokenListesi.size()-1; i++) {
			
			astCompute=tokenListesi.get(i);
			
			
			if(astCompute.getTip().equals(TokenTipi.OzelKelime)&&astCompute.getDeger()!=null&&astCompute.getDeger().equals(ReservedCobolKeywords.ASSIGN)){ // DIPSLAY varsa
				
				if(i==tokenListesi.size()-1){
					endOfTokenListReached=true;
					break;
				}
				
				do{
					i++;
					computeParam=tokenListesi.get(i);
					
					if(i==tokenListesi.size()-1){
						endOfTokenListReached=true;
						break;
					}
					
				}while(!computeParam.getTip().equals(TokenTipi.OzelKelime));
				
				addComputeEnderList.add(i-1);
				if(endOfTokenListReached){
					break;
				}
				
				i--; // Compute dan hemen sonra bir daha compute gelirse yakalamak icin.
			}
			
		}
		
		while(!addComputeEnderList.isEmpty()){
			computeEnder=new OzelKelimeToken<String>(ReservedCobolKeywords.END_ASSIGN, 0,0,0);
			tokenListesi.add(addComputeEnderList.get(addComputeEnderList.size()-1), computeEnder);
			addComputeEnderList.remove (addComputeEnderList.size()-1); //RemoveLast
		}
	}
	
	
private void addEnderForClose() {
	AbstractToken astDisplay;
	AbstractToken displayParam;
	AbstractToken displayEnder;
	List<Integer> addDisplayEnderList=new ArrayList<Integer>(); 
	boolean endOfTokenListReached = false;
	for (int i=0; i<tokenListesi.size()-1; i++) {
		
			astDisplay=tokenListesi.get(i);
			
			
			if(astDisplay.getTip().equals(TokenTipi.OzelKelime)&&astDisplay.getDeger()!=null&&astDisplay.getDeger().equals(ReservedCobolKeywords.CLOSE)){ // CLOSE varsa
			
				if(i==tokenListesi.size()-1){
					endOfTokenListReached=true;
					break;
				}
				i++;
				displayParam=tokenListesi.get(i);
				
				while(displayParam.getTip().equals(TokenTipi.Kelime)||displayParam.getTip().equals(TokenTipi.SatirBasi)||displayParam.getTip().equals(TokenTipi.Nokta)){
					
					if(i==tokenListesi.size()-1){
						endOfTokenListReached=true;
						break;
					}
					i++;
					displayParam=tokenListesi.get(i);
				}
				addDisplayEnderList.add(i-1);
				if(endOfTokenListReached){
					break;
				}
				i--; // Display dan hemen sonra bir daha display gelirse yakalamak icin.
			}
	}
	
	while(!addDisplayEnderList.isEmpty()){
		displayEnder=new OzelKelimeToken<String>(ReservedCobolKeywords.END_CLOSE, 0,0,0);
		tokenListesi.add(addDisplayEnderList.get(addDisplayEnderList.size()-1), displayEnder);
		addDisplayEnderList.remove (addDisplayEnderList.size()-1); //RemoveLast
		
	}
}



private void processNumberStartingNames() {
	AbstractToken<String> joinedItemName;
	String name;
	Double startNumber;
	for (int i=0; i<tokenListesi.size(); i++) {
		if(tokenListesi.get(i).getTip().equals(TokenTipi.Sayi)
				&&tokenListesi.get(i+1).getTip().equals(TokenTipi.Karakter)
				&&tokenListesi.get(i+2).getTip().equals(TokenTipi.Kelime)
				&&tokenListesi.get(i+1).getDeger().equals('-')
				){
			startNumber=(Double) tokenListesi.get(i).getDeger();
			name="F"+startNumber.intValue()+"-"+tokenListesi.get(i+2).getDeger().toString();
			joinedItemName=new KelimeToken<String>(name, tokenListesi.get(i).getSatirNumarasi(), 0, 0);
			tokenListesi.set(i, joinedItemName);
			tokenListesi.get(i+1).setTip(TokenTipi.OncekiKelimeyleBirlesen);
			tokenListesi.get(i+2).setTip(TokenTipi.OncekiKelimeyleBirlesen);
		}
	}
	
	for (int i=tokenListesi.size()-1; i>=0; i--) {
		if(tokenListesi.get(i).getTip().equals(TokenTipi.OncekiKelimeyleBirlesen)){
			tokenListesi.remove(i);
		}
	}
}



/*
private void splitSAnd9AtDataType() {
	OzelKelimeToken pic=new  OzelKelimeToken<String>("PIC",0,0,0);
	AbstractToken picClause;
	String picClause 
	for (int i=0; i<tokenListesi.size();i++) {
		if(tokenListesi.get(i).tokenMatchs(pic)){
			picClause=tokenListesi.get(i);
			if(picClause.getDeger() instanceof String){
				
			}
		}
	}
}*/



private void removeJoinedCauseOfKeyValueTokens() {
	for (int i=tokenListesi.size()-1; i>=0; i--) {
		if(tokenListesi.get(i).getTip().equals(TokenTipi.KeyValueRemaining)){
			tokenListesi.remove(i);
		}
	}
}



private void setKeyValueKeywords() {
	int tokenPosition=0;
	AbstractToken current;
	AbstractToken next;
	Iterator<AbstractToken> it = tokenListesi.iterator();
	KeyValueOzelKelimeToken newCreatedKeyValueToken;
	
	String currentDeger;
	String nextDeger;
	Double nextDegerAsDouble;
	
	boolean nextIsDot=false;
	current=it.next();
	next=it.next();
	if(next.getTip().equals(TokenTipi.Nokta)){
		nextIsDot=true;
		next=it.next();
	}
	
	
	KeyValueOzelKelimeler keyValueOzelKelimeler=new KeyValueOzelKelimeler();
	while(it.hasNext()){
		
		logger.info(current.getDeger()+" "+ next.getDeger());
		
		if((current.getDeger() instanceof String) &&(next.getDeger() instanceof String)){
			
			currentDeger=(String) current.getDeger();
			nextDeger=(String) next.getDeger();
			
			for (KeyValueOzelKelimeler.KeyValueKeyword keyValueKeyword : keyValueOzelKelimeler.keyValueKeywords) {
				if(	currentDeger.equals(keyValueKeyword.getKey())){
					
					newCreatedKeyValueToken= new KeyValueOzelKelimeToken(currentDeger,nextDeger,currentTokenizer.lineno(), current.getSatirNumarasi(),current.getSatirdakiTokenSirasi());
					
					if(nextIsDot){
					
						tokenListesi.set(tokenPosition, newCreatedKeyValueToken);
						tokenListesi.get(tokenPosition+1).setTip(TokenTipi.KeyValueRemaining);
						tokenListesi.get(tokenPosition+2).setTip(TokenTipi.KeyValueRemaining);
					
					}else{
						
						tokenListesi.set(tokenPosition, newCreatedKeyValueToken);
						tokenListesi.get(tokenPosition+1).setTip(TokenTipi.KeyValueRemaining);
						
					}
				}
						
			}
		}else if((current.getDeger() instanceof String) &&(next.getDeger() instanceof Double)){
			
			currentDeger=(String) current.getDeger();
			nextDegerAsDouble=(Double) next.getDeger();
		
			for (KeyValueOzelKelimeler.KeyValueKeyword keyValueKeyword : keyValueOzelKelimeler.keyValueKeywords) {
				if(	currentDeger.equals(keyValueKeyword.getKey())){
					newCreatedKeyValueToken= new KeyValueOzelKelimeToken(currentDeger,nextDegerAsDouble.intValue(),currentTokenizer.lineno(), current.getSatirNumarasi(),current.getSatirdakiTokenSirasi());
					
					if(nextIsDot){
						tokenListesi.set(tokenPosition, newCreatedKeyValueToken);
						tokenListesi.get(tokenPosition+1).setTip(TokenTipi.KeyValueRemaining);
						tokenListesi.get(tokenPosition+1).setTip(TokenTipi.KeyValueRemaining);
					}else{
						tokenListesi.set(tokenPosition, newCreatedKeyValueToken);
						tokenListesi.get(tokenPosition+1).setTip(TokenTipi.KeyValueRemaining);
					}
				}
			}
		}
		
		current=next;
		next=it.next();
		tokenPosition++;
		nextIsDot=false; //Resetliyoruz.
		
		if(next.getTip().equals(TokenTipi.Nokta)){ //  PROGRAM-ID. VLMOKU1.   İkinci noktayi atlamak icin.
			next=it.next();
			nextIsDot=true;
			tokenPosition++;
		}
		
	}
	
	removeJoinedCauseOfKeyValueTokens();
}



private void joinKeywordsWithSpaces() {
	    joinThreeKeywordsWithSpaces(); //Önce bu olmalı. Sonra joinTwo olmalı.
	    removeJoinedWithPreviousTokens();
	    joinTwoKeywordsWithSpaces();
	    removeJoinedWithPreviousTokens();
}



/*ASSIGN TO--> ASSIGN_TO
RECORD KEY IS
ORGANIZATION IS -->ORGANIZATION_IS
*/
private void joinTwoKeywordsWithSpaces() {
	int tokenPosition=0;
	AbstractToken current;
	AbstractToken next;
	Iterator<AbstractToken> it = tokenListesi.iterator(); 
	
	String currentDeger = "";
	String nextDeger;
	
	next=it.next();
	
	IkiliOzelKelimelerCobol ikiliOzelKelimeler=new IkiliOzelKelimelerCobol();
	do{
		current=next;
		next=it.next();
		
		if((current.getDeger() instanceof String) &&(next.getDeger() instanceof String)){
			
			currentDeger=(String) current.getDeger();
			nextDeger=(String) next.getDeger();
			logger.info("Ikili Kelime Current: "+currentDeger);
			logger.info("Ikili Kelime Next: "+nextDeger);
		
			/*if(currentDeger.equals(ReservedCobolKeywords.ASSIGN)&&nextDeger.equals(ReservedCobolKeywords.TO)){
				tokenListesi.get(tokenPosition).setDeger(ReservedCobolKeywords.ASSIGNED_TO);
				tokenListesi.get(tokenPosition+1).setTip(TokenTipi.OncekiKelimeyleBirlesen);
			}else if(currentDeger.equals(ReservedCobolKeywords.ORGANIZATION)&&nextDeger.equals(ReservedCobolKeywords.IS)){
				tokenListesi.get(tokenPosition).setDeger(ReservedCobolKeywords.ORGANIZATION_IS);
				tokenListesi.get(tokenPosition+1).setTip(TokenTipi.OncekiKelimeyleBirlesen);
			}*/
		
			for (IkiliOzelKelimelerCobol.DoubleWordKeyword doubleKeywod : ikiliOzelKelimeler.ikiliKelimeler) {
				if(currentDeger.equals(doubleKeywod.getFirstKeyword())&&nextDeger.equals(doubleKeywod.getSecondKeyword())){
					tokenListesi.get(tokenPosition).setDeger(doubleKeywod.getFirstKeyword()+"_"+doubleKeywod.getSecondKeyword());
					tokenListesi.get(tokenPosition).setTip(TokenTipi.OzelKelime);
					tokenListesi.get(tokenPosition+1).setTip(TokenTipi.OncekiKelimeyleBirlesen);
				}
						
			}
		}
		tokenPosition++;
		
		if(currentDeger.equals("DATA")){
			boolean debug;
			logger.info("test");
		}
		
	}while(it.hasNext());
	
	
}

/*
ACCESS MODE IS --> ACCESS_MODE_IS
FILE STATUS IS
*/
private void joinThreeKeywordsWithSpaces() {
	int tokenPosition=0;
	AbstractToken current;
	AbstractToken next;
	AbstractToken nextOfNext;
	Iterator<AbstractToken> it = tokenListesi.iterator(); 
	
	String currentDeger;
	String nextDeger;
	String nextNextDeger;
	
	current=it.next();
	next=it.next();
	nextOfNext=it.next();
	
	UcluOzelKelimeler ucluOzelKelimeler=new UcluOzelKelimeler();
	while(it.hasNext()){
		
		if((current.getDeger() instanceof String) && (next.getDeger() instanceof String)&&(nextOfNext.getDeger() instanceof String)){
				currentDeger=(String) current.getDeger();
				nextDeger=(String) next.getDeger();
				nextNextDeger=(String) nextOfNext.getDeger();
				
				for (UcluOzelKelimeler.TripleWordKeyword tripleKeyword : ucluOzelKelimeler.ucluKelimeler) {
					if(currentDeger.equals(tripleKeyword.getFirstKeyword())&&nextDeger.equals(tripleKeyword.getSecondKeyword()) &&nextNextDeger.equals(tripleKeyword.getThirdKeyword())){
						tokenListesi.get(tokenPosition).setDeger(tripleKeyword.getFirstKeyword()+"_"+tripleKeyword.getSecondKeyword()+"_"+tripleKeyword.getThirdKeyword());
						tokenListesi.get(tokenPosition).setTip(TokenTipi.OzelKelime);
						tokenListesi.get(tokenPosition+1).setTip(TokenTipi.OncekiKelimeyleBirlesen);
						tokenListesi.get(tokenPosition+2).setTip(TokenTipi.OncekiKelimeyleBirlesen);
					}
				}
		}
		current=next;
		next=nextOfNext;
		nextOfNext=it.next();
		tokenPosition++;
	}
	
}


private void removeJoinedWithPreviousTokens() {
	for (int i=tokenListesi.size()-1; i>=0; i--) {
		if(tokenListesi.get(i).getTip().equals(TokenTipi.OncekiKelimeyleBirlesen)){
			tokenListesi.remove(i);
		}
	}
}


private void setReservedLineNumberTokens() {
	if(!ConverterConfiguration.RESERVERDNUMBERS){
		return;
	}
	
	//Line Basindakini Rezerve Yapar
	{
		AbstractToken astPrevious=null;
		AbstractToken astAtLineStart=null;
		
		Iterator<AbstractToken> it = tokenListesi.iterator(); 
		
		astPrevious=it.next();
		astAtLineStart=it.next();
		
		astPrevious.setTip(TokenTipi.SatirNumaralariIcinRezerve); //İlk Satırın başındakini rezerve set eder.
		
		while(it.hasNext()){
			logger.info("***************************");
			logger.info("astPrevious:"+astPrevious);
			logger.info("astAtLineStart:"+astAtLineStart);
			
			if(astPrevious.getTip().equals(TokenTipi.SatirBasi)&&astAtLineStart.getTip().equals(TokenTipi.Sayi)){
				astAtLineStart.setTip(TokenTipi.SatirNumaralariIcinRezerve);
					logger.info("Change Tip:"+astAtLineStart);
			}
			logger.info("***************************");
			astPrevious=astAtLineStart;
			astAtLineStart=it.next();
		};
	
	}
	
	//Line Sonundakini Rezerve Yapar
	{
		AbstractToken astAtLineEnd=null;
		AbstractToken astNext=null;
		
		Iterator<AbstractToken> it = tokenListesi.iterator(); 
		
		astNext=it.next();
		
		do{
			astAtLineEnd=astNext;
			astNext=it.next();
			
			logger.info("***************************");
			logger.info("astAtLineEnd:"+astAtLineEnd);
			logger.info("astNext:"+astNext);
			
			if(astNext.getTip().equals(TokenTipi.SatirBasi)&&astAtLineEnd.getTip().equals(TokenTipi.Sayi)){
				astAtLineEnd.setTip(TokenTipi.SatirNumaralariIcinRezerve);
					logger.info("Change Tip:"+astAtLineEnd);
			}
			logger.info("***************************");
			
		}while(it.hasNext());
	
		if(astNext.getTip().equals(TokenTipi.Sayi)){  // Son satirda satibasi yoksa son satir sayidir.
			astNext.setTip(TokenTipi.SatirNumaralariIcinRezerve); 
		}
		
	}
	 removeReservedLineNumberTokens();
	
}




private void removeReservedLineNumberTokens() {
	if(!ConverterConfiguration.RESERVERDNUMBERS){
		return;
	}
	for (int i=tokenListesi.size()-1; i>=0; i--) {
		if(tokenListesi.get(i).getTip().equals(TokenTipi.SatirNumaralariIcinRezerve)){
			tokenListesi.remove(i);
		}
	}
}





private void printTokenList() {
	logger.info("*****************************************************************");
	logger.info("*****************************************************************");
	logger.info("*****************************************************************");
	logger.info("*****************************************************************");
	logger.info("TOKEN LISTESI BAŞLANGIÇ");
	for (AbstractToken ast : tokenListesi) {
		 logger.info(ast.toString());
	}
	logger.info("TOKEN LISTESI SON    Size:"+ tokenListesi.size());
	logger.info("*****************************************************************");
	logger.info("*****************************************************************");
	logger.info("*****************************************************************");
	logger.info("*****************************************************************");
}



//(Paragrafın İsmi yazılmamışsa)Procedure Div den HEMEN sonra özel kelime varsa PARAGRAPH MAIN satırını kelimesini ekler. MOVE --> PARAGRAGH_MAIN MOVE
	private void addParagraghMainKeyword() {
		int tokenPosition=0;
		boolean addMainParagraphKeyword=false, addParagraphKeyword=true;
		AbstractToken ast;
		AbstractToken sonrakiKelime = null;
		Iterator<AbstractToken> it = tokenListesi.iterator(); 
		
		while(it.hasNext()){
			ast=it.next();
			tokenPosition++;
			if(ast.getDeger()!=null&&ast.getDeger().equals(ReservedCobolKeywords.PROCEDURE_DIVISION)){
				
				
				while(it.hasNext())	{
					sonrakiKelime=it.next();
					tokenPosition++;
					if(!sonrakiKelime.getTip().equals(TokenTipi.SatirBasi)){
						break;
					}
				}
				
				if(sonrakiKelime.getTip().equals(TokenTipi.OzelKelime) && !sonrakiKelime.getDeger().equals("PARAGRAPH")){
					addMainParagraphKeyword=true;
					 break;
				}else if(sonrakiKelime.getTip().equals(TokenTipi.Kelime)){
					addParagraphKeyword=true;
					break;
				}
			}
		};
		
		tokenPosition--;
		if(addMainParagraphKeyword){
			tokenListesi.add(tokenPosition,new OzelKelimeToken("MAIN_PARAGRAPH",0, 0,0));
			tokenListesi.add(new SatirBasiToken(0,""));
		}
		
	}
	
	//Oncesi ve sonrasi satirbaşı ise ve özel Kelime değilse paragraf ekle
		private void addParagraghKeyword() {
			AbstractToken astPrevious=null;
			AbstractToken astCurrent=null;
			AbstractToken astNext=null;
			
			boolean inProcedureDiv=false;
			Iterator<AbstractToken> it = tokenListesi.iterator(); 
			
			astPrevious=it.next();
			astCurrent=it.next();
			astNext=it.next();
			
			while(it.hasNext()){
				logger.info("***************************");
				logger.info("astPrevious:"+astPrevious);
				logger.info("astCurrent:"+astCurrent);
				logger.info("astNext:"+astNext);
				
				if(astPrevious.getDeger()!=null&&astPrevious.getDeger().equals(ReservedCobolKeywords.PROCEDURE_DIVISION)){
					inProcedureDiv=true;
				}
				
				if(astPrevious.getTip().equals(TokenTipi.SatirBasi)
						&& astCurrent.getTip().equals(TokenTipi.Kelime)
						&&(astNext.getTip().equals(TokenTipi.SatirBasi)||astNext.getTip().equals(TokenTipi.Nokta))
						&&inProcedureDiv)
				{
					astCurrent.setTip(TokenTipi.ParagraphToken);
				}
				astPrevious=astCurrent;
				astCurrent=astNext;
				astNext=it.next();
			};
			
			for (int i=0; i<tokenListesi.size();i++) {
				if(tokenListesi.get(i).getTip().equals(TokenTipi.ParagraphToken)){
					tokenListesi.get(i).setTip(TokenTipi.Kelime);
					tokenListesi.add(i,new OzelKelimeToken("PARAGRAPH",0, 0,0));
				}
				
			}
			
		}	
	


	public StringBuilder exportLexedData(String fullTokenizeFileName){
		StringBuilder sb=new StringBuilder();
		KeyValueOzelKelimeToken keyValueToken;
		for (AbstractToken token : tokenListesi) {
			if(token.getTip()==TokenTipi.SatirBasi){
				logger.info("");
				sb.append("\n");
			}else if(token.getTip()==TokenTipi.KeyValueOzelKelime){
				keyValueToken=(KeyValueOzelKelimeToken) token;
				sb.append(token.getDeger()+"  Uzunluk:"+token.getUzunluk()+" Satir No:"+token.getSatirNumarasi()+" Tipi:"+token.getTip()+ " "+keyValueToken.getValue()+"\n");
			}else if(token.getTip()==TokenTipi.Nokta){
			  // Do nothing;
			}else{
				sb.append(token.getDeger()+"  Uzunluk:"+token.getUzunluk()+" Satir No:"+token.getSatirNumarasi()+" Tipi:"+token.getTip()+"\n");
					
			}
		}
		try {
			WriteToFile.writeToFile(sb,fullTokenizeFileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.debug("",e);
		}
		return sb;
		
	}



	public void removeDotTokens() {
		for (int i=0; i<tokenListesi.size()-1; i++) {
			if(tokenListesi.get(i).getTip().equals(TokenTipi.Nokta)){
				tokenListesi.remove(i);
			}
		}
	}

	public void tokenizeSourceFile(String sourceFileFullName, String module){
		try {
		  		InputStream inputStream       = new FileInputStream(sourceFileFullName);
		  		Reader      inputStreamReader = new InputStreamReader(inputStream);
		  		currentTokenizer = new CustomStreamTokenizer(inputStreamReader);
		  		
		  		//currentTokenizer.commentChar('*');
		  		currentTokenizer.eolIsSignificant(true);
		  		currentTokenizer.ordinaryChar('/');
		  		//currentTokenizer.whitespaceChars('.', '.');
		  		currentTokenizer.ordinaryChar('.');
		  	     // print the stream tokens
		         boolean eof = false;
		         boolean eol=false;
		         boolean inComment=false;
		         int satirdakiTokenSirasi=0;
		         boolean procedureDivReached=false;
		         do {
		            int token = currentTokenizer.nextToken();
		            logger.info("Token:"+token+ " Sval:"+currentTokenizer.sval+ " NVal:"+currentTokenizer.nval);
		            switch (token) {
		               case CustomStreamTokenizer.TT_EOF:
		            	  logger.info("End of File encountered.");
		                  eof = true;
		                  eol=false;
		                  satirdakiTokenSirasi=0;
		                  break;
		               case CustomStreamTokenizer.TT_EOL:
		                  logger.info("End of Line encountered.");
		                  eol=true;
		                  inComment=false;
		                  tokenListesi.add(new SatirBasiToken(currentTokenizer.lineno(),""));
			              satirdakiTokenSirasi=0;
			              break;
		               case CustomStreamTokenizer.TT_WORD:
		            	   logger.info("Word: " + currentTokenizer.sval);
		            	   String tokenVal=currentTokenizer.sval;
		            	   if(tokenVal.equals(ReservedCobolKeywords.PROCEDURE_DIVISION)){
		            		   
		            	   }
		            	   if(ozelKelimeler.ozelKelimeler.contains(tokenVal)&&!inComment) {
		            			   tokenListesi.add(new OzelKelimeToken(tokenVal,currentTokenizer.lineno(), 0,satirdakiTokenSirasi ));
		            			}
		            	   else{
		            		   		tokenListesi.add(new KelimeToken(currentTokenizer.sval,currentTokenizer.lineno(), 0,satirdakiTokenSirasi ));
		            	   		}
		            	   satirdakiTokenSirasi++;
		            	   eol=false;
			               break;
			           case CustomStreamTokenizer.TT_NUMBER:
		                  eol=false;
		                  tokenListesi.add(new SayiToken(currentTokenizer.nval,currentTokenizer.lineno(), 0,satirdakiTokenSirasi));
		                  satirdakiTokenSirasi++;
		                  break;
			           case CustomStreamTokenizer.DOUBLE_QUOTA:
		                  eol=false;
		                  tokenListesi.add(new KelimeToken(currentTokenizer.sval,currentTokenizer.lineno(), 0,satirdakiTokenSirasi));
		                  satirdakiTokenSirasi++;
		                  break;
			           case CustomStreamTokenizer.UST_VIRGUL:
			                  eol=false;
			                  tokenListesi.add(new KelimeToken(currentTokenizer.sval,currentTokenizer.lineno(), 0,satirdakiTokenSirasi));
			                  satirdakiTokenSirasi++;
			                  break; 
			           case CustomStreamTokenizer.STAR:
			                  eol=false;
			                  tokenListesi.add(new KarakterToken((char)token, currentTokenizer.lineno(), 1,satirdakiTokenSirasi));
						      satirdakiTokenSirasi++;
				              inComment=true;
			                  break;   
			           case CustomStreamTokenizer.SLASH:
			        	      eol=false;
			        	      tokenListesi.add(new KarakterToken((char)token, currentTokenizer.lineno(), 1,satirdakiTokenSirasi));
						      satirdakiTokenSirasi++;
				              inComment=true;
			                  break;     
			           case CustomStreamTokenizer.DOT:
			        	      eol=false;
			        	      tokenListesi.add(new NoktaToken((char)token, currentTokenizer.lineno(), 1,satirdakiTokenSirasi));
						      satirdakiTokenSirasi++;
				              inComment=true;
			                  break;     			                  
			           default:
		            	   	   tokenListesi.add(new KarakterToken((char)token, currentTokenizer.lineno(), 1,satirdakiTokenSirasi));
		            	   	   satirdakiTokenSirasi++;
		        		       eol=false;
		            	   }
		         } while (!eof);
		         inputStreamReader.close();
				      } catch (Exception ex) {
		         ex.printStackTrace();
		      }
		
		    setReservedLineNumberTokens();
		    
		    processNumberStartingNames();
		    
		    addIfVisualTokens();
			   
		    removeDotTokens();//Bu asamadan sonra dot Tokenlara ihtiyac yok.
			    
		    joinKeywordsWithSpaces(); //Remove da icinde.
		    
		   // controParagraphName(); addParagraghKeyword bu sorunu çözüyor.
		    
		    addParagraghMainKeyword();
		    
		    addParagraghKeyword();
		    
		    addEnderForDisplay();
		    
		    addEnderForCompute();
		    
		    addEnderForAssign();
		    
		    addEnderForClose();
		    
		    addOpenForOutput();
		    
		    //addEnderForInvalidKey();
		    
		    
		    //025280     PERFORM UNTIL VAC-SON = 2                                    02528000
		    addThenForModeStatement(ReservedCobolKeywords.PERFORM_UNTIL); // 
		    
		    //027090       PERFORM VARYING I FROM 2 BY 1 UNTIL I > AY-CON             02709000
		    addThenForModeStatement(ReservedCobolKeywords.UNTIL);
		    
		    addThenForModeStatement(ReservedCobolKeywords.WHEN);
		    
		    setKeyValueKeywords();  //Remove da icinde.
		    
		    
		    
	}

	@Override
	public Map<String, String> getIncludeFileList() {
		// TODO Auto-generated method stub
		return null;
	}

}
