package tr.com.vbt.java.conditions;

import tr.com.vbt.java.general.JavaClassElement;
import tr.com.vbt.java.utils.JavaWriteUtilities;
import tr.com.vbt.token.AbstractToken;

public class SimpleObjectTypeGreaterThenConditionWriter implements SimpleConditionWriter{

	@Override
	public void writeSimpleCondition(AbstractToken conditionLeft, AbstractToken conOperator,
			AbstractToken conditionRight, ConditionJoiner conditionJoiner) throws Exception{
		
		boolean cast;
		
		JavaClassElement.javaCodeBuffer.append(JavaWriteUtilities.toCustomString(conditionLeft));
		
		JavaClassElement.javaCodeBuffer.append(".compareTo(");
		
		cast=JavaWriteUtilities.addCast(conditionLeft,conditionRight);
		
		JavaClassElement.javaCodeBuffer.append(JavaWriteUtilities.toCustomString(conditionRight));
		
		JavaWriteUtilities.endCast(cast);
		
		JavaWriteUtilities.addTypeChangeFunctionToEnd(conditionLeft,conditionRight);
		
		JavaClassElement.javaCodeBuffer.append(")>0");
		
	}



}
