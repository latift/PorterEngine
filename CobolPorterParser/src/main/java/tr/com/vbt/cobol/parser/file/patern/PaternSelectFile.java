package tr.com.vbt.cobol.parser.file.patern;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.file.ElementSelectFile;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.patern.AbstractPattern;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.KelimeToken;
import tr.com.vbt.token.KeyValueOzelKelimeToken;
import tr.com.vbt.token.OzelKelimeToken;

/**
SELECT VCCFILE ASSIGN TO G30001
ORGANIZATION IS INDEXED
ACCESS MODE IS DYNAMIC
FILE STATUS IS FS1
RECORD KEY IS VCC-KEY
 */
public class PaternSelectFile extends AbstractPattern{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//AbstractPattern asp =new PaternAdd();

	}
	

	public PaternSelectFile() {
		super();
		
		//SELECT
		AbstractToken astKeyword1=new OzelKelimeToken(ReservedCobolKeywords.SELECT, 0, 0, 0);
		patternTokenList.add(astKeyword1);
		
		
		//FILE_NAME
		AbstractToken astKeyword1_1=new KelimeToken<String>();
		astKeyword1_1.setSourceFieldName(ReservedCobolKeywords.FILE_NAME);
		patternTokenList.add(astKeyword1_1);
		
		
		//ASSIGN_TO
		AbstractToken astKeyword6=new KeyValueOzelKelimeToken(ReservedCobolKeywords.ASSIGN_TO,"", 0, 0, 0);
		astKeyword6.setOptional(true);
		astKeyword6.setSourceFieldName(ReservedCobolKeywords.ASSIGN_TO);
		patternTokenList.add(astKeyword6);
				
		//ORGANIZATION IS INDEXED
		AbstractToken astKeyword2=new KeyValueOzelKelimeToken(ReservedCobolKeywords.ORGANIZATION_IS,"", 0, 0, 0);
		astKeyword2.setOptional(true);
		astKeyword2.setSourceFieldName(ReservedCobolKeywords.ORGANIZATION_IS);
		patternTokenList.add(astKeyword2);
		
		//ACCESS MODE IS DYNAMIC
		AbstractToken astKeyword3=new KeyValueOzelKelimeToken(ReservedCobolKeywords.ACCESS_MODE_IS,"", 0, 0, 0);
		astKeyword3.setOptional(true);
		astKeyword3.setSourceFieldName(ReservedCobolKeywords.ACCESS_MODE_IS);
		patternTokenList.add(astKeyword3);
		
		//FILE STATUS IS FS1
		AbstractToken astKeyword4=new KeyValueOzelKelimeToken(ReservedCobolKeywords.FILE_STATUS_IS,"", 0, 0, 0);
		astKeyword4.setOptional(true);
		astKeyword4.setSourceFieldName(ReservedCobolKeywords.FILE_STATUS_IS);
		patternTokenList.add(astKeyword4);
		
		//RECORD KEY IS VCC-KEY
		AbstractToken astKeyword5=new KeyValueOzelKelimeToken(ReservedCobolKeywords.RECORD_KEY_IS,"", 0, 0, 0);
		astKeyword5.setOptional(true);
		astKeyword5.setSourceFieldName(ReservedCobolKeywords.RECORD_KEY_IS);
		patternTokenList.add(astKeyword5);
		
	}
	
	@Override
	public AbstractCommand createElement(){
		ElementSelectFile createdElement = new ElementSelectFile(ReservedCobolKeywords.SELECT_FILE,"FILE_CONTROL.*.SELECT_FILE");
		return createdElement;
	}


	@Override
	public void setTokenToElement(AbstractCommand matchedCommand,
			AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {
		
		ElementSelectFile matchedCommandAdd=(ElementSelectFile) matchedCommand;
		
		super.setSatirNumarasi(matchedCommand,currentTokenForMatch, abstractTokenInPattern);if(abstractTokenInPattern.getSourceFieldName()==null){
		
		}else if(abstractTokenInPattern.getSourceFieldName().equals(ReservedCobolKeywords.FILE_NAME)){
			matchedCommandAdd.setFileName(((String) currentTokenForMatch.getDeger()));
			matchedCommandAdd.getParameters().put(ReservedCobolKeywords.FILE_NAME, matchedCommandAdd.getFileName());
			
		}else if(abstractTokenInPattern.getSourceFieldName().equals(ReservedCobolKeywords.ASSIGN_TO)){
			matchedCommandAdd.setAssignedTo(((String) ((KeyValueOzelKelimeToken)currentTokenForMatch).getValue()));
			matchedCommandAdd.getParameters().put(ReservedCobolKeywords.ASSIGN_TO, matchedCommandAdd.getAssignedTo());
			
		}else if(abstractTokenInPattern.getSourceFieldName().equals(ReservedCobolKeywords.ORGANIZATION_IS)){
			matchedCommandAdd.setOrganizationType(((String) ((KeyValueOzelKelimeToken)currentTokenForMatch).getValue()));
			matchedCommandAdd.getParameters().put(ReservedCobolKeywords.ORGANIZATION_IS, matchedCommandAdd.getOrganizationType());
			
		}else if(abstractTokenInPattern.getSourceFieldName().equals(ReservedCobolKeywords.ACCESS_MODE_IS)){
			matchedCommandAdd.setAccessMode(((String) ((KeyValueOzelKelimeToken)currentTokenForMatch).getValue()));
			matchedCommandAdd.getParameters().put(ReservedCobolKeywords.ACCESS_MODE_IS, matchedCommandAdd.getAccessMode());
		}
		
		else if(abstractTokenInPattern.getSourceFieldName().equals(ReservedCobolKeywords.FILE_STATUS_IS)){
			matchedCommandAdd.setFileStatus(((String) ((KeyValueOzelKelimeToken)currentTokenForMatch).getValue()));
			matchedCommandAdd.getParameters().put(ReservedCobolKeywords.FILE_STATUS_IS, matchedCommandAdd.getAccessMode());
		}
		
		else if(abstractTokenInPattern.getSourceFieldName().equals(ReservedCobolKeywords.RECORD_KEY_IS)){
			matchedCommandAdd.setRecordKey(((String) ((KeyValueOzelKelimeToken)currentTokenForMatch).getValue()));
			matchedCommandAdd.getParameters().put(ReservedCobolKeywords.RECORD_KEY_IS, matchedCommandAdd.getRecordKey());
		}
	}
		
	





	
	
	

	
	
	
	

}
