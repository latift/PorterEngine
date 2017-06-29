package tr.com.vbt.token;

import java.util.HashSet;



public class IkiliOzelKelimelerCobol{
	
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

public IkiliOzelKelimelerCobol() {
	super();
	
	ikiliKelimeler.add(new DoubleWordKeyword("ASSIGN","TO"));
	ikiliKelimeler.add(new DoubleWordKeyword("IDENTIFICATION","DIVISION"));
	ikiliKelimeler.add(new DoubleWordKeyword("ENVIRONMENT","DIVISION"));
	ikiliKelimeler.add(new DoubleWordKeyword("PROCEDURE","DIVISION"));
	ikiliKelimeler.add(new DoubleWordKeyword("CONFIGURATION","SECTION"));
	ikiliKelimeler.add(new DoubleWordKeyword("DATA","DIVISION"));
	ikiliKelimeler.add(new DoubleWordKeyword("INPUT-OUTPUT","SECTION"));
	ikiliKelimeler.add(new DoubleWordKeyword("DATA","DIVISION")); 
	ikiliKelimeler.add(new DoubleWordKeyword("STOP","RUN"));
	ikiliKelimeler.add(new DoubleWordKeyword("WORKING-STORAGE","SECTION"));
	ikiliKelimeler.add(new DoubleWordKeyword("LOCAL-STORAGE","SECTION"));
	//ikiliKelimeler.add(new DoubleWordKeyword("OPEN","INPUT"));
	ikiliKelimeler.add(new DoubleWordKeyword("FILE","SECTION"));
	ikiliKelimeler.add(new DoubleWordKeyword("EXEC","SQL"));
	ikiliKelimeler.add(new DoubleWordKeyword("ORGANIZATION","IS"));
	ikiliKelimeler.add(new DoubleWordKeyword("PERFORM","UNTIL"));
	ikiliKelimeler.add(new DoubleWordKeyword("PERFORM","VARYING"));
	ikiliKelimeler.add(new DoubleWordKeyword("LINKAGE","SECTION"));
	ikiliKelimeler.add(new DoubleWordKeyword("INVALID","KEY"));
	ikiliKelimeler.add(new DoubleWordKeyword("KEY","IS"));
	ikiliKelimeler.add(new DoubleWordKeyword("AT","END"));
	ikiliKelimeler.add(new DoubleWordKeyword("IS","NUMERIC"));
	
}

}


