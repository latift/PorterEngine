package tr.com.vbt.java.conditions;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;


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

//IF A = '1' AND A = '2' --> IF (A == '1' && A == '2')

public class JavaIfElementV2 extends AbstractJavaElement {

	final static Logger logger = Logger.getLogger(JavaIfElementV2.class);

	private List<AbstractToken> conditionList = new ArrayList<AbstractToken>();
	
	ConditionUtilities conUtilities=new ConditionUtilities();


	public boolean writeJavaToStream() throws Exception {

		super.writeJavaToStream();
		
		boolean childIsDoElement;
		try {
			
			conditionList = (List<AbstractToken>) this.parameters.get("conditionList");
			
			conditionList=conUtilities.parseThruKeyword(conditionList);

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

			childIsDoElement=isFirstChildDoElement();
			if (ConversionLogModel.getInstance().getMode().equals(NaturalMode.STRUCTRURED) || !childIsDoElement) {
				JavaClassElement.javaCodeBuffer.append(JavaConstants.OPEN_BRACKET);
			}
			JavaClassElement.javaCodeBuffer.append(JavaConstants.NEW_LINE);

			this.writeChildrenJavaToStream();

			if (ConversionLogModel.getInstance().getMode().equals(NaturalMode.STRUCTRURED) || !childIsDoElement) {
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


	private boolean isFirstChildDoElement() {
		AbstractJava child0;
		if(this.getChildren()==null || this.getChildren().isEmpty()){
			return false;
		}
		if(this.getChildren().get(0)!=null){
			child0=this.getChildren().get(0);
			if(child0 instanceof JavaDoElement){
				return true;
			}
		}
		return false;
	}


}
