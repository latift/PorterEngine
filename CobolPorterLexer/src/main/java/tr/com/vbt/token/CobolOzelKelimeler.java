package tr.com.vbt.token;

import java.util.HashSet;

/**
 * Bu listenin oluşturulmasında NACA dan faydalanılmıştır.
 * @author 47159500
 *
 */

public class CobolOzelKelimeler{
	
public HashSet<String> ozelKelimeler=new HashSet<String>();

public HashSet<String> keyValueOzelKelimeler=new HashSet<String>();

public CobolOzelKelimeler() {
	super();
	
	ozelKelimeler.add("PROGRAM-ID");
	ozelKelimeler.add("AUTHOR");
	ozelKelimeler.add("DATE-WRITTEN");
	ozelKelimeler.add("WORKING-STORAGE-SECTION-WRITTEN");
	ozelKelimeler.add("WORKING-STORAGE-SECTION");
	ozelKelimeler.add("WORKING-STORAGE");
	ozelKelimeler.add("SOURCE-COMPUTER");
	ozelKelimeler.add("OBJECT-COMPUTER");
	ozelKelimeler.add("MOVE");
	ozelKelimeler.add("IF");
	ozelKelimeler.add("THEN");
	ozelKelimeler.add("DISPLAY");
	ozelKelimeler.add("ELSE");
	ozelKelimeler.add("END-IF");
	ozelKelimeler.add("ADD");
	ozelKelimeler.add("TO");
	ozelKelimeler.add("PARAGRAPH");
	ozelKelimeler.add("PERFORM");
	ozelKelimeler.add("TIMES");
	ozelKelimeler.add("GOBACK");
	ozelKelimeler.add("PIC");
	ozelKelimeler.add("END-PERFORM");
	ozelKelimeler.add("FILE-CONTROL");
	ozelKelimeler.add("PERFORM VARYING");
	ozelKelimeler.add("SECTION");
	ozelKelimeler.add("FILE");
	ozelKelimeler.add("END-EXEC");
	ozelKelimeler.add("SQLCA");
	ozelKelimeler.add("SQLCODE");
	
	
	
	
	
	ozelKelimeler.add("HOLD") ; 
	ozelKelimeler.add("ENABLED") ; 
	ozelKelimeler.add("KEYLENGTH") ; 
	ozelKelimeler.add("CLOSED") ; 
	ozelKelimeler.add("AID") ; 
	ozelKelimeler.add("DATASET") ; 
	ozelKelimeler.add("SECONDS") ; 
	ozelKelimeler.add("CONTROL") ; 
	ozelKelimeler.add("WAIT") ; 
	ozelKelimeler.add("RESOURCE") ; 
	ozelKelimeler.add("UCTRAN") ; 
	ozelKelimeler.add("ALARM") ; 
	ozelKelimeler.add("NOUCTRAN") ; 
	ozelKelimeler.add("ERASE") ; 
	ozelKelimeler.add("DATAONLY") ; 
	ozelKelimeler.add("FREEKB") ; 
	ozelKelimeler.add("TRANSID") ; 
	ozelKelimeler.add("DATALENGTH") ; 
	ozelKelimeler.add("COMMAREA") ; 
	ozelKelimeler.add("BIF") ; 
	ozelKelimeler.add("EQUAL") ; 
	ozelKelimeler.add("SQLWARNING") ; 
	ozelKelimeler.add("ENABLE") ; 
	ozelKelimeler.add("CONVERSE") ; 
	ozelKelimeler.add("COMPUTATIONAL") ; 
	ozelKelimeler.add("END_SUBTRACT") ; 
	ozelKelimeler.add("FREE") ; 
	ozelKelimeler.add("CONNECT") ; 
	ozelKelimeler.add("ALLOCATE") ; 
	ozelKelimeler.add("IMMEDIATE") ; 
	ozelKelimeler.add("EXECUTE") ; 
	ozelKelimeler.add("WORK") ; 
	ozelKelimeler.add("NUMERIC_EDITED") ; 
	ozelKelimeler.add("INDEX") ; 
	ozelKelimeler.add("END_RETURN") ; 
	ozelKelimeler.add("ALPHANUMERIC") ; 
	ozelKelimeler.add("EXCLUSIVE") ; 
	ozelKelimeler.add("LOCK") ; 
	ozelKelimeler.add("PREPARE") ; 
	ozelKelimeler.add("PICTURE") ; 
	ozelKelimeler.add("BINARY") ; 
	ozelKelimeler.add("INITIAL") ; 
	ozelKelimeler.add("PREVIOUS") ; 
	ozelKelimeler.add("LESS") ; 
	ozelKelimeler.add("EQUALS") ; 
	ozelKelimeler.add("THAN") ; 
	ozelKelimeler.add("GREATER") ; 
	ozelKelimeler.add("INVALID"); 
	ozelKelimeler.add("STATUS"); 
	ozelKelimeler.add("RANDOM"); 
	ozelKelimeler.add("DYNAMIC"); 
	ozelKelimeler.add("ACCESS"); 
	ozelKelimeler.add("SEPARATE"); 
	ozelKelimeler.add("CHARACTER");
	ozelKelimeler.add("TRAILING"); 
	ozelKelimeler.add("SIGN"); 
	ozelKelimeler.add("READPREV"); 
	ozelKelimeler.add("UNLOCK"); 
	ozelKelimeler.add("ENDBR"); 
	ozelKelimeler.add("TRANSFORM");
	ozelKelimeler.add("SKIP1");
	ozelKelimeler.add("SKIP2");
	ozelKelimeler.add("SKIP3");
	ozelKelimeler.add("DELIMITER"); 
	ozelKelimeler.add("COUNT"); 
	ozelKelimeler.add("RIGHT"); 
	ozelKelimeler.add("POSITIONING");
	ozelKelimeler.add("EJECT"); 
	ozelKelimeler.add("JUST"); 
	ozelKelimeler.add("JUSTIFIED"); 
	ozelKelimeler.add("COMMA"); 
	ozelKelimeler.add("DECIMAL_POINT"); 
	ozelKelimeler.add("SPECIAL_NAMES"); 
	ozelKelimeler.add("RELEASE"); 
	ozelKelimeler.add("KEY"); 
	ozelKelimeler.add("DESCENDING"); 
	ozelKelimeler.add("ASCENDING"); 
	ozelKelimeler.add("SORT"); 
	ozelKelimeler.add("SD"); 
	ozelKelimeler.add("LINES"); 
	ozelKelimeler.add("LINE"); 
	ozelKelimeler.add("PAGE"); 
	ozelKelimeler.add("ADVANCING"); 
	ozelKelimeler.add("CURRENT_DATE");
	ozelKelimeler.add("CURRENT__DATE");
	ozelKelimeler.add("CURRENT__TIME");
	ozelKelimeler.add("CURRENT__TIMESTAMP");
	ozelKelimeler.add("FUNCTION");
	ozelKelimeler.add("REVERSE");
	ozelKelimeler.add("END_WRITE"); 
	ozelKelimeler.add("MODE"); 
	ozelKelimeler.add("RECORDING"); 
	ozelKelimeler.add("COMMIT"); 
	ozelKelimeler.add("SEQUENTIAL"); 
	ozelKelimeler.add("ORGANIZATION"); 
	ozelKelimeler.add("END_DIVIDE"); 
	ozelKelimeler.add("EXTEND"); 
	ozelKelimeler.add("I_O"); 
	ozelKelimeler.add("OUTPUT"); 
	ozelKelimeler.add("INPUT"); 
	ozelKelimeler.add("TIME");
	ozelKelimeler.add("TIMESTAMP");
	ozelKelimeler.add("DAY_OF_WEEK") ; 
	ozelKelimeler.add("DAY"); 
	ozelKelimeler.add("DATE"); 
	ozelKelimeler.add("ACCEPT");
	ozelKelimeler.add("END_READ"); 
	ozelKelimeler.add("BLOCK"); 
	ozelKelimeler.add("OMITTED"); 
	ozelKelimeler.add("STANDARD"); 
	ozelKelimeler.add("ARE"); 
	ozelKelimeler.add("RECORDS"); 
	ozelKelimeler.add("CHARACTERS"); 
	ozelKelimeler.add("CONTAINS"); 
	ozelKelimeler.add("LABEL"); 
	ozelKelimeler.add("RECORD"); 
	ozelKelimeler.add("FD"); 
	ozelKelimeler.add("END_SEARCH"); 
	ozelKelimeler.add("END"); 
	ozelKelimeler.add("AT"); 
	ozelKelimeler.add("SEARCH"); 
	ozelKelimeler.add("UP"); 
	ozelKelimeler.add("STARTBR"); 
	ozelKelimeler.add("READNEXT"); 
	ozelKelimeler.add("DELETEQ"); 
	ozelKelimeler.add("WRITEQ"); 
	ozelKelimeler.add("DEQ"); 
	ozelKelimeler.add("INQUIRE"); 
	ozelKelimeler.add("READ");
	ozelKelimeler.add("END-READ"); 
	ozelKelimeler.add("START"); 
	ozelKelimeler.add("DELAY"); 
	ozelKelimeler.add("REWRITE"); 
	ozelKelimeler.add("GETMAIN"); 
	ozelKelimeler.add("WRITE"); 
	ozelKelimeler.add("SYNCPOINT"); 
	ozelKelimeler.add("SEND"); 
	ozelKelimeler.add("ASSIGN"); 
	ozelKelimeler.add("ENQ"); 
	ozelKelimeler.add("RETURN"); 
	ozelKelimeler.add("READQ"); 
	ozelKelimeler.add("ASKTIME");
	ozelKelimeler.add("XCTL"); 
	ozelKelimeler.add("LINK"); 
	ozelKelimeler.add("UPON"); 
	ozelKelimeler.add("CONSOLE"); 
	ozelKelimeler.add("DISPLAY"); 
	ozelKelimeler.add("POINTER"); 
	ozelKelimeler.add("FOR"); 
	ozelKelimeler.add("PROGRAM"); 
	ozelKelimeler.add("EXIT"); 
	ozelKelimeler.add("REMARKS"); 
	ozelKelimeler.add("END_COMPUTE"); 
	ozelKelimeler.add("ERROR"); 
	ozelKelimeler.add("BLANK"); 
	ozelKelimeler.add("END_UNSTRING"); 
	ozelKelimeler.add("UNSTRING"); 
	ozelKelimeler.add("OVERFLOW"); 
	ozelKelimeler.add("COPYREC"); 
	ozelKelimeler.add("ALPHABETIC_UPPER") ; 
	ozelKelimeler.add("ALPHABETIC"); 
	ozelKelimeler.add("END_STRING") ; 
	ozelKelimeler.add("RUN"); 
	ozelKelimeler.add("STOP"); 
	ozelKelimeler.add("MULTIPLY"); 
	ozelKelimeler.add("REMAINDER"); 
	ozelKelimeler.add("GIVING"); 
	ozelKelimeler.add("DIVIDE"); 
	ozelKelimeler.add("ROUNDED"); 
	ozelKelimeler.add("THROUGH"); 
	ozelKelimeler.add("SIZE SIZE") ; 
	ozelKelimeler.add("DELIMITED"); 
	ozelKelimeler.add("STRING"); 
	ozelKelimeler.add("FILE FILE") ; 
	ozelKelimeler.add("CORRESPONDING"); 
	ozelKelimeler.add("CORR"); 
	ozelKelimeler.add("AFTER"); 
	ozelKelimeler.add("BEFORE"); 
	ozelKelimeler.add("TEST"); 
	ozelKelimeler.add("WITH"); 
	ozelKelimeler.add("MOVE"); 
	ozelKelimeler.add("PERFORM"); 
	ozelKelimeler.add("GOTO"); 
	ozelKelimeler.add("GO"); 
	ozelKelimeler.add("TO"); 
	ozelKelimeler.add("IDENTIFICATION"); 
	ozelKelimeler.add("PROCEDURE"); 
	ozelKelimeler.add("ID"); 
	ozelKelimeler.add("DIVISION"); 
	ozelKelimeler.add("PROGRAM_ID"); 
	ozelKelimeler.add("AUTHOR"); 
	ozelKelimeler.add("DATE_WRITTEN"); 
	ozelKelimeler.add("DATE_COMPILED"); 
	ozelKelimeler.add("ENVIRONMENT"); 
	ozelKelimeler.add("DATA"); 
	ozelKelimeler.add("WORKING_STORAGE"); 
	ozelKelimeler.add("SECTION"); 
	ozelKelimeler.add("LABEL_SENTENCE"); 
	ozelKelimeler.add("PIC"); 
	ozelKelimeler.add("COMP_4"); 
	ozelKelimeler.add("COMP_3");
	ozelKelimeler.add("COMPUTATIONAL_3");
	ozelKelimeler.add("COMPUTATIONAL_4");
	ozelKelimeler.add("SYNC"); 
	ozelKelimeler.add("VALUE"); 
	ozelKelimeler.add("FILLER"); 
	ozelKelimeler.add("REDEFINES"); 
	ozelKelimeler.add("OCCURS"); 
	ozelKelimeler.add("COMP");
	ozelKelimeler.add("COMP_5");
	ozelKelimeler.add("COPY"); 
	ozelKelimeler.add("EXEC"); 
	ozelKelimeler.add("SQL"); 
	ozelKelimeler.add("INCLUDE"); 
	ozelKelimeler.add("END_EXEC"); 
	ozelKelimeler.add("SUPPRESS"); 
	ozelKelimeler.add("LINKAGE"); 
	ozelKelimeler.add("PROCEDURE");
	ozelKelimeler.add("PROCEDURE_POINTER") ;	// PJD Added
	ozelKelimeler.add("TIMES"); 
	ozelKelimeler.add("CICS"); 
	ozelKelimeler.add("IF") ; 
	ozelKelimeler.add("AND") ; 
	ozelKelimeler.add("NOT") ;
	ozelKelimeler.add("FOUND") ;		// PJD Added for EXEC SQL    WHENEVER NOT FOUND CONTINUE           END-EXEC.
	ozelKelimeler.add("ELSE") ; 
	ozelKelimeler.add("ALSO") ; 
	ozelKelimeler.add("IGNORE") ; 
	ozelKelimeler.add("HANDLE") ; 
	ozelKelimeler.add("CONDITION") ; 
	ozelKelimeler.add("RETRIEVE") ; 
	ozelKelimeler.add("INTO") ; 
	ozelKelimeler.add("END_IF") ; 
	ozelKelimeler.add("OR") ; 
	ozelKelimeler.add("RECEIVE") ; 
	ozelKelimeler.add("CALL") ;
	ozelKelimeler.add("END_CALL") ;
	ozelKelimeler.add("USING") ; 
	ozelKelimeler.add("WHEN") ; 
	ozelKelimeler.add("END-EVALUATE") ;
	ozelKelimeler.add("END_EVALUATE") ; 
	ozelKelimeler.add("EVALUATE") ; 
	ozelKelimeler.add("BY") ; 
	ozelKelimeler.add("INITIALIZE") ; 
	ozelKelimeler.add("COMPUTE") ; 
	ozelKelimeler.add("OF") ; 
	ozelKelimeler.add("IN"); 
	ozelKelimeler.add("THEN"); 
	ozelKelimeler.add("NUMERIC");
	ozelKelimeler.add("POSITIVE");
	ozelKelimeler.add("NEGATIVE");
	ozelKelimeler.add("IS"); 
	ozelKelimeler.add("GOBACK"); 
	ozelKelimeler.add("ANY"); 
	ozelKelimeler.add("NEXT"); 
	ozelKelimeler.add("SENTENCE"); 
	ozelKelimeler.add("UNTIL"); 
	ozelKelimeler.add("VARYING"); 
	ozelKelimeler.add("FROM") ; 
	ozelKelimeler.add("OTHER"); 
	ozelKelimeler.add("CONTENT"); 
	ozelKelimeler.add("REFERENCE"); 
	ozelKelimeler.add("ADDRESS"); 
	ozelKelimeler.add("ENTRY");
	ozelKelimeler.add("LENGTH"); 
	ozelKelimeler.add("END_PERFORM"); 
	ozelKelimeler.add("ADD"); 
	ozelKelimeler.add("CONTINUE"); 
	ozelKelimeler.add("ALL"); 
	ozelKelimeler.add("DEPENDING"); 
	ozelKelimeler.add("ON"); 
	ozelKelimeler.add("THRU"); 
	ozelKelimeler.add("WHENEVER"); 
	ozelKelimeler.add("SQLERROR"); 
	ozelKelimeler.add("USAGE"); 
	ozelKelimeler.add("DECLARE");
	ozelKelimeler.add("DROP");
	ozelKelimeler.add("UPDATE"); 
	ozelKelimeler.add("DELETE"); 
	ozelKelimeler.add("INSERT"); 
	ozelKelimeler.add("SET"); 
	ozelKelimeler.add("BEGIN");
	ozelKelimeler.add("WHERE"); 
	ozelKelimeler.add("VALUES"); 
	ozelKelimeler.add("SELECT"); 
	ozelKelimeler.add("OPEN"); 
	ozelKelimeler.add("CLOSE"); 
	ozelKelimeler.add("CURRENT"); 
	ozelKelimeler.add("SUBTRACT"); 
	ozelKelimeler.add("CONFIGURATION"); 
	ozelKelimeler.add("INPUT_OUTPUT"); 
	ozelKelimeler.add("SOURCE_COMPUTER"); 
	ozelKelimeler.add("OBJECT_COMPUTER"); 
	ozelKelimeler.add("FILE_CONTROL") ; 
	ozelKelimeler.add("INDEXED") ; 
	ozelKelimeler.add("INSPECT") ; 
	ozelKelimeler.add("REPLACING") ; 
	ozelKelimeler.add("TALLYING") ;
	ozelKelimeler.add("CONVERTING") ; 
	ozelKelimeler.add("LEADING") ; 
	ozelKelimeler.add("FIRST") ; 
	ozelKelimeler.add("DOWN");
	
	ozelKelimeler.add("CURSOR");  
	ozelKelimeler.add("TABLE");
	ozelKelimeler.add("NULL");
	ozelKelimeler.add("I_O_CONTROL") ;
	ozelKelimeler.add("MAIN");

}

}


