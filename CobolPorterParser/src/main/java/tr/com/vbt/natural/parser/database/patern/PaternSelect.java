package tr.com.vbt.natural.parser.database.patern;

import java.util.ArrayList;
import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.natural.parser.database.ElementSQLSelect;
import tr.com.vbt.patern.carriage_return.AbstractPatternFromXToYWithoutCarriageReturn;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.GenelTipToken;
import tr.com.vbt.token.OzelKelimeToken;

/**
 2946 SELECT MAX(ALTSIRA) INTO MAXSIRA FROM IDGIDBS-TESKI WHERE                                                                      
 2948     HESCINSI=NHESCINS AND DOVIZ=PDOVIZ AND MUSNO1=PDMUSNO1(I)                                                                  
 2950     AND HESNO=PHESNO(I) AND MUSNO2=PDMUSNO2(I) AND                                                                             
 2952     KRX_KOD=PDHESNIT(I) AND SIRA=PBIS(I) AND VEFAT^='V' 
 
 THEN_OF_SELECT  Uzunluk:0 Satir No:0 Tipi:OzelKelime
 *
 */
public class PaternSelect extends AbstractPatternFromXToYWithoutCarriageReturn{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//AbstractPattern asp =new PaternAdd();

	}
	

	public PaternSelect() {
		super();
		
		//FOR
		starterToken=new OzelKelimeToken("SELECT", 0, 0, 0);
		starterToken.setSourceFieldName("FIRST_COMMAND");
		patternTokenList.add(starterToken);
		
		//conditionList
		midfieldToken=new GenelTipToken();
		midfieldToken.setTekrarlayabilir("+");
		midfieldToken.setSourceFieldName("queryTokenList");
		patternTokenList.add(midfieldToken);
		
		//Ender
		enderToken=new OzelKelimeToken(ReservedNaturalKeywords.THEN_OF_SELECT,0,0,0);
		patternTokenList.add(enderToken);
		
		
	}
	
	@Override
	public AbstractCommand createElement(){
		ElementSQLSelect elementRepeat = new ElementSQLSelect(ReservedNaturalKeywords.SELECT,"DATABASE.*.SELECT");
		return elementRepeat;
	}


	@Override
	public void setTokenToElement(AbstractCommand matchedCommand,
			AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {
		
		ElementSQLSelect matchedCommandAdd=(ElementSQLSelect) matchedCommand;
		
		super.setSatirNumarasi(matchedCommand,currentTokenForMatch, abstractTokenInPattern);
		
		if(abstractTokenInPattern.getSourceFieldName()==null){
		
		}else if(abstractTokenInPattern.getSourceFieldName().equals("queryTokenList")){
			
			matchedCommandAdd.getQueryTokenList().add(currentTokenForMatch);
			
			List<AbstractToken> sourceList;
			
			if(matchedCommandAdd.getParameters().get("queryTokenList")!=null){
				sourceList=(List<AbstractToken>) matchedCommandAdd.getParameters().get("queryTokenList");
			}else{
				sourceList=new ArrayList<AbstractToken>();
			}
			
			sourceList.add(currentTokenForMatch);
			matchedCommandAdd.getParameters().put("queryTokenList", sourceList);
			
		}
	}
		
	@Override
	public AbstractCommand getmatchedCommand(List<AbstractToken> tokenListesi, int offset) {
		return super.getmatchedCommand(tokenListesi, offset);
	}




	
	
	

	
	
	
	

}
