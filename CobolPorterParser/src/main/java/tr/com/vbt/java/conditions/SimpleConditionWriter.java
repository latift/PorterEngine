package tr.com.vbt.java.conditions;

import tr.com.vbt.token.AbstractToken;

public interface SimpleConditionWriter {

	public void writeSimpleCondition(AbstractToken conditionLeft, AbstractToken conOperator, AbstractToken conditionRight, ConditionJoiner conditionJoiner) throws Exception;


}
