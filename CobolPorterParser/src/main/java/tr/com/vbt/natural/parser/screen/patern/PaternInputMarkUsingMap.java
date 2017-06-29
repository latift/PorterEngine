package tr.com.vbt.natural.parser.screen.patern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.natural.parser.screen.ElementInputUsingMap;
import tr.com.vbt.patern.AbstractPattern;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.KelimeToken;
import tr.com.vbt.token.OzelKelimeToken;

/**
 *    INPUT MARK *MAP.SECKOD USING MAP 'IDGMMUSS'
 *     
	 *INPUT  Uzunluk:0 Satir No:24 Tipi:OzelKelime
	MARK  Uzunluk:0 Satir No:24 Tipi:OzelKelime
	MAPP  Uzunluk:1 Satir No:24 Tipi:Kelime LocalVariable
	USING_MAP  Uzunluk:0 Satir No:24 Tipi:OzelKelime
	IDGMMUSS  Uzunluk:0 Satir No:24 Tipi:Kelime
 *
 */
public class PaternInputMarkUsingMap extends AbstractPattern{

	final static Logger logger = LoggerFactory.getLogger(PaternInputMarkUsingMap.class);


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//AbstractPattern asp =new PaternAdd();

	}
	

	public PaternInputMarkUsingMap() {
		super();
		//DISPLAY
		AbstractToken astKeyword=new OzelKelimeToken(ReservedNaturalKeywords.INPUT, 0, 0, 0);
		astKeyword.setSourceFieldName("FIRST_COMMAND");
		patternTokenList.add(astKeyword);
		
		//MARK
		AbstractToken astMark=new OzelKelimeToken(ReservedNaturalKeywords.MARK, 0, 0, 0);
		astMark.setSourceFieldName("markToken");
		patternTokenList.add(astMark);
		
		//MAPP
		AbstractToken markToken=new KelimeToken<String>();
		markToken.setSourceFieldName("markToken");
		patternTokenList.add(markToken);
		
		//USING_MAP
		AbstractToken astUsingMap=new OzelKelimeToken(ReservedNaturalKeywords.USING_MAP, 0, 0, 0);
		astUsingMap.setSourceFieldName("astUsingMap");
		patternTokenList.add(astUsingMap);
				
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
		
		super.setSatirNumarasi(matchedCommand,currentTokenForMatch, abstractTokenInPattern);
		if(abstractTokenInPattern.getSourceFieldName()==null){
		
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
		
		else if(abstractTokenInPattern.getSourceFieldName().equals("markToken")){
			logger.info("currentTokenForMatch.getDeger():"+ currentTokenForMatch.getDeger());
			matchedCommandAdd.setMarkToken(currentTokenForMatch);
			matchedCommandAdd.getParameters().put("markToken", currentTokenForMatch);
		}
	}
		





	
	
	

	
	
	
	

}
