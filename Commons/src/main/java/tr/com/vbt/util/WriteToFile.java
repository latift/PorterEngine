package tr.com.vbt.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Writer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WriteToFile {
	
		final static Logger logger = LoggerFactory.getLogger(WriteToFile.class);
	
	    public static void writeToFile(StringBuilder pData, String fileFullName) throws IOException {
	        
	    	Writer outWriter=new java.io.FileWriter(fileFullName);
			BufferedWriter out = new BufferedWriter(outWriter);
	        out.write(pData.toString());
	        out.flush();
	        out.close();
	    }
	    
	    
	    public static void writeToFileByCreatingFileIfNotExist(StringBuilder pData, String fileFullName) throws IOException {
	        
	    	File f = new File(fileFullName);
	    	f.createNewFile();
	 	    Writer outWriter=new java.io.FileWriter(fileFullName);
			BufferedWriter out = new BufferedWriter(outWriter);
	        out.write(pData.toString());
	        out.flush();
	        out.close();
	    }
	    
	    public static StringBuilder readFromFile(String pFilename) throws IOException {
	        BufferedReader in = new BufferedReader(new FileReader(pFilename));
	        StringBuilder data = new StringBuilder();
	        int c = 0;
	        while ((c = in.read()) != -1) {
	            data.append((char)c);
	        }
	        in.close();
	        return data;
	    }
	    
	    public static boolean fileIsThere(String pFilename) {
	    	File f = new File(pFilename);
	    	if(f.exists() && !f.isDirectory()) { 
	    	    return true;
	    	}
	    	return false;
	    }
		public static void writeHeaderToFile(StringBuffer interfaceHeader,
				String fullFileName) throws IOException {
			
			File f = new File(fullFileName);
	    	if(f.exists() ) { 
	    		return;
	    	}
			
			Writer outWriter=new java.io.FileWriter(fullFileName,false);
			BufferedWriter out = new BufferedWriter(outWriter);
			
			out.write(interfaceHeader.toString());
			
	        out.flush();
	        out.close();
			
		}
		
		public static void appendToFile(String codeBuffer,
				String fullFileName) throws IOException {
			
			Writer outWriter=new java.io.FileWriter(fullFileName,true);
			BufferedWriter out = new BufferedWriter(outWriter);
			
			out.write(codeBuffer);
	        out.flush();
	        out.close();
			
		}


}

	
