package tr.com.vbt.natural.parser.datalayout.program.redefiners;

import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.AbstractMultipleLinesCommand;
import tr.com.vbt.cobol.parser.DataTypeMapConverter;
import tr.com.vbt.cobol.parser.Levelable;
import tr.com.vbt.java.general.JavaConstants;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.token.AbstractToken;

/* 	 1 REDEFINE #TFK-SEFNO    Local Normal Redefine Grup Değ. 
 * 
**/
public class ElementProgramRedefineGrupNatural extends AbstractMultipleLinesCommand implements Levelable, DataTypeMapConverter {

	protected long levelNumber; // mandatory
	
	protected String redefineName; // mandatory
	
	private AbstractCommand redefinedCommand;
	

	@Override
	public String toString() {
		return this.redefineName;
	}

		
	public ElementProgramRedefineGrupNatural(AbstractToken baseToken,
			List<AbstractToken> tokenListesi, AbstractCommand parent) {
		super(ReservedNaturalKeywords.PROGRAM_REDEFINE_GROUP, "GENERAL.PROGRAM_REDEFINE_GROUP");
	}

	public ElementProgramRedefineGrupNatural(String elementName, String detailedCobolName) {
		super(elementName, detailedCobolName);
	}

	@Override
	public String exportContents() {
		StringBuilder sb = new StringBuilder();
		if(this.redefinedCommand!=null){
			sb.append(" " + ReservedNaturalKeywords.PROGRAM_REDEFINE_GROUP + "=\""
				+ this.redefineName
				+"RedefinedCommand:"+this.redefinedCommand.toString());
		}else{
			sb.append(" " + ReservedNaturalKeywords.PROGRAM_REDEFINE_GROUP + "=\""
					+ this.redefineName);
		}
		sb.append("Ender:"+this.endingCommand);
		sb.append(JavaConstants.NEW_LINE);
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
		if(this.redefinedCommand!=null){
			sb.append(" " + ReservedNaturalKeywords.PROGRAM_REDEFINE_GROUP + "=\""
				+ this.redefineName
				+"RedefinedCommand:"+this.redefinedCommand.toString());
		}else{
			sb.append(" " + ReservedNaturalKeywords.PROGRAM_REDEFINE_GROUP + "=\""
					+ this.redefineName);
		}
		sb.append("Ender:"+this.endingCommand);
		sb.append(JavaConstants.NEW_LINE);
		return sb.toString();
	}


	public long getLevelNumber() {
		return levelNumber;
	}


	public void setLevelNumber(long levelNumber) {
		this.levelNumber = levelNumber;
	}


	public String getRedefineName() {
		return redefineName;
	}


	public void setRedefineName(String redefineName) {
		this.redefineName = redefineName;
	}


	@Override
	public void generateDataTypeConversionString(
			List<AbstractCommand> commandList, int commandIndex) {
		// TODO Auto-generated method stub
		
	}


	public AbstractCommand getRedefinedCommand() {
		return redefinedCommand;
	}


	public void setRedefinedCommand(AbstractCommand redefinedCommand) {
		this.redefinedCommand = redefinedCommand;
	}


	@Override
	public String getDataName() {
		return redefineName;
	}


	@Override
	public void setDataName(String dataName) {
		this.redefineName=dataName;
		
	}

	
	
	
	
}
