package tr.com.vbt.natural.parser.basicverbs;

import java.util.ArrayList;
import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.token.AbstractToken;

//FORMAT    LS=132 PS=21 
public class ElementFormat extends AbstractCommand{
	
	private List<AbstractToken> parametersOfFormat=new ArrayList<AbstractToken>();
	
	private int formatCode;
	
	public ElementFormat(String elementName,String detailedCobolName) {
		super(elementName, detailedCobolName);
	}
	
	public ElementFormat(AbstractCommand parent, String elementName,
			String detailedCobolName, List<AbstractCommand> commandListesi) {
		super(parent, elementName, detailedCobolName, commandListesi);
	}
	
	public ElementFormat(String elementName,String detailedCobolName, List<AbstractCommand> commandListesi) {
		super(elementName, detailedCobolName, commandListesi);
	}


	


	@Override
	public String exportCommands() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedNaturalKeywords.SET_KEY +" ");
		for (AbstractToken src : parametersOfFormat) {
			sb.append(src.getDeger()+" ");
		}
		sb.append("\n");
		return sb.toString();
	}
	

	@Override
	public String exportContents() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedNaturalKeywords.SET_KEY +" ");
		for (AbstractToken src : parametersOfFormat) {
			sb.append(src.getDeger()+" ");
		}
		sb.append("\n");
		return sb.toString();
	}

	@Override
	public boolean generateDetailedCobolName() {
		// TODO Auto-generated method stub
				return false;
	}


	public int getFormatCode() {
		return formatCode;
	}

	public void setFormatCode(int formatCode) {
		this.formatCode = formatCode;
	}

	public List<AbstractToken> getParametersOfFormat() {
		return parametersOfFormat;
	}

	public void setParametersOfFormat(List<AbstractToken> parametersOfFormat) {
		this.parametersOfFormat = parametersOfFormat;
	}

	

	

	
}
