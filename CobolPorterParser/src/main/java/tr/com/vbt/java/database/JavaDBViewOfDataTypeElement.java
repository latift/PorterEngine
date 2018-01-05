package tr.com.vbt.java.database;

import org.apache.log4j.Logger;


import tr.com.vbt.java.AbstractJavaElement;
import tr.com.vbt.java.general.JavaClassElement;
import tr.com.vbt.java.general.JavaConstants;
import tr.com.vbt.java.util.Utility;
import tr.com.vbt.java.utils.ConvertUtilities;

//*S**1 FWZ-USTGECIS VIEW HAN-USTGECIS 
/* --> 	
 * TableName: HAN-USTGECIS --> HanUstGecis
 * VariableName: FWZ-USTGECIS
 * 

	HanUstgecis FWZ_USTGECIS;
	@Autowired
	HanUstgecisDAO FWZ_USTGECIS_DAO;
	List<HanUstgecis> FWZ_USTGECIS_RESULT_LIST;
	

*/

public class JavaDBViewOfDataTypeElement extends AbstractJavaElement {

	final static Logger logger = Logger.getLogger(JavaDBViewOfDataTypeElement.class);
	
	private String typeName;  // AYK-TESL
	
	private String variableName; // TESL2


	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	@Override
	public boolean writeJavaToStream() throws Exception{ super.writeJavaToStream();
	try {
		typeName = (String) this.parameters.get("typeName");
		typeName=Utility.viewNameToPojoName(typeName);
		
		variableName=(String) this.parameters.get("variableName");
		
		
			//HanUstgecis FWZ_USTGECIS;
			JavaClassElement.javaCodeBuffer.append("public "+ typeName+" "+ variableName.replaceAll("-", "_"));
			JavaClassElement.javaCodeBuffer.append(JavaConstants.DOT_WITH_COMMA+JavaConstants.NEW_LINE);
			
			//@Autowired
		/*	JavaClassElement.javaCodeBuffer.append("@Autowired");
			JavaClassElement.javaCodeBuffer.append(JavaConstants.NEW_LINE);
			
			//HanUstgecisDAO FWZ_USTGECIS_DAO;
			JavaClassElement.javaCodeBuffer.append(typeName+"DAO "+ variableName.replaceAll("-", "_")+"_DAO");
			JavaClassElement.javaCodeBuffer.append(JavaConstants.DOT_WITH_COMMA+JavaConstants.NEW_LINE);*/
			
			//List<HanUstgecis> FWZ_USTGECIS_RESULT_LIST;
			JavaClassElement.javaCodeBuffer.append("public List<"+typeName+"> "+ variableName.replaceAll("-", "_")+"_RESULT_LIST");
			JavaClassElement.javaCodeBuffer.append(JavaConstants.DOT_WITH_COMMA);
			JavaClassElement.javaCodeBuffer.append(JavaConstants.NEW_LINE);
	} catch (Exception e) {
		logger.debug("//Conversion Error"+this.getClass()+this.getSourceCode().getSatirNumarasi()+this.getSourceCode().getCommandName());
		JavaClassElement.javaCodeBuffer.append("/*Conversion Error"+this.getClass()+this.getSourceCode().getSatirNumarasi()
				+this.getSourceCode().getCommandName()+"*/"+JavaConstants.NEW_LINE);
		logger.error("//Conversion Error:"+e.getMessage(), e); 
		ConvertUtilities.writeconversionErrors(e, this);
	}
		
		return true;
	}
	
	//HAN-KONTROL -->HanKontrol
	//HAN-KONTROL-TABLE -->HanKontrolTable
	private String formatTableName(String orjinalTableName) {
		char firstChar,secondChar;
		StringBuffer sb=new StringBuffer();
		String[] orjinalTableNameList=orjinalTableName.split("-");
		for(int i=0; i<orjinalTableNameList.length;i++){
			sb.append(orjinalTableNameList[i].substring(0,1)+orjinalTableNameList[i].substring(1).toLowerCase());
		}
		return sb.toString();
	}

}
