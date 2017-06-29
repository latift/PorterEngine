package tr.com.vbt.natural.parser.datalayout.db.patern;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.natural.parser.datalayout.db.ElementDBRedefineDataGroupNatural;
import tr.com.vbt.patern.AbstractDataTypePattern;
import tr.com.vbt.patern.AbstractPattern;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.KeyValueOzelKelimeToken;
import tr.com.vbt.token.SayiToken;

//03 REDEFINE T-USER-DATE-TIME  
/**
  * @author 47159500
 *
 */
public class PaternDBRedefineDataGroupNatural extends AbstractDataTypePattern{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		AbstractPattern asp =new PaternDBRedefineDataGroupNatural();

	}

	public PaternDBRedefineDataGroupNatural() {
		super();
		
		//01
		AbstractToken astSource=new SayiToken();
		astSource.setTekrarlayabilir("+");
		astSource.setSourceFieldName("levelNumber");
		patternTokenList.add(astSource);

		//REDEFINES
		AbstractToken astRedefines=new KeyValueOzelKelimeToken("REDEFINE","", 0, 0, 0);
		astRedefines.setSourceFieldName("redefine");
		astRedefines.setPojoVariable(true);
		patternTokenList.add(astRedefines);
		
	}

	@Override
	public void setTokenToElement(AbstractCommand matchedCommand,
			AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {
		
		ElementDBRedefineDataGroupNatural matchedCommandAdd=(ElementDBRedefineDataGroupNatural) matchedCommand;
		
		super.setSatirNumarasi(matchedCommand,currentTokenForMatch, abstractTokenInPattern);if(abstractTokenInPattern.getSourceFieldName()==null){
		
		}else if(abstractTokenInPattern.getSourceFieldName().equals("levelNumber")){
			matchedCommandAdd.setLevelNumber(((Integer)currentTokenForMatch.getDeger()));
			matchedCommandAdd.getParameters().put("levelNumber", matchedCommandAdd.getLevelNumber());
		
		}else if(abstractTokenInPattern.getSourceFieldName().equals("redefine")){
			matchedCommandAdd.setRedefineName((String) ((KeyValueOzelKelimeToken)currentTokenForMatch).getValue());
			matchedCommandAdd.getParameters().put("redefine", matchedCommandAdd.getRedefineName());
		}
	}

	@Override
	public AbstractCommand createElement() {
		ElementDBRedefineDataGroupNatural createdElement = new ElementDBRedefineDataGroupNatural(ReservedNaturalKeywords.DB_REDEFINE_GROUP_DATA, "DATABASE.DB_REDEFINE_GROUP_DATA");
		return createdElement;
		
	}

	
	


	

}
