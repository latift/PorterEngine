package tr.com.vbt.natural.parser.database;

import java.util.ArrayList;
import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.AbstractMultipleLinesCommand;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.token.AbstractToken;

public class ElementSQLSelect extends AbstractMultipleLinesCommand{
	
	private List<AbstractToken> queryTokenList=new ArrayList<AbstractToken>();
	
	public ElementSQLSelect(AbstractToken baseToken,
			List<AbstractToken> tokenListesi, AbstractCommand parent) {
		super("ElementSQLSelect","DATABASE.*.SELECT");
	}

	public ElementSQLSelect(String elementName,String detailedCobolName) {
		super(elementName, detailedCobolName);
	}
	

	@Override
	public String exportContents() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedCobolKeywords.SELECT +"=\"");
		for (AbstractToken data : queryTokenList) {
			sb.append(" "+ data.getDeger());	
		}
		if(this.endingCommand!=null){
			sb.append(" 	Ender:"+this.endingCommand.toString() +" ");
		}
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
		sb.append(" "+ReservedCobolKeywords.SELECT +"=\"");
		for (AbstractToken data : queryTokenList) {
			sb.append(" "+ data.getDeger());	
		}
		if(this.endingCommand!=null){
			sb.append(" 	Ender:"+this.endingCommand.toString() +" ");
		}
		sb.append("\"\n");
		return sb.toString();
	}

	public List<AbstractToken> getQueryTokenList() {
		return queryTokenList;
	}

	public void setQueryTokenList(List<AbstractToken> queryTokenList) {
		this.queryTokenList = queryTokenList;
	}



	
	

}
