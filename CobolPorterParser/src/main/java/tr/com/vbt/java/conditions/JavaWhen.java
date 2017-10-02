package tr.com.vbt.java.conditions;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tr.com.vbt.java.AbstractJava;
import tr.com.vbt.java.AbstractJavaElement;
import tr.com.vbt.java.general.JavaClassElement;
import tr.com.vbt.java.general.JavaConstants;
import tr.com.vbt.java.loops.JavaDoElement;
import tr.com.vbt.java.utils.ConditionUtilities;
import tr.com.vbt.java.utils.ConvertUtilities;
import tr.com.vbt.lexer.ConversionLogModel;
import tr.com.vbt.lexer.NaturalMode;
import tr.com.vbt.token.AbstractToken;

public class JavaWhen extends AbstractJavaElement {

	final static Logger logger = LoggerFactory.getLogger(JavaWhen.class);

	private List<AbstractToken> conditionList = new ArrayList<AbstractToken>();

	ConditionUtilities conUtilities = new ConditionUtilities();

	boolean childIsDoElement;
	
	
	@Override
	public boolean writeJavaToStream() throws Exception {
		
		super.writeJavaToStream();

		try {

			conditionList = (List<AbstractToken>) this.parameters.get("conditionList");

			childIsDoElement = isFirstChildDoElement();

			if (this.parent != null
					&& this.parent.getJavaElementName().equalsIgnoreCase("JavaSwitchDecideFirstCondition")) {
				writeSwitchDecideFirstCondition();
				return true;
			}

			if (conditionList == null) {

				JavaClassElement.javaCodeBuffer.append(JavaConstants.NEW_LINE);

				JavaClassElement.javaCodeBuffer.append("else ");

				if (ConversionLogModel.getInstance().getMode().equals(NaturalMode.STRUCTRURED) || !childIsDoElement) {
					JavaClassElement.javaCodeBuffer.append(JavaConstants.OPEN_BRACKET);
				}
				JavaClassElement.javaCodeBuffer.append(JavaConstants.NEW_LINE);

				this.writeChildrenJavaToStream();

				if (ConversionLogModel.getInstance().getMode().equals(NaturalMode.STRUCTRURED) || !childIsDoElement) {
					JavaClassElement.javaCodeBuffer.append(JavaConstants.CLOSE_BRACKET + "// if");
				}

			} else {

				conUtilities.processConditions(conditionList);

				JavaClassElement.javaCodeBuffer.append("if ");

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

				if (ConversionLogModel.getInstance().getMode().equals(NaturalMode.STRUCTRURED) || !childIsDoElement) {
					JavaClassElement.javaCodeBuffer.append(JavaConstants.OPEN_BRACKET);
				}
				JavaClassElement.javaCodeBuffer.append(JavaConstants.NEW_LINE);

				this.writeChildrenJavaToStream();

				if (ConversionLogModel.getInstance().getMode().equals(NaturalMode.STRUCTRURED) || !childIsDoElement) {
					JavaClassElement.javaCodeBuffer.append(JavaConstants.CLOSE_BRACKET + "// if");
				}

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

	private void writeSwitchDecideFirstCondition() throws Exception {
		
		JavaSwitchDecideFirstCondition parentElement=(JavaSwitchDecideFirstCondition) this.parent;
		
		if (conditionList == null) {

			JavaClassElement.javaCodeBuffer.append(JavaConstants.NEW_LINE);

			JavaClassElement.javaCodeBuffer.append("else ");

			childIsDoElement = isFirstChildDoElement();
			if (ConversionLogModel.getInstance().getMode().equals(NaturalMode.STRUCTRURED) || !childIsDoElement) {
				JavaClassElement.javaCodeBuffer.append(JavaConstants.OPEN_BRACKET);
			}
			JavaClassElement.javaCodeBuffer.append(JavaConstants.NEW_LINE);

			this.writeChildrenJavaToStream();

			if (ConversionLogModel.getInstance().getMode().equals(NaturalMode.STRUCTRURED) || !childIsDoElement) {
				JavaClassElement.javaCodeBuffer.append(JavaConstants.CLOSE_BRACKET + "// if");
			}

		} else {

			conUtilities.processConditions(conditionList);

			if(!parentElement.firstChildIfOperated){

				JavaClassElement.javaCodeBuffer.append("if ");
				
				parentElement.firstChildIfOperated=true;
			}else{
				JavaClassElement.javaCodeBuffer.append("else if ");
			}

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

			childIsDoElement = isFirstChildDoElement();
			if (ConversionLogModel.getInstance().getMode().equals(NaturalMode.STRUCTRURED) || !childIsDoElement) {
				JavaClassElement.javaCodeBuffer.append(JavaConstants.OPEN_BRACKET);
			}
			JavaClassElement.javaCodeBuffer.append(JavaConstants.NEW_LINE);

			this.writeChildrenJavaToStream();

			if (ConversionLogModel.getInstance().getMode().equals(NaturalMode.STRUCTRURED) || !childIsDoElement) {
				JavaClassElement.javaCodeBuffer.append(JavaConstants.CLOSE_BRACKET + "// if");
			}

		}
		JavaClassElement.javaCodeBuffer.append(JavaConstants.NEW_LINE);

	}

	private boolean isFirstChildDoElement() {
		AbstractJava child0;
		if (this.getChildren() == null || this.getChildren().isEmpty()) {
			return false;
		}
		if (this.getChildren().get(0) != null) {
			child0 = this.getChildren().get(0);
			if (child0 instanceof JavaDoElement) {
				return true;
			}
		}
		return false;
	}

}
