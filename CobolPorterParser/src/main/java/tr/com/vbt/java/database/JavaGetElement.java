package tr.com.vbt.java.database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tr.com.vbt.java.AbstractJavaElement;
import tr.com.vbt.java.general.JavaClassElement;
import tr.com.vbt.java.general.JavaConstants;
import tr.com.vbt.java.utils.ConvertUtilities;
import tr.com.vbt.java.utils.JavaWriteUtilities;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.ArrayToken;

/**
 *S**  GET KET-CITY ISNS(ITEM)
*S**  GET KET-CITY ISN
 */
/**
 * @author 47159500
 *
 */
public class JavaGetElement extends AbstractJavaElement {

	final static Logger logger = LoggerFactory.getLogger(JavaGetElement.class);
	private AbstractToken isnToken;
	private AbstractToken viewName;

	private ArrayToken isnTokenArray;

	@Override
	public boolean writeJavaToStream() throws Exception{
		super.writeJavaToStream();

		try {
			isnToken = (AbstractToken) this.getParameters().get("isnToken");
			
			viewName = (AbstractToken) this.getParameters().get("viewName");
			
			if (isnToken != null) {
				//KET_AIRLINE=KET_AIRLINE_DAO.getById((long) ISN);
				JavaClassElement.javaCodeBuffer.append(viewName.getTypeNameOfView()+" = "+ viewName.getTypeNameOfView() +"_DAO.getById((long) "+JavaWriteUtilities.toCustomString(isnToken)+");");

			}
			isnTokenArray = (ArrayToken) this.getParameters().get("isnTokenArray");
			if (isnTokenArray != null) {
				//KET_AIRLINE = KET_AIRLINE_DAO.getById((long) ISNS[ITEM-1]);
				JavaClassElement.javaCodeBuffer.append(viewName.getTypeNameOfView()+" = "+ viewName.getTypeNameOfView() +"_DAO.getById((long) "+JavaWriteUtilities.toCustomString(isnTokenArray)+");");

			}

		} catch (Exception e) {
			logger.debug("//Conversion Error"+this.getClass()+this.getSourceCode().getSatirNumarasi()+this.getSourceCode().getCommandName());
			JavaClassElement.javaCodeBuffer.append("/*Conversion Error"+this.getClass()+this.getSourceCode().getSatirNumarasi()
					+this.getSourceCode().getCommandName()+"*/"+JavaConstants.NEW_LINE);
			logger.error("//Conversion Error:"+e.getMessage(), e); 
			ConvertUtilities.writeconversionErrors(e, this);
		}

		return true;
	}

	@Override
	public boolean writeChildrenJavaToStream() {
		// TODO Auto-generated method stub
		return false;
	}

}
