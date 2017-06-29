package tr.com.vbt.natural.parser.loops;

import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.AbstractMultipleLinesCommand;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.token.AbstractToken;

//FOR I = 1 TO SECILENSAY	
// FOR J=1 TO IEKSI1
//FOR I:=1 TO SECILENSAY 
public class ElementFor extends AbstractMultipleLinesCommand {

	String indexName; //k2

	AbstractToken loopStartIndex; //begin

	AbstractToken loopEndPoint;  // k1
	
	AbstractToken stepToken; //
	

	public ElementFor(AbstractToken baseToken, List<AbstractToken> tokenListesi, AbstractCommand parent) {
		super("ElementFor", "LOOPS.*.FOR");
	}

	public ElementFor(String elementName, String detailedCobolName) {
		super(elementName, detailedCobolName);
	}

	@Override
	public String exportContents() {
		StringBuilder sb = new StringBuilder();
		sb.append(" " + ReservedNaturalKeywords.FOR + " ");
		sb.append(" " + indexName + " " + loopStartIndex + " ");
		if (loopEndPoint != null) {
			sb.append(loopEndPoint.getDeger());
		}

		sb.append(" Ender:" + this.endingCommand);
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
		StringBuilder sb = new StringBuilder();
		sb.append(" " + ReservedNaturalKeywords.FOR + " ");
		sb.append(" " + indexName + " " + loopStartIndex + " ");
		if (loopEndPoint != null) {
			sb.append(loopEndPoint.getDeger());
		}

		sb.append(" Ender:" + this.endingCommand);
		sb.append("\"\n");
		return sb.toString();
	}

	public String getIndexName() {
		return indexName;
	}

	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}


	public AbstractToken getLoopEndPoint() {
		return loopEndPoint;
	}

	public void setLoopEndPoint(AbstractToken loopEndPoint) {
		this.loopEndPoint = loopEndPoint;
	}

	public AbstractToken getLoopStartIndex() {
		return loopStartIndex;
	}

	public void setLoopStartIndex(AbstractToken loopStartIndex) {
		this.loopStartIndex = loopStartIndex;
	}

	public AbstractToken getStepToken() {
		return stepToken;
	}

	public void setStepToken(AbstractToken stepToken) {
		this.stepToken = stepToken;
	}
	

}
