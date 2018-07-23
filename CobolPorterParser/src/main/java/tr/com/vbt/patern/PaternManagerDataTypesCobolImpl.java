package tr.com.vbt.patern;

import tr.com.vbt.cobol.parser.basicverbs.patern.PaternDisplay;
import tr.com.vbt.cobol.parser.basicverbs.patern.PaternEnd;
import tr.com.vbt.cobol.parser.basicverbs.patern.PaternStopRun;
import tr.com.vbt.cobol.parser.division.patern.PaternDataDivision;
import tr.com.vbt.cobol.parser.division.patern.PaternIdentificationDivision;
import tr.com.vbt.cobol.parser.division.patern.PaternProcedureDivision;
import tr.com.vbt.cobol.parser.general.patern.PaternAuthor;
import tr.com.vbt.cobol.parser.general.patern.PaternMain;
import tr.com.vbt.cobol.parser.general.patern.PaternProgramId;
import tr.com.vbt.cobol.parser.general.patern.PaternWorkingStorageSection;
import tr.com.vbt.natural.parser.basicverbs.patern.PaternCommentEntry;
import tr.com.vbt.natural.parser.basicverbs.patern.PaternSlashStarComment;

public class PaternManagerDataTypesCobolImpl  extends AbstractPaternManagerNatural{

	public PaternManagerDataTypesCobolImpl() {
		//super(); Super in cons u çalışmamalı.
		
		commmandPatternList.add(new PaternCommentEntry());
		
		//General
		commmandPatternList.add(new PaternIdentificationDivision());
		commmandPatternList.add(new PaternProgramId());
		commmandPatternList.add(new PaternAuthor());
		
		commmandPatternList.add(new PaternDataDivision());
		//commmandPatternList.add(new PaternWorkingStorageSection());
		
		commmandPatternList.add(new PaternProcedureDivision());
		commmandPatternList.add(new PaternWorkingStorageSection());
		commmandPatternList.add(new PaternSlashStarComment());
		commmandPatternList.add(new PaternMain());
		
		
		
		//BasicVerbs
		commmandPatternList.add(new PaternDisplay());
		commmandPatternList.add(new PaternStopRun());
		commmandPatternList.add(new PaternEnd());
		//DataType
				
		//DB DAtaype
		
	}
	

}

