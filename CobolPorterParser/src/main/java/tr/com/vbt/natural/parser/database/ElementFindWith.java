package tr.com.vbt.natural.parser.database;

import java.util.ArrayList;
import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.AbstractMultipleLinesCommand;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.token.AbstractToken;

/**FIND LIMAN WITH L-LIMAN-KODU=#T-KLIM
	
	
 * FIND  Uzunluk:0 Satir No:57 Tipi:OzelKelime
LIMAN  Uzunluk:0 Satir No:57 Tipi:Kelime
WITH  Uzunluk:0 Satir No:57 Tipi:Kelime
L-LIMAN-KODU  Uzunluk:0 Satir No:57 Tipi:Kelime
=  Uzunluk:1 Satir No:57 Tipi:Karakter
T-KLIM  Uzunluk:0 Satir No:57 Tipi:Kelime
 *
 */
public class ElementFindWith extends AbstractMultipleLinesCommand{
	
	private AbstractToken viewName; //Liman
	
	private AbstractToken maxResultCount; //Liman
	
	
	private List<AbstractToken> conditionList=new ArrayList<AbstractToken>();
	
	
	public ElementFindWith(AbstractToken baseToken,
			List<AbstractToken> tokenListesi, AbstractCommand parent) {
		super("Find","DATABASE.*.FIND_WITH");
	}

	public ElementFindWith(String elementName,String detailedCobolName) {
		super(elementName, detailedCobolName);
	}
	
	@Override
	public String exportContents() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedNaturalKeywords.FIND +"=\"");
		for (AbstractToken data : conditionList) {
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
		sb.append(" "+ReservedNaturalKeywords.FIND +"=\"");
		for (AbstractToken data : conditionList) {
			sb.append(" "+ data.getDeger());	
		}
		if(this.endingCommand!=null){
			sb.append(" 	Ender:"+this.endingCommand.toString() +" ");
		}
		sb.append("\"\n");
		return sb.toString();
	}

	



	public AbstractToken getViewName() {
		return viewName;
	}

	public void setViewName(AbstractToken viewName) {
		this.viewName = viewName;
	}

	public List<AbstractToken> getConditionList() {
		return conditionList;
	}

	public void setConditionList(List<AbstractToken> conditionList) {
		this.conditionList = conditionList;
	}

	public AbstractToken getMaxResultCount() {
		return maxResultCount;
	}

	public void setMaxResultCount(AbstractToken maxResultCount) {
		this.maxResultCount = maxResultCount;
	}
	
	
	
}
