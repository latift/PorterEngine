package tr.com.vbt.natural.parser.datalayout.db.patern;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.natural.parser.datalayout.db.ElementDBViewOfNatural;
import tr.com.vbt.patern.AbstractDataTypePattern;
import tr.com.vbt.patern.AbstractPattern;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.KelimeToken;
import tr.com.vbt.token.OzelKelimeToken;
import tr.com.vbt.token.SayiToken;

//*S**1 TESL2 VIEW OF AYK-TESL 
/**
  * protected long levelNumber;
	
	//TESL2
	protected String variableName;

	//AYK-TESL
	protected String tableName;
 *
 */
public class PaternDBViewGroupNatural extends AbstractDataTypePattern{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		AbstractPattern asp =new PaternDBViewGroupNatural();

	}

	public PaternDBViewGroupNatural() {
		super();
		
		//01
		AbstractToken astSource=new SayiToken();
		astSource.setTekrarlayabilir("+");
		astSource.setSourceFieldName("levelNumber");
		patternTokenList.add(astSource);
				

		//TESL
		AbstractToken<String> astSource2=new KelimeToken();
		astSource2.setTekrarlayabilir("+");
		astSource2.setSourceFieldName("variableName");
		patternTokenList.add(astSource2);
		
		//VIEW
		AbstractToken astRedefines=new OzelKelimeToken("VIEW", 0, 0, 0);
		patternTokenList.add(astRedefines);
		
		//HAN-USTGECIS
		AbstractToken<String> astSource3=new KelimeToken();
		astSource3.setTekrarlayabilir("+");
		astSource3.setSourceFieldName("typeName");
		patternTokenList.add(astSource3);
		
		
	}

	@Override
	public void setTokenToElement(AbstractCommand matchedCommand,
			AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {
		
		ElementDBViewOfNatural matchedCommandAdd=(ElementDBViewOfNatural) matchedCommand;
		
		super.setSatirNumarasi(matchedCommand,currentTokenForMatch, abstractTokenInPattern);if(abstractTokenInPattern.getSourceFieldName()==null){
		
		}else if(abstractTokenInPattern.getSourceFieldName().equals("levelNumber")){
			matchedCommandAdd.setLevelNumber(((Long)currentTokenForMatch.getDeger()));
			matchedCommandAdd.getParameters().put("levelNumber", matchedCommandAdd.getLevelNumber());
		
		}else if(abstractTokenInPattern.getSourceFieldName().equals("variableName")){
			matchedCommandAdd.setVariableName((String) currentTokenForMatch.getDeger().toString());
			matchedCommandAdd.getParameters().put("variableName", matchedCommandAdd.getVariableName());
		
		}else if(abstractTokenInPattern.getSourceFieldName().equals("typeName")){
			matchedCommandAdd.setTypeName(currentTokenForMatch.getTypeNameOfView());
			matchedCommandAdd.getParameters().put("typeName", matchedCommandAdd.getTypeName());
		}
	}

	@Override
	public AbstractCommand createElement() {
		ElementDBViewOfNatural createdElement = new ElementDBViewOfNatural(ReservedNaturalKeywords.VIEW_OF, "DATABASE.VIEW_OF");
		return createdElement;
		
	}

	
	


	

}
