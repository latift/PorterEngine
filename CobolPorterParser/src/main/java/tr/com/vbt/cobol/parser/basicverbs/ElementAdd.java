package tr.com.vbt.cobol.parser.basicverbs;

import java.util.ArrayList;
import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedCobolKeywords;

//ADD WS-NUM1 WS-NUM2 TO WS-NUM3 WS-NUM4.
public class ElementAdd extends AbstractCommand{
	
	//ADD WS-NUM1 TO WS-NUM2.
	//parameters sourceNum, destNum; 
	
	private List<String> sourceNum=new ArrayList<String>();
	
	private List<String> destNum=new ArrayList<String>();
	
	/*public ElementAdd(AbstractToken baseToken,
			List<AbstractToken> tokenListesi, AbstractCommand parent) {
		//super("Add","PROCEDURE_DIVISION.*.ADD");
		}*/
	
	public ElementAdd(String elementName,String detailedCobolName,int satirNumarasi) {
		super(elementName, detailedCobolName, satirNumarasi);
	}
	
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
		for (String src : sourceNum) {
			sb.append(src+" ");
		}
		sb.append("DESTINATION: ");
		for (String src : destNum) {
			sb.append(src+" ");
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

	public List<String> getSourceNum() {
		return sourceNum;
	}

	public void setSourceNum(List<String> sourceNum) {
		this.sourceNum = sourceNum;
	}

	public List<String> getDestNum() {
		return destNum;
	}

	public void setDestNum(List<String> destNum) {
		this.destNum = destNum;
	}

	

	
}
