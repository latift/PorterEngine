package tr.com.vbt.natural.parser.screen;

import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.token.AbstractToken;


public class ElementSize extends AbstractCommand{
	
	int lineCount;
	
	int lineLength;
	
	public ElementSize(AbstractToken baseToken,
			List<AbstractToken> tokenListesi, AbstractCommand parent) {
		super(ReservedNaturalKeywords.SIZE,"SCREEN.*.SIZE");
	}
	
	public ElementSize(String elementName,String detailedCobolName) {
		super(elementName, detailedCobolName);
	}

	@Override
	public String exportContents() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedNaturalKeywords.SIZE +" SatirSayisi"+ this.lineCount+" SatirBoyu"+ this.lineLength+"\n");
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
		sb.append(" "+ReservedNaturalKeywords.SIZE +" SatirSayisi"+ this.lineCount+" SatirBoyu"+ this.lineLength+"\n");
		sb.append("\"\n");
		return sb.toString();
	}

	public int getLineCount() {
		return lineCount;
	}

	public void setLineCount(int lineCount) {
		this.lineCount = lineCount;
	}

	public int getLineLength() {
		return lineLength;
	}

	public void setLineLength(int lineLength) {
		this.lineLength = lineLength;
	}
	
	
}
