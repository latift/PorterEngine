package tr.com.vbt.token;

import java.util.HashSet;

/**
 * Bu listenin oluşturulmasında NACA dan faydalanılmıştır.
 * @author 47159500
 *
 */

public class NaturalOzelKelimeler{
	
public HashSet<String> ozelKelimeler=new HashSet<String>();

public HashSet<String> keyValueOzelKelimeler=new HashSet<String>();

public NaturalOzelKelimeler() {
	super();
	

	  
	ozelKelimeler.add("DEFINE");
	ozelKelimeler.add("DATA");
	ozelKelimeler.add("DEFINE DATA");
	ozelKelimeler.add("MAIN_START");
	ozelKelimeler.add("DISPLAY");
	ozelKelimeler.add("END");
	ozelKelimeler.add("INCLUDE");
	ozelKelimeler.add("GET");
	ozelKelimeler.add("LOCAL");
	ozelKelimeler.add("END-LOCAL");
	ozelKelimeler.add("PARAMETER");
	ozelKelimeler.add("END-PARAMETER");
	ozelKelimeler.add("GLOBAL");
	ozelKelimeler.add("END-GLOBAL");
	ozelKelimeler.add("ADD");
	ozelKelimeler.add("TO");
	ozelKelimeler.add("IF");
	ozelKelimeler.add("ELSE");
	ozelKelimeler.add("END-IF");
	ozelKelimeler.add("FOR");
	ozelKelimeler.add("STEP");
	ozelKelimeler.add("END-FOR");
	ozelKelimeler.add("EXAMINE");
	ozelKelimeler.add("DELETE");
	ozelKelimeler.add("REPEAT");
	ozelKelimeler.add("UNTIL");
	ozelKelimeler.add("WHILE");
	
	ozelKelimeler.add("INPUT");
	ozelKelimeler.add("USING");
	ozelKelimeler.add("MAP");
	ozelKelimeler.add("THEN");
	ozelKelimeler.add("ESCAPE");
	ozelKelimeler.add("BOTTOM");
	ozelKelimeler.add("READ");
	ozelKelimeler.add("BY");
	ozelKelimeler.add("STARTING");
	ozelKelimeler.add("FROM");
	ozelKelimeler.add("ENDING");
	ozelKelimeler.add("AT");
	ozelKelimeler.add("PERFORM");
	ozelKelimeler.add("RESET");
	ozelKelimeler.add("END-READ");
	ozelKelimeler.add("END-ENDDATA");
	
	
	ozelKelimeler.add("REINPUT");
	ozelKelimeler.add("END-REPEAT");
	ozelKelimeler.add("ROUTINE");
	ozelKelimeler.add("SUBROUTINE");
	ozelKelimeler.add("END-SUBROUTINE");
	ozelKelimeler.add("COMPUTE");
	ozelKelimeler.add("ASSIGN");
	ozelKelimeler.add("MOVE");
	ozelKelimeler.add("WRITE");
	ozelKelimeler.add("LOCAL");
	ozelKelimeler.add("END-DEFINE");
	ozelKelimeler.add("TO");
	ozelKelimeler.add("REDEFINE");
	ozelKelimeler.add("VIEW");
	ozelKelimeler.add("OF");
	
	ozelKelimeler.add("FORMAT");
	ozelKelimeler.add("SET");
	ozelKelimeler.add("KEY");
	ozelKelimeler.add("NOTITLE");
	ozelKelimeler.add("END-TOPPAGE");
	ozelKelimeler.add("RESET");
	ozelKelimeler.add("FIND");
	ozelKelimeler.add("END-FIND");
	ozelKelimeler.add("MASK");
	ozelKelimeler.add("NE");
	ozelKelimeler.add("CALLNAT");
	ozelKelimeler.add("REINPUT");
	ozelKelimeler.add("EDITED");
	ozelKelimeler.add("NOT");
	ozelKelimeler.add("EQ");
	ozelKelimeler.add("GT");
	ozelKelimeler.add("LT");
	ozelKelimeler.add("GE");
	ozelKelimeler.add("LE");
	ozelKelimeler.add("NE");
	
	ozelKelimeler.add("TRANSACTION");
	ozelKelimeler.add("STACK");
	ozelKelimeler.add("INIT");
	ozelKelimeler.add("CONST");
	ozelKelimeler.add("TOP");
	ozelKelimeler.add("PAGE");
	
	ozelKelimeler.add("END-FIND");
	ozelKelimeler.add("END-ERROR");
	ozelKelimeler.add("END-NOREC");
	ozelKelimeler.add("NO");
	ozelKelimeler.add("RECORDS");
	ozelKelimeler.add("WITH");
	ozelKelimeler.add("ERASE");
	
	ozelKelimeler.add("DECIDE");
	ozelKelimeler.add("END-DECIDE");
	ozelKelimeler.add("EVERY");
	ozelKelimeler.add("ON");
	ozelKelimeler.add("FETCH");
	ozelKelimeler.add("RUN");
	ozelKelimeler.add("RETURN");
	ozelKelimeler.add("END-ENDPAGE");
	ozelKelimeler.add("IGNORE");
	
	ozelKelimeler.add("GIVING");
	ozelKelimeler.add("INDEX");
	ozelKelimeler.add("WINDOW");
	ozelKelimeler.add("LENGTH");
	ozelKelimeler.add("IN");
	
	ozelKelimeler.add("BACKOUT");
	ozelKelimeler.add("TRANSACTION");
	
	ozelKelimeler.add("SIZE");
	ozelKelimeler.add("BASE");
	ozelKelimeler.add("TITLE");
	ozelKelimeler.add("SET");
	ozelKelimeler.add("CONTROL");
	
	ozelKelimeler.add("WITH");
	ozelKelimeler.add("DIVIDE");
	ozelKelimeler.add("INTO");
	ozelKelimeler.add("REMAINDER");
	ozelKelimeler.add("SUBSTR");
	ozelKelimeler.add("SUBSTRING");
	ozelKelimeler.add("COMPRESS");
	ozelKelimeler.add("LEAVE");
	ozelKelimeler.add("NO");
	ozelKelimeler.add("FULL");
	ozelKelimeler.add("MARK");
	ozelKelimeler.add("LEAVING");
	ozelKelimeler.add("OFF");
	ozelKelimeler.add("UPDATE");
	ozelKelimeler.add("STORE");
	ozelKelimeler.add("SELECT");
	ozelKelimeler.add("END-SELECT");
	ozelKelimeler.add("AND");
	ozelKelimeler.add("OR");
	ozelKelimeler.add("SORTED_BY");
	
	ozelKelimeler.add("INTO");
	ozelKelimeler.add("MAX");
	ozelKelimeler.add("SUM");
	ozelKelimeler.add("DISTINCT");
	ozelKelimeler.add("FROM");
	ozelKelimeler.add("COUNT");
	ozelKelimeler.add("MIN");
	ozelKelimeler.add("FIRST");
	ozelKelimeler.add("VALUE");
	ozelKelimeler.add("NONE");
	
	ozelKelimeler.add("DO");
	ozelKelimeler.add("DOEND");
	ozelKelimeler.add("LOOP");
	ozelKelimeler.add("NEWPAGE");
	ozelKelimeler.add("TERMINATE");
	ozelKelimeler.add("ACCEPT");
	ozelKelimeler.add("IF_ACCEPT");
	ozelKelimeler.add("REJECT");
	ozelKelimeler.add("REJECT_IF");
	ozelKelimeler.add("COMMAND");
	ozelKelimeler.add("THRU");
	ozelKelimeler.add("LOGICAL");
	ozelKelimeler.add("DESCENDING");
	ozelKelimeler.add("ASCENDING");
	ozelKelimeler.add("CLOSE");
	ozelKelimeler.add("DOWNLOAD");
	ozelKelimeler.add("AMPERSAND");
	ozelKelimeler.add("STOP");
	ozelKelimeler.add("IMMEDIATE");
	ozelKelimeler.add("ALARM");
	ozelKelimeler.add("NAME"); //MOVE BY NAME
	ozelKelimeler.add("RIGHT");
	ozelKelimeler.add("LEFT");
	ozelKelimeler.add("JUSTIFIED");
	ozelKelimeler.add("ROUNDED");
	ozelKelimeler.add("SUBTRACT");
	ozelKelimeler.add("POSITION");
	ozelKelimeler.add("WHEN");
	ozelKelimeler.add("MULTIPLY");
	ozelKelimeler.add("REPLACE");
	ozelKelimeler.add("WITH");
	ozelKelimeler.add("IN");
	ozelKelimeler.add("PHYSICAL");
	ozelKelimeler.add("SEQUENCE");
	ozelKelimeler.add("FILLER");
	

	/*
	ozelKelimeler.add("PF1");
	ozelKelimeler.add("PF2");
	ozelKelimeler.add("PF3");
	ozelKelimeler.add("PF4");
	ozelKelimeler.add("PF5");
	ozelKelimeler.add("PF6");
	ozelKelimeler.add("PF7");
	ozelKelimeler.add("PF8");
	ozelKelimeler.add("PF9");
	ozelKelimeler.add("PF10");
	ozelKelimeler.add("PF11");
	ozelKelimeler.add("PF12");
	ozelKelimeler.add("PF13");
	ozelKelimeler.add("PF14");
	ozelKelimeler.add("PF15");
	ozelKelimeler.add("PF16");
	ozelKelimeler.add("PF17");
	ozelKelimeler.add("PF18");
	ozelKelimeler.add("PF20");
	ozelKelimeler.add("PF21");
	ozelKelimeler.add("PF22");
	ozelKelimeler.add("PF23");
	ozelKelimeler.add("PF24");*/
	
	
/*	ozelKelimeler.add("PF-KEY");
	ozelKelimeler.add("DATX");
	ozelKelimeler.add("DAT4E");
	ozelKelimeler.add("TIMX");
	ozelKelimeler.add("USER");
	ozelKelimeler.add("PROGRAM");
	ozelKelimeler.add("DEVICE");
	ozelKelimeler.add("LANGUAGE");
	ozelKelimeler.add("COUNTER");
	*/
}

}


