package tr.com.vbt.natural.parser.basicverbs.patern;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.natural.parser.basicverbs.ElementCompress;
import tr.com.vbt.patern.AbstractPattern;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.KelimeToken;
import tr.com.vbt.token.OzelKelimeToken;
import tr.com.vbt.token.TokenTipi;

/**
 *    //*  5130   COMPRESS IDGIDBS-TOZLUK.ADI ' ' IDGIDBS-TOZLUK.SOYAD INTO                                                                    
      //   5132     MAP2.ADSOY1 END_COMPRESS
 *
 */
public class PaternCompressFull extends AbstractPattern{
	
	final static Logger logger = LoggerFactory.getLogger(PaternCompressFull.class);

    protected AbstractToken starterToken; //COMPRESS
	
	protected AbstractToken midfieldToken;
	
	protected AbstractToken intoToken;
	
	protected AbstractToken intoValueToken;
	
	protected AbstractToken destToken;
	
	protected AbstractToken leavingNo;
	
	protected AbstractToken enderToken;  //END_COMPRESS
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//AbstractPattern asp =new PaternAdd();

	}
	

	public PaternCompressFull() {
		super();
		
		//COMPRESS
		this.starterToken=new OzelKelimeToken("COMPRESS_FULL", 0, 0, 0);
		starterToken.setTekrarlayabilir("+");
		starterToken.setSourceFieldName("FIRST_COMMAND");
		patternTokenList.add(starterToken);
		
		//IDGIDBS-TOZLUK.ADI ' ' IDGIDBS-TOZLUK.SOYAD
		this.midfieldToken=new KelimeToken<String>();
		midfieldToken.setTekrarlayabilir("+");
		midfieldToken.setSourceFieldName("source");
		patternTokenList.add(midfieldToken);
		
		
		//INTO
		this.intoToken=new OzelKelimeToken("INTO", 0, 0, 0);
		intoToken.setTekrarlayabilir("+");
		intoToken.setSourceFieldName("paragraghParameters");
		patternTokenList.add(intoToken);
		
		// MAP2.ADSOY1
		this.intoValueToken=new KelimeToken<String>();
		intoValueToken.setTekrarlayabilir("+");
		intoValueToken.setSourceFieldName("dest");
		patternTokenList.add(intoValueToken);
		
	/*	//LEAVING NO
		this.leavingNo=new OzelKelimeToken("LEAVING_NO", 0, 0, 0);
		leavingNo.setOptional(true);
		leavingNo.setSourceFieldName("LEAVING_NO");
		patternTokenList.add(leavingNo);
		
		//LEAVING NO SPACE
		this.leavingNo=new OzelKelimeToken("LEAVING_NO_SPACE", 0, 0, 0);
		leavingNo.setOptional(true);
		leavingNo.setSourceFieldName("LEAVING_NO");
		patternTokenList.add(leavingNo);
		*/
		//LEAVING NO
		this.leavingNo=new OzelKelimeToken("LEAVE_NO", 0, 0, 0);
		leavingNo.setOptional(false);
		leavingNo.setSourceFieldName("LEAVE_NO");
		patternTokenList.add(leavingNo);
		
		//END_COMPRESS
		this.enderToken=new OzelKelimeToken(ReservedNaturalKeywords.END_COMPRESS,0,0,0);
		patternTokenList.add(enderToken);
						
		
	}
	
	@Override
	public AbstractCommand createElement(){
		ElementCompress elementDisplay = new ElementCompress(ReservedNaturalKeywords.COMPRESS,"GENERAL.*.COMPRESS");
		return elementDisplay;
	}


	@Override
	public void setTokenToElement(AbstractCommand matchedCommand,
			AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {
		
		ElementCompress matchedCommandAdd=(ElementCompress) matchedCommand;
		
		super.setSatirNumarasi(matchedCommand,currentTokenForMatch, abstractTokenInPattern);if(abstractTokenInPattern.getSourceFieldName()==null){
		
		}
		
		else if(abstractTokenInPattern.getSourceFieldName().equals("dest")){
				matchedCommandAdd.setDest(currentTokenForMatch);
				matchedCommandAdd.getParameters().put("dest", matchedCommandAdd.getDest());
		
		
		}else if(abstractTokenInPattern.getSourceFieldName().equals("source")){
			if(currentTokenForMatch.getTip().equals(TokenTipi.Karakter)&&currentTokenForMatch.getDeger().equals('#')){
				return;
			}
		
			matchedCommandAdd.getSourceList().add(currentTokenForMatch);
			
			List<AbstractToken> sourceList;
			if(matchedCommandAdd.getParameters().get("source")!=null){
				sourceList=(List<AbstractToken>) matchedCommandAdd.getParameters().get("source");
			}else{
				sourceList=new ArrayList<AbstractToken>();
			}
			sourceList.add(currentTokenForMatch);
			matchedCommandAdd.getParameters().put("source", sourceList);
		}
	}
		



	/** Sadece X TO Y yi destekler.
	 * Önek: * Comment1, Comment2, Comment3 *
	 */
	@Override
	public AbstractCommand getmatchedCommand(List<AbstractToken> tokenListesi, int offset) {
		
		ListIterator<AbstractToken> tokenListIterator=tokenListesi.listIterator(offset);
		
		AbstractToken currentTokenForMatch = null;
		
		AbstractCommand matchedCommand;
		
		matchedCommand=createElement();
		
		if(!tokenListIterator.hasNext()){
			return null;
		}
		
		while(tokenListIterator.hasNext())	{ //Satir Başı bu paternlerde önemli ancak bitişte önemli başlangıçta bunu atlıyoruz.
			currentTokenForMatch=tokenListIterator.next();
			matchedCommand.increaseCommandsMatchPoint();
			if(!currentTokenForMatch.getTip().equals(TokenTipi.SatirBasi)){
				break;
			}
		}
		
		if(!tokenListIterator.hasNext()){
			return null;
		}
		
		
		if(!currentTokenForMatch.tokenMatchs(starterToken)){
			return null;
		}
		setTokenToElement(matchedCommand, currentTokenForMatch,starterToken);
		logger.info(" MATCHED: "+currentTokenForMatch.getDeger());
		
		
		currentTokenForMatch=tokenListIterator.next();
		matchedCommand.increaseCommandsMatchPoint();
		if(!currentTokenForMatch.tokenMatchs(midfieldToken)){
			return null;
		}
		matchedCommand.setSourceCodeSentence(matchedCommand.getSourceCodeSentence(),currentTokenForMatch);
		setTokenToElement(matchedCommand, currentTokenForMatch,midfieldToken);
		logger.info(" MATCHED: "+currentTokenForMatch.getDeger());
		
		
		while(tokenListIterator.hasNext()){
			
			currentTokenForMatch=tokenListIterator.next();
			matchedCommand.increaseCommandsMatchPoint();
			
			if((currentTokenForMatch.getTip().equals(TokenTipi.OzelKelime)
					&&!currentTokenForMatch.getDeger().equals(ReservedCobolKeywords.NOT) //IFElement için eklendi
					&&!currentTokenForMatch.getDeger().equals(ReservedCobolKeywords.OR) //IFElement için eklendi
					&&!currentTokenForMatch.getDeger().equals(ReservedCobolKeywords.AND) //IFElement için eklendi
					&&!currentTokenForMatch.getDeger().equals(ReservedCobolKeywords.EQUAL)
					&&!currentTokenForMatch.getDeger().equals(ReservedCobolKeywords.IS)
					&&!currentTokenForMatch.getDeger().equals(ReservedCobolKeywords.IS_NUMERIC)
					&&!currentTokenForMatch.getDeger().equals(ReservedCobolKeywords.NUMERIC))){ //IFElement için eklendi
				if(currentTokenForMatch.tokenMatchs(intoToken)){
					logger.info(" MATCHED: "+currentTokenForMatch.getDeger());
					currentTokenForMatch=tokenListIterator.next();
					matchedCommand.increaseCommandsMatchPoint();
					if(currentTokenForMatch.getTip().equals(TokenTipi.SatirBasi)){
						currentTokenForMatch=tokenListIterator.next();
						matchedCommand.increaseCommandsMatchPoint();
						
					}
					break;
				}else{
					return null;
				}
			}
			else if(currentTokenForMatch.getTip().equals(TokenTipi.Nokta)||currentTokenForMatch.getTip().equals(TokenTipi.SatirBasi)){
				continue;
			}
			else{
					logger.info(" MATCHED"+currentTokenForMatch.getDeger());
					matchedCommand.setSourceCodeSentence(matchedCommand.getSourceCodeSentence(),currentTokenForMatch);
					setTokenToElement(matchedCommand, currentTokenForMatch,midfieldToken);
				}
		}
		
		if(!currentTokenForMatch.tokenMatchs(intoValueToken)){
			return null;
		}
		logger.info(" MATCHED: "+currentTokenForMatch.getDeger());
		setTokenToElement(matchedCommand, currentTokenForMatch,intoValueToken);
		currentTokenForMatch=tokenListIterator.next();
		matchedCommand.increaseCommandsMatchPoint();
		
		if(currentTokenForMatch.getTip().equals(TokenTipi.SatirBasi)){
			currentTokenForMatch=tokenListIterator.next();
			matchedCommand.increaseCommandsMatchPoint();
			
		}
		
		if(currentTokenForMatch.tokenMatchs(leavingNo)){
			currentTokenForMatch=tokenListIterator.next();
			matchedCommand.increaseCommandsMatchPoint();
		}
		
		if(!currentTokenForMatch.tokenMatchs(enderToken)){
			return null;
		}
		logger.info(" MATCHED: "+currentTokenForMatch.getDeger());
		return  matchedCommand;

	}
	
	
	

	
	
	
	

}
