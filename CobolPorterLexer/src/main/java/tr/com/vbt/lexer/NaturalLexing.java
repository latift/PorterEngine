package tr.com.vbt.lexer;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.ArrayToken;
import tr.com.vbt.token.DortluOzelKelimelerNatural;
import tr.com.vbt.token.IkiliOzelKelimelerNatural;
import tr.com.vbt.token.KarakterToken;
import tr.com.vbt.token.KelimeToken;
import tr.com.vbt.token.KeyValueOzelKelimeToken;
import tr.com.vbt.token.KeyValueOzelKelimelerNatural;
import tr.com.vbt.token.NaturalOzelKelimeler;
import tr.com.vbt.token.NoktaToken;
import tr.com.vbt.token.OzelKelimeToken;
import tr.com.vbt.token.SatirBasiToken;
import tr.com.vbt.token.SayiToken;
import tr.com.vbt.token.TokenTipi;
import tr.com.vbt.token.UcluOzelKelimelerNatural;
import tr.com.vbt.util.ConverterConfiguration;
import tr.com.vbt.util.IfEndIfCommandsUtility;
import tr.com.vbt.util.WriteToFile;

public class NaturalLexing extends AbstractLexing {

	final static Logger logger = LoggerFactory.getLogger(NaturalLexing.class);


	private Map<String, String> includeFileList = new HashMap<>();
	
	private Map<String, FieldWrapper> includeFieldList = new HashMap<>();
	
	public HashMap<String, String> tableColumnReferans=new HashMap<>();
	
	public HashMap<String, RedefinedColumn> tableColumnRedefinersList=new HashMap<>();
	
	protected NaturalOzelKelimeler naturalOzelKelimeler;
	
	final private Set<String> GLOBAL_VARIABLES_SET = new HashSet<String>();

	public NaturalLexing() {
		super();
		naturalOzelKelimeler = new NaturalOzelKelimeler();
		
		initGlobalVariablesSet();
	}
	
	private void initGlobalVariablesSet() {
		GLOBAL_VARIABLES_SET.add("MUSNO1");
		GLOBAL_VARIABLES_SET.add("MUSNO2");
		GLOBAL_VARIABLES_SET.add("KRX_KOD");
		GLOBAL_VARIABLES_SET.add("HESCINSI");
		GLOBAL_VARIABLES_SET.add("DOVIZ");
		GLOBAL_VARIABLES_SET.add("HESNO");
		GLOBAL_VARIABLES_SET.add("ISSIC");
		GLOBAL_VARIABLES_SET.add("ISUSER");
		GLOBAL_VARIABLES_SET.add("YAZICI");
		GLOBAL_VARIABLES_SET.add("LAZERYAZICI");
		GLOBAL_VARIABLES_SET.add("ISSUBE");
		GLOBAL_VARIABLES_SET.add("ISSERVIS");
		GLOBAL_VARIABLES_SET.add("GMESAJ");
		GLOBAL_VARIABLES_SET.add("PIKYAZICI");
		GLOBAL_VARIABLES_SET.add("HESDOKYAZICI");
		GLOBAL_VARIABLES_SET.add("SW_PIKYAZ");

	}

	/**
	 * 
	 * OPEN INPUT VACFILE VCCFILE VLMFILE VCVFILE 02360000 023700 OUTPUT PCFILE.
	 * 02370000 --> OPEN INPUT VACFILE VCCFILE VLMFILE VCVFILE 02360000 023700
	 * OPEN OUTPUT PCFILE. 02370000
	 */
	private void addOpenForOutput() {

		AbstractToken astCurrent, astNext;

		boolean ifFound = false;
		for (int i = 0; i < tokenListesi.size() - 2; i++) {

			astCurrent = tokenListesi.get(i);
			astNext = tokenListesi.get(i + 1);

			logger.debug("addOperForOutput: tokenListesi.get(" + i + ") : " + astCurrent.toString()
					+ " tokenListesi.size()=" + tokenListesi.size());

			if (astNext.getTip().equals(TokenTipi.OzelKelime) // Soraki Kelime
																// OUTPUT ise
																// döngüye devam
																// et.
					&& astNext.getDeger() != null && (astNext.getDeger().equals(ReservedCobolKeywords.OUTPUT))) {
				if (astCurrent.getTip().equals(TokenTipi.OzelKelime) && astCurrent.getDeger() != null
						&& astCurrent.getDeger().equals(ReservedCobolKeywords.OPEN)) {
					// do nothing
				} else {
					tokenListesi.add(i + 1, new OzelKelimeToken<String>("OPEN", 0, 0, 0,true));
					i++;
				}

			}

		}

	}

	private void addIfVisualTokens() {

		addThenForModeStatement(ReservedCobolKeywords.IF);
		
		addThenForModeStatement(ReservedNaturalKeywords.ACCEPT_IF);
		
		addThenForModeStatement(ReservedNaturalKeywords.REJECT_IF);
		
		addThenForModeStatement(ReservedNaturalKeywords.ACCEPT);
		
		addThenForModeStatement(ReservedCobolKeywords.ELSE_IF);

		addThenForModeStatement(ReservedNaturalKeywords.REPEAT_UNTIL);
		
		addThenForModeStatement(ReservedNaturalKeywords.REPEAT_WHILE);

		addThenForModeStatement(ReservedNaturalKeywords.UNTIL);
		
		addThenForModeStatement(ReservedNaturalKeywords.READ);
		
		addThenForModeStatement(ReservedNaturalKeywords.FIND);
		
		addThenForModeStatement(ReservedNaturalKeywords.WHEN);
		
		addEndIfBeforeElse();
	}

	/**
	 * If den sonra THENN yazılmayabiliyor. Bu durumlarda visual ekliyoruz.
	 * Düzeltme Eklemesidir.
	 * 
	 */
	private void addThenForModeStatement(String mode) {

		AbstractToken astCurrent, astWith;

		boolean ifFound = false;
		for (int i = 0; i < tokenListesi.size(); i++) {

			astCurrent = tokenListesi.get(i);
			
			logger.debug(astCurrent.toString());

		    if (astCurrent.getTip().equals(TokenTipi.Karakter)) { // Soraki
				// Karakterse
				// (=
				// vesaire)döngüye
				// devam
				// et.
			continue;
			}else if (astCurrent.isOzelKelime(ReservedNaturalKeywords.AND)
							|| astCurrent.isOzelKelime(ReservedNaturalKeywords.OR)
							|| astCurrent.isOzelKelime(ReservedNaturalKeywords.NOT)
							|| astCurrent.isOzelKelime(ReservedNaturalKeywords.IS)
							|| astCurrent.isOzelKelime(ReservedNaturalKeywords.NUMERIC)
							|| astCurrent.isOzelKelime(ReservedNaturalKeywords.IS_NUMERIC)
							|| astCurrent.isOzelKelime(ReservedNaturalKeywords.EQUAL)
							|| astCurrent.isOzelKelime(ReservedNaturalKeywords.MASK)
							|| astCurrent.isOzelKelime(ReservedNaturalKeywords.SUBSTR)
							|| astCurrent.isOzelKelime(ReservedNaturalKeywords.WITH)
							|| astCurrent.isOzelKelime(ReservedNaturalKeywords.THRU)
							|| astCurrent.isOzelKelime(ReservedNaturalKeywords.STARTING_FROM)
							|| astCurrent.isOzelKelime(ReservedNaturalKeywords.ENDING_AT)
							|| astCurrent.isOzelKelime(ReservedNaturalKeywords.DESCENDING)
							|| astCurrent.isOzelKelime(ReservedNaturalKeywords.ASCENDING)
							|| astCurrent.isOzelKelime(ReservedNaturalKeywords.SORTED_BY)
							|| astCurrent.isOzelKelime(ReservedNaturalKeywords.GE)
							|| astCurrent.isOzelKelime(ReservedNaturalKeywords.GT)
							|| astCurrent.isOzelKelime(ReservedNaturalKeywords.LE)
							|| astCurrent.isOzelKelime(ReservedNaturalKeywords.LT)
							|| astCurrent.isOzelKelime(ReservedNaturalKeywords.EQ)
							|| astCurrent.isOzelKelime(ReservedNaturalKeywords.NE)) { // VLM-DEGER
																							// (
																							// ACOCC-XX2
																							// ,
																							// I
																							// )
				continue;
			}else if (astCurrent.isKelime(" ")) { // VLM-DEGER
																		// (
																		// ACOCC-XX2
																		// ,
																		// I
																		// )
							continue;
			}else if (ifFound && astCurrent.isOzelKelime(ReservedNaturalKeywords.THEN)) { // If
																						// den
																						// sonraki
																						// OzelKelime
																						// THEN
																						// se
																						// bir
																						// şey
																						// yapma
				ifFound = false;
			} // System variable gelmişsse if şartının içinde olabilir. MEsela:
				// IF *LANGUAGE EQ 7
			else if (ifFound && astCurrent.isOzelKelime()
					&& !astCurrent.getDeger().equals(ReservedNaturalKeywords.THEN)) { // IF
																						// den
																						// sonraki
																						// kelime
																						// THEN
																						// değilse
																						// ekle.
				ifFound = false;
				tokenListesi.add(i, new OzelKelimeToken<String>("THEN", 0, 0, 0,true));
			} else if (astCurrent.isOzelKelime(mode)) { // IF varsa
				ifFound = true;
				
				while (true && mode.equals("FIND")|| mode.equals("READ")) {
					i++;
					if(i==tokenListesi.size()){
						return;
					}
					astWith = tokenListesi.get(i);
					if (astWith.isOzelKelime(ReservedNaturalKeywords.WITH)||astWith.isOzelKelime(ReservedNaturalKeywords.BY)) {
						ifFound = true;
						break;
					}
				}
			}
		}

	}

	

	/**
	 * END-IF yazmadan yazılanlar icin düzeltme kodudur.
	 * 
	 * 1) If görürsen Buffer a Enf-İf koy. 2) End-İf görürsen bufferdan bir tane
	 * sil. (End-if i programcı koydu ise) 3) Nokta görürsen(Complex Inner If
	 * serisinin sonuna gelinmiştir.) buffer daki tüm end-ifleri ekle 4) Else
	 * den sonra Else görürsen Else den önce End-İf ekle.
	 */
	private void addEndIf() {
		
		if(ConversionLogModel.getInstance().getMode().equals(NaturalMode.STRUCTRURED)){
			return;
		}
		
		AbstractToken astCurrent;

		AbstractToken endIfInBuffer;

		IfEndIfCommandsUtility endIfutil = new IfEndIfCommandsUtility();

		boolean previousIsElse = false; // Ard arda else gelirseyi kontrol etmek
										// için.

		for (int i = 0; i < tokenListesi.size() - 1; i++) {

			astCurrent = tokenListesi.get(i);

			logger.debug(astCurrent.toString());

			if (astCurrent.isOzelKelime(ReservedCobolKeywords.IF) ) { // If görürsen
				// Buffer a
				// Enf-İf
				// koy
				endIfutil.putEndIfTokenToBuffer();
				previousIsElse = false;
			} else if (astCurrent.isOzelKelime("END-IF")) {// End-İf görürsen bufferdan birtane sil. (End-ifi programcıkoyduise)
				endIfutil.getEndIfFromBuffer();
				previousIsElse = false;
	
			} else if (previousIsElse && astCurrent.isOzelKelime(ReservedCobolKeywords.ELSE)) { // Elseden sonra Else görürsen Else den önce End-İf ekle. (İki Else arasında )

				endIfInBuffer = endIfutil.getEndIfFromBuffer();
				tokenListesi.add(i, endIfInBuffer);
				i++;
				previousIsElse = true;
			}
		}

	}

	/**
	 * Sorun: If-EndIf de ifin enderi EndIf, If-Else-EndIf de Else ender ı EndIf
	 * ama If in Enderi yok. Çözümü: addEndIfBeforeElse: Else gördü isen
	 * öncesinde EndIfBeforeElse ekle.
	 */
	private void addEndIfBeforeElse() {
		
		//Buraya dikkat. Bilerek Reporting yazıldı. Çünkü sadece Strucutred mode da bunu eklemeliyiz. Reportingde farklı olmalı.
		if(ConversionLogModel.getInstance().getMode().equals(NaturalMode.REPORTING)){
			return;
		}

		AbstractToken astCurrent;

		for (int i = 0; i < tokenListesi.size() - 1; i++) {

			astCurrent = tokenListesi.get(i);

			if (astCurrent.getTip().equals(TokenTipi.OzelKelime) // Else gördü
																	// isen
																	// öncesinde
																	// EndIfBeforeElse
																	// ekle.
					&& astCurrent.getDeger() != null && astCurrent.getDeger().equals(ReservedCobolKeywords.ELSE)) {
				tokenListesi.add(i, new OzelKelimeToken<String>("END_IF_BEFORE_ELSE", 0, 0, 0,true));
				i++;
			}
		}

	}
	

	private void addEnderForDisplay() {
		
		AbstractToken astCurrent;
		
		boolean astFormatReached=false;
	
		for (int i = 0; i < tokenListesi.size() - 1; i++) {

			astCurrent = tokenListesi.get(i);

			if(astFormatReached  && (!astCurrent.getTip().equals(TokenTipi.OzelKelime) ||  
						astCurrent.isOzelKelime(ReservedNaturalKeywords.NOTITLE))) {

				astCurrent = tokenListesi.get(i);
				

			}else if(astFormatReached){
			
				tokenListesi.add(i, new OzelKelimeToken<String>(ReservedNaturalKeywords.END_DISPLAY, astCurrent.getSatirNumarasi(), 0, 0,true));
				
				astFormatReached=false;
			}else if (astCurrent.isOzelKelime(ReservedNaturalKeywords.DISPLAY)) { // DIPSLAY
							// varsa
			astFormatReached=true;
			
			}

		}


	}

	private void addEnderForCallNat() {
		AbstractToken astCallNat;
		AbstractToken callNatParam, callNatParamNext, callNatParamNexter;
		AbstractToken callNatEnder;
		List<Integer> addDisplayEnderList = new ArrayList<Integer>();
		boolean endOfTokenListReached = false;
		for (int i = 0; i < tokenListesi.size() - 2; i++) {

			astCallNat = tokenListesi.get(i);

			if (astCallNat.getTip().equals(TokenTipi.OzelKelime) && astCallNat.getDeger() != null
					&& astCallNat.getDeger().equals(ReservedNaturalKeywords.CALLNAT)) { // DIPSLAY
																						// varsa

				if (i == tokenListesi.size() - 1) {
					endOfTokenListReached = true;
					break;
				}
				i++;
				callNatParam = tokenListesi.get(i);
				callNatParamNext = tokenListesi.get(i + 1);
				callNatParamNexter = tokenListesi.get(i + 2);

				while ((callNatParam.getTip().equals(TokenTipi.Kelime)
						|| callNatParam.getTip().equals(TokenTipi.SatirBasi)
						|| callNatParam.getTip().equals(TokenTipi.Nokta)
						|| callNatParam.getTip().equals(TokenTipi.Array)
						|| callNatParam.getTip().equals(TokenTipi.Karakter))) {

					/*
					 * //callnat parametrelerinden sonra becomes_equalto
					 * geliyorsa bitir ve öncesine end_callnat koy.
					 * if(callNatParam.getTip().equals(TokenTipi.Kelime)
					 * &&callNatParamNext.getTip().equals(TokenTipi.Karakter)
					 * &&callNatParamNext.getDeger().equals(':')
					 * &&callNatParamNexter.getTip().equals(TokenTipi.Karakter)
					 * &&callNatParamNexter.getDeger().equals('=')){ break; }
					 */

					if (i == tokenListesi.size() - 1) {
						endOfTokenListReached = true;
						break;
					}
					i++;
					callNatParam = tokenListesi.get(i);
				}
				addDisplayEnderList.add(i);
				if (endOfTokenListReached) {
					break;
				}
				i--; // Display dan hemen sonra bir daha display gelirse
						// yakalamak icin.
			}
		}

		while (!addDisplayEnderList.isEmpty()) {
			callNatEnder = new OzelKelimeToken<String>(ReservedNaturalKeywords.END_CALLNAT, 0, 0, 0,true);
			tokenListesi.add(addDisplayEnderList.get(addDisplayEnderList.size() - 1), callNatEnder);
			addDisplayEnderList.remove(addDisplayEnderList.size() - 1); // RemoveLast

		}
	}

	private void addEnderForFormat() {
		
		AbstractToken astCurrent;
		
		boolean astFormatReached=false;
	
		for (int i = 0; i < tokenListesi.size() - 1; i++) {

			astCurrent = tokenListesi.get(i);

			if(astFormatReached  && (!astCurrent.getTip().equals(TokenTipi.OzelKelime) ||  
						astCurrent.isOzelKelime(ReservedNaturalKeywords.OFF))||
						astCurrent.isOzelKelime(ReservedNaturalKeywords.ON)) {

				astCurrent = tokenListesi.get(i);
				

			}else if(astFormatReached){
			
				tokenListesi.add(i, new OzelKelimeToken<String>(ReservedNaturalKeywords.END_FORMAT, astCurrent.getSatirNumarasi(), 0, 0,true));
				
				astFormatReached=false;
			}else if (astCurrent.isOzelKelime(ReservedNaturalKeywords.FORMAT)) { // DIPSLAY
							// varsa
			astFormatReached=true;
			
			}

		}


	}
	

	private void addEnderForExamine() {
		
		AbstractToken astCurrent,astNext;
		
		boolean astExamineReached=false;
	
		for (int i = 0; i < tokenListesi.size() - 1; i++) {

			astCurrent = tokenListesi.get(i);
		
			if(astExamineReached  && (!astCurrent.getTip().equals(TokenTipi.OzelKelime) ||  
						astCurrent.isOzelKelime(ReservedNaturalKeywords.WITH)||
						astCurrent.isOzelKelime(ReservedNaturalKeywords.FOR)||
						astCurrent.isOzelKelime(ReservedNaturalKeywords.GIVING)||
						astCurrent.isOzelKelime(ReservedNaturalKeywords.GIVING_INDEX)||
						astCurrent.isOzelKelime(ReservedNaturalKeywords.GIVING_LENGTH_IN)||
						astCurrent.isOzelKelime(ReservedNaturalKeywords.EXAMINE_GIVING_POSITION)||
						astCurrent.isOzelKelime(ReservedNaturalKeywords.GIVING_POSITION)||
						astCurrent.isOzelKelime(ReservedNaturalKeywords.GIVING_POSITION_IN)||
						astCurrent.isOzelKelime(ReservedNaturalKeywords.GIVING_INDEX_IN)||
						astCurrent.isOzelKelime(ReservedNaturalKeywords.GIVING_NUMBER)||
						astCurrent.isOzelKelime(ReservedNaturalKeywords.GIVING_LENGTH)||
						astCurrent.isOzelKelime(ReservedNaturalKeywords.GIVING_LENGTH_IN)||
						astCurrent.isOzelKelime(ReservedNaturalKeywords.DELETE)||
						astCurrent.isOzelKelime(ReservedNaturalKeywords.ON))) {

				astCurrent = tokenListesi.get(i);
				
			}else if(astExamineReached){
			
			
				tokenListesi.add(i, new OzelKelimeToken<String>(ReservedNaturalKeywords.END_EXAMINE, astCurrent.getSatirNumarasi(), 0, 0,true));
				
				astExamineReached=false;
			}else if (astCurrent.isOzelKelime(ReservedNaturalKeywords.EXAMINE) || astCurrent.isOzelKelime(ReservedNaturalKeywords.EXAMINE_FULL)) { // DIPSLAY
							// varsa
				astExamineReached=true;
			
			}

		}


	}
	
	private void addEnderForAmpersand() {
		
		AbstractToken astCurrent;
		
		boolean astAmpersandReached=false;
	
		for (int i = 0; i < tokenListesi.size() - 1; i++) {

			astCurrent = tokenListesi.get(i);

			if(astAmpersandReached  && !astCurrent.isOzelKelime()) {

				astCurrent = tokenListesi.get(i);
				

			}else if(astAmpersandReached){
			
				tokenListesi.add(i, new OzelKelimeToken<String>(ReservedNaturalKeywords.END_AMPERSAND, astCurrent.getSatirNumarasi(), 0, 0,true));
				
				astAmpersandReached=false;
			}else if (astCurrent.isOzelKelime(ReservedNaturalKeywords.AMPERSAND)) { // DIPSLAY
							// varsa
				astAmpersandReached=true;
			
			}
		}
	}
	
	private void addEnderForRun() {
		
		AbstractToken astCurrent;
		
		boolean astRunReached=false;
	
		for (int i = 0; i < tokenListesi.size() - 1; i++) {

			astCurrent = tokenListesi.get(i);

			if (astCurrent.isOzelKelime(ReservedNaturalKeywords.RUN)) { // DIPSLAY
																						// varsa
				astRunReached=true;
				
			}else if(astRunReached  && !astCurrent.isOzelKelime()) {

				astCurrent = tokenListesi.get(i);
		
			}else if(astRunReached){
			
				tokenListesi.add(i, new OzelKelimeToken<String>(ReservedNaturalKeywords.END_RUN, astCurrent.getSatirNumarasi(), 0, 0,true));
				
				astRunReached=false;
			}

		}


	}

	private void addEnderForReset() {
		AbstractToken astDisplay;
		AbstractToken displayParam;
		AbstractToken displayEnder;
		List<Integer> addDisplayEnderList = new ArrayList<Integer>();
		boolean endOfTokenListReached = false;
		for (int i = 0; i < tokenListesi.size() - 1; i++) {

			astDisplay = tokenListesi.get(i);

			if (astDisplay.getTip().equals(TokenTipi.OzelKelime) && astDisplay.getDeger() != null
					&& astDisplay.getDeger().equals(ReservedNaturalKeywords.RESET)) { // CLOSE
																						// varsa

				if (i == tokenListesi.size() - 1) {
					endOfTokenListReached = true;
					break;
				}
				i++;
				displayParam = tokenListesi.get(i);

				while (displayParam.getTip().equals(TokenTipi.Kelime)
						|| displayParam.getTip().equals(TokenTipi.SatirBasi)
						|| displayParam.getTip().equals(TokenTipi.Array)
						|| displayParam.getTip().equals(TokenTipi.Nokta)) {

					if (i == tokenListesi.size() - 1) {
						endOfTokenListReached = true;
						break;
					}
					i++;
					displayParam = tokenListesi.get(i);
				}
				addDisplayEnderList.add(i);
				if (endOfTokenListReached) {
					break;
				}
				i--; // Display dan hemen sonra bir daha display gelirse
						// yakalamak icin.
			}
		}

		while (!addDisplayEnderList.isEmpty()) {
			displayEnder = new OzelKelimeToken<String>(ReservedNaturalKeywords.END_RESET, 0, 0, 0,true);
			tokenListesi.add(addDisplayEnderList.get(addDisplayEnderList.size() - 1), displayEnder);
			addDisplayEnderList.remove(addDisplayEnderList.size() - 1); // RemoveLast

		}
	}
	
	private void addEnderForSetKey() {
		
		AbstractToken astCurrent;
		
		boolean astSetKeyReached=false;
	
		for (int i = 0; i < tokenListesi.size() - 1; i++) {

			astCurrent = tokenListesi.get(i);

			if(astSetKeyReached  && !astCurrent.isOzelKelime()) {

				astCurrent = tokenListesi.get(i);
				

			}else if(astSetKeyReached && (!astCurrent.isOneOfOzelKelime("OFF","ONN","ON"))){
			
				tokenListesi.add(i, new OzelKelimeToken<String>(ReservedNaturalKeywords.END_SET_KEY, astCurrent.getSatirNumarasi(), 0, 0,true));
				
				astSetKeyReached=false;
			}else if (astCurrent.isOzelKelime(ReservedNaturalKeywords.SET_KEY)) { // DIPSLAY
							// varsa
				astSetKeyReached=true;
			
			}

		}


	}


	private void addEnderForReInput() {
		
		AbstractToken astCurrent;
		
		boolean astFormatReached=false;
	
		for (int i = 0; i < tokenListesi.size() - 1; i++) {

			astCurrent = tokenListesi.get(i);

			if(astFormatReached  && astCurrent.isOneOfOzelKelime(ReservedNaturalKeywords.MARK,ReservedNaturalKeywords.WITH,ReservedNaturalKeywords.ALARM)) {

				continue;
		
			}else if(astFormatReached && astCurrent.isOzelKelime()){
			
				tokenListesi.add(i, new OzelKelimeToken<String>(ReservedNaturalKeywords.END_REINPUT, astCurrent.getSatirNumarasi(), 0, 0,true));
				
				astFormatReached=false;
			}else if (astCurrent.isOzelKelime(ReservedNaturalKeywords.REINPUT)||astCurrent.isOzelKelime(ReservedNaturalKeywords.REINPUT_WITH)) { // DIPSLAY
							// varsa
			astFormatReached=true;
			
			}

		}

	}


	private void addEnderForInput() {
		
		AbstractToken astCurrent;
		
		boolean astInputReached=false;
	
		for (int i = 0; i < tokenListesi.size() - 1; i++) {

			astCurrent = tokenListesi.get(i);
			
			/**
			 * 	// 2266 INPUT MARK *MAP.SECKOD USING MAP 'IDGMMUSS' --> Bu
				// durumda endInput eklenmez. Çünkü bu INPUTUSINGMAP olarak ele
				// alınmalı.
				if (inputParam.getTip().equals(TokenTipi.OzelKelime)
						&& inputParam.getDeger().equals(ReservedNaturalKeywords.MARK)) {
					return;
				}
			 */
			if(astInputReached  && (astCurrent.isOzelKelime(ReservedNaturalKeywords.MARK))) {

				astInputReached=false;
				

			}else if(astInputReached  && (!astCurrent.isOzelKelime() || astCurrent.isOzelKelime(ReservedNaturalKeywords.WITH_TEXT)|| astCurrent.isOzelKelime("TEXT"))) {

				astCurrent = tokenListesi.get(i);
				

			}else if(astInputReached){
			
				tokenListesi.add(i, new OzelKelimeToken<String>(ReservedNaturalKeywords.END_INPUT, astCurrent.getSatirNumarasi(), 0, 0,true));
				
				astInputReached=false;
			}else if (astCurrent.isOneOfOzelKelime(ReservedNaturalKeywords.INPUT,ReservedNaturalKeywords.INPUT_NO_ERASE)) { // DIPSLAY
							// varsa
				astInputReached=true;
			
			}

		}


	}

	private void addEnderForWrite() {
		AbstractToken astCurrent;
		
		boolean astWriteReached=false;
	
		for (int i = 0; i < tokenListesi.size() - 1; i++) {

			astCurrent = tokenListesi.get(i);

			if(astWriteReached  && (astCurrent.isOneOfOzelKelime(ReservedNaturalKeywords.NOTITLE,ReservedNaturalKeywords.ON,ReservedNaturalKeywords.OFF)
					||astCurrent.isKarakter()||astCurrent.isSayi()
					|| !astCurrent.isOzelKelime())){  // WRITE (1) NOTITLE   icindeki (1) icin eklendi

				continue;
	
			}else if(astWriteReached){
			
				tokenListesi.add(i, new OzelKelimeToken<String>(ReservedNaturalKeywords.END_WRITE, astCurrent.getSatirNumarasi(), 0, 0,true));
				
				astWriteReached=false;
			}else if (astCurrent.isOzelKelime(ReservedNaturalKeywords.WRITE) || astCurrent.isOzelKelime(ReservedNaturalKeywords.WRITE_NOTITLE)) { // WRITE
				// varsa
					astWriteReached=true;

			}
		}
	}



	private void addEnderForClose() {
		AbstractToken astDisplay;
		AbstractToken displayEnder;
		boolean displayReached = false;
		for (int i = 0; i < tokenListesi.size() - 1; i++) {

			astDisplay = tokenListesi.get(i);

			if(displayReached && astDisplay.isOzelKelime())	{
				displayEnder = new OzelKelimeToken<String>(ReservedCobolKeywords.END_CLOSE, 0, 0, 0,true);
				displayEnder.setVisualToken(true);
				tokenListesi.add(i, displayEnder);
				displayReached=false;
			}else if (astDisplay.isOzelKelime(ReservedCobolKeywords.CLOSE)) { 
				displayReached = true;
			}
		}

	}
	

	private void addEnderForDownload() {
		AbstractToken astDisplay;
		AbstractToken displayEnder;
		boolean displayReached = false;
		for (int i = 0; i < tokenListesi.size() - 1; i++) {

			astDisplay = tokenListesi.get(i);

			if(displayReached && astDisplay.isOzelKelime())	{
				displayEnder = new OzelKelimeToken<String>(ReservedCobolKeywords.END_DOWNLOAD, 0, 0, 0,true);
				displayEnder.setVisualToken(true);
				tokenListesi.add(i, displayEnder);
				displayReached=false;
			}else if (astDisplay.isOzelKelime(ReservedCobolKeywords.DOWNLOAD)) { 
				displayReached = true;
			}
		}

	}

	private void addEnderForCompress() {
		AbstractToken astCompress;
		AbstractToken compressParam;
		AbstractToken callNatEnder;
		List<Integer> addDisplayEnderList = new ArrayList<Integer>();
		boolean endOfTokenListReached = false;
		int indexAfterinto = 0;
		boolean intoReached = false;
		for (int i = 0; i < tokenListesi.size() - 1; i++) {

			astCompress = tokenListesi.get(i);

			if (astCompress.isOneOfOzelKelime(ReservedNaturalKeywords.COMPRESS,ReservedNaturalKeywords.COMPRESS_FULL)) { // DIPSLAY
																										// varsa

				indexAfterinto = 0;
				intoReached = false;

				if (i == tokenListesi.size() - 1) {
					endOfTokenListReached = true;
					break;
				}
				i++;
				compressParam = tokenListesi.get(i);

				while (compressParam.getTip().equals(TokenTipi.Kelime)
						|| compressParam.getTip().equals(TokenTipi.SatirBasi)
						|| compressParam.getTip().equals(TokenTipi.Nokta)
						|| compressParam.getTip().equals(TokenTipi.Array)
						|| compressParam.getTip().equals(TokenTipi.Karakter)
						|| (compressParam.getTip().equals(TokenTipi.OzelKelime)
								&& (compressParam.getDeger().equals("INTO")
										||compressParam.getDeger().equals("FULL")
										|| compressParam.getDeger().equals("SUBSTR")))) {

					if (indexAfterinto == 1) {
						i++;
						break;
					}
					if (intoReached) {
						indexAfterinto++;
					}
					if (compressParam != null && compressParam.getDeger() != null
							&& compressParam.getDeger().equals("INTO")) {
						intoReached = true;
					}
					System.out.println(compressParam.toString());
					if (i == tokenListesi.size() - 1) {
						endOfTokenListReached = true;
						break;
					}
					i++;
					compressParam = tokenListesi.get(i);
				}

				AbstractToken next = tokenListesi.get(i);
				if (next.getTip().equals(TokenTipi.SatirBasi)) {
					i++;
				}
				if (next.getTip().equals(TokenTipi.OzelKelime)
						&& (next.getDeger().equals(ReservedNaturalKeywords.LEAVING_NO)
								|| next.getDeger().equals(ReservedNaturalKeywords.LEAVING_NO_SPACE)
								|| next.getDeger().equals(ReservedNaturalKeywords.LEAVE_NO))) {
					i++;
				}
				if (next.getTip().equals(TokenTipi.SatirBasi)) {
					i++;
				}

				tokenListesi.add(i, new OzelKelimeToken<String>(ReservedNaturalKeywords.END_COMPRESS, 0, 0, 0,true));
				if (endOfTokenListReached) {
					break;
				}

			}
		}
	}

	private void addEnderForDecide() {
		AbstractToken astValue, astParam;
		boolean endOfTokenListReached = false;
		for (int i = 0; i < tokenListesi.size() - 2; i++) {

			astValue = tokenListesi.get(i);

			if (astValue.getTip().equals(TokenTipi.OzelKelime) && astValue.getDeger() != null
					&& astValue.getDeger().equals(ReservedNaturalKeywords.DECIDE_ON)) { // DECIDE_ON
				// varsa

				if (i == tokenListesi.size() - 1) {
					endOfTokenListReached = true;
					break;
				}
				i++;
				astParam = tokenListesi.get(i);

				while (true) {

					if (astParam.getTip().equals(TokenTipi.OzelKelime)) {
						if (astParam.getDeger().equals("VALUE")) {
							break;
						}
					}
					i++;
					logger.debug("i=" + i + "  tokenListesi.get(i)=" + tokenListesi.get(i));
					astParam = tokenListesi.get(i);
				}

				tokenListesi.add(i, new OzelKelimeToken<String>(ReservedNaturalKeywords.END_DECIDE_ON, 0, 0, 0,true));
				if (endOfTokenListReached) {
					break;
				}

			}
		}
	}

	/*
	 * private void addEnderForValue() { AbstractToken astValue, astParam;
	 * boolean endOfTokenListReached = false; for (int i = 0; i <
	 * tokenListesi.size() - 2; i++) {
	 * 
	 * astValue = tokenListesi.get(i);
	 * 
	 * if (astValue.getTip().equals(TokenTipi.OzelKelime) && astValue.getDeger()
	 * != null && astValue.getDeger().equals(ReservedNaturalKeywords.VALUE)) {
	 * // VALUE // varsa
	 * 
	 * if (i == tokenListesi.size() - 1) { endOfTokenListReached = true; break;
	 * } i++; astParam = tokenListesi.get(i);
	 * 
	 * while (true) {
	 * 
	 * if (astParam.getTip().equals(TokenTipi.OzelKelime)) { if
	 * (astParam.getDeger().equals("VALUE") ||
	 * astParam.getDeger().equals("NONE") ||
	 * astParam.getDeger().equals("END_DECIDE")) { break; } } i++;
	 * logger.debug("i="+i+"  tokenListesi.get(i)="+tokenListesi.get(i));
	 * astParam = tokenListesi.get(i); }
	 * 
	 * tokenListesi.add(i, new
	 * OzelKelimeToken<String>(ReservedNaturalKeywords.END_VALUE, 0, 0, 0)); if
	 * (endOfTokenListReached) { break; }
	 * 
	 * } } }
	 * 
	 * private void addEnderForNone() { AbstractToken astValue, astParam;
	 * boolean endOfTokenListReached = false; for (int i = 0; i <
	 * tokenListesi.size() - 2; i++) {
	 * 
	 * astValue = tokenListesi.get(i);
	 * 
	 * if (astValue.getTip().equals(TokenTipi.OzelKelime) && astValue.getDeger()
	 * != null && astValue.getDeger().equals(ReservedNaturalKeywords.NONE)) { //
	 * VALUE // varsa
	 * 
	 * if (i == tokenListesi.size() - 1) { endOfTokenListReached = true; break;
	 * } i++; astParam = tokenListesi.get(i);
	 * 
	 * while (true) {
	 * 
	 * if (astParam.getTip().equals(TokenTipi.OzelKelime)&&astParam.getDeger().
	 * equals("END-DECIDE")) { break; } i++;
	 * logger.debug("i="+i+"  tokenListesi.get(i)="+tokenListesi.get(i));
	 * astParam = tokenListesi.get(i); }
	 * 
	 * tokenListesi.add(i, new
	 * OzelKelimeToken<String>(ReservedNaturalKeywords.END_NONE, 0, 0, 0)); if
	 * (endOfTokenListReached) { break; }
	 * 
	 * } } }
	 */

	private void processNumberStartingNames() {
		AbstractToken<String> joinedItemName;
		String name;
		Double startNumber;
		for (int i = 0; i < tokenListesi.size(); i++) {
			if (tokenListesi.get(i).getTip().equals(TokenTipi.Sayi)
					&& tokenListesi.get(i + 1).getTip().equals(TokenTipi.Karakter)
					&& tokenListesi.get(i + 2).getTip().equals(TokenTipi.Kelime)
					&& tokenListesi.get(i + 1).getDeger().equals('-')) {
				startNumber = (Double) tokenListesi.get(i).getDeger();
				name = "F" + startNumber.intValue() + "-" + tokenListesi.get(i + 2).getDeger().toString();
				joinedItemName = new KelimeToken<String>(name, tokenListesi.get(i).getSatirNumarasi(), 0, 0);
				tokenListesi.set(i, joinedItemName);
				tokenListesi.get(i + 1).setTip(TokenTipi.OncekiKelimeyleBirlesen);
				tokenListesi.get(i + 2).setTip(TokenTipi.OncekiKelimeyleBirlesen);
			}
		}

		for (int i = tokenListesi.size() - 1; i >= 0; i--) {
			if (tokenListesi.get(i).getTip().equals(TokenTipi.OncekiKelimeyleBirlesen)) {
				tokenListesi.remove(i);
			}
		}
	}

	// MAP2.MUSNO1 (AD=ODL ) --> MAP2.MUSNO1
	// MUSNO1 (AD=ODL ) --> MUSNO1
	// *S**INPUT (AD=MI'.') -->
	
	//TOPLAMLAR(1) (EM=ZZZZZ,ZZZ,ZZZ,ZZZ.99) 5X
	private void setAd_EM_IP_Parameters() {

		AbstractToken curToken, parantezOpenToken, adToken, equalsToken, inputADParameters, curParameter;

		StringBuffer parameters;
		
		mergeMITWithTire();

		for (int index = 0; index < tokenListesi.size() - 3; index++) {
			try {
				parameters = new StringBuffer();
				// /38T
				curToken = tokenListesi.get(index);
				parantezOpenToken = tokenListesi.get(index + 1);
				adToken = tokenListesi.get(index + 2);
				equalsToken = tokenListesi.get(index + 3);

				if ((curToken.isKelime() || curToken.isArray()|| curToken.isKarakter(')')
						|| (curToken.isOzelKelime()))
						&& parantezOpenToken.isKarakter('(') //Oncesinde array indexi olabilir.
						&& equalsToken.isKarakter('=')
						&& adToken.isADParameters()) {

					inputADParameters=new KelimeToken<>(adToken.getDeger().toString(),0,0,0);
					inputADParameters.setInputParameters(true);
					tokenListesi.remove(index + 1); // (
					tokenListesi.remove(index + 1); // AD
					tokenListesi.remove(index + 1); // =
				
					do {
						
						curParameter = tokenListesi.get(index + 1);
						tokenListesi.remove(index + 1);

						if((curParameter.isKarakter(')'))){
							break;
						}else{
							parameters.append(curParameter.getDeger().toString());
						}

				
					} while (true);

					inputADParameters.setDeger(parameters.toString());
					
					AbstractToken tokenBeforeParantezOpen;
					
					if(adToken.isADParameters()){
						if(curToken.isKarakter(')')){
							tokenBeforeParantezOpen = getWordBeforeOpenParantez(index);
							if(tokenBeforeParantezOpen!=null){

								curToken =tokenBeforeParantezOpen;
							}
						}
						curToken.setInputADParameters(inputADParameters);
						
					}
					
					
					//*S**MOVE ( AD = I ) TO CV1 
					if(curToken.isOzelKelime(ReservedNaturalKeywords.MOVE)&& tokenListesi.get(index+1).isOzelKelime("TO")){
						adToken.setConstantVariableWithQuota(true);
						tokenListesi.add(index+1, inputADParameters);
					}

					
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
		
	}
	
	private AbstractToken getWordBeforeOpenParantez(int index) {
		
		try {
			AbstractToken curToken;
			int index2;
			for(index2=index;index2>0; index2--){
				curToken=tokenListesi.get(index2);
				if(curToken.isKarakter('(')){
					break;
				}
			}
			return tokenListesi.get(index2-1);
		} catch (Exception e) {
			return null;
		}
	}

	// MIT'_' --> MIT_
	private void mergeMITWithTire() {
		
		AbstractToken mitToken, tireToken;
		
		for (int index = 0; index < tokenListesi.size() - 1; index++) {
			mitToken=tokenListesi.get(index);
			tireToken=tokenListesi.get(index+1);
			logger.debug("MITTOKEN:"+mitToken);
			if(mitToken.isKelime("MIT") && tireToken.isKelime("_")){
				mitToken.setDeger("MIT_");
				tokenListesi.remove(index+1);
			}
			
		}
		
	}

	

	private void removeJoinedCauseOfKeyValueTokens() {
		for (int i = tokenListesi.size() - 1; i >= 0; i--) {
			if (tokenListesi.get(i).getTip().equals(TokenTipi.KeyValueRemaining)) {
				tokenListesi.remove(i);
			}
		}
	}

	private void setKeyValueKeywords() {
		int tokenPosition = 0;
		AbstractToken current;
		AbstractToken next;
		Iterator<AbstractToken> it = tokenListesi.iterator();
		KeyValueOzelKelimeToken newCreatedKeyValueToken;

		String currentDeger;
		String nextDeger;
		Double nextDegerAsDouble;

		boolean nextIsDot = false;
		current = it.next();
		next = it.next();
		if (next.getTip().equals(TokenTipi.Nokta)) {
			nextIsDot = true;
			next = it.next();
		}

		KeyValueOzelKelimelerNatural keyValueOzelKelimeler = new KeyValueOzelKelimelerNatural();
		while (it.hasNext()) {

			logger.debug(current.getDeger() + " " + next.getDeger());
			logger.debug("");
			if ((current.getDeger() instanceof String) && (next.getDeger() instanceof String) && !current.isConstantVariableWithQuota()) {

				currentDeger = (String) current.getDeger();
				nextDeger = (String) next.getDeger();

				
				for (KeyValueOzelKelimelerNatural.KeyValueKeyword keyValueKeyword : keyValueOzelKelimeler.keyValueKeywords) {
					if (currentDeger.equals(keyValueKeyword.getKey())) {

						newCreatedKeyValueToken = new KeyValueOzelKelimeToken(currentDeger, nextDeger,
								currentTokenizer.lineno(), current.getSatirNumarasi(),
								current.getSatirdakiTokenSirasi());
						newCreatedKeyValueToken.setPojoVariable(next.isPojoVariable());
						newCreatedKeyValueToken.setLocalVariable(next.isLocalVariable());

						if (nextIsDot) {

							tokenListesi.set(tokenPosition, newCreatedKeyValueToken);
							tokenListesi.get(tokenPosition + 1).setTip(TokenTipi.KeyValueRemaining);
							tokenListesi.get(tokenPosition + 2).setTip(TokenTipi.KeyValueRemaining);

						} else {

							tokenListesi.set(tokenPosition, newCreatedKeyValueToken);
							tokenListesi.get(tokenPosition + 1).setTip(TokenTipi.KeyValueRemaining);

						}
					}

				}
			} else if ((current.getDeger() instanceof String) && (next.getDeger() instanceof Double)) {

				currentDeger = (String) current.getDeger();
				nextDegerAsDouble = (Double) next.getDeger();

				for (KeyValueOzelKelimelerNatural.KeyValueKeyword keyValueKeyword : keyValueOzelKelimeler.keyValueKeywords) {
					if (currentDeger.equals(keyValueKeyword.getKey())) {
						newCreatedKeyValueToken = new KeyValueOzelKelimeToken(currentDeger,
								nextDegerAsDouble.intValue(), currentTokenizer.lineno(), current.getSatirNumarasi(),
								current.getSatirdakiTokenSirasi());

						if (nextIsDot) {
							tokenListesi.set(tokenPosition, newCreatedKeyValueToken);
							tokenListesi.get(tokenPosition + 1).setTip(TokenTipi.KeyValueRemaining);
							tokenListesi.get(tokenPosition + 1).setTip(TokenTipi.KeyValueRemaining);
						} else {
							tokenListesi.set(tokenPosition, newCreatedKeyValueToken);
							tokenListesi.get(tokenPosition + 1).setTip(TokenTipi.KeyValueRemaining);
						}
					}
				}
			}

			current = next;
			next = it.next();
			tokenPosition++;
			nextIsDot = false; // Resetliyoruz.

			if (next.getTip().equals(TokenTipi.Nokta)) { // PROGRAM-ID. VLMOKU1.
															// İkinci noktayi
															// atlamak icin.
				next = it.next();
				nextIsDot = true;
				tokenPosition++;
			}

		}

		removeJoinedCauseOfKeyValueTokens();
	}

	private void joinKeywordsWithSpaces() {
		joinFourKeywordsWithSpaces();// Önce bu olmalı. Sonra joinThree olmalı.
		joinThreeKeywordsWithSpaces(); // Önce bu olmalı. Sonra joinTwo olmalı.
		removeJoinedWithPreviousTokens();
		joinTwoKeywordsWithSpaces();
		removeJoinedWithPreviousTokens();
	}

	/*
	 * ASSIGN TO--> ASSIGN_TO RECORD KEY IS ORGANIZATION IS -->ORGANIZATION_IS
	 */
	private void joinTwoKeywordsWithSpaces() {
		int tokenPosition = 0;
		AbstractToken current;
		AbstractToken next;
		Iterator<AbstractToken> it = tokenListesi.iterator();

		String currentDeger = "";
		String nextDeger;

		next = it.next();

		IkiliOzelKelimelerNatural ikiliOzelKelimeler = new IkiliOzelKelimelerNatural();
		do {
			current = next;
			next = it.next();

			if ((current.getDeger() instanceof String) && (next.getDeger() instanceof String)) {

				currentDeger = (String) current.getDeger();
				nextDeger = (String) next.getDeger();
				logger.debug("Ikili Kelime Current: " + currentDeger);
				logger.debug("Ikili Kelime Next: " + nextDeger);

				for (IkiliOzelKelimelerNatural.DoubleWordKeyword doubleKeywod : ikiliOzelKelimeler.ikiliKelimeler) {
					if (currentDeger.equals(doubleKeywod.getFirstKeyword())
							&& nextDeger.equals(doubleKeywod.getSecondKeyword())) {
						tokenListesi.get(tokenPosition)
								.setDeger(doubleKeywod.getFirstKeyword() + "_" + doubleKeywod.getSecondKeyword());
						// tokenListesi.get(tokenPosition).setTip(TokenTipi.OzelKelime);
						tokenListesi.get(tokenPosition + 1).setTip(TokenTipi.OncekiKelimeyleBirlesen);
					}

				}
			}
			tokenPosition++;

			if (currentDeger.equals("DATA")) {
				boolean debug;
				logger.debug("test");
			}

		} while (it.hasNext());

	}

	/*
	 * ACCESS MODE IS --> ACCESS_MODE_IS FILE STATUS IS
	 */
	private void joinThreeKeywordsWithSpaces() {
		int tokenPosition = 0;
		AbstractToken current;
		AbstractToken next;
		AbstractToken nextOfNext;
		Iterator<AbstractToken> it = tokenListesi.iterator();

		String currentDeger;
		String nextDeger;
		String nextNextDeger;

		current = it.next();
		next = it.next();
		nextOfNext = it.next();

		UcluOzelKelimelerNatural ucluOzelKelimeler = new UcluOzelKelimelerNatural();
		while (it.hasNext()) {

			if ((current.getDeger() instanceof String) && (next.getDeger() instanceof String)
					&& (nextOfNext.getDeger() instanceof String)) {
				currentDeger = (String) current.getDeger();
				nextDeger = (String) next.getDeger();
				nextNextDeger = (String) nextOfNext.getDeger();

				for (UcluOzelKelimelerNatural.TripleWordKeyword tripleKeyword : ucluOzelKelimeler.ucluKelimeler) {
					if (currentDeger.equals(tripleKeyword.getFirstKeyword())
							&& nextDeger.equals(tripleKeyword.getSecondKeyword())
							&& nextNextDeger.equals(tripleKeyword.getThirdKeyword())) {
						tokenListesi.get(tokenPosition).setDeger(tripleKeyword.getFirstKeyword() + "_"
								+ tripleKeyword.getSecondKeyword() + "_" + tripleKeyword.getThirdKeyword());
						// tokenListesi.get(tokenPosition).setTip(TokenTipi.OzelKelime);
						// //OZelKelime ise zaten önceden de özel kelimedir. Ama
						// comment içinde ise özel kelime olmamalı. Bu yüzden bu
						// satir kaldırıldi.
						tokenListesi.get(tokenPosition + 1).setTip(TokenTipi.OncekiKelimeyleBirlesen);
						tokenListesi.get(tokenPosition + 2).setTip(TokenTipi.OncekiKelimeyleBirlesen);
					}
				}
			}
			current = next;
			next = nextOfNext;
			nextOfNext = it.next();
			logger.debug("current:" + current);
			tokenPosition++;
		}

	}

	/*
	 * ACCESS MODE IS --> ACCESS_MODE_IS FILE STATUS IS
	 */
	private void joinFourKeywordsWithSpaces() {
		int tokenPosition = 0;
		AbstractToken first;
		AbstractToken second;
		AbstractToken third;
		AbstractToken fourth;
		Iterator<AbstractToken> it = tokenListesi.iterator();

		String firstDeger;
		String secDeger;
		String thirdDeger;
		String furthDeger;

		first = it.next();
		second = it.next();
		third = it.next();
		fourth = it.next();

		DortluOzelKelimelerNatural dortluOzelKelimeler = new DortluOzelKelimelerNatural();
		while (it.hasNext()) {

			if ((first.getDeger() instanceof String) && (second.getDeger() instanceof String)
					&& (third.getDeger() instanceof String) && (fourth.getDeger() instanceof String)) {
				firstDeger = (String) first.getDeger();
				secDeger = (String) second.getDeger();
				thirdDeger = (String) third.getDeger();
				furthDeger = (String) fourth.getDeger();

				for (DortluOzelKelimelerNatural.FourWordKeyword fourthKeyword : dortluOzelKelimeler.dortluKelimeler) {
					if (firstDeger.equals(fourthKeyword.getFirstKeyword())
							&& secDeger.equals(fourthKeyword.getSecondKeyword())
							&& thirdDeger.equals(fourthKeyword.getThirdKeyword())
							&& furthDeger.equals(fourthKeyword.getFourthKeyword())) {
						tokenListesi.get(tokenPosition)
								.setDeger(fourthKeyword.getFirstKeyword() + "_" + fourthKeyword.getSecondKeyword() + "_"
										+ fourthKeyword.getThirdKeyword() + "_" + fourthKeyword.getFourthKeyword());
						// tokenListesi.get(tokenPosition).setTip(TokenTipi.OzelKelime);
						tokenListesi.get(tokenPosition + 1).setTip(TokenTipi.OncekiKelimeyleBirlesen);
						tokenListesi.get(tokenPosition + 2).setTip(TokenTipi.OncekiKelimeyleBirlesen);
						tokenListesi.get(tokenPosition + 3).setTip(TokenTipi.OncekiKelimeyleBirlesen);
					}
				}
			}
			first = second;
			second = third;
			third = fourth;
			fourth = it.next();
			tokenPosition++;
		}

	}

	private void removeJoinedWithPreviousTokens() {
		for (int i = tokenListesi.size() - 1; i >= 0; i--) {
			if (tokenListesi.get(i).getTip().equals(TokenTipi.OncekiKelimeyleBirlesen)) {
				tokenListesi.remove(i);
			}
		}
	}

	private void removeFirstFourChar() {
		if (ConverterConfiguration.customer.equals("MB")) {
			removeFirstFourCharForMB();
			return;
		}
		// Line Basindakini Rezerve Yapar

		AbstractToken astPrevious = null;
		AbstractToken astCurrent = null;

		Iterator<AbstractToken> it = tokenListesi.iterator();

		astPrevious = it.next();
		astCurrent = it.next();
		astPrevious.setTip(TokenTipi.SatirNumaralariIcinRezerve); // İlk Satırın
																	// başındakini
																	// rezerve
																	// set eder.
		astCurrent.setTip(TokenTipi.SatirNumaralariIcinRezerve);

		astPrevious = astCurrent;
		astCurrent = it.next();
		astCurrent.setTip(TokenTipi.SatirNumaralariIcinRezerve);

		astPrevious = astCurrent;
		astCurrent = it.next();
		astCurrent.setTip(TokenTipi.SatirNumaralariIcinRezerve);

		while (it.hasNext()) {
			logger.debug("***************************");
			logger.debug("astPrevious:" + astPrevious);
			logger.debug("astAtLineStart:" + astCurrent);

			if (astPrevious.getTip().equals(TokenTipi.SatirBasi)) {
				astCurrent.setTip(TokenTipi.SatirNumaralariIcinRezerve);

				// İlk dort karakter rezerve işaretlenir.
				astCurrent = it.next();
				astCurrent.setTip(TokenTipi.SatirNumaralariIcinRezerve);

				astCurrent = it.next();
				astCurrent.setTip(TokenTipi.SatirNumaralariIcinRezerve);

				astCurrent = it.next();
				astCurrent.setTip(TokenTipi.SatirNumaralariIcinRezerve);

				logger.debug("Change Tip:" + astCurrent);
			}
			logger.debug("***************************");
			astPrevious = astCurrent;
			astCurrent = it.next();
		}
		;

		removeReservedLineNumberTokens();

	}

	private void removeFirstFourCharForMB() {
		// Line Basindakini Rezerve Yapar

		AbstractToken astPrevious = null;
		AbstractToken astCurrent = null;

		Iterator<AbstractToken> it = tokenListesi.iterator();

		astPrevious = it.next();
		astCurrent = it.next();
		astPrevious.setTip(TokenTipi.SatirNumaralariIcinRezerve); // İlk Satırın
																	// başındakini
																	// rezerve
																	// set eder.

		while (it.hasNext()) {
			logger.debug("***************************");
			logger.debug("astPrevious:" + astPrevious);
			logger.debug("astAtLineStart:" + astCurrent);

			if (astPrevious.getTip().equals(TokenTipi.SatirBasi)) {
				astCurrent.setTip(TokenTipi.SatirNumaralariIcinRezerve);

				logger.debug("Change Tip:" + astCurrent);
			}
			logger.debug("***************************");
			astPrevious = astCurrent;
			astCurrent = it.next();
		}
		;

		removeReservedLineNumberTokens();

	}

	private void removeReservedLineNumberTokens() {
		if (!ConverterConfiguration.RESERVERDNUMBERS) {
			return;
		}
		for (int i = tokenListesi.size() - 1; i >= 0; i--) {
			if (tokenListesi.get(i).getTip().equals(TokenTipi.SatirNumaralariIcinRezerve)) {
				tokenListesi.remove(i);
			}
		}
	}

	private void printTokenList() {
		logger.debug("*****************************************************************");
		logger.debug("*****************************************************************");
		logger.debug("*****************************************************************");
		logger.debug("*****************************************************************");
		logger.debug("TOKEN LISTESI BAŞLANGIÇ");
		for (AbstractToken ast : tokenListesi) {
			logger.debug(ast.toString());
		}
		logger.debug("TOKEN LISTESI SON    Size:" + tokenListesi.size());
		logger.debug("*****************************************************************");
		logger.debug("*****************************************************************");
		logger.debug("*****************************************************************");
		logger.debug("*****************************************************************");
	}

	// (Paragrafın İsmi yazılmamışsa)Procedure Div den HEMEN sonra özel kelime
	// varsa PARAGRAPH MAIN satırını kelimesini ekler. MOVE --> PARAGRAGH_MAIN
	// MOVE
	private void addParagraghMainKeyword() {
		int tokenPosition = 0;
		boolean addMainParagraphKeyword = false;
		AbstractToken ast = null;
		AbstractToken sonrakiKelime = null;
		Iterator<AbstractToken> it = tokenListesi.iterator();

		while (it.hasNext()) {
			ast = it.next();
			tokenPosition++;
			if (ast.getDeger() != null && ast.getDeger().equals(ReservedNaturalKeywords.END_DEFINE)) {

				while (it.hasNext()) {
					sonrakiKelime = it.next();
					tokenPosition++;
					if (!sonrakiKelime.getTip().equals(TokenTipi.SatirBasi)) {
						break;
					}
				}

				addMainParagraphKeyword = true;
				break;
			}
		}
		;

		tokenPosition--;
		if (addMainParagraphKeyword) {
			tokenListesi.add(tokenPosition, new OzelKelimeToken("MAIN_START", ast.getSatirNumarasi(), 0, 0,true));
			tokenListesi.add(new SatirBasiToken(0,""));
		}

	}

	// Oncesi ve sonrasi satirbaşı ise ve özel Kelime değilse paragraf ekle
	private void addParagraghKeyword() {
		AbstractToken astPrevious = null;
		AbstractToken astCurrent = null;
		AbstractToken astNext = null;

		boolean inProcedureDiv = false;
		Iterator<AbstractToken> it = tokenListesi.iterator();

		astPrevious = it.next();
		astCurrent = it.next();
		astNext = it.next();

		while (it.hasNext()) {
			logger.debug("***************************");
			logger.debug("astPrevious:" + astPrevious);
			logger.debug("astCurrent:" + astCurrent);
			logger.debug("astNext:" + astNext);

			if (astPrevious.getDeger() != null
					&& astPrevious.getDeger().equals(ReservedCobolKeywords.PROCEDURE_DIVISION)) {
				inProcedureDiv = true;
			}

			if (astPrevious.getTip().equals(TokenTipi.SatirBasi) && astCurrent.getTip().equals(TokenTipi.Kelime)
					&& (astNext.getTip().equals(TokenTipi.SatirBasi) || astNext.getTip().equals(TokenTipi.Nokta))
					&& inProcedureDiv) {
				astCurrent.setTip(TokenTipi.ParagraphToken);
			}
			astPrevious = astCurrent;
			astCurrent = astNext;
			astNext = it.next();
		}
		;

		for (int i = 0; i < tokenListesi.size(); i++) {
			if (tokenListesi.get(i).getTip().equals(TokenTipi.ParagraphToken)) {
				tokenListesi.get(i).setTip(TokenTipi.Kelime);
				tokenListesi.add(i, new OzelKelimeToken("PARAGRAPH", 0, 0, 0,true));
			}

		}

	}

	public StringBuilder exportLexedData(String fullTokenizeFileName) {
		StringBuilder sb = new StringBuilder();
		KeyValueOzelKelimeToken keyValueToken;
		for (AbstractToken token : tokenListesi) {
			if (token.getTip() == TokenTipi.SatirBasi) {
				logger.debug("");
				sb.append("\n");
			} else if (token.getTip() == TokenTipi.KeyValueOzelKelime) {
				keyValueToken = (KeyValueOzelKelimeToken) token;
				if(token.getColumnNameToken()!=null){
					sb.append(token.getColumnNameToken().getDeger() + "  Uzunluk:" + token.getUzunluk() + " Satir No:" + token.getSatirNumarasi()
						+ " Tipi:" + token.getTip() + " " + keyValueToken.getValue());
				}else{
					sb.append(token.getDeger() + "  Uzunluk:" + token.getUzunluk() + " Satir No:" + token.getSatirNumarasi()
					+ " Tipi:" + token.getTip() + " " + keyValueToken.getValue());
				}
				sb.append("\n");
			} else if (token.getTip() == TokenTipi.Nokta) {
				// Do nothing;
			} else {
				if(token.getColumnNameToken()!=null){
					sb.append(token.getColumnNameToken().getDeger() + "  Uzunluk:" + token.getUzunluk() + " Satir No:" + token.getSatirNumarasi()
						+ " Tipi:" + token.getTip());
				}else{
					sb.append(token.getDeger() + "  Uzunluk:" + token.getUzunluk() + " Satir No:" + token.getSatirNumarasi()
					+ " Tipi:" + token.getTip());
				
				}

				if (token.isPojoVariable()) {
					sb.append(" Pojo");
				}
				if (token.isLocalVariable()) {
					sb.append(" LocalVariable");
				}
				if (token.isSystemVariable()) {
					sb.append(" SystemVariable");
				}
				sb.append("\n");
			}
		}
		try {
			WriteToFile.writeToFile(sb, fullTokenizeFileName);
		} catch (IOException e) {
			logger.debug("", e);
		}
		return sb;

	}

	public void removeDotTokens() {
		for (int i = 0; i < tokenListesi.size() - 1; i++) {
			if (tokenListesi.get(i).getTip().equals(TokenTipi.Nokta)) {
				tokenListesi.remove(i);
			}
		}
	}

	public void tokenizeSourceFile(String sourceFileFullName, String module, String customer) throws Exception {
		try {
			InputStream inputStream = new FileInputStream(sourceFileFullName);
			Reader inputStreamReader = new InputStreamReader(inputStream);
			currentTokenizer = new CustomStreamTokenizer(inputStreamReader);

			// currentTokenizer.commentChar('*');
			currentTokenizer.eolIsSignificant(true);
			currentTokenizer.ordinaryChar('/');
			currentTokenizer.wordChars('_', '_');
			// currentTokenizer.whitespaceChars('.', '.');
			currentTokenizer.ordinaryChar('.');
			// print the stream tokens
			boolean eof = false;
			boolean eol = false;
			boolean inComment = false;
			boolean isPreviousSlash = false;
			int satirdakiTokenSirasi = 0;
			boolean procedureDivReached = false;
			AbstractToken newToken;
			do {
				int token = currentTokenizer.nextToken();
				// logger.debug("Token:" + token + " Sval:" +
				// currentTokenizer.sval + " NVal:" + currentTokenizer.nval);
				switch (token) {
				case CustomStreamTokenizer.TT_EOF:
					logger.debug("End of File encountered.");
					eof = true;
					eol = false;
					isPreviousSlash = false;
					satirdakiTokenSirasi = 0;
					break;
				case CustomStreamTokenizer.TT_EOL:
					logger.debug("End of Line encountered.");
					eol = true;
					inComment = false;
					isPreviousSlash = false;
					tokenListesi.add(new SatirBasiToken(currentTokenizer.lineno(),""));
					satirdakiTokenSirasi = 0;
					break;
				case CustomStreamTokenizer.TT_WORD:
					logger.debug("Word: " + currentTokenizer.sval);
					String tokenVal = currentTokenizer.sval;
					if (tokenVal.equals(ReservedCobolKeywords.PROCEDURE_DIVISION)) {

					}
					if (naturalOzelKelimeler.ozelKelimeler.contains(tokenVal) && !inComment) {
						// Öncesinde diyez varsa ve comment içinde değilse
						// diyezi tokenListe eklemedik. Sonra gelen eleman
						// systemVariable ise systemVariable olarak set ettik.
						OzelKelimeToken ozelKelime = new OzelKelimeToken(tokenVal, currentTokenizer.lineno(), 0,
								satirdakiTokenSirasi);
						tokenListesi.add(ozelKelime);

					} else {
						tokenListesi.add(new KelimeToken(currentTokenizer.sval.replaceAll("-", "_"),
								currentTokenizer.lineno(), 0, satirdakiTokenSirasi));
					}
					satirdakiTokenSirasi++;
					eol = false;
					isPreviousSlash = false;
					break;
				case CustomStreamTokenizer.TT_NUMBER:
					eol = false;
					isPreviousSlash = false;
					tokenListesi.add(
							new SayiToken(currentTokenizer.nval, currentTokenizer.lineno(), 0, satirdakiTokenSirasi));
					satirdakiTokenSirasi++;
					break;
				case CustomStreamTokenizer.DOUBLE_QUOTA:
					eol = false;
					isPreviousSlash = false;
					newToken = new KelimeToken(currentTokenizer.sval, currentTokenizer.lineno(), 0,
							satirdakiTokenSirasi);
					newToken.setConstantVariableWithQuota(true);
					tokenListesi.add(newToken);
					satirdakiTokenSirasi++;
					break;
				case CustomStreamTokenizer.UST_VIRGUL:
					eol = false;
					isPreviousSlash = false;
					newToken = new KelimeToken(currentTokenizer.sval, currentTokenizer.lineno(), 0,
							satirdakiTokenSirasi);
					newToken.setConstantVariableWithQuota(true);
					tokenListesi.add(newToken);
					satirdakiTokenSirasi++;
					break;
				case CustomStreamTokenizer.STAR:
					eol = false;
					tokenListesi
							.add(new KarakterToken((char) token, currentTokenizer.lineno(), 1, satirdakiTokenSirasi));
					if (isPreviousSlash) {
						inComment = true;
					}
					satirdakiTokenSirasi++;
					logger.debug("TokenValue: *");
					break;
				case CustomStreamTokenizer.SLASH:
					eol = false;
					tokenListesi
							.add(new KarakterToken((char) token, currentTokenizer.lineno(), 1, satirdakiTokenSirasi));
					satirdakiTokenSirasi++;
					isPreviousSlash = true;
					// inComment=true;
					break;
				case CustomStreamTokenizer.DOT:
					eol = false;
					tokenListesi.add(new NoktaToken((char) token, currentTokenizer.lineno(), 1, satirdakiTokenSirasi));
					satirdakiTokenSirasi++;
					// inComment=false;
					isPreviousSlash = false;
					break;
				case CustomStreamTokenizer.SHARP:
					eol = false;
					tokenListesi
							.add(new KarakterToken((char) token, currentTokenizer.lineno(), 1, satirdakiTokenSirasi));
					satirdakiTokenSirasi++;
					// inComment=false;
					isPreviousSlash = false;
					break;
				default:
					tokenListesi
							.add(new KarakterToken((char) token, currentTokenizer.lineno(), 1, satirdakiTokenSirasi));
					satirdakiTokenSirasi++;
					isPreviousSlash = false;
					eol = false;
				}
			} while (!eof);
			inputStreamReader.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		setNaturalMode();
		
		replaceGlobalVariables();
		
		setAmpersand();
		
		changeLastDotToEnd(); //Bazen kodu . ile bitiriyorlar. Bu durumda END ile replace ediyoruz.
		
		removePojosSubTableCountToken();

	//	logger.debug(tokenListesi.get(143).getDeger().toString());
		replaceDoublesToInteger();
		
//		logger.debug(tokenListesi.get(143).getDeger().toString());
		
		
		setIncludedFiles(); //includedFiles Listesini doldurur.
		
		loadTableColumnReferanses(customer,module); //Include edilen using dosyalarından columnreferans degerlerini toplayıp NaturalLExing in columnReferansına ekler.
		
		loadIncludedFields(customer,module);  //Include edilen dosyalardaki fieldleri includedFieldList te toplar.
		
		loadRedefinedTableColumns(customer,module); //Include edilen using dosyalarından columnreferans degerlerini toplayıp NaturalLExing in columnReferansına ekler.
				
		//Include filename i onune ekler.
		addIncludeFileNameForVariablesDefinedInIncludeFile();
		
		//SAdece kolon ismi verilenler için tablo ismini onune ekler.
		addTableNameForColumnsWithoutTable();
	
		setRedefinerOfColumn();
		// setReservedLineNumberTokens();

		removeFirstFourChar();
		//YTLDUZ PROGRAM YAPISI DOĞRU DEĞİLDİ DÜZELTİLDİ TEK MODULKE İÇİN YAPILDI CEM
//		if(ConversionLogModel.getInstance().getMode().equals(NaturalMode.REPORTING)){
//			structureCorrection();
//		}
		
		ViewManagerFactory.getInstance().setTypeNameOfViews(tokenListesi);
		
		SysnonymManagerFactory.getInstance().setSynonymsRealTableName(tokenListesi);
	
		addSchemaNameIfThereIsnt();

		changeKeywordsWhichNotUsedAsKeyword();

		

		joinKeywordsWithSpaces(); // Remove da icinde.

		setAllElementsOfArrayFlag(); // RESET MAP_DIZISI.D_SECIM(*) --> RESET
										// MAP_DIZISI.D_SECIM yapar ve D_SECIM
										// in flagini işaretler
		
		controlDiyezToken();
		
		setViewOfColumns();
		
		controlPojo();

		setAllElementsOfPojoFlag();

		controlStarAndDiyezToken();

		operateEditedKeyword();

		operateMaskKeyword();

		controlAndSetLineNumber();

		controlAndSetLineNumber2();

		// MAP2.MUSNO1 (AD=ODL ) --> MAP2.MUSNO1
		setAd_EM_IP_Parameters();
		
		setVal();
		
		controlTwoDimensionArrayParameters();

		controlOneDimensionArrayParameters();
		
		controlRecords(); // 1034 MAP2.MUSNO1:=+MUSNO1 --> Musno1 i Map2 ye
							// eleman olarak ekler.

		controlTableName();// Schema ismini kaldırıp tablo ismi yapıyor. Ama bu yanlış. Schema ismi IKR-MLYT-KOD VIEW OF IKR-MLYT-KOD
			//örneginde olduğu gibi IKR schema  olabilir ama adabas da schema yok. DB2 da var. Dikkat!!!!!!!!!!!!

		//setPojoVariablesForMB();
		
		setSubstrFields(); // SUBSTR(SB_ACIKLAMA,1,79) --> bunu SB_ACIKLAMA ya
							// çevirir. subStringStartIndex ve subStringEndIndex
							// set eder.
		
		removeSatirBasiTokens();
		
		addWithForFind();
		
		addStarterForBecomesEqualToForReportingMode();
		
		addStarterForBecomesEqualToForStructuredMode();
		
		addIfVisualTokens();
		
		addEnders();
	
		setKeyValueKeywords(); // Remove da icinde.

	}

	
	private void structureCorrection() {
		// TODO Auto-generated method stub

		// TODO Auto-generated method stub
		AbstractToken astCurrent = null, astNext = null,astSecond = null,astThird,astFour;
		List<AbstractToken> carryTokenList = null ;
		carryTokenList = new ArrayList<AbstractToken>();
		boolean searchOneOfToken=true;
		boolean searchToken=false;
		int indexOfSubroutine=0;
		int indexTut=0;

		for (int i = indexTut; i < tokenListesi.size() - 5; i++) {
			astCurrent=tokenListesi.get(i);
			astNext=tokenListesi.get(i+1);
			astSecond=tokenListesi.get(i+2);
			astThird=tokenListesi.get(i+3);
			astFour=tokenListesi.get(i+4);
			if(astCurrent.isOzelKelime(ReservedNaturalKeywords.SUBROUTINE) && searchOneOfToken ) {
				
				indexOfSubroutine=astCurrent.getSatirNumarasi();
				searchOneOfToken=false;
			}
			
			if((astCurrent.isOneOfOzelKelime("RETURN") || astCurrent.isOneOfOzelKelime(ReservedNaturalKeywords.END_SUBROUTINE) )) {
				
				while((!astSecond.getDeger().equals("DEFINE") || !astThird.isOzelKelime(ReservedNaturalKeywords.SUBROUTINE) &&  !astSecond.isOzelKelime(ReservedNaturalKeywords.END)) && !astFour.isKelime()) {
					astCurrent=tokenListesi.get(i);
					astNext=tokenListesi.get(i+1);
					
					i++;
					
					if(i>tokenListesi.size()-2) {
						break;
					}
					if(astCurrent.isSatirBasi() && !searchToken ) {
						continue;
					}
					
				if(!astNext.getDeger().equals("DEFINE")) {
						if(tokenListesi.get(i).getDeger().equals("RETURN")) {
							
						}else {
							carryTokenList.add(tokenListesi.get(i));
							
						}
						searchToken=true;
					}else if(astNext.isOneOfKelime(ReservedNaturalKeywords.END)) {
						carryTokenList.add(tokenListesi.get(i+1));
					} else if(astNext.getDeger().equals("DEFINE")) {
						carryTokenList.add(astNext);
						indexTut=astCurrent.getSatirNumarasi();
						break;
					}
					
				}
				
			}else {
				carryTokenList.add(tokenListesi.get(i));
			}
		
		}
		
//		for(int index=carryTokenList.size();index>0;index--) {
//			tokenListesi.add(index, carryTokenList.get(indexOfSubroutine));
//		}
		tokenListesi=carryTokenList;
		
	 logger.debug("GELDİ");
	
	}

	private void controlDiyezToken() {
		// TODO Auto-generated method stub
		AbstractToken astCurrent, astNext, astNexter, astPrevious = null, astControl;

		boolean isGlobalVariable;

		boolean ifFound = false;
		for (int i = 0; i < tokenListesi.size() - 2; i++) {

			astCurrent = tokenListesi.get(i);
			astNext = tokenListesi.get(i + 1);

			if (i > 0) {
				astPrevious = tokenListesi.get(i - 1);
			}

			logger.debug(astCurrent.toString());

		 // # den sonra kelime varsa # i listeden çıkar ve kelimenin local
				// değişken özelliğini true set et.
		 if (astCurrent.isKarakter('#') && astNext.isKarakter('#')) {
				tokenListesi.remove(i);
				tokenListesi.remove(i);
				astNexter = tokenListesi.get(i);
				astNexter.setDeger("DIYEZ_DIYEZ_" + astNexter.getDeger());
				astNexter.setLocalVariable(true);
				// FMM-ISN(*)
			} else if (astCurrent.isKarakter('#')
					&& (astNext.isKelime()|| astNext.isOzelKelime())) {
				tokenListesi.remove(i);
				astNext.setDeger("DIYEZ_" + astNext.getDeger());  // #DO varsa bu DO ozelkelimede olsa normal kelime olarak düsünülmeli.
				astNext.setLocalVariable(true);
				astNext.setTip(TokenTipi.Kelime);
				// FMM-ISN(*)
			}
		}
		
	
	}

	private void setAmpersand() {
		
		AmpersandManager.operateAmpersands(tokenListesi);
		
	}

	private void removeSatirBasiTokens() {
		for(int i=0;i<tokenListesi.size();i++){
			if(tokenListesi.get(i).isSatirBasi()){
				tokenListesi.remove(i);
			}
		}
		
	}

	/*
	 *  *S**    *S**    REINPUT '*** EK GOREVLI ISE * DEGILSE BOS GECMELISINIZ ***'
	 	*S**    PERF60.UNIKD60=UNITEX 	 
	 	*
	 	*	1)	= varsa ve öncesinde token varsa ondan önce de özelKelime(if, accept, else, or, and, EQ, LT, format gibi, ASSIGN) varsa koyma
	 	*
	 	*   2)  = varsa ve öncesinde token varsa ondan önce de karakter varsa (parantez açma gibi ) koyma
	 	*
	 	*	3)  = varsa öncesinde AD EM CV varsa koyma
	 	*   
	 	*   4)  = varsa öncesinde PS,LS,ZP,SG KD IP varsa koyma
	 */
	private void addStarterForBecomesEqualToForReportingMode() {
		
		if(ConversionLogModel.getInstance().getMode().equals(NaturalMode.STRUCTRURED)){
			return;
		}
		AbstractToken astPrevious, astLeft, astEquals, astRight;

		// AddStarter
		for (int i = 1; i < tokenListesi.size() - 2; i++) {

			astPrevious = tokenListesi.get(i - 1);
			logger.debug("astPrevious:"+astPrevious.getDeger().toString());
			if(astPrevious.getSatirNumarasi()==43){
				logger.debug("");
			}
			if(astPrevious.isSatirBasi()){
				i++;
				astPrevious = tokenListesi.get(i - 1);
			}
		
			astLeft = tokenListesi.get(i);
			if(astLeft.isSatirBasi()){
				i++;
				astLeft = tokenListesi.get(i);
			}
			astEquals = tokenListesi.get(i + 1);
			if(astEquals.isSatirBasi()){
				i++;
				astEquals = tokenListesi.get(i+1);
			}
			if(i+2==tokenListesi.size()){
				return;
			}
			astRight = tokenListesi.get(i + 2); // i+1 nokta
			if(astRight.isSatirBasi()){
				i++;
				astRight = tokenListesi.get(i+2);
			}
			
			if(!astEquals.isKarakter('=') ){
				continue;
			}
			
			if(!astLeft.isKelime() && !astLeft.isArray()){
				continue;
			}

			logger.debug("astCurrent:" + astLeft+ " "+astEquals+" "+astRight);
			//1)	= varsa ve öncesinde token varsa ondan önce de özelKelime(if gibi) yada karakter varsa (parantez açma gibi) becomes_equal_to koyma.
			if( astPrevious.isOneOfOzelKelime("IF","ELSE_IF","ACCEPT","ACCEPT_IF","REJECT_IF","OR","AND","EQ","LT","LE","GT","GE","NE","FORMAT","ASSIGN", "BY","FOR","WITH","NOT")){
				continue;
			}
			
			if( astPrevious.isOneOfKelime("WHERE")){
				continue;
			}
			// 2)  = varsa ve öncesinde token varsa ondan önce de karakter varsa (parantez açma gibi ) koyma
			if(astPrevious.isKarakter('(')){
				continue;
			}
			
			//3)  = varsa öncesinde AD EM CV varsa koyma
			if(astLeft.isADParameters()){
				continue;
			}

			//4)  = varsa öncesinde PS,LS,ZP,SG KD IP varsa koyma
			if(astLeft.isOneOfKelime("PS","LS","ZP","SG","KD","IP")){
				continue;
			}
			//3)  = varsa öncesinde AD EM CV varsa koyma
			if(astLeft.isOneOfKelime("PF1","PF2","PF3","PF4","PF5","PF6","PF7","PF8","PF9","PF10","PF11","PF12","PF13","PF14","PF15","PF16","PF17","PF18","PF19","PF20","PF21","PF22","PF23","PF24","CLR")){
				continue;
			}
			logger.debug("Becomes Equal To ekleniyor. SatirNo:"+astLeft.getSatirNumarasi());
			
			tokenListesi.add(i, new OzelKelimeToken<String>(ReservedNaturalKeywords.BECOMES_EQUAL_TO, 0, 0, 0,true));
			i++;
	
		}
		
	}
	
	
	/*
	 *  *S**    *S**    REINPUT '*** EK GOREVLI ISE * DEGILSE BOS GECMELISINIZ ***'
	 	*S**    PERF60.UNIKD60=UNITEX 	 
	 	*
	 	*	1)	= varsa ve öncesinde token varsa ondan önce de özelKelime(if, accept, else, or, and, EQ, LT, format gibi, ASSIGN) varsa koyma
	 	*
	 	*   2)  = varsa ve öncesinde token varsa ondan önce de karakter varsa (parantez açma gibi ) koyma
	 	*
	 	*	3)  = varsa öncesinde AD EM CV varsa koyma
	 	*   
	 	*   4)  = varsa öncesinde PS,LS,ZP,SG KD IP varsa koyma
	 */
	private void addStarterForBecomesEqualToForStructuredMode() {
		
		if(ConversionLogModel.getInstance().getMode().equals(NaturalMode.REPORTING)){
			return;
		}
		AbstractToken astPrevious, astLeft, astDoubleDot,astEquals, astRight;

		// AddStarter
		for (int i = 1; i < tokenListesi.size() - 3; i++) {

			astPrevious = tokenListesi.get(i - 1);
		
			astLeft = tokenListesi.get(i);
			astDoubleDot= tokenListesi.get(i + 1);
			astEquals = tokenListesi.get(i + 2);
			astRight = tokenListesi.get(i + 3); 
			
			logger.debug("astLeft"+astLeft+"   astDoubleDot:"+astDoubleDot+"   astEquals:"+astEquals);
			if(!(astEquals.isKarakter('=') &&astDoubleDot.isKarakter(':'))){
				continue;
			}
			
			if(!astLeft.isKelime() && !astLeft.isArray()){
				continue;
			}

			logger.debug("astCurrent:" + astLeft.getDeger().toString()+ " "+astEquals.getDeger().toString()+" "+astRight.getDeger().toString());
			//1)	= varsa ve öncesinde token varsa ondan önce de özelKelime(if gibi) yada karakter varsa (parantez açma gibi) becomes_equal_to koyma.
			if( astPrevious.isOneOfOzelKelime("IF","ELSE_IF","ACCEPT","OR","AND","EQ","LT","LE","GT","GE","NE","FORMAT","ASSIGN", "BY","FOR","WITH","NOT")){
				continue;
			}
			
			if( astPrevious.isOneOfKelime("WHERE","CLR")){
				continue;
			}
			// 2)  = varsa ve öncesinde token varsa ondan önce de karakter varsa (parantez açma gibi ) koyma
			if(astPrevious.isKarakter('(')){
				continue;
			}
			
			//3)  = varsa öncesinde AD EM CV varsa koyma
			if(astLeft.isADParameters()){
				continue;
			}

			//4)  = varsa öncesinde PS,LS,ZP,SG KD IP varsa koyma
			if(astLeft.isOneOfKelime("PS","LS","ZP","SG","KD","IP")){
				continue;
			}
			logger.debug("Becomes Equal To ekleniyor. SatirNo:"+astLeft.getSatirNumarasi());
			
			tokenListesi.add(i, new OzelKelimeToken<String>(ReservedNaturalKeywords.BECOMES_EQUAL_TO, 0, 0, 0,true));
			i++;
	
		}
		
	}

	private void changeLastDotToEnd() {
		
		if(tokenListesi.get(tokenListesi.size()-1).isNoktaToken()){
			tokenListesi.remove(tokenListesi.size()-1);
			tokenListesi.add(new OzelKelimeToken<String>("END", 0, 0, 0));
		}
		
	}

	private void addEnders() {
		
		addEnderForBecomesEqualTo();
		
		//addThenForFindAndReadStatement();

		addParagraghMainKeyword();
		
		addEnderForCompute();

		addEnderForAssign();

		addEnderForDisplay();

		addEnderForCallNat();

		addEnderForFormat();
		
		addEnderForAmpersand();
		
		addEnderForClose();

		addEnderForDownload();
		
		addEnderForRun();
		
		addEnderForSetKey();

		addEnderForReInput();

		addEnderForInput();

		addEnderForWrite();

		addEnderForCompress();

		addEnderForDecide();
		
		addEndIf();

		// addEnderForValue();

		// addEnderForNone();

		addEnderForReset();

		addEnderForFetch();
		
		addEnderForExamine();

		addEnderForSelect();
		
		
		
	}

	private void setNaturalMode() {
		AbstractToken astCurrent;
		ConversionLogModel.getInstance().setMode(NaturalMode.STRUCTRURED);
		for (int i = 0; i < tokenListesi.size() - 1; i++) {
			astCurrent = tokenListesi.get(i);
			logger.debug(astCurrent.toString());
			if(astCurrent.isOzelKelime(ReservedNaturalKeywords.LOOP) ||
					astCurrent.isOzelKelime(ReservedNaturalKeywords.DOEND)){
				ConversionLogModel.getInstance().setMode(NaturalMode.REPORTING);
				break;
			}else if(astCurrent.isOzelKelime("END-IF") ||
					astCurrent.isOzelKelime("END-FIND")||
					astCurrent.isOzelKelime("END-FOR")){
				ConversionLogModel.getInstance().setMode(NaturalMode.STRUCTRURED);
				break;
			}
		}
		MDC.put("NaturalMode", ConversionLogModel.getInstance().getMode().toString().substring(0,4));
	}

	private void addWithForFind() {
		AbstractToken astCurrent, astWith;
		
		boolean findFound = false, findWith = false;

		for (int i = 0; i < tokenListesi.size() - 1; i++) {

			astCurrent = tokenListesi.get(i);
			
			if (astCurrent.isOzelKelime(ReservedNaturalKeywords.FIND)) { // IF
			
				do{
					i++;
					
					astCurrent = tokenListesi.get(i);
					
					if(astCurrent.isKelime(ReservedNaturalKeywords.NUMBER)){
						i++;
						
						astCurrent = tokenListesi.get(i);
					}
					
					if(astCurrent.getTip().equals(TokenTipi.Kelime)){
						
						i++;
						
						astWith = tokenListesi.get(i);
						if(astWith.isOzelKelime("WITH")){
							break;
						}
						else if(!(astWith.isOzelKelime("WITH"))){
							
							tokenListesi.add(i,new OzelKelimeToken<String>(ReservedNaturalKeywords.WITH, astCurrent.getSatirNumarasi(), 0, 0,true));
							
							break;
						}
						
						else if(astWith.isKarakter('(')){
							
							i=i+2;
							
							astWith = tokenListesi.get(i);
							
							if(!(astWith.isOzelKelime("WITH"))){
							
								tokenListesi.add(i,new OzelKelimeToken<String>(ReservedNaturalKeywords.WITH, astCurrent.getSatirNumarasi(), 0, 0,true));
								
								break;
							
							}
						}
					}
					
				}while(true);
				
			}

		}
		
		
	}


	/**
	 * Redefined kolon isimlerinin redefinerını set eder.
	 * 
	 *  MUL-PREFIX := 'TTOP:KCKGRP' --> MUL_PREFIX in redefinerını set et. 
	 */
	private void setRedefinerOfColumn() {
		AbstractToken current, previous;

		String redefinerName;
		
		RedefinedColumn redefinedColumn;

		
		for (int i = 0; i < tokenListesi.size() - 1; i++) {

			current = tokenListesi.get(i);
			
			logger.debug("current:" + current);

			if (current.getTip().equals(TokenTipi.Kelime)) {

				redefinerName = current.getDeger().toString().replaceAll("-", "_");
			
				
					if (tableColumnRedefinersList.containsKey(redefinerName)) {

						String schemaName;
					
						redefinedColumn = tableColumnRedefinersList.get(redefinerName);
					
						logger.debug(redefinerName + " tokenı için "+redefinedColumn + " Redefiner eklendi" ) ;
						
						current.setRedefinedColumn(redefinedColumn);
						
						current.setColumnRedefiner(true);
						
						current.setPojoVariable(true);
				}

			}
		}
		
	}

	private void loadIncludedFields(String customer, String module) throws Exception {
		module = module.toLowerCase();
		module = module.replaceAll("/seperatedprograms", "");
			Object includedFileObject;
		
			for (String fileName : includeFileList.values()) {
	
				
				try {
					includedFileObject = Class.forName("tr.com."+customer.toLowerCase()+".dal.variables.global." + fileName)
							.newInstance();
				} catch (Exception e) {
					includedFileObject = Class.forName("tr.com."+customer.toLowerCase()+".dal.variables.local." + fileName)
							.newInstance();
				}
	
				Field f[]=includedFileObject.getClass().getDeclaredFields();
				
				for(int i=0;i<f.length;i++){
					
					if(!f[i].getName().equals("tableColumnReferans")){
						
						includeFieldList.put(f[i].getName(), new FieldWrapper(f[i], fileName));
					}
				}
						
			}
		
	}
	
	private void loadTableColumnReferanses(String customer, String module) throws Exception{
		
		module = module.toLowerCase();
		module = module.replaceAll("/seperatedprograms", "");
		
		Object includedFileObject, includedFileObjectInstance;
		
		Method loadDefinitions;
		
		Field field;
		
		
		Class[] cArg = new Class[2];
        cArg[0] = String.class;
        cArg[1] = String.class;
		
		for (String fileName : includeFileList.values()) {

					
				try {
					includedFileObject = Class.forName("tr.com."+customer.toLowerCase()+".dal.variables.global." + fileName)
							.newInstance();
				} catch (Exception e) {
					includedFileObject = Class.forName("tr.com."+customer.toLowerCase()+".dal.variables.local." + fileName)
							.newInstance();
				}

				
				Class parameters;
				loadDefinitions=includedFileObject.getClass().getDeclaredMethod("loadDefinitions",null);
				loadDefinitions.invoke(includedFileObject, new Object[] {});
				
				try {
					field = includedFileObject.getClass().getDeclaredField("tableColumnReferans");
				} catch (Exception e) {
					field = includedFileObject.getClass().getField("tableColumnReferans");
				}

				//includedFileObjectInstance= //IDGLOZLK.getInstance(sessionId, programName);
				

				
				field.setAccessible(true);

				tableColumnReferans.putAll((HashMap) field.get(includedFileObject));

		}
		
	}
	
	private void loadRedefinedTableColumns(String customer, String module) throws Exception{
		
		module = module.toLowerCase();
		
		module = module.replaceAll("/seperatedprograms", "");
		
		Object includedFileObject;
		
		Field field;
		
		for (String fileName : includeFileList.values()) {

					
				try {
					includedFileObject = Class.forName("tr.com."+customer.toLowerCase()+".dal.variables.global." + fileName)
							.newInstance();
				} catch (Exception e) {
					includedFileObject = Class.forName("tr.com."+customer.toLowerCase()+".dal.variables.local." + fileName)
							.newInstance();
				}

				try {
					field = includedFileObject.getClass().getDeclaredField("tableColumnRedefiners");
				} catch (Exception e) {
					field = includedFileObject.getClass().getField("tableColumnRedefiners");
				}

				field.setAccessible(true);

				tableColumnRedefinersList.putAll((HashMap) field.get(includedFileObject));

		}
		
	}

	//C*TBL-ALT-PERIODIC --> TBL-ALT-PERIODIC
	private void removePojosSubTableCountToken() {
		
		AbstractToken current,next, nexterToken = null;
		
		for (int i = 0; i < tokenListesi.size() - 2; i++) {

			current = tokenListesi.get(i);
			
			next=tokenListesi.get(i+1);
			
			nexterToken=tokenListesi.get(i+2);
			
			if(current!=null && current.getDeger()!=null){
				logger.debug("Current "+current.getDeger().toString());
			}

			if (current.getTip().equals(TokenTipi.Kelime) && current.getDeger().equals("C")
					&& next.getTip().equals(TokenTipi.Karakter)&& next.getDeger().equals('*')
					&& nexterToken.getTip().equals(TokenTipi.Kelime)) {
				nexterToken.setPojoSubTableCount(true);
				tokenListesi.remove(i); //remove C
				tokenListesi.remove(i); // remoce *
				logger.debug("Removed C* from the before position of "+ nexterToken.getDeger().toString());
				
			}

		}
		
	}

	private void replaceDoublesToInteger() {
		AbstractToken current,currentNewToken = null;
		long tokenDeger;
		double tokenDegerMod;
		for (int i = 0; i < tokenListesi.size() - 2; i++) {

			current = tokenListesi.get(i);

			if (current.getTip().equals(TokenTipi.Sayi)) {
				tokenDegerMod=Double.valueOf(current.getDeger().toString());
				if(tokenDegerMod%1==0){
					tokenDeger=(long) tokenDegerMod;
					currentNewToken= new SayiToken<Long>(tokenDeger, current.getSatirNumarasi(), current.getUzunluk(), current.getSatirdakiTokenSirasi());
					tokenListesi.set(i, currentNewToken);
				}
	
			}

		}
		
	}

	private void addSchemaNameIfThereIsnt() {
		SchemaNameManager.addSchemaName(tokenListesi);

	}

	
	
/*	private void setOrjinalTableNameOfView() {
		ViewManager.setOrjinalTableNameOfView(tokenListesi);

	}*/

	// *ISN(3460)
	// UPDATE(2000)
	// DELETE(2000)
	// *NUMBER(1880)
	private void controlAndSetLineNumber() {
		boolean endDefineReached = false;
		ArrayToken arrayToken = null;
		AbstractToken astFirst, astSecond, astThird, astFourth;

		for (int i = 0; i < tokenListesi.size() - 4; i++) {

			astFirst = tokenListesi.get(i);
			astSecond = tokenListesi.get(i + 1);
			astThird = tokenListesi.get(i + 2); // i+1 nokta
			astFourth = tokenListesi.get(i + 3); // i+1 nokta

			logger.debug("LİNE NUMBER:" + astFirst + " " + astSecond + " " + astThird + " " + astFourth);

			if (!astFirst.getTip().equals(TokenTipi.Kelime) && !astFirst.getTip().equals(TokenTipi.OzelKelime)) {
				continue;
			}
			if (!astSecond.getTip().equals(TokenTipi.Karakter)) {
				continue;
			}
			if (!astThird.getTip().equals(TokenTipi.Sayi)) {
				continue;
			}
			if (!astFourth.getTip().equals(TokenTipi.Karakter)) {
				continue;
			}
			if ((astFirst.isSystemVariable() || astFirst.getDeger().toString().equals("UPDATE")
					|| astFirst.getDeger().toString().equals("DELETE")) && astThird.getTip().equals(TokenTipi.Sayi)) {

				astFirst.setRefereSatirNumarasi(astThird.getDeger().toString());
				tokenListesi.remove(i + 3);
				tokenListesi.remove(i + 2);
				tokenListesi.remove(i + 1);
				// if (Configuration.isSchemaName(names[0])) {
				// astTable = new KelimeToken(names[1],
				// astSchema.getSatirNumarasi(), 0,
				// astSchema.getSatirdakiTokenSirasi() + 1);
				// astTable.setColumnNameToken(astColumn);
				// astTable.setPojoVariable(true);
				// astSchema.setTableNameToken(astTable);
				// astSchema.setDeger(names[0]);
				// astSchema.setPojoVariable(true);
				// astTable.setSchemaNameToken(astSchema);
				// tokenListesi.remove(i + 2); // Column Token

			}
		}

	}

	// *ISN(READ-DATE.)
	private void controlAndSetLineNumber2() {
		boolean endDefineReached = false;
		ArrayToken arrayToken = null;
		AbstractToken astFirst, astSecond, astThird, astFourth, astFifth;

		for (int i = 0; i < tokenListesi.size() - 4; i++) {

			astFirst = tokenListesi.get(i);
			astSecond = tokenListesi.get(i + 1);
			astThird = tokenListesi.get(i + 2); // i+1 nokta
			astFourth = tokenListesi.get(i + 3); // i+1 nokta
			astFifth = tokenListesi.get(i + 4); // i+1 nokta

			logger.debug(
					"LİNE NUMBER:" + astFirst + " " + astSecond + " " + astThird + " " + astFourth + " " + astFifth);

			if (!astFirst.getTip().equals(TokenTipi.Kelime) && !astFirst.getTip().equals(TokenTipi.OzelKelime)) {
				continue;
			}
			if (!astSecond.getTip().equals(TokenTipi.Karakter)) {
				continue;
			}

			if (!astThird.getTip().equals(TokenTipi.Kelime)) {
				continue;
			}
			if (!astFourth.getTip().equals(TokenTipi.Nokta)) {
				continue;
			}

			if (!astFifth.getTip().equals(TokenTipi.Karakter)) {
				continue;
			}
			if ((astFirst.isSystemVariable() || astFirst.getDeger().toString().equals("UPDATE")
					|| astFirst.getDeger().toString().equals("DELETE")) && astThird.getTip().equals(TokenTipi.Kelime)) {

				astFirst.setRefereSatirNumarasi(astThird.getDeger().toString() + astFourth.getDeger().toString());
				tokenListesi.remove(i + 4);
				tokenListesi.remove(i + 3);
				tokenListesi.remove(i + 2);
				tokenListesi.remove(i + 1);
				// if (Configuration.isSchemaName(names[0])) {
				// astTable = new KelimeToken(names[1],
				// astSchema.getSatirNumarasi(), 0,
				// astSchema.getSatirdakiTokenSirasi() + 1);
				// astTable.setColumnNameToken(astColumn);
				// astTable.setPojoVariable(true);
				// astSchema.setTableNameToken(astTable);
				// astSchema.setDeger(names[0]);
				// astSchema.setPojoVariable(true);
				// astTable.setSchemaNameToken(astSchema);
				// tokenListesi.remove(i + 2); // Column Token

			}
		}

	}

	private void addEnderForAssign() {
		AbstractToken astCompute = null;
		AbstractToken computeParam;
		AbstractToken computeEnder;
		List<Integer> addComputeEnderList = new ArrayList<Integer>();
		boolean endOfTokenListReached = false;
		for (int i = 0; i < tokenListesi.size() - 1; i++) {

			astCompute = tokenListesi.get(i);

			if (astCompute.getTip().equals(TokenTipi.OzelKelime) && astCompute.getDeger() != null
					&& astCompute.getDeger().equals(ReservedNaturalKeywords.ASSIGN)) { // DIPSLAY
																						// varsa

				if (i == tokenListesi.size() - 1) {
					endOfTokenListReached = true;
					break;
				}

				do {
					i++;
					computeParam = tokenListesi.get(i);

					if (i == tokenListesi.size() - 1) {
						endOfTokenListReached = true;
						break;
					}

				} while (!computeParam.getTip().equals(TokenTipi.OzelKelime));

				addComputeEnderList.add(i);
				if (endOfTokenListReached) {
					break;
				}

				i--; // Compute dan hemen sonra bir daha compute gelirse
						// yakalamak icin.
			}

		}

		while (!addComputeEnderList.isEmpty()) {
			computeEnder = new OzelKelimeToken<String>(ReservedNaturalKeywords.END_ASSIGN, astCompute.getSatirNumarasi(), 0, 0,true);
			tokenListesi.add(addComputeEnderList.get(addComputeEnderList.size() - 1), computeEnder);
			addComputeEnderList.remove(addComputeEnderList.size() - 1); // RemoveLast
		}

	}

	/**
	 * 2946 SELECT MAX(ALTSIRA) INTO MAXSIRA FROM IDGIDBS-TESKI WHERE 2948
	 * HESCINSI=NHESCINS AND DOVIZ=PDOVIZ AND MUSNO1=PDMUSNO1(I) 2950 AND
	 * HESNO=PHESNO(I) AND MUSNO2=PDMUSNO2(I) AND 2952 KRX_KOD=PDHESNIT(I) AND
	 * SIRA=PBIS(I) AND VEFAT^='V' 2954 END-SELECT
	 * 
	 * --> SELECT den sonra Select e ait olmayan özel kelimeleri görmediğin
	 * sürece devam et. Yada := görünceye kadar.
	 * 
	 * OZel Kelimeler: AND OR INTO MAX DISTINCT SORTED_BY
	 */
	private void addEnderForSelect() {
		AbstractToken astSelect;
		AbstractToken astNext, astSelectEnder, astDoubleDot, astEquals;
		boolean endOfTokenListReached = false;
		for (int i = 0; i < tokenListesi.size() - 1; i++) {

			astSelect = tokenListesi.get(i);

			if (astSelect.getTip().equals(TokenTipi.OzelKelime) && astSelect.getDeger() != null
					&& astSelect.getDeger().equals(ReservedNaturalKeywords.SELECT)) { // SELECT
																						// varsa

				i++;
				astNext = tokenListesi.get(i);

				while (true) {

					if (astNext.isOzelKelime()) {
						if (!astNext.isOneOfOzelKelime(ReservedNaturalKeywords.AND,
								ReservedNaturalKeywords.OR,
								ReservedNaturalKeywords.INTO,
								ReservedNaturalKeywords.MAX,
								ReservedNaturalKeywords.MIN,
								ReservedNaturalKeywords.SUM,
								ReservedNaturalKeywords.DISTINCT,
								ReservedNaturalKeywords.ORDER_BY,
								ReservedNaturalKeywords.COUNT,
								ReservedNaturalKeywords.VIEW,
								ReservedNaturalKeywords.FROM,
								ReservedNaturalKeywords.SORTED_BY)) {
							break;
						}
					}

					if (tokenListesi.get(i) != null && tokenListesi.get(i).getDeger() != null
							&& tokenListesi.get(i + 1) != null && tokenListesi.get(i + 1).getDeger() != null
							&& tokenListesi.get(i).getDeger().equals(':')
							&& tokenListesi.get(i + 1).getDeger().equals('=')) { // IF
																					// *PF-KEY='PF5'
																					// FILTER:=
																					// -
																					// FILTER
																					// ESCAPE
																					// TOP
																					// END-IF

						// FILTER dan önce konmali
						i--;
						break;

					}
					i++;
					astNext = tokenListesi.get(i);
					System.out.println(astNext.getDeger());
				}

				astSelectEnder = new OzelKelimeToken<String>(ReservedNaturalKeywords.THEN_OF_SELECT, astNext.getSatirNumarasi(), 0, 0,true);
				tokenListesi.add(i, astSelectEnder);
			}
		}

	}

	// MOVE EDITED VFARKMEB (EM=Z,ZZZ,ZZZ,ZZ9.99) TO VFARKMEBA --> MOVE VFARKMEB
	// TO VFARKMEBA
	private void operateEditedKeyword() {

		AbstractToken editedToken, keywordToken, openParantezToken, curToken;

		int index;

		List<AbstractToken> editMaskTokenList;

		for (int i = 0; i < tokenListesi.size() - 5; i++) {
			editedToken = tokenListesi.get(i);
			keywordToken = tokenListesi.get(i + 1);
			openParantezToken = tokenListesi.get(i + 2);
			if (editedToken.getTip().equals(TokenTipi.OzelKelime) && editedToken.getDeger().equals("EDITED")) {

				editMaskTokenList = new ArrayList<AbstractToken>();

				if (openParantezToken.getTip().equals(TokenTipi.Karakter) && openParantezToken.getDeger().equals('(')) { // (
																															// Varsa
					tokenListesi.remove(i); // remove Edited
					tokenListesi.remove(i + 1); // remove Parantez
					tokenListesi.remove(i + 1); // remove EM
					tokenListesi.remove(i + 1); // remove =

					while (true) {
						curToken = tokenListesi.get(i + 1);
						tokenListesi.remove(i + 1);
						if (curToken.getTip().equals(TokenTipi.Karakter) && curToken.getDeger().equals(')')) {
							break;
						} else {
							editMaskTokenList.add(curToken);
						}
					}
				} else { // MOVE EDITED DATE-TEMP8 TO D-ILKTAR(EM=DDMMYYYY)
					tokenListesi.remove(i); // remove Edited
				}
				keywordToken.setEditMaskTokenList(editMaskTokenList);
				keywordToken.setEdited(true);
			}
		}

	}

	// IF MUHVALOR NE MASK (YYYY'-'MM'-'DD) THEN --> IF MUHVALOR NE MASK THEN
	private void operateMaskKeyword() {

		AbstractToken maskedToken, openParantezToken, curToken;

		int index;

		List<AbstractToken> maskTokenList = null;

		for (int i = 0; i < tokenListesi.size() - 2; i++) {
			maskedToken = tokenListesi.get(i);
			openParantezToken = tokenListesi.get(i + 1);
			if (maskedToken.getTip().equals(TokenTipi.OzelKelime) && maskedToken.getDeger().equals("MASK")) {

				maskTokenList = new ArrayList<AbstractToken>();

				if (openParantezToken.getTip().equals(TokenTipi.Karakter) && openParantezToken.getDeger().equals('(')) { // (
																															// Varsa
					tokenListesi.remove(i + 1);
					while (true) {
						curToken = tokenListesi.get(i + 1);
						tokenListesi.remove(i + 1);
						if (curToken.getTip().equals(TokenTipi.Karakter) && curToken.getDeger().equals(')')) {
							break;
						} else {
							maskTokenList.add(curToken);
						}
					}
				}
				maskedToken.setMaskTokenList(maskTokenList);
				maskedToken.setMasked(true);
			}
		}

	}

	private void changeKeywordsWhichNotUsedAsKeyword() {

		// MAP varsa ve önceki kelimesi USING değilse MAPP olarak değiştir.
		AbstractToken previousToken, curToken, newMapToken;

		for (int i = 0; i < tokenListesi.size() - 2; i++) {
			previousToken = tokenListesi.get(i);
			curToken = tokenListesi.get(i + 1);
			// current kelime ozelKelime ise
			if (curToken.getTip().equals(TokenTipi.OzelKelime)) { // Map
				
				if (  previousToken.getDeger()!=null && !previousToken.getDeger().toString().equals(ReservedCobolKeywords.SET)
						&& curToken.getDeger().toString().equals("KEY")) { // Oncesinde
					// USING
					// yoksa
					newMapToken = new KelimeToken(
							curToken.getDeger().toString() + "_"
									+ curToken.getDeger().toString()
											.substring(curToken.getDeger().toString().length() - 1),
							curToken.getSatirNumarasi(), curToken.getUzunluk() + 1, curToken.getSatirdakiTokenSirasi());
					tokenListesi.remove(i + 1);
					tokenListesi.add(i + 1, newMapToken);
				}
				
				
				// varsa
				if (curToken.isOzelKelime("MAP") && !previousToken.isOzelKelime("USING")) { // Oncesinde
					// USING
					// yoksa
					newMapToken = new KelimeToken(
							curToken.getDeger().toString() + "_"
									+ curToken.getDeger().toString()
											.substring(curToken.getDeger().toString().length() - 1),
							curToken.getSatirNumarasi(), curToken.getUzunluk() + 1, curToken.getSatirdakiTokenSirasi());
					tokenListesi.remove(i + 1);
					tokenListesi.add(i + 1, newMapToken);
				}
																	// varsa
				if (!(previousToken.getTip().equals(TokenTipi.OzelKelime))
						&& (previousToken.getDeger().equals("USING"))) { // Oncesinde
					// USING
					// yoksa
					newMapToken = new KelimeToken(
							curToken.getDeger().toString() + "_"
									+ curToken.getDeger().toString()
											.substring(curToken.getDeger().toString().length() - 1),
							curToken.getSatirNumarasi(), curToken.getUzunluk() + 1, curToken.getSatirdakiTokenSirasi());
					tokenListesi.remove(i + 1);
					tokenListesi.add(i + 1, newMapToken);
				}
				if ((previousToken.getTip().equals(TokenTipi.OzelKelime) && (previousToken.getDeger().equals("PERFORM")
						|| previousToken.getDeger().equals("SUBROUTINE")))) { // Oncesinde
																				// USING
																				// yoksa
					newMapToken = new KelimeToken(
							curToken.getDeger().toString() + "_"
									+ curToken.getDeger().toString()
											.substring(curToken.getDeger().toString().length() - 1),
							curToken.getSatirNumarasi(), curToken.getUzunluk() + 1, curToken.getSatirdakiTokenSirasi());
					tokenListesi.remove(i + 1);
					tokenListesi.add(i + 1, newMapToken);
				}
			}
			if ((previousToken.getTip().equals(TokenTipi.OzelKelime))
					&& (previousToken.getDeger().toString().equals("IF")) && curToken.getTip().equals(TokenTipi.OzelKelime) &&  curToken.getDeger().toString().equals("KEY")) { // Oncesinde
				// USING
				// yoksa
				newMapToken = new KelimeToken(
						curToken.getDeger().toString() + "_"
								+ curToken.getDeger().toString()
										.substring(curToken.getDeger().toString().length() - 1),
						curToken.getSatirNumarasi(), curToken.getUzunluk() + 1, curToken.getSatirdakiTokenSirasi());
				tokenListesi.remove(i + 1);
				tokenListesi.add(i + 1, newMapToken);
			}
			
			//INDEX varsa ve önceki kelimesi GIVING değilse (EXAMINE KOD(*) FOR B-GAZETE GIVING INDEX INDEX)
			if(!previousToken.isOzelKelime(ReservedNaturalKeywords.GIVING) && curToken.isOzelKelime("INDEX")){
				newMapToken = new KelimeToken(
						curToken.getDeger().toString() + "_"
								+ curToken.getDeger().toString()
										.substring(curToken.getDeger().toString().length() - 1),
						curToken.getSatirNumarasi(), curToken.getUzunluk() + 1, curToken.getSatirdakiTokenSirasi());
				tokenListesi.set(i+1, newMapToken);
			}
		}

		// onceki kelime SUBROUTINE se ve current kelime ozelKelime ise
		// ozelkeimeyi --normal kelimeye çevir ve değerinin son harfini değere
		// ekle.
		
		//Bütün notları NOT_T yapıyoruz. Sonra Java Condition kısmında bakıp condition içinde NOT_T görürsek NOT yapacağız.
		for (int i = 0; i < tokenListesi.size() - 2; i++) {
			curToken = tokenListesi.get(i);
			
			if(curToken.isOzelKelime("NOT") ||curToken.isKelime("NOT") ){
				curToken.setTip(TokenTipi.Kelime);
				curToken.setDeger("NOTT");
			}
		}



	}

	private void addEnderForFetch() {
		AbstractToken astFetch;
		AbstractToken astParam, astFetchEnder;
		boolean endOfTokenListReached = false;
		for (int i = 0; i < tokenListesi.size() - 1; i++) {

			astFetch = tokenListesi.get(i);

			if (astFetch.getTip().equals(TokenTipi.OzelKelime) && astFetch.getDeger() != null
					&& (astFetch.getDeger().equals(ReservedNaturalKeywords.FETCH)
							|| astFetch.getDeger().equals(ReservedNaturalKeywords.FETCH_RETURN))) { // CLOSE
																									// varsa

				if (i == tokenListesi.size() - 1) {
					endOfTokenListReached = true;
					break;
				}
				i++;
				astParam = tokenListesi.get(i);

				while (!astParam.getTip().equals(TokenTipi.OzelKelime)) {

					if (i == tokenListesi.size() - 1) {
						endOfTokenListReached = true;
						break;
					}
					i++;
					astParam = tokenListesi.get(i);
				}
				astFetchEnder = new OzelKelimeToken<String>(ReservedNaturalKeywords.END_FETCH, astParam.getSatirNumarasi(), 0, 0,true);
				tokenListesi.add(i, astFetchEnder);
			}
		}
	}

	
	private void addEnderForCompute() {
		
		AbstractToken astCurrent;
		
		boolean astFormatReached=false;
	
		for (int i = 0; i < tokenListesi.size() - 1; i++) {

			astCurrent = tokenListesi.get(i);

			if(astFormatReached && astCurrent.isOzelKelime()){
			
				tokenListesi.add(i, new OzelKelimeToken<String>(ReservedNaturalKeywords.END_COMPUTE, astCurrent.getSatirNumarasi(), 0, 0,true));
				
				astFormatReached=false;
			}else if (astCurrent.isOzelKelime(ReservedNaturalKeywords.COMPUTE)||astCurrent.isOzelKelime(ReservedNaturalKeywords.COMPUTE_ROUNDED)) { // DIPSLAY
							// varsa
				astFormatReached=true;
			
			}

		}

	}


	private void addEnderForBecomesEqualTo() {

		AbstractToken astCurrent, astNext, astEnder, astPrevious;

		// AddEnder
		boolean endOfTokenListReached = false;

		for (int i = 0; i < tokenListesi.size() - 2; i++) {

			astCurrent = tokenListesi.get(i);

			logger.debug("astCurrent:" + astCurrent);

			if (astCurrent.getTip().equals(TokenTipi.OzelKelime)
					&& astCurrent.getDeger().equals(ReservedNaturalKeywords.BECOMES_EQUAL_TO)) {

				if (i == tokenListesi.size() - 1) {
					endOfTokenListReached = true;
					break;
				}
				i++;
				astCurrent = tokenListesi.get(i);

				System.out.println(astCurrent);
				while (astCurrent.getTip().equals(TokenTipi.Kelime) || astCurrent.getTip().equals(TokenTipi.Array)
						|| astCurrent.getTip().equals(TokenTipi.SatirBasi)
						|| astCurrent.getTip().equals(TokenTipi.Nokta) || astCurrent.getTip().equals(TokenTipi.Karakter)
						|| astCurrent.getTip().equals(TokenTipi.Sayi)) {

					if (astCurrent.getTip().equals(TokenTipi.SatirBasi)) {
						astPrevious = tokenListesi.get(i - 1);
						astNext = tokenListesi.get(i + 1);
						if (astPrevious.getTip().equals(TokenTipi.Karakter) // Satir
																			// öncesi
																			// son
																			// karakter
																			// +
																			// -
																			// *
																			// gibi
																			// bir
																			// şeyse
																			// becomes
																			// equal
																			// to
																			// devam
																			// ediyordur.
								&& (astPrevious.getDeger().equals('+') || astPrevious.getDeger().equals('-')
										|| astPrevious.getDeger().equals('/') || astPrevious.getDeger().equals('*')
										|| astPrevious.getDeger().equals('='))) {
							// Do nothing
						} else if (astNext.getTip().equals(TokenTipi.Karakter) // Satir
																				// öncesi
																				// son
																				// karakter
																				// +
																				// -
																				// *
																				// gibi
																				// bir
																				// şeyse
																				// becomes
																				// equal
																				// to
																				// devam
																				// ediyordur.
								&& (astNext.getDeger().equals('+') || astNext.getDeger().equals('-')
										|| astNext.getDeger().equals('/') || astNext.getDeger().equals('*'))) {
							// Do nothing
						} else { // Satirbaşı varsa ve öncesi yada sonrası
									// işaret değilse komut bitmelidir.
							break;
						}
					}

					if (i == tokenListesi.size() - 1) {
						endOfTokenListReached = true;
						break;
					}
					i++;
					astCurrent = tokenListesi.get(i);
					System.out.println(astCurrent);

				}

				if (endOfTokenListReached) {
					break;
				}

				astEnder = new OzelKelimeToken<String>(ReservedNaturalKeywords.END_BECOMES_EQUAL_TO, astCurrent.getSatirNumarasi(), 0, 0,true);
				tokenListesi.add(i, astEnder);
			}
		}
		
		//correctIncorrectVisualEndersCauseOfBecomesEqualTo();

	}

	/**
	 * REINPUT  Uzunluk:0 Satir No:111 Tipi:OzelKelime
	*** GUNCELLESTIRMEK ISTEDIGINIZ KAYIT BULUNAMADI ***  Uzunluk:0 Satir No:111 Tipi:Kelime
	
	BECOMES_EQUAL_TO  Uzunluk:0 Satir No:112 Tipi:OzelKelime
	ESKIENDIK  Uzunluk:0 Satir No:112 Tipi:Kelime LocalVariable
	=  Uzunluk:1 Satir No:112 Tipi:Karakter
	PERF38  Uzunluk:0 Satir No:0 Tipi:Kelime Pojo LocalVariable
	END_BECOMES_EQUAL_TO  Uzunluk:0 Satir No:113 Tipi:OzelKelime

	END_REINPUT  Uzunluk:0 Satir No:0 Tipi:OzelKelime
	
	Yukardaki gibi END_BECOMES_EQUAL_TO dan sonra visual Ender varsa Visual Enderı Önceki becomes_equalto nın yerine taşır.
	 */
	private void correctIncorrectVisualEndersCauseOfBecomesEqualTo() {
		
		AbstractToken curToken, nextToken;
		
		int becomesEqualToIndex=0, visualEnderIndex=0;
		
		for (int i = 0; i < tokenListesi.size()-1; i++) {
			
			curToken=tokenListesi.get(i);
			
			nextToken=tokenListesi.get(i+1);
			
			while(nextToken.isSatirBasi()){
				i++;
				
				if(i+1==tokenListesi.size()){
					return;
				}
				
				nextToken=tokenListesi.get(i+1);
			
			}
			
			logger.debug("CurToken:"+curToken.getDeger());
			logger.debug("nextToken:"+nextToken.getDeger());
			
			if(curToken.isOzelKelime(ReservedNaturalKeywords.BECOMES_EQUAL_TO)){
				
				becomesEqualToIndex=i;
			
			}else if(curToken.isOzelKelime(ReservedNaturalKeywords.END_BECOMES_EQUAL_TO) && nextToken.isVisualEnder()){
				
				visualEnderIndex=i+1;
				
				tokenListesi.remove(visualEnderIndex);
				
				tokenListesi.add(becomesEqualToIndex, nextToken);
			}
			
		}
			
		
	}

	private void addIncludeFileNameForVariablesDefinedInIncludeFile() {

		AbstractToken current;

		KelimeToken includeFilename = null;

		String fieldNameForLookup = null;

		Object includedFileObject;
		
		for (int i = 0; i < tokenListesi.size() - 2; i++) {

			current = tokenListesi.get(i);

			if(current.isConstantVariableWithQuota()){
				continue;
			}
			if (current.getTip().equals(TokenTipi.Kelime)) {
				
				logger.debug("Current:"+current.getDeger().toString());
				logger.debug("");
				   for (Map.Entry<String, FieldWrapper> entry : includeFieldList.entrySet()) {
					    String key = entry.getKey();
					    FieldWrapper fWrapper = entry.getValue();
					    
					   logger.debug("key:"+key);
					    if(current.getDeger().equals(fWrapper.getField().getName()) && (!current.isPojoVariable())&& current.isLocalVariable()){
								
					    	
					    	/*if(fWrapper.getFieldOwnerFile().contains("TOPSCRG1")||fWrapper.getFieldOwnerFile().contains("KETLV0G1")){
					    		current.setDeger(fWrapper.getFieldOwnerFile() + ".getInstance()." + current.getDeger());
						    	
					    	}else{
					    		current.setDeger(fWrapper.getFieldOwnerFile() + "." + current.getDeger());
						    }*/
					    	current.setDeger(fWrapper.getFieldOwnerFile() + ".getInstance(sessionId, programName)." + current.getDeger());
					    	current.setIncludedVariable(true);
					    	current.setIncludedVariable(fWrapper.getField());
					    	logger.debug("Add "+ fWrapper.getFieldOwnerFile()+". Before"+current.getDeger());
					    	
					    	if(fWrapper.getField().getType().getSimpleName().contains("Redefined")){
					    		current.setRedefinedVariable(true);
					    	}
					    }
				   }
			}

		}

	}
	

	private void setIncludedFiles() {

		AbstractToken current, usingToken, parameterToken;

		String using;

		for (int i = 0; i < tokenListesi.size() - 3; i++) {

			current = tokenListesi.get(i);

			usingToken = tokenListesi.get(i + 1);

			parameterToken = tokenListesi.get(i + 2);
			
			if(current.getDeger()==null){
				continue;
			}

			if (current.getTip().equals(TokenTipi.OzelKelime) && ( current.getDeger().toString().equals("GLOBAL")|| current.getDeger().toString().equals("LOCAL"))
					&& usingToken.getDeger()!=null && usingToken.getDeger().toString().equals("USING")) {

				using = current.getDeger().toString() + "_" + usingToken.getDeger().toString();

				if (current.getTip().equals(TokenTipi.OzelKelime) && usingToken.getTip().equals(TokenTipi.OzelKelime)
						&& (using.equals(ReservedNaturalKeywords.GLOBAL_USING)
								|| using.equals(ReservedNaturalKeywords.LOCAL_USING))) {

					includeFileList.put(parameterToken.getDeger().toString(), parameterToken.getDeger().toString());
				}

			}
		}
	}

	
	//CTY_AIRPORT --> KET_AIRLINE.CITY_AIRPORT
	public void addTableNameForColumnsWithoutTable() {

		AbstractToken current, previous, next, nexter, nextnexter;

		String columnName;
		
		boolean selectFindReached=false, whereReached=false; 
		
		boolean isDefinitionPart=true;
		
		for (int i = 0; i < tokenListesi.size() - 1; i++) {

			current = tokenListesi.get(i);
			
			next=tokenListesi.get(i+1);
			
			logger.debug(current.getDeger().toString());
			
			if(current.getDeger().toString().equals("DOVIZ")){
				logger.debug(current.toString());
			}
			
			
			if(current.getDeger()!=null && current.getDeger().equals(ReservedNaturalKeywords.END_DEFINE)){
				isDefinitionPart=false;
				continue;
			}
			
			if(isDefinitionPart){
				continue;
			}
			
			if(i>0){
				previous=tokenListesi.get(i-1);
				if(previous.getTip().equals(TokenTipi.Nokta)){
					continue;
				}
			}
			if(current.isKelime("GDASB1")){
				logger.debug("");
			}
			
			if(current.isOzelKelime("SELECT") || current.isOzelKelime("FIND")){  //Select görünce
				selectFindReached=true;
				whereReached=false;
				continue;
			}
			 
			if(current.isOzelKelime("WHERE") || current.isKelime("WHERE")|| current.isOzelKelime("WITH") || current.isKelime("WITH")){  //Where GÖrünce
				whereReached=true; 
				selectFindReached=false;
				continue;
			}

			if(whereReached  && (current.isOzelKelime() &&!current.isConditionJoiner()&!current.isConditionJoinKeyword()) ){  //Where den sonra özel kelime görünce yani where bitince
				selectFindReached=false;
				whereReached=false;
			}
			
			if(selectFindReached && !whereReached){ //Select ile Where arasındaysa yada Find ile Where arasındaysa işlem yapma
				continue;
			}
		
			
			if(next.isConditionOperator() && whereReached ){ //Where icinde ise ve next conditionOperatorse bir şey yapma
				continue;
			}
			
			logger.debug("current:" + current);

			if (current.getTip().equals(TokenTipi.Kelime)) {

				columnName = current.getDeger().toString().replaceAll("-", "_");
			
				
					if (tableColumnReferans.containsKey(columnName)) {

						String schemaName;
					
						if (tableColumnReferans.get(columnName).contains(".")) {
							schemaName = tableColumnReferans.get(columnName).substring(0,
									tableColumnReferans.get(columnName).indexOf('.'));
						} else {
							schemaName = tableColumnReferans.get(columnName);
						}

				
						if(current.isKelime("GDASB1")){
							logger.debug("");
						}
						if(isLocalVariable(current) || current.isConstantVariableWithQuota() || current.isArray() ){
							continue;
						}
						
						current.setPojoVariable(true);
						
						current.setTableNotDefined(true);
						
						String tableNameDeger = tableColumnReferans.get(columnName)
								.substring(tableColumnReferans.get(columnName).indexOf('.') + 1);

						logger.debug(columnName + " kolonu için "+tableNameDeger + " Tablo ismi ekle" ) ;
						
						
						tokenListesi.add(i, new KelimeToken<>(tableNameDeger, current.getSatirNumarasi(), 0, 0));  //Tablo ismini ekle.
						tokenListesi.add(i+1, new NoktaToken<>("."));  //Nokta ekle
						
						i=i+2;
						
				}

			}
		}

	}
	
	private boolean isLocalVariable(AbstractToken controlToken) {
		
		AbstractToken current;
		
		if(controlToken.isPojoVariable() || controlToken.isConstantVariableWithQuota()){
			return false;
		}
		
		for (int i = 0; i < tokenListesi.size() - 1; i++) {

			current = tokenListesi.get(i);
	
			if(current.isOzelKelime(ReservedNaturalKeywords.END_DEFINE)){
				return false;
			}else if(current.isKelime()&& controlToken.isKelime() && current.valueEquals(controlToken)){
				return true;
			}
			
		}
		return false;
	}

	private void setPojoVariablesForMB() {
		
		if(ConversionLogModel.getInstance().getCustomer().equals("THY")){
			return;
		}
		
		AbstractToken lastTableToken = null, currentToken, newGeneratedTableNameToken;
		
		for (int i = 0; i < tokenListesi.size() - 1; i++) {

			currentToken = tokenListesi.get(i);
			
			if(currentToken.isTable()){
				
				lastTableToken=currentToken;
			
			}else if(!currentToken.isSystemVariable() 
						&& !currentToken.isConstantVariableWithQuota()
						&& !currentToken.isGlobalVariable()
						&& !currentToken.isRecordVariable()
						&& !currentToken.isRedefinedVariable()
						&& !currentToken.isRedefinedVariableDimensionToSimple()
						&& !currentToken.isAmpersand()
						&& currentToken.isKelime()
						&& currentToken.isLocalVariable()
						){
				
				if(lastTableToken==null){
					continue;
				}
				newGeneratedTableNameToken=new KelimeToken(lastTableToken.getDeger().toString(), currentToken.getSatirNumarasi(), 0, currentToken.getSatirdakiTokenSirasi());
				
				newGeneratedTableNameToken.setColumnNameToken(currentToken);
				
				newGeneratedTableNameToken.setPojoVariable(true);
				
				logger.debug(currentToken.getDeger().toString()+" kolonuna "+ newGeneratedTableNameToken.getDeger().toString()+" tablo ismi eklendi");
				
				tokenListesi.set(i, newGeneratedTableNameToken);
			}
		}
	}
	
	/*
	 * VIEW OF gorursen Sonra 1 gorunceye kadar 
	 * tum kelimeleri pojoVariable olarak set et.
	 * Bunları bir listeye at.
	 * Tüm tokenlarda bu listedeki elemandan görürsen pojovariable true set et.
	 */
	private void setViewOfColumns() {
		
		List<AbstractToken> viewOfPojos=new ArrayList<>();
		
		int index=1;
		AbstractToken previousToken = null,curToken, viewOfNameToken = null, columnDefinitionToken = null; 
		
		KelimeToken pojo;
		
		boolean inViewOfState = false;
		do {
			curToken=tokenListesi.get(index);
			
			if(curToken.isOzelKelime(ReservedNaturalKeywords.VIEW_OF) || curToken.isOzelKelime(ReservedNaturalKeywords.VIEW)){
				inViewOfState=true;
				previousToken=tokenListesi.get(index-1);
				index++; //ViewOf tanımını atlamak için kondu PERF30 VIEW OF PERF30
			}else if(inViewOfState){
				if(curToken.isKelime()){
					
					curToken.setPojoVariable(true);
					
					pojo=new KelimeToken<>(previousToken.getDeger(),0,0,0);
					pojo.setColumnNameToken(curToken);
					pojo.setPojoVariable(true);
					
					ViewManagerFactory.getInstance().setTypeNameOfViews(pojo);
					
					tokenListesi.remove(index);
					tokenListesi.add(index,pojo);
					
					viewOfPojos.add(pojo);
					
					logger.debug(curToken.getDeger().toString()+" Pojo olarak set edildi");
				}else if(curToken.isSayi(1)){
					inViewOfState=false;
				}
			}
			
			index++;
			
			
			
		}while(!curToken.isOzelKelime(ReservedNaturalKeywords.END_DEFINE));
		
		String pojoName = null, columnName;
		
		for(int i=index;i<tokenListesi.size();i++){
			
			curToken=tokenListesi.get(i);
			
			logger.debug(curToken.getDeger().toString());
			
			if(curToken.isKelime()){
				
				for(int k=0;k<viewOfPojos.size();k++){
					
					pojoName=viewOfPojos.get(k).getDeger().toString();
					
					columnName=viewOfPojos.get(k).getColumnNameToken().getDeger().toString();
					
					if(columnName.equals(curToken.getDeger().toString())||pojoName.equals(curToken.getDeger().toString())){
						columnDefinitionToken=viewOfPojos.get(k);
						
						pojo=new KelimeToken<>(pojoName,0,0,0);
						pojo.setColumnNameToken(curToken);
						pojo.setPojoVariable(true);
						ViewManagerFactory.getInstance().setTypeNameOfViews(pojo);
						curToken.setPojoVariable(true);
								
						tokenListesi.remove(i); //remove curToken
						tokenListesi.add(i,pojo); //add pojo
						
						
						logger.debug(curToken.getDeger().toString()+" Pojo olarak set edildi");
						
						continue;
					}
				}
				

			}
				
		}
		
	}
	
	
	// MFEXIT-KET_COUNTRY.CNT-AREA
	// IDGIDBS-TGECICI.GEXTRE_SAYFA --> Tek bir token a çevir. isScheması true
	// olsun. tablo tokeninin T_Gecici olarak set et.
	private void controlPojo() {
		boolean endDefineReached = false;
		ArrayToken arrayToken = null;
		AbstractToken astSchema, astTable, astColumn, astNokta;
		int indexOfMiddleScore;
		String schemaNameAndTableName = null, tableName;
		String[] names;

		for (int i = 0; i < tokenListesi.size() - 2; i++) {

			astSchema = tokenListesi.get(i);
			astNokta = tokenListesi.get(i + 1);
			astColumn = tokenListesi.get(i + 2); // i+1 nokta

			logger.debug("astCurrent:" + astSchema);

			if (astSchema.getTip().equals(TokenTipi.Kelime)
					&& astNokta.getTip().equals(TokenTipi.Nokta)) {

				schemaNameAndTableName = astSchema.getDeger().toString();

				names = schemaNameAndTableName.split("_");

				tableName = schemaNameAndTableName.substring(schemaNameAndTableName.indexOf("_") + 1);

				if (ConverterConfiguration.isSchemaName(names[0])) {
					astTable = new KelimeToken(tableName, astSchema.getSatirNumarasi(), 0,
							astSchema.getSatirdakiTokenSirasi() + 1);
					astTable.setColumnNameToken(astColumn);
					astTable.setPojoVariable(true);
					astTable.setLocalVariable(false);
					astTable.setSynoynmsRealTableName(astSchema.getSynoynmsRealTableName());
					astTable.setColumnRedefiner(astColumn.isColumnRedefiner());
					astSchema.setTableNameToken(astTable);
					astSchema.setDeger(names[0]);
					astSchema.setPojoVariable(true);
					astSchema.setLocalVariable(false);
					astTable.setSchemaNameToken(astSchema);
					
					if(ConversionLogModel.getInstance().getCustomer().equals("MB")){
						//idgidbs DISINDAKİ POJOLARDA SCHEMA İSMİ EKLENİR.
						if(!names[0].toUpperCase().equals("IDGIDBS")){
							astTable.setDeger(names[0]+tableName);
						}
					}	
					tokenListesi.remove(i + 2); // Column Token
					tokenListesi.remove(i + 1); // Nokta Token
					tokenListesi.remove(i); // Schema Token
					tokenListesi.add(i, astTable); // TableToken
				}
			}
		}
		System.out.println("test");
		controlPojosDimension();

	}

	private void controlPojosDimension() {
		
		AbstractToken astCurrent = null, astStartParantez = null, astPreDimension, astNextDimension;

		boolean endDefineReached = false;
		ArrayToken arrayToken = null;

		
		for (int i = 0; i < tokenListesi.size() - 3; i++) {

			astCurrent = tokenListesi.get(i);
			astStartParantez = tokenListesi.get(i + 1);
			astPreDimension = tokenListesi.get(i + 2);

		
			if (!endDefineReached) {
				endDefineReached = isEndDefineReached(astCurrent);
				continue;
			}

			logger.debug("astCurrent:" + astCurrent);
			logger.debug(" astStartParantez:" + astStartParantez);
			logger.debug(" astPreDimension:" + astPreDimension);
			// logger.debug(" astEndParantez:" + astEndParantez);

			if (astCurrent.isPojoVariable() && astStartParantez.isKarakter('(')) {
				
				tokenListesi.remove(i+1); // start Parantez
				tokenListesi.remove(i+1); // pre Dimesion
			

				do {

					astNextDimension = tokenListesi.get(i+1);
					tokenListesi.remove(i+1); // next Dimesion
					
					logger.debug(" astPreDimension:" + astPreDimension);
					logger.debug(" astNextDimension:" + astNextDimension);
					
					if(!astNextDimension.isKarakter(')')){
						astPreDimension.setDeger(astPreDimension.getDeger().toString() + astNextDimension.getDeger().toString());
					}

			
				}while(!(astNextDimension.isKarakter(')')));

				
			astCurrent.setPojosDimension(astPreDimension);

			}

		}
		
		
		
		
	}

	// IDGIDBS-TGECICI --> Tek bir token a çevir. isScheması true olsun. tablo
	// tokeninin T_Gecici olarak set et.
	/*
	 * // Schema ismini kaldırıp tablo ismi yapıyor. Ama bu yanlış. Schema ismi IKR-MLYT-KOD VIEW OF IKR-MLYT-KOD
			//örneginde olduğu gibi IKR schema  olabilir ama adabas da schema yok. DB2 da var. Dikkat!!!!!!!!!!!!
	 */
	private void controlTableName() {
		
		if(ConversionLogModel.getInstance().getCustomer().equals("THY")){
			return;
		}
		
		boolean endDefineReached = false;
		ArrayToken arrayToken = null;
		AbstractToken astSchema, astTable;
		int indexOfMiddleScore;
		String schemaNameAndTableName = null, tableName;
		String[] names;

		for (int i = 0; i < tokenListesi.size() - 2; i++) {

			astSchema = tokenListesi.get(i);

			logger.debug("astCurrent:" + astSchema);

			if (astSchema.getTip().equals(TokenTipi.Kelime) && astSchema.getDeger().toString().contains("_")) {

				schemaNameAndTableName = astSchema.getDeger().toString();

				names = schemaNameAndTableName.split("_");
				tableName = schemaNameAndTableName.substring(schemaNameAndTableName.indexOf("_") + 1);

				if (names.length > 0 && ConverterConfiguration.isSchemaName(names[0])) {
					astTable = new KelimeToken(tableName, astSchema.getSatirNumarasi(), 0,
							astSchema.getSatirdakiTokenSirasi() + 1);
					astSchema.setTableNameToken(astTable);
					astSchema.setDeger(names[0]);
					astSchema.setPojoVariable(true);
					astTable.setSchemaNameToken(astSchema);
					astTable.setTable(true);
					astTable.setSynoynmsRealTableName(astSchema.getSynoynmsRealTableName());
					
					//idgidbs DISINDAKİ POJOLARDA SCHEMA İSMİ EKLENİR.
					if(!names[0].toUpperCase().equals("IDGIDBS")){
						astTable.setDeger(names[0]+tableName);
					}
					tokenListesi.remove(i);
					tokenListesi.add(i, astTable);
				}
			}
		}

	}

	// SUBSTR(SB_ACIKLAMA,1,79) --> bunu SB_ACIKLAMA ya çevirir.
	// subStringStartIndex ve subStringEndIndex set eder.
	private void setSubstrFields() {
		AbstractToken current; // SUBSTR
		AbstractToken parantezOpen; // (
		AbstractToken realToken;// SB_ACIKLAMA
		AbstractToken comma1; // ,
		AbstractToken startIndex; // 1
		AbstractToken comma2; // ,
		AbstractToken endIndex; // 79
		AbstractToken parantezClose; // )

		String currentDeger;
		String nextNextDeger;

		for (int i = 0; i < tokenListesi.size() - 8; i++) {

			current = tokenListesi.get(i);
			parantezOpen = tokenListesi.get(i + 1);
			realToken = tokenListesi.get(i + 2);
			comma1 = tokenListesi.get(i + 3);
			startIndex = tokenListesi.get(i + 4);
			comma2 = tokenListesi.get(i + 5);
			endIndex = tokenListesi.get(i + 6);
			parantezClose = tokenListesi.get(i + 7);
			logger.debug("current:" + current);
			logger.debug("current:" + current);

			if (current.getTip().equals(TokenTipi.OzelKelime)
					&& (current.getDeger().equals(ReservedNaturalKeywords.SUBSTR) || current.getDeger().equals(ReservedNaturalKeywords.SUBSTRING) )) {

				if (!(parantezOpen.getTip().equals(TokenTipi.Karakter) && parantezOpen.getDeger().equals('('))) {
					continue;
				}
				if (!(parantezClose.getTip().equals(TokenTipi.Karakter) && parantezClose.getDeger().equals(')'))) {
					continue;
				}

				realToken.setSubstringCommand(true);

				if (startIndex.getTip().equals(TokenTipi.Sayi)) {
					long startIndexDbl = (long) startIndex.getDeger();
					realToken.setSubStringStartIndex((int)startIndexDbl);
				} else {
					realToken.setSubStringStartIndexString((String) startIndex.getDeger());
				}

				if (endIndex.getTip().equals(TokenTipi.Sayi)) {
					long endIndexDbl = (long) endIndex.getDeger();
					realToken.setSubStringEndIndex((int)endIndexDbl);
				} else {
					realToken.setSubStringEndIndexString((String) endIndex.getDeger());
				}

				tokenListesi.add(i, realToken);

				tokenListesi.remove(i + 8);
				tokenListesi.remove(i + 7);
				tokenListesi.remove(i + 6);
				tokenListesi.remove(i + 5);
				tokenListesi.remove(i + 4);
				tokenListesi.remove(i + 3);
				tokenListesi.remove(i + 2);
				tokenListesi.remove(i + 1);

			}
		}
	}

	
	// VAL(FAIZYENIHESNOA)  --> bunu FAIZYENIHESNOA ya çevirir.
		// val=true set eder.
		private void setVal() {
			AbstractToken current; // SUBSTR
			AbstractToken parantezOpen; // (
			AbstractToken realToken;// SB_ACIKLAMA
			AbstractToken comma1; // ,
			AbstractToken startIndex; // 1
			AbstractToken comma2; // ,
			AbstractToken endIndex; // 79
			AbstractToken parantezClose; // )

			String currentDeger;
			String nextNextDeger;

			for (int i = 0; i < tokenListesi.size()-3; i++) {

				current = tokenListesi.get(i);
				parantezOpen = tokenListesi.get(i + 1);
				realToken = tokenListesi.get(i + 2);
				parantezClose = tokenListesi.get(i + 3);
				logger.debug("current:" + current);
			
				if (current.isKelime(ReservedNaturalKeywords.VAL)) {

					if (!(parantezOpen.getTip().equals(TokenTipi.Karakter) && parantezOpen.getDeger().equals('('))) {
						continue;
					}
					if (!(parantezClose.getTip().equals(TokenTipi.Karakter) && parantezClose.getDeger().equals(')'))) {
						continue;
					}

					realToken.setVal(true);

					tokenListesi.add(i, realToken);

					tokenListesi.remove(i + 4);
					tokenListesi.remove(i + 3);
					tokenListesi.remove(i + 2);
					tokenListesi.remove(i + 1);

				}
			}
			
			setVal2();
		}
		
		
		// VAL(MAP.YETSUBE)  --> bunu MAP e çevirir.
				// val=true set eder.
				private void setVal2() {
					AbstractToken current; // SUBSTR
					AbstractToken parantezOpen; // (
					AbstractToken realToken;// SB_ACIKLAMA
					AbstractToken dotToken;// SB_ACIKLAMA
					AbstractToken realLinkedToken;// SB_ACIKLAMA
					AbstractToken parantezClose; // )

					String currentDeger;
					String nextNextDeger;

					for (int i = 0; i < tokenListesi.size()-5; i++) {

						current = tokenListesi.get(i);
						parantezOpen = tokenListesi.get(i + 1);
						realToken = tokenListesi.get(i + 2);
						dotToken= tokenListesi.get(i + 3);
						realLinkedToken = tokenListesi.get(i + 4);
						parantezClose = tokenListesi.get(i + 5);
						logger.debug("current:" + current);
					
						
						if (!current.isKelime(ReservedNaturalKeywords.VAL)
							||!parantezOpen.isKarakter('(')
							|| !parantezClose.isKarakter(')')
							|| !dotToken.isNoktaToken()
							|| !realLinkedToken.isKelime()
							|| !realLinkedToken.isKelime()){
							continue;
						}
					
						realToken.setVal(true);
						realToken.setLinkedToken(realLinkedToken);
						realToken.setRecordVariable(true);

						tokenListesi.add(i, realToken);

						tokenListesi.remove(i + 6);
						tokenListesi.remove(i + 5);
						tokenListesi.remove(i + 4);
						tokenListesi.remove(i + 3);
						tokenListesi.remove(i + 2);
						tokenListesi.remove(i + 1);

					}
				}
		
		
		
	// D_SECIM(*) --> D_SECIM yapar ve D_SECIM in flagini işaretler
	private void setAllElementsOfArrayFlag() {
		AbstractToken current;
		AbstractToken next;
		AbstractToken nextOfNext;
		AbstractToken nextThird;

		String currentDeger;
		String nextNextDeger;

		for (int i = 0; i < tokenListesi.size() - 4; i++) {

			current = tokenListesi.get(i);
			next = tokenListesi.get(i + 1);
			nextOfNext = tokenListesi.get(i + 2);
			nextThird = tokenListesi.get(i + 3);

			//logger.debug("current:" + current.getDeger());

			if ((current.getDeger() instanceof String)
					&& ((next.getTip().equals(TokenTipi.Karakter) && next.getDeger().equals('(')))
					&& ((nextOfNext.getTip().equals(TokenTipi.Karakter) && nextOfNext.getDeger().equals('*')))
					&& ((nextThird.getTip().equals(TokenTipi.Karakter) && nextThird.getDeger().equals(')')))) {
				current.setAllArrayItems(true);
				tokenListesi.remove(i + 3);
				tokenListesi.remove(i + 2);
				tokenListesi.remove(i + 1);
			}
		}

	}

	// D_SECIM(*) --> D_SECIM yapar ve D_SECIM in flagini işaretler
	private void setAllElementsOfPojoFlag() {
		AbstractToken current;
		AbstractToken next;
		AbstractToken nextOfNext;
		AbstractToken nextThird;

		String currentDeger;
		String nextNextDeger;

		for (int i = 0; i < tokenListesi.size() - 4; i++) {

			current = tokenListesi.get(i);
			next = tokenListesi.get(i + 1);
			nextOfNext = tokenListesi.get(i + 2);
			nextThird = tokenListesi.get(i + 3);

			logger.debug("current:" + current);

			if ((current.getDeger() instanceof String) && current.isPojoVariable()
					&& ((next.getTip().equals(TokenTipi.Karakter) && next.getDeger().equals('(')))
					&& ((nextOfNext.getTip().equals(TokenTipi.Karakter) && nextOfNext.getDeger().equals('*')))
					&& ((nextThird.getTip().equals(TokenTipi.Karakter) && nextThird.getDeger().equals(')')))) {
				current.setPojosAllItems(true);
				tokenListesi.remove(i + 3);
				tokenListesi.remove(i + 2);
				tokenListesi.remove(i + 1);
			}
		}

	}

	// 1034 MAP2.MUSNO1:=+MUSNO1 --> Musno1 i Map2 ye eleman olarak ekler.
	public void controlRecords() {
		AbstractToken current;
		AbstractToken next;
		AbstractToken nextOfNext;

		String currentDeger;
		String nextNextDeger;

		for (int i = 0; i < tokenListesi.size() - 2; i++) {

			current = tokenListesi.get(i);
			next = tokenListesi.get(i + 1);
			nextOfNext = tokenListesi.get(i + 2);

			logger.debug("current:" + current);

			if ((current.getDeger() instanceof String) && (next.getTip().equals(TokenTipi.Nokta))
					&& (nextOfNext.getDeger() instanceof String)) {
				currentDeger = (String) current.getDeger();
				nextNextDeger = (String) nextOfNext.getDeger();
				
				current.setLinkedToken(nextOfNext);
				current.setRecordVariable(true);
				tokenListesi.remove(i + 2);
				tokenListesi.remove(i + 1);
			}

		}

	}

	/**
	 * 
	 * Yıldız Analizi: IF *LANGUAGE EQ 7 (SystemVariableBelirten, Sonrasında
	 * Sistem Variable var.)
	 * 
	 * REINPUT 'LIMANI GIRINIZ.' MARK *#T-KLIM (ReInput İçinde, Sonrasında Diyez
	 * Var Mutlaka)
	 * 
	 * #FARK1 :=#FARK1 * -1 (Çarpı İşlemi)
	 * 
	 * Diyez Analizi: Diyez varsa sonraki kelime local değişkendir.
	 */

	private void controlStarAndDiyezToken() {

		// if (!Configuration.pojosAreDefinedInCode) { // MB de pojo
		// tanımlanmıyor
		controlStarAndDiyezTokenWithoutPojoDefinitions();
		// return;
		// }
		/*
		 * AbstractToken astCurrent, astNext, astNexter, astPrevious = null;
		 * 
		 * boolean ifFound = false; for (int i = 0; i < tokenListesi.size() - 2;
		 * i++) {
		 * 
		 * astCurrent = tokenListesi.get(i); astNext = tokenListesi.get(i + 1);
		 * 
		 * if (i > 0) { astPrevious = tokenListesi.get(i - 1); }
		 * 
		 * logger.debug(astCurrent.toString());
		 * 
		 * // IF *LANGUAGE EQ 7 // * ve sonra sistem variable ise * ı
		 * tokenlistden çıkar ve tokenı // sistem variable ını true set et. if
		 * (astCurrent.getTip().equals(TokenTipi.Karakter) &&
		 * astCurrent.getDeger().equals('*') &&
		 * astNext.getTip().equals(TokenTipi.Kelime) &&
		 * controlSystemVariable((KelimeToken) astNext)) {
		 * tokenListesi.remove(i); astNext.setSystemVariable(true); // /*
		 * Commenti varsa O satiri bypass et. } else if (astPrevious != null &&
		 * astPrevious.getTip().equals(TokenTipi.Karakter) &&
		 * astPrevious.getDeger().equals('/') &&
		 * astCurrent.getTip().equals(TokenTipi.Karakter) &&
		 * astCurrent.getDeger().equals('*')) { // Karakterse Devam // Et while
		 * (astCurrent.getTip() != TokenTipi.SatirBasi) { i++; astCurrent =
		 * tokenListesi.get(i); } } // * dan önce satirbaşı varsa. Comment
		 * satırıdır. Bİr şey yapma. else if
		 * (astCurrent.getTip().equals(TokenTipi.Karakter) &&
		 * astCurrent.getDeger().equals('*') &&
		 * astPrevious.getTip().equals(TokenTipi.SatirBasi)) { // Karakterse //
		 * Devam // Et // Comment // patern // yakalar. // Do nothing.
		 * 
		 * } // REINPUT 'LIMANI GIRINIZ.' MARK *#T-KLIM (ReInput İçinde, //
		 * Sonrasında Diyez Var Mutlaka) // * dan sonra diyez varsa yıldızı ve
		 * diyezi tokenListden // kaldır. Sonraki kelimenin local değişken
		 * özelliğini true set // et. else if
		 * (astCurrent.getTip().equals(TokenTipi.Karakter) &&
		 * astCurrent.getDeger().equals('*') &&
		 * astNext.getTip().equals(TokenTipi.Karakter) &&
		 * astNext.getDeger().equals('#')) { // Karakterse // Devam // Et
		 * tokenListesi.remove(i); tokenListesi.remove(i);
		 * astNext.setLocalVariable(true); } // REINPUT 'Yurt Ýçi Transferde
		 * Ülke Kodu TR Olmalý' MARK // *WULKEKOD (MARK dan sonra yıldız sonra
		 * kelime varsa sonraki // kelimenin yıldız flagini set et ve diziden
		 * yıldızı çıkar.) else if
		 * (astCurrent.getTip().equals(TokenTipi.OzelKelime) &&
		 * astCurrent.getDeger().equals(ReservedNaturalKeywords.MARK) &&
		 * astNext.getTip().equals(TokenTipi.Karakter) &&
		 * astNext.getDeger().equals('*')) { astNexter = tokenListesi.get(i +
		 * 2); if (astNexter.getTip().equals(TokenTipi.Kelime)) {
		 * astNexter.setStarSigned(true); tokenListesi.remove(i + 1); } } // #
		 * den sonra kelime varsa # i listeden çıkar ve kelimenin local //
		 * değişken özelliğini true set et. else if
		 * (astCurrent.getTip().equals(TokenTipi.Karakter) &&
		 * astCurrent.getDeger().equals('#') &&
		 * (astNext.getTip().equals(TokenTipi.Kelime))) {
		 * tokenListesi.remove(i); astNext.setLocalVariable(true); // FMM-ISN(*)
		 * } else if (astCurrent.getTip().equals(TokenTipi.Kelime) &&
		 * (astCurrent.isConstantVariableWithDoubleQuota() ||
		 * astCurrent.isConstantVariableWithQuota())) { // Do nothing; //
		 * FMM-ISN(*) } else {// Bunlardan hiçbiri değilse Database Variable
		 * olarak set // et. astCurrent.setPojoVariable(true); } }
		 */
	}

	private void controlStarAndDiyezTokenWithoutPojoDefinitions() {
		AbstractToken astCurrent, astNext, astNexter, astPrevious = null, astControl;

		boolean isGlobalVariable;

		boolean ifFound = false;
		for (int i = 0; i < tokenListesi.size() - 2; i++) {

			astCurrent = tokenListesi.get(i);
			astNext = tokenListesi.get(i + 1);

			if (i > 0) {
				astPrevious = tokenListesi.get(i - 1);
			}

			logger.debug(astCurrent.toString());

			// IF *LANGUAGE EQ 7
			// * ve sonra sistem variable ise * ı tokenlistden çıkar ve tokenı
			// sistem variable ını true set et.
			if (astCurrent.getTip().equals(TokenTipi.Karakter) && astCurrent.getDeger().equals('*')
					&& astNext.getTip().equals(TokenTipi.Kelime) && controlSystemVariable((KelimeToken) astNext)) {
				tokenListesi.remove(i);
				astNext.setSystemVariable(true);
				// /* Commenti varsa O satiri bypass et.
			} else if (astPrevious != null && astPrevious.getTip().equals(TokenTipi.Karakter)
					&& astPrevious.getDeger().equals('/') && astCurrent.getTip().equals(TokenTipi.Karakter)
					&& astCurrent.getDeger().equals('*')) { // Karakterse Devam
															// Et
				while (astCurrent.getTip() != TokenTipi.SatirBasi) {
					i++;
					astCurrent = tokenListesi.get(i);
				}
			} // * dan önce satirbaşı varsa. Comment satırıdır. Bİr şey yapma.
			else if (astCurrent.getTip().equals(TokenTipi.Karakter) && astCurrent.getDeger().equals('*')
					&& astPrevious.getTip().equals(TokenTipi.SatirBasi)) { // Karakterse
																			// Devam
																			// Et
																			// Comment
																			// patern
																			// yakalar.
				// Do nothing.

			} // REINPUT 'LIMANI GIRINIZ.' MARK *#T-KLIM (ReInput İçinde,
				// Sonrasında Diyez Var Mutlaka)
				// * dan sonra diyez varsa yıldızı ve diyezi tokenListden
				// kaldır. Sonraki kelimenin local değişken özelliğini true set
				// et.
			else if (astCurrent.getTip().equals(TokenTipi.Karakter) && astCurrent.getDeger().equals('*')
					&& astNext.getTip().equals(TokenTipi.Karakter) && astNext.getDeger().equals('#')) { // Karakterse
																										// Devam
																										// Et
				tokenListesi.remove(i);
				tokenListesi.remove(i);
				astNext.setLocalVariable(true);
			} // REINPUT 'Yurt Ýçi Transferde Ülke Kodu TR Olmalý' MARK
				// *WULKEKOD (MARK dan sonra yıldız sonra kelime varsa sonraki
				// kelimenin yıldız flagini set et ve diziden yıldızı çıkar.)
			else if (astCurrent.getTip().equals(TokenTipi.OzelKelime)
					&& astCurrent.getDeger().equals(ReservedNaturalKeywords.MARK)
					&& astNext.getTip().equals(TokenTipi.Karakter) && astNext.getDeger().equals('*')) {
				astNexter = tokenListesi.get(i + 2);
				if (astNexter.getTip().equals(TokenTipi.Kelime)) {
					astNexter.setStarSigned(true);
					tokenListesi.remove(i + 1);
				}
			} // # den sonra kelime varsa # i listeden çıkar ve kelimenin local
				// değişken özelliğini true set et.
			else if (astCurrent.isKarakter('#') && astNext.isKarakter('#')) {
				tokenListesi.remove(i);
				tokenListesi.remove(i);
				astNexter = tokenListesi.get(i);
				astNexter.setDeger("DIYEZ_DIYEZ_" + astNexter.getDeger());
				astNexter.setLocalVariable(true);
				// FMM-ISN(*)
			} else if (astCurrent.isKarakter('#')
					&& (astNext.isKelime()|| astNext.isOzelKelime())) {
				tokenListesi.remove(i);
				astNext.setDeger("DIYEZ_" + astNext.getDeger());  // #DO varsa bu DO ozelkelimede olsa normal kelime olarak düsünülmeli.
				astNext.setLocalVariable(true);
				astNext.setTip(TokenTipi.Kelime);
				// FMM-ISN(*)
			}else if (astCurrent.getTip().equals(TokenTipi.Kelime)
					&& (astCurrent.isConstantVariableWithQuota())) {
				// Do nothing;
				// FMM-ISN(*)
			} // TODO: Bu kod toplama işlemini de Global gibi görüyor. Buna bir
				// çare düşün.
				// +MUSNO1 Global Değişkense + yı uçur ve global değişken olarak
				// set et.
				// + dan sonraki kelime ise ve recordvariable değilse ve
				// tepedeki tanım bölgesinde tanımlı değilse globaldeğişkendir.
			else if (astCurrent.getTip().equals(TokenTipi.Karakter) && astCurrent.getDeger().equals('+')
					&& astNext.getTip().equals(TokenTipi.Kelime) && !astNext.isRecordVariable()) {
				isGlobalVariable = true;
				for (int k = 0; k < i; k++) {
					astControl = tokenListesi.get(k);
					if (astControl.getTip().equals(TokenTipi.OzelKelime)
							&& astControl.getDeger().equals(ReservedNaturalKeywords.END_DEFINE)) {
						break;
					}
					if (astControl.getTip().equals(TokenTipi.Kelime)
							&& astControl.getDeger().equals(astNext.getDeger())) {
						isGlobalVariable = false;
						break;
					}
				}
				/*
				 * if (isGlobalVariable) { tokenListesi.remove(i);
				 * astNext.setGlobalVariable(true); astNext.setDeger("GLOBAL_" +
				 * astNext.getDeger()); }
				 */
			} else if (astCurrent.getTip().equals(TokenTipi.Kelime)) {// Bunlardan
																		// hiçbiri
																		// değilse
																		// ve
																		// Kelime
																		// ise
																		// Local
																		// Variable
																		// olarak
																		// set
																		// et.
				astCurrent.setLocalVariable(true);
			}
		}
		
		
		for (int i = 0; i < tokenListesi.size() - 2; i++) {

			astCurrent = tokenListesi.get(i);
			astNext = tokenListesi.get(i + 1);

			if (i > 0) {
				astPrevious = tokenListesi.get(i - 1);
			}

			logger.debug(astCurrent.toString());

			if (astNext.isKarakter('#')
					&& (astCurrent.isKelime())) {
				tokenListesi.remove(i+1);
				astCurrent.setDeger(astCurrent.getDeger()+"_DIYEZ");  // 
				astCurrent.setLocalVariable(true);
				astCurrent.setTip(TokenTipi.Kelime);
				// FMM-ISN(*)
			}
		}
	}

	// Bu kod En-DEfine dan sonrakiler için kontrol eder.
	// Öncekilere etmesi doğru değil.
	// #SIRALIMAN-SAYFA(#SAYFA) --> #SIRALIMAN-SAYFA
	// SCR-EXEMPT-CODE (LNSTART+2) --> SCR-EXEMPT-CODE
	private void controlOneDimensionArrayParameters() {
		AbstractToken astCurrent = null, astStartParantez = null, astPreDimension, astNextDimension;

		boolean endDefineReached = false;
		ArrayToken arrayToken = null;

		for (int i = 0; i < tokenListesi.size() - 3; i++) {

			astCurrent = tokenListesi.get(i);
			astStartParantez = tokenListesi.get(i + 1);
			astPreDimension = tokenListesi.get(i + 2);
			astNextDimension = tokenListesi.get(i + 3);

			if (astCurrent.isPojoVariable()) {

				continue;
			}

			if (!endDefineReached) {
				endDefineReached = isEndDefineReached(astCurrent);
				continue;
			}
			
			if(astCurrent.isKelime("WHERE")|| astCurrent.isOzelKelime("WHERE")){
				continue;
			}

			logger.debug("astCurrent:" + astCurrent);
			logger.debug(" astStartParantez:" + astStartParantez);
			logger.debug(" astPreDimension:" + astPreDimension);
			// logger.debug(" astEndParantez:" + astEndParantez);
			
			if (!astCurrent.isConstantVariableWithQuota()&&astCurrent.getTip().equals(TokenTipi.Kelime) && astStartParantez.getTip().equals(TokenTipi.Karakter)
					&& astStartParantez.getDeger().equals('(')) {
				// && astEndParantez.getTip().equals(TokenTipi.Karakter) &&
				// astEndParantez.getDeger().equals(')')) {

				tokenListesi.remove(i); // tokenin kendisi
				tokenListesi.remove(i); // start Parantez
				tokenListesi.remove(i); // pre Dimesion

				while (!(astNextDimension.getTip().equals(TokenTipi.Karakter)
						&& astNextDimension.getDeger().equals(')'))) {

					logger.debug(" astPreDimension:" + astPreDimension);
					logger.debug(" astNextDimension:" + astNextDimension);

					astPreDimension
							.setDeger(astPreDimension.getDeger().toString() + astNextDimension.getDeger().toString());

					tokenListesi.remove(i); // next Dimension

					astNextDimension = tokenListesi.get(i);

				}

				tokenListesi.remove(i); // remove Parantez

				arrayToken = new ArrayToken(astCurrent.getDeger(), astCurrent.getSatirNumarasi(),
						astCurrent.getUzunluk(), astCurrent.getSatirdakiTokenSirasi(), astPreDimension);
				arrayToken.setInputADParameters(astCurrent.getInputADParameters());

				tokenListesi.add(i, arrayToken);

			}

		}

	}
	
	/**
	 * + MUSNO1 --> MUSNO1
	 */
	
	public void replaceGlobalVariables() {

		AbstractToken plusToken, globalVariableToken;

		for (int i = 0; i < tokenListesi.size() - 2; i++) {

			plusToken = tokenListesi.get(i);
			globalVariableToken = tokenListesi.get(i + 1);

			if (plusToken.getTip().equals(TokenTipi.Karakter) && plusToken.getDeger().equals('+')) {

				if (GLOBAL_VARIABLES_SET.contains(globalVariableToken.getDeger())) {
					tokenListesi.remove(i); // Remove +
					globalVariableToken.setDeger(globalVariableToken.getDeger());
					globalVariableToken.setGlobalVariable(true);
					globalVariableToken.setLocalVariable(true);
				}

			}

		}
	}

	private boolean isEndDefineReached(AbstractToken astCurrent) {
		if (astCurrent != null && astCurrent.getTip().equals(TokenTipi.OzelKelime)
				&& astCurrent.getDeger().equals("END-DEFINE")) {
			return true;
		}
		return false;
	}

	// #SIRALIMAN-SAYFA(#SAYFA) --> #SIRALIMAN-SAYFA
	private void controlTwoDimensionArrayParameters() {
		AbstractToken astCurrent = null, astStartParantez = null, astFirstDimension = null, astSecondDimension = null,
				astEndParantez = null, astComma;

		ArrayToken arrayToken = null;

		boolean endDefineReached = false;

		for (int i = 0; i < tokenListesi.size() - 5; i++) {
			astCurrent = tokenListesi.get(i);
			astStartParantez = tokenListesi.get(i + 1);
			astFirstDimension = tokenListesi.get(i + 2);
			astComma = tokenListesi.get(i + 3);
			astSecondDimension = tokenListesi.get(i + 4);
			astEndParantez = tokenListesi.get(i + 5);

			if (!endDefineReached) {
				endDefineReached = isEndDefineReached(astCurrent);
				continue;
			}
			
			if(astCurrent.isKelime("WHERE")|| astCurrent.isOzelKelime("WHERE")){
				continue;
			}

			logger.debug("astCurrent:" + astCurrent);
			logger.debug(" astStartParantez:" + astStartParantez);
			logger.debug(" astFirstDimension:" + astFirstDimension);
			logger.debug(" astComma:" + astComma);
			logger.debug(" astSecondDimension:" + astSecondDimension);
			logger.debug(" astEndParantez:" + astEndParantez);

			if (!astCurrent.isConstantVariableWithQuota()&&astCurrent.getTip().equals(TokenTipi.Kelime) && astStartParantez.getTip().equals(TokenTipi.Karakter)
					&& astStartParantez.getDeger().equals('(') && astEndParantez.getTip().equals(TokenTipi.Karakter)
					&& astEndParantez.getDeger().equals(')') && astComma.getTip().equals(TokenTipi.Karakter)
					&& (astComma.getDeger().equals(',') || astComma.getDeger().equals(':'))) {
				arrayToken = new ArrayToken(astCurrent.getDeger(), astCurrent.getSatirNumarasi(),
						astCurrent.getUzunluk(), astCurrent.getSatirdakiTokenSirasi(), astFirstDimension,
						astSecondDimension);

				tokenListesi.remove(i + 5); // )
				tokenListesi.remove(i + 4); // SecDimension
				tokenListesi.remove(i + 3); // Comma
				tokenListesi.remove(i + 2); // FirstDimension
				tokenListesi.remove(i + 1); // (
				tokenListesi.remove(i); // tokenin kendisi
				tokenListesi.add(i, arrayToken);
			}

		}

	}

	/**
	 * PF-KEY DATX DAT4E TIMX USER PROGRAM DEVICE
	 * 
	 * *PAGE-NUMBER · *ISN · *COUNTER · *NUMBER · POS (position)
	 * 
	 * @param systemVariable
	 * @return
	 */
	private boolean controlSystemVariable(KelimeToken systemVariable) {
		
		List<String>  systemVariables=new ArrayList<>();
		systemVariables.add("PF_KEY");
		systemVariables.add("DAT");
		systemVariables.add("DAT4I");
		systemVariables.add("DAT4E");
		systemVariables.add("DAT4D");
		systemVariables.add("DATE");
		systemVariables.add("DATN");
		systemVariables.add("TIMX");
		systemVariables.add("TIME");
		systemVariables.add("USER");
		systemVariables.add("PROGRAM");
		systemVariables.add("DEVICE");
		systemVariables.add("LANGUAGE");
		systemVariables.add("PAGE_NUMBER");
		systemVariables.add("ISN");
		systemVariables.add("COUNTER");
		systemVariables.add("NUMBER");
		systemVariables.add("CURS_LINE");
		systemVariables.add("INIT_USER");
		systemVariables.add("TIMN");
		systemVariables.add("LINE_COUNT");
		systemVariables.add("GROUP");
		systemVariables.add("LIBRARY_ID");
		systemVariables.add("CURS_FIELD");
		systemVariables.add("INIT_ID");
		systemVariables.add("ERROR_LINE");
		systemVariables.add("SQLCODE");
		systemVariables.add("ERROR_NR");

		
		if(systemVariables.contains(systemVariable.getDeger())){
			return true;
		}
		
		return false;
	}

	public Map<String, String> getIncludeFileList() {
		return includeFileList;
	}


	
}
