package tr.com.vbt.java.database;

public class SQLStringObject {
	
	private String program;
	
	private int satirNo;
	
	private String sqlString;
	
	private String modul;
	
	

	public SQLStringObject(String program, int satirNo, String sqlString, String modul) {
		super();
		this.program = program;
		this.satirNo = satirNo;
		this.sqlString = sqlString;
		this.modul=modul;
	}

	public String getProgram() {
		return program;
	}

	public void setProgram(String program) {
		this.program = program;
	}



	public int getSatirNo() {
		return satirNo;
	}

	public void setSatirNo(int satirNo) {
		this.satirNo = satirNo;
	}

	public String getSqlString() {
		return sqlString;
	}

	public void setSqlString(String sqlString) {
		this.sqlString = sqlString;
	}

	public String getModul() {
		return modul;
	}

	public void setModul(String modul) {
		this.modul = modul;
	}


	

}
