package tr.com.vbt.natural.parser.basicverbs;

import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.token.AbstractToken;

// 6214 STORE IDGIDBS-TGECICI
public class ElementStore extends AbstractCommand{
	
	private AbstractToken tableName;
	
	public ElementStore(AbstractToken baseToken,
			List<AbstractToken> tokenListesi, AbstractCommand parent) {
		super("ElementFetch","GENERAL.*.STORE");
	}
	
	public ElementStore(String elementName,String detailedCobolName) {
		super(elementName, detailedCobolName);
	}


	@Override
	public String exportContents() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedNaturalKeywords.STORE +"=\""+ this.tableName+"\"\n");
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
		sb.append(" "+ReservedNaturalKeywords.STORE +"=\""+ this.tableName+"\"\n");
		return sb.toString();
	}

	public AbstractToken getTableName() {
		return tableName;
	}

	public void setTableName(AbstractToken tableName) {
		this.tableName = tableName;
	}


	
	
	

	
}
