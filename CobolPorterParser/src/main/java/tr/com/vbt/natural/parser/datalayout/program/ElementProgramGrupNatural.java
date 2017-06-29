package tr.com.vbt.natural.parser.datalayout.program;

import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.AbstractMultipleLinesCommand;
import tr.com.vbt.cobol.parser.DataTypeMapConverter;
import tr.com.vbt.cobol.parser.Levelable;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.token.AbstractToken;

/* 	 
 * 0412 1 MAP_DIZISI     --> ElementProgramGrupNatural                                                                                                               
   0414   2 D_SECIM    (A1/1:100)                                                                                                      
   0416   2 D_HESCINSI (A1/1:100) 
   
   Yada
   
   0216 1 MAP2           --> ElementProgramGrupNatural                                                                                                               
   0218   2 MUSNO1               (N8) 
   0220   2 ADSOY1               (A41)
 * 
**/
public class ElementProgramGrupNatural extends AbstractMultipleLinesCommand implements Levelable, DataTypeMapConverter {

	protected int levelNumber; // mandatory
	
	protected String grupName; // mandatory
	
	private int arrayLength; // 500 Parantez içindeki ifade
	
	
	@Override
	public String toString() {
		return this.grupName;
	}

		
	public ElementProgramGrupNatural(AbstractToken baseToken,
			List<AbstractToken> tokenListesi, AbstractCommand parent) {
		super(ReservedNaturalKeywords.PROGRAM_GROUP, "GENERAL.PROGRAM_GROUP");
	}

	public ElementProgramGrupNatural(String elementName, String detailedCobolName) {
		super(elementName, detailedCobolName);
	}

	@Override
	public String exportContents() {
		StringBuilder sb = new StringBuilder();
		sb.append(" " + ReservedNaturalKeywords.PROGRAM_GROUP + "=\""
				+ this.grupName);
		sb.append("Ender:"+this.endingCommand);
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
		StringBuilder sb = new StringBuilder();
		sb.append(" " + ReservedNaturalKeywords.PROGRAM_GROUP + "=\""
				+ this.grupName);
		sb.append("Ender:"+this.endingCommand);
		sb.append("\"\n");
		return sb.toString();
	}


	public int getLevelNumber() {
		return levelNumber;
	}


	public void setLevelNumber(int levelNumber) {
		this.levelNumber = levelNumber;
	}





	@Override
	public void generateDataTypeConversionString(
			List<AbstractCommand> commandList, int commandIndex) {
		// TODO Auto-generated method stub
		
	}


	public String getGrupName() {
		return grupName;
	}


	public void setGrupName(String grupName) {
		this.grupName = grupName;
	}


	public int getArrayLength() {
		return arrayLength;
	}


	public void setArrayLength(int arrayLength) {
		this.arrayLength = arrayLength;
	}


	@Override
	public String getDataName() {
		return grupName;
	}


	@Override
	public void setDataName(String dataName) {
		this.grupName=dataName;
		
	}
	
	
}
