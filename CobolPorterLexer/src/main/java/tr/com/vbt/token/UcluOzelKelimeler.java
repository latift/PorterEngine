package tr.com.vbt.token;

import java.util.HashSet;

import tr.com.vbt.token.IkiliOzelKelimelerNatural.DoubleWordKeyword;


/*
ACCESS MODE IS --> ACCESS_MODE_IS
FILE STATUS IS
*/
public class UcluOzelKelimeler{
	
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

public UcluOzelKelimeler() {
	super();
	ucluKelimeler.add(new TripleWordKeyword("ACCESS","MODE","IS"));
	ucluKelimeler.add(new TripleWordKeyword("FILE","STATUS","IS"));
	ucluKelimeler.add(new TripleWordKeyword("RECORD","KEY","IS"));
	ucluKelimeler.add(new TripleWordKeyword("RECORDING","MODE","IS"));
	ucluKelimeler.add(new TripleWordKeyword("LABEL","RECORDS","ARE"));
	ucluKelimeler.add(new TripleWordKeyword("NOT","LESS","THAN"));
	ucluKelimeler.add(new TripleWordKeyword("NOT","INVALID","KEY"));
	ucluKelimeler.add(new TripleWordKeyword("NOT","AT","END"));
	ucluKelimeler.add(new TripleWordKeyword("LEAVING","NO","SPACE"));
	
	
}

}


