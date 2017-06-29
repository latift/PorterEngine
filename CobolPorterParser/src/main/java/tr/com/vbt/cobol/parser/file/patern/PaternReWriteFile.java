package tr.com.vbt.cobol.parser.file.patern;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.file.ElementReWriteFile;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.patern.AbstractPattern;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.KelimeToken;
import tr.com.vbt.token.OzelKelimeToken;

/**
 * 
 * 
 * WRITE record-buffer [FROM ws-file-structure]
	   INVALID KEY DISPLAY 'Invalid Key'
	   NOT INVALID KEY DISPLAY 'Record Inserted'
   END-WRITE.
 *
 */
public class PaternReWriteFile extends AbstractPattern{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//AbstractPattern asp =new PaternAdd();

	}
	

	public PaternReWriteFile() {
		super();
		
		//READ
		AbstractToken astKeyword=new OzelKelimeToken("REWRITE", 0, 0, 0);
		patternTokenList.add(astKeyword);
		
		
		//dataToDisplay
		AbstractToken astSource=new KelimeToken<String>();
		astSource.setSourceFieldName("recordBuffer");
		patternTokenList.add(astSource);
		
		//FROM
		AbstractToken astInto=new OzelKelimeToken("FROM", 0, 0, 0);
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
		ElementReWriteFile elementDisplay = new ElementReWriteFile(ReservedCobolKeywords.REWRITE_FILE,"PROCEDURE_DIVISION.*.REWRITE_FILE");
		return elementDisplay;
	}


	@Override
	public void setTokenToElement(AbstractCommand matchedCommand,
			AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {
		
		ElementReWriteFile matchedCommandAdd=(ElementReWriteFile) matchedCommand;
		
		super.setSatirNumarasi(matchedCommand,currentTokenForMatch, abstractTokenInPattern);if(abstractTokenInPattern.getSourceFieldName()==null){
		
		}else if(abstractTokenInPattern.getSourceFieldName().equals("recordBuffer")){
			matchedCommandAdd.setRecordBuffer((String) currentTokenForMatch.getDeger());
			matchedCommandAdd.getParameters().put("recordBuffer", matchedCommandAdd.getRecordBuffer());
		}else if(abstractTokenInPattern.getSourceFieldName().equals("fileStructure")){
			matchedCommandAdd.setFileStructure((String) currentTokenForMatch.getDeger());
			matchedCommandAdd.getParameters().put("fileStructure", matchedCommandAdd.getFileStructure());
		}
	}
		





	
	
	

	
	
	
	

}
