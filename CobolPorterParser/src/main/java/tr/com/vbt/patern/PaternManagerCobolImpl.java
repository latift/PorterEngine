package tr.com.vbt.patern;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.basicverbs.ElementUndefinedCobol;
import tr.com.vbt.cobol.parser.basicverbs.patern.PaternAccept;
import tr.com.vbt.cobol.parser.basicverbs.patern.PaternAdd;
import tr.com.vbt.cobol.parser.basicverbs.patern.PaternCommentEntry;
import tr.com.vbt.cobol.parser.basicverbs.patern.PaternCompute;
import tr.com.vbt.cobol.parser.basicverbs.patern.PaternDisplay;
import tr.com.vbt.cobol.parser.basicverbs.patern.PaternDivide;
import tr.com.vbt.cobol.parser.basicverbs.patern.PaternExit;
import tr.com.vbt.cobol.parser.basicverbs.patern.PaternExitSection;
import tr.com.vbt.cobol.parser.basicverbs.patern.PaternInitialize;
import tr.com.vbt.cobol.parser.basicverbs.patern.PaternMove;
import tr.com.vbt.cobol.parser.basicverbs.patern.PaternMultiply;
import tr.com.vbt.cobol.parser.basicverbs.patern.PaternPerform;
import tr.com.vbt.cobol.parser.basicverbs.patern.PaternStopRun;
import tr.com.vbt.cobol.parser.basicverbs.patern.PaternSubtract;
import tr.com.vbt.cobol.parser.conditions.patern.PaternElse;
import tr.com.vbt.cobol.parser.conditions.patern.PaternEndEvaluate;
import tr.com.vbt.cobol.parser.conditions.patern.PaternEndIf;
import tr.com.vbt.cobol.parser.conditions.patern.PaternEndIfBeforeElse;
import tr.com.vbt.cobol.parser.conditions.patern.PaternEvaluate;
import tr.com.vbt.cobol.parser.conditions.patern.PaternIf;
import tr.com.vbt.cobol.parser.conditions.patern.PaternWhen;
import tr.com.vbt.cobol.parser.datalayout.patern.PaternDataTypeFiller_XX;
import tr.com.vbt.cobol.parser.datalayout.patern.PaternDataTypeFiller_X_Size;
import tr.com.vbt.cobol.parser.datalayout.patern.PaternDataTypeLevel_99;
import tr.com.vbt.cobol.parser.datalayout.patern.PaternDataTypeLevel_99V99;
import tr.com.vbt.cobol.parser.datalayout.patern.PaternDataTypeLevel_99V9_Size;
import tr.com.vbt.cobol.parser.datalayout.patern.PaternDataTypeLevel_9_Size;
import tr.com.vbt.cobol.parser.datalayout.patern.PaternDataTypeLevel_9_SizeV99;
import tr.com.vbt.cobol.parser.datalayout.patern.PaternDataTypeLevel_9_Size_V9_Size;
import tr.com.vbt.cobol.parser.datalayout.patern.PaternDataTypeLevel_AA;
import tr.com.vbt.cobol.parser.datalayout.patern.PaternDataTypeLevel_A_Size;
import tr.com.vbt.cobol.parser.datalayout.patern.PaternDataTypeLevel_S99;
import tr.com.vbt.cobol.parser.datalayout.patern.PaternDataTypeLevel_S99V999;
import tr.com.vbt.cobol.parser.datalayout.patern.PaternDataTypeLevel_S99V9_Size;
import tr.com.vbt.cobol.parser.datalayout.patern.PaternDataTypeLevel_S9_Size;
import tr.com.vbt.cobol.parser.datalayout.patern.PaternDataTypeLevel_S9_Size_V99;
import tr.com.vbt.cobol.parser.datalayout.patern.PaternDataTypeLevel_S9_Size_V9_Size;
import tr.com.vbt.cobol.parser.datalayout.patern.PaternDataTypeLevel_XX;
import tr.com.vbt.cobol.parser.datalayout.patern.PaternDataTypeLevel_X_Size;
import tr.com.vbt.cobol.parser.datalayout.patern.PaternGroupDataType;
import tr.com.vbt.cobol.parser.division.patern.PaternConfigurationSection;
import tr.com.vbt.cobol.parser.division.patern.PaternDataDivision;
import tr.com.vbt.cobol.parser.division.patern.PaternEnvironmentDivision;
import tr.com.vbt.cobol.parser.division.patern.PaternFileControl;
import tr.com.vbt.cobol.parser.division.patern.PaternIdentificationDivision;
import tr.com.vbt.cobol.parser.division.patern.PaternInputOutputSection;
import tr.com.vbt.cobol.parser.division.patern.PaternLinkageSection;
import tr.com.vbt.cobol.parser.division.patern.PaternLocalStorageSection;
import tr.com.vbt.cobol.parser.division.patern.PaternMainParagraph;
import tr.com.vbt.cobol.parser.division.patern.PaternParagraph;
import tr.com.vbt.cobol.parser.division.patern.PaternProcedureDivision;
import tr.com.vbt.cobol.parser.division.patern.PaternSection;
import tr.com.vbt.cobol.parser.division.patern.PaternWorkingStorageSection;
import tr.com.vbt.cobol.parser.file.patern.PaternCloseFile;
import tr.com.vbt.cobol.parser.file.patern.PaternDeleteFile;
import tr.com.vbt.cobol.parser.file.patern.PaternFileDescription;
import tr.com.vbt.cobol.parser.file.patern.PaternFileSection;
import tr.com.vbt.cobol.parser.file.patern.PaternOpenFile;
import tr.com.vbt.cobol.parser.file.patern.PaternReWriteFile;
import tr.com.vbt.cobol.parser.file.patern.PaternReadFileRandom;
import tr.com.vbt.cobol.parser.file.patern.PaternReadFileSequentially;
import tr.com.vbt.cobol.parser.file.patern.PaternSelectFile;
import tr.com.vbt.cobol.parser.file.patern.PaternStartFile;
import tr.com.vbt.cobol.parser.file.patern.PaternWriteFile;
import tr.com.vbt.cobol.parser.file.patern.child.PaternAtEnd;
import tr.com.vbt.cobol.parser.file.patern.child.PaternInvalidKey;
import tr.com.vbt.cobol.parser.file.patern.child.PaternNotAtEnd;
import tr.com.vbt.cobol.parser.file.patern.child.PaternNotInvalidKey;
import tr.com.vbt.cobol.parser.file.patern.enders.PaternEndDelete;
import tr.com.vbt.cobol.parser.file.patern.enders.PaternEndReWrite;
import tr.com.vbt.cobol.parser.file.patern.enders.PaternEndRead;
import tr.com.vbt.cobol.parser.file.patern.enders.PaternEndStart;
import tr.com.vbt.cobol.parser.file.patern.enders.PaternEndWrite;
import tr.com.vbt.cobol.parser.loops.patern.PaternPerformTimes;
import tr.com.vbt.cobol.parser.loops.patern.PaternPerformUntil;
import tr.com.vbt.cobol.parser.loops.patern.PaternPerformVarying;
import tr.com.vbt.cobol.parser.loops.patern.enders.PaternEndPerform;
import tr.com.vbt.cobol.parser.sql.patern.PaternBeginDeclareSectionEndExec;
import tr.com.vbt.cobol.parser.sql.patern.PaternExecSQL;
import tr.com.vbt.cobol.parser.sql.patern.PaternExecSqlEndDeclareSection;
import tr.com.vbt.cobol.parser.sql.patern.PaternInclude;
import tr.com.vbt.cobol.parser.sql.patern.PaternIncludeSQLCA;
import tr.com.vbt.cobol.parser.sql.patern.PaternSQLFrom;
import tr.com.vbt.cobol.parser.sql.patern.PaternSQLInto;
import tr.com.vbt.cobol.parser.sql.patern.PaternSQLSelect;
import tr.com.vbt.cobol.parser.sql.patern.PaternSQLWhere;
import tr.com.vbt.cobol.parser.tableprocessing.patern.PaternSearch;
import tr.com.vbt.cobol.parser.tableprocessing.patern.PaternSetTo;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.TokenTipi;

public class PaternManagerCobolImpl  implements PaternManager{

	final static Logger logger = LoggerFactory.getLogger(PaternManagerCobolImpl.class);
	
	
	protected List<AbstractPattern> commmandPatternList=new ArrayList<AbstractPattern>();

	public PaternManagerCobolImpl() {
		super();
		
		
		//Div And Sections Start
		commmandPatternList.add(new PaternParagraph());
		commmandPatternList.add(new PaternProcedureDivision());
		commmandPatternList.add(new PaternConfigurationSection());
		commmandPatternList.add(new PaternDataDivision());
		commmandPatternList.add(new PaternWorkingStorageSection());
		commmandPatternList.add(new PaternLocalStorageSection());
		commmandPatternList.add(new PaternMainParagraph());
		commmandPatternList.add(new PaternSection());
		commmandPatternList.add(new PaternWorkingStorageSection());
		commmandPatternList.add(new PaternLinkageSection());
		commmandPatternList.add(new PaternEnvironmentDivision());
		commmandPatternList.add(new PaternFileSection());
		commmandPatternList.add(new PaternInputOutputSection());
		commmandPatternList.add(new PaternIdentificationDivision());
		//Div And Sections Start
		
		//DataType Start
		commmandPatternList.add(new PaternDataTypeLevel_S99());
		commmandPatternList.add(new PaternDataTypeLevel_X_Size());
		commmandPatternList.add(new PaternDataTypeLevel_XX());
		commmandPatternList.add(new PaternDataTypeLevel_A_Size());
		commmandPatternList.add(new PaternDataTypeLevel_AA());
		commmandPatternList.add(new PaternDataTypeFiller_X_Size());
		commmandPatternList.add(new PaternDataTypeFiller_XX());
		
		commmandPatternList.add(new PaternDataTypeLevel_99());
		commmandPatternList.add(new PaternDataTypeLevel_99V99());
		commmandPatternList.add(new PaternDataTypeLevel_99V9_Size());
		commmandPatternList.add(new PaternDataTypeLevel_9_Size());
		commmandPatternList.add(new PaternDataTypeLevel_9_Size_V9_Size());
		commmandPatternList.add(new PaternDataTypeLevel_9_SizeV99());
		
		commmandPatternList.add(new PaternDataTypeLevel_S99V999());
		commmandPatternList.add(new PaternDataTypeLevel_S99V9_Size());
		commmandPatternList.add(new PaternDataTypeLevel_S9_Size());
		commmandPatternList.add(new PaternDataTypeLevel_S9_Size_V99());
		commmandPatternList.add(new PaternDataTypeLevel_S9_Size_V9_Size());
		commmandPatternList.add(new PaternGroupDataType());
		//DataType Start
	
		//Basic Verb Start
		commmandPatternList.add(new PaternAccept());
		commmandPatternList.add(new PaternCompute());
		commmandPatternList.add(new PaternDisplay());
		commmandPatternList.add(new PaternMove());
		commmandPatternList.add(new PaternInitialize());
		commmandPatternList.add(new PaternCommentEntry());
		commmandPatternList.add(new PaternExitSection());
		commmandPatternList.add(new PaternExit());
		commmandPatternList.add(new PaternAdd());
		commmandPatternList.add(new PaternDivide());
		commmandPatternList.add(new PaternSubtract());
		commmandPatternList.add(new PaternMultiply());
		commmandPatternList.add(new PaternStopRun());
		commmandPatternList.add(new PaternSearch());
		commmandPatternList.add(new PaternSetTo());
		//Basic Verb End
		
		//Condition Start
		commmandPatternList.add(new PaternIf());
		commmandPatternList.add(new PaternEndIf());
		commmandPatternList.add(new PaternEndIfBeforeElse());
		commmandPatternList.add(new PaternElse());
		commmandPatternList.add(new PaternEvaluate());
		commmandPatternList.add(new PaternEndEvaluate());
		commmandPatternList.add(new PaternWhen());
		//Condition End
		
		
		//Loop start
		commmandPatternList.add(new PaternPerformUntil());
		commmandPatternList.add(new PaternPerformVarying());
		commmandPatternList.add(new PaternPerform());
		commmandPatternList.add(new PaternEndPerform());
		commmandPatternList.add(new PaternPerformTimes());
		//Loop End
		
		
		//File Start
		commmandPatternList.add(new PaternAtEnd());
		commmandPatternList.add(new PaternNotAtEnd());
		commmandPatternList.add(new PaternInvalidKey());
		commmandPatternList.add(new PaternNotInvalidKey());
		
		commmandPatternList.add(new PaternOpenFile());
		commmandPatternList.add(new PaternCloseFile());
		
		commmandPatternList.add(new PaternReadFileRandom());
		commmandPatternList.add(new PaternReadFileSequentially());
		commmandPatternList.add(new PaternWriteFile());
		commmandPatternList.add(new PaternReWriteFile());
		commmandPatternList.add(new PaternDeleteFile());
		commmandPatternList.add(new PaternStartFile());
		
		commmandPatternList.add(new PaternEndRead());
		commmandPatternList.add(new PaternEndStart());
		commmandPatternList.add(new PaternEndWrite());
		commmandPatternList.add(new PaternEndReWrite());
		commmandPatternList.add(new PaternEndDelete());
		
		commmandPatternList.add(new PaternSelectFile());
		commmandPatternList.add(new PaternFileControl());
		commmandPatternList.add(new PaternFileDescription());
		//File End
		
		
		
		//SQL Start
		commmandPatternList.add(new PaternExecSQL());
		commmandPatternList.add(new PaternInclude());
		commmandPatternList.add(new PaternBeginDeclareSectionEndExec());
		commmandPatternList.add(new PaternSQLSelect());
		commmandPatternList.add(new PaternSQLInto());
		commmandPatternList.add(new PaternSQLFrom());
		commmandPatternList.add(new PaternSQLWhere());
		commmandPatternList.add(new PaternExecSqlEndDeclareSection());
		commmandPatternList.add(new PaternIncludeSQLCA());
		//SQL End
		
	}
	
	
	
	
	public AbstractCommand findBestMatchedCommand(List<AbstractToken> tokenListesi, int offset) {
		
		List<AbstractCommand> matchedCommandList=new ArrayList<AbstractCommand>();
		
		ListIterator<AbstractPattern> commandPaternIterator =commmandPatternList.listIterator();
		
		AbstractPattern currentPatern;
		
		String undefinedCommandDescrtiption="";
		
		logger.info("");
		logger.info("");
		logger.info("");
		logger.info("*********************************************************");
		logger.info("COMMAND KONTROL:"+tokenListesi.get(offset));
		logger.info("");
		
		AbstractCommand command;
		do{
			currentPatern=commandPaternIterator.next();
			logger.info("PATERN KONTROL:"+tokenListesi.get(offset)+" "+currentPatern.toString()+" ...");
			command=currentPatern.getmatchedCommand(tokenListesi, offset);
			if(command!=null){
				matchedCommandList.add(command);
				logger.info("PATERN KONTROL:"+tokenListesi.get(offset)+" "+currentPatern.toString()+" ...MATCHED");
			}
		}while(commandPaternIterator.hasNext());
		if(command!=null&&command.getCommandName()!=null&&command.getCommandName().equals("VAC-CLSTYP")){
			logger.info("debug");
		}
		System.out.println(command);
		
		if(matchedCommandList.size()==0){
				logger.info("COMMAND KONTROL: Bulunamadi............" );
				logger.info("*********************************************************************************************");
				AbstractToken token=tokenListesi.get(offset);
				ElementUndefinedCobol undefCommand;
				
				while(!token.getTip().equals(TokenTipi.SatirBasi)){
					offset++;
					try {
							token=tokenListesi.get(offset);
							undefinedCommandDescrtiption=undefinedCommandDescrtiption+" "+token.toString();
						} catch (Exception e) {
							if(token.getTip().equals(TokenTipi.Kelime)||token.getTip().equals(TokenTipi.OzelKelime)||
									token.getTip().equals(TokenTipi.Karakter)||token.getTip().equals(TokenTipi.Nokta)){
								undefCommand=new ElementUndefinedCobol(token.toString(),"UNDEFINED_COBOL_KEYWORD");
								undefCommand.setDataToDisplay(token.toString());
							}else{
								undefCommand=new ElementUndefinedCobol("UNDEFINED_COBOL_KEYWORD","UNDEFINED_COBOL_KEYWORD");
							}
							return undefCommand;
						}
				}
				
				undefCommand=new ElementUndefinedCobol(undefinedCommandDescrtiption,"UNDEFINED_COBOL_KEYWORD");
				undefCommand.setDataToDisplay(token.toString());
				return undefCommand;
		
		}
		logger.info("COMMAND KONTROL: bestMatchedCommand:"+matchedCommandList.get(0).getDetailedCobolName() );
		logger.info("*********************************************************************************************");
		logger.info("");
		return getMaxPointedCommand(matchedCommandList);
		//return matchedCommandList.get(0);
		
	}




	private AbstractCommand getMaxPointedCommand(
			List<AbstractCommand> matchedCommandList) {
		AbstractCommand resultCommand = null;
		for (AbstractCommand abstractCommand : matchedCommandList) {
			if(resultCommand==null){
				resultCommand=abstractCommand;
			}
			if(resultCommand.getCommandMatchPoint()<abstractCommand.getCommandMatchPoint()){
				resultCommand=abstractCommand;
			}
		}
		return resultCommand;
	}



}

