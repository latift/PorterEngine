package tr.com.vbt.natural.parser.loops.patern;

import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.natural.parser.loops.ElementEscapeTop;
import tr.com.vbt.patern.AbstractPattern;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.OzelKelimeToken;

/**
 * //ESCAPE TOP (RP1.)
 *
 */
public class PaternEscapeTop extends AbstractPattern {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// AbstractPattern asp =new PaternAdd();

	}

	public PaternEscapeTop() {
		super();

		// ESCAPE_TOP
		AbstractToken astKeyword = new OzelKelimeToken("ESCAPE_TOP", 0, 0, 0);
		astKeyword.setSourceFieldName("FIRST_COMMAND");
		patternTokenList.add(astKeyword);


	}

	@Override
	public AbstractCommand createElement() {
		ElementEscapeTop elementEscapeTop = new ElementEscapeTop(ReservedNaturalKeywords.ESCAPE_TOP,
				"GENERAL.*.ESCAPE_TOP");
		return elementEscapeTop;
	}

	@Override
	public void setTokenToElement(AbstractCommand matchedCommand, AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {

		ElementEscapeTop matchedCommandAdd = (ElementEscapeTop) matchedCommand;

		super.setSatirNumarasi(matchedCommand, currentTokenForMatch, abstractTokenInPattern);
		if (abstractTokenInPattern.getSourceFieldName() == null) {

		} else if (abstractTokenInPattern.getSourceFieldName().equals("LOOP_NAME")) {

			matchedCommandAdd.setLoopName(currentTokenForMatch);

			matchedCommandAdd.getParameters().put("LOOP_NAME", matchedCommandAdd.getLoopName());

		}else if(abstractTokenInPattern.getSourceFieldName().equals("FIRST_COMMAND")){
			matchedCommandAdd.getParameters().put("FIRST_COMMAND", currentTokenForMatch);
		}
	}
	
	public AbstractCommand getmatchedCommand(List<AbstractToken> tokenListesi, int offset){
		return super.getmatchedCommand(tokenListesi, offset);
	}


}
