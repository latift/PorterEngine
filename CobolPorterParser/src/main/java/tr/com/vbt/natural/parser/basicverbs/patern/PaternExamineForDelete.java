package tr.com.vbt.natural.parser.basicverbs.patern;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.natural.parser.basicverbs.ElementExamineForDelete;
import tr.com.vbt.patern.AbstractPattern;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.KelimeToken;
import tr.com.vbt.token.OzelKelimeToken;

/**
 //EXAMINE #FIRST-NAME FOR ‘SIR’ DELETE
 *
 */
public class PaternExamineForDelete extends AbstractPattern{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//AbstractPattern asp =new PaternAdd();

	}
	

	public PaternExamineForDelete() {
		super();
		
		//EXAMINE
		AbstractToken astKeyword=new OzelKelimeToken("EXAMINE", 0, 0, 0);
		astKeyword.setSourceFieldName("FIRST_COMMAND");
		astKeyword.setTekrarlayabilir("+");
		patternTokenList.add(astKeyword);
		
		
		//#
		/*AbstractToken astSourceDiyez=new KarakterToken('#', 0,0,0);
		patternTokenList.add(astSourceDiyez);*/
		
		//FIRST-NAME
		AbstractToken astSource=new KelimeToken<String>();
		astSource.setOptional(true);
		astSource.setSourceFieldName("MAIN_STRING");
		patternTokenList.add(astSource);
				
		//FOR
		AbstractToken astKeyword2=new OzelKelimeToken("EXAMINEFOR", 0, 0, 0);
		astKeyword2.setTekrarlayabilir("+");
		patternTokenList.add(astKeyword2);
		
		// ‘SIR’
		AbstractToken astDest=new KelimeToken<String>();
		astDest.setTekrarlayabilir("+");
		astDest.setSourceFieldName("SEARCH_STRING");
		patternTokenList.add(astDest);
		
		//DELETE
		AbstractToken astDelete=new OzelKelimeToken("DELETE", 0, 0, 0);
		patternTokenList.add(astDelete);
		
		//Ender
		AbstractToken enderToken=new OzelKelimeToken(ReservedNaturalKeywords.END_EXAMINE,0,0,0);
		patternTokenList.add(enderToken);
		
	}
	
	@Override
	public AbstractCommand createElement(){
		ElementExamineForDelete createdElement = new ElementExamineForDelete(ReservedNaturalKeywords.EXAMINE_DELETE,"GENERAL.*.EXAMINE_DELETE");
		return createdElement;
	}


	@Override
	public void setTokenToElement(AbstractCommand matchedCommand,
			AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {
		
		ElementExamineForDelete matchedCommandAdd=(ElementExamineForDelete) matchedCommand;
		
		super.setSatirNumarasi(matchedCommand,currentTokenForMatch, abstractTokenInPattern);if(abstractTokenInPattern.getSourceFieldName()==null){
		
		}else if(abstractTokenInPattern.getSourceFieldName().equals("MAIN_STRING")){
			
			matchedCommandAdd.setMainString((String) currentTokenForMatch.getDeger());
			
			matchedCommandAdd.getParameters().put("MAIN_STRING",matchedCommandAdd.getMainString());
			
		}else if(abstractTokenInPattern.getSourceFieldName().equals("SEARCH_STRING")){
			
			matchedCommandAdd.setSearchString((String) currentTokenForMatch.getDeger());
			
			matchedCommandAdd.getParameters().put("SEARCH_STRING",matchedCommandAdd.getSearchString());
			
		}
	}


}
