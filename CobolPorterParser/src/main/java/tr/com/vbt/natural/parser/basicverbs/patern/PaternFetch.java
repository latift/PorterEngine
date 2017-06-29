package tr.com.vbt.natural.parser.basicverbs.patern;

import java.util.ArrayList;
import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.natural.parser.basicverbs.ElementFetch;
import tr.com.vbt.patern.carriage_return.AbstractPattern_XY_KKKK_Z_WithoutCarriageReturn;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.GenelTipToken;
import tr.com.vbt.token.KelimeToken;
import tr.com.vbt.token.OzelKelimeToken;
import tr.com.vbt.token.TokenTipi;

/**
 *    FETCH RETURN 'AYKPFMM2'
 *
 */
public class PaternFetch extends AbstractPattern_XY_KKKK_Z_WithoutCarriageReturn{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//AbstractPattern asp =new PaternAdd();

	}
	

	public PaternFetch() {
		super();
		
		//FETCH_RETURN
		this.starterToken=new OzelKelimeToken("FETCH", 0, 0, 0);
		starterToken.setTekrarlayabilir("+");
		starterToken.setSourceFieldName("FIRST_COMMAND");
		patternTokenList.add(starterToken);
		
		//paragraghName //HANMSGN2
		this.starterToken2=new KelimeToken<String>();
		starterToken2.setTekrarlayabilir("+");
		starterToken2.setSourceFieldName("programName");
		patternTokenList.add(starterToken2);
		
		
		//paragraghParameters #ERR-NUM #PROGRAM #PREFIX #SUFFIX #STRING
		this.midfieldToken=new GenelTipToken<>();
		midfieldToken.setTekrarlayabilir("+");
		midfieldToken.setSourceFieldName("paragraghParameters");
		patternTokenList.add(midfieldToken);
		
		//Ender
		this.enderToken=new OzelKelimeToken(ReservedNaturalKeywords.END_FETCH,0,0,0);
		patternTokenList.add(enderToken);
		
	}
	
	@Override
	public AbstractCommand createElement(){
		ElementFetch elementDisplay = new ElementFetch(ReservedNaturalKeywords.FETCH,"GENERAL.*.FETCH");
		return elementDisplay;
	}


	@Override
	public void setTokenToElement(AbstractCommand matchedCommand,
			AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {
		
		ElementFetch matchedCommandAdd=(ElementFetch) matchedCommand;
		
		super.setSatirNumarasi(matchedCommand,currentTokenForMatch, abstractTokenInPattern);
		if(abstractTokenInPattern.getSourceFieldName()==null){
		
		}
		else if(abstractTokenInPattern.getSourceFieldName().equals("programName")){
				matchedCommandAdd.setProgramName((String) currentTokenForMatch.getDeger());
				matchedCommandAdd.getParameters().put("programName", matchedCommandAdd.getProgramName());
		
		}else if(abstractTokenInPattern.getSourceFieldName().equals("paragraghParameters")){
			if(currentTokenForMatch.getTip().equals(TokenTipi.Karakter)&&currentTokenForMatch.getDeger().equals('#')){
				return;
			}
		
			matchedCommandAdd.getParagraghParameters().add(currentTokenForMatch);
			
			List<AbstractToken> sourceList;
			if(matchedCommandAdd.getParameters().get("paragraghParameters")!=null){
				sourceList=(List<AbstractToken>) matchedCommandAdd.getParameters().get("paragraghParameters");
			}else{
				sourceList=new ArrayList<AbstractToken>();
			}
			sourceList.add(currentTokenForMatch);
			matchedCommandAdd.getParameters().put("paragraghParameters", sourceList);
		}
	}
		





	
	
	

	
	
	
	

}
