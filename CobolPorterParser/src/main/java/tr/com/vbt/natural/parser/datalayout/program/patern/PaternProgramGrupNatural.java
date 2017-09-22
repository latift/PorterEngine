package tr.com.vbt.natural.parser.datalayout.program.patern;

import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.natural.parser.datalayout.program.ElementProgramGrupNatural;
import tr.com.vbt.patern.AbstractDataTypePattern;
import tr.com.vbt.patern.AbstractPattern;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.KelimeToken;
import tr.com.vbt.token.SayiToken;

//0412 1 MAP_DIZISI 

// 0880 1 MAP_DIZISI (100)   (100) optional
/**
  * @author 47159500
 *
 */
public class PaternProgramGrupNatural extends AbstractDataTypePattern{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		AbstractPattern asp =new PaternProgramGrupNatural();

	}

	public PaternProgramGrupNatural() {
		super();
		
		//1
		AbstractToken astSource=new SayiToken();
		astSource.setTekrarlayabilir("+");
		astSource.setSourceFieldName("levelNumber");
		patternTokenList.add(astSource);

		
		//MAP_DIZISI
		AbstractToken astSource2=new KelimeToken<String>();
		astSource2.setTekrarlayabilir("+");
		astSource2.setSourceFieldName("grupName");
		patternTokenList.add(astSource2);
		
	}

	@Override
	public void setTokenToElement(AbstractCommand matchedCommand,
			AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {
		
		ElementProgramGrupNatural matchedCommandAdd=(ElementProgramGrupNatural) matchedCommand;
		
		super.setSatirNumarasi(matchedCommand,currentTokenForMatch, abstractTokenInPattern);if(abstractTokenInPattern.getSourceFieldName()==null){
		
		}else if(abstractTokenInPattern.getSourceFieldName().equals("levelNumber")){
			matchedCommandAdd.setLevelNumber(((Long)currentTokenForMatch.getDeger()));
			matchedCommandAdd.getParameters().put("levelNumber", matchedCommandAdd.getLevelNumber());
		
		}else if(abstractTokenInPattern.getSourceFieldName().equals("grupName")){
		
			if(currentTokenForMatch.getColumnNameToken()!=null){
				matchedCommandAdd.setGrupName((String) currentTokenForMatch.getColumnNameToken().getDeger());
			}else{
				matchedCommandAdd.setGrupName((String) currentTokenForMatch.getDeger());
			}
			matchedCommandAdd.getParameters().put("grupName", matchedCommandAdd.getGrupName());
		}
		else if(abstractTokenInPattern.getSourceFieldName().equals("arrayLength")){
			matchedCommandAdd.setArrayLength((long) currentTokenForMatch.getDeger());
			matchedCommandAdd.getParameters().put("arrayLength", matchedCommandAdd.getArrayLength());
		}
	}

	@Override
	public AbstractCommand createElement() {
		ElementProgramGrupNatural createdElement = new ElementProgramGrupNatural(ReservedNaturalKeywords.PROGRAM_GROUP, "GENERAL.PROGRAM_GROUP");
		return createdElement;
		
	}
	
	
	@Override
	public AbstractCommand getmatchedCommand(List<AbstractToken> tokenListesi, int offset){
		return super.getmatchedCommand(tokenListesi, offset);
	}
	
	


	

}
