package tr.com.vbt.natural.parser.datalayout.program.patern;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.natural.parser.datalayout.program.redefiners.ElementProgramRedefineGrupNatural;
import tr.com.vbt.patern.AbstractDataTypePattern;
import tr.com.vbt.patern.AbstractPattern;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.KeyValueOzelKelimeToken;
import tr.com.vbt.token.SayiToken;

 /**S**  
  * 1 REDEFINE #TFK-SEFNO    Local Normal Redefine Grup Değ. 
  		 
 **/
public class PaternProgramRedefineGroupNatural extends AbstractDataTypePattern{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		AbstractPattern asp =new PaternProgramRedefineGroupNatural();

	}

	public PaternProgramRedefineGroupNatural() {
		super();
		
		//01
		AbstractToken astSource=new SayiToken<Integer>();
		astSource.setTekrarlayabilir("+");
		astSource.setSourceFieldName("FIRST_COMMAND");
		patternTokenList.add(astSource);
				
				
		//REDEFINES
		AbstractToken astRedefines=new KeyValueOzelKelimeToken("REDEFINE","", 0, 0, 0);
		astRedefines.setSourceFieldName("redefineName");
		astRedefines.setLocalVariable(true);
		patternTokenList.add(astRedefines);
		
		
	}

	@Override
	public void setTokenToElement(AbstractCommand matchedCommand,
			AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {
		
		ElementProgramRedefineGrupNatural matchedCommandAdd=(ElementProgramRedefineGrupNatural) matchedCommand;
		
		super.setSatirNumarasi(matchedCommand,currentTokenForMatch, abstractTokenInPattern);
		
		if(abstractTokenInPattern.getSourceFieldName()==null){
		
		}else if(abstractTokenInPattern.getSourceFieldName().equals("FIRST_COMMAND")){
			matchedCommandAdd.setLevelNumber(((Long)currentTokenForMatch.getDeger()));
			matchedCommandAdd.getParameters().put("levelNumber", matchedCommandAdd.getLevelNumber());
		}
		
		else if(abstractTokenInPattern.getSourceFieldName().equals("redefineName")){
			matchedCommandAdd.setRedefineName(  (String) ((KeyValueOzelKelimeToken) currentTokenForMatch).getValue());
			matchedCommandAdd.getParameters().put("redefineName", matchedCommandAdd.getRedefineName());
			
		}
		
	}

	@Override
	public AbstractCommand createElement() {
		ElementProgramRedefineGrupNatural createdElement = new ElementProgramRedefineGrupNatural(ReservedNaturalKeywords.PROGRAM_REDEFINE_GROUP, "GENERAL.PROGRAM_REDEFINE_GROUP");
		return createdElement;
		
	}

	
	


	

}
