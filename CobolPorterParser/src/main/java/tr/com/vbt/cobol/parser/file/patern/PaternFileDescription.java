package tr.com.vbt.cobol.parser.file.patern;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.file.ElementFileDescription;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.patern.AbstractPattern;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.KeyValueOzelKelimeToken;
import tr.com.vbt.token.OzelKelimeToken;

/**
 *      018620 FD  PCFILE                                                       01862000
		018630     RECORDING MODE IS V                                          01863000
		018640     LABEL RECORDS ARE STANDARD                                   01864000
		018650     BLOCK 0 RECORDS.
 *
 */
public class PaternFileDescription extends AbstractPattern{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//AbstractPattern asp =new PaternAdd();

	}
	

	public PaternFileDescription() {
		super();
		
		//SELECT VCCFILE
		AbstractToken astKeyword1=new KeyValueOzelKelimeToken("FD","", 0, 0, 0);
		astKeyword1.setTekrarlayabilir("+");
		astKeyword1.setSourceFieldName("fileName");
		patternTokenList.add(astKeyword1);
		
		//RECORDING MODE IS V
		AbstractToken astRecMode=new KeyValueOzelKelimeToken("RECORDING_MODE_IS","", 0, 0, 0);
		astRecMode.setSourceFieldName("RECORDING_MODE_IS");
		astRecMode.setOptional(true);
		patternTokenList.add(astRecMode);
		
		//LABEL RECORDS ARE STANDARD
		AbstractToken astLabelRec=new KeyValueOzelKelimeToken("LABEL_RECORDS_ARE","", 0, 0, 0);
		astLabelRec.setSourceFieldName("LABEL_RECORDS_ARE");
		astLabelRec.setOptional(true);
		patternTokenList.add(astLabelRec);
		
		//BLOCK 0 
		AbstractToken astBlock=new KeyValueOzelKelimeToken("BLOCK","", 0, 0, 0);
		astBlock.setSourceFieldName("BLOCK");
		astBlock.setOptional(true);
		patternTokenList.add(astBlock);
		
		//RECORDS
		AbstractToken astRecords=new OzelKelimeToken("RECORDS", 0, 0, 0);
		astRecords.setOptional(true);
		patternTokenList.add(astRecords);
		
		
	}
	
	@Override
	public AbstractCommand createElement(){
		ElementFileDescription elementDisplay = new ElementFileDescription(ReservedCobolKeywords.FILE_DESCRIPTION,"FILE_SECTION.*.FD");
		return elementDisplay;
	}


	@Override
	public void setTokenToElement(AbstractCommand matchedCommand,
			AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {
		
		ElementFileDescription matchedCommandAdd=(ElementFileDescription) matchedCommand;
		
		super.setSatirNumarasi(matchedCommand,currentTokenForMatch, abstractTokenInPattern);if(abstractTokenInPattern.getSourceFieldName()==null){
		
		}else if(abstractTokenInPattern.getSourceFieldName().equals("fileName")){
			matchedCommandAdd.setFileName(((String) ((KeyValueOzelKelimeToken)currentTokenForMatch).getValue()));
			matchedCommandAdd.getParameters().put("dataToDisplay", matchedCommandAdd.getFileName());
		}
		else if(abstractTokenInPattern.getSourceFieldName().equals("RECORDING_MODE_IS")){
			matchedCommandAdd.setRecordingMode(((String) ((KeyValueOzelKelimeToken)currentTokenForMatch).getValue()));
			matchedCommandAdd.getParameters().put("RECORDING_MODE_IS", matchedCommandAdd.getRecordingMode());
		}
		else if(abstractTokenInPattern.getSourceFieldName().equals("LABEL_RECORDS_ARE")){
			matchedCommandAdd.setLabelRecordsType(((String) ((KeyValueOzelKelimeToken)currentTokenForMatch).getValue()));
			matchedCommandAdd.getParameters().put("LABEL_RECORDS_ARE", matchedCommandAdd.getLabelRecordsType());
		}
		else if(abstractTokenInPattern.getSourceFieldName().equals("BLOCK")){
			matchedCommandAdd.setBlockCount(((int)((KeyValueOzelKelimeToken)currentTokenForMatch).getValue()));
			matchedCommandAdd.getParameters().put("BLOCK", matchedCommandAdd.getBlockCount());
		}
	}
		





	
	
	

	
	
	
	

}
