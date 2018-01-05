package tr.com.vbt.java.jsp.general;

import java.io.IOException;

import org.apache.log4j.Logger;


import tr.com.vbt.java.AbstractJavaElement;
import tr.com.vbt.java.general.JavaConstants;
import tr.com.vbt.util.WriteToFile;

/*
 * 
 * <html>
<body>

</body>
</html>
 */

public class JSPGeneral extends AbstractJavaElement{
	
	final static Logger logger = Logger.getLogger(JSPGeneral.class);
	
	private static JSPGeneral instance = null;

	private JSPGeneral() {
		super();
		
	}

	public static AbstractJavaElement getInstance(){
		if(instance == null) {
	         instance = new JSPGeneral();
	      }
	      return instance;
	}

	@Override
	public boolean writeJavaToStream() throws Exception{ super.writeJavaToStream();
		AbstractJavaElement.javaCodeBuffer.append(JSPConstants.HTML_OPEN_TAG+ JavaConstants.NEW_LINE+JSPConstants.BODY_OPEN_TAG+ JavaConstants.NEW_LINE );
		this.writeChildrenJavaToStream();
		AbstractJavaElement.javaCodeBuffer.append(JSPConstants.BODY_CLOSE_TAG+ JavaConstants.NEW_LINE+JSPConstants.HTML_CLOSE_TAG+ JavaConstants.NEW_LINE );
		return true;
	}

	
	
	public StringBuilder writeJavaBaslat(String fullJavaFilePath) {
		logger.info("Start of Java Export");
		try {
			this.writeJavaToStream();
			WriteToFile.writeToFile(JSPGeneral.javaCodeBuffer,fullJavaFilePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("End of Java Export");
		return JSPGeneral.javaCodeBuffer;
		
	}
	
	public StringBuilder exportJavaTreeBaslat(String fullJavaTreePath) {
		StringBuilder sb=this.exportJavaTree();
		logger.info("Start of Java Tree Export");
		try {
			WriteToFile.writeToFile(sb,fullJavaTreePath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("End of Java Tree Export");
		return sb;
	}
	
	
	
	




	
	

}
