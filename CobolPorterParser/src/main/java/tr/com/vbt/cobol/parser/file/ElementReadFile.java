package tr.com.vbt.cobol.parser.file;

import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.AbstractMultipleLinesCommand;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.token.AbstractToken;

// READ RMFFILE  END-READ
// READ RMFFILE  AT END GO TO SON
// READ RMFFILE  
//		AT END 
//			SET S-BITTI TO TRUE  
// END-READ
/**
 * READ MCPSFILE NEXT RECORD
 */
/**
 * SYNTAX:
 * READ file-name NEXT RECORD INTO ws-file-structure
   		AT END DISPLAY 'End of File'
   		END_AT_END_DISPLAY
   		NOT AT END DISPLAY 'Record Details:' ws-file-structure
   		END_NOT_AT_END_DISPLAY
	END-READ.
	
	NEXT RECORD is optional
	INTO clause is optional
	
 */
/**
 * READ CATFILE2 NEXT 
 * 		AT END 
 * 			MOVE 1 TO FILE-SONU 
 * END-READ
 * 
 *
 */
/** (END-READ yerine .)
 * READ INPFILE NEXT 
 * 		AT END 
 * 			MOVE 1 TO FILE-SONU. 
 * 
 */
/*
024500     READ VCCFILE RECORD KEY IS VCC-KEY                           02450000
024600          INVALID KEY MOVE 1 TO VCC-OKEY1.                        02460000
*/

public class ElementReadFile extends AbstractMultipleLinesCommand{
	
	private String fileName;
	
	private String fileStructure; //optional
	
	private String recordKeyIs;
	
	
	public ElementReadFile(AbstractToken baseToken,
			List<AbstractToken> tokenListesi, AbstractCommand parent) {
		super("ElementReadFile","PROCEDURE_DIVISION.*.READ_FILE");
	}
	
	public ElementReadFile(String elementName,String detailedCobolName) {
		super(elementName, detailedCobolName);
	}

	@Override
	public String exportContents() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedCobolKeywords.READ_FILE +"=\""+ this.fileName);
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
		sb.append(" "+ReservedCobolKeywords.READ_FILE +"=\""+ this.fileName);
		sb.append("\"\n");
		return sb.toString();
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileStructure() {
		return fileStructure;
	}

	public void setFileStructure(String fileStructure) {
		this.fileStructure = fileStructure;
	}

	public String getRecordKeyIs() {
		return recordKeyIs;
	}

	public void setRecordKeyIs(String recordKeyIs) {
		this.recordKeyIs = recordKeyIs;
	}

	
	


	
}
