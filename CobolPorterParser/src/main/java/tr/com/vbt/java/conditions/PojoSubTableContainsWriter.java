package tr.com.vbt.java.conditions;

import tr.com.vbt.ddm.DDM;
import tr.com.vbt.ddm.DDMList;
import tr.com.vbt.java.general.JavaClassElement;
import tr.com.vbt.java.util.Utility;
import tr.com.vbt.java.utils.JavaWriteUtilities;
import tr.com.vbt.token.AbstractToken;

public class PojoSubTableContainsWriter implements SimpleConditionWriter {

	//IF AWB-CLASS-CODE(*)='M' -->  if(FCU.isSubArrayContainsDifferentValue(TKS_AWB.getTksAwbB5s(),"AWB-CLASS-CODE", "M")){
	@Override
	public void writeSimpleCondition(AbstractToken conditionLeft, AbstractToken conOperator,
			AbstractToken conditionRight, ConditionJoiner conditionJoiner) throws Exception {
		
		JavaClassElement.javaCodeBuffer.append("FCU.isSubArrayContainsDifferentValue(");
		
		DDM ddm=DDMList.getInstance().getDDM(conditionLeft);
		
		JavaClassElement.javaCodeBuffer.append( JavaWriteUtilities.ruleSubTableArray(ddm,conditionLeft));
		
		JavaClassElement.javaCodeBuffer.append(",");
		
		JavaClassElement.javaCodeBuffer.append("\""+conditionLeft.getColumnNameToken().getDeger().toString()+"\"");

		JavaClassElement.javaCodeBuffer.append(",");
		
		JavaClassElement.javaCodeBuffer.append(JavaWriteUtilities.toCustomString(conditionRight));
		
		JavaClassElement.javaCodeBuffer.append(")");
		
	}
	
	

}
