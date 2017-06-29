package tr.com.vbt.cobol.parser.file;

import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.token.AbstractToken;

public class ElementSelectFile extends AbstractCommand{
	
	private String fileName;
	private String assignedTo;
	private String organizationType;
	private String accessMode;
	private String fileStatus; 
	private String recordKey;
	
	public ElementSelectFile(AbstractToken baseToken,
			List<AbstractToken> tokenListesi, AbstractCommand parent) {
		super("SelectFile","FILE_CONTROL.*.SELECT_FILE");
	}

	public ElementSelectFile(String elementName,String detailedCobolName) {
		super(elementName, detailedCobolName);
	}
	

	@Override
	public String exportContents() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedCobolKeywords.SELECT_FILE+"=\""+ this.fileName+"\"\n");
		if(this.assignedTo!=null)
			sb.append(" "+ReservedCobolKeywords.ASSIGNED_TO +"=\""+ this.assignedTo+"\"\n");
		if(this.organizationType!=null)
			sb.append(" "+ReservedCobolKeywords.ORGANIZATION_TYPE +"=\""+ this.organizationType+"\"\n");
		if(this.accessMode!=null)
			sb.append(" "+ReservedCobolKeywords.ACCESS_MODE +"=\""+ this.accessMode+"\"\n");
		if(this.fileStatus!=null)
			sb.append(" "+ReservedCobolKeywords.FILE_STATUS +"=\""+ this.fileStatus+"\"\n");
		if(this.recordKey!=null)
			sb.append(" "+ReservedCobolKeywords.RECORD_KEY +"=\""+ this.recordKey+"\"\n");
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
		sb.append(" "+ReservedCobolKeywords.SELECT_FILE+"=\""+ this.fileName+"\"\n");
		if(this.assignedTo!=null)
			sb.append(" "+ReservedCobolKeywords.ASSIGNED_TO +"=\""+ this.assignedTo+"\"\n");
		if(this.organizationType!=null)
			sb.append(" "+ReservedCobolKeywords.ORGANIZATION_TYPE +"=\""+ this.organizationType+"\"\n");
		if(this.accessMode!=null)
			sb.append(" "+ReservedCobolKeywords.ACCESS_MODE +"=\""+ this.accessMode+"\"\n");
		if(this.fileStatus!=null)
			sb.append(" "+ReservedCobolKeywords.FILE_STATUS +"=\""+ this.fileStatus+"\"\n");
		if(this.recordKey!=null)
			sb.append(" "+ReservedCobolKeywords.RECORD_KEY +"=\""+ this.recordKey+"\"\n");
		return sb.toString();
	}

	
	public String getAssignedTo() {
		return assignedTo;
	}

	public void setAssignedTo(String assignedTo) {
		this.assignedTo = assignedTo;
	}

	public String getOrganizationType() {
		return organizationType;
	}

	public void setOrganizationType(String organizationType) {
		this.organizationType = organizationType;
	}

	public String getAccessMode() {
		return accessMode;
	}

	public void setAccessMode(String accessMode) {
		this.accessMode = accessMode;
	}

	public String getFileStatus() {
		return fileStatus;
	}

	public void setFileStatus(String fileStatus) {
		this.fileStatus = fileStatus;
	}

	public String getRecordKey() {
		return recordKey;
	}

	public void setRecordKey(String recordKey) {
		this.recordKey = recordKey;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	
	

	
}
