package tr.com.vbt.natural.parser.database;

import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.AbstractMultipleLinesCommand;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.token.AbstractToken;

/**
	
 *IF  Uzunluk:0 Satir No:58 Tipi:OzelKelime
NO  Uzunluk:0 Satir No:58 Tipi:OzelKelime
RECORDS  Uzunluk:0 Satir No:58 Tipi:OzelKelime
 *
 */
public class ElementIfNoRecords extends AbstractMultipleLinesCommand{
	
	public ElementIfNoRecords(AbstractToken baseToken,
			List<AbstractToken> tokenListesi, AbstractCommand parent) {
		super("IfNoRecords","DATABASE.*.IFNORECORDS");
	}

	public ElementIfNoRecords(String elementName,String detailedCobolName) {
		super(elementName, detailedCobolName);
	}
	
	@Override
	public String exportContents() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedNaturalKeywords.IF_NO_RECORDS);
		sb.append(" 	Ender:"+ this.endingCommand);
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
		sb.append(" "+ReservedNaturalKeywords.IF_NO_RECORDS);
		sb.append("\"\n");
		return sb.toString();
	}

	

	
	
	

	

	
}
