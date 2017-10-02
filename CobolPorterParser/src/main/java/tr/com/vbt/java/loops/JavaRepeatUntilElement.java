package tr.com.vbt.java.loops;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tr.com.vbt.java.AbstractJavaElement;
import tr.com.vbt.java.general.JavaClassElement;
import tr.com.vbt.java.general.JavaConstants;
import tr.com.vbt.java.utils.ConditionUtilities;
import tr.com.vbt.java.utils.ConvertUtilities;
import tr.com.vbt.java.utils.JavaWriteUtilities;
import tr.com.vbt.lexer.ConversionLogModel;
import tr.com.vbt.lexer.NaturalMode;
import tr.com.vbt.natural.parser.datalayout.program.ElementProgramGrupNatural;
import tr.com.vbt.token.AbstractToken;
import tr.com.vbt.token.ArrayToken;
import tr.com.vbt.token.TokenTipi;


//IF A = '1' AND A = '2' --> IF (A == '1' && A == '2')


public class JavaRepeatUntilElement extends  AbstractJavaElement {
	
	final static Logger logger = LoggerFactory.getLogger(JavaRepeatUntilElement.class);
	
	private List<AbstractToken> conditionList = new ArrayList<AbstractToken>();
	
	ConditionUtilities conUtilities=new ConditionUtilities();
	
	public boolean writeJavaToStream() throws Exception {

		super.writeJavaToStream();
		try {
			conditionList = (List<AbstractToken>) this.parameters.get("conditionList");

			conUtilities.processConditions(conditionList);

			JavaClassElement.javaCodeBuffer.append("while ");
			
			JavaClassElement.javaCodeBuffer.append("(!");
			
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
			
			JavaClassElement.javaCodeBuffer.append(")");

			if (ConversionLogModel.getInstance().getMode().equals(NaturalMode.STRUCTRURED)) {
				JavaClassElement.javaCodeBuffer.append(JavaConstants.OPEN_BRACKET);
			}
			JavaClassElement.javaCodeBuffer.append(JavaConstants.NEW_LINE);

			addTryBlock();
			this.writeChildrenJavaToStream();
			addCatchBlock();
			
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
