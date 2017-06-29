package tr.com.vbt.natural.parser.screen.patern;

import java.util.ArrayList;
import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.natural.parser.screen.ElementReInputWith;
import tr.com.vbt.patern.carriage_return.AbstractPatternFromXToYWithoutCarriageReturn;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.KelimeToken;
import tr.com.vbt.token.OzelKelimeToken;

/**
 *    REINPUT 'No employees meet your criteria.'
 *
 */
public class PaternReInputWith extends AbstractPatternFromXToYWithoutCarriageReturn{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//AbstractPattern asp =new PaternAdd();

	}
	

	public PaternReInputWith() {
		super();
		
		//REINPUT
		this.starterToken=new OzelKelimeToken("REINPUT_WITH", 0, 0, 0);
		starterToken.setSourceFieldName("FIRST_COMMAND");
		starterToken.setTekrarlayabilir("+");
		patternTokenList.add(starterToken);
		
		//dataToDisplay
		this.midfieldToken=new KelimeToken<String>();
		midfieldToken.setTekrarlayabilir("+");
		midfieldToken.setSourceFieldName("dataToDisplay");
		patternTokenList.add(midfieldToken);
		
		//Ender
		this.enderToken=new OzelKelimeToken(ReservedNaturalKeywords.END_REINPUT,0,0,0);
		patternTokenList.add(enderToken);
						
		
	}
	
	@Override
	public AbstractCommand createElement(){
		ElementReInputWith elementDisplay = new ElementReInputWith(ReservedNaturalKeywords.REINPUT_WITH,"DATABASE.*.REINPUT");
		return elementDisplay;
	}


	@Override
	public void setTokenToElement(AbstractCommand matchedCommand,
			AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {
		
		ElementReInputWith matchedCommandAdd=(ElementReInputWith) matchedCommand;
		
		super.setSatirNumarasi(matchedCommand,currentTokenForMatch, abstractTokenInPattern);if(abstractTokenInPattern.getSourceFieldName()==null){
		
		}else if(abstractTokenInPattern.getSourceFieldName().equals("dataToDisplay")){
			List<AbstractToken> sourceList;
			if(matchedCommandAdd.getParameters().get("dataToDisplay")!=null){
				sourceList=(List<AbstractToken>) matchedCommandAdd.getParameters().get("dataToDisplay");
			}else{
				sourceList=new ArrayList<AbstractToken>();
			}
			sourceList.add(currentTokenForMatch);
			matchedCommandAdd.getParameters().put("dataToDisplay", sourceList);
		}
	}
		





	
	
	

	
	
	
	

}
