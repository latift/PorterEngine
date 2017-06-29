package tr.com.vbt.java.file;

import tr.com.vbt.java.general.JavaInnerClassElement;


/**
 * ElementReadFile
ElementWriteFile
ElementReWriteFile
ElementDeleteFile
ElementStartFile
ElementCloseFile
ElementSelectFile
ElementSort
 * @author 47159500
 *
 */
public interface JavaFileOperations {

	/**
	 * IDENTIFICATION DIVISION.
PROGRAM-ID. HELLO.

ENVIRONMENT DIVISION.
   INPUT-OUTPUT SECTION.
      FILE-CONTROL.
      SELECT STUDENT ASSIGN TO 'input.txt'
      ORGANIZATION IS LINE SEQUENTIAL.            

DATA DIVISION.
   FILE SECTION.
   FD STUDENT.
   01 STUDENT-FILE.
      05 STUDENT-ID PIC 9(5).
      05 NAME PIC A(25).

   WORKING-STORAGE SECTION.
   01 WS-STUDENT.
      05 WS-STUDENT-ID PIC 9(5).
      05 WS-NAME PIC A(25).
   01 WS-EOF PIC A(1). 

PROCEDURE DIVISION.
   OPEN INPUT STUDENT.
      PERFORM UNTIL WS-EOF='Y'
         READ STUDENT INTO WS-STUDENT
            AT END MOVE 'Y' TO WS-EOF
            NOT AT END DISPLAY WS-STUDENT
         END-READ
      END-PERFORM.
   CLOSE STUDENT.
STOP RUN.*/
	/**
	 * Her çağrıda cursor bir artırılır.
	 * @return
	 */
	public JavaInnerClassElement readFileSequantially();


	/*
Syntax
Following is the syntax to a read record when the file access mode is random:

READ file-name RECORD INTO ws-file-structure
   KEY IS rec-key
   INVALID KEY DISPLAY 'Invalid Key'
   NOT INVALID KEY DISPLAY 'Record Details: ' ws-file-structure
END-READ.
	 */
	
	public JavaInnerClassElement readFileByKey(String Key);
	
	
/**
 * IDENTIFICATION DIVISION.
PROGRAM-ID. HELLO.

ENVIRONMENT DIVISION.
   INPUT-OUTPUT SECTION.
   FILE-CONTROL.
   SELECT STUDENT ASSIGN TO OUT1
      ORGANIZATION IS SEQUENTIAL
      ACCESS IS SEQUENTIAL
      FILE STATUS IS FS.

DATA DIVISION.
   FILE SECTION.
   FD STUDENT
   01 STUDENT-FILE.
      05 STUDENT-ID PIC 9(5).
      05 NAME PIC A(25).
      05 CLASS PIC X(3).

   WORKING-STORAGE SECTION.
   01 WS-STUDENT.
      05 WS-STUDENT-ID PIC 9(5).
      05 WS-NAME PIC A(25).
      05 WS-CLASS PIC X(3).

PROCEDURE DIVISION.
   OPEN EXTEND STUDENT.
      MOVE 1000 TO STUDENT-ID.
      MOVE 'Tim' TO NAME.
      MOVE '10' TO CLASS.
      WRITE STUDENT-FILE
      END-WRITE.	
   CLOSE STUDENT.
STOP RUN.
 */
	
	public boolean writeFile(JavaInnerClassElement record);
	
}
