package tr.com.vbt.patern;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.slf4j.MDC;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.AbstractEndingCommand;
import tr.com.vbt.cobol.parser.AbstractMultipleLinesCommand;
import tr.com.vbt.cobol.parser.DataTypeMapConverter;
import tr.com.vbt.cobol.parser.Levelable;
import tr.com.vbt.java.util.DataTypesCommandsUtility;
import tr.com.vbt.java.util.MultipleLinesCommandsNaturalUtility;
import tr.com.vbt.java.utils.VariableTypes;
import tr.com.vbt.lexer.AbstractLexing;
import tr.com.vbt.lexer.ConversionLogModel;
import tr.com.vbt.lexer.NaturalMode;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.natural.parser.basicverbs.ElementUndefinedCobol;
import tr.com.vbt.natural.parser.conditions.enders.ElementEndNone;
import tr.com.vbt.natural.parser.conditions.enders.ElementEndValue;
import tr.com.vbt.natural.parser.conditions.enders.ElementEndWhen;
import tr.com.vbt.natural.parser.datalayout.db.ElementDBDataTypeNatural;
import tr.com.vbt.natural.parser.datalayout.db.ElementDBViewOfNatural;
import tr.com.vbt.natural.parser.datalayout.db.enders.ElementEndLocal;
import tr.com.vbt.natural.parser.datalayout.db.enders.ElementEndParameter;
import tr.com.vbt.natural.parser.datalayout.program.ElementProgramDataTypeNatural;
import tr.com.vbt.natural.parser.datalayout.program.ElementProgramGrupNatural;
import tr.com.vbt.natural.parser.datalayout.program.ElementProgramOneDimensionArrayNatural;
import tr.com.vbt.natural.parser.datalayout.program.ElementProgramTwoDimensionArrayNatural;
import tr.com.vbt.natural.parser.datalayout.program.redefiners.ElementOneDimenRedefineArrayOfSimpleDataType;
import tr.com.vbt.natural.parser.datalayout.program.redefiners.ElementProgramRedefineGrupNatural;
import tr.com.vbt.natural.parser.datalayout.program.redefiners.ElementRedefineDataTypeOfSimpleDataType;
import tr.com.vbt.natural.parser.datalayout.program.redefiners.ElementTwoDimenRedefineArrayOfOneDimenArray;
import tr.com.vbt.natural.parser.enders.ElementEndGroupDataType;
import tr.com.vbt.natural.parser.enders.ElementEndSubroutine;
import tr.com.vbt.natural.parser.loops.ElementLoop;
import tr.com.vbt.natural.parser.screen.ElementEndDefineWindow;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.KarakterToken;
import tr.com.vbt.token.KelimeToken;
import tr.com.vbt.token.NoktaToken;
import tr.com.vbt.token.TokenTipi;
import tr.com.vbt.util.ConverterConfiguration;
import tr.com.vbt.util.WriteToFile;

public class NaturalCommandList extends AbstractCommandList {

	final static Logger logger = Logger.getLogger(NaturalCommandList.class);
	
	AbstractLexing lexer;

	private static NaturalCommandList instance = null;

	final private Map<String, Levelable> recordVariablesParentMap = new HashMap<>();

	
	
	VirtualEnderManagerForReportingMode enderManagerForReportMode=new VirtualEnderManagerForReportingMode(commandList);
	

	private int offset;



	private NaturalCommandList() {

	}



	public static AbstractCommandList getInstance(List<AbstractToken> tokenListesi, AbstractLexing lexer) {
		if (instance == null) {
			instance = new NaturalCommandList();
			instance.tokenListesi = tokenListesi;
			instance.lexer=lexer;
		}
		return instance;
	}

	public static AbstractCommandList getInstance() {
		if (instance == null) {
			instance = new NaturalCommandList();
		}
		return instance;
	}

	public void parseDataTypeTokensToCommandsList() throws Exception {
		logger.info("*************************************************************************************");
		logger.info("*************************************************************************************");
		logger.info("*************************************************************************************");
		logger.info("Start of Variable Definition Part Parse  Tokens To Command List");
		AbstractCommand command;

		offset = 0;

		int tokenListesiRealSize = getTokenListesiRealSize();

		do {
			command = paternManager.findBestMatchedCommand(tokenListesi, offset);
			calculateOperationState(tokenListesi, offset);

			commandList.add(command);

			if (command instanceof ElementUndefinedCobol) {
				ElementUndefinedCobol undefCommand = (ElementUndefinedCobol) command;
				undefinedCommandSet.add(undefCommand.getDataToDisplay());
			}

			setSourceCodeFullSatir(command, tokenListesi, offset, command.getCommandMatchPoint());
			logger.info("COMMAND MATCHED: " + command);
			//controlAndAddIncludeFiles(command);

			offset += command.getCommandMatchPoint();

			// Data kısmını PaternManagerNaturalImpl ile manage et. Sonraki
			// kısımlarda PaternManagerNaturalImpl da geç. Performans için.
			if (command.getCommandName().equals(ReservedNaturalKeywords.END_DEFINE_DATA)) {
				logger.info("End of Variable Definition Part Parse  Tokens To Command Lis");
				logger.info("*************************************************************************************");
				logger.info("*************************************************************************************");
				logger.info("*************************************************************************************");
				
				addIncludedLocalVariables();
				return;
			}

		} while (offset < tokenListesiRealSize);


	}


/**
 * 	Include edilen dosyalardaki localVariableReferans degiskeni alıp local localVariableReferans e ekleyecek.
 * @throws Exception 
 */
	private void addIncludedLocalVariables() throws Exception{
		String module = ConversionLogModel.getInstance().getModule();
		module = module.toLowerCase();
		module = module.replaceAll("/seperatedprograms", "");
		Object includedFileObject = null;

		for (String fileName : lexer.getIncludeFileList().values()) {
			
			logger.debug("File Name For Local Variable:"+fileName);

			try {
				
				includedFileObject = Class.forName("tr.com."+ConversionLogModel.getInstance().getCustomer().toLowerCase()+".dal.variables.local." + fileName)
						.newInstance();
			} catch (Exception e) {
				try {
					includedFileObject = Class.forName("tr.com."+ConversionLogModel.getInstance().getCustomer().toLowerCase()+".dal.variables.global." + fileName)
							.newInstance();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				continue;  //Globallere bakma.
			}

			Field f[] = includedFileObject.getClass().getFields();

			for (int i = 0; i < f.length; i++) {

				if (f[i].getName().equals("localVariableReferans")) {

					commandListWithIncludedViewVariables.addAll( (ArrayList) f[i].get(includedFileObject));
				}else if (f[i].getName().equals("tableColumnRedefiners")){
					
				}else if(f[i].getName().equals("tableColumnReferans")){
					
				}else{
					
					ElementProgramDataTypeNatural e=new ElementProgramDataTypeNatural(ReservedNaturalKeywords.DB_DATA_TYPE, "DATABASE.DB_DATA_TYPE");
					e.setDataName(f[i].getName());
					e.setDataType(f[i].getType().getSimpleName());
					commandListWithIncludedVariables.add(e);
				}
			}

		}
		AbstractCommand ender;
		
		ElementDBViewOfNatural dbViewOf;
		
		for(AbstractCommand inclCommand: commandListWithIncludedViewVariables ){
			
			if(inclCommand instanceof ElementDBViewOfNatural){
				
				dbViewOf=(ElementDBViewOfNatural) inclCommand;
				
				////*S**1 TESL2 VIEW OF AYK-TESL 
				dbViewOf.getParameters().put("levelNumber", "1");
				dbViewOf.getParameters().put("variableName", dbViewOf.getVariableName());
				dbViewOf.getParameters().put("typeName", dbViewOf.getTypeName());
						
				commandList.add(commandList.size()-1,dbViewOf);
				
				ender = new ElementEndGroupDataType(ReservedCobolKeywords.END_GROUP_DATA_TYPE, "");
				
				commandList.add(commandList.size()-1,ender);
			}
		}

	}

	@Override
	public void parseCommandTokensToCommandsList() {
		logger.info("*************************************************************************************");
		logger.info("*************************************************************************************");
		logger.info("*************************************************************************************");
		logger.info("Start of Program Parse  Tokens To Command List");
		AbstractCommand command;

		int tokenListesiRealSize = getTokenListesiRealSize();

		Iterator<AbstractCommand> it = commandList.iterator();

		do {
			command = paternManager.findBestMatchedCommand(tokenListesi, offset);
			calculateOperationState(tokenListesi, offset);

			commandList.add(command);

			if (command instanceof ElementUndefinedCobol) {
				ElementUndefinedCobol undefCommand = (ElementUndefinedCobol) command;
				undefinedCommandSet.add(undefCommand.getDataToDisplay());
			}

			setSourceCodeFullSatir(command, tokenListesi, offset, command.getCommandMatchPoint());
			logger.info("COMMAND MATCHED: " + command);
			
			offset += command.getCommandMatchPoint();

		} while (offset < tokenListesiRealSize);
		
		addCommandsToIncludedCommandsList();
		logger.info("End of Program Parse  Tokens To Command List");
		logger.info("*************************************************************************************");
		logger.info("*************************************************************************************");
		logger.info("*************************************************************************************");
	}



	private void addCommandsToIncludedCommandsList() {
		this.commandListWithIncludedViewVariables.addAll(this.commandList);
		this.commandListWithIncludedVariables.addAll(this.commandList);
		
	}

	private void setSourceCodeFullSatir(AbstractCommand command, List<AbstractToken> tokenListesi, int startIndex,
			int endIndex) {
		AbstractToken startToken = tokenListesi.get(startIndex);
		AbstractToken endToken = tokenListesi.get(endIndex);

	}

	private void calculateOperationState(List<AbstractToken> tokenListesi, int offset) {
		try {
			int totalTokenCount = tokenListesi.size();
			int operatedTokenCount = offset;
			int bitenOperasyonYüzdesi = operatedTokenCount * 100 / totalTokenCount;

			MDC.put("ConversionStatu", Integer.toString(bitenOperasyonYüzdesi));

			logger.info("PARSE OPERASYONU BİTME YÜZDESİ: Yüzde " + bitenOperasyonYüzdesi);
		} catch (Exception e) {
			logger.debug("", e);

		}

	}

	private int getTokenListesiRealSize() {
		int tokenListesiRealSize = tokenListesi.size();

		for (int index = tokenListesi.size() - 1; index > 0; index--) {
			if (tokenListesi.get(index).getTip().equals(TokenTipi.SatirBasi)
					|| tokenListesi.get(index).getTip().equals(TokenTipi.Nokta)) {
				tokenListesiRealSize--;
			} else {
				break;
			}
		}
		return tokenListesiRealSize;
	}

	public StringBuilder writeCommands(String fullFilePath, String undefinedCommandfullFilePath) {

		StringBuilder sb = new StringBuilder();
		for (AbstractCommand command : commandList) {
			sb.append(command.getSatirNumarasi()+" "+ command.exportCommands());
		}
		try {
			WriteToFile.writeToFile(sb, fullFilePath);
		} catch (IOException e) {
			logger.debug("", e);
		}

		StringBuilder undefinedBuffer = new StringBuilder();
		ElementUndefinedCobol undef;
		for (String undefStr : undefinedCommandSet) {
			undefinedBuffer.append(undefStr + "\n");
		}
		try {
			WriteToFile.writeToFile(undefinedBuffer, undefinedCommandfullFilePath);
		} catch (IOException e) {
			logger.debug("", e);
		}

		return sb;

	}

	public void findAndSetEndingCommands() throws Exception {
		MultipleLinesCommandsNaturalUtility utility = new MultipleLinesCommandsNaturalUtility();
		for (AbstractCommand command : commandList) {

			if (utility.isStarter(command)) {
				utility.putStarterToBuffer((AbstractMultipleLinesCommand) command);
			} else if (utility.isEnder(command)) {
				try {
					utility.registerEnderWithBufferLastStarter((AbstractEndingCommand) command);
				} catch (Exception e) {
					if (ConverterConfiguration.STOP_ENGINE_ON_TREE_CREATE_ERROR) {
				
						logger.error(e.getMessage() + " Önceki Aşamada Tanınamayan(Undefined) Multiple Line Komut var.",
								e);
						throw e;
					} else {
						
						logger.error("Her türlü program devam etmeli", e);
					}
				}
			}
			// System.out.println();
		}

	}

	/**
	 * /** Divisions: Identification Division Environment Division CONFIGURATION
	 * SECTION. INPUT-OUTPUT SECTION. Data Division File section Working-Storage
	 * section Local-Storage section Linkage section Procedure Division
	 */
	public void addVirtualEndings() {

		addVirtualEndingForLocal();
		
		addVirtualEndingForParameter();

		addVirtualEndingForDefineWindow();

		addVirtualEndingForValue();
		
		addVirtualEndingForNone();
		
		addVirtualEndingSubroutine();
		
		addVirtualEndingWhen();
		
		addVirtualLoopForFindAndRead(); //Read yada Find if yada for gibi bir şeyin icinde ise mutlaka loop la kapatilir. 
		
		if(ConversionLogModel.getInstance().getMode()==null || ConversionLogModel.getInstance().getMode().equals(NaturalMode.STRUCTRURED)){
			return;
		}
		enderManagerForReportMode.addVirtualEndersForReportMode();
		
	}


	
	private void addVirtualEndingWhen() {
		ElementEndWhen elementEndWhen;

		AbstractCommand curCommand;
		AbstractCommand nextCommand;

		for (int index = 0; index < commandList.size() - 1; index++) {

			curCommand = commandList.get(index);

			System.out.println(curCommand.getCommandName());

			if (curCommand.getCommandName().equals(ReservedNaturalKeywords.WHEN)) {

				while (true) {
					index++;
					if (index == commandList.size() - 1) {
						break;
					}
					nextCommand = commandList.get(index);

					if (nextCommand.getCommandName().equals(ReservedNaturalKeywords.WHEN)
							|| nextCommand.getCommandName().equals(ReservedNaturalKeywords.END_DECIDE)
							|| nextCommand.getCommandName().equals(ReservedNaturalKeywords.DECIDE)
							|| nextCommand.getCommandName().equals(ReservedNaturalKeywords.DECIDE_FIRST_CONDITION)
							|| nextCommand.getCommandName().equals(ReservedNaturalKeywords.DECIDE_ON_FIRST_VALUE)) {

						elementEndWhen = new ElementEndWhen("ElementEndWhen", "GENERAL.*.END_WHEN");
						elementEndWhen.setVisualCommand(true);
						commandList.add(index, elementEndWhen);
						break;
					}
				}
			}
		}
	}

	private void addVirtualEndingSubroutine() {
		
		if(ConversionLogModel.getInstance().getMode().equals(NaturalMode.STRUCTRURED)){
			return;
		}
		
		AbstractCommand curCommand = null, nextCommand, preCommand;
		
		AbstractCommand elementEndSubroutine = null;
		
		boolean firstSubroutineReached = false;
		
		for (int index = 0; index < commandList.size(); index++) {

			curCommand = commandList.get(index);
	
			if(curCommand.getCommandName().equals(ReservedNaturalKeywords.SUBROUTINE)){
				
				if(firstSubroutineReached){
					
					preCommand = commandList.get(index-1);
					if(preCommand.getCommandName().equals(ReservedNaturalKeywords.END_SUBROUTINE)){
						
						continue;  //END_SUBROUTINE varsa bir şey yapma
						
					}else{
						elementEndSubroutine=new ElementEndSubroutine(ReservedNaturalKeywords.END_SUBROUTINE,"GENERAL.END-SUBROUTINE");
						
						elementEndSubroutine.setVisualCommand(true);
						
						elementEndSubroutine.setSatirNumarasi(curCommand.getSatirNumarasi());
						
						commandList.add(index, elementEndSubroutine);
						
						index++;
					}
				
				}else{
				
					firstSubroutineReached=true;
				}
			}
			
		}
		
		//Add SubroutineEnder before END
		if(firstSubroutineReached){
			
			if(!commandList.get(commandList.size()-2).getCommandName().equals(ReservedNaturalKeywords.END_SUBROUTINE)){
			
				elementEndSubroutine=new ElementEndSubroutine(ReservedNaturalKeywords.END_SUBROUTINE,"GENERAL.END-SUBROUTINE");
				
				elementEndSubroutine.setVisualCommand(true);
			
				elementEndSubroutine.setSatirNumarasi(curCommand.getSatirNumarasi());
			
				commandList.add(commandList.size()-1, elementEndSubroutine);
			}
		}
		
		
	}

	

	
	

	

	private void addVirtualEndingForValue() {
		ElementEndValue elementEndValue;

		AbstractCommand curCommand;
		AbstractCommand nextCommand;

		for (int index = 0; index < commandList.size() - 1; index++) {

			curCommand = commandList.get(index);

			logger.debug(curCommand.getCommandName());

			if (curCommand.getCommandName().equals(ReservedNaturalKeywords.VALUE)) {

				while (true) {
					index++;
					if (index == commandList.size() - 1) {
						break;
					}
					nextCommand = commandList.get(index);

					if (nextCommand.getCommandName().equals(ReservedNaturalKeywords.VALUE)
							|| nextCommand.getCommandName().equals(ReservedNaturalKeywords.NONE)
							|| nextCommand.getCommandName().equals(ReservedNaturalKeywords.END_DECIDE)) {

						elementEndValue = new ElementEndValue("ElementEndValue", "GENERAL.*.END_VALUE");
						elementEndValue.setVisualCommand(true);
						commandList.add(index, elementEndValue);
						break;
					}
				}
			}
		}

	}

	
	
	
	private void addVirtualEndingForNone() {
		ElementEndNone elementEndNone;

		AbstractCommand curCommand;
		AbstractCommand nextCommand;

		for (int index = 0; index < commandList.size() - 1; index++) {

			curCommand = commandList.get(index);

			System.out.println(curCommand.getCommandName());

			if (curCommand.getCommandName().equals(ReservedNaturalKeywords.NONE)) {

				while (true) {
					index++;
					if (index == commandList.size() - 1) {
						break;
					}
					nextCommand = commandList.get(index);

					if (nextCommand.getCommandName().equals(ReservedNaturalKeywords.END_DECIDE)) {

						elementEndNone = new ElementEndNone("ElementEndNone", "GENERAL.*.END_NONE");
						elementEndNone.setVisualCommand(true);
						commandList.add(index, elementEndNone);
						break;
					}
				}
			}
		}

	}

	private void addVirtualEndingForLocal() {

		ElementEndLocal elementEndLocal = new ElementEndLocal("ElementEndLocal", "GENERAL.*.END-LOCAL", true);
		
		elementEndLocal.setVisualCommand(true);
		
		boolean addEnderForLocal = false;

		AbstractCommand curCommand;
		AbstractCommand nextCommand;

		for (int index = 0; index < commandList.size() - 1; index++) {

			curCommand = commandList.get(index);
			nextCommand = commandList.get(index + 1);

			logger.debug("curCommand:"+curCommand+"  curCommand.getCommandName:"+curCommand.getCommandName());

			if (curCommand.getCommandName().equals(ReservedNaturalKeywords.LOCAL)) {
				addEnderForLocal = true;
			} else if (addEnderForLocal && (!nextCommand.getCommandName().equals(ReservedNaturalKeywords.DATA_TYPE)
					&& !nextCommand.getCommandName().equals(ReservedNaturalKeywords.PROGRAM_DATA_TYPE)
					&& !nextCommand.getCommandName().equals(ReservedNaturalKeywords.REDEFINE_GROUP_DATA)
					&& !nextCommand.getCommandName().equals(ReservedNaturalKeywords.PROGRAM_REDEFINE_GROUP)
					&& !nextCommand.getCommandName().equals(ReservedNaturalKeywords.PROGRAM_REDEFINE_DATA_TYPE)
					&& !nextCommand.getCommandName().equals(ReservedNaturalKeywords.PROGRAM_ARRAY_DATA_TYPE)
					&& !nextCommand.getCommandName().equals(ReservedNaturalKeywords.PROGRAM_TWO_DIMENSION_ARRAY_DATA_TYPE)
					&& !nextCommand.getCommandName().equals(ReservedNaturalKeywords.MU_DATA_TYPE)
					&& !nextCommand.getCommandName().equals(ReservedNaturalKeywords.PROGRAM_ONE_DIMENSION_REDEFINE_ARRAY_OF_SIMPLE)
					&& !nextCommand.getCommandName().equals(ReservedNaturalKeywords.PROGRAM_TWO_DIMENSION_REDEFINE_ARRAY_DATA_TYPE)
					&& !nextCommand.getCommandName().equals(ReservedNaturalKeywords.VIEW_OF)
					&& !nextCommand.getCommandName().equals(ReservedNaturalKeywords.DB_DATA_TYPE)
					&& !nextCommand.getCommandName().equals(ReservedNaturalKeywords.DB_REDEFINE_GROUP_DATA)
					&& !nextCommand.getCommandName().equals(ReservedNaturalKeywords.END_GROUP_DATA_TYPE)
					&& !nextCommand.getCommandName().equals(ReservedNaturalKeywords.LOCAL_USING)
					&& !nextCommand.getDetailedCobolName().toUpperCase().contains(ReservedNaturalKeywords.UNDEFINED)
					&& !nextCommand.getCommandName().equals(ReservedNaturalKeywords.PROGRAM_GROUP)
					&& !nextCommand.getCommandName().equals(ReservedNaturalKeywords.COMMENT_ENTRY))) {
				elementEndLocal.setSatirNumarasi(curCommand.getSatirNumarasi());
				elementEndLocal.setVisualCommand(true);
				commandList.add(index + 1, elementEndLocal);
				addEnderForLocal = false;
			}
		}

	}
	
	private void addVirtualEndingForParameter() {

		ElementEndParameter elementEndParameter = new ElementEndParameter("ElementEndParameter", "GENERAL.*.END-PARAMETER");
		
		elementEndParameter.setVisualCommand(true);

		boolean addEnderForParameter = false;

		AbstractCommand curCommand;
		AbstractCommand nextCommand;

		for (int index = 0; index < commandList.size() - 1; index++) {

			curCommand = commandList.get(index);
			nextCommand = commandList.get(index + 1);

			System.out.println(curCommand.getCommandName());

			if (curCommand.getCommandName().equals(ReservedNaturalKeywords.PARAMETER)) {
				addEnderForParameter = true;
			} else if (addEnderForParameter && (!nextCommand.getCommandName().equals(ReservedNaturalKeywords.DATA_TYPE)
					&& !nextCommand.getCommandName().equals(ReservedNaturalKeywords.PROGRAM_DATA_TYPE)
					&& !nextCommand.getCommandName().equals(ReservedNaturalKeywords.REDEFINE_GROUP_DATA)
					&& !nextCommand.getCommandName().equals(ReservedNaturalKeywords.PROGRAM_REDEFINE_GROUP)
					&& !nextCommand.getCommandName().equals(ReservedNaturalKeywords.PROGRAM_REDEFINE_DATA_TYPE)
					&& !nextCommand.getCommandName().equals(ReservedNaturalKeywords.PROGRAM_ARRAY_DATA_TYPE)
					&& !nextCommand.getCommandName()
							.equals(ReservedNaturalKeywords.PROGRAM_TWO_DIMENSION_ARRAY_DATA_TYPE)
					&& !nextCommand.getCommandName().equals(ReservedNaturalKeywords.VIEW_OF)
					&& !nextCommand.getCommandName().equals(ReservedNaturalKeywords.DB_DATA_TYPE)
					&& !nextCommand.getCommandName().equals(ReservedNaturalKeywords.DB_REDEFINE_GROUP_DATA)
					&& !nextCommand.getCommandName().equals(ReservedNaturalKeywords.END_GROUP_DATA_TYPE)
					&& !nextCommand.getCommandName().equals(ReservedNaturalKeywords.LOCAL_USING)
					&& !nextCommand.getDetailedCobolName().toUpperCase().contains(ReservedNaturalKeywords.UNDEFINED)
					&& !nextCommand.getCommandName().equals(ReservedNaturalKeywords.PROGRAM_GROUP)
					&& !nextCommand.getCommandName().equals(ReservedNaturalKeywords.COMMENT_ENTRY))) {
				commandList.add(index + 1, elementEndParameter);
				addEnderForParameter = false;
			}
		}

	}

	private void addVirtualEndingForDefineWindow() {

		ElementEndDefineWindow elementEndDefineWindow = new ElementEndDefineWindow("ElementEndDefineWindow",
				"GENERAL.*.END-DEFINE_WINDOW");
		elementEndDefineWindow.setVisualCommand(true);

		boolean addEnderForLocal = false;

		AbstractCommand curCommand;
		AbstractCommand nextCommand;

		for (int index = 0; index < commandList.size() - 1; index++) {

			curCommand = commandList.get(index);
			nextCommand = commandList.get(index + 1);

			System.out.println(curCommand.getCommandName());

			if (curCommand.getCommandName().equals(ReservedNaturalKeywords.DEFINE_WINDOW)) {
				addEnderForLocal = true;
			} else if (addEnderForLocal && (!nextCommand.getCommandName().equals(ReservedNaturalKeywords.SIZE)
					&& !nextCommand.getCommandName().equals(ReservedNaturalKeywords.TITLE)
					&& !nextCommand.getCommandName().equals(ReservedNaturalKeywords.CONTROL_WINDOW_SCREEN)
					&& !nextCommand.getCommandName().equals(ReservedNaturalKeywords.BASE))) {
				commandList.add(index + 1, elementEndDefineWindow);
				addEnderForLocal = false;
			}
		}
	}
	
	//Read yada Find if yada for gibi bir şeyin icinde ise mutlaka loop la kapatilir. 
	//Bu durumda bu kod bir şey yapmaz.
	//addEndSubroutine den daha sonra cagirilmali.
		//    1 Tüm tokeni gez.
		 	//  1.1 FIND yada READ gördükce buffera koy. 
		 	//  1.2 LOOP gördükce bufferdan cıkar.
			//  1.3 END-SUBROUTINE gördüğünde Bufferda varsa buffer kadar LOOP koy.
		private void addVirtualLoopForFindAndRead() {
			if (ConversionLogModel.getInstance().getMode().equals(NaturalMode.STRUCTRURED)) {
				return;
			}

			AbstractCommand curCommand, visualLoop;
		
			List<AbstractCommand> readFindBuffer=new ArrayList<>();

		
//		    1 Tüm tokeni gez.
			for (int index = 0; index < commandList.size(); index++) {

				curCommand = commandList.get(index);

				logger.debug("CurCommand:" + curCommand);

				//1.1 FIND yada READ gördükce buffera koy. 
				if (curCommand.getCommandName().equals(ReservedNaturalKeywords.FIND_ONE_WITH)||curCommand.getCommandName().equals(ReservedNaturalKeywords.FIND_WITH)
						|| curCommand.getCommandName().equals(ReservedNaturalKeywords.FIND)|| curCommand.getCommandName().equals(ReservedNaturalKeywords.READ)
						|| curCommand.getCommandName().equals(ReservedNaturalKeywords.READ_BY)) {
					logger.debug("Case 1:"+readFindBuffer.size()+" curCommand="+curCommand.toString());	
					readFindBuffer.add(curCommand);
					
				//  1.2 LOOP gördükce bufferdan cıkar.	
				}else if(curCommand.getCommandName().equals(ReservedNaturalKeywords.LOOP)){
					logger.debug("Case 2:"+readFindBuffer.size()+" curCommand="+curCommand.toString());	
					if(readFindBuffer.size()>0){
						readFindBuffer.remove(readFindBuffer.size()-1); //Son Elemani Cikar
					}else{
						
					}
					
					
				//  1.3 END-SUBROUTINE gördüğünde Bufferda varsa buffer kadar LOOP koy.
				}else if(curCommand.getCommandName().equals(ReservedNaturalKeywords.END_SUBROUTINE) || curCommand.getCommandName().equals(ReservedNaturalKeywords.SUBROUTINE)){
					while(readFindBuffer.size()>0){
						logger.debug("Case 3:"+readFindBuffer.size()+" curCommand="+curCommand.toString());	
						visualLoop=new ElementLoop(ReservedNaturalKeywords.LOOP,"GENERAL.*.LOOP");
						visualLoop.setVisualCommand(true);
						if(readFindBuffer.size()>0){
							readFindBuffer.remove(readFindBuffer.size()-1);
						}else{
							
						}
						commandList.add(index,visualLoop);
					}
				}
			}
		}

	/**

	 * 1) current.GroupDataType=true, --> putToBuffer 
	 * 2) current.DataType=true, next.dataType=false, next.GroupDataType=false--> addEnderFromBuffer-->
	 * addEnderFromBuffer since currentLevel<=01 
	 * 3)
	 * current.DataType=true,currentLevel>nextLevel --> addEnderFromBuffer-->
	 * addEnderFromBuffer since currentLevel<=nextLevel
	 * 
	 */
	public void findAndSetDataTypeDefinitions() {

		DataTypesCommandsUtility utility = new DataTypesCommandsUtility();

		AbstractCommand currentCommand, nextCommand;

		Levelable currentLevelable = null, nextLevelable = null;

		for (int index = 0; index < commandList.size() - 1; index++) {
			currentCommand = commandList.get(index);
			nextCommand = commandList.get(index + 1);
			logger.info("Current Command:" + currentCommand.toString());
			logger.info("Next Command:" + nextCommand.toString());
			
			// 1) current.GroupDataType=true, --> putToBuffer
			if (DataTypesCommandsUtility.isGroupDataType(currentCommand) && DataTypesCommandsUtility.isGroupDataType(nextCommand) && currentCommand.getLevelNumber()==nextCommand.getLevelNumber()) {
				
				try {
					if(currentLevelable==null){
						currentLevelable = new ElementEndGroupDataType(ReservedCobolKeywords.END_GROUP_DATA_TYPE, "",
								1);
					}else{
						currentLevelable = new ElementEndGroupDataType(ReservedCobolKeywords.END_GROUP_DATA_TYPE, "",
								currentLevelable.getLevelNumber());
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				commandList.add(index+1, (AbstractCommand) currentLevelable);

			}else if (DataTypesCommandsUtility.isGroupDataType(currentCommand)) {
				currentLevelable = (Levelable) currentCommand;
				currentLevelable = new ElementEndGroupDataType(ReservedCobolKeywords.END_GROUP_DATA_TYPE, "",
						currentLevelable.getLevelNumber());
				logger.info("PUT TO BUFFER:" + currentCommand.toString());
				utility.putEndingCommandToBuffer(currentLevelable);

				// 2) current.DataType=true, next.dataType=false,
				// next.GroupDataType=false--> addEnderFromBuffer-->
				// addEnderFromBuffer since currentLevel<=01
			} else if (DataTypesCommandsUtility.isDataType(currentCommand)
					&& !DataTypesCommandsUtility.isDataType(nextCommand)
					&& !DataTypesCommandsUtility.isGroupDataType(nextCommand)) {
				// addEnderFromBuffer since currentLevel<=01
				while (utility.bufferHasElement()) {
					currentLevelable = utility.getEndGroupDataTypeToBuffer();
					logger.info("**************************START***************************************");
					logger.info("ADD1 FROM BUFFER:" + currentCommand.toString() + " Ender Name:"
							+ ((AbstractCommand) currentLevelable).toString());
					logger.info("Buffer Size:" + utility.bufferSize());
					logger.info("**************************END***************************************");
					System.out.println();
					System.out.println();
					System.out.println();
					index++;
					commandList.add(index, (AbstractCommand) currentLevelable);
				}
				;

				// 3) current.DataType=true,currentLevel>nextLevel -->
				// addEnderFromBuffer--> addEnderFromBuffer since
				// currentLevel>nextLevel
			} else if (DataTypesCommandsUtility.isDataType(currentCommand)
					&& (DataTypesCommandsUtility.isDataType(nextCommand)
							|| DataTypesCommandsUtility.isGroupDataType(nextCommand))) {

				currentLevelable = (Levelable) currentCommand;
				nextLevelable = (Levelable) nextCommand;

				if (nextLevelable.getLevelNumber() < currentLevelable.getLevelNumber()) {

					do {
						if (!utility.bufferHasElement()) {
							break;
						}
						currentLevelable = utility.getEndGroupDataTypeToBuffer();
						if (nextLevelable.getLevelNumber() > currentLevelable.getLevelNumber()) {
							utility.putEndingCommandToBuffer(currentLevelable);
							break;
						}
						logger.info("**************************START***************************************");
						logger.info("ADD2 FROM BUFFER:" + currentCommand.toString() + " Ender Name:"
								+ ((AbstractCommand) currentLevelable).toString());
						logger.info("Buffer2 Size:" + utility.bufferSize());
						logger.info("**************************END***************************************");
						System.out.println();
						System.out.println();
						System.out.println();
						index++;
						commandList.add(index, (AbstractCommand) currentLevelable);
					} while (true);

				}

			}

		}
		;

	}

	public StringBuilder writeCommands(String fullFilePath) {
		StringBuilder sb = new StringBuilder();
		for (AbstractCommand command : commandList) {
			sb.append(command.exportCommands());
		}
		try {
			WriteToFile.writeToFile(sb, fullFilePath);
		} catch (IOException e) {
			logger.debug("", e);
		}
		return sb;

	}

	@Override
	public void generateDatatypeMap() {

		DataTypeMapConverter mapConverter;
		int commandIndex = 0;
		for (AbstractCommand command : this.commandList) {

			if (command instanceof DataTypeMapConverter) {
				mapConverter = (DataTypeMapConverter) command;

				mapConverter.generateDataTypeConversionString(commandList, commandIndex);
			}
			commandIndex++;
		}

		printDataTypeMap();

	}

	private void printDataTypeMap() {
		MapManager manager = MapManagerNaturalImpl.getInstance();
		manager.printMaps();

	}

	/*
	@Override
	public void setRedefineElementsParents() {
		Iterator<AbstractCommand> commandListIt = commandList.iterator();
		AbstractCommand curCommand = null, previosCommand;

		while (commandListIt.hasNext()) {
			previosCommand = curCommand;
			curCommand = commandListIt.next();
			if (curCommand instanceof ElementProgramRedefineGrupNatural) {
				((ElementProgramRedefineGrupNatural) curCommand).setRedefinedCommand(previosCommand);
			}

		}

	}*/
	
	@Override
	public void setRedefineElementsParents() {
		Iterator<AbstractCommand> commandListIt = commandList.iterator();
		AbstractCommand curCommand = null, redefinedCommand;
		
		ElementProgramRedefineGrupNatural redefineGrupCommand;

		while (commandListIt.hasNext()) {
			
			curCommand=commandListIt.next();
			
			if (curCommand instanceof ElementProgramRedefineGrupNatural) {
				redefineGrupCommand=(ElementProgramRedefineGrupNatural) curCommand;
				
				redefinedCommand=findCommandByName(redefineGrupCommand.getRedefineName());
				
				if(redefinedCommand==null){
					throw new NullPointerException(redefineGrupCommand.getRedefineName()+"Bulunamadi. SAtir No:"+redefineGrupCommand.getSatirNumarasi());
				}
				
				((ElementProgramRedefineGrupNatural) curCommand).setRedefinedCommand(redefinedCommand);
			}

		}

	}
	
	private AbstractCommand findCommandByName(String commandDataName) {
		
		Iterator<AbstractCommand> commandListIt = commandList.iterator();
		
		AbstractCommand curCommand;
		
		ElementProgramDataTypeNatural curDataTypeDefinition = null;
		
		ElementDBDataTypeNatural  curDBDataTypeDefinition=null;
		
		ElementProgramOneDimensionArrayNatural curOneDimensionArray=null;
		
		ElementProgramTwoDimensionArrayNatural curTwoDimensionArray=null;
		
		while (commandListIt.hasNext()) {
			curCommand=commandListIt.next();
			logger.debug("CurCommand:"+curCommand.getDataName());
		/*	if(curCommand instanceof ElementProgramDataTypeNatural ){
				curDataTypeDefinition =(ElementProgramDataTypeNatural) curCommand;
			
				if(curDataTypeDefinition.getDataName()==null){
					throw new NullPointerException();
				}
				if(curDataTypeDefinition.getDataName().equals(commandDataName)){
					return curCommand;
				}
			}else if(curCommand instanceof ElementDBDataTypeNatural){
				curDBDataTypeDefinition =(ElementDBDataTypeNatural) curCommand;
				
				if(curDBDataTypeDefinition.getDataName()==null){
					throw new NullPointerException();
				}
				if(curDBDataTypeDefinition.getDataName().equals(commandDataName)){
					return curCommand;
				}
			}else if(curCommand instanceof ElementProgramOneDimensionArrayNatural){
				curOneDimensionArray =(ElementProgramOneDimensionArrayNatural) curCommand;
				
				if(curOneDimensionArray.getDataName()==null){
					throw new NullPointerException();
				}
				if(curOneDimensionArray.getDataName().equals(commandDataName)){
					return curCommand;
				}
			}else if(curCommand instanceof ElementProgramTwoDimensionArrayNatural){
				curTwoDimensionArray =(ElementProgramTwoDimensionArrayNatural) curCommand;
				
				if(curTwoDimensionArray.getDataName()==null){
					throw new NullPointerException();
				}
				if(curOneDimensionArray.getDataName().equals(commandDataName)){
					return curCommand;
				}
			}else {
				curCommand.getDataName().equals(commandDataName){
					
				}
			}*/
			try {
				if(curCommand.getDataName()!=null && curCommand.getDataName().equals(commandDataName)){
					return curCommand;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return null;
	}

	/* ElementProgramGrupNatural olanları bulup
	 * ElementDBDataTypeNatural olması gerekenleri bunla
	 * değiştirir.
	 * 
	 * PaternDBDataTypeNatural ve PaternProgramGrupNatural arasındaki 
	 * benzerlikten dolayı oluyor.
	 * 
	 * //ElementProgramGrupNatural --> ElementDBDataTypeNatural
	 * 
	 *  *S**1 PERF50 VIEW OF PERF50
	 	*S**  	2 SICNO50
	 	*
	 	*
	 	*1 levelli viewOf varsa  isViewOf=true set et.
	 	*
	 	*isViewOf true ise ve level 1 den büyükse ve ElementProgramGrupNatural ise  cevrimi yap.
	 	*
	 	*isViewOF true ise ve level 1 se isViewOf false yapılacak.
	 	*
	 */
	@Override
	public void changeProgramDataTypeToDBDataType() {
		
		AbstractCommand curCommand;
		
		boolean isViewOf=false;
		
		Levelable curLevelable;
		
		ElementProgramGrupNatural elementProgramGrupNatural;
		
		ElementDBDataTypeNatural newCreatedDBDataTypeElement;
		
		for (int indexX = 0; indexX < commandList.size(); indexX++) {
			
			curCommand= commandList.get(indexX);
			
			logger.debug("CurCommand:"+curCommand.toString());
			
			if(curCommand instanceof ElementDBViewOfNatural ){
				
				isViewOf=true;
				continue;
			}
			
			if(isViewOf && curCommand instanceof Levelable){
				
				curLevelable=(Levelable) curCommand;
				
				if(curLevelable.getLevelNumber()==1){
					isViewOf=false;
					continue;
				}
			}
			
			if(isViewOf && curCommand instanceof ElementProgramGrupNatural){
				
				elementProgramGrupNatural=(ElementProgramGrupNatural) curCommand;
				
				newCreatedDBDataTypeElement= new ElementDBDataTypeNatural(ReservedNaturalKeywords.DB_DATA_TYPE, "DATABASE.DB_DATA_TYPE");
				
				newCreatedDBDataTypeElement.setLevelNumber(elementProgramGrupNatural.getLevelNumber());
				
				newCreatedDBDataTypeElement.setDataName(elementProgramGrupNatural.getDataName());
				
				newCreatedDBDataTypeElement.setSatirNumarasi(elementProgramGrupNatural.getSatirNumarasi());
				
				commandList.remove(indexX);
				
				commandList.add(indexX, newCreatedDBDataTypeElement);
				
				logger.debug("Changed Current:"+curCommand +" --> "+newCreatedDBDataTypeElement);
			}
		}
		
	}


	/**
	 * ElementProgramDataTypeNatural olanları bulup
	 * ElementProgramRedefineDataTypeNatural olması gerekenleri bunla
	 * değiştirir.
	 * 
	 * Simple Redefine S**01 #R-BASTAR(A8) S**01 REDEFINE #R-BASTAR S** 02
	 * #R-BASTARN(N8) -->
	 */
	@Override
	public void markSimpleRedefineElements() {
		AbstractCommand command = null, redefineElement, nextElement;
		ElementRedefineDataTypeOfSimpleDataType newElement;
		ElementProgramDataTypeNatural programDataTypeElement;
		ElementProgramRedefineGrupNatural redefineGroupCommand, redefineGroupCommandLevel2 = null;

		ElementProgramDataTypeNatural redefinedElement = null;
		ElementDBDataTypeNatural redefinedDBElement = null;
		ElementProgramOneDimensionArrayNatural redefinedOneDimensionArrayNatural=null;

		int offset = 0, offset2 = 0;

		int indexY;
		for (int indexX = 0; indexX < commandList.size(); indexX++) {
			command = commandList.get(indexX);

			if (command instanceof ElementProgramRedefineGrupNatural) {
				redefineGroupCommand = (ElementProgramRedefineGrupNatural) command;
				indexY = indexX;
				indexY++;
				nextElement = commandList.get(indexY);
				logger.debug("Control For Redefine:" + nextElement.getClass());

				while (nextElement instanceof ElementProgramDataTypeNatural
						|| nextElement instanceof ElementProgramRedefineGrupNatural
						|| (nextElement instanceof ElementEndGroupDataType)) {

					if (nextElement instanceof ElementEndGroupDataType) {

						if (((ElementEndGroupDataType) nextElement).getLevelNumber() == 1) {
							break;
						}

						offset2 = 0;

					} else if (nextElement instanceof ElementProgramRedefineGrupNatural) {

						redefineGroupCommandLevel2 = (ElementProgramRedefineGrupNatural) nextElement;

					} else if(nextElement.getLevelNumber()==command.getLevelNumber()){
						break;
					}else {
					

						programDataTypeElement = (ElementProgramDataTypeNatural) nextElement;

						// if (programDataTypeElement.getLevelNumber() >
						// redefineGroupCommand.getLevelNumber()) {
						logger.debug("Control For Redefine:" + programDataTypeElement.getDataName());
						if (programDataTypeElement.getLevelNumber() > 1) {
							newElement = new ElementRedefineDataTypeOfSimpleDataType(
									ReservedNaturalKeywords.PROGRAM_REDEFINE_DATA_TYPE,
									"GENERAL.PROGRAM_REDEFINE_DATA_TYPE");
							newElement.setLevelNumber(programDataTypeElement.getLevelNumber());
							newElement.setDataType(programDataTypeElement.getDataType());
							newElement.setDataName(programDataTypeElement.getDataName());
							newElement.setLength(programDataTypeElement.getLength());
							newElement.setLengthAfterDot(programDataTypeElement.getLengthAfterDot());
							newElement.setParameters(programDataTypeElement.getParameters());

							if (programDataTypeElement.getLevelNumber() == 2) {
								try {
									redefinedElement = (ElementProgramDataTypeNatural) redefineGroupCommand
											.getRedefinedCommand();
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
									break;
									
								}
								newElement.setRedefineStartIndex(offset);
								offset += newElement.getLength();
								newElement.setRedefineEndIndex(offset);

								if(redefinedElement==null){
									continue;
								}
								newElement.setRedefinedDataName(redefinedElement.getDataName());
								newElement.setRedefinedDataType(redefinedElement.getDataType());
								
						
							} else if (programDataTypeElement.getLevelNumber() == 3) {
									if(redefineGroupCommandLevel2!=null){
										
										if(redefineGroupCommandLevel2.getRedefinedCommand() instanceof ElementProgramDataTypeNatural){
											redefinedElement = (ElementProgramDataTypeNatural) redefineGroupCommandLevel2.getRedefinedCommand();
											newElement.setRedefinedDataName(redefinedElement.getDataName());
											newElement.setRedefinedDataType(redefinedElement.getDataType());
										}else if(redefineGroupCommandLevel2.getRedefinedCommand() instanceof ElementDBViewOfNatural){
											
											break;
										}
										
									}else{ //Level2 ise null ise record içinde Redefine var demektir. O zaman Level 1 alttan devam eder.
										if(redefineGroupCommand.getRedefinedCommand() instanceof ElementProgramDataTypeNatural){
											redefinedElement = (ElementProgramDataTypeNatural) redefineGroupCommand.getRedefinedCommand();
										
											newElement.setRedefinedDataName(redefinedElement.getDataName());
											newElement.setRedefinedDataType(redefinedElement.getDataType());
										
										}else if(redefineGroupCommand.getRedefinedCommand() instanceof ElementDBDataTypeNatural){
											
											redefinedDBElement= (ElementDBDataTypeNatural) redefineGroupCommand.getRedefinedCommand();
											newElement.setRedefinedDataName(redefinedDBElement.getDataName());
											newElement.setRedefinedDataType(redefinedDBElement.getDataType());
										}else if(redefineGroupCommand.getRedefinedCommand() instanceof ElementProgramOneDimensionArrayNatural){
											
											redefinedOneDimensionArrayNatural= (ElementProgramOneDimensionArrayNatural) redefineGroupCommand.getRedefinedCommand();
											newElement.setRedefinedDataName(redefinedOneDimensionArrayNatural.getDataName());
											newElement.setRedefinedDataType(redefinedOneDimensionArrayNatural.getDataType());
										}
									}
									
									newElement.setRedefineStartIndex(offset2);
									offset2 += newElement.getLength();
									newElement.setRedefineEndIndex(offset2);
									
									newElement.getParameters().put("redefineStartIndex", newElement.getRedefineStartIndex());
									newElement.getParameters().put("redefineEndIndex", newElement.getRedefineEndIndex());
									newElement.getParameters().put("redefinedDataType", newElement.getRedefinedDataType());
									newElement.getParameters().put("redefinedDataName", newElement.getRedefinedDataName());
					
							}

							commandList.set(indexY, newElement);
						}

					}
					indexY++;
					nextElement = commandList.get(indexY);
				}
				offset = 0;
				offset2 = 0;

			}

		}

		setRedefiningVariableRedefinedValues();

	}

	/**
	 * Ana objedeki değerleri redefine objesine atar.
	 */
	private void setRedefiningVariableRedefinedValues() {
		AbstractCommand command = null, redefineElement, nextElement;
		ElementRedefineDataTypeOfSimpleDataType redefinerElement = null, redefinedCommandLevel2 = null;
		ElementProgramRedefineGrupNatural redefineGroupCommand;
		ElementProgramDataTypeNatural redefinedCommand = null;
		ElementDBDataTypeNatural redefinedDbDataTypeCommand=null;

		int indexY;
		for (int indexX = 0; indexX < commandList.size(); indexX++) {
			command = commandList.get(indexX);

			if (command instanceof ElementProgramRedefineGrupNatural) {
				redefineGroupCommand = (ElementProgramRedefineGrupNatural) command;
				logger.debug("redefineGroupCommand: " + redefineGroupCommand);
				logger.debug("redefineGroupCommand.getRedefinedCommand: " + redefineGroupCommand.getRedefinedCommand());
				try {
					if(redefineGroupCommand.getRedefinedCommand()  instanceof  ElementProgramDataTypeNatural){
						redefinedCommand = (ElementProgramDataTypeNatural) redefineGroupCommand.getRedefinedCommand();
					}else if(redefineGroupCommand.getRedefinedCommand()  instanceof  ElementDBDataTypeNatural){
						redefinedDbDataTypeCommand=(ElementDBDataTypeNatural) redefineGroupCommand.getRedefinedCommand();
					}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				indexY = indexX;
				indexY++;
				nextElement = commandList.get(indexY);
				while (nextElement instanceof ElementRedefineDataTypeOfSimpleDataType
						|| nextElement instanceof ElementProgramRedefineGrupNatural
						|| nextElement instanceof ElementEndGroupDataType) {

					if (nextElement instanceof ElementProgramRedefineGrupNatural) {
						redefinedCommandLevel2 = redefinerElement;
					} else if (nextElement instanceof ElementEndGroupDataType) {
						redefinedCommandLevel2 = null;

						if (((ElementEndGroupDataType) nextElement).getLevelNumber() == 1) {
							break;
						}

					} else {
						redefinerElement = (ElementRedefineDataTypeOfSimpleDataType) nextElement;
						if (redefinerElement.getLevelNumber() == 2) {
							if (redefinerElement.getLevelNumber() > redefineGroupCommand.getLevelNumber()) {
								logger.debug("Redefine: " + redefinerElement.getDataName()
										+ " Ana objedeki değerleri redefine objesine ata");
								redefinerElement.setRedefinedDataName(redefinedCommand.getDataName());
								redefinerElement.setRedefinedDataType(redefinedCommand.getDataType());
								redefinerElement.getParameters().put("redefinedDataName",
										redefinerElement.getRedefinedDataName());
								redefinerElement.getParameters().put("redefinedDataType",
										redefinerElement.getRedefinedDataType());
								redefinerElement.getParameters().put("redefineStartIndex",
										redefinerElement.getRedefineStartIndex());
								redefinerElement.getParameters().put("redefineEndIndex",
										redefinerElement.getRedefineEndIndex());

							}
						} else if (redefinerElement.getLevelNumber() == 3) {
							if (redefinerElement.getLevelNumber() > redefineGroupCommand.getLevelNumber()) {
								try {
									logger.debug("Redefine: " + redefinerElement.getDataName()
											+ " Ana objedeki değerleri redefine objesine ata");
									redefinerElement.setRedefinedDataName(redefinedCommandLevel2.getDataName());
									redefinerElement.setRedefinedDataType(redefinedCommandLevel2.getDataType());
									redefinerElement.getParameters().put("redefinedDataName",
											redefinerElement.getRedefinedDataName());
									redefinerElement.getParameters().put("redefinedDataType",
											redefinerElement.getRedefinedDataType());
									redefinerElement.getParameters().put("redefineStartIndex",
											redefinerElement.getRedefineStartIndex());
									redefinerElement.getParameters().put("redefineEndIndex",
											redefinerElement.getRedefineEndIndex());
								} catch (Exception e) {
									// Conversion devam etmeli. Redefine hatalı
									// olsada çevrim devam etmeli.
									logger.debug(e.getMessage(), e);
								}

							}
						}
					}
					indexY++;
					nextElement = commandList.get(indexY);
				}
				indexX = indexY;

			}

		}

	}


	private void setRedefiner(AbstractCommand current, List<AbstractCommand> redefinedDataTypeList) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * *S**1 TAX-EXC-TEXT(A50)
			*S**1 REDEFINE TAX-EXC-TEXT
			*S**  2 TAX-EXC-ITEM(A5/10)
	 * 
	 * <PROGRAM_DATA_TYPE PROGRAM_DATA_TYPE="1 TAX_EXC_TEXT  Length:50 DecimalLength:0 Initial Value:null ."></PROGRAM_DATA_TYPE>
			<PROGRAM_REDEFINE_GROUP PROGRAM_REDEFINE_GROUP="TAX_EXC_TEXT>
					<PROGRAM_ARRAY_DATA_TYPE PROGRAM_ARRAY_DATA_TYPE="2 TAX_EXC_ITEM  DataType:A ArrayLength:10">
		</PROGRAM_ARRAY_DATA_TYPE>
	 * 
	 * ElementProgramOneDimensionArrayNatural -->
	 * ElementOneDimenRedefineArrayOfSimpleDataType
	 */

	@Override
	public void markOneDimensionRedefineElementOfSimpleDataType() {
		// Iterator<AbstractCommand> commandListIt=commandList.iterator();
		AbstractCommand curCommand = null, redefineElement, nextElement;
		
		ElementOneDimenRedefineArrayOfSimpleDataType newElement;
		
		ElementProgramOneDimensionArrayNatural programOneDimensionArrayNatural;
		
		ElementProgramRedefineGrupNatural redefineGroupCommand;

		for (int index = 0; index < commandList.size(); index++) {

			curCommand = commandList.get(index);

			if (curCommand instanceof ElementProgramRedefineGrupNatural) {

				redefineGroupCommand = (ElementProgramRedefineGrupNatural) curCommand;

				nextElement = commandList.get(index + 1);

				if (nextElement instanceof ElementProgramOneDimensionArrayNatural) {
					programOneDimensionArrayNatural = (ElementProgramOneDimensionArrayNatural) nextElement;

					if (programOneDimensionArrayNatural.getLevelNumber() > redefineGroupCommand.getLevelNumber()) {
						newElement = new ElementOneDimenRedefineArrayOfSimpleDataType(
								ReservedNaturalKeywords.PROGRAM_ONE_DIMENSION_REDEFINE_ARRAY_OF_SIMPLE,
								"GENERAL.PROGRAM_ONE_DIMENSION_REDEFINE_ARRAY_OF_SIMPLE");
						newElement.setLevelNumber(programOneDimensionArrayNatural.getLevelNumber());
						newElement.getParameters().put("levelNumber", newElement.getLevelNumber());

						newElement.setDataType(programOneDimensionArrayNatural.getDataType());
						newElement.getParameters().put("dataType", newElement.getDataType());

						newElement.setDataName(programOneDimensionArrayNatural.getDataName());
						newElement.getParameters().put("dataName", newElement.getDataName());

						newElement.setArrayLength(programOneDimensionArrayNatural.getArrayLength());
						newElement.getParameters().put("arrayLength", newElement.getArrayLength());

						if(((ElementProgramRedefineGrupNatural) redefineGroupCommand).getRedefinedCommand() instanceof ElementProgramDataTypeNatural){
							
							newElement.setRedefinedDataType((ElementProgramDataTypeNatural) ((ElementProgramRedefineGrupNatural) redefineGroupCommand).getRedefinedCommand());
							newElement.getParameters().put("redefinedCommand", newElement.getRedefinedDataType());
	
							commandList.remove(index + 1);
							commandList.add(index + 1, newElement);

						}
					}

				}
			}

		}
	}

	/**
	 * SimpleRedefineInArray S**01 #SIRA-DIZI(N3/900) S**01 REDEFINE #SIRA-DIZI
	 * S** 02 #SIRA-SAYFA(N3/60,15)
	 * 
	 * ElementProgramTwoDimensionArrayNatural -->
	 * ElementProgramTwoDimensionRedefineArrayNatural
	 */

	@Override
	public void markTwoDimensionRedefineElements() {
		// Iterator<AbstractCommand> commandListIt=commandList.iterator();
		AbstractCommand curCommand = null, redefineElement, nextElement;
		ElementTwoDimenRedefineArrayOfOneDimenArray newElement;
		ElementProgramTwoDimensionArrayNatural programTwoDimensionArrayNatural;
		ElementProgramRedefineGrupNatural redefineGroupCommand;

		for (int index = 0; index < commandList.size(); index++) {

			curCommand = commandList.get(index);

			if (curCommand instanceof ElementProgramRedefineGrupNatural) {

				redefineGroupCommand = (ElementProgramRedefineGrupNatural) curCommand;

				nextElement = commandList.get(index + 1);

				if (nextElement instanceof ElementProgramTwoDimensionArrayNatural) {
					programTwoDimensionArrayNatural = (ElementProgramTwoDimensionArrayNatural) nextElement;

					if (programTwoDimensionArrayNatural.getLevelNumber() > redefineGroupCommand.getLevelNumber()) {
						newElement = new ElementTwoDimenRedefineArrayOfOneDimenArray(
								ReservedNaturalKeywords.PROGRAM_TWO_DIMENSION_REDEFINE_ARRAY_DATA_TYPE,
								"GENERAL.PROGRAM_TWO_DIMENSION_REDEFINE_ARRAY_DATA_TYPE");
						newElement.setLevelNumber(programTwoDimensionArrayNatural.getLevelNumber());
						newElement.getParameters().put("levelNumber", newElement.getLevelNumber());

						newElement.setDataType(programTwoDimensionArrayNatural.getDataType());
						newElement.getParameters().put("dataType", newElement.getDataType());

						newElement.setDataName(programTwoDimensionArrayNatural.getDataName());
						newElement.getParameters().put("dataName", newElement.getDataName());

						newElement.setArrayLength(programTwoDimensionArrayNatural.getArrayLength());
						newElement.getParameters().put("arrayLength", newElement.getArrayLength());

						newElement.setArrayLength2(programTwoDimensionArrayNatural.getArrayLength2());
						newElement.getParameters().put("arrayLength2", newElement.getArrayLength2());

						newElement.setRedefinedCommand(
								((ElementProgramRedefineGrupNatural) redefineGroupCommand).getRedefinedCommand());
						newElement.getParameters().put("redefinedCommand", newElement.getRedefinedCommand());

						commandList.remove(index + 1);
						commandList.add(index + 1, newElement);

					}

				}
			}

		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see tr.com.vbt.patern.CommandList#createAndFillRecordDataTypeMap()
	 * Record Tipindeki commandları gezip. alt sınıflarını mape atar.
	 * 
	 * Algoritma: Grup Data Type görünce onu grupDataType olarak set et. Yeni
	 * grup DataType görünceye kadar. Sonraki elemanları tek tek gez. Bulduğun
	 * elemenların herbirini mape kaydet. Şu şekilde KAydet. Key-->normalCommand
	 * Value-->Grup.Command
	 */
	@Override
	public void createAndFillRecordDataTypeMap() {
		DataTypesCommandsUtility utility = new DataTypesCommandsUtility();

		AbstractCommand curCommmand = null, currentGrupCommand, nextCommand = null;

		Levelable currentLevelable = null, nextLevelable = null, currentGrupCommandLevelable;

		Iterator<AbstractCommand> it = commandList.iterator();

		while (it.hasNext()) {

			if (curCommmand == null) {
				curCommmand = it.next();
			} else {
				curCommmand = nextCommand;
			}

			logger.debug("Current Command:" + curCommmand);
			if (curCommmand.getCommandName().equals(ReservedNaturalKeywords.END_DEFINE_DATA)) {
				return;
			}

			if (!DataTypesCommandsUtility.isRecordGroupDataType(curCommmand)) {
				if(it.hasNext()){
					nextCommand = it.next();
				}
				continue;
			}

			currentGrupCommand = curCommmand;

			do {

				currentGrupCommandLevelable = (Levelable) currentGrupCommand;

				logger.debug(currentGrupCommand.toString());
				if (!it.hasNext()) {
					break;
				}
				nextCommand = it.next();

				if (nextCommand instanceof ElementProgramRedefineGrupNatural) {
					continue;
				}
				if (DataTypesCommandsUtility.isDataType(nextCommand)) {

					nextLevelable = (Levelable) nextCommand;

					if (currentGrupCommandLevelable.getLevelNumber() >= nextLevelable.getLevelNumber()) {
						break;
					}

					recordVariablesParentMap.put(nextLevelable.getDataName(), currentGrupCommandLevelable);

					logger.debug("Record Variable Mape Eklendi:" + nextLevelable.getDataName() + "  "
							+ currentGrupCommandLevelable.getDataName());
					logger.debug("....");
				}

			} while (!DataTypesCommandsUtility.isGroupDataType(nextCommand) || nextCommand instanceof ElementProgramRedefineGrupNatural);

		}
		;

	}

	/**
	 * Tüm Komut kısmı tokenları gezilecek. recordVariablesParentMap le
	 * uyanlarin önüne ilgili abstracttoken eklenecek.
	 */
	@Override
	public void addGrupNamesToRecordItems() {

		AbstractToken curKelimeToken, newlyCreatedGrupNameToken, noktaToken;

		Levelable grupCommand;

		int innerOffset = offset;

		noktaToken = new KarakterToken('.', 0, 0, 0);

		for (int index = innerOffset; index < tokenListesi.size(); index++) {

			curKelimeToken = tokenListesi.get(index);

			
			// FIND XXX WITH ....... THEN : With ile Then arasındakilere
			// eklenmez.
			// FIND ve WITH görünce THEN görünceye kadar devam et.
			/*if (curKelimeToken.getTip().equals(TokenTipi.OzelKelime)
					&& curKelimeToken.getDeger().equals(ReservedNaturalKeywords.FIND)) {

				while (!(curKelimeToken.getTip().equals(TokenTipi.OzelKelime)
						&& curKelimeToken.getDeger().equals(ReservedNaturalKeywords.THEN))) {
					index++;
					curKelimeToken = tokenListesi.get(index);
				}
			}*/

			logger.debug(curKelimeToken.toString());
			
			if (curKelimeToken.getTip().equals(TokenTipi.Kelime) || curKelimeToken.getTip().equals(TokenTipi.Array)) {

				if (recordVariablesParentMap.containsKey(curKelimeToken.getDeger())) {

					grupCommand = recordVariablesParentMap.get(curKelimeToken.getDeger());

					noktaToken = new NoktaToken<>(".");
					tokenListesi.add(index, noktaToken);

					newlyCreatedGrupNameToken = new KelimeToken<>(grupCommand.getDataName(), 0, 0, 0);
					tokenListesi.add(index, newlyCreatedGrupNameToken);

					index = index + 2;

				}
			}

		}

	}

	@Override
	public void controlRecords() {
		AbstractToken current;
		AbstractToken next;
		AbstractToken nextOfNext;

		String currentDeger;
		String nextNextDeger;

		int innerOffset = offset;

		for (int i = innerOffset; i < tokenListesi.size() - 2; i++) {

			current = tokenListesi.get(i);
			next = tokenListesi.get(i + 1);
			nextOfNext = tokenListesi.get(i + 2);

			logger.debug("current:" + current);

			if ((current.getDeger() instanceof String) && (next.getTip().equals(TokenTipi.Nokta))
					&& (nextOfNext.getDeger() instanceof String)) {
				currentDeger = (String) current.getDeger();
				nextNextDeger = (String) nextOfNext.getDeger();

				current.setLinkedToken(nextOfNext);
				current.setRecordVariable(true);
				tokenListesi.remove(i + 2);
				tokenListesi.remove(i + 1);
			}

		}

	}

	@Override
	public void markRedefinedTokens() {

		AbstractToken current;

		int innerOffset = offset;
		
		ElementRedefineDataTypeOfSimpleDataType simpleRedefine;

		redefinedCommandList = new ArrayList<Levelable>();
		
		Levelable redDataType;
		
		for (int k = 0; k < commandList.size(); k++) {
			if (commandList.get(k) instanceof ElementRedefineDataTypeOfSimpleDataType || commandList.get(k) instanceof ElementOneDimenRedefineArrayOfSimpleDataType) {
				redefinedCommandList.add((Levelable) commandList.get(k));
			}
		}

		for (int i = innerOffset; i < tokenListesi.size() - 2; i++) {

			current = tokenListesi.get(i);

			logger.debug("current:" + current);

			if (current.isKelime() || current.isArray()) {

				for (int k = 0; k < redefinedCommandList.size(); k++) {
					
					if (redefinedCommandList.get(k).getDataName().equals(current.getDeger().toString())) {
						current.setRedefinedVariable(true);
						if(redefinedCommandList.get(k) instanceof ElementRedefineDataTypeOfSimpleDataType){
							simpleRedefine=(ElementRedefineDataTypeOfSimpleDataType) redefinedCommandList.get(k);
						
							String varType=simpleRedefine.getDataType();
							if(varType.equals("A")){
								current.setVarType(VariableTypes.STRING_TYPE);
							}else if(varType.equals("N")){
								current.setVarType(VariableTypes.LONG_TYPE);
							}else if(varType.equals("D")){
								current.setVarType(VariableTypes.DATE_TYPE);
							}
						}
						if(redefinedCommandList.get(k) instanceof ElementOneDimenRedefineArrayOfSimpleDataType){
							current.setRedefinedVariableDimensionToSimple(true);
						}
					}else if (current.getLinkedToken()!=null &&  redefinedCommandList.get(k).getDataName().equals(current.getLinkedToken().getDeger().toString())) {
						current.getLinkedToken().setRedefinedVariable(true);
						if(redefinedCommandList.get(k) instanceof ElementRedefineDataTypeOfSimpleDataType){
							simpleRedefine=(ElementRedefineDataTypeOfSimpleDataType) redefinedCommandList.get(k);
						
							String varType=simpleRedefine.getDataType();
							if(varType.equals("A")){
								current.getLinkedToken().setVarType(VariableTypes.STRING_TYPE);
							}else if(varType.equals("N")){
								current.getLinkedToken().setVarType(VariableTypes.LONG_TYPE);
							}else if(varType.equals("D")){
								current.getLinkedToken().setVarType(VariableTypes.DATE_TYPE);
							}
						}
						if(redefinedCommandList.get(k) instanceof ElementOneDimenRedefineArrayOfSimpleDataType){
							current.getLinkedToken().setRedefinedVariableDimensionToSimple(true);
						}
					}
				}
			}

		}
	}



	public static void reset() {
		instance = null;

	}

	@Override
	public void setTypeNameOfView() {
		
		AbstractToken current;
		AbstractToken next;
		AbstractToken nextOfNext;

		/*
		int innerOffset = offset;

		for (int i = innerOffset; i < tokenListesi.size() - 2; i++) {

			current = tokenListesi.get(i);
			next = tokenListesi.get(i + 1);
			nextOfNext = tokenListesi.get(i + 2);

			logger.debug("current:" + current);

			if(includedPojoList)

		}*/
		
	}



	@Override
	public void addDefineData() {
		// TODO Auto-generated method stub
		
	}



}
