package tr.com.vbt.token;

import java.util.HashSet;

import tr.com.vbt.lexer.ConversionLogModel;
import tr.com.vbt.lexer.NaturalMode;
import tr.com.vbt.token.UcluOzelKelimelerNatural.TripleWordKeyword;



public class IkiliOzelKelimelerNatural{
	
	public HashSet<DoubleWordKeyword> ikiliKelimeler=new HashSet<DoubleWordKeyword>();
	
	public class DoubleWordKeyword{
		String firstKeyword;
		String secondKeyword;
		
		
		public DoubleWordKeyword(String firstKeyword, String secondKeyword) {
			super();
			this.firstKeyword = firstKeyword;
			this.secondKeyword = secondKeyword;
		}
		public String getFirstKeyword() {
			return firstKeyword;
		}
		public void setFirstKeyword(String firstKeyword) {
			this.firstKeyword = firstKeyword;
		}
		public String getSecondKeyword() {
			return secondKeyword;
		}
		public void setSecondKeyword(String secondKeyword) {
			this.secondKeyword = secondKeyword;
		}
		
		
	}

public IkiliOzelKelimelerNatural() {
	super();
	
	
	ikiliKelimeler.add(new DoubleWordKeyword("GLOBAL","USING"));
	ikiliKelimeler.add(new DoubleWordKeyword("LOCAL","USING"));
	ikiliKelimeler.add(new DoubleWordKeyword("STARTING","FROM"));
	ikiliKelimeler.add(new DoubleWordKeyword("ENDING","AT"));
	ikiliKelimeler.add(new DoubleWordKeyword("DEFINE","SUBROUTINE"));
	ikiliKelimeler.add(new DoubleWordKeyword("ESCAPE","BOTTOM"));
	ikiliKelimeler.add(new DoubleWordKeyword("ESCAPE","TOP"));
	ikiliKelimeler.add(new DoubleWordKeyword("ESCAPE","ROUTINE"));
	ikiliKelimeler.add(new DoubleWordKeyword("DEFINE","DATA"));
	ikiliKelimeler.add(new DoubleWordKeyword("VIEW","OF"));
	ikiliKelimeler.add(new DoubleWordKeyword("SET","KEY"));
	ikiliKelimeler.add(new DoubleWordKeyword("WRITE","NOTITLE"));
	ikiliKelimeler.add(new DoubleWordKeyword("CLOSE","PRINTER"));
	ikiliKelimeler.add(new DoubleWordKeyword("FETCH","RETURN"));
	ikiliKelimeler.add(new DoubleWordKeyword("GIVING","INDEX"));
	ikiliKelimeler.add(new DoubleWordKeyword("GIVING","POSITION"));
	ikiliKelimeler.add(new DoubleWordKeyword("GIVING","LENGTH"));
	ikiliKelimeler.add(new DoubleWordKeyword("GIVING","NUMBER"));
	ikiliKelimeler.add(new DoubleWordKeyword("DEFINE","WINDOW"));
	ikiliKelimeler.add(new DoubleWordKeyword("CONTROL","WINDOW"));
	ikiliKelimeler.add(new DoubleWordKeyword("CONTROL","SCREEN"));
	ikiliKelimeler.add(new DoubleWordKeyword("BACKOUT","TRANSACTION"));
	ikiliKelimeler.add(new DoubleWordKeyword("SET","CONTROL"));
	ikiliKelimeler.add(new DoubleWordKeyword("REINPUT","WITH"));
	ikiliKelimeler.add(new DoubleWordKeyword("IF","NO"));
	ikiliKelimeler.add(new DoubleWordKeyword("LEAVING","NO"));
	ikiliKelimeler.add(new DoubleWordKeyword("LEAVE","NO"));
	ikiliKelimeler.add(new DoubleWordKeyword("SET","WINDOW"));
	ikiliKelimeler.add(new DoubleWordKeyword("SORTED","BY"));
	ikiliKelimeler.add(new DoubleWordKeyword("USING","MAP"));
	ikiliKelimeler.add(new DoubleWordKeyword("END","TRANSACTION"));
	ikiliKelimeler.add(new DoubleWordKeyword("REPEAT","UNTIL"));
	ikiliKelimeler.add(new DoubleWordKeyword("REPEAT","WHILE"));
	ikiliKelimeler.add(new DoubleWordKeyword("DECIDE","ON"));
	ikiliKelimeler.add(new DoubleWordKeyword("WITH","TEXT"));
	ikiliKelimeler.add(new DoubleWordKeyword("ON","ERROR"));
	ikiliKelimeler.add(new DoubleWordKeyword("INPUT","MAP"));
	ikiliKelimeler.add(new DoubleWordKeyword("EXAMINE","FULL"));
	
	//TODO REPORTING MODDADA GEREK DUYULMAMASI LAZIM
	if(ConversionLogModel.getInstance().getMode().equals(NaturalMode.REPORTING)){
		ikiliKelimeler.add(new DoubleWordKeyword("ELSE","IF"));
	}
	ikiliKelimeler.add(new DoubleWordKeyword("ACCEPT","IF"));
	ikiliKelimeler.add(new DoubleWordKeyword("REJECT","IF"));
	ikiliKelimeler.add(new DoubleWordKeyword("STACK","COMMAND"));
	ikiliKelimeler.add(new DoubleWordKeyword("STACK","TOP"));
	ikiliKelimeler.add(new DoubleWordKeyword("LOGICAL","DESCENDING"));
	ikiliKelimeler.add(new DoubleWordKeyword("LOGICAL","ASCENDING"));
	ikiliKelimeler.add(new DoubleWordKeyword("NO","ERASE"));
	ikiliKelimeler.add(new DoubleWordKeyword("COMPUTE","ROUNDED"));
	ikiliKelimeler.add(new DoubleWordKeyword("ORDER","BY"));
	ikiliKelimeler.add(new DoubleWordKeyword("GROUP","BY"));
}

}



