package tr.com.vbt.java.conditions;

import tr.com.vbt.java.general.JavaClassElement;
import tr.com.vbt.java.general.JavaConstants;
import tr.com.vbt.java.utils.ConvertUtilities;
import tr.com.vbt.java.utils.JavaWriteUtilities;
import tr.com.vbt.lexer.ConversionLogModel;
import tr.com.vbt.token.AbstractToken;

public class SimpleBigDecimalTypeEqualsConditionWriter implements SimpleConditionWriter{

	//*S**  IF STR1 EQ STR2
	//*S** IF A =5
	//*S** IF A =' '
	
	//IF D_SVFMEB(I) = 0 THEN --> if(  D_SVFMEB(I).compareTo(0)>0)
	@Override
	public void writeSimpleCondition(AbstractToken conditionLeft, AbstractToken conOperator,
			AbstractToken conditionRight, ConditionJoiner conditionJoiner) throws Exception {
		
		boolean cast;
		
		JavaClassElement.javaCodeBuffer.append(JavaWriteUtilities.toCustomString(conditionLeft));
		
		JavaClassElement.javaCodeBuffer.append(".compareTo(");
		
		cast=JavaWriteUtilities.addCast(conditionLeft,conditionRight);
		
		JavaClassElement.javaCodeBuffer.append(JavaWriteUtilities.toCustomString(conditionRight));
		
		JavaWriteUtilities.endCast(cast);
		
		JavaWriteUtilities.addTypeChangeFunctionToEnd(conditionLeft,conditionRight);
		
		JavaClassElement.javaCodeBuffer.append(")0");
		
		ConversionLogModel.getInstance().writeError(1, conditionLeft,"== olması gereken > olmuş");
		
		
	}





}
