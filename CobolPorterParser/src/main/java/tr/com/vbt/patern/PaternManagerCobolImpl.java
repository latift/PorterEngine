package tr.com.vbt.patern;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.apache.log4j.Logger;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.basicverbs.ElementUndefinedCobol;
import tr.com.vbt.cobol.parser.basicverbs.patern.PaternDisplay;
import tr.com.vbt.cobol.parser.basicverbs.patern.PaternEnd;
import tr.com.vbt.cobol.parser.basicverbs.patern.PaternStopRun;
import tr.com.vbt.cobol.parser.division.patern.PaternIdentificationDivision;
import tr.com.vbt.cobol.parser.general.patern.PaternMain;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.TokenTipi;

public class PaternManagerCobolImpl  implements PaternManager{

	final static Logger logger = Logger.getLogger(PaternManagerCobolImpl.class);
	
	
	protected List<AbstractPattern> commmandPatternList=new ArrayList<AbstractPattern>();

	public PaternManagerCobolImpl() {
		super();
		
		
		//Div And Sections Start
		commmandPatternList.add(new PaternIdentificationDivision());
		commmandPatternList.add(new PaternMain());
		//Div And Sections Start
		
		//DataType Start
		//DataType Start
	
		//Basic Verb Start
		commmandPatternList.add(new PaternEnd());
		commmandPatternList.add(new PaternDisplay());
		commmandPatternList.add(new PaternStopRun());
		
		//Basic Verb End
		
		//Condition Start
		//Condition End
		
		
		//Loop start
		//Loop End
		
		
		//File Start
		//File End
		
		
		//SQL Start
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

