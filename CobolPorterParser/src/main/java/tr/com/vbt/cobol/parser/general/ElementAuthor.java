package tr.com.vbt.cobol.parser.general;

import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.KelimeToken;

//PROGRAM-ID. HELLO_WORD.
public class ElementAuthor extends AbstractCommand{
	
	private AbstractToken<KelimeToken> param;
	
	public ElementAuthor(AbstractToken baseToken,
			List<AbstractToken> tokenListesi, AbstractCommand parent) {
		super("ElementAuthor","GENERAL.*.AUTHOR");
	}
	
	public ElementAuthor(String elementName,String detailedCobolName) {
		super(elementName, detailedCobolName);
	}


	@Override
	public String exportContents() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedCobolKeywords.AUTHOR );
		sb.append("\n");
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
		sb.append(" "+ReservedCobolKeywords.AUTHOR );
		sb.append("\n");
		return sb.toString();
	}

	public AbstractToken<KelimeToken> getParam() {
		return param;
	}

	public void setParam(AbstractToken<KelimeToken> param) {
		this.param = param;
	}
	
	

	
	
}
