package tr.com.vbt.natural.parser.basicverbs;

import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.token.AbstractToken;

/**
//  EXAMINE  BASLIK '-' REPLACE WITH ' '
 * 
 * EXAMINE  Uzunluk:0 Satir No:98 Tipi:OzelKelime
BASLIK  Uzunluk:0 Satir No:98 Tipi:Kelime LocalVariable
-  Uzunluk:0 Satir No:98 Tipi:Kelime
REPLACE  Uzunluk:0 Satir No:98 Tipi:Kelime LocalVariable
WITH  Uzunluk:0 Satir No:98 Tipi:OzelKelime
  Uzunluk:0 Satir No:98 Tipi:Kelime
*
*/
public class ElementExamineReplaceWith extends AbstractCommand{
	
	public ElementExamineReplaceWith(AbstractToken baseToken, List<AbstractToken> tokenListesi, AbstractCommand parent) {
		super("ElementExamine","EXAMINE");
	}
	
	public ElementExamineReplaceWith(String elementName,String detailedCobolName) {
		super(elementName, detailedCobolName);
	}

	@Override
	public String exportContents() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedNaturalKeywords.EXAMINE_REPLACE_WITH);
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
		sb.append(" "+ReservedNaturalKeywords.EXAMINE_REPLACE_WITH);
		return sb.toString();
	}


	
}
