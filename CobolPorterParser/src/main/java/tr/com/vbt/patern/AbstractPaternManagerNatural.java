package tr.com.vbt.patern;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.apache.log4j.Logger;


import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.natural.parser.basicverbs.ElementUndefinedCobol;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.TokenTipi;

public abstract class  AbstractPaternManagerNatural  implements PaternManager{
	
	final static Logger logger = Logger.getLogger(AbstractPaternManagerNatural.class);

	protected List<AbstractPattern> commmandPatternList=new ArrayList<AbstractPattern>();
	
	
	public AbstractCommand findBestMatchedCommand(List<AbstractToken> tokenListesi, int offset) {
		
		List<AbstractCommand> matchedCommandList=new ArrayList<AbstractCommand>();
		
		ListIterator<AbstractPattern> commandPaternIterator =commmandPatternList.listIterator();
		AbstractPattern currentPatern;
		
		
		
		logger.info("");
		logger.info("");
		logger.info("");
		logger.info("*********************************************************");
		logger.info("COMMAND KONTROL:"+tokenListesi.get(offset));
		
		logger.info("");
		
		AbstractCommand command = null;
		do{
			currentPatern=commandPaternIterator.next();
			logger.info("PATERN KONTROL:		"+tokenListesi.get(offset)+" 	 Patern:"+currentPatern.toString()+" ...");
			try {
				command=currentPatern.getmatchedCommand(tokenListesi, offset);
			} catch (ClassCastException e) {
				logger.debug("",e);
			}
			if(command!=null){
				matchedCommandList.add(command);
				logger.info("PATERN KONTROL:		"+tokenListesi.get(offset)+" 	 Patern:"+currentPatern.toString()+" ...MATCHED");
				//break; //TODO: Kaldırılabilir. İlk bulduğu doğrudur diyoruz. PErformans için. 
				
				
			}
		}while(commandPaternIterator.hasNext());

		
		logger.debug(command);
		
		if(matchedCommandList.size()==0){
			String undefinedCommandDescrtiption="";
			logger.info("COMMAND KONTROL: Bulunamadi............" );
			logger.info("*********************************************************************************************");
			AbstractToken token=tokenListesi.get(offset);
			ElementUndefinedCobol undefCommand = null;
			int matchPointOfUndefined=0;
			
			if(token.getTip().equals(TokenTipi.CommandKey)|| token.isSayi()){
				undefinedCommandDescrtiption=token.getDeger().toString();
				offset++;
				matchPointOfUndefined++;
				try {
					token=tokenListesi.get(offset);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			while(!token.isOzelKelime() && !token.isSayi()){
				offset++;
				matchPointOfUndefined++;
				try {
						token=tokenListesi.get(offset);
						if(token.getDeger()!=null){
							undefinedCommandDescrtiption=undefinedCommandDescrtiption+" "+token.getDeger();
						}
					} catch (Exception e) {
						if(token.getTip().equals(TokenTipi.Kelime)||token.getTip().equals(TokenTipi.OzelKelime)||
								token.getTip().equals(TokenTipi.Karakter)||token.getTip().equals(TokenTipi.Nokta)){
							undefCommand=new ElementUndefinedCobol(token.toString(),"UNDEFINED_COBOL_KEYWORD");
							undefCommand.setSatirNumarasi(token.getSatirNumarasi());
							undefCommand.setDataToDisplay(token.toString());
							undefCommand.getParameters().put("dataToDisplay",undefCommand.getDataToDisplay());
						}else{
							undefCommand=new ElementUndefinedCobol("UNDEFINED_COBOL_KEYWORD","UNDEFINED_COBOL_KEYWORD");
							undefCommand.setSatirNumarasi(token.getSatirNumarasi());
						}
						return undefCommand;
					}
			}
			
			
			undefCommand=new ElementUndefinedCobol(undefinedCommandDescrtiption,"UNDEFINED_COBOL_KEYWORD");
			undefCommand.setDataToDisplay(undefinedCommandDescrtiption);
			undefCommand.getParameters().put("dataToDisplay",undefCommand.getDataToDisplay());
			undefCommand.setCommandMatchPoint(matchPointOfUndefined);
			undefCommand.setSatirNumarasi(token.getSatirNumarasi());
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

