package tr.com.vbt.natural.parser.screen.patern;

import java.util.List;

import org.apache.log4j.Logger;


import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.natural.parser.screen.ElementInputUsingMap;
import tr.com.vbt.patern.AbstractPattern;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.KelimeToken;
import tr.com.vbt.token.OzelKelimeToken;

/**
 *     INPUT USING MAP 'MAP01'
 *     
 *     INPUT_USING_MAP  Uzunluk:0 Satir No:6 Tipi:OzelKelime
		MAP01  Uzunluk:0 Satir No:6 Tipi:Kelime
 *
 */
public class PaternInputMap extends AbstractPattern{

	final static Logger logger = Logger.getLogger(PaternInputMap.class);


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//AbstractPattern asp =new PaternAdd();

	}
	

	public PaternInputMap() {
		super();
		//DISPLAY
		AbstractToken astKeyword=new OzelKelimeToken(ReservedNaturalKeywords.INPUT_MAP, 0, 0, 0);
		astKeyword.setSourceFieldName("FIRST_COMMAND");
		patternTokenList.add(astKeyword);
				
		//mapName
		AbstractToken astSource=new KelimeToken<String>();
		astSource.setSourceFieldName("mapName");
		patternTokenList.add(astSource);
		
	}
	
	@Override
	public AbstractCommand createElement(){
		ElementInputUsingMap elementDisplay = new ElementInputUsingMap(ReservedNaturalKeywords.INPUT_USING_MAP,"SCREEN.*.INPUT_USING_MAP");
		return elementDisplay;
	}


	@Override
	public void setTokenToElement(AbstractCommand matchedCommand,
			AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {
		
		ElementInputUsingMap matchedCommandAdd=(ElementInputUsingMap) matchedCommand;
		
		super.setSatirNumarasi(matchedCommand,currentTokenForMatch, abstractTokenInPattern);if(abstractTokenInPattern.getSourceFieldName()==null){
		
		}else if(abstractTokenInPattern.getSourceFieldName().equals("mapName")){
			logger.info("currentTokenForMatch.getDeger():"+ currentTokenForMatch.getDeger());
			try {
				matchedCommandAdd.setMapName((String) currentTokenForMatch.getDeger());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			matchedCommandAdd.getParameters().put("mapName", matchedCommandAdd.getMapName());
		}
	}


	@Override
	public AbstractCommand getmatchedCommand(List<AbstractToken> tokenListesi, int offset) {
		// TODO Auto-generated method stub
		return super.getmatchedCommand(tokenListesi, offset);
	}
		





	
	
	

	
	
	
	

}
