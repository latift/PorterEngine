package tr.com.vbt.cobol.parser.basicverbs;

import java.util.ArrayList;
import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.token.AbstractToken;

/**MOVE CCX TO CC1 --> CC1=CCX;
 * 
 * @author 47159500
 *
 */
public class ElementMove extends AbstractCommand{
	
	private String dataToMove;
	
	private List<String> destVariable=new ArrayList<String>();
	
	public ElementMove(AbstractToken baseToken,
			List<AbstractToken> tokenListesi, AbstractCommand parent) {
		super("Move","PROCEDURE_DIVISION.*.MOVE");
	}

	public ElementMove(String elementName,String detailedCobolName) {
		super(elementName, detailedCobolName);
	}
	
	@Override
	public String exportContents() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedCobolKeywords.MOVE +"=\""+ this.dataToMove+" TO ");
		for (String data : destVariable) {
			sb.append(" "+ data);	
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
		sb.append(" "+ReservedCobolKeywords.MOVE +"=\""+ this.dataToMove+" TO ");
		for (String data : destVariable) {
			sb.append(" "+ data);	
		}
		sb.append("\"\n");
		return sb.toString();
	}

	public String getDataToMove() {
		return dataToMove;
	}

	public void setDataToMove(String dataToMove) {
		this.dataToMove = dataToMove;
	}

	public List<String> getDestVariable() {
		return destVariable;
	}

	public void setDestVariable(List<String> destVariable) {
		this.destVariable = destVariable;
	}

	
	
	

	

	
}
