package tr.com.vbt.token;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import tr.com.vbt.lexer.RedefinedColumn;

public abstract class AbstractToken<T> {

	protected T deger;

	protected int satirNumarasi;

	protected int uzunluk;

	protected TokenTipi tip;

	protected int satirdakiTokenSirasi;

	protected String tekrarlayabilir; // + tek bir tane, * n tane

	protected String sourceFieldName;

	protected boolean isStarter;

	protected boolean isEnder;

	private boolean isOptional;

	private boolean isSystemVariable;

	private boolean isLocalVariable;

	private boolean isRedefinedVariable;
	
	private boolean isRedefinedVariableDimensionToSimple;

	private boolean isPojoVariable;
	
	private boolean isAmpersand;
	
	private AbstractToken pojosDimension;
	
	private boolean isPojoSubTableCount;

	private boolean isGlobalVariable;

	private boolean isRecordVariable;

	private boolean isAllArrayItems;

	private boolean isPojosAllItems;

	private boolean isConstantVariableWithQuota;

	private AbstractToken linkedToken;

	private boolean isSubstringCommand;

	private int subStringStartIndex;

	private String subStringStartIndexString;

	private int subStringEndIndex;

	private String subStringEndIndexString;

	protected List<Character> shouldHaveList = new ArrayList<Character>();

	private AbstractToken tableNameToken;

	private AbstractToken schemaNameToken;

	private AbstractToken columnNameToken;

	private boolean starSigned;

	private AbstractToken inputADParameters;
	
	private AbstractToken inputIPParameters;
	
	private AbstractToken inputCVParameters;
	
	private AbstractToken inputEMParameters;
	
	private boolean isInputParameters;

	private int indendation;

	private String indendationType; // T, X

	private int indexOfArrayForInputMap; // T, X

	private int newLineCount; // / gördükçe bir artırıylıro. Input command
								// kullaniyor

	private boolean edited;

	private boolean masked;

	private boolean filterName;

	private boolean filterOPerator;

	private boolean filtreValue;

	private boolean filtrejoiner;

	private boolean filtreParantez;

	private String refereSatirNumarasi;

	List<AbstractToken> editMaskTokenList;

	List<AbstractToken> maskTokenList;
	
	private String synoynmsRealTableName;
	
	private String typeNameOfView;
	
	private RedefinedColumn redefinedColumn;
	
	private boolean columnRedefiner;
	
	private boolean conditionJoiner;
	
	private boolean visualToken;
	
	public AbstractToken(T deger, int satirNumarasi, int uzunluk, TokenTipi tip, int satirdakiTokenSirasi) {
		super();
		this.deger = deger;
		this.satirNumarasi = satirNumarasi;
		this.uzunluk = uzunluk;
		this.tip = tip;
		this.satirdakiTokenSirasi = satirdakiTokenSirasi;
	}
	
	public AbstractToken(T deger, int satirNumarasi, int uzunluk, TokenTipi tip, int satirdakiTokenSirasi,boolean visualToken) {
		super();
		this.deger = deger;
		this.satirNumarasi = satirNumarasi;
		this.uzunluk = uzunluk;
		this.tip = tip;
		this.satirdakiTokenSirasi = satirdakiTokenSirasi;
		this.visualToken=visualToken;
	}

	public AbstractToken(T deger, int satirNumarasi, int uzunluk, TokenTipi tip, int satirdakiTokenSirasi,
			boolean isStarter, boolean isEnder) {
		super();
		this.deger = deger;
		this.satirNumarasi = satirNumarasi;
		this.uzunluk = uzunluk;
		this.tip = tip;
		this.satirdakiTokenSirasi = satirdakiTokenSirasi;
		this.isStarter = isStarter;
		this.isEnder = isEnder;
	}

	public AbstractToken(int satirNumarasi2, TokenTipi tokenType) {
		super();
		this.satirNumarasi = satirNumarasi2;
		this.tip = tokenType;
	}

	public AbstractToken(int satirNumarasi2, TokenTipi tokenType, boolean isStarter, boolean isEnder) {
		super();
		this.satirNumarasi = satirNumarasi2;
		this.tip = tokenType;
		this.isStarter = isStarter;
		this.isEnder = isEnder;
	}

	public AbstractToken(TokenTipi tokenType) {
		super();
		this.tip = tokenType;
	}

	public AbstractToken(T deger2, TokenTipi satirnumaralariicinrezerve) {
		super();
		this.deger = deger2;
		this.tip = TokenTipi.SatirNumaralariIcinRezerve;

	}

	@Override
	public boolean equals(Object obj) {
		AbstractToken dest = (AbstractToken) obj;
		if ((this.deger == null || dest.deger == this.deger) && (dest.satirdakiTokenSirasi == this.satirdakiTokenSirasi)
				&& (dest.satirNumarasi == this.satirNumarasi) && (this.tip == null || dest.tip == this.tip)
				&& (dest.uzunluk == this.uzunluk))
			return true;
		return false;
	}

	public boolean tokenMatchs(Object obj) {
		// This: CurrentTOkenForMatch
		// dest: tokenInPatern
		AbstractToken tokenInPatern = (AbstractToken) obj;
		if ((this.deger == null || tokenInPatern.deger == null || tokenInPatern.deger.equals(this.deger))
				&& (this.tip == null || tokenInPatern.tip == this.tip || tokenInPatern.tip == TokenTipi.GenelTip
						|| this.tip == TokenTipi.GenelTip)) {
			if (this.hasItem(tokenInPatern))
				return true;
		}
		return false;
	}

	private boolean hasItem(AbstractToken tokenInPatern) {
		if (this.getDeger() == null) { // Deger bossa kontrol etmemelisin.
			return true;
		}
		String thisDeger = this.getDeger().toString();
		Iterator<Character> it = tokenInPatern.shouldHaveList.iterator();
		Character shouldBe;
		while (it.hasNext()) {
			shouldBe = it.next();
			if (thisDeger.indexOf(shouldBe) == -1) {
				return false;
			}
			;
		}
		return true;
	}

	public T getDeger() {
		return deger;
	}

	public void setDeger(T deger) {
		this.deger = deger;
	}

	public int getSatirNumarasi() {
		return satirNumarasi;
	}

	public void setSatirNumarasi(int satirNumarasi) {
		this.satirNumarasi = satirNumarasi;
	}

	public int getUzunluk() {
		return uzunluk;
	}

	public void setUzunluk(int uzunluk) {
		this.uzunluk = uzunluk;
	}

	public TokenTipi getTip() {
		return tip;
	}

	public void setTip(TokenTipi tip) {
		this.tip = tip;
	}

	public int getSatirdakiTokenSirasi() {
		return satirdakiTokenSirasi;
	}

	public void setSatirdakiTokenSirasi(int satirdakiTokenSirasi) {
		this.satirdakiTokenSirasi = satirdakiTokenSirasi;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("Token:");
		if (deger != null) {
			sb.append(deger.toString());
		}
		if (tip != null) {
			sb.append("  TokenTip :" + tip.toString());
		}
		// sb.append(" SatirNo: "+satirNumarasi);
		// sb.append(" SatirdakiTokenSirasi: "+satirdakiTokenSirasi+"
		// ..............");
		return sb.toString();
	}

	public String getTekrarlayabilir() {
		return tekrarlayabilir;
	}

	public void setTekrarlayabilir(String tekrarlayabilir) {
		this.tekrarlayabilir = tekrarlayabilir;
	}

	public String getSourceFieldName() {
		return sourceFieldName;
	}

	public void setSourceFieldName(String sourceFieldName) {
		this.sourceFieldName = sourceFieldName;
	}

	public boolean isStarter() {
		return isStarter;
	}

	public void setStarter(boolean isStarter) {
		this.isStarter = isStarter;
	}

	public boolean isEnder() {
		return isEnder;
	}

	public void setEnder(boolean isEnder) {
		this.isEnder = isEnder;
	}

	public boolean isOptional() {
		return isOptional;
	}

	public void setOptional(boolean isOptional) {
		this.isOptional = isOptional;
	}

	public void addToShouldHaveList(Character shouldBeInPattern) {
		shouldHaveList.add(shouldBeInPattern);
	}

	public boolean isSystemVariable() {
		return isSystemVariable;
	}

	public void setSystemVariable(boolean isSystemVariable) {
		this.isSystemVariable = isSystemVariable;
	}

	public List<Character> getShouldHaveList() {
		return shouldHaveList;
	}

	public void setShouldHaveList(List<Character> shouldHaveList) {
		this.shouldHaveList = shouldHaveList;
	}

	public boolean isLocalVariable() {
		return isLocalVariable;
	}

	public void setLocalVariable(boolean isLocalVariable) {
		this.isLocalVariable = isLocalVariable;
	}

	public boolean isAllArrayItems() {
		return isAllArrayItems;
	}

	public void setAllArrayItems(boolean isAllArrayItems) {
		this.isAllArrayItems = isAllArrayItems;
	}

	public boolean isPojoVariable() {
		return isPojoVariable;
	}

	public void setPojoVariable(boolean isPojoVariable) {
		this.isPojoVariable = isPojoVariable;
	}

	public boolean isConstantVariableWithQuota() {
		return isConstantVariableWithQuota;
	}

	public void setConstantVariableWithQuota(boolean isConstantVariableWithQuota) {
		this.isConstantVariableWithQuota = isConstantVariableWithQuota;
	}

	public boolean isGlobalVariable() {
		return isGlobalVariable;
	}

	public void setGlobalVariable(boolean isGlobalVariable) {
		this.isGlobalVariable = isGlobalVariable;
	}

	public AbstractToken getLinkedToken() {
		return linkedToken;
	}

	public void setLinkedToken(AbstractToken linkedToken) {
		this.linkedToken = linkedToken;
	}

	public boolean isSubstringCommand() {
		return isSubstringCommand;
	}

	public void setSubstringCommand(boolean isSubstringCommand) {
		this.isSubstringCommand = isSubstringCommand;
	}

	public int getSubStringStartIndex() {
		return subStringStartIndex;
	}

	public void setSubStringStartIndex(int subStringStartIndex) {
		this.subStringStartIndex = subStringStartIndex - 1; // Her zaman bir
															// eksiği olmali.
															// Nat ve Java Farkı
	}

	public int getSubStringEndIndex() {
		return subStringEndIndex;
	}

	public void setSubStringEndIndex(int subStringEndIndex) {
		this.subStringEndIndex = subStringEndIndex; // Bir eksiği olmamali.
													// Çünkü bu natural da
													// endIndex değil lentgth
													// dir.
	}

	public AbstractToken getTableNameToken() {
		return tableNameToken;
	}

	public void setTableNameToken(AbstractToken tableNameToken) {
		this.tableNameToken = tableNameToken;
	}

	public AbstractToken getColumnNameToken() {
		return columnNameToken;
	}

	public void setColumnNameToken(AbstractToken columnNameToken) {
		this.columnNameToken = columnNameToken;
	}

	public void setStarSigned(boolean b) {
		this.starSigned = b;

	}

	public boolean isStarSigned() {
		return starSigned;
	}

	public AbstractToken getSchemaNameToken() {
		return schemaNameToken;
	}

	public void setSchemaNameToken(AbstractToken schemaNameToken) {
		this.schemaNameToken = schemaNameToken;
	}

	public boolean isRecordVariable() {
		return isRecordVariable;
	}

	public void setRecordVariable(boolean isRecordVariable) {
		this.isRecordVariable = isRecordVariable;
	}

	public String getSubStringStartIndexString() {
		return subStringStartIndexString;
	}

	public void setSubStringStartIndexString(String subStringStartIndexString) {
		this.subStringStartIndexString = subStringStartIndexString;
	}

	public String getSubStringEndIndexString() {
		return subStringEndIndexString;
	}

	public void setSubStringEndIndexString(String subStringEndIndexString) {
		this.subStringEndIndexString = subStringEndIndexString;
	}

	public AbstractToken getInputADParameters() {
		return inputADParameters;
	}

	public void setInputADParameters(AbstractToken inputADParameters) {
		this.inputADParameters = inputADParameters;
	}

	
	public AbstractToken getInputCVParameters() {
		return inputCVParameters;
	}

	public void setInputCVParameters(AbstractToken inputCVParameters) {
		this.inputCVParameters = inputCVParameters;
	}

	public int getIndendation() {
		return indendation;
	}

	public void setIndendation(int indendation) {
		this.indendation = indendation;
	}

	public String getIndendationType() {
		return indendationType;
	}

	public void setIndendationType(String indendationType) {
		this.indendationType = indendationType;
	}

	public int getIndexOfArrayForInputMap() {
		return indexOfArrayForInputMap;
	}

	public void setIndexOfArrayForInputMap(int indexOfArrayForInputMap) {
		this.indexOfArrayForInputMap = indexOfArrayForInputMap;
	}

	public int getNewLineCount() {
		return newLineCount;
	}

	public void setNewLineCount(int newLineCount) {
		this.newLineCount = newLineCount;
	}

	public List<AbstractToken> getEditMaskTokenList() {
		return editMaskTokenList;
	}

	public void setEditMaskTokenList(List<AbstractToken> editMaskTokenList) {
		this.editMaskTokenList = editMaskTokenList;
	}

	public boolean isEdited() {
		return edited;
	}

	public void setEdited(boolean edited) {
		this.edited = edited;
	}

	public boolean isMasked() {
		return masked;
	}

	public void setMasked(boolean masked) {
		this.masked = masked;
	}

	public List<AbstractToken> getMaskTokenList() {
		return maskTokenList;
	}

	public void setMaskTokenList(List<AbstractToken> maskTokenList) {
		this.maskTokenList = maskTokenList;
	}

	public boolean isFilterName() {
		return filterName;
	}

	public void setFilterName(boolean filterName) {
		this.filterName = filterName;
	}

	public boolean isFilterOPerator() {
		return filterOPerator;
	}

	public void setFilterOPerator(boolean filterOPerator) {
		this.filterOPerator = filterOPerator;
	}

	public boolean isFiltreValue() {
		return filtreValue;
	}

	public void setFiltreValue(boolean filtreValue) {
		this.filtreValue = filtreValue;
	}

	public boolean isFiltrejoiner() {
		return filtrejoiner;
	}

	public void setFiltrejoiner(boolean filtrejoiner) {
		this.filtrejoiner = filtrejoiner;
	}

	public boolean isFiltreParantez() {
		return filtreParantez;
	}

	public void setFiltreParantez(boolean filtreParantez) {
		this.filtreParantez = filtreParantez;
	}

	public boolean isRedefinedVariable() {
		return isRedefinedVariable;
	}

	public void setRedefinedVariable(boolean isRedefinedVariable) {
		this.isRedefinedVariable = isRedefinedVariable;
	}

	public String getRefereSatirNumarasi() {
		return refereSatirNumarasi;
	}

	public void setRefereSatirNumarasi(String refereSatirNumarasi) {
		this.refereSatirNumarasi = refereSatirNumarasi;
	}

	public String toCustomString() {

		StringBuilder returnStringBuffer;

		AbstractToken maskToken;

		if (this.isEdited()) {
			returnStringBuffer = new StringBuilder();
			returnStringBuffer.append("formatWithMask(" + toCustomSystemVariableString() + ",");
			returnStringBuffer.append("\"");
			int i;
			for (i = 0; i < this.getEditMaskTokenList().size(); i++) {
				maskToken = (AbstractToken) getEditMaskTokenList().get(i);
				returnStringBuffer.append(maskToken.getDeger());
			}
			returnStringBuffer.append("\"");
			returnStringBuffer.append(")");
			return returnStringBuffer.toString();
		}
		if(this.isSystemVariable){
			return toCustomSystemVariableString();
			
		}
		if(this.tip.equals(TokenTipi.Sayi)){
			return this.getDeger().toString();
		}
		return this.getDeger().toString().replaceAll("-", "_");
	}
	
	public String toCustomSystemVariableString() {
		
		if(this.isSystemVariable){
			StringBuilder returnStringBuffer = new StringBuilder();
		
			returnStringBuffer.append("getSystemVariableStr(\""+getDeger().toString().replaceAll("-", "_")+"\")");
			
			return returnStringBuffer.toString();
		}else{
			return getDeger().toString().replaceAll("-", "_");
		}
	}

	public boolean isPojosAllItems() {
		return isPojosAllItems;
	}

	public void setPojosAllItems(boolean isPojosAllItems) {
		this.isPojosAllItems = isPojosAllItems;
	}

	public boolean isPojoSubTableCount() {
		return isPojoSubTableCount;
	}

	public void setPojoSubTableCount(boolean isPojoSubTableCount) {
		this.isPojoSubTableCount = isPojoSubTableCount;
	}

	public String getSynoynmsRealTableName() {
		return synoynmsRealTableName;
	}

	public void setSynoynmsRealTableName(String orjinalTableName) {
		this.synoynmsRealTableName = orjinalTableName;
	}

	public boolean isKarakter(char c) {
		if(this.getTip().equals(TokenTipi.Karakter)&&
				this.getDeger().equals(c)){
			return true;
		}
		return false;
	}
	
	public boolean isKarakter(String karakterString) {
		if(this.getTip().equals(TokenTipi.Karakter)&&
				this.getDeger().equals(karakterString)){
			return true;
		}
		return false;
	}
	
	public boolean isKarakter() {
		if(this.getTip().equals(TokenTipi.Karakter)){
			return true;
		}
		return false;
	}

	public AbstractToken getPojosDimension() {
		return pojosDimension;
	}

	public void setPojosDimension(AbstractToken pojosDimension) {
		this.pojosDimension = pojosDimension;
	}

	public boolean isOzelKelime(String ozelKelime) {
		if(this.getTip().equals(TokenTipi.OzelKelime)&&
				this.getDeger().equals(ozelKelime)){
			return true;
		}
		return false;
	}
	
	public boolean isOzelKelime() {
		if(this.getTip().equals(TokenTipi.OzelKelime)){
			return true;
		}
		return false;
	}

	public boolean isKelime(String kelime) {
		if(this.getTip().equals(TokenTipi.Kelime)&&
				this.getDeger().equals(kelime)){
			return true;
		}
		return false;
	}
	
	public boolean isOneOfKelime(String... kelime) {
		for(int i=0;i<kelime.length;i++){
			if(this.getDeger().toString().equals(kelime[i].toString())){
				return true;
			}
		}
		return false;
	}
	

	public AbstractToken getInputIPParameters() {
		return inputIPParameters;
	}



	public void setInputIPParameters(AbstractToken inputIPParameters) {
		this.inputIPParameters = inputIPParameters;
	}

	public RedefinedColumn getRedefinedColumn() {
		return redefinedColumn;
	}



	public void setRedefinedColumn(RedefinedColumn redefinedColumn) {
		this.redefinedColumn = redefinedColumn;
	}

	public boolean isColumnRedefiner() {
		return columnRedefiner;
	}

	public void setColumnRedefiner(boolean columnRedefiner) {
		this.columnRedefiner = columnRedefiner;
	}

	public String getTypeNameOfView() {
		if(typeNameOfView!=null){
			return typeNameOfView;
		}else{
			return deger.toString();
		}
	}

	public void setTypeNameOfView(String typeNameOfView) {
		this.typeNameOfView = typeNameOfView;
	}

	public boolean isKelime() {
		if(this.getTip().equals(TokenTipi.Kelime)){
			return true;
		}
		return false;
	}
	
	public boolean isSayi() {
		if(this.getTip().equals(TokenTipi.Sayi)){
			return true;
		}
		return false;
	}

	public boolean isSayi(int i) {
		if(this.getTip().equals(TokenTipi.Sayi) && Integer.parseInt(this.getDeger().toString())==i){
			return true;
		}
		return false;
	}

	public boolean isArray() {
		if(this.getTip().equals(TokenTipi.Array)){
			return true;
		}
		return false;
	}

	public boolean isRedefinedVariableDimensionToSimple() {
		return isRedefinedVariableDimensionToSimple;
	}

	public void setRedefinedVariableDimensionToSimple(boolean isRedefinedVariableDimensionToSimple) {
		this.isRedefinedVariableDimensionToSimple = isRedefinedVariableDimensionToSimple;
	}

	public boolean isConditionJoiner() {
		return conditionJoiner;
	}

	public void setConditionJoiner(boolean conditionJoiner) {
		this.conditionJoiner = conditionJoiner;
	}

/*
 * 	currentToken.setConditionJoiner(true);
				}else if(currentToken.getDeger().equals('=')&& (!previousToken.getDeger().equals('>') && !previousToken.getDeger().equals('<'))){ //>= yada <= için işlem yapma
					currentToken.setDeger("==");
					currentToken.setConditionOperator(true);
				}else if(currentToken.getDeger().equals("EQ")){
					currentToken.setDeger("==");
					currentToken.setConditionOperator(true);
				}else if(currentToken.getDeger().equals("NE")){
					currentToken.setDeger("!=");
					currentToken.setConditionOperator(true);
				}else if(currentToken.getDeger().equals("GT")){
					currentToken.setDeger(">");
					currentToken.setConditionOperator(true);
				}else if(currentToken.getDeger().equals("GE")){
					currentToken.setDeger(">=");
					currentToken.setConditionOperator(true);
				}else if(currentToken.getDeger().equals("LT")){
					currentToken.setDeger("<");
					currentToken.setConditionOperator(true);
				}else if(currentToken.getDeger().equals("LE")){
					currentToken.setDeger("<=");
					currentToken.setConditionOperator(true);
				}else if(currentToken.getDeger().equals("NOT")){
					currentToken.setDeger("!");
				}else if(currentToken.getDeger().equals('^')){
					nextToken=conditionList.get(index+1);
					if(nextToken.getDeger().equals('=')){
						currentToken.setConditionOperator(true);
						currentToken.setDeger("!=");
 */

	public boolean isConditionOperator() {
		if(		this.getDeger().equals("=")
				||this.getDeger().equals('=')
				||this.getDeger().equals(">")
				||this.getDeger().equals('>')
				||this.getDeger().equals(">=")
				||this.getDeger().equals("<")
				||this.getDeger().equals('<')
				||this.getDeger().equals("<=")
				||this.getDeger().equals("==")
				||this.getDeger().equals("!=")
				||this.getDeger().equals("EQ")
				||this.getDeger().equals("NE")
				||this.getDeger().equals("GT")
				||this.getDeger().equals("GE")
				||this.getDeger().equals("LT")
				||this.getDeger().equals("LE")
				||this.getDeger().equals("NE")
				||this.getDeger().equals("NE")
				||this.getDeger().equals("NE")
				||this.getDeger().equals(">")
				||this.getDeger().equals("<")){
			return true;
		}
		return false;
	}



	public AbstractToken getInputEMParameters() {
		return inputEMParameters;
	}

	public void setInputEMParameters(AbstractToken inputEMParameters) {
		this.inputEMParameters = inputEMParameters;
	}

	public boolean isNotOperator(){
		if(this.isOzelKelime("NOT")|| this.isOzelKelime("!")|| this.isKarakter("!")|| this.isKarakter('!')){
			return true;
		}
		return false;
	}
	
	public boolean controlOpenParantez() {
		if(isKarakter('(')){
			return true;
		}
		return false;
	}
	


	public boolean controlCloseParantez() {
		if(isKarakter(')')){
			return true;
		}
		return false;
	}

	public boolean isInputParameters() {
		return isInputParameters;
	}

	public void setInputParameters(boolean isInputParameters) {
		this.isInputParameters = isInputParameters;
	}
	
	public boolean isVisualToken() {
		return visualToken;
	}

	public void setVisualToken(boolean visualToken) {
		this.visualToken = visualToken;
	}

	public boolean isVisualEnder() {
		if(this.visualToken && this.isOzelKelime() && this.getDeger().toString().startsWith("END")){
			return true;
		}
		return false;
	}

	public boolean isSatirBasi() {
		if(this.getTip().equals(TokenTipi.SatirBasi)){
			return true;
		}
		return false;
	}

	public boolean isKelime(String... kelime) {
		for(int i=0; i<kelime.length; i++){
			if(this.isKelime() && this.deger.equals(kelime)){
				return true;
			}
		}
		return false;
	}

	public boolean isNoktaToken() {
		if(this.getTip().equals(TokenTipi.Nokta)){
			return true;
		}
		return false;
	}

	public boolean isOneOfOzelKelime(String... ozKelime) {
		for(int i=0; i<ozKelime.length; i++){
			if(this.isOzelKelime() && this.deger.equals(ozKelime[i])){
				return true;
			}
		}
		return false;
	}

	public boolean isAmpersand() {
		return isAmpersand;
	}

	public void setAmpersand(boolean isAmpersand) {
		this.isAmpersand = isAmpersand;
	}
	
	

}
