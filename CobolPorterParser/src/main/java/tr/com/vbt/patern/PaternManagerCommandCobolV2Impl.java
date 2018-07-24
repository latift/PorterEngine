package tr.com.vbt.patern;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.apache.log4j.Logger;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.basicverbs.ElementUndefinedCobol;
import tr.com.vbt.cobol.parser.v2.patern.PaternMoveV2;
import tr.com.vbt.cobol.parser.v2.patern.PaternPlusV2;
import tr.com.vbt.cobol.parser.v2.patern.PaternSubstringV2;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.TokenTipi;

public class PaternManagerCommandCobolV2Impl  implements PaternManager{

	final static Logger logger = Logger.getLogger(PaternManagerCommandCobolV2Impl.class);
	
	
	protected List<AbstractPattern> commmandPatternList=new ArrayList<AbstractPattern>();

	public PaternManagerCommandCobolV2Impl() {
		super();
		
		
		//commmandPatternList.add(new PaternSubstringV2());
		commmandPatternList.add(new PaternMoveV2());
		commmandPatternList.add(new PaternPlusV2());
		
	
		
	}
	
	
	
	
	public AbstractCommand findBestMatchedCommand(List<AbstractToken> tokenListesi, int offset) {
		
		List<AbstractCommand> matchedCommandList=new ArrayList<AbstractCommand>();
		
		ListIterator<AbstractPattern> commandPaternIterator =commmandPatternList.listIterator();
		
		AbstractPattern currentPatern;
		
		ListIterator<AbstractPattern> commandPaternSubIterator =commmandPatternList.listIterator();
		AbstractPattern currentSubPatern;
		
		String undefinedCommandDescrtiption="";
		
		logger.info("");
		logger.info("");
		logger.info("");
		logger.info("*********************************************************");
		logger.info("COMMAND KONTROL:"+tokenListesi.get(offset));
		logger.info("");
		
		AbstractCommand command;
		do {
			currentPatern = commandPaternIterator.next();
			logger.info("PATERN KONTROL:" + tokenListesi.get(offset) + " " + currentPatern.toString() + " ...");
			do {
				currentSubPatern = commandPaternSubIterator.next();
//CEPNİ
				//getmatchedCommand'e Anacommand ve subcommand mantığında paternler yollanır
				//Orn: Move-Move // Move-Plus // Move-Substring
				//currentPatern.getmatchedCommand komutuna ek olarak subpaternin yollanması gerekmekte abstractpatterne fonksiyon eklendi ve PatternV2'ye parametre eklendi.
				//currentSubPatern.patternTokenList şu an boş gitmekte ve Pattern2'de getmatchedCommand içinde null pointer almakta
				command = currentPatern.getmatchedCommand(tokenListesi, offset,currentSubPatern.patternTokenList);
				if (command != null) {
					matchedCommandList.add(command);
					logger.info("PATERN KONTROL:" + tokenListesi.get(offset) + " " + currentPatern.toString() + " ...MATCHED");
				}
			} while (commandPaternSubIterator.hasNext());
			//subpaternler için iterator sıfırlanır.
			commandPaternSubIterator =commmandPatternList.listIterator();
//CEPNİ
			
//			command = currentPatern.getmatchedCommand(tokenListesi, offset);
//			if (command != null) {
//				matchedCommandList.add(command);
//				logger.info("PATERN KONTROL:" + tokenListesi.get(offset) + " " + currentPatern.toString() + " ...MATCHED");
//				break;
//			}
			
		} while (commandPaternIterator.hasNext());
		if (command != null && command.getCommandName() != null && command.getCommandName().equals("VAC-CLSTYP")) {
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

