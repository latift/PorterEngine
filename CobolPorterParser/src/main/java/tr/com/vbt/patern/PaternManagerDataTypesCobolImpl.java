package tr.com.vbt.patern;

import tr.com.vbt.cobol.parser.division.patern.PaternIdentificationDivision;
import tr.com.vbt.cobol.parser.general.patern.PaternProgramId;
import tr.com.vbt.natural.parser.basicverbs.patern.PaternCommentEntry;
import tr.com.vbt.natural.parser.basicverbs.patern.PaternSlashStarComment;

public class PaternManagerDataTypesCobolImpl  extends AbstractPaternManagerNatural{

	public PaternManagerDataTypesCobolImpl() {
		//super(); Super in cons u çalışmamalı.
		
		commmandPatternList.add(new PaternCommentEntry());
		
		//General
		commmandPatternList.add(new PaternIdentificationDivision());
		commmandPatternList.add(new PaternProgramId());
		commmandPatternList.add(new PaternSlashStarComment());
		
		
		//BasicVerbs
		
		
		//DataType
				
		//DB DAtaype
		
	}
	

}

