package tr.com.vbt.cobol.parser.tableprocessing;

import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.token.AbstractToken;

//PERFORM paraX Y TIMES --> for (int i=0; i<Y;i++){paraX();}
//Parameters: ParagraghName, RunCount
public class ElementSearchAll extends AbstractCommand{
	
	private String paragraghName;
	
	public ElementSearchAll(AbstractToken baseToken,
			List<AbstractToken> tokenListesi, AbstractCommand parent) {
		super("ElementSearchAll","PROCEDURE_DIVISION.*.SEARCH_ALL");
	}
	
	public ElementSearchAll(String elementName,String detailedCobolName) {
		super(elementName, detailedCobolName);
	}

	
	@Override
	public String exportContents() {
		return null;
	}

	@Override
	public boolean generateDetailedCobolName() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String exportCommands() {
		return null;
	}

	public String getParagraghName() {
		return paragraghName;
	}

	public void setParagraghName(String paragraghName) {
		this.paragraghName = paragraghName;
	}


	
}
