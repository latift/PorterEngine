package tr.com.vbt.cobol.parser.file.patern;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.file.ElementDeleteFile;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.patern.AbstractPattern;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.KelimeToken;
import tr.com.vbt.token.OzelKelimeToken;

/**
 *   Delete Verb:
DELETE file-name RECORD
   INVALID KEY DISPLAY 'Invalid Key'
   NOT INVALID KEY DISPLAY 'Record Deleted'
END-DELETE.
 *
 */
public class PaternDeleteFile extends AbstractPattern{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//AbstractPattern asp =new PaternAdd();

	}
	

	public PaternDeleteFile() {
		super();
		
		//DELETE
		AbstractToken starter=new OzelKelimeToken("DELETE", 0, 0, 0);
		patternTokenList.add(starter);
		
		//fileName
		AbstractToken astSource=new KelimeToken<String>();
		astSource.setSourceFieldName("fileName");
		patternTokenList.add(astSource);
		
		//DELETE
		AbstractToken delete=new OzelKelimeToken("RECORD", 0, 0, 0);
		patternTokenList.add(delete);
		
	}
	
	@Override
	public AbstractCommand createElement(){
		ElementDeleteFile elementDisplay = new ElementDeleteFile(ReservedCobolKeywords.DELETE_FILE,"PROCEDURE_DIVISION.*.DELETE");
		return elementDisplay;
	}


	@Override
	public void setTokenToElement(AbstractCommand matchedCommand,
			AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {
		
		ElementDeleteFile matchedCommandAdd=(ElementDeleteFile) matchedCommand;
		
		super.setSatirNumarasi(matchedCommand,currentTokenForMatch, abstractTokenInPattern);if(abstractTokenInPattern.getSourceFieldName()==null){
		
		}else if(abstractTokenInPattern.getSourceFieldName().equals("fileName")){
			matchedCommandAdd.setFileName((String) currentTokenForMatch.getDeger());
			matchedCommandAdd.getParameters().put("filename", matchedCommandAdd.getFileName());
		}	
	}
		





	
	
	

	
	
	
	

}
