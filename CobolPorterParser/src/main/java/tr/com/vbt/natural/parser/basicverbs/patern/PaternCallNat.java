package tr.com.vbt.natural.parser.basicverbs.patern;

import java.util.ArrayList;
import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.natural.parser.basicverbs.ElementCallNat;
import tr.com.vbt.patern.carriage_return.AbstractPattern_XY_KKKK_Z_WithoutCarriageReturn;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.GenelTipToken;
import tr.com.vbt.token.KelimeToken;
import tr.com.vbt.token.OzelKelimeToken;
import tr.com.vbt.token.TokenTipi;

/**
 *     CALLNAT 'HANMSGN2' #ERR-NUM #PROGRAM #PREFIX #SUFFIX #STRING
 *
 */
public class PaternCallNat extends AbstractPattern_XY_KKKK_Z_WithoutCarriageReturn{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//AbstractPattern asp =new PaternAdd();

	}
	

	public PaternCallNat() {
		super();
		
		//CALLNAT
		this.starterToken=new OzelKelimeToken("CALLNAT", 0, 0, 0);
		starterToken.setTekrarlayabilir("+");
		starterToken.setSourceFieldName("FIRST_COMMAND");
		patternTokenList.add(starterToken);
		
		//paragraghName //HANMSGN2
		this.starterToken2=new KelimeToken<String>();
		starterToken2.setTekrarlayabilir("+");
		starterToken2.setSourceFieldName("paragraghName");
		patternTokenList.add(starterToken2);
		
		
		//paragraghParameters #ERR-NUM #PROGRAM #PREFIX #SUFFIX #STRING
		this.midfieldToken=new GenelTipToken<>();
		midfieldToken.setTekrarlayabilir("+");
		midfieldToken.setSourceFieldName("paragraghParameters");
		patternTokenList.add(midfieldToken);
		
		//Ender
		this.enderToken=new OzelKelimeToken(ReservedNaturalKeywords.END_CALLNAT,0,0,0);
		patternTokenList.add(enderToken);
						
		
	}
	
	@Override
	public AbstractCommand createElement(){
		ElementCallNat elementDisplay = new ElementCallNat(ReservedNaturalKeywords.CALLNAT,"GENERAL.*.CALLNAT");
		return elementDisplay;
	}


	@Override
	public void setTokenToElement(AbstractCommand matchedCommand,
			AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {
		
		ElementCallNat matchedCommandAdd=(ElementCallNat) matchedCommand;
		
		super.setSatirNumarasi(matchedCommand,currentTokenForMatch, abstractTokenInPattern);if(abstractTokenInPattern.getSourceFieldName()==null){
		
		
		}
		else if(abstractTokenInPattern.getSourceFieldName().equals("paragraghName")){
				matchedCommandAdd.setParagraghName((String) currentTokenForMatch.getDeger());
				matchedCommandAdd.getParameters().put("paragraghName", matchedCommandAdd.getParagraghName());
		
		
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
		




	public AbstractCommand getmatchedCommand(List<AbstractToken> tokenListesi, int offset) {
		return super.getmatchedCommand(tokenListesi, offset);
	}
	
	
	

	
	
	
	

}
