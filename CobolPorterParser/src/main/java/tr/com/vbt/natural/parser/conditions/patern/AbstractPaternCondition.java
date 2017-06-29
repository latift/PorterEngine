package tr.com.vbt.natural.parser.conditions.patern;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.patern.AbstractPattern;
import tr.com.vbt.token.AbstractToken;

public abstract class AbstractPaternCondition extends AbstractPattern{

	@Override
	public void setTokenToElement(AbstractCommand matchedCommand,
			AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public AbstractCommand createElement() {
		// TODO Auto-generated method stub
		return null;
	}

}
