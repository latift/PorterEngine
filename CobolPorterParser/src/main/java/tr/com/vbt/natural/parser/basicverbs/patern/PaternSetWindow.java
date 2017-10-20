package tr.com.vbt.natural.parser.basicverbs.patern;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.natural.parser.basicverbs.ElementSetWindow;
import tr.com.vbt.patern.AbstractPattern;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.KelimeToken;
import tr.com.vbt.token.OzelKelimeToken;

/**
//1970 *   SET WINDOW OFF
//1914 SET WINDOW 'WSWIFT'
 */
public class PaternSetWindow extends AbstractPattern{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//AbstractPattern asp =new PaternAdd();

	}
	

	public PaternSetWindow() {
		super();
		
		//SET_CONTROL
		AbstractToken astKeyword=new OzelKelimeToken("SET_WINDOW", 0, 0, 0);
		astKeyword.setTekrarlayabilir("+");
		astKeyword.setSourceFieldName("FIRST_COMMAND");
		patternTokenList.add(astKeyword);
		
		//A
		AbstractToken astSource=new KelimeToken<String>();
		astSource.setOptional(true);
		astSource.setTekrarlayabilir("+");
		astSource.setSourceFieldName("windowName");
		patternTokenList.add(astSource);
		
		//OFF
		AbstractToken astKeyword2=new OzelKelimeToken("OFF", 0, 0, 0);
		astKeyword2.setOptional(true);
		astKeyword2.setTekrarlayabilir("+");
		astKeyword2.setSourceFieldName("windowOffState");
		patternTokenList.add(astKeyword2);
		
	}
	
	@Override
	public AbstractCommand createElement(){
		ElementSetWindow createdElement = new ElementSetWindow(ReservedNaturalKeywords.SET_WINDOW,"GENERAL.*.SET_WINDOW");
		return createdElement;
	}


	@Override
	public void setTokenToElement(AbstractCommand matchedCommand,
			AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {
		
		ElementSetWindow matchedCommandAdd=(ElementSetWindow) matchedCommand;
		
		super.setSatirNumarasi(matchedCommand,currentTokenForMatch, abstractTokenInPattern);
		
		if(abstractTokenInPattern.getSourceFieldName()==null){
		
		}else if(abstractTokenInPattern.getSourceFieldName().equals("windowName")){
			String windowName=(String) currentTokenForMatch.getDeger();
			windowName=windowName.replaceAll("-", "_");
			matchedCommandAdd.setWindowName(windowName);
			matchedCommandAdd.getParameters().put("controlName", matchedCommandAdd.getWindowName());
		}else if(abstractTokenInPattern.getSourceFieldName().equals("windowOffState")){
			String windowOffState=(String) currentTokenForMatch.getDeger();
			matchedCommandAdd.setWindowOffState(windowOffState);
			matchedCommandAdd.getParameters().put("windowOffState", matchedCommandAdd.getWindowOffState());
		}
	}
		





	
	
	

	
	
	
	

}
