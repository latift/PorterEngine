package tr.com.vbt.natural.parser.basicverbs;

import java.util.ArrayList;
import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.token.AbstractToken;

//*  5130   COMPRESS IDGIDBS-TOZLUK.ADI ' ' IDGIDBS-TOZLUK.SOYAD INTO                                                                    
//   5132     MAP2.ADSOY1 
public class ElementCompress extends AbstractCommand{
	
	private List<AbstractToken> sourceList=new ArrayList<AbstractToken>();
	
	private AbstractToken dest;
	
	private boolean isLeavingNo;
	
	private boolean isFull;
	
	public ElementCompress(String elementName,String detailedCobolName) {
		super(elementName, detailedCobolName);
	}
	
	public ElementCompress(AbstractCommand parent, String elementName,
			String detailedCobolName, List<AbstractCommand> commandListesi) {
		super(parent, elementName, detailedCobolName, commandListesi);
	}
	
	public ElementCompress(String elementName,String detailedCobolName, List<AbstractCommand> commandListesi) {
		super(elementName, detailedCobolName, commandListesi);
	}
	
	public ElementCompress(AbstractToken baseToken,
			List<AbstractToken> tokenListesi, AbstractCommand parent) {
		super("ElementComment","GENERAL.*.COMPRESS");
	}


	@Override
	public String exportCommands() {
		StringBuilder sb=new StringBuilder(); 
		sb.append(this.satirNumarasi);
		sb.append(" "+ReservedNaturalKeywords.COMPRESS);
		if(isFull){
			sb.append(" FULL");
		}
		sb.append(" "+"=\"");
		for (AbstractToken src : sourceList) {
			sb.append(src.getDeger()+" ");
		}
		sb.append("Into: ");
		sb.append(dest.getDeger()+" ");
		if(isLeavingNo){
			sb.append(" Leaving No");
		}
		sb.append("\"\n");
		return sb.toString();
	}
	

	@Override
	public String exportContents() {
		StringBuilder sb=new StringBuilder(); 
		sb.append(this.satirNumarasi);
		sb.append(" "+ReservedNaturalKeywords.COMPRESS);
		if(isFull){
			sb.append(" FULL");
		}
		sb.append(" "+"=\"");
		for (AbstractToken src : sourceList) {
			sb.append(src.getDeger()+" ");
		}
		sb.append("Into: ");
		sb.append(dest.getDeger()+" ");
		if(isLeavingNo){
			sb.append(" Leaving No");
		}
		sb.append("\"\n");
		return sb.toString();
	}

	@Override
	public boolean generateDetailedCobolName() {
				return false;
	}

	public List<AbstractToken> getSourceList() {
		return sourceList;
	}

	public void setSourceList(List<AbstractToken> sourceList) {
		this.sourceList = sourceList;
	}

	public AbstractToken getDest() {
		return dest;
	}

	public void setDest(AbstractToken dest) {
		this.dest = dest;
	}

	public boolean isLeavingNo() {
		return isLeavingNo;
	}

	public void setLeavingNo(boolean isLeavingNo) {
		this.isLeavingNo = isLeavingNo;
	}

	public boolean isFull() {
		return isFull;
	}

	public void setFull(boolean isFull) {
		this.isFull = isFull;
	}
	
	
}
