package tr.com.vbt.patern;

import tr.com.vbt.natural.parser.basicverbs.patern.PaternCommentEntry;
import tr.com.vbt.natural.parser.basicverbs.patern.PaternSlashStarComment;
import tr.com.vbt.natural.parser.datalayout.db.patern.PaternDBDataTypeNatural;
import tr.com.vbt.natural.parser.datalayout.db.patern.PaternDBMultipleUnitDataTypeNatural;
import tr.com.vbt.natural.parser.datalayout.db.patern.PaternDBRedefineDataGroupNatural;
import tr.com.vbt.natural.parser.datalayout.db.patern.PaternDBViewGroupNatural;
import tr.com.vbt.natural.parser.datalayout.db.patern.PaternDBViewOfGroupNatural;
import tr.com.vbt.natural.parser.datalayout.db.patern.PaternLocal;
import tr.com.vbt.natural.parser.datalayout.db.patern.PaternParameter;
import tr.com.vbt.natural.parser.datalayout.program.patern.PaternProgramDataTypeNatural;
import tr.com.vbt.natural.parser.datalayout.program.patern.PaternProgramDataTypeNaturalWithInit;
import tr.com.vbt.natural.parser.datalayout.program.patern.PaternProgramDataTypeNaturalWithInitKelime;
import tr.com.vbt.natural.parser.datalayout.program.patern.PaternProgramGrupArrayNatural;
import tr.com.vbt.natural.parser.datalayout.program.patern.PaternProgramGrupArrayNatural2;
import tr.com.vbt.natural.parser.datalayout.program.patern.PaternProgramGrupNatural;
import tr.com.vbt.natural.parser.datalayout.program.patern.PaternProgramOneDimensionArrayNatural;
import tr.com.vbt.natural.parser.datalayout.program.patern.PaternProgramOneDimensionArrayNatural2;
import tr.com.vbt.natural.parser.datalayout.program.patern.PaternProgramOneDimensionArrayNatural2WithInit;
import tr.com.vbt.natural.parser.datalayout.program.patern.PaternProgramOneDimensionArrayNaturalWithInit;
import tr.com.vbt.natural.parser.datalayout.program.patern.PaternProgramOneDimensionFloatArrayNatural;
import tr.com.vbt.natural.parser.datalayout.program.patern.PaternProgramOneDimensionFloatArrayNatural2;
import tr.com.vbt.natural.parser.datalayout.program.patern.PaternProgramOneDimensionFloatArrayNatural2WithInit;
import tr.com.vbt.natural.parser.datalayout.program.patern.PaternProgramOneDimensionFloatArrayNaturalWithInit;
import tr.com.vbt.natural.parser.datalayout.program.patern.PaternProgramRedefineGroupNatural;
import tr.com.vbt.natural.parser.datalayout.program.patern.PaternProgramTwoDimensionArrayNatural;
import tr.com.vbt.natural.parser.datalayout.program.patern.PaternProgramTwoDimensionArrayNatural2;
import tr.com.vbt.natural.parser.datalayout.program.patern.PaternProgramTwoDimensionFloatArrayNatural;
import tr.com.vbt.natural.parser.datalayout.program.patern.PaternProgramTwoDimensionFloatArrayNatural2;
import tr.com.vbt.natural.parser.enders.patern.PaternEndDefineData;
import tr.com.vbt.natural.parser.general.patern.PaternDefineData;
import tr.com.vbt.natural.parser.general.patern.PaternGlobalUsing;
import tr.com.vbt.natural.parser.general.patern.PaternLocalUsing;

public class PaternManagerDataTypesNaturalImpl  extends AbstractPaternManagerNatural{

	public PaternManagerDataTypesNaturalImpl() {
		//super(); Super in cons u çalışmamalı.
		
		commmandPatternList.add(new PaternDBViewOfGroupNatural());
		commmandPatternList.add(new PaternDBViewGroupNatural());
		
		commmandPatternList.add(new PaternCommentEntry());
		
		
		//General
		commmandPatternList.add(new PaternSlashStarComment());
		
		
		commmandPatternList.add(new PaternGlobalUsing());
		commmandPatternList.add(new PaternLocalUsing());
		
		//BasicVerbs
		
		
		//DataType
		commmandPatternList.add(new PaternProgramRedefineGroupNatural());
		
		commmandPatternList.add(new PaternProgramDataTypeNatural());
		commmandPatternList.add(new PaternProgramDataTypeNaturalWithInit());
		commmandPatternList.add(new PaternProgramDataTypeNaturalWithInitKelime());
		
		commmandPatternList.add(new PaternProgramGrupNatural());
		commmandPatternList.add(new PaternProgramGrupArrayNatural());
		commmandPatternList.add(new PaternProgramGrupArrayNatural2());
		
		commmandPatternList.add(new PaternProgramOneDimensionArrayNatural());
		commmandPatternList.add(new PaternProgramOneDimensionArrayNaturalWithInit());
		commmandPatternList.add(new PaternProgramOneDimensionArrayNatural2());
		commmandPatternList.add(new PaternProgramOneDimensionArrayNatural2WithInit());
		commmandPatternList.add(new PaternProgramOneDimensionFloatArrayNatural());
		commmandPatternList.add(new PaternProgramOneDimensionFloatArrayNaturalWithInit());
		commmandPatternList.add(new PaternProgramOneDimensionFloatArrayNatural2());
		commmandPatternList.add(new PaternProgramOneDimensionFloatArrayNatural2WithInit());
		
		commmandPatternList.add(new PaternProgramTwoDimensionArrayNatural());
		commmandPatternList.add(new PaternProgramTwoDimensionArrayNatural2());
		commmandPatternList.add(new PaternProgramTwoDimensionFloatArrayNatural());
		commmandPatternList.add(new PaternProgramTwoDimensionFloatArrayNatural2());
		
		commmandPatternList.add(new PaternDefineData());
		commmandPatternList.add(new PaternEndDefineData());//End Define da bu manager ile bulunur. Sonrası Diğer manager ı kayar.
		commmandPatternList.add(new PaternLocal());
		commmandPatternList.add(new PaternParameter());
		
		//DB DAtaype
		commmandPatternList.add(new PaternDBViewOfGroupNatural());
		commmandPatternList.add(new PaternDBViewGroupNatural());
		commmandPatternList.add(new PaternDBDataTypeNatural());
		commmandPatternList.add(new PaternDBRedefineDataGroupNatural());
		commmandPatternList.add(new PaternDBMultipleUnitDataTypeNatural());
		
	}
	

}

