package tr.com.vbt.natural.parser.basicverbs.patern;

import java.util.ArrayList;
import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.natural.parser.basicverbs.ElementAdd;
import tr.com.vbt.patern.AbstractPattern;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.GenelTipToken;
import tr.com.vbt.token.OzelKelimeToken;

/**
 *  ADD 100 TO #SALARY
 *
 */
public class PaternAdd extends AbstractPattern{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//AbstractPattern asp =new PaternAdd();

	}
	

	public PaternAdd() {
		super();
		
		
		
		//ADD
		AbstractToken astKeyword=new OzelKelimeToken("ADD", 0, 0, 0);
		astKeyword.setTekrarlayabilir("+");
		astKeyword.setSourceFieldName("FIRST_COMMAND");
		patternTokenList.add(astKeyword);
		
		//A
		AbstractToken astSource=new GenelTipToken<>();
		astSource.setSourceFieldName("SOURCE");
		patternTokenList.add(astSource);
		
		//TO
		AbstractToken astKeyword2=new OzelKelimeToken("TO", 0, 0, 0);
		astKeyword2.setTekrarlayabilir("+");
		patternTokenList.add(astKeyword2);
		
		// C
		AbstractToken astDest=new GenelTipToken<>();
		astDest.setTekrarlayabilir("+");
		astDest.setSourceFieldName("DESTINATION");
		patternTokenList.add(astDest);
		
		
	}
	
	@Override
	public AbstractCommand createElement(){
		ElementAdd createdElement = new ElementAdd(ReservedNaturalKeywords.ADD,"GENERAL.*.ADD");
		return createdElement;
	}


	@Override
	public void setTokenToElement(AbstractCommand matchedCommand,
			AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {
		
		ElementAdd matchedCommandAdd=(ElementAdd) matchedCommand;
		
		super.setSatirNumarasi(matchedCommand,currentTokenForMatch, abstractTokenInPattern);if(abstractTokenInPattern.getSourceFieldName()==null){
		
		}else if(abstractTokenInPattern.getSourceFieldName().equals("SOURCE")){
			
			matchedCommandAdd.getSourceNum().add(currentTokenForMatch);
			
			List<AbstractToken> sourceList;
			if(matchedCommandAdd.getParameters().get("SOURCE")!=null){
				sourceList=(List<AbstractToken>) matchedCommandAdd.getParameters().get("SOURCE");
			}else{
				sourceList=new ArrayList<AbstractToken>();
			}
			sourceList.add(currentTokenForMatch);
			matchedCommandAdd.getParameters().put("SOURCE", sourceList);
			
		}else if(abstractTokenInPattern.getSourceFieldName().equals("DESTINATION")){
			matchedCommandAdd.getDestNum().add(currentTokenForMatch);
			
			List<AbstractToken> sourceList;
			if(matchedCommandAdd.getParameters().get("DESTINATION")!=null){
				sourceList=(List<AbstractToken>) matchedCommandAdd.getParameters().get("DESTINATION");
			}else{
				sourceList=new ArrayList<AbstractToken>();
			}
			sourceList.add(currentTokenForMatch);
			matchedCommandAdd.getParameters().put("DESTINATION", sourceList);
		}
	}
	
	public AbstractCommand getmatchedCommand(List<AbstractToken> tokenListesi, int offset){
		return super.getmatchedCommand(tokenListesi, offset);
	}
	
	

}
