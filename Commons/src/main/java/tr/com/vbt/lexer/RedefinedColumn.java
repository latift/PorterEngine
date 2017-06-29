package tr.com.vbt.lexer;

import java.lang.reflect.Field;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//
public class RedefinedColumn {
	
	final static Logger logger = LoggerFactory.getLogger(RedefinedColumn.class);
	
	private String view;
	
	private String table;
	private String column;
	private String redefineName;
	private int startIndex=-1;
	private int endIndex=-1;
	private String variableType;


	public RedefinedColumn(String view, String table, String column, String redefineName, int startIndex, int endIndex,
			String variableType) {
		super();
		this.view = view;
		this.table = table;
		this.column = column;
		this.redefineName = redefineName;
		this.startIndex = startIndex;
		this.endIndex = endIndex;
		this.variableType = variableType;
	}

	
	

	public RedefinedColumn(String view, String table, String column, String redefineName, String variableType) {
		super();
		this.view = view;
		this.table = table;
		this.column = column;
		this.redefineName = redefineName;
		this.variableType = variableType;
	}

	
	public RedefinedColumn(String view, String table, String column, String redefineName) {
		super();
		this.view = view;
		this.table = table;
		this.column = column;
		this.redefineName = redefineName;
	}



	public String getView() {
		return view;
	}


	public void setView(String view) {
		this.view = view;
	}


	public String getTable() {
		return table;
	}


	public void setTable(String table) {
		this.table = table;
	}


	public String getColumn() {
		return column;
	}


	public void setColumn(String column) {
		this.column = column;
	}


	public String getRedefineName() {
		return redefineName;
	}


	public void setRedefineName(String redefineName) {
		this.redefineName = redefineName;
	}


	public int getStartIndex() {
		return startIndex;
	}


	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}


	public int getEndIndex() {
		return endIndex;
	}


	public void setEndIndex(int endIndex) {
		this.endIndex = endIndex;
	}


	public String getVariableType() {
		return variableType;
	}


	public void setVariableType(String variableType) {
		this.variableType = variableType;
	}

	
	

}
