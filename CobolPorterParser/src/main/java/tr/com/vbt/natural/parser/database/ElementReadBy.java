package tr.com.vbt.natural.parser.database;

import java.util.ArrayList;
import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.AbstractMultipleLinesCommand;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.token.AbstractToken;

/**
 * *S**READ UHM-ACILIS1 BY UHT-SCTARIH EQ #UHT-SCTRH-N
	
	READ  Uzunluk:0 Satir No:2 Tipi:OzelKelime
	UHM-ACILIS1  Uzunluk:0 Satir No:2 Tipi:Kelime
	BY  Uzunluk:0 Satir No:2 Tipi:OzelKelime
	UHT-SCTARIH  Uzunluk:0 Satir No:2 Tipi:Kelime
	EQ  Uzunluk:0 Satir No:2 Tipi:OzelKelime
	UHT-SCTRH-N  Uzunluk:0 Satir No:2 Tipi:Kelime
 *
 */
public class ElementReadBy extends AbstractMultipleLinesCommand{
	
	private AbstractToken viewName; //Liman
	
	private List<AbstractToken> conditionList=new ArrayList<AbstractToken>();
	
	private AbstractToken thru; //Liman
	
	
	public ElementReadBy(AbstractToken baseToken,
			List<AbstractToken> tokenListesi, AbstractCommand parent) {
		super("Read","DATABASE.*.READ_BY");
	}

	public ElementReadBy(String elementName,String detailedCobolName) {
		super(elementName, detailedCobolName);
	}
	
	@Override
	public String exportContents() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedNaturalKeywords.READ_BY +"=\"");
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
		sb.append(" "+ReservedNaturalKeywords.READ_BY +"=\"");
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

	public AbstractToken getThru() {
		return thru;
	}

	public void setThru(AbstractToken thru) {
		this.thru = thru;
	}

	



	
	
	

	

	
}
