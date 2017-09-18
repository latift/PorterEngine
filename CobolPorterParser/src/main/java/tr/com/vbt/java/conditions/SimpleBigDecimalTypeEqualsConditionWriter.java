package tr.com.vbt.java.conditions;

import tr.com.vbt.java.general.JavaClassElement;
import tr.com.vbt.java.general.JavaConstants;
import tr.com.vbt.java.utils.ConvertUtilities;
import tr.com.vbt.java.utils.JavaWriteUtilities;
import tr.com.vbt.token.AbstractToken;

public class SimpleBigDecimalTypeEqualsConditionWriter implements SimpleConditionWriter{

	//*S**  IF STR1 EQ STR2
	//*S** IF A =5
	//*S** IF A =' '
	
	//IF D_SVFMEB(I) = 0 THEN --> if(  D_SVFMEB(I).compareTo(0)>0)
	@Override
	public void writeSimpleCondition(AbstractToken conditionLeft, AbstractToken conOperator,
			AbstractToken conditionRight, ConditionJoiner conditionJoiner) throws Exception {
		
		boolean castDone=false;
		
		JavaClassElement.javaCodeBuffer.append(JavaWriteUtilities.toCustomString(conditionLeft));
		
		JavaClassElement.javaCodeBuffer.append(".compareTo(");
		
		if(ConvertUtilities.isBigDecimal(conditionLeft) && ConvertUtilities.isPrimitiveType(conditionRight)){
			castDone=addBigDecimalCast();
		}
		
		JavaClassElement.javaCodeBuffer.append(JavaWriteUtilities.toCustomString(conditionRight));
		
		if(castDone){
			JavaClassElement.javaCodeBuffer.append(")");
		}
		JavaClassElement.javaCodeBuffer.append(")>0");
		
		
	}

	private boolean addBigDecimalCast() {
		JavaClassElement.javaCodeBuffer.append("BigDecimal.valueOf(");
		return true;
	}



}
