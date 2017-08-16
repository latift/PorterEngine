package tr.com.vbt.java.database;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tr.com.vbt.java.AbstractJavaElement;
import tr.com.vbt.java.general.JavaClassElement;
import tr.com.vbt.java.general.JavaConstants;
import tr.com.vbt.java.util.Utility;
import tr.com.vbt.java.utils.ConvertUtilities;
import tr.com.vbt.java.utils.JavaWriteUtilities;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.token.AbstractToken;
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
	
	@Override
	public boolean writeJavaToStream() throws Exception{ super.writeJavaToStream();

		AbstractToken curToken, intoToken = null;
		
		try {
			queryTokenList = (List<AbstractToken>) this.parameters.get("queryTokenList");
			
			if(queryTokenList==null){
				JavaClassElement.javaCodeBuffer.append("//TODO ENGINE: SQL HATASI ");
				return true;
			}
			for(int i=0; i<queryTokenList.size();i++){
				
				curToken=queryTokenList.get(i);
				
				if(curToken.getTip().equals(TokenTipi.OzelKelime)&& curToken.getDeger().equals(ReservedNaturalKeywords.INTO)){
					if(i+1<queryTokenList.size()){
						intoToken=queryTokenList.get(i+1);
						queryTokenList.remove(i+1); //INTO value
						queryTokenList.remove(i);   //INTO
					}
						break;
				}
			}
			
			JavaClassElement.javaCodeBuffer.append(intoToken.getDeger());	
			JavaClassElement.javaCodeBuffer.append("=runPreparedStatementLong"+JavaConstants.OPEN_NORMAL_BRACKET);
			JavaClassElement.javaCodeBuffer.append("\"Select ");
			
			for(int i=0; i<queryTokenList.size();i++){
				
				curToken=queryTokenList.get(i);
				
					
					/*if(curToken.getTip().equals(TokenTipi.OzelKelime)&&
							(curToken.getDeger().equals(ReservedNaturalKeywords.AND)||
									curToken.getDeger().equals(ReservedNaturalKeywords.OR)		
									)){
						// Add +"
						JavaClassElement.javaCodeBuffer.append("+\" ");
					}*/
					
					if(curToken.isPojoVariable()){
						JavaClassElement.javaCodeBuffer.append("\"+ ");
						
						JavaClassElement.javaCodeBuffer.append(JavaWriteUtilities.toCustomString(curToken));
						JavaClassElement.javaCodeBuffer.append("+\"");
						
					}else{
						JavaClassElement.javaCodeBuffer.append(JavaWriteUtilities.toCustomString(curToken));
					}
					/*
					if(curToken.isPojoVariable()){
						JavaClassElement.javaCodeBuffer.append(Utility.pojoNameToPojoDotColumnName(curToken));
					}else if(curToken.isRecordVariable()){
						JavaClassElement.javaCodeBuffer.append(Utility.recordNameToRecordDotRecordFieldName(curToken));
					}else if(curToken.isConstantVariableWithQuota()){
						JavaClassElement.javaCodeBuffer.append("\""+curToken.getDeger()+"\"");
					}else{
						JavaClassElement.javaCodeBuffer.append(curToken.getDeger());
					}
					*/
					/*if(curToken.getTip().equals(TokenTipi.Karakter)&& curToken.getDeger().equals('=')){
						// Add "+
						JavaClassElement.javaCodeBuffer.append("\"+");
					}
					
					JavaClassElement.javaCodeBuffer.append(" ");*/
	
			}
			
			JavaClassElement.javaCodeBuffer.append("\"");
			JavaClassElement.javaCodeBuffer.append(",\"Long\"");
			JavaClassElement.javaCodeBuffer.append(JavaConstants.CLOSE_NORMAL_BRACKET+ JavaConstants.DOT_WITH_COMMA+ JavaConstants.NEW_LINE);
			
		} catch (Exception e) {
			logger.debug("//Conversion Error"+this.getClass()+this.getSourceCode().getSatirNumarasi()+this.getSourceCode().getCommandName());
			JavaClassElement.javaCodeBuffer.append("/*Conversion Error"+this.getClass()+this.getSourceCode().getSatirNumarasi()
					+this.getSourceCode().getCommandName()+"*/"+JavaConstants.NEW_LINE);
			logger.error("//Conversion Error:"+e.getMessage(), e); 
			ConvertUtilities.writeconversionErrors(e, this); 
		}
		return true;
	}
}
