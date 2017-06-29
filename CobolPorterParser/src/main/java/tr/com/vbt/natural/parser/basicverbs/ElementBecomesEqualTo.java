package tr.com.vbt.natural.parser.basicverbs;

import java.util.ArrayList;
import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.token.AbstractToken;

// #FARK1 := T-MIKTAR - T-UPLIFT
// #T-TERM :=T-INIT-USER
//#TOPLAM-UPLIFT2  := #TOPLAM-UPLIFT2  + ( T-GYOG * T-UPLIFT )

public class ElementBecomesEqualTo extends AbstractCommand{
	
	private List<AbstractToken> copyFrom=new ArrayList<AbstractToken>();  //#TOPLAM-UPLIFT2  + ( T-GYOG * T-UPLIFT )
	
	private AbstractToken copyTo;
	
	public ElementBecomesEqualTo(String elementName,String detailedCobolName) {
		super(elementName, detailedCobolName);
	}
	
	public ElementBecomesEqualTo(AbstractCommand parent, String elementName,
			String detailedCobolName, List<AbstractCommand> commandListesi) {
		super(parent, elementName, detailedCobolName, commandListesi);
	}
	
	public ElementBecomesEqualTo(String elementName,String detailedCobolName, List<AbstractCommand> commandListesi) {
		super(elementName, detailedCobolName, commandListesi);
	}


	


	@Override
	public String exportCommands() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedNaturalKeywords.COPY_FROM +"=\"");
		for (AbstractToken src : copyFrom) {
			sb.append(src.getDeger()+" ");
		}
		sb.append("CopyTo: ");
		sb.append(copyTo+" ");
		sb.append("\"\n");
		return sb.toString();
	}
	

	@Override
	public String exportContents() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedNaturalKeywords.COPY_FROM +"=\"");
		sb.append("CopyFrom: ");
		for (AbstractToken src : copyFrom) {
			sb.append(src.getDeger()+" ");
		}
		sb.append("CopyTo: ");
		sb.append(copyTo+" ");
		sb.append("\"\n");
		return sb.toString();
	}

	@Override
	public boolean generateDetailedCobolName() {
		// TODO Auto-generated method stub
				return false;
	}

	public List<AbstractToken> getCopyFrom() {
		return copyFrom;
	}

	public void setCopyFrom(List<AbstractToken> copyFrom) {
		this.copyFrom = copyFrom;
	}

	public AbstractToken getCopyTo() {
		return copyTo;
	}

	public void setCopyTo(AbstractToken copyTo) {
		this.copyTo = copyTo;
	}





	

	
}
