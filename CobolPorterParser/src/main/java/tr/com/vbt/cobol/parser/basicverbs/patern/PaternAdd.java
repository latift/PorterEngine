package tr.com.vbt.cobol.parser.basicverbs.patern;

import java.util.ArrayList;
import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.cobol.parser.basicverbs.ElementAdd;
import tr.com.vbt.lexer.ReservedCobolKeywords;
import tr.com.vbt.patern.AbstractPattern;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.KelimeToken;
import tr.com.vbt.token.OzelKelimeToken;
import tr.com.vbt.token.SayiToken;

/**
 *  ADD A B TO C D
 *  ADD A B C TO D GIVING E
 * @author 47159500
 *
 */
public class PaternAdd extends AbstractPattern{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//AbstractPattern asp =new PaternAdd();

	}
	

	public PaternAdd() {
		super();
		
		
		
		//ADD
		AbstractToken astKeyword=new OzelKelimeToken("ADD", 0, 0, 0);
		//astKeyword.setDeger("ADD");
		astKeyword.setTekrarlayabilir("+");
		
		//astKeyword.setTip(TokenTipi.OzelKelime);
		
		patternTokenList.add(astKeyword);
		
		
		//A
		AbstractToken astSource=new KelimeToken<String>();
		astSource.setOptional(true);
		astSource.setSourceFieldName("SOURCE");
		patternTokenList.add(astSource);
		
		//A
		AbstractToken astSourceInt=new SayiToken<Integer>();
		astSourceInt.setOptional(true);
		astSourceInt.setSourceFieldName("SOURCE_INT");
		patternTokenList.add(astSourceInt);
				
		//TO
		AbstractToken astKeyword2=new OzelKelimeToken("TO", 0, 0, 0);
		astKeyword2.setTekrarlayabilir("+");
		patternTokenList.add(astKeyword2);
		
		// C
		AbstractToken astDest=new KelimeToken<String>();
		astDest.setTekrarlayabilir("+");
		astDest.setSourceFieldName("DESTINATION");
		patternTokenList.add(astDest);
		
		
	}
	
	@Override
	public AbstractCommand createElement(){
		ElementAdd createdElement = new ElementAdd(ReservedCobolKeywords.ADD,"PROCEDURE_DIVISION.*.ADD");
		return createdElement;
	}


	@Override
	public void setTokenToElement(AbstractCommand matchedCommand,
			AbstractToken currentTokenForMatch,
			AbstractToken abstractTokenInPattern) {
		
		ElementAdd matchedCommandAdd=(ElementAdd) matchedCommand;
		
		super.setSatirNumarasi(matchedCommand,currentTokenForMatch, abstractTokenInPattern);if(abstractTokenInPattern.getSourceFieldName()==null){
		
		}else if(abstractTokenInPattern.getSourceFieldName().equals("SOURCE")){
			
			matchedCommandAdd.getSourceNum().add((String) currentTokenForMatch.getDeger());
			
			List<String> sourceList;
			if(matchedCommandAdd.getParameters().get("SOURCE")!=null){
				sourceList=(List<String>) matchedCommandAdd.getParameters().get("SOURCE");
			}else{
				sourceList=new ArrayList<String>();
			}
			sourceList.add((String) currentTokenForMatch.getDeger());
			matchedCommandAdd.getParameters().put("SOURCE", sourceList);
			
		}else if(abstractTokenInPattern.getSourceFieldName().equals("SOURCE_INT")){
			
			Double degerAsDbl=(Double) currentTokenForMatch.getDeger();
			String degerAsStr=String.valueOf(degerAsDbl);
			matchedCommandAdd.getSourceNum().add(degerAsStr);
			
			List<String> sourceList;
			if(matchedCommandAdd.getParameters().get("SOURCE")!=null){
				sourceList=(List<String>) matchedCommandAdd.getParameters().get("SOURCE");
			}else{
				sourceList=new ArrayList<String>();
			}
			sourceList.add(degerAsStr);
			matchedCommandAdd.getParameters().put("SOURCE", sourceList);
			
		}else if(abstractTokenInPattern.getSourceFieldName().equals("DESTINATION")){
			matchedCommandAdd.getDestNum().add((String) currentTokenForMatch.getDeger());
			
			List<String> sourceList;
			if(matchedCommandAdd.getParameters().get("DESTINATION")!=null){
				sourceList=(List<String>) matchedCommandAdd.getParameters().get("DESTINATION");
			}else{
				sourceList=new ArrayList<String>();
			}
			sourceList.add((String) currentTokenForMatch.getDeger());
			matchedCommandAdd.getParameters().put("DESTINATION", sourceList);
		}
	}


	@Override
	public String toString() {
	return "ADD PATERN";
	}


		





	
	
	

	
	
	
	

}
