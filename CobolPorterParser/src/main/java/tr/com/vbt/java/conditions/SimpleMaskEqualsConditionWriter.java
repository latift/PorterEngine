package tr.com.vbt.java.conditions;

import tr.com.vbt.java.general.JavaClassElement;
import tr.com.vbt.java.utils.JavaWriteUtilities;
import tr.com.vbt.token.AbstractToken;

public class SimpleMaskEqualsConditionWriter implements SimpleConditionWriter {

	//IF PERF15 .SERIN15 ^ = MASK (ANN ) THEN  --> !equalsMask(PERF15.getSerin15(), "ANN")
	@Override
	public void writeSimpleCondition(AbstractToken conditionLeft, AbstractToken conOperator,
			AbstractToken conditionRight, ConditionJoiner conditionJoiner) throws Exception{
		
		
		JavaClassElement.javaCodeBuffer.append("equalsMask(");
		
		JavaClassElement.javaCodeBuffer.append(JavaWriteUtilities.toCustomString(conditionLeft));
		
		JavaClassElement.javaCodeBuffer.append(",");
		
		JavaClassElement.javaCodeBuffer.append(JavaWriteUtilities.toCustomString(conditionRight));
		
		JavaClassElement.javaCodeBuffer.append(")");

	}

}
