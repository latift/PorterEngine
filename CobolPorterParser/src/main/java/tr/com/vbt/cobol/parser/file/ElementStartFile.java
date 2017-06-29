package tr.com.vbt.cobol.parser.file;

import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.AbstractMultipleLinesCommand;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.token.AbstractToken;

/**
 * START file-name KEY IS [=, >, <, NOT, <= or >=] rec-key
	   INVALID KEY DISPLAY 'Invalid Key'
	   NOT INVALID KEY DISPLAY 'File Pointer Updated'
	END-START.
 *
 */

public class ElementStartFile extends AbstractMultipleLinesCommand{
	
	private String fileName;
	
	private String keyIs;
	
	private String recKeyName;
	
	
	public ElementStartFile(AbstractToken baseToken,
			List<AbstractToken> tokenListesi, AbstractCommand parent) {
		super("ElementStartFile","PROCEDURE_DIVISION.*.START_FILE");
	}
	
	public ElementStartFile(String elementName,String detailedCobolName) {
		super(elementName, detailedCobolName);
	}

	@Override
	public String exportContents() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedCobolKeywords.START_FILE +"=\""+ this.fileName);
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
		sb.append(" "+ReservedCobolKeywords.START_FILE +"=\""+ this.fileName);
		sb.append("\"\n");
		return sb.toString();
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getKeyIs() {
		return keyIs;
	}

	public void setKeyIs(String keyIs) {
		this.keyIs = keyIs;
	}

	public String getRecKeyName() {
		return recKeyName;
	}

	public void setRecKeyName(String recKeyName) {
		this.recKeyName = recKeyName;
	}


	
	


	
}
