package tr.com.vbt.natural.parser.basicverbs;

import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.token.AbstractToken;

//DIVIDE A INTO B  --> B (B=B/A)
public class ElementDivide extends AbstractCommand{
	
	//DIVIDE A INTO B  --> B (B=B/A)
	
	private AbstractToken bolunen;
	
	private AbstractToken bolen;
	
	private AbstractToken sonuc;
	
	private AbstractToken kalan;
	
	
	public ElementDivide(String elementName,String detailedCobolName) {
		super(elementName, detailedCobolName);
	}
	
	public ElementDivide(AbstractCommand parent, String elementName,
			String detailedCobolName, List<AbstractCommand> commandListesi) {
		super(parent, elementName, detailedCobolName, commandListesi);
	}
	
	public ElementDivide(String elementName,String detailedCobolName, List<AbstractCommand> commandListesi) {
		super(elementName, detailedCobolName, commandListesi);
	}

	public ElementDivide(AbstractToken baseToken,
			List<AbstractToken> tokenListesi, AbstractCommand parent) {
		super("ElementDivide","PROCEDURE_DIVISION.*.DIVIDE");
	}
	


	@Override
	public String exportCommands() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedCobolKeywords.DIVIDE +"=\"");
		sb.append(bolunen+" ");
		sb.append("Divide By: ");
		sb.append(bolen);
		sb.append("\"\n");
		return sb.toString();
	}
	

	@Override
	public String exportContents() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedCobolKeywords.DIVIDE +"=\"");
		sb.append(bolunen+" ");
		sb.append("Divide By: ");
		sb.append(bolen);
		sb.append("\"\n");
		return sb.toString();
	}

	@Override
	public boolean generateDetailedCobolName() {
		// TODO Auto-generated method stub
				return false;
	}

	public AbstractToken getBolunen() {
		return bolunen;
	}

	public void setBolunen(AbstractToken bolunen) {
		this.bolunen = bolunen;
	}

	public AbstractToken getBolen() {
		return bolen;
	}

	public void setBolen(AbstractToken bolen) {
		this.bolen = bolen;
	}

	public AbstractToken getSonuc() {
		return sonuc;
	}

	public void setSonuc(AbstractToken sonuc) {
		this.sonuc = sonuc;
	}

	public AbstractToken getKalan() {
		return kalan;
	}

	public void setKalan(AbstractToken kalan) {
		this.kalan = kalan;
	}


	
	
	
}
