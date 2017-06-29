package tr.com.vbt.natural.parser.loops.patern;

import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.natural.parser.loops.ElementEscapeTop;
import tr.com.vbt.natural.parser.loops.ElementStackTopCommand;
import tr.com.vbt.patern.AbstractPattern;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.GenelTipToken;
import tr.com.vbt.token.KelimeToken;
import tr.com.vbt.token.OzelKelimeToken;

/**
 * //ESCAPE TOP (RP1.)
 *
 */
public class PaternStackTop extends AbstractPattern {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// AbstractPattern asp =new PaternAdd();

	}

	public PaternStackTop() {
		super();

		// ESCAPE_TOP
		AbstractToken astKeyword = new OzelKelimeToken(ReservedNaturalKeywords.STACK_TOP, 0, 0, 0);
		astKeyword.setSourceFieldName("FIRST_COMMAND");
		patternTokenList.add(astKeyword);

		AbstractToken astCommand=new KelimeToken();
				astCommand.setTekrarlayabilir("+");
		astCommand.setSourceFieldName("command");
		patternTokenList.add(astCommand);

	}

	@Override
	public AbstractCommand createElement() {
		ElementStackTopCommand elementEscapeTop = new ElementStackTopCommand(ReservedNaturalKeywords.STACK_TOP_COMMAND,
				"GENERAL.*.STACK_TOP_COMMAND");
		return elementEscapeTop;
	}

	@Override
	public void setTokenToElement(AbstractCommand matchedCommand, AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {

		ElementStackTopCommand matchedCommandAdd = (ElementStackTopCommand) matchedCommand;

		super.setSatirNumarasi(matchedCommand, currentTokenForMatch, abstractTokenInPattern);
		if (abstractTokenInPattern.getSourceFieldName() == null) {

		} else if (abstractTokenInPattern.getSourceFieldName().equals("command")) {

			matchedCommandAdd.setCommand(currentTokenForMatch);

			matchedCommandAdd.getParameters().put("command", matchedCommandAdd.getCommand());

		}
	}
	
	public AbstractCommand getmatchedCommand(List<AbstractToken> tokenListesi, int offset){
		return super.getmatchedCommand(tokenListesi, offset);
	}


}
