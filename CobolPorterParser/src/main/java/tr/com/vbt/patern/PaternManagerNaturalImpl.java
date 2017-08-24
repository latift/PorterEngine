package tr.com.vbt.patern;

import tr.com.vbt.cobol.parser.file.patern.PaternCloseFile;
import tr.com.vbt.natural.parser.basicverbs.patern.PaternAdd;
import tr.com.vbt.natural.parser.basicverbs.patern.PaternAssign;
import tr.com.vbt.natural.parser.basicverbs.patern.PaternAtEndOfPage;
import tr.com.vbt.natural.parser.basicverbs.patern.PaternAtTopOfPage;
import tr.com.vbt.natural.parser.basicverbs.patern.PaternBecomesEqualTo;
import tr.com.vbt.natural.parser.basicverbs.patern.PaternBecomesEqualTo2;
import tr.com.vbt.natural.parser.basicverbs.patern.PaternCallNat;
import tr.com.vbt.natural.parser.basicverbs.patern.PaternClosePrinter;
import tr.com.vbt.natural.parser.basicverbs.patern.PaternCommentEntry;
import tr.com.vbt.natural.parser.basicverbs.patern.PaternCompress;
import tr.com.vbt.natural.parser.basicverbs.patern.PaternCompute;
import tr.com.vbt.natural.parser.basicverbs.patern.PaternComputeRounded;
import tr.com.vbt.natural.parser.basicverbs.patern.PaternDelete;
import tr.com.vbt.natural.parser.basicverbs.patern.PaternDisplay;
import tr.com.vbt.natural.parser.basicverbs.patern.PaternDivide;
import tr.com.vbt.natural.parser.basicverbs.patern.PaternExamineForDelete;
import tr.com.vbt.natural.parser.basicverbs.patern.PaternExamineForGiving;
import tr.com.vbt.natural.parser.basicverbs.patern.PaternExamineForGivingIndex;
import tr.com.vbt.natural.parser.basicverbs.patern.PaternExamineForGivingLength;
import tr.com.vbt.natural.parser.basicverbs.patern.PaternExamineForGivingLengthIn;
import tr.com.vbt.natural.parser.basicverbs.patern.PaternExamineForGivingNumber;
import tr.com.vbt.natural.parser.basicverbs.patern.PaternExamineForGivingPosition;
import tr.com.vbt.natural.parser.basicverbs.patern.PaternExamineForGivingPositionIn;
import tr.com.vbt.natural.parser.basicverbs.patern.PaternExamineFullForGivingLengthIn;
import tr.com.vbt.natural.parser.basicverbs.patern.PaternExamineGivingPositionIn;
import tr.com.vbt.natural.parser.basicverbs.patern.PaternExamineReplaceWith;
import tr.com.vbt.natural.parser.basicverbs.patern.PaternFetch;
import tr.com.vbt.natural.parser.basicverbs.patern.PaternFetchReturn;
import tr.com.vbt.natural.parser.basicverbs.patern.PaternFormat;
import tr.com.vbt.natural.parser.basicverbs.patern.PaternFormatWithNumber;
import tr.com.vbt.natural.parser.basicverbs.patern.PaternInclude;
import tr.com.vbt.natural.parser.basicverbs.patern.PaternMove;
import tr.com.vbt.natural.parser.basicverbs.patern.PaternMoveAD;
import tr.com.vbt.natural.parser.basicverbs.patern.PaternMoveByName;
import tr.com.vbt.natural.parser.basicverbs.patern.PaternMoveLeftJustified;
import tr.com.vbt.natural.parser.basicverbs.patern.PaternMoveRightJustified;
import tr.com.vbt.natural.parser.basicverbs.patern.PaternPerform;
import tr.com.vbt.natural.parser.basicverbs.patern.PaternRedefine;
import tr.com.vbt.natural.parser.basicverbs.patern.PaternReset;
import tr.com.vbt.natural.parser.basicverbs.patern.PaternRun;
import tr.com.vbt.natural.parser.basicverbs.patern.PaternSetControl;
import tr.com.vbt.natural.parser.basicverbs.patern.PaternSetKey;
import tr.com.vbt.natural.parser.basicverbs.patern.PaternSetWindow;
import tr.com.vbt.natural.parser.basicverbs.patern.PaternSlashStarComment;
import tr.com.vbt.natural.parser.basicverbs.patern.PaternStop;
import tr.com.vbt.natural.parser.basicverbs.patern.PaternStore;
import tr.com.vbt.natural.parser.basicverbs.patern.PaternSubtract;
import tr.com.vbt.natural.parser.conditions.patern.PaternAccept;
import tr.com.vbt.natural.parser.conditions.patern.PaternAcceptIf;
import tr.com.vbt.natural.parser.conditions.patern.PaternDecideForFirstCondition;
import tr.com.vbt.natural.parser.conditions.patern.PaternDecideOn;
import tr.com.vbt.natural.parser.conditions.patern.PaternElse;
import tr.com.vbt.natural.parser.conditions.patern.PaternElseIf;
import tr.com.vbt.natural.parser.conditions.patern.PaternEndDecide;
import tr.com.vbt.natural.parser.conditions.patern.PaternEndEvaluate;
import tr.com.vbt.natural.parser.conditions.patern.PaternEndIf;
import tr.com.vbt.natural.parser.conditions.patern.PaternEndIfBeforeElse;
import tr.com.vbt.natural.parser.conditions.patern.PaternIf;
import tr.com.vbt.natural.parser.conditions.patern.PaternIgnore;
import tr.com.vbt.natural.parser.conditions.patern.PaternNone;
import tr.com.vbt.natural.parser.conditions.patern.PaternRejectIf;
import tr.com.vbt.natural.parser.conditions.patern.PaternValue;
import tr.com.vbt.natural.parser.conditions.patern.PaternWhen;
import tr.com.vbt.natural.parser.database.enders.PaternEndFind;
import tr.com.vbt.natural.parser.database.enders.PaternEndNoRec;
import tr.com.vbt.natural.parser.database.enders.PaternEndRead;
import tr.com.vbt.natural.parser.database.enders.PaternEndSelect;
import tr.com.vbt.natural.parser.database.patern.PaternFindNumberWith;
import tr.com.vbt.natural.parser.database.patern.PaternFindOneWith;
import tr.com.vbt.natural.parser.database.patern.PaternFindWith;
import tr.com.vbt.natural.parser.database.patern.PaternGet;
import tr.com.vbt.natural.parser.database.patern.PaternIfNo;
import tr.com.vbt.natural.parser.database.patern.PaternIfNoRecord;
import tr.com.vbt.natural.parser.database.patern.PaternIfNoRecordFound;
import tr.com.vbt.natural.parser.database.patern.PaternIfNoRecords;
import tr.com.vbt.natural.parser.database.patern.PaternIfNoRecordsFound;
import tr.com.vbt.natural.parser.database.patern.PaternRead;
import tr.com.vbt.natural.parser.database.patern.PaternReadBy;
import tr.com.vbt.natural.parser.database.patern.PaternReadByLogicalDescending;
import tr.com.vbt.natural.parser.database.patern.PaternReadByThru;
import tr.com.vbt.natural.parser.database.patern.PaternReadWith;
import tr.com.vbt.natural.parser.database.patern.PaternReadWithThru;
import tr.com.vbt.natural.parser.database.patern.PaternSelect;
import tr.com.vbt.natural.parser.enders.patern.PaternEndAtEndOfPage;
import tr.com.vbt.natural.parser.enders.patern.PaternEndDefineData;
import tr.com.vbt.natural.parser.enders.patern.PaternEndDisplay;
import tr.com.vbt.natural.parser.enders.patern.PaternEndMainStart;
import tr.com.vbt.natural.parser.enders.patern.PaternEndSubroutine;
import tr.com.vbt.natural.parser.enders.patern.PaternEndTopOfPage;
import tr.com.vbt.natural.parser.file.patern.PaternDownloadFile;
import tr.com.vbt.natural.parser.general.patern.PaternAmpersand;
import tr.com.vbt.natural.parser.general.patern.PaternBackoutTransaction;
import tr.com.vbt.natural.parser.general.patern.PaternDefineData;
import tr.com.vbt.natural.parser.general.patern.PaternDo;
import tr.com.vbt.natural.parser.general.patern.PaternEndOfTransaction;
import tr.com.vbt.natural.parser.general.patern.PaternEndTransaction;
import tr.com.vbt.natural.parser.general.patern.PaternGlobalUsing;
import tr.com.vbt.natural.parser.general.patern.PaternLocalUsing;
import tr.com.vbt.natural.parser.general.patern.PaternMainStart;
import tr.com.vbt.natural.parser.general.patern.PaternOnError;
import tr.com.vbt.natural.parser.general.patern.PaternSubroutine;
import tr.com.vbt.natural.parser.general.patern.PaternUpdate;
import tr.com.vbt.natural.parser.loops.patern.PaternDoEnd;
import tr.com.vbt.natural.parser.loops.patern.PaternEscape;
import tr.com.vbt.natural.parser.loops.patern.PaternEscapeBottom;
import tr.com.vbt.natural.parser.loops.patern.PaternEscapeBottom2;
import tr.com.vbt.natural.parser.loops.patern.PaternEscapeRoutine;
import tr.com.vbt.natural.parser.loops.patern.PaternEscapeTop;
import tr.com.vbt.natural.parser.loops.patern.PaternEscapeTop2;
import tr.com.vbt.natural.parser.loops.patern.PaternFor;
import tr.com.vbt.natural.parser.loops.patern.PaternFor2;
import tr.com.vbt.natural.parser.loops.patern.PaternFor3;
import tr.com.vbt.natural.parser.loops.patern.PaternLoop;
import tr.com.vbt.natural.parser.loops.patern.PaternLoop2;
import tr.com.vbt.natural.parser.loops.patern.PaternNewPage;
import tr.com.vbt.natural.parser.loops.patern.PaternNewPage2;
import tr.com.vbt.natural.parser.loops.patern.PaternRepeat;
import tr.com.vbt.natural.parser.loops.patern.PaternRepeatUntil;
import tr.com.vbt.natural.parser.loops.patern.PaternRepeatWhile;
import tr.com.vbt.natural.parser.loops.patern.PaternReturn;
import tr.com.vbt.natural.parser.loops.patern.PaternStackCommand;
import tr.com.vbt.natural.parser.loops.patern.PaternStackTop;
import tr.com.vbt.natural.parser.loops.patern.PaternStackTopCommand;
import tr.com.vbt.natural.parser.loops.patern.PaternTerminate;
import tr.com.vbt.natural.parser.loops.patern.PaternUntil;
import tr.com.vbt.natural.parser.loops.patern.enders.PaternEndFor;
import tr.com.vbt.natural.parser.loops.patern.enders.PaternEndRepeat;
import tr.com.vbt.natural.parser.screen.patern.PaternBase;
import tr.com.vbt.natural.parser.screen.patern.PaternBase2;
import tr.com.vbt.natural.parser.screen.patern.PaternControlScreen;
import tr.com.vbt.natural.parser.screen.patern.PaternControlWindow;
import tr.com.vbt.natural.parser.screen.patern.PaternDefineWindow;
import tr.com.vbt.natural.parser.screen.patern.PaternInput;
import tr.com.vbt.natural.parser.screen.patern.PaternInputMap;
import tr.com.vbt.natural.parser.screen.patern.PaternInputMarkUsingMap;
import tr.com.vbt.natural.parser.screen.patern.PaternInputNoErase;
import tr.com.vbt.natural.parser.screen.patern.PaternInputUsingMap;
import tr.com.vbt.natural.parser.screen.patern.PaternReInput;
import tr.com.vbt.natural.parser.screen.patern.PaternReInputWith;
import tr.com.vbt.natural.parser.screen.patern.PaternSize;
import tr.com.vbt.natural.parser.screen.patern.PaternTitle;
import tr.com.vbt.natural.parser.screen.patern.PaternWrite;
import tr.com.vbt.natural.parser.screen.patern.PaternWriteNoTitle;
import tr.com.vbt.natural.parser.screen.patern.PaternWriteWithPrintNumber;
import tr.com.vbt.natural.parser.screen.patern.PaternWriteWithPrintNumberAndNoTitle;

public class PaternManagerNaturalImpl  extends AbstractPaternManagerNatural{

	public PaternManagerNaturalImpl() {
		super();
		  
		commmandPatternList.add(new PaternWhen());
		commmandPatternList.add(new PaternExamineForGivingNumber());
		
		//General
		commmandPatternList.add(new PaternSlashStarComment());
		
		commmandPatternList.add(new PaternFetch());
		commmandPatternList.add(new PaternFetchReturn());
		commmandPatternList.add(new PaternRun());
		commmandPatternList.add(new PaternDefineData());
		commmandPatternList.add(new PaternInclude());
		commmandPatternList.add(new PaternEndDefineData());
		commmandPatternList.add(new PaternMainStart());
		commmandPatternList.add(new PaternEndMainStart());
		
		commmandPatternList.add(new PaternGlobalUsing());
		commmandPatternList.add(new PaternLocalUsing());
		commmandPatternList.add(new PaternSubroutine());
		commmandPatternList.add(new PaternEndSubroutine());
		
		commmandPatternList.add(new PaternBackoutTransaction());
		commmandPatternList.add(new PaternEndOfTransaction());
		commmandPatternList.add(new PaternCompute());
		commmandPatternList.add(new PaternComputeRounded());
		commmandPatternList.add(new PaternAssign());
		commmandPatternList.add(new PaternEndTransaction());
		commmandPatternList.add(new PaternStore());
		commmandPatternList.add(new PaternUpdate());
		commmandPatternList.add(new PaternDelete());
		commmandPatternList.add(new PaternSetControl());
		commmandPatternList.add(new PaternAtTopOfPage());
		commmandPatternList.add(new PaternEndTopOfPage());
		
		commmandPatternList.add(new PaternDo());
		commmandPatternList.add(new PaternDoEnd());
		commmandPatternList.add(new PaternOnError());
		
		//BasicVerbs
		commmandPatternList.add(new PaternDisplay());
		commmandPatternList.add(new PaternEndDisplay());
		commmandPatternList.add(new PaternExamineForDelete());
		commmandPatternList.add(new PaternExamineForGivingIndex());
		commmandPatternList.add(new PaternExamineForGivingPosition());
		commmandPatternList.add(new PaternExamineForGivingPositionIn());
		commmandPatternList.add(new PaternExamineGivingPositionIn());
		commmandPatternList.add(new PaternExamineFullForGivingLengthIn());
		commmandPatternList.add(new PaternExamineForGivingLength());
		commmandPatternList.add(new PaternExamineForGivingLengthIn());
		commmandPatternList.add(new PaternExamineForGiving());
		commmandPatternList.add(new PaternExamineForGivingNumber());
		commmandPatternList.add(new PaternExamineReplaceWith());
		commmandPatternList.add(new PaternAdd());
		commmandPatternList.add(new PaternPerform());
		commmandPatternList.add(new PaternMove());
		commmandPatternList.add(new PaternMoveAD()); //Ne olduğunu anlamalıyız.
		commmandPatternList.add(new PaternMoveByName());
		commmandPatternList.add(new PaternMoveLeftJustified());
		commmandPatternList.add(new PaternMoveRightJustified());
		commmandPatternList.add(new PaternReset());
		commmandPatternList.add(new PaternFormat());
		commmandPatternList.add(new PaternAmpersand());
		commmandPatternList.add(new PaternFormatWithNumber());
		commmandPatternList.add(new PaternCloseFile());
		commmandPatternList.add(new PaternAmpersand());
		commmandPatternList.add(new PaternDownloadFile());
		commmandPatternList.add(new PaternAtEndOfPage());
		commmandPatternList.add(new PaternEndAtEndOfPage());
		commmandPatternList.add(new PaternCallNat());
		commmandPatternList.add(new PaternStop());
		commmandPatternList.add(new PaternClosePrinter());
		commmandPatternList.add(new PaternSetKey());
		commmandPatternList.add(new PaternCommentEntry());
		commmandPatternList.add(new PaternBecomesEqualTo());
		commmandPatternList.add(new PaternBecomesEqualTo2());
		commmandPatternList.add(new PaternRedefine());
		commmandPatternList.add(new PaternDivide());
		commmandPatternList.add(new PaternCompress());
		commmandPatternList.add(new PaternNewPage());
		commmandPatternList.add(new PaternNewPage2());
		commmandPatternList.add(new PaternTerminate());
		commmandPatternList.add(new PaternSubtract());
		
		
		
		//Condition Start
		commmandPatternList.add(new PaternAcceptIf());
		commmandPatternList.add(new PaternAccept());
		commmandPatternList.add(new PaternRejectIf());
		commmandPatternList.add(new PaternIf());
		commmandPatternList.add(new PaternEndIf());
		commmandPatternList.add(new PaternEndIfBeforeElse());
		commmandPatternList.add(new PaternElse());
		commmandPatternList.add(new PaternElseIf());
		commmandPatternList.add(new PaternEndEvaluate());
		commmandPatternList.add(new PaternIgnore());
		commmandPatternList.add(new PaternDecideOn());
		commmandPatternList.add(new PaternDecideForFirstCondition());
		commmandPatternList.add(new PaternEndDecide());
		commmandPatternList.add(new PaternValue());
		commmandPatternList.add(new PaternNone());
		//Condition End
		
		//Loops
		commmandPatternList.add(new PaternRepeat());
		commmandPatternList.add(new PaternRepeatUntil());
		commmandPatternList.add(new PaternRepeatWhile());
		commmandPatternList.add(new PaternUntil());
		commmandPatternList.add(new PaternEndRepeat());
		commmandPatternList.add(new PaternEscapeTop());
		commmandPatternList.add(new PaternEscapeTop2());
		commmandPatternList.add(new PaternEscapeBottom());
		commmandPatternList.add(new PaternEscapeBottom2());
		commmandPatternList.add(new PaternEscapeRoutine());
		commmandPatternList.add(new PaternEscape());
		commmandPatternList.add(new PaternReturn());
		commmandPatternList.add(new PaternFor());
		commmandPatternList.add(new PaternFor2());
		commmandPatternList.add(new PaternFor3());
		commmandPatternList.add(new PaternEndFor());
		commmandPatternList.add(new PaternLoop());
		commmandPatternList.add(new PaternLoop2());
		commmandPatternList.add(new PaternStackTopCommand());
		commmandPatternList.add(new PaternStackCommand());
		commmandPatternList.add(new PaternStackTop());
		//
		
		//Screen
		commmandPatternList.add(new PaternInputUsingMap());
		commmandPatternList.add(new PaternInputMap());
		commmandPatternList.add(new PaternInputMarkUsingMap());
		commmandPatternList.add(new PaternReInput());
		commmandPatternList.add(new PaternReInputWith());
		commmandPatternList.add(new PaternInput());
		commmandPatternList.add(new PaternInputNoErase());
		
		commmandPatternList.add(new PaternWrite());
		commmandPatternList.add(new PaternWriteNoTitle());
		commmandPatternList.add(new PaternWriteWithPrintNumber());
		commmandPatternList.add(new PaternWriteWithPrintNumberAndNoTitle());
		
		commmandPatternList.add(new PaternControlWindow());
		commmandPatternList.add(new PaternControlScreen());
		commmandPatternList.add(new PaternDefineWindow());
		commmandPatternList.add(new PaternSetWindow());
		commmandPatternList.add(new PaternSize());
		commmandPatternList.add(new PaternTitle());
		commmandPatternList.add(new PaternBase());
		commmandPatternList.add(new PaternBase2());
		//Screen
		
		//Database
		commmandPatternList.add(new PaternReadBy());
		commmandPatternList.add(new PaternRead());
		commmandPatternList.add(new PaternReadByThru());
		commmandPatternList.add(new PaternReadByLogicalDescending());
		commmandPatternList.add(new PaternReadWith());
		commmandPatternList.add(new PaternReadWithThru());
		commmandPatternList.add(new PaternEndRead());
		commmandPatternList.add(new PaternFindWith());
		commmandPatternList.add(new PaternFindOneWith());
		commmandPatternList.add(new PaternFindNumberWith());
		commmandPatternList.add(new PaternEndFind());
		commmandPatternList.add(new PaternGet());
		commmandPatternList.add(new PaternIfNoRecords());
		commmandPatternList.add(new PaternIfNoRecordsFound());
		commmandPatternList.add(new PaternIfNoRecordFound());
		commmandPatternList.add(new PaternIfNoRecord());
		commmandPatternList.add(new PaternIfNo());
		commmandPatternList.add(new PaternEndNoRec());
		commmandPatternList.add(new PaternSelect());
		commmandPatternList.add(new PaternEndSelect());
		//Database
		
	}


}

