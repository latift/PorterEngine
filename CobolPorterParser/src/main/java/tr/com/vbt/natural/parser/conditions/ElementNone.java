package tr.com.vbt.natural.parser.conditions;

import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.AbstractMultipleLinesCommand;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.token.AbstractToken;

/*
 * *S**DECIDE ON EVERY #SECIM
   *S**  VALUE 1 FETCH RETURN 'TOPADEP5'
 */
public class ElementNone extends AbstractMultipleLinesCommand{
	
	private AbstractToken valueToken;
	
	public ElementNone(AbstractToken baseToken,
			List<AbstractToken> tokenListesi, AbstractCommand parent) {
		super("ElementNone","GENERAL.*.NONE");
	}
	
	public ElementNone(String elementName,String detailedCobolName) {
		super(elementName, detailedCobolName);
	}

	

	@Override
	public String exportContents() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedNaturalKeywords.NONE +" \"");
		sb.append("\"\n");
		return sb.toString();
		
		
		
	}

	@Override
	public boolean generateDetailedCobolName() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String exportCommands() {
		StringBuilder sb=new StringBuilder();
		sb.append(ReservedNaturalKeywords.NONE);
		sb.append("\"\n");
		return sb.toString();
	}

	public AbstractToken getValueToken() {
		return valueToken;
	}

	public void setValueToken(AbstractToken valueToken) {
		this.valueToken = valueToken;
	}


	
}
