package tr.com.vbt.natural.parser.database;

import java.util.ArrayList;
import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
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
public class ElementFindNumberWith extends AbstractCommand{
	
	private String viewName; //Liman
	
	private String countName; //Count
	
	private List<AbstractToken> conditionList=new ArrayList<AbstractToken>();
	
	
	public ElementFindNumberWith(AbstractToken baseToken,
			List<AbstractToken> tokenListesi, AbstractCommand parent) {
		super("Find Number","DATABASE.*.FIND_NUMBER_WITH");
	}

	public ElementFindNumberWith(String elementName,String detailedCobolName) {
		super(elementName, detailedCobolName);
	}
	
	@Override
	public String exportContents() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedNaturalKeywords.FIND+" "+ countName +"=\"");
		for (AbstractToken data : conditionList) {
			sb.append(" "+ data.getDeger());	
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
		sb.append(" "+ReservedNaturalKeywords.FIND+" "+ countName +"=\"");
		for (AbstractToken data : conditionList) {
			sb.append(" "+ data.getDeger());	
		}
		sb.append("\"\n");
		return sb.toString();
	}

	

	public String getViewName() {
		return viewName;
	}

	public void setViewName(String viewName) {
		this.viewName = viewName;
	}

	public List<AbstractToken> getConditionList() {
		return conditionList;
	}

	public void setConditionList(List<AbstractToken> conditionList) {
		this.conditionList = conditionList;
	}

	public String getCountName() {
		return countName;
	}

	public void setCountName(String countName) {
		this.countName = countName;
	}
	
	
	
}
