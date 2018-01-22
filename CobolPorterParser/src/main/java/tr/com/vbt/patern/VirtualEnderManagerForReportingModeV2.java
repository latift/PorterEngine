package tr.com.vbt.patern;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;


import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.AbstractEndingCommand;
import tr.com.vbt.cobol.parser.AbstractMultipleLinesCommand;
import tr.com.vbt.cobol.parser.Levelable;
import tr.com.vbt.java.util.MultipleLinesCommandsUtility;
import tr.com.vbt.lexer.ConversionLogModel;
import tr.com.vbt.lexer.NaturalMode;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.natural.parser.conditions.enders.ElementEndIf;
import tr.com.vbt.natural.parser.general.ElementDo;
import tr.com.vbt.natural.parser.general.ElementDoEnd;
import tr.com.vbt.natural.parser.general.ElementEndError;
import tr.com.vbt.natural.parser.loops.ElementLoop;

public class VirtualEnderManagerForReportingModeV2 {

	final static Logger logger = Logger.getLogger(VirtualEnderManagerForReportingModeV2.class);

	protected List<AbstractCommand> commandList;

	public VirtualEnderManagerForReportingModeV2(List<AbstractCommand> commandList) {
		this.commandList = commandList;
	}

	//1) IF, IF_NO_RECORDS, ELSE, ELSE_IF den sonra DO yoksa DO ekle ve kapanısa DOEND ve IF Ekler
	
	//Main den İtibaren tokenListesini Sonuna Kadar Gez.
	// if gördü isen
	public void addVirtualEndersForReportMode() {

		if (ConversionLogModel.getInstance().getMode().equals(NaturalMode.STRUCTRURED)) {
			return;
		}
		
		calculateCommandLevels();
		
		for (int index = 0; index < commandList.size() - 1; index++) {
			
		}

	
	}

	private void calculateCommandLevels() {
		Levelable curCommand, nextCommand;

		int curIndex = 0;
		
		for (int index = 0; index < commandList.size() - 1; index++) {

			curCommand = commandList.get(index);
			
			if(curCommand.equals(ReservedNaturalKeywords.MAIN_START)){
				curCommand.setLevelNumber(0); //Main Leveli 0 olacak.
				curIndex=index;
				break;
			}
		
		}
		
		for ( int index=curIndex; index < commandList.size() - 1; index++) {

			curCommand = commandList.get(index);
			
			nextCommand=commandList.get(index+1);
			
			nextCommand.calculateLevel(curCommand);
		
		}
		
	}


}
