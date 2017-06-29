package tr.com.vbt.token;

import java.util.HashSet;


/*
AT TOP OF PAGE --> AT_TOP_OF_PAGE
END OF TRANSACTION STACK --> END_OF_TRANSACTION_STACK

*/
public class DortluOzelKelimelerNatural{
	
	public HashSet<FourWordKeyword> dortluKelimeler=new HashSet<FourWordKeyword>();
	
	public class FourWordKeyword{
		String firstKeyword;
		String secondKeyword;
		String thirdKeyword;
		String fourthKeyword;
		
		
		public FourWordKeyword(String firstKeyword, String secondKeyword,
				String thirdKeyword, String fourthKeyword) {
			super();
			this.firstKeyword = firstKeyword;
			this.secondKeyword = secondKeyword;
			this.thirdKeyword = thirdKeyword;
			this.fourthKeyword = fourthKeyword;
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
		public String getFourthKeyword() {
			return fourthKeyword;
		}
		public void setFourthKeyword(String fourthKeyword) {
			this.fourthKeyword = fourthKeyword;
		}
		
		
	}

public DortluOzelKelimelerNatural() {
	super();
	//ucluKelimeler.add(new TripleWordKeyword("ACCESS","MODE","IS"));
	dortluKelimeler.add(new FourWordKeyword("AT","TOP","OF","PAGE"));
	dortluKelimeler.add(new FourWordKeyword("AT","END","OF","PAGE"));
	dortluKelimeler.add(new FourWordKeyword("IF","NO","RECORD","FOUND"));
	dortluKelimeler.add(new FourWordKeyword("IF","NO","RECORDS","FOUND"));

}

}


