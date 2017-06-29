package tr.com.vbt.cobol.parser;

import java.util.ListIterator;

import tr.com.vbt.token.AbstractToken;

public class ElementGlobalVariable extends AbstractCommand{

	
	public ElementGlobalVariable(String elementName, ListIterator<AbstractToken> parentTokenListIterator) {
		super(elementName,"DATA_DIVISION.WORKING-STORAGE_SECTION.PIC");
		//this.regExString=RegExManager.REGEX_GLOBAL_VARIABLE;
	}
	
	public ElementGlobalVariable(String elementName,String detailedCobolName) {
		super(elementName, detailedCobolName);
	}
	private String variableName;
	private String variableLevel;
	private String variableType;
	private String variableLength;
	

	@Override
	public String toString() {
		StringBuilder sb=new StringBuilder();
		sb.append(" Type:"+this.variableType);
		sb.append(" Name:"+this.variableName);
		sb.append(" Level:"+this.variableLevel);
		sb.append(" Length:"+this.variableLength);
		return sb.toString();
	}


	@Override
	public String exportContents() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public boolean generateDetailedCobolName() {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public String exportCommands() {
		// TODO Auto-generated method stub
		return null;
	}


		
}

