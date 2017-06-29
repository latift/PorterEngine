package tr.com.vbt.cobol.parser.conditions;

import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.AbstractMultipleLinesCommand;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.token.AbstractToken;

/**
 * EVALUATE TRUE
      WHEN WS-A > 2
         DISPLAY 'WS-A GREATER THAN 2'

      WHEN WS-A < 0
         DISPLAY 'WS-A LESS THAN 0'

      WHEN OTHER
         DISPLAY 'INVALID VALUE OF WS-A'
   END-EVALUATE.
 * @author 47159500
 *
 */
public class ElementEvaluate extends AbstractMultipleLinesCommand{
	
	private String condition;
	
	public ElementEvaluate(AbstractToken baseToken,
			List<AbstractToken> tokenListesi, AbstractCommand parent) {
		super("ElementEvaluate","PROCEDURE_DIVISION.*.EVALUATE");
	}
	
	
	public ElementEvaluate(String elementName,String detailedCobolName) {
		super(elementName, detailedCobolName);
	}

	

	@Override
	public String exportContents() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedCobolKeywords.EVALUATE +"=\"");
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
		sb.append(" "+ReservedCobolKeywords.EVALUATE +"=\"");
		sb.append("\"\n");
		return sb.toString();
	}


	public String getCondition() {
		return condition;
	}


	public void setCondition(String condition) {
		this.condition = condition;
	}


}
