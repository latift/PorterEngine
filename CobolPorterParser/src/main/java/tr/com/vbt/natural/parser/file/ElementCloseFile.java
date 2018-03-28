package tr.com.vbt.natural.parser.file;

import java.util.ArrayList;
import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.token.AbstractToken;

//CLOSE RMFFILE
public class ElementCloseFile extends AbstractCommand{
	
	private List<String> fileNameList=new ArrayList<String>();
	

	
	public ElementCloseFile(String elementName,String detailedCobolName) {
		super(elementName, detailedCobolName);
	}
	
	public ElementCloseFile(AbstractCommand parent, String elementName,
			String detailedCobolName, List<AbstractCommand> commandListesi) {
		super(parent, elementName, detailedCobolName, commandListesi);
	}
	
	public ElementCloseFile(String elementName,String detailedCobolName, List<AbstractCommand> commandListesi) {
		super(elementName, detailedCobolName, commandListesi);
	}
	
	public ElementCloseFile(AbstractToken baseToken,
			List<AbstractToken> tokenListesi, AbstractCommand parent) {
		super("ElementCloseFile","PROCEDURE_DIVISION.*.CLOSE_FILE");
	}





	@Override
	public String exportContents() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedCobolKeywords.CLOSE +"=\"");
		for (String data : fileNameList) {
			sb.append(" "+ data);	
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
		sb.append(" "+ReservedCobolKeywords.CLOSE +"=\"");
		for (String data : fileNameList) {
			sb.append(" "+ data);	
		}
		sb.append("\"\n");
		return sb.toString();
	}

	public List<String> getFileNameList() {
		return fileNameList;
	}

	public void setFileNameList(List<String> fileNameList) {
		this.fileNameList = fileNameList;
	}



	
}
