package tr.com.vbt.java.conditions;

import tr.com.vbt.java.general.JavaClassElement;
import tr.com.vbt.java.utils.ConvertUtilities;
import tr.com.vbt.java.utils.JavaWriteUtilities;
import tr.com.vbt.token.AbstractToken;

public class SimpleBigDecimalTypeLessThenOrEqualsConditionWriter implements SimpleConditionWriter {

	//3508   IF D_SVFMEB(I) <= ab THEN --> if(  D_SVFMEB(I).compareTo(ab)<=0)
	@Override
	public void writeSimpleCondition(AbstractToken conditionLeft, AbstractToken conOperator,
			AbstractToken conditionRight, ConditionJoiner conditionJoiner) throws Exception {

		JavaClassElement.javaCodeBuffer.append(JavaWriteUtilities.toCustomString(conditionLeft));
		
		JavaClassElement.javaCodeBuffer.append(".compareTo(");
		
		boolean castDone=false;
		if(ConvertUtilities.isBigDecimal(conditionLeft) && ConvertUtilities.isPrimitiveType(conditionRight)){
			castDone=addBigDecimalCast();
		}
		
		JavaClassElement.javaCodeBuffer.append(JavaWriteUtilities.toCustomString(conditionRight));
		
		if(castDone){
			JavaClassElement.javaCodeBuffer.append(")");
		}
		
		JavaClassElement.javaCodeBuffer.append(")<=0");
		

	}
	
	private boolean addBigDecimalCast() {
		JavaClassElement.javaCodeBuffer.append("BigDecimal.valueOf(");
		return true;
	}

}
