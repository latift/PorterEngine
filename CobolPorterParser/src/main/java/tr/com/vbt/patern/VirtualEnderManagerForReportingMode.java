package tr.com.vbt.patern;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
import tr.com.vbt.natural.parser.enders.ElementEndEndOfPage;
import tr.com.vbt.natural.parser.general.ElementDo;
import tr.com.vbt.natural.parser.general.ElementDoEnd;
import tr.com.vbt.natural.parser.general.ElementEndError;
import tr.com.vbt.natural.parser.loops.ElementLoop;

public class VirtualEnderManagerForReportingMode {

	final static Logger logger = LoggerFactory.getLogger(VirtualEnderManagerForReportingMode.class);

	protected List<AbstractCommand> commandList;

	public VirtualEnderManagerForReportingMode(List<AbstractCommand> commandList) {
		this.commandList = commandList;
	}

	public void addVirtualEndersForReportMode() {
		//addEndErrorWithDoDoend(); // Reporting Mode Do DoEndleri sayar. O olunca OnErrorEnd çakar.

		addVirtualAtTopOfPage();
		
		addVirtualEndIf(); // Reporting Mode level kullanir. if den sonra do yoksa ilgili yere end-if koyar.

		
	}

	


/*
 * 	At Top Of PAge den sonra DO varsa sonraki DOEND i bulur ve sonrasına Ender koyar.
 * 	At Top Of PAge den sonra DO yoksa sonraki komuttan sonra DOEND koyar. 
 */
	private void addVirtualAtTopOfPage() {
		if (ConversionLogModel.getInstance().getMode().equals(NaturalMode.STRUCTRURED)) {
			return;
		}
		AbstractCommand curCommand = null, nextCommand;
				
		for (int index = 0; index < commandList.size() - 1; index++) {
			
			curCommand=commandList.get(index);
			
			nextCommand=commandList.get(index+1);
			
			// AT TOP OF PAGE
			// DO
			// 	SOME COMMAND
			//DOEND
			// Put ender to here after DOEND
			if(curCommand.commandNameEquals(ReservedNaturalKeywords.AT_TOP_OF_PAGE) && nextCommand.commandNameEquals(ReservedNaturalKeywords.DO)){
				
				do{
					index++;
					nextCommand=commandList.get(index+1);
				}while(!nextCommand.commandNameEquals(ReservedNaturalKeywords.DOEND));
				
				ElementEndEndOfPage visualeEnder=new ElementEndEndOfPage(ReservedNaturalKeywords.END_ENDPAGE,"GENERAL.*.END_ENDPAGE");
				visualeEnder.setSatirNumarasi(nextCommand.getSatirNumarasi());
				visualeEnder.setVisualCommand(true);
				commandList.add(index+2,visualeEnder );
			
				// AT TOP OF PAGE (index)
				// 	ONE SIMPLE COMMAND (index+1)
				// Put ender to here after DOEND (index+2)
			}else if(curCommand.commandNameEquals(ReservedNaturalKeywords.AT_TOP_OF_PAGE) && !nextCommand.commandNameEquals(ReservedNaturalKeywords.DO)){
				
				ElementEndEndOfPage visualeEnder=new ElementEndEndOfPage(ReservedNaturalKeywords.END_ENDPAGE,"GENERAL.*.END_ENDPAGE");
				visualeEnder.setSatirNumarasi(nextCommand.getSatirNumarasi());
				visualeEnder.setVisualCommand(true);
				commandList.add(index+2,visualeEnder );
			}
			
		}
		
	}

	//
	// 1: Tüm Token Dizisini Gez.
	//	  	1.1  If ve Do görürsen buffera END-IF koy.  
	//				Mode unu IF+DO set et. LastItemMode u IF+DO set et.
	//		1.2. If siz Do görürsen buffera END-IF koy. 
	//				Mode unu    DO set et. LastItemMode u DO set et.
	//		1.3  DO suz if görürsen buffera END-IF koy. 
	//				Mode unu IF set et.    LastItemMode u IF set et. Levelini kaydet.
	//		1.4. DoEnd görürsen
	//		 		1.4.1 LastItemMode IF+DO ise  
	//							bufferdaki END-IF i koy. LastItemMode u ve Leveli bufferdaki sonItem ile set et.
	//				1.4.2 LastItemMode DO ise 
	//							bir şey yapma. LastItemMode u ve Leveli bufferdaki sonItem ile set et.
	//		1.5  LastItemMode IF ise  ve CurrentLevel LastItemLevel ile aynı ise, 
	//						Bufferdaki END-IF i koy.  LastItemMode u ve Leveli  bufferdaki sonItem ile set et.
	private void addVirtualEndIf() {
		if (ConversionLogModel.getInstance().getMode().equals(NaturalMode.STRUCTRURED)) {
			return;
		}

		AbstractCommand curCommand = null, nextCommand,visualEndIf, visualEndError, previousCommand;
		
		List<AbstractCommand> commandBuffer=new ArrayList<>();
		
		String currentMode = "";
		
		int currentLevel=0;
		
		boolean dataDefinitionPart=true;
		
		for (int index = 0; index < commandList.size() - 1; index++) {
			
			previousCommand=curCommand;
			
			curCommand=commandList.get(index);
			
			/*
			 *  PERP206A
			 *  *S**IF PRIN = 'P' THEN 
				*S**	DO
				*S**		WRITE(2) 
				*S**      		HARTR50
				*S**      		PERF41.UNIAD41(AL=30) PERF40.UNVAD40(AL=30)
				*S**      		ISGRP50   DEREC50
				*S**      		KADEM50 3X ISTAZ50 6X KIDUC50 3X EKTAZ50
				*S**      		DIGRP50   DKADE50   YDUCR50   PARBR50
				*S**		IF *LINE-COUNT(2) > 60 THEN 
				*S**					DO
				*S**                          NEWPAGE(2)
				*S**                          PERFORM YAZ
				*S**                    DOEND
				*S**   DOEND
			 */
			if(curCommand.isOneOfCommands(ReservedCobolKeywords.END_IF)&& curCommand.isVisualCommand()&& currentMode.contains("DO")){ //PERP206A if do if do doend doend şeklindeki kodlar için yazıldı.
				continue;
			}else if(curCommand.isOneOfCommands(ReservedCobolKeywords.END_IF)&& curCommand.isVisualCommand()){ //Visual Eklenenleri dikkate alma. Önceki komutu iki kere operasyondan geçir.
				curCommand=previousCommand;
			}
			
			nextCommand=commandList.get(index+1);
			
			if(curCommand.getCommandName().equals(ReservedNaturalKeywords.MAIN_START)){
				dataDefinitionPart=false;
				continue;
			}
			if(dataDefinitionPart){
				continue;
			}
			
			////	  	1.1  If ve Do görürsen buffera END-IF koy.  Mode unu IF+DO set et. LastItemMode u IF+DO set et.
			if(curCommand.isOneOfCommands(ReservedCobolKeywords.IF,ReservedCobolKeywords.ELSE,ReservedCobolKeywords.ELSE_IF,ReservedNaturalKeywords.REJECT_IF,ReservedNaturalKeywords.ACCEPT_IF,ReservedNaturalKeywords.IF_NO_RECORDS,ReservedNaturalKeywords.IF_NO)
					&& nextCommand.commandNameEquals(ReservedNaturalKeywords.DO)){
				currentLevel++;
				currentMode="IF+DO";
				visualEndIf=new ElementEndIf(ReservedCobolKeywords.END_IF,"GENERAL.*.END_IF");
				visualEndIf.setVisualCommand(true);
				visualEndIf.setMode(currentMode);
				visualEndIf.setLevelNumber(currentLevel);
				commandBuffer.add(visualEndIf);
				
				logger.debug("Case 1: Buffera VisualEndIf Eklendi. CurrentMode: IF+DO  CurrentLevel:"+currentLevel+" CurCommand:"+curCommand.getSatirNumarasi()+curCommand.getCommandName());
				
			}else 	if(curCommand.isOneOfCommands(ReservedNaturalKeywords.ON_ERROR)
					&& nextCommand.commandNameEquals(ReservedNaturalKeywords.DO)){
				currentLevel++;
				currentMode="ONERROR+DO";
				visualEndError = new ElementEndError("END_ERROR", "GENERAL.*.END_ERROR");
				visualEndError.setVisualCommand(true);
				visualEndError.setMode(currentMode);
				visualEndError.setLevelNumber(currentLevel);
				commandBuffer.add(visualEndError);
				
				logger.debug("Case 1_1: Buffera visualEndError Eklendi.  CurrentMode: IF+DO  CurrentLevel:"+currentLevel+" CurCommand:"+curCommand.getSatirNumarasi()+curCommand.getCommandName());
				
			}
			//1.2. If siz Do görürsen 
			else if(nextCommand.commandNameEquals(ReservedNaturalKeywords.DO)) {//IF
				//Mode unu    DO set et. LastItemMode u DO set et.
				currentMode="DO";
				currentLevel++;
				visualEndIf=new ElementEndIf(ReservedCobolKeywords.END_IF,"GENERAL.*.END_IF");
				visualEndIf.setVisualCommand(true);
				visualEndIf.setMode(currentMode);
				visualEndIf.setLevelNumber(currentLevel);
				commandBuffer.add(visualEndIf);
				logger.debug("Case 2: Buffera VisualEndIf Eklendi.  CurrentMode: DO  CurrentLevel:"+currentLevel+" CurCommand:"+curCommand.getSatirNumarasi()+curCommand.getCommandName());
				
			}
			//		1.3  DO suz if görürsen buffera END-IF koy.
			else if(curCommand.isOneOfCommands(ReservedCobolKeywords.IF,ReservedCobolKeywords.ELSE_IF,ReservedCobolKeywords.ELSE,ReservedNaturalKeywords.REJECT_IF,ReservedNaturalKeywords.ACCEPT_IF,ReservedNaturalKeywords.IF_NO_RECORDS,ReservedNaturalKeywords.IF_NO)
					&& !nextCommand.commandNameEquals(ReservedNaturalKeywords.DO)){
//				Mode unu IF set et.    LastItemMode u IF set et. Levelini kaydet.
				currentMode="IF";
				currentLevel++;
				visualEndIf=new ElementEndIf(ReservedCobolKeywords.END_IF,"GENERAL.*.END_IF");
				visualEndIf.setVisualCommand(true);
				visualEndIf.setMode(currentMode);
				visualEndIf.setLevelNumber(currentLevel);
				commandBuffer.add(visualEndIf);
				logger.debug("Case 3: Buffera VisualEndIf Eklendi. CurrentMode: IF  CurrentLevel:"+currentLevel+" CurCommand:"+curCommand.getSatirNumarasi()+curCommand.getCommandName());
				
				// 1.4. DoEnd görürsen
			}else if(curCommand.commandNameEquals(ReservedNaturalKeywords.DOEND) && currentMode.equals("IF+DO")){
					currentLevel--;
//		 			1.4.1 LastItemMode IF+DO ise  bufferdaki END-IF i koy. LastItemMode u ve Leveli bufferdaki sonItem ile set et.
					commandList.add(index+1,commandBuffer.get(commandBuffer.size()-1)); //Bufferdaki son elemanı koy.
					commandBuffer.remove(commandBuffer.size()-1);//Bufferdan sil.
					if(commandBuffer.size()==0){
						currentMode="";
					}else{
						currentMode=commandBuffer.get(commandBuffer.size()-1).getMode();
					}
					
					logger.debug("Case 4: Koda Bufferdan Ender Eklendi CurrentMode: "+currentMode+"  CurrentLevel:"+currentLevel+" CurCommand:"+curCommand.getSatirNumarasi()+curCommand.getCommandName());
			}else if(curCommand.commandNameEquals(ReservedNaturalKeywords.DOEND) && currentMode.equals("ONERROR+DO")){
				currentLevel--;
//	 			1.4.1 LastItemMode IF+DO ise  bufferdaki END-IF i koy. LastItemMode u ve Leveli bufferdaki sonItem ile set et.
				commandList.add(index+1,commandBuffer.get(commandBuffer.size()-1)); //Bufferdaki son elemanı koy.
				commandBuffer.remove(commandBuffer.size()-1);//Bufferdan sil.
				if(commandBuffer.size()==0){
					currentMode="";
				}else{
					currentMode=commandBuffer.get(commandBuffer.size()-1).getMode();
				}
				
				logger.debug("Case 4_1: Koda Bufferdan Ender Eklendi CurrentMode: "+currentMode+"  CurrentLevel:"+currentLevel+" CurCommand:"+curCommand.getSatirNumarasi()+curCommand.getCommandName());
			}
//					1.4.2 LastItemMode DO ise  bir şey yapma. Bufferdan bir kayit sil.		
			else if(curCommand.commandNameEquals(ReservedNaturalKeywords.DOEND) && currentMode.equals("DO")){
					currentLevel--;
					commandBuffer.remove(commandBuffer.size()-1);//Bufferdan sil.
					if(commandBuffer.size()==0){
						currentMode="";
					}else{
						currentMode=commandBuffer.get(commandBuffer.size()-1).getMode();
					}
					
					logger.debug("Case 5: Koda Bir şey yapılmadı. Bufferdan silindi. CurrentMode: "+currentMode+"  CurrentLevel:"+currentLevel+" CurCommand:"+curCommand.getSatirNumarasi()+curCommand.getCommandName());
				
//				1.5  LastItemMode IF ise  ve CurrentLevel LastItemLevel ile aynı ise, 
			}else if(currentMode.equals("IF") && currentLevel==commandBuffer.get(commandBuffer.size()-1).getLevelNumber() && curCommand.isSimple()){
//				Bufferdaki END-IF i koy.  LastItemMode u ve Leveli  bufferdaki sonItem ile set et.
				currentLevel--;
				commandList.add(index+1,commandBuffer.get(commandBuffer.size()-1)); //Bufferdaki son elemanı koy.
				commandBuffer.remove(commandBuffer.size()-1);//Bufferdan sil.
				if(commandBuffer.size()==0){
					currentMode="";
				}else{
					currentMode=commandBuffer.get(commandBuffer.size()-1).getMode();
				}
				
				
				logger.debug("Case 6: CurrentMode: IF+DO  CurrentLevel:"+currentLevel+" CurCommand:"+curCommand.getSatirNumarasi()+curCommand.getCommandName());
			}else if(currentMode.equals("IF") && currentLevel-1==commandBuffer.get(commandBuffer.size()-1).getLevelNumber() && curCommand.isEnder()){
//						Bufferdaki END-IF i koy.  LastItemMode u ve Leveli  bufferdaki sonItem ile set et.
					currentLevel--;
					commandList.add(index+1,commandBuffer.get(commandBuffer.size()-1)); //Bufferdaki son elemanı koy.
					commandBuffer.remove(commandBuffer.size()-1);//Bufferdan sil.
					if(commandBuffer.size()==0){
						currentMode="";
					}else{
						currentMode=commandBuffer.get(commandBuffer.size()-1).getMode();
					}
					
					
					logger.debug("Case 6: CurrentMode: IF+DO  CurrentLevel:"+currentLevel+" CurCommand:"+curCommand.getSatirNumarasi()+curCommand.getCommandName());
			}else if(curCommand.isStarter()){
					currentLevel++;
					logger.debug("Case 7: CurrentMode: IF+DO  CurrentLevel:"+currentLevel+" CurCommand:"+curCommand.getSatirNumarasi()+curCommand.getCommandName());
			}else if(curCommand.isEnder()){
					currentLevel--;
					logger.debug("Case 8: CurrentMode: IF+DO  CurrentLevel:"+currentLevel+" CurCommand:"+curCommand.getSatirNumarasi()+curCommand.getCommandName());
			}
		
		}
		
	}
	
	
	/**
	 * 0) OnError den sonra mutlaka DO olur. DO yoksa 
	 * bu method bir şey yapmaz. O durumla karşılaşılırsa başka bir method yazılmali.
	 * 
	 *//*
	private void addEndErrorWithDoDoend() {

		if (ConversionLogModel.getInstance().getMode().equals(NaturalMode.STRUCTRURED)) {
			return;
		}

		ElementEndError elementEndError;

		int doBufferSize = 0;
		int onErrorIndex = 0;
		boolean onErrorDetected = false;

		AbstractCommand curCommand;

		for (int index = 0; index < commandList.size() - 1; index++) {

			curCommand = commandList.get(index);

			if (curCommand.getCommandName().equals(ReservedNaturalKeywords.ON_ERROR)) {
				onErrorDetected = true;
				onErrorIndex = index;

			}
			if (onErrorDetected && curCommand.getCommandName().equals(ReservedNaturalKeywords.END_ERROR)) {
				return; // 0. Asama
			}
		}

		if (!onErrorDetected) {
			return;
		}

		for (int index = onErrorIndex + 1; index < commandList.size(); index++) {

			curCommand = commandList.get(index);

			if (curCommand.equals(ReservedNaturalKeywords.DO)) {
				doBufferSize++;
			} else if (curCommand.equals(ReservedNaturalKeywords.DOEND)) {
				doBufferSize--;
			}

			if (doBufferSize == 0) {
				elementEndError = new ElementEndError("END_ERROR", "GENERAL.*.END_ERROR");
				elementEndError.setVisualCommand(true);
				commandList.add(index + 1, elementEndError);
				return;
			}
		}

	}*/


	
	

}
