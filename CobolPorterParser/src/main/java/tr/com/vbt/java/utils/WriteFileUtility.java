package tr.com.vbt.java.utils;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;


public class WriteFileUtility {
	
	final static Logger logger = Logger.getLogger(WriteFileUtility.class);

	public static boolean createFileInPath(String str) throws Exception {
		File file= new File(str);

		boolean result = false;
		try {
			result=file.mkdir();
		} catch (SecurityException se) {
			logger.warn("Directory Created Edilemedi: " + str);
		}
		if(result){
			logger.warn("Directory is Created : " + str);
		}
		else{
			throw new Exception("Directory Create edilemedi."+str);
		}
		return result;
	}

	public static boolean deleteFolder(File file) throws IOException {

		boolean result = false;
		
		if (file.isDirectory()) {

			// directory is empty, then delete it
			if (file.list().length == 0) {

				result=file.delete();
				logger.warn("Directory is deleted : " + file.getAbsolutePath());

			} else {

				// list all the directory contents
				String files[] = file.list();

				for (String temp : files) {
					// construct the file structure
					File fileDelete = new File(file, temp);

					// recursive delete
					deleteFolder(fileDelete);
				}

				// check the directory again, if empty then delete it
				if (file.list().length == 0) {
					result=file.delete();
					logger.warn("Directory is deleted : " + file.getAbsolutePath());
				}else{
					logger.warn("Directory Silinemedi : " + file.getAbsolutePath());
				}
			}

		} else {
			// if file, then delete it
			result=file.delete();
			logger.warn("File is deleted : " + file.getAbsolutePath());
		}
		
		return result;
	}

}
