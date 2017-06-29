package tr.com.vbt.natural.parser.basicverbs;

import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.token.AbstractToken;

//* *S**1 REDEFINE #P-BITTAR
public class ElementRedefine extends AbstractCommand{
	
	private String redefineParameter;
	
	public ElementRedefine(AbstractToken baseToken,
			List<AbstractToken> tokenListesi, AbstractCommand parent) {
		super("Perform","GENERAL.*.REDEFINE");
	}
	
	public ElementRedefine(String elementName,String detailedCobolName) {
		super(elementName, detailedCobolName);
	}


	@Override
	public String exportContents() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedNaturalKeywords.REDEFINE +"=\""+ this.redefineParameter+"\"\n");
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
		sb.append(" "+ReservedNaturalKeywords.REDEFINE +"=\""+ this.redefineParameter+"\"\n");
		return sb.toString();
	}

	public String getRedefineParameter() {
		return redefineParameter;
	}

	public void setRedefineParameter(String redefineParameter) {
		this.redefineParameter = redefineParameter;
	}

	
	

	
}
