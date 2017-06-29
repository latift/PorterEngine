package tr.com.vbt.java.conditions;

import tr.com.vbt.java.general.JavaClassElement;
import tr.com.vbt.java.utils.JavaWriteUtilities;
import tr.com.vbt.token.AbstractToken;

public class ConditionJoiner {

	private AbstractToken conditionOperator;

	public AbstractToken getConditionOperator() {
		return conditionOperator;
	}

	public void setConditionOperator(AbstractToken conditionOperator) {
		this.conditionOperator = conditionOperator;
	}

	public ConditionJoiner(AbstractToken conditionOperator) {
		super();
		this.conditionOperator = conditionOperator;
	}
	
	public void writeCondition() throws Exception {
		JavaClassElement.javaCodeBuffer.append(JavaWriteUtilities.toCustomString(conditionOperator));
		JavaClassElement.javaCodeBuffer.append(" ");
	}
	
}
