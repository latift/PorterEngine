package tr.com.vbt.cobol.parser.file;

import java.util.ArrayList;
import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.token.AbstractToken;

//OPEN INPUT   RMFFILE -->  FileReader fileReader = new FileReader(fileName);  BufferedReader bufferedReader =new BufferedReader(fileReader);
/**
 * OPEN "mode" file-name.
 * 
 *
 */
public class ElementOpenFile extends AbstractCommand{
	
	private List<String> fileNameList=new ArrayList<String>();
	
	private String mode;
	
	public ElementOpenFile(AbstractToken baseToken,
			List<AbstractToken> tokenListesi, AbstractCommand parent) {
		super("Open","PROCEDURE_DIVISION.*.OPEN_FILE");
	}
	
	public ElementOpenFile(String elementName,String detailedCobolName) {
		super(elementName, detailedCobolName);
	}



	@Override
	public String exportContents() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedCobolKeywords.OPEN +"=\"");
		for (String data : fileNameList) {
			sb.append(" "+ data);	
		}
		sb.append("Mode:"+mode);
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
		sb.append(" "+ReservedCobolKeywords.OPEN +"=\"");
		for (String data : fileNameList) {
			sb.append(" "+ data);	
		}
		sb.append("Mode:"+mode);
		sb.append("\"\n");
		return sb.toString();
	}

	
	public List<String> getFileNameList() {
		return fileNameList;
	}

	public void setFileNameList(List<String> fileNameList) {
		this.fileNameList = fileNameList;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	

	

	
}
