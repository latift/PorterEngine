package tr.com.vbt.natural.parser.loops.patern;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.ReservedNaturalKeywords;
import tr.com.vbt.natural.parser.loops.ElementFor;
import tr.com.vbt.patern.AbstractPattern;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.GenelTipToken;
import tr.com.vbt.token.KarakterToken;
import tr.com.vbt.token.KelimeToken;
import tr.com.vbt.token.OzelKelimeToken;

/**
//FOR I = 1 TO SECILENSAY	
// FOR J=1 TO IEKSI1
//FOR I:=1 TO SECILENSAY 
 *FOR K 1  15
 *
 */
public class PaternFor extends AbstractPattern{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//AbstractPattern asp =new PaternAdd();

	}
	

	public PaternFor() {
		super();
		
		//FOR
		AbstractToken astKeyword=new OzelKelimeToken("FOR", 0, 0, 0);
		astKeyword.setSourceFieldName("FIRST_COMMAND");
		patternTokenList.add(astKeyword);
		
		//I
		AbstractToken astSource=new KelimeToken<String>();
		astSource.setSourceFieldName("indexName");
		patternTokenList.add(astSource);
		
		//:
		AbstractToken astSource2=new KarakterToken(':', 0,0,0);
		astSource2.setOptional(true);
		patternTokenList.add(astSource2);
				
		//=
		AbstractToken astSource3=new KarakterToken('=', 0,0,0);
		patternTokenList.add(astSource3);
		
		//1
		AbstractToken astSource4=new GenelTipToken();
		astSource4.setSourceFieldName("loopStartIndex");
		patternTokenList.add(astSource4);
		
		//TO
		AbstractToken astKeyword6=new OzelKelimeToken("TO", 0, 0, 0);
		patternTokenList.add(astKeyword6);
		
		//SECILENSAY
		AbstractToken astSource5=new GenelTipToken();
		astSource5.setSourceFieldName("loopEndPoint");
		patternTokenList.add(astSource5);
	
		
	}
	
	@Override
	public AbstractCommand createElement(){
		ElementFor elementRepeat = new ElementFor(ReservedNaturalKeywords.FOR,"GENERAL.*.FOR");
		return elementRepeat;
	}


	@Override
	public void setTokenToElement(AbstractCommand matchedCommand,
			AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {
		
		ElementFor matchedCommandAdd=(ElementFor) matchedCommand;
		
		super.setSatirNumarasi(matchedCommand,currentTokenForMatch, abstractTokenInPattern);if(abstractTokenInPattern.getSourceFieldName()==null){
		
		}else if(abstractTokenInPattern.getSourceFieldName().equals("indexName")){
				
			matchedCommandAdd.setIndexName((String) currentTokenForMatch.getDeger());
			
			matchedCommandAdd.getParameters().put("indexName", matchedCommandAdd.getIndexName());
			
		}else if(abstractTokenInPattern.getSourceFieldName().equals("loopStartIndex")){
			
			matchedCommandAdd.setLoopStartIndex(currentTokenForMatch);
				
			matchedCommandAdd.getParameters().put("loopStartIndex", matchedCommandAdd.getLoopStartIndex());
		
		}else if(abstractTokenInPattern.getSourceFieldName().equals("loopEndPoint")){
			
			matchedCommandAdd.setLoopEndPoint(currentTokenForMatch);
			
			matchedCommandAdd.getParameters().put("loopEndPoint", matchedCommandAdd.getLoopEndPoint());
			
		}
	}
		





	
	
	

	
	
	
	

}
