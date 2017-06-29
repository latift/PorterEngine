package tr.com.vbt.cobol.parser.basicverbs.patern;

import java.util.ArrayList;
import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.basicverbs.ElementDisplay;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.patern.carriage_return.AbstractPatternFromXToYWithoutCarriageReturn;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.KelimeToken;
import tr.com.vbt.token.OzelKelimeToken;

/**
 *     DISPLAY 'IN B-PARA
 *
 */
public class PaternDisplay extends AbstractPatternFromXToYWithoutCarriageReturn{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//AbstractPattern asp =new PaternAdd();

	}
	

	public PaternDisplay() {
		super();
		
		//DISPLAY
		this.starterToken=new OzelKelimeToken("DISPLAY", 0, 0, 0);
		starterToken.setTekrarlayabilir("+");
		patternTokenList.add(starterToken);
		
		
		
		//dataToDisplay
		this.midfieldToken=new KelimeToken<String>();
		midfieldToken.setTekrarlayabilir("+");
		midfieldToken.setSourceFieldName("dataToDisplay");
		patternTokenList.add(midfieldToken);
		
		//Ender
		this.enderToken=new OzelKelimeToken(ReservedCobolKeywords.END_DISPLAY,0,0,0);
		patternTokenList.add(enderToken);
						
		
	}
	
	@Override
	public AbstractCommand createElement(){
		ElementDisplay elementDisplay = new ElementDisplay(ReservedCobolKeywords.DISPLAY,"PROCEDURE_DIVISION.*.DISPLAY");
		return elementDisplay;
	}


	@Override
	public void setTokenToElement(AbstractCommand matchedCommand,
			AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {
		
		ElementDisplay matchedCommandAdd=(ElementDisplay) matchedCommand;
		
		super.setSatirNumarasi(matchedCommand,currentTokenForMatch, abstractTokenInPattern);if(abstractTokenInPattern.getSourceFieldName()==null){
		
		}else if(abstractTokenInPattern.getSourceFieldName().equals("dataToDisplay")){
			matchedCommandAdd.getDataToDisplay().add((String) currentTokenForMatch.getDeger());
			
			List<String> sourceList;
			if(matchedCommandAdd.getParameters().get("dataToDisplay")!=null){
				sourceList=(List<String>) matchedCommandAdd.getParameters().get("dataToDisplay");
			}else{
				sourceList=new ArrayList<String>();
			}
			sourceList.add((String) currentTokenForMatch.getDeger());
			matchedCommandAdd.getParameters().put("dataToDisplay", sourceList);
		}
	}
		





	
	
	

	
	
	
	

}
