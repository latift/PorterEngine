package tr.com.vbt.natural.parser.basicverbs;

import java.util.ArrayList;
import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.token.AbstractToken;

//ADD WS-NUM1 WS-NUM2 TO WS-NUM3 WS-NUM4.
public class ElementAdd extends AbstractCommand{
	
	//ADD WS-NUM1 TO WS-NUM2.
	//parameters sourceNum, destNum; 
	
	private List<AbstractToken> sourceNum=new ArrayList<AbstractToken>();
	
	private List<AbstractToken> destNum=new ArrayList<AbstractToken>();
	
	/*public ElementAdd(AbstractToken baseToken,
			List<AbstractToken> tokenListesi, AbstractCommand parent) {
		//super("Add","GENERAL.*.ADD");
		}*/
	
	public ElementAdd(String elementName,String detailedCobolName) {
		super(elementName, detailedCobolName);
	}
	
	public ElementAdd(AbstractCommand parent, String elementName,
			String detailedCobolName, List<AbstractCommand> commandListesi) {
		super(parent, elementName, detailedCobolName, commandListesi);
	}
	
	public ElementAdd(String elementName,String detailedCobolName, List<AbstractCommand> commandListesi) {
		super(elementName, detailedCobolName, commandListesi);
	}


	


	@Override
	public String exportCommands() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedCobolKeywords.ADD +"=\"");
		sb.append("SOURCE: ");
		for (AbstractToken src : sourceNum) {
			sb.append(src.getDeger()+" ");
		}
		sb.append("DESTINATION: ");
		for (AbstractToken src : destNum) {
			sb.append(src.getDeger()+" ");
		}
		sb.append("\"\n");
		return sb.toString();
	}
	

	@Override
	public String exportContents() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedCobolKeywords.ADD +"=\""+ this.sourceNum+" "+  destNum);
		sb.append("\"\n");
		return sb.toString();
	}

	@Override
	public boolean generateDetailedCobolName() {
		// TODO Auto-generated method stub
				return false;
	}

	public List<AbstractToken> getSourceNum() {
		return sourceNum;
	}

	public void setSourceNum(List<AbstractToken> sourceNum) {
		this.sourceNum = sourceNum;
	}

	public List<AbstractToken> getDestNum() {
		return destNum;
	}

	public void setDestNum(List<AbstractToken> destNum) {
		this.destNum = destNum;
	}


	

	
}
