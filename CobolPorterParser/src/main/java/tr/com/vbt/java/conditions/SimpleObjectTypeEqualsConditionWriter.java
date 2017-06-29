package tr.com.vbt.java.conditions;

import tr.com.vbt.java.general.JavaClassElement;
import tr.com.vbt.java.general.JavaConstants;
import tr.com.vbt.java.utils.JavaWriteUtilities;
import tr.com.vbt.token.AbstractToken;

public class SimpleObjectTypeEqualsConditionWriter implements SimpleConditionWriter{

	//*S**  IF STR1 EQ STR2
	//*S** IF A =5
	//*S** IF A =' '
	
	@Override
	public void writeSimpleCondition(AbstractToken conditionLeft, AbstractToken conOperator,
			AbstractToken conditionRight, ConditionJoiner conditionJoiner) throws Exception {
		
		JavaClassElement.javaCodeBuffer.append(JavaWriteUtilities.toCustomString(conditionLeft));
		
		JavaClassElement.javaCodeBuffer.append(".equals(");
		
		JavaClassElement.javaCodeBuffer.append(JavaWriteUtilities.toCustomString(conditionRight));
		
		JavaClassElement.javaCodeBuffer.append(")");
		
		
	}



}
