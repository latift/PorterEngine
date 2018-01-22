package tr.com.vbt.ddm;

/**
 *   
 *     User DBA               - List DDM KET-NOTE-ATPCO -           Library: KETPROD
0 DDM-DBID     14  DDM-FNR    252 VSAM Name          Default Sequence    Page   1
0 T L  DB  Name                             F Leng  S D  Remark
  - -  --  -------------------------------- - ----  - -  -----------------------
  0 T L DB  Name                             F Leng  S D  Remark
  - -  --  -------------------------------- - ----  - -  -----------------------
  *        Generated from ADABAS FNR 252
  *        ADABAS DBID 16
    1  A0  NOT-KEY                          A    6  N
  M 1  A1  NOT-ISN                          P  9.0  N
  M 1  A2  NOT-TEXT                         A   70  N
    1  A3  NOT-EFF-FROM                     N  6.0  N
    1  A4  NOT-EFF-TO                       N  6.0  N
    1  C1  NOT-APP-FROM                     N  6.0  N
    1  C2  NOT-APP-TO                       N  6.0  N
  M 1  A5  NOT-SECURITY                     A   19  N
    1  A6  NOT-STATUS                       A    1  N
    1  A7  NOT-CLOSED-DATE                  N  6.0  N
  P 1  A8  NOT-CODE-PERIODIC
  M 2  A9  NOT-CODE                         A    7  N
  P 1  AA  NOT-TXT-PERIODIC
    2  AB  NOT-TXT-DESC                     A    2  N
    2  AC  NOT-TXT                          A   70  N
    2  AD  NOT-TXT-ID                       N  2.0  N
  P 1  B1  NOT-PERIODIC1                                /* KUUK GRUPLAR ICIN
    2  B2  NOT-ORG                          A    3  N
    2  B3  NOT-DEST                         A    3  N
    2  B4  NOT-OW                           P  9.2  N
    2  B5  NOT-RT                           P  9.2  N

 * @author User
 *
 */

public class DDM {
	
	private String TableName;
	private String T; 
	private String L;
	private String DB;
	private String Name;
	private String F;
	private String Leng;
	private String S;
	private String D;
	
	private DDM firstLevelDDM;
	
	
	
	public DDM(String tableName, String t, String l, String dB, String name, String f, String leng, String s,
			String d) {
		super();
		TableName = tableName;
		T = t;
		L = l;
		DB=dB.substring(0,1).toUpperCase()+dB.substring(1);
		Name = name;
		F = f;
		Leng = leng;
		S = s;
		D = d;
	}
	
	
	public DDM(String tableName, String t, String l, String dB, String name, String f, String leng) {
		super();
		TableName = tableName;
		T = t;
		L = l;
		DB=dB.substring(0,1).toUpperCase()+dB.substring(1);
		Name = name;
		F = f;
		Leng = leng;
	}
	
	public DDM(String tableName, String t, String l, String dB, String name) {
		super();
		TableName = tableName;
		T = t;
		L = l;
		DB=dB.substring(0,1).toUpperCase()+dB.substring(1);
		Name = name;
	}


	public String getTableName() {
		return TableName;
	}
	public void setTableName(String tableName) {
		TableName = tableName;
	}
	public String getT() {
		return T;
	}
	public void setT(String t) {
		T = t;
	}
	public String getL() {
		return L;
	}
	public void setL(String l) {
		L = l;
	}
	public String getDB() {
		return DB;
	}
	public void setDB(String dB) {
		DB=dB.substring(0,1).toUpperCase()+dB.substring(1);
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getF() {
		return F;
	}
	public void setF(String f) {
		F = f;
	}
	public String getLeng() {
		return Leng;
	}
	public void setLeng(String leng) {
		Leng = leng;
	}
	public String getS() {
		return S;
	}
	public void setS(String s) {
		S = s;
	}
	public String getD() {
		return D;
	}
	public void setD(String d) {
		D = d;
	}


	public DDM getFirstLevelDDM() {
		if(this.firstLevelDDM==null){
			return this;
		}
		return firstLevelDDM;
	}


	public void setFirstLevelDDM(DDM firstLevelDDM) {
		this.firstLevelDDM = firstLevelDDM;
	}



	
	


}
