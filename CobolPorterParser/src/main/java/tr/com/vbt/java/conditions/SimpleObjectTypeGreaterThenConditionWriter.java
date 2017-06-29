package tr.com.vbt.java.conditions;

import tr.com.vbt.java.general.JavaClassElement;
import tr.com.vbt.java.utils.JavaWriteUtilities;
import tr.com.vbt.token.AbstractToken;

public class SimpleObjectTypeGreaterThenConditionWriter implements SimpleConditionWriter{

	@Override
	public void writeSimpleCondition(AbstractToken conditionLeft, AbstractToken conOperator,
			AbstractToken conditionRight, ConditionJoiner conditionJoiner) throws Exception{
		
		JavaClassElement.javaCodeBuffer.append(JavaWriteUtilities.toCustomString(conditionLeft));
		
		JavaClassElement.javaCodeBuffer.append(".compareTo(");
		
		JavaClassElement.javaCodeBuffer.append(JavaWriteUtilities.toCustomString(conditionRight));
		
		JavaClassElement.javaCodeBuffer.append(")>0");
		
	}



}
