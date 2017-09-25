package tr.com.vbt.java.database;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tr.com.vbt.java.AbstractJavaElement;
import tr.com.vbt.java.database.AbsctractConditionalJavaElement.Filter;
import tr.com.vbt.java.general.JavaClassElement;
import tr.com.vbt.java.general.JavaConstants;
import tr.com.vbt.java.util.Utility;
import tr.com.vbt.java.utils.ConvertUtilities;
import tr.com.vbt.java.utils.JavaWriteUtilities;
import tr.com.vbt.lexer.ConversionLogModel;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.KelimeToken;
import tr.com.vbt.token.NoktaToken;
import tr.com.vbt.token.TokenTipi;



public class JavaSqlSelectElement extends  AbstractJavaElement{
	
	final static Logger logger = LoggerFactory.getLogger(JavaSqlSelectElement.class);

	/**
	 * 2946 SELECT MAX(ALTSIRA) INTO MAXSIRA FROM IDGIDBS-TESKI WHERE                                                                      
 2948     HESCINSI=NHESCINS AND DOVIZ=PDOVIZ AND MUSNO1=PDMUSNO1(I)                                                                  
 2950     AND HESNO=PHESNO(I) AND MUSNO2=PDMUSNO2(I) AND                                                                             
 2952     KRX_KOD=PDHESNIT(I) AND SIRA=PBIS(I) AND VEFAT^='V' 
 
 --> MAXSIRA=runPreparedStatement("SELECT MAX(ALTSIRA) INTO MAXSIRA FROM IDGIDBS-TESKI WHERE  HESCINSI="+NHESCINS+" AND DOVIZ="+PDOVIZ+" AND MUSNO1=PDMUSNO1(I)                                                                  
 AND HESNO=PHESNO(I) AND MUSNO2=PDMUSNO2(I) AND                                                                             
 KRX_KOD=PDHESNIT(I) AND SIRA=PBIS(I) AND VEFAT^='V' ");
 
	 */
	private List<AbstractToken> queryTokenList=new ArrayList<AbstractToken>();
	
	AbstractToken curToken, intoToken = null, tableToken;
	
	List<AbstractToken> intoTokenList=new ArrayList<>();
	
	StringBuffer sqlString;
	
	String sqlStringKey;
	
	SQLStringObject sqlStringOjbect;
	
	@Override
	public boolean writeJavaToStream() throws Exception{ 
		super.writeJavaToStream();

		try {
			queryTokenList = (List<AbstractToken>) this.parameters.get("queryTokenList");
			
			convertConditions(); // Tek token olmayan filtre operatorlerini tek tokena düşürür.
			
			if(queryTokenList==null || queryTokenList.size()==0){
				JavaClassElement.javaCodeBuffer.append("//TODO: JavaSqlSelectElement: queryTokenList null");
				return true;
			}
			defineConditionTokenTypes();
			
			if(isFunctionSQL()){
				correctCountKeyword();
				writeFunctionSQLJavaCode();
			}else if(isDistinctSQL()){
				writeDistinctSQLJavaCode();
			}else if(isPojoSQL()){
				writePojoSQLJavaCode();
			}else{ //Yukardakilerden biri değilse en azından aşağıdaki şekilde yazsın
				writeUnknownTypeSQLJavaCode();
			}
			
			return true;
		} catch (Exception e) {
			logger.debug("//Conversion Error"+this.getClass()+this.getSourceCode().getSatirNumarasi()+this.getSourceCode().getCommandName());
			JavaClassElement.javaCodeBuffer.append("/*Conversion Error"+this.getClass()+this.getSourceCode().getSatirNumarasi()
					+this.getSourceCode().getCommandName()+"*/"+JavaConstants.NEW_LINE);
			logger.error("//Conversion Error:"+e.getMessage(), e); 
			ConvertUtilities.writeconversionErrors(e, this); 
			return true;
		}	
		
		
	}



	private void correctCountKeyword() {
		
		AbstractToken schemaToken;
		
		for(int i=0; i<queryTokenList.size();i++){
			
			curToken=queryTokenList.get(i);
			
			if(curToken.isOzelKelime(ReservedNaturalKeywords.COUNT)|| curToken.isKelime(ReservedNaturalKeywords.COUNT)){
				
				curToken.setDeger("COUNT(*)");
				break;
			}
		}
		
	}



	/*
	 *  2190      SELECT * INTO VIEW MUHSWFT-TBICKODU FROM
		2200              MUHSWFT-TBICKODU WHERE BICKODU LIKE BICA12
		2210          IF NO
		2220             ESCAPE BOTTOM
		2230          END-NOREC
		2240          PERFORM MAP_DIZISI_DEGER_ATA
		2250      END-SELECT
	 */
	/*
 * 	   RESULT_LIST=runPreparedStatementPojoV2("SELECT * FROM MUHSWFT.TBICKODU WHERE BICKODU LIKE 'BIC%'",Muhswfttbickodu.class);
		
	    sqlIterator=RESULT_LIST.iterator();
		while(sqlIterator.hasNext()){
			MUHSWFTTBICKODU=(Muhswfttbickodu) sqlIterator.next();
			
			logger.debug("MUHSWFTTBICKODU.getBankaAdi(): "+MUHSWFTTBICKODU.getBankaAdi());
		
		}
	 */
	private void writePojoSQLJavaCode() throws Exception {
		if(queryTokenList==null){
			JavaClassElement.javaCodeBuffer.append("//TODO ENGINE: SQL HATASI ");
			return;
		}
		
		setIntoFieldsAndRemoveFromSQL();
		
		setTableNameAndPutSchema();
	
		String fieldName=Utility.viewNameToBiggerPojoName(tableToken.getDeger().toString());
		String fieldType=Utility.viewNameToPojoName(tableToken.getDeger().toString());
		
		//DISTINCT_RESULTLIST=runPreparedStatementDistinctV2("Select  DISTINCT EBIMNO , FISNO , ISLEM_TURU , ISLASTUR , ARB_SW FROM IDGIDBS.TSUBE_MUHASEBE WHERE BAKOD = \'"+B+"\'");
		writeParameters();
		
		JavaClassElement.javaCodeBuffer.append("RESULT_LIST");
		JavaClassElement.javaCodeBuffer.append("=runPreparedStatementPojoV2"+JavaConstants.OPEN_NORMAL_BRACKET);

		writeSQL();
		
		registerSQLAndWriteKeyToJava();
		
		JavaClassElement.javaCodeBuffer.append(","+fieldType+".class, params");
		
		JavaClassElement.javaCodeBuffer.append(JavaConstants.CLOSE_NORMAL_BRACKET+ JavaConstants.DOT_WITH_COMMA+ JavaConstants.NEW_LINE);
		
		//  sqlIterator=RESULT_LIST.iterator();
		JavaClassElement.javaCodeBuffer.append("sqlIterator=RESULT_LIST.iterator()"+ JavaConstants.DOT_WITH_COMMA+ JavaConstants.NEW_LINE);
		JavaClassElement.javaCodeBuffer.append("while(sqlIterator.hasNext()){"+ JavaConstants.DOT_WITH_COMMA+ JavaConstants.NEW_LINE);
		
		JavaClassElement.javaCodeBuffer.append(fieldName+"=("+fieldType+") sqlIterator.next()"+ JavaConstants.DOT_WITH_COMMA+ JavaConstants.NEW_LINE);
		
		this.writeChildrenJavaToStream();
		JavaClassElement.javaCodeBuffer.append(JavaConstants.DOT_WITH_COMMA+ JavaConstants.NEW_LINE);
		JavaClassElement.javaCodeBuffer.append("} //Distinct SQL While End"+JavaConstants.DOT_WITH_COMMA+ JavaConstants.NEW_LINE);
	
	return;
		
	}

	/*
	 *  	0834 SELECT DISTINCT EBIMNO, FISNO, ISLEM_TURU, ISLASTUR,ARB_SW
		0836     INTO EBIM_NO, ISLEMFIS, ISLTUR, ISL_ASTUR, ARBSW
		0838     FROM IDGIDBS-TSUBE_MUHASEBE
		0840     WHERE REFERANS = PREFERANS AND BAKOD = 'B'
		0842     ORDER BY FISNO, ISLEM_TURU, ISLASTUR,ARB_SW
	 * 
	 * 
 * 		DISTINCT_RESULTLIST=runPreparedStatementDistinctV2("Select  DISTINCT EBIMNO , FISNO , ISLEM_TURU , ISLASTUR , ARB_SW FROM IDGIDBS.TSUBE_MUHASEBE WHERE BAKOD = \'"+B+"\'",TsubeMuhasebe.class);
		sqlIterator=DISTINCT_RESULTLIST.iterator();
		while(sqlIterator.hasNext()){
			distinctResultRecord=(Object[]) sqlIterator.next();
			EBIM_NO =convertResultToLong(distinctResultRecord[0]);
			ISLEMFIS=convertResultToLong(distinctResultRecord[1]);
			ISLEM_TURU=convertResultToString(distinctResultRecord[2]);
	 */
	private void writeDistinctSQLJavaCode() throws Exception{
		if(queryTokenList==null){
			JavaClassElement.javaCodeBuffer.append("//TODO ENGINE: SQL HATASI ");
			return;
		}
		
		setIntoFieldsAndRemoveFromSQL();
		
		setTableNameAndPutSchema();
	
		//DISTINCT_RESULTLIST=runPreparedStatementDistinctV2("Select  DISTINCT EBIMNO , FISNO , ISLEM_TURU , ISLASTUR , ARB_SW FROM IDGIDBS.TSUBE_MUHASEBE WHERE BAKOD = \'"+B+"\'");
		
		writeParameters();
		
		JavaClassElement.javaCodeBuffer.append("DISTINCT_RESULTLIST");
		JavaClassElement.javaCodeBuffer.append("=runPreparedStatementDistinctV2"+JavaConstants.OPEN_NORMAL_BRACKET);
		
		writeSQL();
		
		registerSQLAndWriteKeyToJava();
		
		JavaClassElement.javaCodeBuffer.append(", params");
		
		JavaClassElement.javaCodeBuffer.append(JavaConstants.CLOSE_NORMAL_BRACKET+ JavaConstants.DOT_WITH_COMMA+ JavaConstants.NEW_LINE);
		
		JavaClassElement.javaCodeBuffer.append("sqlIterator=DISTINCT_RESULTLIST.iterator()"+ JavaConstants.DOT_WITH_COMMA+ JavaConstants.NEW_LINE);
		JavaClassElement.javaCodeBuffer.append("while(sqlIterator.hasNext()){"+ JavaConstants.DOT_WITH_COMMA+ JavaConstants.NEW_LINE);
		JavaClassElement.javaCodeBuffer.append("distinctResultRecord=(Object[]) sqlIterator.next()"+ JavaConstants.DOT_WITH_COMMA+ JavaConstants.NEW_LINE);
		
		for(int i=0; i<intoTokenList.size();i++){
			intoToken=intoTokenList.get(i);
			if(intoToken.isKarakter(',')){
				intoTokenList.remove(i);
			}
		}
		
		for(int i=0; i<intoTokenList.size();i++){
			//EBIM_NO =convertResultToLong(distinctResultRecord[0]);
			intoToken=intoTokenList.get(i);
			if(intoToken.isPojoVariable()){ // Motor düzeltmesidir. Pojo olmaması gerekirken pojo set ediliyor.
				intoToken=intoToken.getColumnNameToken();
			}
			JavaClassElement.javaCodeBuffer.append(intoToken.getDeger()+"=convertResultToLong(distinctResultRecord["+i+"])"+ JavaConstants.DOT_WITH_COMMA+ JavaConstants.NEW_LINE);
			
		}
		
		this.writeChildrenJavaToStream();
		JavaClassElement.javaCodeBuffer.append(JavaConstants.DOT_WITH_COMMA+ JavaConstants.NEW_LINE);
		JavaClassElement.javaCodeBuffer.append("} //Distinct SQL While End"+JavaConstants.DOT_WITH_COMMA+ JavaConstants.NEW_LINE);
	
	return;
		
	}

	//FROM TSUBE_MUHASEBE
	//--> TableName=TSUBE_MUHASEBE
	private void setTableNameAndPutSchema() {
		
		AbstractToken schemaToken;
		
		for(int i=0; i<queryTokenList.size();i++){
			
			curToken=queryTokenList.get(i);
			
			if(curToken.isOzelKelime(ReservedNaturalKeywords.FROM)){
				tableToken=queryTokenList.get(i+1);
				
				break;
			}
		}

		
		
		
	}

	/**
	 * INTO dan FROM a kadar olanları siler
	 */
	private void setIntoFieldsAndRemoveFromSQL() {
		
		intoTokenList=new ArrayList<>();
		
		for(int i=0; i<queryTokenList.size();i++){
			
			curToken=queryTokenList.get(i);
			
			if(curToken.isOzelKelime(ReservedNaturalKeywords.INTO)){
				
				queryTokenList.remove(i);
				
				while(true){
					
					if(i==queryTokenList.size()){
						break;
					}
					intoToken=queryTokenList.get(i);
					
					if(intoToken.isOzelKelime(ReservedNaturalKeywords.FROM)){
						break;
					}else{
						intoTokenList.add(intoToken);
						queryTokenList.remove(i);
					}
				};
				
			}
			
			
		}
		
		
		
	}

	/*
 *   	 0950   SELECT MAX(HESNO) INTO MAXHESNO FROM IDGIDBS-TSUBEHES
		0960          WHERE HESCINSI=NKS AND DOVIZ=PDOVIZ AND
		0970          HESNO>=BASHESNO AND HESNO<=BITHESNO
		0980          AND GONDERME_SUBE=PSUBE
		0990          AND YOLDAFLAG=4
		1000   END-SELECT
		
		MAXHESNO=runPreparedStatementFunctionV2("Select  MAX ( HESNO ) FROM IDGIDBS.TSUBEHES");
	 */
	private void writeFunctionSQLJavaCode() throws Exception {
		
		if(queryTokenList==null){
			JavaClassElement.javaCodeBuffer.append("//TODO ENGINE: SQL HATASI ");
			return;
		}
		
		setIntoFieldsAndRemoveFromSQL();
		
		setTableNameAndPutSchema();
	
		intoToken=intoTokenList.get(0);
		//DISTINCT_RESULTLIST=runPreparedStatementDistinctV2("Select  DISTINCT EBIMNO , FISNO , ISLEM_TURU , ISLASTUR , ARB_SW FROM IDGIDBS.TSUBE_MUHASEBE WHERE BAKOD = \'"+B+"\'");
		
		writeParameters();
		
		JavaClassElement.javaCodeBuffer.append(JavaWriteUtilities.toCustomString(intoToken));
		JavaClassElement.javaCodeBuffer.append("=runPreparedStatementFunctionV2"+JavaConstants.OPEN_NORMAL_BRACKET);
		
		writeSQL();
		
		registerSQLAndWriteKeyToJava();
		
		JavaClassElement.javaCodeBuffer.append(", params");
		
		JavaClassElement.javaCodeBuffer.append(JavaConstants.CLOSE_NORMAL_BRACKET+ JavaConstants.DOT_WITH_COMMA+ JavaConstants.NEW_LINE);
		
		JavaClassElement.javaCodeBuffer.append("{"+ JavaConstants.DOT_WITH_COMMA+ JavaConstants.NEW_LINE);
		
		this.writeChildrenJavaToStream();
		JavaClassElement.javaCodeBuffer.append(JavaConstants.DOT_WITH_COMMA+ JavaConstants.NEW_LINE);
		JavaClassElement.javaCodeBuffer.append("} //Function SQL End"+JavaConstants.DOT_WITH_COMMA+ JavaConstants.NEW_LINE);
	
		return;
	}
	
	private void writeUnknownTypeSQLJavaCode() throws Exception {
		
		try {
			if(queryTokenList==null){
				JavaClassElement.javaCodeBuffer.append("//TODO ENGINE: SQL HATASI ");
				return;
			}
			
			setIntoFieldsAndRemoveFromSQL();
			
			setTableNameAndPutSchema();

			intoToken=intoTokenList.get(0);
			//DISTINCT_RESULTLIST=runPreparedStatementDistinctV2("Select  DISTINCT EBIMNO , FISNO , ISLEM_TURU , ISLASTUR , ARB_SW FROM IDGIDBS.TSUBE_MUHASEBE WHERE BAKOD = \'"+B+"\'");
			
			writeParameters();
			
			JavaClassElement.javaCodeBuffer.append(JavaWriteUtilities.toCustomString(intoToken));
			JavaClassElement.javaCodeBuffer.append("=runPreparedStatementUnknownTypeV2"+JavaConstants.OPEN_NORMAL_BRACKET);
			
			writeSQL();
			
			registerSQLAndWriteKeyToJava();
			
			JavaClassElement.javaCodeBuffer.append(", params");
			
			JavaClassElement.javaCodeBuffer.append(JavaConstants.CLOSE_NORMAL_BRACKET+ JavaConstants.DOT_WITH_COMMA+ JavaConstants.NEW_LINE);
			
			JavaClassElement.javaCodeBuffer.append("{"+ JavaConstants.DOT_WITH_COMMA+ JavaConstants.NEW_LINE);
			
			this.writeChildrenJavaToStream();
			JavaClassElement.javaCodeBuffer.append(JavaConstants.DOT_WITH_COMMA+ JavaConstants.NEW_LINE);
			JavaClassElement.javaCodeBuffer.append("} //Function SQL End"+JavaConstants.DOT_WITH_COMMA+ JavaConstants.NEW_LINE);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		return;
	}

	/*
	 * 		List<SQLParameter> params=new ArrayList<>();
			params.add(new SQLParameter("NKS", NKS));
			params.add(new SQLParameter("NKS2", NK2S));
	 */	
	private void writeParameters() {
		JavaClassElement.javaCodeBuffer.append("params=new ArrayList<>()"+ JavaConstants.DOT_WITH_COMMA+ JavaConstants.NEW_LINE);
		for(int i=0; i<queryTokenList.size();i++){

			curToken=queryTokenList.get(i);
			
			if(curToken.isFiltreValue() && !curToken.isConstantVariableWithQuota()){
				JavaClassElement.javaCodeBuffer.append("params.add(new SQLParameter(\""+curToken.getFiltreNameToken().getDeger().toString()+"\", "+curToken.getDeger().toString()+"))"+ JavaConstants.DOT_WITH_COMMA+ JavaConstants.NEW_LINE);
			}
		}
		
	}



	private void writeSQL() throws Exception{
		
		sqlString=new StringBuffer();
		
		sqlString.append("\"Select ");
		
		for(int i=0; i<queryTokenList.size();i++){
			
				curToken=queryTokenList.get(i);
			
				sqlString.append(" ");
				
				logger.debug(curToken.getDeger().toString());
				
				//Parametre icin
				
				if(curToken.isConstantVariableWithQuota()){
						sqlString.append("'"+curToken.getDeger().toString()+"'");
				
				}else if(curToken.isFiltreValue() && !curToken.isConstantVariableWithQuota()){
					sqlString.append(":");
					sqlString.append(curToken.getFiltreNameToken().getDeger().toString());
				}else if(curToken.getSchemaNameToken()!=null){ //TAblo ismi ise.
					sqlString.append(curToken.getSchemaNameToken().getDeger().toString()+"."+curToken.getDeger().toString());
				
				}else {
					sqlString.append(JavaWriteUtilities.toCustomString(curToken));
				}
				
			
		}
		
		sqlString.append("\"");
	}
	private void registerSQLAndWriteKeyToJava() {

		String fileName=ConversionLogModel.getInstance().getFileName();
		
		int SatirNo=queryTokenList.get(0).getSatirNumarasi();
		
		String sqlSentence=sqlString.toString();
		
		String moduleName=ConversionLogModel.getInstance().getModule();
		
		SQLStringObject sqlString=new SQLStringObject(fileName,SatirNo , sqlSentence,moduleName);
		
		sqlStringKey=ConversionLogModel.getInstance().getFileName()+"_"+queryTokenList.get(0).getSatirNumarasi();
		
		SQLManager.registerSQL(sqlStringKey, sqlString);
		
		JavaClassElement.javaCodeBuffer.append("IsciDovizleriSQL."+sqlStringKey);
		
		
	}

	private boolean isFunctionSQL() {
		try {
			AbstractToken sqlFirstKeyword= queryTokenList.get(0);
			if(sqlFirstKeyword.isSQLFunctionKeyword(sqlFirstKeyword)){
				return true;
			}
		} catch (Exception e) {
			logger.debug(e.getMessage(),e);
			return false;
		}
		return false;
	}
	
	private boolean isDistinctSQL() {
		try {
			AbstractToken sqlFirstKeyword= queryTokenList.get(0);
			if(sqlFirstKeyword.isOzelKelime("DISTINCT") || sqlFirstKeyword.isKelime("DISTINCT")){
				return true;
			}
		} catch (Exception e) {
			logger.debug(e.getMessage(),e);
			return false;
		}
		return false;
	}
	
	private boolean isPojoSQL() {
		try {
			AbstractToken sqlFirstKeyword= queryTokenList.get(0);
			if(sqlFirstKeyword.isKarakter('*')){
				return true;
			}
		} catch (Exception e) {
			logger.debug(e.getMessage(),e);
			return false;
		}
		return false;
	}
	
	
	private void defineConditionTokenTypes() {
		AbstractToken currentToken = null;
		
		/**
		 * Filtrename ve FiltreValue birbirini takip eder.
		 */
		boolean isFiltrename=true;
		
		boolean whereReached=false;
		
		boolean whereClauseEnderReached=false;
		
		AbstractToken filtreNameToken = null;
		
		for (int index=0; index<queryTokenList.size();index++ ) {
			
			currentToken=queryTokenList.get(index);
			
			if(currentToken.isOzelKelime(ReservedNaturalKeywords.WHERE) || currentToken.isKelime(ReservedNaturalKeywords.WHERE)){
				whereReached=true;
				continue;
			}
			
			if(currentToken.isOneOfOzelKelime(ReservedNaturalKeywords.ORDER_BY,ReservedNaturalKeywords.SORTED_BY, ReservedNaturalKeywords.GROUP_BY)||
					currentToken.isOneOfKelime(ReservedNaturalKeywords.ORDER_BY,ReservedNaturalKeywords.SORTED_BY, ReservedNaturalKeywords.GROUP_BY)){
				whereClauseEnderReached=true;
			}
			
			if(!whereReached){
				continue;
			}
			
			if(whereClauseEnderReached){
				break;
			}
			
			if(currentToken.isSayi()){
				currentToken.setConstantVariableWithQuota(true);
			}
			
			if(currentToken.isOneOfOzelKelime(ReservedNaturalKeywords.AND,ReservedNaturalKeywords.OR) || currentToken.isOneOfKelime(ReservedNaturalKeywords.AND,ReservedNaturalKeywords.OR)){
				currentToken.setFiltrejoiner(true);
			}else if(currentToken.isKarakter() || currentToken.isKelime("LIKE")){
				if(currentToken.isKarakter('(')||currentToken.isKarakter(')')){
					currentToken.setFiltreParantez(true);
				}else{
					currentToken.setFilterOPerator(true);
				}
			}else {
				if(isFiltrename){
					currentToken.setFilterName(true);
					filtreNameToken=currentToken;
					isFiltrename=false;
				}else{
					currentToken.setFiltreValue(true);	
					currentToken.setFiltreNameToken(filtreNameToken);
					isFiltrename=true;
				}
			}
		}
		
	}
	
	
	protected List<AbstractToken> convertConditions() {

		List<AbstractToken> resultList = new ArrayList<AbstractToken>();

		AbstractToken currentToken = null, nextToken = null, previousToken = null;

		System.out.println(queryTokenList);
		
		if(queryTokenList==null){
			return null;
		}

		for (int index = 0; index < queryTokenList.size(); index++) {

			currentToken = queryTokenList.get(index);

			if (index > 0) {
				previousToken = queryTokenList.get(index - 1);
			}
			//
			if (currentToken.getDeger().equals('^')) {
				nextToken = queryTokenList.get(index + 1);
				if (nextToken.getDeger().equals('=')) {
					currentToken.setDeger("^=");
					queryTokenList.remove(index + 1);
				}
			} else if (currentToken.getDeger().equals('>')) {
				nextToken = queryTokenList.get(index + 1);
				if (nextToken.getDeger().equals('=')) {
					currentToken.setDeger(">=");
					queryTokenList.remove(index + 1);
				}
			} else if (currentToken.getDeger().equals('<')) {
				nextToken = queryTokenList.get(index + 1);
				if (nextToken.getDeger().equals('=')) {
					currentToken.setDeger("<=");
					queryTokenList.remove(index + 1);
				}
			}
			resultList.add(currentToken);
		}
		return resultList;
	}


}
