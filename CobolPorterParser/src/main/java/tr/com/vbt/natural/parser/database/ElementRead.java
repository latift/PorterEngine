package tr.com.vbt.natural.parser.database;

import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.AbstractMultipleLinesCommand;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.token.AbstractToken;

/**RD1. READ EMPLOYEES-VIEW BY NAME
 * 
 * 
 * MOVE  Uzunluk:0 Satir No:11 Tipi:OzelKelime
	#  Uzunluk:1 Satir No:11 Tipi:Karakter
	NAME-START  Uzunluk:0 Satir No:11 Tipi:Kelime
	TO  Uzunluk:0 Satir No:11 Tipi:OzelKelime
	#  Uzunluk:1 Satir No:11 Tipi:Karakter
	NAME-END  Uzunluk:0 Satir No:11 Tipi:Kelime
	
	
 * @author 47159500
 *
 */
public class ElementRead extends AbstractMultipleLinesCommand{
	
	private String readName;
	
	private String viewName;
	
	private String columnName;
	
	private String startName;
	
	private String endName;
	
	public ElementRead(AbstractToken baseToken,
			List<AbstractToken> tokenListesi, AbstractCommand parent) {
		super("Read","DATABASE.*.READ");
	}

	public ElementRead(String elementName,String detailedCobolName) {
		super(elementName, detailedCobolName);
	}
	
	@Override
	public String exportContents() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedNaturalKeywords.READ +"=\""+ this.viewName+" "+ this.columnName+" "+this.startName+" "+this.endName+" ");
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
		sb.append(" "+ReservedNaturalKeywords.READ +"=\""+ this.viewName+" "+ this.columnName+" "+this.startName+" "+this.endName+" ");
		sb.append(" 	Ender:"+ this.endingCommand);
		sb.append("\"\n");
		return sb.toString();
	}

	public String getViewName() {
		return viewName;
	}

	public void setViewName(String viewName) {
		this.viewName = viewName;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getStartName() {
		return startName;
	}

	public void setStartName(String startName) {
		this.startName = startName;
	}

	public String getEndName() {
		return endName;
	}

	public void setEndName(String endName) {
		this.endName = endName;
	}

	public String getReadName() {
		return readName;
	}

	public void setReadName(String readName) {
		this.readName = readName;
	}

	

	
	
	

	

	
}
