package tr.com.vbt.java.database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tr.com.vbt.java.AbstractJavaElement;
import tr.com.vbt.java.general.JavaClassElement;
import tr.com.vbt.java.general.JavaConstants;
import tr.com.vbt.java.utils.ConvertUtilities;
import tr.com.vbt.token.AbstractToken;


//// 6214 STORE IDGIDBS-TGECICI  --> -->save(KET_AIRLINE_DAO, KET_AIRLINE);
public class JavaSaveElement extends  AbstractJavaElement{
	
	final static Logger logger = LoggerFactory.getLogger(JavaSaveElement.class);


	private AbstractToken tableName;
	
	
	@Override
	public boolean writeJavaToStream() throws Exception{ super.writeJavaToStream();

		try {
			tableName=(AbstractToken) this.parameters.get("tableName");
			
			JavaClassElement.javaCodeBuffer.append("//save("+ConvertUtilities.getDAOOfTableWithUnderscore(tableName)+","+tableName.getDeger().toString().replaceAll("-", "_")+")"+JavaConstants.DOT_WITH_COMMA);
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

}
