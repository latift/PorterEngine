package tr.com.vbt.cobol.parser.file.patern;

import java.util.ArrayList;
import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.file.ElementOpenFile;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.patern.carriage_return.AbstractPatternFromXOptionalYToNextKeyword;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.KelimeToken;
import tr.com.vbt.token.OzelKelimeToken;

/**
 *    OPEN INPUT   RMFFILE
 *
 */
public class PaternOpenFile extends AbstractPatternFromXOptionalYToNextKeyword{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//AbstractPattern asp =new PaternAdd();

	}
	

	public PaternOpenFile() {
		super();
		
		//OPEN_INPUT
		AbstractToken starter=new OzelKelimeToken("OPEN", 0, 0, 0);
		starter.setTekrarlayabilir("+");
		patternTokenList.add(starter);
		
		AbstractToken mode=new OzelKelimeToken("INPUT", 0, 0, 0);
		mode.setOptional(true);
		mode.setSourceFieldName("mode");
		patternTokenList.add(mode);
		
		AbstractToken mode2=new OzelKelimeToken("OUTPUT", 0, 0, 0);
		mode2.setOptional(true);
		mode2.setSourceFieldName("mode");
		patternTokenList.add(mode2);
		
		
		//dataToDisplay
		AbstractToken astSource=new KelimeToken<String>();
		astSource.setTekrarlayabilir("+");
		astSource.setSourceFieldName("fileNameList");
		patternTokenList.add(astSource);
		
	}
	
	@Override
	public AbstractCommand createElement(){
		ElementOpenFile elementDisplay = new ElementOpenFile(ReservedCobolKeywords.OPEN,"PROCEDURE_DIVISION.*.OPEN_FILE");
		return elementDisplay;
	}


	@Override
	public void setTokenToElement(AbstractCommand matchedCommand,
			AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {
		
		ElementOpenFile matchedCommandAdd=(ElementOpenFile) matchedCommand;
		
		super.setSatirNumarasi(matchedCommand,currentTokenForMatch, abstractTokenInPattern);if(abstractTokenInPattern.getSourceFieldName()==null){
		
		}else if(abstractTokenInPattern.getSourceFieldName().equals("mode")){
			
			String mode=(String) currentTokenForMatch.getDeger();
			matchedCommandAdd.setMode(mode);
			matchedCommandAdd.getParameters().put("mode", matchedCommandAdd.getMode());
			
		}else if(abstractTokenInPattern.getSourceFieldName().equals("fileNameList")){
			matchedCommandAdd.getFileNameList().add((String) currentTokenForMatch.getDeger());
			
			List<String> sourceList;
			if(matchedCommandAdd.getParameters().get("fileNameList")!=null){
				sourceList=(List<String>) matchedCommandAdd.getParameters().get("fileNameList");
			}else{
				sourceList=new ArrayList<String>();
			}
			sourceList.add((String) currentTokenForMatch.getDeger());
			matchedCommandAdd.getParameters().put("fileNameList", sourceList);
		}	
	}
		





	
	
	

	
	
	
	

}
