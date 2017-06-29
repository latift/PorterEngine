package tr.com.vbt.framework.general;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import tr.com.vbt.cobol.parser.AbstractCommand;
import tr.com.vbt.lexer.RedefinedColumn;

public abstract class AbstractVariables implements Variables{
	
	protected String sessionId;
	
	protected String programName;
	
	
	//*S****DV          0000     V1KET-NOTE-TAX-EXC                KET-MULTIFILE
	//localVariableReferans.add(new ElementDBViewOfNatural(1,"KET-NOTE-TAX-EXC", "KET-MULTIFILE"));
	public List<AbstractCommand> localVariableReferans=new ArrayList<AbstractCommand>();
	
	
	//*S****DD          0000A  26 2MUL-TIMESTAMP
	//tableColumnReferans.put("MUL_TIMESTAMP","KET_MULTIFILE");
	public HashMap<String, String> tableColumnReferans=new HashMap<>();
	
	
	//*S****DFR         0000A  13 3MUL-PREFIX
	//tableColumnReferans.put("MUL_PREFIX","KET_MULTIFILE");
	//tableColumnRedefiners.put("MUL_PREFIX", new RedefinedColumn("KET_KCKGRP_EXC","KET_MULTFILE","MUL_TIMESTAMP","MUL_PREFIX",0,13,"A"));
	public HashMap<String, RedefinedColumn> tableColumnRedefiners=new HashMap<>();

	public AbstractVariables(String sessionId, String programName) {
		this.sessionId=sessionId;
		this.programName=programName;
	}

	public AbstractVariables(String sessionId) {
		this.sessionId=sessionId;
	}
	
	public AbstractVariables() {
	}

	public HashMap<String, String> getTableColumnReferans() {
		return tableColumnReferans;
	}

	public void setTableColumnReferans(HashMap<String, String> tableColumnReferans) {
		this.tableColumnReferans = tableColumnReferans;
	}

	public HashMap<String, RedefinedColumn> getTableColumnRedefiners() {
		return tableColumnRedefiners;
	}

	public void setTableColumnRedefiners(HashMap<String, RedefinedColumn> tableColumnRedefiners) {
		this.tableColumnRedefiners = tableColumnRedefiners;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getProgramName() {
		return programName;
	}

	public void setProgramName(String programName) {
		this.programName = programName;
	}





	
	
}
