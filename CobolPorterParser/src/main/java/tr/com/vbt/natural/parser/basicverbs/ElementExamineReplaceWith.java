package tr.com.vbt.natural.parser.basicverbs;

import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.token.AbstractToken;

/**
//  EXAMINE  BASLIK '-' REPLACE WITH ' '
 * 
 *  EXAMINE FULL PADI1 FOR ' '  REPLACE WITH '*'
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
	
	private AbstractToken sourceToken; //PAD1
	
	private AbstractToken searchVar;// ' '
	
	private AbstractToken replaceVar; // '*'
	
	
	
	public ElementExamineReplaceWith(AbstractToken baseToken, List<AbstractToken> tokenListesi, AbstractCommand parent) {
		super("ElementExamine","EXAMINE");
	}
	
	public ElementExamineReplaceWith(String elementName,String detailedCobolName) {
		super(elementName, detailedCobolName);
	}

	@Override
	public String exportContents() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedNaturalKeywords.EXAMINE_REPLACE_WITH +"=\"");
		if(sourceToken!=null){
			sb.append("sourceToken="+sourceToken.getDeger());
		}
		if(searchVar!=null){
			sb.append(" SearchVar="+searchVar.getDeger());
		}
		if(replaceVar!=null){
			sb.append(" replaceVar="+replaceVar.getDeger());
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
		sb.append(" "+ReservedNaturalKeywords.EXAMINE_REPLACE_WITH +"=\"");
		if(sourceToken!=null){
			sb.append("sourceToken="+sourceToken.getDeger());
		}
		if(searchVar!=null){
			sb.append(" SearchVar="+searchVar.getDeger());
		}
		if(replaceVar!=null){
			sb.append(" replaceVar="+replaceVar.getDeger());
		}
		sb.append("\"\n");
		return sb.toString();
	}

	public AbstractToken getSourceToken() {
		return sourceToken;
	}

	public void setSourceToken(AbstractToken sourceToken) {
		this.sourceToken = sourceToken;
	}

	public AbstractToken getSearchVar() {
		return searchVar;
	}

	public void setSearchVar(AbstractToken searchVar) {
		this.searchVar = searchVar;
	}

	public AbstractToken getReplaceVar() {
		return replaceVar;
	}

	public void setReplaceVar(AbstractToken replaceVar) {
		this.replaceVar = replaceVar;
	}

	
	

	
}
