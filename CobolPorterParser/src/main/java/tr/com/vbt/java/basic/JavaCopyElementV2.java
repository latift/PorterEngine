package tr.com.vbt.java.basic;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tr.com.vbt.java.AbstractJavaElement;
import tr.com.vbt.java.general.JavaClassElement;
import tr.com.vbt.java.general.JavaConstants;
import tr.com.vbt.java.utils.ConvertUtilities;
import tr.com.vbt.java.utils.JavaWriteUtilities;
import tr.com.vbt.token.AbstractToken;

//MOVE 0 TO CAT-CT --> cat-ct=0;
public class JavaCopyElementV2 extends AbstractJavaElement {

	final static Logger logger = LoggerFactory.getLogger(JavaCopyElementV2.class);

	private AbstractToken dataToMove;

	private List<AbstractToken> destVariable = new ArrayList<AbstractToken>();

	public boolean writeJavaToStream() throws Exception{
		super.writeJavaToStream();
		destVariable = (List<AbstractToken>) this.parameters.get("destVariable");
		dataToMove = (AbstractToken) this.parameters.get("dataToMove");
		String setterString, getterString;
		try {
			try {
				for (AbstractToken destVar1 : destVariable) {

					JavaClassElement.javaCodeBuffer.append(JavaWriteUtilities.toCustomString(destVar1));
					JavaClassElement.javaCodeBuffer.append("=");
					JavaClassElement.javaCodeBuffer.append(JavaWriteUtilities.toCustomString(dataToMove));

					JavaClassElement.javaCodeBuffer.append(JavaConstants.DOT_WITH_COMMA + JavaConstants.NEW_LINE);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
//				e.printStackTrace();
			}
		} catch (Exception e) {
			logger.debug("//Conversion Error" + this.getClass() + this.getSourceCode().getSatirNumarasi()
					+ this.getSourceCode().getCommandName());
			JavaClassElement.javaCodeBuffer
					.append("/*Conversion Error" + this.getClass() + this.getSourceCode().getSatirNumarasi()
							+ this.getSourceCode().getCommandName() + "*/" + JavaConstants.NEW_LINE);
			logger.error("//Conversion Error:" + e.getMessage(), e);
			ConvertUtilities.writeconversionErrors(e, this);
		}
		JavaClassElement.javaCodeBuffer.append(JavaConstants.NEW_LINE);
		return true;
	}

	@Override
	public boolean writeChildrenJavaToStream() {
		// TODO Auto-generated method stub
		return false;
	}

}
