package tr.com.vbt.cobol.parser.file.patern;

import java.util.ArrayList;
import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.file.ElementCloseFile;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.patern.carriage_return.AbstractPatternFromXToYWithCarriageReturn;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.KelimeToken;
import tr.com.vbt.token.OzelKelimeToken;

/**
 *  CLOSE RMFFILE. 
 */
public class PaternCloseFile extends AbstractPatternFromXToYWithCarriageReturn{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//AbstractPattern asp =new PaternAdd();

	}
	

	public PaternCloseFile() {
		super();
		
		//CLOSE
		AbstractToken starter=new OzelKelimeToken("CLOSE", 0, 0, 0);
		starter.setTekrarlayabilir("+");
		patternTokenList.add(starter);
		
		
		//fileName
		AbstractToken astSource=new KelimeToken<String>();
		astSource.setTekrarlayabilir("+");
		astSource.setSourceFieldName("fileNameList");
		patternTokenList.add(astSource);
		
		//Ender
		AbstractToken ender=new OzelKelimeToken(ReservedCobolKeywords.END_CLOSE,0,0,0);
		patternTokenList.add(ender);
						
		
		
	}
	
	@Override
	public AbstractCommand createElement(){
		ElementCloseFile createdElement = new ElementCloseFile(ReservedCobolKeywords.CLOSE,"PROCEDURE_DIVISION.*.CLOSE_FILE");
		return createdElement;
	}


	@Override
	public void setTokenToElement(AbstractCommand matchedCommand,
			AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {
		
		ElementCloseFile matchedCommandAdd=(ElementCloseFile) matchedCommand;
		
		super.setSatirNumarasi(matchedCommand,currentTokenForMatch, abstractTokenInPattern);if(abstractTokenInPattern.getSourceFieldName()==null){
		
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
