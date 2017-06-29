package tr.com.vbt.cobol.parser.file;

import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.AbstractMultipleLinesCommand;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.token.AbstractToken;

/**
 * 
 * 
Write:
WRITE record-buffer [FROM ws-file-structure]
   INVALID KEY DISPLAY 'Invalid Key'
   NOT INVALID KEY DISPLAY 'Record Inserted'
END-WRITE.
 *
 */

public class ElementReWriteFile extends AbstractMultipleLinesCommand{
	
	private String recordBuffer;
	
	private String fileStructure; //optional
	
	
	public ElementReWriteFile(AbstractToken baseToken,
			List<AbstractToken> tokenListesi, AbstractCommand parent) {
		super("ElementReWriteFile","PROCEDURE_DIVISION.*.REWRITE_FILE");
	}
	
	public ElementReWriteFile(String elementName,String detailedCobolName) {
		super(elementName, detailedCobolName);
	}

	@Override
	public String exportContents() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedCobolKeywords.REWRITE_FILE +"=\""+ this.recordBuffer+"\n");
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
		sb.append(" "+ReservedCobolKeywords.REWRITE_FILE +"=\""+ this.recordBuffer+"\n");
		return sb.toString();
	}



	public String getFileStructure() {
		return fileStructure;
	}

	public void setFileStructure(String fileStructure) {
		this.fileStructure = fileStructure;
	}

	public String getRecordBuffer() {
		return recordBuffer;
	}

	public void setRecordBuffer(String recordBuffer) {
		this.recordBuffer = recordBuffer;
	}


	
	


	
}
