package tr.com.vbt.natural.parser.database.patern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.natural.parser.database.ElementGet;
import tr.com.vbt.patern.AbstractPattern;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.ArrayToken;
import tr.com.vbt.token.KelimeToken;
import tr.com.vbt.token.OzelKelimeToken;

/**
*   *S**  GET KET-CITY ISNS(ITEM)
*S**  GET KET-CITY ISN
 */
public class PaternGet extends AbstractPattern {

	final static Logger logger = LoggerFactory.getLogger(PaternGet.class);
	protected ArrayToken isnsArrayToken;
	AbstractToken astViewName,isnToken,starterToken;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// AbstractPattern asp =new PaternAdd();

	}

	public PaternGet() {
		super();

		// FIND
		starterToken = new OzelKelimeToken("GET", 0, 0, 0);
		starterToken.setTekrarlayabilir("+");
		starterToken.setSourceFieldName("FIRST_COMMAND");
		patternTokenList.add(starterToken);

		// KET-CITY
		astViewName = new KelimeToken<String>();
		astViewName.setSourceFieldName("viewName");
		patternTokenList.add(astViewName);

		// ISN
		isnToken = new KelimeToken<String>();
		isnToken.setSourceFieldName("isnToken");
		isnToken.setOptional(true);
		patternTokenList.add(isnToken);
		
		//  ISNS(ITEM) Array durumunda bu kod calisir
		isnsArrayToken=new ArrayToken();
		isnsArrayToken.setTekrarlayabilir("+");
		isnsArrayToken.setOptional(true);
		isnsArrayToken.setSourceFieldName("isnTokenArray");
		patternTokenList.add(isnsArrayToken);
		

	}

	@Override
	public AbstractCommand createElement() {
		ElementGet createdElement = new ElementGet(
				ReservedNaturalKeywords.GET, "DATABASE.*.GET");
		return createdElement;
	}

	@Override
	public void setTokenToElement(AbstractCommand matchedCommand,
			AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {

		ElementGet matchedCommandAdd = (ElementGet) matchedCommand;

		super.setSatirNumarasi(matchedCommand,currentTokenForMatch, abstractTokenInPattern);
		
		if(abstractTokenInPattern.getSourceFieldName()==null){
			
		}else if (abstractTokenInPattern.getSourceFieldName().equals("viewName")) {
			matchedCommandAdd.setViewName(currentTokenForMatch);
			matchedCommandAdd.getParameters().put("viewName",matchedCommandAdd.getViewName());
			
		}else if(abstractTokenInPattern.getSourceFieldName().equals("isnToken")){
			
			matchedCommandAdd.setIsnToken(currentTokenForMatch);
			matchedCommandAdd.getParameters().put("isnToken",matchedCommandAdd.getIsnToken());
			
		}else if(abstractTokenInPattern.getSourceFieldName().equals("isnTokenArray")){
			
			matchedCommandAdd.setIsnArrayToken((ArrayToken) currentTokenForMatch);
			matchedCommandAdd.getParameters().put("isnTokenArray",matchedCommandAdd.getIsnArrayToken());
		}
	}
	
	

}
