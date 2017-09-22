package tr.com.vbt.cobol.parser.enders;

import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.AbstractEndingCommand;
import tr.com.vbt.cobol.parser.Levelable;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.token.AbstractToken;

//END_GROUP_DATA_TYPE
public class ElementEndGroupDataType extends AbstractEndingCommand implements Levelable{
	
	private long levelNumber;
	
	public ElementEndGroupDataType(AbstractToken baseToken,
			List<AbstractToken> tokenListesi, AbstractCommand parent, long levelNumber) {
		super("EndPerform","WORKING-STORAGE-SECTION.*.END_GROUP_DATA_TYPE");
		this.levelNumber=levelNumber;
	}
	
	public ElementEndGroupDataType(String elementName,String detailedCobolName) {
		super(elementName, detailedCobolName);
	}

	public ElementEndGroupDataType(String elementName,String detailedCobolName, long levelNumber) {
		super(elementName, detailedCobolName);
		this.levelNumber=levelNumber;
	}
	

	@Override
	public String exportContents() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedCobolKeywords.END_GROUP_DATA_TYPE +levelNumber+"\n");
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
		sb.append(" "+ReservedCobolKeywords.END_GROUP_DATA_TYPE +levelNumber+ "\n");
		return sb.toString();
	}

	public long getLevelNumber() {
		return levelNumber;
	}

	public void setLevelNumber(long levelNumber) {
		this.levelNumber = levelNumber;
	}

	@Override
	public String getDataName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDataName(String dataName) {
		// TODO Auto-generated method stub
		
	}



	
}
