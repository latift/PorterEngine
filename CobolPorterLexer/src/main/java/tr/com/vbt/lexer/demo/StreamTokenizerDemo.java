package tr.com.vbt.lexer.demo;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StreamTokenizer;

import org.apache.log4j.Logger;


public class StreamTokenizerDemo {

	   final static Logger logger = Logger.getLogger(StreamTokenizerDemo.class);
	
	   public static void main(String[] args) {

	      try {
	    	 
	  		 
	  		InputStream inputStream       = new FileInputStream("C:/test.txt");
	  		Reader      inputStreamReader = new InputStreamReader(inputStream);
	 		 StreamTokenizer st = new StreamTokenizer(inputStreamReader);

	         // print the stream tokens
	         boolean eof = false;
	         do {

	            int token = st.nextToken();
	            switch (token) {
	               case StreamTokenizer.TT_EOF:
	                  logger.info("End of File encountered.");
	                  eof = true;
	                  break;
	               case StreamTokenizer.TT_EOL:
	                  logger.info("End of Line encountered.");
	                  break;
	               case StreamTokenizer.TT_WORD:
	                  logger.info("Word: " + st.sval);
	                  break;
	               case StreamTokenizer.TT_NUMBER:
	                  logger.info("Number: " + st.nval);
	                  break;
	               default:
	                  logger.info((char) token + " encountered.");
	                  if (token == '!') {
	                     eof = true;
	                  }
	            }
	         } while (!eof);
	         inputStreamReader.close();
		        

	      } catch (Exception ex) {
	         ex.printStackTrace();
	      }
	   }
	}
