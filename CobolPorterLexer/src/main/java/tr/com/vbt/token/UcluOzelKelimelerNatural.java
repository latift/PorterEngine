package tr.com.vbt.token;

import java.util.HashSet;


/*
ACCESS MODE IS --> ACCESS_MODE_IS
FILE STATUS IS
*/
public class UcluOzelKelimelerNatural{
	
	public HashSet<TripleWordKeyword> ucluKelimeler=new HashSet<TripleWordKeyword>();
	
	public class TripleWordKeyword{
		String firstKeyword;
		String secondKeyword;
		String thirdKeyword;
		
		
		public TripleWordKeyword(String firstKeyword, String secondKeyword,
				String thirdKeyword) {
			super();
			this.firstKeyword = firstKeyword;
			this.secondKeyword = secondKeyword;
			this.thirdKeyword = thirdKeyword;
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
		public String getThirdKeyword() {
			return thirdKeyword;
		}
		public void setThirdKeyword(String thirdKeyword) {
			this.thirdKeyword = thirdKeyword;
		}
	}

public UcluOzelKelimelerNatural() {
	super();
	//ucluKelimeler.add(new TripleWordKeyword("ACCESS","MODE","IS"));
	ucluKelimeler.add(new TripleWordKeyword("INPUT","USING","MAP"));
	ucluKelimeler.add(new TripleWordKeyword("IF","NO","RECORDS"));
	ucluKelimeler.add(new TripleWordKeyword("INPUT","NO","ERASE"));
	ucluKelimeler.add(new TripleWordKeyword("END","OF","TRANSACTION"));
	ucluKelimeler.add(new TripleWordKeyword("FIRST","VALUE","OF"));
	ucluKelimeler.add(new TripleWordKeyword("IF","NO","RECORD"));
	ucluKelimeler.add(new TripleWordKeyword("STACK","TOP","COMMAND"));
	ucluKelimeler.add(new TripleWordKeyword("LEAVING","NO","SPACE"));
	 

}

}


