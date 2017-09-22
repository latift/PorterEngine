package tr.com.vbt.cobol.parser.file;

import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.AbstractMultipleLinesCommand;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.token.AbstractToken;

public class ElementFileDescription extends AbstractMultipleLinesCommand{
	
	private String fileName;
	
	private String recordingMode;
	
	private String labelRecordsType;
	
	private long blockCount;
	
	public ElementFileDescription(AbstractToken baseToken,
			List<AbstractToken> tokenListesi, AbstractCommand parent) {
		super("FileDescription","FILE_SECTION.*.FD");
	}

	public ElementFileDescription(String elementName,String detailedCobolName) {
		super(elementName, detailedCobolName);
	}
	
	@Override
	public String exportContents() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedCobolKeywords.FILE_DESCRIPTION +"=\""+ this.fileName+"\"");
		if(this.endingCommand!=null){
			sb.append(" 	Ender:"+ this.endingCommand.toString());
		}
		sb.append("\n");
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
		sb.append(" "+ReservedCobolKeywords.FILE_DESCRIPTION +"=\""+ this.fileName+"\"");
		if(this.endingCommand!=null){
			sb.append(" 	Ender:"+ this.endingCommand.toString());
		}
		sb.append("\n");
		return sb.toString();
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public long getBlockCount() {
		return blockCount;
	}

	public void setBlockCount(long blockCount) {
		this.blockCount = blockCount;
	}

	public String getRecordingMode() {
		return recordingMode;
	}

	public void setRecordingMode(String recordingMode) {
		this.recordingMode = recordingMode;
	}

	public String getLabelRecordsType() {
		return labelRecordsType;
	}

	public void setLabelRecordsType(String labelRecordsType) {
		this.labelRecordsType = labelRecordsType;
	}

	

	

	
}
