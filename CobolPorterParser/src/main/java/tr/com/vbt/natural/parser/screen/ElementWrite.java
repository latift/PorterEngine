package tr.com.vbt.natural.parser.screen;

import java.util.ArrayList;
import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.token.AbstractToken;


public class ElementWrite extends AbstractCommand{
	
	private List<AbstractToken> inputParameters=new ArrayList<AbstractToken>();
	
	public ElementWrite(AbstractToken baseToken,
			List<AbstractToken> tokenListesi, AbstractCommand parent) {
		super(ReservedNaturalKeywords.WRITE,"SCREEN.*.WRITE");
	}
	
	public ElementWrite(String elementName,String detailedCobolName) {
		super(elementName, detailedCobolName);
	}

	@Override
	public String exportContents() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedNaturalKeywords.WRITE +"=\""+ this.inputParameters+"\n");
		
		for (AbstractToken token : inputParameters) {
			sb.append(" "+ token.toString());	
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
		sb.append(" "+ReservedNaturalKeywords.WRITE +"=\""+ this.inputParameters+"\n");
		
		for (AbstractToken token : inputParameters) {
			sb.append(" "+ token.toString().replace("-", "_"));	
		}
		sb.append("\"\n");
		
		return sb.toString();
	}

	public List<AbstractToken> getInputParameters() {
		return inputParameters;
	}

	public void setInputParameters(List<AbstractToken> inputParameters) {
		this.inputParameters = inputParameters;
	}


	

	

	
	
}
