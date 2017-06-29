package tr.com.vbt.cobol.parser.file.patern;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.file.ElementReadFile;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.patern.AbstractPattern;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.KelimeToken;
import tr.com.vbt.token.KeyValueOzelKelimeToken;
import tr.com.vbt.token.OzelKelimeToken;

/**
 *READ file-name RECORD INTO ws-file-structure
   KEY IS rec-key
   INVALID KEY DISPLAY 'Invalid Key'
   NOT INVALID KEY DISPLAY 'Record Details: ' ws-file-structure
END-READ.

 *
 */

//Gerçek patern aşağıda. Diğerleri child.
//READ file-name NEXT RECORD INTO ws-file-structure

public class PaternReadFileRandom extends AbstractPattern{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//AbstractPattern asp =new PaternAdd();

	}
	

	public PaternReadFileRandom() {
		super();
		
		//READ
		AbstractToken astKeyword=new OzelKelimeToken("READ", 0, 0, 0);
		patternTokenList.add(astKeyword);
		
		
		//dataToDisplay
		AbstractToken astSource=new KelimeToken<String>();
		astSource.setSourceFieldName("filename");
		patternTokenList.add(astSource);
		
		//RECORD_KEY_IS
		AbstractToken astRecKey=new KeyValueOzelKelimeToken("RECORD_KEY_IS","", 0, 0, 0);
		astRecKey.setOptional(true);
		astRecKey.setSourceFieldName("RECORD_KEY_IS");
		patternTokenList.add(astRecKey);
		
		//NEXT
		AbstractToken astNext=new OzelKelimeToken("NEXT", 0, 0, 0);
		astNext.setOptional(true);   //READ VCCFILE RECORD KEY IS VCC-KEY: Örneğin burada Optional 
		patternTokenList.add(astNext);
		
		//RECORD
		AbstractToken astRecord=new OzelKelimeToken("RECORD", 0, 0, 0);
		astRecord.setOptional(true);
		patternTokenList.add(astRecord);
		
		//INTO
		AbstractToken astInto=new OzelKelimeToken("INTO", 0, 0, 0);
		astInto.setOptional(true);
		patternTokenList.add(astInto);
		
		//ws-file-structure
		AbstractToken astFileStructure=new KelimeToken<String>();
		astFileStructure.setOptional(true);
		astFileStructure.setSourceFieldName("fileStructure");
		patternTokenList.add(astFileStructure);
		
	}
	
	@Override
	public AbstractCommand createElement(){
		ElementReadFile elementDisplay = new ElementReadFile(ReservedCobolKeywords.READ_FILE,"PROCEDURE_DIVISION.*.READ_FILE");
		return elementDisplay;
	}


	@Override
	public void setTokenToElement(AbstractCommand matchedCommand,
			AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {
		
		ElementReadFile matchedCommandAdd=(ElementReadFile) matchedCommand;
		
		super.setSatirNumarasi(matchedCommand,currentTokenForMatch, abstractTokenInPattern);if(abstractTokenInPattern.getSourceFieldName()==null){
		
		}else if(abstractTokenInPattern.getSourceFieldName().equals("filename")){
			matchedCommandAdd.setFileName((String) currentTokenForMatch.getDeger());
			matchedCommandAdd.getParameters().put("filename", matchedCommandAdd.getFileName());
		}else if(abstractTokenInPattern.getSourceFieldName().equals("fileStructure")){
			matchedCommandAdd.setFileStructure((String) currentTokenForMatch.getDeger());
			matchedCommandAdd.getParameters().put("fileStructure", matchedCommandAdd.getFileStructure());
		}else if(abstractTokenInPattern.getSourceFieldName().equals("RECORD_KEY_IS")){
			matchedCommandAdd.setRecordKeyIs((String) currentTokenForMatch.getDeger());
			matchedCommandAdd.getParameters().put("RECORD_KEY_IS", matchedCommandAdd.getRecordKeyIs());
		}
	}
		



	

	
	
	

	
	
	
	

}
