package tr.com.vbt.java.conditions;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;


import tr.com.vbt.java.AbstractJavaElement;
import tr.com.vbt.java.general.JavaClassElement;
import tr.com.vbt.java.general.JavaConstants;
import tr.com.vbt.java.utils.ConditionUtilities;
import tr.com.vbt.java.utils.ConvertUtilities;
import tr.com.vbt.lexer.ConversionLogModel;
import tr.com.vbt.lexer.NaturalMode;
import tr.com.vbt.token.AbstractToken;


//IF A = '1' AND A = '2' --> IF (A == '1' && A == '2')


public class JavaElseIfElement extends  AbstractJavaElement {
	
	final static Logger logger = Logger.getLogger(JavaElseIfElement.class);
	
	private List<AbstractToken> conditionList=new ArrayList<AbstractToken>();
	
	ConditionUtilities conUtilities=new ConditionUtilities();
	
	public boolean writeJavaToStream() throws Exception {

		super.writeJavaToStream();
		try {
			conditionList = (List<AbstractToken>) this.parameters.get("conditionList");

			conUtilities.processConditions(conditionList);

			JavaClassElement.javaCodeBuffer.append("else if ");
			
			try {
				conUtilities.writeConditions();
			} catch (Exception e) {
				logger.debug("//Conversion Error" + this.getClass() + this.getSourceCode().getSatirNumarasi()
						+ this.getSourceCode().getCommandName());
				JavaClassElement.javaCodeBuffer
						.append("/*Conversion Error" + this.getClass() + this.getSourceCode().getSatirNumarasi()
								+ this.getSourceCode().getCommandName() + "*/" + JavaConstants.NEW_LINE);
				logger.error("//Conversion Error:" + e.getMessage(), e);
				ConvertUtilities.writeconversionErrors(e, this);
			}

			if (ConversionLogModel.getInstance().getMode().equals(NaturalMode.STRUCTRURED)) {
				JavaClassElement.javaCodeBuffer.append(JavaConstants.OPEN_BRACKET);
			}
			JavaClassElement.javaCodeBuffer.append(JavaConstants.NEW_LINE);

			this.writeChildrenJavaToStream();

			if (ConversionLogModel.getInstance().getMode().equals(NaturalMode.STRUCTRURED)) {
				JavaClassElement.javaCodeBuffer.append(JavaConstants.CLOSE_BRACKET + "// if");
			}

			JavaClassElement.javaCodeBuffer.append(JavaConstants.NEW_LINE);

		} catch (Exception e) {
			logger.debug("//Conversion Error" + this.getClass() + this.getSourceCode().getSatirNumarasi()
					+ this.getSourceCode().getCommandName());
			JavaClassElement.javaCodeBuffer
					.append("/*Conversion Error" + this.getClass() + this.getSourceCode().getSatirNumarasi()
							+ this.getSourceCode().getCommandName() + "*/" + JavaConstants.NEW_LINE);
			logger.error("//Conversion Error:" + e.getMessage(), e);
			ConvertUtilities.writeconversionErrors(e, this);
		}
		return true;
	}


	
}
