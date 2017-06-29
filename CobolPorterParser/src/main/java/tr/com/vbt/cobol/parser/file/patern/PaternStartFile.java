package tr.com.vbt.cobol.parser.file.patern;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.file.ElementStartFile;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.patern.AbstractPattern;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.KarakterToken;
import tr.com.vbt.token.KelimeToken;
import tr.com.vbt.token.OzelKelimeToken;

/**
 * Start Verb:
	START file-name KEY IS [=, >, <, NOT, <= or >=] rec-key
	   INVALID KEY DISPLAY 'Invalid Key'
	   NOT INVALID KEY DISPLAY 'File Pointer Updated'
	END-START.
 * @author 47159500
 *
 *
 *START MILFILE KEY IS > MIL-KEY
 *START VLMFILE KEY IS 'NOT LESS THAN' VLM-KEY 
 *
 *NOT LESS THAN Uclu Kelime
 *=, 
 *>, 
 *<, 
 *NOT, 
 *<= 
 *>=
 */


public class PaternStartFile extends AbstractPattern{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//AbstractPattern asp =new PaternAdd();

	}
	


	
	public PaternStartFile() {
		super();
		
		//READ
		AbstractToken astKeyword=new OzelKelimeToken("START", 0, 0, 0);
		patternTokenList.add(astKeyword);
		
		
		//filename
		AbstractToken astSource=new KelimeToken<String>();
		astSource.setSourceFieldName("filename");
		patternTokenList.add(astSource);
		
		//KEY_IS
		AbstractToken astRecKey=new OzelKelimeToken("KEY_IS", 0, 0, 0);
		patternTokenList.add(astRecKey);
		
		//NOT_LESS_THAN
		AbstractToken ast1=new OzelKelimeToken("NOT_LESS_THAN", 0, 0, 0);
		ast1.setOptional(true);
		ast1.setSourceFieldName("KEY_IS_STR");
		patternTokenList.add(ast1);
		
		//=
		AbstractToken ast1_0=new OzelKelimeToken("NOT", 0, 0, 0);
		ast1_0.setOptional(true);
		ast1_0.setSourceFieldName("KEY_IS");
		patternTokenList.add(ast1_0);
		
		
		//<
		AbstractToken ast2=new KarakterToken('<', 0, 1, 0, false,false);
		ast2.setOptional(true);
		ast2.setSourceFieldName("KEY_IS");
		patternTokenList.add(ast2);
		
		//>
		AbstractToken ast3=new KarakterToken('>', 0, 1, 0, false,false);
		ast3.setOptional(true);
		ast3.setSourceFieldName("KEY_IS");
		patternTokenList.add(ast3);
		
		//=
		AbstractToken ast1_1=new KarakterToken('=', 0, 1, 0, false,false);
		ast1_1.setOptional(true);
		ast1_1.setSourceFieldName("KEY_IS");
		patternTokenList.add(ast1_1);
		
		
		//filename
		AbstractToken recKeyName=new KelimeToken<String>();
		recKeyName.setSourceFieldName("recKeyName");
		patternTokenList.add(recKeyName);
		
	
	}
	
	@Override
	public AbstractCommand createElement(){
		ElementStartFile elementDisplay = new ElementStartFile(ReservedCobolKeywords.START_FILE,"PROCEDURE_DIVISION.*.START_FILE");
		return elementDisplay;
	}


	@Override
	public void setTokenToElement(AbstractCommand matchedCommand,
			AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {
		
		ElementStartFile matchedCommandAdd=(ElementStartFile) matchedCommand;
		
		super.setSatirNumarasi(matchedCommand,currentTokenForMatch, abstractTokenInPattern);if(abstractTokenInPattern.getSourceFieldName()==null){
		
		}else if(abstractTokenInPattern.getSourceFieldName().equals("filename")){
			matchedCommandAdd.setFileName((String) currentTokenForMatch.getDeger());
			matchedCommandAdd.getParameters().put("filename", matchedCommandAdd.getFileName());
		}else if(abstractTokenInPattern.getSourceFieldName().equals("KEY_IS_STR")){
			matchedCommandAdd.setKeyIs((String) currentTokenForMatch.getDeger());
			matchedCommandAdd.getParameters().put("RECORD_KEY_IS", matchedCommandAdd.getKeyIs());
		}else if(abstractTokenInPattern.getSourceFieldName().equals("KEY_IS")){
			matchedCommandAdd.setKeyIs(((Character) currentTokenForMatch.getDeger()).toString());
			matchedCommandAdd.getParameters().put("RECORD_KEY_IS", matchedCommandAdd.getKeyIs());
		}else if(abstractTokenInPattern.getSourceFieldName().equals("recKeyName")){
			matchedCommandAdd.setRecKeyName((String) currentTokenForMatch.getDeger());
			matchedCommandAdd.getParameters().put("recKeyName", matchedCommandAdd.getRecKeyName());
		}
	}
		





	
	
	

	
	
	
	

}
