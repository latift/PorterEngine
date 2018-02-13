package tr.com.vbt.java.basic;

import java.util.List;

import org.apache.log4j.Logger;


import tr.com.vbt.java.AbstractJavaElement;
import tr.com.vbt.java.general.JavaClassElement;
import tr.com.vbt.java.general.JavaClassGeneral;
import tr.com.vbt.java.general.JavaConstants;
import tr.com.vbt.java.utils.ConvertUtilities;
import tr.com.vbt.java.utils.JavaWriteUtilities;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.ArrayToken;

// REINPUT 'No employees meet your criteria.' --> ShowDialog('No employees meet your criteria.');
public class JavaReInputElement extends AbstractJavaElement {

	final static Logger logger = Logger.getLogger(JavaReInputElement.class);

	private List<AbstractToken> dataToDisplay;
	
	private boolean isFull;

	StringBuilder dataToDisplayBuilder = new StringBuilder();
	
	String dataToDisplayAsStr;

	private AbstractToken markValue;
	private ArrayToken markValueToken;

	public boolean writeJavaToStream() throws Exception {
		super.writeJavaToStream();

		dataToDisplay = (List<AbstractToken>) this.getParameters().get("dataToDisplay");

		if (this.getParameters().get("markValue") instanceof ArrayToken) {

			markValueToken = (ArrayToken) this.getParameters().get("markValue");
			markValue = null;
		} else {
			markValue = (AbstractToken) this.getParameters().get("markValue");
			markValueToken = null;
		}

		if (this.getParameters().get("full")!=null && this.getParameters().get("full").toString().equals("true")){
			isFull=true;
		}
		try {
			for (AbstractToken item : dataToDisplay) {

				if(!item.isKarakter('-')){
					dataToDisplayBuilder.append(JavaWriteUtilities.toCustomString(item));
				}
				logger.debug(dataToDisplayBuilder.toString());
			}
			dataToDisplayAsStr=dataToDisplayBuilder.toString().replace("\"", " ");
			dataToDisplayAsStr="\""+dataToDisplayAsStr+"\"";
			logger.debug(dataToDisplayAsStr);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
//			e1.printStackTrace();
		}
		//changeDoubleQuotaToSingleQuota();
		try {
			JavaClassElement.javaCodeBuffer.append("showDialogV2(" + dataToDisplayAsStr + ")" + JavaConstants.DOT_WITH_COMMA + JavaConstants.NEW_LINE);
		} catch (Exception e) {
			logger.debug("//Conversion Error" + this.getClass() + this.getSourceCode().getSatirNumarasi() + this.getSourceCode().getCommandName());
			JavaClassElement.javaCodeBuffer.append("/*Conversion Error" + this.getClass() + this.getSourceCode().getSatirNumarasi() + this.getSourceCode().getCommandName() + "*/" + JavaConstants.NEW_LINE);
			logger.error("//Conversion Error:" + e.getMessage(), e);
			ConvertUtilities.writeconversionErrors(e, this);
		}

		return true;
	}

	private void changeDoubleQuotaToSingleQuota() {
		if (dataToDisplayAsStr.contains(JavaConstants.DOUBLE_QUOTA)) {
			String parcalar[] = dataToDisplayAsStr.split(JavaConstants.DOUBLE_QUOTA);
			String temp = "";
			if (parcalar.length == 2) {
				temp = JavaConstants.DOUBLE_QUOTA + parcalar[0] + parcalar[1] + JavaConstants.DOUBLE_QUOTA;
			} else {
				for (int i = 0; i < parcalar.length; i++) {
					if (i == 0 || i == parcalar.length - 1) {
						temp += JavaConstants.DOUBLE_QUOTA;
						if (i == 0) {
							temp += parcalar[i + 1];
						}
					} else {
						temp = temp + JavaConstants.SINGLE_QUOTA + parcalar[i + 1];
					}
				}
			}
			dataToDisplayAsStr = temp;
		}
	}

	@Override
	public boolean writeChildrenJavaToStream() {
		// TODO Auto-generated method stub
		return false;
	}

}
