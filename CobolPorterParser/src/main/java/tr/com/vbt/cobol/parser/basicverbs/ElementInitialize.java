package tr.com.vbt.cobol.parser.basicverbs;

import java.util.ArrayList;
import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedCobolKeywords;

//INITIALIZE T-PGNTOP  T-IOCTOP  T-CPUTOP 
/*
 * 
 * DATA DIVISION.
   WORKING-STORAGE SECTION.
   01 WS-NAME PIC A(30) VALUE 'ABCDEF'.
   01 WS-ID PIC 9(5).
   01 WS-ADDRESS. 
   05 WS-HOUSE-NUMBER PIC 9(3).
   05 WS-COUNTRY PIC X(15).
   05 WS-PINCODE PIC 9(6) VALUE 123456.

PROCEDURE DIVISION.
   A000-FIRST-PARA.
   INITIALIZE WS-NAME, WS-ADDRESS.
   INITIALIZE WS-ID REPLACING NUMERIC DATA BY 12345.
   DISPLAY "My name is   : "WS-NAME.
   DISPLAY "My ID is     : "WS-ID.
   DISPLAY "Address      : "WS-ADDRESS.
   DISPLAY "House Number : "WS-HOUSE-NUMBER.
   DISPLAY "Country      : "WS-COUNTRY.
   DISPLAY "Pincode      : "WS-PINCODE.
   
   My name is   :                               
	My ID is     : 12345
	Address      : 000               000000
	House Number : 000
	Country      :                
	Pincode      : 000000
 */
public class ElementInitialize extends AbstractCommand{
	
	private List<String> params=new ArrayList<String>();
	
	public ElementInitialize(String elementName,String detailedCobolName) {
		super(elementName, detailedCobolName);
	}
	
	public ElementInitialize(AbstractCommand parent, String elementName,
			String detailedCobolName, List<AbstractCommand> commandListesi) {
		super(parent, elementName, detailedCobolName, commandListesi);
	}
	
	public ElementInitialize(String elementName,String detailedCobolName, List<AbstractCommand> commandListesi) {
		super(elementName, detailedCobolName, commandListesi);
	}



	@Override
	public String exportCommands() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedCobolKeywords.INITIALIZE +" ");
		sb.append("PARAMS: ");
		for (String src : params) {
			sb.append(src+" ");
		}
		
		sb.append("\n");
		return sb.toString();
	}
	

	@Override
	public String exportContents() {
		StringBuilder sb=new StringBuilder();
		sb.append(" "+ReservedCobolKeywords.INITIALIZE +" ");
		sb.append("PARAMS: ");
		for (String src : params) {
			sb.append(src+" ");
		}
		
		sb.append("\n");
		return sb.toString();
	}

	@Override
	public boolean generateDetailedCobolName() {
		// TODO Auto-generated method stub
				return false;
	}

	
	public List<String> getParams() {
		return params;
	}

	public void setParams(List<String> params) {
		this.params = params;
	}

}
