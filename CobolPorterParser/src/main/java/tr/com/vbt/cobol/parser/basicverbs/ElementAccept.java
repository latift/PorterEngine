package tr.com.vbt.cobol.parser.basicverbs;

import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.token.AbstractToken;

/**
 * ACCEPT WS-STUDENT-NAME.
   ACCEPT WS-DATE FROM SYSTEM-DATE
 * 
 * 
 *
 */
public class ElementAccept extends AbstractCommand{
	
	private String inputName;
	
	public ElementAccept(AbstractToken baseToken,
			List<AbstractToken> tokenListesi, AbstractCommand parent,int satirNumarasi) {
		super("Accept","PROCEDURE_DIVISION.*.ACCEPT", satirNumarasi);
	}
	
	public ElementAccept(String elementName,String detailedCobolName,int satirNumarasi) {
		super(elementName, detailedCobolName, satirNumarasi);
	}

	public ElementAccept(String elementName,String detailedCobolName) {
		super(elementName, detailedCobolName);
	}

	@Override
	public String exportContents() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedCobolKeywords.ACCEPT +"=\""+ this.inputName+"\"\n");
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
		sb.append(" "+ReservedCobolKeywords.ACCEPT +"=\""+ this.inputName+"\"\n");
		return sb.toString();
	}

	public String getInputName() {
		return inputName;
	}

	public void setInputName(String inputName) {
		this.inputName = inputName;
	}

	

	
}
